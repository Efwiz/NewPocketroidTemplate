package com.crashinvaders.vfx.effects.util;

import com.badlogic.gdx.Gdx;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.effects.ShaderVfxEffect;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.gl.VfxGLUtils;

/* JADX INFO: loaded from: classes8.dex */
public class CombineEffect extends ShaderVfxEffect {
    private static final String U_SOURCE0_INTENSITY = "u_src0Intensity";
    private static final String U_SOURCE0_SATURATION = "u_src0Saturation";
    private static final String U_SOURCE1_INTENSITY = "u_src1Intensity";
    private static final String U_SOURCE1_SATURATION = "u_src1Saturation";
    private static final String U_TEXTURE0 = "u_texture0";
    private static final String U_TEXTURE1 = "u_texture1";
    private float s1i;
    private float s1s;
    private float s2i;
    private float s2s;

    public CombineEffect() {
        super(VfxGLUtils.compileShader(Gdx.files.classpath("gdxvfx/shaders/screenspace.vert"), Gdx.files.classpath("gdxvfx/shaders/combine.frag")));
        this.s1i = 1.0f;
        this.s2i = 1.0f;
        this.s1s = 1.0f;
        this.s2s = 1.0f;
        rebind();
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.program.begin();
        this.program.setUniformi(U_TEXTURE0, 0);
        this.program.setUniformi(U_TEXTURE1, 1);
        this.program.setUniformf(U_SOURCE0_INTENSITY, this.s1i);
        this.program.setUniformf(U_SOURCE1_INTENSITY, this.s2i);
        this.program.setUniformf(U_SOURCE0_SATURATION, this.s1s);
        this.program.setUniformf(U_SOURCE1_SATURATION, this.s2s);
        this.program.end();
    }

    public void render(VfxRenderContext vfxRenderContext, VfxFrameBuffer vfxFrameBuffer, VfxFrameBuffer vfxFrameBuffer2, VfxFrameBuffer vfxFrameBuffer3) {
        vfxFrameBuffer.getTexture().bind(0);
        vfxFrameBuffer2.getTexture().bind(1);
        renderShader(vfxRenderContext, vfxFrameBuffer3);
    }

    public float getSource1Intensity() {
        return this.s1i;
    }

    public void setSource1Intensity(float f) {
        this.s1i = f;
        setUniform(U_SOURCE0_INTENSITY, f);
    }

    public float getSource2Intensity() {
        return this.s2i;
    }

    public void setSource2Intensity(float f) {
        this.s2i = f;
        setUniform(U_SOURCE1_INTENSITY, f);
    }

    public float getSource1Saturation() {
        return this.s1s;
    }

    public void setSource1Saturation(float f) {
        this.s1s = f;
        setUniform(U_SOURCE0_SATURATION, f);
    }

    public float getSource2Saturation() {
        return this.s2s;
    }

    public void setSource2Saturation(float f) {
        this.s2s = f;
        setUniform(U_SOURCE1_SATURATION, f);
    }
}
