package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.util.Objects;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface FloatPredicate extends DoublePredicate, Predicate<Float> {
    boolean test(float f);

    @Override // java.util.function.DoublePredicate
    @Deprecated
    default boolean test(double d) {
        return test(SafeMath.safeDoubleToFloat(d));
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default boolean test(Float f) {
        return test(f.floatValue());
    }

    default FloatPredicate and(final FloatPredicate floatPredicate) {
        Objects.requireNonNull(floatPredicate);
        return new FloatPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatPredicate$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatPredicate
            public final boolean test(float f) {
                return FloatPredicate.lambda$and$0(this.f$0, floatPredicate, f);
            }
        };
    }

    static /* synthetic */ boolean lambda$and$0(FloatPredicate _this, FloatPredicate floatPredicate, float f) {
        return _this.test(f) && floatPredicate.test(f);
    }

    @Override // java.util.function.DoublePredicate
    default FloatPredicate and(DoublePredicate doublePredicate) {
        FloatPredicate floatPredicate$$ExternalSyntheticLambda0;
        if (doublePredicate instanceof FloatPredicate) {
            floatPredicate$$ExternalSyntheticLambda0 = (FloatPredicate) doublePredicate;
        } else {
            Objects.requireNonNull(doublePredicate);
            floatPredicate$$ExternalSyntheticLambda0 = new FloatPredicate$$ExternalSyntheticLambda0(doublePredicate);
        }
        return and(floatPredicate$$ExternalSyntheticLambda0);
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default Predicate<Float> and(Predicate<? super Float> predicate) {
        return super.and(predicate);
    }

    static /* synthetic */ boolean lambda$negate$1(FloatPredicate _this, float f) {
        return !_this.test(f);
    }

    @Override // java.util.function.DoublePredicate, java.util.function.Predicate
    default FloatPredicate negate() {
        return new FloatPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatPredicate$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatPredicate
            public final boolean test(float f) {
                return FloatPredicate.lambda$negate$1(this.f$0, f);
            }
        };
    }

    default FloatPredicate or(final FloatPredicate floatPredicate) {
        Objects.requireNonNull(floatPredicate);
        return new FloatPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatPredicate$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatPredicate
            public final boolean test(float f) {
                return FloatPredicate.lambda$or$2(this.f$0, floatPredicate, f);
            }
        };
    }

    static /* synthetic */ boolean lambda$or$2(FloatPredicate _this, FloatPredicate floatPredicate, float f) {
        return _this.test(f) || floatPredicate.test(f);
    }

    @Override // java.util.function.DoublePredicate
    default FloatPredicate or(DoublePredicate doublePredicate) {
        FloatPredicate floatPredicate$$ExternalSyntheticLambda0;
        if (doublePredicate instanceof FloatPredicate) {
            floatPredicate$$ExternalSyntheticLambda0 = (FloatPredicate) doublePredicate;
        } else {
            Objects.requireNonNull(doublePredicate);
            floatPredicate$$ExternalSyntheticLambda0 = new FloatPredicate$$ExternalSyntheticLambda0(doublePredicate);
        }
        return or(floatPredicate$$ExternalSyntheticLambda0);
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default Predicate<Float> or(Predicate<? super Float> predicate) {
        return super.or(predicate);
    }
}
