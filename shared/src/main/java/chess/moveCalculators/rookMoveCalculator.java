package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class rookMoveCalculator extends chessMoveCalculator {

    public static Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        // Check all straight directions for as long as there are possible moves
        upPath(board, myPosition, pieceColor, currentRow, currentCol, possibleMoves);
        rightPath(board, myPosition, pieceColor, currentRow, currentCol, possibleMoves);
        downPath(board, myPosition, pieceColor, currentRow, currentCol, possibleMoves);
        leftPath(board, myPosition, pieceColor, currentRow, currentCol, possibleMoves);

        return possibleMoves;
    }
}
