package io.github.rosemoe.sora.event;

import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.EditorSearcher;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: PublishSearchResultEvent.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006¨\u0006\b"}, d2 = {"Lio/github/rosemoe/sora/event/PublishSearchResultEvent;", "Lio/github/rosemoe/sora/event/Event;", "editor", "Lio/github/rosemoe/sora/widget/CodeEditor;", "(Lio/github/rosemoe/sora/widget/CodeEditor;)V", "getSearcher", "Lio/github/rosemoe/sora/widget/EditorSearcher;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PublishSearchResultEvent extends Event {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PublishSearchResultEvent(CodeEditor editor) {
        super(editor);
        Intrinsics.checkNotNullParameter(editor, "editor");
    }

    public final EditorSearcher getSearcher() {
        return getEditor().getSearcher();
    }
}
