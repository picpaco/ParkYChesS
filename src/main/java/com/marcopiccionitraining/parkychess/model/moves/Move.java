package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.pieces.King;
import com.marcopiccionitraining.parkychess.model.pieces.Pawn;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class Move implements Command {
    @Getter @Setter
    private @NonNull Position from;
    @Getter @Setter
    private @NonNull Position to;
    @Getter @Setter
    private MoveNames name;
    @Getter @Setter
    private Piece capturedPiece;

    public boolean isLegal (@NonNull Board chessboard) {
        assert chessboard.getPiece(from) != null : "There must be a piece on the from square " + from;
        boolean isLegalFlag = true;
        LocalGameState localGameState = new LocalGameState(chessboard);
        execute(chessboard);
        assert !(chessboard.isEmpty(getTo())) : "There should be a piece on the {} square now." + to;
        Piece pieceMoved = chessboard.getPiece(to);
        log.debug("Checking if move {} is legal:", this);
        List<Position> opponentPiecesPositions = chessboard.getPiecesPositionsForColor(
                PlayerColor.getOpponentColor(pieceMoved.getColor()));
        log.debug("Get a list of the opponent pieces' positions: {}", opponentPiecesPositions);
        outerLoop:
        for (Position opponentPiecePosition : opponentPiecesPositions) {
            Piece opponentPiece = chessboard.getPiece(opponentPiecePosition);
            List<Move> opponentPiecePseudolegalMoves = new ArrayList<>();
            if (opponentPiece.getName().equals(PieceNames.PAWN)) {
                //TODO see if Pawn.canCaptureOpponentKing can be applied.
                Pawn opponentPawn = (Pawn)opponentPiece;
                opponentPiecePseudolegalMoves.addAll
                        (opponentPawn.getPseudolegalCaptureMovesAndPromotions(opponentPiecePosition, chessboard));
            } else {
                if (opponentPiece.getName().equals(PieceNames.KING)) {
                    //TODO see if king.canCaptureOpponentKing can be applied.
                    King opponentKing = (King) opponentPiece;
                    opponentPiecePseudolegalMoves.addAll
                            (opponentKing.getPseudoLegalMoves(opponentPiecePosition, chessboard));
                } else {
                    opponentPiecePseudolegalMoves.addAll
                            (opponentPiece.getPseudoLegalMoves(opponentPiecePosition, chessboard));
                }
            }
            log.debug("For opponent's piece {}, get its pseudolegal moves: {}, ",opponentPiece, opponentPiecePseudolegalMoves);
            log.info("Pawn advances of one and two steps are excluded because they are not captures.");
            for (Move opponentPieceMove : opponentPiecePseudolegalMoves) {
                log.debug("If the 'to' position of an opponent's pseudolegal move is the same as ");
                log.debug(" the friendly king's current position, then this is NOT a legal move.");
                log.debug("Analyzing {}'s move {} {} ", opponentPiece.getColor(), opponentPieceMove.name, opponentPieceMove);
                log.debug("{} king is in {}", pieceMoved.getColor(), chessboard.getKingPosition(pieceMoved.getColor()));
                if (opponentPieceMove.to.equals(chessboard.getKingPosition(pieceMoved.getColor()))) {
                        isLegalFlag = false;
                        break outerLoop;
                }
            }
        }
        undo (chessboard, localGameState);
        log.error("Move {} is {}", this, (isLegalFlag ? "legal." : "not legal."));
        return isLegalFlag;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Move move)) return false;
        return Objects.equals(from, move.from) && Objects.equals(to, move.to) && Objects.equals(name, move.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, name);
    }

    @Override
    public String toString() {
        return "" + from + to;
    }

    public abstract boolean execute (Board chessboard);

    public abstract void undo (Board chessboard, LocalGameState localGameState);
}