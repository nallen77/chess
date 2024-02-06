package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

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

    // Comment just to commit this code that did pass all phase 0 tests
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, pieceType);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", pieceType=" + pieceType +
                '}';
    }

    private HashSet<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        // Up
        int nextRow = currentRow + 1;

        // Check board boundary
        if (nextRow < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, currentCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally so don't add move
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(nextMove);
                }
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }
        }

        // Up Right
        nextRow = currentRow + 1;
        int nextCol = currentCol + 1;

        // Check board boundary
        if (nextRow < 9 && nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally so don't add move
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(nextMove);
                }
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }
        }

        // Right
        nextCol = currentCol + 1;

        // Check board boundary
        if (nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(currentRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally so don't add move
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(nextMove);
                }
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }
        }

        // Down Right
        nextRow = currentRow - 1;
        nextCol = currentCol + 1;

        // Check board boundary
        if (nextRow > 0 && nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally so don't add move
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(nextMove);
                }
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }
        }

        // Down
        nextRow = currentRow - 1;

        // Check board boundary
        if (nextRow > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, currentCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally so don't add move
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(nextMove);
                }
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }
        }

        // Down Left
        nextRow = currentRow - 1;
        nextCol = currentCol - 1;

        // Check board boundary
        if (nextRow > 0 && nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally so don't add move
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(nextMove);
                }
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }
        }

        // Left
        nextCol = currentCol - 1;

        // Check board boundary
        if (nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(currentRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally so don't add move
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(nextMove);
                }
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }
        }

        // Up Left
        nextRow = currentRow + 1;
        nextCol = currentCol - 1;

        // Check board boundary
        if (nextRow < 9 && nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally so don't add move
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(nextMove);
                }
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }
        }

        return possibleMoves;
    }

    private Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        HashSet<ChessMove> possibleMoves = new HashSet<>();

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
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
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

        // Upper right diagonal path
        nextRow = currentRow + 1;
        nextCol = currentCol + 1;
        while (nextRow < 9 && nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
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

        // Lower right diagonal path
        nextRow = currentRow - 1;
        nextCol = currentCol + 1;
        while (nextRow > 0 && nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
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

        // Lower left diagonal path
        nextRow = currentRow - 1;
        nextCol = currentCol - 1;
        while (nextRow > 0 && nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
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

        // Up
        nextRow = currentRow + 1;

        // Check board boundary
        while (nextRow < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, currentCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
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

        // Right
        nextCol = currentCol + 1;

        // Check board boundary
        while (nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(currentRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
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

        // Down
        nextRow = currentRow - 1;

        // Check board boundary
        while (nextRow > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, currentCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
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

        // Left
        nextCol = currentCol - 1;

        // Check board boundary
        while (nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(currentRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
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
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
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

        // Upper right diagonal path
        nextRow = currentRow + 1;
        nextCol = currentCol + 1;
        while (nextRow < 9 && nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
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

        // Lower right diagonal path
        nextRow = currentRow - 1;
        nextCol = currentCol + 1;
        while (nextRow > 0 && nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
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

        // Lower left diagonal path
        nextRow = currentRow - 1;
        nextCol = currentCol - 1;
        while (nextRow > 0 && nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
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

        return possibleMoves;
    }

    private Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        // Up 2, left 1
        int nextRow = currentRow + 2;
        int nextCol = currentCol - 1;

        // Check board boundary
        if (nextRow < 9 && nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally so don't add move
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(nextMove);
                }
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }
        }

        // Up 2, right 1
        nextRow = currentRow + 2;
        nextCol = currentCol + 1;

        // Check board boundary
        if (nextRow < 9 && nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally so don't add move
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(nextMove);
                }
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }
        }

        // Right 2, up 1
        nextRow = currentRow + 1;
        nextCol = currentCol + 2;

        // Check board boundary
        if (nextRow < 9 && nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally so don't add move
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(nextMove);
                }
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }
        }

        // Right 2, down 1
        nextRow = currentRow - 1;
        nextCol = currentCol + 2;

        // Check board boundary
        if (nextRow > 0 && nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally so don't add move
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(nextMove);
                }
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }
        }

        // Down 2, right 1
        nextRow = currentRow - 2;
        nextCol = currentCol + 1;

        // Check board boundary
        if (nextRow > 0 && nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally so don't add move
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(nextMove);
                }
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }
        }

        // Down 2, left 1
        nextRow = currentRow - 2;
        nextCol = currentCol - 1;

        // Check board boundary
        if (nextRow > 0 && nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally so don't add move
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(nextMove);
                }
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }
        }


        // Left 2, down 1
        nextRow = currentRow - 1;
        nextCol = currentCol - 2;

        // Check board boundary
        if (nextRow > 0 && nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally so don't add move
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(nextMove);
                }
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }
        }

        // Left 2, up 1
        nextRow = currentRow + 1;
        nextCol = currentCol - 2;

        // Check board boundary
        if (nextRow < 9 && nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally so don't add move
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
                    possibleMoves.add(nextMove);
                }
            }
            // If no piece, add to possible moves
            else {
                possibleMoves.add(nextMove);
            }
        }

        return possibleMoves;
    }

    private Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        // Up
        int nextRow = currentRow + 1;

        // Check board boundary
        while (nextRow < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, currentCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
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

        // Right
        int nextCol = currentCol + 1;

        // Check board boundary
        while (nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(currentRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
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

        // Down
        nextRow = currentRow - 1;

        // Check board boundary
        while (nextRow > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, currentCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
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

        // Left
        nextCol = currentCol - 1;

        // Check board boundary
        while (nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(currentRow, nextCol);
            ChessMove nextMove = new ChessMove(myPosition, nextPosition, null);

            // Check if there is a piece
            if (board.getPiece(nextPosition) != null) {

                // If enemy piece, add move to take it. Otherwise, there is an ally, so break the loop
                if (board.getPiece(nextPosition).pieceColor != this.pieceColor) {
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



        return possibleMoves;
    }

    private Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        // White move forward
        if (this.pieceColor == ChessGame.TeamColor.WHITE) {
            int nextRow = currentRow + 1;
            int nextColLeft = currentCol - 1;
            int nextColRight = currentCol + 1;
            int doubleMove = currentRow + 2;
            ChessPosition forwardPosition = new ChessPosition(nextRow, currentCol);
            ChessPosition doubleForward = new ChessPosition(doubleMove, currentCol);
            ChessPosition forwardLeft = new ChessPosition(nextRow, nextColLeft);
            ChessPosition forwardRight = new ChessPosition(nextRow, nextColRight);

            // Check board boundary
            if (nextRow < 9) {

                // Forward
                if (board.getPiece(forwardPosition) == null) {

                    // Check promotion row in front
                    if (nextRow == 8) {
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, PieceType.QUEEN));
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, PieceType.BISHOP));
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, PieceType.KNIGHT));
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, PieceType.ROOK));
                    }
                    // Otherwise, check if in starting position and no pieces in front
                    else if (currentRow == 2 && board.getPiece(doubleForward) == null) {
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, null));
                        possibleMoves.add(new ChessMove(myPosition, doubleForward, null));
                    }

                    // Otherwise, add front move
                    else {
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, null));
                    }
                }

                if (nextColLeft > 1) {
                    // Forward left
                    if (board.getPiece(forwardLeft) != null) {
                        // Check promotion row front left
                        if (board.getPiece(forwardLeft).pieceColor != this.pieceColor && nextRow == 8) {
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, PieceType.QUEEN));
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, PieceType.BISHOP));
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, PieceType.KNIGHT));
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, PieceType.ROOK));
                        }
                        // Check forward left for enemy piece
                        else if (board.getPiece(forwardLeft).pieceColor != this.pieceColor) {
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, null));
                        }
                    }
                }

                if (nextColRight < 9) {
                    // Forward right
                    if (board.getPiece(forwardRight) != null) {
                        // Check promotion row front right
                        if (board.getPiece(forwardRight).pieceColor != this.pieceColor && nextRow == 8) {
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, PieceType.QUEEN));
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, PieceType.BISHOP));
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, PieceType.KNIGHT));
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, PieceType.ROOK));
                        }
                        // Check forward right for enemy piece
                        else if (board.getPiece(forwardRight).pieceColor != this.pieceColor) {
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, null));
                        }
                    }
                }

            }
        }

        // Black move forward
        if (this.pieceColor == ChessGame.TeamColor.BLACK) {
            int nextRow = currentRow - 1;
            int nextColLeft = currentCol + 1;
            int nextColRight = currentCol - 1;
            int doubleMove = currentRow - 2;
            ChessPosition forwardPosition = new ChessPosition(nextRow, currentCol);
            ChessPosition doubleForward = new ChessPosition(doubleMove, currentCol);
            ChessPosition forwardLeft = new ChessPosition(nextRow, nextColLeft);
            ChessPosition forwardRight = new ChessPosition(nextRow, nextColRight);

            // Check board boundary
            if (nextRow > 0) {

                // Forward
                if (board.getPiece(forwardPosition) == null) {

                    // Check promotion row in front
                    if (nextRow == 1) {
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, PieceType.QUEEN));
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, PieceType.BISHOP));
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, PieceType.KNIGHT));
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, PieceType.ROOK));
                    }
                    // Otherwise, check if in starting position and no pieces in front
                    else if (currentRow == 7 && board.getPiece(doubleForward) == null) {
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, null));
                        possibleMoves.add(new ChessMove(myPosition, doubleForward, null));
                    }

                    // Otherwise, add front move
                    else {
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, null));
                    }
                }

                if (nextColLeft < 9) {
                    // Forward left
                    if (board.getPiece(forwardLeft) != null) {
                        // Check promotion row front left
                        if (board.getPiece(forwardLeft).pieceColor != this.pieceColor && nextRow == 1) {
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, PieceType.QUEEN));
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, PieceType.BISHOP));
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, PieceType.KNIGHT));
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, PieceType.ROOK));
                        }
                        // Check forward left for enemy piece
                        else if (board.getPiece(forwardLeft).pieceColor != this.pieceColor) {
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, null));
                        }
                    }
                }

                if (nextColRight > 1) {
                    // Forward right
                    if (board.getPiece(forwardRight) != null) {
                        // Check promotion row front right
                        if (board.getPiece(forwardRight).pieceColor != this.pieceColor && nextRow == 1) {
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, PieceType.QUEEN));
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, PieceType.BISHOP));
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, PieceType.KNIGHT));
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, PieceType.ROOK));
                        }
                        // Check forward right for enemy piece
                        else if (board.getPiece(forwardRight).pieceColor != this.pieceColor) {
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, null));
                        }
                    }
                }
            }
        }

        return possibleMoves;
    }
}
