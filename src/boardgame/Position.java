
package boardgame;

/**
 * Class Position
 */
public class Position {
	/**
	 * variaveis - row, column
	 */
	private int row;
	private int column;

	/**
	 * Construtor 
	 * 
	 * Recebe row, column
	 */
	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Getter Setters
	 */
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * Metodo Set values
	 * 
	 * Metodo recebe novos valores para woe e column
	 */
	public void setValues(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Metodo to String
	 * 
	 * Metodo converte que converte para String
	 */
	@Override
	public String toString() {
		return row + ", " + column;
	}
}