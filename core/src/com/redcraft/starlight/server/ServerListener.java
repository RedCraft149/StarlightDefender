package com.redcraft.starlight.server;

import com.redcraft.communication.server.GServer;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.events.EntityEvent;
import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.events.Listener;

public class ServerListener implements Listener {
    protected GServer<?> server;

    public ServerListener() {
        server = Shared.SERVER.get(SConstants.server,GServer.class);
    }

    public void send(EntityEvent target, Packet packet) {
        server.send(packet,target.getEntity().getUUID());
    }

    public void sendExcluding(EntityEvent target, Packet packet) {
        server.sendExcluding(packet,target.getEntity().getUUID());
    }

    public void send(Packet packet) {
        server.sendToAll(packet);
    }
}
