package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnPassantCapture extends Move{
    /**
     * capturedGhostPawnSquare is the 'to' position of the pawn that has just performed a double move
     * and can potentially be captured (by an opposite pawn positioned on its 5th adjacent rank) as if
     * it would have moved one square only.
     */
    @Getter
    private final Position capturedPawnGhostSquare;

    public EnPassantCapture (@NonNull Position from, @NonNull Position to, @NonNull Board chessboard){
        assert !chessboard.isEmpty(from) : "The from position must be not empty.";
        assert chessboard.isEmpty(to) : "The to position must be empty.";
        assert chessboard.getPiece(from) != null : "The pawn on the from square " + from + " must exist.";
        Position leftGhost = new Position(from.getRow(),from.getColumn() - 1);
        Position rightGhost = new Position(from.getRow(),from.getColumn() + 1);
        if (leftGhost.isInsideBoard() && !(chessboard.isEmpty(leftGhost))) {
            log.debug("Left ghost position {} for {} created.", leftGhost, chessboard.getCurrentPlayerColor());
            capturedPawnGhostSquare = leftGhost;
        } else {
            if ((rightGhost.isInsideBoard() && !(chessboard.isEmpty(rightGhost)))) {
                log.debug("Right ghost position {} for {} created.", rightGhost, chessboard.getCurrentPlayerColor());
                capturedPawnGhostSquare = rightGhost;
            } else {
                capturedPawnGhostSquare = null;
                assert false : "You shouldn't be here.";
            }
        }
        setFrom (from);
        setTo (to);
        setName(MoveNames.EN_PASSANT_CAPTURE);
    }

    @Override
    public boolean execute (@NonNull Board chessboard) {
        assert chessboard.getPiece(getFrom()) != null : "The pawn on 'from' square must exist.";
        assert getTo().isInsideBoard() : "The 'to' square must exist.";
        assert chessboard.isEmpty(getTo()) : "The 'to' square must be empty.";
        assert !chessboard.isEmpty(capturedPawnGhostSquare) : "There must be a pawn on capturedPawnGhostSquare " + capturedPawnGhostSquare;
        //chessboard.getGameStateContainer().peek().setMovedPiece(chessboard.getPiece(getFrom()));
        //FIXME The container shouldn't be empty now
        chessboard.getGameStateContainer().peek().setCapturedGhostPawnSquare(capturedPawnGhostSquare);
        log.debug("About to capture a pawn on ghost square: {}", capturedPawnGhostSquare);
        log.debug("{} Current position:  {}", chessboard.getCurrentPlayerColor(), chessboard);
        if (!chessboard.isEmpty(capturedPawnGhostSquare)) {
            new StandardMove(getFrom(), getTo()).execute(chessboard);
            assert (chessboard.getPiece(getTo()) != null) : "The piece just set must exist.";
            Piece pawnToCapture = chessboard.getPiece(capturedPawnGhostSquare);
            assert pawnToCapture != null : "The pawn to capture must exist on " + capturedPawnGhostSquare;
            if (!(chessboard.getGameStateContainer().isEmpty())) {
                chessboard.getGameStateContainer().peek().setCapturedPiece(pawnToCapture);
            }
            chessboard.setEmpty(getFrom());
            chessboard.setEmpty(capturedPawnGhostSquare);
            log.debug("Position after executing en passant capture move {}: {}", this, chessboard);
//        chessboard.getPiece(capturePosition).setPosition(null);
            assert chessboard.isEmpty(getFrom()) : "The from position should be empty.";
            //        chessboard.getPiece(getFrom()).setPosition(null);
            return true;
        } else {
            log.debug("capturedPawnGhostSquare {} was unexpectedly empty.", capturedPawnGhostSquare);
            return false;
        }
    }

    @Override
    public void undo (@NonNull Board chessboard, @NonNull FENStateString oldGameState) throws  IllegalStateException {
        chessboard.setPiece(chessboard.getPiece(oldGameState.getLastExecutedMove().getTo()), getFrom());
        Position capturedGhostPawnSquare = oldGameState.getCapturedGhostPawnSquare();
        chessboard.setPiece(oldGameState.getCapturedPiece(), capturedGhostPawnSquare);
        chessboard.setPiece(chessboard.getPiece(getTo()),getFrom());
        chessboard.setEmpty(getTo());
        log.debug("I have just set square {} to empty", oldGameState.getLastExecutedMove().getTo());
    }
}