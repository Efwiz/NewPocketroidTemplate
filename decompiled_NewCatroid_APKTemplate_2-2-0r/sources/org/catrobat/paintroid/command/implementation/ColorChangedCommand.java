package org.catrobat.paintroid.command.implementation;

import android.content.Context;
import android.graphics.Canvas;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.MainActivity;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.ToolReference;
import org.catrobat.paintroid.tools.implementation.EraserTool;
import org.catrobat.paintroid.tools.implementation.LineTool;

/* JADX INFO: compiled from: ColorChangedCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0006\u0010\u001d\u001a\u00020\u0017R\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u001e"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/ColorChangedCommand;", "Lorg/catrobat/paintroid/command/Command;", "toolReference", "Lorg/catrobat/paintroid/tools/ToolReference;", "context", "Landroid/content/Context;", TypedValues.Custom.S_COLOR, "", "(Lorg/catrobat/paintroid/tools/ToolReference;Landroid/content/Context;I)V", "<set-?>", "getColor", "()I", "getContext", "()Landroid/content/Context;", "firstTime", "", "getFirstTime", "()Z", "setFirstTime", "(Z)V", "getToolReference", "()Lorg/catrobat/paintroid/tools/ToolReference;", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "runInUndoMode", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ColorChangedCommand implements Command {
    private int color;
    private Context context;
    private boolean firstTime;
    private ToolReference toolReference;

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
    }

    public ColorChangedCommand(ToolReference toolReference, Context context, int i) {
        Intrinsics.checkNotNullParameter(toolReference, "toolReference");
        Intrinsics.checkNotNullParameter(context, "context");
        this.toolReference = toolReference;
        this.context = context;
        this.color = i;
        this.firstTime = true;
    }

    public final ToolReference getToolReference() {
        return this.toolReference;
    }

    public final Context getContext() {
        return this.context;
    }

    public final int getColor() {
        return this.color;
    }

    public final boolean getFirstTime() {
        return this.firstTime;
    }

    public final void setFirstTime(boolean z) {
        this.firstTime = z;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        if (!(this.toolReference.getTool() instanceof LineTool)) {
            ((MainActivity) this.context).runOnUiThread(new Runnable() { // from class: org.catrobat.paintroid.command.implementation.ColorChangedCommand$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    ColorChangedCommand.m5916run$lambda0(this.f$0);
                }
            });
        } else if ((this.toolReference.getTool() instanceof LineTool) && !this.firstTime) {
            ((MainActivity) this.context).runOnUiThread(new Runnable() { // from class: org.catrobat.paintroid.command.implementation.ColorChangedCommand$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    ColorChangedCommand.m5917run$lambda1(this.f$0);
                }
            });
        } else {
            ((MainActivity) this.context).runOnUiThread(new Runnable() { // from class: org.catrobat.paintroid.command.implementation.ColorChangedCommand$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    ColorChangedCommand.m5918run$lambda2(this.f$0);
                }
            });
            this.firstTime = false;
        }
        if (this.toolReference.getTool() instanceof EraserTool) {
            return;
        }
        ((MainActivity) this.context).runOnUiThread(new Runnable() { // from class: org.catrobat.paintroid.command.implementation.ColorChangedCommand$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ColorChangedCommand.m5919run$lambda3(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: run$lambda-0, reason: not valid java name */
    public static final void m5916run$lambda0(ColorChangedCommand this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Tool tool = this$0.getToolReference().getTool();
        if (tool == null) {
            return;
        }
        Tool.DefaultImpls.changePaintColor$default(tool, this$0.getColor(), false, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: run$lambda-1, reason: not valid java name */
    public static final void m5917run$lambda1(ColorChangedCommand this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Tool tool = this$0.getToolReference().getTool();
        if (tool == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.LineTool");
        }
        LineTool.undoColorChangedCommand$default((LineTool) tool, this$0.getColor(), false, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: run$lambda-2, reason: not valid java name */
    public static final void m5918run$lambda2(ColorChangedCommand this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Tool tool = this$0.getToolReference().getTool();
        if (tool == null) {
            return;
        }
        Tool.DefaultImpls.changePaintColor$default(tool, this$0.getColor(), false, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: run$lambda-3, reason: not valid java name */
    public static final void m5919run$lambda3(ColorChangedCommand this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ((MainActivity) this$0.getContext()).getBottomNavigationViewHolder().setColorButtonColor(this$0.getColor());
    }

    public final void runInUndoMode() {
        if (!(this.toolReference.getTool() instanceof LineTool)) {
            ((MainActivity) this.context).runOnUiThread(new Runnable() { // from class: org.catrobat.paintroid.command.implementation.ColorChangedCommand$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ColorChangedCommand.m5920runInUndoMode$lambda4(this.f$0);
                }
            });
        } else if ((this.toolReference.getTool() instanceof LineTool) && !this.firstTime) {
            ((MainActivity) this.context).runOnUiThread(new Runnable() { // from class: org.catrobat.paintroid.command.implementation.ColorChangedCommand$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ColorChangedCommand.m5921runInUndoMode$lambda5(this.f$0);
                }
            });
        } else {
            ((MainActivity) this.context).runOnUiThread(new Runnable() { // from class: org.catrobat.paintroid.command.implementation.ColorChangedCommand$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ColorChangedCommand.m5922runInUndoMode$lambda6(this.f$0);
                }
            });
            this.firstTime = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: runInUndoMode$lambda-4, reason: not valid java name */
    public static final void m5920runInUndoMode$lambda4(ColorChangedCommand this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Tool tool = this$0.getToolReference().getTool();
        if (tool == null) {
            return;
        }
        tool.changePaintColor(this$0.getColor(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: runInUndoMode$lambda-5, reason: not valid java name */
    public static final void m5921runInUndoMode$lambda5(ColorChangedCommand this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Tool tool = this$0.getToolReference().getTool();
        if (tool == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.LineTool");
        }
        ((LineTool) tool).undoColorChangedCommand(this$0.getColor(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: runInUndoMode$lambda-6, reason: not valid java name */
    public static final void m5922runInUndoMode$lambda6(ColorChangedCommand this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Tool tool = this$0.getToolReference().getTool();
        if (tool == null) {
            return;
        }
        tool.changePaintColor(this$0.getColor(), false);
    }
}
