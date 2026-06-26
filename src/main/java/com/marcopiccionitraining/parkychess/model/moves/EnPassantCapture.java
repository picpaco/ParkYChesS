package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.pieces.Pawn;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnPassantCapture extends Move{
    /**
     * It is the 'to' position of the pawn that has just performed a double move
     * and can potentially be captured (by an opposite pawn positioned on its 5th adjacent row) as if
     * it would have moved one square only.
     */
    @Getter
    private final Position capturedPawnGhostPosition;
    @Getter
    private Pawn capturedPawn;
    @Getter
    private Move lastExecutedMove;

    public EnPassantCapture (@NonNull Position from, @NonNull Position to, @NonNull Board chessboard){
        assert !chessboard.isEmpty(from) : "The from position must not be empty.";
        assert chessboard.isEmpty(to) : "The to position must be empty.";
        assert chessboard.getPiece(from).getName().equals(PieceNames.PAWN) : "The capturing piece should be a pawn.";
        assert chessboard.getLastExecutedMove().getName().equals(MoveNames.DOUBLE_PAWN) :
                "Last executed move before an en passant capture should be a Double Pawn move.";
        log.error("Last executed move's 'to' position: {}; En passant move's 'to''s: {}",
                chessboard.getLastExecutedMove().getTo(), to);
//        assert (chessboard.getLastExecutedMove().getTo().equals(to)) :
  //              "Last executed move's 'to' position should match this en passant move 'to' position.";
        PlayerColor opponentColor = PlayerColor.getOpponentColor(chessboard.getCurrentPlayerColor());
        assert (chessboard.getPawnSkippedPosition(opponentColor).getColumn()) == to.getColumn() :
        "The column of the skipped position should be the same as the en passant capture position column";
        log.error("Last executed move: {}", chessboard.getLastExecutedMove());
        setFrom (from);
        setTo (to);
        setName(MoveNames.EN_PASSANT_CAPTURE);
        //FIXME
        if (opponentColor.equals(PlayerColor.BLACK)) {
            capturedPawnGhostPosition = new Position(chessboard.getPawnSkippedPosition(opponentColor).getRow() + 1,
                    chessboard.getPawnSkippedPosition(opponentColor).getColumn());
        } else {
            capturedPawnGhostPosition = new Position(chessboard.getPawnSkippedPosition(opponentColor).getRow() - 1,
                    chessboard.getPawnSkippedPosition(opponentColor).getColumn());
        }
        log.error("Captured Pawn Ghost Position: {}", capturedPawnGhostPosition);
        Pawn capturedPawn = (Pawn) chessboard.getPiece(capturedPawnGhostPosition);
        assert !chessboard.isEmpty(capturedPawnGhostPosition) : "There should be a pawn on " + capturedPawnGhostPosition + ".";
        log.error("{} has the move; captured pawn is {} and its color is {}; captured pawn ghost position is: {}",
                    chessboard.getCurrentPlayerColor(), capturedPawn, capturedPawn.getColor(), capturedPawnGhostPosition);
        log.debug("PawnSkippedPosition for {}: {}", opponentColor, to);
        log.debug("chessboard ready for executing en passant capture: {}", chessboard);
        assert !(capturedPawn.getColor().equals(chessboard.getCurrentPlayerColor())) :
        "The captured pawn's color should not match the current color.";
    }

    @Override
    public boolean execute (@NonNull Board chessboard){//, LocalGameState localGameState) {
        log.debug("About to execute an en passant capture from {} to {}...", getFrom(), getTo());
        assert !(chessboard.isEmpty(getFrom())) : "The piece on 'from' position must exist.";
        assert chessboard.getPiece(getFrom()).getName().equals(PieceNames.PAWN) :
                "The piece on 'from' position is a " + chessboard.getPiece(getFrom()).getName() +" instead of a pawn.";
        assert chessboard.isEmpty(getTo()) : "The 'to' position must be empty.";
        assert !chessboard.isEmpty(capturedPawnGhostPosition) : "capturedPawnGhostSquare cannot be empty.";
        assert chessboard.getPiece(capturedPawnGhostPosition).getName().equals(PieceNames.PAWN) :
                "The piece on capturedPawnGhostPosition must be a pawn.";
        //FIXME The container shouldn't be empty now
//        chessboard.getGameStateContainer().peek().setCapturedGhostPawnSquare(capturedPawnGhostPosition);
        log.debug("About to capture a pawn on ghost square: {}", capturedPawnGhostPosition);
        log.debug("{} Current position:  {}", chessboard.getCurrentPlayerColor(), chessboard);
        capturedPawn = (Pawn) chessboard.getPiece(capturedPawnGhostPosition);
        assert capturedPawn != null : "The pawn to capture must exist on " + capturedPawnGhostPosition;
        chessboard.setPiece(chessboard.getPiece(getFrom()), getTo());
        assert (!(chessboard.isEmpty(getTo()))) : "The piece just set must exist.";
        chessboard.setEmpty(getFrom().getRow(), getFrom().getColumn());
        chessboard.setEmpty(capturedPawnGhostPosition.getRow(), capturedPawnGhostPosition.getColumn());
        log.debug("Position after executing en passant capture move {}: {}", this, chessboard);
        assert chessboard.isEmpty(getFrom()) : "The from position should be empty.";
        lastExecutedMove = chessboard.getLastExecutedMove();
        return true;
    }

    @Override
    public void undo (@NonNull Board chessboard, LocalGameState localGameState) {
        assert chessboard.isEmpty(getFrom()) : "The from position must be empty,";
        assert !(chessboard.isEmpty(getTo())) : "The to position must contain a piece.";
        assert chessboard.getPiece(getTo()).getName().equals(PieceNames.PAWN) : "There must be a pawn on the to position";

        chessboard.setPiece(capturedPawn, capturedPawnGhostPosition);
        chessboard.setPiece(chessboard.getPiece(getTo()), getFrom());
        chessboard.setEmpty(getTo().getRow(), getTo().getColumn());
    }

    @Override
    public String toString(){
        return super.toString() + "ep";
    }
}