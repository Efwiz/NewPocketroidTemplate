package org.jetbrains.kotlin.one.util.streamex;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.ObjLongConsumer;

/* JADX INFO: loaded from: classes2.dex */
public interface LongCollector<A, R> extends MergingCollector<Long, A, R> {
    ObjLongConsumer<A> longAccumulator();

    @Override // java.util.stream.Collector
    default BiConsumer<A, Long> accumulator() {
        final ObjLongConsumer<A> objLongConsumerLongAccumulator = longAccumulator();
        Objects.requireNonNull(objLongConsumerLongAccumulator);
        return new BiConsumer() { // from class: org.jetbrains.kotlin.one.util.streamex.LongCollector$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                objLongConsumerLongAccumulator.accept(obj, ((Long) obj2).longValue());
            }
        };
    }
}
