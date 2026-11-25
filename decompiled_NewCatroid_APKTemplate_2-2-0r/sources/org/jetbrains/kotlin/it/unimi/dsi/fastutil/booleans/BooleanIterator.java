package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface BooleanIterator extends PrimitiveIterator<Boolean, BooleanConsumer> {
    boolean nextBoolean();

    @Override // java.util.Iterator
    @Deprecated
    default Boolean next() {
        return Boolean.valueOf(nextBoolean());
    }

    @Override // java.util.PrimitiveIterator
    default void forEachRemaining(BooleanConsumer booleanConsumer) {
        Objects.requireNonNull(booleanConsumer);
        while (hasNext()) {
            booleanConsumer.accept(nextBoolean());
        }
    }

    @Override // java.util.Iterator
    @Deprecated
    default void forEachRemaining(Consumer<? super Boolean> consumer) {
        BooleanConsumer booleanIterable$$ExternalSyntheticLambda0;
        if (consumer instanceof BooleanConsumer) {
            booleanIterable$$ExternalSyntheticLambda0 = (BooleanConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            booleanIterable$$ExternalSyntheticLambda0 = new BooleanIterable$$ExternalSyntheticLambda0(consumer);
        }
        forEachRemaining(booleanIterable$$ExternalSyntheticLambda0);
    }
}
