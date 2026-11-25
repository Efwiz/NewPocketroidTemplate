package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface ByteSpliterator extends Spliterator.OfPrimitive<Byte, ByteConsumer, ByteSpliterator> {
    @Override // java.util.Spliterator.OfPrimitive, java.util.Spliterator
    ByteSpliterator trySplit();

    @Override // java.util.Spliterator
    @Deprecated
    default boolean tryAdvance(Consumer<? super Byte> consumer) {
        ByteConsumer byteIterable$$ExternalSyntheticLambda0;
        if (consumer instanceof ByteConsumer) {
            byteIterable$$ExternalSyntheticLambda0 = (ByteConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            byteIterable$$ExternalSyntheticLambda0 = new ByteIterable$$ExternalSyntheticLambda0(consumer);
        }
        return tryAdvance(byteIterable$$ExternalSyntheticLambda0);
    }

    @Override // java.util.Spliterator
    @Deprecated
    default void forEachRemaining(Consumer<? super Byte> consumer) {
        ByteConsumer byteIterable$$ExternalSyntheticLambda0;
        if (consumer instanceof ByteConsumer) {
            byteIterable$$ExternalSyntheticLambda0 = (ByteConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            byteIterable$$ExternalSyntheticLambda0 = new ByteIterable$$ExternalSyntheticLambda0(consumer);
        }
        forEachRemaining(byteIterable$$ExternalSyntheticLambda0);
    }

    @Override // java.util.Spliterator
    default ByteComparator getComparator() {
        throw new IllegalStateException();
    }
}
