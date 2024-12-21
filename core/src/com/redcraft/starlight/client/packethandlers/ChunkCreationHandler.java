package com.redcraft.starlight.client.packethandlers;

import com.redcraft.communication.packets.handlers.PolySyncPacketReceiver;
import com.redcraft.communication.packets.handlers.SyncPacketReceiver;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.CEntity;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.Entities;
import com.redcraft.starlight.shared.packets.ChunkCreationPacket;
import com.redcraft.starlight.shared.packets.PackedEntity;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.starlight.shared.world.Chunk;
import com.redcraft.starlight.shared.world.Universe;

public class ChunkCreationHandler extends PolySyncPacketReceiver<ChunkCreationPacket> {

    public ChunkCreationHandler() {
        super(StarlightDefenderPacketList.CHUNK_CREATION,null);
    }

    @Override
    public void handle(ChunkCreationPacket packet) {
        Chunk chunk = Shared.CLIENT.get(CComponents.universe, Universe.class).getChunk(packet.index);
        for(PackedEntity entity : packet.entities) {
            System.out.println(entity);
            CEntity obj = Entities.clientEntityFromType(entity.type,entity.uuid);
            if(obj == null) continue;
            obj.setPosition(entity.x,entity.y);
            chunk.addEntity(obj);
        }
    }
}
