package org.catrobat.paintroid.controller;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.test.espresso.idling.CountingIdlingResource;
import java.util.HashSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.colorpicker.OnColorPickedListener;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.ToolFactory;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolReference;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.implementation.ClippingTool;
import org.catrobat.paintroid.tools.implementation.EraserTool;
import org.catrobat.paintroid.tools.implementation.ImportTool;
import org.catrobat.paintroid.tools.implementation.LineTool;
import org.catrobat.paintroid.tools.implementation.SprayTool;
import org.catrobat.paintroid.tools.implementation.TextTool;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;
import org.catrobat.paintroid.ui.viewholder.TopBarViewHolder;

/* JADX INFO: compiled from: DefaultToolController.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u0018H\u0016J\u0010\u0010'\u001a\u00020\u00142\u0006\u0010 \u001a\u00020!H\u0002J\b\u0010(\u001a\u00020%H\u0016J\b\u0010)\u001a\u00020%H\u0016J\b\u0010*\u001a\u00020%H\u0016J\b\u0010+\u001a\u00020%H\u0016J\b\u0010,\u001a\u00020%H\u0016J\b\u0010-\u001a\u00020\u0018H\u0016J\u0010\u0010.\u001a\u00020%2\u0006\u0010/\u001a\u00020!H\u0002J\b\u00100\u001a\u00020%H\u0016J\b\u00101\u001a\u00020%H\u0016J\b\u00102\u001a\u00020%H\u0016J\u0012\u00103\u001a\u00020%2\b\u00104\u001a\u0004\u0018\u000105H\u0016J\u0010\u00106\u001a\u00020%2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u00107\u001a\u00020%H\u0016J\u0010\u00108\u001a\u00020%2\u0006\u00109\u001a\u00020\u0014H\u0002J\u0010\u00108\u001a\u00020%2\u0006\u0010 \u001a\u00020!H\u0016J\b\u0010:\u001a\u00020%H\u0016J\b\u0010;\u001a\u00020\u0018H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u0016\u0010\u001c\u001a\u0004\u0018\u00010\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010 \u001a\u0004\u0018\u00010!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006<"}, d2 = {"Lorg/catrobat/paintroid/controller/DefaultToolController;", "Lorg/catrobat/paintroid/controller/ToolController;", "toolReference", "Lorg/catrobat/paintroid/tools/ToolReference;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolFactory", "Lorg/catrobat/paintroid/tools/ToolFactory;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "(Lorg/catrobat/paintroid/tools/ToolReference;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolFactory;Lorg/catrobat/paintroid/command/CommandManager;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/ContextCallback;)V", "currentTool", "Lorg/catrobat/paintroid/tools/Tool;", "getCurrentTool", "()Lorg/catrobat/paintroid/tools/Tool;", "isDefaultTool", "", "()Z", "onColorPickedListener", "Lorg/catrobat/paintroid/colorpicker/OnColorPickedListener;", "toolColor", "", "getToolColor", "()Ljava/lang/Integer;", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "adjustClippingToolOnBackPressed", "", "backPressed", "createAndSetupTool", "createTool", "disableHideOption", "disableToolOptionsView", "enableHideOption", "enableToolOptionsView", "hasToolOptionsView", "hidePlusIfShown", "currentToolType", "hideToolOptionsView", "resetToolInternalState", "resetToolInternalStateOnImageLoaded", "setBitmapFromSource", "bitmap", "Landroid/graphics/Bitmap;", "setOnColorPickedListener", "showToolOptionsView", "switchTool", "tool", "toggleToolOptionsView", "toolOptionsViewVisible", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultToolController implements ToolController {
    private final CommandManager commandManager;
    private final ContextCallback contextCallback;
    private final CountingIdlingResource idlingResource;
    private OnColorPickedListener onColorPickedListener;
    private final ToolFactory toolFactory;
    private final ToolOptionsViewController toolOptionsViewController;
    private final ToolPaint toolPaint;
    private final ToolReference toolReference;
    private final Workspace workspace;

    public DefaultToolController(ToolReference toolReference, ToolOptionsViewController toolOptionsViewController, ToolFactory toolFactory, CommandManager commandManager, Workspace workspace, CountingIdlingResource idlingResource, ToolPaint toolPaint, ContextCallback contextCallback) {
        Intrinsics.checkNotNullParameter(toolReference, "toolReference");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolFactory, "toolFactory");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        this.toolReference = toolReference;
        this.toolOptionsViewController = toolOptionsViewController;
        this.toolFactory = toolFactory;
        this.commandManager = commandManager;
        this.workspace = workspace;
        this.idlingResource = idlingResource;
        this.toolPaint = toolPaint;
        this.contextCallback = contextCallback;
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public HashSet<ToolType> getToolList() {
        return ToolController.DefaultImpls.getToolList(this);
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public boolean isDefaultTool() {
        Tool tool = this.toolReference.getTool();
        return (tool == null ? null : tool.getToolType()) == ToolType.BRUSH;
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public Integer getToolColor() {
        Paint drawPaint;
        Tool tool = this.toolReference.getTool();
        if (tool == null || (drawPaint = tool.getDrawPaint()) == null) {
            return null;
        }
        return Integer.valueOf(drawPaint.getColor());
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public Tool getCurrentTool() {
        return this.toolReference.getTool();
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public ToolType getToolType() {
        Tool tool = this.toolReference.getTool();
        if (tool == null) {
            return null;
        }
        return tool.getToolType();
    }

    private final Tool createAndSetupTool(ToolType toolType) {
        OnColorPickedListener onColorPickedListener;
        if (toolType != ToolType.HAND || toolType != ToolType.CLIP) {
            this.toolOptionsViewController.removeToolViews();
        }
        if (getToolList().contains(toolType)) {
            this.toolOptionsViewController.showCheckmark();
        } else {
            this.toolOptionsViewController.hideCheckmark();
        }
        Tool tool = this.toolReference.getTool();
        if ((tool == null ? null : tool.getToolType()) == ToolType.SPRAY) {
            Tool currentTool = getCurrentTool();
            if (currentTool == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.SprayTool");
            }
            ((SprayTool) currentTool).resetRadiusToStrokeWidth();
        }
        ToolFactory toolFactory = this.toolFactory;
        ToolOptionsViewController toolOptionsViewController = this.toolOptionsViewController;
        CommandManager commandManager = this.commandManager;
        Workspace workspace = this.workspace;
        CountingIdlingResource countingIdlingResource = this.idlingResource;
        ToolPaint toolPaint = this.toolPaint;
        ContextCallback contextCallback = this.contextCallback;
        OnColorPickedListener onColorPickedListener2 = this.onColorPickedListener;
        if (onColorPickedListener2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("onColorPickedListener");
            onColorPickedListener = null;
        } else {
            onColorPickedListener = onColorPickedListener2;
        }
        Tool toolCreateTool = toolFactory.createTool(toolType, toolOptionsViewController, commandManager, workspace, countingIdlingResource, toolPaint, contextCallback, onColorPickedListener);
        if (toolType != ToolType.HAND) {
            this.toolOptionsViewController.resetToOrigin();
            this.toolOptionsViewController.show();
        }
        return toolCreateTool;
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public void setOnColorPickedListener(OnColorPickedListener onColorPickedListener) {
        Intrinsics.checkNotNullParameter(onColorPickedListener, "onColorPickedListener");
        this.onColorPickedListener = onColorPickedListener;
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public void switchTool(ToolType toolType) {
        Intrinsics.checkNotNullParameter(toolType, "toolType");
        switchTool(createAndSetupTool(toolType));
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public void hideToolOptionsView() {
        this.toolOptionsViewController.hide();
        Tool tool = this.toolReference.getTool();
        if ((tool == null ? null : tool.getToolType()) == ToolType.TEXT) {
            Tool tool2 = this.toolReference.getTool();
            if (tool2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.TextTool");
            }
            ((TextTool) tool2).hideTextToolLayout();
        }
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public void showToolOptionsView() {
        this.toolOptionsViewController.show();
        Tool tool = this.toolReference.getTool();
        if ((tool == null ? null : tool.getToolType()) == ToolType.TEXT) {
            Tool tool2 = this.toolReference.getTool();
            if (tool2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.TextTool");
            }
            ((TextTool) tool2).showTextToolLayout();
        }
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public boolean toolOptionsViewVisible() {
        return this.toolOptionsViewController.getToolOptionsShown();
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public void resetToolInternalState() {
        Tool tool = this.toolReference.getTool();
        if (tool == null) {
            return;
        }
        tool.resetInternalState(Tool.StateChange.RESET_INTERNAL_STATE);
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public void resetToolInternalStateOnImageLoaded() {
        Tool tool = this.toolReference.getTool();
        if (tool == null) {
            return;
        }
        tool.resetInternalState(Tool.StateChange.NEW_IMAGE_LOADED);
    }

    private final void switchTool(Tool tool) {
        Tool tool2 = this.toolReference.getTool();
        ToolType toolType = tool2 == null ? null : tool2.getToolType();
        if (toolType == ToolType.ERASER) {
            if (tool2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.EraserTool");
            }
            ((EraserTool) tool2).setSavedColor();
        }
        if (toolType != null) {
            hidePlusIfShown(toolType);
        }
        if (toolType == tool.getToolType()) {
            Bundle bundle = new Bundle();
            tool2.onSaveInstanceState(bundle);
            tool.onRestoreInstanceState(bundle);
        }
        this.toolReference.setTool(tool);
        this.workspace.invalidate();
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public void adjustClippingToolOnBackPressed(boolean backPressed) {
        Tool currentTool = getCurrentTool();
        if (currentTool == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.ClippingTool");
        }
        ClippingTool clippingTool = (ClippingTool) currentTool;
        if (backPressed) {
            if (clippingTool.getAreaClosed()) {
                clippingTool.handleDown(new PointF(0.0f, 0.0f));
                clippingTool.setWasRecentlyApplied(true);
                clippingTool.resetInternalState(Tool.StateChange.NEW_IMAGE_LOADED);
                return;
            }
            return;
        }
        clippingTool.onClickOnButton();
    }

    private final void hidePlusIfShown(ToolType currentToolType) {
        TopBarViewHolder topBarViewHolder;
        ImageButton plusButton;
        if (currentToolType != ToolType.LINE || LineTool.INSTANCE.getTopBarViewHolder() == null || (topBarViewHolder = LineTool.INSTANCE.getTopBarViewHolder()) == null || (plusButton = topBarViewHolder.getPlusButton()) == null || plusButton.getVisibility() != 0) {
            return;
        }
        TopBarViewHolder topBarViewHolder2 = LineTool.INSTANCE.getTopBarViewHolder();
        ImageButton plusButton2 = topBarViewHolder2 == null ? null : topBarViewHolder2.getPlusButton();
        if (plusButton2 == null) {
            return;
        }
        plusButton2.setVisibility(8);
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public void disableToolOptionsView() {
        this.toolOptionsViewController.disable();
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public void enableToolOptionsView() {
        this.toolOptionsViewController.enable();
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public void enableHideOption() {
        this.toolOptionsViewController.enableHide();
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public void disableHideOption() {
        this.toolOptionsViewController.disableHide();
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public void createTool() {
        Tool tool = this.toolReference.getTool();
        if (tool == null) {
            this.toolReference.setTool(createAndSetupTool(ToolType.BRUSH));
        } else {
            Bundle bundle = new Bundle();
            Tool tool2 = this.toolReference.getTool();
            if (tool2 != null) {
                tool2.onSaveInstanceState(bundle);
            }
            this.toolReference.setTool(createAndSetupTool(tool.getToolType()));
            Tool tool3 = this.toolReference.getTool();
            if (tool3 != null) {
                tool3.onRestoreInstanceState(bundle);
            }
        }
        this.workspace.invalidate();
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public void toggleToolOptionsView() {
        if (this.toolOptionsViewController.getToolOptionsShown()) {
            this.toolOptionsViewController.hide();
        } else {
            this.toolOptionsViewController.show();
        }
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public boolean hasToolOptionsView() {
        ToolType toolType = getToolType();
        if (toolType == null) {
            return false;
        }
        return toolType.getHasOptions();
    }

    @Override // org.catrobat.paintroid.controller.ToolController
    public void setBitmapFromSource(Bitmap bitmap) {
        ImportTool importTool;
        if (bitmap == null || (importTool = (ImportTool) this.toolReference.getTool()) == null) {
            return;
        }
        importTool.setBitmapFromSource(bitmap);
    }
}
