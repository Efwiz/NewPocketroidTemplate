package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0447h2;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3804u;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2477lf extends AbstractC0824Ac {
    static final /* synthetic */ boolean k = true;
    public static final /* synthetic */ int l = 0;
    private final AbstractC0744s2 i;
    private final AbstractC2525mG j;

    public C2477lf(S10 s10, AbstractC0744s2 abstractC0744s2, AbstractC2525mG abstractC2525mG) {
        super(s10);
        this.i = abstractC0744s2;
        this.j = abstractC2525mG;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        if (k) {
            return 0;
        }
        throw new AssertionError("DexItemBasedConstString has no register arguments.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 20;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C2477lf J() {
        return this;
    }

    public final AbstractC0744s2 J2() {
        return this.i;
    }

    public final AbstractC2525mG K2() {
        return this.j;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C2477lf) && abstractC2412ko.J().i == this.i && abstractC2412ko.J().j.equals(this.j);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean i1() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean j1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean r2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        return super.toString() + " \"" + this.i.r0() + "\"";
    }

    public static C2477lf a(S10 s10, C2477lf c2477lf) {
        if (k || s10 != c2477lf.b()) {
            return new C2477lf(s10, c2477lf.i, c2477lf.j);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.a(this, new C0447h2(c2322jf.a(W0(), b()), this.i, this.j));
    }

    @Override // com.android.tools.r8.internal.AbstractC0824Ac, com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.L a(C0702m c0702m, C2409kl c2409kl) {
        return com.android.tools.r8.ir.optimize.L.a();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.b(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new C2134h7(this.i, this.j), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        return c0702m.a().X1;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return AbstractC3472z00.b((C0702m<?>) c0702m, WH.b());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final B1 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        C1 c1B = c0702m.b();
        AbstractC0744s2 abstractC0744s2 = this.i;
        AbstractC2525mG abstractC2525mG = this.j;
        c1B.getClass();
        return C1.a(abstractC0744s2, abstractC2525mG);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    final void a(C3804u c3804u) {
        if (this.j.f()) {
            if (!k) {
                AbstractC0744s2 abstractC0744s2 = this.i;
                abstractC0744s2.getClass();
                if (!(abstractC0744s2 instanceof C0767v2)) {
                    throw new AssertionError();
                }
            }
            this.i.w0();
        }
    }
}
