package com.epherical.weaving.event;

public enum EventOrder {
    // Happens first
    FIRST,
    SECOND,
    THIRD,
    FOURTH,
    FIFTH,
    SIXTH,
    SEVENTH,
    MONITOR
    // Happens last, events should not change anything when listening on this level.

}
