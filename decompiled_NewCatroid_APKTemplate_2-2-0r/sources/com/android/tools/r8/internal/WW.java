package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class WW extends Spliterators.AbstractSpliterator {
    final /* synthetic */ Iterator a;
    final /* synthetic */ Iterator b;
    final /* synthetic */ BiFunction c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    WW(long j, int i, Iterator it, Iterator it2, BiFunction biFunction) {
        super(j, i);
        this.a = it;
        this.b = it2;
        this.c = biFunction;
    }

    @Override // java.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        if (!this.a.hasNext() || !this.b.hasNext()) {
            return false;
        }
        consumer.accept(this.c.apply(this.a.next(), this.b.next()));
        return true;
    }
}
