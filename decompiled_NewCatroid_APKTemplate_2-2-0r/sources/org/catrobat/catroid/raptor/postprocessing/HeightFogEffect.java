package org.catrobat.catroid.raptor.postprocessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.crashinvaders.vfx.effects.ShaderVfxEffect;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/postprocessing/HeightFogEffect.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HeightFogEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private static final String FSH = Gdx.files.internal("shaders/height_fog.frag").readString();
    private static final String VSH = "attribute vec4 a_position;\nattribute vec2 a_texCoord0;\nvarying vec2 v_texCoords;\nvoid main() {\n    v_texCoords = a_texCoord0;\n    gl_Position = a_position;\n}";
    private Camera camera;
    private Texture depthTexture;
    public Color fogColor;
    public float fogDensity;
    public float fogHeight;
    public float heightFalloff;
    public Matrix4 tmpMat;
    public Matrix4 tmpMat2;

    public HeightFogEffect() {
        super(new ShaderProgram(VSH, FSH));
        this.fogColor = new Color(0.5f, 0.6f, 0.7f, 1.0f);
        this.fogDensity = 0.01f;
        this.heightFalloff = 0.05f;
        this.fogHeight = 0.0f;
        this.tmpMat = new Matrix4();
        this.tmpMat2 = new Matrix4();
        if (this.program.isCompiled()) {
            return;
        }
        Gdx.f82app.error("HeightFog", this.program.getLog());
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setDepthTexture(Texture depthTexture) {
        this.depthTexture = depthTexture;
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
        this.program.setUniformi("u_texture0", 0);
        this.program.setUniformi("u_depthTexture", 1);
        if (this.camera != null) {
            this.program.setUniformMatrix("u_invProjection", this.tmpMat.set(this.camera.projection).inv());
            this.program.setUniformMatrix("u_invView", this.tmpMat2.set(this.camera.view).inv());
            this.program.setUniformf("u_cameraPos", this.camera.position);
            this.program.setUniformf("u_farPlane", this.camera.far);
        }
        this.program.setUniformf("u_fogColor", this.fogColor.r, this.fogColor.g, this.fogColor.b);
        this.program.setUniformf("u_fogDensity", this.fogDensity);
        this.program.setUniformf("u_heightFalloff", this.heightFalloff);
        this.program.setUniformf("u_fogHeight", this.fogHeight);
        this.program.end();
        renderShader(context, dstBuffer);
    }
}
