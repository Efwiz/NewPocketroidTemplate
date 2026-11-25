package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.AbstractC3735i1;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3829y;
import com.android.tools.r8.utils.InterfaceC3877c1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Aj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0831Aj {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final com.android.tools.r8.utils.A1 b;
    private final VP c;
    private final IdentityHashMap d = new IdentityHashMap();

    C0831Aj(C0702m c0702m, VP vp) {
        if (!e && !c0702m.Y().x1().a) {
            throw new AssertionError();
        }
        this.a = c0702m;
        this.b = c0702m.Y();
        this.c = vp;
    }

    private void a(final Consumer consumer) {
        final com.android.tools.r8.shaking.L.a aVar = com.android.tools.r8.shaking.L.a.b;
        final Set setC = OU.c();
        this.d.values().forEach(new Consumer() { // from class: com.android.tools.r8.internal.Aj$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0831Aj.a(aVar, setC, (Map) obj);
            }
        });
        setC.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Aj$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(consumer, (C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public void a(com.android.tools.r8.shaking.L.a aVar, com.android.tools.r8.graph.Y0 y0) {
        if (!e && !aVar.c() && !aVar.b()) {
            throw new AssertionError();
        }
        ((Map) this.d.computeIfAbsent(y0.B0(), new Function() { // from class: com.android.tools.r8.internal.Aj$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0831Aj.a((C0767v2) obj);
            }
        })).put(y0, aVar);
    }

    private void c(final Consumer consumer) {
        ((C3733i) this.a.f()).a(this.c.g, new Consumer() { // from class: com.android.tools.r8.internal.Aj$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(consumer, (C0729q2) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Aj$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0831Aj.a((JA) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(Consumer consumer) {
        a(consumer);
        c(consumer);
    }

    public final boolean b(com.android.tools.r8.graph.Y0 y0) {
        return ((Map) this.d.getOrDefault(y0.B0(), Collections.emptyMap())).containsKey(y0);
    }

    private void b(final Consumer consumer) {
        ((C3733i) this.a.f()).v().a(new Consumer() { // from class: com.android.tools.r8.internal.Aj$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(consumer, (com.android.tools.r8.graph.R2) obj);
            }
        });
    }

    public final C3829y a(final com.android.tools.r8.shaking.L.a aVar) {
        b(new Consumer() { // from class: com.android.tools.r8.internal.Aj$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(aVar, (com.android.tools.r8.graph.Y0) obj);
            }
        });
        ((C3733i) this.a.f()).v().a(new BiPredicate() { // from class: com.android.tools.r8.internal.Aj$$ExternalSyntheticLambda5
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return this.f$0.a((com.android.tools.r8.graph.Y0) obj, (com.android.tools.r8.graph.U2) obj2);
            }
        });
        if (aVar.b()) {
            return new C3523zj(this);
        }
        return C3829y.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(C0767v2 c0767v2) {
        return new IdentityHashMap();
    }

    public final void a(com.android.tools.r8.graph.W0 w0, C2409kl c2409kl) {
        if (w0.u1() && this.d.containsKey(w0.J0()) && c2409kl.t().H()) {
            a(c2409kl);
        }
    }

    private void a(C2409kl c2409kl) {
        ArrayList<RW> arrayList = new ArrayList();
        for (RW rw : c2409kl.b((Predicate) new Aj$$ExternalSyntheticLambda6())) {
            if (b(rw.getField())) {
                arrayList.add(rw);
            }
        }
        for (RW rw2 : arrayList) {
            if (rw2.a1()) {
                AbstractC2871ql.a(c2409kl, rw2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.U2 u2) {
        return ((Map) this.d.getOrDefault(y0.B0(), Collections.emptyMap())).containsKey(y0);
    }

    public final void a(final C3012sl c3012sl, ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        y3Var.a("[Proto] Post optimize generated extension registry");
        final EK ekA = EK.a(CN.a(new InterfaceC3877c1() { // from class: com.android.tools.r8.internal.Aj$$ExternalSyntheticLambda10
            @Override // com.android.tools.r8.utils.InterfaceC3877c1
            public final void forEach(Consumer consumer) {
                this.f$0.d(consumer);
            }
        }), VE.a(), this.a);
        ekA.a(new DK() { // from class: com.android.tools.r8.internal.Aj$$ExternalSyntheticLambda11
            @Override // com.android.tools.r8.internal.DK
            public final void a(com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
                C0831Aj.a(c3012sl, ekA, o5Var, c1230Ob);
            }
        }, executorService);
        y3Var.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3012sl c3012sl, EK ek, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
        c3012sl.b(o5Var, com.android.tools.r8.ir.optimize.info.q.b(), ek, c1230Ob);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final com.android.tools.r8.shaking.L.a aVar, final Set set, Map map) {
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Aj$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C0831Aj.a(aVar, set, (com.android.tools.r8.graph.Y0) obj, (com.android.tools.r8.shaking.L.a) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.shaking.L.a aVar, Set set, com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.shaking.L.a aVar2) {
        if (aVar2 == aVar) {
            set.add(y0.B0());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Consumer consumer, C0767v2 c0767v2) {
        C0729q2 c0729q2A = C0729q2.a(((C3733i) this.a.f()).c(c0767v2));
        if (c0729q2A == null || !c0729q2A.p1()) {
            return;
        }
        consumer.accept(c0729q2A.X1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Consumer consumer, C0729q2 c0729q2) {
        c0729q2.g(consumer, new Predicate() { // from class: com.android.tools.r8.internal.Aj$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((com.android.tools.r8.graph.W0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(com.android.tools.r8.graph.W0 w0) {
        return this.c.b(w0.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(JA ja) {
        if (!e) {
            throw new AssertionError();
        }
    }

    public final void a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.shaking.L.a aVar) {
        if (aVar.e()) {
            VP vp = this.c;
            vp.getClass();
            if (vp.b(o5Var.getReference())) {
                a(aVar, y0);
            }
        }
    }

    public final boolean a(com.android.tools.r8.graph.Y0 y0) {
        C3733i c3733i = (C3733i) this.a.f();
        AbstractC0677i3 abstractC0677i3C = c3733i.c(y0);
        com.android.tools.r8.graph.T2 t2V = c3733i.v();
        AbstractC3735i1 abstractC3735i1W = c3733i.w();
        com.android.tools.r8.graph.m5 m5VarU = abstractC0677i3C.u();
        return m5VarU != null && a(m5VarU, t2V, abstractC3735i1W);
    }

    public final boolean a(com.android.tools.r8.graph.m5 m5Var, com.android.tools.r8.graph.T2 t2, AbstractC3735i1 abstractC3735i1) {
        com.android.tools.r8.graph.U2 u2C;
        abstractC3735i1.getClass();
        if (abstractC3735i1.a(m5Var.e(), m5Var.a()).c((com.android.tools.r8.shaking.K0) this.b) || m5Var.getReference().h != this.c.h || (u2C = t2.b(m5Var.getReference())) == null) {
            return false;
        }
        final VP vp = this.c;
        Objects.requireNonNull(vp);
        return u2C.a(new Predicate() { // from class: com.android.tools.r8.internal.Aj$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return vp.a((com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Consumer consumer, com.android.tools.r8.graph.R2 r2) {
        com.android.tools.r8.graph.Y0 y0E = ((com.android.tools.r8.graph.U2) r2).e();
        if (a(y0E)) {
            consumer.accept(y0E);
        }
    }
}
