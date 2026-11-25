package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class MH extends AbstractC2222iI {
    static final /* synthetic */ boolean e = true;

    private MH(int i, long j) {
        super(i, j);
    }

    static MH a(int i, long j, C2005fV c2005fV) {
        if (e || c2005fV != null) {
            return new MH(i, j);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2222iI
    final boolean a(long j) {
        return j != i();
    }

    @Override // com.android.tools.r8.internal.ZU
    final ZU a(int i, C2005fV c2005fV) {
        long jI = i();
        c2005fV.getClass();
        return a(i, jI, c2005fV);
    }
}
