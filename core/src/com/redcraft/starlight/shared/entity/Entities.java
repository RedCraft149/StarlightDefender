package com.redcraft.starlight.shared.entity;

import com.redcraft.starlight.client.elements.*;
import com.redcraft.starlight.client.elements.spacestation.CSpaceStationConnector;
import com.redcraft.starlight.client.elements.spacestation.CSpaceStationModule;
import com.redcraft.starlight.client.elements.spacestation.CSpaceStationSolarPanel;
import com.redcraft.starlight.server.elements.*;
import com.redcraft.starlight.server.elements.spacestation.SSpaceStationConnector;
import com.redcraft.starlight.server.elements.spacestation.SSpaceStationModule;
import com.redcraft.starlight.server.elements.spacestation.SSpaceStationPart;
import com.redcraft.starlight.server.elements.spacestation.SSpaceStationSolarPanel;

import java.util.UUID;

public class Entities {
    public static final int PLAYER =    0x01;
    public static final int ENEMY  =    0x02;
    public static final int ENEMY2 =    0x03;

    public static final int ROCK =      0x10;
    public static final int MINE =      0x11;

    public static final int BULLET =    0x20;
    public static final int SPACE_STATION_CONNECTOR = 0x100;
    public static final int SPACE_STATION_MODULE = 0x200;
    public static final int SPACE_STATION_SOLAR_PANEL = 0x400;

    public static int typeServer(Entity e) {
        if(e instanceof SPlayer) return PLAYER;
        if(e instanceof SEnemy) {
            SEnemy enemy = (SEnemy) e;
            if(enemy.getType() == SEnemy.BASIC) return ENEMY;
            if(enemy.getType() == SEnemy.ADVANCED) return ENEMY2;
        }
        if(e instanceof SBullet) return BULLET;
        if(e instanceof SStaticObject) {
            SStaticObject object = (SStaticObject) e;
            if(object.getType() == StaticObjects.ROCK) return ROCK;
            if(object.getType() == StaticObjects.MINE) return MINE;
        }
        if(e instanceof SSpaceStationPart) {
            SSpaceStationPart part = (SSpaceStationPart) e;
            int direction = part.getDirection();
            if(e instanceof SSpaceStationConnector) return SPACE_STATION_CONNECTOR | direction;
            if(e instanceof SSpaceStationModule) return SPACE_STATION_MODULE | direction;
            if(e instanceof SSpaceStationSolarPanel) return SPACE_STATION_SOLAR_PANEL | direction;
        }
        return 0;
    }
    public static CEntity clientEntityFromType(int type, UUID uuid) {
        switch (type) {
            case PLAYER: return new CPlayer(uuid);
            case ENEMY: return new CEnemy(uuid, CEnemy.Type.BASIC);
            case ENEMY2: return new CEnemy(uuid, CEnemy.Type.ADVANCED);
            case ROCK: return new CStaticObject(uuid, CStaticObject.Type.ROCK);
            case MINE: return new CStaticObject(uuid, CStaticObject.Type.MINE);
            case BULLET: return new CBullet(uuid);
        }
        if((type & SPACE_STATION_SOLAR_PANEL) != 0) {
            return new CSpaceStationSolarPanel(uuid,type & 0xF);
        }
        if((type & SPACE_STATION_CONNECTOR) != 0) {
            return new CSpaceStationConnector(uuid, type & 0xF);
        }
        if((type & SPACE_STATION_MODULE) != 0) {
            return new CSpaceStationModule(uuid,type & 0xF);
        }
        return null;
    }
}
