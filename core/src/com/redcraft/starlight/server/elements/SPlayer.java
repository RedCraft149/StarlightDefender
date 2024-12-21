package com.redcraft.starlight.server.elements;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.server.SConstants;
import com.redcraft.starlight.server.collision.CircularShape;
import com.redcraft.starlight.shared.Components;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.events.HealthChangeEvent;
import com.redcraft.starlight.shared.events.PlayerDeathEvent;
import com.redcraft.starlight.shared.events.PlayerDeathTimerChangeEvent;
import com.redcraft.rlib.Processable;
import com.redcraft.rlib.events.EventHandler;
import com.redcraft.rlib.events.QuickEvent;

import java.util.UUID;

public class SPlayer extends SCollisionEntity implements Processable {

    public static final int REGENERATION_SCORE = 200;

    public float health;
    public float deathTimer = 0f;
    public int score;
    public int ammo = 100;
    public float ammoGainTimer = 5f;

    public float shieldsUpTime;
    public boolean shieldsUp;

    public SUniverse universe;

    public SPlayer(UUID uuid, SUniverse universe) {
        super(uuid, new CircularShape(0f,0f,1/8f));
        health = 1f;
        this.universe = universe;
    }

    public void setHealth(float newHealth) {
        Shared.SERVER.get(SConstants.gameEventHandler, EventHandler.class).throwEvent(new HealthChangeEvent(this,newHealth,health));
        this.health = newHealth;
    }

    public void addHealth(float delta) {
        setHealth(health+delta);
        if(health <= 0f) {
            Components.getResponsibleEventHandler(getComponents()).throwEvent(new PlayerDeathEvent(this));
            deathTimer = 5f;
            setScore(0);
        }
    }

    public void damage(float damage, float x, float y) {
        if(isDead()) return;
        if(deathTimer > -2.5f) return;
        if(shieldsUpTime > 0f && shieldsUp) {
            Components.getResponsibleEventHandler(getComponents()).throwEvent(
                    new QuickEvent("player_shield_hit").set("x", x - position.x).set("y", y - position.y).set("time", -damage).set("entity", this));
            shieldsUpTime += damage * 4f; //damage is always negative
        } else {
            addHealth(damage);
        }
    }

    public boolean isDead() {
        return health <= 0f;
    }

    public void addScore(int delta) {
        setScore(this.score+delta);
        if(!shieldsUp) addShieldsUpTime(delta * 0.1f);
    }
    public void setScore(int score) {
        if(score / REGENERATION_SCORE > this.score / REGENERATION_SCORE && score >= REGENERATION_SCORE) {
            setHealth(1f);
        }
        this.score = score;
        Components.getResponsibleEventHandler(getComponents()).throwEvent(
                new QuickEvent("score_changed").set("entity",this).set("score",score));
    }
    public int getScore() {
        return score;
    }

    public void addShieldsUpTime(float seconds) {
        shieldsUpTime += seconds;
        if(shieldsUpTime > 10f) shieldsUpTime = 10f;
        throwEvent(new QuickEvent("player_shield_time").set("time",shieldsUpTime).set("active",shieldsUp).set("entity",this));
    }

    public void raiseShields() {
        shieldsUp = true;
    }

    public void shoot() {
        if(isDead()) return;
        if(ammo <= 0) return;
        Vector2 direction = new Vector2(0f,-1f).rotateRad(getRotation());
        Vector2 position = getPosition().add(new Vector2(direction).scl(0.25f));
        universe.entityDistributor().addEntity(new SBullet(UUID.randomUUID(),direction.scl(0.1f),position,this,5f));
        setAmmo(ammo-1);
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
        throwEvent(new QuickEvent("ammo_changed").set("ammo",ammo).set("entity",this));
    }
    public void addAmmo(int delta) {
        setAmmo(ammo + delta);
    }
    public int getAmmo() {
        return ammo;
    }

    public void respawn() {
        setHealth(1f);
        setAmmo(100);
    }

    @Override
    public void process(float dt) {
        if(deathTimer > 0f) {
            throwEvent(new PlayerDeathTimerChangeEvent(this,deathTimer - dt));
        }
        deathTimer -= dt;
        if(shieldsUpTime > 0f && shieldsUp) {
            shieldsUpTime -= dt;
            throwEvent(new QuickEvent("player_shield_time").set("time",shieldsUpTime).set("active",shieldsUp).set("entity",this));
        } else if(shieldsUp) {
            shieldsUp = false;
            throwEvent(new QuickEvent("player_shield_time").set("time",0f).set("active",false).set("entity",this));
        }

        ammoGainTimer -= dt;
        if(ammoGainTimer < 0f) {
            ammoGainTimer = 5f;
            if(ammo < 10) addAmmo(1);
        }
    }
}
