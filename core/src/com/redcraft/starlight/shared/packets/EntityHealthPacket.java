package com.redcraft.starlight.shared.packets;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.serial.ByteStringBuilder;
import com.redcraft.rlib.serial.ByteStringReader;

import java.util.UUID;

public class EntityHealthPacket implements Packet {

    public UUID uuid;
    public float newHealth;

    public EntityHealthPacket() {
    }

    public EntityHealthPacket(UUID uuid, float newHealth) {
        this.uuid = uuid;
        this.newHealth = newHealth;
    }

    @Override
    public int header() {
        return StarlightDefenderPacketList.ENTITY_HEALTH;
    }

    @Override
    public byte[] pack() {
        ByteStringBuilder builder = new ByteStringBuilder().begin(0,true);
        builder.append(uuid.getMostSignificantBits());
        builder.append(uuid.getLeastSignificantBits());
        builder.append(newHealth);
        return builder.end();
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteStringReader reader = new ByteStringReader();
        reader.use(bytes);
        uuid = new UUID(reader.readLong(),reader.readLong());
        newHealth = reader.readFloat();
    }

    @Override
    public Packet copy() {
        return new EntityHealthPacket(new UUID(uuid.getMostSignificantBits(),uuid.getLeastSignificantBits()),newHealth);
    }

}
