package org.catrobat.paintroid.tools.implementation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.test.espresso.idling.CountingIdlingResource;
import com.reandroid.arsc.value.ResConfigBase;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.CommandFactory;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.command.implementation.DefaultCommandFactory;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.common.PointScrollBehavior;
import org.catrobat.paintroid.tools.common.ScrollBehavior;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;

/* JADX INFO: compiled from: BaseTool.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u001a\u0010\u001a\u001a\u00020\u001b2\b\b\u0001\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020\u001dH\u0016J\u0010\u0010%\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020'H&J(\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020+2\u0006\u0010-\u001a\u00020\u001d2\u0006\u0010.\u001a\u00020\u001dH\u0016J\b\u0010/\u001a\u00020\u001fH\u0016J\u0012\u00100\u001a\u00020\u001f2\b\u00101\u001a\u0004\u0018\u00010\u0016H\u0016J\u001a\u00102\u001a\u00020\u001f2\b\u00101\u001a\u0004\u0018\u00010\u00162\u0006\u00103\u001a\u00020\u001fH\u0016J\u0012\u00104\u001a\u00020\u001f2\b\u00101\u001a\u0004\u0018\u00010\u0016H\u0016J\u0012\u00105\u001a\u00020\u001b2\b\u00106\u001a\u0004\u0018\u000107H\u0016J\u0012\u00108\u001a\u00020\u001b2\b\u00106\u001a\u0004\u0018\u000107H\u0016J\b\u00109\u001a\u00020\u001bH\u0014J\u0010\u00109\u001a\u00020\u001b2\u0006\u0010:\u001a\u00020;H\u0016R\u0012\u0010\u000f\u001a\u00020\u00108\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00020\r8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0002\u001a\u00020\u00038\u0016@\u0016X\u0097\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\n\u001a\u00020\u000b8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u00020\u00168\u0004X\u0085\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u00168\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0018\u001a\u00020\u00198\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u00020\u00078\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\t8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000¨\u0006<"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/BaseTool;", "Lorg/catrobat/paintroid/tools/Tool;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "(Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;)V", "commandFactory", "Lorg/catrobat/paintroid/command/CommandFactory;", "drawPaint", "Landroid/graphics/Paint;", "getDrawPaint", "()Landroid/graphics/Paint;", "movedDistance", "Landroid/graphics/PointF;", "previousEventCoordinate", "scrollBehavior", "Lorg/catrobat/paintroid/tools/common/ScrollBehavior;", "changePaintColor", "", TypedValues.Custom.S_COLOR, "", "invalidate", "", "changePaintStrokeCap", "cap", "Landroid/graphics/Paint$Cap;", "changePaintStrokeWidth", "strokeWidth", "draw", "canvas", "Landroid/graphics/Canvas;", "getAutoScrollDirection", "Landroid/graphics/Point;", "pointX", "", "pointY", ResConfigBase.NAME_screenWidth, ResConfigBase.NAME_screenHeight, "handToolMode", "handleDown", "coordinate", "handleMove", "shouldAnimate", "handleUp", "onRestoreInstanceState", "bundle", "Landroid/os/Bundle;", "onSaveInstanceState", "resetInternalState", "stateChange", "Lorg/catrobat/paintroid/tools/Tool$StateChange;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class BaseTool implements Tool {
    protected CommandFactory commandFactory;
    protected CommandManager commandManager;
    public ContextCallback contextCallback;
    protected CountingIdlingResource idlingResource;
    protected final PointF movedDistance;
    public PointF previousEventCoordinate;
    protected ScrollBehavior scrollBehavior;
    public ToolOptionsViewController toolOptionsViewController;
    protected ToolPaint toolPaint;
    protected Workspace workspace;

    @Override // org.catrobat.paintroid.tools.Tool
    public abstract void draw(Canvas canvas);

    @Override // org.catrobat.paintroid.tools.Tool
    public boolean handToolMode() {
        return false;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public boolean handleDown(PointF coordinate) {
        return true;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void onRestoreInstanceState(Bundle bundle) {
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void onSaveInstanceState(Bundle bundle) {
    }

    protected void resetInternalState() {
    }

    public BaseTool(ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager) {
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        this.contextCallback = contextCallback;
        this.toolOptionsViewController = toolOptionsViewController;
        this.toolPaint = toolPaint;
        this.workspace = workspace;
        this.idlingResource = idlingResource;
        this.commandManager = commandManager;
        this.commandFactory = new DefaultCommandFactory();
        this.scrollBehavior = new PointScrollBehavior(this.contextCallback.getScrollTolerance());
        this.movedDistance = new PointF(0.0f, 0.0f);
        this.previousEventCoordinate = new PointF(0.0f, 0.0f);
        ToolPaint toolPaint2 = this.toolPaint;
        if (toolPaint2 == null || toolPaint2.getBitmapPaint() == null || this.toolPaint.getBitmapPaint().getPathEffect() == null) {
            return;
        }
        this.toolPaint.getBitmapPaint().setPathEffect(null);
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void changePaintColor(int color, boolean invalidate) {
        this.toolPaint.setColor(color);
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void changePaintStrokeWidth(int strokeWidth) {
        this.toolPaint.setStrokeWidth(strokeWidth);
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void changePaintStrokeCap(Paint.Cap cap) {
        Intrinsics.checkNotNullParameter(cap, "cap");
        this.toolPaint.setStrokeCap(cap);
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public boolean handleUp(PointF coordinate) {
        this.toolOptionsViewController.animateBottomAndTopNavigation(false);
        return true;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public boolean handleMove(PointF coordinate, boolean shouldAnimate) {
        if (shouldAnimate) {
            this.toolOptionsViewController.animateBottomAndTopNavigation(true);
        }
        return true;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public Paint getDrawPaint() {
        return new Paint(this.toolPaint.getBitmapPaint());
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void resetInternalState(Tool.StateChange stateChange) {
        Intrinsics.checkNotNullParameter(stateChange, "stateChange");
        if (getToolType().shouldReactToStateChange(stateChange)) {
            resetInternalState();
        }
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public Point getAutoScrollDirection(float pointX, float pointY, int screenWidth, int screenHeight) {
        return this.scrollBehavior.getScrollDirection(pointX, pointY, screenWidth, screenHeight);
    }
}
