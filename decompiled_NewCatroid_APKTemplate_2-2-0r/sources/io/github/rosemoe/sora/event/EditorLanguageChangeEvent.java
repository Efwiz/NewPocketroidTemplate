package io.github.rosemoe.sora.event;

import io.github.rosemoe.sora.lang.Language;
import io.github.rosemoe.sora.widget.CodeEditor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: editorMinorEvents.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lio/github/rosemoe/sora/event/EditorLanguageChangeEvent;", "Lio/github/rosemoe/sora/event/Event;", "editor", "Lio/github/rosemoe/sora/widget/CodeEditor;", "newLanguage", "Lio/github/rosemoe/sora/lang/Language;", "(Lio/github/rosemoe/sora/widget/CodeEditor;Lio/github/rosemoe/sora/lang/Language;)V", "getNewLanguage", "()Lio/github/rosemoe/sora/lang/Language;", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EditorLanguageChangeEvent extends Event {
    private final Language newLanguage;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditorLanguageChangeEvent(CodeEditor editor, Language newLanguage) {
        super(editor);
        Intrinsics.checkNotNullParameter(editor, "editor");
        Intrinsics.checkNotNullParameter(newLanguage, "newLanguage");
        this.newLanguage = newLanguage;
    }

    public final Language getNewLanguage() {
        return this.newLanguage;
    }
}
