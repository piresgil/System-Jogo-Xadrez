package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 * Class Rook Torre
 * 
 * Herda de ChessPiece
 *
 */
public class Rook extends ChessPiece {
	/**
	 * Construtor 
	 * 
	 * Busca construtor da Super Class
	 */
	public Rook(Board board, Color color) {
		super(board, color);
	}

	/**
	 * Metodo To String
	 * 
	 *  Busca super class (Override)
	 */
	@Override
	public String toString() {
		return "R";
	}

	/**
	 * Metodo Possible Moves
	 * 
	 * Busca super class (Override)
	 * 
	 * Cria matriz de boolean(false) com o tamanho do
	 * tabuleiro Cria nova variavel do tipo Position nos valores zero-zero para
	 * fazer testes
	 */
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		/**
		 * Above
		 * 
		 * Seta valores para a posisão p(row -1) porque a peça vai andar para cima(Above)
		 * Enquanto existir posisão no tabuleiro 
		 * E enquanto nÃ£o
		 * existir peça na posisão Retorna valor na matriz como True
		 */
		p.setValues(position.getRow() - 1, position.getColumn());//-1) porque a peça vai andar para cima(above)
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() - 1);//-1) porque a peça vai andar para cima(above)
		}
		/**
		 * Testa se existe posisão no tabuleiro e se existe peça do oponente Retorna valor na matriz como True
		 */
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		/**
		 * Left
		 * 
		 * Seta valores para a posisão p(column -1) porque a peça vai andar para esquerda(left)
		 * Enquanto existir posisão no tabuleiro E enquanto nÃ£o
		 * existir peça na posisão Retorna valor na matriz como True
		 */
		p.setValues(position.getRow(), position.getColumn() - 1);//-1) porque a peça vai andar para esquerda(left)
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() - 1);//-1) porque a peça vai andar para esquerda(left)
		}
		/**
		 * Testa se existe posisão no tabuleiro e se existe peça do oponente Retorna valor na matriz como True
		 */
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		/**
		 * Rigth
		 * 
		 * Seta valores para a posisão p(column +1) porque a peça vai andar para direita(rigth)
		 * Enquanto existir posisão no tabuleiro E enquanto nÃ£o
		 * existir peça na posisão Retorna valor na matriz como True
		 */
		p.setValues(position.getRow(), position.getColumn() + 1);//+1) porque a peça vai andar para direita(rigth)
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() + 1);//+1) porque a peça vai andar para direita(rigth)
		}
		/**
		 * Testa se existe posisão no tabuleiro e se existe peça do oponente Retorna valor na matriz como True
		 */
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		/**
		 * Below
		 * 
		 * Seta valores para a posisão p(row +1) porque a peça vai andar para baixo(Below)
		 * Enquanto existir posisão no tabuleiro 
		 * E enquanto nÃ£o
		 * existir peça na posisão Retorna valor na matriz como True
		 */
		p.setValues(position.getRow() + 1, position.getColumn());//+1) porque a peça vai andar para baixo(below)
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() + 1);//+1) porque a peça vai andar para baixo(below)
		}
		/**
		 * Testa se existe posisão no tabuleiro e se existe peça do oponente Retorna valor na matriz como True
		 */
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		return mat;
	}
}