package com.redcraft.rlib.events;


import com.redcraft.rlib.function.Consumer;

/**
 * Tagging interface for all Events.
 * All classes that implement this interface can be thrown and listened to in use with {@link EventHandler}.
 */
public interface Event {
    default void throwEvent(EventHandler handler) {handler.throwEvent(this);}
    default Runnable executable(EventHandler handler) {return ()->throwEvent(handler);}
    static <T extends Event> Listener createListener(Consumer<T> action) {
        return new Listener() {
            @EventListener
            public void onEvent(T event) {
                action.accept(event);
            }
        };
    }

}
