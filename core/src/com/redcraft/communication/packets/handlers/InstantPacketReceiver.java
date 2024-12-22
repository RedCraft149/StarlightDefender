package com.redcraft.communication.packets.handlers;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.function.Consumer;

public class InstantPacketReceiver<T extends Packet> extends PacketReceiver<T>{
    public InstantPacketReceiver(int header, Consumer<T> target) {
        super(header, target);
    }

    public InstantPacketReceiver(String header, Consumer<T> target) {
        super(header, target);
    }

    public InstantPacketReceiver(int header) {
        super(header);
    }
    public InstantPacketReceiver(String header) {
        super(header);
    }

    @Override
    public void receive(T t) {
        target.accept(t);
    }
}
