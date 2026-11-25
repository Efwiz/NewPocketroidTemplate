package com.crashinvaders.vfx.utils;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/* JADX INFO: loaded from: classes8.dex */
public class ViewportQuadMesh {
    private static final int U1 = 2;
    private static final int U2 = 6;
    private static final int U3 = 10;
    private static final int U4 = 14;
    private static final int V1 = 3;
    private static final int V2 = 7;
    private static final int V3 = 11;
    private static final int V4 = 15;
    private static final int VERT_SIZE = 16;
    private static final int X1 = 0;
    private static final int X2 = 4;
    private static final int X3 = 8;
    private static final int X4 = 12;
    private static final int Y1 = 1;
    private static final int Y2 = 5;
    private static final int Y3 = 9;
    private static final int Y4 = 13;
    private static final float[] verts = {-1.0f, -1.0f, 0.0f, 0.0f, 1.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 0.0f, 1.0f};
    private final Mesh mesh;

    public ViewportQuadMesh() {
        this(new VertexAttribute(1, 2, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(16, 2, "a_texCoord0"));
    }

    public ViewportQuadMesh(VertexAttribute... vertexAttributeArr) {
        Mesh mesh = new Mesh(true, 4, 0, vertexAttributeArr);
        this.mesh = mesh;
        mesh.setVertices(verts);
    }

    public void dispose() {
        this.mesh.dispose();
    }

    public void render(ShaderProgram shaderProgram) {
        this.mesh.render(shaderProgram, 6, 0, 4);
    }

    public Mesh getMesh() {
        return this.mesh;
    }
}
