package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.HashSet;

public class KingMoveCalculator extends ChessMoveCalculator {

    public static HashSet<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        kingMovesUp(board, myPosition, currentRow, currentCol, possibleMoves, pieceColor);

        kingMovesUpRight(board, myPosition, currentRow, currentCol, possibleMoves, pieceColor);

        kingMovesRight(board, myPosition, currentRow, currentCol, possibleMoves, pieceColor);

        kingMovesDownRight(board, myPosition, currentRow, currentCol, possibleMoves, pieceColor);

        kingMovesDown(board, myPosition, currentRow, currentCol, possibleMoves, pieceColor);

        kingMovesDownLeft(board, myPosition, currentRow, currentCol, possibleMoves, pieceColor);

        kingMovesLeft(board, myPosition, currentRow, currentCol, possibleMoves, pieceColor);

        kingMovesUpLeft(board, myPosition, currentRow, currentCol, possibleMoves, pieceColor);

        return possibleMoves;
    }

    private static void kingMovesUpLeft(ChessBoard board, ChessPosition myPosition, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves, ChessGame.TeamColor pieceColor) {
        int nextColumn;
        int nextRow;
        // Up Left
        nextRow = currentRow + 1;
        nextColumn = currentCol - 1;

        // Check board boundary
        if (nextRow < 9 && nextColumn > 0) {
            // Add the move if possible
            ChessPosition nextPosition = new ChessPosition(nextRow, nextColumn);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);
            addPossibleMove(board, possibleMoves, pieceColor, nextPosition, nextMove);
        }
    }

    private static void kingMovesLeft(ChessBoard board, ChessPosition myPosition, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves, ChessGame.TeamColor pieceColor) {
        int nextCol;
        // Left
        nextCol = currentCol - 1;

        // Check board boundary
        if (nextCol > 0) {
            // Add the move if possible
            ChessPosition nextPosition = new ChessPosition(currentRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);
            addPossibleMove(board, possibleMoves, pieceColor, nextPosition, nextMove);
        }
    }

    private static void kingMovesDownLeft(ChessBoard board, ChessPosition myPosition, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves, ChessGame.TeamColor pieceColor) {
        int nextRow;
        int nextCo;
        // Down Left
        nextRow = currentRow - 1;
        nextCo = currentCol - 1;

        // Check board boundary
        if (nextRow > 0 && nextCo > 0) {
            // Add the move if possible
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCo);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);
            addPossibleMove(board, possibleMoves, pieceColor, nextPosition, nextMove);
        }
    }

    private static void kingMovesDown(ChessBoard board, ChessPosition myPosition, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves, ChessGame.TeamColor pieceColor) {
        int nextRow;
        // Down
        nextRow = currentRow - 1;

        // Check board boundary
        if (nextRow > 0) {
            // Add the move if possible
            ChessPosition nextPosition = new ChessPosition(nextRow, currentCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);
            addPossibleMove(board, possibleMoves, pieceColor, nextPosition, nextMove);
        }
    }

    private static void kingMovesDownRight(ChessBoard board, ChessPosition myPosition, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves, ChessGame.TeamColor pieceColor) {
        int nextR;
        int nextCol;
        // Down Right
        nextR = currentRow - 1;
        nextCol = currentCol + 1;

        // Check board boundary
        if (nextR > 0 && nextCol < 9) {
            // Add the move if possible
            ChessPosition nextPosition = new ChessPosition(nextR, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);
            addPossibleMove(board, possibleMoves, pieceColor, nextPosition, nextMove);
        }
    }

    private static void kingMovesRight(ChessBoard board, ChessPosition myPosition, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves, ChessGame.TeamColor pieceColor) {
        int nextColumn;
        // Right
        nextColumn = currentCol + 1;

        // Check board boundary
        if (nextColumn < 9) {
            // Add the move if possible
            ChessPosition nextPosition = new ChessPosition(currentRow, nextColumn);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);
            addPossibleMove(board, possibleMoves, pieceColor, nextPosition, nextMove);
        }
    }

    private static void kingMovesUpRight(ChessBoard board, ChessPosition myPosition, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves, ChessGame.TeamColor pieceColor) {
        int nextR;
        int nextC;
        // Up Right
        nextR = currentRow + 1;
        nextC = currentCol + 1;

        // Check board boundary
        if (nextR < 9 && nextC < 9) {
            // Add the move if possible
            ChessPosition nextPosition = new ChessPosition(nextR, nextC);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);
            addPossibleMove(board, possibleMoves, pieceColor, nextPosition, nextMove);
        }
    }

    private static void kingMovesUp(ChessBoard board, ChessPosition myPosition, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves, ChessGame.TeamColor pieceColor) {
        int next;
        // Up
        next = currentRow + 1;

        // Check board boundary
        if (next < 9) {
            // Add the move if possible
            ChessPosition nextPosition = new ChessPosition(next, currentCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);
            addPossibleMove(board, possibleMoves, pieceColor, nextPosition, nextMove);
        }
    }

}
