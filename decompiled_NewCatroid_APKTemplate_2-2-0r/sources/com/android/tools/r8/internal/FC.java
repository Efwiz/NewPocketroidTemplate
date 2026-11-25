package com.android.tools.r8.internal;

import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class FC extends T4 {
    static final /* synthetic */ boolean i = true;

    public FC(EnumC2527mI enumC2527mI, S10 s10, S10 s11, S10 s12) {
        super(enumC2527mI, s10, s11, s12);
    }

    abstract P7 O2();

    public abstract com.android.tools.r8.dex.code.B1 a(int i2, int i3, int i4);

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 b1A;
        int iA = c2322jf.a(W0(), L2());
        int iA2 = c2322jf.a(W0(), this.a);
        if (a(c2322jf.d())) {
            int iA3 = c2322jf.a(W0(), N2());
            if (iA != iA2) {
                boolean z = i;
                if (!z && !K2()) {
                    throw new AssertionError();
                }
                if (!z && iA3 != iA2) {
                    throw new AssertionError();
                }
            } else {
                iA = iA3;
            }
            int i2 = EC.a[this.h.ordinal()];
            if (i2 == 1) {
                b1A = b(iA2, iA);
            } else {
                if (i2 != 2) {
                    throw new C3405y10("Unexpected type " + this.h);
                }
                b1A = c(iA2, iA);
            }
        } else if (d(N2())) {
            int iA4 = c2322jf.a(W0(), N2());
            int i3 = EC.a[this.h.ordinal()];
            if (i3 == 1) {
                b1A = a(iA2, iA, iA4);
            } else {
                if (i3 != 2) {
                    throw new C3405y10("Unexpected type " + this.h);
                }
                b1A = d(iA2, iA, iA4);
            }
        } else {
            boolean z2 = i;
            if (!z2 && !h(N2())) {
                throw new AssertionError();
            }
            C0978Fc c0978FcE = N2().n().E();
            if (c0978FcE.R2()) {
                b1A = c(iA2, iA, c0978FcE.N2());
            } else {
                if (!z2 && !c0978FcE.Q2()) {
                    throw new AssertionError();
                }
                b1A = b(iA2, iA, c0978FcE.N2());
            }
        }
        c2322jf.a(this, b1A);
    }

    public abstract com.android.tools.r8.dex.code.B1 b(int i2, int i3);

    public abstract com.android.tools.r8.dex.code.B1 b(int i2, int i3, int i4);

    public abstract com.android.tools.r8.dex.code.B1 c(int i2, int i3);

    public abstract com.android.tools.r8.dex.code.B1 c(int i2, int i3, int i4);

    public abstract com.android.tools.r8.dex.code.B1 d(int i2, int i3, int i4);

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(S10 s10) {
        if (s10 == L2()) {
            return true;
        }
        if (i || s10 == N2()) {
            return !h(s10);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean i2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final FC l0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final SA a(C2409kl c2409kl, Function function) {
        long jN2;
        C0978Fc c0978Fc;
        SA sa = (SA) function.apply(L2());
        SA sa2 = (SA) function.apply(N2());
        sa.getClass();
        if (sa instanceof C0852Bc) {
            sa2.getClass();
            if (sa2 instanceof C0852Bc) {
                C0978Fc c0978FcC = sa.a().c();
                C0978Fc c0978FcC2 = sa2.a().c();
                EnumC2527mI enumC2527mI = this.h;
                if (enumC2527mI == EnumC2527mI.INT) {
                    c0978Fc = new C0978Fc(c2409kl.a(j(), AbstractC3472z00.k()), a(c0978FcC.N2(), c0978FcC2.N2()));
                } else {
                    if (!i && enumC2527mI != EnumC2527mI.LONG) {
                        throw new AssertionError();
                    }
                    if (!(this instanceof TU) && !(this instanceof WU) && !(this instanceof N10)) {
                        jN2 = c0978FcC2.O2();
                    } else {
                        jN2 = c0978FcC2.N2();
                    }
                    c0978Fc = new C0978Fc(c2409kl.a(j(), AbstractC3472z00.l()), a(c0978FcC.O2(), jN2));
                }
                return new C0852Bc(c0978Fc);
            }
        }
        return C3106u5.c();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new Q7(O2(), this.h), this);
    }
}
