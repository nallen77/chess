package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.HashSet;

public class chessMoveCalculator {

    /**
     * @param board the current board state of the game
     * @param nextPosition the position being checked for an enemy piece
     * @param pieceColor the current piece's color
     * @return whether there is an enemy piece in the next position
     */
    public static boolean isEnemyPiece(ChessBoard board, ChessPosition nextPosition, ChessGame.TeamColor pieceColor) {

        // Check if there is a piece, and if it is an enemy piece, then return true.
        // Otherwise, there is an ally, so return false.
        return (board.getPiece(nextPosition) != null) && (board.getPiece(nextPosition).getTeamColor() != pieceColor);

    }

    /**
     * @param board the current board state of the game
     * @param possibleMoves the list of possible moves to be updated
     * @param pieceColor the current piece's color
     * @param nextPosition the position being checked to add
     * @param nextMove the move to be added or not
     */
    public static void addPossibleMove(ChessBoard board, HashSet<ChessMove> possibleMoves, ChessGame.TeamColor pieceColor, ChessPosition nextPosition, ChessMove nextMove) {

        // Check if there is an enemy piece, then add to possible moves
        if (isEnemyPiece(board, nextPosition, pieceColor)) {
            possibleMoves.add(nextMove);
        }
        // If no piece, add to possible moves
        else if (board.getPiece(nextPosition) == null) {
            possibleMoves.add(nextMove);
        }
    }

    static void upAndLeftPath(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves) {
        // Upper left diagonal path
        int nextRow = currentRow + 1;
        int nextCol = currentCol - 1;

        // Check board boundary
        while (nextRow < 9 && nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) {
                    possibleMoves.add(nextMove);
                }
                break;
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }

            // Proceed up and to the left
            nextRow++;
            nextCol--;
        }
    }

    static void upAndRightPath(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves) {
        int nextCol;
        int nextRow;
        // Upper right diagonal path
        nextRow = currentRow + 1;
        nextCol = currentCol + 1;
        while (nextRow < 9 && nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) {
                    possibleMoves.add(nextMove);
                }
                break;
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }

            // Proceed up and to the right
            nextRow++;
            nextCol++;
        }
    }

    static void downAndRightPath(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves) {
        int nextRow;
        int nextCol;
        // Lower right diagonal path
        nextRow = currentRow - 1;
        nextCol = currentCol + 1;
        while (nextRow > 0 && nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) {
                    possibleMoves.add(nextMove);
                }
                break;
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }

            // Proceed down and to the right
            nextRow--;
            nextCol++;
        }
    }

    static void downAndLeftPath(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves) {
        int nextCol;
        int nextRow;
        // Lower left diagonal path
        nextRow = currentRow - 1;
        nextCol = currentCol - 1;
        while (nextRow > 0 && nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) {
                    possibleMoves.add(nextMove);
                }
                break;
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }

            // Proceed down and to the left
            nextRow--;
            nextCol--;
        }
    }

    static void upPath(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves) {
        int nextRow;
        // Up
        nextRow = currentRow + 1;

        // Check board boundary
        while (nextRow < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, currentCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) {
                    possibleMoves.add(nextMove);
                }
                break;
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }

            // Proceed up
            nextRow++;
        }
    }

    static void rightPath(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves) {
        int nextCol;
        // Right
        nextCol = currentCol + 1;

        // Check board boundary
        while (nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(currentRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) {
                    possibleMoves.add(nextMove);
                }
                break;
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }

            // Proceed right
            nextCol++;
        }
    }

    static void downPath(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves) {
        int nextRow;
        // Down
        nextRow = currentRow - 1;

        // Check board boundary
        while (nextRow > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, currentCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) {
                    possibleMoves.add(nextMove);
                }
                break;
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }

            // Proceed down
            nextRow--;
        }
    }

    static void leftPath(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves) {
        int nextCol;
        // Left
        nextCol = currentCol - 1;

        // Check board boundary
        while (nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(currentRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) {
                    possibleMoves.add(nextMove);
                }
                break;
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }

            // Proceed left
            nextCol--;
        }
    }
}
