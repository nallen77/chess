package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class DrawBoard {
    private final PrintStream out;

    public DrawBoard() {
        out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    }

    public void setup() {
        out.print(ERASE_SCREEN);
        String[][] board = createInitialBoard();
        printChessBoard(board, true);
        printChessBoard(board, false);
    }

    private String[][] createInitialBoard() {
        String[][] board = new String[8][8];
        String afterSpacing = "\u2001\u2005";
        String beforeSpacing = "\u2006";

        // Setting up white pieces
        board[0] = new String[]{beforeSpacing + "R" + afterSpacing, beforeSpacing + "N" + afterSpacing, beforeSpacing + "B" + afterSpacing, beforeSpacing + "Q" + afterSpacing, beforeSpacing + "K" + afterSpacing, beforeSpacing + "B" + afterSpacing, beforeSpacing + "N" + afterSpacing, beforeSpacing + "R" + afterSpacing};
        board[1] = new String[]{beforeSpacing + "P" + afterSpacing, beforeSpacing + "P" + afterSpacing, beforeSpacing + "P" + afterSpacing, beforeSpacing + "P" + afterSpacing, beforeSpacing + "P" + afterSpacing, beforeSpacing + "P" + afterSpacing, beforeSpacing + "P" + afterSpacing, beforeSpacing + "P" + afterSpacing};

        // Setting up empty spaces
        for (int i = 2; i < 6; i++) {
            board[i] = new String[]{beforeSpacing + " " + afterSpacing, beforeSpacing + " " + afterSpacing, beforeSpacing + " " + afterSpacing, beforeSpacing + " " + afterSpacing, beforeSpacing + " " + afterSpacing, beforeSpacing + " " + afterSpacing, beforeSpacing + " " + afterSpacing, beforeSpacing + " " + afterSpacing};
        }

        // Setting up black pieces
        board[6] = new String[]{beforeSpacing + "P" + afterSpacing, beforeSpacing + "P" + afterSpacing, beforeSpacing + "P" + afterSpacing, beforeSpacing + "P" + afterSpacing, beforeSpacing + "P" + afterSpacing, beforeSpacing + "P" + afterSpacing, beforeSpacing + "P" + afterSpacing, beforeSpacing + "P" + afterSpacing};
        board[7] = new String[]{beforeSpacing + "R" + afterSpacing, beforeSpacing + "N" + afterSpacing, beforeSpacing + "B" + afterSpacing, beforeSpacing + "Q" + afterSpacing, beforeSpacing + "K" + afterSpacing, beforeSpacing + "B" + afterSpacing, beforeSpacing + "N" + afterSpacing, beforeSpacing + "R" + afterSpacing};

        return board;
    }

    private void printChessBoard(String[][] board, boolean isWhiteView) {
        String spacing = "\u2001\u2005\u2006";
        String[] numbers = isWhiteView ? new String[]{"1" + spacing, "2" + spacing, "3" + spacing, "4" + spacing, "5" + spacing, "6" + spacing, "7" + spacing, "8" + spacing} : new String[]{"8" + spacing, "7" + spacing, "6" + spacing, "5" + spacing, "4" + spacing, "3" + spacing, "2" + spacing, "1" + spacing};

        row(isWhiteView);
        for (int i = 0; i < 8; i++) {
            out.print(SET_BG_COLOR_BLACK + EMPTY);
            out.print(SET_BG_COLOR_YELLOW + numbers[i]);
            for (int j = 0; j < 8; j++) {
                boolean isWhiteSquare = (i + j) % 2 == 0;
                out.print(isWhiteSquare ? SET_BG_COLOR_WHITE : SET_BG_COLOR_BLACK);
                String piece = board[isWhiteView ? i : 7 - i][isWhiteView ? j : 7 - j];
                if (!piece.trim().isEmpty()) {
                    if ((isWhiteView && i < 4) || (!isWhiteView && i >= 4)) {
                        out.print(SET_TEXT_COLOR_BLUE);
                    } else {
                        out.print(SET_TEXT_COLOR_MAGENTA);
                    }
                } else {
                    if ((isWhiteView && isWhiteSquare) || (!isWhiteView && !isWhiteSquare)) {
                        out.print(SET_TEXT_COLOR_BLACK);
                    } else {
                        out.print(SET_TEXT_COLOR_WHITE);
                    }
                }
                out.print(piece);
            }
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(SET_BG_COLOR_YELLOW + numbers[i]);
            out.print(SET_BG_COLOR_BLACK + EMPTY);
            out.print("\n");
        }

        row(isWhiteView);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
        out.print("\n");
    }

    private void row(boolean isWhiteView) {
        out.print("   ");

        out.print(SET_BG_COLOR_YELLOW);
        out.print(SET_TEXT_COLOR_BLACK);
        String spacing = "\u2001\u2005\u2006";
        System.out.print(SET_BG_COLOR_YELLOW + spacing + "  ");
        String[] fileLetters = isWhiteView ? new String[]{"a" + spacing, "b"+ spacing, "c"+ spacing, "d"+ spacing, "e"+ spacing, "f"+ spacing, "g"+ spacing, "h"+ spacing} : new String[]{"h"+ spacing, "g"+ spacing, "f"+ spacing, "e"+ spacing, "d"+ spacing, "c"+ spacing, "b"+ spacing, "a"+ spacing};
        for (String letter : fileLetters) {
            out.print(letter);
        }
        out.print(spacing);
        out.print(SET_BG_COLOR_BLACK);
        out.print("\n");
    }
}