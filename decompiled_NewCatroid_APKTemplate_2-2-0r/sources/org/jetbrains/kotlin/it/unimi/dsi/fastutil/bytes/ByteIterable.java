package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface ByteIterable extends Iterable<Byte> {
    @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterable
    ByteIterator iterator();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList, java.util.List
    default ByteSpliterator spliterator() {
        return ByteSpliterators.asSpliteratorUnknownSize(iterator(), 0);
    }

    default void forEach(ByteConsumer byteConsumer) {
        Objects.requireNonNull(byteConsumer);
        iterator().forEachRemaining(byteConsumer);
    }

    @Override // java.lang.Iterable
    @Deprecated
    default void forEach(Consumer<? super Byte> consumer) {
        ByteConsumer byteIterable$$ExternalSyntheticLambda0;
        Objects.requireNonNull(consumer);
        if (consumer instanceof ByteConsumer) {
            byteIterable$$ExternalSyntheticLambda0 = (ByteConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            byteIterable$$ExternalSyntheticLambda0 = new ByteIterable$$ExternalSyntheticLambda0(consumer);
        }
        forEach(byteIterable$$ExternalSyntheticLambda0);
    }
}
