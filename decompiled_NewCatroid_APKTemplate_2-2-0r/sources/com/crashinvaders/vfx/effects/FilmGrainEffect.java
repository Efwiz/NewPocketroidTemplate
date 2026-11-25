package com.crashinvaders.vfx.effects;

import com.badlogic.gdx.Gdx;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;
import com.crashinvaders.vfx.gl.VfxGLUtils;

/* JADX INFO: loaded from: classes8.dex */
public class FilmGrainEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private static final String U_NOISE_AMOUNT = "u_noiseAmount";
    private static final String U_SEED = "u_seed";
    private static final String U_TEXTURE0 = "u_texture0";
    private float noiseAmount;
    private float seed;

    public FilmGrainEffect() {
        super(VfxGLUtils.compileShader(Gdx.files.classpath("gdxvfx/shaders/screenspace.vert"), Gdx.files.classpath("gdxvfx/shaders/film-grain.frag")));
        this.seed = 0.0f;
        this.noiseAmount = 0.18f;
        rebind();
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void update(float f) {
        super.update(f);
        setSeed((this.seed + f) % 1.0f);
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.program.begin();
        this.program.setUniformi(U_TEXTURE0, 0);
        this.program.setUniformf(U_SEED, this.seed);
        this.program.setUniformf(U_NOISE_AMOUNT, this.noiseAmount);
        this.program.begin();
    }

    @Override // com.crashinvaders.vfx.effects.ChainVfxEffect
    public void render(VfxRenderContext vfxRenderContext, VfxPingPongWrapper vfxPingPongWrapper) {
        render(vfxRenderContext, vfxPingPongWrapper.getSrcBuffer(), vfxPingPongWrapper.getDstBuffer());
    }

    public void render(VfxRenderContext vfxRenderContext, VfxFrameBuffer vfxFrameBuffer, VfxFrameBuffer vfxFrameBuffer2) {
        vfxFrameBuffer.getTexture().bind(0);
        renderShader(vfxRenderContext, vfxFrameBuffer2);
    }

    public float getSeed() {
        return this.seed;
    }

    public void setSeed(float f) {
        this.seed = f;
        setUniform(U_SEED, f);
    }

    public float getNoiseAmount() {
        return this.noiseAmount;
    }

    public void setNoiseAmount(float f) {
        this.noiseAmount = f;
        setUniform(U_NOISE_AMOUNT, f);
    }
}
