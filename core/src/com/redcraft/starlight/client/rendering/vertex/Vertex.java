package com.redcraft.starlight.client.rendering.vertex;

import com.badlogic.gdx.math.Matrix3;

public interface Vertex {
    void transform(Matrix3 transform);
    int stride();
    float[] toFloatArray();

    /**
     * Converts an array of vertices <b>of the same type</b> to a float array.
     */
    static float[] toFloatArray(Vertex[] vertices) {
        if(vertices.length==0) return new float[0];
        int stride = vertices[0].stride();

        float[] data = new float[vertices.length*stride];
        for(int i = 0; i < vertices.length; i++) {
            float[] vdata = vertices[i].toFloatArray();
            System.arraycopy(vdata,0,data,i*stride,stride);
        }
        return data;
    }
}
