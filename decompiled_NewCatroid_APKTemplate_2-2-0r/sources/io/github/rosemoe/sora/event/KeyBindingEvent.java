package io.github.rosemoe.sora.event;

import android.view.KeyEvent;
import io.github.rosemoe.sora.widget.CodeEditor;

/* JADX INFO: loaded from: classes11.dex */
public class KeyBindingEvent extends EditorKeyEvent {
    private final boolean editorAbleToHandle;

    public KeyBindingEvent(CodeEditor codeEditor, KeyEvent keyEvent, EditorKeyEvent.Type type, boolean z) {
        super(codeEditor, keyEvent, type);
        this.editorAbleToHandle = z;
    }

    public boolean canEditorHandle() {
        return this.editorAbleToHandle;
    }
}
