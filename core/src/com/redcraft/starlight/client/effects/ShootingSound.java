package com.redcraft.starlight.client.effects;

import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.events.PlayerShootEvent;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.rlib.events.EventListener;
import com.redcraft.rlib.events.Listener;

public class ShootingSound implements Listener {
    @EventListener
    public void onPlayerShoot(PlayerShootEvent event) {
        Shared.CLIENT.get(CComponents.soundSystem,SoundSystem.class).play("shot",1f);
    }
}
