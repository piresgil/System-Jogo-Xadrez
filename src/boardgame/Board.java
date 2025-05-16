/**
 * @author Daniel Gil
 */
package boardgame;

/**
 * Class Board Tabuleiro
 */
public class Board {

    /*
     * Variareis
     * rows, columns, pieces
     */
    private int rows;
    private int columns;

    /**
     * @matriz bidimensional que representa as peças no tabuleiro de xadrez.
     * Cada elemento da matriz pode conter uma instância da classe {@link Piece}
     * ou null se a casa estiver vazia.
     */
    private Piece[][] pieces;

    /**
     * Construtor
     * inicializa um novo tabuleiro de xadrez com o número especificado de linhas e colunas.
     *
     * @param rows    O número de linhas a serem criadas no tabuleiro. Deve ser um valor maior ou igual a 1.
     * @param columns O número de colunas a serem criadas no tabuleiro. Deve ser um valor maior ou igual a 1.
     * @throws BoardException Se `rows` ou `columns` for menor que 1.
     */
    public Board(int rows, int columns) {

        if (rows < 1 || columns < 1) {
            throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
        }
        this.rows = rows;
        this.columns = columns;

        pieces = new Piece[rows][columns];
    }

    /**
     * Getters
     */
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    /**
     * Methods Especiais
     * Retorna a peça localizada em uma determinada linha e coluna do tabuleiro.
     * <p>
     * Este método recebe as coordenadas da linha e da coluna e retorna a peça
     * (uma instância de {@link Piece} ou `null` se a casa estiver vazia) nessa posição.
     * Realiza uma verificação utilizando o método {@link #positionExists(int, int)}
     * para garantir que as coordenadas fornecidas sejam válidas dentro dos limites do tabuleiro.
     *
     * @param rows    A linha da posição desejada (indexada a partir de 0).
     * @param columns A coluna da posição desejada (indexada a partir de 0).
     * @return A peça encontrada na posição especificada, ou `null` se a casa estiver vazia.
     * @throws BoardException Se as coordenadas da linha ou da coluna estiverem fora dos limites do tabuleiro.
     */
    public Piece piece(int rows, int columns) {
        /*
         * Tratamento de exceções BoardException Testa se existem posições disponíveis
         * usando metodo position exists
         */
        if (!positionExists(rows, columns)) {
            throw new BoardException("Position not on the Board");
        }
        return pieces[rows][columns];
    }

    /**
     * Methods Especiais
     * Retorna a peça localizada na posição especificada do tabuleiro.
     * <p>
     * Esta sobrecarga do método {@link #piece(int, int)} recebe um objeto
     * {@link Position} e retorna a peça (uma instância de {@link Piece} ou
     * `null` se a casa estiver vazia) nessa posição. Realiza uma verificação
     * utilizando o método {@link #positionExists(Position)} para garantir que
     * a posição fornecida seja válida dentro dos limites do tabuleiro.
     *
     * @param position A {@link Position} do tabuleiro onde a peça desejada está localizada.
     * @return A peça encontrada na posição especificada, ou `null` se a casa estiver vazia.
     * @throws BoardException Se a posição fornecida estiver fora dos limites do tabuleiro.
     */
    public Piece piece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the Board");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    /**
     * Metodo place Piece
     * Coloca uma peça no tabuleiro na posição especificada.
     * <p>
     * Este método recebe uma instância de {@link Piece} e a {@link Position} onde
     * ela deve ser colocada. Realiza uma verificação utilizando o método
     * {@link #thereIsAPiece(Position)} para garantir que não haja outra peça
     * já presente na posição de destino.
     *
     * @param piece    A peça a ser colocada no tabuleiro.
     * @param position A posição no tabuleiro onde a peça será colocada.
     * @throws BoardException Se já existir uma peça na posição especificada.
     */
    public void placePiece(Piece piece, Position position) {

        if (thereIsAPiece(position)) {
            throw new BoardException("There is already a piece on position " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    /**
     * Metodo removePiece
     * Remove uma peça do tabuleiro na posição especificada.
     * <p>
     * Este método recebe uma {@link Position} e, se houver uma peça nessa posição,
     * remove-a do tabuleiro, desassocia a sua posição (definindo-a como `null`)
     * e a retorna. Se a posição estiver vazia, retorna `null`. Realiza uma
     * verificação utilizando o método {@link #positionExists(Position)} para
     * garantir que a posição fornecida seja válida dentro dos limites do tabuleiro.
     *
     * @param position A {@link Position} da peça a ser removida.
     * @return A peça removida da posição especificada, ou `null` se a posição estiver vazia.
     * @throws BoardException Se a posição fornecida estiver fora dos limites do tabuleiro.
     */
    public Piece removePiece(Position position) {

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
     * Method positionExists(row, column)
     * Verifica se uma determinada posição (linha e coluna) existe dentro dos limites do tabuleiro.
     *
     * @param row    A linha a ser verificada (indexada a partir de 0).
     * @param column A coluna a ser verificada (indexada a partir de 0).
     * @return `true` se a linha e a coluna estiverem dentro dos limites válidos do tabuleiro;
     * `false` caso contrário.
     */
    private boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    /**
     * Métodos positionExists(Position)
     * Verifica se uma determinada posição existe dentro dos limites do tabuleiro.
     * <p>
     * Esta sobrecarga do método {@link #positionExists(int, int)} recebe um objeto
     * {@link Position} e utiliza os seus métodos {@link Position#getRow()} e
     * {@link Position#getColumn()} para verificar a existência da posição.
     *
     * @param position A {@link Position} a ser verificada.
     * @return `true` se a posição estiver dentro dos limites válidos do tabuleiro;
     * `false` caso contrário.
     */
    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    /**
     * Method thereIsAPiece
     * Verifica se existe uma peça em uma determinada posição do tabuleiro.
     * <p>
     * Este método recebe uma {@link Position} e utiliza o método {@link #piece(Position)}
     * para obter a peça nessa posição. Retorna `true` se houver uma peça (ou seja,
     * o resultado de {@code piece(position)} não for `null`), e `false` caso contrário.
     * Realiza uma verificação de validade da posição utilizando o método
     * {@link #positionExists(Position)}.
     *
     * @param position A {@link Position} a ser verificada.
     * @return `true` se houver uma peça na posição especificada; `false` caso contrário.
     * @throws BoardException Se a posição fornecida estiver fora dos limites do tabuleiro.
     */
    public boolean thereIsAPiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the Board");
        }
        return piece(position) != null;
    }
}