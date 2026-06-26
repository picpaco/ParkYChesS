package com.marcopiccionitraining.parkychess.model;

import java.util.Collection;

public interface EssentialStack <E> {
    E peek();
    E pop();
    void push(E item);
}