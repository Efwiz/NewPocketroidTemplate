package com.crashinvaders.vfx.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.effects.util.CombineEffect;
import com.crashinvaders.vfx.effects.util.CopyEffect;
import com.crashinvaders.vfx.effects.util.GammaThresholdEffect;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;
import com.crashinvaders.vfx.gl.VfxGLUtils;

/* JADX INFO: loaded from: classes8.dex */
public class BloomEffect extends CompositeVfxEffect implements ChainVfxEffect {
    private boolean blending;
    private final GaussianBlurEffect blur;
    private final CombineEffect combine;
    private final CopyEffect copy;
    private int dfactor;
    private int sfactor;
    private final GammaThresholdEffect threshold;

    public BloomEffect() {
        this(new Settings(10, 0.85f, 1.0f, 0.85f, 1.1f, 0.85f));
    }

    public BloomEffect(Settings settings) {
        this.blending = false;
        this.copy = (CopyEffect) register(new CopyEffect());
        this.blur = (GaussianBlurEffect) register(new GaussianBlurEffect());
        this.threshold = (GammaThresholdEffect) register(new GammaThresholdEffect(GammaThresholdEffect.Type.RGBA));
        this.combine = (CombineEffect) register(new CombineEffect());
        applySettings(settings);
    }

    @Override // com.crashinvaders.vfx.effects.ChainVfxEffect
    public void render(VfxRenderContext vfxRenderContext, VfxPingPongWrapper vfxPingPongWrapper) {
        VfxFrameBuffer vfxFrameBufferObtain = vfxRenderContext.getBufferPool().obtain();
        this.copy.render(vfxRenderContext, vfxPingPongWrapper.getSrcBuffer(), vfxFrameBufferObtain);
        boolean zIsGLEnabled = VfxGLUtils.isGLEnabled(GL20.GL_BLEND);
        Gdx.gl.glDisable(GL20.GL_BLEND);
        this.threshold.render(vfxRenderContext, vfxPingPongWrapper);
        vfxPingPongWrapper.swap();
        this.blur.render(vfxRenderContext, vfxPingPongWrapper);
        vfxPingPongWrapper.swap();
        if (this.blending || zIsGLEnabled) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
        }
        if (this.blending) {
            Gdx.gl.glBlendFunc(this.sfactor, this.dfactor);
        }
        this.combine.render(vfxRenderContext, vfxFrameBufferObtain, vfxPingPongWrapper.getSrcBuffer(), vfxPingPongWrapper.getDstBuffer());
        vfxRenderContext.getBufferPool().free(vfxFrameBufferObtain);
    }

    public float getBaseIntensity() {
        return this.combine.getSource1Intensity();
    }

    public void setBaseIntensity(float f) {
        this.combine.setSource1Intensity(f);
    }

    public float getBaseSaturation() {
        return this.combine.getSource1Saturation();
    }

    public void setBaseSaturation(float f) {
        this.combine.setSource1Saturation(f);
    }

    public float getBloomIntensity() {
        return this.combine.getSource2Intensity();
    }

    public void setBloomIntensity(float f) {
        this.combine.setSource2Intensity(f);
    }

    public float getBloomSaturation() {
        return this.combine.getSource2Saturation();
    }

    public void setBloomSaturation(float f) {
        this.combine.setSource2Saturation(f);
    }

    public int getBlurPasses() {
        return this.blur.getPasses();
    }

    public void setBlurPasses(int i) {
        this.blur.setPasses(i);
    }

    public float getBlurAmount() {
        return this.blur.getAmount();
    }

    public void setBlurAmount(float f) {
        this.blur.setAmount(f);
    }

    public boolean isBlendingEnabled() {
        return this.blending;
    }

    public float getThreshold() {
        return this.threshold.getGamma();
    }

    public int getBlendingSourceFactor() {
        return this.sfactor;
    }

    public int getBlendingDestFactor() {
        return this.dfactor;
    }

    public void setThreshold(float f) {
        this.threshold.setGamma(f);
    }

    public void enableBlending(int i, int i2) {
        this.blending = true;
        this.sfactor = i;
        this.dfactor = i2;
    }

    public void disableBlending() {
        this.blending = false;
    }

    public GaussianBlurEffect.BlurType getBlurType() {
        return this.blur.getType();
    }

    public void setBlurType(GaussianBlurEffect.BlurType blurType) {
        this.blur.setType(blurType);
    }

    public void applySettings(Settings settings) {
        setThreshold(settings.bloomThreshold);
        setBaseIntensity(settings.baseIntensity);
        setBaseSaturation(settings.baseSaturation);
        setBloomIntensity(settings.bloomIntensity);
        setBloomSaturation(settings.bloomSaturation);
        setBlurPasses(settings.blurPasses);
        setBlurAmount(settings.blurAmount);
        setBlurType(settings.blurType);
    }

    public static class Settings {
        public final float baseIntensity;
        public final float baseSaturation;
        public final float bloomIntensity;
        public final float bloomSaturation;
        public final float bloomThreshold;
        public final float blurAmount;
        public final int blurPasses;
        public final GaussianBlurEffect.BlurType blurType;

        public Settings(int i, float f, float f2, float f3, float f4, float f5) {
            this(GaussianBlurEffect.BlurType.Gaussian5x5b, i, 0.0f, f, f2, f3, f4, f5);
        }

        public Settings(GaussianBlurEffect.BlurType blurType, int i, float f, float f2, float f3, float f4, float f5, float f6) {
            this.blurType = blurType;
            this.blurPasses = i;
            this.blurAmount = f;
            this.bloomThreshold = f2;
            this.baseIntensity = f3;
            this.baseSaturation = f4;
            this.bloomIntensity = f5;
            this.bloomSaturation = f6;
        }

        public Settings(Settings settings) {
            this.blurType = settings.blurType;
            this.blurPasses = settings.blurPasses;
            this.blurAmount = settings.blurAmount;
            this.bloomThreshold = settings.bloomThreshold;
            this.baseIntensity = settings.baseIntensity;
            this.baseSaturation = settings.baseSaturation;
            this.bloomIntensity = settings.bloomIntensity;
            this.bloomSaturation = settings.bloomSaturation;
        }
    }
}
