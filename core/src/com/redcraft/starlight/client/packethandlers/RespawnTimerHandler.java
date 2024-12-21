package com.redcraft.starlight.client.packethandlers;

import com.redcraft.communication.packets.handlers.PolySyncPacketReceiver;
import com.redcraft.communication.packets.handlers.SyncPacketReceiver;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.gui.DeathScreen;
import com.redcraft.starlight.client.frames.ConnectedFrame;
import com.redcraft.starlight.client.frames.GameFrame;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.starlight.shared.packets.RespawnTimerPacket;

public class RespawnTimerHandler extends PolySyncPacketReceiver<RespawnTimerPacket> {
    public RespawnTimerHandler() {
        super(StarlightDefenderPacketList.RESPAWN_TIMER,null);
    }

    @Override
    public void handle(RespawnTimerPacket respawnTimerPacket) {
        ConnectedFrame frame = Shared.CLIENT.get(CComponents.connectedFrame, ConnectedFrame.class);
        frame.getChild("game").as(GameFrame.class).getOverlay().getElement("deathscreen", DeathScreen.class)
                .setRespawnTime(respawnTimerPacket.time());
    }
}
