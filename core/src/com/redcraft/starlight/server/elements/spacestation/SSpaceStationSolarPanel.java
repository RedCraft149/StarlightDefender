package com.redcraft.starlight.server.elements.spacestation;

import java.util.UUID;

public class SSpaceStationSolarPanel extends SSpaceStationPart{
    public SSpaceStationSolarPanel(UUID uuid, int direction) {
        super(uuid);
        setDirection(direction);
    }
}
