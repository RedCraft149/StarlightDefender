package com.redcraft.starlight.server.packethandlers.out;

import com.redcraft.starlight.server.ServerListener;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.rlib.events.EventListener;
import com.redcraft.rlib.events.QuickEvent;

public class PlayerShieldHitHandler extends ServerListener {
    @EventListener
    public void onEvent(QuickEvent event) {
        if(!event.matches("player_shield_hit")) return;
        server.send(StarlightDefenderPacketList.newPacket("player_shield_hit")
                .set("x",event.readFloat("x"))
                .set("y",event.readFloat("y"))
                .set("time",event.readFloat("time")),
                event.read("entity", Entity.class).getUUID());
    }
}
