package com.redcraft.rlib.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.redcraft.rlib.serial.TypeTranslation.typesOf;

public class ReflectiveClass<T> {
    Class<T> clazz;

    public ReflectiveClass(Class<T> clazz) {
        this.clazz = clazz;
    }

    public ReflectiveObject<T> newInstance() {
        return newInstance(new Class[0],new Object[0]);
    }
    public ReflectiveObject<T> newInstance(Object... params) {
        Class<?>[] paramTypes = typesOf(params);
        return newInstance(paramTypes,params);
    }
    public ReflectiveObject<T> newInstance(Class<?>[] paramTypes, Object[] params) {
        Constructor<T> constructor;
        try {
            constructor = clazz.getConstructor(paramTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }

        T obj;
        try {
            obj = constructor.newInstance(params);
        } catch (Exception e) {
            return null;
        }
        return new ReflectiveObject<T>(this,obj);
    }

    public T newObject(Object... params) {
        return newInstance(params).reference;
    }
    public T newObject() {
        return newInstance().reference;
    }

    public <X> X newCastedObject(Class<X> type, Object... params) {
        T obj = newObject(params);
        if(!type.isInstance(obj)) return null;
        @SuppressWarnings("unchecked") // check above
        X x = (X) obj;
        return x;
    }
    public <X> X newCastedObject(Class<X> type) {
        T obj = newObject();
        if(!type.isInstance(obj)) return null;
        @SuppressWarnings("unchecked") // check above
        X x = (X) obj;
        return x;
    }

    public Method findMethod(String name, Class<?>[] paramTypes) {
        try {
            return clazz.getMethod(name,paramTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public Field findField(String name) {
        try {
            return clazz.getField(name);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public Class<T> getReferenceClass() {
        return clazz;
    }


}
