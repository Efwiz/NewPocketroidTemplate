package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/glutils/GLFrameBuffer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class GLFrameBuffer<T extends GLTexture> implements Disposable {
    protected static final int GL_DEPTH24_STENCIL8_OES = 35056;
    protected static int defaultFramebufferHandle;
    protected GLFrameBufferBuilder<? extends GLFrameBuffer<T>> bufferBuilder;
    private IntBuffer defaultDrawBuffers;
    protected int depthStencilPackedBufferHandle;
    protected int depthbufferHandle;
    protected int framebufferHandle;
    protected boolean hasDepthStencilPackedBuffer;
    protected boolean isMRT;
    protected int stencilbufferHandle;
    protected static final Map<Application, Array<GLFrameBuffer>> buffers = new HashMap();
    protected static boolean defaultFramebufferHandleInitialized = false;
    static final IntBuffer singleInt = BufferUtils.newIntBuffer(1);
    protected Array<T> textureAttachments = new Array<>();
    protected final IntArray colorBufferHandles = new IntArray();

    protected abstract void attachFrameBufferColorTexture(T t);

    protected abstract T createTexture(FrameBufferTextureAttachmentSpec frameBufferTextureAttachmentSpec);

    protected abstract void disposeColorTexture(T t);

    GLFrameBuffer() {
    }

    protected GLFrameBuffer(GLFrameBufferBuilder<? extends GLFrameBuffer<T>> gLFrameBufferBuilder) {
        this.bufferBuilder = gLFrameBufferBuilder;
        build();
    }

    public T getColorBufferTexture() {
        return this.textureAttachments.first();
    }

    public Array<T> getTextureAttachments() {
        return this.textureAttachments;
    }

    protected void build() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        GL20 gl20 = Gdx.gl20;
        checkValidBuilder();
        if (!defaultFramebufferHandleInitialized) {
            defaultFramebufferHandleInitialized = true;
            if (Gdx.f82app.getType() == Application.ApplicationType.iOS) {
                IntBuffer intBufferAsIntBuffer = ByteBuffer.allocateDirect(64).order(ByteOrder.nativeOrder()).asIntBuffer();
                gl20.glGetIntegerv(36006, intBufferAsIntBuffer);
                defaultFramebufferHandle = intBufferAsIntBuffer.get(0);
            } else {
                defaultFramebufferHandle = 0;
            }
        }
        int iGlGenFramebuffer = gl20.glGenFramebuffer();
        this.framebufferHandle = iGlGenFramebuffer;
        gl20.glBindFramebuffer(GL20.GL_FRAMEBUFFER, iGlGenFramebuffer);
        int i10 = this.bufferBuilder.width;
        int i11 = this.bufferBuilder.height;
        boolean z = this.bufferBuilder.hasDepthRenderBuffer;
        int i12 = GL20.GL_RENDERBUFFER;
        if (z) {
            int iGlGenRenderbuffer = gl20.glGenRenderbuffer();
            this.depthbufferHandle = iGlGenRenderbuffer;
            gl20.glBindRenderbuffer(GL20.GL_RENDERBUFFER, iGlGenRenderbuffer);
            if (this.bufferBuilder.samples > 0) {
                Gdx.gl31.glRenderbufferStorageMultisample(GL20.GL_RENDERBUFFER, this.bufferBuilder.samples, this.bufferBuilder.depthRenderBufferSpec.internalFormat, i10, i11);
            } else {
                gl20.glRenderbufferStorage(GL20.GL_RENDERBUFFER, this.bufferBuilder.depthRenderBufferSpec.internalFormat, i10, i11);
            }
        }
        if (this.bufferBuilder.hasStencilRenderBuffer) {
            int iGlGenRenderbuffer2 = gl20.glGenRenderbuffer();
            this.stencilbufferHandle = iGlGenRenderbuffer2;
            gl20.glBindRenderbuffer(GL20.GL_RENDERBUFFER, iGlGenRenderbuffer2);
            if (this.bufferBuilder.samples > 0) {
                Gdx.gl31.glRenderbufferStorageMultisample(GL20.GL_RENDERBUFFER, this.bufferBuilder.samples, this.bufferBuilder.stencilRenderBufferSpec.internalFormat, i10, i11);
            } else {
                gl20.glRenderbufferStorage(GL20.GL_RENDERBUFFER, this.bufferBuilder.stencilRenderBufferSpec.internalFormat, i10, i11);
            }
        }
        if (this.bufferBuilder.hasPackedStencilDepthRenderBuffer) {
            int iGlGenRenderbuffer3 = gl20.glGenRenderbuffer();
            this.depthStencilPackedBufferHandle = iGlGenRenderbuffer3;
            gl20.glBindRenderbuffer(GL20.GL_RENDERBUFFER, iGlGenRenderbuffer3);
            if (this.bufferBuilder.samples > 0) {
                Gdx.gl31.glRenderbufferStorageMultisample(GL20.GL_RENDERBUFFER, this.bufferBuilder.samples, this.bufferBuilder.packedStencilDepthRenderBufferSpec.internalFormat, i10, i11);
            } else {
                gl20.glRenderbufferStorage(GL20.GL_RENDERBUFFER, this.bufferBuilder.packedStencilDepthRenderBufferSpec.internalFormat, i10, i11);
            }
            this.hasDepthStencilPackedBuffer = true;
        }
        boolean z2 = this.bufferBuilder.textureAttachmentSpecs.size > 1;
        this.isMRT = z2;
        if (z2) {
            Array.ArrayIterator<FrameBufferTextureAttachmentSpec> it = this.bufferBuilder.textureAttachmentSpecs.iterator();
            i4 = 0;
            while (it.hasNext()) {
                FrameBufferTextureAttachmentSpec next = it.next();
                GLTexture gLTextureCreateTexture = createTexture(next);
                this.textureAttachments.add((T) gLTextureCreateTexture);
                if (next.isColorTexture()) {
                    i7 = i12;
                    i8 = i11;
                    i9 = i10;
                    gl20.glFramebufferTexture2D(GL20.GL_FRAMEBUFFER, i4 + GL20.GL_COLOR_ATTACHMENT0, 3553, gLTextureCreateTexture.getTextureObjectHandle(), 0);
                    i4++;
                } else {
                    i7 = i12;
                    i8 = i11;
                    i9 = i10;
                    if (next.isDepth) {
                        gl20.glFramebufferTexture2D(GL20.GL_FRAMEBUFFER, GL20.GL_DEPTH_ATTACHMENT, 3553, gLTextureCreateTexture.getTextureObjectHandle(), 0);
                    } else if (next.isStencil) {
                        gl20.glFramebufferTexture2D(GL20.GL_FRAMEBUFFER, GL20.GL_STENCIL_ATTACHMENT, 3553, gLTextureCreateTexture.getTextureObjectHandle(), 0);
                    }
                }
                i12 = i7;
                i10 = i9;
                i11 = i8;
            }
            i = i12;
            i2 = i11;
            i3 = i10;
        } else {
            i = 36161;
            i2 = i11;
            i3 = i10;
            if (this.bufferBuilder.textureAttachmentSpecs.size > 0) {
                GLTexture gLTextureCreateTexture2 = createTexture(this.bufferBuilder.textureAttachmentSpecs.first());
                this.textureAttachments.add((T) gLTextureCreateTexture2);
                gl20.glBindTexture(gLTextureCreateTexture2.glTarget, gLTextureCreateTexture2.getTextureObjectHandle());
            }
            i4 = 0;
        }
        Array.ArrayIterator<FrameBufferRenderBufferAttachmentSpec> it2 = this.bufferBuilder.colorRenderBufferSpecs.iterator();
        int i13 = i4;
        while (it2.hasNext()) {
            FrameBufferRenderBufferAttachmentSpec next2 = it2.next();
            int iGlGenRenderbuffer4 = gl20.glGenRenderbuffer();
            gl20.glBindRenderbuffer(i, iGlGenRenderbuffer4);
            if (this.bufferBuilder.samples > 0) {
                i5 = i3;
                i6 = i2;
                Gdx.gl31.glRenderbufferStorageMultisample(GL20.GL_RENDERBUFFER, this.bufferBuilder.samples, next2.internalFormat, i5, i6);
            } else {
                i5 = i3;
                i6 = i2;
                gl20.glRenderbufferStorage(i, next2.internalFormat, i5, i6);
            }
            Gdx.gl.glFramebufferRenderbuffer(GL20.GL_FRAMEBUFFER, i13 + GL20.GL_COLOR_ATTACHMENT0, i, iGlGenRenderbuffer4);
            this.colorBufferHandles.add(iGlGenRenderbuffer4);
            i13++;
            i2 = i6;
            i3 = i5;
        }
        int i14 = i3;
        int i15 = i2;
        if (this.isMRT || this.bufferBuilder.samples > 0) {
            this.defaultDrawBuffers = BufferUtils.newIntBuffer(i13);
            for (int i16 = 0; i16 < i13; i16++) {
                this.defaultDrawBuffers.put(i16 + GL20.GL_COLOR_ATTACHMENT0);
            }
            this.defaultDrawBuffers.position(0);
            Gdx.gl30.glDrawBuffers(i13, this.defaultDrawBuffers);
        } else if (this.bufferBuilder.textureAttachmentSpecs.size > 0) {
            attachFrameBufferColorTexture(this.textureAttachments.first());
        }
        if (this.bufferBuilder.hasDepthRenderBuffer) {
            gl20.glFramebufferRenderbuffer(GL20.GL_FRAMEBUFFER, GL20.GL_DEPTH_ATTACHMENT, i, this.depthbufferHandle);
        }
        if (this.bufferBuilder.hasStencilRenderBuffer) {
            gl20.glFramebufferRenderbuffer(GL20.GL_FRAMEBUFFER, GL20.GL_STENCIL_ATTACHMENT, i, this.stencilbufferHandle);
        }
        if (this.bufferBuilder.hasPackedStencilDepthRenderBuffer) {
            gl20.glFramebufferRenderbuffer(GL20.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, i, this.depthStencilPackedBufferHandle);
        }
        gl20.glBindRenderbuffer(i, 0);
        Array.ArrayIterator<T> it3 = this.textureAttachments.iterator();
        while (it3.hasNext()) {
            gl20.glBindTexture(it3.next().glTarget, 0);
        }
        int iGlCheckFramebufferStatus = gl20.glCheckFramebufferStatus(GL20.GL_FRAMEBUFFER);
        if (iGlCheckFramebufferStatus == 36061 && this.bufferBuilder.hasDepthRenderBuffer && this.bufferBuilder.hasStencilRenderBuffer && (Gdx.graphics.supportsExtension("GL_OES_packed_depth_stencil") || Gdx.graphics.supportsExtension("GL_EXT_packed_depth_stencil"))) {
            if (this.bufferBuilder.hasDepthRenderBuffer) {
                gl20.glDeleteRenderbuffer(this.depthbufferHandle);
                this.depthbufferHandle = 0;
            }
            if (this.bufferBuilder.hasStencilRenderBuffer) {
                gl20.glDeleteRenderbuffer(this.stencilbufferHandle);
                this.stencilbufferHandle = 0;
            }
            if (this.bufferBuilder.hasPackedStencilDepthRenderBuffer) {
                gl20.glDeleteRenderbuffer(this.depthStencilPackedBufferHandle);
                this.depthStencilPackedBufferHandle = 0;
            }
            int iGlGenRenderbuffer5 = gl20.glGenRenderbuffer();
            this.depthStencilPackedBufferHandle = iGlGenRenderbuffer5;
            this.hasDepthStencilPackedBuffer = true;
            gl20.glBindRenderbuffer(i, iGlGenRenderbuffer5);
            if (this.bufferBuilder.samples > 0) {
                Gdx.gl31.glRenderbufferStorageMultisample(GL20.GL_RENDERBUFFER, this.bufferBuilder.samples, 35056, i14, i15);
            } else {
                gl20.glRenderbufferStorage(i, 35056, i14, i15);
            }
            gl20.glBindRenderbuffer(i, 0);
            gl20.glFramebufferRenderbuffer(GL20.GL_FRAMEBUFFER, GL20.GL_DEPTH_ATTACHMENT, i, this.depthStencilPackedBufferHandle);
            gl20.glFramebufferRenderbuffer(GL20.GL_FRAMEBUFFER, GL20.GL_STENCIL_ATTACHMENT, i, this.depthStencilPackedBufferHandle);
            iGlCheckFramebufferStatus = gl20.glCheckFramebufferStatus(GL20.GL_FRAMEBUFFER);
        }
        gl20.glBindFramebuffer(GL20.GL_FRAMEBUFFER, defaultFramebufferHandle);
        if (iGlCheckFramebufferStatus != 36053) {
            Array.ArrayIterator<T> it4 = this.textureAttachments.iterator();
            while (it4.hasNext()) {
                disposeColorTexture(it4.next());
            }
            if (this.hasDepthStencilPackedBuffer) {
                gl20.glDeleteBuffer(this.depthStencilPackedBufferHandle);
            } else {
                if (this.bufferBuilder.hasDepthRenderBuffer) {
                    gl20.glDeleteRenderbuffer(this.depthbufferHandle);
                }
                if (this.bufferBuilder.hasStencilRenderBuffer) {
                    gl20.glDeleteRenderbuffer(this.stencilbufferHandle);
                }
            }
            gl20.glDeleteFramebuffer(this.framebufferHandle);
            if (iGlCheckFramebufferStatus == 36054) {
                throw new IllegalStateException("Frame buffer couldn't be constructed: incomplete attachment");
            }
            if (iGlCheckFramebufferStatus == 36057) {
                throw new IllegalStateException("Frame buffer couldn't be constructed: incomplete dimensions");
            }
            if (iGlCheckFramebufferStatus == 36055) {
                throw new IllegalStateException("Frame buffer couldn't be constructed: missing attachment");
            }
            if (iGlCheckFramebufferStatus == 36061) {
                throw new IllegalStateException("Frame buffer couldn't be constructed: unsupported combination of formats");
            }
            if (iGlCheckFramebufferStatus == 36182) {
                throw new IllegalStateException("Frame buffer couldn't be constructed: multisample mismatch");
            }
            throw new IllegalStateException("Frame buffer couldn't be constructed: unknown error " + iGlCheckFramebufferStatus);
        }
        addManagedFrameBuffer(Gdx.f82app, this);
    }

    private void checkValidBuilder() {
        if (this.bufferBuilder.samples > 0 && !Gdx.graphics.isGL31Available()) {
            throw new GdxRuntimeException("Framebuffer multisample requires GLES 3.1+");
        }
        if (this.bufferBuilder.samples > 0 && this.bufferBuilder.textureAttachmentSpecs.size > 0) {
            throw new GdxRuntimeException("Framebuffer multisample with texture attachments not yet supported");
        }
        if (!Gdx.graphics.isGL30Available()) {
            boolean z = Gdx.graphics.supportsExtension("GL_OES_packed_depth_stencil") || Gdx.graphics.supportsExtension("GL_EXT_packed_depth_stencil");
            if (this.bufferBuilder.hasPackedStencilDepthRenderBuffer && !z) {
                throw new GdxRuntimeException("Packed Stencil/Render render buffers are not available on GLES 2.0");
            }
            if (this.bufferBuilder.textureAttachmentSpecs.size > 1) {
                throw new GdxRuntimeException("Multiple render targets not available on GLES 2.0");
            }
            Array.ArrayIterator<FrameBufferTextureAttachmentSpec> it = this.bufferBuilder.textureAttachmentSpecs.iterator();
            while (it.hasNext()) {
                FrameBufferTextureAttachmentSpec next = it.next();
                if (next.isDepth) {
                    throw new GdxRuntimeException("Depth texture FrameBuffer Attachment not available on GLES 2.0");
                }
                if (next.isStencil) {
                    throw new GdxRuntimeException("Stencil texture FrameBuffer Attachment not available on GLES 2.0");
                }
                if (next.isFloat && !Gdx.graphics.supportsExtension("OES_texture_float")) {
                    throw new GdxRuntimeException("Float texture FrameBuffer Attachment not available on GLES 2.0");
                }
            }
        }
        if (this.bufferBuilder.hasPackedStencilDepthRenderBuffer) {
            if (this.bufferBuilder.hasDepthRenderBuffer || this.bufferBuilder.hasStencilRenderBuffer) {
                throw new GdxRuntimeException("Frame buffer couldn't be constructed: packed stencil depth buffer cannot be specified together with separated depth or stencil buffer");
            }
        }
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        GL20 gl20 = Gdx.gl20;
        Array.ArrayIterator<T> it = this.textureAttachments.iterator();
        while (it.hasNext()) {
            disposeColorTexture(it.next());
        }
        gl20.glDeleteRenderbuffer(this.depthStencilPackedBufferHandle);
        gl20.glDeleteRenderbuffer(this.depthbufferHandle);
        gl20.glDeleteRenderbuffer(this.stencilbufferHandle);
        gl20.glDeleteFramebuffer(this.framebufferHandle);
        Map<Application, Array<GLFrameBuffer>> map = buffers;
        if (map.get(Gdx.f82app) != null) {
            map.get(Gdx.f82app).removeValue(this, true);
        }
    }

    public void bind() {
        Gdx.gl20.glBindFramebuffer(GL20.GL_FRAMEBUFFER, this.framebufferHandle);
    }

    public static void unbind() {
        Gdx.gl20.glBindFramebuffer(GL20.GL_FRAMEBUFFER, defaultFramebufferHandle);
    }

    public void begin() {
        bind();
        setFrameBufferViewport();
    }

    protected void setFrameBufferViewport() {
        Gdx.gl20.glViewport(0, 0, this.bufferBuilder.width, this.bufferBuilder.height);
    }

    public void end() {
        end(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
    }

    public void end(int i, int i2, int i3, int i4) {
        unbind();
        Gdx.gl20.glViewport(i, i2, i3, i4);
    }

    public void transfer(GLFrameBuffer<T> gLFrameBuffer) {
        Array.ArrayIterator<FrameBufferTextureAttachmentSpec> it = gLFrameBuffer.bufferBuilder.textureAttachmentSpecs.iterator();
        int i = 0;
        while (it.hasNext()) {
            FrameBufferTextureAttachmentSpec next = it.next();
            if (next.isDepth && (this.bufferBuilder.hasDepthRenderBuffer || this.bufferBuilder.hasPackedStencilDepthRenderBuffer)) {
                i |= 256;
            } else if (next.isStencil && (this.bufferBuilder.hasStencilRenderBuffer || this.bufferBuilder.hasPackedStencilDepthRenderBuffer)) {
                i |= 1024;
            } else if (this.colorBufferHandles.size > 0) {
                i |= 16384;
            }
        }
        transfer(gLFrameBuffer, i);
    }

    public void transfer(GLFrameBuffer<T> gLFrameBuffer, int i) {
        if (gLFrameBuffer.getWidth() != getWidth() || gLFrameBuffer.getHeight() != getHeight()) {
            throw new IllegalArgumentException("source and destination frame buffers must have same size.");
        }
        Gdx.gl.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, this.framebufferHandle);
        Gdx.gl.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, gLFrameBuffer.framebufferHandle);
        Array.ArrayIterator<FrameBufferTextureAttachmentSpec> it = gLFrameBuffer.bufferBuilder.textureAttachmentSpecs.iterator();
        int i2 = i;
        int i3 = 0;
        int i4 = 0;
        while (it.hasNext()) {
            if (it.next().isColorTexture()) {
                Gdx.gl30.glReadBuffer(i3 + GL20.GL_COLOR_ATTACHMENT0);
                IntBuffer intBuffer = singleInt;
                intBuffer.clear();
                intBuffer.put(i4 + GL20.GL_COLOR_ATTACHMENT0);
                intBuffer.flip();
                Gdx.gl30.glDrawBuffers(1, intBuffer);
                Gdx.gl30.glBlitFramebuffer(0, 0, getWidth(), getHeight(), 0, 0, gLFrameBuffer.getWidth(), gLFrameBuffer.getHeight(), i2, GL20.GL_NEAREST);
                i3++;
                i2 = 16384;
            }
            i4++;
        }
        if (i2 != 16384) {
            Gdx.gl30.glBlitFramebuffer(0, 0, getWidth(), getHeight(), 0, 0, gLFrameBuffer.getWidth(), gLFrameBuffer.getHeight(), i2, GL20.GL_NEAREST);
        }
        if (gLFrameBuffer.defaultDrawBuffers != null) {
            Gdx.gl30.glDrawBuffers(gLFrameBuffer.defaultDrawBuffers.limit(), gLFrameBuffer.defaultDrawBuffers);
        }
        Gdx.gl.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, 0);
        Gdx.gl.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
    }

    public int getFramebufferHandle() {
        return this.framebufferHandle;
    }

    public int getDepthBufferHandle() {
        return this.depthbufferHandle;
    }

    public int getColorBufferHandle(int i) {
        return this.colorBufferHandles.get(i);
    }

    public int getStencilBufferHandle() {
        return this.stencilbufferHandle;
    }

    protected int getDepthStencilPackedBuffer() {
        return this.depthStencilPackedBufferHandle;
    }

    public int getHeight() {
        return this.bufferBuilder.height;
    }

    public int getWidth() {
        return this.bufferBuilder.width;
    }

    private static void addManagedFrameBuffer(Application application, GLFrameBuffer gLFrameBuffer) {
        Map<Application, Array<GLFrameBuffer>> map = buffers;
        Array<GLFrameBuffer> array = map.get(application);
        if (array == null) {
            array = new Array<>();
        }
        array.add(gLFrameBuffer);
        map.put(application, array);
    }

    public static void invalidateAllFrameBuffers(Application application) {
        Array<GLFrameBuffer> array;
        if (Gdx.gl20 == null || (array = buffers.get(application)) == null) {
            return;
        }
        for (int i = 0; i < array.size; i++) {
            array.get(i).build();
        }
    }

    public static void clearAllFrameBuffers(Application application) {
        buffers.remove(application);
    }

    public static StringBuilder getManagedStatus(StringBuilder sb) {
        sb.append("Managed buffers/app: { ");
        Iterator<Application> it = buffers.keySet().iterator();
        while (it.hasNext()) {
            sb.append(buffers.get(it.next()).size);
            sb.append(" ");
        }
        sb.append("}");
        return sb;
    }

    public static String getManagedStatus() {
        return getManagedStatus(new StringBuilder()).toString();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/glutils/GLFrameBuffer$FrameBufferTextureAttachmentSpec.class
      classes8.dex
     */
    protected static class FrameBufferTextureAttachmentSpec {
        int format;
        int internalFormat;
        boolean isDepth;
        boolean isFloat;
        boolean isGpuOnly;
        boolean isStencil;
        int type;

        public FrameBufferTextureAttachmentSpec(int i, int i2, int i3) {
            this.internalFormat = i;
            this.format = i2;
            this.type = i3;
        }

        public boolean isColorTexture() {
            return (this.isDepth || this.isStencil) ? false : true;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/glutils/GLFrameBuffer$FrameBufferRenderBufferAttachmentSpec.class
      classes8.dex
     */
    protected static class FrameBufferRenderBufferAttachmentSpec {
        int internalFormat;

        public FrameBufferRenderBufferAttachmentSpec(int i) {
            this.internalFormat = i;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/glutils/GLFrameBuffer$GLFrameBufferBuilder.class
      classes8.dex
     */
    public static abstract class GLFrameBufferBuilder<U extends GLFrameBuffer<? extends GLTexture>> {
        protected Array<FrameBufferRenderBufferAttachmentSpec> colorRenderBufferSpecs;
        protected FrameBufferRenderBufferAttachmentSpec depthRenderBufferSpec;
        protected boolean hasDepthRenderBuffer;
        protected boolean hasPackedStencilDepthRenderBuffer;
        protected boolean hasStencilRenderBuffer;
        protected int height;
        protected FrameBufferRenderBufferAttachmentSpec packedStencilDepthRenderBufferSpec;
        protected int samples;
        protected FrameBufferRenderBufferAttachmentSpec stencilRenderBufferSpec;
        protected Array<FrameBufferTextureAttachmentSpec> textureAttachmentSpecs;
        protected int width;

        public abstract U build();

        public GLFrameBufferBuilder(int i, int i2) {
            this(i, i2, 0);
        }

        public GLFrameBufferBuilder(int i, int i2, int i3) {
            this.textureAttachmentSpecs = new Array<>();
            this.colorRenderBufferSpecs = new Array<>();
            this.width = i;
            this.height = i2;
            this.samples = i3;
        }

        public GLFrameBufferBuilder<U> addColorTextureAttachment(int i, int i2, int i3) {
            this.textureAttachmentSpecs.add(new FrameBufferTextureAttachmentSpec(i, i2, i3));
            return this;
        }

        public GLFrameBufferBuilder<U> addBasicColorTextureAttachment(Pixmap.Format format) {
            int glFormat = Pixmap.Format.toGlFormat(format);
            return addColorTextureAttachment(glFormat, glFormat, Pixmap.Format.toGlType(format));
        }

        public GLFrameBufferBuilder<U> addFloatAttachment(int i, int i2, int i3, boolean z) {
            FrameBufferTextureAttachmentSpec frameBufferTextureAttachmentSpec = new FrameBufferTextureAttachmentSpec(i, i2, i3);
            frameBufferTextureAttachmentSpec.isFloat = true;
            frameBufferTextureAttachmentSpec.isGpuOnly = z;
            this.textureAttachmentSpecs.add(frameBufferTextureAttachmentSpec);
            return this;
        }

        public GLFrameBufferBuilder<U> addDepthTextureAttachment(int i, int i2) {
            FrameBufferTextureAttachmentSpec frameBufferTextureAttachmentSpec = new FrameBufferTextureAttachmentSpec(i, GL20.GL_DEPTH_COMPONENT, i2);
            frameBufferTextureAttachmentSpec.isDepth = true;
            this.textureAttachmentSpecs.add(frameBufferTextureAttachmentSpec);
            return this;
        }

        public GLFrameBufferBuilder<U> addStencilTextureAttachment(int i, int i2) {
            FrameBufferTextureAttachmentSpec frameBufferTextureAttachmentSpec = new FrameBufferTextureAttachmentSpec(i, GL20.GL_STENCIL_ATTACHMENT, i2);
            frameBufferTextureAttachmentSpec.isStencil = true;
            this.textureAttachmentSpecs.add(frameBufferTextureAttachmentSpec);
            return this;
        }

        public GLFrameBufferBuilder<U> addDepthRenderBuffer(int i) {
            this.depthRenderBufferSpec = new FrameBufferRenderBufferAttachmentSpec(i);
            this.hasDepthRenderBuffer = true;
            return this;
        }

        public GLFrameBufferBuilder<U> addColorRenderBuffer(int i) {
            this.colorRenderBufferSpecs.add(new FrameBufferRenderBufferAttachmentSpec(i));
            return this;
        }

        public GLFrameBufferBuilder<U> addStencilRenderBuffer(int i) {
            this.stencilRenderBufferSpec = new FrameBufferRenderBufferAttachmentSpec(i);
            this.hasStencilRenderBuffer = true;
            return this;
        }

        public GLFrameBufferBuilder<U> addStencilDepthPackedRenderBuffer(int i) {
            this.packedStencilDepthRenderBufferSpec = new FrameBufferRenderBufferAttachmentSpec(i);
            this.hasPackedStencilDepthRenderBuffer = true;
            return this;
        }

        public GLFrameBufferBuilder<U> addBasicDepthRenderBuffer() {
            return addDepthRenderBuffer(GL20.GL_DEPTH_COMPONENT16);
        }

        public GLFrameBufferBuilder<U> addBasicStencilRenderBuffer() {
            return addStencilRenderBuffer(GL20.GL_STENCIL_INDEX8);
        }

        public GLFrameBufferBuilder<U> addBasicStencilDepthPackedRenderBuffer() {
            return addStencilDepthPackedRenderBuffer(35056);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/glutils/GLFrameBuffer$FrameBufferBuilder.class
      classes8.dex
     */
    public static class FrameBufferBuilder extends GLFrameBufferBuilder<FrameBuffer> {
        public FrameBufferBuilder(int i, int i2) {
            super(i, i2);
        }

        public FrameBufferBuilder(int i, int i2, int i3) {
            super(i, i2, i3);
        }

        @Override // com.badlogic.gdx.graphics.glutils.GLFrameBuffer.GLFrameBufferBuilder
        public FrameBuffer build() {
            return new FrameBuffer(this);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/glutils/GLFrameBuffer$FloatFrameBufferBuilder.class
      classes8.dex
     */
    public static class FloatFrameBufferBuilder extends GLFrameBufferBuilder<FloatFrameBuffer> {
        public FloatFrameBufferBuilder(int i, int i2) {
            super(i, i2);
        }

        public FloatFrameBufferBuilder(int i, int i2, int i3) {
            super(i, i2, i3);
        }

        @Override // com.badlogic.gdx.graphics.glutils.GLFrameBuffer.GLFrameBufferBuilder
        public FloatFrameBuffer build() {
            return new FloatFrameBuffer(this);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/glutils/GLFrameBuffer$FrameBufferCubemapBuilder.class
      classes8.dex
     */
    public static class FrameBufferCubemapBuilder extends GLFrameBufferBuilder<FrameBufferCubemap> {
        public FrameBufferCubemapBuilder(int i, int i2) {
            super(i, i2);
        }

        public FrameBufferCubemapBuilder(int i, int i2, int i3) {
            super(i, i2, i3);
        }

        @Override // com.badlogic.gdx.graphics.glutils.GLFrameBuffer.GLFrameBufferBuilder
        public FrameBufferCubemap build() {
            return new FrameBufferCubemap(this);
        }
    }
}
