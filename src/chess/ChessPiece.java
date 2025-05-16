/**
 * @author Daniel Gil
 */
package chess;

import boardgame.Piece;
import boardgame.Position;
import boardgame.Board;

/**
 * Class Chess Piece
 *
 * @Abstract para acessar aos métodos abstratos
 */
public abstract class ChessPiece extends Piece {
    /// Variareis
    /// color or desta peça de xadrez ([branco][#WHITE] ou [preto][#BLACK])
    /// moveCount número de movimentos que esta peça já realizou no jogo.
    private Color color;
    private int moveCount;

    /// Construtor
    /// inicializa uma nova peça de xadrez com o tabuleiro em que ela está localizada e a sua cor.
    ///
    /// Chama o construtor da superclasse [Piece] para associar a peça ao tabuleiro.
    ///
    /// @param board O tabuleiro de xadrez onde a peça será colocada.
    /// @param color A cor da peça ([branco][#WHITE] ou [preto][#BLACK]).
    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    /**
     * Getter Setters
     */
    public Color getColor() {
        return color;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount() {
        moveCount++;
    }

    public void decreaseMoveCount() {
        moveCount--;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }

    /**
     * Method is There Opponent Piece
     * Verifica se existe uma peça adversária na posição especificada.
     *
     * @param position A posição a ser verificada no tabuleiro.
     * @return `true` se houver uma peça adversária na posição; `false` caso contrário
     * (se a casa estiver vazia ou contiver uma peça da mesma cor).
     */
    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p != null && p.getColor() != color;
    }
}