/**
 * @author Daniel Gil
 */
package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 * Representa a peça do Bispo no jogo de xadrez.
 * <p>
 * O Bispo move-se qualquer número de casas nas diagonais.
 */
public class Bishop extends ChessPiece {
    /// Construtor da classe `Bishop`.
    ///
    /// @param board A instância do tabuleiro em que o Bispo está.
    /// @param color A cor do Bispo ([branco][#WHITE] ou
    ///              [preto][#BLACK]).
    public Bishop(Board board, Color color) {
        super(board, color);
    }

    /**
     * Retorna uma representação em String do Bispo para fins de exibição
     * no tabuleiro.
     * <p>
     * Este método sobrescreve o método {@link Object#toString()} da superclasse.
     *
     * @return Uma string "B" representando o Bispo.
     */
    @Override
    public String toString() {
        return "B";
    }

    /**
     * Retorna uma matriz booleana indicando os movimentos possíveis do Bispo
     * na sua posição atual.
     * <p>
     * O Bispo move-se ao longo das quatro diagonais. O método verifica os
     * movimentos possíveis em cada uma das quatro direções diagonais, parando
     * quando encontra o limite do tabuleiro ou outra peça. As casas ocupadas
     * por peças adversárias também são marcadas como movimentos possíveis
     * (para captura).
     *
     * @return Uma matriz booleana com as mesmas dimensões do tabuleiro, onde
     * `true` indica que a casa correspondente é um movimento possível para o
     * Bispo, e `false` caso contrário.
     */
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);

        // NW
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // NE
        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // SE
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // SW
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        return mat;
    }
}