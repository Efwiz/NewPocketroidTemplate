package io.github.rosemoe.sora.event;

import android.view.MotionEvent;
import io.github.rosemoe.sora.lang.styling.Span;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.TextRange;
import io.github.rosemoe.sora.widget.CodeEditor;

/* JADX INFO: loaded from: classes11.dex */
public abstract class EditorMotionEvent extends Event {
    private final MotionEvent event;
    private final CharPosition pos;
    private final Span span;
    private final TextRange spanRange;

    @Override // io.github.rosemoe.sora.event.Event
    public boolean canIntercept() {
        return true;
    }

    public EditorMotionEvent(CodeEditor codeEditor, CharPosition charPosition, MotionEvent motionEvent, Span span, TextRange textRange) {
        super(codeEditor);
        this.pos = charPosition;
        this.event = motionEvent;
        this.span = span;
        this.spanRange = textRange;
    }

    public boolean isFromMouse() {
        return this.event.isFromSource(8194);
    }

    public int getLine() {
        return this.pos.line;
    }

    public int getColumn() {
        return this.pos.column;
    }

    public int getIndex() {
        return this.pos.index;
    }

    public CharPosition getCharPosition() {
        return this.pos.fromThis();
    }

    public float getX() {
        return this.event.getX();
    }

    public float getY() {
        return this.event.getY();
    }

    public MotionEvent getCausingEvent() {
        return this.event;
    }

    public Span getSpan() {
        return this.span;
    }

    public TextRange getSpanRange() {
        return this.spanRange;
    }
}
