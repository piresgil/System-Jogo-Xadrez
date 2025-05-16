/**
 * @author Daniel Gil
 */
package chess;

import boardgame.Position;

/**
 * Representa uma posição no sistema de coordenadas do xadrez (e.g., a1, h8).
 */
public class ChessPosition {
    /**
     * A coluna da posição (de 'a' a 'h').
     */
    private char column;
    /**
     * A linha da posição (de 1 a 8).
     */
    private int row;

    /**
     * Construtor da classe `ChessPosition` que inicializa uma nova posição de xadrez.
     *
     * @param column A letra da coluna (de 'a' a 'h').
     * @param row    O número da linha (de 1 a 8).
     * @throws ChessException Se a linha ou a coluna estiverem fora dos limites válidos do tabuleiro de xadrez.
     */
    public ChessPosition(char column, int row) {
        if (row < 1 || row > 8 || column > 'h' || column < 'a') {
            throw new ChessException("Error instantiating ChessPossition. Valid values are from a1 to h8.");
        }
        this.column = column;
        this.row = row;
    }

    // Getters

    /**
     * @return A letra da coluna ('a' a 'h').
     */
    public char getColumn() {
        return column;
    }

    /**
     * @return O número da linha (1 a 8).
     */
    public int getRow() {
        return row;
    }

    /**
     * Method toPosition
     * Converte esta posição de xadrez para uma posição na matriz bidimensional do tabuleiro (`boardgame.Position`).
     * A linha é convertida de 1-8 para 7-0 (indexação da matriz) e a coluna de 'a'-'h' para 0-7.
     *
     * @return Um objeto {@link boardgame.Position} correspondente a esta posição de xadrez.
     */
    protected Position toPosition() {
        return new Position(8 - row, column - 'a');
    }

    /**
     * Method fromPosition
     * Converte uma posição na matriz bidimensional do tabuleiro (`boardgame.Position`) para uma posição de xadrez (`ChessPosition`).
     * A linha é convertida de 0-7 para 8-1 e a coluna de 0-7 para 'a'-'h'.
     *
     * @param position A {@link boardgame.Position} a ser convertida.
     * @return Um objeto {@link ChessPosition} correspondente à posição na matriz do tabuleiro.
     */
    protected static ChessPosition fromPosition(Position position) {
        return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
    }

    /**
     * Method ToString
     *
     * @return Uma string representando a posição de xadrez.
     */
    @Override
    public String toString() {
        return "" + column + row;
    }
}
