package com.crashinvaders.vfx.effects;

import com.badlogic.gdx.Gdx;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;
import com.crashinvaders.vfx.gl.VfxGLUtils;

/* JADX INFO: loaded from: classes8.dex */
public class LevelsEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private static final String Brightness = "u_brightness";
    private static final String Contrast = "u_contrast";
    private static final String Gamma = "u_gamma";
    private static final String Hue = "u_hue";
    private static final String Saturation = "u_saturation";
    private static final String Texture = "u_texture0";
    private float brightness;
    private float contrast;
    private float gamma;
    private float hue;
    private float saturation;

    public LevelsEffect() {
        super(VfxGLUtils.compileShader(Gdx.files.classpath("gdxvfx/shaders/screenspace.vert"), Gdx.files.classpath("gdxvfx/shaders/levels.frag")));
        this.brightness = 0.0f;
        this.contrast = 1.0f;
        this.saturation = 1.0f;
        this.hue = 1.0f;
        this.gamma = 1.0f;
        rebind();
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.program.begin();
        this.program.setUniformi(Texture, 0);
        this.program.setUniformf(Brightness, this.brightness);
        this.program.setUniformf(Contrast, this.contrast);
        this.program.setUniformf(Saturation, this.saturation);
        this.program.setUniformf(Hue, this.hue);
        this.program.setUniformf(Gamma, this.gamma);
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

    public float getContrast() {
        return this.contrast;
    }

    public void setContrast(float f) {
        this.contrast = f;
        setUniform(Contrast, f);
    }

    public float getBrightness() {
        return this.brightness;
    }

    public void setBrightness(float f) {
        this.brightness = f;
        setUniform(Brightness, f);
    }

    public float getSaturation() {
        return this.saturation;
    }

    public void setSaturation(float f) {
        this.saturation = f;
        setUniform(Saturation, f);
    }

    public float getHue() {
        return this.hue;
    }

    public void setHue(float f) {
        this.hue = f;
        setUniform(Hue, f);
    }

    public float getGamma() {
        return this.gamma;
    }

    public void setGamma(float f) {
        this.gamma = f;
        setUniform(Gamma, f);
    }
}
