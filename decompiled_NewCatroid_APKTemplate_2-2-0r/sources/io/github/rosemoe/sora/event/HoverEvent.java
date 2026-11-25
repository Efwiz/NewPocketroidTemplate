package io.github.rosemoe.sora.event;

import android.view.MotionEvent;
import io.github.rosemoe.sora.lang.styling.Span;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.TextRange;
import io.github.rosemoe.sora.widget.CodeEditor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: editorMinorEvents.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lio/github/rosemoe/sora/event/HoverEvent;", "Lio/github/rosemoe/sora/event/EditorMotionEvent;", "editor", "Lio/github/rosemoe/sora/widget/CodeEditor;", "position", "Lio/github/rosemoe/sora/text/CharPosition;", "event", "Landroid/view/MotionEvent;", "span", "Lio/github/rosemoe/sora/lang/styling/Span;", "spanRange", "Lio/github/rosemoe/sora/text/TextRange;", "(Lio/github/rosemoe/sora/widget/CodeEditor;Lio/github/rosemoe/sora/text/CharPosition;Landroid/view/MotionEvent;Lio/github/rosemoe/sora/lang/styling/Span;Lio/github/rosemoe/sora/text/TextRange;)V", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class HoverEvent extends EditorMotionEvent {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HoverEvent(CodeEditor editor, CharPosition position, MotionEvent event, Span span, TextRange textRange) {
        super(editor, position, event, span, textRange);
        Intrinsics.checkNotNullParameter(editor, "editor");
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(event, "event");
    }
}
