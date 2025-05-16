/**
* Pacote tabuleiro de jogo
*/
package boardgame;

/**
 * Class Board Tabuleiro
 */
public class Board {
	/**
	 * Variaveis row, column piece que sera uma matriz [][] - muitas peças no
	 * tabuleiro
	 */
	private int rows;
	private int columns;
	private Piece[][] pieces;

	/**
	 * Construtor
	 * 
	 * Recebe row e column
	 * 
	 * Instancia matriz de pieces
	 * 
	 * Trata excepções
	 */
	public Board(int rows, int columns) {
		/**
		 * Tratamento de excepções
		 * 
		 * BoardException
		 * 
		 * Testa de row, column maiores que 1
		 */
		if (rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		/**
		 * Instancia a matriz, com row e column
		 */
		pieces = new Piece[rows][columns];
	}

	/**
	 * Getters Setter
	 * 
	 * Apenas getters para que não existam inconscistencias
	 */
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	/**
	 * Metodos Especiais
	 * 
	 * Metodo que retorna uma peça informando row column
	 * 
	 * Trata excepções
	 */
	public Piece piece(int rows, int columns) {
		/**
		 * Tratamento de excepções BoardException Testa se existe posições disponiveis
		 * uzando metodo position exists
		 */
		if (!positionExists(rows, columns)) {
			throw new BoardException("Position not on the Board");
		}
		return pieces[rows][columns];
	}

	/**
	 * Metodos Especiais Sobrecarga
	 * 
	 * Do metodo Piece Retornando uma pocição numa determinada row e column
	 * 
	 * Trata excepções
	 */
	public Piece piece(Position position) {
		/**
		 * Tratamento de excepções
		 * 
		 * BoardException
		 * 
		 * Testa se existe posições disponiveis uzando metodo position exists
		 */
		if (!positionExists(position)) {
			throw new BoardException("Position not on the Board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}

	/**
	 * Metodo place Piece
	 * 
	 * Recebe uma Piece e uma Position
	 * 
	 * Trata excepções
	 */
	public void placePiece(Piece piece, Position position) {
		/**
		 * Tratamento de excepções
		 * 
		 * BoardException
		 * 
		 * Testa se existe peça na posição uzando metodo there is a piece
		 */
		if (thereIsAPiece(position)) {
			throw new BoardException("There is alredy a piece on position " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

	/**
	 * Metodo Remove piece
	 * 
	 * Recebe uma Position e
	 * 
	 * cria variavel auxiliar do tipo Piece para receber valor da Position
	 * 
	 * Reseta como nula a variavel auxiliar
	 * 
	 * Reseta peças nas pocições row e column como nulas
	 * 
	 * Retorna variavel auxiliar
	 * 
	 * Trata excepções
	 */
	public Piece removePiece(Position position) {
		/**
		 * Tratamento de excepções
		 * 
		 * BoardException
		 * 
		 * Testa se existe posições disponiveis uzando metodo position exists
		 */
		if (!positionExists(position)) {
			throw new BoardException("Position not on the Board");
		}
		if (piece(position) == null) {
			return null;
		}
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}

	/**
	 * Metodos de verificação - position exists
	 * 
	 * Metodos que verifica a existencia de posição Recebe row e column
	 * 
	 * Retorna True quando row/column estiver entre zero e rows/columns (variaveis
	 * privadas da class Board (tamanho da matriz))
	 */
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	/**
	 * Metodos de verificação - position exists Sobrecarga que verifica Posição
	 * Recebe uma Position uza o metodo da sob-carga para testar a Posição Retorna
	 * True quando a Posição na row, column forem True conforme metodo da sob-carga
	 */
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}

	/**
	 * Metodos de verificação - there is a piece
	 * 
	 * Metodo que verifica se existe peça na posição
	 * 
	 * Recebe uma Position
	 * 
	 * Retorna a peça na posição que seja diferente de nulo
	 * 
	 * Trata excepçoes
	 */
	public boolean thereIsAPiece(Position position) {
		/**
		 * Tratamento de excepções
		 * 
		 * BoardException
		 * 
		 * Testa se existe posições disponiveis uzando metodo position exists
		 */
		if (!positionExists(position)) {
			throw new BoardException("Position not on the Board");
		}
		return piece(position) != null;
	}
}