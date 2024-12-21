package com.redcraft.starlight.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Files {
    public static Texture texture(String name) {
        name = name.replace('.','/');
        return new Texture(Gdx.files.internal("textures/"+name+".png"));
    }
    public static ShaderProgram shader(String name) {
        return new ShaderProgram(Gdx.files.internal("shaders/"+name+".vert"),Gdx.files.internal("shaders/"+name+".frag"));
    }
    public static BitmapFont font(String name, int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/"+name+".ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        return generator.generateFont(parameter);
    }
}
