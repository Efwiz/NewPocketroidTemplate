package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.Objects;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface IntPredicate extends java.util.function.IntPredicate, Predicate<Integer> {
    @Override // java.util.function.Predicate
    @Deprecated
    default boolean test(Integer num) {
        return test(num.intValue());
    }

    @Override // java.util.function.IntPredicate
    default IntPredicate and(final java.util.function.IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return new IntPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntPredicate$$ExternalSyntheticLambda1
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return IntPredicate.lambda$and$0(this.f$0, intPredicate, i);
            }
        };
    }

    static /* synthetic */ boolean lambda$and$0(IntPredicate _this, java.util.function.IntPredicate intPredicate, int i) {
        return _this.test(i) && intPredicate.test(i);
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default Predicate<Integer> and(Predicate<? super Integer> predicate) {
        return super.and(predicate);
    }

    static /* synthetic */ boolean lambda$negate$1(IntPredicate _this, int i) {
        return !_this.test(i);
    }

    @Override // java.util.function.IntPredicate, java.util.function.Predicate
    default IntPredicate negate() {
        return new IntPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntPredicate$$ExternalSyntheticLambda2
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return IntPredicate.lambda$negate$1(this.f$0, i);
            }
        };
    }

    @Override // java.util.function.IntPredicate
    default IntPredicate or(final java.util.function.IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return new IntPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntPredicate$$ExternalSyntheticLambda0
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return IntPredicate.lambda$or$2(this.f$0, intPredicate, i);
            }
        };
    }

    static /* synthetic */ boolean lambda$or$2(IntPredicate _this, java.util.function.IntPredicate intPredicate, int i) {
        return _this.test(i) || intPredicate.test(i);
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default Predicate<Integer> or(Predicate<? super Integer> predicate) {
        return super.or(predicate);
    }
}
