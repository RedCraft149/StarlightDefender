package com.redcraft.starlight.client.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.utils.OrderedMap;
import com.redcraft.starlight.client.rendering.vertex.PositionTextureColorVertex;
import com.redcraft.starlight.client.rendering.vertex.Vertex;
import com.redcraft.starlight.util.Files;

import java.util.HashMap;
import java.util.Map;

public class SpriteManager {
    Map<String, ShaderProgram> shaders;
    Map<String, VertexAttributes> vertexAttributes;
    OrderedMap<String, SpriteGroup> groups;

    ShaderProgram currentShader;

    public SpriteManager() {
        shaders = new HashMap<>();
        vertexAttributes = new HashMap<>();
        groups = new OrderedMap<>();
        currentShader = null;
        createDefaults();
    }

    private void createDefaults() {
        shaders.put("default", Files.shader("default"));
        vertexAttributes.put("default", new VertexAttributes(new VertexAttribute(-1, 2, "a_position"), VertexAttribute.TexCoords(0), VertexAttribute.ColorUnpacked()));
    }

    public SpriteGroup add(String name, String texture, String shader, String vertexAttributes, int maxSprites, int stride) {
        Texture tex = new Texture(Gdx.files.internal("textures/" + texture + ".png"));
        VertexAttributes va = this.vertexAttributes.get(vertexAttributes);
        ShaderProgram shaderProgram = shaders.get(shader);

        SpriteGroup group = new SpriteGroup(maxSprites, tex, va, stride);
        group.linkShader(shaderProgram);
        groups.put(name, group);
        return group;
    }

    public SpriteGroup add(String name, String texture, int maxSprites) {
        return add(name, texture, "default", "default", maxSprites, 8);
    }

    public SpriteGroup get(String name) {
        return groups.get(name);
    }

    public boolean has(String name) {
        return groups.containsKey(name);
    }

    public void flush(String name) {
        if (!has(name)) return;
        get(name).flush();
    }

    public void draw(String name, Matrix3 transform, Vertex[] vertices) {
        if (!has(name)) return;
        get(name).draw(transform,vertices);
    }
    public void draw(String name, Matrix3 transform) {
        draw(name,transform, PositionTextureColorVertex.postionTextureColorSprite());
    }
    public void draw(String name, Matrix3 transform, float alpha) {
        draw(name,transform, PositionTextureColorVertex.postionTextureColorSprite(alpha));
    }
    public void draw(String name, Matrix3 transform, float r, float g, float b, float a) {
        draw(name,transform, PositionTextureColorVertex.postionTextureColorSprite(r,g,b,a));
    }
    public void render(String name, RenderSystem renderSystem) {
        if(!has(name)) return;
        SpriteGroup group = groups.get(name);
        if(group.getShader() != currentShader) {
            currentShader = group.getShader();
            renderSystem.bindSpriteShader(currentShader);
        }
        groups.get(name).render();
    }
    public void flushAll() {
        for(SpriteGroup group : groups.values()) group.flush();
    }
    public void renderAll(RenderSystem system) {
        for(String group : groups.keys()) render(group,system);
    }
    public void dispose() {
        for(SpriteGroup group : groups.values()) group.dispose();
        groups.clear();
        for(ShaderProgram shader : shaders.values()) shader.dispose();
        shaders.clear();
        vertexAttributes.clear();
    }
}
