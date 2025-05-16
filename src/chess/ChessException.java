
package chess;

import boardgame.BoardException;

/**
 * Class ChessException Herda BoardException
 */
public class ChessException extends BoardException {
	/**
	 * valor default
	 */
	private static final long serialVersionUID = 1L;

	public ChessException(String msg) {
		super(msg);
	}
}