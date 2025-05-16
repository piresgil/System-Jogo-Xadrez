
package chess;

import boardgame.Piece;
import boardgame.Position;
import boardgame.Board;

/**
 * Class Chess Piece
 * 
 * Herda de Piece Abstract para acessar aos metodos abstractos
 */
public abstract class ChessPiece extends Piece {
	/**
	 * Variaveis - color moveCount
	 */
	private Color color;
	private int moveCount;

	/**
	 * Construtor
	 * 
	 * Recebe color Busca construtor da super class para Receber board
	 */
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	/**
	 * Getter Setters
	 * 
	 * get color
	 * 
	 * Get move count
	 * 
	 * Increse/decrease move count
	 * 
	 * get Chess Position - retorna posisões(pocisões da matriz) e converte para
	 * ChessPosition ( a1, h8)
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
	 * Metodo is There Opponent Piece
	 * 
	 * Metodo boolean que testa se existe peça do oponente
	 * 
	 * Cria variavel p do tipo ChessPiece
	 * 
	 * Retorna True quando a variavel p é diferente de nulo e quando a cor da
	 * variavel p é diferente da cor da class ChessPiece (cor do oponente)
	 */
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p.getColor() != color;
	}
}