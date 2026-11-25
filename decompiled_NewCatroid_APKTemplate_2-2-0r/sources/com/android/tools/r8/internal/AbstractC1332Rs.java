package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0644e4;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.EnumC3542a0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3749l;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Rs, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1332Rs extends AbstractC1305Qs {
    static final /* synthetic */ boolean k = true;

    AbstractC1332Rs(C0684j2 c0684j2, S10 s10, List list) {
        super(c0684j2, s10, list);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean I2() {
        return true;
    }

    public final Iterable T2() {
        return AbstractC2734ot.d(this.b);
    }

    public S10 U2() {
        if (k || this.b.size() > 0) {
            return (S10) this.b.get(0);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final S10 V0() {
        return U2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean Z1() {
        return true;
    }

    public abstract AbstractC0748t0 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C1207Nf c1207Nf);

    @Override // com.android.tools.r8.internal.AbstractC1305Qs
    public final com.android.tools.r8.ir.optimize.W a(com.android.tools.r8.graph.o5 o5Var, EnumC3542a0 enumC3542a0, com.android.tools.r8.ir.optimize.N n, V9 v9, AbstractC3477z20 abstractC3477z20) {
        return n.a(this, o5Var, enumC3542a0, abstractC3477z20);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC1332Rs c0() {
        return this;
    }

    protected final boolean e(C2322jf c2322jf) {
        com.android.tools.r8.graph.W0 w0B;
        C0684j2 c0684j2S2 = S2();
        if (c0684j2S2.B0() != c2322jf.d().d().t() || (w0B = c2322jf.d().d().a().b(c0684j2S2)) == null || w0B.E0()) {
            return false;
        }
        boolean z = k;
        if (!z && c0684j2S2.e != w0B.J0()) {
            throw new AssertionError();
        }
        if (z || w0B.getReference() == c0684j2S2) {
            return true;
        }
        throw new AssertionError();
    }

    protected final boolean f(C2322jf c2322jf) {
        AbstractC0748t0 abstractC0748t0B;
        if (!c2322jf.b().i0) {
            return false;
        }
        C0729q2 c0729q2A = c2322jf.c().a();
        if (!c0729q2A.D1() || (abstractC0748t0B = c2322jf.a.f().b(S2())) == null || abstractC0748t0B.a().f0() || !abstractC0748t0B.getAccessFlags().j()) {
            return false;
        }
        AbstractC0727q0 abstractC0727q0A = abstractC0748t0B.a();
        return c0729q2A.D1() && abstractC0727q0A.D1() && c0729q2A.g1() == abstractC0727q0A.g1();
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs
    public final AbstractC0748t0 h(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        C1207Nf c1207NfN;
        if (c0702m.f().i()) {
            S10 s10U2 = U2();
            C0702m<C3733i> c0702mI0 = c0702m.i0();
            s10U2.getClass();
            c1207NfN = AbstractC1154Lf.a(c0702mI0, s10U2);
        } else {
            c1207NfN = AbstractC1154Lf.n();
        }
        return a(c0702m, o5Var, c1207NfN);
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs, com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, S10 s10) {
        return s10 == U2() || super.a(c0702m, o5Var, s10);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C0702m c0702m, C2433l20 c2433l20) {
        C0702m<C3733i> c0702mI0;
        C1229Oa c1229OaA;
        boolean z = k;
        if (!z) {
            super.a(c0702m, c2433l20);
        }
        S10 s10U2 = U2();
        AbstractC3472z00 abstractC3472z00W = s10U2.w();
        if (!z && !abstractC3472z00W.G()) {
            throw new AssertionError();
        }
        if (!c0702m.f().i() || (c1229OaA = s10U2.a((c0702mI0 = c0702m.i0()), (AbstractC3472z00) null, WH.i())) == null) {
            return;
        }
        S10 s10U3 = U2();
        s10U3.getClass();
        C0767v2 c0767v2A = C3030t00.a(AbstractC1154Lf.a(c0702mI0, s10U3), S2(), c0702mI0);
        if (!z && !((C3733i) c0702mI0.f()).c(c1229OaA.R(), c0767v2A) && !c0702m.Y().n1.C && !s10U2.a(c0702mI0).E() && !c1229OaA.a(c0702mI0) && !a(c0702mI0, c0767v2A, c1229OaA.R())) {
            throw new AssertionError("The receiver lower bound does not match the receiver type");
        }
    }

    private boolean a(C0702m c0702m, C0767v2 c0767v2, C0767v2 c0767v3) {
        C0729q2 c0729q2B;
        AbstractC0727q0 abstractC0727q0C;
        return U2().m().H() && U2().b(new Rs$$ExternalSyntheticLambda0()) && (c0729q2B = c0702m.b(c0767v2)) != null && !((C3733i) c0702m.f()).b(c0729q2B) && (abstractC0727q0C = c0702m.c(c0767v3)) != null && abstractC0727q0C.c(c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        if (c0702m.Y().W0) {
            return true;
        }
        S10 s10U2 = U2();
        if (!c2332jo.c() && s10U2.w().F()) {
            return true;
        }
        if (S2().e.M0() && S2().b(c0702m.a().g4.c)) {
            return !f2();
        }
        C2974sB c2974sBX = c0702m.x();
        c2974sBX.getClass();
        if (c2974sBX.a(S2(), this.b)) {
            return false;
        }
        if (!c0702m.q()) {
            return true;
        }
        boolean z = k;
        if (!z && !c0702m.f().h()) {
            throw new AssertionError();
        }
        C0702m<? extends C0680j> c0702mH0 = c0702m.h0();
        com.android.tools.r8.graph.I4.c<?> cVarO = ((C0680j) c0702mH0.f()).b(S2(), R2()).o();
        if (cVarO == null || cVarO.a(o5Var, c0702mH0).b()) {
            return true;
        }
        if (c2332jo.b()) {
            return false;
        }
        AbstractC0748t0 abstractC0748t0C = cVarO.c();
        if (!c0702m.t().a(S2()).f()) {
            C3749l c3749lT = c0702m.t();
            c3749lT.getClass();
            if (!c3749lT.a(abstractC0748t0C.getReference()).f()) {
                AbstractC0748t0 abstractC0748t0H = h(c0702m, o5Var);
                if (abstractC0748t0H == null) {
                    return true;
                }
                if ((abstractC0748t0H instanceof C0644e4) && c0702m.x().a(this, abstractC0748t0H.W())) {
                    return false;
                }
                C3749l c3749lT2 = c0702m.t();
                c3749lT2.getClass();
                if (c3749lT2.a(abstractC0748t0H.getReference()).f()) {
                    return false;
                }
                com.android.tools.r8.graph.W0 w0E = abstractC0748t0H.e();
                com.android.tools.r8.ir.optimize.info.h hVarI1 = w0E.i1();
                if ((c2332jo instanceof C2025fo) && w0E.C1()) {
                    if (!z && !W1()) {
                        throw new AssertionError();
                    }
                    if (!hVarI1.a(Z()).e()) {
                        return !W1();
                    }
                }
                return hVarI1.a(this, c0702m.Y());
            }
        }
        return false;
    }
}
