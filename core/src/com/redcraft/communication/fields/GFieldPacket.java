package com.redcraft.communication.fields;

import com.redcraft.communication.packets.GUnnamedPacket;
import com.redcraft.communication.packets.Packet;
import com.redcraft.rlib.serial.ByteStringBuilder;
import com.redcraft.rlib.serial.ByteStringReader;

import java.lang.reflect.Array;
import java.util.UUID;

public class GFieldPacket implements Packet {

    private GUnnamedPacket data;
    private String name;
    private UUID owner;

    public GFieldPacket() {
    }

    public GFieldPacket(Object data, String name, UUID owner) {
        this.data = new GUnnamedPacket("data",1,new Class[]{data.getClass()}).set(0,data);
        this.name = name;
        this.owner = owner;
    }

    @Override
    public int header() {
        return "internal:gfield_packet".hashCode();
    }

    @Override
    public byte[] pack() {
        ByteStringBuilder builder = new ByteStringBuilder().begin(0,true);

        byte[] nameBytes = name.getBytes();
        builder.append(nameBytes.length);
        builder.append(nameBytes);

        builder.append(owner.getMostSignificantBits());
        builder.append(owner.getLeastSignificantBits());

        byte[] dataBytes = data.pack();
        builder.append(dataBytes.length);
        builder.append(resolveDataType(data.getType(0)));
        builder.append(dataBytes);

        return builder.end();
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteStringReader reader = new ByteStringReader().use(bytes);
        int nameLength = reader.readInteger();
        name = new String(reader.readByteArray(nameLength));
        owner = new UUID(reader.readLong(),reader.readLong());

        int dataLength = reader.readInteger();
        int dataType = reader.readInteger();
        byte[] tmp = reader.readByteArray(dataLength);
        data = new GUnnamedPacket("data",1,new Class[]{resolveDataClass(dataType)});
        data.unpack(tmp);
    }

    private int resolveDataType(Class<?> clazz) {
        if(clazz == boolean.class) return 1;
        if(clazz == byte.class) return 2;
        if(clazz == short.class) return 3;
        if(clazz == int.class) return 4;
        if(clazz == long.class) return 5;
        if(clazz == float.class) return 6;
        if(clazz == double.class) return 7;
        if(clazz == char.class) return 8;
        if(clazz == UUID.class) return 9;
        if(clazz == String.class) return 10;
        if(clazz.isArray()) return 0x10 | resolveDataType(clazz.getComponentType());
        return 0;
    }
    private Class<?> resolveDataClass(int type) {
        if((type & 0x10) != 0) {
            return Array.newInstance(resolveDataClass(type & 0xF),0).getClass();
        }

        switch (type) {
            case 1: return boolean.class;
            case 2: return byte.class;
            case 3: return short.class;
            case 4: return int.class;
            case 5: return long.class;
            case 6: return float.class;
            case 7: return double.class;
            case 8: return char.class;
            case 9: return UUID.class;
            case 10: return String.class;
            default: return void.class;
        }
    }

    public GUnnamedPacket getData() {
        return data;
    }

    public Object getContent() {
        return data.get(0);
    }
    public String getName() {
        return name;
    }
    public UUID getOwner() {
        return owner;
    }


    @Override
    public Packet copy() {
        return new GFieldPacket(data == null ? null : data.copy(),name,new UUID(owner.getMostSignificantBits(),owner.getLeastSignificantBits()));
    }
}
