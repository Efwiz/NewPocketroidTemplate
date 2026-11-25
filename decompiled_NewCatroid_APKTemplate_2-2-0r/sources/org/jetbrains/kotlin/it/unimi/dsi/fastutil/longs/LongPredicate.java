package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.Objects;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface LongPredicate extends java.util.function.LongPredicate, Predicate<Long> {
    @Override // java.util.function.Predicate
    @Deprecated
    default boolean test(Long l) {
        return test(l.longValue());
    }

    @Override // java.util.function.LongPredicate
    default LongPredicate and(final java.util.function.LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        return new LongPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongPredicate$$ExternalSyntheticLambda1
            @Override // java.util.function.LongPredicate
            public final boolean test(long j) {
                return LongPredicate.lambda$and$0(this.f$0, longPredicate, j);
            }
        };
    }

    static /* synthetic */ boolean lambda$and$0(LongPredicate _this, java.util.function.LongPredicate longPredicate, long j) {
        return _this.test(j) && longPredicate.test(j);
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default Predicate<Long> and(Predicate<? super Long> predicate) {
        return super.and(predicate);
    }

    static /* synthetic */ boolean lambda$negate$1(LongPredicate _this, long j) {
        return !_this.test(j);
    }

    @Override // java.util.function.LongPredicate, java.util.function.Predicate
    default LongPredicate negate() {
        return new LongPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongPredicate$$ExternalSyntheticLambda0
            @Override // java.util.function.LongPredicate
            public final boolean test(long j) {
                return LongPredicate.lambda$negate$1(this.f$0, j);
            }
        };
    }

    @Override // java.util.function.LongPredicate
    default LongPredicate or(final java.util.function.LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        return new LongPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongPredicate$$ExternalSyntheticLambda2
            @Override // java.util.function.LongPredicate
            public final boolean test(long j) {
                return LongPredicate.lambda$or$2(this.f$0, longPredicate, j);
            }
        };
    }

    static /* synthetic */ boolean lambda$or$2(LongPredicate _this, java.util.function.LongPredicate longPredicate, long j) {
        return _this.test(j) || longPredicate.test(j);
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default Predicate<Long> or(Predicate<? super Long> predicate) {
        return super.or(predicate);
    }
}
