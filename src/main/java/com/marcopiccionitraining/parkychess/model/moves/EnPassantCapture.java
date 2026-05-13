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

    public EnPassantCapture (@NonNull Position from, @NonNull Position to, @NonNull Board chessboard){
        assert !chessboard.isEmpty(from) : "The from position must not be empty.";
        assert chessboard.isEmpty(to) : "The to position must be empty.";
        assert chessboard.getPiece(from).getName().equals(PieceNames.PAWN) : "The capturing piece should be a pawn.";

        setFrom (from);
        PlayerColor opponentColor = PlayerColor.getOpponentColor(chessboard.getPiece(from).getColor());
        setTo (chessboard.getPawnSkippedPosition(opponentColor));
        setName(MoveNames.EN_PASSANT_CAPTURE);
        if (opponentColor.equals(PlayerColor.BLACK)) {
            capturedPawnGhostPosition = new Position(chessboard.getPawnSkippedPosition(opponentColor).getRow() + 1,
                    chessboard.getPawnSkippedPosition(opponentColor).getColumn());
            log.debug("Attacking player is {}; captured pawn ghost position is: {}",
                    chessboard.getCurrentPlayerColor(), capturedPawnGhostPosition);
        } else {
            capturedPawnGhostPosition = new Position(chessboard.getPawnSkippedPosition(opponentColor).getRow() - 1,
                    chessboard.getPawnSkippedPosition(opponentColor).getColumn());
            log.debug("Attacking player is {}; capturedPawnGhostPosition is: {}",
                    chessboard.getCurrentPlayerColor(), capturedPawnGhostPosition);

        }
        log.debug("PawnSkippedPosition for {}: {}", opponentColor, chessboard.getPawnSkippedPosition(opponentColor));
        capturedPawn = (Pawn) chessboard.getPiece(capturedPawnGhostPosition);
        log.debug("capturedPawn: {}{}", capturedPawn, capturedPawnGhostPosition);
        assert chessboard.getPiece(capturedPawnGhostPosition).getColor().equals(opponentColor) :
        "The captured pawn's color should match the opponent color.";
    }

    @Override
    public boolean execute (@NonNull Board chessboard) {
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
        log.trace("{} Current position:  {}", chessboard.getCurrentPlayerColor(), chessboard);
     //   new StandardMove(getFrom(), getTo()).execute(chessboard);
        capturedPawn = (Pawn) chessboard.getPiece(capturedPawnGhostPosition);
        assert capturedPawn != null : "The pawn to capture must exist on " + capturedPawnGhostPosition;
        chessboard.setPiece(chessboard.getPiece(getFrom()), getTo());
        assert (!(chessboard.isEmpty(getTo()))) : "The piece just set must exist.";
        chessboard.setEmpty(getFrom());
        chessboard.setEmpty(capturedPawnGhostPosition);
        log.trace("Position after executing en passant capture move {}: {}", this, chessboard);
        assert chessboard.isEmpty(getFrom()) : "The from position should be empty.";
        return true;
    }

    @Override
    public void undo (@NonNull Board chessboard) {
     //   public void undo (@NonNull Board chessboard, @NonNull FENStateString oldGameState) {
        assert chessboard.isEmpty(getFrom()) : "The from position must be empty,";
        assert !(chessboard.isEmpty(getTo())) : "The to position must contain a piece.";
        assert chessboard.getPiece(getTo()).getName().equals(PieceNames.PAWN) : "There must be a pawn on the to position";

        chessboard.setPiece(capturedPawn, capturedPawnGhostPosition);
        chessboard.setPiece(chessboard.getPiece(getTo()), getFrom());
        chessboard.setEmpty(getTo());
    }

    @Override
    public String toString(){
        return super.toString() + "ep";
    }
}