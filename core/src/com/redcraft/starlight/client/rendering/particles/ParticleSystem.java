package com.redcraft.starlight.client.rendering.particles;

import com.redcraft.starlight.client.rendering.Drawable;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.rlib.Processable;
import com.redcraft.rlib.function.Predicate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticleSystem implements Drawable, Processable {
    List<Particle> particles;

    public ParticleSystem() {
        particles = new ArrayList<>();
    }

    public void addParticle(Particle particle) {
        this.particles.add(particle);
    }

    public void cancelParticles(Predicate<Particle> predicate) {
        Iterator<Particle> itr = particles.iterator();
        while (itr.hasNext()) {
            if(predicate.test(itr.next())) itr.remove();
        }
    }
    @Override
    public void draw(RenderSystem system) {
        for(Particle particle : particles) particle.draw(system);
    }

    @Override
    public void process(float dt) {
        Iterator<Particle> itr = particles.iterator();
        while (itr.hasNext()) {
            Particle particle = itr.next();
            if(particle.lifetime <= 0f) itr.remove();
            else particle.process(dt);
        }
    }
}
