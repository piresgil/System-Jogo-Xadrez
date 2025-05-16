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
 * Representa a peça Rei no jogo de xadrez.
 * <p>
 * O Rei é uma {@link ChessPiece} que pode se mover uma casa em qualquer direção
 * (horizontal, vertical ou diagonal). No jogo de xadrez, cada jogador tem um Rei
 * e o objetivo final é dar xeque-mate ao Rei adversário.
 */
public class King extends ChessPiece {

    /**
     * Variavel
     * Dependência para a partida de xadrez atual.
     * <p>
     * Esta variável mantém uma referência à instância da classe {@link ChessMatch}
     * à qual esta peça está associada. Através desta referência, a peça pode
     * interagir com o estado geral do jogo, como verificar regras específicas
     * ou acessar informações sobre outros elementos da partida.
     */
    private ChessMatch chessMatch;

    /// Construtor
    /// inicializa um novo Rei com o tabuleiro
    /// em que ele está localizado, a sua cor e a partida de xadrez associada.
    ///
    /// Chama o construtor da superclasse [ChessPiece] para associar o Rei
    /// ao tabuleiro e definir a sua cor. Adicionalmente, estabelece uma dependência
    /// com a instância da partida de xadrez atual ([ChessMatch]).
    ///
    /// @param board      O tabuleiro de xadrez onde o Rei será colocado.
    /// @param color      A cor do Rei ([branco][#WHITE] ou [preto][#BLACK]).
    /// @param chessMatch A instância da partida de xadrez atual à qual o Rei pertence.
    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    /**
     * Metodo To String
     *
     * @return Uma string "K" representando o Rei.
     */
    @Override
    public String toString() {
        return "K";
    }

    /**
     * Metodo CanMove
     * Verifica se o Rei pode se mover para uma determinada posição.
     * <p>
     * O Rei pode se mover para uma casa se ela estiver vazia ou contiver uma
     * peça adversária.
     *
     * @param position A posição de destino a ser verificada.
     * @return `true` se o Rei puder se mover para a posição; `false` caso contrário
     * (se a casa estiver ocupada por uma peça da mesma cor).
     */
    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p == null || p.getColor() != getColor();
    }

    /**
     * Metodo testRookCastling
     * Verifica se uma torre está em uma posição válida para o roque.
     * <p>
     * Para o roque ser possível, deve haver uma torre na posição especificada,
     * da mesma cor do Rei, e que ainda não tenha realizado nenhum movimento.
     *
     * @param position A posição da possível torre para o roque.
     * @return `true` se a torre estiver apta para o roque; `false` caso contrário.
     */
    private boolean testRookCastling(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
    }

    /*** Retorna uma matriz booleana indicando os movimentos possíveis do Rei
     * na sua posição atual.
     * <p>
     * Este método sobrescreve o método {@link ChessPiece#possibleMoves()} da
     * superclasse. O Rei pode se mover uma casa em qualquer direção (horizontal,
     * vertical ou diagonal). Além disso, verifica a possibilidade de roque (castling)
     * com as torres, tanto para o lado do rei quanto para o lado da rainha,
     * desde que as condições para o roque sejam atendidas.
     *
     * @return Uma matriz booleana com as mesmas dimensões do tabuleiro, onde
     * `true` indica que a casa correspondente é um movimento possível para o Rei,
     * e `false` caso contrário.
     */
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);

        // Above
        p.setValues(position.getRow() - 1, position.getColumn());
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Below
        p.setValues(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Left
        p.setValues(position.getRow(), position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Right
        p.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // NW cima esquerda
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // NE cima Direita
        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // SW Baixo esquerda
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // SE baixo Direita
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Special move Castling
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            // special move castling kingside rook
            Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(posT1)) {
                Position p1 = new Position(position.getRow(), position.getColumn() + 1);
                Position p2 = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
                    mat[position.getRow()][position.getColumn() + 2] = true;
                }
            }
            // special move castling queenside rook
            Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(posT2)) {
                Position p1 = new Position(position.getRow(), position.getColumn() - 1);
                Position p2 = new Position(position.getRow(), position.getColumn() - 2);
                Position p3 = new Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
                    mat[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }

        return mat;
    }
}