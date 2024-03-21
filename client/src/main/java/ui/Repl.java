package ui;

import chess.ChessBoard;

import java.util.Scanner;


public class Repl {
    private final ChessClient client;

    private final ChessBoard chessBoard;

    public Repl(String serverUrl) {
        client = new ChessClient(serverUrl, this);
        chessBoard = new ChessBoard();
    }

    public void run() {
        System.out.println("Welcome to Chess.");
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + ">>> ");
    }

}

