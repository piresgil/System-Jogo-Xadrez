
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
 *
 */
public class UI {
	/**
	 * Retirado do site
	 * https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	 * 
	 * Cores do Texto
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
	 * 
	 * Metodo para limpar  ecran 
	 * 
	 * Retirado do site
	 * https://stackoverflow.com/questions/2979383/java-clear-the-console
	 */
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	/**
	 * Metodo Read Chess Positon
	 * 
	 * Metodos para ler uma pocição
	 * 
	 * Recebe uma entrada do scanner
	 * 
	 * Cria variaveis para receber valor do scanner
	 * 
	 * Converte para char e integer
	 * 
	 * Tratamento de excepcões
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
	 * Metodo Print Match
	 * 
	 * Metodo imprime tabuleiro de jogo
	 * 
	 * Recebe uma ChessMatch e imprime
	 * 
	 * Recebe uma lista do tipo ChessPiece (peças capturadas) e imprime atravez do
	 * metodo printcapturedpieces
	 * 
	 * Imprime Turn e Verifica se existe check ou check Mate(imprime winner e
	 * encerra sistema)
	 */
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces());
		System.out.println();
		printCapturedPieces(captured);
		System.out.println();
		System.out.println("Turn: " + chessMatch.getTurn());

		if (!chessMatch.getcheckMate()) {
			System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
			if (chessMatch.getcheck()) {
				System.out.println("CHECK!");
			}
		} else {
			System.out.println("CHECKMATE!");
			System.out.println("Winner: " + chessMatch.getCurrentPlayer());
		}
	}

	/**
	 * MetodoPrint Board
	 * 
	 * Metodo para imprimir tabuleiro
	 * 
	 * Recebe uma matriz do tipo (class ChessPiece)
	 * 
	 * Percorre matriz (com 2 for) e ainda uza metodo auxiliar para imprimir uma
	 * peça
	 * 
	 * Dentro do 1Âº for imprime uma coluna com os numeros de 1 a 8 (8 - i) que faz
	 * os numeros ficarem invertidos 8 a 1
	 * 
	 * Dentro do 2Âº for imprime entao a pocição [i][j] da peça atravez do metodo
	 * auxiliar
	 * 
	 * No final depois dos (for) imprimir linha com os valores de 'a' ate 'h'
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
	 * Metodo Print Piece
	 * 
	 * Metodo auxiliar para imprimir uma peça
	 * 
	 * Recebe uma chessPiece piece
	 * 
	 * Testa se existe peça se NAO existir - Imprime "-"
	 * 
	 * Se existir peça Testa cor da peça e Imprime
	 * 
	 * No final antes de fechar metodo imprimir espaço em branco (" ") para as peças
	 * ficarem distanciadas
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
	 * Metodo Print board Sobrecarga
	 * 
	 * Metodo para imprimir Tabuleiro Sobrecarga
	 * 
	 * Recebe mais uma matriz boolean para mostrar possible moves
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
	 * Metodo print Capture Piece
	 *
	 * Metodo imprime peças capturadas
	 * 
	 * Recebe uma lista de ChessPiece Cria 2 listas
	 * 
	 * Uma para preto outra para branco (expressoes lambdida(predicado) filter x,
	 * recebe lista em x, e filtra x pelo metodo getColor)
	 * 
	 * Em seguida imprime na tela e uza o Arrays.tostring para imprrimir a lista
	 * respectiva de branco ou preto
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