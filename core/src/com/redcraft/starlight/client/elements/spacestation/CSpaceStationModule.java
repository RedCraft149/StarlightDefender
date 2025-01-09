package com.redcraft.starlight.client.elements.spacestation;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.client.CConstants;

import java.util.UUID;

public class CSpaceStationModule extends CSpaceStationPart{
    public CSpaceStationModule(UUID uuid, int direction) {
        super(uuid, CConstants.SPACE_STATION_PART_4x4,0.25f);
        setTexture("space_station/module");
        setScale(0.25f);
        recalculateTransformMatrix();
        setDirection(direction);
    }
}
