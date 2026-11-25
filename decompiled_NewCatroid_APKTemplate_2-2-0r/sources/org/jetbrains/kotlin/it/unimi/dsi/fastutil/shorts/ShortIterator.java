package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface ShortIterator extends PrimitiveIterator<Short, ShortConsumer> {
    short nextShort();

    @Override // java.util.Iterator
    @Deprecated
    default Short next() {
        return Short.valueOf(nextShort());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.PrimitiveIterator
    default void forEachRemaining(ShortConsumer shortConsumer) {
        Objects.requireNonNull(shortConsumer);
        while (hasNext()) {
            shortConsumer.accept(nextShort());
        }
    }

    @Override // java.util.Iterator
    @Deprecated
    default void forEachRemaining(Consumer<? super Short> consumer) {
        ShortConsumer shortIterable$$ExternalSyntheticLambda0;
        if (consumer instanceof ShortConsumer) {
            shortIterable$$ExternalSyntheticLambda0 = (ShortConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            shortIterable$$ExternalSyntheticLambda0 = new ShortIterable$$ExternalSyntheticLambda0(consumer);
        }
        forEachRemaining(shortIterable$$ExternalSyntheticLambda0);
    }
}
