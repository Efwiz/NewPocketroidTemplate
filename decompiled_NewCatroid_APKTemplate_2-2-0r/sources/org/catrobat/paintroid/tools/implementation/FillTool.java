package org.catrobat.paintroid.tools.implementation;

import android.graphics.Canvas;
import android.graphics.PointF;
import androidx.test.espresso.idling.CountingIdlingResource;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.options.FillToolOptionsView;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;

/* JADX INFO: compiled from: FillTool.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0016J\u000e\u0010#\u001a\u00020\u00142\u0006\u0010$\u001a\u00020%J\u0012\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J\u0012\u0010*\u001a\u00020 2\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J\u001a\u0010+\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010,\u001a\u00020'H\u0016J\u0012\u0010-\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J\u0012\u0010.\u001a\u00020 2\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J\b\u0010/\u001a\u00020 H\u0016J\u0010\u00100\u001a\u00020)2\u0006\u0010(\u001a\u00020)H\u0016J\u000e\u00101\u001a\u00020 2\u0006\u00102\u001a\u00020%R\u0018\u0010\u0013\u001a\u00020\u00148\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u001cX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e¨\u00063"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/FillTool;", "Lorg/catrobat/paintroid/tools/implementation/BaseTool;", "fillToolOptionsView", "Lorg/catrobat/paintroid/tools/options/FillToolOptionsView;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "drawTime", "", "(Lorg/catrobat/paintroid/tools/options/FillToolOptionsView;Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;J)V", "colorTolerance", "", "getColorTolerance$annotations", "()V", "getDrawTime", "()J", "setDrawTime", "(J)V", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "draw", "", "canvas", "Landroid/graphics/Canvas;", "getToleranceAbsoluteValue", "toleranceInPercent", "", "handleDown", "", "coordinate", "Landroid/graphics/PointF;", "handleDownAnimations", "handleMove", "shouldAnimate", "handleUp", "handleUpAnimations", "resetInternalState", "toolPositionCoordinates", "updateColorTolerance", "colorToleranceInPercent", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class FillTool extends BaseTool {
    public float colorTolerance;
    private long drawTime;
    private final ToolType toolType;

    public static /* synthetic */ void getColorTolerance$annotations() {
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
    }

    public final float getToleranceAbsoluteValue(int toleranceInPercent) {
        return (toleranceInPercent * 510) / 100.0f;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleDown(PointF coordinate) {
        return false;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleMove(PointF coordinate, boolean shouldAnimate) {
        return false;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool
    public void resetInternalState() {
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
    public FillTool(FillToolOptionsView fillToolOptionsView, ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager, long j) {
        super(contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager);
        Intrinsics.checkNotNullParameter(fillToolOptionsView, "fillToolOptionsView");
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        this.drawTime = j;
        this.colorTolerance = 61.2f;
        fillToolOptionsView.setCallback(new FillToolOptionsView.Callback() { // from class: org.catrobat.paintroid.tools.implementation.FillTool.1
            @Override // org.catrobat.paintroid.tools.options.FillToolOptionsView.Callback
            public void onColorToleranceChanged(int colorTolerance) {
                FillTool.this.updateColorTolerance(colorTolerance);
            }
        });
        this.toolType = ToolType.FILL;
    }

    public final void updateColorTolerance(int colorToleranceInPercent) {
        this.colorTolerance = getToleranceAbsoluteValue(colorToleranceInPercent);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleUp(PointF coordinate) {
        if (coordinate == null || !this.workspace.contains(coordinate)) {
            return false;
        }
        this.commandManager.addCommand(this.commandFactory.createFillCommand((int) coordinate.x, (int) coordinate.y, this.toolPaint.getBitmapPaint(), this.colorTolerance));
        return true;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public ToolType getToolType() {
        return this.toolType;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleUpAnimations(PointF coordinate) {
        super.handleUp(coordinate);
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleDownAnimations(PointF coordinate) {
        super.handleDown(coordinate);
    }
}
