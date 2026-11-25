package org.catrobat.catroid.sensing;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.PixelCopy;
import android.view.SurfaceView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.ScreenModes;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sensing/ColorDetection.class
  classes14.dex
 */
/* JADX INFO: compiled from: ColorDetection.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010*\u001a\u00020\u001d2\u0014\u0010+\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0012\u0004\u0012\u00020\u001d0\u001cH\u0007J\u0018\u0010,\u001a\u00020\u00112\u0006\u0010-\u001a\u00020\b2\u0006\u0010.\u001a\u00020\bH\u0002J&\u0010/\u001a\u0002002\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u0017022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u000206H\u0004J\u0010\u00107\u001a\u0002042\u0006\u00108\u001a\u000209H&J(\u0010:\u001a\u00020;2\u0006\u00108\u001a\u0002092\u0006\u0010(\u001a\u00020<2\u0006\u0010&\u001a\u00020<2\u0006\u0010=\u001a\u00020>H\u0004J\u001e\u0010?\u001a\u00020\u001d2\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u0017022\u0006\u00105\u001a\u000206H\u0002J\u0010\u0010@\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010AH&J\b\u0010B\u001a\u00020CH\u0004J\u0012\u0010D\u001a\u00020C2\b\u0010E\u001a\u0004\u0018\u00010\u0001H&J\b\u0010F\u001a\u00020\u001dH&R\u001a\u0010\u0007\u001a\u00020\bX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\bX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\n \u0018*\u0004\u0018\u00010\u00170\u0017X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR(\u0010\u001b\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0012\u0004\u0012\u00020\u001d0\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0014\u0010\u0002\u001a\u00020\u0003X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0014\u0010&\u001a\u00020\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\nR\u0014\u0010(\u001a\u00020\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\n¨\u0006G"}, d2 = {"Lorg/catrobat/catroid/sensing/ColorDetection;", "", "scope", "Lorg/catrobat/catroid/content/Scope;", "stageListener", "Lorg/catrobat/catroid/stage/StageListener;", "(Lorg/catrobat/catroid/content/Scope;Lorg/catrobat/catroid/stage/StageListener;)V", "bufferHeight", "", "getBufferHeight", "()I", "setBufferHeight", "(I)V", "bufferWidth", "getBufferWidth", "setBufferWidth", "cameraBitmap", "Landroid/graphics/Bitmap;", "getCameraBitmap", "()Landroid/graphics/Bitmap;", "setCameraBitmap", "(Landroid/graphics/Bitmap;)V", "look", "Lorg/catrobat/catroid/content/Look;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getLook", "()Lorg/catrobat/catroid/content/Look;", "receiveBitmapFromPixelCopy", "Lkotlin/Function1;", "", "getReceiveBitmapFromPixelCopy", "()Lkotlin/jvm/functions/Function1;", "setReceiveBitmapFromPixelCopy", "(Lkotlin/jvm/functions/Function1;)V", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "getStageListener", "()Lorg/catrobat/catroid/stage/StageListener;", "virtualHeight", "getVirtualHeight", "virtualWidth", "getVirtualWidth", "callPixelCopyWithSurfaceView", "callback", "createNewBitmap", "width", "height", "createPicture", "Lcom/badlogic/gdx/graphics/Pixmap;", "lookList", "", "projectionMatrix", "Lcom/badlogic/gdx/math/Matrix4;", "batch", "Lcom/badlogic/gdx/graphics/g2d/SpriteBatch;", "createProjectionMatrix", "project", "Lorg/catrobat/catroid/content/Project;", "createViewport", "Lcom/badlogic/gdx/utils/viewport/Viewport;", "", "camera", "Lcom/badlogic/gdx/graphics/Camera;", "drawSprites", "getLooksOfRelevantSprites", "", "isLookInvalid", "", "isParameterInvalid", "parameter", "setBufferParameters", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ColorDetection {
    private int bufferHeight;
    private int bufferWidth;
    private Bitmap cameraBitmap;
    private final Look look;
    private Function1<? super Bitmap, Unit> receiveBitmapFromPixelCopy;
    private final Scope scope;
    private final StageListener stageListener;
    private final int virtualHeight;
    private final int virtualWidth;

    public abstract Matrix4 createProjectionMatrix(Project project);

    public abstract List<Look> getLooksOfRelevantSprites();

    public abstract boolean isParameterInvalid(Object parameter);

    public abstract void setBufferParameters();

    public ColorDetection(Scope scope, StageListener stageListener) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.scope = scope;
        this.stageListener = stageListener;
        this.look = getScope().getSprite().look;
        this.virtualHeight = ProjectManager.getInstance().getCurrentProject().getXmlHeader().getVirtualScreenHeight();
        this.virtualWidth = ProjectManager.getInstance().getCurrentProject().getXmlHeader().getVirtualScreenWidth();
        this.receiveBitmapFromPixelCopy = new Function1<Bitmap, Unit>() { // from class: org.catrobat.catroid.sensing.ColorDetection$receiveBitmapFromPixelCopy$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Bitmap bitmap) {
                invoke2(bitmap);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Bitmap bitmap) {
                this.this$0.setCameraBitmap(bitmap);
            }
        };
    }

    protected Scope getScope() {
        return this.scope;
    }

    protected StageListener getStageListener() {
        return this.stageListener;
    }

    protected Look getLook() {
        return this.look;
    }

    protected final int getBufferWidth() {
        return this.bufferWidth;
    }

    protected final void setBufferWidth(int i) {
        this.bufferWidth = i;
    }

    protected final int getBufferHeight() {
        return this.bufferHeight;
    }

    protected final void setBufferHeight(int i) {
        this.bufferHeight = i;
    }

    protected final Bitmap getCameraBitmap() {
        return this.cameraBitmap;
    }

    protected final void setCameraBitmap(Bitmap bitmap) {
        this.cameraBitmap = bitmap;
    }

    protected final int getVirtualHeight() {
        return this.virtualHeight;
    }

    protected final int getVirtualWidth() {
        return this.virtualWidth;
    }

    protected final boolean isLookInvalid() {
        return getLook().getWidth() <= Float.MIN_VALUE || getLook().getHeight() <= Float.MIN_VALUE;
    }

    protected final Pixmap createPicture(List<? extends Look> lookList, Matrix4 projectionMatrix, SpriteBatch batch) {
        Intrinsics.checkNotNullParameter(lookList, "lookList");
        Intrinsics.checkNotNullParameter(projectionMatrix, "projectionMatrix");
        Intrinsics.checkNotNullParameter(batch, "batch");
        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, this.bufferWidth, this.bufferHeight, false);
        batch.setProjectionMatrix(projectionMatrix);
        frameBuffer.begin();
        batch.begin();
        Gdx.gl20.glClear(16384);
        drawSprites(lookList, batch);
        batch.end();
        Pixmap frameBufferPixmap = ScreenUtils.getFrameBufferPixmap(0, 0, frameBuffer.getWidth(), frameBuffer.getHeight());
        frameBuffer.end();
        frameBuffer.dispose();
        Intrinsics.checkNotNull(frameBufferPixmap);
        return frameBufferPixmap;
    }

    private final void drawSprites(List<? extends Look> lookList, SpriteBatch batch) {
        Iterator<T> it = lookList.iterator();
        while (it.hasNext()) {
            ((Look) it.next()).draw(batch, 1.0f);
        }
    }

    protected final Viewport createViewport(Project project, float virtualWidth, float virtualHeight, Camera camera) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(camera, "camera");
        if (project.getScreenMode() == ScreenModes.STRETCH) {
            return new ScalingViewport(Scaling.stretch, virtualWidth, virtualHeight, camera);
        }
        return new ExtendViewport(virtualWidth, virtualHeight, camera);
    }

    public final Function1<Bitmap, Unit> getReceiveBitmapFromPixelCopy() {
        return this.receiveBitmapFromPixelCopy;
    }

    public final void setReceiveBitmapFromPixelCopy(Function1<? super Bitmap, Unit> function1) {
        Intrinsics.checkNotNullParameter(function1, "<set-?>");
        this.receiveBitmapFromPixelCopy = function1;
    }

    public final void callPixelCopyWithSurfaceView(final Function1<? super Bitmap, Unit> callback) {
        final Bitmap bitmapCreateNewBitmap;
        Intrinsics.checkNotNullParameter(callback, "callback");
        SurfaceView surfaceView = StageActivity.getActiveCameraManager().getPreviewView().getSurfaceView();
        if (ProjectManager.getInstance().isCurrentProjectLandscapeMode()) {
            bitmapCreateNewBitmap = createNewBitmap(surfaceView.getHeight(), surfaceView.getWidth());
        } else {
            bitmapCreateNewBitmap = createNewBitmap(surfaceView.getWidth(), surfaceView.getHeight());
        }
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();
        PixelCopy.request(surfaceView, bitmapCreateNewBitmap, new PixelCopy.OnPixelCopyFinishedListener() { // from class: org.catrobat.catroid.sensing.ColorDetection$$ExternalSyntheticLambda0
            @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
            public final void onPixelCopyFinished(int i) {
                ColorDetection.callPixelCopyWithSurfaceView$lambda$1(callback, bitmapCreateNewBitmap, handlerThread, i);
            }
        }, new Handler(handlerThread.getLooper()));
        handlerThread.join();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void callPixelCopyWithSurfaceView$lambda$1(Function1 callback, Bitmap bitmap, HandlerThread pixelCopyHandlerThread, int i) {
        Intrinsics.checkNotNullParameter(callback, "$callback");
        Intrinsics.checkNotNullParameter(bitmap, "$bitmap");
        Intrinsics.checkNotNullParameter(pixelCopyHandlerThread, "$pixelCopyHandlerThread");
        if (i == 0) {
            callback.invoke(bitmap);
        } else {
            callback.invoke(null);
        }
        pixelCopyHandlerThread.quitSafely();
    }

    private final Bitmap createNewBitmap(int width, int height) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        return bitmapCreateBitmap;
    }
}
