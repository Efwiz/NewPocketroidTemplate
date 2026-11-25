package io.github.rosemoe.sora.lang.styling.span;

import io.github.rosemoe.sora.lang.styling.Span;
import io.github.rosemoe.sora.lang.styling.color.ConstColor;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.stage.StageActivity;

/* JADX INFO: compiled from: SpanConstColorResolver.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0012\u0010\t\u001a\u0004\u0018\u00010\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lio/github/rosemoe/sora/lang/styling/span/SpanConstColorResolver;", "Lio/github/rosemoe/sora/lang/styling/span/SpanColorResolver;", "foreground", "", "background", "(II)V", StageActivity.STYLE_BACKGROUND_COLOR, "Lio/github/rosemoe/sora/lang/styling/color/ConstColor;", "foregroundColor", "getBackgroundColor", "span", "Lio/github/rosemoe/sora/lang/styling/Span;", "getForegroundColor", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SpanConstColorResolver implements SpanColorResolver {
    private final ConstColor backgroundColor;
    private final ConstColor foregroundColor;

    /* JADX WARN: Illegal instructions before constructor call */
    public SpanConstColorResolver() {
        int i = 0;
        this(i, i, 3, null);
    }

    public SpanConstColorResolver(int i, int i2) {
        this.foregroundColor = i == 0 ? null : new ConstColor(i);
        this.backgroundColor = i2 != 0 ? new ConstColor(i2) : null;
    }

    public /* synthetic */ SpanConstColorResolver(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2);
    }

    @Override // io.github.rosemoe.sora.lang.styling.span.SpanColorResolver
    public ConstColor getForegroundColor(Span span) {
        Intrinsics.checkNotNullParameter(span, "span");
        return this.foregroundColor;
    }

    @Override // io.github.rosemoe.sora.lang.styling.span.SpanColorResolver
    public ConstColor getBackgroundColor(Span span) {
        Intrinsics.checkNotNullParameter(span, "span");
        return this.backgroundColor;
    }
}
