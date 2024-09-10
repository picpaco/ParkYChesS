package com.marcopiccionitraining.parkychess.model;

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
}

