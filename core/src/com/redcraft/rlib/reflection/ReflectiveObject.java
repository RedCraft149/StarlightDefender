package com.redcraft.rlib.reflection;


import com.redcraft.rlib.serial.TypeTranslation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectiveObject<T> {
    ReflectiveClass<T> type;
    T reference;

    public ReflectiveObject(ReflectiveClass<T> type, T reference) {
        this.type = type;
        this.reference = reference;
    }

    public Object invoke(String method) {
        return invoke(method,new Class<?>[0], new Object[0]);
    }
    public Object invoke(String method, Object... args) {
        Class<?>[] paramTypes = TypeTranslation.typesOf(args);
        return invoke(method,paramTypes,args);
    }
    public Object invoke(String method, Class<?>[] paramTypes, Object[] params) {
        Method m = type.findMethod(method,paramTypes);
        if(m==null) return null;
        try {
            return m.invoke(reference,params);
        } catch (Exception e) {
            return null;
        }
    }

    public Object getField(String name) {
        Field f = type.findField(name);
        if(f==null) return null;
        try {
            return f.get(reference);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public void setField(String name, Object value) {
        Field f = type.findField(name);
        if(f==null) return;
        try {
            f.set(reference,value);
        } catch (IllegalAccessException ignored) {}
    }

    public <R> R cast(Class<R> type) {
        if(!type.isAssignableFrom(this.type.clazz)) return (R) null;
        try {
            @SuppressWarnings("unchecked") //cast gets checked and errors get handled
            R ref = (R) reference;
            return ref;
        } catch (ClassCastException e) {
            return (R) null;
        }
    }

    public ReflectiveClass<T> getType() {
        return type;
    }
    public T getReference() {
        return reference;
    }
}
