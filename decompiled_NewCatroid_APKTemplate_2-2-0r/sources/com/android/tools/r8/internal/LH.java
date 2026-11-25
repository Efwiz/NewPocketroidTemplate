package com.android.tools.r8.internal;

import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class LH extends AbstractC3328x10 {
    static final /* synthetic */ boolean i = true;
    public final EnumC2527mI h;

    public LH(EnumC2527mI enumC2527mI, S10 s10, S10 s11) {
        super(s10, s11);
        this.h = enumC2527mI;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 51;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof LH) && abstractC2412ko.v0().h == this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final LH v0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final SA a(C2409kl c2409kl, Function function) {
        C0978Fc c0978Fc;
        SA sa = (SA) function.apply(K2());
        sa.getClass();
        if (!(sa instanceof C0852Bc)) {
            return C3106u5.c();
        }
        C0978Fc c0978FcC = sa.a().c();
        S10 s10A = c2409kl.a(j(), XM.a(this.h));
        EnumC2527mI enumC2527mI = this.h;
        if (enumC2527mI == EnumC2527mI.INT) {
            c0978Fc = new C0978Fc(s10A, ~c0978FcC.N2());
        } else {
            if (!i && enumC2527mI != EnumC2527mI.LONG) {
                throw new AssertionError();
            }
            c0978Fc = new C0978Fc(s10A, ~c0978FcC.O2());
        }
        return new C0852Bc(c0978Fc);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 s2;
        if (!i && !c2322jf.b().W()) {
            throw new AssertionError();
        }
        int iA = c2322jf.a(W0(), J2());
        int iA2 = c2322jf.a(W0(), K2());
        int i2 = KH.a[this.h.ordinal()];
        if (i2 == 1) {
            s2 = new com.android.tools.r8.dex.code.S2(iA, iA2);
        } else if (i2 == 2) {
            s2 = new com.android.tools.r8.dex.code.T2(iA, iA2);
        } else {
            throw new C3405y10("Unexpected type " + this.h);
        }
        c2322jf.a(this, s2);
    }

    @Override // com.android.tools.r8.internal.AbstractC3328x10, com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        throw new C3405y10();
    }
}
