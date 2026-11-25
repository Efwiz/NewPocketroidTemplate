package org.catrobat.paintroid.tools.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import androidx.test.espresso.idling.CountingIdlingResource;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.colorpicker.OnColorPickedListener;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;

/* JADX INFO: compiled from: PipetteTool.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0016J\u0012\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u0012\u0010%\u001a\u00020\u001e2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u001a\u0010&\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010'\u001a\u00020\"H\u0016J\u0012\u0010(\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u0012\u0010)\u001a\u00020\u001e2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\b\u0010*\u001a\u00020\u001eH\u0014J\u0012\u0010+\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0002J\u0010\u0010,\u001a\u00020$2\u0006\u0010#\u001a\u00020$H\u0016J\b\u0010-\u001a\u00020\u001eH\u0002R\u001a\u0010\u0011\u001a\u00020\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u0006."}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/PipetteTool;", "Lorg/catrobat/paintroid/tools/implementation/BaseTool;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lorg/catrobat/paintroid/colorpicker/OnColorPickedListener;", "(Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;Lorg/catrobat/paintroid/colorpicker/OnColorPickedListener;)V", "drawTime", "", "getDrawTime", "()J", "setDrawTime", "(J)V", "surfaceBitmap", "Landroid/graphics/Bitmap;", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "draw", "", "canvas", "Landroid/graphics/Canvas;", "handleDown", "", "coordinate", "Landroid/graphics/PointF;", "handleDownAnimations", "handleMove", "shouldAnimate", "handleUp", "handleUpAnimations", "resetInternalState", "setColor", "toolPositionCoordinates", "updateSurfaceBitmap", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class PipetteTool extends BaseTool {
    private long drawTime;
    private final OnColorPickedListener listener;
    private Bitmap surfaceBitmap;

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public PointF toolPositionCoordinates(PointF coordinate) {
        Intrinsics.checkNotNullParameter(coordinate, "coordinate");
        return coordinate;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PipetteTool(ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager, OnColorPickedListener listener) {
        super(contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager);
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.listener = listener;
        updateSurfaceBitmap();
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public ToolType getToolType() {
        return ToolType.PIPETTE;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public long getDrawTime() {
        return this.drawTime;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void setDrawTime(long j) {
        this.drawTime = j;
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
    public boolean handleDown(PointF coordinate) {
        return setColor(coordinate);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleMove(PointF coordinate, boolean shouldAnimate) {
        return setColor(coordinate);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleUp(PointF coordinate) {
        return setColor(coordinate);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool
    protected void resetInternalState() {
        updateSurfaceBitmap();
    }

    private final boolean setColor(PointF coordinate) {
        if (coordinate == null || !this.workspace.contains(coordinate)) {
            return false;
        }
        Bitmap bitmap = this.surfaceBitmap;
        Integer numValueOf = bitmap == null ? null : Integer.valueOf(bitmap.getPixel((int) coordinate.x, (int) coordinate.y));
        if (numValueOf == null) {
            return false;
        }
        int iIntValue = numValueOf.intValue();
        this.listener.colorChanged(iIntValue);
        Tool.DefaultImpls.changePaintColor$default(this, iIntValue, false, 2, null);
        return true;
    }

    private final void updateSurfaceBitmap() {
        this.surfaceBitmap = this.workspace.getBitmapOfAllLayers();
    }
}
