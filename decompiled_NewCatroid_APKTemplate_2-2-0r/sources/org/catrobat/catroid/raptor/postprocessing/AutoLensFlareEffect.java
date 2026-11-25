package org.catrobat.catroid.raptor.postprocessing;

import com.badlogic.gdx.Gdx;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.crashinvaders.vfx.effects.ShaderVfxEffect;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;
import com.crashinvaders.vfx.gl.VfxGLUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/postprocessing/AutoLensFlareEffect.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AutoLensFlareEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private float dispersal;
    private float distortion;
    private float intensity;
    private float size;
    private float threshold;

    public AutoLensFlareEffect() {
        super(VfxGLUtils.compileShader(Gdx.files.internal("shaders/screenspace.vert"), Gdx.files.internal("shaders/auto_flare.frag")));
        this.threshold = 0.8f;
        this.intensity = 2.0f;
        this.dispersal = 0.4f;
        this.distortion = 0.02f;
        this.size = 1.0f;
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void resize(int width, int height) {
        float f = this.size;
        super.resize((int) (width / f), (int) (height / f));
    }

    public void setSize(float size) {
        this.size = size;
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void update(float delta) {
        super.update(delta);
        setUniform("u_threshold", this.threshold);
        setUniform("u_intensity", this.intensity);
        setUniform("u_dispersal", this.dispersal);
        setUniform("u_distortion", this.distortion);
    }

    @Override // com.crashinvaders.vfx.effects.ChainVfxEffect
    public void render(VfxRenderContext context, VfxPingPongWrapper buffers) {
        VfxFrameBuffer srcBuffer = buffers.getSrcBuffer();
        buffers.getDstBuffer();
        try {
            srcBuffer.getTexture().bind(0);
            this.program.bind();
            this.program.setUniformi("u_texture0", 0);
            context.getViewportMesh().render(this.program);
        } catch (Exception e) {
            Gdx.f82app.error("AutoLensFlare", "Render error", e);
        }
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public void setDispersal(float dispersal) {
        this.dispersal = dispersal;
    }
}
