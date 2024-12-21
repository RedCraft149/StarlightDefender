package com.redcraft.starlight.client.elements;

import com.redcraft.starlight.client.rendering.RenderSystem;

import java.util.UUID;

public class CBullet extends CEntity {
    public CBullet(UUID uuid) {
        super(uuid);

        setTexture("bullet");
        setScale(.05f);

        recalculateTransformMatrix();
    }



    public void draw(RenderSystem system) {
        super.draw(system);
        //system.lines().circle(position.x,position.y,0.025f,8);
    }
}
