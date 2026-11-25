package org.catrobat.paintroid.listener;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.mgsx.gltf.data.extensions.KHRLightsPunctual;
import org.catrobat.catroid.visualplacement.VisualPlacementActivity;
import org.catrobat.paintroid.UserPreferences;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;
import org.catrobat.paintroid.ui.DrawingSurface;
import org.catrobat.paintroid.ui.zoomwindow.ZoomWindowController;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: DrawingSurfaceListener.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\b\u0016\u0018\u00002\u00020\u0001:\u0003567B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020\u00052\u0006\u0010 \u001a\u00020!H\u0002J\"\u0010#\u001a\u00020\u001f2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010 \u001a\u00020!2\u0006\u0010&\u001a\u00020\bH\u0002J\u0018\u0010'\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020%2\u0006\u0010 \u001a\u00020!H\u0002J\u001a\u0010(\u001a\u00020\u001f2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010 \u001a\u00020!H\u0002J\u0018\u0010)\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u0005H\u0002J\u0018\u0010,\u001a\u00020\b2\u0006\u0010-\u001a\u00020.2\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010/\u001a\u00020\u001f2\u0006\u00100\u001a\u00020\u0017H\u0002J\u0010\u00101\u001a\u00020\u001f2\u0006\u00102\u001a\u00020\nH\u0002J\u0016\u00103\u001a\u00020\u001f2\u0006\u00104\u001a\u00020\u001d2\u0006\u0010\u0014\u001a\u00020\u0015R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lorg/catrobat/paintroid/listener/DrawingSurfaceListener;", "Landroid/view/View$OnTouchListener;", "callback", "Lorg/catrobat/paintroid/listener/DrawingSurfaceListener$DrawingSurfaceListenerCallback;", "displayDensity", "", "(Lorg/catrobat/paintroid/listener/DrawingSurfaceListener$DrawingSurfaceListenerCallback;F)V", "callZoomWindow", "", "canvasTouchPoint", "Landroid/graphics/PointF;", "drawerEdgeSize", "", "eventTouchPoint", "eventX", "eventY", "pointerDistance", "recentTouchEventsData", "", "Lorg/catrobat/paintroid/listener/DrawingSurfaceListener$TouchEventData;", "sharedPreferences", "Lorg/catrobat/paintroid/UserPreferences;", "timerStartDraw", "", "touchMode", "Lorg/catrobat/paintroid/listener/DrawingSurfaceListener$TouchMode;", "xMidPoint", "yMidPoint", "zoomController", "Lorg/catrobat/paintroid/ui/zoomwindow/ZoomWindowController;", "calculateMidPoint", "", "event", "Landroid/view/MotionEvent;", "calculatePointerDistance", "handleActionMove", "currentTool", "Lorg/catrobat/paintroid/tools/Tool;", "shouldAnimate", "handleZoomWindowOnMove", "handleZoomWindowOnTouch", "newHandEvent", "x", "y", "onTouch", Promotion.ACTION_VIEW, "Landroid/view/View;", "removeObsoleteTouchEventsData", "timeStamp", "saveToolActionBeforeZoom", KHRLightsPunctual.GLTFLight.TYPE_POINT, "setZoomController", "zoomWindowController", "DrawingSurfaceListenerCallback", "TouchEventData", "TouchMode", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class DrawingSurfaceListener implements View.OnTouchListener {
    private boolean callZoomWindow;
    private final DrawingSurfaceListenerCallback callback;
    private final PointF canvasTouchPoint;
    private final float displayDensity;
    private final int drawerEdgeSize;
    private final PointF eventTouchPoint;
    private float eventX;
    private float eventY;
    private float pointerDistance;
    private List<TouchEventData> recentTouchEventsData;
    private UserPreferences sharedPreferences;
    private long timerStartDraw;
    private TouchMode touchMode;
    private float xMidPoint;
    private float yMidPoint;
    private ZoomWindowController zoomController;

    /* JADX INFO: compiled from: DrawingSurfaceListener.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\n\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&J\b\u0010\b\u001a\u00020\tH&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH&J\u0018\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\fH&¨\u0006\u0010"}, d2 = {"Lorg/catrobat/paintroid/listener/DrawingSurfaceListener$DrawingSurfaceListenerCallback;", "", "convertToCanvasFromSurface", "", "surfacePoint", "Landroid/graphics/PointF;", "getCurrentTool", "Lorg/catrobat/paintroid/tools/Tool;", "getToolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "multiplyPerspectiveScale", "factor", "", "translatePerspective", "x", "y", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface DrawingSurfaceListenerCallback {
        void convertToCanvasFromSurface(PointF surfacePoint);

        Tool getCurrentTool();

        ToolOptionsViewController getToolOptionsViewController();

        void multiplyPerspectiveScale(float factor);

        void translatePerspective(float x, float y);
    }

    /* JADX INFO: compiled from: DrawingSurfaceListener.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/catrobat/paintroid/listener/DrawingSurfaceListener$TouchMode;", "", "(Ljava/lang/String;I)V", "DRAW", "PINCH", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public enum TouchMode {
        DRAW,
        PINCH
    }

    public DrawingSurfaceListener(DrawingSurfaceListenerCallback callback, float f) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
        this.displayDensity = f;
        this.drawerEdgeSize = (int) ((f * 20.0f) + 0.5f);
        this.callZoomWindow = true;
        this.recentTouchEventsData = new ArrayList();
        this.touchMode = TouchMode.DRAW;
        this.canvasTouchPoint = new PointF();
        this.eventTouchPoint = new PointF();
    }

    /* JADX INFO: compiled from: DrawingSurfaceListener.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0018"}, d2 = {"Lorg/catrobat/paintroid/listener/DrawingSurfaceListener$TouchEventData;", "", "timeStamp", "", VisualPlacementActivity.X_COORDINATE_BUNDLE_ARGUMENT, "", VisualPlacementActivity.Y_COORDINATE_BUNDLE_ARGUMENT, "(JFF)V", "getTimeStamp", "()J", "getXCoordinate", "()F", "getYCoordinate", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    private static final /* data */ class TouchEventData {
        private final long timeStamp;
        private final float xCoordinate;
        private final float yCoordinate;

        public static /* synthetic */ TouchEventData copy$default(TouchEventData touchEventData, long j, float f, float f2, int i, Object obj) {
            if ((i & 1) != 0) {
                j = touchEventData.timeStamp;
            }
            if ((i & 2) != 0) {
                f = touchEventData.xCoordinate;
            }
            if ((i & 4) != 0) {
                f2 = touchEventData.yCoordinate;
            }
            return touchEventData.copy(j, f, f2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final long getTimeStamp() {
            return this.timeStamp;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final float getXCoordinate() {
            return this.xCoordinate;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final float getYCoordinate() {
            return this.yCoordinate;
        }

        public final TouchEventData copy(long timeStamp, float xCoordinate, float yCoordinate) {
            return new TouchEventData(timeStamp, xCoordinate, yCoordinate);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof TouchEventData)) {
                return false;
            }
            TouchEventData touchEventData = (TouchEventData) other;
            return this.timeStamp == touchEventData.timeStamp && Intrinsics.areEqual((Object) Float.valueOf(this.xCoordinate), (Object) Float.valueOf(touchEventData.xCoordinate)) && Intrinsics.areEqual((Object) Float.valueOf(this.yCoordinate), (Object) Float.valueOf(touchEventData.yCoordinate));
        }

        public int hashCode() {
            return (((Long.hashCode(this.timeStamp) * 31) + Float.hashCode(this.xCoordinate)) * 31) + Float.hashCode(this.yCoordinate);
        }

        public String toString() {
            return "TouchEventData(timeStamp=" + this.timeStamp + ", xCoordinate=" + this.xCoordinate + ", yCoordinate=" + this.yCoordinate + ')';
        }

        public TouchEventData(long j, float f, float f2) {
            this.timeStamp = j;
            this.xCoordinate = f;
            this.yCoordinate = f2;
        }

        public final long getTimeStamp() {
            return this.timeStamp;
        }

        public final float getXCoordinate() {
            return this.xCoordinate;
        }

        public final float getYCoordinate() {
            return this.yCoordinate;
        }
    }

    private final void newHandEvent(float x, float y) {
        this.eventX = x;
        this.eventY = y;
    }

    private final float calculatePointerDistance(MotionEvent event) {
        return (float) Math.hypot(event.getX(0) - event.getX(1), event.getY(0) - event.getY(1));
    }

    private final void calculateMidPoint(MotionEvent event) {
        this.xMidPoint = (event.getX(0) + event.getX(1)) / 2.0f;
        this.yMidPoint = (event.getY(0) + event.getY(1)) / 2.0f;
    }

    public final void setZoomController(ZoomWindowController zoomWindowController, UserPreferences sharedPreferences) {
        Intrinsics.checkNotNullParameter(zoomWindowController, "zoomWindowController");
        Intrinsics.checkNotNullParameter(sharedPreferences, "sharedPreferences");
        this.zoomController = zoomWindowController;
        this.sharedPreferences = sharedPreferences;
    }

    private final void handleActionMove(Tool currentTool, MotionEvent event, boolean shouldAnimate) {
        float f;
        float f2;
        if (event.getPointerCount() == 1) {
            if (currentTool == null) {
                return;
            }
            this.recentTouchEventsData.add(new TouchEventData(event.getEventTime(), event.getX(), event.getY()));
            removeObsoleteTouchEventsData(event.getEventTime());
            if (currentTool.handToolMode()) {
                if (this.touchMode == TouchMode.PINCH) {
                    this.touchMode = TouchMode.DRAW;
                    f = 0.0f;
                    f2 = 0.0f;
                } else {
                    f = this.eventX;
                    f2 = this.eventY;
                }
                newHandEvent(event.getX(), event.getY());
                if ((f > 0.0f && this.eventX != f) || (f2 > 0.0f && this.eventY != f2)) {
                    this.callback.translatePerspective(this.eventX - f, this.eventY - f2);
                }
            } else if (this.touchMode != TouchMode.PINCH) {
                this.touchMode = TouchMode.DRAW;
                currentTool.handleMove(this.canvasTouchPoint, shouldAnimate);
            }
            handleZoomWindowOnMove(currentTool, event);
            return;
        }
        if (this.touchMode == TouchMode.DRAW) {
            saveToolActionBeforeZoom(new PointF(event.getX(), event.getY()));
            if (currentTool != null) {
                currentTool.resetInternalState(Tool.StateChange.MOVE_CANCELED);
            }
        }
        this.touchMode = TouchMode.PINCH;
        float f3 = this.pointerDistance;
        float fCalculatePointerDistance = calculatePointerDistance(event);
        this.pointerDistance = fCalculatePointerDistance;
        if (f3 > 0.0f && f3 != fCalculatePointerDistance) {
            this.callback.multiplyPerspectiveScale(fCalculatePointerDistance / f3);
        }
        float f4 = this.xMidPoint;
        float f5 = this.yMidPoint;
        calculateMidPoint(event);
        if ((f4 > 0.0f && this.xMidPoint != f4) || (f5 > 0.0f && this.yMidPoint != f5)) {
            this.callback.translatePerspective(this.xMidPoint - f4, this.yMidPoint - f5);
        }
        ZoomWindowController zoomWindowController = this.zoomController;
        if (zoomWindowController == null) {
            Intrinsics.throwUninitializedPropertyAccessException("zoomController");
            zoomWindowController = null;
        }
        zoomWindowController.dismissOnPinch();
    }

    private final void handleZoomWindowOnMove(Tool currentTool, MotionEvent event) {
        ToolType toolType;
        UserPreferences userPreferences = this.sharedPreferences;
        ZoomWindowController zoomWindowController = null;
        if (userPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sharedPreferences");
            userPreferences = null;
        }
        if (userPreferences.getPreferenceZoomWindowEnabled()) {
            Tool currentTool2 = this.callback.getCurrentTool();
            if (!StringsKt.equals$default((currentTool2 == null || (toolType = currentTool2.getToolType()) == null) ? null : toolType.name(), ToolType.CURSOR.name(), false, 2, null)) {
                ZoomWindowController zoomWindowController2 = this.zoomController;
                if (zoomWindowController2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("zoomController");
                } else {
                    zoomWindowController = zoomWindowController2;
                }
                zoomWindowController.onMove(this.canvasTouchPoint, new PointF(event.getX(), event.getY()));
                return;
            }
            ZoomWindowController zoomWindowController3 = this.zoomController;
            if (zoomWindowController3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("zoomController");
            } else {
                zoomWindowController = zoomWindowController3;
            }
            zoomWindowController.onMove(currentTool.toolPositionCoordinates(this.canvasTouchPoint), new PointF(event.getX(), event.getY()));
        }
    }

    private final void saveToolActionBeforeZoom(PointF point) {
        ToolType toolType;
        ToolType toolType2;
        Tool currentTool = this.callback.getCurrentTool();
        if (!StringsKt.equals$default((currentTool == null || (toolType = currentTool.getToolType()) == null) ? null : toolType.name(), ToolType.CURSOR.name(), false, 2, null)) {
            if (!StringsKt.equals$default((currentTool == null || (toolType2 = currentTool.getToolType()) == null) ? null : toolType2.name(), ToolType.LINE.name(), false, 2, null)) {
                return;
            }
        }
        if (currentTool != null) {
            currentTool.handleUp(point);
        }
        if (currentTool == null) {
            return;
        }
        currentTool.handleDown(point);
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0089  */
    /* JADX WARN: Code duplicated, block: B:28:0x0090  */
    /* JADX WARN: Code duplicated, block: B:30:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:34:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:38:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:41:0x00f6  */
    /* JADX WARN: Code duplicated, block: B:42:0x00fc A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:44:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:47:0x0116  */
    /* JADX WARN: Code duplicated, block: B:49:0x011a  */
    /* JADX WARN: Code duplicated, block: B:54:0x012d  */
    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent event) {
        Tool currentTool;
        ZoomWindowController zoomWindowController;
        long jCurrentTimeMillis;
        float x;
        float y;
        float f;
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(event, "event");
        DrawingSurface drawingSurface = (DrawingSurface) view;
        Tool currentTool2 = this.callback.getCurrentTool();
        this.canvasTouchPoint.x = event.getX();
        this.canvasTouchPoint.y = event.getY();
        this.eventTouchPoint.x = this.canvasTouchPoint.x;
        this.eventTouchPoint.y = this.canvasTouchPoint.y;
        this.callback.convertToCanvasFromSurface(this.canvasTouchPoint);
        int action = event.getAction() & 255;
        if (action != 0) {
            if (action == 1) {
                if (this.touchMode == TouchMode.DRAW) {
                    jCurrentTimeMillis = System.currentTimeMillis() - this.timerStartDraw;
                    removeObsoleteTouchEventsData(event.getEventTime());
                    if (this.recentTouchEventsData.size() > 1) {
                        TouchEventData touchEventData = this.recentTouchEventsData.get(0);
                        x = event.getX() - touchEventData.getXCoordinate();
                        y = event.getY() - touchEventData.getYCoordinate();
                        f = (x * x) + (y * y);
                        if (f < this.displayDensity * 50.0f || f == 0.0f) {
                            x = 0.0f;
                            y = 0.0f;
                        }
                    } else {
                        x = 0.0f;
                        y = 0.0f;
                    }
                    this.canvasTouchPoint.x = event.getX() - x;
                    this.canvasTouchPoint.y = event.getY() - y;
                    this.callback.convertToCanvasFromSurface(this.canvasTouchPoint);
                    if (currentTool2 != null) {
                        currentTool2.setDrawTime(jCurrentTimeMillis);
                    }
                    if (currentTool2 != null) {
                        currentTool2.handleUp(this.canvasTouchPoint);
                    }
                } else if (currentTool2 != null) {
                    currentTool2.resetInternalState(Tool.StateChange.MOVE_CANCELED);
                }
                this.pointerDistance = 0.0f;
                this.xMidPoint = 0.0f;
                this.yMidPoint = 0.0f;
                this.eventX = 0.0f;
                this.eventY = 0.0f;
                this.touchMode = TouchMode.DRAW;
                if (this.callZoomWindow) {
                    zoomWindowController = this.zoomController;
                    if (zoomWindowController == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("zoomController");
                        zoomWindowController = null;
                    }
                    zoomWindowController.dismiss();
                }
                currentTool = this.callback.getCurrentTool();
                if (currentTool != null) {
                    currentTool.handToolMode();
                }
            } else if (action == 2) {
                int height = drawingSurface.getHeight() / 4;
                handleActionMove(currentTool2, event, event.getY() < ((float) height) || event.getY() > ((float) (drawingSurface.getHeight() - height)));
            } else if (action == 3) {
                if (this.touchMode == TouchMode.DRAW) {
                    jCurrentTimeMillis = System.currentTimeMillis() - this.timerStartDraw;
                    removeObsoleteTouchEventsData(event.getEventTime());
                    if (this.recentTouchEventsData.size() > 1) {
                        TouchEventData touchEventData2 = this.recentTouchEventsData.get(0);
                        x = event.getX() - touchEventData2.getXCoordinate();
                        y = event.getY() - touchEventData2.getYCoordinate();
                        f = (x * x) + (y * y);
                        if (f < this.displayDensity * 50.0f) {
                            x = 0.0f;
                            y = 0.0f;
                        } else {
                            x = 0.0f;
                            y = 0.0f;
                        }
                    } else {
                        x = 0.0f;
                        y = 0.0f;
                    }
                    this.canvasTouchPoint.x = event.getX() - x;
                    this.canvasTouchPoint.y = event.getY() - y;
                    this.callback.convertToCanvasFromSurface(this.canvasTouchPoint);
                    if (currentTool2 != null) {
                        currentTool2.setDrawTime(jCurrentTimeMillis);
                    }
                    if (currentTool2 != null) {
                        currentTool2.handleUp(this.canvasTouchPoint);
                    }
                } else if (currentTool2 != null) {
                    currentTool2.resetInternalState(Tool.StateChange.MOVE_CANCELED);
                }
                this.pointerDistance = 0.0f;
                this.xMidPoint = 0.0f;
                this.yMidPoint = 0.0f;
                this.eventX = 0.0f;
                this.eventY = 0.0f;
                this.touchMode = TouchMode.DRAW;
                if (this.callZoomWindow) {
                    zoomWindowController = this.zoomController;
                    if (zoomWindowController == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("zoomController");
                        zoomWindowController = null;
                    }
                    zoomWindowController.dismiss();
                }
                currentTool = this.callback.getCurrentTool();
                if (currentTool != null) {
                    currentTool.handToolMode();
                }
            } else if (action == 6) {
                if (currentTool2 != null) {
                    currentTool2.handleDownAnimations(this.canvasTouchPoint);
                }
                if (currentTool2 != null) {
                    currentTool2.handleUpAnimations(this.canvasTouchPoint);
                }
                drawingSurface.refreshDrawingSurface();
                return false;
            }
        } else {
            if (this.eventTouchPoint.x < this.drawerEdgeSize || view.getWidth() - this.eventTouchPoint.x < this.drawerEdgeSize) {
                return false;
            }
            this.timerStartDraw = System.currentTimeMillis();
            this.recentTouchEventsData.add(new TouchEventData(event.getEventTime(), event.getX(), event.getY()));
            if (currentTool2 != null) {
                currentTool2.handleDown(this.canvasTouchPoint);
            }
            handleZoomWindowOnTouch(currentTool2, event);
        }
        drawingSurface.refreshDrawingSurface();
        return true;
    }

    private final void handleZoomWindowOnTouch(Tool currentTool, MotionEvent event) {
        ToolType toolType;
        PointF pointF;
        UserPreferences userPreferences = this.sharedPreferences;
        ZoomWindowController zoomWindowController = null;
        if (userPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sharedPreferences");
            userPreferences = null;
        }
        boolean z = false;
        if (userPreferences.getPreferenceZoomWindowEnabled()) {
            if (!StringsKt.equals$default((currentTool == null || (toolType = currentTool.getToolType()) == null) ? null : toolType.name(), ToolType.CURSOR.name(), false, 2, null)) {
                ZoomWindowController zoomWindowController2 = this.zoomController;
                if (zoomWindowController2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("zoomController");
                } else {
                    zoomWindowController = zoomWindowController2;
                }
                zoomWindowController.show(this.canvasTouchPoint, new PointF(event.getX(), event.getY()));
            } else if (currentTool != null && (pointF = currentTool.toolPositionCoordinates(this.canvasTouchPoint)) != null) {
                ZoomWindowController zoomWindowController3 = this.zoomController;
                if (zoomWindowController3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("zoomController");
                } else {
                    zoomWindowController = zoomWindowController3;
                }
                zoomWindowController.show(pointF, new PointF(event.getX(), event.getY()));
                Unit unit = Unit.INSTANCE;
            }
            z = true;
        }
        this.callZoomWindow = z;
    }

    private final void removeObsoleteTouchEventsData(long timeStamp) {
        ArrayList arrayList = new ArrayList();
        for (TouchEventData touchEventData : this.recentTouchEventsData) {
            if (timeStamp - touchEventData.getTimeStamp() <= 30) {
                break;
            } else {
                arrayList.add(touchEventData);
            }
        }
        this.recentTouchEventsData.removeAll(arrayList);
    }
}
