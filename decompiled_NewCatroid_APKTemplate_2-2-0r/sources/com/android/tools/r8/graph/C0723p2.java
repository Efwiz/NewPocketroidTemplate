package com.android.tools.r8.graph;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.graph.p2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0723p2 implements Iterator {
    private C0729q2 a = a();
    private final Iterator b;
    final /* synthetic */ Q0 c;

    C0723p2(Iterable iterable, Q0 q0) {
        this.c = q0;
        this.b = iterable.iterator();
    }

    private C0729q2 a() {
        while (this.b.hasNext()) {
            AbstractC0727q0 abstractC0727q0A = this.c.a((C0767v2) this.b.next());
            if (abstractC0727q0A != null && abstractC0727q0A.e0()) {
                return abstractC0727q0A.a0();
            }
        }
        return null;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a != null;
    }

    @Override // java.util.Iterator
    public final Object next() {
        C0729q2 c0729q2 = this.a;
        this.a = a();
        return c0729q2;
    }
}
