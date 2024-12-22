package com.redcraft.rlib.components;

public abstract class ComponentSystemAccessor {
    ComponentSystem components;

    public ComponentSystemAccessor() {
        components = ComponentSystem.create();
    }

    public void createComponents() {
        setComponents(ComponentSystem.create());
    }
    public ComponentSystem getOrCreateComponents() {
        if(components ==null) components = ComponentSystem.create();
        return components;
    }
    public ComponentSystem getComponents() {
        return components == null ? ComponentSystem.empty() : components;
    }
    public void removeComponents() {
        this.components = null;
    }
    public void setComponents(ComponentSystem components) {
        this.components = components;
    }

    public boolean isFlagSet(String flag) {
        return getComponents().isFlagSet(flag);
    }
    public void setFlag(String flag) {
        getOrCreateComponents().setFlag(flag);
    }
    public void clearFlag(String flag) {
        getOrCreateComponents().clearFlag(flag);
    }

    public <T> T get(String key, Class<T> type) {
        return components.get(key,type);
    }
    public void set(String key, Object object) {
        components.set(key,object);
    }
    public void remove(String key) {
        components.remove(key);
    }


}
