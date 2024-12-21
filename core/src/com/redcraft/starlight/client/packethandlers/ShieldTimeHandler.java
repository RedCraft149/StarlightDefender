package com.redcraft.starlight.client.packethandlers;

import com.redcraft.communication.packets.handlers.GPolySyncPacketReceiver;
import com.redcraft.communication.packets.handlers.GSyncPacketReceiver;
import com.redcraft.communication.packets.handlers.Receiver;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.CUniverse;
import com.redcraft.starlight.client.elements.gui.ProgressBar;
import com.redcraft.starlight.client.frames.ConnectedFrame;
import com.redcraft.starlight.client.frames.GameFrame;
import com.redcraft.starlight.shared.Shared;

public class ShieldTimeHandler extends GPolySyncPacketReceiver {
    public ShieldTimeHandler() {
        super("player_shield_time");
        setTarget(this);
    }

    @Receiver(fields = {"time","active"})
    public void handle(float time, boolean active) {
        ConnectedFrame frame = Shared.CLIENT.get(CComponents.connectedFrame, ConnectedFrame.class);
        GameFrame gameFrame = (GameFrame) frame.getChild("game");
        gameFrame.getOverlay().getElement("shield", ProgressBar.class).setProgress(time * 0.1f);

        CUniverse universe = Shared.CLIENT.get(CComponents.universe,CUniverse.class);
        universe.getPlayer().setShieldsUp(active);
    }
}
