package com.redcraft.communication.packets.handlers;

import com.redcraft.communication.packets.GPacket;
import com.redcraft.communication.packets.handlers.internal.GPacketActionReflector;
import com.redcraft.communication.packets.handlers.internal.GPacketMethodReflector;
import com.redcraft.communication.packets.handlers.internal.GPacketReflector;

public abstract class GPacketReceiver implements PacketHandler<GPacket> {
    private final int header;
    protected GPacketReflector reflector;
    public GPacketReceiver(int header, Object target) {
        this.header = header;
        setTarget(target);
    }

    public GPacketReceiver(int header) {
        this.header = header;
    }

    public void setTarget(Object target) {
        reflector = target instanceof GPacketAction ?
                new GPacketActionReflector((GPacketAction) target) : new GPacketMethodReflector(target);
    }

    @Override
    public int header() {
        return header;
    }

    public static GInstantPacketReceiver instant(String header, Object target) {
        return new GInstantPacketReceiver(header,target);
    }
    public static GSyncPacketReceiver sync(String header, Object target) {
        return new GSyncPacketReceiver(header,target);
    }
    public static GPolySyncPacketReceiver poly(String header, Object target) {
        return new GPolySyncPacketReceiver(header,target);
    }
}
