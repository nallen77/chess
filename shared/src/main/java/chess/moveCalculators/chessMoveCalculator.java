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
}
