package com.redcraft.rlib.reflection;

public class Reflection {
    public static <T> T newInstance(Class<T> type) {
        return new ReflectiveClass<>(type).newObject();
    }

    public static ReflectiveClass<?> findClass(String name) {
        ClassLoader loader = Reflection.class.getClassLoader();
        Class<?> clazz;
        try {
            clazz = loader.loadClass(name);
        } catch (ClassNotFoundException e) {
            return null;
        }

        return new ReflectiveClass<>(clazz);
    }

    public static <T> T newInstance(Class<T> superType, String className) {
        ReflectiveClass<?> clazz = findClass(className);
        if(clazz==null) return null;
        return clazz.newCastedObject(superType);
    }
}
