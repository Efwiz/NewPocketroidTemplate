package com.crashinvaders.vfx.effects.util;

import com.badlogic.gdx.Gdx;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.crashinvaders.vfx.effects.ShaderVfxEffect;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;
import com.crashinvaders.vfx.gl.VfxGLUtils;

/* JADX INFO: loaded from: classes8.dex */
public class GammaThresholdEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private static final String U_TEXTURE0 = "u_texture0";
    private static final String U_THRESHOLD = "u_threshold";
    private static final String U_THRESHOLD_INV = "u_thresholdInv";
    private float gamma;

    public enum Type {
        RGBA,
        RGB,
        ALPHA_PREMULTIPLIED
    }

    public GammaThresholdEffect(Type type) {
        super(VfxGLUtils.compileShader(Gdx.files.classpath("gdxvfx/shaders/screenspace.vert"), Gdx.files.classpath("gdxvfx/shaders/gamma-threshold.frag"), "#define THRESHOLD_TYPE " + type.name()));
        rebind();
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.program.begin();
        this.program.setUniformi(U_TEXTURE0, 0);
        this.program.setUniformf(U_THRESHOLD, this.gamma);
        this.program.setUniformf(U_THRESHOLD_INV, 1.0f / (1.0f - this.gamma));
        this.program.end();
    }

    @Override // com.crashinvaders.vfx.effects.ChainVfxEffect
    public void render(VfxRenderContext vfxRenderContext, VfxPingPongWrapper vfxPingPongWrapper) {
        render(vfxRenderContext, vfxPingPongWrapper.getSrcBuffer(), vfxPingPongWrapper.getDstBuffer());
    }

    public void render(VfxRenderContext vfxRenderContext, VfxFrameBuffer vfxFrameBuffer, VfxFrameBuffer vfxFrameBuffer2) {
        vfxFrameBuffer.getTexture().bind(0);
        renderShader(vfxRenderContext, vfxFrameBuffer2);
    }

    public void setGamma(float f) {
        this.gamma = f;
        setUniform(U_THRESHOLD, f);
        setUniform(U_THRESHOLD_INV, 1.0f / (1.0f - f));
    }

    public float getGamma() {
        return this.gamma;
    }
}
