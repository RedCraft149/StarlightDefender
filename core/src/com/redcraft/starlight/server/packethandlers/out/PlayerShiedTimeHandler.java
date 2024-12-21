package com.redcraft.starlight.server.packethandlers.out;

import com.redcraft.starlight.server.ServerListener;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.rlib.events.EventListener;
import com.redcraft.rlib.events.QuickEvent;

public class PlayerShiedTimeHandler extends ServerListener {
    @EventListener
    public void onEvent(QuickEvent event) {
        if(!event.matches("player_shield_time")) return;
        server.send(StarlightDefenderPacketList.newPacket("player_shield_time")
                        .set("time",event.readFloat("time"))
                        .set("active",event.readBoolean("active"))
                ,event.read("entity", Entity.class).getUUID());
    }
}
