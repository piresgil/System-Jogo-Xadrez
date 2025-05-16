/**
 * @author Daniel Gil
 */
package boardgame;

/**
 * Class Piece abstract
 */
public abstract class Piece {

    /*
     * Variaveis
     */
    /**
     * A posição atual da peça no tabuleiro.
     * <p>
     * Esta posição representa as coordenadas da peça dentro da matriz de peças
     * do {@link Board tabuleiro}. Se a peça ainda não foi colocada no tabuleiro,
     * esta variável será `null`.
     *
     * @see Board
     */
    protected Position position;

    /**
     * O tabuleiro de xadrez onde a peça está localizada.
     * <p>
     * Esta variável mantém uma referência ao objeto {@link Board} ao qual esta
     * peça pertence, permitindo que a peça acesse informações sobre o tabuleiro
     * e interaja com outras peças.
     *
     * @see Board
     */
    private Board board;

    /**
     * Construtor
     * associa a peça ao tabuleiro de xadrez.
     * <p>
     * Inicializa a peça com o tabuleiro fornecido e define a sua posição inicial como
     * `null`, indicando que a peça ainda não foi colocada numa casa específica do tabuleiro.
     *
     * @param board O tabuleiro de xadrez ao qual a peça será associada.
     */
    public Piece(Board board) {
        this.board = board;
        position = null; // Peça recém-criada não tem posições por padrão o java já coloca null, apenas para referência
    }

    // Getters

    protected Board getBoard() {
        return board;
    }

    /**
     * Metodo possible Moves
     * <p>
     * Movimentos positives Metodo abstrato
     * cria matriz boolean
     */
    public abstract boolean[][] possibleMoves();

    /**
     * Metodo possible Move
     * <p>
     * Metodo que utiliza metodo abstracto possibleMoves (faz um hook(gancho) com o
     * Metodo abstracto)
     * <p>
     * Retorna os movimentos possiveis
     */

    public boolean possibleMove(Position position) {
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    /**
     * Metodo is any possible move
     * <p>
     * Metodo que mostra movimentos possiveis
     * <p>
     * Percorre a matriz e busca posisoes verdadeiras, para poder mostrar movimentos
     * possiveis
     */
    public boolean isThereAnyPossibleMove() {
        boolean[][] mat = possibleMoves();

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if (mat[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }
}