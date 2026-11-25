package org.jetbrains.kotlin.one.util.streamex;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.ObjIntConsumer;

/* JADX INFO: loaded from: classes2.dex */
public interface IntCollector<A, R> extends MergingCollector<Integer, A, R> {
    ObjIntConsumer<A> intAccumulator();

    @Override // java.util.stream.Collector
    default BiConsumer<A, Integer> accumulator() {
        final ObjIntConsumer<A> objIntConsumerIntAccumulator = intAccumulator();
        Objects.requireNonNull(objIntConsumerIntAccumulator);
        return new BiConsumer() { // from class: org.jetbrains.kotlin.one.util.streamex.IntCollector$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                objIntConsumerIntAccumulator.accept(obj, ((Integer) obj2).intValue());
            }
        };
    }
}
