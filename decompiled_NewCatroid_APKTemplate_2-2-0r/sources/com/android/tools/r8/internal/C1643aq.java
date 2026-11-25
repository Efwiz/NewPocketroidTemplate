package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1643aq extends AbstractC2891r1 {
    final /* synthetic */ C2807pq a;

    C1643aq(C2807pq c2807pq) {
        this.a = c2807pq;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean contains(Object obj) {
        return this.a.containsValue(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC2891r1, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC2703oS
    public final InterfaceC1912eK iterator() {
        return new C2731oq(this.a, 0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        return this.a.c;
    }
}
