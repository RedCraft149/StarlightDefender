package com.redcraft.starlight.server.packethandlers;

import com.redcraft.communication.packets.handlers.SyncPacketReceiver;
import com.redcraft.starlight.server.SConstants;
import com.redcraft.starlight.server.elements.SPlayer;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.starlight.shared.packets.PlayerRotationPacket;
import com.redcraft.starlight.shared.world.Universe;

import java.util.UUID;

public class PlayerRotationPacketHandler extends SyncPacketReceiver<PlayerRotationPacket> {

    Universe universe;
    UUID uuid;

    public PlayerRotationPacketHandler(UUID uuid) {
        super(StarlightDefenderPacketList.PLAYER_ROTATION,null);
        universe = Shared.SERVER.get(SConstants.universe, Universe.class);
        this.uuid = uuid;
    }

    @Override
    public void handle(PlayerRotationPacket playerRotationPacket) {
        Entity e = universe.entityFinder().searchEntity(uuid);
        if(e==null) return;
        if(!(e instanceof SPlayer)) return;
        SPlayer player = (SPlayer) e;
        if(player.isDead()) return;
        e.setRotation(playerRotationPacket.rotation());
    }
}
