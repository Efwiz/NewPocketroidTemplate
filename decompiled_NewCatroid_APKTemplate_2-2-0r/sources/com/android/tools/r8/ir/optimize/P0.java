package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.S9;
import com.android.tools.r8.internal.U9;
import com.android.tools.r8.utils.C3932m1;
import com.android.tools.r8.utils.D3;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class P0 {
    private static final N0 a = new N0(3, AbstractC1639am.i(), AbstractC1639am.i());
    static final /* synthetic */ boolean b = true;

    public static N0 a(C2409kl c2409kl, J0 j0) {
        O0 o0 = new O0();
        D3 d3A = new I0(new C3932m1(), j0, o0).a(c2409kl.j());
        if (d3A.f()) {
            o0.c = 3;
        } else {
            M0 m0 = (M0) d3A.c().i();
            o0.c = m0.a;
            o0.b = m0.b;
        }
        return L0.a(o0.c) ? a : new N0(o0.c, o0.a, o0.b);
    }

    public static N0 a(final C0702m c0702m, C2409kl c2409kl) {
        if (!b && !c2409kl.h().e().S1()) {
            throw new AssertionError();
        }
        final S10 s10O = c2409kl.o();
        if (!s10O.S()) {
            return a;
        }
        final o5 o5VarH = c2409kl.h();
        return a(c2409kl, new J0() { // from class: com.android.tools.r8.ir.optimize.P0$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.ir.optimize.J0
            public final int a(AbstractC2412ko abstractC2412ko) {
                return P0.a(s10O, c0702m, o5VarH, abstractC2412ko);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int a(S10 s10, C0702m c0702m, o5 o5Var, AbstractC2412ko abstractC2412ko) {
        if ((abstractC2412ko.Z1() && abstractC2412ko.c0().U2() == s10) || ((abstractC2412ko.P1() && abstractC2412ko.S().e() == s10) || (abstractC2412ko.k2() && abstractC2412ko.m0().e() == s10))) {
            return !abstractC2412ko.h().D() ? 2 : 3;
        }
        return abstractC2412ko.c(c0702m, o5Var) ? 3 : 1;
    }

    public static N0 a(final C0702m c0702m, final o5 o5Var, final C2409kl c2409kl) {
        if (b || c2409kl.h().e().E0()) {
            return a(c2409kl, new J0() { // from class: com.android.tools.r8.ir.optimize.P0$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.ir.optimize.J0
                public final int a(AbstractC2412ko abstractC2412ko) {
                    return P0.a(c2409kl, o5Var, c0702m, abstractC2412ko);
                }
            });
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int a(C2409kl c2409kl, o5 o5Var, C0702m c0702m, AbstractC2412ko abstractC2412ko) {
        if (abstractC2412ko.a(c2409kl.h().t(), o5Var, c0702m, U9.a, S9.a)) {
            return !abstractC2412ko.h().D() ? 2 : 3;
        }
        return (abstractC2412ko.Z1() || abstractC2412ko.c(c0702m, o5Var)) ? 3 : 1;
    }
}
