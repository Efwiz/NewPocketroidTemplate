package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.ir.optimize.EnumC3542a0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3749l;
import com.android.tools.r8.shaking.C3804u;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1721bt extends AbstractC1305Qs {
    static final /* synthetic */ boolean l = true;
    public static final /* synthetic */ int m = 0;
    private final boolean k;

    public C1721bt(C0684j2 c0684j2, S10 s10, List list) {
        this(c0684j2, s10, list, false);
        if (!l && c0684j2.h.f.size() != list.size()) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 38;
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is
    public final EnumC1953et M2() {
        return EnumC1953et.e;
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is
    protected final String N2() {
        return "Static";
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs
    public final boolean R2() {
        return this.k;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 u1;
        int iP2 = P2();
        c2322jf.a(iP2);
        if (d(c2322jf)) {
            if (!l) {
                c(c2322jf);
            }
            u1 = new com.android.tools.r8.dex.code.V1(b(c2322jf), iP2, S2());
        } else {
            int[] iArr = new int[5];
            u1 = new com.android.tools.r8.dex.code.U1(a(c2322jf, iArr), S2(), iArr[0], iArr[1], iArr[2], iArr[3], iArr[4]);
        }
        a(u1, c2322jf);
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs, com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C1721bt) && super.b(abstractC2412ko);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1721bt g0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs
    public final AbstractC0748t0 h(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.graph.W0 w0B;
        com.android.tools.r8.graph.W0 w0B2;
        C0684j2 c0684j2S2 = S2();
        if (c0702m.f().i()) {
            C0702m<C3733i> c0702mI0 = c0702m.i0();
            w0B = ((C3733i) c0702mI0.f()).b(c0684j2S2, o5Var, c0702mI0);
            if (!l && (w0B2 = C0664h.b(c0684j2S2, o5Var)) != null && !AbstractC1305Qs.j && w0B2 != w0B) {
                throw new AssertionError();
            }
        } else {
            AbstractC0727q0 abstractC0727q0C = c0702m.c(S2().e);
            if (abstractC0727q0C == null || !(abstractC0727q0C.f0() || c0702m.W().a(abstractC0727q0C.d))) {
                c0702m.f().getClass();
                w0B = C0664h.b(c0684j2S2, o5Var);
            } else {
                w0B = abstractC0727q0C.c(S2());
            }
        }
        return com.android.tools.r8.graph.W0.a(w0B, (com.android.tools.r8.graph.Q0) c0702m);
    }

    public C1721bt(C0684j2 c0684j2, S10 s10, List list, boolean z) {
        super(c0684j2, s10, list);
        this.k = z;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.c(S2(), o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC1305Qs
    public final com.android.tools.r8.ir.optimize.W a(com.android.tools.r8.graph.o5 o5Var, EnumC3542a0 enumC3542a0, com.android.tools.r8.ir.optimize.N n, V9 v9, AbstractC3477z20 abstractC3477z20) {
        return n.a(this, o5Var, enumC3542a0, v9, abstractC3477z20);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new I7(184, S2(), this.k), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0767v2 c0767v2, com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, U9 u9, S9 s9) {
        return T9.a(this, c0767v2, o5Var, c0702m, u9, s9);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        if (!c0702m.q() || c0702m.Y().W0) {
            return true;
        }
        C2974sB c2974sBX = c0702m.x();
        c2974sBX.getClass();
        if (c2974sBX.a(S2(), this.b)) {
            return false;
        }
        if (!c0702m.f().i()) {
            return true;
        }
        C0702m<C3733i> c0702mI0 = c0702m.i0();
        com.android.tools.r8.graph.I4.c<?> cVarO = ((C3733i) c0702mI0.f()).b(S2(), this.k).o();
        if (cVarO == null) {
            return true;
        }
        AbstractC0748t0 abstractC0748t0C = cVarO.c();
        if (!abstractC0748t0C.e().E0() || cVarO.a(o5Var, c0702mI0).b()) {
            return true;
        }
        C3749l c3749lT = c0702m.t();
        c3749lT.getClass();
        if (c3749lT.a(abstractC0748t0C.getReference()).f()) {
            return false;
        }
        if (abstractC0748t0C.B().a(this, c0702m.Y())) {
            return true;
        }
        if (c2332jo.a()) {
            return false;
        }
        return abstractC0748t0C.a().a(c0702m, o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        abstractC1855de.a(this);
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    final void a(C3804u c3804u) {
        c3804u.e(S2());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.b(S2(), this.b, this.k);
    }
}
