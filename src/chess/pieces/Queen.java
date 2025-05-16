/**
 * @author Daniel Gil
 */
package chess.pieces;


import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 * Representa a peça da Rainha no jogo de xadrez.
 * <p>
 * A Rainha pode se mover qualquer número de casas na horizontal, vertical
 * ou diagonal.
 */
public class Queen extends ChessPiece {
    /// Construtor da classe `Queen`.
    ///
    /// @param board A instância do tabuleiro em que a Rainha está.
    /// @param color A cor da Rainha ([branco][#WHITE] ou
    ///                           [preto][#BLACK]).
    public Queen(Board board, Color color) {
        super(board, color);
    }

    /**
     * Retorna uma representação em String da Rainha para fins de exibição
     * no tabuleiro.
     * <p>
     * Este método sobrescreve o método {@link Object#toString()} da superclasse.
     *
     * @return Uma string "Q" representando a Rainha.
     */
    @Override
    public String toString() {
        return "Q";
    }

    /**
     * Retorna uma matriz booleana indicando os movimentos possíveis da Rainha
     * na sua posição atual.
     * <p>
     * A Rainha combina os movimentos da Torre (horizontal e vertical) e do
     * Bispo (diagonal). O método verifica os movimentos possíveis em oito
     * direções: acima, abaixo, esquerda, direita e nas quatro diagonais,
     * parando quando encontra o limite do tabuleiro ou outra peça. As casas
     * ocupadas por peças adversárias também são marcadas como movimentos
     * possíveis (para captura).
     *
     * @return Uma matriz booleana com as mesmas dimensões do tabuleiro, onde
     * `true` indica que a casa correspondente é um movimento possível para a
     * Rainha, e `false` caso contrário.
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