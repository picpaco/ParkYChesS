package com.marcopiccionitraining.parkychess.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
public class MoveGeneratorStatistics {

    private long numberOfLeaves;
    public HashMap<String, Long> getMovesMap() {
        return movesMap;
    }
    private final HashMap<String, Long> movesMap;
 //   private final Logger LOGGER = LoggerFactory.getLogger(MoveGeneratorStatistics.class);
    private final HashMap<Integer, Long> numberOfCapturesPerDepth = new HashMap<>();
    private long numberOfCaptures;
    private long numberOfEnPassantCaptures;
    private long numberOfChecks;
    private long numberOfDiscoveryChecks;
    private long numberOfDoubleChecks;
    private long numberOfCheckmates;
    private long numberOfStalemates;
    private long numberOfCastles;
    private long numberOfPromotions;

    public MoveGeneratorStatistics(){
        movesMap = new HashMap<>();
    }
    public void addMoveNodes(String key, long numNodes){
        if (movesMap.containsKey(key)) {
            long currentValue = movesMap.get(key);
            currentValue = currentValue + numNodes;
            movesMap.put(key, currentValue);
        } else {
            movesMap.put(key, numNodes);
        }
    }
    public void incrementNumberOfEnPassantCaptures(){
        numberOfEnPassantCaptures++;
    //    LOGGER.debug("Just incremented numberOfEnPassantCaptures to {}", numberOfEnPassantCaptures);
    }
    public void setNumberOfLeaves(long numberOfLeaves) {
        this.numberOfLeaves = numberOfLeaves;
    }
    public long getNumberOfCaptures() {
        return numberOfCaptures;
    }
    public long getNumberOfEnPassantCaptures() {
        return  numberOfEnPassantCaptures;
    }
    public long getNumberOfChecks() {
        return numberOfChecks;
    }
    public void incrementNumberOfChecks() {
        numberOfChecks++;
     //   LOGGER.debug("Just incremented numberOfChecks to {}", numberOfChecks);
    }
    public void incrementNumberOfStalemates() {
        numberOfStalemates++;
  //      LOGGER.debug("Just incremented numberOfStalemates to {}", numberOfStalemates);
    }
    public long getNumberOfDiscoveryChecks() {
        return numberOfDiscoveryChecks;
    }
    public long getNumberOfDoubleChecks() {
        return numberOfDoubleChecks;
    }
    public void incrementNumberOfDoubleChecks() {
        numberOfDoubleChecks++;
    //    LOGGER.debug("Just incremented numberOfDoubleChecks to {}", numberOfDoubleChecks);
    }
    public void incrementNumberOfDiscoveryChecks() {
        numberOfDiscoveryChecks++;
      //  LOGGER.debug("Just incremented numberOfDiscoveryChecks to {}", numberOfDiscoveryChecks);
    }
    public long getNumberOfCheckmates() {
        return numberOfCheckmates;
    }
    public long getNumberOfStalemates() { return numberOfStalemates;}
    public long getNumberOfCastles() {
        return numberOfCastles;
    }
    public long getNumberOfPromotions() {
        return numberOfPromotions;
    }
    @Override
    public String toString() {
        return "MoveGeneratorStatistics: " +
                "\nnumberOfLeafNodes="  + numberOfLeaves +
                "\nmoves map: " + movesMap +
                "\nnumberOfCaptures=" + numberOfCaptures +
                ", numberOfEnPassantCaptures=" + numberOfEnPassantCaptures +
                ",\nnumberOfChecks=" + numberOfChecks +
                ", numberOfDiscoveryChecks=" + numberOfDiscoveryChecks +
                ", numberOfDoubleChecks=" + numberOfDoubleChecks +
                ", numberOfCheckmates=" + numberOfCheckmates +
                ", numberOfStalemates=" + numberOfStalemates +
                ",\nnumberOfCastles=" + numberOfCastles +
                ", numberOfPromotions=" + numberOfPromotions +
                '}';
    }
    public void addNumberOfCapturesPerDepth(Integer depth, Long numberOfCaptures) {
        numberOfCapturesPerDepth.put(depth, numberOfCaptures);
    }

//    public HashMap<Integer, Long> getNumberOfCapturesPerDepth() {
    //       return numberOfCapturesPerDepth;
    //   }

 //   public long getNumberOfDepthRelatedLeaves(int currentDepth) {
 //       if (movesMap.get((long)currentDepth) == null) {
 //           return 0;
 //       }
 //       return movesMap.get((long)currentDepth);
 //   }
    public void incrementNumberOfCheckmates() {
        numberOfCheckmates++;
      //  LOGGER.trace("Just incremented numberOfCheckmates to {}", numberOfCheckmates);

    }
    public void incrementNumberOfCaptures() {
        numberOfCaptures++;
     //   LOGGER.trace("Just incremented numberOfCaptures to {}", numberOfCaptures);
    }

    public void resetNumberOfCaptures() {
        numberOfCaptures = 0;
    }

    public void resetNumberOfCapturesAtDepth(int currentDepth) {
        numberOfCapturesPerDepth.put(currentDepth, 0L);
    }

    public void decrementNumberOfChecks() {
    //    LOGGER.trace("Just decremented number of checks to {}", numberOfChecks);
        numberOfChecks--;
    }
}