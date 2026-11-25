package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface ShortPredicate extends IntPredicate, Predicate<Short> {
    boolean test(short s);

    @Override // java.util.function.IntPredicate
    @Deprecated
    default boolean test(int i) {
        return test(SafeMath.safeIntToShort(i));
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default boolean test(Short sh) {
        return test(sh.shortValue());
    }

    default ShortPredicate and(final ShortPredicate shortPredicate) {
        Objects.requireNonNull(shortPredicate);
        return new ShortPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortPredicate$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortPredicate
            public final boolean test(short s) {
                return ShortPredicate.lambda$and$0(this.f$0, shortPredicate, s);
            }
        };
    }

    static /* synthetic */ boolean lambda$and$0(ShortPredicate _this, ShortPredicate shortPredicate, short s) {
        return _this.test(s) && shortPredicate.test(s);
    }

    @Override // java.util.function.IntPredicate
    default ShortPredicate and(IntPredicate intPredicate) {
        ShortPredicate shortPredicate$$ExternalSyntheticLambda1;
        if (intPredicate instanceof ShortPredicate) {
            shortPredicate$$ExternalSyntheticLambda1 = (ShortPredicate) intPredicate;
        } else {
            Objects.requireNonNull(intPredicate);
            shortPredicate$$ExternalSyntheticLambda1 = new ShortPredicate$$ExternalSyntheticLambda1(intPredicate);
        }
        return and(shortPredicate$$ExternalSyntheticLambda1);
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default Predicate<Short> and(Predicate<? super Short> predicate) {
        return super.and(predicate);
    }

    static /* synthetic */ boolean lambda$negate$1(ShortPredicate _this, short s) {
        return !_this.test(s);
    }

    @Override // java.util.function.IntPredicate, java.util.function.Predicate
    default ShortPredicate negate() {
        return new ShortPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortPredicate$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortPredicate
            public final boolean test(short s) {
                return ShortPredicate.lambda$negate$1(this.f$0, s);
            }
        };
    }

    default ShortPredicate or(final ShortPredicate shortPredicate) {
        Objects.requireNonNull(shortPredicate);
        return new ShortPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortPredicate$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortPredicate
            public final boolean test(short s) {
                return ShortPredicate.lambda$or$2(this.f$0, shortPredicate, s);
            }
        };
    }

    static /* synthetic */ boolean lambda$or$2(ShortPredicate _this, ShortPredicate shortPredicate, short s) {
        return _this.test(s) || shortPredicate.test(s);
    }

    @Override // java.util.function.IntPredicate
    default ShortPredicate or(IntPredicate intPredicate) {
        ShortPredicate shortPredicate$$ExternalSyntheticLambda1;
        if (intPredicate instanceof ShortPredicate) {
            shortPredicate$$ExternalSyntheticLambda1 = (ShortPredicate) intPredicate;
        } else {
            Objects.requireNonNull(intPredicate);
            shortPredicate$$ExternalSyntheticLambda1 = new ShortPredicate$$ExternalSyntheticLambda1(intPredicate);
        }
        return or(shortPredicate$$ExternalSyntheticLambda1);
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default Predicate<Short> or(Predicate<? super Short> predicate) {
        return super.or(predicate);
    }
}
