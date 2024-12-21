package com.redcraft.starlight.shared.entity;

import com.redcraft.starlight.client.elements.*;
import com.redcraft.starlight.server.elements.*;

import java.util.UUID;

public class Entities {
    public static final int PLAYER =    0x01;
    public static final int ENEMY  =    0x02;
    public static final int ENEMY2 =    0x03;

    public static final int ROCK =      0x10;
    public static final int MINE =      0x11;

    public static final int BULLET =    0x20;

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
            default: return null;
        }
    }
}
