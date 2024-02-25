package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class bishopMoveCalculator extends chessMoveCalculator {

    public static Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        // Check all diagonal directions for as long as there are possible moves
        upAndLeftPath(board, myPosition, pieceColor, currentRow, currentCol, possibleMoves);
        upAndRightPath(board, myPosition, pieceColor, currentRow, currentCol, possibleMoves);
        downAndRightPath(board, myPosition, pieceColor, currentRow, currentCol, possibleMoves);
        downAndLeftPath(board, myPosition, pieceColor, currentRow, currentCol, possibleMoves);

        return possibleMoves;
    }
}
