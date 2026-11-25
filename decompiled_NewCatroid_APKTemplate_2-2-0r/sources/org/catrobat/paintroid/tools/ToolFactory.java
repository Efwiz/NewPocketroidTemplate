package org.catrobat.paintroid.tools;

import androidx.test.espresso.idling.CountingIdlingResource;
import kotlin.Metadata;
import org.catrobat.paintroid.colorpicker.OnColorPickedListener;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;

/* JADX INFO: compiled from: ToolFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001JH\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H&¨\u0006\u0014"}, d2 = {"Lorg/catrobat/paintroid/tools/ToolFactory;", "", "createTool", "Lorg/catrobat/paintroid/tools/Tool;", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "onColorPickedListener", "Lorg/catrobat/paintroid/colorpicker/OnColorPickedListener;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface ToolFactory {
    Tool createTool(ToolType toolType, ToolOptionsViewController toolOptionsViewController, CommandManager commandManager, Workspace workspace, CountingIdlingResource idlingResource, ToolPaint toolPaint, ContextCallback contextCallback, OnColorPickedListener onColorPickedListener);
}
