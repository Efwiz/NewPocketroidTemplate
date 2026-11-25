package org.catrobat.catroid.raptor.postprocessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.crashinvaders.vfx.effects.ShaderVfxEffect;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/postprocessing/DepthOfFieldEffect.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DepthOfFieldEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private static final String FSH = Gdx.files.internal("shaders/dof.frag").readString();
    private static final String VSH = "attribute vec4 a_position;\nattribute vec2 a_texCoord0;\nvarying vec2 v_texCoords;\nvoid main() {\n    v_texCoords = a_texCoord0;\n    gl_Position = a_position;\n}";
    public float blurSize;
    private Camera camera;
    private Texture depthTexture;
    public float focusDistance;
    public float focusRange;
    public float transition;

    public DepthOfFieldEffect() {
        super(new ShaderProgram(VSH, FSH));
        this.focusDistance = 15.0f;
        this.focusRange = 5.0f;
        this.blurSize = 0.02f;
        this.transition = 10.0f;
        if (this.program.isCompiled()) {
            return;
        }
        Gdx.f82app.error("DoF", this.program.getLog());
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
        this.program.setUniformf("u_farPlane", this.camera.far);
        this.program.setUniformf("u_aspectRatio", Gdx.graphics.getWidth() / Gdx.graphics.getHeight());
        this.program.setUniformf("u_focusDistance", this.focusDistance);
        this.program.setUniformf("u_focusRange", this.focusRange);
        this.program.setUniformf("u_transition", this.transition);
        this.program.setUniformf("u_blurSize", this.blurSize);
        this.program.end();
        renderShader(context, dstBuffer);
    }
}
