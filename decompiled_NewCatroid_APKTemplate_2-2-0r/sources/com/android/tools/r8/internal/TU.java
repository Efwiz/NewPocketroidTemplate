package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class TU extends FC {
    public TU(EnumC2527mI enumC2527mI, S10 s10, S10 s11, S10 s12) {
        super(enumC2527mI, s10, s11, s12);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final TU D0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 57;
    }

    @Override // com.android.tools.r8.internal.T4
    public final boolean K2() {
        return false;
    }

    @Override // com.android.tools.r8.internal.FC
    final P7 O2() {
        return P7.a;
    }

    @Override // com.android.tools.r8.internal.T4
    final int a(int i, int i2) {
        return i << i2;
    }

    @Override // com.android.tools.r8.internal.T4
    final long a(long j, long j2) {
        return j << ((int) j2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 b(int i, int i2) {
        return new com.android.tools.r8.dex.code.C3(i, i2);
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 c(int i, int i2) {
        return new com.android.tools.r8.dex.code.F3(i, i2);
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 d(int i, int i2, int i3) {
        return new com.android.tools.r8.dex.code.G3(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.T4
    final boolean h(S10 s10) {
        return this.h == EnumC2527mI.INT && s10.L() && s10.n().E().R2();
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 b(int i, int i2, int i3) {
        throw new C3405y10("Unsupported instruction ShlIntLit16");
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 c(int i, int i2, int i3) {
        return new com.android.tools.r8.dex.code.E3(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof TU) && abstractC2412ko.D0().h == this.h;
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 a(int i, int i2, int i3) {
        return new com.android.tools.r8.dex.code.D3(i, i2, i3);
    }
}
