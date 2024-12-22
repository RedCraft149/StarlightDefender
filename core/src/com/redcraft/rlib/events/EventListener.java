package com.redcraft.rlib.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark a method as an event listener.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventListener {
    /**
     * @return true if this listener not only listens to the specified event, but also its subclasses, otherwise false.
     */
    boolean allowExtensions() default false;
}
