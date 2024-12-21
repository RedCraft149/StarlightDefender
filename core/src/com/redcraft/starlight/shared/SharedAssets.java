package com.redcraft.starlight.shared;


import com.redcraft.rlib.actions.Action;
import com.redcraft.rlib.actions.Actions;

import java.util.HashMap;
import java.util.Map;

public class SharedAssets {
    private final Map<String,Object> assets;

    public SharedAssets() {
        assets = new HashMap<>();
    }

    public boolean has(String key) {
        return assets.containsKey(key);
    }
    public Object get(String key) {
        return assets.get(key);
    }
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type) {
        Object value = get(key);
        if(value==null) return null;
        if(type.isInstance(value)) return (T) value; //'unchecked cast', but checked by if-statement
        return null;
    }
    public <T> T get(String key, Class<T> type, T defaultValue) {
        T value = get(key,type);
        return value != null ? value : defaultValue;
    }
    public float getFloat(String key) {
        return (float) get(key,Float.class,0f);
    }
    public int getInteger(String key) {
        return (int) get(key,Integer.class,0);
    }
    public void set(String key, Object value) {
        if(key.contains(":")) throw new IllegalArgumentException("':' is an internal character, cannot be used in a key.");
        assets.put(key,value);
    }

    public <T extends Action> void setAction(String key, T action) {
        assets.put("action:"+key,action);
    }
    public <T> T run(String key, Class<T> returnType, Object... args) {
        Action action = get("action:"+key, Action.class);
        if(action==null) return null;
        return Actions.run(action,args);
    }

    public boolean run(String key, Object... args) {
        Action action = get("action:"+key, Action.class);
        if(action==null) return false;
        Actions.run(action,args);
        return true;
    }
}
