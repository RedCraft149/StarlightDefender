package com.redcraft.starlight.server.packethandlers.out;

import com.redcraft.starlight.server.ServerListener;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.rlib.events.EventListener;
import com.redcraft.rlib.events.QuickEvent;

public class PlayerAmmoHandler extends ServerListener {
    @EventListener
    public void onAmmoChange(QuickEvent event) {
        if(!event.matches("ammo_changed")) return;
        server.send(
                StarlightDefenderPacketList.newPacket("player_ammo").set("ammo",event.readInt("ammo")),
                event.read("entity", Entity.class).getUUID()
        );
    }
}
