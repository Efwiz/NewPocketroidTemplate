package org.catrobat.paintroid.tools.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PointF;
import androidx.core.view.ViewCompat;
import androidx.test.espresso.idling.CountingIdlingResource;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.MainActivity;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.common.CommonBrushChangedListener;
import org.catrobat.paintroid.tools.common.CommonBrushPreviewListener;
import org.catrobat.paintroid.tools.options.BrushToolOptionsView;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;

/* JADX INFO: compiled from: ClippingTool.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013¢\u0006\u0002\u0010\u0014J\u0010\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;H\u0016J\u0006\u0010<\u001a\u000209J\u0010\u0010=\u001a\u0002092\u0006\u0010>\u001a\u00020?H\u0016J\u0012\u0010@\u001a\u00020\u00162\b\u0010A\u001a\u0004\u0018\u00010BH\u0016J\u0012\u0010C\u001a\u00020\u00162\b\u0010A\u001a\u0004\u0018\u00010BH\u0016J\u0006\u0010D\u001a\u000209J\u0010\u0010E\u001a\u0002092\u0006\u0010F\u001a\u00020GH\u0016R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u001c8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001e\"\u0004\b!\u0010\"R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u0010\u0010'\u001a\u0004\u0018\u00010(X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010)\u001a\u00020*8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b+\u0010,R\u0014\u0010-\u001a\u00020*8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b.\u0010,R\u0014\u0010/\u001a\u00020\u001c8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b0\u0010\u001eR\u0014\u00101\u001a\u0002028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b3\u00104R\u001a\u00105\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\u0018\"\u0004\b7\u0010\u001a¨\u0006H"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/ClippingTool;", "Lorg/catrobat/paintroid/tools/implementation/BrushTool;", "brushToolOptionsView", "Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "drawTime", "", "mainActivity", "Lorg/catrobat/paintroid/MainActivity;", "(Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView;Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;JLorg/catrobat/paintroid/MainActivity;)V", "areaClosed", "", "getAreaClosed", "()Z", "setAreaClosed", "(Z)V", "bitmapPaint", "Landroid/graphics/Paint;", "getBitmapPaint", "()Landroid/graphics/Paint;", "clippingPaint", "getClippingPaint", "setClippingPaint", "(Landroid/graphics/Paint;)V", "getMainActivity", "()Lorg/catrobat/paintroid/MainActivity;", "setMainActivity", "(Lorg/catrobat/paintroid/MainActivity;)V", "newBitmap", "Landroid/graphics/Bitmap;", "pathGapLength", "", "getPathGapLength", "()F", "pathLineLength", "getPathLineLength", "previewPaint", "getPreviewPaint", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "wasRecentlyApplied", "getWasRecentlyApplied", "setWasRecentlyApplied", "changePaintStrokeWidth", "", "strokeWidth", "", "copyBitmapOfCurrentLayer", "draw", "canvas", "Landroid/graphics/Canvas;", "handleDown", "coordinate", "Landroid/graphics/PointF;", "handleUp", "onClickOnButton", "resetInternalState", "stateChange", "Lorg/catrobat/paintroid/tools/Tool$StateChange;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ClippingTool extends BrushTool {
    private boolean areaClosed;
    private Paint clippingPaint;
    private MainActivity mainActivity;
    private Bitmap newBitmap;
    private boolean wasRecentlyApplied;

    public final MainActivity getMainActivity() {
        return this.mainActivity;
    }

    public final void setMainActivity(MainActivity mainActivity) {
        Intrinsics.checkNotNullParameter(mainActivity, "<set-?>");
        this.mainActivity = mainActivity;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClippingTool(BrushToolOptionsView brushToolOptionsView, ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager, long j, MainActivity mainActivity) {
        super(brushToolOptionsView, contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, j);
        Intrinsics.checkNotNullParameter(brushToolOptionsView, "brushToolOptionsView");
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        Intrinsics.checkNotNullParameter(mainActivity, "mainActivity");
        this.mainActivity = mainActivity;
        this.clippingPaint = new Paint();
        toolPaint.getBitmapPaint().setStrokeWidth(10.0f);
        toolPaint.getBitmapPaint().setStyle(Paint.Style.STROKE);
        toolPaint.getBitmapPaint().setPathEffect(new DashPathEffect(new float[]{getPathLineLength(), getPathGapLength()}, 0.0f));
        brushToolOptionsView.setBrushChangedListener(new CommonBrushChangedListener(this));
        brushToolOptionsView.setBrushPreviewListener(new CommonBrushPreviewListener(toolPaint, getToolType()));
        brushToolOptionsView.setCurrentPaint(toolPaint.getBitmapPaint());
        brushToolOptionsView.invalidate();
        toolOptionsViewController.showCheckmark();
        brushToolOptionsView.hideCaps();
        copyBitmapOfCurrentLayer();
    }

    public final boolean getWasRecentlyApplied() {
        return this.wasRecentlyApplied;
    }

    public final void setWasRecentlyApplied(boolean z) {
        this.wasRecentlyApplied = z;
    }

    public final boolean getAreaClosed() {
        return this.areaClosed;
    }

    public final void setAreaClosed(boolean z) {
        this.areaClosed = z;
    }

    public final Paint getClippingPaint() {
        return this.clippingPaint;
    }

    public final void setClippingPaint(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "<set-?>");
        this.clippingPaint = paint;
    }

    private final float getPathLineLength() {
        return this.toolPaint.getBitmapPaint().getStrokeWidth() * 2.5f;
    }

    private final float getPathGapLength() {
        return this.toolPaint.getBitmapPaint().getStrokeWidth() * 2.0f;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BrushTool
    protected Paint getBitmapPaint() {
        return this.toolPaint.getBitmapPaint();
    }

    @Override // org.catrobat.paintroid.tools.implementation.BrushTool
    protected Paint getPreviewPaint() {
        return this.toolPaint.getPreviewPaint();
    }

    @Override // org.catrobat.paintroid.tools.implementation.BrushTool, org.catrobat.paintroid.tools.Tool
    public ToolType getToolType() {
        return ToolType.CLIP;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BrushTool, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Paint previewPaint = this.toolPaint.getPreviewPaint();
        this.clippingPaint = previewPaint;
        previewPaint.setPathEffect(this.toolPaint.getBitmapPaint().getPathEffect());
        Paint paint = this.clippingPaint;
        int previewColor = this.toolPaint.getPreviewColor();
        int i = ViewCompat.MEASURED_STATE_MASK;
        if (previewColor == -16777216) {
            i = -1;
        }
        paint.setColor(i);
        this.clippingPaint.setStrokeWidth(this.toolPaint.getBitmapPaint().getStrokeWidth() + 5);
        this.idlingResource.increment();
        canvas.save();
        canvas.clipRect(0, 0, this.workspace.getWidth(), this.workspace.getHeight());
        canvas.drawPath(this.pathToDraw, getClippingPaint());
        canvas.drawPath(this.pathToDraw, this.toolPaint.getBitmapPaint());
        canvas.restore();
        this.idlingResource.decrement();
    }

    public final void copyBitmapOfCurrentLayer() {
        if (this.workspace.getBitmapOfCurrentLayer() != null) {
            Bitmap bitmapOfCurrentLayer = this.workspace.getBitmapOfCurrentLayer();
            Bitmap bitmapCopy = null;
            if (bitmapOfCurrentLayer != null) {
                Bitmap bitmapOfCurrentLayer2 = this.workspace.getBitmapOfCurrentLayer();
                bitmapCopy = bitmapOfCurrentLayer.copy(bitmapOfCurrentLayer2 != null ? bitmapOfCurrentLayer2.getConfig() : null, true);
            }
            this.newBitmap = bitmapCopy;
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BrushTool, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleDown(PointF coordinate) {
        if (this.areaClosed) {
            super.resetInternalState();
            this.areaClosed = false;
            this.commandManager.undoInClippingTool();
            Tool.DefaultImpls.changePaintColor$default(this, this.toolPaint.getPreviewPaint().getColor(), false, 2, null);
            getBrushToolOptionsView().setCurrentPaint(this.toolPaint.getBitmapPaint());
            getBrushToolOptionsView().invalidate();
            this.mainActivity.getBottomNavigationViewHolder().setColorButtonColor(this.toolPaint.getPreviewColor());
        }
        return super.handleDown(coordinate);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BrushTool, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleUp(PointF coordinate) {
        PointF initialEventCoordinate = getInitialEventCoordinate();
        if (Intrinsics.areEqual(this.previousEventCoordinate, getInitialEventCoordinate())) {
            super.resetInternalState();
            return false;
        }
        if (!this.areaClosed && coordinate != null && initialEventCoordinate != null) {
            this.pathToDraw.incReserve(1);
            this.pathToDraw.quadTo(coordinate.x, coordinate.y, initialEventCoordinate.x, initialEventCoordinate.y);
            getPointArray().add(new PointF(coordinate.x, coordinate.y));
            this.areaClosed = true;
        }
        return super.handleUp(coordinate);
    }

    /* JADX WARN: Code duplicated, block: B:6:0x000e  */
    public final void onClickOnButton() {
        Bitmap.Config config;
        Bitmap bitmap;
        Bitmap bitmapCreateBitmap;
        this.idlingResource.increment();
        if (this.areaClosed) {
            Bitmap bitmap2 = this.newBitmap;
            if (bitmap2 == null || (config = bitmap2.getConfig()) == null || (bitmap = this.newBitmap) == null) {
                bitmapCreateBitmap = null;
            } else {
                int width = bitmap.getWidth();
                Bitmap bitmap3 = this.newBitmap;
                if (bitmap3 == null) {
                    bitmapCreateBitmap = null;
                } else {
                    bitmapCreateBitmap = Bitmap.createBitmap(width, bitmap3.getHeight(), config);
                }
            }
            Paint paint = new Paint();
            paint.setColor(ViewCompat.MEASURED_STATE_MASK);
            paint.setStyle(Paint.Style.FILL);
            if (bitmapCreateBitmap != null) {
                new Canvas(bitmapCreateBitmap).drawPath(this.pathToDraw, paint);
            }
            Bitmap bitmap4 = this.newBitmap;
            if (bitmap4 != null) {
                this.commandManager.addCommand(bitmapCreateBitmap != null ? this.commandFactory.createClippingCommand(bitmap4, bitmapCreateBitmap) : null);
                this.commandManager.adjustUndoListForClippingTool();
            }
            this.areaClosed = false;
            this.wasRecentlyApplied = true;
            this.pathToDraw.rewind();
        }
        this.idlingResource.decrement();
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void resetInternalState(Tool.StateChange stateChange) {
        Intrinsics.checkNotNullParameter(stateChange, "stateChange");
        if (stateChange == Tool.StateChange.NEW_IMAGE_LOADED) {
            this.areaClosed = false;
            super.resetInternalState();
            copyBitmapOfCurrentLayer();
        } else if (stateChange == Tool.StateChange.RESET_INTERNAL_STATE && this.wasRecentlyApplied) {
            copyBitmapOfCurrentLayer();
            this.wasRecentlyApplied = false;
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void changePaintStrokeWidth(int strokeWidth) {
        super.changePaintStrokeWidth(strokeWidth);
        this.toolPaint.getBitmapPaint().setPathEffect(new DashPathEffect(new float[]{getPathLineLength(), getPathGapLength()}, 0.0f));
    }
}
