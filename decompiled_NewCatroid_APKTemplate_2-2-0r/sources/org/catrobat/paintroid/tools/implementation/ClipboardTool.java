package org.catrobat.paintroid.tools.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import androidx.test.espresso.idling.CountingIdlingResource;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.options.ClipboardToolOptionsView;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;

/* JADX INFO: compiled from: ClipboardTool.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u0006\u0010\u001f\u001a\u00020 J\b\u0010!\u001a\u00020 H\u0002J\u0012\u0010\"\u001a\u00020 2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u0012\u0010%\u001a\u00020 2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\b\u0010&\u001a\u00020 H\u0016J\u0012\u0010'\u001a\u00020 2\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J\u0012\u0010*\u001a\u00020 2\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J\b\u0010+\u001a\u00020 H\u0002J\b\u0010,\u001a\u00020 H\u0014J\u0010\u0010-\u001a\u00020$2\u0006\u0010#\u001a\u00020$H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00188BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e¨\u0006."}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/ClipboardTool;", "Lorg/catrobat/paintroid/tools/implementation/BaseToolWithRectangleShape;", "clipboardToolOptionsView", "Lorg/catrobat/paintroid/tools/options/ClipboardToolOptionsView;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "drawTime", "", "(Lorg/catrobat/paintroid/tools/options/ClipboardToolOptionsView;Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;J)V", "getDrawTime", "()J", "setDrawTime", "(J)V", "isDrawingBitmapReusable", "", "()Z", "readyForPaste", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "copyBoxContent", "", "cutBoxContent", "handleDownAnimations", "coordinate", "Landroid/graphics/PointF;", "handleUpAnimations", "onClickOnButton", "onRestoreInstanceState", "bundle", "Landroid/os/Bundle;", "onSaveInstanceState", "pasteBoxContent", "resetInternalState", "toolPositionCoordinates", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ClipboardTool extends BaseToolWithRectangleShape {
    private final ClipboardToolOptionsView clipboardToolOptionsView;
    private long drawTime;
    private boolean readyForPaste;

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool
    protected void resetInternalState() {
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
    public ClipboardTool(ClipboardToolOptionsView clipboardToolOptionsView, ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager, long j) {
        super(contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager);
        Intrinsics.checkNotNullParameter(clipboardToolOptionsView, "clipboardToolOptionsView");
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        this.drawTime = j;
        this.rotationEnabled = true;
        this.clipboardToolOptionsView = clipboardToolOptionsView;
        setBitmap(Bitmap.createBitmap((int) this.boxWidth, (int) this.boxHeight, Bitmap.Config.ARGB_8888));
        clipboardToolOptionsView.setCallback(new ClipboardToolOptionsView.Callback() { // from class: org.catrobat.paintroid.tools.implementation.ClipboardTool$callback$1
            @Override // org.catrobat.paintroid.tools.options.ClipboardToolOptionsView.Callback
            public void copyClicked() {
                this.this$0.highlightBox();
                this.this$0.copyBoxContent();
                this.this$0.clipboardToolOptionsView.enablePaste(true);
            }

            @Override // org.catrobat.paintroid.tools.options.ClipboardToolOptionsView.Callback
            public void cutClicked() {
                this.this$0.highlightBox();
                this.this$0.copyBoxContent();
                this.this$0.cutBoxContent();
                this.this$0.clipboardToolOptionsView.enablePaste(true);
            }

            @Override // org.catrobat.paintroid.tools.options.ClipboardToolOptionsView.Callback
            public void pasteClicked() {
                this.this$0.highlightBox();
                this.this$0.pasteBoxContent();
            }
        });
        toolOptionsViewController.showDelayed();
    }

    private final boolean isDrawingBitmapReusable() {
        Bitmap bitmap = this.drawingBitmap;
        return bitmap != null && bitmap.getWidth() == ((int) this.boxWidth) && bitmap.getHeight() == ((int) this.boxHeight);
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public ToolType getToolType() {
        return ToolType.CLIPBOARD;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleUpAnimations(PointF coordinate) {
        super.handleUp(coordinate);
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleDownAnimations(PointF coordinate) {
        super.handleDown(coordinate);
    }

    public final void copyBoxContent() {
        Bitmap bitmap;
        if (isDrawingBitmapReusable()) {
            Bitmap bitmap2 = this.drawingBitmap;
            if (bitmap2 != null) {
                bitmap2.eraseColor(0);
            }
        } else {
            this.drawingBitmap = Bitmap.createBitmap((int) this.boxWidth, (int) this.boxHeight, Bitmap.Config.ARGB_8888);
        }
        Bitmap bitmapOfCurrentLayer = this.workspace.getBitmapOfCurrentLayer();
        if (bitmapOfCurrentLayer != null && (bitmap = this.drawingBitmap) != null) {
            Canvas canvas = new Canvas(bitmap);
            float f = 2;
            canvas.translate((-this.toolPosition.x) + (this.boxWidth / f), (-this.toolPosition.y) + (this.boxHeight / f));
            canvas.rotate(-this.boxRotation, this.toolPosition.x, this.toolPosition.y);
            canvas.drawBitmap(bitmapOfCurrentLayer, 0.0f, 0.0f, (Paint) null);
        }
        this.readyForPaste = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void pasteBoxContent() {
        Bitmap bitmap = this.drawingBitmap;
        if (bitmap == null) {
            return;
        }
        this.commandManager.addCommand(this.commandFactory.createClipboardCommand(bitmap, this.toolPosition, this.boxWidth, this.boxHeight, this.boxRotation));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void cutBoxContent() {
        this.commandManager.addCommand(this.commandFactory.createCutCommand(this.toolPosition, this.boxWidth, this.boxHeight, this.boxRotation));
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithShape
    public void onClickOnButton() {
        if (!this.readyForPaste || this.drawingBitmap == null) {
            this.contextCallback.showNotification(R.string.clipboard_tool_copy_hint);
        } else if (boxIntersectsWorkspace()) {
            pasteBoxContent();
            highlightBox();
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape, org.catrobat.paintroid.tools.implementation.BaseToolWithShape, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle != null) {
            bundle.putParcelable("BUNDLE_TOOL_DRAWING_BITMAP", this.drawingBitmap);
        }
        if (bundle == null) {
            return;
        }
        bundle.putBoolean("BUNDLE_TOOL_READY_FOR_PASTE", this.readyForPaste);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape, org.catrobat.paintroid.tools.implementation.BaseToolWithShape, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        if (bundle != null) {
            this.readyForPaste = bundle.getBoolean("BUNDLE_TOOL_READY_FOR_PASTE", this.readyForPaste);
            this.drawingBitmap = (Bitmap) bundle.getParcelable("BUNDLE_TOOL_DRAWING_BITMAP");
        }
        this.clipboardToolOptionsView.enablePaste(this.readyForPaste);
    }
}
