package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.as, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1645as extends BE {
    static final /* synthetic */ boolean i = true;
    final IdentityHashMap g;
    final Consumer h;

    public C1645as(C0702m c0702m, com.android.tools.r8.graph.H3 h3, C1678bF c1678bF, Consumer consumer) {
        super(c0702m, h3, c1678bF);
        this.g = new IdentityHashMap();
        this.h = consumer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C1751cF c1751cF, final C0729q2 c0729q2) {
        c1751cF.a(new BiConsumer() { // from class: com.android.tools.r8.internal.as$$ExternalSyntheticLambda8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c0729q2, (AbstractC0717o2) obj, (YE) obj2);
            }
        });
    }

    private C1751cF l(C0729q2 c0729q2) {
        final C1751cF c1751cFB = C1751cF.b();
        com.android.tools.r8.graph.H3 h3 = this.b;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.as$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c1751cFB, (C0729q2) obj);
            }
        };
        h3.getClass();
        h3.a(CM.b(), consumer, c0729q2);
        c0729q2.h(new Consumer() { // from class: com.android.tools.r8.internal.as$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c1751cFB, (com.android.tools.r8.graph.o5) obj);
            }
        }, CM.b());
        this.g.put(c0729q2, c1751cFB);
        return c1751cFB;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean m(C0729q2 c0729q2) {
        return !c0729q2.isInterface();
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final void a(Collection collection) {
        super.a(collection);
        if (i) {
            return;
        }
        b(collection);
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final boolean c(C0729q2 c0729q2) {
        return c0729q2.isInterface() && super.c(c0729q2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final void i(C0729q2 c0729q2) {
        this.g.remove(c0729q2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final void k(C0729q2 c0729q2) {
        if (!i && this.g.containsKey(c0729q2)) {
            throw new AssertionError();
        }
        c(l(c0729q2), c0729q2);
    }

    private void b(Collection collection) {
        if (!i && !collection.stream().filter(new Predicate() { // from class: com.android.tools.r8.internal.as$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((C0729q2) obj).isInterface();
            }
        }).allMatch(new Predicate() { // from class: com.android.tools.r8.internal.as$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((C0729q2) obj);
            }
        })) {
            throw new AssertionError();
        }
    }

    private void c(final C1751cF c1751cF, C0729q2 c0729q2) {
        this.b.b(new Predicate() { // from class: com.android.tools.r8.internal.as$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1645as.m((C0729q2) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.as$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(c1751cF, (C0729q2) obj);
            }
        }, c0729q2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final void a(Consumer consumer, C0729q2 c0729q2) {
        for (C0729q2 c0729q3 : this.b.a(c0729q2)) {
            if (c0729q3.isInterface()) {
                consumer.accept(c0729q3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C1751cF c1751cF, C0729q2 c0729q2) {
        C1751cF c1751cF2 = (C1751cF) this.g.get(c0729q2);
        if (!i && c1751cF2 == null) {
            throw new AssertionError();
        }
        c1751cF.a(this.a, c1751cF2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C1751cF c1751cF, com.android.tools.r8.graph.o5 o5Var) {
        YE yeB = this.f.b(o5Var);
        if (yeB.d()) {
            return;
        }
        if (!i && !yeB.isUnknown()) {
            AbstractC1777cc abstractC1777ccE = yeB.e();
            abstractC1777ccE.getClass();
            if (!(abstractC1777ccE instanceof C2247ic)) {
                throw new AssertionError();
            }
        }
        c1751cF.a(this.a, o5Var, yeB);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2, AbstractC0717o2 abstractC0717o2, YE ye) {
        com.android.tools.r8.graph.I4 i4A = ((C3733i) this.a.f()).a(c0729q2, abstractC0717o2);
        if (i4A.h()) {
            if (!i && !i4A.k().z()) {
                throw new AssertionError();
            }
            return;
        }
        if (!i && !i4A.x()) {
            throw new AssertionError();
        }
        AbstractC0748t0 abstractC0748t0C = i4A.c();
        abstractC0748t0C.getClass();
        if (!(abstractC0748t0C instanceof com.android.tools.r8.graph.o5)) {
            this.h.accept(abstractC0717o2);
            return;
        }
        com.android.tools.r8.graph.o5 o5VarS = i4A.s();
        if (o5VarS == null || o5VarS.a() == c0729q2) {
            return;
        }
        YE yeA = a(c0729q2, o5VarS, ye);
        if (yeA.d()) {
            return;
        }
        this.f.a(this.a, o5VarS, yeA);
    }

    private YE a(final C0729q2 c0729q2, final com.android.tools.r8.graph.o5 o5Var, YE ye) {
        if (!ye.c()) {
            return ye.a();
        }
        ZE zeB = ye.j().b(this.a, o5Var.z(), FW.a(), new Function() { // from class: com.android.tools.r8.internal.as$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0729q2, o5Var, (C1207Nf) obj);
            }
        });
        C1678bF c1678bF = this.f;
        c1678bF.getClass();
        if (!c1678bF.a(o5Var.getReference()).h() || !(zeB instanceof C2247ic)) {
            return zeB;
        }
        C2247ic c2247icJ = zeB.j();
        if (i || c2247icJ.m().size() == 1) {
            return (YE) c2247icJ.m().iterator().next();
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C1207Nf a(final C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var, C1207Nf c1207Nf) {
        if (c1207Nf.m() ? true : c1207Nf.o().b().S().a(new BiPredicate() { // from class: com.android.tools.r8.internal.as$$ExternalSyntheticLambda5
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return this.f$0.a(c0729q2, (C0767v2) obj, (Boolean) obj2);
            }
        })) {
            return AbstractC1154Lf.a(AbstractC3472z00.a(o5Var.t(), WH.i(), (C0702m<?>) this.a).b());
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(C0729q2 c0729q2, C0767v2 c0767v2, Boolean bool) {
        return ((C3733i) this.a.f()).c(c0729q2.getType(), c0767v2);
    }
}
