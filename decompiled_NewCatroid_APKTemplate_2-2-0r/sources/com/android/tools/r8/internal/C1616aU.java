package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0478n3;
import com.android.tools.r8.dex.code.C0483o3;
import com.android.tools.r8.dex.code.C0488p3;
import com.android.tools.r8.dex.code.C0493q3;
import com.android.tools.r8.ir.optimize.C3550d0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1616aU extends AbstractC1954eu {
    static final /* synthetic */ boolean i = true;
    public static final /* synthetic */ int j = 0;

    public C1616aU() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1616aU B0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        if (i) {
            return 0;
        }
        throw new AssertionError("Return defines no values.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 56;
    }

    public final boolean K2() {
        return this.b.size() == 0;
    }

    public S10 L2() {
        if (i || !K2()) {
            return (S10) this.b.get(0);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    public final com.android.tools.r8.dex.code.J b(C2322jf c2322jf) {
        if (K2()) {
            return new C0488p3();
        }
        int iA = c2322jf.a(W0(), L2());
        if (!i && K2()) {
            throw new AssertionError();
        }
        AbstractC3472z00 abstractC3472z00W = L2().w();
        if (abstractC3472z00W.I()) {
            return new C0483o3(iA);
        }
        if (abstractC3472z00W.K()) {
            return new C0478n3(iA);
        }
        if (abstractC3472z00W.M()) {
            return new C0493q3(iA);
        }
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean u2() {
        return true;
    }

    public C1616aU(S10 s10) {
        super(s10);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        if (K2()) {
            nb.c();
        } else {
            nb.c(L2());
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.a(this, b(c2322jf));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        if (K2()) {
            return;
        }
        c3340xC.a(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        B7 c3109u8;
        if (K2()) {
            c3109u8 = new C3186v8();
        } else {
            if (!i && K2()) {
                throw new AssertionError();
            }
            c3109u8 = new C3109u8(EnumC1812d20.a(L2().w()));
        }
        l6.a(c3109u8, this);
    }

    @Override // com.android.tools.r8.internal.AbstractC1954eu, com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.u2()) {
            return false;
        }
        C1616aU c1616aUB0 = abstractC2412ko.B0();
        if (K2()) {
            return c1616aUB0.K2();
        }
        boolean z = i;
        if (!z && K2()) {
            throw new AssertionError();
        }
        AbstractC3472z00 abstractC3472z00W = L2().w();
        if (!z && c1616aUB0.K2()) {
            throw new AssertionError();
        }
        AbstractC3472z00 abstractC3472z00W2 = c1616aUB0.L2().w();
        return (abstractC3472z00W.I() && abstractC3472z00W2.I()) || (abstractC3472z00W.K() && abstractC3472z00W2.K()) || (abstractC3472z00W.M() && abstractC3472z00W2.M());
    }
}
