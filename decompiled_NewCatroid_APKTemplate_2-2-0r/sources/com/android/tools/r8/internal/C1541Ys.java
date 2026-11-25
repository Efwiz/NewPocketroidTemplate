package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0753u;
import java.util.Collection;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ys, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1541Ys implements C7 {
    private final C0722p1 a;

    public C1541Ys(C0702m c0702m) {
        this.a = c0702m.a();
    }

    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        if (!b7.J()) {
            return C3224ve.c();
        }
        I7 i7L = b7.l();
        if (i7L.J()) {
            if (!i7L.T().b(this.a)) {
                C0684j2 c0684j2T = i7L.T();
                if (c0684j2T.B0() != o5Var.t()) {
                    return C3224ve.c();
                }
                com.android.tools.r8.graph.o5 o5VarF = o5Var.a().f(c0684j2T);
                if (o5VarF == null || o5VarF.getAccessFlags().j() || o5VarF.e().E0() || (i7L.V() && o5VarF.H())) {
                    return C3224ve.c();
                }
                return o5VarF.getAccessFlags().h() ? a(i7L) : a(i7L, o5VarF);
            }
        }
        return C3224ve.c();
    }

    private static C3224ve a(final I7 i7) {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.Ys$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return C1541Ys.a(i7, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection a(I7 i7, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        return AbstractC1639am.c(new I7(182, i7.T(), i7.V()));
    }

    private C3224ve a(final I7 i7, final com.android.tools.r8.graph.o5 o5Var) {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.Ys$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(o5Var, i7, interfaceC1374Ti, ac, g7, o5Var2, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection a(com.android.tools.r8.graph.o5 o5Var, I7 i7, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        C0684j2 c0684j2A = a(o5Var);
        C0729q2 c0729q2A = o5Var.a();
        synchronized (c0729q2A.Y()) {
            if (c0729q2A.f(c0684j2A) == null) {
                com.android.tools.r8.graph.o5 o5VarA = o5Var.e().a(c0729q2A, c0684j2A);
                C0753u c0753uA = C1071Ii.a(this.a).a(c0684j2A, c0729q2A.isInterface()).b(o5Var.getReference()).a();
                c0729q2A.a(o5VarA.e());
                g7.a(new C1514Xs(o5VarA, o5Var, c0753uA));
            }
        }
        return AbstractC1639am.c(new I7(183, c0684j2A, i7.V()));
    }

    private C0684j2 a(AbstractC0748t0 abstractC0748t0) {
        return abstractC0748t0.getReference().a(this.a, this.a.c("$invoke$special$" + abstractC0748t0.u().toString()));
    }
}
