package org.catrobat.paintroid.tools.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.os.Bundle;
import androidx.test.espresso.idling.CountingIdlingResource;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;

/* JADX INFO: compiled from: ImportTool.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0012\u0010\u001d\u001a\u00020\u001a2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\u0012\u0010 \u001a\u00020\u001a2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010!\u001a\u00020\u001aH\u0016J\u0012\u0010\"\u001a\u00020\u001a2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u0012\u0010%\u001a\u00020\u001a2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u000e\u0010&\u001a\u00020\u001a2\u0006\u0010'\u001a\u00020(J\u0010\u0010)\u001a\u00020\u001f2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016R\u001a\u0010\u000e\u001a\u00020\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006*"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/ImportTool;", "Lorg/catrobat/paintroid/tools/implementation/BaseToolWithRectangleShape;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "drawTime", "", "(Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;J)V", "getDrawTime", "()J", "setDrawTime", "(J)V", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "drawShape", "", "canvas", "Landroid/graphics/Canvas;", "handleDownAnimations", "coordinate", "Landroid/graphics/PointF;", "handleUpAnimations", "onClickOnButton", "onRestoreInstanceState", "bundle", "Landroid/os/Bundle;", "onSaveInstanceState", "setBitmapFromSource", "bitmap", "Landroid/graphics/Bitmap;", "toolPositionCoordinates", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ImportTool extends BaseToolWithRectangleShape {
    private long drawTime;

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
    public ImportTool(ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager, long j) {
        super(contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager);
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        this.drawTime = j;
        this.rotationEnabled = true;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public ToolType getToolType() {
        return ToolType.IMPORTPNG;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleUpAnimations(PointF coordinate) {
        super.handleUp(coordinate);
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleDownAnimations(PointF coordinate) {
        super.handleDown(coordinate);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape, org.catrobat.paintroid.tools.implementation.BaseToolWithShape
    public void drawShape(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (this.drawingBitmap != null) {
            super.drawShape(canvas);
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape, org.catrobat.paintroid.tools.implementation.BaseToolWithShape, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle == null) {
            return;
        }
        bundle.putParcelable("BUNDLE_TOOL_DRAWING_BITMAP", this.drawingBitmap);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape, org.catrobat.paintroid.tools.implementation.BaseToolWithShape, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        this.drawingBitmap = bundle == null ? null : (Bitmap) bundle.getParcelable("BUNDLE_TOOL_DRAWING_BITMAP");
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithShape
    public void onClickOnButton() {
        Bitmap bitmap = this.drawingBitmap;
        if (bitmap == null) {
            return;
        }
        highlightBox();
        this.commandManager.addCommand(this.commandFactory.createClipboardCommand(bitmap, this.toolPosition, this.boxWidth, this.boxHeight, this.boxRotation));
    }

    public final void setBitmapFromSource(Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        super.setBitmap(bitmap);
        float width = this.workspace.getWidth() * 2.0f;
        float height = this.workspace.getHeight() * 2.0f;
        this.boxWidth = Math.max(20.0f, Math.min(width, bitmap.getWidth()));
        this.boxHeight = Math.max(20.0f, Math.min(height, bitmap.getHeight()));
    }
}
