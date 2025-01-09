package com.redcraft.starlight.client.packethandlers;

import com.redcraft.communication.packets.handlers.PolySyncPacketReceiver;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.gui.DeathScreen;
import com.redcraft.starlight.client.elements.gui.LevelClearedScreen;
import com.redcraft.starlight.client.frames.ConnectedFrame;
import com.redcraft.starlight.client.frames.GameFrame;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.starlight.shared.packets.PauseTimerPacket;

public class PauseTimerHandler extends PolySyncPacketReceiver<PauseTimerPacket> {
    public PauseTimerHandler() {
        super(StarlightDefenderPacketList.PAUSE_TIMER,null);
    }

    @Override
    public void handle(PauseTimerPacket pauseTimerPacket) {
        ConnectedFrame frame = Shared.CLIENT.get(CComponents.connectedFrame, ConnectedFrame.class);
        frame.getChild("game").as(GameFrame.class).getOverlay().getElement("deathscreen", DeathScreen.class)
                .setRespawnTime(pauseTimerPacket.time());
        frame.getChild("game").as(GameFrame.class).getOverlay().getElement("level_cleared_screen", LevelClearedScreen.class)
                .setPauseTime(pauseTimerPacket.time());
    }
}
