package com.crashinvaders.vfx.effects.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.effects.ShaderVfxEffect;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.gl.VfxGLUtils;

/* JADX INFO: loaded from: classes8.dex */
public class MixEffect extends ShaderVfxEffect {
    private static final String U_MIX = "u_mix";
    private static final String U_TEXTURE0 = "u_texture0";
    private static final String U_TEXTURE1 = "u_texture1";
    private float mixFactor;

    public enum Method {
        MAX,
        MIX
    }

    public MixEffect(Method method) {
        super(VfxGLUtils.compileShader(Gdx.files.classpath("gdxvfx/shaders/screenspace.vert"), Gdx.files.classpath("gdxvfx/shaders/mix.frag"), "#define METHOD " + method.name()));
        this.mixFactor = 0.5f;
        rebind();
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.program.begin();
        this.program.setUniformi(U_TEXTURE0, 0);
        this.program.setUniformi(U_TEXTURE1, 1);
        this.program.setUniformf(U_MIX, this.mixFactor);
        this.program.end();
    }

    public void render(VfxRenderContext vfxRenderContext, VfxFrameBuffer vfxFrameBuffer, VfxFrameBuffer vfxFrameBuffer2, VfxFrameBuffer vfxFrameBuffer3) {
        vfxFrameBuffer.getTexture().bind(0);
        vfxFrameBuffer2.getTexture().bind(1);
        renderShader(vfxRenderContext, vfxFrameBuffer3);
    }

    public float getMixFactor() {
        return this.mixFactor;
    }

    public void setMixFactor(float f) {
        this.mixFactor = MathUtils.clamp(0.0f, 1.0f, f);
        setUniform(U_MIX, f);
    }
}
