package org.catrobat.paintroid;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import kotlin.Metadata;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolReference;
import org.catrobat.paintroid.ui.Perspective;

/* JADX INFO: compiled from: PaintroidApplicationFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 ¨\u0006%"}, d2 = {"Lorg/catrobat/paintroid/PaintroidApplicationFragment;", "Landroidx/fragment/app/Fragment;", "()V", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "getCommandManager", "()Lorg/catrobat/paintroid/command/CommandManager;", "setCommandManager", "(Lorg/catrobat/paintroid/command/CommandManager;)V", "currentTool", "Lorg/catrobat/paintroid/tools/ToolReference;", "getCurrentTool", "()Lorg/catrobat/paintroid/tools/ToolReference;", "setCurrentTool", "(Lorg/catrobat/paintroid/tools/ToolReference;)V", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "getLayerModel", "()Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "setLayerModel", "(Lorg/catrobat/paintroid/contract/LayerContracts$Model;)V", "perspective", "Lorg/catrobat/paintroid/ui/Perspective;", "getPerspective", "()Lorg/catrobat/paintroid/ui/Perspective;", "setPerspective", "(Lorg/catrobat/paintroid/ui/Perspective;)V", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "getToolPaint", "()Lorg/catrobat/paintroid/tools/ToolPaint;", "setToolPaint", "(Lorg/catrobat/paintroid/tools/ToolPaint;)V", "onCreate", "", "savedInstance", "Landroid/os/Bundle;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class PaintroidApplicationFragment extends Fragment {
    private CommandManager commandManager;
    private ToolReference currentTool;
    private LayerContracts.Model layerModel;
    private Perspective perspective;
    private ToolPaint toolPaint;

    public final CommandManager getCommandManager() {
        return this.commandManager;
    }

    public final void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public final ToolReference getCurrentTool() {
        return this.currentTool;
    }

    public final void setCurrentTool(ToolReference toolReference) {
        this.currentTool = toolReference;
    }

    public final Perspective getPerspective() {
        return this.perspective;
    }

    public final void setPerspective(Perspective perspective) {
        this.perspective = perspective;
    }

    public final LayerContracts.Model getLayerModel() {
        return this.layerModel;
    }

    public final void setLayerModel(LayerContracts.Model model) {
        this.layerModel = model;
    }

    public final ToolPaint getToolPaint() {
        return this.toolPaint;
    }

    public final void setToolPaint(ToolPaint toolPaint) {
        this.toolPaint = toolPaint;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setRetainInstance(true);
    }
}
