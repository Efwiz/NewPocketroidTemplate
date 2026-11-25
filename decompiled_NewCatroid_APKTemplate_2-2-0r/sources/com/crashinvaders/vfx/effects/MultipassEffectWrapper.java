package com.crashinvaders.vfx.effects;

import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;

/* JADX INFO: loaded from: classes8.dex */
public class MultipassEffectWrapper extends AbstractVfxEffect implements ChainVfxEffect {
    private final ChainVfxEffect effect;
    private int passes = 1;

    public MultipassEffectWrapper(ChainVfxEffect chainVfxEffect) {
        this.effect = chainVfxEffect;
    }

    @Override // com.crashinvaders.vfx.effects.VfxEffect
    public void resize(int i, int i2) {
        this.effect.resize(i, i2);
    }

    @Override // com.crashinvaders.vfx.effects.VfxEffect
    public void update(float f) {
        this.effect.update(f);
    }

    @Override // com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        this.effect.rebind();
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.effect.dispose();
    }

    @Override // com.crashinvaders.vfx.effects.ChainVfxEffect
    public void render(VfxRenderContext vfxRenderContext, VfxPingPongWrapper vfxPingPongWrapper) {
        int i = this.passes;
        if (i == 0) {
            vfxPingPongWrapper.swap();
            return;
        }
        for (int i2 = 0; i2 < i; i2++) {
            this.effect.render(vfxRenderContext, vfxPingPongWrapper);
            if (i2 < i - 1) {
                vfxPingPongWrapper.swap();
            }
        }
    }

    public int getPasses() {
        return this.passes;
    }

    public void setPasses(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Passes value cannot be a negative number.");
        }
        this.passes = i;
    }
}
