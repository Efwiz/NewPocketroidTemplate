package io.github.rosemoe.sora.event;

import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.widget.CodeEditor;

/* JADX INFO: loaded from: classes11.dex */
public class ContentChangeEvent extends Event {
    public static final int ACTION_DELETE = 3;
    public static final int ACTION_INSERT = 2;
    public static final int ACTION_SET_NEW_TEXT = 1;
    private final int action;
    private final boolean causedByUndoManager;
    private final CharPosition end;
    private final CharPosition start;
    private final CharSequence textChanged;

    public ContentChangeEvent(CodeEditor codeEditor, int i, CharPosition charPosition, CharPosition charPosition2, CharSequence charSequence, boolean z) {
        super(codeEditor);
        this.action = i;
        this.start = charPosition;
        this.end = charPosition2;
        this.textChanged = charSequence;
        this.causedByUndoManager = z;
    }

    public int getAction() {
        return this.action;
    }

    public CharPosition getChangeStart() {
        return this.start;
    }

    public CharPosition getChangeEnd() {
        return this.end;
    }

    public CharSequence getChangedText() {
        return this.textChanged;
    }

    public boolean isCausedByUndoManager() {
        return this.causedByUndoManager;
    }
}
