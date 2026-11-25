package io.github.rosemoe.sora.event;

import android.view.KeyEvent;
import io.github.rosemoe.sora.widget.CodeEditor;

/* JADX INFO: loaded from: classes11.dex */
public class EditorKeyEvent extends ResultedEvent<Boolean> {
    private final boolean altPressed;
    private final boolean shiftPressed;
    private final KeyEvent src;
    private final Type type;

    public enum Type {
        UP,
        DOWN,
        MULTIPLE
    }

    @Override // io.github.rosemoe.sora.event.Event
    public boolean canIntercept() {
        return true;
    }

    public EditorKeyEvent(CodeEditor codeEditor, KeyEvent keyEvent, Type type) {
        super(codeEditor);
        this.src = keyEvent;
        this.type = type;
        this.shiftPressed = getEditor().getKeyMetaStates().isShiftPressed();
        this.altPressed = getEditor().getKeyMetaStates().isAltPressed();
    }

    public int getAction() {
        return this.src.getAction();
    }

    public int getKeyCode() {
        return this.src.getKeyCode();
    }

    public int getRepeatCount() {
        return this.src.getRepeatCount();
    }

    public int getMetaState() {
        return this.src.getMetaState();
    }

    public int getModifiers() {
        return this.src.getModifiers();
    }

    public long getDownTime() {
        return this.src.getDownTime();
    }

    public Type getEventType() {
        return this.type;
    }

    @Override // io.github.rosemoe.sora.event.Event
    public long getEventTime() {
        return this.src.getEventTime();
    }

    public boolean isShiftPressed() {
        return this.shiftPressed;
    }

    public boolean isAltPressed() {
        return this.altPressed;
    }

    public boolean isCtrlPressed() {
        return (this.src.getMetaState() & 4096) != 0;
    }

    public final boolean result(boolean z) {
        boolean zBooleanValue = isResultSet() ? getResult().booleanValue() : false;
        if (isIntercepted()) {
            return zBooleanValue;
        }
        return zBooleanValue || z;
    }
}
