package com.crashinvaders.vfx.framebuffer;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/* JADX INFO: loaded from: classes8.dex */
public class VfxFrameBufferQueue implements Disposable {
    private final Array<VfxFrameBuffer> buffers;
    private int currentIdx = 0;
    private Texture.TextureWrap wrapU = Texture.TextureWrap.ClampToEdge;
    private Texture.TextureWrap wrapV = Texture.TextureWrap.ClampToEdge;
    private Texture.TextureFilter filterMin = Texture.TextureFilter.Nearest;
    private Texture.TextureFilter filterMag = Texture.TextureFilter.Nearest;

    public VfxFrameBufferQueue(Pixmap.Format format, int i) {
        if (i < 1) {
            throw new IllegalArgumentException("FBO amount should be a positive number.");
        }
        this.buffers = new Array<>(true, i);
        for (int i2 = 0; i2 < i; i2++) {
            this.buffers.add(new VfxFrameBuffer(format));
        }
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        for (int i = 0; i < this.buffers.size; i++) {
            this.buffers.get(i).dispose();
        }
        this.buffers.clear();
    }

    public void resize(int i, int i2) {
        for (int i3 = 0; i3 < this.buffers.size; i3++) {
            this.buffers.get(i3).initialize(i, i2);
        }
    }

    public void rebind() {
        for (int i = 0; i < this.buffers.size; i++) {
            VfxFrameBuffer vfxFrameBuffer = this.buffers.get(i);
            if (vfxFrameBuffer.getFbo() != null) {
                Texture colorBufferTexture = vfxFrameBuffer.getFbo().getColorBufferTexture();
                colorBufferTexture.setWrap(this.wrapU, this.wrapV);
                colorBufferTexture.setFilter(this.filterMin, this.filterMag);
            }
        }
    }

    public VfxFrameBuffer getCurrent() {
        return this.buffers.get(this.currentIdx);
    }

    public VfxFrameBuffer changeToNext() {
        this.currentIdx = (this.currentIdx + 1) % this.buffers.size;
        return getCurrent();
    }

    public void setTextureParams(Texture.TextureWrap textureWrap, Texture.TextureWrap textureWrap2, Texture.TextureFilter textureFilter, Texture.TextureFilter textureFilter2) {
        this.wrapU = textureWrap;
        this.wrapV = textureWrap2;
        this.filterMin = textureFilter;
        this.filterMag = textureFilter2;
        rebind();
    }
}
