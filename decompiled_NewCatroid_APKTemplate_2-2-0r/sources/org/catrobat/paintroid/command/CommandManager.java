package org.catrobat.paintroid.command;

import kotlin.Metadata;
import org.catrobat.paintroid.model.CommandManagerModel;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: CommandManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0014\bf\u0018\u00002\u00020\u0001:\u0001,J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\fH&J\u0010\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0012\u0010\u0015\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\fH&J\b\u0010\u0016\u001a\u00020\u0010H&J\b\u0010\u0017\u001a\u00020\u0010H&J\b\u0010\u0018\u001a\u00020\u0019H&J\n\u0010\u001a\u001a\u0004\u0018\u00010\u0003H&J\b\u0010\u001b\u001a\u00020\u0019H&J\b\u0010\u001c\u001a\u00020\u0007H&J\u0012\u0010\u001d\u001a\u00020\u00102\b\u0010\u001e\u001a\u0004\u0018\u00010\u0003H&J\b\u0010\u001f\u001a\u00020\u0010H&J\b\u0010 \u001a\u00020\u0010H&J\b\u0010!\u001a\u00020\u0010H&J\b\u0010\"\u001a\u00020\u0010H&J\u0010\u0010#\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0014H&J\b\u0010$\u001a\u00020\u0010H&J\u0010\u0010%\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fH&J\b\u0010&\u001a\u00020\u0010H&J\b\u0010'\u001a\u00020\u0010H&J\b\u0010(\u001a\u00020\u0010H&J\u0010\u0010)\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fH&J\b\u0010*\u001a\u00020\u0010H&J\b\u0010+\u001a\u00020\u0010H&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\bR\u0012\u0010\t\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\bR\u0012\u0010\n\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u0004\u0018\u00010\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006-"}, d2 = {"Lorg/catrobat/paintroid/command/CommandManager;", "", "commandManagerModel", "Lorg/catrobat/paintroid/model/CommandManagerModel;", "getCommandManagerModel", "()Lorg/catrobat/paintroid/model/CommandManagerModel;", "isBusy", "", "()Z", "isRedoAvailable", "isUndoAvailable", "lastExecutedCommand", "Lorg/catrobat/paintroid/command/Command;", "getLastExecutedCommand", "()Lorg/catrobat/paintroid/command/Command;", "addCommand", "", "command", "addCommandListener", "commandListener", "Lorg/catrobat/paintroid/command/CommandManager$CommandListener;", "addCommandWithoutUndo", "adjustUndoListForClippingTool", "executeAllCommands", "getColorCommandCount", "", "getCommandManagerModelForCatrobatImage", "getUndoCommandCount", "isLastColorCommandOnTop", "loadCommandsCatrobatImage", "model", "popFirstCommandInRedo", "popFirstCommandInUndo", LineReader.REDO, "redoInConnectedLinesMode", "removeCommandListener", "reset", "setInitialStateCommand", "shutdown", LineReader.UNDO, "undoIgnoringColorChanges", "undoIgnoringColorChangesAndAddCommand", "undoInClippingTool", "undoInConnectedLinesMode", "CommandListener", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface CommandManager {

    /* JADX INFO: compiled from: CommandManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lorg/catrobat/paintroid/command/CommandManager$CommandListener;", "", "commandPostExecute", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface CommandListener {
        void commandPostExecute();
    }

    void addCommand(Command command);

    void addCommandListener(CommandListener commandListener);

    void addCommandWithoutUndo(Command command);

    void adjustUndoListForClippingTool();

    void executeAllCommands();

    int getColorCommandCount();

    CommandManagerModel getCommandManagerModel();

    CommandManagerModel getCommandManagerModelForCatrobatImage();

    Command getLastExecutedCommand();

    int getUndoCommandCount();

    boolean isBusy();

    boolean isLastColorCommandOnTop();

    boolean isRedoAvailable();

    boolean isUndoAvailable();

    void loadCommandsCatrobatImage(CommandManagerModel model);

    void popFirstCommandInRedo();

    void popFirstCommandInUndo();

    void redo();

    void redoInConnectedLinesMode();

    void removeCommandListener(CommandListener commandListener);

    void reset();

    void setInitialStateCommand(Command command);

    void shutdown();

    void undo();

    void undoIgnoringColorChanges();

    void undoIgnoringColorChangesAndAddCommand(Command command);

    void undoInClippingTool();

    void undoInConnectedLinesMode();
}
