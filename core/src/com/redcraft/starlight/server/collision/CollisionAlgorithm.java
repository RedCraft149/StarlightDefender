package com.redcraft.starlight.server.collision;

public interface CollisionAlgorithm<A extends Shape, B extends Shape> {
    boolean collide(A a, B b);

    Class<A> getTypeA();
    Class<B> getTypeB();

    @SuppressWarnings("unchecked") //cast are checked using reflection
    default boolean collideUnknown(Shape s1, Shape s2) {
        if(getTypeA().isInstance(s1) && getTypeB().isInstance(s2)) return collide((A) s1,(B) s2);
        if(getTypeA().isInstance(s2) && getTypeB().isInstance(s1)) return collide((A) s2,(B) s1);
        return false;
    }
}
