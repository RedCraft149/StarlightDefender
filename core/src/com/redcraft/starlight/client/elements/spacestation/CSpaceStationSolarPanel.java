package com.redcraft.starlight.client.elements.spacestation;



import com.redcraft.starlight.client.CConstants;

import java.util.UUID;

public class CSpaceStationSolarPanel extends CSpaceStationPart{
    public CSpaceStationSolarPanel(UUID uuid, int direction) {
        super(uuid, CConstants.SPACE_STATION_PART_2x2,0.5f);
        setTexture("space_station/solar_panel");
        setScale(0.25f);
        recalculateTransformMatrix();
        setDirection(direction);
    }
}
