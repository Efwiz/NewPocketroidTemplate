package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class N10 extends FC {
    public N10(EnumC2527mI enumC2527mI, S10 s10, S10 s11, S10 s12) {
        super(enumC2527mI, s10, s11, s12);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 67;
    }

    @Override // com.android.tools.r8.internal.T4
    public final boolean K2() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final N10 M0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.FC
    final P7 O2() {
        return P7.c;
    }

    @Override // com.android.tools.r8.internal.T4
    final int a(int i, int i2) {
        return i >>> i2;
    }

    @Override // com.android.tools.r8.internal.T4
    final long a(long j, long j2) {
        return j >>> ((int) j2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 b(int i, int i2) {
        return new com.android.tools.r8.dex.code.f4(i, i2);
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 c(int i, int i2) {
        return new com.android.tools.r8.dex.code.i4(i, i2);
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 d(int i, int i2, int i3) {
        return new com.android.tools.r8.dex.code.j4(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.T4
    final boolean h(S10 s10) {
        return this.h == EnumC2527mI.INT && s10.L() && s10.n().E().R2();
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 b(int i, int i2, int i3) {
        throw new C3405y10("Unsupported instruction ShrIntLit16");
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 c(int i, int i2, int i3) {
        return new com.android.tools.r8.dex.code.h4(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof N10) && abstractC2412ko.M0().h == this.h;
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 a(int i, int i2, int i3) {
        return new com.android.tools.r8.dex.code.g4(i, i2, i3);
    }
}
