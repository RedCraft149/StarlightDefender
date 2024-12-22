package com.redcraft.rlib;

public interface Castable<T> {
    default <R extends T> R as(Class<R> type) {
        return type.cast(this);
    }
    default <R> R to(Class<R> type) {
        return type.cast(this);
    }
    default <R> boolean is(Class<R> type) {
        return type.isInstance(this);
    }
}
