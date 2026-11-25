package io.github.rosemoe.sora.event;

import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ColorSchemeUpdateEvent.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lio/github/rosemoe/sora/event/ColorSchemeUpdateEvent;", "Lio/github/rosemoe/sora/event/Event;", "editor", "Lio/github/rosemoe/sora/widget/CodeEditor;", "(Lio/github/rosemoe/sora/widget/CodeEditor;)V", "colorScheme", "Lio/github/rosemoe/sora/widget/schemes/EditorColorScheme;", "getColorScheme", "()Lio/github/rosemoe/sora/widget/schemes/EditorColorScheme;", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ColorSchemeUpdateEvent extends Event {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorSchemeUpdateEvent(CodeEditor editor) {
        super(editor);
        Intrinsics.checkNotNullParameter(editor, "editor");
    }

    public final EditorColorScheme getColorScheme() {
        EditorColorScheme colorScheme = getEditor().getColorScheme();
        Intrinsics.checkNotNullExpressionValue(colorScheme, "getColorScheme(...)");
        return colorScheme;
    }
}
