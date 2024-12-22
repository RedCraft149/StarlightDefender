package com.redcraft.communication.fields;

import com.redcraft.rlib.events.EventHandler;

import java.util.UUID;

/**
 * A general shared field between a client and a server.
 */
public class GField<T> {
    protected T      value;
    protected String name;
    protected UUID   owner;

    private final GFieldCommunicationType communicationType;
    private final EventHandler eventHandler;

    public GField(String name, UUID owner, EventHandler eventHandler) {
        this.name = name;
        this.owner = owner;
        this.eventHandler = eventHandler;
        this.communicationType = GFieldCommunicationType.OTHERS;
    }
    public GField(T value, String name, UUID owner, EventHandler eventHandler) {
        this.value = value;
        this.name = name;
        this.owner = owner;
        this.eventHandler = eventHandler;
        this.communicationType = GFieldCommunicationType.OTHERS;
    }
    public GField(String name, UUID owner, GFieldCommunicationType communicationType, EventHandler eventHandler) {
        this.name = name;
        this.owner = owner;
        this.communicationType = communicationType;
        this.eventHandler = eventHandler;
    }
    public GField(T value, String name, UUID owner, GFieldCommunicationType communicationType, EventHandler eventHandler) {
        this.value = value;
        this.name = name;
        this.owner = owner;
        this.communicationType = communicationType;
        this.eventHandler = eventHandler;
    }
    public GField(T value, String name, UUID owner, GFieldCommunicationType communicationType, EventHandler eventHandler, GFieldRegistry registry) {
        this.value = value;
        this.name = name;
        this.owner = owner;
        this.communicationType = communicationType;
        this.eventHandler = eventHandler;
        this.register(registry);
    }

    public GField<T> register(GFieldRegistry registry) {
        registry.addField(owner,name,this);
        return this;
    }
    public void set(T value) {
        this.value = value;
        if(eventHandler != null) eventHandler.throwEvent(new GFieldEvent(this));
    }
    public void setSilent(T value) {
        this.value = value;
    }
    public T get() {
        return value;
    }
    public GFieldCommunicationType getCommunicationType() {
        return communicationType;
    }
    public UUID getOwner() {
        return owner;
    }
    public GFieldPacket createPacket() {
        return new GFieldPacket(value,name,owner);
    }
    public boolean set(GFieldPacket packet) {
        if(name != null && !name.equals(packet.getName())) return false;
        if(owner != null && !owner.equals(packet.getOwner())) return false;

        try {
            @SuppressWarnings("unchecked")
            T casted = (T) packet.getContent();
            this.value = casted;
            return true;
        } catch (ClassCastException ex) {
            return false;
        }
    }



    public boolean deepEquals(Object other) {
        if(!(other instanceof GField)) return false;
        GField<?> field = (GField<?>) other;
        return this.name.equals(field.name) && this.owner.equals(field.owner) && this.value.equals(field.value);
    }
    public boolean equals(Object other) {
        if(!(other instanceof GField)) return false;
        GField<?> field = (GField<?>) other;
        return this.name.equals(field.name) && this.owner.equals(field.owner);
    }
    public int hashCode() {
        return name.hashCode() ^ owner.hashCode();
    }
}
