package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0410a0;
import com.android.tools.r8.graph.AbstractC0639e;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3804u;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3446yc extends AbstractC0824Ac {
    static final /* synthetic */ boolean k = true;
    public static final /* synthetic */ int l = 0;
    private final C0767v2 i;
    private final boolean j;

    public C3446yc(C0767v2 c0767v2, S10 s10) {
        this(s10, c0767v2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C3446yc A() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        if (k) {
            return 0;
        }
        throw new AssertionError("ConstClass has no register arguments.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 12;
    }

    public final C0767v2 J2() {
        return this.i;
    }

    public final C0767v2 K2() {
        return this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        C0767v2 c0767v2B = this.i.b(c0702m.a());
        if (c0767v2B.W0()) {
            return false;
        }
        if (!c0702m.q()) {
            return c0767v2B != o5Var.t();
        }
        if (!k && !c0702m.f().h()) {
            throw new AssertionError();
        }
        C0702m c0702mH0 = c0702m.h0();
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2B);
        return abstractC0727q0C == null || !abstractC0727q0C.d(c0702m) || AbstractC0639e.a(abstractC0727q0C, o5Var, c0702mH0).b();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean i1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        return super.toString() + this.i.r0();
    }

    public C3446yc(S10 s10, C0767v2 c0767v2) {
        super(s10);
        if (!k && c0767v2.W0()) {
            throw new AssertionError();
        }
        this.i = c0767v2;
        this.j = false;
    }

    public static C3446yc a(S10 s10, C3446yc c3446yc) {
        if (k || s10 != c3446yc.b()) {
            return new C3446yc(s10, c3446yc.i);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.a(this, new C0410a0(c2322jf.a(W0(), this.a), this.i, this.j));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        return b(c0702m, o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC0824Ac, com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.b(this.i, o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        WH whB = WH.b();
        int i = AbstractC3472z00.b;
        return AbstractC3472z00.a(c0702m.a().m2, whB, (C0702m<?>) c0702m).b();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        return c0702m.a().m2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.b(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new X6(this.i, false), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    final void a(C3804u c3804u) {
        c3804u.a(this.i, null, this.j);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final B1 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        if (!a(c0702m, o5Var, C2332jo.a)) {
            return c0702m.b().a(this.i);
        }
        return C2892r10.F();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C3446yc) && abstractC2412ko.A().i == this.i;
    }
}
