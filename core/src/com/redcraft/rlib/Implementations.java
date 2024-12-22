package com.redcraft.rlib;

import com.redcraft.rlib.reflection.Reflection;

import java.util.HashMap;
import java.util.Map;

public class Implementations {

    private static final Map<Class<?>,Object> utilities = new HashMap<>();
    private static final Map<Class<?>,Class<?>> implementations = new HashMap<>();

    public static <T> T getUtility(Class<T> type) {
        Object obj = utilities.get(type);
        if(obj==null) throw new RuntimeException("Utility "+type+" is not supported");
        if(!type.isInstance(obj)) throw new RuntimeException("Utility "+type+" is not supported");
        return type.cast(obj);
    }

    public static <T> T getImplementation(Class<? super T> type) {
        Class<?> implClass = implementations.get(type);
        if(implClass==null) throw new RuntimeException("Implementation of "+type+" is not supported");
        if(!type.isAssignableFrom(implClass)) throw new RuntimeException("Implementation of "+type+" is not supported");
        @SuppressWarnings("unchecked")
        Class<T> castedImplClass = (Class<T>) implClass;
        return Reflection.newInstance(castedImplClass);
    }

    public static <T> void setImplementation(Class<T> base, Class<? extends T> impl) {
        implementations.put(base,impl);
    }
    public static <T> void setUtility(Class<T> type, T o) {
        utilities.put(type,o);
    }
}
