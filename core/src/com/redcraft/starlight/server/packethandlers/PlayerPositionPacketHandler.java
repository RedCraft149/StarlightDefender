package com.redcraft.starlight.server.packethandlers;

import com.redcraft.communication.packets.handlers.SyncPacketReceiver;
import com.redcraft.starlight.server.SConstants;
import com.redcraft.starlight.server.elements.SPlayer;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.world.Universe;
import com.redcraft.communication.packets.GPacket;

import java.util.UUID;

public class PlayerPositionPacketHandler extends SyncPacketReceiver<GPacket> {

    Universe universe;
    UUID uuid;

    public PlayerPositionPacketHandler(UUID uuid) {
        super("player_position",null);
        universe = Shared.SERVER.get(SConstants.universe, Universe.class);
        this.uuid = uuid;
    }

    @Override
    public void handle(GPacket playerPositionPacket) {
        float x = playerPositionPacket.getFloat("x");
        float y = playerPositionPacket.getFloat("y");
        Entity e = universe.entityFinder().searchEntity(uuid,x,y);
        if(e==null) return;
        if(!(e instanceof SPlayer)) return;
        SPlayer player = (SPlayer) e;
        if(player.isDead()) return;
        e.setPosition(x,y);
    }
}
