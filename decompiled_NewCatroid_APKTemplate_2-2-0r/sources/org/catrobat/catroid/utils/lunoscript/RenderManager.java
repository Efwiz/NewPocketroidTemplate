package org.catrobat.catroid.utils.lunoscript;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import java.util.concurrent.ConcurrentLinkedQueue;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/RenderManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: RenderManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rJ\u000e\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rJ\u0006\u0010\u0012\u001a\u00020\u000fJ\b\u0010\u0013\u001a\u00020\u000fH\u0002J\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\u0015J\u0016\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u0015J\u0006\u0010\u001a\u001a\u00020\u000fR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/RenderManager;", "", "()V", "batch", "Lcom/badlogic/gdx/graphics/g2d/SpriteBatch;", "fbo", "Lcom/badlogic/gdx/graphics/glutils/FrameBuffer;", "fboRegion", "Lcom/badlogic/gdx/graphics/g2d/TextureRegion;", "shapeRenderer", "Lcom/badlogic/gdx/graphics/glutils/ShapeRenderer;", "tasks", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lorg/catrobat/catroid/utils/lunoscript/RenderTask;", "addTask", "", "task", "deleteTask", "dispose", "executeTasks", "getHeight", "", "getWidth", "initialize", "width", "height", "render", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RenderManager {
    private static SpriteBatch batch;
    private static FrameBuffer fbo;
    private static TextureRegion fboRegion;
    public static final RenderManager INSTANCE = new RenderManager();
    private static final ConcurrentLinkedQueue<RenderTask> tasks = new ConcurrentLinkedQueue<>();
    private static final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private RenderManager() {
    }

    public final void initialize(int width, int height) {
        FrameBuffer frameBuffer = fbo;
        if (frameBuffer != null && frameBuffer != null) {
            frameBuffer.dispose();
        }
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
        FrameBuffer frameBuffer2 = fbo;
        Intrinsics.checkNotNull(frameBuffer2);
        TextureRegion textureRegion = new TextureRegion(frameBuffer2.getColorBufferTexture());
        fboRegion = textureRegion;
        Intrinsics.checkNotNull(textureRegion);
        textureRegion.flip(false, true);
        SpriteBatch spriteBatch = batch;
        if (spriteBatch != null && spriteBatch != null) {
            spriteBatch.dispose();
        }
        batch = new SpriteBatch();
    }

    public final void dispose() {
        FrameBuffer frameBuffer = fbo;
        if (frameBuffer != null) {
            frameBuffer.dispose();
        }
        SpriteBatch spriteBatch = batch;
        if (spriteBatch != null) {
            spriteBatch.dispose();
        }
        fbo = null;
        batch = null;
    }

    public final void addTask(RenderTask task) {
        Intrinsics.checkNotNullParameter(task, "task");
        tasks.add(task);
    }

    public final void deleteTask(RenderTask task) {
        Intrinsics.checkNotNullParameter(task, "task");
        tasks.remove(task);
    }

    public final int getWidth() {
        FrameBuffer frameBuffer = fbo;
        if (frameBuffer != null) {
            return frameBuffer.getWidth();
        }
        return 0;
    }

    public final int getHeight() {
        FrameBuffer frameBuffer = fbo;
        if (frameBuffer != null) {
            return frameBuffer.getHeight();
        }
        return 0;
    }

    private final void executeTasks() {
        ConcurrentLinkedQueue<RenderTask> concurrentLinkedQueue = tasks;
        if (concurrentLinkedQueue.isEmpty()) {
            return;
        }
        FrameBuffer frameBuffer = fbo;
        Intrinsics.checkNotNull(frameBuffer);
        frameBuffer.begin();
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(16384);
        ShapeRenderer shapeRenderer2 = shapeRenderer;
        Matrix4 projectionMatrix = shapeRenderer2.getProjectionMatrix();
        FrameBuffer frameBuffer2 = fbo;
        Intrinsics.checkNotNull(frameBuffer2);
        float width = frameBuffer2.getWidth();
        FrameBuffer frameBuffer3 = fbo;
        Intrinsics.checkNotNull(frameBuffer3);
        projectionMatrix.setToOrtho2D(0.0f, 0.0f, width, frameBuffer3.getHeight());
        shapeRenderer2.begin(ShapeRenderer.ShapeType.Filled);
        for (RenderTask renderTask : concurrentLinkedQueue) {
            try {
                renderTask.getFunction().call(renderTask.getInterpreter(), CollectionsKt.listOf(new LunoValue.NativeObject(shapeRenderer)), new Token(TokenType.EOF, "", null, -1, -1));
            } catch (LunoRuntimeError unused) {
            }
        }
        shapeRenderer.end();
        FrameBuffer frameBuffer4 = fbo;
        Intrinsics.checkNotNull(frameBuffer4);
        frameBuffer4.end();
    }

    public final void render() {
        Matrix4 projectionMatrix;
        executeTasks();
        SpriteBatch spriteBatch = batch;
        if (spriteBatch != null && (projectionMatrix = spriteBatch.getProjectionMatrix()) != null) {
            projectionMatrix.setToOrtho2D(0.0f, 0.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        SpriteBatch spriteBatch2 = batch;
        if (spriteBatch2 != null) {
            spriteBatch2.begin();
        }
        SpriteBatch spriteBatch3 = batch;
        if (spriteBatch3 != null) {
            spriteBatch3.draw(fboRegion, 0.0f, 0.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        SpriteBatch spriteBatch4 = batch;
        if (spriteBatch4 != null) {
            spriteBatch4.end();
        }
    }
}
