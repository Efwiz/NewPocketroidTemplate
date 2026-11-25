package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface ByteUnaryOperator extends IntUnaryOperator, UnaryOperator<Byte> {
    byte apply(byte b);

    @Override // java.util.function.IntUnaryOperator
    @Deprecated
    default int applyAsInt(int i) {
        return apply(SafeMath.safeIntToByte(i));
    }

    @Override // java.util.function.Function
    @Deprecated
    default Byte apply(Byte b) {
        return Byte.valueOf(apply(b.byteValue()));
    }
}
