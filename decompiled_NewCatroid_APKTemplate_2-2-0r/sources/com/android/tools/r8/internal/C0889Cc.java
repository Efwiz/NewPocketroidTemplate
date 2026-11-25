package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0420c0;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3804u;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Cc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0889Cc extends AbstractC0824Ac {
    static final /* synthetic */ boolean j = true;
    private final C0699l2 i;

    public C0889Cc(S10 s10, C0699l2 c0699l2) {
        super(s10);
        this.i = c0699l2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0889Cc C() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        if (j) {
            return 0;
        }
        throw new AssertionError("ConstMethodHandle has no register arguments.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 13;
    }

    public final C0699l2 J2() {
        return this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C0889Cc) && abstractC2412ko.C().i == this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean r2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        return super.toString() + " \"" + this.i + "\"";
    }

    public static C0889Cc a(C2617nW c2617nW, C0889Cc c0889Cc) {
        return new C0889Cc(c2617nW, c0889Cc.i);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.a(this, new C0420c0(c2322jf.a(W0(), this.a), this.i));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new Z6(this.i), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return AbstractC3472z00.a(c0702m.a().C2, WH.b(), (C0702m<?>) c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        return c0702m.a().C2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.b(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    final void a(C3804u c3804u) {
        c3804u.a(this.i, com.android.tools.r8.graph.I5.b);
    }

    @Override // com.android.tools.r8.internal.AbstractC0824Ac, com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.c;
    }
}
