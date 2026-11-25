package com.crashinvaders.vfx;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Disposable;
import com.crashinvaders.vfx.framebuffer.VfxFrameBufferPool;
import com.crashinvaders.vfx.framebuffer.VfxFrameBufferRenderer;
import com.crashinvaders.vfx.utils.ViewportQuadMesh;

/* JADX INFO: loaded from: classes8.dex */
public class VfxRenderContext implements Disposable {
    private int bufferHeight;
    private final VfxFrameBufferPool bufferPool;
    private final VfxFrameBufferRenderer bufferRenderer = new VfxFrameBufferRenderer();
    private int bufferWidth;
    private final Pixmap.Format pixelFormat;

    public VfxRenderContext(Pixmap.Format format, int i, int i2) {
        this.bufferPool = new VfxFrameBufferPool(format, i, i2, 8);
        this.pixelFormat = format;
        this.bufferWidth = i;
        this.bufferHeight = i2;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.bufferPool.dispose();
        this.bufferRenderer.dispose();
    }

    public void resize(int i, int i2) {
        this.bufferWidth = i;
        this.bufferHeight = i2;
        this.bufferPool.resize(i, i2);
    }

    public VfxFrameBufferPool getBufferPool() {
        return this.bufferPool;
    }

    public void rebind() {
        this.bufferRenderer.rebind();
    }

    public Pixmap.Format getPixelFormat() {
        return this.pixelFormat;
    }

    public VfxFrameBufferRenderer getBufferRenderer() {
        return this.bufferRenderer;
    }

    public ViewportQuadMesh getViewportMesh() {
        return this.bufferRenderer.getMesh();
    }

    public int getBufferWidth() {
        return this.bufferWidth;
    }

    public int getBufferHeight() {
        return this.bufferHeight;
    }
}
