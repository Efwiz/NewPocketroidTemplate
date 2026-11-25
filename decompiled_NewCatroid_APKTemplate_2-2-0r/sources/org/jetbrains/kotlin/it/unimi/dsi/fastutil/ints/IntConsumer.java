package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface IntConsumer extends Consumer<Integer>, java.util.function.IntConsumer {
    @Override // java.util.function.Consumer
    @Deprecated
    default void accept(Integer num) {
        accept(num.intValue());
    }

    @Override // java.util.function.IntConsumer
    default IntConsumer andThen(final java.util.function.IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        return new IntConsumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntConsumer$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                IntConsumer.lambda$andThen$0(this.f$0, intConsumer, i);
            }
        };
    }

    static /* synthetic */ void lambda$andThen$0(IntConsumer _this, java.util.function.IntConsumer intConsumer, int i) {
        _this.accept(i);
        intConsumer.accept(i);
    }

    @Override // java.util.function.Consumer
    @Deprecated
    default Consumer<Integer> andThen(Consumer<? super Integer> consumer) {
        return super.andThen(consumer);
    }
}
