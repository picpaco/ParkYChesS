package com.marcopiccionitraining.parkychess.model;

import lombok.NonNull;

public record Direction (int rowDelta, int columnDelta) {

    public static final Direction NORTH = new Direction(-1, 0);
    public static final Direction SOUTH = new Direction(1, 0);
    public static final Direction EAST = new Direction(0, 1);
    public static final Direction WEST = new Direction(0, -1);
    public static final Direction NORTH_EAST = NORTH.add(EAST);
    public static final Direction NORTH_WEST = NORTH.add(WEST);
    public static final Direction SOUTH_EAST = SOUTH.add(EAST);
    public static final Direction SOUTH_WEST = SOUTH.add(WEST);

    public Direction add (@NonNull Direction other) {
        return new Direction(rowDelta + other.rowDelta,
                columnDelta + other.columnDelta);
    }

@Override @NonNull
    public String toString(){
    @NonNull String result;
    if (this.equals(Direction.SOUTH)) {
        result = "SOUTH";
    } else if (this.equals(Direction.SOUTH_EAST)) {
        result = "SOUTH_EAST";
    } else if (this.equals(Direction.SOUTH_WEST)) {
        result = "SOUTH_WEST";
    } else if (this.equals(Direction.NORTH)) {
        result = "NORTH";
    } else if (this.equals(Direction.NORTH_EAST)) {
        result = "NORTH_EAST";
    } else if (this.equals(Direction.NORTH_WEST)) {
        result = "NORTH_WEST";
    } else if (this.equals(Direction.EAST)) {
        result = "EAST";
    } else if (this.equals(Direction.WEST)) {
        result = "WEST";
    } else {
        throw new RuntimeException("Invalid direction was used for toString().");
    }
    return result;
}
}
