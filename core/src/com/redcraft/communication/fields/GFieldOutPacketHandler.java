package com.redcraft.communication.fields;

import com.redcraft.communication.server.GServer;
import com.redcraft.rlib.events.EventListener;
import com.redcraft.rlib.events.Listener;

public class GFieldOutPacketHandler implements Listener {
    GServer<?> server;

    public GFieldOutPacketHandler(GServer<?> server) {
        this.server = server;
    }

    @EventListener
    public void onFieldChange(GFieldEvent event) {
        switch (event.getField().getCommunicationType()) {
            case ALL:
                server.sendToAll(event.getField().createPacket());
            case SELF:
                server.send(event.getField().createPacket(),event.getField().getOwner());
            case OTHERS:
                server.sendExcluding(event.getField().createPacket(), event.getField().getOwner());
        }
    }
}
