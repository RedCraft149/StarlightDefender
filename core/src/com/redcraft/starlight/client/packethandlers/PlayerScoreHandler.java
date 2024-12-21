package com.redcraft.starlight.client.packethandlers;

import com.redcraft.communication.packets.handlers.PolySyncPacketReceiver;
import com.redcraft.communication.packets.handlers.SyncPacketReceiver;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.CUniverse;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.communication.packets.GPacket;

public class PlayerScoreHandler extends PolySyncPacketReceiver<GPacket> {
    public PlayerScoreHandler() {
        super("player_score",null);
    }

    @Override
    public void handle(GPacket playerScorePacket) {
        CUniverse universe = Shared.CLIENT.get(CComponents.universe, CUniverse.class);
        universe.getPlayer().setScore(playerScorePacket.getInteger("score"));
    }
}
