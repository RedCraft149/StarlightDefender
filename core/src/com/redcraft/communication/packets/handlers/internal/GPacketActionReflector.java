package com.redcraft.communication.packets.handlers.internal;

import com.redcraft.communication.packets.GPacket;
import com.redcraft.communication.packets.handlers.GPacketAction;
import com.redcraft.rlib.actions.Actions;

public class GPacketActionReflector implements GPacketReflector {

    GPacketAction packetAction;

    public GPacketActionReflector(GPacketAction packetAction) {
        this.packetAction = packetAction;
    }

    @Override
    public boolean reflect(GPacket packet) {
        Object[] args = reflectArguments(packet);
        Actions.run(packetAction.getAction(),args);
        return true;
    }

    private Object[] reflectArguments(GPacket packet) {
        Object[] args = new Object[packetAction.getLength()];
        for(int i = 0; i < packetAction.getLength(); i++) {
            args[i] = packet.get(packetAction.getIndex(i));
        }
        return args;
    }
}
