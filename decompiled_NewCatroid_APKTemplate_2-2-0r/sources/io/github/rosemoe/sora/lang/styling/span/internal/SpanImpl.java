package io.github.rosemoe.sora.lang.styling.span.internal;

import androidx.collection.MutableIntObjectMap;
import io.github.rosemoe.sora.lang.styling.Span;
import io.github.rosemoe.sora.lang.styling.SpanPool;
import io.github.rosemoe.sora.lang.styling.color.ResolvableColor;
import io.github.rosemoe.sora.lang.styling.span.SpanExt;
import io.github.rosemoe.sora.lang.styling.span.SpanExtAttrs;
import java.util.Objects;
import kotlin.jvm.functions.Function2;

/* JADX INFO: loaded from: classes11.dex */
public class SpanImpl implements Span {
    private static final SpanPool<SpanImpl> pool = new SpanPool<>(new Function2() { // from class: io.github.rosemoe.sora.lang.styling.span.internal.SpanImpl$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return new SpanImpl(((Integer) obj).intValue(), ((Long) obj2).longValue());
        }
    });
    private int column;
    private MutableIntObjectMap<SpanExt> extMap;
    private Object extra;
    private long style;

    SpanImpl() {
    }

    SpanImpl(int i, long j) {
        setColumn(i);
        setStyle(j);
    }

    public static SpanImpl obtain(int i, long j) {
        return (SpanImpl) pool.obtain(i, j);
    }

    @Override // io.github.rosemoe.sora.lang.styling.Span
    public void setColumn(int i) {
        this.column = i;
    }

    @Override // io.github.rosemoe.sora.lang.styling.Span
    public int getColumn() {
        return this.column;
    }

    @Override // io.github.rosemoe.sora.lang.styling.Span
    public void setStyle(long j) {
        this.style = j;
    }

    @Override // io.github.rosemoe.sora.lang.styling.Span
    public long getStyle() {
        return this.style;
    }

    @Override // io.github.rosemoe.sora.lang.styling.Span
    public void setUnderlineColor(ResolvableColor resolvableColor) {
        setSpanExt(3, resolvableColor);
    }

    @Override // io.github.rosemoe.sora.lang.styling.Span
    public ResolvableColor getUnderlineColor() {
        return (ResolvableColor) getSpanExt(3);
    }

    @Override // io.github.rosemoe.sora.lang.styling.Span
    public void setExtra(Object obj) {
        this.extra = obj;
    }

    @Override // io.github.rosemoe.sora.lang.styling.Span
    public Object getExtra() {
        return this.extra;
    }

    @Override // io.github.rosemoe.sora.lang.styling.Span
    public void setSpanExt(int i, SpanExt spanExt) {
        if (!SpanExtAttrs.checkType(i, spanExt)) {
            throw new IllegalArgumentException("type mismatch: extType " + i + " and extObj " + spanExt);
        }
        if (spanExt == null) {
            MutableIntObjectMap<SpanExt> mutableIntObjectMap = this.extMap;
            if (mutableIntObjectMap != null) {
                mutableIntObjectMap.remove(i);
                return;
            }
            return;
        }
        if (this.extMap == null) {
            this.extMap = new MutableIntObjectMap<>();
        }
        this.extMap.set(i, spanExt);
    }

    @Override // io.github.rosemoe.sora.lang.styling.Span
    public boolean hasSpanExt(int i) {
        return getSpanExt(i) != null;
    }

    @Override // io.github.rosemoe.sora.lang.styling.Span
    public <T> T getSpanExt(int i) {
        MutableIntObjectMap<SpanExt> mutableIntObjectMap = this.extMap;
        if (mutableIntObjectMap == null) {
            return null;
        }
        return (T) mutableIntObjectMap.get(i);
    }

    @Override // io.github.rosemoe.sora.lang.styling.Span
    public void removeAllSpanExt() {
        MutableIntObjectMap<SpanExt> mutableIntObjectMap = this.extMap;
        if (mutableIntObjectMap != null) {
            mutableIntObjectMap.clear();
        }
    }

    @Override // io.github.rosemoe.sora.lang.styling.Span
    public void reset() {
        setColumn(0);
        setStyle(0L);
        removeAllSpanExt();
    }

    @Override // io.github.rosemoe.sora.lang.styling.Span
    public Span copy() {
        SpanImpl spanImpl = new SpanImpl();
        spanImpl.setColumn(getColumn());
        spanImpl.setStyle(getStyle());
        if (this.extMap != null) {
            MutableIntObjectMap<SpanExt> mutableIntObjectMap = new MutableIntObjectMap<>();
            spanImpl.extMap = mutableIntObjectMap;
            mutableIntObjectMap.putAll(this.extMap);
        }
        return spanImpl;
    }

    @Override // io.github.rosemoe.sora.lang.styling.Span
    public boolean recycle() {
        reset();
        return pool.offer(this);
    }

    public String toString() {
        return "SpanImpl{column=" + this.column + ", style=" + this.style + ", extra=" + this.extra + ", extMap=" + this.extMap + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SpanImpl spanImpl = (SpanImpl) obj;
        return this.column == spanImpl.column && this.style == spanImpl.style && Objects.equals(this.extMap, spanImpl.extMap);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.column), Long.valueOf(this.style), this.extMap);
    }
}
