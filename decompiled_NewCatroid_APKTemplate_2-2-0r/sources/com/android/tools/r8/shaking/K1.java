package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.CM;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class K1 {
    static final /* synthetic */ boolean d = true;
    private final I1 a = new I1(this);
    private final C0702m b;
    private final Consumer c;

    public K1(Consumer consumer, C0702m c0702m) {
        this.b = c0702m;
        this.c = consumer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0702m a() {
        return this.b;
    }

    public final void a(Set set) {
        com.android.tools.r8.synthesis.D D = this.b.D();
        C0722p1 c0722p1A = this.b.a();
        com.android.tools.r8.androidapi.b bVarD = this.b.d();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            C0767v2 c0767v2 = (C0767v2) it.next();
            C0729q2 c0729q2A = C0729q2.a(this.b.c(c0767v2));
            if (!d && c0729q2A == null) {
                throw new AssertionError();
            }
            this.c.accept(c0767v2);
            if (C0633d0.a(c0729q2A.s0(), c0722p1A, D, bVarD) == null) {
                C0658g0 c0658g0S0 = c0729q2A.s0();
                C0702m c0702m = this.b;
                I1 i1 = this.a;
                for (C0633d0 c0633d0 : c0658g0S0.c) {
                    c0633d0.a(c0702m, i1);
                }
            }
            c0729q2A.c(new Consumer() { // from class: com.android.tools.r8.shaking.K1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((com.android.tools.r8.graph.T0) obj);
                }
            }, CM.b());
            c0729q2A.g(new Consumer() { // from class: com.android.tools.r8.shaking.K1$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((o5) obj);
                }
            }, new Predicate() { // from class: com.android.tools.r8.shaking.K1$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.a((com.android.tools.r8.graph.W0) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.T0 t0) {
        this.c.accept(t0.getReference().h);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(com.android.tools.r8.graph.W0 w0) {
        C0684j2 reference = w0.getReference();
        Consumer consumer = this.c;
        C0737r2 c0737r2 = reference.h;
        consumer.accept(c0737r2.e);
        for (C0767v2 c0767v2 : c0737r2.f.a) {
            consumer.accept(c0767v2);
        }
        return w0.q1();
    }

    public final void a(o5 o5Var) {
        o5Var.a(new J1(this, o5Var));
    }

    public static C0729q2 a(final C0702m c0702m, o5 o5Var, final Predicate predicate) {
        final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
        new K1(new Consumer() { // from class: com.android.tools.r8.shaking.K1$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                K1.a(c0702m, predicate, n, (C0767v2) obj);
            }
        }, c0702m).a(o5Var);
        return (C0729q2) n.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, Predicate predicate, com.android.tools.r8.utils.N n, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C;
        C0767v2 c0767v2B = c0767v2.b(c0702m.a());
        if (c0767v2B.O0() && predicate.test(c0767v2B) && (abstractC0727q0C = c0702m.c(c0767v2B)) != null && abstractC0727q0C.e0()) {
            n.a(abstractC0727q0C.a0());
        }
    }
}
