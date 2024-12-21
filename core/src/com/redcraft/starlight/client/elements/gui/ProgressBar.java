package com.redcraft.starlight.client.elements.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.redcraft.starlight.client.rendering.Drawable;
import com.redcraft.starlight.client.rendering.RenderSystem;

public class ProgressBar implements Drawable {

    float progress;
    float width;
    float height;
    float x, y;

    Color on = Color.RED;
    Color off = new Color(0.1f,0.1f,0.1f,1f);

    public ProgressBar(float width, float height, float x, float y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public ProgressBar(float width, float height, float x, float y, Color on) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.on = on;
    }

    public void setOnColor(Color color) {
        this.on = color;
    }
    public void setOffColor(Color color) {
        this.off = color;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
    public float progress() {
        return MathUtils.clamp(progress,0,1);
    }

    public void draw(RenderSystem system) {
        system.begin(RenderSystem.SOLIDS);
        system.solids().setColor(off);
        system.solids().rect(x-width/2f,y-height/2f,width,height);

        system.solids().setColor(on);
        system.solids().rect(x-width/2f,y-height/2f,width*progress(),height);
        system.end(RenderSystem.SOLIDS);
    }
}
