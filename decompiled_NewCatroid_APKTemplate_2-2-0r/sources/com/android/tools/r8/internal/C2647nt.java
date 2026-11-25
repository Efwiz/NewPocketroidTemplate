package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2647nt extends AbstractC0984Fi {
    final /* synthetic */ Iterable b;
    final /* synthetic */ int c = 1;

    C2647nt(Iterable iterable) {
        this.b = iterable;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        Iterable iterable = this.b;
        if (iterable instanceof List) {
            List list = (List) iterable;
            return list.subList(Math.min(list.size(), this.c), list.size()).iterator();
        }
        Iterator it = iterable.iterator();
        int i = this.c;
        int i2 = AbstractC3123uM.a;
        it.getClass();
        AbstractC3123uM.a("numberToAdvance must be nonnegative", i >= 0);
        for (int i3 = 0; i3 < i && it.hasNext(); i3++) {
            it.next();
        }
        return new C2570mt(it);
    }

    @Override // java.lang.Iterable
    public final Spliterator spliterator() {
        Iterable iterable = this.b;
        if (!(iterable instanceof List)) {
            return AbstractC1699bX.a(iterable).skip(this.c).spliterator();
        }
        List list = (List) iterable;
        return list.subList(Math.min(list.size(), this.c), list.size()).spliterator();
    }
}
