package org.catrobat.paintroid.tools.implementation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.test.espresso.idling.CountingIdlingResource;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
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

/* JADX INFO: compiled from: BrushTool.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\b\u0016\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u0010\u00102\u001a\u00020%2\u0006\u00103\u001a\u00020\u001eH\u0002J\u0010\u00104\u001a\u00020%2\u0006\u00103\u001a\u00020\u001eH\u0002J\u0018\u00105\u001a\u0002062\u0006\u00107\u001a\u0002082\u0006\u00109\u001a\u00020%H\u0016J\u0010\u0010:\u001a\u0002062\u0006\u0010;\u001a\u00020<H\u0016J\b\u0010=\u001a\u00020%H\u0002J\u0012\u0010>\u001a\u00020%2\b\u00103\u001a\u0004\u0018\u00010\u001eH\u0016J\u0012\u0010?\u001a\u0002062\b\u00103\u001a\u0004\u0018\u00010\u001eH\u0016J\u001a\u0010@\u001a\u00020%2\b\u00103\u001a\u0004\u0018\u00010\u001e2\u0006\u0010A\u001a\u00020%H\u0016J\u0012\u0010B\u001a\u00020%2\b\u00103\u001a\u0004\u0018\u00010\u001eH\u0016J\u0012\u0010C\u001a\u0002062\b\u00103\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010D\u001a\u000206H\u0002J\b\u0010E\u001a\u000206H\u0014J\b\u0010F\u001a\u000206H\u0002J\u0010\u0010G\u001a\u00020\u001e2\u0006\u00103\u001a\u00020\u001eH\u0016R\u0014\u0010\u0013\u001a\u00020\u00148TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u001f\u001a\u0004\u0018\u00010\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u000e\u0010$\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010&\u001a\u00020'8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0017\u0010(\u001a\b\u0012\u0004\u0012\u00020\u001e0)¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0014\u0010,\u001a\u00020\u00148TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b-\u0010\u0016R\u0014\u0010.\u001a\u00020/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u00101¨\u0006H"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/BrushTool;", "Lorg/catrobat/paintroid/tools/implementation/BaseTool;", "brushToolOptionsView", "Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "drawTime", "", "(Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView;Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;J)V", "bitmapPaint", "Landroid/graphics/Paint;", "getBitmapPaint", "()Landroid/graphics/Paint;", "getBrushToolOptionsView", "()Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView;", "getDrawTime", "()J", "setDrawTime", "(J)V", "drawToolMovedDistance", "Landroid/graphics/PointF;", "initialEventCoordinate", "getInitialEventCoordinate", "()Landroid/graphics/PointF;", "setInitialEventCoordinate", "(Landroid/graphics/PointF;)V", "pathInsideBitmap", "", "pathToDraw", "Lorg/catrobat/paintroid/command/serialization/SerializablePath;", "pointArray", "", "getPointArray", "()Ljava/util/List;", "previewPaint", "getPreviewPaint", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "addPathCommand", "coordinate", "addPointCommand", "changePaintColor", "", TypedValues.Custom.S_COLOR, "", "invalidate", "draw", "canvas", "Landroid/graphics/Canvas;", "eventCoordinatesAreNull", "handleDown", "handleDownAnimations", "handleMove", "shouldAnimate", "handleUp", "handleUpAnimations", "hideBrushSpecificLayoutOnHandleDown", "resetInternalState", "showBrushSpecificLayoutOnHandleUp", "toolPositionCoordinates", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class BrushTool extends BaseTool {
    private final BrushToolOptionsView brushToolOptionsView;
    private long drawTime;
    private final PointF drawToolMovedDistance;
    private PointF initialEventCoordinate;
    private boolean pathInsideBitmap;
    public SerializablePath pathToDraw;
    private final List<PointF> pointArray;

    @Override // org.catrobat.paintroid.tools.Tool
    public PointF toolPositionCoordinates(PointF coordinate) {
        Intrinsics.checkNotNullParameter(coordinate, "coordinate");
        return coordinate;
    }

    public final BrushToolOptionsView getBrushToolOptionsView() {
        return this.brushToolOptionsView;
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
    public BrushTool(BrushToolOptionsView brushToolOptionsView, ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager, long j) {
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
        this.drawToolMovedDistance = new PointF(0.0f, 0.0f);
        this.pointArray = new ArrayList();
        toolOptionsViewController.enable();
        this.pathToDraw.incReserve(1);
        brushToolOptionsView.setBrushChangedListener(new CommonBrushChangedListener(this));
        brushToolOptionsView.setBrushPreviewListener(new CommonBrushPreviewListener(toolPaint, getToolType()));
        brushToolOptionsView.setCurrentPaint(toolPaint.getBitmapPaint());
        brushToolOptionsView.setStrokeCapButtonChecked(toolPaint.getStrokeCap());
    }

    protected Paint getPreviewPaint() {
        return this.toolPaint.getPreviewPaint();
    }

    protected Paint getBitmapPaint() {
        return this.toolPaint.getBitmapPaint();
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public ToolType getToolType() {
        return ToolType.BRUSH;
    }

    public final PointF getInitialEventCoordinate() {
        return this.initialEventCoordinate;
    }

    public final void setInitialEventCoordinate(PointF pointF) {
        this.initialEventCoordinate = pointF;
    }

    public final List<PointF> getPointArray() {
        return this.pointArray;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        canvas.save();
        canvas.clipRect(0, 0, this.workspace.getWidth(), this.workspace.getHeight());
        canvas.drawPath(this.pathToDraw, getPreviewPaint());
        canvas.restore();
    }

    private final void hideBrushSpecificLayoutOnHandleDown() {
        if (this.toolOptionsViewController.isVisible()) {
            if (this.brushToolOptionsView.getTopToolOptions().getVisibility() == 0) {
                this.toolOptionsViewController.slideUp(this.brushToolOptionsView.getTopToolOptions(), true, false);
            }
            if (this.brushToolOptionsView.getBottomToolOptions().getVisibility() == 0) {
                this.toolOptionsViewController.slideDown(this.brushToolOptionsView.getBottomToolOptions(), true, false);
            }
        }
    }

    private final void showBrushSpecificLayoutOnHandleUp() {
        if (this.toolOptionsViewController.isVisible()) {
            return;
        }
        if (this.brushToolOptionsView.getBottomToolOptions().getVisibility() == 4) {
            this.toolOptionsViewController.slideDown(this.brushToolOptionsView.getTopToolOptions(), false, true);
        }
        if (this.brushToolOptionsView.getBottomToolOptions().getVisibility() == 4) {
            this.toolOptionsViewController.slideUp(this.brushToolOptionsView.getBottomToolOptions(), false, true);
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleDown(PointF coordinate) {
        if (coordinate == null) {
            return false;
        }
        super.handleDown(coordinate);
        this.initialEventCoordinate = new PointF(coordinate.x, coordinate.y);
        this.previousEventCoordinate = new PointF(coordinate.x, coordinate.y);
        this.pathToDraw.moveTo(coordinate.x, coordinate.y);
        this.drawToolMovedDistance.set(0.0f, 0.0f);
        this.pointArray.add(new PointF(coordinate.x, coordinate.y));
        this.pathInsideBitmap = this.workspace.contains(coordinate);
        return true;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleDownAnimations(PointF coordinate) {
        hideBrushSpecificLayoutOnHandleDown();
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleUpAnimations(PointF coordinate) {
        showBrushSpecificLayoutOnHandleUp();
        super.handleUp(coordinate);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleMove(PointF coordinate, boolean shouldAnimate) {
        if (eventCoordinatesAreNull() || coordinate == null) {
            return false;
        }
        super.handleMove(coordinate, shouldAnimate);
        if (shouldAnimate) {
            hideBrushSpecificLayoutOnHandleDown();
        }
        PointF pointF = this.previousEventCoordinate;
        if (pointF != null) {
            this.pathToDraw.quadTo(pointF.x, pointF.y, coordinate.x, coordinate.y);
            this.pathToDraw.incReserve(1);
            PointF pointF2 = this.drawToolMovedDistance;
            pointF2.set(pointF2.x + Math.abs(coordinate.x - pointF.x), this.drawToolMovedDistance.y + Math.abs(coordinate.y - pointF.y));
            getPointArray().add(new PointF(coordinate.x, coordinate.y));
            pointF.set(coordinate.x, coordinate.y);
        }
        if (!this.pathInsideBitmap && this.workspace.contains(coordinate)) {
            this.pathInsideBitmap = true;
        }
        return true;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleUp(PointF coordinate) {
        if (eventCoordinatesAreNull() || coordinate == null) {
            return false;
        }
        showBrushSpecificLayoutOnHandleUp();
        super.handleUp(coordinate);
        if (!this.pathInsideBitmap && this.workspace.contains(coordinate)) {
            this.pathInsideBitmap = true;
        }
        PointF pointF = this.previousEventCoordinate;
        if (pointF != null) {
            PointF pointF2 = this.drawToolMovedDistance;
            pointF2.set(pointF2.x + Math.abs(coordinate.x - pointF.x), this.drawToolMovedDistance.y + Math.abs(coordinate.y - pointF.y));
        }
        if (5.0f < Math.max(this.drawToolMovedDistance.x, this.drawToolMovedDistance.y)) {
            return addPathCommand(coordinate);
        }
        PointF pointF3 = this.initialEventCoordinate;
        if (pointF3 == null) {
            return false;
        }
        return addPointCommand(pointF3);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool
    protected void resetInternalState() {
        this.pathToDraw.rewind();
        this.pointArray.clear();
        this.initialEventCoordinate = null;
        this.previousEventCoordinate = null;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void changePaintColor(int color, boolean invalidate) {
        super.changePaintColor(color, invalidate);
        if (invalidate) {
            this.brushToolOptionsView.invalidate();
        }
    }

    private final boolean eventCoordinatesAreNull() {
        return this.initialEventCoordinate == null || this.previousEventCoordinate == null;
    }

    private final boolean addPathCommand(PointF coordinate) {
        this.pathToDraw.lineTo(coordinate.x, coordinate.y);
        if (!this.pathInsideBitmap) {
            resetInternalState(Tool.StateChange.RESET_INTERNAL_STATE);
            return false;
        }
        PointF pointF = this.initialEventCoordinate;
        Double dValueOf = pointF == null ? null : Double.valueOf(Math.sqrt(((coordinate.x - pointF.x) * (coordinate.x - pointF.x)) + ((coordinate.y - pointF.y) * (coordinate.y - pointF.y))));
        Double dValueOf2 = dValueOf != null ? Double.valueOf(dValueOf.doubleValue() / getDrawTime()) : null;
        if (!AdvancedSettingsAlgorithms.smoothing || (dValueOf2 != null && dValueOf2.doubleValue() < 0.2d)) {
            this.commandManager.addCommand(this.commandFactory.createPathCommand(getBitmapPaint(), this.pathToDraw));
        } else {
            this.commandManager.addCommand(this.commandFactory.createPathCommand(getBitmapPaint(), AdvancedSettingsAlgorithms.smoothingAlgorithm(this.pointArray)));
        }
        this.pointArray.clear();
        return true;
    }

    private final boolean addPointCommand(PointF coordinate) {
        if (!this.pathInsideBitmap) {
            resetInternalState(Tool.StateChange.RESET_INTERNAL_STATE);
            return false;
        }
        this.pointArray.clear();
        this.commandManager.addCommand(this.commandFactory.createPointCommand(getBitmapPaint(), coordinate));
        return true;
    }
}
