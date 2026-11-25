package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.InterfaceC3910i3;
import java.util.ArrayDeque;
import java.util.concurrent.ExecutorService;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2914rM extends WE {
    static final /* synthetic */ boolean g = true;
    private final VE c;
    private final C1257Pb d;
    private final ArrayDeque e;
    private final CN f;

    private C2914rM(C0702m c0702m, C2205i6 c2205i6, VE ve) {
        this.f = CN.b();
        this.c = ve;
        this.d = c0702m.p();
        this.e = a(c2205i6);
    }

    @Override // com.android.tools.r8.internal.TE
    public final C1230Ob a(com.android.tools.r8.graph.o5 o5Var) {
        return this.d.a(o5Var);
    }

    @Override // com.android.tools.r8.internal.TE
    public final VE b() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.TE
    public final boolean d(com.android.tools.r8.graph.o5 o5Var) {
        if (g || !this.a.b(o5Var)) {
            return !this.f.b(o5Var);
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.utils.y3 a(QM qm, com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.utils.y3 y3VarA = qm.a(o5Var, this.d.a(o5Var));
        y3VarA.c();
        return y3VarA;
    }

    private static ArrayDeque a(C2205i6 c2205i6) {
        ArrayDeque arrayDeque = new ArrayDeque();
        while (!c2205i6.b()) {
            arrayDeque.addLast(c2205i6.c());
        }
        return arrayDeque;
    }

    final void a(final QM qm, com.android.tools.r8.ir.optimize.info.o oVar, ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        com.android.tools.r8.utils.w3 w3VarA = y3Var.a(C3879c3.a(executorService), "secondary-processor");
        while (!this.e.isEmpty()) {
            CN cn = (CN) this.e.removeFirst();
            this.a = cn;
            boolean z = g;
            if (!z && cn.isEmpty()) {
                throw new AssertionError();
            }
            if (!z && !this.b.isEmpty()) {
                throw new AssertionError();
            }
            do {
                if (!g) {
                    oVar.b();
                }
                w3VarA.a(C3879c3.a(this.a, new InterfaceC3910i3() { // from class: com.android.tools.r8.internal.rM$$ExternalSyntheticLambda0
                    @Override // com.android.tools.r8.utils.InterfaceC3910i3
                    public final Object apply(Object obj) {
                        return this.f$0.a(qm, (com.android.tools.r8.graph.o5) obj);
                    }
                }, executorService));
                oVar.c();
                this.f.a(this.a);
                if (this.b.isEmpty()) {
                    this.a = CN.e();
                } else {
                    this.a = this.b;
                    this.b = CN.c();
                }
            } while (!this.a.isEmpty());
        }
        w3VarA.a();
    }
}
