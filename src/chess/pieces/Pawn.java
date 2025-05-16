/**
 * @author Daniel Gil
 */
package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

/**
 * Representa a peça do Peão no jogo de xadrez.
 * <p>
 * O Peão tem movimentos especiais para o primeiro movimento (duas casas para
 * frente), movimentos diagonais para capturar peças e o movimento especial
 * "en passant".
 */
public class Pawn extends ChessPiece {

    /**
     * Dependência da partida de xadrez para verificar condições especiais
     * como o movimento "en passant".
     */
    private ChessMatch chessMatch;

    /// Construtor da classe `Pawn`.
    ///
    /// @param board      A instância do tabuleiro em que o Peão está.
    /// @param color      A cor do Peão ([branco][#WHITE] ou
    ///                   [preto][#BLACK]).
    /// @param chessMatch A instância da partida de xadrez atual.
    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    /**
     * Retorna uma representação em String do Peão para fins de exibição
     * no tabuleiro.
     * <p>
     * Este método sobrescreve o método {@link Object#toString()} da superclasse.
     *
     * @return Uma string "P" representando o Peão.
     */
    @Override
    public String toString() {
        return "P";
    }

    /**
     * Retorna uma matriz booleana indicando os movimentos possíveis do Peão
     * na sua posição atual.
     * <p>
     * Os movimentos possíveis do Peão dependem de sua cor e do estado do jogo.
     * Peões brancos movem-se para frente (diminuindo a linha), e peões pretos
     * movem-se para baixo (aumentando a linha). No primeiro movimento, um peão
     * pode mover-se duas casas para frente. Peões capturam peças adversárias
     * movendo-se uma casa na diagonal. O método também considera o movimento
     * especial "en passant".
     *
     * @return Uma matriz booleana com as mesmas dimensões do tabuleiro, onde
     * `true` indica que a casa correspondente é um movimento possível para o
     * Peão, e `false` caso contrário.
     */
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);

        if (getColor() == Color.White) {
            // Above 1x
            p.setValues(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            // Above x2 (first move)
            p.setValues(position.getRow() - 2, position.getColumn());
            Position p2 = new Position(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)
                    && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            // Diagonal left (capture)
            p.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            // Diagonal right (capture)
            p.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // Special move en passant (left)
            if (position.getRow() == 3) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left)
                        && getBoard().piece(left) == chessMatch.getenPassantVulnerable()) {
                    mat[left.getRow() - 1][left.getColumn()] = true;
                }
                // Special move en passant (right)
                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(right) && isThereOpponentPiece(right)
                        && getBoard().piece(right) == chessMatch.getenPassantVulnerable()) {
                    mat[right.getRow() - 1][right.getColumn()] = true;
                }
            }

        } else {
            // Below 1x
            p.setValues(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            // Below x2 (first move)
            p.setValues(position.getRow() + 2, position.getColumn());
            Position p2 = new Position(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)
                    && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            // Diagonal left (capture)
            p.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            // Diagonal right (capture)
            p.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            // Special move en passant (left)
            if (position.getRow() == 4) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left)
                        && getBoard().piece(left) == chessMatch.getenPassantVulnerable()) {
                    mat[left.getRow() + 1][left.getColumn()] = true;
                }
                // Special move en passant (right)
                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(right) && isThereOpponentPiece(right)
                        && getBoard().piece(right) == chessMatch.getenPassantVulnerable()) {
                    mat[right.getRow() + 1][right.getColumn()] = true;
                }
            }
        }
        return mat;
    }
}