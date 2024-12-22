package com.redcraft.rlib.events;

public abstract class QuickEventListener implements Listener {
    private final String name;

    public QuickEventListener(String name) {
        this.name = name;
    }

    public abstract void onEvent(QuickEvent event);

    @EventListener
    public void onEvent0(QuickEvent event) {
        if(event.name().equals(name)) onEvent(event);
    }
}
