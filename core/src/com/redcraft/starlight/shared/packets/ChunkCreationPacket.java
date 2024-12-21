package com.redcraft.starlight.shared.packets;

import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.serial.ByteStringBuilder;
import com.redcraft.rlib.serial.ByteStringReader;

public class ChunkCreationPacket implements Packet {

    public PackedEntity[] entities;
    public int index;

    public ChunkCreationPacket(int index, PackedEntity[] entities) {
        this.index = index;
        this.entities = entities;
    }

    public ChunkCreationPacket() {
        index = 0;
        entities = new PackedEntity[0];
    }

    @Override
    public int header() {
        return StarlightDefenderPacketList.CHUNK_CREATION;
    }

    @Override
    public byte[] pack() {
        ByteStringBuilder builder = new ByteStringBuilder();
        builder.begin(0, true);
        builder.append(index);
        builder.append(entities.length);
        for(PackedEntity entity : entities) {
            builder.append(entity.pack());
        }
        return builder.end();
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteStringReader reader = new ByteStringReader();
        reader.use(bytes);
        index = reader.readInteger();
        entities = new PackedEntity[reader.readInteger()];
        for(int i = 0; i < entities.length; i++) {
            entities[i] = new PackedEntity();
            entities[i].unpack(reader.readByteArray(PackedEntity.SIZE_IN_BYTES));
        }
    }

    @Override
    public Packet copy() {
        PackedEntity[] copy = new PackedEntity[entities.length];
        for(int i = 0; i < entities.length; i++) {
            copy[i] = (PackedEntity) entities[i].copy();
        }
        return new ChunkCreationPacket(index, copy);
    }



    public String toString() {
        return "ChunkCreationPacket(index="+index+", entities="+entities.length+")";
    }
}
