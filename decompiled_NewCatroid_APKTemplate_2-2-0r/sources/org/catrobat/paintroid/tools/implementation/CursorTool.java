package org.catrobat.paintroid.tools.implementation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.test.espresso.idling.CountingIdlingResource;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.mgsx.gltf.data.extensions.KHRLightsPunctual;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.command.serialization.SerializablePath;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.common.CommonBrushChangedListener;
import org.catrobat.paintroid.tools.common.CommonBrushPreviewListener;
import org.catrobat.paintroid.tools.helper.AdvancedSettingsAlgorithms;
import org.catrobat.paintroid.tools.options.BrushToolOptionsView;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;

/* JADX INFO: compiled from: CursorTool.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0013\b\u0016\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u0010\u0010+\u001a\u00020$2\u0006\u0010,\u001a\u00020\u001dH\u0002J\u0010\u0010-\u001a\u00020$2\u0006\u0010,\u001a\u00020\u001dH\u0002J\u0018\u0010.\u001a\u00020\u001d2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u000200H\u0002J\u0018\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u00142\u0006\u00105\u001a\u00020$H\u0016J \u00106\u001a\u0002002\u0006\u00107\u001a\u0002002\u0006\u00108\u001a\u0002002\u0006\u00109\u001a\u000200H\u0002J \u0010:\u001a\u00020$2\u0006\u0010;\u001a\u00020\u001d2\u0006\u0010<\u001a\u00020\u00142\u0006\u0010=\u001a\u00020\u0014H\u0002J\u0010\u0010>\u001a\u0002032\u0006\u0010?\u001a\u00020@H\u0016J(\u0010A\u001a\u0002032\u0006\u0010?\u001a\u00020@2\u0006\u0010B\u001a\u0002002\u0006\u0010C\u001a\u0002002\u0006\u0010D\u001a\u000200H\u0002J(\u0010E\u001a\u0002032\u0006\u0010?\u001a\u00020@2\u0006\u0010B\u001a\u0002002\u0006\u0010C\u001a\u0002002\u0006\u0010D\u001a\u000200H\u0002J\u0010\u0010F\u001a\u0002032\u0006\u0010?\u001a\u00020@H\u0016J\u0012\u0010G\u001a\u00020$2\b\u0010,\u001a\u0004\u0018\u00010\u001dH\u0016J\u0012\u0010H\u001a\u0002032\b\u0010,\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010I\u001a\u000203H\u0002J\u001a\u0010J\u001a\u00020$2\b\u0010,\u001a\u0004\u0018\u00010\u001d2\u0006\u0010K\u001a\u00020$H\u0016J\u0012\u0010L\u001a\u00020$2\b\u0010,\u001a\u0004\u0018\u00010\u001dH\u0016J\u0012\u0010M\u001a\u0002032\b\u0010,\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010N\u001a\u000203H\u0002J\b\u0010O\u001a\u000203H\u0016J\b\u0010P\u001a\u000203H\u0016J\b\u0010Q\u001a\u000203H\u0002J\u0010\u0010R\u001a\u00020\u001d2\u0006\u0010,\u001a\u00020\u001dH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0015\u001a\u00020\u00148\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u001e\u001a\u00020\u001f8\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b \u0010\u0017R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001d0\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010%\u001a\u00020$8\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b&\u0010\u0017R\u0014\u0010'\u001a\u00020(8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*¨\u0006S"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/CursorTool;", "Lorg/catrobat/paintroid/tools/implementation/BaseToolWithShape;", "brushToolOptionsView", "Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "drawTime", "", "(Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView;Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;J)V", "cursorToolPrimaryShapeColor", "", "cursorToolSecondaryShapeColor", "getCursorToolSecondaryShapeColor$annotations", "()V", "getDrawTime", "()J", "setDrawTime", "(J)V", "initialEventCoordinate", "Landroid/graphics/PointF;", "pathToDraw", "Lorg/catrobat/paintroid/command/serialization/SerializablePath;", "getPathToDraw$annotations", "pointArray", "", "pointInsideBitmap", "", "toolInDrawMode", "getToolInDrawMode$annotations", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "addPathCommand", "coordinate", "addPointCommand", "calculateNewClampedToolPosition", "deltaX", "", "deltaY", "changePaintColor", "", TypedValues.Custom.S_COLOR, "invalidate", "clamp", "value", "min", "max", "contains", KHRLightsPunctual.GLTFLight.TYPE_POINT, "width", "height", "draw", "canvas", "Landroid/graphics/Canvas;", "drawCircle", "strokeWidth", "outerCircleRadius", "innerCircleRadius", "drawRect", "drawShape", "handleDown", "handleDownAnimations", "handleDrawMode", "handleMove", "shouldAnimate", "handleUp", "handleUpAnimations", "hideToolOptions", "onClickOnButton", "resetInternalState", "showToolOptions", "toolPositionCoordinates", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class CursorTool extends BaseToolWithShape {
    private final BrushToolOptionsView brushToolOptionsView;
    private final int cursorToolPrimaryShapeColor;
    public int cursorToolSecondaryShapeColor;
    private long drawTime;
    private final PointF initialEventCoordinate;
    public SerializablePath pathToDraw;
    private final List<PointF> pointArray;
    private boolean pointInsideBitmap;
    public boolean toolInDrawMode;

    public static /* synthetic */ void getCursorToolSecondaryShapeColor$annotations() {
    }

    public static /* synthetic */ void getPathToDraw$annotations() {
    }

    public static /* synthetic */ void getToolInDrawMode$annotations() {
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithShape
    public void onClickOnButton() {
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
    public CursorTool(BrushToolOptionsView brushToolOptionsView, ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager, long j) {
        super(contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager);
        Intrinsics.checkNotNullParameter(brushToolOptionsView, "brushToolOptionsView");
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        this.brushToolOptionsView = brushToolOptionsView;
        this.drawTime = j;
        this.pathToDraw = new SerializablePath();
        this.initialEventCoordinate = new PointF(0.0f, 0.0f);
        this.pointArray = new ArrayList();
        this.pathToDraw.incReserve(1);
        this.cursorToolPrimaryShapeColor = contextCallback.getColor(R.color.pocketpaint_main_cursor_tool_inactive_primary_color);
        this.cursorToolSecondaryShapeColor = -3355444;
        this.pointInsideBitmap = false;
        brushToolOptionsView.setBrushChangedListener(new CommonBrushChangedListener(this));
        brushToolOptionsView.setBrushPreviewListener(new CommonBrushPreviewListener(toolPaint, getToolType()));
        brushToolOptionsView.setCurrentPaint(toolPaint.getBitmapPaint());
        brushToolOptionsView.setStrokeCapButtonChecked(toolPaint.getStrokeCap());
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public ToolType getToolType() {
        return ToolType.CURSOR;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleUpAnimations(PointF coordinate) {
        super.handleUp(coordinate);
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleDownAnimations(PointF coordinate) {
        super.handleDown(coordinate);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void changePaintColor(int color, boolean invalidate) {
        super.changePaintColor(color, invalidate);
        if (this.toolInDrawMode) {
            this.cursorToolSecondaryShapeColor = this.toolPaint.getColor();
        }
        if (invalidate) {
            this.brushToolOptionsView.invalidate();
        }
    }

    private final void hideToolOptions() {
        if (this.toolOptionsViewController.isVisible()) {
            if (this.brushToolOptionsView.getTopLayout().getVisibility() == 0) {
                this.toolOptionsViewController.slideUp(this.brushToolOptionsView.getTopLayout(), true, false);
            }
            if (this.brushToolOptionsView.getBottomLayout().getVisibility() == 0) {
                this.toolOptionsViewController.slideDown(this.brushToolOptionsView.getBottomLayout(), true, false);
            }
        }
    }

    private final void showToolOptions() {
        if (this.toolOptionsViewController.isVisible()) {
            return;
        }
        if (this.brushToolOptionsView.getBottomLayout().getVisibility() == 4) {
            this.toolOptionsViewController.slideDown(this.brushToolOptionsView.getTopLayout(), false, true);
        }
        if (this.brushToolOptionsView.getBottomLayout().getVisibility() == 4) {
            this.toolOptionsViewController.slideUp(this.brushToolOptionsView.getBottomLayout(), false, true);
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleDown(PointF coordinate) {
        super.handleDown(coordinate);
        this.pathToDraw.moveTo(this.toolPosition.x, this.toolPosition.y);
        if (coordinate != null) {
            PointF pointF = this.previousEventCoordinate;
            if (pointF != null) {
                pointF.set(coordinate);
            }
            this.initialEventCoordinate.set(coordinate);
        }
        this.pointArray.add(new PointF(this.toolPosition.x, this.toolPosition.y));
        this.movedDistance.set(0.0f, 0.0f);
        this.pointInsideBitmap = this.workspace.contains(this.toolPosition);
        return true;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleMove(PointF coordinate, boolean shouldAnimate) {
        if (coordinate != null) {
            if (shouldAnimate) {
                hideToolOptions();
            }
            super.handleMove(coordinate, shouldAnimate);
            PointF pointF = this.previousEventCoordinate;
            if (pointF != null) {
                float f = coordinate.x - pointF.x;
                float f2 = coordinate.y - pointF.y;
                pointF.set(coordinate.x, coordinate.y);
                this.pointInsideBitmap = this.pointInsideBitmap || this.workspace.contains(this.toolPosition);
                PointF pointFCalculateNewClampedToolPosition = calculateNewClampedToolPosition(f, f2);
                if (this.toolInDrawMode) {
                    this.pathToDraw.quadTo(this.toolPosition.x, this.toolPosition.y, (this.toolPosition.x + pointFCalculateNewClampedToolPosition.x) / 2.0f, (this.toolPosition.y + pointFCalculateNewClampedToolPosition.y) / 2.0f);
                    this.pathToDraw.incReserve(1);
                }
                this.pointArray.add(new PointF(this.toolPosition.x, this.toolPosition.y));
                this.toolPosition.set(pointFCalculateNewClampedToolPosition);
                this.movedDistance.offset(Math.abs(f), Math.abs(f2));
            }
        }
        return true;
    }

    private final PointF calculateNewClampedToolPosition(float deltaX, float deltaY) {
        PointF pointF = new PointF(this.toolPosition.x + deltaX, this.toolPosition.y + deltaY);
        PointF surfacePointFromCanvasPoint = this.workspace.getSurfacePointFromCanvasPoint(pointF);
        int surfaceWidth = this.workspace.getSurfaceWidth();
        int surfaceHeight = this.workspace.getSurfaceHeight();
        if (!contains(surfacePointFromCanvasPoint, surfaceWidth, surfaceHeight)) {
            surfacePointFromCanvasPoint.x = clamp(surfacePointFromCanvasPoint.x, 0.0f, surfaceWidth);
            surfacePointFromCanvasPoint.y = clamp(surfacePointFromCanvasPoint.y, 0.0f, surfaceHeight);
            pointF.set(this.workspace.getCanvasPointFromSurfacePoint(surfacePointFromCanvasPoint));
        }
        return pointF;
    }

    private final float clamp(float value, float min, float max) {
        return Math.min(max, Math.max(value, min));
    }

    private final boolean contains(PointF point, int width, int height) {
        return point.x >= 0.0f && point.y >= 0.0f && point.x < ((float) width) && point.y < ((float) height);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleUp(PointF coordinate) {
        PointF pointF;
        showToolOptions();
        super.handleUp(coordinate);
        if (!this.pointInsideBitmap && this.workspace.contains(this.toolPosition)) {
            this.pointInsideBitmap = true;
        }
        if (coordinate != null && (pointF = this.previousEventCoordinate) != null) {
            this.movedDistance.set(this.movedDistance.x + Math.abs(coordinate.x - pointF.x), this.movedDistance.y + Math.abs(coordinate.y - pointF.y));
        }
        handleDrawMode();
        this.pointArray.clear();
        return true;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool
    public void resetInternalState() {
        this.pointArray.clear();
        this.pathToDraw.rewind();
    }

    private final void drawCircle(Canvas canvas, float strokeWidth, float outerCircleRadius, float innerCircleRadius) {
        canvas.drawCircle(this.toolPosition.x, this.toolPosition.y, outerCircleRadius, this.linePaint);
        this.linePaint.setColor(-3355444);
        canvas.drawCircle(this.toolPosition.x, this.toolPosition.y, innerCircleRadius, this.linePaint);
        this.linePaint.setColor(0);
        this.linePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(this.toolPosition.x, this.toolPosition.y, innerCircleRadius - (strokeWidth / 2.0f), this.linePaint);
    }

    private final void drawRect(Canvas canvas, float strokeWidth, float outerCircleRadius, float innerCircleRadius) {
        RectF rectF = new RectF(this.toolPosition.x - outerCircleRadius, this.toolPosition.y - outerCircleRadius, this.toolPosition.x + outerCircleRadius, this.toolPosition.y + outerCircleRadius);
        canvas.drawRect(rectF, this.linePaint);
        rectF.set(this.toolPosition.x - innerCircleRadius, this.toolPosition.y - innerCircleRadius, this.toolPosition.x + innerCircleRadius, this.toolPosition.y + innerCircleRadius);
        this.linePaint.setColor(-3355444);
        canvas.drawRect(rectF, this.linePaint);
        this.linePaint.setColor(0);
        this.linePaint.setStyle(Paint.Style.FILL);
        float f = strokeWidth / 2.0f;
        rectF.set((this.toolPosition.x - innerCircleRadius) + f, (this.toolPosition.y - innerCircleRadius) + f, (this.toolPosition.x + innerCircleRadius) - f, (this.toolPosition.y + innerCircleRadius) - f);
        canvas.drawRect(rectF, this.linePaint);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithShape
    public void drawShape(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        float fMax = Math.max(this.toolPaint.getStrokeWidth() / 2.0f, 1.0f);
        float strokeWidthForZoom = getStrokeWidthForZoom(5.0f, 1.0f, 10.0f);
        float f = strokeWidthForZoom * 2;
        float f2 = strokeWidthForZoom / 2.0f;
        float f3 = fMax + f2;
        float f4 = f3 + strokeWidthForZoom;
        Paint paint = this.linePaint;
        paint.setColor(this.cursorToolPrimaryShapeColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidthForZoom);
        if (this.toolPaint.getStrokeCap() == Paint.Cap.ROUND) {
            drawCircle(canvas, strokeWidthForZoom, f4, f3);
        } else {
            drawRect(canvas, strokeWidthForZoom, f4, f3);
        }
        this.linePaint.setStyle(Paint.Style.FILL);
        float f5 = f + strokeWidthForZoom;
        int i = 0;
        float f6 = f2;
        while (i < 4) {
            if (i % 2 == 0) {
                this.linePaint.setColor(this.cursorToolSecondaryShapeColor);
            } else {
                this.linePaint.setColor(this.cursorToolPrimaryShapeColor);
            }
            canvas.drawLine((this.toolPosition.x - f4) - f6, this.toolPosition.y, (this.toolPosition.x - f4) - f5, this.toolPosition.y, this.linePaint);
            canvas.drawLine(this.toolPosition.x + f4 + f6, this.toolPosition.y, this.toolPosition.x + f4 + f5, this.toolPosition.y, this.linePaint);
            canvas.drawLine(this.toolPosition.x, this.toolPosition.y + f4 + f6, this.toolPosition.x, this.toolPosition.y + f4 + f5, this.linePaint);
            canvas.drawLine(this.toolPosition.x, (this.toolPosition.y - f4) - f6, this.toolPosition.x, (this.toolPosition.y - f4) - f5, this.linePaint);
            i++;
            float f7 = i;
            f6 = f2 + (f * f7);
            f5 = strokeWidthForZoom + ((f7 + 1.0f) * f);
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (this.toolInDrawMode) {
            canvas.save();
            canvas.clipRect(0, 0, this.workspace.getWidth(), this.workspace.getHeight());
            canvas.drawPath(this.pathToDraw, this.toolPaint.getPreviewPaint());
            canvas.restore();
        }
        drawShape(canvas);
    }

    private final boolean addPathCommand(PointF coordinate) {
        this.pathToDraw.lineTo(coordinate.x, coordinate.y);
        RectF rectF = new RectF();
        this.pathToDraw.computeBounds(rectF, true);
        rectF.inset(-this.toolPaint.getStrokeWidth(), -this.toolPaint.getStrokeWidth());
        if (this.workspace.intersectsWith(rectF)) {
            double dSqrt = Math.sqrt(((coordinate.x - this.initialEventCoordinate.x) * (coordinate.x - this.initialEventCoordinate.x)) + (coordinate.y - this.initialEventCoordinate.y)) / getDrawTime();
            if (!AdvancedSettingsAlgorithms.smoothing || dSqrt < 0.2d) {
                this.commandManager.addCommand(this.commandFactory.createPathCommand(this.toolPaint.getBitmapPaint(), this.pathToDraw));
            } else {
                SerializablePath serializablePathSmoothingAlgorithm = AdvancedSettingsAlgorithms.smoothingAlgorithm(this.pointArray);
                serializablePathSmoothingAlgorithm.computeBounds(rectF, true);
                this.commandManager.addCommand(this.commandFactory.createPathCommand(this.toolPaint.getBitmapPaint(), serializablePathSmoothingAlgorithm));
            }
            return true;
        }
        resetInternalState(Tool.StateChange.RESET_INTERNAL_STATE);
        return false;
    }

    private final boolean addPointCommand(PointF coordinate) {
        if (!this.pointInsideBitmap) {
            resetInternalState(Tool.StateChange.RESET_INTERNAL_STATE);
            return false;
        }
        this.commandManager.addCommand(this.commandFactory.createPointCommand(this.toolPaint.getBitmapPaint(), coordinate));
        return true;
    }

    private final void handleDrawMode() {
        if (this.toolInDrawMode) {
            if (5.0f < this.movedDistance.x || 5.0f < this.movedDistance.y) {
                addPathCommand(this.toolPosition);
                this.cursorToolSecondaryShapeColor = this.toolPaint.getColor();
            } else {
                this.contextCallback.showNotification(R.string.cursor_draw_inactive);
                this.toolInDrawMode = false;
                this.cursorToolSecondaryShapeColor = -3355444;
            }
        } else if (5.0f >= this.movedDistance.x && 5.0f >= this.movedDistance.y) {
            this.contextCallback.showNotification(R.string.cursor_draw_active);
            this.toolInDrawMode = true;
            this.cursorToolSecondaryShapeColor = this.toolPaint.getColor();
            addPointCommand(this.toolPosition);
        }
        this.pointArray.clear();
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public PointF toolPositionCoordinates(PointF coordinate) {
        Intrinsics.checkNotNullParameter(coordinate, "coordinate");
        PointF pointF = new PointF(0.0f, 0.0f);
        PointF pointF2 = this.previousEventCoordinate;
        return pointF2 == null ? pointF : calculateNewClampedToolPosition(coordinate.x - pointF2.x, coordinate.y - pointF2.y);
    }
}
