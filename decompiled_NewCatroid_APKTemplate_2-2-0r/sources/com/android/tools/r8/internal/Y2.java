package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0479o;
import com.android.tools.r8.dex.code.C0484p;
import com.android.tools.r8.dex.code.C0489q;
import com.android.tools.r8.dex.code.C0498s;
import com.android.tools.r8.dex.code.C0503t;
import com.android.tools.r8.dex.code.C0508u;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class Y2 extends V2 {
    static final /* synthetic */ boolean i = true;
    private EnumC2523mE h;

    public Y2(EnumC2523mE enumC2523mE, S10 s10, S10 s11, S10 s12) {
        super(s10, Arrays.asList(s11, s12));
        this.h = enumC2523mE;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 6;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean I2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.V2
    public final V2 L2() {
        return new Y2(EnumC2523mE.INT, b(), J2(), K2());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final S10 V0() {
        return J2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(AbstractC2412ko abstractC2412ko, com.android.tools.r8.ir.regalloc.f fVar, CE ce) {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.p1() && abstractC2412ko.t().h == this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c1() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean p1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Y2 t() {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1299Qm
    public final EnumC2523mE d() {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 c0498s;
        int iA = c2322jf.a(W0(), this.a);
        int iA2 = c2322jf.a(W0(), J2());
        int iA3 = c2322jf.a(W0(), K2());
        switch (this.h) {
            case OBJECT:
                c0498s = new C0498s(iA, iA2, iA3);
                break;
            case BOOLEAN_OR_BYTE:
                C1969f3 c1969f3A = J2().w().a();
                if (c1969f3A != null && c1969f3A.S() == AbstractC3472z00.e()) {
                    c0498s = new C0484p(iA, iA2, iA3);
                } else {
                    if (!i && !J2().w().y() && c1969f3A.S() != AbstractC3472z00.g()) {
                        throw new AssertionError();
                    }
                    c0498s = new C0489q(iA, iA2, iA3);
                }
                break;
            case CHAR:
                c0498s = new com.android.tools.r8.dex.code.r(iA, iA2, iA3);
                break;
            case SHORT:
                c0498s = new C0503t(iA, iA2, iA3);
                break;
            case INT:
            case FLOAT:
                c0498s = new C0479o(iA, iA2, iA3);
                break;
            case LONG:
            case DOUBLE:
                if (!i && !c2322jf.b().Z() && iA == iA2) {
                    throw new AssertionError();
                }
                c0498s = new C0508u(iA, iA2, iA3);
                break;
                break;
            case INT_OR_FLOAT:
            case LONG_OR_DOUBLE:
                throw new C3405y10("Unexpected imprecise type: " + this.h);
            default:
                throw new C3405y10("Unexpected type " + this.h);
        }
        c2322jf.a(this, c0498s);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, S10 s10) {
        boolean z = i;
        if (!z && (s10 == null || !s10.w().I())) {
            throw new AssertionError();
        }
        if (z || this.a != null) {
            return this.a.w().I();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        if (!i && !this.a.w().I()) {
            throw new AssertionError();
        }
        C0767v2 c0767v2A = q00.a(J2());
        return c0767v2A == C0722p1.S5 ? c0767v2A : c0767v2A.a(c0702m.a());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
        c3340xC.b(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new B6(this.h), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        AbstractC3472z00 abstractC3472z00T;
        C1969f3 c1969f3A = J2().w().r() ? J2().w().a() : null;
        switch (this.h) {
            case OBJECT:
                if (c1969f3A == null) {
                    abstractC3472z00T = AbstractC3472z00.m();
                } else {
                    abstractC3472z00T = c1969f3A.T();
                }
                if (i || abstractC3472z00T.I()) {
                    return abstractC3472z00T;
                }
                throw new AssertionError();
            case BOOLEAN_OR_BYTE:
            case CHAR:
            case SHORT:
            case INT:
                if (i || c1969f3A == null || c1969f3A.T().C()) {
                    return AbstractC3472z00.k();
                }
                throw new AssertionError();
            case FLOAT:
                if (i || c1969f3A == null || c1969f3A.T().B()) {
                    return AbstractC3472z00.j();
                }
                throw new AssertionError();
            case LONG:
                if (i || c1969f3A == null || c1969f3A.T().D()) {
                    return AbstractC3472z00.l();
                }
                throw new AssertionError();
            case DOUBLE:
                if (i || c1969f3A == null || c1969f3A.T().z()) {
                    return AbstractC3472z00.i();
                }
                throw new AssertionError();
            case INT_OR_FLOAT:
                if (!i && c1969f3A != null && !c1969f3A.T().K()) {
                    throw new AssertionError();
                }
                return a(this.a, EnumC1968f20.INT_OR_FLOAT);
            case LONG_OR_DOUBLE:
                if (!i && c1969f3A != null && !c1969f3A.T().M()) {
                    throw new AssertionError();
                }
                return a(this.a, EnumC1968f20.LONG_OR_DOUBLE);
            default:
                throw new C3405y10("Unexpected member type: " + this.h);
        }
    }

    private static AbstractC3472z00 a(S10 s10, EnumC1968f20 enumC1968f20) {
        AbstractC3472z00 abstractC3472z00A = s10.a(enumC1968f20);
        if (abstractC3472z00A != null) {
            return abstractC3472z00A;
        }
        throw new C1367Tb("Failure to constrain value: " + s10 + " by constraint: " + enumC1968f20);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, S10 s10) {
        return J2() == s10;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(Set set) {
        return J2().w().r() && J2().w().a().S() == AbstractC3472z00.e();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1299Qm
    public final void a(C3403y00 c3403y00) {
        c3403y00.a(this.h, this.a, J2(), new Consumer() { // from class: com.android.tools.r8.internal.Y2$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((EnumC2523mE) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(EnumC2523mE enumC2523mE) {
        this.h = enumC2523mE;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        if (J2().i() || !K2().L()) {
            return true;
        }
        B1 b1A = J2().m().a(c0702m, o5Var);
        if (!b1A.u()) {
            return true;
        }
        int iS = b1A.s();
        int iN2 = K2().n().E().N2();
        return iS <= 0 || iN2 < 0 || iS <= iN2;
    }
}
