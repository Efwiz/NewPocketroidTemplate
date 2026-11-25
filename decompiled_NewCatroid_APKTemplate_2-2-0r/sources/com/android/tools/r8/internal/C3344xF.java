package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0467l2;
import com.android.tools.r8.dex.code.C0472m2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.ir.optimize.C3550d0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xF, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3344xF extends AbstractC2412ko {
    static final /* synthetic */ boolean i = true;
    private final EnumC3423yF h;

    public C3344xF(EnumC3423yF enumC3423yF, S10 s10) {
        super((S10) null, s10);
        this.h = enumC3423yF;
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
        throw new AssertionError("Monitor defines no values.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 42;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean I2() {
        return true;
    }

    public final boolean J2() {
        return this.h == EnumC3423yF.ENTER;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String U0() {
        int i2 = AbstractC3268wF.a[this.h.ordinal()];
        if (i2 == 1) {
            return "MonitorEnter";
        }
        if (i2 == 2) {
            return "MonitorExit";
        }
        throw new C3405y10("Unknown monitor type:" + this.h);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final S10 V0() {
        return e();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C3344xF) && abstractC2412ko.m0().h == this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return false;
    }

    public final S10 e() {
        return (S10) this.b.get(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean k2() {
        return J2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C3344xF m0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        int iB = c2322jf.b(W0(), e());
        if (iB > 255) {
            iB = c2322jf.a(W0(), e());
        }
        if (this.h == EnumC3423yF.ENTER) {
            c2322jf.a(this, new C0467l2(iB));
        } else {
            c2322jf.a(this, new C0472m2(iB));
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new R7(this.h), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.a(this.h, e());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, S10 s10) {
        return e() == s10;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
