package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/glutils/FloatFrameBuffer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class FloatFrameBuffer extends FrameBuffer {
    FloatFrameBuffer() {
        checkExtensions();
    }

    protected FloatFrameBuffer(GLFrameBuffer.GLFrameBufferBuilder<? extends GLFrameBuffer<Texture>> gLFrameBufferBuilder) {
        super(gLFrameBufferBuilder);
        checkExtensions();
    }

    public FloatFrameBuffer(int i, int i2, boolean z) {
        checkExtensions();
        GLFrameBuffer.FloatFrameBufferBuilder floatFrameBufferBuilder = new GLFrameBuffer.FloatFrameBufferBuilder(i, i2);
        floatFrameBufferBuilder.addFloatAttachment(GL30.GL_RGBA32F, GL20.GL_RGBA, 5126, false);
        if (z) {
            floatFrameBufferBuilder.addBasicDepthRenderBuffer();
        }
        this.bufferBuilder = floatFrameBufferBuilder;
        build();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.badlogic.gdx.graphics.glutils.FrameBuffer, com.badlogic.gdx.graphics.glutils.GLFrameBuffer
    public Texture createTexture(GLFrameBuffer.FrameBufferTextureAttachmentSpec frameBufferTextureAttachmentSpec) {
        Texture texture = new Texture(new FloatTextureData(this.bufferBuilder.width, this.bufferBuilder.height, frameBufferTextureAttachmentSpec.internalFormat, frameBufferTextureAttachmentSpec.format, frameBufferTextureAttachmentSpec.type, frameBufferTextureAttachmentSpec.isGpuOnly));
        if (Gdx.f82app.getType() == Application.ApplicationType.Desktop || Gdx.f82app.getType() == Application.ApplicationType.Applet) {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }
        texture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        return texture;
    }

    private void checkExtensions() {
        if (Gdx.graphics.isGL30Available() && Gdx.f82app.getType() == Application.ApplicationType.WebGL && !Gdx.graphics.supportsExtension("EXT_color_buffer_float")) {
            throw new GdxRuntimeException("Extension EXT_color_buffer_float not supported!");
        }
    }
}
