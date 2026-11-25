package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class YW extends Spliterators.AbstractSpliterator {
    long a;
    final /* synthetic */ Iterator b;
    final /* synthetic */ ZW c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    YW(long j, int i, Iterator it, ZW zw) {
        super(j, i);
        this.b = it;
        this.c = zw;
        this.a = 0L;
    }

    @Override // java.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        if (!this.b.hasNext()) {
            return false;
        }
        ZW zw = this.c;
        Object next = this.b.next();
        long j = this.a;
        this.a = 1 + j;
        consumer.accept(zw.a(next, j));
        return true;
    }
}
