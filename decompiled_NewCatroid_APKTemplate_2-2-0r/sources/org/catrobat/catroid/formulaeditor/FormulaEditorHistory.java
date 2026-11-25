package org.catrobat.catroid.formulaeditor;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import org.catrobat.catroid.content.bricks.Brick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/FormulaEditorHistory.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class FormulaEditorHistory {
    private static final int MAXIMUM_HISTORY_LENGTH = 32;
    private UndoState current;
    private boolean hasUnsavedChanges = false;
    Map<Brick.FormulaField, InternFormulaState> initialStates;
    private Stack<UndoState> redoStack;
    private Stack<UndoState> undoStack;

    public FormulaEditorHistory(UndoState state) {
        this.undoStack = null;
        this.redoStack = null;
        this.initialStates = null;
        this.current = state;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.initialStates = new HashMap();
    }

    public void push(UndoState state) {
        UndoState undoState = this.current;
        if (undoState == null || !undoState.equals(state)) {
            UndoState undoState2 = this.current;
            if (undoState2 != null) {
                this.undoStack.push(undoState2);
                if (!this.initialStates.containsKey(this.current.formulaField)) {
                    this.initialStates.put(this.current.formulaField, this.current.internFormulaState);
                }
            }
            this.current = state;
            this.redoStack.clear();
            this.hasUnsavedChanges = true;
            if (this.undoStack.size() > 32) {
                this.undoStack.removeElementAt(0);
            }
        }
    }

    public UndoState backward() {
        this.redoStack.push(this.current);
        this.hasUnsavedChanges = true;
        if (!this.undoStack.empty()) {
            this.current = this.undoStack.pop();
        }
        return this.current;
    }

    public UndoState forward() {
        this.undoStack.push(this.current);
        this.hasUnsavedChanges = true;
        if (!this.redoStack.empty()) {
            this.current = this.redoStack.pop();
        }
        return this.current;
    }

    public void updateCurrentSelection(InternFormulaTokenSelection internFormulaTokenSelection) {
        this.current.internFormulaState.setSelection(internFormulaTokenSelection);
    }

    public void updateCurrentState(UndoState state) {
        this.current = state;
    }

    public void clear() {
        this.undoStack.clear();
        this.redoStack.clear();
        this.current = null;
        this.hasUnsavedChanges = false;
    }

    public void updateCurrentCursor(int cursorPosition) {
        this.current.internFormulaState.setExternCursorPosition(cursorPosition);
    }

    public boolean undoIsPossible() {
        return !this.undoStack.empty();
    }

    public boolean redoIsPossible() {
        return !this.redoStack.empty();
    }

    public boolean hasUnsavedChanges() {
        return this.hasUnsavedChanges;
    }

    public Map<Brick.FormulaField, InternFormulaState> getInitialStates() {
        return this.initialStates;
    }

    public void changesSaved() {
        this.hasUnsavedChanges = false;
    }
}
