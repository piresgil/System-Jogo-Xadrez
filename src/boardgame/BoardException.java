
package boardgame;

/**
 * Class BoardException
 */
public class BoardException extends RuntimeException {
	/**
	 * valor default
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Construtor
	 */
	public BoardException(String msg) {
		super(msg);
	}
}