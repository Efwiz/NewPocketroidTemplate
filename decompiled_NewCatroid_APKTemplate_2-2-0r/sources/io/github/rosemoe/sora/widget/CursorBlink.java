package io.github.rosemoe.sora.widget;

import io.github.rosemoe.sora.event.EventReceiver;
import io.github.rosemoe.sora.event.SelectionChangeEvent;
import io.github.rosemoe.sora.event.Unsubscribe;
import io.github.rosemoe.sora.text.CharPosition;

/* JADX INFO: loaded from: classes11.dex */
final class CursorBlink implements Runnable, EventReceiver<SelectionChangeEvent> {
    private float[] buffer;
    final CodeEditor editor;
    int period;
    public boolean valid;
    long lastSelectionModificationTime = 0;
    public boolean visibility = true;

    public CursorBlink(CodeEditor codeEditor, int i) {
        this.editor = codeEditor;
        this.period = i;
        codeEditor.subscribeEvent(SelectionChangeEvent.class, this);
    }

    @Override // io.github.rosemoe.sora.event.EventReceiver
    public void onReceive(SelectionChangeEvent selectionChangeEvent, Unsubscribe unsubscribe) {
        onSelectionChanged();
    }

    public void setPeriod(int i) {
        this.period = i;
        if (i <= 0) {
            this.visibility = true;
            this.valid = false;
        } else {
            this.valid = true;
        }
    }

    public void onSelectionChanged() {
        this.lastSelectionModificationTime = System.currentTimeMillis();
        this.visibility = true;
    }

    public boolean isSelectionVisible() {
        return this.buffer[0] >= ((float) this.editor.getOffsetY()) && this.buffer[0] - ((float) this.editor.getRowHeight()) <= ((float) (this.editor.getOffsetY() + this.editor.getHeight())) && this.buffer[1] >= ((float) this.editor.getOffsetX()) && this.buffer[1] - 100.0f <= ((float) (this.editor.getOffsetX() + this.editor.getWidth()));
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.valid && this.period > 0) {
            if (System.currentTimeMillis() - this.lastSelectionModificationTime >= ((long) this.period) * 2) {
                this.visibility = !this.visibility;
                CharPosition charPositionLeft = this.editor.getCursor().left();
                this.buffer = this.editor.getLayout().getCharLayoutOffset(charPositionLeft.line, charPositionLeft.column, this.buffer);
                if (!this.editor.getCursor().isSelected() && isSelectionVisible()) {
                    this.editor.postInvalidate();
                }
            } else {
                this.visibility = true;
            }
            this.editor.postDelayedInLifecycle(this, this.period);
            return;
        }
        this.visibility = true;
    }
}
