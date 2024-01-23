package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType pieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.pieceType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to.
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        return switch (pieceType) {
            case KING -> kingMoves(board, myPosition);
            case QUEEN -> queenMoves(board, myPosition);
            case BISHOP -> bishopMoves(board, myPosition);
            case KNIGHT -> knightMoves(board, myPosition);
            case ROOK -> rookMoves(board, myPosition);
            case PAWN -> pawnMoves(board, myPosition);
        };
    }

    private HashSet<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        ChessPosition currentPosition = myPosition;
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        // Check valid move range

            // Check if there is a team piece

                // Add to possibleMoves

        return possibleMoves;
    }

    private Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        ChessPosition currentPosition = myPosition;
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        return possibleMoves;
    }

    private Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        // Upper left diagonal path
        int nextRow = currentRow + 1;
        int nextCol = currentCol - 1;

        // Check board boundary
        while (nextRow < 9 && nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(new ChessMove(myPosition, nextPosition, null));
                }
                break;
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(new ChessMove(myPosition, nextPosition, null));
            }

            // Proceed up and to the left
            nextRow++;
            nextCol--;
        }

        // Upper right diagonal path
        nextRow = currentRow + 1;
        nextCol = currentCol + 1;
        while (nextRow < 9 && nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(new ChessMove(myPosition, nextPosition, null));
                }
                break;
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(new ChessMove(myPosition, nextPosition, null));
            }

            // Proceed up and to the right
            nextRow++;
            nextCol++;
        }

        // Lower right diagonal path
        nextRow = currentRow - 1;
        nextCol = currentCol + 1;
        while (nextRow > 0 && nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(new ChessMove(myPosition, nextPosition, null));
                }
                break;
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(new ChessMove(myPosition, nextPosition, null));
            }

            // Proceed down and to the right
            nextRow--;
            nextCol++;
        }

        // Lower left diagonal path
        nextRow = currentRow - 1;
        nextCol = currentCol - 1;
        while (nextRow > 0 && nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(new ChessMove(myPosition, nextPosition, null));
                }
                break;
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(new ChessMove(myPosition, nextPosition, null));
            }

            // Proceed down and to the left
            nextRow--;
            nextCol--;
        }

        return possibleMoves;
    }

    private Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        ChessPosition currentPosition = myPosition;
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        return possibleMoves;
    }

    private Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        ChessPosition currentPosition = myPosition;
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        return possibleMoves;
    }

    private Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        ChessPosition currentPosition = myPosition;
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        return possibleMoves;
    }
}
