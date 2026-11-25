package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2861qb implements Spliterator {
    private final Spliterator.OfInt a;
    final /* synthetic */ IntFunction b;
    final /* synthetic */ int c;
    final /* synthetic */ Comparator d;

    C2861qb(Spliterator.OfInt ofInt, IntFunction intFunction, int i, Comparator comparator) {
        this.b = intFunction;
        this.c = i;
        this.d = comparator;
        this.a = ofInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, IntFunction intFunction, int i) {
        consumer.accept(intFunction.apply(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Consumer consumer, IntFunction intFunction, int i) {
        consumer.accept(intFunction.apply(i));
    }

    @Override // java.util.Spliterator
    public final int characteristics() {
        return this.c | 16464;
    }

    @Override // java.util.Spliterator
    public final long estimateSize() {
        return this.a.estimateSize();
    }

    @Override // java.util.Spliterator
    public final void forEachRemaining(final Consumer consumer) {
        Spliterator.OfInt ofInt = this.a;
        final IntFunction intFunction = this.b;
        ofInt.forEachRemaining(new IntConsumer() { // from class: com.android.tools.r8.internal.qb$$ExternalSyntheticLambda1
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                C2861qb.a(consumer, intFunction, i);
            }
        });
    }

    @Override // java.util.Spliterator
    public final Comparator getComparator() {
        if (hasCharacteristics(4)) {
            return this.d;
        }
        throw new IllegalStateException();
    }

    @Override // java.util.Spliterator
    public final boolean tryAdvance(final Consumer consumer) {
        Spliterator.OfInt ofInt = this.a;
        final IntFunction intFunction = this.b;
        return ofInt.tryAdvance(new IntConsumer() { // from class: com.android.tools.r8.internal.qb$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                C2861qb.b(consumer, intFunction, i);
            }
        });
    }

    @Override // java.util.Spliterator
    public final Spliterator trySplit() {
        Spliterator.OfInt ofIntTrySplit = this.a.trySplit();
        if (ofIntTrySplit == null) {
            return null;
        }
        return new C2861qb(ofIntTrySplit, this.b, this.c, this.d);
    }
}
