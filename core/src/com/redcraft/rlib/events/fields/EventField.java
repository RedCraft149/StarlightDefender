package com.redcraft.rlib.events.fields;

import com.redcraft.rlib.events.EventHandler;
import com.redcraft.rlib.events.QuickEvent;
import com.redcraft.rlib.function.Supplier;

import java.util.UUID;

public class EventField<T> {
    protected T value;
    protected String name;
    protected UUID owner;
    private final Supplier<QuickEvent> context;
    private final EventHandler eventHandler;


    public EventField(T value, String name, UUID owner, Supplier<QuickEvent> context, EventHandler eventHandler) {
        this.value = value;
        this.name = name;
        this.owner = owner;
        this.context = context;
        this.eventHandler = eventHandler;
    }

    public EventField(T value, String name, UUID owner, EventHandler eventHandler) {
        this(value,name,owner,QuickEvent::new,eventHandler);
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
        this.eventHandler.throwEvent(context.get().set("value",value).set("name",name).set("owner",owner).name("event_field_changed"));
    }

    public void setFrom(QuickEvent event) {
        if(!event.matches("event_field_changed")) return;
        if(name != null && !name.equals(event.read("name",String.class))) return;
        if(owner != null && !owner.equals(event.read("owner",UUID.class))) return;

        try {
            @SuppressWarnings("unchecked")
            T value = (T) event.read("value");
            this.value = value;
        } catch (ClassCastException ex) {
            return;
        }
    }

    public void setSilent(T value) {
        this.value = value;
    }

    public UUID getOwner() {
        return owner;
    }
    public String getName() {
        return name;
    }
}
