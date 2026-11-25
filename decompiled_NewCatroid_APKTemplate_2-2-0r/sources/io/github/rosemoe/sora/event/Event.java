package io.github.rosemoe.sora.event;

import io.github.rosemoe.sora.widget.CodeEditor;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public abstract class Event {
    private final CodeEditor mEditor;
    private final long mEventTime;
    private int mInterceptTargets;

    public boolean canIntercept() {
        return false;
    }

    public Event(CodeEditor codeEditor) {
        this(codeEditor, System.currentTimeMillis());
    }

    public Event(CodeEditor codeEditor, long j) {
        this.mEditor = (CodeEditor) Objects.requireNonNull(codeEditor);
        this.mEventTime = j;
        this.mInterceptTargets = 0;
    }

    public long getEventTime() {
        return this.mEventTime;
    }

    public CodeEditor getEditor() {
        return this.mEditor;
    }

    public void intercept() {
        if (!canIntercept()) {
            throw new UnsupportedOperationException("intercept() not supported");
        }
        this.mInterceptTargets = 3;
    }

    public void intercept(int i) {
        if (!canIntercept()) {
            throw new UnsupportedOperationException("intercept() not supported");
        }
        this.mInterceptTargets = i;
    }

    public int getInterceptTargets() {
        return this.mInterceptTargets;
    }

    public boolean isIntercepted() {
        return this.mInterceptTargets != 0;
    }
}
