package com.redcraft.communication.fields;

import com.redcraft.communication.packets.handlers.PolySyncPacketReceiver;

public class GFieldInPacketHandler extends PolySyncPacketReceiver<GFieldPacket> {
    GFieldRegistry registry;

    public GFieldInPacketHandler(GFieldRegistry registry) {
        super("internal:gfield_packet", null);
        setTarget(this::handle);
        this.registry = registry;
    }

    public void handle(GFieldPacket packet) {
        GField<?> field = registry.getField(packet.getOwner(),packet.getName());
        if(field != null) field.set(packet);
    }
}
