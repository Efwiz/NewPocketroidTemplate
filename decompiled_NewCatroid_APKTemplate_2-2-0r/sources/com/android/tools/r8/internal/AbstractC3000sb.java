package com.android.tools.r8.internal;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC3000sb implements Spliterator {
    Spliterator a;
    final Spliterator b;
    final Function c;
    final InterfaceC2933rb d;
    int e;
    long f;

    AbstractC3000sb(Spliterator spliterator, Spliterator spliterator2, Function function, InterfaceC2933rb interfaceC2933rb, int i, long j) {
        this.a = spliterator;
        this.b = spliterator2;
        this.c = function;
        this.d = interfaceC2933rb;
        this.e = i;
        this.f = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) {
        this.a = (Spliterator) this.c.apply(obj);
    }

    @Override // java.util.Spliterator
    public final int characteristics() {
        return this.e;
    }

    @Override // java.util.Spliterator
    public final long estimateSize() {
        Spliterator spliterator = this.a;
        if (spliterator != null) {
            this.f = Math.max(this.f, spliterator.estimateSize());
        }
        return Math.max(this.f, 0L);
    }

    @Override // java.util.Spliterator
    public final void forEachRemaining(final Consumer consumer) {
        Spliterator spliterator = this.a;
        if (spliterator != null) {
            spliterator.forEachRemaining(consumer);
            this.a = null;
        }
        this.b.forEachRemaining(new Consumer() { // from class: com.android.tools.r8.internal.sb$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(consumer, obj);
            }
        });
        this.f = 0L;
    }

    @Override // java.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        do {
            Spliterator spliterator = this.a;
            if (spliterator != null && spliterator.tryAdvance(consumer)) {
                long j = this.f;
                if (j == Long.MAX_VALUE) {
                    return true;
                }
                this.f = j - 1;
                return true;
            }
            this.a = null;
        } while (this.b.tryAdvance(new Consumer() { // from class: com.android.tools.r8.internal.sb$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(obj);
            }
        }));
        return false;
    }

    @Override // java.util.Spliterator
    public final Spliterator trySplit() {
        Spliterator spliteratorTrySplit = this.b.trySplit();
        if (spliteratorTrySplit == null) {
            Spliterator spliterator = this.a;
            if (spliterator == null) {
                return null;
            }
            this.a = null;
            return spliterator;
        }
        int i = this.e & (-65);
        long jEstimateSize = estimateSize();
        if (jEstimateSize < Long.MAX_VALUE) {
            jEstimateSize /= 2;
            this.f -= jEstimateSize;
            this.e = i;
        }
        Spliterator spliteratorA = this.d.a(this.a, spliteratorTrySplit, this.c, i, jEstimateSize);
        this.a = null;
        return spliteratorA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Consumer consumer, Object obj) {
        Spliterator spliterator = (Spliterator) this.c.apply(obj);
        if (spliterator != null) {
            spliterator.forEachRemaining(consumer);
        }
    }
}
