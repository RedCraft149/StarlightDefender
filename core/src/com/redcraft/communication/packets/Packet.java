package com.redcraft.communication.packets;

/**
 * Represents a piece of data that can be sent by a {@link com.redcraft.communication.channels.Channel}.
 */
public interface Packet {

    //public Packet() {}
    //public Packet(...) {...}

    int header();
    //Indirect
    byte[] pack();
    void unpack(byte[] bytes);

    //Direct
    Packet copy();
}
