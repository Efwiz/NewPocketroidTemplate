package com.crashinvaders.vfx.effects;

import com.badlogic.gdx.Gdx;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;
import com.crashinvaders.vfx.gl.VfxGLUtils;

/* JADX INFO: loaded from: classes8.dex */
public class VignettingEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private static final String CENTER_X = "u_centerX";
    private static final String CENTER_Y = "u_centerY";
    private static final String SATURATION = "u_saturation";
    private static final String SATURATION_MUL = "u_saturationMul";
    private static final String TEXTURE0 = "u_texture0";
    private static final String VIGNETTE_INTENSITY = "u_vignetteIntensity";
    private static final String VIGNETTE_X = "u_vignetteX";
    private static final String VIGNETTE_Y = "u_vignetteY";
    private float centerX;
    private float centerY;
    private float intensity;
    private float saturation;
    private final boolean saturationEnabled;
    private float saturationMul;
    private float vignetteX;
    private float vignetteY;

    public VignettingEffect(boolean z) {
        super(VfxGLUtils.compileShader(Gdx.files.classpath("gdxvfx/shaders/screenspace.vert"), Gdx.files.classpath("gdxvfx/shaders/vignetting.frag"), z ? "#define CONTROL_SATURATION" : ""));
        this.vignetteX = 0.8f;
        this.vignetteY = 0.25f;
        this.centerX = 0.5f;
        this.centerY = 0.5f;
        this.intensity = 1.0f;
        this.saturation = 0.0f;
        this.saturationMul = 0.0f;
        this.saturationEnabled = z;
        rebind();
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        this.program.begin();
        this.program.setUniformi(TEXTURE0, 0);
        if (this.saturationEnabled) {
            this.program.setUniformf(SATURATION, this.saturation);
            this.program.setUniformf(SATURATION_MUL, this.saturationMul);
        }
        this.program.setUniformf(VIGNETTE_INTENSITY, this.intensity);
        this.program.setUniformf(VIGNETTE_X, this.vignetteX);
        this.program.setUniformf(VIGNETTE_Y, this.vignetteY);
        this.program.setUniformf(CENTER_X, this.centerX);
        this.program.setUniformf(CENTER_Y, this.centerY);
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

    public void setIntensity(float f) {
        this.intensity = f;
        setUniform(VIGNETTE_INTENSITY, f);
    }

    public void setSaturation(float f) {
        this.saturation = f;
        if (this.saturationEnabled) {
            setUniform(SATURATION, f);
        }
    }

    public void setSaturationMul(float f) {
        this.saturationMul = f;
        if (this.saturationEnabled) {
            setUniform(SATURATION_MUL, f);
        }
    }

    public void setCoords(float f, float f2) {
        this.vignetteX = f;
        this.vignetteY = f2;
        this.program.begin();
        this.program.setUniformf(VIGNETTE_X, f);
        this.program.setUniformf(VIGNETTE_Y, f2);
        this.program.end();
    }

    public void setVignetteX(float f) {
        this.vignetteX = f;
        setUniform(VIGNETTE_X, f);
    }

    public void setVignetteY(float f) {
        this.vignetteY = f;
        setUniform(VIGNETTE_Y, f);
    }

    public void setCenter(float f, float f2) {
        this.centerX = f;
        this.centerY = f2;
        this.program.begin();
        this.program.setUniformf(CENTER_X, this.centerX);
        this.program.setUniformf(CENTER_Y, this.centerY);
        this.program.end();
    }

    public float getCenterX() {
        return this.centerX;
    }

    public float getCenterY() {
        return this.centerY;
    }

    public float getVignetteX() {
        return this.vignetteX;
    }

    public float getVignetteY() {
        return this.vignetteY;
    }

    public float getIntensity() {
        return this.intensity;
    }

    public float getSaturation() {
        return this.saturation;
    }

    public float getSaturationMul() {
        return this.saturationMul;
    }

    public boolean isSaturationControlEnabled() {
        return this.saturationEnabled;
    }
}
