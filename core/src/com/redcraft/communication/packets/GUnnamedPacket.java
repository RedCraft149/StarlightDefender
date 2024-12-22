package com.redcraft.communication.packets;

import com.redcraft.rlib.function.Supplier;
import com.redcraft.rlib.serial.ByteStringBuilder;
import com.redcraft.rlib.serial.ByteStringReader;
import com.redcraft.rlib.serial.TypeTranslation;

import java.lang.reflect.Array;
import java.util.UUID;

public class GUnnamedPacket implements Packet {

    String name;
    int length;
    Class<?>[] types;
    Object[] fields;

    public GUnnamedPacket(String name, int length, Class<?>[] types) {
        this(name,length,types,new Object[length]);
    }

    public GUnnamedPacket(String name) {
        this(name,0,new Class[0],new Object[0]);
    }

    public GUnnamedPacket(String name, Class<?> type) {
       this(name,1,new Class[]{type},new Object[1]);
    }

    public GUnnamedPacket(String name, Class<?>... types) {
        this(name, types.length,types,new Object[types.length]);
    }

    public GUnnamedPacket(String name, int length, Class<?>[] types, Object[] fields) {
        if(fields.length != length) throw new IllegalArgumentException("fields.length must equal length!");
        if(types.length != length) throw new IllegalArgumentException("types.length must equal length!");

        this.name = name;
        this.length = length;
        this.types = types;
        this.fields = fields;
    }

    public GUnnamedPacket set(int index, Object object) {
        if(index < 0 || index >= length) return this;
        //if(!types[index].isInstance(object)) return this;
        fields[index] = object;
        return this;
    }

    public <T> T get(int index, Class<T> type) {
        if(index < 0 || index >= length) return null;
        Object obj = fields[index];
        return (T) obj;
    }

    public <T> T getOrDefault(int index, Class<T> type, T defaultValue) {
        T value = get(index,type);
        return value == null ? defaultValue : value;
    }

    public <T> T get(int index) {
        if(index < 0 || index >= length) return null;
        Object obj = fields[index];
        return (T) obj;
    }

    public Class<?> getType(int index) {
        if(index < 0 || index >= length) return null;
        return types[index];
    }

    @Override
    public int header() {
        return name.hashCode();
    }

    @Override
    public byte[] pack() {
        ByteStringBuilder builder = new ByteStringBuilder().begin(4,true);
        for(int i = 0; i < length; i++) {
            append(fields[i],builder);
        }
        return builder.end();
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteStringReader reader = new ByteStringReader().use(bytes);
        for(int i = 0; i < length; i++) {
            fields[i] = read(types[i],reader);
        }
    }

    @Override
    public Packet copy() {
        Object[] newFields = new Object[length];
        for(int i = 0; i < length; i++) {
            newFields[i] = copy(fields[i]);
        }
        return new GUnnamedPacket(name,length,types,newFields);
    }

    private void append(Object object, ByteStringBuilder builder) {
        Class<?> type = TypeTranslation.translateType(object.getClass()); //ensure primitive type
        if     (type == boolean.class) builder.append((boolean) (Boolean) object);
        else if(type == byte.class) builder.append((byte) (Byte) object);
        else if(type == short.class) builder.append((short) (Short) object);
        else if(type == int.class) builder.append((int) (Integer) object);
        else if(type == long.class) builder.append((long) (Long) object);
        else if(type == float.class) builder.append((float) (Float) object);
        else if(type == double.class) builder.append((double) (Double) object);
        else if(type == char.class) builder.append((char) (Character) object);
        else if(type.isArray()) appendArray(object,builder);
        else if(type == String.class) appendString((String) object,builder);
        else if(type == UUID.class) appendUUID((UUID) object,builder);
        else System.err.println("No primitive found for: "+type);
    }

    private void appendArray(Object array, ByteStringBuilder builder) {
        int length = Array.getLength(array);
        builder.append(length);
        for(int i = 0; i < length; i++) {
            Object element = Array.get(array,i);
            append(element,builder);
        }
    }

    private void appendString(String string, ByteStringBuilder builder) {
        byte[] data = string.getBytes();
        builder.append(data.length);
        builder.append(data);
    }
    private void appendUUID(UUID uuid, ByteStringBuilder builder) {
        builder.append(uuid.getMostSignificantBits());
        builder.append(uuid.getLeastSignificantBits());
    }

    private Object read(Class<?> type, ByteStringReader reader) {
        type = TypeTranslation.translateType(type); //ensure primitive type
        if(type == boolean.class) return reader.readBoolean();
        if(type == byte.class) return reader.readByte();
        if(type == short.class) return reader.readShort();
        if(type == int.class) return reader.readInteger();
        if(type == long.class) return reader.readLong();
        if(type == float.class) return reader.readFloat();
        if(type == double.class) return reader.readDouble();
        if(type == char.class) return reader.readChar();
        if(type.isArray()) return readArray(type,reader);
        if(type == String.class) return readString(reader);
        if(type == UUID.class) return readUUID(reader);
        System.err.println("No primitive found for: "+type);
        return null;
    }

    private Object readArray(Class<?> type, ByteStringReader reader) {
        Class<?> component = type.getComponentType();
        int length = reader.readInteger();
        Object array = Array.newInstance(component,length);
        for(int i = 0; i < length; i++) {
            Array.set(array,i,read(component,reader));
        }
        return array;
    }

    private String readString(ByteStringReader reader) {
        int length = reader.readInteger();
        byte[] data = reader.readByteArray(length);
        return new String(data);
    }

    private UUID readUUID(ByteStringReader reader) {
        return new UUID(reader.readLong(),reader.readLong());
    }

    protected Object copy(Object object) {
        Class<?> type = TypeTranslation.translateType(object.getClass()); //ensure primitive type
        if(type == boolean.class) return (boolean) (Boolean) object;
        if(type == byte.class) return (byte) (Byte) object;
        if(type == short.class) return (short) (Short) object;
        if(type == int.class) return (int) (Integer) object;
        if(type == long.class) return (long) (Long) object;
        if(type == float.class) return (float) (Float) object;
        if(type == double.class) return (double) (Double) object;
        if(type.isArray()) return copyArray(object);
        if(type == String.class) return (String) object;
        if(type == UUID.class) return copyUUID((UUID) object);
        System.err.println("No primitive found for: "+type);
        return null;
    }

    private Object copyArray(Object array) {
        Class<?> component = array.getClass().getComponentType();
        int length = Array.getLength(array);
        Object out = Array.newInstance(component,length);
        for(int i = 0; i < length; i++) {
            Array.set(out,i,copy(Array.get(array,i)));
        }
        return out;
    }

    private Object copyUUID(UUID uuid) {
        return new UUID(uuid.getMostSignificantBits(),uuid.getLeastSignificantBits());
    }

    public static Supplier<GUnnamedPacket> build(String spec) {
        spec = spec.replace("\n","").replace("\r","").replace(" ","");
        int nameSeparator = spec.indexOf(':');
        String name = spec.substring(0,nameSeparator);

        String[] data = spec.substring(nameSeparator+1).split(",");
        Class<?>[] types = new Class[data.length];
        for(int i = 0; i < types.length; i++) {
            types[i] = resolveType(data[i]);
            if(types[i] == null) throw new IllegalArgumentException("Malformed specification: No primitive found for '"+data[i]+"'");
        }
        int length = types.length;
        return ()-> new GUnnamedPacket(name,length,types);
    }

    protected static Class<?> resolveType(String str) {
        if(str.endsWith("[]")) {
            Class<?> type = resolveType(str.substring(0,str.length()-2));
            return Array.newInstance(type,0).getClass();
        }
        if(isOneOf(str,"bool","boolean","1b")) return boolean.class;
        if(isOneOf(str,"b","byte","8b")) return byte.class;
        if(isOneOf(str,"s","short","16b")) return short.class;
        if(isOneOf(str,"i","int","integer","32b")) return int.class;
        if(isOneOf(str,"l","long","64b")) return long.class;
        if(isOneOf(str,"f","float","32f")) return float.class;
        if(isOneOf(str,"d","double","64f")) return double.class;
        if(isOneOf(str,"c","char","character")) return char.class;
        if(isOneOf(str,"uuid","128b")) return UUID.class;
        if(isOneOf(str,"str","string")) return String.class;
        return null;
    }

    private static boolean isOneOf(String test, String... samples) {
        for(String sample : samples) {
            if(test.equalsIgnoreCase(sample)) return true;
        }
        return false;
    }

}
