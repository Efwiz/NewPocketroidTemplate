package com.android.tools.r8.internal;

import com.android.tools.r8.dex.C0572k$$ExternalSyntheticLambda8;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.InterfaceC3910i3;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class K2 {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final P2 b;

    public K2(C0702m c0702m, P2 p2) {
        this.a = c0702m;
        this.b = p2;
    }

    public final void a(H2 h2, C2844qM c2844qM, ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        y3Var.a("Enqueue methods for reprocessing");
        y3Var.a("Rewrite methods to reprocess");
        c2844qM.a(this.a);
        y3Var.c();
        y3Var.a("Enqueue methods with non-trivial info");
        a(h2, c2844qM);
        y3Var.c();
        y3Var.a("Enqueue affected methods");
        if (h2 != null) {
            a(h2, c2844qM, executorService);
        }
        y3Var.c();
        y3Var.c();
    }

    private void a(final H2 h2, final C2844qM c2844qM) {
        final AbstractC1476Wj abstractC1476WjE = this.a.E();
        Iterator<C0729q2> it = ((C3733i) this.a.f()).d().iterator();
        while (it.hasNext()) {
            it.next().g(new Consumer() { // from class: com.android.tools.r8.internal.K2$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(h2, c2844qM, abstractC1476WjE, (com.android.tools.r8.graph.o5) obj);
                }
            }, new C0572k$$ExternalSyntheticLambda8());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(H2 h2, C2844qM c2844qM, AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.o5 o5Var) {
        if (o5Var.e().a1().F0()) {
            return;
        }
        if (h2 != null && h2.j(h2.h(o5Var.getReference()))) {
            if (!c && ((C3733i) this.a.f()).c(o5Var)) {
                throw new AssertionError();
            }
            c2844qM.a(abstractC1476Wj, o5Var);
            this.a.Y().n1.H0.accept(o5Var);
            return;
        }
        com.android.tools.r8.ir.optimize.info.a aVarI = o5Var.B().i();
        XE xeA = this.b.a(o5Var);
        xeA.getClass();
        aVarI.getClass();
        if (aVarI instanceof com.android.tools.r8.ir.optimize.info.b) {
            com.android.tools.r8.ir.optimize.info.b bVarA = aVarI.a();
            for (int i = 0; i < o5Var.e().h1(); i++) {
                if (aVarI.a(i).g() || xeA.a(i).a(o5Var, bVarA, i, o5Var.a(i))) {
                    if (((C3733i) this.a.f()).c(o5Var)) {
                        return;
                    }
                    c2844qM.a(abstractC1476Wj, o5Var);
                    this.a.Y().n1.H0.accept(o5Var);
                    return;
                }
            }
        }
    }

    private void a(final H2 h2, final C2844qM c2844qM, ExecutorService executorService) {
        final AbstractC1476Wj abstractC1476WjE = this.a.E();
        C3879c3.a(((C3733i) this.a.f()).d(), new InterfaceC3910i3() { // from class: com.android.tools.r8.internal.K2$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3910i3
            public final Object apply(Object obj) {
                return this.f$0.a(c2844qM, abstractC1476WjE, h2, (C0729q2) obj);
            }
        }, executorService).forEach(new Consumer() { // from class: com.android.tools.r8.internal.K2$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c2844qM.a((List) obj, abstractC1476WjE);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ List a(final C2844qM c2844qM, final AbstractC1476Wj abstractC1476Wj, final H2 h2, C0729q2 c0729q2) {
        final ArrayList arrayList = new ArrayList();
        c0729q2.g(new Consumer() { // from class: com.android.tools.r8.internal.K2$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2844qM, abstractC1476Wj, h2, arrayList, (com.android.tools.r8.graph.o5) obj);
            }
        }, new C0572k$$ExternalSyntheticLambda8());
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2844qM c2844qM, AbstractC1476Wj abstractC1476Wj, H2 h2, List list, com.android.tools.r8.graph.o5 o5Var) {
        if (c2844qM.b(abstractC1476Wj, o5Var)) {
            return;
        }
        J2 j2 = new J2(this.a, o5Var, h2);
        o5Var.a(j2);
        if (((Boolean) j2.f()).booleanValue()) {
            if (!c && o5Var.B().u()) {
                throw new AssertionError();
            }
            list.add(o5Var);
        }
    }
}
