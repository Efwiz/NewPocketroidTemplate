package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface ShortIterable extends Iterable<Short> {
    @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
    ShortIterator iterator();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
    default ShortSpliterator spliterator() {
        return ShortSpliterators.asSpliteratorUnknownSize(iterator(), 0);
    }

    default void forEach(ShortConsumer shortConsumer) {
        Objects.requireNonNull(shortConsumer);
        iterator().forEachRemaining(shortConsumer);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
    @Deprecated
    default void forEach(Consumer<? super Short> consumer) {
        ShortConsumer shortIterable$$ExternalSyntheticLambda0;
        Objects.requireNonNull(consumer);
        if (consumer instanceof ShortConsumer) {
            shortIterable$$ExternalSyntheticLambda0 = (ShortConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            shortIterable$$ExternalSyntheticLambda0 = new ShortIterable$$ExternalSyntheticLambda0(consumer);
        }
        forEach(shortIterable$$ExternalSyntheticLambda0);
    }
}
