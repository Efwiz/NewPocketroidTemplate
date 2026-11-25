package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0772w0;
import com.android.tools.r8.ir.optimize.C3580q0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3455yl extends AbstractC3082tl {
    static final /* synthetic */ boolean e = true;
    private final com.android.tools.r8.ir.optimize.F c;
    private final com.android.tools.r8.utils.A1 d;

    public C3455yl(C0702m c0702m, com.android.tools.r8.ir.optimize.M m) {
        super(c0702m, m);
        this.c = m.a();
        this.d = c0702m.Y();
    }

    @Override // com.android.tools.r8.internal.AbstractC3082tl
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final C0772w0 a(C2409kl c2409kl, C2133h6 c2133h6, com.android.tools.r8.utils.y3 y3Var) {
        if (this.d.i0) {
            C0919Dd.b(this.a);
        }
        c2409kl.u();
        c2409kl.E();
        com.android.tools.r8.ir.optimize.E0.c(c2409kl, this.d);
        com.android.tools.r8.ir.optimize.E0.a(this.a, c2409kl);
        com.android.tools.r8.ir.optimize.E0.a(c2409kl, this.c, this.d);
        com.android.tools.r8.ir.optimize.E0.a(c2409kl, this.d);
        com.android.tools.r8.ir.optimize.E0.b(c2409kl, this.d);
        if (this.d.p()) {
            for (C3332x4 c3332x4 : c2409kl.d) {
                if (c3332x4.D()) {
                    for (C3332x4 c3332x5 : c3332x4.n().c()) {
                        C3332x4 c3332x4J = c3332x5.j();
                        if (c3332x4J != null && c3332x4J.y().size() > 1 && c3332x4J.t().size() > 1 && c3332x4J.u().size() > 1) {
                            N1 n1 = new N1();
                            n1.a(c3332x5);
                            n1.b(c3332x5.k().Y0());
                            c3332x5.p().addFirst(n1);
                        }
                    }
                }
            }
        }
        if (!e) {
            this.b.b(c2409kl);
        }
        y3Var.a("Allocate registers");
        com.android.tools.r8.ir.regalloc.b bVar = new com.android.tools.r8.ir.regalloc.b(this.a, c2409kl);
        bVar.a();
        y3Var.c();
        if (c2409kl.k().c()) {
            y3Var.a("Peephole optimize");
            for (int i = 0; i < 2; i++) {
                com.android.tools.r8.ir.optimize.F.a((C0702m<?>) this.a, c2409kl);
                C3580q0.a((C0702m<?>) this.a, c2409kl, bVar);
            }
            y3Var.c();
        }
        y3Var.a("Clean up");
        com.android.tools.r8.ir.optimize.F.a(c2409kl, bVar);
        com.android.tools.r8.ir.optimize.F.a((C0702m<?>) this.a, c2409kl);
        y3Var.c();
        return new C2322jf(c2409kl, c2133h6, bVar, this.d, c2409kl.k()).a();
    }
}
