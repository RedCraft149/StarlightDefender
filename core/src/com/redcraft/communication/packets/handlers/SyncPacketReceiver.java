package com.redcraft.communication.packets.handlers;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.Processable;
import com.redcraft.rlib.function.Consumer;

public class SyncPacketReceiver<T extends Packet> extends PacketReceiver<T> implements Processable {

    private T recent;

    public SyncPacketReceiver(int header, Consumer<T> target) {
        super(header, target);
    }

    public SyncPacketReceiver(String header, Consumer<T> target) {
        super(header, target);
    }

    @Override
    public final void receive(T t) {
        recent = t;
    }

    protected void handle(T packet) {
        if(target != null) target.accept(packet);
    }

    @Override
    public void process(float dt) {
        if(recent != null) handle(recent);
        recent = null;
    }
}
