package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface DoubleConsumer extends Consumer<Double>, java.util.function.DoubleConsumer {
    @Override // java.util.function.Consumer
    @Deprecated
    default void accept(Double d) {
        accept(d.doubleValue());
    }

    @Override // java.util.function.DoubleConsumer
    default DoubleConsumer andThen(final java.util.function.DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        return new DoubleConsumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleConsumer$$ExternalSyntheticLambda0
            @Override // java.util.function.DoubleConsumer
            public final void accept(double d) {
                DoubleConsumer.lambda$andThen$0(this.f$0, doubleConsumer, d);
            }
        };
    }

    static /* synthetic */ void lambda$andThen$0(DoubleConsumer _this, java.util.function.DoubleConsumer doubleConsumer, double d) {
        _this.accept(d);
        doubleConsumer.accept(d);
    }

    @Override // java.util.function.Consumer
    @Deprecated
    default Consumer<Double> andThen(Consumer<? super Double> consumer) {
        return super.andThen(consumer);
    }
}
