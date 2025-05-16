/**
 * @author Daniel Gil
 */
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
     * @param row
     * @param column
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
     * @param row
     * @param column
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