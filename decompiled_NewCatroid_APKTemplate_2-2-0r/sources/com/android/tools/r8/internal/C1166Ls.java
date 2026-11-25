package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3804u;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ls, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1166Ls extends AbstractC1332Rs {
    static final /* synthetic */ boolean m = true;
    public static final /* synthetic */ int n = 0;
    private final boolean l;

    public C1166Ls(C0684j2 c0684j2, S10 s10, List list, boolean z) {
        super(c0684j2, s10, list);
        this.l = z;
        if (!m && c0684j2.f.toString().equals("<init>") && s10 != null) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 33;
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is
    public final EnumC1953et M2() {
        return EnumC1953et.c;
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is
    protected final String N2() {
        return "Direct";
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs
    public final boolean R2() {
        return this.l;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean W1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1166Ls Z() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 m1;
        int iP2 = P2();
        c2322jf.a(iP2);
        if (d(c2322jf)) {
            if (!m) {
                c(c2322jf);
            }
            m1 = new com.android.tools.r8.dex.code.N1(b(c2322jf), iP2, S2());
        } else {
            int[] iArr = new int[5];
            m1 = new com.android.tools.r8.dex.code.M1(a(c2322jf, iArr), S2(), iArr[0], iArr[1], iArr[2], iArr[3], iArr[4]);
        }
        a(m1, c2322jf);
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs, com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.W1() && super.b(abstractC2412ko);
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs, com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC2744p f(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        AbstractC0748t0 abstractC0748t0H;
        return (c0702m.a().b(S2()) && (abstractC0748t0H = h(c0702m, o5Var)) != null && (abstractC0748t0H instanceof com.android.tools.r8.graph.o5)) ? abstractC0748t0H.e().i1().a(this).f() : AbstractC2903rB.a(c0702m, this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0722p1 c0722p1) {
        return S2().b(c0722p1);
    }

    @Override // com.android.tools.r8.internal.AbstractC1332Rs
    public final AbstractC0748t0 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C1207Nf c1207Nf) {
        com.android.tools.r8.graph.W0 w0A;
        C0684j2 c0684j2S2 = S2();
        if (c0702m.f().i()) {
            C0702m<C3733i> c0702mI0 = c0702m.i0();
            w0A = ((C3733i) c0702mI0.f()).a(c0684j2S2, o5Var, c0702mI0);
            if (!m) {
                c0702m.f().getClass();
                com.android.tools.r8.graph.W0 w0A2 = C0664h.a(c0684j2S2, o5Var);
                if (w0A2 != null && !AbstractC1305Qs.j && w0A2 != w0A) {
                    throw new AssertionError();
                }
            }
        } else {
            c0702m.f().getClass();
            w0A = C0664h.a(c0684j2S2, o5Var);
        }
        return com.android.tools.r8.graph.W0.a(w0A, (com.android.tools.r8.graph.Q0) c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.a(S2(), o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new I7(183, S2(), this.l), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0767v2 c0767v2, com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, U9 u9, S9 s9) {
        return T9.a(this, c0767v2, c0702m, u9, s9);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.L a(C0702m c0702m, C2409kl c2409kl) {
        if (a(c0702m, c2409kl.h(), C2332jo.c)) {
            return com.android.tools.r8.ir.optimize.L.e();
        }
        if (!S2().b(c0702m.a())) {
            return com.android.tools.r8.ir.optimize.L.a();
        }
        if (U2().m() == c2409kl.o()) {
            return com.android.tools.r8.ir.optimize.L.e();
        }
        return com.android.tools.r8.ir.optimize.L.a(U2());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        abstractC1855de.a(this);
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.a(S2(), this.b, this.l);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    final void a(C3804u c3804u) {
        c3804u.a(S2());
    }
}
