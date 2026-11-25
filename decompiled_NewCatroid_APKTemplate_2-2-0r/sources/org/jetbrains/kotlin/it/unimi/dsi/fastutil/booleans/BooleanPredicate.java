package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import java.util.function.Predicate;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface BooleanPredicate extends Predicate<Boolean> {
    boolean test(boolean z);

    @Override // java.util.function.Predicate
    @Deprecated
    default boolean test(Boolean bool) {
        return test(bool.booleanValue());
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default Predicate<Boolean> and(Predicate<? super Boolean> predicate) {
        return super.and(predicate);
    }

    static /* synthetic */ boolean lambda$negate$3(BooleanPredicate _this, boolean z) {
        return !_this.test(z);
    }

    @Override // java.util.function.Predicate
    default Predicate<Boolean> negate() {
        return new BooleanPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanPredicate$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanPredicate
            public final boolean test(boolean z) {
                return BooleanPredicate.lambda$negate$3(this.f$0, z);
            }
        };
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default Predicate<Boolean> or(Predicate<? super Boolean> predicate) {
        return super.or(predicate);
    }
}
