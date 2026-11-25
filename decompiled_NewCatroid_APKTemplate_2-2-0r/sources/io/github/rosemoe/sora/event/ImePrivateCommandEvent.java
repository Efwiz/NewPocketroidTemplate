package io.github.rosemoe.sora.event;

import android.os.Bundle;
import io.github.rosemoe.sora.widget.CodeEditor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: editorMinorEvents.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lio/github/rosemoe/sora/event/ImePrivateCommandEvent;", "Lio/github/rosemoe/sora/event/Event;", "editor", "Lio/github/rosemoe/sora/widget/CodeEditor;", "action", "", "data", "Landroid/os/Bundle;", "(Lio/github/rosemoe/sora/widget/CodeEditor;Ljava/lang/String;Landroid/os/Bundle;)V", "getAction", "()Ljava/lang/String;", "getData", "()Landroid/os/Bundle;", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImePrivateCommandEvent extends Event {
    private final String action;
    private final Bundle data;

    public final String getAction() {
        return this.action;
    }

    public final Bundle getData() {
        return this.data;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImePrivateCommandEvent(CodeEditor editor, String action, Bundle bundle) {
        super(editor);
        Intrinsics.checkNotNullParameter(editor, "editor");
        Intrinsics.checkNotNullParameter(action, "action");
        this.action = action;
        this.data = bundle;
    }
}
