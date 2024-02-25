package chess.moveCalculators;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

public class pawnMoveCalculator extends chessMoveCalculator {

    public static Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        whitePawnMoves(board, myPosition, currentRow, currentCol, possibleMoves, pieceColor);

        blackPawnMoves(board, myPosition, currentRow, currentCol, possibleMoves, pieceColor);

        return possibleMoves;
    }

    private static void blackPawnMoves(ChessBoard board, ChessPosition myPosition, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves, ChessGame.TeamColor pieceColor) {
        // Black move forward
        if (pieceColor == ChessGame.TeamColor.BLACK) {
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
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, ChessPiece.PieceType.QUEEN));
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, ChessPiece.PieceType.BISHOP));
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, ChessPiece.PieceType.KNIGHT));
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, ChessPiece.PieceType.ROOK));
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
                        if (board.getPiece(forwardLeft).getTeamColor() != pieceColor && nextRow == 1) {
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, ChessPiece.PieceType.QUEEN));
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, ChessPiece.PieceType.BISHOP));
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, ChessPiece.PieceType.KNIGHT));
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, ChessPiece.PieceType.ROOK));
                        }
                        // Check forward left for enemy piece
                        else if (board.getPiece(forwardLeft).getTeamColor() != pieceColor) {
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, null));
                        }
                    }
                }

                if (nextColRight >= 1) {
                    // Forward right
                    if (board.getPiece(forwardRight) != null) {
                        // Check promotion row front right
                        if (board.getPiece(forwardRight).getTeamColor() != pieceColor && nextRow == 1) {
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, ChessPiece.PieceType.QUEEN));
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, ChessPiece.PieceType.BISHOP));
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, ChessPiece.PieceType.KNIGHT));
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, ChessPiece.PieceType.ROOK));
                        }
                        // Check forward right for enemy piece
                        else if (board.getPiece(forwardRight).getTeamColor() != pieceColor) {
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, null));
                        }
                    }
                }
            }
        }
    }

    private static void whitePawnMoves(ChessBoard board, ChessPosition myPosition, int currentRow, int currentCol, HashSet<ChessMove> possibleMoves, ChessGame.TeamColor pieceColor) {
        // White move forward
        if (pieceColor == ChessGame.TeamColor.WHITE) {
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
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, ChessPiece.PieceType.QUEEN));
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, ChessPiece.PieceType.BISHOP));
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, ChessPiece.PieceType.KNIGHT));
                        possibleMoves.add(new ChessMove(myPosition, forwardPosition, ChessPiece.PieceType.ROOK));
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

                if (nextColLeft >= 1) {
                    // Forward left
                    if (board.getPiece(forwardLeft) != null) {
                        // Check promotion row front left
                        if (board.getPiece(forwardLeft).getTeamColor() != pieceColor && nextRow == 8) {
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, ChessPiece.PieceType.QUEEN));
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, ChessPiece.PieceType.BISHOP));
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, ChessPiece.PieceType.KNIGHT));
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, ChessPiece.PieceType.ROOK));
                        }
                        // Check forward left for enemy piece
                        else if (board.getPiece(forwardLeft).getTeamColor() != pieceColor) {
                            possibleMoves.add(new ChessMove(myPosition, forwardLeft, null));
                        }
                    }
                }

                if (nextColRight < 9) {
                    // Forward right
                    if (board.getPiece(forwardRight) != null) {
                        // Check promotion row front right
                        if (board.getPiece(forwardRight).getTeamColor() != pieceColor && nextRow == 8) {
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, ChessPiece.PieceType.QUEEN));
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, ChessPiece.PieceType.BISHOP));
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, ChessPiece.PieceType.KNIGHT));
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, ChessPiece.PieceType.ROOK));
                        }
                        // Check forward right for enemy piece
                        else if (board.getPiece(forwardRight).getTeamColor() != pieceColor) {
                            possibleMoves.add(new ChessMove(myPosition, forwardRight, null));
                        }
                    }
                }

            }
        }
    }
}
