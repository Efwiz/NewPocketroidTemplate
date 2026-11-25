package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.ir.optimize.C3550d0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Jf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1099Jf extends AbstractC2412ko {
    static final /* synthetic */ boolean h = true;

    public C1099Jf(C2617nW c2617nW, C2617nW c2617nW2, C2617nW c2617nW3) {
        this(new C2708oW(c2617nW, c2617nW2), c2617nW3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 22;
    }

    public final C2617nW J2() {
        return ((C2708oW) b()).j0()[0];
    }

    public final C2617nW K2() {
        return ((C2708oW) b()).j0()[1];
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1099Jf L() {
        return this;
    }

    public final C2617nW L2() {
        return (C2617nW) this.b.get(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return abstractC2412ko instanceof C1099Jf;
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
    public final S10 f(S10 s10) {
        if (!h && !(s10 instanceof C2708oW)) {
            throw new AssertionError();
        }
        for (C2617nW c2617nW : ((C2708oW) s10).j0()) {
            c2617nW.b = this;
        }
        return super.f(s10);
    }

    private C1099Jf(C2708oW c2708oW, C2617nW c2617nW) {
        super(c2708oW, c2617nW);
        if (h) {
            return;
        }
        if (!c2617nW.T() || (c2617nW instanceof C2708oW)) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        throw new C3405y10("This classfile-specific IR should not be inserted in the Dex backend.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        if (((S10) this.b.get(0)).w().M()) {
            l6.a(new D8(D8.a.Dup2), this);
        } else {
            l6.a(new D8(D8.a.Dup), this);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
