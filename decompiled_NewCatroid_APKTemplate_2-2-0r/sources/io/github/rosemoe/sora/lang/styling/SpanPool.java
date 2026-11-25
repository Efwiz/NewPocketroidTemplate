package io.github.rosemoe.sora.lang.styling;

import io.github.rosemoe.sora.lang.styling.Span;
import java.util.concurrent.ArrayBlockingQueue;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SpanPool.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0016\u0018\u0000 \u0016*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0001\u0016BI\b\u0007\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00126\u0010\u0006\u001a2\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\u0002\u0010\rJ\u001d\u0010\u0010\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\u0011J\u0015\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0015R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R>\u0010\u0006\u001a2\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00028\u00000\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lio/github/rosemoe/sora/lang/styling/SpanPool;", "SpanT", "Lio/github/rosemoe/sora/lang/styling/Span;", "", "capacity", "", "factory", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "column", "", "style", "(ILkotlin/jvm/functions/Function2;)V", "cacheQueue", "Ljava/util/concurrent/ArrayBlockingQueue;", "obtain", "(IJ)Lio/github/rosemoe/sora/lang/styling/Span;", "offer", "", "span", "(Lio/github/rosemoe/sora/lang/styling/Span;)Z", "Companion", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SpanPool<SpanT extends Span> {
    public static final int CAPACITY_LARGE = 16384;
    public static final int CAPACITY_SMALL = 8192;
    public static final int DEFAULT_CAPACITY = 16384;
    private final ArrayBlockingQueue<SpanT> cacheQueue;
    private final Function2<Integer, Long, SpanT> factory;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SpanPool(Function2<? super Integer, ? super Long, ? extends SpanT> factory) {
        this(0, factory, 1, null);
        Intrinsics.checkNotNullParameter(factory, "factory");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SpanPool(int i, Function2<? super Integer, ? super Long, ? extends SpanT> factory) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.factory = factory;
        this.cacheQueue = new ArrayBlockingQueue<>(i);
    }

    public /* synthetic */ SpanPool(int i, Function2 function2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 16384 : i, function2);
    }

    public boolean offer(SpanT span) {
        Intrinsics.checkNotNullParameter(span, "span");
        return this.cacheQueue.offer(span);
    }

    public SpanT obtain(int column, long style) {
        SpanT spantPoll = this.cacheQueue.poll();
        if (spantPoll != null) {
            spantPoll.setColumn(column);
            spantPoll.setStyle(style);
            return spantPoll;
        }
        return this.factory.invoke(Integer.valueOf(column), Long.valueOf(style));
    }
}
