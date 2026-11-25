package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface LongConsumer extends Consumer<Long>, java.util.function.LongConsumer {
    @Override // java.util.function.Consumer
    @Deprecated
    default void accept(Long l) {
        accept(l.longValue());
    }

    @Override // java.util.function.LongConsumer
    default LongConsumer andThen(final java.util.function.LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        return new LongConsumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongConsumer$$ExternalSyntheticLambda0
            @Override // java.util.function.LongConsumer
            public final void accept(long j) {
                LongConsumer.lambda$andThen$0(this.f$0, longConsumer, j);
            }
        };
    }

    static /* synthetic */ void lambda$andThen$0(LongConsumer _this, java.util.function.LongConsumer longConsumer, long j) {
        _this.accept(j);
        longConsumer.accept(j);
    }

    @Override // java.util.function.Consumer
    @Deprecated
    default Consumer<Long> andThen(Consumer<? super Long> consumer) {
        return super.andThen(consumer);
    }
}
