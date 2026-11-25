package io.github.rosemoe.sora.event;

import io.github.rosemoe.sora.widget.CodeEditor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: editorMinorEvents.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lio/github/rosemoe/sora/event/EditorReleaseEvent;", "Lio/github/rosemoe/sora/event/Event;", "editor", "Lio/github/rosemoe/sora/widget/CodeEditor;", "(Lio/github/rosemoe/sora/widget/CodeEditor;)V", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EditorReleaseEvent extends Event {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditorReleaseEvent(CodeEditor editor) {
        super(editor);
        Intrinsics.checkNotNullParameter(editor, "editor");
    }
}
