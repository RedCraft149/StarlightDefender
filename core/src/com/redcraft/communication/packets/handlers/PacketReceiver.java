package com.redcraft.communication.packets.handlers;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.function.Consumer;

public abstract class PacketReceiver<T extends Packet> implements PacketHandler<T> {

    private final int header;
    protected Consumer<T> target;

    public PacketReceiver(int header, Consumer<T> target) {
        this.header = header;
        this.target = target;
    }
    public PacketReceiver(String header, Consumer<T> target) {
        this(header.hashCode(),target);
    }
    public PacketReceiver(int header) {
        this.header = header;
    }
    public PacketReceiver(String header) {
        this.header = header.hashCode();
    }

    public void setTarget(Consumer<T> target) {
        this.target = target;
    }

    @Override
    public int header() {
        return header;
    }
}
