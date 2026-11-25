package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class CF extends U2 {
    public CF(EnumC2527mI enumC2527mI, S10 s10, S10 s11, S10 s12) {
        super(enumC2527mI, s10, s11, s12);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 45;
    }

    @Override // com.android.tools.r8.internal.T4
    public final boolean K2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.U2
    final C3481z6.a O2() {
        return C3481z6.a.Mul;
    }

    @Override // com.android.tools.r8.internal.T4
    final double a(double d, double d2) {
        return d * d2;
    }

    @Override // com.android.tools.r8.internal.T4
    final float a(float f, float f2) {
        return f * f2;
    }

    @Override // com.android.tools.r8.internal.T4
    final int a(int i, int i2) {
        return i * i2;
    }

    @Override // com.android.tools.r8.internal.T4
    final long a(long j, long j2) {
        return j * j2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 b(int i, int i2, int i3) {
        return i == i3 ? new com.android.tools.r8.dex.code.D2(i, i3, i2) : new com.android.tools.r8.dex.code.D2(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 c(int i, int i2, int i3) {
        return i == i3 ? new com.android.tools.r8.dex.code.F2(i, i3, i2) : new com.android.tools.r8.dex.code.F2(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 d(int i, int i2) {
        return new com.android.tools.r8.dex.code.E2(i, i2);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 e(int i, int i2) {
        return new com.android.tools.r8.dex.code.I2(i, i2);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 f(int i, int i2, int i3) {
        return i == i3 ? new com.android.tools.r8.dex.code.J2(i, i3, i2) : new com.android.tools.r8.dex.code.J2(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final CF p0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 b(int i, int i2) {
        return new com.android.tools.r8.dex.code.A2(i, i2);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 c(int i, int i2) {
        return new com.android.tools.r8.dex.code.C2(i, i2);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 d(int i, int i2, int i3) {
        return new com.android.tools.r8.dex.code.G2(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 e(int i, int i2, int i3) {
        return new com.android.tools.r8.dex.code.H2(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof CF) && abstractC2412ko.p0().h == this.h;
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 a(int i, int i2, int i3) {
        if (i == i3) {
            return new com.android.tools.r8.dex.code.B2(i, i3, i2);
        }
        return new com.android.tools.r8.dex.code.B2(i, i2, i3);
    }
}
