package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface FloatConsumer extends Consumer<Float>, DoubleConsumer {
    void accept(float f);

    @Override // java.util.function.DoubleConsumer
    @Deprecated
    default void accept(double d) {
        accept(SafeMath.safeDoubleToFloat(d));
    }

    @Override // java.util.function.Consumer
    @Deprecated
    default void accept(Float f) {
        accept(f.floatValue());
    }

    default FloatConsumer andThen(final FloatConsumer floatConsumer) {
        Objects.requireNonNull(floatConsumer);
        return new FloatConsumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatConsumer$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatConsumer
            public final void accept(float f) {
                FloatConsumer.lambda$andThen$0(this.f$0, floatConsumer, f);
            }
        };
    }

    static /* synthetic */ void lambda$andThen$0(FloatConsumer _this, FloatConsumer floatConsumer, float f) {
        _this.accept(f);
        floatConsumer.accept(f);
    }

    @Override // java.util.function.DoubleConsumer
    default FloatConsumer andThen(final DoubleConsumer doubleConsumer) {
        FloatConsumer floatConsumer;
        if (doubleConsumer instanceof FloatConsumer) {
            floatConsumer = (FloatConsumer) doubleConsumer;
        } else {
            Objects.requireNonNull(doubleConsumer);
            floatConsumer = new FloatConsumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatConsumer$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatConsumer
                public final void accept(float f) {
                    doubleConsumer.accept(f);
                }
            };
        }
        return andThen(floatConsumer);
    }

    @Override // java.util.function.Consumer
    @Deprecated
    default Consumer<Float> andThen(Consumer<? super Float> consumer) {
        return super.andThen(consumer);
    }
}
