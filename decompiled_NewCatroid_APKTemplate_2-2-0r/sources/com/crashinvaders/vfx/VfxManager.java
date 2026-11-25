package com.crashinvaders.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;
import com.crashinvaders.vfx.utils.PrioritizedArray;

/* JADX INFO: loaded from: classes8.dex */
public final class VfxManager implements Disposable {
    public static final int MAX_FRAME_BUFFER_SIDE = 8192;
    private static final Vector2 tmpVec = new Vector2();
    private boolean applyingEffects;
    private boolean blendingEnabled;
    private boolean capturing;
    private final VfxRenderContext context;
    private boolean disabled;
    private final PrioritizedArray<ChainVfxEffect> effects;
    private int height;
    private final VfxPingPongWrapper pingPongWrapper;
    private final Array<ChainVfxEffect> tmpEffectArray;
    private int width;

    public VfxManager(Pixmap.Format format) {
        this(format, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
    }

    public VfxManager(Pixmap.Format format, int i, int i2) {
        this.effects = new PrioritizedArray<>();
        this.tmpEffectArray = new Array<>();
        this.capturing = false;
        this.disabled = false;
        this.applyingEffects = false;
        this.blendingEnabled = false;
        this.width = i;
        this.height = i2;
        VfxRenderContext vfxRenderContext = new VfxRenderContext(format, i, i2);
        this.context = vfxRenderContext;
        this.pingPongWrapper = new VfxPingPongWrapper(vfxRenderContext.getBufferPool());
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.pingPongWrapper.reset();
        this.context.dispose();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isCapturing() {
        return this.capturing;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean z) {
        this.disabled = z;
    }

    public boolean isBlendingEnabled() {
        return this.blendingEnabled;
    }

    public void setBlendingEnabled(boolean z) {
        this.blendingEnabled = z;
    }

    public Pixmap.Format getPixelFormat() {
        return this.context.getPixelFormat();
    }

    public void setEffectTextureParams(Texture.TextureWrap textureWrap, Texture.TextureWrap textureWrap2, Texture.TextureFilter textureFilter, Texture.TextureFilter textureFilter2) {
        this.context.getBufferPool().setTextureParams(textureWrap, textureWrap2, textureFilter, textureFilter2);
    }

    public boolean isApplyingEffects() {
        return this.applyingEffects;
    }

    public VfxFrameBuffer getResultBuffer() {
        return this.pingPongWrapper.getDstBuffer();
    }

    public VfxPingPongWrapper getPingPongWrapper() {
        return this.pingPongWrapper;
    }

    public VfxRenderContext getRenderContext() {
        return this.context;
    }

    public void addEffect(ChainVfxEffect chainVfxEffect) {
        addEffect(chainVfxEffect, 0);
    }

    public void addEffect(ChainVfxEffect chainVfxEffect, int i) {
        this.effects.add(chainVfxEffect, i);
        chainVfxEffect.resize(this.width, this.height);
    }

    public void removeEffect(ChainVfxEffect chainVfxEffect) {
        this.effects.remove(chainVfxEffect);
    }

    public void removeAllEffects() {
        this.effects.clear();
    }

    public void setEffectPriority(ChainVfxEffect chainVfxEffect, int i) {
        this.effects.setPriority(chainVfxEffect, i);
    }

    public void cleanUpBuffers() {
        cleanUpBuffers(Color.CLEAR);
    }

    public void cleanUpBuffers(Color color) {
        if (this.applyingEffects) {
            throw new IllegalStateException("Cannot clean up buffers when applying effects.");
        }
        if (this.capturing) {
            throw new IllegalStateException("Cannot clean up buffers when capturing a scene.");
        }
        this.pingPongWrapper.cleanUpBuffers(color);
    }

    public void resize(int i, int i2) {
        Vector2 vector2ConstrainFrameBufferSize = constrainFrameBufferSize(i, i2);
        int i3 = (int) vector2ConstrainFrameBufferSize.x;
        this.width = i3;
        int i4 = (int) vector2ConstrainFrameBufferSize.y;
        this.height = i4;
        this.context.resize(i3, i4);
        for (int i5 = 0; i5 < this.effects.size(); i5++) {
            this.effects.get(i5).resize(i3, i4);
        }
    }

    public void rebind() {
        this.context.rebind();
        for (int i = 0; i < this.effects.size(); i++) {
            this.effects.get(i).rebind();
        }
    }

    public void update(float f) {
        for (int i = 0; i < this.effects.size(); i++) {
            this.effects.get(i).update(f);
        }
    }

    public void beginInputCapture() {
        if (this.applyingEffects) {
            throw new IllegalStateException("Capture is not available when VfxManager is applying the effects.");
        }
        if (this.capturing) {
            return;
        }
        this.capturing = true;
        this.pingPongWrapper.begin();
    }

    public void endInputCapture() {
        if (!this.capturing) {
            throw new IllegalStateException("The capturing is not started. Forgot to call #beginInputCapture()?");
        }
        this.capturing = false;
        this.pingPongWrapper.end();
    }

    public void useAsInput(VfxFrameBuffer vfxFrameBuffer) {
        useAsInput(vfxFrameBuffer.getTexture());
    }

    public void useAsInput(Texture texture) {
        if (this.capturing) {
            throw new IllegalStateException("Cannot set captured input when capture helper is currently capturing.");
        }
        if (this.applyingEffects) {
            throw new IllegalStateException("Cannot update the input buffer when applying effects.");
        }
        this.context.getBufferRenderer().renderToFbo(texture, this.pingPongWrapper.getDstBuffer());
    }

    public void applyEffects() {
        if (this.capturing) {
            throw new IllegalStateException("You should call VfxManager.endCapture() before applying the effects.");
        }
        if (this.disabled) {
            return;
        }
        Array<ChainVfxEffect> arrayFilterEnabledEffects = filterEnabledEffects(this.tmpEffectArray);
        if (arrayFilterEnabledEffects.size == 0) {
            arrayFilterEnabledEffects.clear();
            return;
        }
        this.applyingEffects = true;
        if (this.blendingEnabled) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
        }
        Gdx.gl.glDisable(GL20.GL_CULL_FACE);
        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
        this.pingPongWrapper.swap();
        this.pingPongWrapper.begin();
        for (int i = 0; i < arrayFilterEnabledEffects.size; i++) {
            arrayFilterEnabledEffects.get(i).render(this.context, this.pingPongWrapper);
            if (i < arrayFilterEnabledEffects.size - 1) {
                this.pingPongWrapper.swap();
            }
        }
        arrayFilterEnabledEffects.clear();
        this.pingPongWrapper.end();
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        if (this.blendingEnabled) {
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
        this.applyingEffects = false;
    }

    public void renderToScreen() {
        if (this.capturing) {
            throw new IllegalStateException("You should call endCapture() before rendering the result.");
        }
        if (this.blendingEnabled) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
        }
        this.context.getBufferRenderer().renderToScreen(this.pingPongWrapper.getDstBuffer());
        if (this.blendingEnabled) {
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public void renderToScreen(int i, int i2, int i3, int i4) {
        if (this.capturing) {
            throw new IllegalStateException("You should call endCapture() before rendering the result.");
        }
        if (this.blendingEnabled) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
        }
        this.context.getBufferRenderer().renderToScreen(this.pingPongWrapper.getDstBuffer(), i, i2, i3, i4);
        if (this.blendingEnabled) {
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public void renderToFbo(VfxFrameBuffer vfxFrameBuffer) {
        if (this.capturing) {
            throw new IllegalStateException("You should call endCapture() before rendering the result.");
        }
        if (this.blendingEnabled) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
        }
        this.context.getBufferRenderer().renderToFbo(this.pingPongWrapper.getDstBuffer(), vfxFrameBuffer);
        if (this.blendingEnabled) {
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public boolean anyEnabledEffects() {
        for (int i = 0; i < this.effects.size(); i++) {
            if (!this.effects.get(i).isDisabled()) {
                return true;
            }
        }
        return false;
    }

    private Array<ChainVfxEffect> filterEnabledEffects(Array<ChainVfxEffect> array) {
        for (int i = 0; i < this.effects.size(); i++) {
            ChainVfxEffect chainVfxEffect = this.effects.get(i);
            if (!chainVfxEffect.isDisabled()) {
                array.add(chainVfxEffect);
            }
        }
        return array;
    }

    public static Vector2 constrainFrameBufferSize(int i, int i2) {
        if (i < 1) {
            i = 1;
        }
        if (i2 < 1) {
            i2 = 1;
        }
        if (i <= 8192 && i2 <= 8192) {
            return tmpVec.set(i, i2);
        }
        Vector2 vector2 = tmpVec;
        vector2.set(Scaling.fit.apply(i, i2, 8192.0f, 8192.0f));
        if (vector2.x < 1.0f) {
            vector2.x = 1.0f;
        }
        if (vector2.y < 1.0f) {
            vector2.y = 1.0f;
        }
        return vector2;
    }
}
