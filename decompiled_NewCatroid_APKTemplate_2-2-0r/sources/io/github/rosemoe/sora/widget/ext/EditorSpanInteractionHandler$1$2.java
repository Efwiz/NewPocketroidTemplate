package io.github.rosemoe.sora.widget.ext;

import io.github.rosemoe.sora.lang.styling.Span;
import io.github.rosemoe.sora.lang.styling.span.SpanInteractionInfo;
import io.github.rosemoe.sora.text.TextRange;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EditorSpanInteractionHandler.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class EditorSpanInteractionHandler$1$2 extends FunctionReferenceImpl implements Function3<Span, SpanInteractionInfo, TextRange, Boolean> {
    EditorSpanInteractionHandler$1$2(Object obj) {
        super(3, obj, EditorSpanInteractionHandler.class, "handleSpanClick", "handleSpanClick(Lio/github/rosemoe/sora/lang/styling/Span;Lio/github/rosemoe/sora/lang/styling/span/SpanInteractionInfo;Lio/github/rosemoe/sora/text/TextRange;)Z", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Boolean invoke(Span p0, SpanInteractionInfo p1, TextRange p2) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        Intrinsics.checkNotNullParameter(p2, "p2");
        return Boolean.valueOf(((EditorSpanInteractionHandler) this.receiver).handleSpanClick(p0, p1, p2));
    }
}
