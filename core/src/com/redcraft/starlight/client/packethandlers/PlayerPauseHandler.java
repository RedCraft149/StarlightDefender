package com.redcraft.starlight.client.packethandlers;

import com.redcraft.communication.packets.handlers.PolySyncPacketReceiver;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.effects.SoundSystem;
import com.redcraft.starlight.client.elements.CUniverse;
import com.redcraft.starlight.client.elements.gui.DeathScreen;
import com.redcraft.starlight.client.elements.gui.LevelClearedScreen;
import com.redcraft.starlight.client.frames.ConnectedFrame;
import com.redcraft.starlight.client.frames.GameFrame;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.events.HealthChangeEvent;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.starlight.shared.packets.PlayerPausePacket;
import com.redcraft.rlib.events.EventHandler;

public class PlayerPauseHandler extends PolySyncPacketReceiver<PlayerPausePacket> {
    public PlayerPauseHandler() {
        super(StarlightDefenderPacketList.PLAYER_DEATH,null);
    }

    @Override
    public void handle(PlayerPausePacket playerPausePacket) {
        ConnectedFrame frame = Shared.CLIENT.get(CComponents.connectedFrame, ConnectedFrame.class);
        if(playerPausePacket.resume()) {
            frame.getChild("game").resume();
            frame.getChild("game").as(GameFrame.class).getOverlay().getElement("deathscreen",DeathScreen.class).hide();
            frame.getChild("game").as(GameFrame.class).getOverlay().getElement("level_cleared_screen",LevelClearedScreen.class).hide();
            Shared.CLIENT.get(CComponents.universe, CUniverse.class).getPlayer().setHealth(1f);
            return;
        } else {
            frame.getChild("game").pause();
            frame.getChild("game").as(GameFrame.class).getController().reset();
        }
        if(!playerPausePacket.levelCleared()) {
            frame.getChild("game").as(GameFrame.class).getOverlay().getElement("deathscreen",DeathScreen.class).show();
            Shared.CLIENT.get(CComponents.gameEventHandler, EventHandler.class)
                    .throwEvent(new HealthChangeEvent(Shared.CLIENT.get(CComponents.universe, CUniverse.class).getPlayer(), 0f,0f));
            System.out.println("paused, player died");
            Shared.CLIENT.get(CComponents.soundSystem, SoundSystem.class).play("death",1f);
        } else {
            frame.getChild("game").as(GameFrame.class).getOverlay().getElement("level_cleared_screen", LevelClearedScreen.class).show();
        }
    }
}
