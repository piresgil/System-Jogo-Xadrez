
package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 * Class King Rei
 * 
 * Herda de ChessPiece
 *
 */
public class Knight extends ChessPiece {
	/**
	 * Construtor
	 * 
	 * Busca construtor da Super Class
	 */
	public Knight(Board board, Color color) {
		super(board, color);
	}

	/**
	 * Metodo To String
	 * 
	 *  Busca super class (Override)
	 */
	@Override
	public String toString() {
		return "N";
	}

	/**
	 * Metodo Can Move
	 * 
	 * Cria nova variavel p do tipo ChessPiece
	 * 
	 * retorna True quando p for nulo (casa vazia) OU QUANDO a quando a cor é
	 * diferente da cor da CLASS
	 */
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	/**
	 * Metodo Possible Moves
	 * 
	 * Cria matriz de boolean(false) com o tamanho do tabuleiro
	 * 
	 */
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);
		/**
		 * Movimento 1
		 */
		p.setValues(position.getRow() - 1, position.getColumn()-2);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 *Movimento 2
		 */
		p.setValues(position.getRow()-2, position.getColumn()-1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 *Movimento 3
		 */
		p.setValues(position.getRow()-2, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 * Movimento 4
		 */
		p.setValues(position.getRow()-1, position.getColumn() + 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 * Movimento 5
		 */
		p.setValues(position.getRow() + 1, position.getColumn() + 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 * Movimento 6
		 */
		p.setValues(position.getRow() + 2, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 * Movimento 7
		 */
		p.setValues(position.getRow() + 2, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 * Movimento 8
		 */
		p.setValues(position.getRow() + 1, position.getColumn() - 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		return mat;
	}
}