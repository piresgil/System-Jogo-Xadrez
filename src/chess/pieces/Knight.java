/**
 * @author Daniel Gil
 */
package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 * Representa a peça do Cavalo no jogo de xadrez.
 * <p>
 * O Cavalo move-se em forma de "L": duas casas em uma direção (horizontal ou
 * vertical) e então uma casa perpendicularmente. O Cavalo é a única peça que
 * pode saltar sobre outras peças.
 */
public class Knight extends ChessPiece {
    /// Construtor da classe `Knight`.
    ///
    /// @param board A instância do tabuleiro em que o Cavalo está.
    /// @param color A cor do Cavalo ([branco][#WHITE] ou
    ///              [preto][#BLACK]).
    public Knight(Board board, Color color) {
        super(board, color);
    }

    /**
     * Retorna uma representação em String do Cavalo para fins de exibição
     * no tabuleiro.
     * <p>
     * Este método sobrescreve o método {@link Object#toString()} da superclasse.
     *
     * @return Uma string "N" representando o Cavalo.
     */
    @Override
    public String toString() {
        return "N";
    }

    /**
     * Verifica se o Cavalo pode mover-se para uma determinada posição.
     * <p>
     * O Cavalo pode mover-se para uma posição se ela estiver vazia ou contiver
     * uma peça adversária.
     *
     * @param position A {@link Position} de destino potencial.
     * @return `true` se o Cavalo puder mover-se para a posição, `false` caso
     * contrário.
     */
    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p == null || p.getColor() != getColor();
    }

    /**
     * Retorna uma matriz booleana indicando os movimentos possíveis do Cavalo
     * na sua posição atual.
     * <p>
     * O Cavalo tem oito movimentos possíveis, em forma de "L". O método verifica
     * cada um desses oito movimentos e marca como `true` na matriz de movimentos
     * possíveis as casas que estão dentro dos limites do tabuleiro e para as
     * quais o Cavalo pode se mover (casas vazias ou ocupadas por peças
     * adversárias).
     *
     * @return Uma matriz booleana com as mesmas dimensões do tabuleiro, onde
     * `true` indica que a casa correspondente é um movimento possível para o
     * Cavalo, e `false` caso contrário.
     */
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);

        // Movimento 1: Duas casas acima, uma à esquerda
        p.setValues(position.getRow() - 2, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento 2: Duas casas acima, uma à direita
        p.setValues(position.getRow() - 2, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento 3: Uma casa acima, duas à esquerda
        p.setValues(position.getRow() - 1, position.getColumn() - 2);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento 4: Uma casa acima, duas à direita
        p.setValues(position.getRow() - 1, position.getColumn() + 2);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento 5: Duas casas abaixo, uma à esquerda
        p.setValues(position.getRow() + 2, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento 6: Duas casas abaixo, uma à direita
        p.setValues(position.getRow() + 2, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento 7: Uma casa abaixo, duas à esquerda
        p.setValues(position.getRow() + 1, position.getColumn() - 2);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento 8: Uma casa abaixo, duas à direita
        p.setValues(position.getRow() + 1, position.getColumn() + 2);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        return mat;
    }
}