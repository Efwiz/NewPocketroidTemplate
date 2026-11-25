package io.github.rosemoe.sora.event;

import io.github.rosemoe.sora.widget.CodeEditor;

/* JADX INFO: loaded from: classes11.dex */
public class SnippetEvent extends Event {
    public static final int ACTION_SHIFT = 2;
    public static final int ACTION_START = 1;
    public static final int ACTION_STOP = 3;
    private final int action;
    private final int currentTabStop;
    private final int totalTabStop;

    public SnippetEvent(CodeEditor codeEditor, int i, int i2, int i3) {
        super(codeEditor);
        this.action = i;
        this.currentTabStop = i2;
        this.totalTabStop = i3;
    }

    public int getAction() {
        return this.action;
    }

    public int getCurrentTabStop() {
        return this.currentTabStop;
    }

    public int getTotalTabStop() {
        return this.totalTabStop;
    }
}
