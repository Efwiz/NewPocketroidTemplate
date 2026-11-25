package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface BytePredicate extends IntPredicate, Predicate<Byte> {
    boolean test(byte b);

    @Override // java.util.function.IntPredicate
    @Deprecated
    default boolean test(int i) {
        return test(SafeMath.safeIntToByte(i));
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default boolean test(Byte b) {
        return test(b.byteValue());
    }

    default BytePredicate and(final BytePredicate bytePredicate) {
        Objects.requireNonNull(bytePredicate);
        return new BytePredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.BytePredicate$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.BytePredicate
            public final boolean test(byte b) {
                return BytePredicate.lambda$and$0(this.f$0, bytePredicate, b);
            }
        };
    }

    static /* synthetic */ boolean lambda$and$0(BytePredicate _this, BytePredicate bytePredicate, byte b) {
        return _this.test(b) && bytePredicate.test(b);
    }

    @Override // java.util.function.IntPredicate
    default BytePredicate and(IntPredicate intPredicate) {
        BytePredicate bytePredicate$$ExternalSyntheticLambda1;
        if (intPredicate instanceof BytePredicate) {
            bytePredicate$$ExternalSyntheticLambda1 = (BytePredicate) intPredicate;
        } else {
            Objects.requireNonNull(intPredicate);
            bytePredicate$$ExternalSyntheticLambda1 = new BytePredicate$$ExternalSyntheticLambda1(intPredicate);
        }
        return and(bytePredicate$$ExternalSyntheticLambda1);
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default Predicate<Byte> and(Predicate<? super Byte> predicate) {
        return super.and(predicate);
    }

    static /* synthetic */ boolean lambda$negate$1(BytePredicate _this, byte b) {
        return !_this.test(b);
    }

    @Override // java.util.function.IntPredicate, java.util.function.Predicate
    default BytePredicate negate() {
        return new BytePredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.BytePredicate$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.BytePredicate
            public final boolean test(byte b) {
                return BytePredicate.lambda$negate$1(this.f$0, b);
            }
        };
    }

    default BytePredicate or(final BytePredicate bytePredicate) {
        Objects.requireNonNull(bytePredicate);
        return new BytePredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.BytePredicate$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.BytePredicate
            public final boolean test(byte b) {
                return BytePredicate.lambda$or$2(this.f$0, bytePredicate, b);
            }
        };
    }

    static /* synthetic */ boolean lambda$or$2(BytePredicate _this, BytePredicate bytePredicate, byte b) {
        return _this.test(b) || bytePredicate.test(b);
    }

    @Override // java.util.function.IntPredicate
    default BytePredicate or(IntPredicate intPredicate) {
        BytePredicate bytePredicate$$ExternalSyntheticLambda1;
        if (intPredicate instanceof BytePredicate) {
            bytePredicate$$ExternalSyntheticLambda1 = (BytePredicate) intPredicate;
        } else {
            Objects.requireNonNull(intPredicate);
            bytePredicate$$ExternalSyntheticLambda1 = new BytePredicate$$ExternalSyntheticLambda1(intPredicate);
        }
        return or(bytePredicate$$ExternalSyntheticLambda1);
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default Predicate<Byte> or(Predicate<? super Byte> predicate) {
        return super.or(predicate);
    }
}
