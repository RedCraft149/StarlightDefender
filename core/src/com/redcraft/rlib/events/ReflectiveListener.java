package com.redcraft.rlib.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectiveListener {
    Method method;
    Listener handle;
    Class<? extends Event> type;
    boolean allowExtensions;

    public ReflectiveListener(String method, Listener handle) {
        this.handle = handle;
        this.method = findMethod(method);
        if(this.method==null) throw new IllegalArgumentException("There is no method called "+method+" in "+handle.getClass()+".");
        this.type = findType();
        if(this.type==null) throw new IllegalArgumentException("Method "+method+" is no event listener method!");
        allowExtensions = findExtensionPermission();
    }
    public ReflectiveListener(Method method, Listener handle) {
        this.handle = handle;
        this.method = method;
        this.type = findType();
        if(this.type==null) throw new IllegalArgumentException("Method "+method+" is no event listener method!");
        allowExtensions = findExtensionPermission();
    }

    public Listener getHandle() {
        return handle;
    }

    public boolean accept(Event event) {
        if(!allowExtensions) return event.getClass()==type;
        else return type.isAssignableFrom(event.getClass());
    }
    public boolean handle(Event event) {
        try {
            method.invoke(handle,event);
            return true;
        } catch (IllegalAccessException ex) {
            return false;
        } catch (InvocationTargetException e) {
            return false;
        }
    }

    private Method findMethod(String name) {
        Class<? extends Listener> clazz = handle.getClass();
        for(Method method : clazz.getMethods()) {
            if(method.getName().equals(name)) return method;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Class<? extends Event> findType() {
        Class<?>[] params = method.getParameterTypes();
        if(params.length!=1) return null;
        if(!Event.class.isAssignableFrom(params[0])) return null;
        return (Class<? extends Event>) params[0];
    }

    private boolean findExtensionPermission() {
        EventListener annotation = method.getAnnotation(EventListener.class);
        if(annotation==null) return false; //if the listener was manually registered, annotation==null could be true
        return annotation.allowExtensions();
    }


}
