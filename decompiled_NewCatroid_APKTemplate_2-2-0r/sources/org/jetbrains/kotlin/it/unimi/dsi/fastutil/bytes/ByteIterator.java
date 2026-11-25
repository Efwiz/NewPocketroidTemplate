package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface ByteIterator extends PrimitiveIterator<Byte, ByteConsumer> {
    byte nextByte();

    @Override // java.util.Iterator
    @Deprecated
    default Byte next() {
        return Byte.valueOf(nextByte());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.PrimitiveIterator
    default void forEachRemaining(ByteConsumer byteConsumer) {
        Objects.requireNonNull(byteConsumer);
        while (hasNext()) {
            byteConsumer.accept(nextByte());
        }
    }

    @Override // java.util.Iterator
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
}
