package com.crashinvaders.vfx.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;
import com.crashinvaders.vfx.gl.VfxGLUtils;

/* JADX INFO: loaded from: classes8.dex */
public class FxaaEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private static final String U_FXAA_REDUCE_MIN = "u_fxaaReduceMin";
    private static final String U_FXAA_REDUCE_MUL = "u_fxaaReduceMul";
    private static final String U_FXAA_SPAN_MAX = "u_fxaaSpanMax";
    private static final String U_TEXTURE0 = "u_texture0";
    private static final String U_VIEWPORT_INVERSE = "u_viewportInverse";
    private float fxaaReduceMin;
    private float fxaaReduceMul;
    private float fxaaSpanMax;
    private final Vector2 viewportInverse;

    public FxaaEffect() {
        this(0.0078125f, 0.125f, 8.0f, true);
    }

    public FxaaEffect(float f, float f2, float f3, boolean z) {
        super(VfxGLUtils.compileShader(Gdx.files.classpath("gdxvfx/shaders/screenspace.vert"), Gdx.files.classpath("gdxvfx/shaders/fxaa.frag"), z ? "#define SUPPORT_ALPHA" : ""));
        this.viewportInverse = new Vector2();
        this.fxaaReduceMin = f;
        this.fxaaReduceMul = f2;
        this.fxaaSpanMax = f3;
        rebind();
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void resize(int i, int i2) {
        super.resize(i, i2);
        this.viewportInverse.set(1.0f / i, 1.0f / i2);
        setUniform(U_VIEWPORT_INVERSE, this.viewportInverse);
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.program.begin();
        this.program.setUniformi(U_TEXTURE0, 0);
        this.program.setUniformf(U_VIEWPORT_INVERSE, this.viewportInverse);
        this.program.setUniformf(U_FXAA_REDUCE_MIN, this.fxaaReduceMin);
        this.program.setUniformf(U_FXAA_REDUCE_MUL, this.fxaaReduceMul);
        this.program.setUniformf(U_FXAA_SPAN_MAX, this.fxaaSpanMax);
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

    public void setReduceMin(float f) {
        this.fxaaReduceMin = f;
        setUniform(U_FXAA_REDUCE_MIN, f);
    }

    public void setReduceMul(float f) {
        this.fxaaReduceMul = f;
        setUniform(U_FXAA_REDUCE_MUL, f);
    }

    public void setSpanMax(float f) {
        this.fxaaSpanMax = f;
        setUniform(U_FXAA_SPAN_MAX, f);
    }
}
