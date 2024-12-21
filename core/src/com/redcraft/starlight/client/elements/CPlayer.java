package com.redcraft.starlight.client.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.client.rendering.particles.Particle;
import com.redcraft.starlight.shared.Components;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.events.HealthChangeEvent;
import com.redcraft.rlib.events.EventHandler;
import com.redcraft.rlib.events.QuickEvent;

import java.util.*;

public class CPlayer extends CEntity {

    float health = 1f;
    int hurtTimer = 0;
    int score;
    int ammo = 100;
    List<ShieldHit> shieldHits;
    boolean shieldsUp;

    public CPlayer(UUID uuid) {
        super(uuid);

        setTexture("player");
        setScale(Shared.CLIENT.getFloat("player.scale"));

        recalculateTransformMatrix();

        shieldHits = new LinkedList<>();
    }

    public void draw(RenderSystem system) {
        //super
        interpolations();
        if(texture==null || texture.isEmpty()) return;

        if(hurtTimer<=0) system.sprites().draw(texture,transform);
        else {
            system.sprites().draw(texture,transform,0.7f,0.7f,0.7f,1f);
            hurtTimer--;
        }
        system.end(RenderSystem.SPRITES);
        system.begin(RenderSystem.ALPHA | RenderSystem.SPRITES | RenderSystem.WORLD_TRANSFORM | RenderSystem.WORLD_PROJECTION);

        drawShiedHits(system);

        drawParticles(system);
    }

    private void drawParticles(RenderSystem system) {
        Vector2 particlePosition =  new Vector2( 0.32f,0.3f).mul(transform);
        Vector2 particlePosition2 = new Vector2(-0.32f,0.3f).mul(transform);
        Vector2 particlePosition3 = new Vector2(-0.475f,0.25f).mul(transform);
        Vector2 particlePosition4 = new Vector2( 0.475f,0.25f).mul(transform);


        List<Vector2> positions = Arrays.asList(particlePosition,particlePosition2,particlePosition3,particlePosition4);
        for(Vector2 particle : positions) {
            Vector2 particleDirection = new Vector2(particle).sub(new Vector2(0,1f).mul(transform)).nor().scl(-1/256f);
            particleDirection.rotateRad((float) Math.random()*2-1);
            system.particles().addParticle(new Particle(0.2f,particle,
                    particleDirection,(p,f)->p.setAlpha(p.lifetime()/0.2f),
                    1/32f,"spark"));
        }
    }
    private void drawShiedHits(RenderSystem system) {
        system.begin(RenderSystem.SOLIDS | RenderSystem.WORLD_PROJECTION | RenderSystem.WORLD_TRANSFORM);
        system.solids().setColor(0f,0.8f,1f,0.3f);
        if(shieldsUp) system.solids().circle(position.x, position.y, 1/6f,32);

        Iterator<ShieldHit> itr = shieldHits.iterator();
        while (itr.hasNext()) {
            ShieldHit hit = itr.next();
            hit.time -= Gdx.graphics.getDeltaTime();
            if(hit.time <= 0f) itr.remove();
            else system.solids().circle(hit.x + position.x, hit.y + position.y, hit.time * 0.125f,16);
        }

        system.end(RenderSystem.SOLIDS);
    }

    public Matrix3 createWorldTransform(float projW, float projH) {
        float x = -getPosition().x+projW*0.5f;
        float y = -getPosition().y+projH*0.5f;
        if(hurtTimer > 0 && health > 0) {
            x += MathUtils.random(-1,1) * 0.002f * (float) Math.sqrt(hurtTimer);
            y += MathUtils.random(-1,1) * 0.002f * (float) Math.sqrt(hurtTimer);
        }
        return new Matrix3().idt().trn(x,y);
    }

    public void setHealth(float health) {
        if(this.health>health) hurtTimer = 30;
        get(Components.RESPONSIBLE_EVENT_HANDLER, EventHandler.class).throwEvent(new HealthChangeEvent(this,health,this.health));
        this.health = health;
    }
    public float getHealth() {
        return health;
    }
    public void setScore(int score) {
        this.score = score;
        get(Components.RESPONSIBLE_EVENT_HANDLER, EventHandler.class).throwEvent(
                new QuickEvent("score_changed").set("entity",this).set("score",score));
    }
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
    public int getAmmo() {
        return ammo;
    }

    public void addShieldHit(float x, float y, float time) {
        shieldHits.add(new ShieldHit(x,y,time*4f));
        System.out.println("SHIELD HIT");
    }
    public void setShieldsUp(boolean shields) {
        this.shieldsUp = shields;
    }

    private static class ShieldHit {
        float x, y, time;

        public ShieldHit(float x, float y, float time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }
    }

}
