package com.redcraft.communication.packets;

import com.redcraft.rlib.function.Supplier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GPacket extends GUnnamedPacket {

    Map<String,Integer> names;

    public GPacket(String name, int length, Class<?>[] types) {
        super(name, length, types);
    }

    public GPacket(String name, int length, Class<?>[] types, String[] names) {
        super(name,length,types);
        setNames(new HashMap<>());
        for(int i = 0; i < length; i++) {
            this.names.put(names[i],i);
        }
    }

    public GPacket(String name, int length, Class<?>[] types, Object[] fields) {
        super(name, length, types, fields);
    }

    public GPacket(GPacket packet) {
        this(packet.name,packet.length,packet.types,packet.fields);
        setNames(packet.names);
    }
    
    public GPacket setNames(Map<String,Integer> names) {
        this.names = names;
        return this;
    }

    @Override
    public GPacket copy() {
        Object[] newFields = new Object[length];
        for(int i = 0; i < length; i++) {
            newFields[i] = copy(fields[i]);
        }
        return new GPacket(name,length,types,newFields).setNames(names);
    }

    public GPacket set(String key, Object value) {
        int index = names.containsKey(key) ? names.get(key) : -1;
        set(index,value);
        return this;
    }

    public <T> T get(String key, Class<T> type) {
        int index = names.containsKey(key) ? names.get(key) : -1;
        return get(index,type);
    }

    public Object get(String key){
        int index = names.containsKey(key) ? names.get(key) : -1;
        return get(index);
    }

    public <T> T getOrDefault(String key, Class<T>  type, T defaultValue) {
        int index = names.containsKey(key) ? names.get(key) : -1;
        return getOrDefault(index,type,defaultValue);
    }

    public boolean getBoolean(String key) {
        return get(key,boolean.class);
    }
    public float getFloat(String key) {
        return get(key,float.class);
    }
    public int getInteger(String key) {
        return get(key,int.class);
    }

    public String toString() {
        return names.toString()+ "\n" + Arrays.toString(fields);
    }

    public static Supplier<Packet> parse(String spec) {
        spec = spec.replace("\n","").replace("\r","").replace(" ","");
        String[] fields = spec.split("[,;]");

        int index = 0;
        String packetName = null;
        Class<?>[] types = new Class[fields.length];
        Map<String,Integer> names = new HashMap<>();

        for(String field : fields) {
            String key = getKey(field);
            String value = getValue(field);
            if(key.equals("name")) packetName = value;
            else {
                types[index] = GUnnamedPacket.resolveType(key);
                if(types[index] == null) throw new IllegalArgumentException("Malformed packet specification: no primitive found for "+key+"!");
                String[] fieldNames = value.split("/");
                for(String n : fieldNames) names.put(n,index);
                index++;
            }
        }

        if(packetName == null) throw new IllegalArgumentException("Malformed packet specification: no name found!");
        Class<?>[] compactTypes = new Class[index];
        System.arraycopy(types,0,compactTypes,0,index);

        final String finalPacketName = packetName;
        final int length = compactTypes.length;
        return () -> new GPacket(finalPacketName,length,compactTypes).setNames(names);
    }

    private static String getKey(String f) {
        return f.substring(0,f.indexOf(':'));
    }
    private static String getValue(String f) {
        return f.substring(f.indexOf(':')+1);
    }
}
