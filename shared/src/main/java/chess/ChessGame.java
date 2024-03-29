package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessGame.TeamColor teamTurn;
    private ChessBoard board = new ChessBoard();

    public ChessGame() {
        teamTurn = TeamColor.WHITE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.deepEquals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, board);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "teamTurn=" + teamTurn +
                ", board=" + board +
                '}';
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        HashSet<ChessMove> validMoves = new HashSet<>();
        ChessPiece pieceToMove = board.getPiece(startPosition);
        HashSet<ChessMove> possibleMoves = (HashSet<ChessMove>) pieceToMove.pieceMoves(board, startPosition);

        // Check each possible move to ensure it does not endanger the king
        for (ChessMove move : possibleMoves) {
            ChessGame nextBoardState = new ChessGame();
            nextBoardState.copyBoard(board);
            nextBoardState.board.addPiece(move.getEndPosition(), pieceToMove);
            nextBoardState.board.addPiece(move.getStartPosition(), null);
            if (!nextBoardState.isInCheck(pieceToMove.getTeamColor())) {
                validMoves.add(move);
            }
        }

        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece pieceToMove = board.getPiece(move.getStartPosition());

        if (pieceToMove.getTeamColor() != teamTurn) {
            throw new InvalidMoveException();
        }

        // Check for valid move and make it
        HashSet<ChessMove> validMoves = (HashSet<ChessMove>) validMoves(move.getStartPosition());
        boolean valid = false;
        for (ChessMove validMove : validMoves) {
            if (move.equals(validMove)) {

                valid = true;

                // Move the piece, checking if it is a pawn in the promotion area
                if (move.getPromotionPiece() != null) {
                    ChessPiece promotedPiece  = new ChessPiece(teamTurn, move.getPromotionPiece());
                    board.addPiece(move.getEndPosition(), promotedPiece);
                }
                else {
                    board.addPiece(move.getEndPosition(), new ChessPiece(teamTurn, pieceToMove.getPieceType()));
                }

                // Remove the piece from the last position by setting to null
                board.addPiece(move.getStartPosition(), null);

                // Change whose turn it is
                if (getTeamTurn() == TeamColor.WHITE) {
                    setTeamTurn(TeamColor.BLACK);
                }
                else {
                    setTeamTurn(TeamColor.WHITE);
                }

                break;
            }
        }

        if (!valid) {
            throw new InvalidMoveException();
        }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        // Check each tile on the board for the possible moves of opponent pieces
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                // If there isn't a piece, or the piece is the current team, continue to the next tile
                if ((board.getPiece(currentPosition) != null) && (board.getPiece(currentPosition).getTeamColor() != teamColor)) {

                    // Calculate all possible moves for an opponent piece
                    HashSet<ChessMove> possibleMoves = (HashSet<ChessMove>) board.getPiece(currentPosition).pieceMoves(board, currentPosition);
                    for (ChessMove move : possibleMoves) {
                        // If any of its moves ends at the King, then current team is in check
                        if (board.getPiece(move.getEndPosition()) != null) {
                                if (board.getPiece(move.getEndPosition()).equals(new ChessPiece(teamColor, ChessPiece.PieceType.KING))) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {

        // As mentioned in class, these conditions are effectively what determines a checkmate
        return isInCheck(teamColor) && isInStalemate(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {

        // For every tile, if there is a piece of the current turn, check its valid moves.
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                if (board.getPiece(currentPosition) != null) {
                    if (board.getPiece(currentPosition).getTeamColor() == teamColor) {
                        HashSet<ChessMove> validMoves = (HashSet<ChessMove>) validMoves(currentPosition);

                        // Will return whether there are any valid moves.
                        return validMoves.isEmpty();

                    }
                }
            }
        }

        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    /**
     * This function copies the current board into a new object for checking valid moves
     * @param currentBoard the current game's board state to be copied
     */
    public void copyBoard(ChessBoard currentBoard) {

        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                if (currentBoard.getPiece(currentPosition) != null) {
                    this.board.addPiece(currentPosition, currentBoard.getPiece(currentPosition));
                }
                else {
                    this.board.addPiece(currentPosition, null);
                }
            }
        }
    }
}
