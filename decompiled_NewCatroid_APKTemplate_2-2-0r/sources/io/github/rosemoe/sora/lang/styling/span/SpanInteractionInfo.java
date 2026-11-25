package io.github.rosemoe.sora.lang.styling.span;

import kotlin.Metadata;

/* JADX INFO: compiled from: SpanInteractionInfo.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\n\u0010\u0002\u001a\u0004\u0018\u00010\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0005H&J\b\u0010\u0007\u001a\u00020\u0005H&¨\u0006\b"}, d2 = {"Lio/github/rosemoe/sora/lang/styling/span/SpanInteractionInfo;", "Lio/github/rosemoe/sora/lang/styling/span/SpanExt;", "getData", "", "isClickable", "", "isDoubleClickable", "isLongClickable", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SpanInteractionInfo extends SpanExt {
    Object getData();

    boolean isClickable();

    boolean isDoubleClickable();

    boolean isLongClickable();
}
