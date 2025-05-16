
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
public class Bishop extends ChessPiece {
	/**
	 * Construtor
	 * 
	 * Busca construtor da Super Class
	 */
	public Bishop(Board board, Color color) {
		super(board, color);
	}

	/**
	 * Metodo To String
	 * 
	 * Busca super class (Override)
	 */
	@Override
	public String toString() {
		return "B";
	}

	/**
	 * Metodo Possible Moves
	 * 
	 * Busca super class (Override)
	 * 
	 * Cria matriz de boolean(false) com o tamanho do tabuleiro Cria nova variavel
	 * do tipo Position nos valores zero-zero para fazer testes
	 */
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		/**
		 * NW
		 * 
		 * Nord- este Diagonal Cima esquerda
		 * 
		 * Retorna valor na matriz como True
		 */
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() - 1);
		}
		/**
		 * Testa se existe posisões no tabuleiro e se existe peça do oponente Retorna
		 * valor na matriz como True
		 */
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 * NE
		 * 
		 * Norte oeste diaginal Cima direita
		 * 
		 * Retorna valor na matriz como True
		 */
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}
		/**
		 * Testa se existe posisões no tabuleiro e se existe peça do oponente Retorna
		 * valor na matriz como True
		 */
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 * SE
		 * 
		 * Sul este diaginal baixo direita
		 * 
		 * 
		 * Retorna valor na matriz como True
		 */
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() + 1);
			}

			/**
			 * Testa se existe posisões no tabuleiro e se existe peça do oponente Retorna
			 * valor na matriz como True
			 */
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			/**
			 * SW
			 * 
			 * Sul Este - diagonal baixo-esquerda
			 * 
			 * Retorna valor na matriz como True
			 */
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
				p.setValues(p.getRow() + 1, p.getColumn() - 1);
			}
			/**
			 * Testa se existe posisões no tabuleiro e se existe peça do oponente Retorna
			 * valor na matriz como True
			 */
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		return mat;
	}
}