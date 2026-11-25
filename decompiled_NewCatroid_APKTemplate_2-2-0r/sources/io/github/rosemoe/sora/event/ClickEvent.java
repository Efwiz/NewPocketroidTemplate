package io.github.rosemoe.sora.event;

import android.view.MotionEvent;
import io.github.rosemoe.sora.lang.styling.Span;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.TextRange;
import io.github.rosemoe.sora.widget.CodeEditor;

/* JADX INFO: loaded from: classes11.dex */
public class ClickEvent extends EditorMotionEvent {
    public ClickEvent(CodeEditor codeEditor, CharPosition charPosition, MotionEvent motionEvent, Span span, TextRange textRange) {
        super(codeEditor, charPosition, motionEvent, span, textRange);
    }
}
