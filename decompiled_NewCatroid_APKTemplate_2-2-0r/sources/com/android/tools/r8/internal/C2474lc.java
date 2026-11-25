package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2474lc extends AbstractC1704bc {
    private B1 a;

    C2474lc(B1 b1) {
        this.a = b1;
    }

    public static AbstractC1860di a(B1 b1) {
        return b1.isUnknown() ? C2276j10.e() : new C2474lc(b1);
    }

    @Override // com.android.tools.r8.internal.AbstractC1860di
    public final C2474lc c() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1860di
    public final B1 a(C1 c1) {
        return this.a;
    }

    public final AbstractC1860di a(B1 b1, C1 c1) {
        if (b1.isUnknown()) {
            return C2276j10.e();
        }
        B1 b1A = this.a.a(b1, c1, false, false);
        this.a = b1A;
        return b1A.isUnknown() ? C2276j10.e() : this;
    }
}
