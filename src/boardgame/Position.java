/**
 * @author Daniel Gil
 */
package boardgame;

/**
 * Class Position
 */
public class Position {

    /*
     * Variareis
     * row, column
     */
    private int row;
    private int column;

    /**
     * Construtor
     * inicializa uma nova posição no tabuleiro com a linha e a coluna especificadas.
     *
     * @param row    A linha da posição.
     * @param column A coluna da posição.
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }


    // Getter Setters

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
     * Method setValues
     * Define os valores da linha e da coluna desta posição.
     *
     * @param row    O novo valor para a linha da posição.
     * @param column O novo valor para a coluna da posição.
     */
    public void setValues(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Metodo to String
     *
     * @return imprime a posição
     */
    @Override
    public String toString() {
        return row + ", " + column;
    }
}