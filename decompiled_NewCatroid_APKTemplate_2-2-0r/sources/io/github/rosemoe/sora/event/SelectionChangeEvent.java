package io.github.rosemoe.sora.event;

import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.Cursor;
import io.github.rosemoe.sora.widget.CodeEditor;

/* JADX INFO: loaded from: classes11.dex */
public class SelectionChangeEvent extends Event {
    public static final int CAUSE_IME = 4;
    public static final int CAUSE_KEYBOARD_OR_CODE = 7;
    public static final int CAUSE_LONG_PRESS = 5;
    public static final int CAUSE_MOUSE_INPUT = 8;
    public static final int CAUSE_SEARCH = 6;
    public static final int CAUSE_SELECTION_HANDLE = 2;
    public static final int CAUSE_TAP = 3;
    public static final int CAUSE_TEXT_MODIFICATION = 1;
    public static final int CAUSE_UNKNOWN = 0;
    private final int cause;
    private final CharPosition left;
    private final CharPosition right;

    public SelectionChangeEvent(CodeEditor codeEditor, int i) {
        super(codeEditor);
        Cursor cursor = codeEditor.getText().getCursor();
        this.left = cursor.left();
        this.right = cursor.right();
        this.cause = i;
    }

    public int getCause() {
        return this.cause;
    }

    public CharPosition getLeft() {
        return this.left;
    }

    public CharPosition getRight() {
        return this.right;
    }

    public boolean isSelected() {
        return this.left.index != this.right.index;
    }
}
