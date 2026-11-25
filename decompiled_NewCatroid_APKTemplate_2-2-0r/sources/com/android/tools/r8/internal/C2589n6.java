package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3762m1;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.n6, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2589n6 extends AbstractC2755p6 {
    private final Set a = OU.c();
    private final Set b = OU.c();

    C2589n6(final C0702m c0702m, C2205i6 c2205i6) {
        final com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        CN cnA = QE.a(c0702m, com.android.tools.r8.graph.H3.a(c0702m), ((C3733i) c0702m.f()).d(), new Predicate() { // from class: com.android.tools.r8.internal.n6$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2589n6.a(c0702m, a1Y, (com.android.tools.r8.graph.o5) obj);
            }
        });
        for (C2768pH c2768pH : c2205i6.a()) {
            com.android.tools.r8.graph.o5 o5VarB = c2768pH.b();
            C0684j2 reference = o5VarB.getReference();
            if (!cnA.b(o5VarB) && (!c0702m.Y().P0().i || !o5VarB.e().D1().d())) {
                if (!o5VarB.e().v1() || !c0702m.J() || !c0702m.B().a(o5VarB.a())) {
                    int iF = c2768pH.f();
                    if (iF == 1) {
                        this.a.add(reference);
                    } else if (iF > 1) {
                        this.b.add(reference);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0702m c0702m, com.android.tools.r8.utils.A1 a1, com.android.tools.r8.graph.o5 o5Var) {
        C3762m1 c3762m1A = c0702m.a(o5Var);
        return !c3762m1A.e((com.android.tools.r8.shaking.K0) a1) || c3762m1A.c((com.android.tools.r8.shaking.K0) a1);
    }

    @Override // com.android.tools.r8.internal.AbstractC2755p6
    public final boolean b(com.android.tools.r8.graph.o5 o5Var) {
        return this.b.contains(o5Var.getReference());
    }

    @Override // com.android.tools.r8.internal.AbstractC2755p6
    public final void c(com.android.tools.r8.graph.o5 o5Var) {
        this.a.remove(o5Var.getReference());
        this.b.remove(o5Var.getReference());
    }

    @Override // com.android.tools.r8.internal.AbstractC2755p6
    public final boolean a(com.android.tools.r8.graph.o5 o5Var) {
        return this.a.contains(o5Var.getReference());
    }
}
