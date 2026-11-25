package com.crashinvaders.vfx.effects;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.effects.util.CopyEffect;
import com.crashinvaders.vfx.effects.util.MixEffect;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxFrameBufferQueue;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;

/* JADX INFO: loaded from: classes8.dex */
public class MotionBlurEffect extends CompositeVfxEffect implements ChainVfxEffect {
    private final CopyEffect copyFilter;
    private boolean firstFrameRendered = false;
    private final VfxFrameBufferQueue localBuffer;
    private final MixEffect mixFilter;

    public MotionBlurEffect(Pixmap.Format format, MixEffect.Method method, float f) {
        MixEffect mixEffect = (MixEffect) register(new MixEffect(method));
        this.mixFilter = mixEffect;
        mixEffect.setMixFactor(f);
        this.copyFilter = (CopyEffect) register(new CopyEffect());
        this.localBuffer = new VfxFrameBufferQueue(format, Gdx.f82app.getType() == Application.ApplicationType.WebGL ? 2 : 1);
    }

    @Override // com.crashinvaders.vfx.effects.CompositeVfxEffect, com.badlogic.gdx.utils.Disposable
    public void dispose() {
        super.dispose();
        this.localBuffer.dispose();
    }

    @Override // com.crashinvaders.vfx.effects.CompositeVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void resize(int i, int i2) {
        super.resize(i, i2);
        this.localBuffer.resize(i, i2);
        this.firstFrameRendered = false;
    }

    @Override // com.crashinvaders.vfx.effects.CompositeVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.localBuffer.rebind();
    }

    @Override // com.crashinvaders.vfx.effects.ChainVfxEffect
    public void render(VfxRenderContext vfxRenderContext, VfxPingPongWrapper vfxPingPongWrapper) {
        VfxFrameBuffer vfxFrameBufferChangeToNext = this.localBuffer.changeToNext();
        if (!this.firstFrameRendered) {
            this.copyFilter.render(vfxRenderContext, vfxPingPongWrapper.getSrcBuffer(), vfxFrameBufferChangeToNext);
            vfxPingPongWrapper.swap();
            this.firstFrameRendered = true;
        } else {
            this.mixFilter.render(vfxRenderContext, vfxPingPongWrapper.getSrcBuffer(), vfxFrameBufferChangeToNext, vfxPingPongWrapper.getDstBuffer());
            this.copyFilter.render(vfxRenderContext, vfxPingPongWrapper.getDstBuffer(), vfxFrameBufferChangeToNext);
        }
    }
}
