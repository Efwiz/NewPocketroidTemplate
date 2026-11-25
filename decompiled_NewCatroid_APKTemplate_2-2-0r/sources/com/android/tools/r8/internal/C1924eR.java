package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1924eR extends AbstractC2122h {
    final /* synthetic */ C2537mR a;

    C1924eR(C2537mR c2537mR) {
        this.a = c2537mR;
    }

    @Override // com.android.tools.r8.internal.AbstractC2122h
    public final boolean b(boolean z) {
        C2537mR c2537mR = this.a;
        boolean[] zArr = c2537mR.b;
        Object[] objArr = c2537mR.a;
        if (!c2537mR.d || zArr[c2537mR.e] != z) {
            int i = c2537mR.e;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return false;
                }
                if (objArr[i2] == null || zArr[i2] != z) {
                    i = i2;
                }
            }
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final void clear() {
        this.a.clear();
    }

    @Override // com.android.tools.r8.internal.AbstractC2122h, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC1889e5, java.util.Set
    public final InterfaceC2282j5 iterator() {
        return new C2463lR(this.a);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        return this.a.g;
    }
}
