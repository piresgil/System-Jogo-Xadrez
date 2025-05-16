/**
 * @author Daniel Gil
 */
package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 * Representa a peça Torre no jogo de xadrez.
 * <p>
 * A Torre é uma {@link ChessPiece} que pode se mover qualquer número de casas
 * horizontalmente ou verticalmente.
 */
public class Rook extends ChessPiece {

    /// Construtor
    /// inicializa uma nova Torre com o tabuleiro
    /// em que ela está localizada e a sua cor.
    ///
    /// Chama o construtor da superclasse [ChessPiece] para associar a Torre
    /// ao tabuleiro e definir a sua cor.
    ///
    /// @param board O tabuleiro de xadrez onde a Torre será colocada.
    /// @param color A cor da Torre ([branco][#WHITE] ou [preto][#BLACK]).
    public Rook(Board board, Color color) {
        super(board, color);
    }

    /**
     * Metodo To String
     *
     * @return Uma string "R" representando a Torre.
     */
    @Override
    public String toString() {
        return "R";
    }

    /**
     * Method possibleMoves
     * Retorna uma matriz booleana indicando os movimentos possíveis da Torre
     * na sua posição atual.
     * <p>
     * Este método sobrescreve o método {@link ChessPiece#possibleMoves()} da
     * superclasse. A Torre pode se mover qualquer número de casas horizontalmente
     * ou verticalmente. O método verifica os movimentos possíveis em quatro direções:
     * acima, abaixo, esquerda e direita, parando quando encontra o limite do
     * tabuleiro ou outra peça. As casas ocupadas por peças adversárias também são
     * marcadas como movimentos possíveis (para captura).
     *
     * @return Uma matriz booleana com as mesmas dimensões do tabuleiro, onde
     * `true` indica que a casa correspondente é um movimento possível para a
     * Torre, e `false` caso contrário.
     */
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);

        // Above
        p.setValues(position.getRow() - 1, position.getColumn());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Left
        p.setValues(position.getRow(), position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Right
        p.setValues(position.getRow(), position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Below
        p.setValues(position.getRow() + 1, position.getColumn());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        return mat;
    }
}