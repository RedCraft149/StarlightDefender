package com.redcraft.starlight.server.elements.ai;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.server.elements.SBullet;
import com.redcraft.starlight.server.elements.SEnemy;
import com.redcraft.starlight.shared.world.Universe;

import java.util.UUID;

public class AdvancedAIController extends BasicAIController {

    long lastShot = 0;
    Universe universe;
    boolean inRange = false;

    public AdvancedAIController(SEnemy object, Universe universe) {
        super(object);
        this.universe = universe;
    }

    @Override
    public void process(float dst, Vector2 D, Vector2... O) {
        inRange = object.getPosition().dst(D) < 1f;
        super.process(dst,D,O);
    }

    @Override
    public void updateVelocity(Vector2 goal) {
        Vector2 dif = new Vector2(goal).sub(velocity);
        dif.scl(0.2f);
        velocity.add(dif);
        if(dif.len() < 0.001 && inRange) randomShoot();
    }

    public void randomShoot() {
       if(System.currentTimeMillis() - lastShot < 400) return;
       if(!MathUtils.randomBoolean(0.25f)) return;
       //conditions
       lastShot = System.currentTimeMillis();
       Vector2 direction = new Vector2(0f,-1f).rotateRad(object.getRotation());
       Vector2 position = object.getPosition().add(new Vector2(direction).scl(0.25f));
       universe.entityDistributor().addEntity(new SBullet(UUID.randomUUID(),direction.scl(0.1f),position,object,5f));
    }
}
