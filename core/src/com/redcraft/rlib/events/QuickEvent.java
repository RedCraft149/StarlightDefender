package com.redcraft.rlib.events;

import com.redcraft.rlib.function.Consumer;

import java.util.HashMap;
import java.util.Map;

/**
 * General-purpose event implementation for faster implementation.
 * This class should only be used for rarely thrown events, otherwise performance could decrease.
 */
public class QuickEvent implements Event {
    Map<String,Entry> data;
    String name;

    public QuickEvent(Map<String, Entry> data, String name) {
        this.data = data;
        this.name = name;
    }
    public QuickEvent(String name) {
        this(new HashMap<>(),name);
    }
    public QuickEvent() {
        this(new HashMap<>(),"");
    }

    public QuickEvent set(String name, boolean b) {
        data.put(name,new Entry(boolean.class,b));
        return this;
    }
    public QuickEvent set(String name, byte b) {
        data.put(name,new Entry(byte.class,b));
        return this;
    }
    public QuickEvent set(String name, short s) {
        data.put(name,new Entry(short.class,s));
        return this;
    }
    public QuickEvent set(String name, int i) {
        data.put(name,new Entry(int.class,i));
        return this;
    }
    public QuickEvent set(String name, long l) {
        data.put(name,new Entry(long.class,l));
        return this;
    }
    public QuickEvent set(String name, float f) {
        data.put(name,new Entry(float.class,f));
        return this;
    }
    public QuickEvent set(String name, double d) {
        data.put(name,new Entry(double.class,d));
        return this;
    }
    public QuickEvent set(String name, char c) {
        data.put(name,new Entry(char.class,c));
        return this;
    }
    public QuickEvent set(String name, Object obj) {
        data.put(name,new Entry(obj.getClass(),obj));
        return this;
    }
    public QuickEvent name(String name) {
        this.name = name;
        return this;
    }

    public QuickEvent booleanField(String name) {
        return set(name,false);
    }
    public QuickEvent byteField(String name) {
        return set(name,(byte) 0);
    }
    public QuickEvent shortField(String name) {
        return set(name,(short) 0);
    }
    public QuickEvent intField(String name) {
        return set(name,0);
    }
    public QuickEvent longField(String name) {
        return set(name,0L);
    }
    public QuickEvent floatField(String name) {
        return set(name,0f);
    }
    public QuickEvent doubleField(String name) {
        return set(name,0d);
    }
    public QuickEvent charField(String name) {
        return set(name,'\0');
    }
    public QuickEvent objectField(String name) {
        return set(name,new Object());
    }
    public QuickEvent field(String name, Class<?> type) {
        data.put(name,new Entry(type,null));
        return this;
    }

    public boolean readBoolean(String name) {
        return read(name,boolean.class,false);
    }
    public byte readByte(String name) {
        return read(name,byte.class,(byte) 0);
    }
    public short readShort(String name) {
        return read(name,short.class,(short) 0);
    }
    public int readInt(String name) {
        return read(name,int.class,0);
    }
    public long readLong(String name) {
        return read(name,long.class,0L);
    }
    public float readFloat(String name) {
        return read(name,float.class,0f);
    }
    public double readDouble(String name) {
        return read(name,double.class,0d);
    }
    public char readChar(String name) {
        return read(name,char.class,'\0');
    }
    public Object read(String name) {
        Entry entry = data.get(name);
        if(entry==null) return null;
        return entry.obj;
    }
    public <T> T read(String name, Class<T> type) {
        return read(name,type,null);
    }
    public <T> T read(String name, Class<T> type, T defaultValue) {
        Entry entry = data.get(name);
        if(entry==null) return defaultValue;
        if(!type.isAssignableFrom(entry.type)) {
            System.out.println("type mismatch: "+type+" <> "+entry.type);
            return defaultValue;
        }
        return entry.castUnchecked(type);
    }
    public String name() {
        return name;
    }
    public boolean matches(String name) {
        return this.name.equals(name);
    }

    public Listener listener(Consumer<QuickEvent> action) {
        if(this.name.length()>0) {
            return new Listener() {
                @EventListener
                public void onEvent(QuickEvent event) {
                    if (event.name.equals(QuickEvent.this.name)) action.accept(event);
                }
            };
        } else {
            return new Listener() {
                @EventListener
                public void onEvent(QuickEvent event) {
                    action.accept(event);
                }
            };
        }
    }
    public void registerAsListener(EventHandler handler, Consumer<QuickEvent> action) {
        handler.register(listener(action));
    }

    public static Listener listener(String name, Consumer<QuickEvent> action) {
        return new Listener() {
            @EventListener
            public void onEvent(QuickEvent event) {
                if(event.name().equals(name)) action.accept(event);
            }
        };
    }

    private static class Entry {
        Class<?> type;
        Object obj;

        public Entry(Class<?> type, Object obj) {
            this.type = type;
            this.obj = obj;
        }

        @SuppressWarnings("unchecked")
        public <T> T castUnchecked(Class<T> type) {
            return (T) obj;
        }
    }

}
