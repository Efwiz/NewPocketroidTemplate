package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pool;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/utils/ShapeCache.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ShapeCache implements Disposable, RenderableProvider {
    private final MeshBuilder builder;
    private boolean building;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private final String f115id;
    private final Mesh mesh;
    private final Renderable renderable;

    public ShapeCache() {
        this(5000, 5000, new VertexAttributes(new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(4, 4, ShaderProgram.COLOR_ATTRIBUTE)), 1);
    }

    public ShapeCache(int i, int i2, VertexAttributes vertexAttributes, int i3) {
        this.f115id = "id";
        Renderable renderable = new Renderable();
        this.renderable = renderable;
        Mesh mesh = new Mesh(false, i, i2, vertexAttributes);
        this.mesh = mesh;
        this.builder = new MeshBuilder();
        renderable.meshPart.mesh = mesh;
        renderable.meshPart.primitiveType = i3;
        renderable.material = new Material();
    }

    public MeshPartBuilder begin() {
        return begin(1);
    }

    public MeshPartBuilder begin(int i) {
        if (this.building) {
            throw new GdxRuntimeException("Call end() after calling begin()");
        }
        this.building = true;
        this.builder.begin(this.mesh.getVertexAttributes());
        this.builder.part("id", i, this.renderable.meshPart);
        return this.builder;
    }

    public void end() {
        if (!this.building) {
            throw new GdxRuntimeException("Call begin() prior to calling end()");
        }
        this.building = false;
        this.builder.end(this.mesh);
    }

    @Override // com.badlogic.gdx.graphics.g3d.RenderableProvider
    public void getRenderables(Array<Renderable> array, Pool<Renderable> pool) {
        array.add(this.renderable);
    }

    public Material getMaterial() {
        return this.renderable.material;
    }

    public Matrix4 getWorldTransform() {
        return this.renderable.worldTransform;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.mesh.dispose();
    }
}
