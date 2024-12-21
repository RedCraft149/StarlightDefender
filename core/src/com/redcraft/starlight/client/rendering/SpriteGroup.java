package com.redcraft.starlight.client.rendering;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.utils.Disposable;
import com.redcraft.starlight.client.rendering.vertex.Vertex;

import java.util.Arrays;

public class SpriteGroup implements Disposable {
    Texture texture;
    Mesh mesh;
    ShaderProgram program;
    VertexAttributes vertexAttributes;

    float[] buffer;
    int bufferIndex;

    public SpriteGroup(int sprites, Texture texture, VertexAttributes vertexAttributes, int stride) {
        this.texture = texture;
        this.vertexAttributes = vertexAttributes;
        createMesh(sprites);
        createRectangleIndices(sprites);
        buffer = new float[sprites*4*stride];
    }

    private void createRectangleIndices(int rectangles) {
        short[] indices = new short[rectangles*6];
        for (int i = 0; i < rectangles; i++) {
            int h = i*6;
            int k = i*4;
            indices[h  ] = (short) (k  );
            indices[h+1] = (short) (k+1);
            indices[h+2] = (short) (k+2);
            indices[h+3] = (short) (k+2);
            indices[h+4] = (short) (k+3);
            indices[h+5] = (short) (k  );
        }
        mesh.setIndices(indices);
    }
    private void createMesh(int rectangles) {
        mesh = new Mesh(true,rectangles*4,rectangles*6,vertexAttributes);
    }

    public void draw(Matrix3 transform, Vertex[] vertices) {
        for(Vertex vertex : vertices) vertex.transform(transform);
        float[] data = Vertex.toFloatArray(vertices);
        System.arraycopy(data,0,buffer,bufferIndex,data.length);
        bufferIndex += data.length;
    }
    public void flush() {
        mesh.setVertices(buffer);
        Arrays.fill(buffer,0f);
        bufferIndex = 0;
    }
    public void render() {
        if(program==null) return;
        texture.bind(0);
        program.setUniformi("u_texture",0);
        mesh.render(program, GL20.GL_TRIANGLES);
    }

    /**
     * SpriteGroup will not be responsible for disposing {@code program}.
     */
    public void linkShader(ShaderProgram program) {
        this.program = program;
    }
    public ShaderProgram getShader() {
        return program;
    }

    @Override
    public void dispose() {
        texture.dispose();
        mesh.dispose();
        buffer = null;
        bufferIndex = 0;
        vertexAttributes = null;
    }
}
