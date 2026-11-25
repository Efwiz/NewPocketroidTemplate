package org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface CharConsumer extends Consumer<Character>, IntConsumer {
    void accept(char c);

    @Override // java.util.function.IntConsumer
    @Deprecated
    default void accept(int i) {
        accept(SafeMath.safeIntToChar(i));
    }

    @Override // java.util.function.Consumer
    @Deprecated
    default void accept(Character ch) {
        accept(ch.charValue());
    }

    default CharConsumer andThen(final CharConsumer charConsumer) {
        Objects.requireNonNull(charConsumer);
        return new CharConsumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars.CharConsumer$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars.CharConsumer
            public final void accept(char c) {
                CharConsumer.lambda$andThen$0(this.f$0, charConsumer, c);
            }
        };
    }

    static /* synthetic */ void lambda$andThen$0(CharConsumer _this, CharConsumer charConsumer, char c) {
        _this.accept(c);
        charConsumer.accept(c);
    }

    @Override // java.util.function.IntConsumer
    default CharConsumer andThen(final IntConsumer intConsumer) {
        CharConsumer charConsumer;
        if (intConsumer instanceof CharConsumer) {
            charConsumer = (CharConsumer) intConsumer;
        } else {
            Objects.requireNonNull(intConsumer);
            charConsumer = new CharConsumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars.CharConsumer$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars.CharConsumer
                public final void accept(char c) {
                    intConsumer.accept(c);
                }
            };
        }
        return andThen(charConsumer);
    }

    @Override // java.util.function.Consumer
    @Deprecated
    default Consumer<Character> andThen(Consumer<? super Character> consumer) {
        return super.andThen(consumer);
    }
}
