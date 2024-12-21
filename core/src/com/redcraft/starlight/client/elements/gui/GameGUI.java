package com.redcraft.starlight.client.elements.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Disposable;
import com.redcraft.starlight.client.rendering.Drawable;
import com.redcraft.starlight.client.rendering.RenderSystem;

import java.util.HashMap;
import java.util.Map;

public class GameGUI implements Drawable, Disposable {
    Map<String,Drawable> elements;
    HealthBar health;

    public GameGUI() {
        this.elements = new HashMap<>();
        create();
    }

    private void create() {
        health = new HealthBar(null);
        addElement("health",health);
        addElement("deathscreen",new DeathScreen());
        addElement("score",new ScoreDisplay());
        addElement("shield",new ProgressBar(Gdx.graphics.getWidth() * 0.2f, 25,Gdx.graphics.getWidth()*0.125f,50, Color.CYAN));
        addElement("ammo",new AmmunitionDisplay());
    }

    public void draw(RenderSystem system) {
        for(Drawable d : elements.values()) d.draw(system);
    }

    public void addElement(String name, Drawable drawable) {
        this.elements.put(name,drawable);
    }
    public <T> T getElement(String name, Class<T> type) {
        return type.cast(this.elements.get(name));
    }

    public HealthBar getHealthBar() {
        return health;
    }

    @Override
    public void dispose() {
        for(Drawable d :  elements.values()) {
            if(d instanceof Disposable) ((Disposable) d).dispose();
        }
    }
}
