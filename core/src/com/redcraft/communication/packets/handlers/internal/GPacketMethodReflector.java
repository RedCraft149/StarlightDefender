package com.redcraft.communication.packets.handlers.internal;

import com.redcraft.communication.packets.GPacket;
import com.redcraft.communication.packets.handlers.Receiver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GPacketMethodReflector implements GPacketReflector {

    int length;
    String[] indices;
    Method method;
    Object target;

    public GPacketMethodReflector(Object target) {
        this.target = target;
        Class<?> clazz = target.getClass();
        for(Method method : clazz.getMethods()) {
            Receiver annotation = method.getAnnotation(Receiver.class);
            if(annotation == null) continue;
            this.method = method;
            setTo(annotation);
            break;
        }
        if(length == 0) throw new IllegalArgumentException("Target is no receiver!");
    }

    public void setTo(Receiver annotation) {
        indices = annotation.fields();
        length = indices.length;
    }

    @Override
    public boolean reflect(GPacket packet) {
        try {
            method.invoke(target,reflectArguments(packet));
            return true;
        } catch (IllegalAccessException e) {
            return false;
        } catch (InvocationTargetException e) {
            return false;
        }
    }

    public Object[] reflectArguments(GPacket packet) {
        Object[] args = new Object[length];
        for(int i = 0; i < length; i++) {
            args[i] = packet.get(indices[i]);
        }
        return args;
    }
}
