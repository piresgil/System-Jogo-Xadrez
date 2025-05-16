/**
 * @author Daniel Gil
 */
package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

/**
 * Classe principal para executar o jogo de xadrez no console.
 * <p>
 * Esta classe contém o método `main` que inicializa uma partida de xadrez,
 * interage com o usuário para obter os movimentos e exibe o estado do jogo
 * no console. Ela também trata exceções específicas do jogo de xadrez e
 * erros de entrada do usuário.
 */
public class Program {
    /**
     * Método principal que inicia e controla a execução do jogo de xadrez.
     *
     * @param args Os argumentos da linha de comando (não utilizados neste programa).
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> capturedPieces = new ArrayList<>();

        while (!chessMatch.getCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch, capturedPieces);

                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(sc);

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

                if (capturedPiece != null) {
                    capturedPieces.add(capturedPiece);
                }

                if (chessMatch.getPromoted() != null) {
                    System.out.print("Enter piece for promotion ([B]/[N]/[R]/[Q]) :");
                    String type = sc.nextLine().toUpperCase();
                    chessMatch.replacePromotedPiece(type);
                }
            } catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.clearScreen();
        UI.printMatch(chessMatch, capturedPieces);
        sc.close();
    }
}