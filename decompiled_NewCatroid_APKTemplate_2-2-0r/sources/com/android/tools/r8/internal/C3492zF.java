package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zF, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3492zF extends AbstractC2412ko {
    static final /* synthetic */ boolean h = true;

    public C3492zF(S10 s10, S10 s11) {
        super(s10, s11);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return 65535;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 65535;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 43;
    }

    public S10 J2() {
        return this.a;
    }

    public S10 K2() {
        return (S10) this.b.get(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC0824Ac X0() {
        if (h || r2()) {
            return K2().b.X0();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public boolean b(AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.l2();
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
    public final boolean l2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public C3492zF n0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public boolean r2() {
        return K2().L();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        return super.toString() + " (" + a() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, S10 s10) {
        throw new C3405y10("as long as we're analyzing SSA IR.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.a(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public void a(L6 l6) {
        throw new C3405y10("This DEX-specific instruction should not be seen in the CF backend");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return K2().w();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        throw new C3405y10("This DEX-specific instruction should not be seen in the CF backend");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public void a(C0702m c0702m, C2433l20 c2433l20) {
        super.a(c0702m, c2433l20);
        if (!(this instanceof C1289Qd) && !h && !K2().w().equals(a())) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
