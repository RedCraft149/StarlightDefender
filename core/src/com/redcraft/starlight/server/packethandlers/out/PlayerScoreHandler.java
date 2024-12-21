package com.redcraft.starlight.server.packethandlers.out;

import com.redcraft.starlight.server.ServerListener;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.rlib.events.EventListener;
import com.redcraft.rlib.events.QuickEvent;

public class PlayerScoreHandler extends ServerListener {
    @EventListener
    public void onScoreChange(QuickEvent event) {
        if(!event.matches("score_changed")) return;
        server.send(
                StarlightDefenderPacketList.newPacket("player_score").set("score",event.readInt("score")),
                event.read("entity", Entity.class).getUUID()
        );
    }
}
