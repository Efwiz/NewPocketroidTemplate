package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0459k;
import com.android.tools.r8.dex.code.C0464l;
import com.android.tools.r8.dex.code.C0497r3;
import com.android.tools.r8.dex.code.C0502s3;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vY, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3218vY extends U2 {
    static final /* synthetic */ boolean j = true;

    public C3218vY(EnumC2527mI enumC2527mI, S10 s10, S10 s11, S10 s12) {
        super(enumC2527mI, s10, s11, s12);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 63;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C3218vY J0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.T4
    public final boolean K2() {
        return false;
    }

    @Override // com.android.tools.r8.internal.T4
    final int M2() {
        if (!d(L2())) {
            if (j || h(L2())) {
                return L2().n().E().R2() ? 255 : 15;
            }
            throw new AssertionError();
        }
        if (d(N2())) {
            return 255;
        }
        if (!j) {
            S10 s10N2 = N2();
            if (this.h != EnumC2527mI.INT || !s10N2.L() || !s10N2.n().E().U2()) {
                throw new AssertionError();
            }
        }
        return N2().n().E().V2() ? 255 : 15;
    }

    @Override // com.android.tools.r8.internal.U2
    final C3481z6.a O2() {
        return C3481z6.a.Sub;
    }

    @Override // com.android.tools.r8.internal.T4
    final double a(double d, double d2) {
        return d - d2;
    }

    @Override // com.android.tools.r8.internal.T4
    final float a(float f, float f2) {
        return f - f2;
    }

    @Override // com.android.tools.r8.internal.T4
    final int a(int i, int i2) {
        return i - i2;
    }

    @Override // com.android.tools.r8.internal.T4
    final long a(long j2, long j3) {
        return j2 - j3;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 b(int i, int i2, int i3) {
        return new com.android.tools.r8.dex.code.Y3(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 c(int i, int i2, int i3) {
        return new com.android.tools.r8.dex.code.a4(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 d(int i, int i2) {
        return new com.android.tools.r8.dex.code.Z3(i, i2);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 e(int i, int i2) {
        return new com.android.tools.r8.dex.code.b4(i, i2);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 f(int i, int i2, int i3) {
        return new com.android.tools.r8.dex.code.c4(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 b(int i, int i2) {
        return new com.android.tools.r8.dex.code.V3(i, i2);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 c(int i, int i2) {
        return new com.android.tools.r8.dex.code.X3(i, i2);
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 d(int i, int i2, int i3) {
        throw new C3405y10("Unsupported instruction SubIntLit16");
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 e(int i, int i2, int i3) {
        throw new C3405y10("Unsupported instruction SubIntLit8");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C3218vY) && abstractC2412ko.J0().h == this.h;
    }

    @Override // com.android.tools.r8.internal.U2, com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(S10 s10) {
        if (L2() == N2()) {
            return true;
        }
        if (s10 == L2()) {
            return !h(s10);
        }
        if (j || s10 == N2()) {
            return (this.h == EnumC2527mI.INT && s10.L() && s10.n().E().U2() && !h(L2())) ? false : true;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.U2
    public final com.android.tools.r8.dex.code.B1 a(int i, int i2, int i3) {
        return new com.android.tools.r8.dex.code.W3(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.U2, com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 a4Var;
        if (!a(c2322jf.d())) {
            EnumC2527mI enumC2527mI = this.h;
            EnumC2527mI enumC2527mI2 = EnumC2527mI.INT;
            if (enumC2527mI == enumC2527mI2) {
                if (!d(L2())) {
                    boolean z = j;
                    if (!z && !h(L2())) {
                        throw new AssertionError();
                    }
                    C0978Fc c0978FcE = L2().n().E();
                    int iA = c2322jf.a(W0(), N2());
                    int iA2 = c2322jf.a(W0(), this.a);
                    if (c0978FcE.R2()) {
                        a4Var = new C0502s3(iA2, iA, c0978FcE.N2());
                    } else {
                        if (!z && !c0978FcE.Q2()) {
                            throw new AssertionError();
                        }
                        a4Var = new C0497r3(iA2, iA, c0978FcE.N2());
                    }
                } else if (!d(N2())) {
                    boolean z2 = j;
                    if (!z2) {
                        S10 s10N2 = N2();
                        if (this.h != enumC2527mI2 || !s10N2.L() || !s10N2.n().E().U2()) {
                            throw new AssertionError();
                        }
                    }
                    int iA3 = c2322jf.a(W0(), this.a);
                    if (!z2 && !d(L2())) {
                        throw new AssertionError();
                    }
                    int iA4 = c2322jf.a(W0(), L2());
                    C0978Fc c0978FcE2 = N2().n().E();
                    if (c0978FcE2.V2()) {
                        a4Var = new C0464l(iA3, iA4, -c0978FcE2.N2());
                    } else {
                        if (!z2 && !c0978FcE2.U2()) {
                            throw new AssertionError();
                        }
                        a4Var = new C0459k(iA3, iA4, -c0978FcE2.N2());
                    }
                } else {
                    if (!j && this.h != enumC2527mI2) {
                        throw new AssertionError();
                    }
                    a4Var = new com.android.tools.r8.dex.code.a4(c2322jf.a(W0(), this.a), c2322jf.a(W0(), L2()), c2322jf.a(W0(), N2()));
                }
                c2322jf.a(this, a4Var);
                return;
            }
        }
        super.a(c2322jf);
    }
}
