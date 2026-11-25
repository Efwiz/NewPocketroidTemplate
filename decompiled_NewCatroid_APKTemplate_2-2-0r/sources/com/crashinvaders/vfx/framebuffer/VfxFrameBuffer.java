package com.crashinvaders.vfx.framebuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.crashinvaders.vfx.gl.VfxGLUtils;
import com.crashinvaders.vfx.gl.VfxGlViewport;

/* JADX INFO: loaded from: classes8.dex */
public class VfxFrameBuffer implements Disposable {
    private static int bufferNesting;
    private static final OrthographicCamera tmpCam = new OrthographicCamera();
    private static final Matrix4 zeroTransform = new Matrix4();
    private boolean drawing;
    private boolean initialized;
    private final Pixmap.Format pixelFormat;
    private int previousFboHandle;
    private final Matrix4 localProjection = new Matrix4();
    private final Matrix4 localTransform = new Matrix4();
    private final RendererManager renderers = new RendererManager();
    private final VfxGlViewport preservedViewport = new VfxGlViewport();
    private FrameBuffer fbo = null;

    public interface Renderer {
        void assignLocalMatrices(Matrix4 matrix4, Matrix4 matrix5);

        void flush();

        void restoreOwnMatrices();
    }

    public static int getBufferNesting() {
        return bufferNesting;
    }

    public VfxFrameBuffer(Pixmap.Format format) {
        this.pixelFormat = format;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        reset();
    }

    public void initialize(int i, int i2) {
        if (this.initialized) {
            dispose();
        }
        this.initialized = true;
        int boundFboHandle = getBoundFboHandle();
        FrameBuffer frameBuffer = new FrameBuffer(this.pixelFormat, i, i2, false);
        this.fbo = frameBuffer;
        frameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        Gdx.gl20.glBindFramebuffer(GL20.GL_FRAMEBUFFER, boundFboHandle);
        OrthographicCamera orthographicCamera = tmpCam;
        orthographicCamera.setToOrtho(false, i, i2);
        this.localProjection.set(orthographicCamera.combined);
        this.localTransform.set(zeroTransform);
    }

    public void reset() {
        if (this.initialized) {
            this.initialized = false;
            this.fbo.dispose();
            this.fbo = null;
        }
    }

    public FrameBuffer getFbo() {
        return this.fbo;
    }

    public Texture getTexture() {
        FrameBuffer frameBuffer = this.fbo;
        if (frameBuffer == null) {
            return null;
        }
        return frameBuffer.getColorBufferTexture();
    }

    public Pixmap.Format getPixelFormat() {
        return this.pixelFormat;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public boolean isDrawing() {
        return this.drawing;
    }

    public void addRenderer(Renderer renderer) {
        this.renderers.addRenderer(renderer);
    }

    public void removeRenderer(Renderer renderer) {
        this.renderers.removeRenderer(renderer);
    }

    public void clearRenderers() {
        this.renderers.clearRenderers();
    }

    public void setProjectionMatrix(Matrix4 matrix4) {
        this.localProjection.set(matrix4);
    }

    public void setTransformMatrix(Matrix4 matrix4) {
        this.localTransform.set(matrix4);
    }

    public Matrix4 getProjectionMatrix() {
        return this.localProjection;
    }

    public Matrix4 getTransformMatrix() {
        return this.localTransform;
    }

    public void begin() {
        bufferNesting++;
        if (!this.initialized) {
            throw new IllegalStateException("VfxFrameBuffer must be initialized first");
        }
        if (this.drawing) {
            throw new IllegalStateException("Already drawing");
        }
        this.drawing = true;
        this.renderers.flush();
        this.previousFboHandle = getBoundFboHandle();
        this.preservedViewport.set(getViewport());
        Gdx.gl20.glBindFramebuffer(GL20.GL_FRAMEBUFFER, this.fbo.getFramebufferHandle());
        Gdx.gl20.glViewport(0, 0, getFbo().getWidth(), getFbo().getHeight());
        this.renderers.assignLocalMatrices(this.localProjection, this.localTransform);
    }

    public void end() {
        bufferNesting--;
        if (!this.initialized) {
            throw new IllegalStateException("VfxFrameBuffer must be initialized first");
        }
        if (!this.drawing) {
            throw new IllegalStateException("Is not drawing");
        }
        if (getBoundFboHandle() != this.fbo.getFramebufferHandle()) {
            throw new IllegalStateException("Current bound OpenGL FBO's handle doesn't match to wrapped one. It seems like begin/end order was violated.");
        }
        this.drawing = false;
        this.renderers.flush();
        Gdx.gl20.glBindFramebuffer(GL20.GL_FRAMEBUFFER, this.previousFboHandle);
        Gdx.gl20.glViewport(this.preservedViewport.x, this.preservedViewport.y, this.preservedViewport.width, this.preservedViewport.height);
        this.renderers.restoreOwnMatrices();
    }

    protected int getBoundFboHandle() {
        return VfxGLUtils.getBoundFboHandle();
    }

    protected VfxGlViewport getViewport() {
        return VfxGLUtils.getViewport();
    }

    private static class RendererManager implements Renderer {
        private final Array<Renderer> renderers = new Array<>();

        RendererManager() {
        }

        public void addRenderer(Renderer renderer) {
            this.renderers.add(renderer);
        }

        public void removeRenderer(Renderer renderer) {
            this.renderers.removeValue(renderer, true);
        }

        public void clearRenderers() {
            this.renderers.clear();
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.Renderer
        public void flush() {
            for (int i = 0; i < this.renderers.size; i++) {
                this.renderers.get(i).flush();
            }
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.Renderer
        public void assignLocalMatrices(Matrix4 matrix4, Matrix4 matrix5) {
            for (int i = 0; i < this.renderers.size; i++) {
                this.renderers.get(i).assignLocalMatrices(matrix4, matrix5);
            }
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.Renderer
        public void restoreOwnMatrices() {
            for (int i = 0; i < this.renderers.size; i++) {
                this.renderers.get(i).restoreOwnMatrices();
            }
        }
    }

    public static abstract class RendererAdapter implements Renderer {
        private final Matrix4 preservedProjection = new Matrix4();
        private final Matrix4 preservedTransform = new Matrix4();

        protected abstract Matrix4 getProjection();

        protected abstract Matrix4 getTransform();

        protected abstract void setProjection(Matrix4 matrix4);

        protected abstract void setTransform(Matrix4 matrix4);

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.Renderer
        public void assignLocalMatrices(Matrix4 matrix4, Matrix4 matrix5) {
            this.preservedProjection.set(getProjection());
            this.preservedTransform.set(getTransform());
            setProjection(matrix4);
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.Renderer
        public void restoreOwnMatrices() {
            setProjection(this.preservedProjection);
        }
    }

    public static class BatchRendererAdapter extends RendererAdapter implements Pool.Poolable {
        private Batch batch;

        public BatchRendererAdapter() {
        }

        public BatchRendererAdapter(Batch batch) {
            initialize(batch);
        }

        public BatchRendererAdapter initialize(Batch batch) {
            this.batch = batch;
            return this;
        }

        @Override // com.badlogic.gdx.utils.Pool.Poolable
        public void reset() {
            this.batch = null;
        }

        public Batch getBatch() {
            return this.batch;
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.Renderer
        public void flush() {
            this.batch.isDrawing();
            this.batch.flush();
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.RendererAdapter
        protected Matrix4 getProjection() {
            return this.batch.getProjectionMatrix();
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.RendererAdapter
        protected Matrix4 getTransform() {
            return this.batch.getTransformMatrix();
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.RendererAdapter
        protected void setProjection(Matrix4 matrix4) {
            this.batch.setProjectionMatrix(matrix4);
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.RendererAdapter
        protected void setTransform(Matrix4 matrix4) {
            this.batch.setTransformMatrix(matrix4);
        }
    }

    public static class ShapeRendererAdapter extends RendererAdapter implements Pool.Poolable {
        private ShapeRenderer shapeRenderer;

        public ShapeRendererAdapter() {
        }

        public ShapeRendererAdapter(ShapeRenderer shapeRenderer) {
            initialize(shapeRenderer);
        }

        public ShapeRendererAdapter initialize(ShapeRenderer shapeRenderer) {
            this.shapeRenderer = shapeRenderer;
            return this;
        }

        @Override // com.badlogic.gdx.utils.Pool.Poolable
        public void reset() {
            this.shapeRenderer = null;
        }

        public ShapeRenderer getShapeRenderer() {
            return this.shapeRenderer;
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.Renderer
        public void flush() {
            if (this.shapeRenderer.isDrawing()) {
                this.shapeRenderer.flush();
            }
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.RendererAdapter
        protected Matrix4 getProjection() {
            return this.shapeRenderer.getProjectionMatrix();
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.RendererAdapter
        protected Matrix4 getTransform() {
            return this.shapeRenderer.getTransformMatrix();
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.RendererAdapter
        protected void setProjection(Matrix4 matrix4) {
            this.shapeRenderer.setProjectionMatrix(matrix4);
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.RendererAdapter
        protected void setTransform(Matrix4 matrix4) {
            this.shapeRenderer.setTransformMatrix(matrix4);
        }
    }
}
