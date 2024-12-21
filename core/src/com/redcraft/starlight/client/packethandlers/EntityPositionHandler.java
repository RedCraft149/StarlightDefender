package com.redcraft.starlight.client.packethandlers;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.communication.packets.handlers.PolySyncPacketReceiver;
import com.redcraft.communication.packets.handlers.SyncPacketReceiver;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.CEntity;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.packets.EntityPositionPacket;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.starlight.shared.world.Universe;

public class EntityPositionHandler extends PolySyncPacketReceiver<EntityPositionPacket> {
    public EntityPositionHandler() {
        super(StarlightDefenderPacketList.ENTITY_POSITION,null);
    }

    @Override
    public void handle(EntityPositionPacket entityPositionPacket) {
        Universe universe = Shared.CLIENT.get(CComponents.universe, Universe.class);
        Entity entity = universe.entityFinder().searchEntity(entityPositionPacket.uuid);
        if(entity==null) return;
        if(entity instanceof CEntity) {
            CEntity c = (CEntity) entity;
            c.setTarget(new Vector2(entityPositionPacket.x,entityPositionPacket.y));
        } else {
            entity.setPosition(entityPositionPacket.x,entityPositionPacket.y);
        }
    }
}
