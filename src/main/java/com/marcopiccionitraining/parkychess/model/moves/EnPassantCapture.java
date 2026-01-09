package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.FENStateString;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.Position;
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
    private Position capturedPawnGhostSquare;
    @Getter
    private Position capturePosition;

    public EnPassantCapture (Position from, Position to){
        assert from != null : "The from position must exist.";
        assert to != null : "The to position must exist.";
        setFrom (from);
        setTo (to);
        capturedPawnGhostSquare = new Position(from.row(), to.column());
        setName (MoveNames.EN_PASSANT_CAPTURE);
        capturePosition = new Position(from.row(), to.column());
        log.trace ("An EnPassant capture Move has just been created from {} to {}.", from, to);
    }

    @Override
    public boolean execute (@NonNull Board chessboard) {
        assert chessboard.getPiece(getFrom()) != null : "The pawn on 'from' square must exist.";
        assert chessboard.isEmpty(getTo()) : "The 'to' square must be empty.";
        //chessboard.getGameStateContainer().peek().setMovedPiece(chessboard.getPiece(getFrom()));
        capturedPawnGhostSquare = new Position(getFrom().row(), getTo().column());
        log.debug("capturedPawnGhostSquare: {}", capturedPawnGhostSquare);
        log.debug("Last executed move: {}",chessboard.getLastExecutedMove());
        log.debug("{} moves:  {}", chessboard.getCurrentPlayerColor(), chessboard);
        assert !(chessboard.isEmpty(capturedPawnGhostSquare)): "The pawn on " + capturedPawnGhostSquare +
                " must exist.";
        new StandardMove(getFrom(), getTo()).execute(chessboard);
        assert (chessboard.getPiece(getTo()) != null) : "The piece just set must exist.";
        Piece capturedPawn = chessboard.getPiece(capturedPawnGhostSquare);
        assert capturedPawn != null : "The captured pawn must exist.";
     //   if (!(chessboard.getGameStateContainer().isEmpty())) {
     //       chessboard.getGameStateContainer().peek().setCapturedPiece(capturedPawn);
      //  }
        chessboard.setEmpty(getFrom());
        chessboard.setEmpty(capturedPawnGhostSquare);
        log.debug("Position after executing en passant capture move {}: {}", this, chessboard);
//        chessboard.getPiece(capturePosition).setPosition(null);
        assert chessboard.isEmpty(getFrom()) : "The from position should be empty.";
        //        chessboard.getPiece(getFrom()).setPosition(null);
        return true;
    }

    @Override
    public void undo (@NonNull Board chessboard, @NonNull FENStateString oldGameState) throws  IllegalStateException {
      //  log.debug("moved piece: {}", oldGameState.getMovedPiece());
      //  chessboard.setPiece(oldGameState.getMovedPiece(), getFrom());
        chessboard.setPiece(chessboard.getPiece(oldGameState.getLastExecutedMove().getTo()), getFrom());
     //   log.debug("captured piece: {}", oldGameState.getCapturedPiece());
     //   log.debug("I about to set {} on {}", oldGameState.getCapturedPiece(),
     //           oldGameState.getCapturedGhostPawnSquare());
        //FIXME find an alternative way to get the captured piece and then remove the variable.
        chessboard.setPiece(chessboard.getPiece(oldGameState.getCapturedGhostPawnSquare()), getTo());
        chessboard.setEmpty(oldGameState.getLastExecutedMove().getTo());
        log.debug("I have just set square {} to empty", oldGameState.getLastExecutedMove().getTo());
    }
}