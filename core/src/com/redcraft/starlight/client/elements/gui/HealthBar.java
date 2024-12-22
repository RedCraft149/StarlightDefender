package com.redcraft.starlight.client.elements.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.events.HealthChangeEvent;
import com.redcraft.rlib.events.EventHandler;
import com.redcraft.rlib.events.EventListener;
import com.redcraft.rlib.events.Listener;

import java.util.UUID;

public class HealthBar extends ProgressBar implements Listener {

    UUID target;

    public HealthBar(UUID target) {
        super(Gdx.graphics.getWidth()/2f,25f,Gdx.graphics.getWidth()/2f,50f);
        setOffColor(Color.DARK_GRAY);
        setProgress(1f);

        this.target = target;

        Shared.CLIENT.get(CComponents.gameEventHandler, EventHandler.class).register(this);
    }

    public void setTarget(UUID target) {
        this.target = target;

    }

    @EventListener
    public void onHealthChanged(HealthChangeEvent event) {
        if(event.getEntity().getUUID().equals(target)) this.setProgress(event.getNewHealth());
    }
}
