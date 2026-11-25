package io.github.rosemoe.sora.lang.styling;

import io.github.rosemoe.sora.lang.styling.color.ConstColor;
import io.github.rosemoe.sora.lang.styling.color.ResolvableColor;
import io.github.rosemoe.sora.lang.styling.span.SpanExt;
import java.util.Collection;

/* JADX INFO: loaded from: classes11.dex */
public interface Span {
    Span copy();

    int getColumn();

    Object getExtra();

    <T> T getSpanExt(int i);

    long getStyle();

    ResolvableColor getUnderlineColor();

    boolean hasSpanExt(int i);

    boolean recycle();

    void removeAllSpanExt();

    void reset();

    void setColumn(int i);

    void setExtra(Object obj);

    void setSpanExt(int i, SpanExt spanExt);

    void setStyle(long j);

    void setUnderlineColor(ResolvableColor resolvableColor);

    default void shiftColumnBy(int i) {
        setColumn(getColumn() + i);
    }

    default int getForegroundColorId() {
        return TextStyle.getForegroundColorId(getStyle());
    }

    default int getBackgroundColorId() {
        return TextStyle.getBackgroundColorId(getStyle());
    }

    default long getStyleBits() {
        return TextStyle.getStyleBits(getStyle());
    }

    default void setUnderlineColor(int i) {
        if (i == 0) {
            setUnderlineColor((ResolvableColor) null);
        } else {
            setUnderlineColor(new ConstColor(i));
        }
    }

    static Span obtain(int i, long j) {
        return SpanFactory.obtain(i, j);
    }

    static void recycleAll(Collection<Span> collection) {
        SpanFactory.recycleAll(collection);
    }
}
