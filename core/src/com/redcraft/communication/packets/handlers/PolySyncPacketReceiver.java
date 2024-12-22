package com.redcraft.communication.packets.handlers;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.Processable;
import com.redcraft.rlib.function.Consumer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PolySyncPacketReceiver<T extends Packet> extends PacketReceiver<T> implements Processable {

    private final List<T> recent;

    public PolySyncPacketReceiver(int header, Consumer<T> target) {
        super(header, target);
        recent = Collections.synchronizedList(new LinkedList<>());
    }

    public PolySyncPacketReceiver(String header, Consumer<T> target) {
        this(header.hashCode(),target);
    }

    public PolySyncPacketReceiver(int header) {
        super(header);
        recent = Collections.synchronizedList(new LinkedList<>());
    }

    public PolySyncPacketReceiver(String header) {
        this(header.hashCode());
    }

    protected void handle(T packet) {
        target.accept(packet);
    }

    @Override
    public void receive(T t) {
        synchronized (recent) {
            recent.add(t);
        }
    }

    @Override
    public void process(float dt) {
        synchronized (recent) {
            if(recent.isEmpty()) return;
            for(T t : recent) handle(t);
            recent.clear();
        }
    }
}
