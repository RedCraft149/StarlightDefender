package com.redcraft.starlight.client.packethandlers;

import com.redcraft.communication.packets.handlers.PolySyncPacketReceiver;
import com.redcraft.communication.packets.handlers.SyncPacketReceiver;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.CEntity;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.packets.EntityRotationPacket;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.starlight.shared.world.Universe;

public class EntityRotationHandler extends PolySyncPacketReceiver<EntityRotationPacket> {
    public EntityRotationHandler() {
        super(StarlightDefenderPacketList.ENTITY_ROTATION,null);
    }

    @Override
    public void handle(EntityRotationPacket entityRotationPacket) {
        Universe universe = Shared.CLIENT.get(CComponents.universe, Universe.class);
        Entity entity = universe.entityFinder().searchEntity(entityRotationPacket.uuid);
        if(entity==null) return;
        if(!(entity instanceof CEntity)) return;
        CEntity c = (CEntity) entity;
        c.setRotationTarget(entityRotationPacket.rotation);
    }
}
