package org.mozilla.javascript.tools.debugger;

/* JADX INFO: loaded from: classes6.dex */
public interface GuiCallback {
    void dispatchNextGuiEvent() throws InterruptedException;

    void enterInterrupt(Dim.StackFrame stackFrame, String str, String str2);

    boolean isGuiEventThread();

    void updateSourceText(Dim.SourceInfo sourceInfo);
}
