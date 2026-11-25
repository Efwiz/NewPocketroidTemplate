package org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface CharSpliterator extends Spliterator.OfPrimitive<Character, CharConsumer, CharSpliterator> {
    @Override // java.util.Spliterator.OfPrimitive, java.util.Spliterator
    CharSpliterator trySplit();

    @Override // java.util.Spliterator
    @Deprecated
    default boolean tryAdvance(Consumer<? super Character> consumer) {
        CharConsumer charIterator$$ExternalSyntheticLambda0;
        if (consumer instanceof CharConsumer) {
            charIterator$$ExternalSyntheticLambda0 = (CharConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            charIterator$$ExternalSyntheticLambda0 = new CharIterator$$ExternalSyntheticLambda0(consumer);
        }
        return tryAdvance(charIterator$$ExternalSyntheticLambda0);
    }

    @Override // java.util.Spliterator
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

    @Override // java.util.Spliterator
    default CharComparator getComparator() {
        throw new IllegalStateException();
    }
}
