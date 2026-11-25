package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class SW extends AbstractC2412ko {
    static final /* synthetic */ boolean h = true;

    public SW(C2617nW c2617nW, S10 s10) {
        super(s10, c2617nW);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 65535;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 61;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final SW H0() {
        return this;
    }

    public final S10 J2() {
        return (S10) this.b.get(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return abstractC2412ko instanceof SW;
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
    public final void a(C2322jf c2322jf) {
        throw new C3405y10("This classfile-specific IR should not be inserted in the Dex backend.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(S10 s10) {
        boolean z = h;
        if (!z && s10 != J2()) {
            throw new AssertionError();
        }
        if (z || (J2() instanceof C2617nW)) {
            return false;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new R8(H2(), l6.a(this.a)), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        return q00.a(J2());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        throw new C3405y10("This IR must not be inserted before load and store insertion.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return J2().w();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.L a(C0702m c0702m, C2409kl c2409kl) {
        if (this.a instanceof C2723oi) {
            return com.android.tools.r8.ir.optimize.L.e();
        }
        return com.android.tools.r8.ir.optimize.L.a();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
