package org.catrobat.paintroid.command.implementation;

import android.graphics.Canvas;
import androidx.core.view.ViewCompat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.common.CommonFactory;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.model.CommandManagerModel;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: DefaultCommandManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u001b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0019H\u0002J\u0012\u0010\u001e\u001a\u00020\u001c2\b\u0010\u001f\u001a\u0004\u0018\u00010\u000fH\u0016J\u0010\u0010 \u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\tH\u0016J\u0012\u0010\"\u001a\u00020\u001c2\b\u0010\u001f\u001a\u0004\u0018\u00010\u000fH\u0016J\u0016\u0010#\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0019H\u0002J\b\u0010$\u001a\u00020\u001cH\u0016J\u001e\u0010%\u001a\u00020\u00112\u0006\u0010&\u001a\u00020'2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00110\bH\u0002J\b\u0010)\u001a\u00020\u001cH\u0016J\u0012\u0010*\u001a\u00020\u001c2\b\u0010\u001f\u001a\u0004\u0018\u00010\u000fH\u0002J\u001e\u0010+\u001a\u00020\u001c2\u0006\u0010&\u001a\u00020'2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00110,H\u0002J\b\u0010-\u001a\u00020'H\u0016J\n\u0010.\u001a\u0004\u0018\u00010\u000bH\u0016J\b\u0010/\u001a\u00020'H\u0016J\u001a\u00100\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u000f2\b\b\u0002\u00101\u001a\u00020\u0011H\u0002J\b\u00102\u001a\u00020\u0011H\u0016J\u0012\u00103\u001a\u00020\u001c2\b\u00104\u001a\u0004\u0018\u00010\u000bH\u0016J\b\u00105\u001a\u00020\u001cH\u0002J\b\u00106\u001a\u00020\u001cH\u0016J\b\u00107\u001a\u00020\u001cH\u0016J\u0016\u00108\u001a\u00020\u001c2\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0019H\u0002J\b\u0010:\u001a\u00020\u001cH\u0016J\b\u0010;\u001a\u00020\u001cH\u0016J\u000e\u0010<\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0019H\u0002J\u0010\u0010=\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\tH\u0016J\b\u0010>\u001a\u00020\u001cH\u0016J\u000e\u0010?\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0019H\u0002J\u0010\u0010@\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u000fH\u0016J\b\u0010A\u001a\u00020\u001cH\u0016J\b\u0010B\u001a\u00020\u001cH\u0016J\b\u0010C\u001a\u00020\u001cH\u0016J\u0010\u0010D\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u000fH\u0016J\b\u0010E\u001a\u00020\u001cH\u0016J\b\u0010F\u001a\u00020\u001cH\u0016R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\u0004\u0018\u00010\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0012R\u0014\u0010\u0014\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0012R\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006G"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/DefaultCommandManager;", "Lorg/catrobat/paintroid/command/CommandManager;", "commonFactory", "Lorg/catrobat/paintroid/common/CommonFactory;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "(Lorg/catrobat/paintroid/common/CommonFactory;Lorg/catrobat/paintroid/contract/LayerContracts$Model;)V", "commandListeners", "", "Lorg/catrobat/paintroid/command/CommandManager$CommandListener;", "commandManagerModel", "Lorg/catrobat/paintroid/model/CommandManagerModel;", "getCommandManagerModel", "()Lorg/catrobat/paintroid/model/CommandManagerModel;", "initialStateCommand", "Lorg/catrobat/paintroid/command/Command;", "isBusy", "", "()Z", "isRedoAvailable", "isUndoAvailable", "lastExecutedCommand", "getLastExecutedCommand", "()Lorg/catrobat/paintroid/command/Command;", "redoCommandList", "Ljava/util/Deque;", "undoCommandList", "addAndExecuteCommands", "", "commands", "addCommand", "command", "addCommandListener", "commandListener", "addCommandWithoutUndo", "addCommands", "adjustUndoListForClippingTool", "backUpCheckBoxes", "layerCount", "", "checkBoxes", "executeAllCommands", "executeCommand", "fetchBackCheckBoxes", "", "getColorCommandCount", "getCommandManagerModelForCatrobatImage", "getUndoCommandCount", "handleUndo", "ignoreColorCommand", "isLastColorCommandOnTop", "loadCommandsCatrobatImage", "model", "notifyCommandExecuted", "popFirstCommandInRedo", "popFirstCommandInUndo", "recolorLastLine", "colorCommandList", LineReader.REDO, "redoInConnectedLinesMode", "removeColorCommands", "removeCommandListener", "reset", "separateColorCommandsAndUndo", "setInitialStateCommand", "shutdown", LineReader.UNDO, "undoIgnoringColorChanges", "undoIgnoringColorChangesAndAddCommand", "undoInClippingTool", "undoInConnectedLinesMode", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultCommandManager implements CommandManager {
    private final List<CommandManager.CommandListener> commandListeners;
    private final CommonFactory commonFactory;
    private Command initialStateCommand;
    private final LayerContracts.Model layerModel;
    private final Deque<Command> redoCommandList;
    private final Deque<Command> undoCommandList;

    @Override // org.catrobat.paintroid.command.CommandManager
    public boolean isBusy() {
        return false;
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void shutdown() {
    }

    public DefaultCommandManager(CommonFactory commonFactory, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(commonFactory, "commonFactory");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        this.commonFactory = commonFactory;
        this.layerModel = layerModel;
        this.commandListeners = new ArrayList();
        this.redoCommandList = new ArrayDeque();
        this.undoCommandList = new ArrayDeque();
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public Command getLastExecutedCommand() {
        return (Command) CollectionsKt.firstOrNull(this.undoCommandList);
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public boolean isUndoAvailable() {
        return !this.undoCommandList.isEmpty();
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public boolean isRedoAvailable() {
        return !this.redoCommandList.isEmpty();
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public CommandManagerModel getCommandManagerModel() {
        ArrayList arrayList = new ArrayList();
        Iterator<Command> itDescendingIterator = this.redoCommandList.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            arrayList.add(itDescendingIterator.next());
        }
        arrayList.addAll(this.undoCommandList);
        Command command = this.initialStateCommand;
        if (command == null) {
            return null;
        }
        return new CommandManagerModel(command, arrayList);
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void addCommandListener(CommandManager.CommandListener commandListener) {
        Intrinsics.checkNotNullParameter(commandListener, "commandListener");
        this.commandListeners.add(commandListener);
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void removeCommandListener(CommandManager.CommandListener commandListener) {
        Intrinsics.checkNotNullParameter(commandListener, "commandListener");
        this.commandListeners.remove(commandListener);
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void addCommand(Command command) {
        this.redoCommandList.clear();
        if (command != null) {
            this.undoCommandList.addFirst(command);
        }
        executeCommand(command);
        notifyCommandExecuted();
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void addCommandWithoutUndo(Command command) {
        this.redoCommandList.clear();
        executeCommand(command);
        notifyCommandExecuted();
    }

    private final void executeCommand(Command command) {
        LayerContracts.Layer currentLayer = this.layerModel.getCurrentLayer();
        Canvas canvasCreateCanvas = this.commonFactory.createCanvas();
        canvasCreateCanvas.setBitmap(currentLayer == null ? null : currentLayer.getBitmap());
        if (command == null) {
            return;
        }
        command.run(canvasCreateCanvas, this.layerModel);
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void loadCommandsCatrobatImage(CommandManagerModel model) {
        if (model == null) {
            return;
        }
        setInitialStateCommand(model.getInitialCommand());
        reset();
        Iterator<Command> it = model.getCommands().iterator();
        while (it.hasNext()) {
            addCommand(it.next());
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void undo() {
        if (isUndoAvailable()) {
            Command command = this.undoCommandList.pop();
            this.redoCommandList.addFirst(command);
            Intrinsics.checkNotNullExpressionValue(command, "command");
            handleUndo$default(this, command, false, 2, null);
            notifyCommandExecuted();
        }
    }

    static /* synthetic */ void handleUndo$default(DefaultCommandManager defaultCommandManager, Command command, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        defaultCommandManager.handleUndo(command, z);
    }

    private final void handleUndo(Command command, boolean ignoreColorCommand) {
        boolean zRemoveLayerAt;
        LayerContracts.Layer layerAt;
        int layerCount = this.layerModel.getLayerCount();
        String currentCommandName = command.getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue("AddEmptyLayerCommand", "AddEmptyLayerCommand::class.java.simpleName");
        Regex regex = new Regex("AddEmptyLayerCommand");
        Intrinsics.checkNotNullExpressionValue("MergeLayersCommand", "MergeLayersCommand::class.java.simpleName");
        Regex regex2 = new Regex("MergeLayersCommand");
        Intrinsics.checkNotNullExpressionValue(currentCommandName, "currentCommandName");
        String str = currentCommandName;
        int i = 0;
        if (regex.matches(str)) {
            layerCount--;
            layerAt = this.layerModel.getLayerAt(0);
            zRemoveLayerAt = this.layerModel.removeLayerAt(0);
        } else {
            zRemoveLayerAt = true;
            layerAt = null;
        }
        ArrayList arrayList = new ArrayList(Collections.nCopies(layerCount, true));
        if (!regex2.matches(str) && !backUpCheckBoxes(layerCount, arrayList)) {
            zRemoveLayerAt = false;
        }
        if (!zRemoveLayerAt) {
            if (layerAt != null && this.layerModel.getLayerIndexOf(layerAt) == -1) {
                this.layerModel.addLayerAt(0, layerAt);
            }
            if (isRedoAvailable()) {
                this.undoCommandList.addFirst(this.redoCommandList.pop());
                return;
            }
            return;
        }
        this.layerModel.reset();
        Canvas canvasCreateCanvas = this.commonFactory.createCanvas();
        Command command2 = this.initialStateCommand;
        if (command2 != null) {
            command2.run(canvasCreateCanvas, this.layerModel);
        }
        int colorCommandCount = getColorCommandCount();
        Iterator<Command> itDescendingIterator = this.undoCommandList.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            Command next = itDescendingIterator.next();
            boolean z = next instanceof ColorChangedCommand;
            if (!z || !ignoreColorCommand) {
                LayerContracts.Layer currentLayer = this.layerModel.getCurrentLayer();
                canvasCreateCanvas.setBitmap(currentLayer == null ? null : currentLayer.getBitmap());
                if (z && (i = i + 1) < colorCommandCount) {
                    ((ColorChangedCommand) next).runInUndoMode();
                } else {
                    next.run(canvasCreateCanvas, this.layerModel);
                }
            }
        }
        if (regex2.matches(str)) {
            return;
        }
        fetchBackCheckBoxes(layerCount, arrayList);
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public int getColorCommandCount() {
        Iterator<Command> it = this.undoCommandList.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "undoCommandList.iterator()");
        int i = 0;
        while (it.hasNext()) {
            if (it.next() instanceof ColorChangedCommand) {
                i++;
            }
        }
        return i;
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public boolean isLastColorCommandOnTop() {
        if (!(this.undoCommandList.getFirst() instanceof ColorChangedCommand)) {
            return false;
        }
        Iterator<Command> it = this.undoCommandList.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "undoCommandList.iterator()");
        int i = 0;
        while (it.hasNext()) {
            if (it.next() instanceof ColorChangedCommand) {
                i++;
            }
        }
        return i == 1;
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void executeAllCommands() {
        int layerCount = this.layerModel.getLayerCount();
        ArrayList arrayList = new ArrayList(Collections.nCopies(layerCount, true));
        backUpCheckBoxes(layerCount, arrayList);
        this.layerModel.reset();
        Canvas canvasCreateCanvas = this.commonFactory.createCanvas();
        Command command = this.initialStateCommand;
        if (command != null) {
            command.run(canvasCreateCanvas, this.layerModel);
        }
        Iterator<Command> itDescendingIterator = this.undoCommandList.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            LayerContracts.Layer currentLayer = this.layerModel.getCurrentLayer();
            canvasCreateCanvas.setBitmap(currentLayer == null ? null : currentLayer.getBitmap());
            itDescendingIterator.next().run(canvasCreateCanvas, this.layerModel);
        }
        fetchBackCheckBoxes(layerCount, arrayList);
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void redo() {
        if (isRedoAvailable()) {
            Command commandPop = this.redoCommandList.pop();
            this.undoCommandList.addFirst(commandPop);
            LayerContracts.Layer currentLayer = this.layerModel.getCurrentLayer();
            Canvas canvasCreateCanvas = this.commonFactory.createCanvas();
            if (currentLayer != null) {
                canvasCreateCanvas.setBitmap(currentLayer.getBitmap());
            }
            commandPop.run(canvasCreateCanvas, this.layerModel);
            notifyCommandExecuted();
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void reset() {
        this.undoCommandList.clear();
        this.redoCommandList.clear();
        this.layerModel.reset();
        if (this.initialStateCommand != null) {
            Canvas canvasCreateCanvas = this.commonFactory.createCanvas();
            Command command = this.initialStateCommand;
            if (command != null) {
                command.run(canvasCreateCanvas, this.layerModel);
            }
        }
        notifyCommandExecuted();
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void undoIgnoringColorChanges() {
        addAndExecuteCommands(separateColorCommandsAndUndo());
        notifyCommandExecuted();
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void undoIgnoringColorChangesAndAddCommand(Command command) {
        Intrinsics.checkNotNullParameter(command, "command");
        Deque<Command> dequeSeparateColorCommandsAndUndo = separateColorCommandsAndUndo();
        this.undoCommandList.addFirst(command);
        this.redoCommandList.clear();
        executeCommand(command);
        addAndExecuteCommands(dequeSeparateColorCommandsAndUndo);
        notifyCommandExecuted();
    }

    private final Deque<Command> separateColorCommandsAndUndo() {
        Deque<Command> dequeRemoveColorCommands = removeColorCommands();
        if (!this.undoCommandList.isEmpty() && this.undoCommandList.getFirst() != null) {
            Command command = this.undoCommandList.pop();
            this.redoCommandList.addFirst(command);
            Intrinsics.checkNotNullExpressionValue(command, "command");
            handleUndo(command, true);
        }
        return dequeRemoveColorCommands;
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void undoInConnectedLinesMode() {
        Command command;
        Deque<Command> dequeRemoveColorCommands = removeColorCommands();
        if (!this.undoCommandList.isEmpty()) {
            if (!dequeRemoveColorCommands.isEmpty()) {
                Command commandPop = dequeRemoveColorCommands.pop();
                Intrinsics.checkNotNullExpressionValue(commandPop, "{\n                colorC…dList.pop()\n            }");
                command = commandPop;
            } else {
                Command commandPop2 = this.undoCommandList.pop();
                Intrinsics.checkNotNullExpressionValue(commandPop2, "{\n                undoCo…dList.pop()\n            }");
                command = commandPop2;
            }
            this.redoCommandList.addFirst(command);
            handleUndo$default(this, command, false, 2, null);
            recolorLastLine(dequeRemoveColorCommands);
            return;
        }
        if (!dequeRemoveColorCommands.isEmpty()) {
            addCommands(dequeRemoveColorCommands);
        }
        if (this.undoCommandList.isEmpty()) {
            return;
        }
        undo();
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0068 A[EDGE_INSN: B:19:0x0068->B:20:0x006a BREAK  A[LOOP:0: B:14:0x0051->B:30:?]] */
    private final void recolorLastLine(Deque<Command> colorCommandList) {
        int color;
        if (!this.undoCommandList.isEmpty() && !(this.undoCommandList.getFirst() instanceof ColorChangedCommand)) {
            Command commandPop = this.undoCommandList.pop();
            if (!colorCommandList.isEmpty()) {
                Command first = colorCommandList.getFirst();
                Intrinsics.checkNotNullExpressionValue(first, "colorCommandList.first");
                Command command = first;
                if (!(command instanceof ColorChangedCommand)) {
                    color = ViewCompat.MEASURED_STATE_MASK;
                    break;
                }
                color = ((ColorChangedCommand) command).getColor();
            } else if (!this.undoCommandList.isEmpty()) {
                Iterator<Command> it = this.undoCommandList.iterator();
                Intrinsics.checkNotNullExpressionValue(it, "undoCommandList.iterator()");
                while (true) {
                    if (!it.hasNext()) {
                        color = ViewCompat.MEASURED_STATE_MASK;
                        break;
                    }
                    Command next = it.next();
                    if (next instanceof ColorChangedCommand) {
                        color = ((ColorChangedCommand) next).getColor();
                        break;
                    }
                }
            } else {
                color = ViewCompat.MEASURED_STATE_MASK;
                break;
            }
            if (commandPop instanceof PathCommand) {
                handleUndo$default(this, commandPop, false, 2, null);
                ((PathCommand) commandPop).getPaint().setColor(color);
                this.undoCommandList.addFirst(commandPop);
                executeCommand(commandPop);
            } else if (commandPop instanceof PointCommand) {
                handleUndo$default(this, commandPop, false, 2, null);
                ((PointCommand) commandPop).getPaint().setColor(color);
                this.undoCommandList.addFirst(commandPop);
                executeCommand(commandPop);
            }
        }
        addAndExecuteCommands(colorCommandList);
        notifyCommandExecuted();
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void redoInConnectedLinesMode() {
        if (isRedoAvailable()) {
            Command commandPop = this.redoCommandList.pop();
            if (commandPop instanceof ColorChangedCommand) {
                Deque<Command> dequeRemoveColorCommands = removeColorCommands();
                if (!this.undoCommandList.isEmpty()) {
                    Command first = this.undoCommandList.getFirst();
                    int color = ((ColorChangedCommand) commandPop).getColor();
                    if (first instanceof PathCommand) {
                        ((PathCommand) first).getPaint().setColor(color);
                    } else if (first instanceof PointCommand) {
                        ((PointCommand) first).getPaint().setColor(color);
                    }
                    executeAllCommands();
                }
                addAndExecuteCommands(dequeRemoveColorCommands);
            }
            this.undoCommandList.addFirst(commandPop);
            executeCommand(commandPop);
            notifyCommandExecuted();
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public CommandManagerModel getCommandManagerModelForCatrobatImage() {
        CommandManagerModel commandManagerModel = getCommandManagerModel();
        if (commandManagerModel == null) {
            return null;
        }
        Command initialCommand = commandManagerModel.getInitialCommand();
        List<Command> commands = commandManagerModel.getCommands();
        ArrayList arrayList = new ArrayList();
        for (Object obj : commands) {
            if (!(((Command) obj) instanceof ColorChangedCommand)) {
                arrayList.add(obj);
            }
        }
        return new CommandManagerModel(initialCommand, CollectionsKt.toMutableList((Collection) arrayList));
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void adjustUndoListForClippingTool() {
        if (isUndoAvailable() && StringsKt.split$default((CharSequence) this.undoCommandList.getFirst().toString(), new String[]{".", PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT}, false, 0, 6, (Object) null).size() >= 5 && Intrinsics.areEqual((String) StringsKt.split$default((CharSequence) this.undoCommandList.getFirst().toString(), new String[]{".", PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT}, false, 0, 6, (Object) null).get(5), "ClippingCommand")) {
            Command commandPop = this.undoCommandList.pop();
            this.undoCommandList.pop();
            this.undoCommandList.addFirst(commandPop);
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void undoInClippingTool() {
        if (isUndoAvailable()) {
            Command command = this.undoCommandList.pop();
            Intrinsics.checkNotNullExpressionValue(command, "command");
            handleUndo$default(this, command, false, 2, null);
            notifyCommandExecuted();
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void popFirstCommandInUndo() {
        if (isUndoAvailable()) {
            this.undoCommandList.pop();
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void popFirstCommandInRedo() {
        if (isRedoAvailable()) {
            this.redoCommandList.pop();
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void setInitialStateCommand(Command command) {
        Intrinsics.checkNotNullParameter(command, "command");
        this.initialStateCommand = command;
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public int getUndoCommandCount() {
        return this.undoCommandList.size();
    }

    private final void notifyCommandExecuted() {
        Iterator<CommandManager.CommandListener> it = this.commandListeners.iterator();
        while (it.hasNext()) {
            it.next().commandPostExecute();
        }
    }

    private final boolean backUpCheckBoxes(int layerCount, List<Boolean> checkBoxes) {
        Unit unit;
        Unit unit2 = null;
        boolean z = true;
        if (layerCount > 1) {
            int i = layerCount - 1;
            if (i >= 0) {
                while (true) {
                    int i2 = i - 1;
                    LayerContracts.Layer layerAt = this.layerModel.getLayerAt(i);
                    if (layerAt == null) {
                        unit = null;
                    } else {
                        checkBoxes.set(i, Boolean.valueOf(layerAt.getIsVisible()));
                        unit = Unit.INSTANCE;
                    }
                    if (unit == null) {
                        z = false;
                    }
                    if (i2 < 0) {
                        break;
                    }
                    i = i2;
                }
            }
        } else {
            LayerContracts.Layer layerAt2 = this.layerModel.getLayerAt(0);
            if (layerAt2 != null) {
                checkBoxes.set(0, Boolean.valueOf(layerAt2.getIsVisible()));
                unit2 = Unit.INSTANCE;
            }
            if (unit2 == null) {
                return false;
            }
        }
        return z;
    }

    private final void fetchBackCheckBoxes(int layerCount, List<Boolean> checkBoxes) {
        if (layerCount <= 1) {
            LayerContracts.Layer currentLayer = this.layerModel.getCurrentLayer();
            if (currentLayer == null || checkBoxes.get(0).booleanValue()) {
                return;
            }
            currentLayer.setVisible(false);
            return;
        }
        int i = layerCount - 1;
        if (i < 0) {
            return;
        }
        while (true) {
            int i2 = i - 1;
            LayerContracts.Layer layerAt = this.layerModel.getLayerAt(i);
            if (!checkBoxes.get(i).booleanValue() && layerAt != null) {
                layerAt.setVisible(false);
            }
            if (i2 < 0) {
                return;
            } else {
                i = i2;
            }
        }
    }

    private final Deque<Command> removeColorCommands() {
        ArrayDeque arrayDeque = new ArrayDeque();
        while (!this.undoCommandList.isEmpty() && (this.undoCommandList.getFirst() instanceof ColorChangedCommand)) {
            arrayDeque.addLast(this.undoCommandList.pop());
        }
        return arrayDeque;
    }

    private final void addAndExecuteCommands(Deque<Command> commands) {
        Iterator<Command> itDescendingIterator = commands.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            Command next = itDescendingIterator.next();
            this.undoCommandList.addFirst(next);
            executeCommand(next);
        }
    }

    private final void addCommands(Deque<Command> commands) {
        Iterator<Command> itDescendingIterator = commands.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            this.undoCommandList.addFirst(itDescendingIterator.next());
        }
    }
}
