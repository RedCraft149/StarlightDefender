package com.redcraft.starlight.shared.packets;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.serial.ByteStringBuilder;
import com.redcraft.rlib.serial.ByteStringReader;

import java.util.UUID;

public class EntityPositionPacket implements Packet {
    public float x;
    public float y;
    public UUID uuid;

    public EntityPositionPacket() {
    }

    public EntityPositionPacket(float x, float y, UUID uuid) {
        this.x = x;
        this.y = y;
        this.uuid = uuid;
    }

    @Override
    public int header() {
        return StarlightDefenderPacketList.ENTITY_POSITION;
    }

    @Override
    public byte[] pack() {
        ByteStringBuilder builder = new ByteStringBuilder();
        builder.begin(0,true);
        builder.append(x).append(y);
        builder.append(uuid.getMostSignificantBits()).append(uuid.getLeastSignificantBits());
        return builder.end();
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteStringReader reader = new ByteStringReader().use(bytes);
        x = reader.readFloat();
        y = reader.readFloat();
        uuid = new UUID(reader.readLong(),reader.readLong());
    }

    @Override
    public Packet copy() {
        return new EntityPositionPacket(x,y,uuid);
    }

}
