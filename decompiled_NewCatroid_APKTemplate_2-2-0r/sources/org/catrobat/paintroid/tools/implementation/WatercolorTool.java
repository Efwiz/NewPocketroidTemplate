package org.catrobat.paintroid.tools.implementation;

import android.graphics.BlurMaskFilter;
import android.graphics.Paint;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.test.espresso.idling.CountingIdlingResource;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.options.BrushToolOptionsView;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;

/* JADX INFO: compiled from: WatercolorTool.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dBE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001e"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/WatercolorTool;", "Lorg/catrobat/paintroid/tools/implementation/BrushTool;", "brushToolOptionsView", "Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "drawTime", "", "(Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView;Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;J)V", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "changePaintColor", "", TypedValues.Custom.S_COLOR, "", "invalidate", "", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class WatercolorTool extends BrushTool {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WatercolorTool(BrushToolOptionsView brushToolOptionsView, ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager, long j) {
        super(brushToolOptionsView, contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, j);
        Intrinsics.checkNotNullParameter(brushToolOptionsView, "brushToolOptionsView");
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        Paint bitmapPaint = getBitmapPaint();
        Companion companion = INSTANCE;
        bitmapPaint.setMaskFilter(new BlurMaskFilter(companion.calcRange(getBitmapPaint().getAlpha()), BlurMaskFilter.Blur.INNER));
        getPreviewPaint().setMaskFilter(new BlurMaskFilter(companion.calcRange(getPreviewPaint().getAlpha()), BlurMaskFilter.Blur.INNER));
    }

    @Override // org.catrobat.paintroid.tools.implementation.BrushTool, org.catrobat.paintroid.tools.Tool
    public ToolType getToolType() {
        return ToolType.WATERCOLOR;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BrushTool, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void changePaintColor(int color, boolean invalidate) {
        super.changePaintColor(color, invalidate);
        if (invalidate) {
            getBrushToolOptionsView().invalidate();
        }
        Paint bitmapPaint = getBitmapPaint();
        Companion companion = INSTANCE;
        bitmapPaint.setMaskFilter(new BlurMaskFilter(companion.calcRange(getBitmapPaint().getAlpha()), BlurMaskFilter.Blur.INNER));
        getPreviewPaint().setMaskFilter(new BlurMaskFilter(companion.calcRange(getPreviewPaint().getAlpha()), BlurMaskFilter.Blur.INNER));
    }

    /* JADX INFO: compiled from: WatercolorTool.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/WatercolorTool$Companion;", "", "()V", "calcRange", "", "value", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final float calcRange(int value) {
            return 170 - (((value * 130) / 255) + 20);
        }
    }
}
