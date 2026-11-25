package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.InterfaceC0694k5;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class E7 extends G7 {
    static final /* synthetic */ boolean h = true;
    private final C0702m a;
    private final N9 b;
    private final C0825Ad c;
    private final LinkedHashMap d;
    private final ArrayList e;
    private final ArrayList f;
    private G7 g;

    private E7(C0702m c0702m, N9 n9, C0825Ad c0825Ad) {
        this.d = new LinkedHashMap();
        this.e = new ArrayList();
        this.f = new ArrayList();
        this.g = this;
        this.a = c0702m;
        this.b = n9;
        this.c = c0825Ad;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1124Kc
    public final void a(C0684j2 c0684j2, com.android.tools.r8.graph.o5 o5Var) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3345xG
    public final void a(com.android.tools.r8.graph.m5 m5Var, com.android.tools.r8.graph.o5 o5Var, AbstractC0748t0 abstractC0748t0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1720bs
    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3345xG
    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, C0729q2 c0729q2, AbstractC0748t0 abstractC0748t0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3345xG
    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, AbstractC0748t0 abstractC0748t0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2128h20
    public final void a(C0729q2 c0729q2, InterfaceC0694k5 interfaceC0694k5) {
    }

    @Override // com.android.tools.r8.internal.GQ
    public final void a(C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1098Je
    public final void a(C0754u0 c0754u0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3345xG
    public final void b(com.android.tools.r8.graph.m5 m5Var, com.android.tools.r8.graph.o5 o5Var, AbstractC0748t0 abstractC0748t0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1398Ue
    public final void b(C0754u0 c0754u0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1398Ue
    public final void c(C0754u0 c0754u0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2632ng
    public final void d(C0754u0 c0754u0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1398Ue
    public final void e(C0754u0 c0754u0) {
    }

    @Override // com.android.tools.r8.internal.AZ
    public final void f(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.c.c(o5Var, this.g);
    }

    @Override // com.android.tools.r8.internal.GQ
    public final void g(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.c.c(o5Var);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1720bs
    public final void h(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1869ds
    public final void i(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.c.c(o5Var);
    }

    @Override // com.android.tools.r8.internal.GQ
    public final void j(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.c.c(o5Var);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1720bs
    public final void k(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1720bs
    public final void l(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.c.c(o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1098Je
    public final void m(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.c.c(o5Var, this.g);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void n(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.c.c(o5Var);
    }

    @Override // com.android.tools.r8.internal.GQ
    public final void o(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1370Te
    public final void p(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.c.c(o5Var);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void q(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.c.c(o5Var);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void r(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.c.c(o5Var);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void s(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.c.c(o5Var);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void t(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.c.c(o5Var);
    }

    @Override // com.android.tools.r8.internal.GQ
    public final void u(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1471We
    public final void v(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.c.c(o5Var, this.g);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2749p2
    public final void c(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.c.c(o5Var);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void d(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.c.c(o5Var);
    }

    @Override // com.android.tools.r8.internal.W3
    public final void e(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.c.c(o5Var, this.g);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(com.android.tools.r8.graph.o5 o5Var) {
        this.c.c(o5Var, this.g);
    }

    @Override // com.android.tools.r8.internal.W3
    public final void b(C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var) {
        AbstractC0984Fi.a(c0729q2.R1(), c0729q2.k2()).forEach(new Consumer() { // from class: com.android.tools.r8.internal.E7$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.InterfaceC2128h20
    public final void b(C0729q2 c0729q2) {
        AbstractC0984Fi.a(c0729q2.R1(), c0729q2.k2()).forEach(new Consumer() { // from class: com.android.tools.r8.internal.E7$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.o5 o5Var) {
        this.c.c(o5Var, this.g);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1567Zs
    public final void a(C1514Xs c1514Xs) {
        synchronized (this.d) {
            if (!h && this.d.containsKey(c1514Xs.a().getReference())) {
                throw new AssertionError();
            }
            this.d.put(c1514Xs.a().getReference(), c1514Xs);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C1514Xs c1514Xs) {
        c1514Xs.a().a(c1514Xs.b().e().a1(), this.a);
    }

    @Override // com.android.tools.r8.internal.HQ
    public final void a(C0729q2 c0729q2) {
        this.c.a(AbstractC0984Fi.a(c0729q2.R1(), c0729q2.k2()));
    }

    @Override // com.android.tools.r8.internal.G7
    public final void b() {
        boolean z = h;
        if (!z && !this.d.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.e.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.f.isEmpty()) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.LA
    public final void a(HA ha, com.android.tools.r8.graph.o5 o5Var) {
        synchronized (this.e) {
            this.e.add(ha);
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC1124Kc
    public final void a(C1096Jc c1096Jc, com.android.tools.r8.graph.o5 o5Var) {
        synchronized (this.f) {
            this.f.add(c1096Jc);
        }
    }

    @Override // com.android.tools.r8.internal.G7
    public final List a() {
        ArrayList arrayList = new ArrayList();
        a(new E7$$ExternalSyntheticLambda0(arrayList));
        a(this.b, new E7$$ExternalSyntheticLambda0(arrayList));
        E7$$ExternalSyntheticLambda0 e7$$ExternalSyntheticLambda0 = new E7$$ExternalSyntheticLambda0(arrayList);
        for (C1096Jc c1096Jc : this.f) {
            c1096Jc.a(this.g);
            c1096Jc.a().l(e7$$ExternalSyntheticLambda0);
        }
        this.f.clear();
        return arrayList;
    }

    private void a(final Consumer consumer) {
        this.d.values().forEach(new Consumer() { // from class: com.android.tools.r8.internal.E7$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((C1514Xs) obj);
            }
        });
        this.d.values().forEach(new Consumer() { // from class: com.android.tools.r8.internal.E7$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(consumer, (C1514Xs) obj);
            }
        });
        this.d.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Consumer consumer, C1514Xs c1514Xs) {
        c1514Xs.b().a(c1514Xs.c(), this.a);
        consumer.accept(c1514Xs.b());
    }

    private void a(N9 n9, Consumer consumer) {
        this.e.sort(Comparator.comparing(new Function() { // from class: com.android.tools.r8.internal.E7$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((HA) obj).e();
            }
        }));
        for (HA ha : this.e) {
            ha.i.b(n9, consumer);
            ha.d().l((Consumer<? super com.android.tools.r8.graph.o5>) consumer);
        }
        this.e.clear();
    }
}
