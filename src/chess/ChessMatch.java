/**
 * Pacote chess
 */
package chess;

import java.security.InvalidParameterException;
/**
 * Imports
 */
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

/**
 * Class chessMatch
 * 
 * Aqui sera o coração do sistema
 */
public class ChessMatch {
	/**
	 * variaveis - board, turn, currentPlayer, check, checkMate, enPassantVulnerable
	 * 
	 * depois criar 2 listas do tipo Pieces(para ser de um tipo mais generico) uma
	 * com as pças do tabuleiro outra para pças capturadas
	 * 
	 * INSTANCIA LISTAS antes do construtor
	 */
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	/**
	 * Costrutor
	 * 
	 * Instancia tabuleiro de 8 por 8
	 * 
	 * Instancia posisões inicia turno
	 * 
	 * e inicia player Branco(por deufult)
	 * 
	 * depois inicia as pças no tabuleiro atravez do metodo initialSetup
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
	 * 
	 * das variaveis turn, currentPlayerm check e checkMate
	 */
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getcheck() {
		return check;
	}

	public boolean getcheckMate() {
		return checkMate;
	}

	public ChessPiece getenPassantVulnerable() {
		return enPassantVulnerable;
	}

	public ChessPiece getPromoted() {
		return promoted;
	}

	/**
	 * Metodo Get Piece
	 * 
	 * Cria matriz do tipo ChessPiece
	 * 
	 * Retorna uma matriz de pças
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
	 * Matriz boolean Possible Moves
	 * 
	 * Recebe uma ChessPosition
	 * 
	 * Converte para Position
	 * 
	 * valida source
	 * 
	 * Retorna os movimentos possiveis da peça no tabuleiro
	 */

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	/**
	 * Metodo perform Chess Move
	 * 
	 * Metodo que retira uma peça da source e coloca no destino
	 * 
	 * Recebe 2 ChessPosition
	 * 
	 * Cria 2 variaveis auxiliares e converte para Position
	 * 
	 * Faz uma validação de source
	 * 
	 * Faz validação de target
	 * 
	 * Cria Piece do tipo Position Captura a peça e faz o movimento da source para o
	 * target
	 * 
	 * Testa se existe Check no movimento do Current Player, que desfaz o movimento
	 * e lança excepção
	 * 
	 * Em seguida Testa operação ternÃ¡ria SE existir check do currentPlayer devolve
	 * true, se nÃ£o houver check devolve False
	 * 
	 * caso nÃ£o exista check ou checkmate chama o nextTurn para passar de vez
	 * Retorna a peça capturada
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

		ChessPiece newPiece= newPiece(type,promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}

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
	 * Metodo Make Move
	 * 
	 * Recebe 2 Position (souce e target)
	 * 
	 * Cria 2 variaveis auxiliares
	 * 
	 * Uma (ChessPiece) para remover a peça da source uza
	 * downcasting(ChessPiece)para removePiece
	 * 
	 * outra para remover a peça do destino Chamando o metodo placePiece da Class
	 * ChessMatch pela variavel privada da Class
	 * 
	 * Testa se houve peça capturada
	 * 
	 * E entao remove da lista de pças do tabuleiro e Addiciona hÃ¡ lista de pças
	 * capturadas
	 * 
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
	 * Metodo undo Move
	 * 
	 * Desfaz a jogada
	 * 
	 * Recebe 2 posisões para source e target,
	 * 
	 * recebe uma eventual peça capturada
	 * 
	 * Cria ChessPiece p para remover peça de destino no tabuleiro Repoe peça no
	 * tabuleiro pelo metodo placePiece Uza downcasting(ChessPiece) para removePiece
	 * 
	 * Testa entao se existe peça capturada Repoe peça no tabuleiro e Remove da
	 * Lista de capturadas para Adicionar na Lista de Pças no tabuleiro
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
	 * Metodo de validação
	 * 
	 * Recebe Position
	 * 
	 * Testa se existe peça atravez do metodo thereIsAPiece
	 * 
	 * Testa se o currentPlayer Ã© da mesma cor que a peça selecionada
	 * 
	 * Testa se existem movimentos possiveis atravez do metodo
	 * isThereAnyPossibleMove
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
	 * Metodo de validação
	 * 
	 * Recebe 2 Position - source e target Testa se existe movimento possivel
	 * atravez do metodo posibleMove
	 * 
	 * Lança excepção
	 */
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}

	/**
	 * Metodo Next Turn
	 * 
	 * Incrementa turno e passa a vez para jogador oposto atravez da expresÃ£o
	 * ternÃ¡ria (CurrentPlayer recebe (se o player for Branco) recebe Preto mas (se
	 * o player for Preto) recebe Branco) e assim muda para player oposto
	 */

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.White) ? Color.Black : Color.White;
	}

	/**
	 * Metodo Color Opponent
	 * 
	 * Metodo que Testa pela condição ternÃ¡ria qual a cor do oponente SE a cor for
	 * branca retorna Preto, SE a cor for Preto retorna Branco
	 */
	private Color opponent(Color color) {
		return (color == Color.White) ? Color.Black : Color.White;
	}

	/**
	 * Metodo Chess Piece King
	 * 
	 * Metodo que verifica cor do King Cria LISTA do tipo Piece e filtra pela COR
	 * (uza downcasting para ChessPiece)
	 * 
	 * Percorrer Lista para encontrar pças King
	 * 
	 * Trata excepção especial- deixa estoirar
	 */
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());

		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		/**
		 * SE EXISTIR ESTA EXCEPÃ‡ÃƒO Ã© porque algo esta errado no sistema
		 */
		throw new IllegalStateException("There is no " + color + " king on the board");
	}

	/**
	 * Metodo TEST Check
	 * 
	 * Metodo que testa se existe Check
	 * 
	 * Recebe uma cor Cria uma Position para o King que recebe o metodo king()
	 * chamando os metodos getChessPosition e convertendo atravez do toPosition,
	 * 
	 * Cria uma LISTA do tipo Piece para receber as pças do oponente filtrada pela
	 * cor do oponente (metodo opponent)
	 * 
	 * percorer Lista Cria matriz boolean e
	 * 
	 * Testa se o King esta dentro dos possiveis Movimentos e retorna True, caso nÃ£o
	 * enconttre a posição do King nos movimentos possiveis Retorna false, e nÃ£o
	 * existe Check
	 * 
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
	 * Metodo TEST Check Mate
	 * 
	 * Metodo que testa se existe Check Mate
	 * 
	 * Recebe uma Cor,
	 * 
	 * testa se existe check Se NAO existir retorna false ,
	 * 
	 * Cria uma LISTA do tipo Piece para receber as pças do oponente filtrada pela
	 * cor
	 * 
	 * Percorer Lista
	 * 
	 * Cria matriz boolean e Percorre matriz boolean com 2 FOR
	 * 
	 * Testa se a possição(que Ã© um movimento possivel)
	 * 
	 * Cria Position para source(uza downcasting) e target
	 * 
	 * Cria um boolean para testar check recebendo a color Desfaz a jogada, e Volta
	 * a testar se existe check
	 * 
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
	 * 
	 * Metodo que recebe as pças novas adiciona pças ha lista piecesontheboard
	 */
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	/**
	 * Metodo Initial Setup
	 * 
	 * Metodo responsavel por iniciar as pças no tabuleiro
	 */
	private void initialSetup() {
		/**
		 * color white
		 */
//		placeNewPiece('h', 7, new Rook(board, Color.White));
//		placeNewPiece('d', 1, new Rook(board, Color.White));
//		placeNewPiece('e', 1, new King(board, Color.White));
//		
//		placeNewPiece('a', 8, new King(board, Color.Black));
//		placeNewPiece('b', 8, new Rook(board, Color.Black));

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
		/**
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
