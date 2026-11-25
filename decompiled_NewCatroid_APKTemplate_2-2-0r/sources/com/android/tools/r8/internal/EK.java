package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.utils.C3879c3;
import java.util.concurrent.ExecutorService;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EK extends WE {
    private final VE c;
    private final C1257Pb d;

    private EK(C1257Pb c1257Pb, VE ve, CN cn) {
        this.c = ve;
        this.d = c1257Pb;
        this.a = cn;
    }

    public static CK a(VE ve, C1257Pb c1257Pb) {
        return new CK(ve, c1257Pb);
    }

    @Override // com.android.tools.r8.internal.TE
    public final VE b() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.TE
    public final boolean d(com.android.tools.r8.graph.o5 o5Var) {
        return true;
    }

    public static EK a(com.android.tools.r8.graph.o5 o5Var, VE ve, C0702m c0702m) {
        CN cnB = CN.b();
        cnB.add(o5Var);
        return new EK(c0702m.p(), ve, cnB);
    }

    public static EK a(com.android.tools.r8.graph.o5 o5Var, VE ve, C1257Pb c1257Pb) {
        CN cnB = CN.b();
        cnB.add(o5Var);
        return new EK(c1257Pb, ve, cnB);
    }

    public static EK a(CN cn, VE ve, C0702m c0702m) {
        return new EK(c0702m.p(), ve, cn);
    }

    @Override // com.android.tools.r8.internal.TE
    public final C1230Ob a(com.android.tools.r8.graph.o5 o5Var) {
        return this.d.a(o5Var);
    }

    public final void a(DK dk) {
        while (!this.a.isEmpty()) {
            for (com.android.tools.r8.graph.o5 o5Var : this.a) {
                dk.a(o5Var, this.d.a(o5Var));
            }
            if (this.b.isEmpty()) {
                this.a = CN.e();
            } else {
                this.a = this.b;
                this.b = CN.c();
            }
        }
    }

    public final void a(final DK dk, ExecutorService executorService) {
        while (!this.a.isEmpty()) {
            C3879c3.a(this.a, new com.android.tools.r8.utils.U1() { // from class: com.android.tools.r8.internal.EK$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.utils.U1
                public final void accept(Object obj, int i) {
                    this.f$0.a(dk, (com.android.tools.r8.graph.o5) obj, i);
                }
            }, executorService, C3879c3.a.b);
            if (this.b.isEmpty()) {
                this.a = CN.e();
            } else {
                this.a = this.b;
                this.b = CN.c();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(DK dk, com.android.tools.r8.graph.o5 o5Var, int i) {
        dk.a(o5Var, this.d.a(o5Var));
    }
}
