package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3804u;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2115gt extends AbstractC1332Rs {
    static final /* synthetic */ boolean l = true;
    public static final /* synthetic */ int m = 0;

    public C2115gt(C0684j2 c0684j2, S10 s10, List list) {
        super(c0684j2, s10, list);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 40;
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is
    public final EnumC1953et M2() {
        return EnumC1953et.g;
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is
    protected final String N2() {
        return "Virtual";
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs
    public final boolean R2() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 m1;
        int iP2 = P2();
        c2322jf.a(iP2);
        if (d(c2322jf)) {
            if (!l) {
                c(c2322jf);
            }
            int iB = b(c2322jf);
            m1 = (e(c2322jf) || f(c2322jf)) ? new com.android.tools.r8.dex.code.N1(iB, iP2, S2()) : new com.android.tools.r8.dex.code.Z1(iB, iP2, S2());
        } else {
            int[] iArr = new int[5];
            int iA = a(c2322jf, iArr);
            m1 = (e(c2322jf) || f(c2322jf)) ? new com.android.tools.r8.dex.code.M1(iA, S2(), iArr[0], iArr[1], iArr[2], iArr[3], iArr[4]) : new com.android.tools.r8.dex.code.Y1(iA, S2(), iArr[0], iArr[1], iArr[2], iArr[3], iArr[4]);
        }
        a(m1, c2322jf);
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs, com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.f2() && super.b(abstractC2412ko);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean f2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C2115gt i0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1332Rs
    public final AbstractC0748t0 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C1207Nf c1207Nf) {
        return a(c0702m, o5Var, c1207Nf, S2());
    }

    public static AbstractC0748t0 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C1207Nf c1207Nf, C0684j2 c0684j2) {
        com.android.tools.r8.graph.W0 w0A;
        AbstractC0727q0 abstractC0727q0C;
        com.android.tools.r8.graph.W0 w0C;
        if (c0702m.f().i()) {
            C0702m<C3733i> c0702mI0 = c0702m.i0();
            w0A = ((C3733i) c0702mI0.f()).a(c0702mI0, c0684j2, o5Var, false, c0702m, c1207Nf);
        } else {
            C0767v2 c0767v2 = c0684j2.e;
            w0A = (c0767v2.O0() && (abstractC0727q0C = c0702m.c(c0767v2)) != null && (abstractC0727q0C.f0() || c0702m.W().a(abstractC0727q0C.d)) && (w0C = abstractC0727q0C.c(c0684j2)) != null && (abstractC0727q0C.e.h() || w0C.z1())) ? w0C : null;
        }
        return com.android.tools.r8.graph.W0.a(w0A, (com.android.tools.r8.graph.Q0) c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.d(S2(), o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new I7(182, S2(), false), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0767v2 c0767v2, com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, U9 u9, S9 s9) {
        return T9.a(this, c0767v2, o5Var, c0702m, u9, s9);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    final void a(C3804u c3804u) {
        c3804u.h(S2());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.b(S2(), this.b);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        abstractC1855de.a(this);
        return null;
    }
}
