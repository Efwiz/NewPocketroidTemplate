package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0428d3;
import com.android.tools.r8.dex.code.C0433e3;
import com.android.tools.r8.dex.code.C0438f3;
import com.android.tools.r8.dex.code.C0443g3;
import com.android.tools.r8.dex.code.C0448h3;
import com.android.tools.r8.dex.code.C0453i3;
import com.android.tools.r8.dex.code.C0458j3;
import com.android.tools.r8.dex.code.C0463k3;
import com.android.tools.r8.dex.code.C0468l3;
import com.android.tools.r8.dex.code.C0473m3;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class US extends U2 {
    public US(EnumC2527mI enumC2527mI, S10 s10, S10 s11, S10 s12) {
        super(enumC2527mI, s10, s11, s12);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final US A0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 55;
    }

    @Override // com.android.tools.r8.internal.T4
    public final boolean K2() {
        return false;
    }

    @Override // com.android.tools.r8.internal.U2
    final C3481z6.a O2() {
        return C3481z6.a.Rem;
    }

    @Override // com.android.tools.r8.internal.T4
    final double a(double d, double d2) {
        return d % d2;
    }

    @Override // com.android.tools.r8.internal.T4
    final float a(float f, float f2) {
        return f % f2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 b(int i, int i2, int i3) {
        return new C0443g3(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 c(int i, int i2, int i3) {
        return new C0453i3(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 d(int i, int i2) {
        return new C0448h3(i, i2);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 e(int i, int i2) {
        return new C0468l3(i, i2);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 f(int i, int i2, int i3) {
        return new C0473m3(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        EnumC2527mI enumC2527mI = this.h;
        return (enumC2527mI == EnumC2527mI.DOUBLE || enumC2527mI == EnumC2527mI.FLOAT) ? false : true;
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 b(int i, int i2) {
        return new C0428d3(i, i2);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 c(int i, int i2) {
        return new C0438f3(i, i2);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 d(int i, int i2, int i3) {
        return new C0458j3(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 e(int i, int i2, int i3) {
        return new C0463k3(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof US) && abstractC2412ko.A0().h == this.h;
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 a(int i, int i2, int i3) {
        return new C0433e3(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.T4
    final int a(int i, int i2) {
        return i % i2;
    }

    @Override // com.android.tools.r8.internal.T4
    final long a(long j, long j2) {
        return j % j2;
    }

    @Override // com.android.tools.r8.internal.U2, com.android.tools.r8.internal.AbstractC2412ko
    public final SA a(C2409kl c2409kl, Function function) {
        SA sa = (SA) function.apply(N2());
        sa.getClass();
        if ((sa instanceof C0852Bc) && !sa.a().c().T2()) {
            return super.a(c2409kl, function);
        }
        return C3106u5.c();
    }
}
