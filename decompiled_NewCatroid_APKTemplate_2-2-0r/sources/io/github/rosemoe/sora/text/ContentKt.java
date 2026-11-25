package io.github.rosemoe.sora.text;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Content.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00040\u0003¨\u0006\u0005"}, d2 = {"batchEdit", "Lio/github/rosemoe/sora/text/Content;", "block", "Lkotlin/Function1;", "", "editor_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ContentKt {
    public static final Content batchEdit(Content content, Function1<? super Content, Unit> block) {
        Intrinsics.checkNotNullParameter(content, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        content.beginBatchEdit();
        block.invoke(content);
        content.endBatchEdit();
        return content;
    }
}
