package io.github.rosemoe.sora.widget.ext;

import io.github.rosemoe.sora.lang.styling.span.SpanInteractionInfo;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EditorSpanInteractionHandler.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class EditorSpanInteractionHandler$1$1 extends FunctionReferenceImpl implements Function1<SpanInteractionInfo, Boolean> {
    public static final EditorSpanInteractionHandler$1$1 INSTANCE = new EditorSpanInteractionHandler$1$1();

    EditorSpanInteractionHandler$1$1() {
        super(1, SpanInteractionInfo.class, "isClickable", "isClickable()Z", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(SpanInteractionInfo p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return Boolean.valueOf(p0.isClickable());
    }
}
