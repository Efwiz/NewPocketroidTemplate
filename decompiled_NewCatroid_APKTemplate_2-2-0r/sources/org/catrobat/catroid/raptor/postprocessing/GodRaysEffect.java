package org.catrobat.catroid.raptor.postprocessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.crashinvaders.vfx.effects.ShaderVfxEffect;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/postprocessing/GodRaysEffect.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GodRaysEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private static final String FSH = Gdx.files.internal("shaders/god_rays.frag").readString();
    private static final String VSH = "attribute vec4 a_position; attribute vec2 a_texCoord0; varying vec2 v_texCoords; void main() { v_texCoords = a_texCoord0; gl_Position = a_position; }";
    private Camera camera;
    public float decay;
    public float density;
    private Texture depthTexture;
    public float exposure;
    private Vector3 sunDirection;
    public float weight;

    public GodRaysEffect() {
        super(new ShaderProgram(VSH, FSH));
        this.sunDirection = new Vector3(1.0f, -1.5f, 1.0f);
        this.exposure = 0.3f;
        this.decay = 0.95f;
        this.density = 0.8f;
        this.weight = 0.6f;
        if (this.program.isCompiled()) {
            return;
        }
        Gdx.f82app.error("GodRays", this.program.getLog());
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setDepthTexture(Texture depthTexture) {
        this.depthTexture = depthTexture;
    }

    public void setSunDirection(Vector3 dir) {
        this.sunDirection.set(dir);
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.program.begin();
        this.program.setUniformi("u_texture0", 0);
        this.program.setUniformi("u_depthTexture", 1);
        this.program.end();
    }

    @Override // com.crashinvaders.vfx.effects.ChainVfxEffect
    public void render(VfxRenderContext context, VfxPingPongWrapper buffers) {
        VfxFrameBuffer srcBuffer = buffers.getSrcBuffer();
        VfxFrameBuffer dstBuffer = buffers.getDstBuffer();
        this.program.begin();
        Texture texture = this.depthTexture;
        if (texture != null) {
            texture.bind(1);
        }
        srcBuffer.getTexture().bind(0);
        if (this.camera != null) {
            Vector3 vector3 = new Vector3(new Vector3(this.camera.position).sub(new Vector3(this.sunDirection).scl(1000.0f)));
            this.camera.project(vector3);
            float width = vector3.x / Gdx.graphics.getWidth();
            float height = vector3.y / Gdx.graphics.getHeight();
            if (vector3.z < 0.0f) {
                width = -100.0f;
                height = -100.0f;
            }
            this.program.setUniformf("u_sunPos", width, height);
            this.program.setUniformf("u_farPlane", this.camera.far);
        }
        this.program.setUniformf("u_exposure", this.exposure);
        this.program.setUniformf("u_decay", this.decay);
        this.program.setUniformf("u_density", this.density);
        this.program.setUniformf("u_weight", this.weight);
        this.program.end();
        renderShader(context, dstBuffer);
    }
}
