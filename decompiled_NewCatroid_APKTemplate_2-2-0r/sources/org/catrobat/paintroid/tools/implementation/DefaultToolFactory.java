package org.catrobat.paintroid.tools.implementation;

import android.view.ViewGroup;
import androidx.test.espresso.idling.CountingIdlingResource;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.MainActivity;
import org.catrobat.paintroid.colorpicker.OnColorPickedListener;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.ToolFactory;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;
import org.catrobat.paintroid.ui.tools.DefaultBrushToolOptionsView;
import org.catrobat.paintroid.ui.tools.DefaultClipboardToolOptionsView;
import org.catrobat.paintroid.ui.tools.DefaultFillToolOptionsView;
import org.catrobat.paintroid.ui.tools.DefaultShapeToolOptionsView;
import org.catrobat.paintroid.ui.tools.DefaultSmudgeToolOptionsView;
import org.catrobat.paintroid.ui.tools.DefaultSprayToolOptionsView;
import org.catrobat.paintroid.ui.tools.DefaultTextToolOptionsView;
import org.catrobat.paintroid.ui.tools.DefaultTransformToolOptionsView;

/* JADX INFO: compiled from: DefaultToolFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004JH\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\u001a"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/DefaultToolFactory;", "Lorg/catrobat/paintroid/tools/ToolFactory;", "mainActivity", "Lorg/catrobat/paintroid/MainActivity;", "(Lorg/catrobat/paintroid/MainActivity;)V", "getMainActivity", "()Lorg/catrobat/paintroid/MainActivity;", "setMainActivity", "createTool", "Lorg/catrobat/paintroid/tools/Tool;", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "onColorPickedListener", "Lorg/catrobat/paintroid/colorpicker/OnColorPickedListener;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultToolFactory implements ToolFactory {
    private MainActivity mainActivity;

    /* JADX INFO: compiled from: DefaultToolFactory.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ToolType.values().length];
            iArr[ToolType.CURSOR.ordinal()] = 1;
            iArr[ToolType.CLIPBOARD.ordinal()] = 2;
            iArr[ToolType.IMPORTPNG.ordinal()] = 3;
            iArr[ToolType.PIPETTE.ordinal()] = 4;
            iArr[ToolType.FILL.ordinal()] = 5;
            iArr[ToolType.TRANSFORM.ordinal()] = 6;
            iArr[ToolType.SHAPE.ordinal()] = 7;
            iArr[ToolType.ERASER.ordinal()] = 8;
            iArr[ToolType.LINE.ordinal()] = 9;
            iArr[ToolType.TEXT.ordinal()] = 10;
            iArr[ToolType.HAND.ordinal()] = 11;
            iArr[ToolType.SPRAY.ordinal()] = 12;
            iArr[ToolType.WATERCOLOR.ordinal()] = 13;
            iArr[ToolType.SMUDGE.ordinal()] = 14;
            iArr[ToolType.CLIP.ordinal()] = 15;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DefaultToolFactory(MainActivity mainActivity) {
        Intrinsics.checkNotNullParameter(mainActivity, "mainActivity");
        this.mainActivity = mainActivity;
    }

    public final MainActivity getMainActivity() {
        return this.mainActivity;
    }

    public final void setMainActivity(MainActivity mainActivity) {
        Intrinsics.checkNotNullParameter(mainActivity, "<set-?>");
        this.mainActivity = mainActivity;
    }

    @Override // org.catrobat.paintroid.tools.ToolFactory
    public Tool createTool(ToolType toolType, ToolOptionsViewController toolOptionsViewController, CommandManager commandManager, Workspace workspace, CountingIdlingResource idlingResource, ToolPaint toolPaint, ContextCallback contextCallback, OnColorPickedListener onColorPickedListener) {
        Intrinsics.checkNotNullParameter(toolType, "toolType");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(onColorPickedListener, "onColorPickedListener");
        ViewGroup toolSpecificOptionsLayout = toolOptionsViewController.getToolSpecificOptionsLayout();
        switch (WhenMappings.$EnumSwitchMapping$0[toolType.ordinal()]) {
            case 1:
                return new CursorTool(new DefaultBrushToolOptionsView(toolSpecificOptionsLayout), contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, 30000000L);
            case 2:
                return new ClipboardTool(new DefaultClipboardToolOptionsView(toolSpecificOptionsLayout), contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, 30000000L);
            case 3:
                return new ImportTool(contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, 30000000L);
            case 4:
                return new PipetteTool(contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, onColorPickedListener);
            case 5:
                return new FillTool(new DefaultFillToolOptionsView(toolSpecificOptionsLayout), contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, 30000000L);
            case 6:
                return new TransformTool(new DefaultTransformToolOptionsView(toolSpecificOptionsLayout), contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, 30000000L);
            case 7:
                return new ShapeTool(new DefaultShapeToolOptionsView(toolSpecificOptionsLayout), contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, 30000000L);
            case 8:
                return new EraserTool(new DefaultBrushToolOptionsView(toolSpecificOptionsLayout), contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, this.mainActivity.getBottomNavigationViewHolder(), 30000000L);
            case 9:
                return new LineTool(new DefaultBrushToolOptionsView(toolSpecificOptionsLayout), contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, 30000000L);
            case 10:
                return new TextTool(new DefaultTextToolOptionsView(toolSpecificOptionsLayout), contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, 30000000L);
            case 11:
                return new HandTool(contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, 30000000L);
            case 12:
                return new SprayTool(new DefaultSprayToolOptionsView(toolSpecificOptionsLayout), contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, 30000000L);
            case 13:
                return new WatercolorTool(new DefaultBrushToolOptionsView(toolSpecificOptionsLayout), contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, 30000000L);
            case 14:
                return new SmudgeTool(new DefaultSmudgeToolOptionsView(toolSpecificOptionsLayout), contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager);
            case 15:
                return new ClippingTool(new DefaultBrushToolOptionsView(toolSpecificOptionsLayout), contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, 30000000L, this.mainActivity);
            default:
                return new BrushTool(new DefaultBrushToolOptionsView(toolSpecificOptionsLayout), contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager, 30000000L);
        }
    }
}
