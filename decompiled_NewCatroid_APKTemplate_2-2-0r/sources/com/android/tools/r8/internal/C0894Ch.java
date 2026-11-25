package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.C3733i;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ch, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0894Ch {
    private final IdentityHashMap a = new IdentityHashMap();
    private final MR b = new MR();

    C0894Ch() {
    }

    public final void a(final C0702m c0702m) {
        ((C3733i) c0702m.f()).v().a(new Consumer() { // from class: com.android.tools.r8.internal.Ch$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0702m, (com.android.tools.r8.graph.R2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0702m c0702m, com.android.tools.r8.graph.R2 r2) {
        com.android.tools.r8.graph.U2 u2 = (com.android.tools.r8.graph.U2) r2;
        final com.android.tools.r8.graph.m5 m5VarU = ((C3733i) c0702m.f()).c(u2.e()).u();
        if (m5VarU == null || u2.k() || u2.s()) {
            return;
        }
        u2.c(new Consumer() { // from class: com.android.tools.r8.internal.Ch$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(m5VarU, (com.android.tools.r8.graph.o5) obj);
            }
        });
        this.b.a(u2.f(), m5VarU.e());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(com.android.tools.r8.graph.W0 w0) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.m5 m5Var, com.android.tools.r8.graph.o5 o5Var) {
        ((List) this.a.computeIfAbsent(o5Var.e(), new Function() { // from class: com.android.tools.r8.internal.Ch$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0894Ch.a((com.android.tools.r8.graph.W0) obj);
            }
        })).add(m5Var);
    }

    final void a(com.android.tools.r8.graph.o5 o5Var, Consumer consumer) {
        List<com.android.tools.r8.graph.m5> list = (List) this.a.get(o5Var.e());
        if (list != null) {
            for (com.android.tools.r8.graph.m5 m5Var : list) {
                int iD = this.b.d(m5Var.e()) - 1;
                if (iD > 0) {
                    this.b.a(iD, m5Var.e());
                } else {
                    consumer.accept(m5Var);
                }
            }
        }
    }
}
