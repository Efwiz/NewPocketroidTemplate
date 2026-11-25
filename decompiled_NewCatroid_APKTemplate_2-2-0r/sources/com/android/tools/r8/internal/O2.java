package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.InterfaceC3910i3;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class O2 {
    private final C0702m a;
    private final com.android.tools.r8.graph.H3 b;
    private final Map c;
    private final ConcurrentHashMap d = new ConcurrentHashMap();

    public O2(C0702m c0702m, com.android.tools.r8.graph.H3 h3, IdentityHashMap identityHashMap) {
        this.a = c0702m;
        this.b = h3;
        this.c = identityHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0) {
        return abstractC0727q0 != null;
    }

    public final H2 a(ArrayList arrayList, final Consumer consumer, ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        y3Var.a("Optimize components");
        final R2 r2A = R2.a(this.a);
        final C1257Pb c1257PbP = this.a.p();
        ArrayList arrayListA = C3879c3.a(arrayList, new InterfaceC3910i3() { // from class: com.android.tools.r8.internal.O2$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3910i3
            public final Object apply(Object obj) {
                return this.f$0.a(r2A, c1257PbP, consumer, (Set) obj);
            }
        }, executorService);
        r2A.b(this.a);
        y3Var.c();
        y3Var.a("Build graph lens");
        C0702m c0702m = this.a;
        boolean z = H2.o;
        final G2 g2 = new G2(c0702m);
        arrayListA.forEach(new Consumer() { // from class: com.android.tools.r8.internal.O2$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                g2.a((G2) obj);
            }
        });
        H2 h2A = g2.a();
        y3Var.c();
        return h2A;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ G2 a(R2 r2, C1257Pb c1257Pb, Consumer consumer, Set set) {
        return new N2(this, r2, c1257Pb).a(set, (C2720of) this.c.getOrDefault(set, C2720of.d()), consumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2720of c2720of, C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0) {
        C2720of c2720ofA = (C2720of) this.d.get(abstractC0727q0);
        if (c2720ofA == null) {
            c2720ofA = a(abstractC0727q0);
        }
        c2720of.a(c2720ofA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C2720of a(AbstractC0727q0 abstractC0727q0) {
        final C2720of c2720ofA = C2720of.a();
        this.b.a(abstractC0727q0, new BiPredicate() { // from class: com.android.tools.r8.internal.O2$$ExternalSyntheticLambda2
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return O2.a((C0767v2) obj, (AbstractC0727q0) obj2);
            }
        }, new BiConsumer() { // from class: com.android.tools.r8.internal.O2$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c2720ofA, (C0767v2) obj, (AbstractC0727q0) obj2);
            }
        });
        abstractC0727q0.b(new Consumer() { // from class: com.android.tools.r8.internal.O2$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                O2.a(c2720ofA, (AbstractC0748t0) obj);
            }
        }, new O2$$ExternalSyntheticLambda4());
        this.d.put(abstractC0727q0, c2720ofA);
        return c2720ofA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C2720of c2720of, AbstractC0748t0 abstractC0748t0) {
        c2720of.a(abstractC0748t0.z());
    }
}
