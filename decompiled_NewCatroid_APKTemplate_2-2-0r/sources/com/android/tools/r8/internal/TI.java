package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class TI extends AbstractC1576a0 {
    final /* synthetic */ C1684bJ a;

    TI(C1684bJ c1684bJ) {
        this.a = c1684bJ;
    }

    @Override // com.android.tools.r8.internal.AbstractC1576a0
    /* JADX INFO: renamed from: a */
    public final XC iterator() {
        return new C1604aJ(this.a);
    }

    @Override // com.android.tools.r8.internal.AbstractC1576a0
    public final boolean b(long j) {
        C1684bJ c1684bJ = this.a;
        long[] jArr = c1684bJ.b;
        Object[] objArr = c1684bJ.a;
        if (!c1684bJ.d || jArr[c1684bJ.e] != j) {
            int i = c1684bJ.e;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return false;
                }
                if (objArr[i2] == null || jArr[i2] != j) {
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

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        return this.a.g;
    }
}
