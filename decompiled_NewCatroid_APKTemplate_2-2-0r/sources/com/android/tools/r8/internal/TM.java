package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.InterfaceC3910i3;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.concurrent.ExecutorService;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class TM extends WE {
    static final /* synthetic */ boolean h = true;
    private final C0702m c;
    private final AbstractC2755p6 d;
    private final VE e;
    private final ArrayDeque f;
    private C1257Pb g;

    private TM(C0702m c0702m, C2205i6 c2205i6, VE ve) {
        this.c = c0702m;
        this.d = c2205i6.a(c0702m);
        this.e = ve;
        this.f = a(c0702m, c2205i6);
    }

    static TM a(C0702m c0702m, VE ve, ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        int i = C2205i6.c;
        return new TM(c0702m, new C2358k6(c0702m).a(executorService, y3Var), ve);
    }

    @Override // com.android.tools.r8.internal.TE
    public final VE b() {
        return this.e;
    }

    @Override // com.android.tools.r8.internal.TE
    public final boolean c() {
        return true;
    }

    @Override // com.android.tools.r8.internal.TE
    public final boolean d(com.android.tools.r8.graph.o5 o5Var) {
        if (h || !this.a.b(o5Var)) {
            return !o5Var.e().M1();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.TE
    public final C1230Ob a(com.android.tools.r8.graph.o5 o5Var) {
        return this.g.a(o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.utils.y3 a(QM qm, com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.utils.y3 y3VarA = qm.a(o5Var, this.g.a(o5Var));
        y3VarA.c();
        return y3VarA;
    }

    @Override // com.android.tools.r8.internal.WE, com.android.tools.r8.internal.TE
    public final AbstractC2755p6 a() {
        return this.d;
    }

    private static ArrayDeque a(C0702m c0702m, C2205i6 c2205i6) {
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        ArrayDeque arrayDeque = new ArrayDeque();
        Collection<C2768pH> collectionA = c2205i6.a();
        while (!collectionA.isEmpty()) {
            arrayDeque.addLast(c2205i6.c());
        }
        a1Y.n1.x.accept(arrayDeque);
        return arrayDeque;
    }

    final void a(final QM qm, SM sm, RM rm, com.android.tools.r8.utils.y3 y3Var, ExecutorService executorService) {
        com.android.tools.r8.utils.w3 w3VarA = y3Var.a(C3879c3.a(executorService), "primary-processor");
        while (!this.f.isEmpty()) {
            this.g = this.c.p();
            CN cn = (CN) this.f.removeFirst();
            this.a = cn;
            boolean z = h;
            if (!z && cn.isEmpty()) {
                throw new AssertionError();
            }
            if (!z && !this.b.isEmpty()) {
                throw new AssertionError();
            }
            do {
                sm.a(this.a);
                w3VarA.a(C3879c3.a(this.a, new InterfaceC3910i3() { // from class: com.android.tools.r8.internal.TM$$ExternalSyntheticLambda0
                    @Override // com.android.tools.r8.utils.InterfaceC3910i3
                    public final Object apply(Object obj) {
                        return this.f$0.a(qm, (com.android.tools.r8.graph.o5) obj);
                    }
                }, executorService));
                rm.a(this.a, executorService);
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
