package org.jetbrains.kotlin.one.util.streamex;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.ObjDoubleConsumer;

/* JADX INFO: loaded from: classes2.dex */
public interface DoubleCollector<A, R> extends MergingCollector<Double, A, R> {
    ObjDoubleConsumer<A> doubleAccumulator();

    @Override // java.util.stream.Collector
    default BiConsumer<A, Double> accumulator() {
        final ObjDoubleConsumer<A> objDoubleConsumerDoubleAccumulator = doubleAccumulator();
        Objects.requireNonNull(objDoubleConsumerDoubleAccumulator);
        return new BiConsumer() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleCollector$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                objDoubleConsumerDoubleAccumulator.accept(obj, ((Double) obj2).doubleValue());
            }
        };
    }
}
