
package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.List;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

/**
 * Class UI (User Interface)
 */
public class UI {

    /**
     * Cores do Texto
     * <p>
     * Retirado do site
     * https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
     */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * Cores do fundo
     * <p>
     * Retirado do site
     * https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
     */
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    /**
     * Metodo Clear Screen
     * <p>
     * Metodo para limpar  ecran
     * <p>
     * Retirado do site
     * https://stackoverflow.com/questions/2979383/java-clear-the-console
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Metodo ReadChessPositon
     * Lê uma posição de xadrez a partir da entrada do scanner.
     * <p>
     * Este método espera uma entrada no formato de notação de xadrez (e.g., "a1", "h8").
     * Ele lê a linha da entrada, extrai a coluna (a primeira letra) e a linha (o restante da string),
     * convertendo-os para os tipos apropriados para criar um objeto {@link ChessPosition}.
     *
     * @param sc O objeto {@link Scanner} de onde a entrada será lida.
     * @return Um objeto {@link ChessPosition} representando a posição de xadrez lida.
     * @throws InputMismatchException Se a entrada não estiver no formato válido (e.g., "a1" a "h8").
     */
    public static ChessPosition readChessPosition(Scanner sc) {

        try {
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Error reading ChessPossition. Valid values a1 to h8.");
        }
    }

    /**
     * Metodo PrintMatch
     * Imprime o tabuleiro de xadrez atual e as informações da partida.
     * <p>
     * Este método recebe uma instância de {@link ChessMatch} para acessar o estado
     * atual do jogo e uma lista de {@link ChessPiece} que foram capturadas.
     * Imprime o tabuleiro utilizando o método {@link #printBoard(ChessPiece[][])},
     * as peças capturadas através do método {@link #printCapturedPieces(List)},
     * o número do turno atual e indica se há xeque ou xeque-mate. Em caso de xeque-mate,
     * imprime o vencedor e encerra o sistema.
     *
     * @param chessMatch A instância de {@link ChessMatch} contendo o estado atual do jogo.
     * @param captured Uma lista de {@link ChessPiece} que foram capturadas durante a partida.
     */
    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(captured);
        System.out.println();
        System.out.println("Turn: " + chessMatch.getTurn());

        if (!chessMatch.getcheckMate()) {
            System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
            if (chessMatch.getCheck()) {
                System.out.println("CHECK!");
            }
        } else {
            System.out.println("CHECKMATE!");
            System.out.println("Winner: " + chessMatch.getCurrentPlayer());
        }
    }

    /**
     * Method printBoard
     * Imprime o tabuleiro de xadrez no formato de texto para a saída do console.
     * <p>
     * Este método recebe uma matriz bidimensional de {@link ChessPiece} e itera
     * pelas suas linhas e colunas para imprimir cada casa do tabuleiro.
     * <p>
     * A numeração das linhas é impressa na lateral esquerda, de 8 a 1, e as colunas
     * são identificadas pelas letras de 'a' a 'h' na parte inferior.
     * Cada peça é impressa utilizando o método auxiliar {@link #printPiece(ChessPiece, boolean)}.
     *
     * @param pieces A matriz bidimensional de {@link ChessPiece} representando
     *               o estado atual do tabuleiro.
     */
    public static void printBoard(ChessPiece[][] pieces) {

        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], false);// uza metodo auxiliar
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    /**
     * Method printPiece
     * Método auxiliar para imprimir uma peça de xadrez no tabuleiro.
     * <p>
     * Recebe uma instância de {@link ChessPiece} e um booleano indicando se o fundo deve ser destacado.
     * Se a peça for nula (casa vazia), imprime "-". Caso contrário, imprime a representação
     * da peça com a cor apropriada (branco em branco, amarelo em preto), utilizando códigos ANSI
     * para formatação de cores no terminal. Adiciona um espaço em branco após a impressão da peça.
     *
     * @param piece      A peça de xadrez a ser impressa. Pode ser `null` para representar uma casa vazia.
     * @param background Um booleano que indica se o fundo da casa deve ser destacado (por exemplo, para indicar movimentos possíveis).
     */
    private static void printPiece(ChessPiece piece, boolean background) {
        if (background) {
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            if (piece.getColor() == Color.White) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            } else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    /**
     * Method printBoard
     * Imprime o tabuleiro de xadrez no formato de texto para a saída do console,
     * destacando opcionalmente as casas com movimentos possíveis.
     * <p>
     * Esta sobrecarga do método {@link #printBoard(ChessPiece[][])} recebe adicionalmente
     * uma matriz booleana que indica quais casas devem ser destacadas para mostrar
     * os movimentos possíveis de uma peça selecionada. As casas destacadas são
     * impressas com um fundo azul utilizando códigos ANSI.
     *
     * @param pieces        A matriz bidimensional de {@link ChessPiece} representando
     *                      o estado atual do tabuleiro.
     * @param possibleMoves Uma matriz booleana com as mesmas dimensões do tabuleiro,
     *                      onde `true` indica que a casa correspondente é um movimento possível
     *                      e `false` caso contrário.
     */
    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {

        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);// uza metodo auxiliar
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    /**
     * Metodo printCapturePiece
     * Imprime as peças capturadas durante a partida de xadrez, separadas por cor.
     * <p>
     * Este método recebe uma lista de {@link ChessPiece} que foram capturadas.
     * Utiliza expressões lambda (predicados) com o método {@code filter} do Stream
     * para criar duas listas separadas: uma contendo as peças brancas capturadas
     * e outra com as peças pretas capturadas. Em seguida, imprime na saída do
     * console as peças capturadas de cada cor, utilizando códigos ANSI para
     * formatação de cores (branco e amarelo).
     *
     * @param captured Uma lista de {@link ChessPiece} que foram capturadas durante a partida.
     */
    private static void printCapturedPieces(List<ChessPiece> captured) {

        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.White)
                .collect(Collectors.toList());

        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.Black)
                .collect(Collectors.toList());

        System.out.println("Captured pieces: ");
        System.out.print("White: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString(white.toArray()));
        System.out.print(ANSI_RESET);
        System.out.print("Black: ");
        System.out.print(ANSI_YELLOW);
        System.out.println(Arrays.toString(black.toArray()));
        System.out.print(ANSI_RESET);
    }
}