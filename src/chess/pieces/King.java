
package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

/**
 * Class King Rei
 * 
 * Herda de ChessPiece
 *
 */
public class King extends ChessPiece {

	/**
	 * Dependendcia para CHESSMATCH
	 */
	private ChessMatch chessMatch;

	/**
	 * Construtor
	 * 
	 * Busca construtor da Super Class
	 * 
	 * e adiciona dependencia
	 */
	public King(Board board, Color color,ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	/**
	 * Metodo To String
	 * 
	 * Busca super class (Override)
	 */
	@Override
	public String toString() {
		return "K";
	}

	/**
	 * Metodo Can Move
	 * 
	 * Cria nova variavel p do tipo ChessPiece
	 * 
	 * retorna True quando p for nulo (casa vazia) OU QUANDO a quando a cor Ã©
	 * diferente da cor da CLASS
	 */
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	/**
	 * Metodo test Rook Castling
	 * 
	 * Movimentos Especiais - Castling
	 * 
	 * Testa se existe uma torre para a jogada especial(roque\Castling)
	 * 
	 * retorna true quando a existir peçam for da mesma cor e ainda nao tiver klk
	 * movimento
	 * 
	 */
	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
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
		 * Above
		 * 
		 * Seta valores para a posisão p(row -1) porque a peça vai andar para
		 * cima(Above) Se existir posisão no tabuleiro e se existir peça na posisão
		 * Retorna valor na matriz como True
		 */
		p.setValues(position.getRow() - 1, position.getColumn());// -1) porque a peça vai andar para cima(above)
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 * Below
		 * 
		 * Seta valores para a posisão p(row +1) porque a peça vai andar para
		 * baixo(below) Se existir posisão no tabuleiro e se existir peça na posisão
		 * Retorna valor na matriz como True
		 */
		p.setValues(position.getRow() + 1, position.getColumn());// +1) porque a peça vai andar para baixo(Below)
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 * Left
		 * 
		 * Seta valores para a posisão p(columnn -1) porque a peça vai andar para
		 * esquerda(left) Se existir posisão no tabuleiro e se existir peça na posisão
		 * Retorna valor na matriz como True
		 */
		p.setValues(position.getRow(), position.getColumn() - 1);// -1) porque a peça vai andar para esquerda(left)
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 * Rigth
		 * 
		 * Seta valores para a posisão p(columnn +1) porque a peça vai andar para
		 * direita(Rigth) Se existir posisão no tabuleiro e se existir peça na posisão
		 * Retorna valor na matriz como True
		 */
		p.setValues(position.getRow(), position.getColumn() + 1);// +1) porque a peça vai andar para direita(Rigth)
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 * NW cima esquerda
		 * 
		 * Seta valores para a posisão p(row -1)(columnn -1) porque a peça vai andar
		 * para Nord-Este(cima esquerd)(NW) Se existir posisão no tabuleiro e se existir
		 * peça na posisão Retorna valor na matriz como True
		 */
		p.setValues(position.getRow() - 1, position.getColumn() - 1);// -1)-1) porque a peça vai andar para
																		// Nord-Este(cima
																		// esquerd)(NW)
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 * NE cima Direita
		 * 
		 * Seta valores para a posisão p(row -1)(columnn +1) porque a peça vai andar
		 * para Nord-oeste(cima direita)(NE) Se existir posisão no tabuleiro e se
		 * existir peça na posisão Retorna valor na matriz como True
		 */
		p.setValues(position.getRow() - 1, position.getColumn() + 1);// -1)+1) porque a peça vai andar para
																		// Nord-Este(cima
																		// esquerd)(NE)
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 * SW Baixo esquerda
		 * 
		 * Seta valores para a posisão p(row +1)(columnn -1) porque a peça vai andar
		 * para sud-Este(baixo esquerda)(SW) Se existir posisão no tabuleiro e se
		 * existir peça na posisão Retorna valor na matriz como True
		 */
		p.setValues(position.getRow() + 1, position.getColumn() - 1);// +1)-1) porque a peça vai andar para
																		// sud-Este(baixo esquerda)(SW)
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 * SE baixo Direita
		 * 
		 * Seta valores para a posisão p(row +1)(columnn +1) porque a peça vai andar
		 * para sud-oeste(baixo direita)(SE) Se existir posisão no tabuleiro e se
		 * existir peça na posisão Retorna valor na matriz como True
		 */
		p.setValues(position.getRow() + 1, position.getColumn() + 1);// +1)+1) porque a peça vai andar para
																		// sud-oeste(baixo direita)(SE)
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 * Movimento especial Castling
		 * 
		 */

		if (getMoveCount() == 0 && !chessMatch.getcheck()) {
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