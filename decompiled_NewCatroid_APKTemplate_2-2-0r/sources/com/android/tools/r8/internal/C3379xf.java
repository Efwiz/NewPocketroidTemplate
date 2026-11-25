package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0460k0;
import com.android.tools.r8.dex.code.C0465l0;
import com.android.tools.r8.dex.code.C0470m0;
import com.android.tools.r8.dex.code.C0475n0;
import com.android.tools.r8.dex.code.C0480o0;
import com.android.tools.r8.dex.code.C0485p0;
import com.android.tools.r8.dex.code.C0490q0;
import com.android.tools.r8.dex.code.C0494r0;
import com.android.tools.r8.dex.code.C0499s0;
import com.android.tools.r8.dex.code.C0504t0;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3379xf extends U2 {
    public C3379xf(EnumC2527mI enumC2527mI, S10 s10, S10 s11, S10 s12) {
        super(enumC2527mI, s10, s11, s12);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 21;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C3379xf K() {
        return this;
    }

    @Override // com.android.tools.r8.internal.T4
    public final boolean K2() {
        return false;
    }

    @Override // com.android.tools.r8.internal.U2
    final C3481z6.a O2() {
        return C3481z6.a.Div;
    }

    @Override // com.android.tools.r8.internal.T4
    final double a(double d, double d2) {
        return d / d2;
    }

    @Override // com.android.tools.r8.internal.T4
    final float a(float f, float f2) {
        return f / f2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 b(int i, int i2, int i3) {
        return new C0475n0(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 c(int i, int i2, int i3) {
        return new C0485p0(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 d(int i, int i2) {
        return new C0480o0(i, i2);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 e(int i, int i2) {
        return new C0499s0(i, i2);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 f(int i, int i2, int i3) {
        return new C0504t0(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        EnumC2527mI enumC2527mI = this.h;
        return (enumC2527mI == EnumC2527mI.DOUBLE || enumC2527mI == EnumC2527mI.FLOAT) ? false : true;
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 b(int i, int i2) {
        return new C0460k0(i, i2);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 c(int i, int i2) {
        return new C0470m0(i, i2);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 d(int i, int i2, int i3) {
        return new C0490q0(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 e(int i, int i2, int i3) {
        return new C0494r0(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C3379xf) && abstractC2412ko.K().h == this.h;
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 a(int i, int i2, int i3) {
        return new C0465l0(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.T4
    final int a(int i, int i2) {
        return i / i2;
    }

    @Override // com.android.tools.r8.internal.T4
    final long a(long j, long j2) {
        return j / j2;
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

    @Override // com.android.tools.r8.internal.U2, com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.a(this.h, L2(), N2());
    }
}
