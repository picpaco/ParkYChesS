package com.marcopiccionitraining.parkychess.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashMap;

public class MoveGeneratorStatistics {

    @Getter @Setter
    private long numberOfLeaves;
  //  private final HashMap<PlayerColor, HashMap<String, Long>> movesMap;
    private final HashMap<String, Long> blackMovesMap;
    private final HashMap<String, Long> whiteMovesMap;
    private final HashMap<Integer, Long> numberOfCapturesPerDepth;
    @Getter @Setter
    private long numberOfCaptures;
    @Getter @Setter
    private long numberOfEnPassantCaptures;
    @Getter @Setter
    private long numberOfChecks;
    @Getter @Setter
    private long numberOfDiscoveryChecks;
    @Getter @Setter
    private long numberOfDoubleChecks;
    @Getter @Setter
    private long numberOfCheckmates;
    @Getter @Setter
    private long numberOfStalemates;
    @Getter @Setter
    private long numberOfCastles;
    @Getter @Setter
    private long numberOfPromotions;

    public MoveGeneratorStatistics(){
        numberOfCapturesPerDepth = new HashMap<>();
        blackMovesMap = new HashMap<>();
        whiteMovesMap = new HashMap<>();
  //    HashMap<String, Long> whiteMovesMap = new HashMap<>();
    //    movesMap.put(PlayerColor.WHITE, whiteMovesMap);
    //    HashMap<String, Long> blackMovesMap = new HashMap<>();
    //    movesMap.put(PlayerColor.BLACK, blackMovesMap);
    }
  /*  public void addMoveNodes(PlayerColor superKey, String key, Long numNodes){
        HashMap<String, Long> coloredMovesMap = movesMap.get(superKey);
        if (coloredMovesMap.containsKey(key)) {
            Long currentValue = coloredMovesMap.get(key);
            currentValue = currentValue + numNodes;
            coloredMovesMap.put(key, currentValue);
        } else {
            coloredMovesMap.put(key, numNodes);
        }
        movesMap.put(superKey, coloredMovesMap);
    } */

  //  private HashMap<String, Long> getMovesMap() {
  //      return movesMap;
  //  }

    public void addMoveNodes(PlayerColor mapColor, String key, Long numNodes){
        if (mapColor.equals(PlayerColor.BLACK)) {
            if (blackMovesMap.containsKey(key)) {
                Long currentValue = blackMovesMap.get(key);
                currentValue = currentValue + numNodes;
                blackMovesMap.put(key, currentValue);
            } else {
                blackMovesMap.put(key, numNodes);
            }
        }  else {
            if (whiteMovesMap.containsKey(key)) {
                Long currentValue = whiteMovesMap.get(key);
                currentValue = currentValue + numNodes;
                whiteMovesMap.put(key, currentValue);
            }  else {
                whiteMovesMap.put(key, numNodes);
            }
        }
    }

    public String getMovesMapAsString(PlayerColor mapColor){
        if (PlayerColor.BLACK.equals(mapColor)) {
            return blackMovesMap.toString();
        } else{
            return whiteMovesMap.toString();
        }
    }

    public void incrementNumberOfEnPassantCaptures(){
        numberOfEnPassantCaptures++;
    }

    public void incrementNumberOfChecks() {
        numberOfChecks++;
    }

    public void incrementNumberOfStalemates() {
        numberOfStalemates++;
    }

    public void incrementNumberOfDoubleChecks() {
        numberOfDoubleChecks++;
    }

    public void incrementNumberOfDiscoveryChecks() {
        numberOfDiscoveryChecks++;
    }

  //  public Set<Map.Entry<String, Long>> getEntrySet(PlayerColor color) {
   //         return ((movesMap.get(color)).entrySet());
   // }

    public Collection<Long> getMapValues(PlayerColor color) {
        if (color.equals(PlayerColor.WHITE)) {
            return whiteMovesMap.values();
        } else {
            return blackMovesMap.values();
        }
    }
      /*  if (color.equals(PlayerColor.WHITE)) {
            HashMap<String, Long> whiteMovesMap = movesMap.get(PlayerColor.WHITE);
            return whiteMovesMap.values();
        }  else {
            HashMap<String, Long> blackMovesMap = movesMap.get(PlayerColor.BLACK);
            return blackMovesMap.values();
        }
    }*/

    @Override
    public String toString() {
        return "Move Generator Statistics: " +
                "\nnumberOfLeafNodes="  + numberOfLeaves +
            //    "\nBlack moves map: " + getMapValues(PlayerColor.BLACK).toString() +
            //    "\nWhite moves map: " + getMapValues(PlayerColor.WHITE).toString() +
                "\nblackMovesMap=" + blackMovesMap +
                "\nwhiteMovesMap=" + whiteMovesMap +
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

 /*   public long getNumberOfDepthRelatedLeaves(int currentDepth) {
        if (whiteMovesMap.get(currentDepth) == null) {
            return 0;
        }
        return whiteMovesMap.get((long)currentDepth);
    }

  */
    public void incrementNumberOfCheckmates() {
        numberOfCheckmates++;
    }

    public void incrementNumberOfCaptures() {
        numberOfCaptures++;
    }

    public void resetNumberOfCaptures() {
        numberOfCaptures = 0;
    }

    public void resetNumberOfCapturesAtDepth(int currentDepth) {
        numberOfCapturesPerDepth.put(currentDepth, 0L);
    }

    public void decrementNumberOfChecks() {
        numberOfChecks--;
    }
}