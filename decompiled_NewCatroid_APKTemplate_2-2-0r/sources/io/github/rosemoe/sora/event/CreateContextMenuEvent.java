package io.github.rosemoe.sora.event;

import android.view.ContextMenu;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.widget.CodeEditor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: editorMinorEvents.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lio/github/rosemoe/sora/event/CreateContextMenuEvent;", "Lio/github/rosemoe/sora/event/Event;", "editor", "Lio/github/rosemoe/sora/widget/CodeEditor;", LineReader.MENU, "Landroid/view/ContextMenu;", "position", "Lio/github/rosemoe/sora/text/CharPosition;", "(Lio/github/rosemoe/sora/widget/CodeEditor;Landroid/view/ContextMenu;Lio/github/rosemoe/sora/text/CharPosition;)V", "getMenu", "()Landroid/view/ContextMenu;", "getPosition", "()Lio/github/rosemoe/sora/text/CharPosition;", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateContextMenuEvent extends Event {
    private final ContextMenu menu;
    private final CharPosition position;

    public final ContextMenu getMenu() {
        return this.menu;
    }

    public final CharPosition getPosition() {
        return this.position;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CreateContextMenuEvent(CodeEditor editor, ContextMenu menu, CharPosition position) {
        super(editor);
        Intrinsics.checkNotNullParameter(editor, "editor");
        Intrinsics.checkNotNullParameter(menu, "menu");
        Intrinsics.checkNotNullParameter(position, "position");
        this.menu = menu;
        this.position = position;
    }
}
