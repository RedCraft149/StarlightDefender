package com.redcraft.starlight.server.elements.spacestation;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.rlib.Processable;
import com.redcraft.rlib.events.QuickEvent;
import com.redcraft.starlight.server.elements.SBullet;
import com.redcraft.starlight.server.elements.SEntity;
import com.redcraft.starlight.server.elements.SPlayer;
import com.redcraft.starlight.server.elements.SUniverse;
import com.redcraft.starlight.util.RMath;


import java.util.*;

public class SSpaceStation implements Processable {

    private static final float SHOOT_DELAY = 0.6f;
    private static final float MAX_DISTANCE = 1.5f;

    SUniverse universe;
    List<SSpaceStationPart> parts;
    Set<SPlayer> destroyers;
    float x, y;
    float lastShot;

    public SSpaceStation(SUniverse universe, float x, float y) {
        this.universe = universe;
        this.x = x;
        this.y = y;

        int[][] data = SSpaceStationGenerator2.generateSpaceStation();
        parts = SSpaceStationGenerator2.convert(data,0.25f,x,y);
        for(SSpaceStationPart part : parts) {
            universe.entityDistributor().addEntity(part);
        }
        destroyers = new HashSet<>();
    }

    @Override
    public void process(float dt) {
        lastShot -= dt;
        if(lastShot > 0) return;
        Iterator<SSpaceStationPart> itr = parts.iterator();
        while (itr.hasNext()){
            SSpaceStationPart part = itr.next();
            if(!part.exists()) {
                if(part.getDestroyer() != null) destroyers.add(part.getDestroyer());
                itr.remove();
                continue;
            }
            if(!(part instanceof SSpaceStationModule)) continue;
            SEntity target = getTarget((SSpaceStationModule) part);
            if(target == null) continue;
            // Vector2 targetPosition = calculateTargetPosition(target,part,0.1f,0.1f);
            shoot((SSpaceStationModule) part,target.getPosition());
        }
        lastShot = SHOOT_DELAY;
    }

    public SEntity getTarget(SSpaceStationModule module) {
        float nearestDst = MAX_DISTANCE;
        SEntity target = null;
        for(SPlayer player : universe.getPlayers()) {
            float currentDistance = RMath.modularDistance(player.getPosition(),module.getPosition(),new Vector2(universe.width(),universe.height()));
            if(currentDistance<nearestDst) {
                nearestDst = currentDistance;
                target = player;
            }
        }
        return target;
    }
    public Vector2 calculateTargetPosition(SEntity target, SEntity part, float speed, float bulletSpeed) {
        Vector2 direction = new Vector2(0f,-1f).rotateRad(target.getRotation()).nor();
        float dt = target.getPosition().sub(part.getPosition()).len() / bulletSpeed;
        return target.getPosition().add(direction.scl(dt*speed));
    }
    public void shoot(SSpaceStationModule part, Vector2 target) {
        Vector2 direction = RMath.nearestDirectionVector(part.getPosition(),target,new Vector2(universe.width(),universe.height()));
        direction.nor().scl(0.1f);
        SBullet bullet = new SBullet(UUID.randomUUID(),direction,part.getPosition(),part,1.5f);
        universe.entityDistributor().addEntity(bullet);
    }

    public boolean isDestroyed() {
        return parts.isEmpty();
    }
    public void distributeRewards() {
        for(SPlayer player : destroyers) {
            player.addHealth(0.3f);
            player.addAmmo(40);
            player.addScore(50);
        }
    }

    public void destroy() {
        universe.eventHandler().throwEvent(new QuickEvent("space_station_destroyed").set("players",destroyers));
        distributeRewards();
    }
}
