package com.redcraft.starlight.client.effects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.CEntity;
import com.redcraft.starlight.client.events.ElementDestroyEvent;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.client.rendering.particles.Particle;
import com.redcraft.starlight.client.rendering.particles.ParticleSystem;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.rlib.events.EventListener;
import com.redcraft.rlib.events.Listener;

public class ElementDestruction implements Listener {
    @EventListener
    public void onElementDestroyed(ElementDestroyEvent event) {
        spawnParticles(event.particles,(CEntity) event.getEntity());
        if(event.playSound) Shared.CLIENT.get(CComponents.soundSystem,SoundSystem.class).play("explosion",1f);
    }

    public void spawnParticles(int amount, CEntity entity) {
        ParticleSystem system = Shared.CLIENT.get(CComponents.renderSystem, RenderSystem.class).particles();
        for(int i = 0; i < amount; i++) {
            double angle = Math.random()*Math.PI*2;
            float dx = (float) Math.cos(angle);
            float dy = (float) Math.sin(angle);
            system.addParticle(new Particle(
                    0.2f,
                    entity.getPosition().add(new Vector2(dx, dy).scl(entity.getScale()*0.5f)),
                    new Vector2(dx, dy).scl(0.005f),
                    (p,f)->p.setAlpha(p.lifetime()/0.2f),
                    1/64f* MathUtils.random()*2,
                    "spark"
            ));
        }
    }
}
