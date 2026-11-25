package org.catrobat.paintroid.tools.implementation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.test.espresso.idling.CountingIdlingResource;
import com.gaurav.avnc.vnc.XKeySym;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.command.implementation.FlipCommand;
import org.catrobat.paintroid.command.implementation.RotateCommand;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.helper.CropAlgorithm;
import org.catrobat.paintroid.tools.helper.DefaultNumberRangeFilter;
import org.catrobat.paintroid.tools.helper.JavaCropAlgorithm;
import org.catrobat.paintroid.tools.helper.SetCenterAlgorithm;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;
import org.catrobat.paintroid.tools.options.ToolOptionsVisibilityController;
import org.catrobat.paintroid.tools.options.TransformToolOptionsView;
import org.catrobat.paintroid.ui.tools.NumberRangeFilter;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: TransformTool.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\b\u00106\u001a\u00020\u0014H\u0002J\b\u00107\u001a\u000208H\u0002J\u0010\u00109\u001a\u0002082\u0006\u0010:\u001a\u00020;H\u0002J8\u0010<\u001a\u0002082\u0006\u0010:\u001a\u00020;2\u0006\u0010=\u001a\u00020&2\u0006\u0010>\u001a\u00020&2\u0006\u0010?\u001a\u00020&2\u0006\u0010@\u001a\u00020&2\u0006\u0010A\u001a\u00020&H\u0002J \u0010B\u001a\u0002082\u0006\u0010:\u001a\u00020;2\u0006\u0010C\u001a\u00020&2\u0006\u0010D\u001a\u00020&H\u0014J\b\u0010E\u001a\u000208H\u0002J\b\u0010F\u001a\u000208H\u0002J\b\u0010G\u001a\u000208H\u0002J\b\u0010H\u001a\u000208H\u0002J\u0012\u0010I\u001a\u00020\u00142\b\u0010J\u001a\u0004\u0018\u00010KH\u0016J\u0012\u0010L\u001a\u0002082\b\u0010J\u001a\u0004\u0018\u00010KH\u0016J\u001a\u0010M\u001a\u00020\u00142\b\u0010J\u001a\u0004\u0018\u00010K2\u0006\u0010N\u001a\u00020\u0014H\u0016J\u0012\u0010O\u001a\u0002082\b\u0010J\u001a\u0004\u0018\u00010KH\u0016J\b\u0010P\u001a\u000208H\u0002J\b\u0010Q\u001a\u000208H\u0002J\u0010\u0010R\u001a\u0002082\u0006\u0010S\u001a\u00020TH\u0002J\b\u0010U\u001a\u000208H\u0016J(\u0010V\u001a\u0002082\u0006\u0010W\u001a\u00020&2\u0006\u0010X\u001a\u00020&2\u0006\u0010Y\u001a\u00020&2\u0006\u0010Z\u001a\u00020&H\u0014J\b\u0010[\u001a\u000208H\u0014J\b\u0010\\\u001a\u000208H\u0002J\b\u0010]\u001a\u000208H\u0002J\b\u0010^\u001a\u000208H\u0002J\b\u0010_\u001a\u000208H\u0002J\u0010\u0010`\u001a\u0002082\u0006\u0010a\u001a\u00020bH\u0002J\b\u0010c\u001a\u000208H\u0002J\u0010\u0010d\u001a\u00020K2\u0006\u0010J\u001a\u00020KH\u0016J\b\u0010e\u001a\u000208H\u0002R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010%\u001a\u00020&8\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b'\u0010(R\u0018\u0010)\u001a\u00020&8\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b*\u0010(R\u0018\u0010+\u001a\u00020&8\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b,\u0010(R\u0018\u0010-\u001a\u00020&8\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b.\u0010(R\u000e\u0010/\u001a\u000200X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00101\u001a\u0002028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b3\u00104R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006f"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/TransformTool;", "Lorg/catrobat/paintroid/tools/implementation/BaseToolWithRectangleShape;", "transformToolOptionsView", "Lorg/catrobat/paintroid/tools/options/TransformToolOptionsView;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "drawTime", "", "(Lorg/catrobat/paintroid/tools/options/TransformToolOptionsView;Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;J)V", "checkMarkClicked", "", "getCheckMarkClicked", "()Z", "setCheckMarkClicked", "(Z)V", "cropAlgorithm", "Lorg/catrobat/paintroid/tools/helper/CropAlgorithm;", "cropRunFinished", "getDrawTime", "()J", "setDrawTime", "(J)V", "isSetCenter", "maxImageResolutionInformationAlreadyShown", "rangeFilterHeight", "Lorg/catrobat/paintroid/ui/tools/NumberRangeFilter;", "rangeFilterWidth", "resizeBoundHeightYBottom", "", "getResizeBoundHeightYBottom$annotations", "()V", "resizeBoundHeightYTop", "getResizeBoundHeightYTop$annotations", "resizeBoundWidthXLeft", "getResizeBoundWidthXLeft$annotations", "resizeBoundWidthXRight", "getResizeBoundWidthXRight$annotations", "setCenterCropAlgorithm", "Lorg/catrobat/paintroid/tools/helper/SetCenterAlgorithm;", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "zeroSizeBitmap", "areResizeBordersValid", "autoCrop", "", "drawCursor", "canvas", "Landroid/graphics/Canvas;", "drawCursorCircle", "strokeWidth", "outerCircleRadius", "innerCircleRadius", "positionX", "positionY", "drawToolSpecifics", "boxWidth", "boxHeight", "executeResizeCommand", "executeSetCenterCommand", "flipHorizontal", "flipVertical", "handleDown", "coordinate", "Landroid/graphics/PointF;", "handleDownAnimations", "handleMove", "shouldAnimate", "handleUpAnimations", "initResizeBounds", "initialiseResizingState", "onApplyResizeClicked", "resizePercentage", "", "onClickOnButton", "preventThatBoxGetsTooLarge", "oldWidth", "oldHeight", "oldPosX", "oldPosY", "resetInternalState", "resetScaleAndTranslation", "rotateClockWise", "rotateCounterClockWise", "setCenter", "setRectangle", "rectangle", "Landroid/graphics/RectF;", "swapWidthAndHeight", "toolPositionCoordinates", "updateToolOptions", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class TransformTool extends BaseToolWithRectangleShape {
    private boolean checkMarkClicked;
    private final CropAlgorithm cropAlgorithm;
    private boolean cropRunFinished;
    private long drawTime;
    private boolean isSetCenter;
    private boolean maxImageResolutionInformationAlreadyShown;
    private final NumberRangeFilter rangeFilterHeight;
    private final NumberRangeFilter rangeFilterWidth;
    public float resizeBoundHeightYBottom;
    public float resizeBoundHeightYTop;
    public float resizeBoundWidthXLeft;
    public float resizeBoundWidthXRight;
    private final SetCenterAlgorithm setCenterCropAlgorithm;
    private final TransformToolOptionsView transformToolOptionsView;
    private boolean zeroSizeBitmap;

    public static /* synthetic */ void getResizeBoundHeightYBottom$annotations() {
    }

    public static /* synthetic */ void getResizeBoundHeightYTop$annotations() {
    }

    public static /* synthetic */ void getResizeBoundWidthXLeft$annotations() {
    }

    public static /* synthetic */ void getResizeBoundWidthXRight$annotations() {
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public PointF toolPositionCoordinates(PointF coordinate) {
        Intrinsics.checkNotNullParameter(coordinate, "coordinate");
        return coordinate;
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
    public TransformTool(TransformToolOptionsView transformToolOptionsView, final ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager, long j) {
        super(contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager);
        Intrinsics.checkNotNullParameter(transformToolOptionsView, "transformToolOptionsView");
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        this.transformToolOptionsView = transformToolOptionsView;
        this.drawTime = j;
        this.rotationEnabled = false;
        this.resizePointsVisible = false;
        this.respectMaximumBorderRatio = false;
        this.boxHeight = workspace.getHeight();
        this.boxWidth = workspace.getWidth();
        this.toolPosition.x = this.boxWidth / 2.0f;
        this.toolPosition.y = this.boxHeight / 2.0f;
        JavaCropAlgorithm javaCropAlgorithm = new JavaCropAlgorithm();
        this.cropAlgorithm = javaCropAlgorithm;
        this.setCenterCropAlgorithm = new SetCenterAlgorithm(javaCropAlgorithm);
        this.cropRunFinished = true;
        this.maximumBoxResolution = this.metrics.widthPixels * this.metrics.heightPixels * 4.0f;
        this.respectMaximumBoxResolution = true;
        initResizeBounds();
        toolOptionsViewController.setCallback(new ToolOptionsVisibilityController.Callback() { // from class: org.catrobat.paintroid.tools.implementation.TransformTool.1
            @Override // org.catrobat.paintroid.tools.options.ToolOptionsVisibilityController.Callback
            public void onHide() {
                if (!TransformTool.this.isSetCenter) {
                    if (TransformTool.this.zeroSizeBitmap) {
                        TransformTool.this.zeroSizeBitmap = false;
                        return;
                    } else {
                        contextCallback.showNotificationWithDuration(R.string.transform_info_text, ContextCallback.NotificationDuration.LONG);
                        return;
                    }
                }
                contextCallback.showNotificationWithDuration(R.string.set_center_info_text, ContextCallback.NotificationDuration.LONG);
            }

            @Override // org.catrobat.paintroid.tools.options.ToolOptionsVisibilityController.Callback
            public void onShow() {
                TransformTool.this.updateToolOptions();
            }
        });
        transformToolOptionsView.setCallback(new TransformToolOptionsView.Callback() { // from class: org.catrobat.paintroid.tools.implementation.TransformTool.2
            @Override // org.catrobat.paintroid.tools.options.TransformToolOptionsView.Callback
            public void autoCropClicked() {
                TransformTool.this.autoCrop();
            }

            @Override // org.catrobat.paintroid.tools.options.TransformToolOptionsView.Callback
            public void setCenterClicked() {
                TransformTool.this.setCenter();
            }

            @Override // org.catrobat.paintroid.tools.options.TransformToolOptionsView.Callback
            public void rotateCounterClockwiseClicked() {
                TransformTool.this.rotateCounterClockWise();
            }

            @Override // org.catrobat.paintroid.tools.options.TransformToolOptionsView.Callback
            public void rotateClockwiseClicked() {
                TransformTool.this.rotateClockWise();
            }

            @Override // org.catrobat.paintroid.tools.options.TransformToolOptionsView.Callback
            public void flipHorizontalClicked() {
                TransformTool.this.flipHorizontal();
            }

            @Override // org.catrobat.paintroid.tools.options.TransformToolOptionsView.Callback
            public void flipVerticalClicked() {
                TransformTool.this.flipVertical();
            }

            @Override // org.catrobat.paintroid.tools.options.TransformToolOptionsView.Callback
            public void setBoxWidth(float boxWidth) {
                TransformTool.this.boxWidth = boxWidth;
            }

            @Override // org.catrobat.paintroid.tools.options.TransformToolOptionsView.Callback
            public void setBoxHeight(float boxHeight) {
                TransformTool.this.boxHeight = boxHeight;
            }

            @Override // org.catrobat.paintroid.tools.options.TransformToolOptionsView.Callback
            public void hideToolOptions() {
                TransformTool.this.toolOptionsViewController.hide();
            }

            @Override // org.catrobat.paintroid.tools.options.TransformToolOptionsView.Callback
            public void applyResizeClicked(int resizePercentage) {
                TransformTool.this.onApplyResizeClicked(resizePercentage);
            }
        });
        DefaultNumberRangeFilter defaultNumberRangeFilter = new DefaultNumberRangeFilter(1, (int) (this.maximumBoxResolution / this.boxWidth));
        this.rangeFilterHeight = defaultNumberRangeFilter;
        DefaultNumberRangeFilter defaultNumberRangeFilter2 = new DefaultNumberRangeFilter(1, (int) (this.maximumBoxResolution / this.boxHeight));
        this.rangeFilterWidth = defaultNumberRangeFilter2;
        transformToolOptionsView.setHeightFilter(defaultNumberRangeFilter);
        transformToolOptionsView.setWidthFilter(defaultNumberRangeFilter2);
        updateToolOptions();
        toolOptionsViewController.showDelayed();
    }

    public final boolean getCheckMarkClicked() {
        return this.checkMarkClicked;
    }

    public final void setCheckMarkClicked(boolean z) {
        this.checkMarkClicked = z;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public ToolType getToolType() {
        return ToolType.TRANSFORM;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleUpAnimations(PointF coordinate) {
        super.handleUp(coordinate);
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleDownAnimations(PointF coordinate) {
        super.handleDown(coordinate);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool
    protected void resetInternalState() {
        initialiseResizingState();
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleDown(PointF coordinate) {
        if (coordinate == null) {
            return false;
        }
        if (this.isSetCenter) {
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new C42291(coordinate, null), 3, null);
            return true;
        }
        return super.handleDown(coordinate);
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.tools.implementation.TransformTool$handleDown$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TransformTool.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.tools.implementation.TransformTool$handleDown$1", f = "TransformTool.kt", i = {}, l = {223}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C42291 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ PointF $coordinate;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C42291(PointF pointF, Continuation<? super C42291> continuation) {
            super(2, continuation);
            this.$coordinate = pointF;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return TransformTool.this.new C42291(this.$coordinate, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C42291) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                TransformTool.this.toolPosition.x = this.$coordinate.x;
                TransformTool.this.toolPosition.y = this.$coordinate.y;
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C02751(TransformTool.this, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: org.catrobat.paintroid.tools.implementation.TransformTool$handleDown$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: TransformTool.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "org.catrobat.paintroid.tools.implementation.TransformTool$handleDown$1$1", f = "TransformTool.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02751 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ TransformTool this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02751(TransformTool transformTool, Continuation<? super C02751> continuation) {
                super(2, continuation);
                this.this$0 = transformTool;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02751(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02751) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.workspace.invalidate();
                return Unit.INSTANCE;
            }
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleMove(PointF coordinate, boolean shouldAnimate) {
        if (this.isSetCenter) {
            return false;
        }
        return super.handleMove(coordinate, true);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape, org.catrobat.paintroid.tools.implementation.BaseToolWithShape
    protected void drawToolSpecifics(Canvas canvas, float boxWidth, float boxHeight) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (this.isSetCenter) {
            drawCursor(canvas);
            return;
        }
        if (this.cropRunFinished) {
            this.linePaint.setColor(this.primaryShapeColor);
            float f = 2;
            this.linePaint.setStrokeWidth(this.toolStrokeWidth * f);
            PointF pointF = new PointF((-boxWidth) / f, (-boxHeight) / f);
            float f2 = boxWidth;
            int i = 0;
            float f3 = boxHeight;
            while (i < 4) {
                float f4 = 10;
                float f5 = f2 / f4;
                canvas.drawLine(pointF.x - (this.toolStrokeWidth / f), pointF.y, pointF.x + f5, pointF.y, this.linePaint);
                canvas.drawLine(pointF.x, pointF.y - (this.toolStrokeWidth / f), pointF.x, pointF.y + (f3 / f4), this.linePaint);
                float f6 = f2 / f;
                canvas.drawLine((pointF.x + f6) - f5, pointF.y, pointF.x + f6 + f5, pointF.y, this.linePaint);
                canvas.rotate(90.0f);
                float f7 = pointF.x;
                pointF.x = pointF.y;
                pointF.y = f7;
                i++;
                float f8 = f2;
                f2 = f3;
                f3 = f8;
            }
        }
    }

    private final void resetScaleAndTranslation() {
        this.workspace.resetPerspective();
        this.workspace.setScale(this.workspace.getScaleForCenterBitmap() * 0.95f);
    }

    private final void initialiseResizingState() {
        this.cropRunFinished = false;
        this.resizeBoundWidthXRight = 0.0f;
        this.resizeBoundHeightYBottom = 0.0f;
        this.resizeBoundWidthXLeft = this.workspace.getWidth();
        this.resizeBoundHeightYTop = this.workspace.getHeight();
        if (!this.checkMarkClicked) {
            resetScaleAndTranslation();
            this.resizeBoundWidthXRight = this.workspace.getWidth() - 1.0f;
            this.resizeBoundHeightYBottom = this.workspace.getHeight() - 1.0f;
            this.resizeBoundWidthXLeft = 0.0f;
            this.resizeBoundHeightYTop = 0.0f;
        } else {
            float f = 1;
            this.resizeBoundWidthXRight = this.toolPosition.x + (this.workspace.getWidth() / 2) + f;
            this.resizeBoundWidthXLeft = (this.toolPosition.x - (this.workspace.getWidth() / 2)) + f;
            this.resizeBoundHeightYBottom = this.toolPosition.y + (this.workspace.getHeight() / 2) + f;
            this.resizeBoundHeightYTop = (this.toolPosition.y - (this.workspace.getHeight() / 2)) + f;
        }
        setRectangle(new RectF(this.resizeBoundWidthXLeft, this.resizeBoundHeightYTop, this.resizeBoundWidthXRight, this.resizeBoundHeightYBottom));
        if (this.checkMarkClicked) {
            this.workspace.getPerspective().surfaceTranslationX += this.resizeBoundWidthXLeft;
            this.workspace.getPerspective().surfaceTranslationY += this.resizeBoundHeightYTop;
            this.workspace.getPerspective().setBitmapDimensions((int) this.boxWidth, (int) this.boxHeight);
            this.toolPosition.x -= this.resizeBoundWidthXLeft;
            this.toolPosition.y -= this.resizeBoundHeightYTop;
            this.checkMarkClicked = false;
        }
        this.cropRunFinished = true;
        updateToolOptions();
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.tools.implementation.TransformTool$executeSetCenterCommand$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TransformTool.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.tools.implementation.TransformTool$executeSetCenterCommand$1", f = "TransformTool.kt", i = {}, l = {TarConstants.XSTAR_PREFIX_OFFSET}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C42281 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C42281(Continuation<? super C42281> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return TransformTool.this.new C42281(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C42281) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Rect rectCrop = TransformTool.this.setCenterCropAlgorithm.crop(TransformTool.this.workspace.getBitmapOfAllLayers(), TransformTool.this.toolPosition);
                if (rectCrop != null) {
                    TransformTool.this.boxWidth = rectCrop.width() + 1.0f;
                    TransformTool.this.boxHeight = rectCrop.height() + 1.0f;
                    TransformTool.this.toolPosition.x = rectCrop.left + ((rectCrop.width() + 1) / 2.0f);
                    TransformTool.this.toolPosition.y = rectCrop.top + ((rectCrop.height() + 1) / 2.0f);
                } else {
                    TransformTool.this.toolPosition.x = TransformTool.this.boxWidth / 2.0f;
                    TransformTool.this.toolPosition.y = TransformTool.this.boxHeight / 2.0f;
                }
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C02741(TransformTool.this, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: org.catrobat.paintroid.tools.implementation.TransformTool$executeSetCenterCommand$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: TransformTool.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "org.catrobat.paintroid.tools.implementation.TransformTool$executeSetCenterCommand$1$1", f = "TransformTool.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02741 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ TransformTool this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02741(TransformTool transformTool, Continuation<? super C02741> continuation) {
                super(2, continuation);
                this.this$0 = transformTool;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02741(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02741) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.isSetCenter = false;
                this.this$0.shouldDrawRectangle = true;
                this.this$0.workspace.invalidate();
                this.this$0.toolOptionsViewController.hide();
                return Unit.INSTANCE;
            }
        }
    }

    private final void executeSetCenterCommand() {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new C42281(null), 3, null);
    }

    private final void executeResizeCommand() {
        if (this.cropRunFinished) {
            this.cropRunFinished = false;
            initResizeBounds();
            if (areResizeBordersValid()) {
                this.commandManager.addCommand(this.commandFactory.createCropCommand((int) Math.floor(this.resizeBoundWidthXLeft), (int) Math.floor(this.resizeBoundHeightYTop), (int) Math.floor(this.resizeBoundWidthXRight), (int) Math.floor(this.resizeBoundHeightYBottom), (int) this.maximumBoxResolution));
            } else {
                this.cropRunFinished = true;
                this.contextCallback.showNotification(R.string.resize_nothing_to_resize);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onApplyResizeClicked(int resizePercentage) {
        float f = resizePercentage;
        int width = (int) ((this.workspace.getWidth() / 100.0f) * f);
        int height = (int) ((this.workspace.getHeight() / 100.0f) * f);
        if (width == 0 || height == 0) {
            this.zeroSizeBitmap = true;
            this.contextCallback.showNotificationWithDuration(R.string.resize_cannot_resize_to_this_size, ContextCallback.NotificationDuration.LONG);
        } else {
            this.commandManager.addCommand(this.commandFactory.createResizeCommand(width, height));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void flipHorizontal() {
        this.commandManager.addCommand(this.commandFactory.createFlipCommand(FlipCommand.FlipDirection.FLIP_HORIZONTAL));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void flipVertical() {
        this.commandManager.addCommand(this.commandFactory.createFlipCommand(FlipCommand.FlipDirection.FLIP_VERTICAL));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void rotateCounterClockWise() {
        this.commandManager.addCommand(this.commandFactory.createRotateCommand(RotateCommand.RotateDirection.ROTATE_LEFT));
        swapWidthAndHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void rotateClockWise() {
        this.commandManager.addCommand(this.commandFactory.createRotateCommand(RotateCommand.RotateDirection.ROTATE_RIGHT));
        swapWidthAndHeight();
    }

    private final void swapWidthAndHeight() {
        float f = this.boxWidth;
        this.boxWidth = this.boxHeight;
        this.boxHeight = f;
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.tools.implementation.TransformTool$autoCrop$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TransformTool.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.tools.implementation.TransformTool$autoCrop$1", f = "TransformTool.kt", i = {}, l = {XKeySym.XK_Zacute}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C42271 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C42271(Continuation<? super C42271> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return TransformTool.this.new C42271(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C42271) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                TransformTool.this.idlingResource.increment();
                Rect rectCrop = TransformTool.this.cropAlgorithm.crop(TransformTool.this.workspace.getBitmapOfAllLayers());
                if (rectCrop != null) {
                    TransformTool.this.boxWidth = rectCrop.width() + 1.0f;
                    TransformTool.this.boxHeight = rectCrop.height() + 1.0f;
                    TransformTool.this.toolPosition.x = rectCrop.left + ((rectCrop.width() + 1) / 2.0f);
                    TransformTool.this.toolPosition.y = rectCrop.top + ((rectCrop.height() + 1) / 2.0f);
                }
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C02731(TransformTool.this, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            TransformTool.this.idlingResource.decrement();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: org.catrobat.paintroid.tools.implementation.TransformTool$autoCrop$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: TransformTool.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "org.catrobat.paintroid.tools.implementation.TransformTool$autoCrop$1$1", f = "TransformTool.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02731 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ TransformTool this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02731(TransformTool transformTool, Continuation<? super C02731> continuation) {
                super(2, continuation);
                this.this$0 = transformTool;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02731(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02731) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.workspace.invalidate();
                this.this$0.toolOptionsViewController.hide();
                return Unit.INSTANCE;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void autoCrop() {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new C42271(null), 3, null);
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.tools.implementation.TransformTool$setCenter$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TransformTool.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.tools.implementation.TransformTool$setCenter$1", f = "TransformTool.kt", i = {}, l = {440}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C42301 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C42301(Continuation<? super C42301> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return TransformTool.this.new C42301(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C42301) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                TransformTool.this.isSetCenter = true;
                TransformTool.this.shouldDrawRectangle = false;
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C02761(TransformTool.this, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            TransformTool.this.workspace.invalidate();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: org.catrobat.paintroid.tools.implementation.TransformTool$setCenter$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: TransformTool.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "org.catrobat.paintroid.tools.implementation.TransformTool$setCenter$1$1", f = "TransformTool.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02761 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ TransformTool this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02761(TransformTool transformTool, Continuation<? super C02761> continuation) {
                super(2, continuation);
                this.this$0 = transformTool;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02761(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02761) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.toolOptionsViewController.hide();
                return Unit.INSTANCE;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setCenter() {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new C42301(null), 3, null);
    }

    private final void drawCursor(Canvas canvas) {
        float fMax = Math.max(this.toolPaint.getStrokeWidth() / 2.0f, 1.0f);
        float strokeWidthForZoom = getStrokeWidthForZoom(5.0f, 1.0f, 10.0f);
        float f = strokeWidthForZoom * 2;
        float f2 = strokeWidthForZoom / 2.0f;
        float f3 = fMax + f2;
        float f4 = f3 + strokeWidthForZoom;
        Paint paint = this.linePaint;
        paint.setColor(this.contextCallback.getColor(R.color.pocketpaint_main_cursor_tool_inactive_primary_color));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidthForZoom);
        drawCursorCircle(canvas, strokeWidthForZoom, f4, f3, 0.0f, 0.0f);
        this.linePaint.setStyle(Paint.Style.FILL);
        float f5 = f + strokeWidthForZoom;
        int i = 0;
        float f6 = f2;
        while (i < 4) {
            if (i % 2 == 0) {
                this.linePaint.setColor(-3355444);
            } else {
                this.linePaint.setColor(this.contextCallback.getColor(R.color.pocketpaint_main_cursor_tool_inactive_primary_color));
            }
            float f7 = 0.0f - f4;
            float f8 = f7 - f6;
            float f9 = f7 - f5;
            canvas.drawLine(f8, 0.0f, f9, 0.0f, this.linePaint);
            float f10 = 0.0f + f4;
            float f11 = f6 + f10;
            float f12 = f5 + f10;
            canvas.drawLine(f11, 0.0f, f12, 0.0f, this.linePaint);
            canvas.drawLine(0.0f, f11, 0.0f, f12, this.linePaint);
            canvas.drawLine(0.0f, f8, 0.0f, f9, this.linePaint);
            i++;
            float f13 = i;
            f6 = (f * f13) + f2;
            f5 = ((f13 + 1.0f) * f) + strokeWidthForZoom;
        }
        this.linePaint.setStyle(Paint.Style.STROKE);
    }

    private final void drawCursorCircle(Canvas canvas, float strokeWidth, float outerCircleRadius, float innerCircleRadius, float positionX, float positionY) {
        canvas.drawCircle(positionX, positionY, outerCircleRadius, this.linePaint);
        this.linePaint.setColor(-3355444);
        canvas.drawCircle(positionX, positionY, innerCircleRadius, this.linePaint);
        this.linePaint.setColor(0);
        this.linePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(positionX, positionY, innerCircleRadius - (strokeWidth / 2.0f), this.linePaint);
    }

    private final boolean areResizeBordersValid() {
        float f = this.resizeBoundWidthXRight;
        float f2 = this.resizeBoundWidthXLeft;
        if (f < f2 || this.resizeBoundHeightYTop > this.resizeBoundHeightYBottom || f2 >= this.workspace.getWidth() || Math.min(this.resizeBoundWidthXRight, this.resizeBoundHeightYBottom) < 0.0f || this.resizeBoundHeightYTop >= this.workspace.getHeight()) {
            return false;
        }
        if (this.resizeBoundWidthXLeft == 0.0f && this.resizeBoundHeightYTop == 0.0f && this.resizeBoundWidthXRight == this.workspace.getWidth() - 1.0f && this.resizeBoundHeightYBottom == this.workspace.getHeight() - 1.0f) {
            return false;
        }
        float f3 = 1;
        return ((this.resizeBoundWidthXRight - this.resizeBoundWidthXLeft) + f3) * ((this.resizeBoundHeightYBottom - this.resizeBoundHeightYTop) + f3) <= this.maximumBoxResolution;
    }

    private final void setRectangle(RectF rectangle) {
        if (!this.checkMarkClicked) {
            this.boxWidth = (rectangle.right - rectangle.left) + 1.0f;
            this.boxHeight = (rectangle.bottom - rectangle.top) + 1.0f;
            this.toolPosition.x = rectangle.left + (this.boxWidth / 2.0f);
            this.toolPosition.y = rectangle.top + (this.boxHeight / 2.0f);
            return;
        }
        this.boxWidth = rectangle.right - rectangle.left;
        this.boxHeight = rectangle.bottom - rectangle.top;
    }

    private final void initResizeBounds() {
        this.resizeBoundWidthXLeft = this.toolPosition.x - (this.boxWidth / 2.0f);
        this.resizeBoundWidthXRight = (this.toolPosition.x + (this.boxWidth / 2.0f)) - 1.0f;
        this.resizeBoundHeightYTop = this.toolPosition.y - (this.boxHeight / 2.0f);
        this.resizeBoundHeightYBottom = (this.toolPosition.y + (this.boxHeight / 2.0f)) - 1.0f;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithShape
    public void onClickOnButton() {
        if (this.isSetCenter) {
            executeSetCenterCommand();
        } else {
            executeResizeCommand();
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape
    protected void preventThatBoxGetsTooLarge(float oldWidth, float oldHeight, float oldPosX, float oldPosY) {
        super.preventThatBoxGetsTooLarge(oldWidth, oldHeight, oldPosX, oldPosY);
        if (this.maxImageResolutionInformationAlreadyShown) {
            return;
        }
        this.contextCallback.showNotification(R.string.resize_max_image_resolution_reached);
        this.maxImageResolutionInformationAlreadyShown = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateToolOptions() {
        this.rangeFilterHeight.setMax((int) (this.maximumBoxResolution / this.boxWidth));
        this.rangeFilterWidth.setMax((int) (this.maximumBoxResolution / this.boxHeight));
        this.transformToolOptionsView.setWidth((int) this.boxWidth);
        this.transformToolOptionsView.setHeight((int) this.boxHeight);
    }
}
