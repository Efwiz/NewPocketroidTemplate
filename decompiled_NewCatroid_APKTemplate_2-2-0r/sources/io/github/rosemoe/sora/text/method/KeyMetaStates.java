package io.github.rosemoe.sora.text.method;

import android.text.Editable;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyEvent;
import io.github.rosemoe.sora.widget.CodeEditor;

/* JADX INFO: loaded from: classes11.dex */
public class KeyMetaStates extends MetaKeyKeyListener {
    private final CodeEditor editor;
    private final Editable dest = Editable.Factory.getInstance().newEditable("");
    private boolean isCtrlPressed = false;

    public KeyMetaStates(CodeEditor codeEditor) {
        this.editor = codeEditor;
    }

    public void onKeyDown(KeyEvent keyEvent) {
        super.onKeyDown(this.editor, this.dest, keyEvent.getKeyCode(), keyEvent);
        this.isCtrlPressed = keyEvent.isCtrlPressed();
    }

    public void onKeyUp(KeyEvent keyEvent) {
        super.onKeyUp(this.editor, this.dest, keyEvent.getKeyCode(), keyEvent);
        this.isCtrlPressed = keyEvent.isCtrlPressed();
    }

    public boolean isCtrlPressed() {
        return this.isCtrlPressed;
    }

    public int getMetaState(KeyEvent keyEvent) {
        return getMetaState(this.dest, keyEvent);
    }

    public boolean isShiftPressed() {
        return getMetaState(this.dest, 1) != 0;
    }

    public boolean isAltPressed() {
        return getMetaState(this.dest, 2) != 0;
    }

    public void adjust() {
        adjustMetaAfterKeypress(this.dest);
    }

    public void clearMetaStates(int i) {
        clearMetaKeyState(this.editor, this.dest, i);
    }
}
