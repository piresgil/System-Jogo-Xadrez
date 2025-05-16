
package chess;

import boardgame.Position;

/**
 * Class Ches Position
 *
 */
public class ChessPosition {
	/**
	 * Variaveis - column, row
	 */
	private char column;
	private int row;

	/**
	 * Construtor
	 * 
	 * Recebe column, row
	 * 
	 * Trata excepções
	 */
	public ChessPosition(char column, int row) {
		/**
		 * Tratamento de excepções
		 * 
		 * ChessException
		 * 
		 * Testa se row e column estao dentro dos parametros da matriz
		 */
		if (row < 1 || row > 8 || column > 'h' || column < 'a') {
			throw new ChessException("Error instantiating ChessPossition. Valid values are from a1 to h8.");
		}
		this.column = column;
		this.row = row;
	}

	/**
	 * getters Setters
	 * 
	 * apenas get column, row
	 */
	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	/**
	 * Metodos Especiais
	 * 
	 * toPosition - retorna uma nova posição
	 * 
	 * fromPosition - retorna uma nova ChessPosition
	 */
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}

	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
	}

	/**
	 * Metodo To String
	 */
	@Override
	public String toString() {
		return "" + column + row;
	}
}