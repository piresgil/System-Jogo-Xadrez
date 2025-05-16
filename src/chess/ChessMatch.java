/**
 * @author Daniel Gil
 */
package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class chessMatch
 * <p>
 * Regras do Jogo de Xadrez
 */
public class ChessMatch {
    /// Variareis
    /// turn turno do jogo.
    /// color representa a cor do jogador atual ([branco][#WHITE] ou [preto][#BLACK]).
    /// board tabuleiro de xadrez onde a partida está a decorrer.
    /// check booleano que indica se o rei do jogador atual está em xeque.
    /// checkMate booleano que indica se a partida terminou em xeque-mate.
    /// enPassantVulnerable indica se a peça que está vulnerável a um movimento "en passant" no turno atual.Pode ser `null` se nenhuma peça estiver vulnerável.
    /// promoted indica se a peça se movimentou no último turno
    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;

    /**
     * @Listas lista de todas as peças que estão atualmente no tabuleiro.
     * lista de todas as peças que foram capturadas durante a partida.
     */
    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    /**
     * Construtor
     * inicializa uma nova partida de xadrez.
     * <p>
     * Este construtor realiza as seguintes ações:
     * <ul>
     * <li>Instancia um tabuleiro de xadrez padrão de 8x8.</li>
     * <li>Define o turno inicial como 1.</li>
     * <li>Define o jogador inicial como Branco (por padrão).</li>
     * <li>Chama o método {@link #initialSetup()} para posicionar as peças no tabuleiro.</li>
     * </ul>
     */
    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.White;
        // chama initialSetup
        initialSetup();
    }

    /**
     * GETTERs
     * <p>
     * das variaveis turn, currentPlayerm check e checkMate
     */
    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    public ChessPiece getenPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted() {
        return promoted;
    }

    /**
     * Method getPieces
     * uma matriz bidimensional contendo todas as peças de xadrez presentes no tabuleiro.
     * <p>
     * Este método cria uma nova matriz do tipo {@link ChessPiece} com as mesmas
     * dimensões do tabuleiro atual e preenche-a com as peças, realizando um
     * downcasting do tipo genérico {@link Piece} para {@code ChessPiece}.
     *
     * @return Uma matriz bidimensional de {@link ChessPiece} representando
     * as peças no tabuleiro.
     */
    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);// uza downCasting para itepretar como ChessPiece
            }
        }
        return mat;
    }

    /**
     * Maethod possibleMoves
     * Retorna uma matriz booleana indicando os movimentos possíveis para a peça
     * na posição de xadrez especificada.
     *
     * @param sourcePosition A {@link ChessPosition} da peça para a qual os movimentos possíveis serão calculados.
     * @return Uma matriz booleana com as mesmas dimensões do tabuleiro, onde
     * `true` indica que a casa correspondente é um movimento possível para a peça,
     * e `false` caso contrário.
     * @throws ChessException Se não houver peça na posição de origem ou se a peça
     *                        não pertencer ao jogador atual.
     */
    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    /**
     * Metodo perform Chess Move
     * Realiza um movimento de xadrez da posição de origem para a posição de destino.
     * <p>
     * Este método retira a peça da posição de origem, coloca-a na posição de destino,
     * e captura qualquer peça adversária que esteja na posição de destino. Valida
     * as posições de origem e destino, verifica se o movimento coloca o jogador atual
     * em xeque, e atualiza o estado do jogo (turno, jogador atual, xeque, xeque-mate,
     * e variáveis especiais como "en passant" e promoção).
     *
     * @param sourcePosition A {@link ChessPosition} da peça a ser movida.
     * @param targetPosition A {@link ChessPosition} para onde a peça será movida.
     * @return A peça capturada durante o movimento (pode ser `null` se nenhuma peça for capturada).
     * @throws ChessException Se a posição de origem for inválida, se não houver movimentos possíveis para a peça na origem,
     *                        se a posição de destino for inválida para o movimento,
     *                        ou se o movimento colocar o jogador atual em xeque.
     */
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }

        ChessPiece movedPiece = (ChessPiece) board.piece(target);

        // Special MOVE PROMOTION
        promoted = null;
        if (movedPiece instanceof Pawn) {
            if (movedPiece.getColor() == Color.White && target.getRow() == 0
                    || movedPiece.getColor() == Color.Black && target.getRow() == 7) {
                promoted = (ChessPiece) board.piece(target);
                promoted = replacePromotedPiece("Q");
            }
        }

        check = (testCheck(opponent(currentPlayer))) ? true : false;

        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }

        // Special move en passant
        if (movedPiece instanceof Pawn
                && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
            enPassantVulnerable = movedPiece;
        } else {
            enPassantVulnerable = null;
        }

        return (ChessPiece) capturedPiece;
    }

    public ChessPiece replacePromotedPiece(String type) {
        if (promoted == null) {
            throw new IllegalStateException("There is no piece to be promoted");
        }
        if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
            throw new InvalidParameterException("Invalid type for promotion");
        }

        Position pos = promoted.getChessPosition().toPosition();
        Piece p = board.removePiece(pos);
        piecesOnTheBoard.remove(p);

        ChessPiece newPiece = newPiece(type, promoted.getColor());
        board.placePiece(newPiece, pos);
        piecesOnTheBoard.add(newPiece);

        return newPiece;
    }

    /**
     * Method newPiece
     * Cria uma nova instância de {@link ChessPiece} do tipo e cor especificados.
     *
     * @param type  O tipo da peça para criar ("B" para Bispo, "N" para Cavalo,
     *              "Q" para Rainha, "R" para Torre).
     * @param color A cor da peça ({@link Color}).
     * @return Uma nova instância de {@link ChessPiece} do tipo e cor especificados.
     */
    private ChessPiece newPiece(String type, Color color) {
        if (type.equals("B"))
            return new Bishop(board, color);
        if (type.equals("N"))
            return new Knight(board, color);
        if (type.equals("Q"))
            return new Queen(board, color);
        return new Rook(board, color);

    }

    /**
     * Metodo makeMove
     * Realiza o movimento de uma peça no tabuleiro da posição de origem para a
     * posição de destino.
     * <p>
     * Este método remove a peça da origem, incrementa seu contador de movimentos,
     * remove qualquer peça que esteja no destino, coloca a peça movida no destino,
     * e atualiza as listas de peças no tabuleiro e peças capturadas. Também lida
     * com os movimentos especiais de roque e "en passant".
     *
     * @param source A {@link Position} de origem da peça.
     * @param target A {@link Position} de destino da peça.
     * @return A peça capturada durante o movimento (pode ser `null`).
     */
    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        // Special move Castling KingSide Rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
            board.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }
        // Special move Castling QueenSide Rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
            board.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }

        // especial move en passant

        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if (p.getColor() == Color.White) {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                } else {
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                capturedPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }

        return capturedPiece;
    }

    /**
     * Metodo undoMove
     * Desfaz um movimento realizado no tabuleiro.
     * <p>
     * Este método remove a peça da posição de destino, decrementa seu contador
     * de movimentos, a coloca de volta na posição de origem e, se houver uma
     * peça capturada, a repõe no tabuleiro e a move de volta para a lista de
     * peças no tabuleiro, removendo-a da lista de peças capturadas. Também
     * desfaz os movimentos especiais de roque e "en passant".
     *
     * @param source        A {@link Position} de origem do movimento a ser desfeito.
     * @param target        A {@link Position} de destino do movimento a ser desfeito.
     * @param capturedPiece A peça capturada durante o movimento (pode ser `null`).
     */
    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }

        // Special move Castling KingSide Rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetT);
            board.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }
        // Special move Castling QueenSide Rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetT);
            board.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }

        // especial move en passant

        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece) board.removePiece(target);
                Position pawnPosition;
                if (p.getColor() == Color.White) {
                    pawnPosition = new Position(3, target.getColumn());
                } else {
                    pawnPosition = new Position(4, target.getColumn());
                }
                board.placePiece(pawn, pawnPosition);
            }
        }

    }

    /**
     * Metodo de validateSourcePosition
     * Valida a posição de origem de um movimento.
     * <p>
     * Este método verifica se existe uma peça na posição fornecida, se essa peça
     * pertence ao jogador atual e se ela possui algum movimento possível. Se alguma
     * dessas condições não for atendida, uma {@link ChessException} é lançada.
     *
     * @param position A {@link Position} a ser validada como posição de origem.
     * @throws ChessException Se não houver peça na posição de origem, se a peça
     *                        não pertencer ao jogador atual ou se a peça não tiver
     *                        movimentos possíveis.
     */
    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position.");
        }
        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {// DownCasting para ChessPiece para poder
            // comparar a cor
            throw new ChessException("The Chosen piece is not yours.");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for the chosen piece.");
        }
    }

    /**
     * Metodo validateTargetPosition
     * Valida a posição de destino de um movimento.
     * <p>
     * Este método verifica se o movimento da peça na posição de origem para a
     * posição de destino especificada é um movimento válido, utilizando o método
     * {@link boardgame.Piece#possibleMove(Position)}. Se o movimento não for
     * possível, uma {@link ChessException} é lançada.
     *
     * @param source A {@link Position} de origem da peça.
     * @param target A {@link Position} de destino do movimento.
     * @throws ChessException Se o movimento da peça da origem para o destino não for permitido.
     */
    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can't move to target position");
        }
    }

    /// Metodo Next Turn
    /// Avança para o próximo turno da partida e troca o jogador atual.
    ///
    /// Este método incrementa o contador de turnos e utiliza uma expressão ternária
    /// para alternar o valor da variável [#currentPlayer] entre [#WHITE]
    /// e [#BLACK], passando a vez para o oponente.
    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.White) ? Color.Black : Color.White;
    }

    /// Metodo Opponent
    /// Retorna a cor do oponente para uma determinada cor.
    ///
    /// Este método utiliza uma expressão ternária para determinar a cor do oponente.
    /// Se a cor fornecida for [#WHITE], retorna [#BLACK];
    /// se a cor fornecida for [#BLACK], retorna [#WHITE].
    ///
    /// @param color A cor do jogador atual.
    /// @return A cor do oponente.
    private Color opponent(Color color) {
        return (color == Color.White) ? Color.Black : Color.White;
    }

    /**
     * King
     * Retorna a peça do Rei da cor especificada que está atualmente no tabuleiro.
     * <p>
     * Este método filtra a lista de peças no tabuleiro (`{@link #piecesOnTheBoard}`)
     * para encontrar todas as peças da cor fornecida. Em seguida, itera sobre essa
     * lista para identificar a instância da classe {@link King}.
     *
     * @param color A cor do Rei a ser encontrado ({@link Color}).
     * @return A peça do Rei da cor especificada.
     * @throws IllegalStateException Se não houver um Rei da cor especificada no tabuleiro.
     */
    private ChessPiece king(Color color) {
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
                .collect(Collectors.toList());

        for (Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    /**
     * Metodo testCheck
     * Verifica se o rei da cor especificada está em xeque.
     * <p>
     * Este método obtém a posição do rei da cor fornecida utilizando o método
     * {@link #king(Color)} e converte essa posição de xadrez para uma posição
     * na matriz do tabuleiro. Em seguida, itera sobre todas as peças adversárias
     * no tabuleiro, obtendo seus movimentos possíveis. Se algum dos movimentos
     * possíveis de uma peça adversária atinge a posição do rei, o método retorna
     * `true`, indicando que o rei está em xeque.
     *
     * @param color A cor do rei a ser verificada ({@link Color}).
     * @return `true` se o rei estiver em xeque, `false` caso contrário.
     */
    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream()
                .filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());

        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo testCheckMate
     * Verifica se o rei da cor especificada está em xeque-mate.
     * <p>
     * Este método primeiro verifica se o rei da cor fornecida já está em xeque
     * utilizando o método {@link #testCheck(Color)}. Se o rei não estiver em
     * xeque, então não há xeque-mate, e o método retorna `false`.
     * <p>
     * Se o rei estiver em xeque, o método itera sobre todas as peças da cor
     * fornecida no tabuleiro. Para cada peça, ele verifica todos os seus movimentos
     * possíveis. Para cada movimento possível, o método simula o movimento
     * (utilizando {@link #makeMove(Position, Position)}), verifica se o rei ainda
     * está em xeque após o movimento (utilizando {@link #testCheck(Color)}), e
     * então desfaz o movimento (utilizando {@link #undoMove(Position, Position, Piece)}).
     * <p>
     * Se existir algum movimento que retire o rei do xeque, o método retorna `false`,
     * indicando que não há xeque-mate. Se não houver nenhum movimento possível para
     * nenhuma das peças que retire o rei do xeque, o método retorna `true`,
     * indicando que é xeque-mate.
     *
     * @param color A cor do rei a ser verificada para xeque-mate ({@link Color}).
     * @return `true` se o rei estiver em xeque-mate, `false` caso contrário.
     */
    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
                .collect(Collectors.toList());

        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Metodo Place New Piece
     * Coloca uma nova peça no tabuleiro na posição de xadrez especificada.
     * <p>
     * Este método recebe a coluna (letra), a linha (número) e a peça de xadrez
     * a ser colocada. Ele converte a notação de xadrez para uma {@link boardgame.Position}
     * utilizando {@link ChessPosition#toPosition()} e então utiliza o método
     * {@link Board#placePiece(Piece, Position)} para colocar a peça no tabuleiro.
     * Finalmente, adiciona a peça à lista de peças ativas no tabuleiro
     * (`piecesOnTheBoard`).
     *
     * @param column A coluna da posição de xadrez (de 'a' a 'h').
     * @param row    A linha da posição de xadrez (de 1 a 8).
     * @param piece  A {@link ChessPiece} a ser colocada no tabuleiro.
     */
    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    /**
     * Metodo Initial Setup
     * Configura o tabuleiro de xadrez com as peças iniciais em suas posições padrão.
     * <p>
     * Este método é responsável por instanciar cada peça de xadrez (Torres, Cavalos,
     * Bispos, Rainha, Rei e Peões) para ambas as cores (Branco e Preto) e colocá-las
     * em suas posições iniciais no tabuleiro, utilizando o método
     * {@link #placeNewPiece(char, int, ChessPiece)}.
     */
    private void initialSetup() {
        /*
         * color white
         */

        placeNewPiece('a', 1, new Rook(board, Color.White));
        placeNewPiece('b', 1, new Knight(board, Color.White));
        placeNewPiece('c', 1, new Bishop(board, Color.White));
        placeNewPiece('d', 1, new Queen(board, Color.White));
        placeNewPiece('e', 1, new King(board, Color.White, this));
        placeNewPiece('f', 1, new Bishop(board, Color.White));
        placeNewPiece('g', 1, new Knight(board, Color.White));
        placeNewPiece('h', 1, new Rook(board, Color.White));
        placeNewPiece('a', 2, new Pawn(board, Color.White, this));
        placeNewPiece('b', 2, new Pawn(board, Color.White, this));
        placeNewPiece('c', 2, new Pawn(board, Color.White, this));
        placeNewPiece('d', 2, new Pawn(board, Color.White, this));
        placeNewPiece('e', 2, new Pawn(board, Color.White, this));
        placeNewPiece('f', 2, new Pawn(board, Color.White, this));
        placeNewPiece('g', 2, new Pawn(board, Color.White, this));
        placeNewPiece('h', 2, new Pawn(board, Color.White, this));
        /*
         * Color black
         */
        placeNewPiece('a', 8, new Rook(board, Color.Black));
        placeNewPiece('b', 8, new Knight(board, Color.Black));
        placeNewPiece('c', 8, new Bishop(board, Color.Black));
        placeNewPiece('d', 8, new Queen(board, Color.Black));
        placeNewPiece('e', 8, new King(board, Color.Black, this));
        placeNewPiece('f', 8, new Bishop(board, Color.Black));
        placeNewPiece('g', 8, new Knight(board, Color.Black));
        placeNewPiece('h', 8, new Rook(board, Color.Black));
        placeNewPiece('a', 7, new Pawn(board, Color.Black, this));
        placeNewPiece('b', 7, new Pawn(board, Color.Black, this));
        placeNewPiece('c', 7, new Pawn(board, Color.Black, this));
        placeNewPiece('d', 7, new Pawn(board, Color.Black, this));
        placeNewPiece('e', 7, new Pawn(board, Color.Black, this));
        placeNewPiece('f', 7, new Pawn(board, Color.Black, this));
        placeNewPiece('g', 7, new Pawn(board, Color.Black, this));
        placeNewPiece('h', 7, new Pawn(board, Color.Black, this));

    }
}
