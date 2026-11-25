package org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars;

import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface CharIterator extends PrimitiveIterator<Character, CharConsumer> {
    char nextChar();

    @Override // java.util.Iterator
    @Deprecated
    default Character next() {
        return Character.valueOf(nextChar());
    }

    @Override // java.util.PrimitiveIterator
    default void forEachRemaining(CharConsumer charConsumer) {
        Objects.requireNonNull(charConsumer);
        while (hasNext()) {
            charConsumer.accept(nextChar());
        }
    }

    @Override // java.util.Iterator
    @Deprecated
    default void forEachRemaining(Consumer<? super Character> consumer) {
        CharConsumer charIterator$$ExternalSyntheticLambda0;
        if (consumer instanceof CharConsumer) {
            charIterator$$ExternalSyntheticLambda0 = (CharConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            charIterator$$ExternalSyntheticLambda0 = new CharIterator$$ExternalSyntheticLambda0(consumer);
        }
        forEachRemaining(charIterator$$ExternalSyntheticLambda0);
    }
}
