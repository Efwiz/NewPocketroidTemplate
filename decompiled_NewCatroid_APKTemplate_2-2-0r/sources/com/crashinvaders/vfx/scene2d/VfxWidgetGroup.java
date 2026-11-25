package com.crashinvaders.vfx.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class VfxWidgetGroup extends WidgetGroup {
    private final VfxManager vfxManager;
    private boolean initialized = false;
    private boolean resizePending = false;
    private boolean matchWidgetSize = false;
    private boolean updateManager = true;
    private final CustomRendererAdapter rendererAdapter = new CustomRendererAdapter();

    @Override // com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.utils.Cullable
    @Deprecated
    public void setCullingArea(Rectangle rectangle) {
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    @Deprecated
    public void setTransform(boolean z) {
    }

    public VfxWidgetGroup(Pixmap.Format format) {
        this.vfxManager = new VfxManager(format);
        super.setTransform(false);
    }

    public VfxManager getVfxManager() {
        return this.vfxManager;
    }

    public boolean isMatchWidgetSize() {
        return this.matchWidgetSize;
    }

    public void setMatchWidgetSize(boolean z) {
        if (this.matchWidgetSize == z) {
            return;
        }
        this.matchWidgetSize = z;
        this.resizePending = true;
    }

    public boolean isUpdateManager() {
        return this.updateManager;
    }

    public void setUpdateManager(boolean z) {
        this.updateManager = z;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
    protected void setStage(Stage stage) {
        super.setStage(stage);
        if (stage != null) {
            initialize();
        } else {
            reset();
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.Actor
    protected void sizeChanged() {
        super.sizeChanged();
        this.resizePending = true;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
    public void act(float f) {
        super.act(f);
        if (this.updateManager) {
            this.vfxManager.update(f);
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float f) {
        validate();
        VfxFrameBuffer resultBuffer = this.vfxManager.getResultBuffer();
        batch.end();
        performPendingResize();
        this.vfxManager.cleanUpBuffers();
        resultBuffer.addRenderer(this.rendererAdapter);
        this.vfxManager.beginInputCapture();
        batch.begin();
        validate();
        drawChildren(batch, f);
        batch.end();
        this.vfxManager.endInputCapture();
        resultBuffer.removeRenderer(this.rendererAdapter);
        this.vfxManager.applyEffects();
        batch.begin();
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * f);
        batch.draw(this.vfxManager.getResultBuffer().getFbo().getColorBufferTexture(), getX(), getY(), getWidth(), getHeight(), 0.0f, 0.0f, 1.0f, 1.0f);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group
    protected void drawChildren(Batch batch, float f) {
        boolean zIsCapturing = this.vfxManager.isCapturing();
        if (zIsCapturing) {
            super.setTransform(true);
        }
        if (!zIsCapturing) {
            clipBegin();
        }
        super.drawChildren(batch, f);
        batch.flush();
        if (zIsCapturing) {
            super.setTransform(false);
        }
        if (zIsCapturing) {
            return;
        }
        clipEnd();
    }

    private void initialize() {
        if (this.initialized) {
            return;
        }
        performPendingResize();
        this.rendererAdapter.initialize(getStage().getBatch());
        this.resizePending = false;
        this.initialized = true;
    }

    private void reset() {
        if (this.initialized) {
            this.vfxManager.dispose();
            this.rendererAdapter.reset();
            this.resizePending = false;
            this.initialized = false;
        }
    }

    private void performPendingResize() {
        int iFloor;
        int iFloor2;
        if (this.resizePending) {
            if (((int) getWidth()) == 0 || ((int) getHeight()) == 0) {
                iFloor = 16;
                iFloor2 = 16;
            } else if (this.matchWidgetSize) {
                iFloor = MathUtils.floor(getWidth());
                iFloor2 = MathUtils.floor(getHeight());
            } else {
                Viewport viewport = getStage().getViewport();
                float screenWidth = viewport.getScreenWidth() / viewport.getWorldWidth();
                iFloor = MathUtils.floor(getWidth() * screenWidth);
                iFloor2 = MathUtils.floor(getHeight() * screenWidth);
                this.rendererAdapter.updateOwnProjection();
            }
            this.vfxManager.resize(iFloor, iFloor2);
            this.resizePending = false;
        }
    }

    private class CustomRendererAdapter implements VfxFrameBuffer.Renderer {
        private Batch batch;
        private final Matrix4 ownProjection;
        private final Matrix4 preservedProjection;

        private CustomRendererAdapter() {
            this.preservedProjection = new Matrix4();
            this.ownProjection = new Matrix4();
        }

        public void initialize(Batch batch) {
            this.batch = batch;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            this.batch = null;
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.Renderer
        public void flush() {
            this.batch.flush();
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.Renderer
        public void assignLocalMatrices(Matrix4 matrix4, Matrix4 matrix5) {
            this.preservedProjection.set(this.batch.getProjectionMatrix());
            if (!VfxWidgetGroup.this.matchWidgetSize) {
                matrix4 = this.ownProjection;
            }
            this.batch.setProjectionMatrix(matrix4);
        }

        @Override // com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.Renderer
        public void restoreOwnMatrices() {
            this.batch.setProjectionMatrix(this.preservedProjection);
        }

        public void updateOwnProjection() {
            this.ownProjection.setToOrtho2D(0.0f, 0.0f, VfxWidgetGroup.this.getWidth(), VfxWidgetGroup.this.getHeight());
        }
    }
}
