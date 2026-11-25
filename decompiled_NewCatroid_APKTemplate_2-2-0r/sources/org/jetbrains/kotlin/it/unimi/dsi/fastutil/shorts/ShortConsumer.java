package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface ShortConsumer extends Consumer<Short>, IntConsumer {
    void accept(short s);

    @Override // java.util.function.IntConsumer
    @Deprecated
    default void accept(int i) {
        accept(SafeMath.safeIntToShort(i));
    }

    @Override // java.util.function.Consumer
    @Deprecated
    default void accept(Short sh) {
        accept(sh.shortValue());
    }

    default ShortConsumer andThen(final ShortConsumer shortConsumer) {
        Objects.requireNonNull(shortConsumer);
        return new ShortConsumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortConsumer$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortConsumer
            public final void accept(short s) {
                ShortConsumer.lambda$andThen$0(this.f$0, shortConsumer, s);
            }
        };
    }

    static /* synthetic */ void lambda$andThen$0(ShortConsumer _this, ShortConsumer shortConsumer, short s) {
        _this.accept(s);
        shortConsumer.accept(s);
    }

    @Override // java.util.function.IntConsumer
    default ShortConsumer andThen(final IntConsumer intConsumer) {
        ShortConsumer shortConsumer;
        if (intConsumer instanceof ShortConsumer) {
            shortConsumer = (ShortConsumer) intConsumer;
        } else {
            Objects.requireNonNull(intConsumer);
            shortConsumer = new ShortConsumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortConsumer$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortConsumer
                public final void accept(short s) {
                    intConsumer.accept(s);
                }
            };
        }
        return andThen(shortConsumer);
    }

    @Override // java.util.function.Consumer
    @Deprecated
    default Consumer<Short> andThen(Consumer<? super Short> consumer) {
        return super.andThen(consumer);
    }
}
