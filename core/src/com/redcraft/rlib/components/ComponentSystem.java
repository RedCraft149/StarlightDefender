package com.redcraft.rlib.components;

import java.util.HashMap;
import java.util.Map;

public class ComponentSystem {

    private static final ComponentSystem EMPTY = new ComponentSystem();
    private static final Object FLAG_SET = new Object();

    public static ComponentSystem empty() {
        return EMPTY;
    }
    public static ComponentSystem create() {
        ComponentSystem system = new ComponentSystem();
        system.components = new HashMap<>();
        return system;
    }


    private Map<String,Object> components = null;
    private ComponentSystem() {
    }

    public Object get(String key) {
        if(components==null) return null;
        return components.get(key);
    }
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type) {
        Object value = get(key);
        if(value==null) return null;
        if(type.isInstance(value)) return (T) value;
        return null;
    }

    public void remove(String key) {
        if(components==null) return;
        components.remove(key);
    }
    public boolean isFlagSet(String key) {
        if(components==null) return false;
        return components.get(key) == FLAG_SET;
    }
    public boolean isPresent(String key) {
        if(components==null) return false;
        return components.containsKey(key);
    }
    public void clear() {
        if(components==null) return;
        components.clear();
    }

    public ComponentSystem set(String key, Object value) {
        if(components==null) return this;
        components.put(key, value);
        return this;
    }
    public ComponentSystem setFlag(String key) {
        if(components==null) return this;
        components.put(key, FLAG_SET);
        return this;
    }
    public ComponentSystem clearFlag(String key) {
        if(components==null) return this;
        if(components.get(key) == FLAG_SET) components.remove(key);
        return this;
    }
    public boolean isEmpty() {
        return this.components==null || this.components.isEmpty();
    }
}
