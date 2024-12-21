package com.redcraft.starlight.client.packethandlers;

import com.redcraft.communication.packets.handlers.PolySyncPacketReceiver;
import com.redcraft.communication.packets.handlers.SyncPacketReceiver;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.effects.SoundSystem;
import com.redcraft.starlight.client.elements.CUniverse;
import com.redcraft.starlight.client.elements.gui.DeathScreen;
import com.redcraft.starlight.client.frames.ConnectedFrame;
import com.redcraft.starlight.client.frames.GameFrame;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.events.HealthChangeEvent;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.starlight.shared.packets.PlayerDeathPacket;
import com.redcraft.rlib.events.EventHandler;

public class PlayerDeathHandler extends PolySyncPacketReceiver<PlayerDeathPacket> {
    public PlayerDeathHandler() {
        super(StarlightDefenderPacketList.PLAYER_DEATH,null);
    }

    @Override
    public void handle(PlayerDeathPacket playerDeathPacket) {
        ConnectedFrame frame = Shared.CLIENT.get(CComponents.connectedFrame, ConnectedFrame.class);
        if(!playerDeathPacket.respawn()) {
            frame.getChild("game").pause();
            frame.getChild("game").as(GameFrame.class).getOverlay().getElement("deathscreen",DeathScreen.class).show();
            frame.getChild("game").as(GameFrame.class).getController().reset();
            Shared.CLIENT.get(CComponents.gameEventHandler, EventHandler.class)
                    .throwEvent(new HealthChangeEvent(Shared.CLIENT.get(CComponents.universe, CUniverse.class).getPlayer(), 0f,0f));
            System.out.println("paused, player died");
            Shared.CLIENT.get(CComponents.soundSystem, SoundSystem.class).play("death",1f);
        } else {
            frame.getChild("game").resume();
            frame.getChild("game").as(GameFrame.class).getOverlay().getElement("deathscreen",DeathScreen.class).hide();
            Shared.CLIENT.get(CComponents.universe, CUniverse.class).getPlayer().setHealth(1f);
        }
    }
}
