package com.crashinvaders.vfx.framebuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/* JADX INFO: loaded from: classes8.dex */
public class VfxFrameBufferPool implements Disposable {
    private static final String TAG = "VfxFrameBufferPool";
    private boolean disposed;
    protected final Array<VfxFrameBuffer> freeBuffers;
    public int freePeak;
    private int height;
    protected final Array<VfxFrameBuffer> managedBuffers;
    private Pixmap.Format pixelFormat;
    private Texture.TextureFilter textureFilterMag;
    private Texture.TextureFilter textureFilterMin;
    private Texture.TextureWrap textureWrapU;
    private Texture.TextureWrap textureWrapV;
    private int width;

    public VfxFrameBufferPool() {
        this(Pixmap.Format.RGBA8888, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), 16);
    }

    public VfxFrameBufferPool(Pixmap.Format format, int i, int i2, int i3) {
        this.textureWrapU = Texture.TextureWrap.ClampToEdge;
        this.textureWrapV = Texture.TextureWrap.ClampToEdge;
        this.textureFilterMin = Texture.TextureFilter.Nearest;
        this.textureFilterMag = Texture.TextureFilter.Nearest;
        this.disposed = false;
        this.width = i;
        this.height = i2;
        this.pixelFormat = format;
        this.managedBuffers = new Array<>(false, i3);
        this.freeBuffers = new Array<>(false, i3);
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        if (this.managedBuffers.size != this.freeBuffers.size) {
            Gdx.f82app.error(TAG, "At the moment of disposal, the pool still has some managed buffers unfreed (" + (this.managedBuffers.size - this.freeBuffers.size) + "). Someone's using them and hasn't freed?");
        }
        this.disposed = true;
        for (int i = 0; i < this.managedBuffers.size; i++) {
            this.managedBuffers.get(i).dispose();
        }
        this.managedBuffers.clear();
        this.freeBuffers.clear();
    }

    public void resize(int i, int i2) {
        this.width = i;
        this.height = i2;
        cleanupInvalid();
    }

    public VfxFrameBuffer obtain() {
        if (this.disposed) {
            throw new IllegalStateException("Instance is already disposed");
        }
        return this.freeBuffers.size == 0 ? createBuffer() : this.freeBuffers.pop();
    }

    public void free(VfxFrameBuffer vfxFrameBuffer) {
        if (this.disposed) {
            throw new IllegalStateException("Instance is already disposed");
        }
        if (vfxFrameBuffer == null) {
            throw new IllegalArgumentException("buffer cannot be null.");
        }
        if (!validateBuffer(vfxFrameBuffer)) {
            this.managedBuffers.removeValue(vfxFrameBuffer, true);
            vfxFrameBuffer.dispose();
        } else {
            this.freeBuffers.add(vfxFrameBuffer);
            this.freePeak = Math.max(this.freePeak, this.freeBuffers.size);
            resetBuffer(vfxFrameBuffer);
        }
    }

    public void clearFree() {
        for (int i = 0; i < this.freeBuffers.size; i++) {
            VfxFrameBuffer vfxFrameBuffer = this.freeBuffers.get(i);
            this.managedBuffers.removeValue(vfxFrameBuffer, true);
            vfxFrameBuffer.dispose();
        }
        this.freeBuffers.clear();
    }

    public int getFreeCount() {
        return this.freeBuffers.size;
    }

    protected VfxFrameBuffer createBuffer() {
        VfxFrameBuffer vfxFrameBuffer = new VfxFrameBuffer(this.pixelFormat);
        vfxFrameBuffer.initialize(this.width, this.height);
        this.managedBuffers.add(vfxFrameBuffer);
        return vfxFrameBuffer;
    }

    protected void resetBuffer(VfxFrameBuffer vfxFrameBuffer) {
        vfxFrameBuffer.clearRenderers();
        Texture texture = vfxFrameBuffer.getTexture();
        texture.setWrap(this.textureWrapU, this.textureWrapV);
        texture.setFilter(this.textureFilterMin, this.textureFilterMag);
    }

    protected boolean validateBuffer(VfxFrameBuffer vfxFrameBuffer) {
        FrameBuffer fbo = vfxFrameBuffer.getFbo();
        return vfxFrameBuffer.isInitialized() && this.width == fbo.getWidth() && this.height == fbo.getHeight() && this.pixelFormat == vfxFrameBuffer.getPixelFormat();
    }

    protected void cleanupInvalid() {
        for (int i = 0; i < this.managedBuffers.size; i++) {
            VfxFrameBuffer vfxFrameBuffer = this.managedBuffers.get(i);
            if (!validateBuffer(vfxFrameBuffer)) {
                if (this.freeBuffers.removeValue(vfxFrameBuffer, true)) {
                    this.managedBuffers.removeValue(vfxFrameBuffer, true);
                    vfxFrameBuffer.dispose();
                } else {
                    vfxFrameBuffer.initialize(this.width, this.height);
                }
            }
        }
    }

    public void setTextureParams(Texture.TextureWrap textureWrap, Texture.TextureWrap textureWrap2, Texture.TextureFilter textureFilter, Texture.TextureFilter textureFilter2) {
        this.textureWrapU = textureWrap;
        this.textureWrapV = textureWrap2;
        this.textureFilterMin = textureFilter;
        this.textureFilterMag = textureFilter2;
        for (int i = 0; i < this.freeBuffers.size; i++) {
            Texture texture = this.freeBuffers.get(i).getTexture();
            texture.setWrap(textureWrap, textureWrap2);
            texture.setFilter(textureFilter, textureFilter2);
        }
    }
}
