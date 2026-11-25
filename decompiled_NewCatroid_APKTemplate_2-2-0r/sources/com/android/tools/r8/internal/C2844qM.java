package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.InterfaceC3986x0;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2844qM {
    private final C1749cD a;

    C2844qM(AbstractC1476Wj abstractC1476Wj) {
        this.a = C1749cD.a(abstractC1476Wj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.o5 o5Var) {
        this.a.a(abstractC1476Wj, o5Var);
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var) {
        this.a.a(o5Var);
    }

    public final boolean b(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.o5 o5Var) {
        return this.a.b(abstractC1476Wj, o5Var);
    }

    public final void d(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.o5 o5Var) {
        this.a.a(abstractC1476Wj, o5Var.getReference());
    }

    public final void a(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.o5 o5Var) {
        this.a.a(abstractC1476Wj, o5Var);
    }

    public final void a(Collection collection, final AbstractC1476Wj abstractC1476Wj) {
        collection.forEach(new Consumer() { // from class: com.android.tools.r8.internal.qM$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c(abstractC1476Wj, (com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    public final C2844qM a(C1749cD c1749cD) {
        this.a.a(c1749cD);
        return this;
    }

    public final void a(CN cn) {
        cn.forEach(new Consumer() { // from class: com.android.tools.r8.internal.qM$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    public final C2844qM a(Set set) {
        this.a.a(set);
        return this;
    }

    public final C2844qM a(C0702m c0702m) {
        C1749cD c1749cD = this.a;
        c1749cD.getClass();
        c1749cD.c(c0702m.E());
        return this;
    }

    public final C2844qM a(AbstractC1476Wj abstractC1476Wj) {
        this.a.c(abstractC1476Wj);
        return this;
    }

    final C2914rM a(final C0702m c0702m, VE ve, ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        Set setB = ((C3733i) c0702m.f()).B();
        if (!setB.isEmpty()) {
            final CN cnB = CN.b();
            setB.forEach(new Consumer() { // from class: com.android.tools.r8.internal.qM$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2844qM.a(c0702m, cnB, (C0684j2) obj);
                }
            });
            a(cnB);
        }
        if (this.a.b()) {
            return null;
        }
        return new C2914rM(c0702m, new C3498zL(c0702m, this.a.a(c0702m)).a(executorService, y3Var), ve);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0702m c0702m, CN cn, C0684j2 c0684j2) {
        c0702m.getClass();
        C0729q2 c0729q2A = C0729q2.a(c0702m.c(c0684j2.e));
        com.android.tools.r8.graph.W0 w0C = c0684j2.c((AbstractC0727q0) c0729q2A);
        if (w0C != null) {
            cn.getClass();
            cn.add(new com.android.tools.r8.graph.o5(c0729q2A, w0C));
        }
    }

    public final void a(com.android.tools.r8.utils.B0 b0) throws IOException {
        final C1749cD c1749cD = this.a;
        Objects.requireNonNull(c1749cD);
        b0.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.qM$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                c1749cD.a((InterfaceC3986x0) obj);
            }
        });
    }
}
