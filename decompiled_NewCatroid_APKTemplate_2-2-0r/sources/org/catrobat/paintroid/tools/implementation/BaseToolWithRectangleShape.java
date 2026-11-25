package org.catrobat.paintroid.tools.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.exifinterface.media.ExifInterface;
import androidx.test.espresso.idling.CountingIdlingResource;
import com.reandroid.arsc.value.ResConfigBase;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;

/* JADX INFO: compiled from: BaseToolWithRectangleShape.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000Æ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0006\n\u0002\b\u000b\b&\u0018\u00002\u00020\u0001:\u0004\u008e\u0001\u008f\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u0010\u0010B\u001a\u00020.2\u0006\u0010C\u001a\u00020>H\u0007J\b\u0010D\u001a\u00020.H\u0004J+\u0010E\u001a\u00020.2\u0006\u0010F\u001a\u00020\u00162\u0006\u0010G\u001a\u00020\u00162\f\u0010H\u001a\b\u0012\u0004\u0012\u00020>0IH\u0002¢\u0006\u0002\u0010JJ\b\u0010K\u001a\u00020LH\u0002J\u0010\u0010M\u001a\u00020L2\u0006\u0010N\u001a\u00020OH\u0016J \u0010P\u001a\u00020L2\u0006\u0010N\u001a\u00020O2\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u0016H\u0014J(\u0010Q\u001a\u00020L2\u0006\u0010N\u001a\u00020O2\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u0016H\u0002J \u0010R\u001a\u00020L2\u0006\u0010N\u001a\u00020O2\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J \u0010S\u001a\u00020L2\u0006\u0010N\u001a\u00020O2\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010T\u001a\u00020L2\u0006\u0010N\u001a\u00020OH\u0016J \u0010U\u001a\u00020L2\u0006\u0010N\u001a\u00020O2\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u0016H\u0014J\u0018\u0010V\u001a\u00020 2\u0006\u0010W\u001a\u00020\u00162\u0006\u0010X\u001a\u00020\u0016H\u0002J(\u0010Y\u001a\u00020Z2\u0006\u0010[\u001a\u00020\u00162\u0006\u0010\\\u001a\u00020\u00162\u0006\u0010]\u001a\u00020*2\u0006\u0010^\u001a\u00020*H\u0016J\u0010\u0010_\u001a\u00020*2\u0006\u0010`\u001a\u00020*H\u0002J\u0013\u0010a\u001a\b\u0012\u0004\u0012\u00020>0IH\u0002¢\u0006\u0002\u0010bJ\u0012\u0010c\u001a\u00020.2\b\u0010C\u001a\u0004\u0018\u00010>H\u0016J\u001a\u0010d\u001a\u00020.2\b\u0010C\u001a\u0004\u0018\u00010>2\u0006\u0010e\u001a\u00020.H\u0016J\u0012\u0010f\u001a\u00020.2\b\u0010C\u001a\u0004\u0018\u00010>H\u0016J\b\u0010g\u001a\u00020LH\u0002J\u0006\u0010h\u001a\u00020LJ\u000e\u0010i\u001a\u00020L2\u0006\u0010j\u001a\u00020.JS\u0010k\u001a\u00020L\"\b\b\u0000\u0010l*\u00020m\"\b\b\u0001\u0010n*\u00020m2\u0016\u0010o\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u0001Hl0I\"\u0004\u0018\u0001Hl2\u0018\u0010p\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002Hl0r\u0012\u0004\u0012\u0002Hn0qH\u0002¢\u0006\u0002\u0010sJ\b\u0010t\u001a\u00020LH\u0002J\u0018\u0010u\u001a\u00020L2\u0006\u0010v\u001a\u00020\u00162\u0006\u0010w\u001a\u00020\u0016H\u0002J\u0012\u0010x\u001a\u00020L2\b\u0010y\u001a\u0004\u0018\u00010zH\u0016J\u0012\u0010{\u001a\u00020L2\b\u0010y\u001a\u0004\u0018\u00010zH\u0016J)\u0010|\u001a\u00020L2\u0006\u0010}\u001a\u00020\u00162\u0006\u0010~\u001a\u00020\u00162\u0006\u0010\u007f\u001a\u00020\u00162\u0007\u0010\u0080\u0001\u001a\u00020\u0016H\u0014J\u0019\u0010\u0081\u0001\u001a\u00020L2\u0006\u0010v\u001a\u00020\u00162\u0006\u0010w\u001a\u00020\u0016H\u0002J\u001c\u0010\u0082\u0001\u001a\u00020L2\u0007\u0010\u0083\u0001\u001a\u00020\u00162\b\u0010\u0084\u0001\u001a\u00030\u0085\u0001H\u0002J\"\u0010\u0086\u0001\u001a\u00020L2\u0006\u0010F\u001a\u00020\u00162\u0006\u0010G\u001a\u00020\u00162\u0007\u0010\u0087\u0001\u001a\u00020\u0016H\u0002J\u001c\u0010\u0088\u0001\u001a\u00020L2\u0007\u0010\u0089\u0001\u001a\u00020\u00162\b\u0010\u0084\u0001\u001a\u00030\u0085\u0001H\u0002J\u0019\u0010\u008a\u0001\u001a\u00020L2\u0006\u0010v\u001a\u00020\u00162\u0006\u0010w\u001a\u00020\u0016H\u0002J\u0012\u0010\u008b\u0001\u001a\u00020L2\t\u0010\u008c\u0001\u001a\u0004\u0018\u00010$J\t\u0010\u008d\u0001\u001a\u00020LH\u0002R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0015\u001a\u00020\u00168\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u0017\u0010\u0018R\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u001aR\u0018\u0010\u001b\u001a\u00020\u00168\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u001c\u0010\u0018R\u0018\u0010\u001d\u001a\u00020\u00168\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u001e\u0010\u0018R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010#\u001a\u0004\u0018\u00010$8\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b%\u0010\u0018R\u0012\u0010&\u001a\u00020\u00168\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010(X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010)\u001a\u00020*8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010-\u001a\u00020.8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010/\u001a\u00020.8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u00100\u001a\u00020.8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u00105\u001a\u00020.8\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b6\u0010\u0018R\u0018\u00107\u001a\u00020\u00168\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b8\u0010\u0018R\u0012\u00109\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u001aR\u0012\u0010:\u001a\u00020.8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020<X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020>X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010?\u001a\u00020\u00168\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0090\u0001"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/BaseToolWithRectangleShape;", "Lorg/catrobat/paintroid/tools/implementation/BaseToolWithShape;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "(Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;)V", "arcPaint", "Landroid/graphics/Paint;", "arcPath", "Landroid/graphics/Path;", "arrowPaint", "arrowPath", "boxHeight", "", "getBoxHeight$annotations", "()V", "boxResizeMargin", "Ljava/lang/Float;", "boxRotation", "getBoxRotation$annotations", "boxWidth", "getBoxWidth$annotations", "currentAction", "Lorg/catrobat/paintroid/tools/implementation/BaseToolWithRectangleShape$FloatingBoxAction;", "downTimer", "Landroid/os/CountDownTimer;", "drawingBitmap", "Landroid/graphics/Bitmap;", "getDrawingBitmap$annotations", "maximumBoxResolution", "overlayDrawable", "Landroid/graphics/drawable/Drawable;", "rectangleShrinkingOnHighlight", "", "resizeAction", "Lorg/catrobat/paintroid/tools/implementation/BaseToolWithRectangleShape$ResizeAction;", "resizePointsVisible", "", "respectMaximumBorderRatio", "respectMaximumBoxResolution", "rotationArrowArcRadius", "rotationArrowArcStrokeWidth", "rotationArrowHeadSize", "rotationArrowOffset", "rotationEnabled", "getRotationEnabled$annotations", "rotationSymbolDistance", "getRotationSymbolDistance$annotations", "rotationSymbolWidth", "shouldDrawRectangle", "tempDrawingRectangle", "Landroid/graphics/RectF;", "tempToolPosition", "Landroid/graphics/PointF;", "toolStrokeWidth", "touchDownPositionX", "touchDownPositionY", "boxContainsPoint", "coordinate", "boxIntersectsWorkspace", "checkRotationPoints", "clickCoordinatesRotatedX", "clickCoordinatesRotatedY", "rotationPoints", "", "(FF[Landroid/graphics/PointF;)Z", "createOverlayDrawable", "", "draw", "canvas", "Landroid/graphics/Canvas;", "drawBitmap", "drawOverlayDrawable", "drawRectangle", "drawRotationArrows", "drawShape", "drawToolSpecifics", "getAction", "clickCoordinatesX", "clickCoordinatesY", "getAutoScrollDirection", "Landroid/graphics/Point;", "pointX", "pointY", ResConfigBase.NAME_screenWidth, ResConfigBase.NAME_screenHeight, "getDensitySpecificValue", "value", "getRotationPoints", "()[Landroid/graphics/PointF;", "handleDown", "handleMove", "shouldAnimate", "handleUp", "hideToolSpecificLayout", "highlightBox", "highlightBoxWhenClickInBox", "highlight", "ifNotNull", ExifInterface.GPS_DIRECTION_TRUE, "", "R", "options", "block", "Lkotlin/Function1;", "", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "initScaleDependedValues", "move", "deltaX", "deltaY", "onRestoreInstanceState", "bundle", "Landroid/os/Bundle;", "onSaveInstanceState", "preventThatBoxGetsTooLarge", "oldWidth", "oldHeight", "oldPosX", "oldPosY", "resize", "resizeHeight", "deltaYCorrected", "rotationRadian", "", "resizeOnFrame", "margin", "resizeWidth", "deltaXCorrected", "rotate", "setBitmap", "bitmap", "showToolSpecificLayout", "FloatingBoxAction", "ResizeAction", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class BaseToolWithRectangleShape extends BaseToolWithShape {
    private final Paint arcPaint;
    private final Path arcPath;
    private final Paint arrowPaint;
    private final Path arrowPath;
    public float boxHeight;
    private Float boxResizeMargin;
    public float boxRotation;
    public float boxWidth;
    private FloatingBoxAction currentAction;
    private CountDownTimer downTimer;
    public Bitmap drawingBitmap;
    public float maximumBoxResolution;
    private Drawable overlayDrawable;
    public int rectangleShrinkingOnHighlight;
    private ResizeAction resizeAction;
    public boolean resizePointsVisible;
    public boolean respectMaximumBorderRatio;
    public boolean respectMaximumBoxResolution;
    private final int rotationArrowArcRadius;
    private final int rotationArrowArcStrokeWidth;
    private final int rotationArrowHeadSize;
    private final int rotationArrowOffset;
    public boolean rotationEnabled;
    public float rotationSymbolDistance;
    private Float rotationSymbolWidth;
    public boolean shouldDrawRectangle;
    private final RectF tempDrawingRectangle;
    private final PointF tempToolPosition;
    public float toolStrokeWidth;
    private float touchDownPositionX;
    private float touchDownPositionY;

    /* JADX INFO: compiled from: BaseToolWithRectangleShape.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/BaseToolWithRectangleShape$FloatingBoxAction;", "", "(Ljava/lang/String;I)V", "NONE", "MOVE", "RESIZE", "ROTATE", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    private enum FloatingBoxAction {
        NONE,
        MOVE,
        RESIZE,
        ROTATE
    }

    /* JADX INFO: compiled from: BaseToolWithRectangleShape.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u000b\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\f"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/BaseToolWithRectangleShape$ResizeAction;", "", "(Ljava/lang/String;I)V", "NONE", "TOP", "RIGHT", "BOTTOM", "LEFT", "TOPLEFT", "TOPRIGHT", "BOTTOMLEFT", "BOTTOMRIGHT", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    private enum ResizeAction {
        NONE,
        TOP,
        RIGHT,
        BOTTOM,
        LEFT,
        TOPLEFT,
        TOPRIGHT,
        BOTTOMLEFT,
        BOTTOMRIGHT
    }

    /* JADX INFO: compiled from: BaseToolWithRectangleShape.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ResizeAction.values().length];
            iArr[ResizeAction.LEFT.ordinal()] = 1;
            iArr[ResizeAction.RIGHT.ordinal()] = 2;
            iArr[ResizeAction.TOP.ordinal()] = 3;
            iArr[ResizeAction.TOPRIGHT.ordinal()] = 4;
            iArr[ResizeAction.TOPLEFT.ordinal()] = 5;
            iArr[ResizeAction.BOTTOM.ordinal()] = 6;
            iArr[ResizeAction.BOTTOMLEFT.ordinal()] = 7;
            iArr[ResizeAction.BOTTOMRIGHT.ordinal()] = 8;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static /* synthetic */ void getBoxHeight$annotations() {
    }

    public static /* synthetic */ void getBoxRotation$annotations() {
    }

    public static /* synthetic */ void getBoxWidth$annotations() {
    }

    public static /* synthetic */ void getDrawingBitmap$annotations() {
    }

    public static /* synthetic */ void getRotationEnabled$annotations() {
    }

    public static /* synthetic */ void getRotationSymbolDistance$annotations() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseToolWithRectangleShape(ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager) {
        super(contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager);
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        this.shouldDrawRectangle = true;
        Float fValueOf = Float.valueOf(0.0f);
        this.boxResizeMargin = fValueOf;
        this.rotationSymbolWidth = fValueOf;
        float scale = ((contextCallback.getOrientation() == ContextCallback.ScreenOrientation.PORTRAIT ? this.metrics.widthPixels : this.metrics.heightPixels) / workspace.getScale()) - (2 * getInverselyProportionalSizeForZoom(100.0f));
        this.boxWidth = scale;
        this.boxHeight = scale;
        if (scale > workspace.getHeight() * 2.0f || this.boxWidth > workspace.getWidth() * 2.0f) {
            this.boxHeight = workspace.getHeight() * 2.0f;
            this.boxWidth = workspace.getWidth() * 2.0f;
        }
        this.rectangleShrinkingOnHighlight = 0;
        this.rotationArrowArcStrokeWidth = getDensitySpecificValue(2);
        this.rotationArrowArcRadius = getDensitySpecificValue(8);
        this.rotationArrowHeadSize = getDensitySpecificValue(3);
        this.rotationArrowOffset = getDensitySpecificValue(3);
        this.resizeAction = ResizeAction.NONE;
        this.rotationEnabled = false;
        this.resizePointsVisible = true;
        this.respectMaximumBorderRatio = true;
        this.respectMaximumBoxResolution = false;
        this.maximumBoxResolution = 0.0f;
        initScaleDependedValues();
        createOverlayDrawable();
        Paint paint = this.linePaint;
        paint.reset();
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        Paint paint2 = new Paint();
        this.arcPaint = paint2;
        paint2.setColor(-1);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeCap(Paint.Cap.BUTT);
        Paint paint3 = new Paint();
        this.arrowPaint = paint3;
        paint3.setColor(-1);
        paint3.setStyle(Paint.Style.FILL);
        this.arcPath = new Path();
        this.arrowPath = new Path();
        this.tempDrawingRectangle = new RectF();
        this.tempToolPosition = new PointF();
    }

    private final int getDensitySpecificValue(int value) {
        return (value * Math.max(160, this.metrics.densityDpi)) / 160;
    }

    private final void initScaleDependedValues() {
        this.toolStrokeWidth = getStrokeWidthForZoom(3.0f, 1.0f, 8.0f);
        this.boxResizeMargin = Float.valueOf(getInverselyProportionalSizeForZoom(20.0f));
        this.rotationSymbolDistance = getInverselyProportionalSizeForZoom(20.0f) * 2;
        this.rotationSymbolWidth = Float.valueOf(getInverselyProportionalSizeForZoom(30.0f));
    }

    public final void setBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            this.drawingBitmap = bitmap;
        }
        this.workspace.invalidate();
    }

    private final void hideToolSpecificLayout() {
        if (!(this instanceof TextTool) && this.toolOptionsViewController.isVisible() && this.toolOptionsViewController.getToolSpecificOptionsLayout().getVisibility() == 0) {
            this.toolOptionsViewController.slideDown(this.toolOptionsViewController.getToolSpecificOptionsLayout(), true, false);
        }
        this.toolOptionsViewController.animateBottomAndTopNavigation(true);
    }

    private final void showToolSpecificLayout() {
        if (!(this instanceof TextTool) && !this.toolOptionsViewController.isVisible() && this.toolOptionsViewController.getToolSpecificOptionsLayout().getVisibility() == 4) {
            this.toolOptionsViewController.slideUp(this.toolOptionsViewController.getToolSpecificOptionsLayout(), false, true);
        }
        this.toolOptionsViewController.animateBottomAndTopNavigation(false);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleDown(PointF coordinate) {
        this.movedDistance.set(0.0f, 0.0f);
        if (coordinate != null) {
            this.previousEventCoordinate = new PointF(coordinate.x, coordinate.y);
            this.currentAction = getAction(coordinate.x, coordinate.y);
        }
        this.touchDownPositionX = this.toolPosition.x;
        this.touchDownPositionY = this.toolPosition.y;
        return true;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleMove(PointF coordinate, boolean shouldAnimate) {
        if (this.previousEventCoordinate == null || this.currentAction == null) {
            return false;
        }
        hideToolSpecificLayout();
        ifNotNull(new PointF[]{coordinate, this.previousEventCoordinate}, new Function1<List<? extends PointF>, Unit>() { // from class: org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape.handleMove.1

            /* JADX INFO: renamed from: org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape$handleMove$1$WhenMappings */
            /* JADX INFO: compiled from: BaseToolWithRectangleShape.kt */
            @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[FloatingBoxAction.values().length];
                    iArr[FloatingBoxAction.MOVE.ordinal()] = 1;
                    iArr[FloatingBoxAction.RESIZE.ordinal()] = 2;
                    iArr[FloatingBoxAction.ROTATE.ordinal()] = 3;
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends PointF> list) {
                invoke2(list);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<? extends PointF> dstr$coordinate$previousEventCoordinate) {
                Intrinsics.checkNotNullParameter(dstr$coordinate$previousEventCoordinate, "$dstr$coordinate$previousEventCoordinate");
                PointF pointF = dstr$coordinate$previousEventCoordinate.get(0);
                PointF pointF2 = dstr$coordinate$previousEventCoordinate.get(1);
                PointF pointF3 = new PointF(pointF.x - pointF2.x, pointF.y - pointF2.y);
                BaseToolWithRectangleShape.this.movedDistance.set(BaseToolWithRectangleShape.this.movedDistance.x + Math.abs(pointF3.x), BaseToolWithRectangleShape.this.movedDistance.y + Math.abs(pointF3.y));
                pointF2.set(pointF.x, pointF.y);
                FloatingBoxAction floatingBoxAction = BaseToolWithRectangleShape.this.currentAction;
                int i = floatingBoxAction == null ? -1 : WhenMappings.$EnumSwitchMapping$0[floatingBoxAction.ordinal()];
                if (i == 1) {
                    BaseToolWithRectangleShape.this.move(pointF3.x, pointF3.y);
                } else if (i == 2) {
                    BaseToolWithRectangleShape.this.resize(pointF3.x, pointF3.y);
                } else {
                    if (i != 3) {
                        return;
                    }
                    BaseToolWithRectangleShape.this.rotate(pointF3.x, pointF3.y);
                }
            }
        });
        return true;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleUp(PointF coordinate) {
        if (this.previousEventCoordinate == null) {
            return false;
        }
        showToolSpecificLayout();
        ifNotNull(new PointF[]{coordinate, this.previousEventCoordinate}, new Function1<List<? extends PointF>, Unit>() { // from class: org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape.handleUp.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends PointF> list) {
                invoke2(list);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<? extends PointF> dstr$coordinate$previousEventCoordinate) {
                Intrinsics.checkNotNullParameter(dstr$coordinate$previousEventCoordinate, "$dstr$coordinate$previousEventCoordinate");
                PointF pointF = dstr$coordinate$previousEventCoordinate.get(0);
                PointF pointF2 = dstr$coordinate$previousEventCoordinate.get(1);
                BaseToolWithRectangleShape.this.movedDistance.x += Math.abs(pointF.x - pointF2.x);
                BaseToolWithRectangleShape.this.movedDistance.y += Math.abs(pointF.y - pointF2.y);
            }
        });
        return true;
    }

    public final boolean boxContainsPoint(PointF coordinate) {
        Intrinsics.checkNotNullParameter(coordinate, "coordinate");
        float f = coordinate.x - this.toolPosition.x;
        float f2 = coordinate.y - this.toolPosition.y;
        double d = -((this.boxRotation * 3.1415927f) / 180.0f);
        float fCos = ((((float) Math.cos(d)) * f) - (((float) Math.sin(d)) * f2)) + this.toolPosition.x;
        float fSin = (f * ((float) Math.sin(d))) + (f2 * ((float) Math.cos(d))) + this.toolPosition.y;
        float f3 = 2;
        return fCos > this.toolPosition.x - (this.boxWidth / f3) && fCos < this.toolPosition.x + (this.boxWidth / f3) && fSin > this.toolPosition.y - (this.boxHeight / f3) && fSin < this.toolPosition.y + (this.boxHeight / f3);
    }

    protected final boolean boxIntersectsWorkspace() {
        float f = 2;
        return this.toolPosition.x - (this.boxWidth / f) < ((float) this.workspace.getWidth()) && this.toolPosition.y - (this.boxHeight / f) < ((float) this.workspace.getHeight()) && this.toolPosition.x + (this.boxWidth / f) >= 0.0f && this.toolPosition.y + (this.boxHeight / f) >= 0.0f;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        drawShape(canvas);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithShape
    public void drawShape(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        initScaleDependedValues();
        float f = this.boxWidth;
        float f2 = this.boxHeight;
        float f3 = this.boxRotation;
        this.tempToolPosition.set(this.toolPosition.x, this.toolPosition.y);
        canvas.save();
        canvas.translate(this.tempToolPosition.x, this.tempToolPosition.y);
        canvas.rotate(f3);
        if (this.resizePointsVisible) {
            drawToolSpecifics(canvas, f, f2);
        }
        if (this.rotationEnabled) {
            drawRotationArrows(canvas, f, f2);
        }
        drawBitmap(canvas, f, f2);
        if (this.overlayDrawable != null) {
            drawOverlayDrawable(canvas, f, f2, f3);
        }
        if (this.shouldDrawRectangle) {
            drawRectangle(canvas, f, f2);
        }
        drawToolSpecifics(canvas, f, f2);
        canvas.restore();
    }

    private final void drawRotationArrows(Canvas canvas, float boxWidth, float boxHeight) {
        float inverselyProportionalSizeForZoom = getInverselyProportionalSizeForZoom(this.rotationArrowArcStrokeWidth);
        float inverselyProportionalSizeForZoom2 = getInverselyProportionalSizeForZoom(this.rotationArrowArcRadius);
        float inverselyProportionalSizeForZoom3 = getInverselyProportionalSizeForZoom(this.rotationArrowHeadSize);
        float inverselyProportionalSizeForZoom4 = getInverselyProportionalSizeForZoom(this.rotationArrowOffset);
        this.arcPaint.setStrokeWidth(inverselyProportionalSizeForZoom);
        float f = boxHeight;
        int i = 0;
        float f2 = boxWidth;
        while (i < 4) {
            float f3 = 2;
            float f4 = ((-f2) / f3) - inverselyProportionalSizeForZoom4;
            float f5 = ((-f) / f3) - inverselyProportionalSizeForZoom4;
            this.arcPath.reset();
            float f6 = f4 - inverselyProportionalSizeForZoom2;
            float f7 = f5 - inverselyProportionalSizeForZoom2;
            this.tempDrawingRectangle.set(f6, f7, f4 + inverselyProportionalSizeForZoom2, f5 + inverselyProportionalSizeForZoom2);
            this.arcPath.addArc(this.tempDrawingRectangle, 180.0f, 90.0f);
            canvas.drawPath(this.arcPath, this.arcPaint);
            Path path = this.arrowPath;
            path.reset();
            path.moveTo(f6 - inverselyProportionalSizeForZoom3, f5);
            path.lineTo(f6 + inverselyProportionalSizeForZoom3, f5);
            path.lineTo(f6, f5 + inverselyProportionalSizeForZoom3);
            path.close();
            path.moveTo(f4, f7 - inverselyProportionalSizeForZoom3);
            path.lineTo(f4, f7 + inverselyProportionalSizeForZoom3);
            path.lineTo(f4 + inverselyProportionalSizeForZoom3, f7);
            path.close();
            canvas.drawPath(this.arrowPath, this.arrowPaint);
            canvas.rotate(90.0f);
            i++;
            float f8 = f;
            f = f2;
            f2 = f8;
        }
    }

    protected void drawBitmap(Canvas canvas, float boxWidth, float boxHeight) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Bitmap bitmap = this.drawingBitmap;
        if (bitmap == null) {
            return;
        }
        float f = 2;
        this.tempDrawingRectangle.set((-boxWidth) / f, (-boxHeight) / f, boxWidth / f, boxHeight / f);
        canvas.clipRect(this.tempDrawingRectangle);
        Paint paint = new Paint();
        LayerContracts.Layer currentLayer = this.workspace.getLayerModel().getCurrentLayer();
        if (currentLayer != null) {
            paint.setAlpha(currentLayer.getValueForOpacityPercentage());
        }
        canvas.drawBitmap(bitmap, (Rect) null, this.tempDrawingRectangle, paint);
    }

    private final void drawOverlayDrawable(Canvas canvas, float boxWidth, float boxHeight, float boxRotation) {
        int iMin = (int) (Math.min(boxWidth, boxHeight) / 8);
        canvas.save();
        canvas.rotate(-boxRotation);
        Drawable drawable = this.overlayDrawable;
        if (drawable != null) {
            int i = -iMin;
            drawable.setBounds(i, i, iMin, iMin);
            drawable.draw(canvas);
        }
        canvas.restore();
    }

    private final void drawRectangle(Canvas canvas, float boxWidth, float boxHeight) {
        this.linePaint.setStrokeWidth(this.toolStrokeWidth);
        this.linePaint.setColor(this.secondaryShapeColor);
        RectF rectF = this.tempDrawingRectangle;
        float f = 2;
        int i = this.rectangleShrinkingOnHighlight;
        rectF.set(((-boxWidth) / f) + i, ((-boxHeight) / f) + i, (boxWidth / f) - i, (boxHeight / f) - i);
        canvas.drawRect(this.tempDrawingRectangle, this.linePaint);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void move(float deltaX, float deltaY) {
        this.toolPosition.x += deltaX;
        this.toolPosition.y += deltaY;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void rotate(float deltaX, float deltaY) {
        PointF pointF = this.previousEventCoordinate;
        if (pointF == null) {
            return;
        }
        PointF pointF2 = new PointF(pointF.x, pointF.y);
        double d = (pointF.x - deltaX) - this.toolPosition.x;
        float degrees = (this.boxRotation + (((float) Math.toDegrees(-(Math.atan2((pointF.y - deltaY) - this.toolPosition.y, d) - Math.atan2(pointF2.y - this.toolPosition.y, pointF2.x - this.toolPosition.x)))) + 360.0f)) % 360.0f;
        this.boxRotation = degrees;
        if (degrees > 180.0f) {
            this.boxRotation = degrees - 360.0f;
        }
    }

    private final void resizeOnFrame(float clickCoordinatesRotatedX, float clickCoordinatesRotatedY, float margin) {
        ResizeAction resizeAction;
        ResizeAction resizeAction2;
        float f = 2;
        if (clickCoordinatesRotatedX < (this.toolPosition.x - (this.boxWidth / f)) + margin) {
            this.resizeAction = ResizeAction.LEFT;
        } else if (clickCoordinatesRotatedX > (this.toolPosition.x + (this.boxWidth / f)) - margin) {
            this.resizeAction = ResizeAction.RIGHT;
        }
        if (clickCoordinatesRotatedY < (this.toolPosition.y - (this.boxHeight / f)) + margin) {
            int i = WhenMappings.$EnumSwitchMapping$0[this.resizeAction.ordinal()];
            if (i == 1) {
                resizeAction2 = ResizeAction.TOPLEFT;
            } else if (i == 2) {
                resizeAction2 = ResizeAction.TOPRIGHT;
            } else {
                resizeAction2 = ResizeAction.TOP;
            }
            this.resizeAction = resizeAction2;
            return;
        }
        if (clickCoordinatesRotatedY > (this.toolPosition.y + (this.boxHeight / f)) - margin) {
            int i2 = WhenMappings.$EnumSwitchMapping$0[this.resizeAction.ordinal()];
            if (i2 == 1) {
                resizeAction = ResizeAction.BOTTOMLEFT;
            } else if (i2 == 2) {
                resizeAction = ResizeAction.BOTTOMRIGHT;
            } else {
                resizeAction = ResizeAction.BOTTOM;
            }
            this.resizeAction = resizeAction;
        }
    }

    private final PointF[] getRotationPoints() {
        float f = 2;
        float f2 = (this.toolPosition.x - (this.boxWidth / f)) - (this.rotationSymbolDistance / f);
        float f3 = this.toolPosition.x + (this.boxWidth / f) + (this.rotationSymbolDistance / f);
        float f4 = (this.toolPosition.y - (this.boxHeight / f)) - (this.rotationSymbolDistance / f);
        float f5 = this.toolPosition.y + (this.boxHeight / f) + (this.rotationSymbolDistance / f);
        return new PointF[]{new PointF(f2, f4), new PointF(f3, f4), new PointF(f2, f5), new PointF(f3, f5)};
    }

    private final FloatingBoxAction getAction(float clickCoordinatesX, float clickCoordinatesY) {
        this.resizeAction = ResizeAction.NONE;
        double d = -((this.boxRotation * 3.1415927f) / 180.0f);
        float fCos = (this.toolPosition.x + (((float) Math.cos(d)) * (clickCoordinatesX - this.toolPosition.x))) - (((float) Math.sin(d)) * (clickCoordinatesY - this.toolPosition.y));
        float fSin = this.toolPosition.y + (((float) Math.sin(d)) * (clickCoordinatesX - this.toolPosition.x)) + (((float) Math.cos(d)) * (clickCoordinatesY - this.toolPosition.y));
        Float f = this.boxResizeMargin;
        float fFloatValue = f == null ? 0.0f : f.floatValue();
        float f2 = 2;
        if (fCos < (this.toolPosition.x + (this.boxWidth / f2)) - fFloatValue && fCos > (this.toolPosition.x - (this.boxWidth / f2)) + fFloatValue && fSin < (this.toolPosition.y + (this.boxHeight / f2)) - fFloatValue && fSin > (this.toolPosition.y - (this.boxHeight / f2)) + fFloatValue) {
            return FloatingBoxAction.MOVE;
        }
        if (fCos < this.toolPosition.x + (this.boxWidth / f2) + fFloatValue && fCos > (this.toolPosition.x - (this.boxWidth / f2)) - fFloatValue && fSin < this.toolPosition.y + (this.boxHeight / f2) + fFloatValue && fSin > (this.toolPosition.y - (this.boxHeight / f2)) - fFloatValue) {
            resizeOnFrame(fCos, fSin, fFloatValue);
            return FloatingBoxAction.RESIZE;
        }
        if (this.rotationEnabled && checkRotationPoints(fCos, fSin, getRotationPoints())) {
            return FloatingBoxAction.ROTATE;
        }
        return FloatingBoxAction.MOVE;
    }

    private final void resizeHeight(float deltaYCorrected, double rotationRadian) {
        float height = this.workspace.getHeight() * 2.0f;
        double d = deltaYCorrected / 2;
        float fSin = (float) (Math.sin(rotationRadian) * d);
        float fCos = (float) (d * Math.cos(rotationRadian));
        switch (WhenMappings.$EnumSwitchMapping$0[this.resizeAction.ordinal()]) {
            case 3:
            case 4:
            case 5:
                float f = this.boxHeight - deltaYCorrected;
                float f2 = this.toolPosition.x - fSin;
                float f3 = this.toolPosition.y + fCos;
                if (this.respectMaximumBorderRatio && f > height) {
                    this.boxHeight = height;
                } else {
                    this.boxHeight = f;
                    this.toolPosition.x = f2;
                    this.toolPosition.y = f3;
                }
                break;
            case 6:
            case 7:
            case 8:
                float f4 = this.boxHeight + deltaYCorrected;
                float f5 = this.toolPosition.x - fSin;
                float f6 = this.toolPosition.y + fCos;
                if (this.respectMaximumBorderRatio && f4 > height) {
                    this.boxHeight = height;
                } else {
                    this.boxHeight = f4;
                    this.toolPosition.x = f5;
                    this.toolPosition.y = f6;
                }
                break;
        }
    }

    private final void resizeWidth(float deltaXCorrected, double rotationRadian) {
        float width = this.workspace.getWidth() * 2.0f;
        double d = deltaXCorrected / 2;
        float fCos = (float) (Math.cos(rotationRadian) * d);
        float fSin = (float) (d * Math.sin(rotationRadian));
        int i = WhenMappings.$EnumSwitchMapping$0[this.resizeAction.ordinal()];
        if (i != 1) {
            if (i != 2 && i != 4) {
                if (i != 5 && i != 7) {
                    if (i != 8) {
                        return;
                    }
                }
            }
            float f = this.boxWidth + deltaXCorrected;
            float f2 = this.toolPosition.x + fCos;
            float f3 = this.toolPosition.y + fSin;
            if (this.respectMaximumBorderRatio && f > width) {
                this.boxWidth = width;
                return;
            }
            this.boxWidth = f;
            this.toolPosition.x = f2;
            this.toolPosition.y = f3;
            return;
        }
        float f4 = this.boxWidth - deltaXCorrected;
        float f5 = this.toolPosition.x + fCos;
        float f6 = this.toolPosition.y + fSin;
        if (this.respectMaximumBorderRatio && f4 > width) {
            this.boxWidth = width;
            return;
        }
        this.boxWidth = f4;
        this.toolPosition.x = f5;
        this.toolPosition.y = f6;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:15:0x0053  */
    /* JADX WARN: Code duplicated, block: B:17:0x005f  */
    /* JADX WARN: Code duplicated, block: B:19:0x006d  */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0035, code lost:
    
        if (r11 != 8) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void resize(float r11, float r12) {
        /*
            r10 = this;
            float r0 = r10.boxRotation
            double r0 = (double) r0
            double r0 = java.lang.Math.toRadians(r0)
            double r2 = -r0
            double r4 = java.lang.Math.cos(r2)
            double r6 = (double) r11
            double r4 = r4 * r6
            double r8 = java.lang.Math.sin(r2)
            double r11 = (double) r12
            double r8 = r8 * r11
            double r4 = r4 - r8
            double r8 = java.lang.Math.sin(r2)
            double r8 = r8 * r6
            double r2 = java.lang.Math.cos(r2)
            double r2 = r2 * r11
            double r8 = r8 + r2
            org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape$ResizeAction r11 = r10.resizeAction
            int[] r12 = org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape.WhenMappings.$EnumSwitchMapping$0
            int r11 = r11.ordinal()
            r11 = r12[r11]
            r12 = 4
            if (r11 == r12) goto L53
            r12 = 5
            if (r11 == r12) goto L38
            r12 = 7
            if (r11 == r12) goto L53
            r12 = 8
            if (r11 == r12) goto L38
            goto L7a
        L38:
            double r11 = java.lang.Math.abs(r4)
            double r2 = java.lang.Math.abs(r8)
            int r11 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r11 <= 0) goto L4b
            float r11 = r10.boxWidth
            double r2 = (double) r11
            double r2 = r2 + r4
            float r12 = r10.boxHeight
            goto L65
        L4b:
            float r11 = r10.boxWidth
            double r2 = (double) r11
            float r12 = r10.boxHeight
            double r4 = (double) r12
            double r4 = r4 + r8
            goto L74
        L53:
            double r11 = java.lang.Math.abs(r4)
            double r2 = java.lang.Math.abs(r8)
            int r11 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r11 <= 0) goto L6d
            float r11 = r10.boxWidth
            double r2 = (double) r11
            double r2 = r2 - r4
            float r12 = r10.boxHeight
        L65:
            double r6 = (double) r12
            double r2 = r2 * r6
            double r6 = (double) r11
            double r2 = r2 / r6
            double r11 = (double) r12
            double r8 = r2 - r11
            goto L7a
        L6d:
            float r11 = r10.boxWidth
            double r2 = (double) r11
            float r12 = r10.boxHeight
            double r4 = (double) r12
            double r4 = r4 - r8
        L74:
            double r2 = r2 * r4
            double r4 = (double) r12
            double r2 = r2 / r4
            double r11 = (double) r11
            double r4 = r2 - r11
        L7a:
            android.graphics.PointF r11 = r10.toolPosition
            float r11 = r11.x
            android.graphics.PointF r12 = r10.toolPosition
            float r12 = r12.y
            float r2 = r10.boxHeight
            float r3 = r10.boxWidth
            float r6 = (float) r8
            r10.resizeHeight(r6, r0)
            float r4 = (float) r4
            r10.resizeWidth(r4, r0)
            float r0 = r10.boxWidth
            r1 = 1077936128(0x40400000, float:3.0)
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 >= 0) goto L9c
            r10.boxWidth = r1
            android.graphics.PointF r0 = r10.toolPosition
            r0.x = r11
        L9c:
            float r0 = r10.boxHeight
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 >= 0) goto La8
            r10.boxHeight = r1
            android.graphics.PointF r0 = r10.toolPosition
            r0.y = r12
        La8:
            boolean r0 = r10.respectMaximumBoxResolution
            if (r0 == 0) goto Lbf
            float r0 = r10.maximumBoxResolution
            r1 = 0
            int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r1 <= 0) goto Lbf
            float r1 = r10.boxWidth
            float r4 = r10.boxHeight
            float r1 = r1 * r4
            int r0 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r0 <= 0) goto Lbf
            r10.preventThatBoxGetsTooLarge(r3, r2, r11, r12)
        Lbf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape.resize(float, float):void");
    }

    protected void preventThatBoxGetsTooLarge(float oldWidth, float oldHeight, float oldPosX, float oldPosY) {
        this.boxWidth = oldWidth;
        this.boxHeight = oldHeight;
        this.toolPosition.x = oldPosX;
        this.toolPosition.y = oldPosY;
    }

    private final void createOverlayDrawable() {
        int overlayDrawableResource = getToolType().getOverlayDrawableResource();
        if (overlayDrawableResource != 0) {
            Drawable drawable = this.contextCallback.getDrawable(overlayDrawableResource);
            this.overlayDrawable = drawable;
            if (drawable == null) {
                return;
            }
            drawable.setFilterBitmap(false);
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape$highlightBox$1] */
    public final void highlightBox() {
        this.downTimer = new CountDownTimer(83L) { // from class: org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape.highlightBox.1
            @Override // android.os.CountDownTimer
            public void onTick(long millisUntilFinished) {
                BaseToolWithRectangleShape.this.highlightBoxWhenClickInBox(true);
                BaseToolWithRectangleShape.this.workspace.invalidate();
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                BaseToolWithRectangleShape.this.highlightBoxWhenClickInBox(false);
                BaseToolWithRectangleShape.this.workspace.invalidate();
                CountDownTimer countDownTimer = BaseToolWithRectangleShape.this.downTimer;
                if (countDownTimer == null) {
                    return;
                }
                countDownTimer.cancel();
            }
        }.start();
    }

    public final void highlightBoxWhenClickInBox(boolean highlight) {
        this.secondaryShapeColor = this.contextCallback.getColor(highlight ? R.color.pocketpaint_main_rectangle_tool_highlight_color : R.color.pocketpaint_main_rectangle_tool_accent_color);
        this.rectangleShrinkingOnHighlight = highlight ? 5 : 0;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithShape, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public Point getAutoScrollDirection(float pointX, float pointY, int screenWidth, int screenHeight) {
        if (this.currentAction == FloatingBoxAction.MOVE || this.currentAction == FloatingBoxAction.RESIZE) {
            return super.getAutoScrollDirection(pointX, pointY, screenWidth, screenHeight);
        }
        return new Point(0, 0);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithShape, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle == null) {
            return;
        }
        bundle.putFloat("BOX_WIDTH", this.boxWidth);
        bundle.putFloat("BOX_HEIGHT", this.boxHeight);
        bundle.putFloat("BOX_ROTATION", this.boxRotation);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithShape, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        if (bundle == null) {
            return;
        }
        this.boxWidth = bundle.getFloat("BOX_WIDTH", this.boxWidth);
        this.boxHeight = bundle.getFloat("BOX_HEIGHT", this.boxHeight);
        this.boxRotation = bundle.getFloat("BOX_ROTATION", this.boxRotation);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithShape
    protected void drawToolSpecifics(Canvas canvas, float boxWidth, float boxHeight) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        this.linePaint.setColor(this.primaryShapeColor);
        float f = 2;
        this.linePaint.setStrokeWidth(this.toolStrokeWidth * f);
        int i = this.rectangleShrinkingOnHighlight;
        PointF pointF = new PointF(((-boxWidth) / f) + i, ((-boxHeight) / f) + i);
        float f2 = boxWidth;
        int i2 = 0;
        float f3 = boxHeight;
        while (i2 < 4) {
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
            i2++;
            float f8 = f2;
            f2 = f3;
            f3 = f8;
        }
    }

    private final <T, R> void ifNotNull(T[] options, Function1<? super List<? extends T>, ? extends R> block) {
        for (T t : options) {
            if (t == null) {
                return;
            }
        }
        block.invoke(ArraysKt.filterNotNull(options));
    }

    private final boolean checkRotationPoints(float clickCoordinatesRotatedX, float clickCoordinatesRotatedY, PointF[] rotationPoints) {
        for (PointF pointF : rotationPoints) {
            float f = 2;
            if (clickCoordinatesRotatedX > pointF.x - (this.rotationSymbolDistance / f) && clickCoordinatesRotatedX < pointF.x + (this.rotationSymbolDistance / f) && clickCoordinatesRotatedY > pointF.y - (this.rotationSymbolDistance / f) && clickCoordinatesRotatedY < pointF.y + (this.rotationSymbolDistance / f)) {
                return true;
            }
        }
        return false;
    }
}
