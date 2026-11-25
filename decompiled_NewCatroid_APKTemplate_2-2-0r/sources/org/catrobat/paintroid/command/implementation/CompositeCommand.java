package org.catrobat.paintroid.command.implementation;

import android.graphics.Canvas;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: CompositeCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0001J\b\u0010\u000b\u001a\u00020\tH\u0016J\u0018\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R*\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00042\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/CompositeCommand;", "Lorg/catrobat/paintroid/command/Command;", "()V", "<set-?>", "", "commands", "getCommands", "()Ljava/util/List;", "addCommand", "", "command", "freeResources", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class CompositeCommand implements Command {
    private List<Command> commands = new ArrayList();

    public final List<Command> getCommands() {
        return this.commands;
    }

    public final void addCommand(Command command) {
        Intrinsics.checkNotNullParameter(command, "command");
        this.commands.add(command);
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        for (Command command : this.commands) {
            LayerContracts.Layer currentLayer = layerModel.getCurrentLayer();
            if (currentLayer != null) {
                canvas.setBitmap(currentLayer.getBitmap());
            }
            command.run(canvas, layerModel);
        }
    }

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
        Iterator<T> it = this.commands.iterator();
        while (it.hasNext()) {
            ((Command) it.next()).freeResources();
        }
    }
}
