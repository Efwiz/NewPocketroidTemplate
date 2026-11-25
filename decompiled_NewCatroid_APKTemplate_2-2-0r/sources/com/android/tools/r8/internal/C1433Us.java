package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.ir.optimize.EnumC3542a0;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Us, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1433Us extends AbstractC1305Qs {
    static final /* synthetic */ boolean l = true;
    private final C0737r2 k;

    public C1433Us(C0684j2 c0684j2, C0737r2 c0737r2, S10 s10, List list) {
        super(c0684j2, s10, list);
        this.k = c0737r2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 37;
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs, com.android.tools.r8.internal.AbstractC1084Is
    public final C0767v2 L2() {
        return this.k.e;
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is
    public final EnumC1953et M2() {
        return EnumC1953et.j;
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is
    protected final String N2() {
        return "Polymorphic";
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs
    public final boolean R2() {
        return false;
    }

    public final C0737r2 T2() {
        return this.k;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 s1;
        int iP2 = P2();
        c2322jf.a(iP2);
        if (d(c2322jf)) {
            if (!l) {
                c(c2322jf);
            }
            s1 = new com.android.tools.r8.dex.code.T1(b(c2322jf), iP2, S2(), this.k);
        } else {
            int[] iArr = new int[5];
            s1 = new com.android.tools.r8.dex.code.S1(a(c2322jf, iArr), S2(), this.k, iArr[0], iArr[1], iArr[2], iArr[3], iArr[4]);
        }
        a(s1, c2322jf);
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs, com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C1433Us) && this.k.equals(abstractC2412ko.f0().k) && super.b(abstractC2412ko);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1433Us f0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs
    public final CN g(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs
    public final AbstractC0748t0 h(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        C0684j2 c0684j2S2 = S2();
        l6.a(new I7(182, l6.a.a().a(c0684j2S2.e, this.k, c0684j2S2.f), false), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        S2();
        c3550d0.getClass();
        return com.android.tools.r8.ir.optimize.V.c;
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs
    public final com.android.tools.r8.ir.optimize.W a(com.android.tools.r8.graph.o5 o5Var, EnumC3542a0 enumC3542a0, com.android.tools.r8.ir.optimize.N n, V9 v9, AbstractC3477z20 abstractC3477z20) {
        throw new C3405y10("Unexpected invoke-polymorphic with `" + o5Var.w() + "` as single target");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        abstractC1855de.a(this);
        return null;
    }
}
