package com.redcraft.starlight.server.elements.spacestation;

import com.redcraft.starlight.server.collision.CircularShape;
import com.redcraft.starlight.server.collision.Shape;
import com.redcraft.starlight.server.elements.SCollisionEntity;
import com.redcraft.starlight.server.elements.SPlayer;

import java.util.UUID;

public abstract class SSpaceStationPart extends SCollisionEntity {

    protected int direction;
    protected SPlayer destroyer;

    public SSpaceStationPart(UUID uuid) {
        super(uuid, new CircularShape(0f,0f,0.125f));
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    public int getDirection() {
        return direction;
    }
    public void setDestroyer(SPlayer player) {
        this.destroyer = player;
    }
    public SPlayer getDestroyer() {
        return destroyer;
    }

    @Override
    public String toString() {
        return "SSpaceStationPart{(" + getClass() +")" +
                "direction=" + direction +
                '}';
    }
}
