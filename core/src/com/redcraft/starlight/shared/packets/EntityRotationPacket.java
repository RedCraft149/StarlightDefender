package com.redcraft.starlight.shared.packets;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.serial.ByteStringBuilder;
import com.redcraft.rlib.serial.ByteStringReader;

import java.util.UUID;

public class EntityRotationPacket implements Packet {

    public float rotation;
    public UUID uuid;

    public EntityRotationPacket() {
    }

    public EntityRotationPacket(float rotation, UUID uuid) {
        this.rotation = rotation;
        this.uuid = uuid;
    }

    @Override
    public int header() {
        return StarlightDefenderPacketList.ENTITY_ROTATION;
    }

    @Override
    public byte[] pack() {
        ByteStringBuilder builder = new ByteStringBuilder();
        builder.begin(0,true);
        builder.append(rotation);
        builder.append(uuid.getMostSignificantBits()).append(uuid.getLeastSignificantBits());
        return builder.end();
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteStringReader reader = new ByteStringReader().use(bytes);
        rotation = reader.readFloat();
        uuid = new UUID(reader.readLong(),reader.readLong());
    }

    @Override
    public Packet copy() {
        return new EntityRotationPacket(rotation,uuid);
    }
}
