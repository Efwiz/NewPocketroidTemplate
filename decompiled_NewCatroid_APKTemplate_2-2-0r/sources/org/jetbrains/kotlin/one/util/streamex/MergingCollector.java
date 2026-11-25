package org.jetbrains.kotlin.one.util.streamex;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;

/* JADX INFO: loaded from: classes2.dex */
interface MergingCollector<T, A, R> extends Collector<T, A, R> {
    BiConsumer<A, A> merger();

    @Override // java.util.stream.Collector
    default BinaryOperator<A> combiner() {
        final BiConsumer<A, A> biConsumerMerger = merger();
        return new BinaryOperator() { // from class: org.jetbrains.kotlin.one.util.streamex.MergingCollector$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return MergingCollector.lambda$combiner$0(biConsumerMerger, obj, obj2);
            }
        };
    }

    static /* synthetic */ Object lambda$combiner$0(BiConsumer biConsumer, Object obj, Object obj2) {
        biConsumer.accept(obj, obj2);
        return obj;
    }
}
