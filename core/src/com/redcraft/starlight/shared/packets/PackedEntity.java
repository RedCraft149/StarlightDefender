package com.redcraft.starlight.shared.packets;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.serial.ByteStringBuilder;
import com.redcraft.rlib.serial.ByteStringReader;

import java.util.UUID;

public class PackedEntity implements Packet {

    public static int SIZE_IN_BYTES = 28;

    public UUID uuid;
    public int type;
    public float x, y;

    public PackedEntity(UUID uuid, int type, float x, float y) {
        this.uuid = uuid;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public PackedEntity() {
    }

    @Override
    public int header() {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] pack() {
        ByteStringBuilder builder = new ByteStringBuilder();
        builder.begin(0, true);
        builder.append(uuid.getMostSignificantBits()).append(uuid.getLeastSignificantBits());
        builder.append(type);
        builder.append(x).append(y);
        return builder.end();
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteStringReader reader = new ByteStringReader();
        reader.use(bytes);
        uuid = new UUID(reader.readLong(), reader.readLong());
        type = reader.readInteger();
        x = reader.readFloat();
        y = reader.readFloat();
    }

    @Override
    public Packet copy() {
        return new PackedEntity(new UUID(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits()), type, x, y);
    }

    @Override
    public String toString() {
        return "PackedEntity{" +
                "uuid=" + uuid +
                ", type=" + type +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
