package io.github.rosemoe.sora.event;

import io.github.rosemoe.sora.widget.CodeEditor;

/* JADX INFO: loaded from: classes11.dex */
public abstract class ResultedEvent<T> extends Event {
    private T result;

    public ResultedEvent(CodeEditor codeEditor) {
        super(codeEditor);
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T t) {
        this.result = t;
    }

    public boolean isResultSet() {
        return this.result != null;
    }
}
