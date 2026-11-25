package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface BooleanIterable extends Iterable<Boolean> {
    @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterable
    BooleanIterator iterator();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList, java.util.List
    default BooleanSpliterator spliterator() {
        return BooleanSpliterators.asSpliteratorUnknownSize(iterator(), 0);
    }

    default void forEach(BooleanConsumer booleanConsumer) {
        Objects.requireNonNull(booleanConsumer);
        iterator().forEachRemaining(booleanConsumer);
    }

    @Override // java.lang.Iterable
    @Deprecated
    default void forEach(Consumer<? super Boolean> consumer) {
        BooleanConsumer booleanIterable$$ExternalSyntheticLambda0;
        Objects.requireNonNull(consumer);
        if (consumer instanceof BooleanConsumer) {
            booleanIterable$$ExternalSyntheticLambda0 = (BooleanConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            booleanIterable$$ExternalSyntheticLambda0 = new BooleanIterable$$ExternalSyntheticLambda0(consumer);
        }
        forEach(booleanIterable$$ExternalSyntheticLambda0);
    }
}
