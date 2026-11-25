package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3316wt extends AbstractC3028t {
    static final AbstractC3253w10 e = new C3316wt(new Object[0], 0, 0);
    private final Object[] c;
    private final int d;

    C3316wt(Object[] objArr, int i, int i2) {
        super(i, i2);
        this.c = objArr;
        this.d = 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC3028t
    protected final Object a(int i) {
        return this.c[this.d + i];
    }
}
