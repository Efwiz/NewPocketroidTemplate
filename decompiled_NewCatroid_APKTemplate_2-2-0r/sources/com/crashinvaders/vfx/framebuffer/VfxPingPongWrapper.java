package com.crashinvaders.vfx.framebuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Pool;

/* JADX INFO: loaded from: classes8.dex */
public class VfxPingPongWrapper implements Pool.Poolable {
    protected VfxFrameBuffer bufDst;
    protected VfxFrameBuffer bufSrc;
    protected VfxFrameBufferPool bufferPool = null;
    protected boolean capturing;

    public VfxPingPongWrapper() {
    }

    public VfxPingPongWrapper(VfxFrameBufferPool vfxFrameBufferPool) {
        initialize(vfxFrameBufferPool);
    }

    public VfxPingPongWrapper(VfxFrameBuffer vfxFrameBuffer, VfxFrameBuffer vfxFrameBuffer2) {
        initialize(vfxFrameBuffer2, vfxFrameBuffer);
    }

    public VfxPingPongWrapper initialize(VfxFrameBufferPool vfxFrameBufferPool) {
        this.bufferPool = vfxFrameBufferPool;
        return initialize(vfxFrameBufferPool.obtain(), vfxFrameBufferPool.obtain());
    }

    public VfxPingPongWrapper initialize(VfxFrameBuffer vfxFrameBuffer, VfxFrameBuffer vfxFrameBuffer2) {
        if (this.capturing) {
            throw new IllegalStateException("Ping pong buffer cannot be initialized during capturing stage. It seems the instance is already initialized.");
        }
        if (isInitialized()) {
            reset();
        }
        this.bufSrc = vfxFrameBuffer;
        this.bufDst = vfxFrameBuffer2;
        return this;
    }

    @Override // com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        if (this.capturing) {
            throw new IllegalStateException("Ping pong buffer cannot be reset during capturing stage. Forgot to call end()?");
        }
        VfxFrameBufferPool vfxFrameBufferPool = this.bufferPool;
        if (vfxFrameBufferPool != null) {
            vfxFrameBufferPool.free(this.bufSrc);
            this.bufferPool.free(this.bufDst);
            this.bufferPool = null;
        }
        this.bufSrc = null;
        this.bufDst = null;
    }

    public boolean isInitialized() {
        return (this.bufDst == null || this.bufSrc == null) ? false : true;
    }

    public void begin() {
        if (this.capturing) {
            throw new IllegalStateException("Ping pong buffer is already in capturing state.");
        }
        this.capturing = true;
        this.bufDst.begin();
    }

    public void end() {
        if (!this.capturing) {
            throw new IllegalStateException("Ping pong is not in capturing state. You should call begin() before calling end().");
        }
        this.bufDst.end();
        this.capturing = false;
    }

    public void swap() {
        if (this.capturing) {
            this.bufDst.end();
        }
        VfxFrameBuffer vfxFrameBuffer = this.bufDst;
        VfxFrameBuffer vfxFrameBuffer2 = this.bufSrc;
        this.bufDst = vfxFrameBuffer2;
        this.bufSrc = vfxFrameBuffer;
        if (this.capturing) {
            vfxFrameBuffer2.begin();
        }
    }

    public boolean isCapturing() {
        return this.capturing;
    }

    public Texture getSrcTexture() {
        return this.bufSrc.getFbo().getColorBufferTexture();
    }

    public VfxFrameBuffer getSrcBuffer() {
        return this.bufSrc;
    }

    public Texture getDstTexture() {
        return this.bufDst.getFbo().getColorBufferTexture();
    }

    public VfxFrameBuffer getDstBuffer() {
        return this.bufDst;
    }

    public void cleanUpBuffers(Color color) {
        cleanUpBuffers(color.r, color.g, color.b, color.a);
    }

    public void cleanUpBuffers(float f, float f2, float f3, float f4) {
        boolean z = this.capturing;
        if (!z) {
            begin();
        }
        Gdx.gl.glClearColor(f, f2, f3, f4);
        Gdx.gl.glClear(16384);
        swap();
        Gdx.gl.glClear(16384);
        if (z) {
            return;
        }
        end();
    }
}
