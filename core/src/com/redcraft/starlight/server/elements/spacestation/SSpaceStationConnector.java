package com.redcraft.starlight.server.elements.spacestation;

import java.util.UUID;

public class SSpaceStationConnector extends SSpaceStationPart{
    public SSpaceStationConnector(UUID uuid, int direction) {
        super(uuid);
        this.direction = direction;
    }
}
