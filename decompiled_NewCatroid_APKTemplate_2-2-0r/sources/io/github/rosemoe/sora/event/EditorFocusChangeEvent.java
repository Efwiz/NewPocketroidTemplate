package io.github.rosemoe.sora.event;

import io.github.rosemoe.sora.widget.CodeEditor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: editorMinorEvents.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007¨\u0006\b"}, d2 = {"Lio/github/rosemoe/sora/event/EditorFocusChangeEvent;", "Lio/github/rosemoe/sora/event/Event;", "editor", "Lio/github/rosemoe/sora/widget/CodeEditor;", "isGainFocus", "", "(Lio/github/rosemoe/sora/widget/CodeEditor;Z)V", "()Z", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EditorFocusChangeEvent extends Event {
    private final boolean isGainFocus;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditorFocusChangeEvent(CodeEditor editor, boolean z) {
        super(editor);
        Intrinsics.checkNotNullParameter(editor, "editor");
        this.isGainFocus = z;
    }

    /* JADX INFO: renamed from: isGainFocus, reason: from getter */
    public final boolean getIsGainFocus() {
        return this.isGainFocus;
    }
}
