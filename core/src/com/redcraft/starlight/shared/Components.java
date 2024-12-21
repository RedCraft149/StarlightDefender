package com.redcraft.starlight.shared;


import com.redcraft.rlib.components.ComponentSystem;
import com.redcraft.rlib.events.EventHandler;

public class Components {
    private static short signCounter = 0;

    public static final String THROW_POSITION_EVENTS = createSigned("throwPositionEvents");
    public static final String THROW_ROTATION_EVENTS = createSigned("throwRotationEvents");
    public static final String THROW_REMOVE_EVENTS = createSigned("throwRemoveEvents");
    public static final String RESPONSIBLE_EVENT_HANDLER = createSigned("responsibleEventHandler");

    public static boolean throwPositionEvents(ComponentSystem system) {
        return system.isFlagSet(THROW_POSITION_EVENTS);
    }
    public static boolean throwRotationEvents(ComponentSystem system) {
        return system.isFlagSet(THROW_ROTATION_EVENTS);
    }
    public static boolean throwRemoveEvents(ComponentSystem system) {
        return system.isFlagSet(THROW_REMOVE_EVENTS);
    }
    public static EventHandler getResponsibleEventHandler(ComponentSystem system) {
        return system.get(RESPONSIBLE_EVENT_HANDLER, EventHandler.class);
    }


    private static String createSigned(String name) {
        StringBuilder builder = new StringBuilder();
        for(int i = 3; i >= 0; i--) {
            int num = signCounter >> (i*4);
            num &= 0xF;
            builder.append(Integer.toHexString(num));
        }
        builder.append("sgn:");
        builder.append(name);
        signCounter++;
        return builder.toString();
    }
}
