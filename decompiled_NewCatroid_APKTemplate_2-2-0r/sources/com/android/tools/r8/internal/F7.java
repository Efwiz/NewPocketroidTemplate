package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.utils.C3912j0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class F7 extends G7 {
    static final /* synthetic */ boolean j = true;
    private final C0702m a;
    private final BiConsumer b;
    private final BiConsumer c;
    private final BiConsumer d;
    private final com.android.tools.r8.shaking.O e;
    private final IdentityHashMap f = new IdentityHashMap();
    private final ArrayList g = new ArrayList();
    private final ArrayList h = new ArrayList();
    private final BiConsumer i;

    public F7(C0702m c0702m, BiConsumer biConsumer, BiConsumer biConsumer2, BiConsumer biConsumer3, com.android.tools.r8.shaking.O o, BiConsumer biConsumer4) {
        this.a = c0702m;
        this.b = biConsumer;
        this.c = biConsumer2;
        this.d = biConsumer3;
        this.e = o;
        this.i = biConsumer4;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1124Kc
    public final void a(C0684j2 c0684j2, com.android.tools.r8.graph.o5 o5Var) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3345xG
    public final void a(com.android.tools.r8.graph.m5 m5Var, com.android.tools.r8.graph.o5 o5Var, AbstractC0748t0 abstractC0748t0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3345xG
    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, C0729q2 c0729q2, AbstractC0748t0 abstractC0748t0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3345xG
    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, AbstractC0748t0 abstractC0748t0) {
    }

    @Override // com.android.tools.r8.internal.HQ
    public final void a(C0729q2 c0729q2) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2128h20
    public final void a(C0729q2 c0729q2, InterfaceC0694k5 interfaceC0694k5) {
    }

    @Override // com.android.tools.r8.internal.GQ
    public final void a(C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3345xG
    public final void b(com.android.tools.r8.graph.m5 m5Var, com.android.tools.r8.graph.o5 o5Var, AbstractC0748t0 abstractC0748t0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2128h20
    public final void b(C0729q2 c0729q2) {
    }

    @Override // com.android.tools.r8.internal.W3
    public final void b(C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2749p2
    public final void c(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void d(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.internal.W3
    public final void e(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.internal.AZ
    public final void f(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.d.accept(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.GQ
    public final void g(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1720bs
    public final void h(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.i.accept(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1869ds
    public final void i(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.e.a(o5Var, new F7$$ExternalSyntheticLambda2());
    }

    @Override // com.android.tools.r8.internal.GQ
    public final void j(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1720bs
    public final void k(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.i.accept(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1720bs
    public final void l(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1098Je
    public final void m(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void n(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.internal.GQ
    public final void o(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1370Te
    public final void p(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void q(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void r(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void s(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void t(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.internal.GQ
    public final void u(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1471We
    public final void v(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1398Ue
    public final void c(C0754u0 c0754u0) {
        this.e.a(c0754u0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2632ng
    public final void d(C0754u0 c0754u0) {
        this.e.a(c0754u0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1398Ue
    public final void e(C0754u0 c0754u0) {
        this.e.a(c0754u0);
    }

    private void c() {
        Collections.sort(this.g);
        this.g.forEach(new Consumer() { // from class: com.android.tools.r8.internal.F7$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((C1514Xs) obj);
            }
        });
    }

    private void d() {
        ArrayList arrayList = new ArrayList(this.f.entrySet());
        arrayList.sort(Comparator.comparing(new Function() { // from class: com.android.tools.r8.internal.F7$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return F7.a((Map.Entry) obj);
            }
        }));
        final Set setC = OU.c();
        arrayList.forEach(new Consumer() { // from class: com.android.tools.r8.internal.F7$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(setC, (Map.Entry) obj);
            }
        });
        KA.a(this.a, setC);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1398Ue
    public final void b(C0754u0 c0754u0) {
        this.e.a(c0754u0);
    }

    @Override // com.android.tools.r8.internal.G7
    public final void b() {
        if (!j) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C1514Xs c1514Xs) {
        c1514Xs.b().a(c1514Xs.c(), this.a);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1720bs
    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.i.accept(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1098Je
    public final void a(C0754u0 c0754u0) {
        this.e.a(c0754u0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1567Zs
    public final void a(C1514Xs c1514Xs) {
        synchronized (this.g) {
            this.g.add(c1514Xs);
        }
    }

    @Override // com.android.tools.r8.internal.LA
    public final void a(HA ha, com.android.tools.r8.graph.o5 o5Var) {
        synchronized (this.f) {
            this.f.put(ha, o5Var);
        }
        this.b.accept(ha, o5Var);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1124Kc
    public final void a(C1096Jc c1096Jc, com.android.tools.r8.graph.o5 o5Var) {
        synchronized (this.h) {
            this.h.add(c1096Jc);
        }
        this.c.accept(c1096Jc, o5Var);
    }

    @Override // com.android.tools.r8.internal.G7
    public final List a() {
        c();
        d();
        return Collections.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C0767v2 a(Map.Entry entry) {
        return ((HA) entry.getKey()).c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Set set, Map.Entry entry) {
        HA ha = (HA) entry.getKey();
        com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) entry.getValue();
        GA ga = ha.i;
        ga.getClass();
        ga.b(InterfaceC1011Gi.a(), C3912j0.b());
        if (ha.d.g.contains(this.a.a().u5)) {
            set.add(o5Var.a());
        }
    }
}
