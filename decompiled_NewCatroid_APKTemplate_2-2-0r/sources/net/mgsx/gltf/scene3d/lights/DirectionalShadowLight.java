package net.mgsx.gltf.scene3d.lights;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.environment.ShadowMap;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Disposable;

/* JADX INFO: loaded from: classes12.dex */
public class DirectionalShadowLight extends DirectionalLightEx implements ShadowMap, Disposable {
    protected static final float SQRT2 = (float) Math.sqrt(2.0d);
    protected Camera cam;
    protected final Vector3 center;
    protected FrameBuffer fbo;
    protected final TextureDescriptor textureDesc;
    protected final Vector3 tmpV;

    public boolean equals(DirectionalShadowLight directionalShadowLight) {
        return directionalShadowLight != null && directionalShadowLight == this;
    }

    public DirectionalShadowLight() {
        this(1024, 1024);
    }

    public DirectionalShadowLight(int i, int i2) {
        this(i, i2, 100.0f, 100.0f, 0.0f, 100.0f);
    }

    public DirectionalShadowLight(int i, int i2, float f, float f2, float f3, float f4) {
        this.tmpV = new Vector3();
        this.center = new Vector3();
        this.fbo = createFrameBuffer(i, i2);
        OrthographicCamera orthographicCamera = new OrthographicCamera(f, f2);
        this.cam = orthographicCamera;
        orthographicCamera.near = f3;
        this.cam.far = f4;
        TextureDescriptor textureDescriptor = new TextureDescriptor();
        this.textureDesc = textureDescriptor;
        Texture.TextureFilter textureFilter = Texture.TextureFilter.Nearest;
        textureDescriptor.magFilter = textureFilter;
        textureDescriptor.minFilter = textureFilter;
        Texture.TextureWrap textureWrap = Texture.TextureWrap.ClampToEdge;
        textureDescriptor.vWrap = textureWrap;
        textureDescriptor.uWrap = textureWrap;
    }

    public DirectionalShadowLight setShadowMapSize(int i, int i2) {
        FrameBuffer frameBuffer = this.fbo;
        if (frameBuffer == null || frameBuffer.getWidth() != i || this.fbo.getHeight() != i2) {
            FrameBuffer frameBuffer2 = this.fbo;
            if (frameBuffer2 != null) {
                frameBuffer2.dispose();
            }
            this.fbo = createFrameBuffer(i, i2);
        }
        return this;
    }

    protected FrameBuffer createFrameBuffer(int i, int i2) {
        return new FrameBuffer(Pixmap.Format.RGBA8888, i, i2, true);
    }

    public DirectionalShadowLight setViewport(float f, float f2, float f3, float f4) {
        this.cam.viewportWidth = f;
        this.cam.viewportHeight = f2;
        this.cam.near = f3;
        this.cam.far = f4;
        return this;
    }

    public DirectionalShadowLight setCenter(Vector3 vector3) {
        this.center.set(vector3);
        return this;
    }

    public Vector3 getCenter(Vector3 vector3) {
        return vector3.set(this.center);
    }

    public DirectionalShadowLight setCenter(float f, float f2, float f3) {
        this.center.set(f, f2, f3);
        return this;
    }

    public DirectionalShadowLight setBounds(BoundingBox boundingBox) {
        float width = boundingBox.getWidth();
        float height = boundingBox.getHeight();
        float fMax = Math.max(Math.max(width, height), boundingBox.getDepth()) * SQRT2;
        boundingBox.getCenter(this.center);
        return setViewport(fMax, fMax, 0.0f, fMax);
    }

    protected void validate() {
        this.cam.position.set(this.direction).scl(-(this.cam.near + ((this.cam.far - this.cam.near) * 0.5f))).add(this.center);
        this.cam.direction.set(this.direction).nor();
        this.cam.normalizeUp();
        this.cam.update();
    }

    public void begin() {
        validate();
        int width = this.fbo.getWidth();
        int height = this.fbo.getHeight();
        this.fbo.begin();
        Gdx.gl.glViewport(0, 0, width, height);
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(16640);
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(1, 1, width - 2, height - 2);
    }

    public void end() {
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        this.fbo.end();
    }

    public FrameBuffer getFrameBuffer() {
        return this.fbo;
    }

    public Camera getCamera() {
        return this.cam;
    }

    @Override // com.badlogic.gdx.graphics.g3d.environment.ShadowMap
    public Matrix4 getProjViewTrans() {
        return this.cam.combined;
    }

    @Override // com.badlogic.gdx.graphics.g3d.environment.ShadowMap
    public TextureDescriptor getDepthMap() {
        this.textureDesc.texture = this.fbo.getColorBufferTexture();
        return this.textureDesc;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        FrameBuffer frameBuffer = this.fbo;
        if (frameBuffer != null) {
            frameBuffer.dispose();
        }
        this.fbo = null;
    }

    @Override // net.mgsx.gltf.scene3d.lights.DirectionalLightEx
    public boolean equals(DirectionalLightEx directionalLightEx) {
        if (directionalLightEx instanceof DirectionalShadowLight) {
            return equals((DirectionalShadowLight) directionalLightEx);
        }
        return false;
    }
}
