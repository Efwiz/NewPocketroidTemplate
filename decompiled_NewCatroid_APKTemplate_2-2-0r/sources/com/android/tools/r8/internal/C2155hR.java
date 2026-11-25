package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2155hR extends AbstractC3177v1 {
    final /* synthetic */ C2537mR a;

    private C2155hR(C2537mR c2537mR) {
        this.a = c2537mR;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return this.a.containsKey(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC2891r1, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC2703oS
    public final InterfaceC1912eK iterator() {
        return new C2077gR(this.a);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        C2537mR c2537mR = this.a;
        int i = c2537mR.g;
        c2537mR.c(obj);
        return this.a.g != i;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.g;
    }

    /* synthetic */ C2155hR(C2537mR c2537mR, int i) {
        this(c2537mR);
    }
}
