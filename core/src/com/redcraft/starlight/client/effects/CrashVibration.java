package com.redcraft.starlight.client.effects;

import com.badlogic.gdx.Gdx;
import com.redcraft.starlight.shared.events.HealthChangeEvent;
import com.redcraft.rlib.events.EventListener;
import com.redcraft.rlib.events.Listener;

public class CrashVibration implements Listener {
    @EventListener
    public void onDamage(HealthChangeEvent event) {
        if(event.getNewHealth() >= event.getOldHealth()) return;
        Gdx.input.vibrate(100);
    }
}
