package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.ObjectFactory;
import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;

public class Knight extends Piece {
    private final Logger LOGGER = LoggerFactory.getLogger(Knight.class);

    public Knight(PlayerColor playerColor){
        super(playerColor);
        setName(PieceName.KNIGHT);
    }

    // private Collection<Optional<Position>> getPotentialDestinationPositions(Position from){
    private Collection<Position> getPotentialDestinationPositions(Position from){
    //    LOGGER.trace("Entering getPotentialDestinationPositions (from = {}", from);
      //  Collection<Optional<Position>> allJumpSquares = new ArrayList<>();
        Collection<Position> allJumpSquares = new ArrayList<>();
    //    Optional<Position> oneStepNorth = from.stepTowardsDirection(Direction.NORTH);
        Position oneStepNorth = from.stepTowardsDirection(Direction.NORTH);
     //   Optional<Position> oneStepSouth = from.stepTowardsDirection(Direction.SOUTH);
        Position oneStepSouth = from.stepTowardsDirection(Direction.SOUTH);
     //   Optional<Position> oneStepEast = from.stepTowardsDirection(Direction.EAST);
        Position oneStepEast = from.stepTowardsDirection(Direction.EAST);
      //  Optional<Position> oneStepWest = from.stepTowardsDirection(Direction.WEST);
        Position oneStepWest = from.stepTowardsDirection(Direction.WEST);
        //oneStepNorth.ifPresent(position -> allJumpSquares.addAll(performKnightJump(position, Direction.NORTH)));
        if (oneStepNorth != null){
          //  position -> allJumpSquares.addAll(performKnightJump(position, Direction.NORTH));
            allJumpSquares.addAll(performKnightJump(oneStepNorth, Direction.NORTH));
        }
  //      oneStepSouth.ifPresent(position -> allJumpSquares.addAll(performKnightJump(position, Direction.SOUTH)));
  //      oneStepEast.ifPresent(position -> allJumpSquares.addAll(performKnightJump(position, Direction.EAST)));
  //      oneStepWest.ifPresent(position -> allJumpSquares.addAll(performKnightJump(position, Direction.WEST)));
        if (oneStepSouth != null){
            allJumpSquares.addAll(performKnightJump(oneStepSouth, Direction.SOUTH));
        }
        if (oneStepWest != null){
            allJumpSquares.addAll(performKnightJump(oneStepWest, Direction.WEST));
        }
        if (oneStepEast != null){
            allJumpSquares.addAll(performKnightJump(oneStepEast, Direction.EAST));
        }
        //    LOGGER.trace("Exiting getPotentialDestinationPositions(from = {})", from);
    //    LOGGER.trace("All jumping squares detected:{}", allJumpSquares);
        return allJumpSquares;
    }

    private Collection<Position> performKnightJump (Position step1, Direction towards){
    //    private Collection<Optional<Position>> performKnightJump (Position step1, Direction towards){
            assert step1 != null : " Step 1 must exist.";
    //    Collection<Optional<Position>> jumpSquares = new ArrayList<>();
        Collection<Position> jumpSquares = new ArrayList<>();
        //Optional<Position> step1Twice = step1.stepTowardsDirection(towards);
        Position step1Twice = step1.stepTowardsDirection(towards);
        if (step1Twice != null && (towards.equals(Direction.NORTH) || towards.equals(Direction.SOUTH))){
            Position step1TwiceThenEast = step1Twice.stepTowardsDirection(Direction.EAST);
            Position step1TwiceThenWest = step1Twice.stepTowardsDirection(Direction.WEST);
            if (step1TwiceThenEast != null){
                jumpSquares.add(step1TwiceThenEast);
            }
            if (step1TwiceThenWest != null) {
                jumpSquares.add(step1TwiceThenWest);
            }
        } else {
            if (step1Twice != null && (towards.equals(Direction.EAST) || towards.equals(Direction.WEST))) {
                Position step1TwiceThenNorth = step1Twice.stepTowardsDirection(Direction.NORTH);
                Position step1TwiceThenSouth = step1Twice.stepTowardsDirection(Direction.SOUTH);
                if (step1TwiceThenNorth != null) {
                    jumpSquares.add(step1TwiceThenNorth);
                }
                if (step1TwiceThenSouth != null) {
                    jumpSquares.add(step1TwiceThenSouth);
                }
            }
        }
     /*   if (step1Twice.isPresent() && (towards.equals(Direction.NORTH) || towards.equals(Direction.SOUTH))) {
            Optional<Position> step1TwiceThenEast = step1Twice.get().stepTowardsDirection(Direction.EAST);
            Optional<Position> step1TwiceThenWest = step1Twice.get().stepTowardsDirection(Direction.WEST);
            if (step1TwiceThenEast.isPresent()) {
                jumpSquares.add(step1TwiceThenEast);
            }
            if (step1TwiceThenWest.isPresent()) {
                jumpSquares.add(step1TwiceThenWest);
            }
        } else {
            if (step1Twice.isPresent() && (towards.equals(Direction.EAST) || towards.equals(Direction.WEST))) {
                Optional<Position> step1TwiceThenNorth = step1Twice.get().stepTowardsDirection(Direction.NORTH);
                Optional<Position> step1TwiceThenSouth = step1Twice.get().stepTowardsDirection(Direction.SOUTH);
                if (step1TwiceThenNorth.isPresent()) {
                    jumpSquares.add(step1TwiceThenNorth);
                }
                if (step1TwiceThenSouth.isPresent()) {
                    jumpSquares.add(step1TwiceThenSouth);
                }
            }
        }*/
        return jumpSquares;
    }
    //private Collection<Optional<Position>> getLegalDestinationPositions(Position from, Board chessboard){
    private Collection<Position> getLegalDestinationPositions(Position from, Board chessboard){
    //    LOGGER.trace("Entering getLegalDestinationPositions, from = {} chessboard)", from);
        //Collection<Optional<Position>> tiles = new ArrayList<>();
        Collection<Position> tiles = new ArrayList<>();
        for (Position position : chessboard.getPotentialKnightDestinations(from)) {
   //     for (Position position : getPotentialDestinationPositions(from)) {
           // for (Optional<Position> position : getPotentialDestinationPositions(from)) {
         //   if (position != null && !(chessboard.isInside((position)))){
         //       continue;
         //   }
        //    assert position != null;
            if (chessboard.isEmpty(position)){
              //  if (position != null && chessboard.isEmpty(position)){
                tiles.add(position);
                continue;
            }
            if (!(chessboard.getPiece(position).getColor().equals(getColor()))){
            //    if (position != null && !(chessboard.getPiece(position).getColor().equals(getColor()))){
                tiles.add(position);
            }
       /*     if (position.isPresent() && !(chessboard.isInside(position.get()))) {
                continue;
            }
            if (position.isPresent() && chessboard.isEmpty(position.get())) {
                tiles.add(position);
                continue;
            }
            if (position.isPresent() && !(chessboard.getPiece(position.get()).getColor().equals(getColor()))){
                tiles.add(position);
            }*/
        }
    //    LOGGER.trace("Exiting getLegalDestinationPositions(from, chessboard). Tiles: {}", tiles);
        return tiles;
    }
    //TODO fix the getPseudoLegalMoves method name: legal or pseudo-legal? Legal in this case.
    @Override
    public Collection<Move> getPseudoLegalMoves(Position from, Board chessboard){
    //    LOGGER.trace("Entering getPseudoLegalMoves(from = {}, chessboard)", from);
        Collection<Move> moves = new ArrayList<>();
        for (Position to : getLegalDestinationPositions(from, chessboard)) {
       //     for (Optional<Position> to : getLegalDestinationPositions(from, chessboard)) {
        //if (to.isPresent()) {
            if (to != null) {
           //     StandardMove move = new StandardMove(from, to.get());
            //    StandardMove move = new StandardMove(from, to.get());
            //    moves.add(move);
                moves.add(new StandardMove(from, to));
            }
        }
    //    LOGGER.trace("Exiting getPseudoLegalMoves(Position from = {}, Board chessboard). Moves found: {}", from, moves);
        return moves;
    }
    @Override
    public Piece copy() {
        Knight knightCopy = new Knight(getColor());
        knightCopy.setHasMoved(hasMoved());
        return knightCopy;
    }
}