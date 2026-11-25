package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class QR extends AbstractC1576a0 {
    final /* synthetic */ YR a;

    QR(YR yr) {
        this.a = yr;
    }

    @Override // com.android.tools.r8.internal.AbstractC1576a0
    /* JADX INFO: renamed from: a */
    public final XC iterator() {
        return new XR(this.a);
    }

    @Override // com.android.tools.r8.internal.AbstractC1576a0
    public final boolean b(long j) {
        YR yr = this.a;
        long[] jArr = yr.b;
        Object[] objArr = yr.a;
        if (!yr.d || jArr[yr.e] != j) {
            int i = yr.e;
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
