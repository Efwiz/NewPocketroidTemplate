package com.badlogic.gdx.graphics.g3d.particles.batches;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleShader;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.graphics.g3d.particles.renderers.PointSpriteControllerRenderData;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/batches/PointSpriteParticleBatch.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class PointSpriteParticleBatch extends BufferedParticleBatch<PointSpriteControllerRenderData> {
    protected static final VertexAttributes CPU_ATTRIBUTES;
    protected static final int CPU_COLOR_OFFSET;
    protected static final int CPU_POSITION_OFFSET;
    protected static final int CPU_REGION_OFFSET;
    protected static final int CPU_SIZE_AND_ROTATION_OFFSET;
    protected static final int CPU_VERTEX_SIZE;
    protected static final Vector3 TMP_V1 = new Vector3();
    private static boolean pointSpritesEnabled = false;
    protected static final int sizeAndRotationUsage = 512;
    protected BlendingAttribute blendingAttribute;
    protected DepthTestAttribute depthTestAttribute;
    Renderable renderable;
    private float[] vertices;

    static {
        VertexAttributes vertexAttributes = new VertexAttributes(new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(2, 4, ShaderProgram.COLOR_ATTRIBUTE), new VertexAttribute(16, 4, "a_region"), new VertexAttribute(512, 3, "a_sizeAndRotation"));
        CPU_ATTRIBUTES = vertexAttributes;
        CPU_VERTEX_SIZE = (short) (vertexAttributes.vertexSize / 4);
        CPU_POSITION_OFFSET = (short) (vertexAttributes.findByUsage(1).offset / 4);
        CPU_COLOR_OFFSET = (short) (vertexAttributes.findByUsage(2).offset / 4);
        CPU_REGION_OFFSET = (short) (vertexAttributes.findByUsage(16).offset / 4);
        CPU_SIZE_AND_ROTATION_OFFSET = (short) (vertexAttributes.findByUsage(512).offset / 4);
    }

    private static void enablePointSprites() {
        Gdx.gl.glEnable(GL20.GL_VERTEX_PROGRAM_POINT_SIZE);
        if (Gdx.f82app.getType() == Application.ApplicationType.Desktop) {
            Gdx.gl.glEnable(34913);
        }
        pointSpritesEnabled = true;
    }

    public PointSpriteParticleBatch() {
        this(1000);
    }

    public PointSpriteParticleBatch(int i) {
        this(i, new ParticleShader.Config(ParticleShader.ParticleType.Point));
    }

    public PointSpriteParticleBatch(int i, ParticleShader.Config config) {
        this(i, config, null, null);
    }

    public PointSpriteParticleBatch(int i, ParticleShader.Config config, BlendingAttribute blendingAttribute, DepthTestAttribute depthTestAttribute) {
        super(PointSpriteControllerRenderData.class);
        if (!pointSpritesEnabled) {
            enablePointSprites();
        }
        this.blendingAttribute = blendingAttribute;
        this.depthTestAttribute = depthTestAttribute;
        if (blendingAttribute == null) {
            this.blendingAttribute = new BlendingAttribute(1, GL20.GL_ONE_MINUS_SRC_ALPHA, 1.0f);
        }
        if (this.depthTestAttribute == null) {
            this.depthTestAttribute = new DepthTestAttribute(515, false);
        }
        allocRenderable();
        ensureCapacity(i);
        this.renderable.shader = new ParticleShader(this.renderable, config);
        this.renderable.shader.init();
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.batches.BufferedParticleBatch
    protected void allocParticlesData(int i) {
        this.vertices = new float[CPU_VERTEX_SIZE * i];
        if (this.renderable.meshPart.mesh != null) {
            this.renderable.meshPart.mesh.dispose();
        }
        this.renderable.meshPart.mesh = new Mesh(false, i, 0, CPU_ATTRIBUTES);
    }

    protected void allocRenderable() {
        Renderable renderable = new Renderable();
        this.renderable = renderable;
        renderable.meshPart.primitiveType = 0;
        this.renderable.meshPart.offset = 0;
        this.renderable.material = new Material(this.blendingAttribute, this.depthTestAttribute, TextureAttribute.createDiffuse((Texture) null));
    }

    public void setTexture(Texture texture) {
        ((TextureAttribute) this.renderable.material.get(TextureAttribute.Diffuse)).textureDescription.texture = texture;
    }

    public Texture getTexture() {
        return (Texture) ((TextureAttribute) this.renderable.material.get(TextureAttribute.Diffuse)).textureDescription.texture;
    }

    public BlendingAttribute getBlendingAttribute() {
        return this.blendingAttribute;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.badlogic.gdx.graphics.g3d.particles.batches.BufferedParticleBatch
    protected void flush(int[] iArr) {
        Array.ArrayIterator it = this.renderData.iterator();
        int i = 0;
        while (it.hasNext()) {
            PointSpriteControllerRenderData pointSpriteControllerRenderData = (PointSpriteControllerRenderData) it.next();
            ParallelArray.FloatChannel floatChannel = pointSpriteControllerRenderData.scaleChannel;
            ParallelArray.FloatChannel floatChannel2 = pointSpriteControllerRenderData.regionChannel;
            ParallelArray.FloatChannel floatChannel3 = pointSpriteControllerRenderData.positionChannel;
            ParallelArray.FloatChannel floatChannel4 = pointSpriteControllerRenderData.colorChannel;
            ParallelArray.FloatChannel floatChannel5 = pointSpriteControllerRenderData.rotationChannel;
            int i2 = 0;
            while (i2 < pointSpriteControllerRenderData.controller.particles.size) {
                int i3 = iArr[i] * CPU_VERTEX_SIZE;
                int i4 = floatChannel2.strideSize * i2;
                int i5 = floatChannel3.strideSize * i2;
                int i6 = floatChannel4.strideSize * i2;
                int i7 = floatChannel5.strideSize * i2;
                float[] fArr = this.vertices;
                int i8 = CPU_POSITION_OFFSET;
                Array.ArrayIterator arrayIterator = it;
                fArr[i3 + i8] = floatChannel3.data[i5];
                this.vertices[i3 + i8 + 1] = floatChannel3.data[i5 + 1];
                this.vertices[i3 + i8 + 2] = floatChannel3.data[i5 + 2];
                float[] fArr2 = this.vertices;
                int i9 = CPU_COLOR_OFFSET;
                fArr2[i3 + i9] = floatChannel4.data[i6];
                this.vertices[i3 + i9 + 1] = floatChannel4.data[i6 + 1];
                this.vertices[i3 + i9 + 2] = floatChannel4.data[i6 + 2];
                this.vertices[i9 + i3 + 3] = floatChannel4.data[i6 + 3];
                float[] fArr3 = this.vertices;
                int i10 = CPU_SIZE_AND_ROTATION_OFFSET;
                fArr3[i3 + i10] = floatChannel.data[floatChannel.strideSize * i2];
                this.vertices[i3 + i10 + 1] = floatChannel5.data[i7];
                this.vertices[i10 + i3 + 2] = floatChannel5.data[i7 + 1];
                float[] fArr4 = this.vertices;
                int i11 = CPU_REGION_OFFSET;
                fArr4[i3 + i11] = floatChannel2.data[i4];
                this.vertices[i3 + i11 + 1] = floatChannel2.data[i4 + 1];
                this.vertices[i3 + i11 + 2] = floatChannel2.data[i4 + 2];
                this.vertices[i3 + i11 + 3] = floatChannel2.data[i4 + 3];
                i2++;
                i++;
                pointSpriteControllerRenderData = pointSpriteControllerRenderData;
                it = arrayIterator;
            }
        }
        this.renderable.meshPart.size = this.bufferedParticlesCount;
        this.renderable.meshPart.mesh.setVertices(this.vertices, 0, this.bufferedParticlesCount * CPU_VERTEX_SIZE);
        this.renderable.meshPart.update();
    }

    @Override // com.badlogic.gdx.graphics.g3d.RenderableProvider
    public void getRenderables(Array<Renderable> array, Pool<Renderable> pool) {
        if (this.bufferedParticlesCount > 0) {
            array.add(pool.obtain().set(this.renderable));
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch, com.badlogic.gdx.graphics.g3d.particles.ResourceData.Configurable
    public void save(AssetManager assetManager, ResourceData resourceData) {
        resourceData.createSaveData("pointSpriteBatch").saveAsset(assetManager.getAssetFileName(getTexture()), Texture.class);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch, com.badlogic.gdx.graphics.g3d.particles.ResourceData.Configurable
    public void load(AssetManager assetManager, ResourceData resourceData) {
        ResourceData.SaveData saveData = resourceData.getSaveData("pointSpriteBatch");
        if (saveData != null) {
            setTexture((Texture) assetManager.get(saveData.loadAsset()));
        }
    }
}
