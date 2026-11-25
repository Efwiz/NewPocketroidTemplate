package io.github.rosemoe.sora.event;

import io.github.rosemoe.sora.widget.CodeEditor;

/* JADX INFO: loaded from: classes11.dex */
public class HandleStateChangeEvent extends Event {
    public static final int HANDLE_TYPE_INSERT = 0;
    public static final int HANDLE_TYPE_LEFT = 1;
    public static final int HANDLE_TYPE_RIGHT = 2;
    private final boolean isHeld;
    private final int which;

    public HandleStateChangeEvent(CodeEditor codeEditor, int i, boolean z) {
        super(codeEditor);
        this.which = i;
        this.isHeld = z;
    }

    public int getHandleType() {
        return this.which;
    }

    public boolean isHeld() {
        return this.isHeld;
    }
}
