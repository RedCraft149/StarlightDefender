package com.redcraft.starlight.server.elements.spacestation;

import java.util.UUID;

public class SSpaceStationModule extends SSpaceStationPart{
    public SSpaceStationModule(UUID uuid, int direction) {
        super(uuid);
        this.direction = direction;
    }
}
