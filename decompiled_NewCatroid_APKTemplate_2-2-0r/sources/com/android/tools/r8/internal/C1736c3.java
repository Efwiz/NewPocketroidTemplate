package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.ir.optimize.C3550d0;
import java.util.Arrays;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.c3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1736c3 extends V2 {
    static final /* synthetic */ boolean i = true;
    private EnumC2523mE h;

    public C1736c3(EnumC2523mE enumC2523mE, S10 s10, S10 s11, S10 s12) {
        super(null, Arrays.asList(s10, s11, s12));
        boolean z = i;
        if (!z && enumC2523mE == null) {
            throw new AssertionError();
        }
        if (!z) {
            s10.a(EnumC1812d20.OBJECT);
        }
        if (!z) {
            s11.a(EnumC1812d20.INT);
        }
        this.h = enumC2523mE;
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
        throw new AssertionError("ArrayPut instructions define no values.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 8;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean I2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.V2
    public final V2 L2() {
        return new C1736c3(EnumC2523mE.INT, J2(), K2(), value());
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
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(AbstractC2412ko abstractC2412ko, com.android.tools.r8.ir.regalloc.f fVar, CE ce) {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        if (c0702m.Y().W0) {
            return true;
        }
        S10 s10M = J2().m();
        if (!s10M.i()) {
            AbstractC2412ko abstractC2412ko = s10M.b;
            abstractC2412ko.getClass();
            if (abstractC2412ko instanceof AG) {
                S10 s10M2 = s10M.b.r0().J2().m();
                if (!s10M2.i() && s10M2.b.B1()) {
                    S10 s10M3 = K2().m();
                    if (!s10M3.i() && s10M3.b.B1()) {
                        long jP2 = s10M3.b.E().P2();
                        long jP3 = s10M2.b.E().P2();
                        if (jP2 >= 0 && jP2 < jP3) {
                            AbstractC3472z00 abstractC3472z00W = s10M.w();
                            return (abstractC3472z00W.r() && value().w().b(abstractC3472z00W.a().T(), (C0702m<?>) c0702m)) ? false : true;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean r1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1736c3 v() {
        return this;
    }

    public S10 value() {
        return (S10) this.b.get(2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1299Qm
    public final EnumC2523mE d() {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 f;
        int iA = c2322jf.a(W0(), value());
        int iA2 = c2322jf.a(W0(), J2());
        int iA3 = c2322jf.a(W0(), K2());
        switch (this.h) {
            case OBJECT:
                f = new com.android.tools.r8.dex.code.F(iA, iA2, iA3);
                break;
            case BOOLEAN_OR_BYTE:
                C1969f3 c1969f3A = J2().w().a();
                if (c1969f3A != null && c1969f3A.S() == AbstractC3472z00.e()) {
                    f = new com.android.tools.r8.dex.code.C(iA, iA2, iA3);
                } else {
                    if (!i && !J2().w().y() && c1969f3A.S() != AbstractC3472z00.g()) {
                        throw new AssertionError();
                    }
                    f = new com.android.tools.r8.dex.code.D(iA, iA2, iA3);
                }
                break;
            case CHAR:
                f = new com.android.tools.r8.dex.code.E(iA, iA2, iA3);
                break;
            case SHORT:
                f = new com.android.tools.r8.dex.code.G(iA, iA2, iA3);
                break;
            case INT:
            case FLOAT:
                f = new com.android.tools.r8.dex.code.B(iA, iA2, iA3);
                break;
            case LONG:
            case DOUBLE:
                f = new com.android.tools.r8.dex.code.H(iA, iA2, iA3);
                break;
            case INT_OR_FLOAT:
            case LONG_OR_DOUBLE:
                throw new C3405y10("Unexpected imprecise type: " + this.h);
            default:
                throw new C3405y10("Unexpected type: " + this.h);
        }
        c2322jf.a(this, f);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.r1() && abstractC2412ko.v().h == this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.L a(C0702m c0702m, C2409kl c2409kl) {
        if (!b(c0702m, c2409kl.h())) {
            S10 s10M = J2().m();
            if (s10M.b(new c3$$ExternalSyntheticLambda1())) {
                return com.android.tools.r8.ir.optimize.L.a(s10M);
            }
        }
        return com.android.tools.r8.ir.optimize.L.e();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new D6(this.h), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, S10 s10) {
        return J2() == s10;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1299Qm
    public final void a(C3403y00 c3403y00) {
        c3403y00.a(this.h, value(), J2(), new Consumer() { // from class: com.android.tools.r8.internal.c3$$ExternalSyntheticLambda0
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
}
