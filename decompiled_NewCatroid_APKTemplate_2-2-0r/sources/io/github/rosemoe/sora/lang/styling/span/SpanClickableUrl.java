package io.github.rosemoe.sora.lang.styling.span;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SpanClickableUrl.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\u0003H\u0016J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\tH\u0016J\b\u0010\u000b\u001a\u00020\tH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"}, d2 = {"Lio/github/rosemoe/sora/lang/styling/span/SpanClickableUrl;", "Lio/github/rosemoe/sora/lang/styling/span/SpanInteractionInfo;", "link", "", "(Ljava/lang/String;)V", "getLink", "()Ljava/lang/String;", "getData", "isClickable", "", "isDoubleClickable", "isLongClickable", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SpanClickableUrl implements SpanInteractionInfo {
    private final String link;

    @Override // io.github.rosemoe.sora.lang.styling.span.SpanInteractionInfo
    public boolean isClickable() {
        return false;
    }

    @Override // io.github.rosemoe.sora.lang.styling.span.SpanInteractionInfo
    public boolean isDoubleClickable() {
        return true;
    }

    @Override // io.github.rosemoe.sora.lang.styling.span.SpanInteractionInfo
    public boolean isLongClickable() {
        return false;
    }

    public SpanClickableUrl(String link) {
        Intrinsics.checkNotNullParameter(link, "link");
        this.link = link;
    }

    public final String getLink() {
        return this.link;
    }

    @Override // io.github.rosemoe.sora.lang.styling.span.SpanInteractionInfo
    /* JADX INFO: renamed from: getData, reason: from getter */
    public String getLink() {
        return this.link;
    }
}
