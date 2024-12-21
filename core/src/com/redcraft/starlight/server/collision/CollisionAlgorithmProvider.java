package com.redcraft.starlight.server.collision;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.rlib.Pair;

import java.util.HashMap;
import java.util.Map;

public class CollisionAlgorithmProvider {

    Map<Pair<Class<?>, Class<?>>, CollisionAlgorithm<?,?>> algorithms;

    public CollisionAlgorithmProvider() {
        algorithms = new HashMap<>();
    }

    public CollisionAlgorithm<?,?> getAlgorithm(Shape a, Shape b) {
        return algorithms.get(new Pair<>(a.getClass(),b.getClass()));
    }
    public void registerAlgorithm(CollisionAlgorithm<?,?> algorithm) {
        algorithms.put(new Pair<>(algorithm.getTypeA(),algorithm.getTypeB()),algorithm);
    }

    public static CollisionAlgorithmProvider defaultConfiguration(Vector2 modularDimensions) {
        CollisionAlgorithmProvider collisionAlgorithmProvider = new CollisionAlgorithmProvider();
        collisionAlgorithmProvider.registerAlgorithm(new CCAlgorithm(modularDimensions));
        return collisionAlgorithmProvider;
    }
}
