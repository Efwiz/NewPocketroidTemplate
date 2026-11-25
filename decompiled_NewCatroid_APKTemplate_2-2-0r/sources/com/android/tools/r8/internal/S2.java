package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.C3733i;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class S2 {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final com.android.tools.r8.graph.H3 b;
    private final C1678bF c;

    public S2(C0702m c0702m, com.android.tools.r8.graph.H3 h3, C1678bF c1678bF) {
        this.a = c0702m;
        this.b = h3;
        this.c = c1678bF;
    }

    private boolean b(com.android.tools.r8.graph.o5 o5Var) {
        if (!d && o5Var.e().R0() && o5Var.e().D1().e()) {
            throw new AssertionError("Unexpected virtual method without library method override information: " + o5Var.w());
        }
        return o5Var.e().D1().c() || !((C3733i) this.a.f()).w().a(o5Var).e(this.a.Y());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c(com.android.tools.r8.graph.o5 o5Var) {
        if (!b(o5Var)) {
            return false;
        }
        if (o5Var.e().R0() && !o5Var.a().e.h() && !o5Var.getAccessFlags().h()) {
            return true;
        }
        a(o5Var);
        return false;
    }

    public final void a(Collection collection) {
        QE.a(this.a, this.b, collection, new Predicate() { // from class: com.android.tools.r8.internal.S2$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.c((com.android.tools.r8.graph.o5) obj);
            }
        }).forEach(new Consumer() { // from class: com.android.tools.r8.internal.S2$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.o5 o5Var) {
        this.c.b(o5Var, C2508m10.k());
    }
}
