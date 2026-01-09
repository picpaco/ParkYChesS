package com.marcopiccionitraining.parkychess.model;

import org.jetbrains.annotations.NotNull;

public record Direction (int rowDelta, int columnDelta) {

    public static final Direction NORTH = new Direction(-1, 0);
    public static final Direction SOUTH = new Direction(1, 0);
    public static final Direction EAST = new Direction(0, 1);
    public static final Direction WEST = new Direction(0, -1);
    public static final Direction NORTH_EAST = NORTH.add(EAST);
    public static final Direction NORTH_WEST = NORTH.add(WEST);
    public static final Direction SOUTH_EAST = SOUTH.add(EAST);
    public static final Direction SOUTH_WEST = SOUTH.add(WEST);

    public Direction add (Direction other) {
        return new Direction(rowDelta + other.rowDelta,
                columnDelta + other.columnDelta);
    }

@NotNull
@Override
    public String toString(){
        if (this.equals(Direction.SOUTH)) return "SOUTH";
        if (this.equals(Direction.SOUTH_EAST)) return "SOUTH_EAST";
        if (this.equals(Direction.SOUTH_WEST)) return "SOUTH_WEST";
        if (this.equals(Direction.NORTH)) return "NORTH";
        if (this.equals(Direction.NORTH_EAST)) return "NORTH_EAST";
        if (this.equals(Direction.NORTH_WEST)) return "NORTH_WEST";
        if (this.equals(Direction.EAST)) return "EAST";
        if (this.equals(Direction.WEST)) return "WEST";
        throw new RuntimeException("Invalid direction was used for toString().");


 /*   return switch (this) {
        case SOUTH -> "SOUTH";
        case NORTH -> "NORTH";
        case EAST -> "EAST";
        case WEST -> "WEST";
        default -> "ERRONEOUS OR MISSING DIRECTION";
    };
  */
}
}

