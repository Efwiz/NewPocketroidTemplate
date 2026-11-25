package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0639e;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3804u;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class DG extends AbstractC2412ko {
    static final /* synthetic */ boolean j = true;
    public static final /* synthetic */ int k = 0;
    public final C0767v2 h;
    private boolean i;

    public DG(C0767v2 c0767v2, S10 s10) {
        super(s10);
        this.i = true;
        if (!j && c0767v2 == null) {
            throw new AssertionError();
        }
        this.h = c0767v2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        if (j) {
            return 0;
        }
        throw new AssertionError("NewInstance has no register arguments");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 49;
    }

    public C0767v2 J2() {
        return this.h;
    }

    public final boolean K2() {
        return this.i;
    }

    public final void L2() {
        this.i = false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        abstractC1855de.a(this);
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.p2() && abstractC2412ko.t0().h == this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        if (c0702m.q()) {
            return this.h.a(o5Var, c0702m);
        }
        return this.h != o5Var.t();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean p2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final DG t0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        return super.toString() + " " + this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.a(this, new com.android.tools.r8.dex.code.P2(c2322jf.a(W0(), this.a), this.h));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.h(this.h, o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.b(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new V7(null, this.h), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return AbstractC3472z00.a(this.h, WH.b(), (C0702m<?>) c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0767v2 c0767v2, com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, U9 u9, S9 s9) {
        return T9.a(this, c0767v2, c0702m, u9, s9);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        C0684j2 reference;
        C0722p1 c0722p1A = c0702m.a();
        if (!c0702m.q()) {
            return (c0722p1A.P5.contains(this.h) && c0722p1A.Q5.contains(this.h)) ? false : true;
        }
        boolean z = j;
        if (!z && !c0702m.f().h()) {
            throw new AssertionError();
        }
        C0702m c0702mH0 = c0702m.h0();
        if (this.h.W0() || this.h.M0()) {
            if (z) {
                return true;
            }
            throw new AssertionError("Unexpected new-instance instruction with primitive or array type");
        }
        AbstractC0727q0 abstractC0727q0C = c0702m.c(this.h);
        if (abstractC0727q0C == null || abstractC0727q0C.x1() || !abstractC0727q0C.d(c0702m) || AbstractC0639e.a(abstractC0727q0C, o5Var, c0702mH0).b() || abstractC0727q0C.a(c0702mH0, o5Var)) {
            return true;
        }
        com.android.tools.r8.graph.I4 i4A = ((C0680j) c0702mH0.f()).a(this.h, c0722p1A.g4.h);
        return (!i4A.x() || (reference = i4A.t().getReference()) == c0722p1A.r4.l || reference == c0722p1A.g4.h) ? false : true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C0702m c0702m, C2433l20 c2433l20) {
        AbstractC3472z00 abstractC3472z00A = a();
        boolean z = j;
        if (!z && !abstractC3472z00A.w()) {
            throw new AssertionError();
        }
        if (!z && abstractC3472z00A.b().R() != this.h && !c0702m.Y().n1.C) {
            throw new AssertionError();
        }
        if (!z && !abstractC3472z00A.x()) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    final void a(C3804u c3804u) {
        c3804u.d(this.h);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.b(this.h);
    }
}
