package com.redcraft.rlib.serial;

public class TypeTranslation {
    public static Class<?> translateType(Class<?> t) {
        if(t==Boolean.class) return boolean.class;
        if(t==Byte.class) return byte.class;
        if(t==Short.class) return short.class;
        if(t==Integer.class) return int.class;
        if(t==Long.class) return long.class;
        if(t==Float.class) return float.class;
        if(t==Double.class) return double.class;
        if(t==Character.class) return char.class;
        return t;
    }

    public static Class<?>[] typesOf(Object... objects) {
        final int length = objects.length;
        Class<?>[] types = new Class[length];
        for(int i = 0; i < length; i++) {
            types[i] = translateType(objects[i].getClass());
        }
        return types;
    }
}
