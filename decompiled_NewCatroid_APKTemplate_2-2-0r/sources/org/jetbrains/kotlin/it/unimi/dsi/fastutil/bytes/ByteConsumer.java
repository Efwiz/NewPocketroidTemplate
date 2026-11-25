package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface ByteConsumer extends Consumer<Byte>, IntConsumer {
    void accept(byte b);

    @Override // java.util.function.IntConsumer
    @Deprecated
    default void accept(int i) {
        accept(SafeMath.safeIntToByte(i));
    }

    @Override // java.util.function.Consumer
    @Deprecated
    default void accept(Byte b) {
        accept(b.byteValue());
    }

    default ByteConsumer andThen(final ByteConsumer byteConsumer) {
        Objects.requireNonNull(byteConsumer);
        return new ByteConsumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteConsumer$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteConsumer
            public final void accept(byte b) {
                ByteConsumer.lambda$andThen$0(this.f$0, byteConsumer, b);
            }
        };
    }

    static /* synthetic */ void lambda$andThen$0(ByteConsumer _this, ByteConsumer byteConsumer, byte b) {
        _this.accept(b);
        byteConsumer.accept(b);
    }

    @Override // java.util.function.IntConsumer
    default ByteConsumer andThen(final IntConsumer intConsumer) {
        ByteConsumer byteConsumer;
        if (intConsumer instanceof ByteConsumer) {
            byteConsumer = (ByteConsumer) intConsumer;
        } else {
            Objects.requireNonNull(intConsumer);
            byteConsumer = new ByteConsumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteConsumer$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteConsumer
                public final void accept(byte b) {
                    intConsumer.accept(b);
                }
            };
        }
        return andThen(byteConsumer);
    }

    @Override // java.util.function.Consumer
    @Deprecated
    default Consumer<Byte> andThen(Consumer<? super Byte> consumer) {
        return super.andThen(consumer);
    }
}
