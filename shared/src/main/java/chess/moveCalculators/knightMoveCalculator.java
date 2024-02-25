package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class knightMoveCalculator extends chessMoveCalculator {

    public static Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        // Up 2, left 1
        int nextRow = currentRow + 2;
        int nextCol = currentCol - 1;

        // Check board boundary
        if (nextRow < 9 && nextCol > 0) {
            // Add move if possible
            knightMove(board, myPosition, pieceColor, possibleMoves, nextRow, nextCol);
        }

        // Up 2, right 1
        nextRow = currentRow + 2;
        nextCol = currentCol + 1;

        // Check board boundary
        if (nextRow < 9 && nextCol < 9) {
            // Add move if possible
            knightMove(board, myPosition, pieceColor, possibleMoves, nextRow, nextCol);
        }

        // Right 2, up 1
        nextRow = currentRow + 1;
        nextCol = currentCol + 2;

        // Check board boundary
        if (nextRow < 9 && nextCol < 9) {
            // Add move if possible
            knightMove(board, myPosition, pieceColor, possibleMoves, nextRow, nextCol);
        }

        // Right 2, down 1
        nextRow = currentRow - 1;
        nextCol = currentCol + 2;

        // Check board boundary
        if (nextRow > 0 && nextCol < 9) {
            // Add move if possible
            knightMove(board, myPosition, pieceColor, possibleMoves, nextRow, nextCol);
        }

        // Down 2, right 1
        nextRow = currentRow - 2;
        nextCol = currentCol + 1;

        // Check board boundary
        if (nextRow > 0 && nextCol < 9) {
            // Add move if possible
            knightMove(board, myPosition, pieceColor, possibleMoves, nextRow, nextCol);
        }

        // Down 2, left 1
        nextRow = currentRow - 2;
        nextCol = currentCol - 1;

        // Check board boundary
        if (nextRow > 0 && nextCol > 0) {
            // Add move if possible
            knightMove(board, myPosition, pieceColor, possibleMoves, nextRow, nextCol);
        }


        // Left 2, down 1
        nextRow = currentRow - 1;
        nextCol = currentCol - 2;

        // Check board boundary
        if (nextRow > 0 && nextCol > 0) {
            // Add move if possible
            knightMove(board, myPosition, pieceColor, possibleMoves, nextRow, nextCol);
        }

        // Left 2, up 1
        nextRow = currentRow + 1;
        nextCol = currentCol - 2;

        // Check board boundary
        if (nextRow < 9 && nextCol > 0) {
            // Add move if possible
            knightMove(board, myPosition, pieceColor, possibleMoves, nextRow, nextCol);
        }
        return possibleMoves;
    }

    private static void knightMove(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, HashSet<ChessMove> possibleMoves, int nextRow, int nextCol) {
        ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
        ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);
        addPossibleMove(board, possibleMoves, pieceColor, nextPosition, nextMove);
    }
}
