package com.redcraft.starlight.client.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.redcraft.starlight.client.rendering.RenderSystem;

public class StarBackground implements Disposable {

    private static final Color[] COLORS = new Color[]{Color.BLUE,Color.YELLOW,Color.RED,Color.CYAN,Color.ORANGE,Color.WHITE};

    Texture texture;
    Pixmap pixmap;

    public StarBackground(float density) {
        create(density);
    }

    public void create(float density) {
        pixmap = new Pixmap(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        for(int i = 0; i < Gdx.graphics.getWidth()*Gdx.graphics.getHeight()*density; i++) {
            int x = MathUtils.random(Gdx.graphics.getWidth()-1);
            int y = MathUtils.random(Gdx.graphics.getHeight()-1);
            int color = MathUtils.random(COLORS.length-1);
            pixmap.drawPixel(x,y,Color.rgba8888(COLORS[color]));
        }
        texture = new Texture(pixmap);
    }

    public void render(RenderSystem system) {
        system.begin(RenderSystem.OVERLAY);
        system.overlays().draw(texture,0,0);
        system.end(RenderSystem.OVERLAY);
    }

    @Override
    public void dispose() {
        pixmap.dispose();
        texture.dispose();
    }
}
