package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/glutils/FrameBuffer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class FrameBuffer extends GLFrameBuffer<Texture> {
    FrameBuffer() {
    }

    protected FrameBuffer(GLFrameBuffer.GLFrameBufferBuilder<? extends GLFrameBuffer<Texture>> gLFrameBufferBuilder) {
        super(gLFrameBufferBuilder);
    }

    public FrameBuffer(Pixmap.Format format, int i, int i2, boolean z) {
        this(format, i, i2, z, false);
    }

    public FrameBuffer(Pixmap.Format format, int i, int i2, boolean z, boolean z2) {
        GLFrameBuffer.FrameBufferBuilder frameBufferBuilder = new GLFrameBuffer.FrameBufferBuilder(i, i2);
        frameBufferBuilder.addBasicColorTextureAttachment(format);
        if (z) {
            frameBufferBuilder.addBasicDepthRenderBuffer();
        }
        if (z2) {
            frameBufferBuilder.addBasicStencilRenderBuffer();
        }
        this.bufferBuilder = frameBufferBuilder;
        build();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.badlogic.gdx.graphics.glutils.GLFrameBuffer
    public Texture createTexture(GLFrameBuffer.FrameBufferTextureAttachmentSpec frameBufferTextureAttachmentSpec) {
        Texture texture = new Texture(new GLOnlyTextureData(this.bufferBuilder.width, this.bufferBuilder.height, 0, frameBufferTextureAttachmentSpec.internalFormat, frameBufferTextureAttachmentSpec.format, frameBufferTextureAttachmentSpec.type));
        if (!frameBufferTextureAttachmentSpec.isDepth || Gdx.f82app.getType() != Application.ApplicationType.WebGL) {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        texture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        return texture;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.badlogic.gdx.graphics.glutils.GLFrameBuffer
    public void disposeColorTexture(Texture texture) {
        texture.dispose();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.badlogic.gdx.graphics.glutils.GLFrameBuffer
    public void attachFrameBufferColorTexture(Texture texture) {
        Gdx.gl20.glFramebufferTexture2D(GL20.GL_FRAMEBUFFER, GL20.GL_COLOR_ATTACHMENT0, 3553, texture.getTextureObjectHandle(), 0);
    }

    public static void unbind() {
        GLFrameBuffer.unbind();
    }
}
