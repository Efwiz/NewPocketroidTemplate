package org.catrobat.paintroid.tools.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import androidx.test.espresso.idling.CountingIdlingResource;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.options.SprayToolOptionsView;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: SprayTool.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\b\u0010C\u001a\u00020DH\u0002J\b\u0010E\u001a\u00020\u0018H\u0002J\b\u0010F\u001a\u00020\u0018H\u0002J\b\u0010G\u001a\u00020DH\u0002J\u0010\u0010H\u001a\u00020D2\u0006\u0010I\u001a\u00020 H\u0016J\u0012\u0010J\u001a\u00020\"2\b\u0010K\u001a\u0004\u0018\u00010\u0018H\u0016J\u0012\u0010L\u001a\u00020D2\b\u0010K\u001a\u0004\u0018\u00010\u0018H\u0016J\u001a\u0010M\u001a\u00020\"2\b\u0010K\u001a\u0004\u0018\u00010\u00182\u0006\u0010N\u001a\u00020\"H\u0016J\u0012\u0010O\u001a\u00020\"2\b\u0010K\u001a\u0004\u0018\u00010\u0018H\u0016J\u0012\u0010P\u001a\u00020D2\b\u0010K\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010Q\u001a\u00020DH\u0002J\u0012\u0010R\u001a\u00020D2\b\u0010S\u001a\u0004\u0018\u00010TH\u0016J\u0012\u0010U\u001a\u00020D2\b\u0010S\u001a\u0004\u0018\u00010TH\u0016J\b\u0010V\u001a\u00020DH\u0014J\u0006\u0010W\u001a\u00020DJ\b\u0010X\u001a\u00020DH\u0002J\u0010\u0010Y\u001a\u00020\u00182\u0006\u0010K\u001a\u00020\u0018H\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010!\u001a\u00020\"8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b#\u0010$\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u000e\u0010)\u001a\u00020*X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R$\u0010/\u001a\u0002008\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b1\u0010$\u001a\u0004\b2\u00103\"\u0004\b4\u00105R*\u00106\u001a\b\u0012\u0004\u0012\u00020\u0018078\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b8\u0010$\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u001a\u0010=\u001a\u00020>X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010@\"\u0004\bA\u0010B¨\u0006Z"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/SprayTool;", "Lorg/catrobat/paintroid/tools/implementation/BaseTool;", "sprayToolOptionsView", "Lorg/catrobat/paintroid/tools/options/SprayToolOptionsView;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "drawTime", "", "(Lorg/catrobat/paintroid/tools/options/SprayToolOptionsView;Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;J)V", "getContextCallback", "()Lorg/catrobat/paintroid/tools/ContextCallback;", "setContextCallback", "(Lorg/catrobat/paintroid/tools/ContextCallback;)V", "currentCoordinate", "Landroid/graphics/PointF;", "getDrawTime", "()J", "setDrawTime", "(J)V", "previewBitmap", "Landroid/graphics/Bitmap;", "previewCanvas", "Landroid/graphics/Canvas;", "sprayActive", "", "getSprayActive$annotations", "()V", "getSprayActive", "()Z", "setSprayActive", "(Z)V", "sprayRadius", "", "getSprayToolOptionsView", "()Lorg/catrobat/paintroid/tools/options/SprayToolOptionsView;", "setSprayToolOptionsView", "(Lorg/catrobat/paintroid/tools/options/SprayToolOptionsView;)V", "sprayToolScope", "Lkotlinx/coroutines/CoroutineScope;", "getSprayToolScope$annotations", "getSprayToolScope", "()Lkotlinx/coroutines/CoroutineScope;", "setSprayToolScope", "(Lkotlinx/coroutines/CoroutineScope;)V", "sprayedPoints", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "getSprayedPoints$annotations", "getSprayedPoints", "()Ljava/util/concurrent/ConcurrentLinkedQueue;", "setSprayedPoints", "(Ljava/util/concurrent/ConcurrentLinkedQueue;)V", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "setToolType", "(Lorg/catrobat/paintroid/tools/ToolType;)V", "addSprayCommand", "", "createMidPoint", "createRandomPointInCircle", "createSprayPatternAsync", "draw", "canvas", "handleDown", "coordinate", "handleDownAnimations", "handleMove", "shouldAnimate", "handleUp", "handleUpAnimations", "hideToolOptions", "onRestoreInstanceState", "bundle", "Landroid/os/Bundle;", "onSaveInstanceState", "resetInternalState", "resetRadiusToStrokeWidth", "showToolOptions", "toolPositionCoordinates", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class SprayTool extends BaseTool {
    private ContextCallback contextCallback;
    private PointF currentCoordinate;
    private long drawTime;
    private Bitmap previewBitmap;
    private final Canvas previewCanvas;
    private boolean sprayActive;
    private int sprayRadius;
    private SprayToolOptionsView sprayToolOptionsView;
    private CoroutineScope sprayToolScope;
    private ConcurrentLinkedQueue<PointF> sprayedPoints;
    private ToolType toolType;

    public static /* synthetic */ void getSprayActive$annotations() {
    }

    public static /* synthetic */ void getSprayToolScope$annotations() {
    }

    public static /* synthetic */ void getSprayedPoints$annotations() {
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public PointF toolPositionCoordinates(PointF coordinate) {
        Intrinsics.checkNotNullParameter(coordinate, "coordinate");
        return coordinate;
    }

    public final SprayToolOptionsView getSprayToolOptionsView() {
        return this.sprayToolOptionsView;
    }

    public final void setSprayToolOptionsView(SprayToolOptionsView sprayToolOptionsView) {
        Intrinsics.checkNotNullParameter(sprayToolOptionsView, "<set-?>");
        this.sprayToolOptionsView = sprayToolOptionsView;
    }

    public ContextCallback getContextCallback() {
        return this.contextCallback;
    }

    public void setContextCallback(ContextCallback contextCallback) {
        Intrinsics.checkNotNullParameter(contextCallback, "<set-?>");
        this.contextCallback = contextCallback;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public long getDrawTime() {
        return this.drawTime;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void setDrawTime(long j) {
        this.drawTime = j;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SprayTool(SprayToolOptionsView sprayToolOptionsView, ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager, long j) {
        super(contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager);
        Intrinsics.checkNotNullParameter(sprayToolOptionsView, "sprayToolOptionsView");
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        this.sprayToolOptionsView = sprayToolOptionsView;
        this.contextCallback = contextCallback;
        this.drawTime = j;
        this.sprayToolScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getMain());
        this.sprayedPoints = new ConcurrentLinkedQueue<>();
        this.toolType = ToolType.SPRAY;
        this.sprayRadius = 30;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(workspace.getWidth(), workspace.getHeight(), Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(workspace.w… Bitmap.Config.ARGB_8888)");
        this.previewBitmap = bitmapCreateBitmap;
        this.previewCanvas = new Canvas(this.previewBitmap);
        this.sprayToolOptionsView.setCallback(new SprayToolOptionsView.Callback() { // from class: org.catrobat.paintroid.tools.implementation.SprayTool.1
            @Override // org.catrobat.paintroid.tools.options.SprayToolOptionsView.Callback
            public void radiusChanged(int radius) {
                SprayTool.this.sprayRadius = (radius * 2) + 30;
            }
        });
        this.sprayToolOptionsView.setCurrentPaint(toolPaint.getBitmapPaint());
        toolPaint.setStrokeWidth(5.0f);
        toolOptionsViewController.showDelayed();
    }

    public final CoroutineScope getSprayToolScope() {
        return this.sprayToolScope;
    }

    public final void setSprayToolScope(CoroutineScope coroutineScope) {
        Intrinsics.checkNotNullParameter(coroutineScope, "<set-?>");
        this.sprayToolScope = coroutineScope;
    }

    public final ConcurrentLinkedQueue<PointF> getSprayedPoints() {
        return this.sprayedPoints;
    }

    public final void setSprayedPoints(ConcurrentLinkedQueue<PointF> concurrentLinkedQueue) {
        Intrinsics.checkNotNullParameter(concurrentLinkedQueue, "<set-?>");
        this.sprayedPoints = concurrentLinkedQueue;
    }

    public final boolean getSprayActive() {
        return this.sprayActive;
    }

    public final void setSprayActive(boolean z) {
        this.sprayActive = z;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public ToolType getToolType() {
        return this.toolType;
    }

    public void setToolType(ToolType toolType) {
        Intrinsics.checkNotNullParameter(toolType, "<set-?>");
        this.toolType = toolType;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleUpAnimations(PointF coordinate) {
        showToolOptions();
        this.toolOptionsViewController.animateBottomAndTopNavigation(false);
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleDownAnimations(PointF coordinate) {
        hideToolOptions();
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        canvas.save();
        canvas.drawBitmap(this.previewBitmap, 0.0f, 0.0f, (Paint) null);
        canvas.restore();
    }

    private final void hideToolOptions() {
        if (this.toolOptionsViewController.getToolOptionsShown() && this.toolOptionsViewController.getToolSpecificOptionsLayout().getVisibility() == 0) {
            this.toolOptionsViewController.slideUp(this.toolOptionsViewController.getToolSpecificOptionsLayout(), true, false);
        }
    }

    private final void showToolOptions() {
        if (this.toolOptionsViewController.getToolOptionsShown() || this.toolOptionsViewController.getToolSpecificOptionsLayout().getVisibility() != 4) {
            return;
        }
        this.toolOptionsViewController.slideDown(this.toolOptionsViewController.getToolSpecificOptionsLayout(), false, true);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleUp(PointF coordinate) {
        CoroutineScopeKt.cancel$default(this.sprayToolScope, null, 1, null);
        this.currentCoordinate = coordinate;
        addSprayCommand();
        showToolOptions();
        super.handleUp(coordinate);
        return true;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleMove(PointF coordinate, boolean shouldAnimate) {
        if (shouldAnimate) {
            hideToolOptions();
        }
        super.handleMove(coordinate, shouldAnimate);
        this.currentCoordinate = coordinate;
        return true;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleDown(PointF coordinate) {
        if (this.sprayActive || coordinate == null) {
            return false;
        }
        super.handleDown(coordinate);
        this.sprayActive = true;
        this.currentCoordinate = coordinate;
        createSprayPatternAsync();
        return true;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle != null) {
            bundle.putInt("BUNDLE_RADIUS", this.sprayRadius);
        }
        CoroutineScopeKt.cancel$default(this.sprayToolScope, null, 1, null);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        if (bundle == null) {
            return;
        }
        int i = bundle.getInt("BUNDLE_RADIUS");
        this.sprayRadius = i;
        getSprayToolOptionsView().setRadius(i);
    }

    private final void addSprayCommand() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(getSprayedPoints());
        float[] fArr = new float[arrayList.size() * 2];
        int i = 0;
        for (Object obj : arrayList) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            PointF pointF = (PointF) obj;
            int i3 = i * 2;
            fArr[i3] = pointF.x;
            fArr[i3 + 1] = pointF.y;
            i = i2;
        }
        this.commandManager.addCommand(this.commandFactory.createSprayCommand(fArr, getDrawPaint()));
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.tools.implementation.SprayTool$createSprayPatternAsync$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: SprayTool.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.tools.implementation.SprayTool$createSprayPatternAsync$1", f = "SprayTool.kt", i = {}, l = {211}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C42261 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C42261(Continuation<? super C42261> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SprayTool.this.new C42261(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C42261) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0 || i == 1) {
                ResultKt.throwOnFailure(obj);
                do {
                    int i2 = SprayTool.this.sprayRadius / 30;
                    SprayTool sprayTool = SprayTool.this;
                    for (int i3 = 0; i3 < i2; i3++) {
                        if (sprayTool.getSprayedPoints().isEmpty()) {
                            PointF pointFCreateMidPoint = sprayTool.createMidPoint();
                            if (sprayTool.workspace.contains(pointFCreateMidPoint)) {
                                sprayTool.previewCanvas.drawPoint(pointFCreateMidPoint.x, pointFCreateMidPoint.y, sprayTool.getDrawPaint());
                                sprayTool.getSprayedPoints().add(pointFCreateMidPoint);
                            }
                        }
                        PointF pointFCreateRandomPointInCircle = sprayTool.createRandomPointInCircle();
                        if (sprayTool.workspace.contains(pointFCreateRandomPointInCircle)) {
                            sprayTool.previewCanvas.drawPoint(pointFCreateRandomPointInCircle.x, pointFCreateRandomPointInCircle.y, sprayTool.getDrawPaint());
                            sprayTool.getSprayedPoints().add(pointFCreateRandomPointInCircle);
                        }
                    }
                    this.label = 1;
                } while (DelayKt.delay(1L, this) != coroutine_suspended);
                return coroutine_suspended;
            }
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
    }

    private final void createSprayPatternAsync() {
        CoroutineScope CoroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault());
        this.sprayToolScope = CoroutineScope;
        BuildersKt__Builders_commonKt.launch$default(CoroutineScope, null, null, new C42261(null), 3, null);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool
    protected void resetInternalState() {
        CoroutineScopeKt.cancel$default(this.sprayToolScope, null, 1, null);
        this.sprayActive = false;
        this.sprayedPoints.clear();
        this.previewBitmap.eraseColor(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PointF createRandomPointInCircle() {
        PointF pointF = new PointF();
        float fPow = this.sprayRadius * ((float) Math.pow(Random.INSTANCE.nextFloat(), 0.5f));
        double dNextFloat = ((double) (Random.INSTANCE.nextFloat() * 2.0f)) * 3.141592653589793d;
        float fCos = ((float) Math.cos(dNextFloat)) * fPow;
        PointF pointF2 = this.currentCoordinate;
        pointF.x = fCos + (pointF2 == null ? 0.0f : pointF2.x);
        float fSin = fPow * ((float) Math.sin(dNextFloat));
        PointF pointF3 = this.currentCoordinate;
        pointF.y = fSin + (pointF3 != null ? pointF3.y : 0.0f);
        return pointF;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PointF createMidPoint() {
        PointF pointF = new PointF();
        PointF pointF2 = this.currentCoordinate;
        pointF.x = pointF2 == null ? 0.0f : pointF2.x;
        PointF pointF3 = this.currentCoordinate;
        pointF.y = pointF3 != null ? pointF3.y : 0.0f;
        return pointF;
    }

    public final void resetRadiusToStrokeWidth() {
        this.toolPaint.setStrokeWidth(this.sprayToolOptionsView.getRadius());
    }
}
