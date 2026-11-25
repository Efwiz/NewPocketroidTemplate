package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.util.Objects;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface DoublePredicate extends java.util.function.DoublePredicate, Predicate<Double> {
    @Override // java.util.function.Predicate
    @Deprecated
    default boolean test(Double d) {
        return test(d.doubleValue());
    }

    @Override // java.util.function.DoublePredicate
    default DoublePredicate and(final java.util.function.DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        return new DoublePredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoublePredicate$$ExternalSyntheticLambda1
            @Override // java.util.function.DoublePredicate
            public final boolean test(double d) {
                return DoublePredicate.lambda$and$0(this.f$0, doublePredicate, d);
            }
        };
    }

    static /* synthetic */ boolean lambda$and$0(DoublePredicate _this, java.util.function.DoublePredicate doublePredicate, double d) {
        return _this.test(d) && doublePredicate.test(d);
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default Predicate<Double> and(Predicate<? super Double> predicate) {
        return super.and(predicate);
    }

    static /* synthetic */ boolean lambda$negate$1(DoublePredicate _this, double d) {
        return !_this.test(d);
    }

    @Override // java.util.function.DoublePredicate, java.util.function.Predicate
    default DoublePredicate negate() {
        return new DoublePredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoublePredicate$$ExternalSyntheticLambda2
            @Override // java.util.function.DoublePredicate
            public final boolean test(double d) {
                return DoublePredicate.lambda$negate$1(this.f$0, d);
            }
        };
    }

    @Override // java.util.function.DoublePredicate
    default DoublePredicate or(final java.util.function.DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        return new DoublePredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoublePredicate$$ExternalSyntheticLambda0
            @Override // java.util.function.DoublePredicate
            public final boolean test(double d) {
                return DoublePredicate.lambda$or$2(this.f$0, doublePredicate, d);
            }
        };
    }

    static /* synthetic */ boolean lambda$or$2(DoublePredicate _this, java.util.function.DoublePredicate doublePredicate, double d) {
        return _this.test(d) || doublePredicate.test(d);
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default Predicate<Double> or(Predicate<? super Double> predicate) {
        return super.or(predicate);
    }
}
