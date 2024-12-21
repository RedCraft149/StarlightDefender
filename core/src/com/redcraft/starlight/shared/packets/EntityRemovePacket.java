package com.redcraft.starlight.shared.packets;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.serial.ByteStringBuilder;
import com.redcraft.rlib.serial.ByteStringReader;

import java.util.UUID;

public class EntityRemovePacket implements Packet {
    public UUID uuid;
    /**
     * X and Y position of the entity to speed up entity searching.
     */
    public float x, y;

    public EntityRemovePacket(UUID uuid, float x, float y) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
    }

    public EntityRemovePacket() {
    }

    @Override
    public int header() {
        return StarlightDefenderPacketList.ENTITY_REMOVE;
    }

    @Override
    public byte[] pack() {
        ByteStringBuilder builder = new ByteStringBuilder();
        builder.begin(0,true);
        builder.append(uuid.getMostSignificantBits());
        builder.append(uuid.getLeastSignificantBits());
        builder.append(x);
        builder.append(y);
        return builder.end();
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteStringReader reader = new ByteStringReader();
        reader.use(bytes);
        uuid = new UUID(reader.readLong(), reader.readLong());
        x = reader.readFloat();
        y = reader.readFloat();
    }

    @Override
    public Packet copy() {
        return new EntityRemovePacket(new UUID(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits()), x, y);
    }

}
