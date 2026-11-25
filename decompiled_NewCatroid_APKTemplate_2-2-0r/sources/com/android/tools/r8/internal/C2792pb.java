package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2792pb implements Spliterator, Consumer {
    Object a = null;
    final /* synthetic */ Spliterator b;
    final /* synthetic */ Predicate c;

    C2792pb(Spliterator spliterator, Predicate predicate) {
        this.b = spliterator;
        this.c = predicate;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.a = obj;
    }

    @Override // java.util.Spliterator
    public final int characteristics() {
        return this.b.characteristics() & 277;
    }

    @Override // java.util.Spliterator
    public final long estimateSize() {
        return this.b.estimateSize() / 2;
    }

    @Override // java.util.Spliterator
    public final Comparator getComparator() {
        return this.b.getComparator();
    }

    @Override // java.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        while (this.b.tryAdvance(this)) {
            try {
                if (this.c.test(this.a)) {
                    consumer.accept(this.a);
                    this.a = null;
                    return true;
                }
                this.a = null;
            } catch (Throwable th) {
                this.a = null;
                throw th;
            }
        }
        return false;
    }

    @Override // java.util.Spliterator
    public final Spliterator trySplit() {
        Spliterator spliteratorTrySplit = this.b.trySplit();
        if (spliteratorTrySplit == null) {
            return null;
        }
        Predicate predicate = this.c;
        int i = AbstractC3123uM.a;
        predicate.getClass();
        return new C2792pb(spliteratorTrySplit, predicate);
    }
}
