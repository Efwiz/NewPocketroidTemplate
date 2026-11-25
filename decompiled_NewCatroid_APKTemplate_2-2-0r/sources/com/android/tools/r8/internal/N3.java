package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class N3 extends AbstractC2412ko {
    static final /* synthetic */ boolean k = true;
    private L3 h;
    private final M3 i;
    private final AbstractC2412ko j;

    public N3(L3 l3, M3 m3, S10 s10, S10 s11, AbstractC2412ko abstractC2412ko, C0702m c0702m) {
        super(s10, s11);
        boolean z = k;
        if (!z && l3 == null && m3 == null) {
            throw new AssertionError();
        }
        if (!z && l3 != null) {
            l3.a(s11, c0702m);
        }
        if (!z && m3 != null) {
            M3.a(s11);
        }
        this.h = l3;
        this.i = m3;
        this.j = abstractC2412ko;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        throw new C3405y10("Expected Assume instructions to be removed after IR processing.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        throw new C3405y10("Expected Assume instructions to be removed after IR processing.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 9;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean I2() {
        return L2();
    }

    public final L3 J2() {
        return this.h;
    }

    public final boolean K2() {
        return this.h != null;
    }

    public final boolean L2() {
        return this.i != null;
    }

    public final AbstractC2412ko M2() {
        return this.j;
    }

    public S10 N2() {
        return (S10) this.b.get(0);
    }

    public final void O2() {
        this.h = null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String U0() {
        return "Assume";
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final S10 V0() {
        return N2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    public final void b(C0702m c0702m) {
        if (!K2() || k) {
            return;
        }
        L3 l3 = this.h;
        b();
        l3.a(N2(), c0702m);
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
    public final String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (L2()) {
            sb.append("; not null");
        }
        if (K2()) {
            C1207Nf c1207Nf = this.h.a;
            if (d1() && !c1207Nf.o().a(this.a.w())) {
                sb.append("; upper bound: ").append(c1207Nf.o());
            }
            if (c1207Nf.g()) {
                sb.append("; lower bound: ").append(c1207Nf.d());
            }
        }
        return sb.toString();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final N3 w() {
        return this;
    }

    public static N3 a(S10 s10, S10 s11, C3406y2 c3406y2, C0702m c0702m) {
        return new N3(null, M3.a, s10, s11, c3406y2, c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(Set set) {
        return N2().b(set);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        if (!(abstractC2412ko instanceof N3)) {
            return false;
        }
        N3 n3W = abstractC2412ko.w();
        return Objects.equals(this.h, n3W.h) && Objects.equals(this.i, n3W.i);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, S10 s10) {
        boolean z = k;
        if (!z && (s10 == null || !s10.w().I())) {
            throw new AssertionError();
        }
        if (!z && this.a == null) {
            throw new AssertionError();
        }
        AbstractC3472z00 abstractC3472z00W = this.a.w();
        if (abstractC3472z00W.H()) {
            return false;
        }
        if (K2()) {
            abstractC3472z00W = this.h.a.o();
        }
        if (c0702m.f().i() && abstractC3472z00W.w() && s10.w().w() && c0702m.f().o().a(abstractC3472z00W.b().R(), s10.w().b().R())) {
            return false;
        }
        return abstractC3472z00W.I();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        throw new C3405y10("Expected Assume instructions to be removed after IR processing.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        throw new C3405y10("Expected Assume instructions to be removed after IR processing.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        if (L2()) {
            if (k || N2().w().I()) {
                return N2().w().d().P();
            }
            throw new AssertionError();
        }
        return N2().w();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        return q00.a(N2());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        throw new C3405y10("Expected Assume instructions to be removed after IR processing.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C0702m c0702m, C2433l20 c2433l20) {
        boolean z = k;
        if (!z) {
            super.a(c0702m, c2433l20);
        }
        AbstractC3472z00 abstractC3472z00W = N2().w();
        if (!z && !abstractC3472z00W.I()) {
            throw new AssertionError(abstractC3472z00W);
        }
        AbstractC3472z00 abstractC3472z00A = a();
        if (L2()) {
            if (z || abstractC3472z00W.E() || abstractC3472z00A.equals(abstractC3472z00W.d().P())) {
                return;
            }
            throw new AssertionError("At " + this + System.lineSeparator() + abstractC3472z00A + " != " + abstractC3472z00W);
        }
        if (!z && !K2()) {
            throw new AssertionError();
        }
        if (!z && N2().J()) {
            throw new AssertionError();
        }
        if (z || abstractC3472z00A.equals(abstractC3472z00W)) {
            return;
        }
        throw new AssertionError("At " + this + System.lineSeparator() + abstractC3472z00A + " != " + abstractC3472z00W);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
