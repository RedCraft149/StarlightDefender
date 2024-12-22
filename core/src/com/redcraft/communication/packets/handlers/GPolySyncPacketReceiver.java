package com.redcraft.communication.packets.handlers;

import com.redcraft.communication.packets.GPacket;
import com.redcraft.rlib.Processable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GPolySyncPacketReceiver extends GPacketReceiver implements Processable {

    private final List<GPacket> recent;

    public GPolySyncPacketReceiver(int header, Object target) {
        super(header,target);
        recent = Collections.synchronizedList(new LinkedList<>());
    }
    public GPolySyncPacketReceiver(String header, Object target) {
        this(header.hashCode(),target);
    }

    public GPolySyncPacketReceiver(int header) {
        super(header);
        recent = Collections.synchronizedList(new LinkedList<>());
    }
    public GPolySyncPacketReceiver(String header) {
        this(header.hashCode());
    }

    protected void handle(GPacket packet) {
        reflector.reflect(packet);
    }

    @Override
    public void receive(GPacket packet) {
        synchronized (recent) {
            recent.add(packet);
        }
    }

    @Override
    public void process(float dt) {
        synchronized (recent) {
            if(recent.isEmpty()) return;
            for(GPacket packet : recent) handle(packet);
            recent.clear();
        }
    }
}
