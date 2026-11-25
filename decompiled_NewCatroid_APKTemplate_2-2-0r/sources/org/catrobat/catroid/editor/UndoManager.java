package org.catrobat.catroid.editor;

import java.util.ArrayDeque;
import java.util.Deque;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/UndoManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UndoManager {
    private static final int MAX_HISTORY = 50;
    private final EditorActivity activity;
    private final Deque<EditorCommand> undoStack = new ArrayDeque();
    private final Deque<EditorCommand> redoStack = new ArrayDeque();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/UndoManager$EditorCommand.class
      classes14.dex
     */
    public interface EditorCommand {
        void redo();

        void undo();
    }

    public UndoManager(EditorActivity activity) {
        this.activity = activity;
    }

    public void pushCommand(EditorCommand cmd) {
        this.undoStack.push(cmd);
        if (this.undoStack.size() > 50) {
            this.undoStack.removeLast();
        }
        this.redoStack.clear();
    }

    public void undo() {
        if (this.undoStack.isEmpty()) {
            return;
        }
        EditorCommand editorCommandPop = this.undoStack.pop();
        editorCommandPop.undo();
        this.redoStack.push(editorCommandPop);
        this.activity.updateHierarchy();
    }

    public void redo() {
        if (this.redoStack.isEmpty()) {
            return;
        }
        EditorCommand editorCommandPop = this.redoStack.pop();
        editorCommandPop.redo();
        this.undoStack.push(editorCommandPop);
        this.activity.updateHierarchy();
    }
}
