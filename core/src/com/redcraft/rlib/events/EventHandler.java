package com.redcraft.rlib.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Caches and distributes thrown events to listeners.
 */
public class EventHandler {
    private final List<Event> cached;
    private final List<ReflectiveListener> listeners;
    private boolean interrupted;

    public EventHandler() {
        listeners = new ArrayList<>();
        cached = new ArrayList<>();
    }

    public void register(Listener listener) {
        for(Method method : listenerMethods(listener)) {
            listeners.add(new ReflectiveListener(method,listener));
        }
    }
    public void register(ReflectiveListener listener) {
        listeners.add(listener);
    }

    public void unregister(Listener listener) {
        Iterator<ReflectiveListener> itr = listeners.iterator();
        while(itr.hasNext()) {
            ReflectiveListener reflectiveListener = itr.next();
            if(reflectiveListener.getHandle().equals(listener)) itr.remove();
        }
    }
    public void unregister(ReflectiveListener listener) {
        listeners.remove(listener);
    }

    /**
     * Distributes all events thrown by {@link EventHandler#throwEvent(Event)} to all listening listeners.
     */
    public void distributeCachedEvents() {
        ArrayList<Event> copy = new ArrayList<>(cached);
        for(Event event : copy) distributeEvent(event);
        cached.removeAll(copy);
    }

    /**
     * Distributes an event to all listening listeners.
     * In contrast to {@link EventHandler#throwEvent(Event)},
     * all listener methods are invoked immediately.
     * @param event Event to distribute.
     */
    public void distributeEvent(Event event) {
        interrupted = false;
        for(ReflectiveListener listener : listeners) {
            if(listener.accept(event)) listener.handle(event);
            if(interrupted) break;
        }
    }

    /**
     * Throw an event. The event will be passed to all listening listeners when {@link EventHandler#distributeCachedEvents()} is called.
     * @param event Event to throw.
     */
    public void throwEvent(Event event) {
        cached.add(event);
    }

    /**
     * Interrupt the distribution of an event.
     */
    public void interrupt() {
        interrupted = true;
    }

    private List<Method> listenerMethods(Listener listener) {
        List<Method> listenerMethods = new LinkedList<>();
        Class<? extends Listener> clazz = listener.getClass();
        for(Method method : clazz.getMethods()) {
            if(method.isAnnotationPresent(EventListener.class)) listenerMethods.add(method);
        }
        return listenerMethods;
    }
}
