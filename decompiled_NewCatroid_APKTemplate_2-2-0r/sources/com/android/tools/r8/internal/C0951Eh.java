package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.q2$$ExternalSyntheticLambda9;
import com.android.tools.r8.ir.optimize.C3585t;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3738j;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Eh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0951Eh {
    static final /* synthetic */ boolean h = true;
    private final C1 a;
    private final C0702m b;
    private final C0722p1 c;
    private final ConcurrentHashMap f = new ConcurrentHashMap();
    private final ConcurrentHashMap g = new ConcurrentHashMap();
    private final C0894Ch d = new C0894Ch();
    private final C0923Dh e = new C0923Dh();

    C0951Eh(C0702m c0702m) {
        this.a = c0702m.b();
        this.b = c0702m;
        this.c = c0702m.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC1860di a(com.android.tools.r8.graph.m5 m5Var, B1 b1, S10 s10, com.android.tools.r8.graph.T0 t0, AbstractC1860di abstractC1860di) {
        if (abstractC1860di == null || (abstractC1860di instanceof C3411y5)) {
            C0767v2 type = m5Var.getType();
            if (type.M0()) {
                return C1468Wb.a(b1);
            }
            if (type.W0()) {
                return C2474lc.a(b1);
            }
            if (!h && !type.O0()) {
                throw new AssertionError();
            }
            C0702m c0702m = this.b;
            s10.getClass();
            return C1550Zb.a(b1, F20.a(c0702m, AbstractC1154Lf.a(c0702m, s10).a(WH.i()), m5Var.getType()));
        }
        if (abstractC1860di instanceof C2276j10) {
            return abstractC1860di;
        }
        boolean z = h;
        if (!z && !abstractC1860di.d()) {
            throw new AssertionError();
        }
        if (abstractC1860di instanceof C1468Wb) {
            return abstractC1860di.a().a(this.b, b1);
        }
        if (abstractC1860di instanceof C2474lc) {
            return abstractC1860di.c().a(b1, this.a);
        }
        if (!z && !(abstractC1860di instanceof C1550Zb)) {
            throw new AssertionError();
        }
        C1550Zb c1550ZbB = abstractC1860di.b();
        C0702m c0702m2 = this.b;
        s10.getClass();
        return c1550ZbB.a(c0702m2, b1, AbstractC1154Lf.a(c0702m2, s10), m5Var);
    }

    private void b() {
        final com.android.tools.r8.graph.T2 t2V = ((C3733i) this.b.f()).v();
        ((C3733i) this.b.f()).z().a(new BiConsumer() { // from class: com.android.tools.r8.internal.Eh$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(t2V, (C0729q2) obj, (Set) obj2);
            }
        });
        for (C0729q2 c0729q2 : ((C3733i) this.b.f()).d()) {
            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Eh$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(t2V, (com.android.tools.r8.graph.m5) obj);
                }
            };
            c0729q2.getClass();
            c0729q2.e(consumer, CM.b());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public void a(com.android.tools.r8.ir.optimize.info.o oVar, final C0729q2 c0729q2) {
        Map map = (Map) this.g.get(c0729q2);
        if (map == null) {
            return;
        }
        for (com.android.tools.r8.graph.T0 t0 : c0729q2.w1()) {
            B1 b1 = (B1) map.getOrDefault(t0, C2892r10.F());
            b1.getClass();
            if (b1 instanceof H5) {
                oVar.a(new Consumer() { // from class: com.android.tools.r8.internal.Eh$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((C3738j) obj).a(c0729q2);
                    }
                });
                return;
            } else if (!b1.isUnknown()) {
                oVar.a(t0, this.b, b1);
            }
        }
    }

    public final void a() {
        this.d.a(this.b);
        this.e.a(this.b);
        b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.S2 s2, C0729q2 c0729q2, Set set) {
        if (((C3733i) this.b.f()).c(c0729q2) || c0729q2.w1().isEmpty()) {
            return;
        }
        IdentityHashMap identityHashMap = new IdentityHashMap();
        for (com.android.tools.r8.graph.T0 t0 : c0729q2.w1()) {
            com.android.tools.r8.graph.U2 u2B = ((com.android.tools.r8.graph.T2) s2).b(t0.getReference());
            if (u2B != null && !u2B.k()) {
                identityHashMap.put(t0, H5.F());
            }
        }
        this.g.put(c0729q2, identityHashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.S2 s2, com.android.tools.r8.graph.m5 m5Var) {
        com.android.tools.r8.graph.U2 u2B = ((com.android.tools.r8.graph.T2) s2).b(m5Var.getReference());
        if (this.b.a(m5Var).c((com.android.tools.r8.shaking.K0) this.b.Y()) || (u2B != null && u2B.s())) {
            this.f.put(m5Var.e(), C2276j10.e());
        }
    }

    final void a(C3585t c3585t) {
        c3585t.a(new BiConsumer() { // from class: com.android.tools.r8.internal.Eh$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((com.android.tools.r8.graph.T0) obj, (com.android.tools.r8.graph.B2) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.T0 t0, final com.android.tools.r8.graph.B2 b2) {
        final C0767v2 type = t0.getType();
        C0767v2 type2 = t0.getType();
        b2.getClass();
        if (b2 == com.android.tools.r8.graph.B2.a(type2)) {
            return;
        }
        if (!h && !type.O0() && !type.W0()) {
            throw new AssertionError();
        }
        this.f.compute(t0, new BiFunction() { // from class: com.android.tools.r8.internal.Eh$$ExternalSyntheticLambda6
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a(b2, type, (com.android.tools.r8.graph.T0) obj, (AbstractC1860di) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC1860di a(com.android.tools.r8.graph.B2 b2, C0767v2 c0767v2, com.android.tools.r8.graph.T0 t0, AbstractC1860di abstractC1860di) {
        if (abstractC1860di == null) {
            B1 b1A = b2.a(this.a);
            if (c0767v2.O0()) {
                if (!h && !b1A.C() && !(b1A instanceof C2616nV)) {
                    throw new AssertionError();
                }
                C0767v2 c0767v3 = this.c.X1;
                if (c0767v2 == c0767v3) {
                    int i = AbstractC1154Lf.b;
                    return C1550Zb.a(b1A, OH.o());
                }
                C0702m c0702m = this.b;
                WH whB = WH.b();
                c0767v3.getClass();
                return C1550Zb.a(b1A, AbstractC1154Lf.a(AbstractC3472z00.a(c0767v3, whB, (C0702m<?>) c0702m).b()));
            }
            if (h || c0767v2.W0()) {
                return C2474lc.a(b1A);
            }
            throw new AssertionError();
        }
        return C2276j10.e();
    }

    final void a(AbstractC1182Mh abstractC1182Mh, com.android.tools.r8.graph.m5 m5Var) {
        if (abstractC1182Mh.L1()) {
            a(m5Var, abstractC1182Mh.value());
        }
    }

    private void a(final com.android.tools.r8.graph.m5 m5Var, final S10 s10) {
        final B1 b1F;
        if (s10.V()) {
            b1F = this.a.a(0L);
        } else {
            int i = B1.b;
            b1F = C2892r10.F();
        }
        this.f.compute(m5Var.e(), new BiFunction() { // from class: com.android.tools.r8.internal.Eh$$ExternalSyntheticLambda5
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a(m5Var, b1F, s10, (com.android.tools.r8.graph.T0) obj, (AbstractC1860di) obj2);
            }
        });
    }

    final void a(DG dg, C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var) {
        B1 b1F;
        AbstractC2980sH abstractC2980sHB;
        Map map = (Map) this.g.get(c0729q2);
        if (map == null) {
            return;
        }
        C1166Ls c1166LsA = AbstractC2871ql.a(this.c, dg.b());
        if (c1166LsA == null) {
            this.g.remove(c0729q2);
            return;
        }
        AbstractC0748t0 abstractC0748t0H = c1166LsA.h(this.b, o5Var);
        if (abstractC0748t0H == null) {
            this.g.remove(c0729q2);
            return;
        }
        AbstractC1049Hn abstractC1049HnB = abstractC0748t0H.e().i1().a(c1166LsA).b();
        synchronized (map) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                com.android.tools.r8.graph.T0 t0 = (com.android.tools.r8.graph.T0) entry.getKey();
                B1 b1 = (B1) entry.getValue();
                boolean zA = AbstractC1046Hk.a(this.b, t0);
                InterfaceC0989Fn interfaceC0989FnA = abstractC1049HnB.a(t0);
                if (interfaceC0989FnA.h()) {
                    b1F = b1.a(((S10) ((ArrayList) c1166LsA.J2()).get(interfaceC0989FnA.f().b())).a(this.b, o5Var), this.b.b(), t0.getType().X0(), zA);
                    if (!h) {
                        b1F.getClass();
                        if (b1F instanceof H5) {
                            throw new AssertionError();
                        }
                    }
                } else if (interfaceC0989FnA.g()) {
                    b1F = b1.a(interfaceC0989FnA.j(), this.b.b(), t0.getType().X0(), zA);
                } else {
                    if (!interfaceC0989FnA.a() && !h && !interfaceC0989FnA.isUnknown()) {
                        throw new AssertionError();
                    }
                    b1F = C2892r10.F();
                }
                if (!h) {
                    b1F.getClass();
                    if (b1F instanceof H5) {
                        throw new AssertionError();
                    }
                }
                if (zA && b1F.c() && (abstractC2980sHB = t0.X0().f().b()) != null && b1F.b().F() >= abstractC2980sHB.F()) {
                    b1F = C2892r10.F();
                }
                if (!b1F.isUnknown()) {
                    entry.setValue(b1F);
                } else {
                    it.remove();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.ir.optimize.info.o oVar, com.android.tools.r8.graph.m5 m5Var) {
        AbstractC1860di abstractC1860di = (AbstractC1860di) this.f.getOrDefault(m5Var.e(), C3411y5.e());
        B1 b1A = abstractC1860di.a(this.b.b());
        if (b1A.y()) {
            oVar.a(m5Var.e(), this.b, b1A);
        }
        if ((abstractC1860di instanceof C1550Zb) && m5Var.e().X0().g().m()) {
            AbstractC1154Lf abstractC1154LfE = abstractC1860di.b().e();
            if (!abstractC1154LfE.m()) {
                if (!h && F20.a(this.b, abstractC1154LfE, m5Var.getType()) != abstractC1154LfE) {
                    throw new AssertionError();
                }
                if (abstractC1154LfE.k()) {
                    oVar.a(m5Var.e(), abstractC1154LfE);
                } else {
                    C0767v2 type = m5Var.getType();
                    C0702m c0702m = this.b;
                    type.getClass();
                    if (abstractC1154LfE.a().b(this.b, AbstractC1154Lf.a(c0702m, AbstractC3472z00.a(type, WH.i(), (C0702m<?>) c0702m)))) {
                        oVar.a(m5Var.e(), abstractC1154LfE);
                    }
                }
            }
        }
        if (m5Var.getAccessFlags().o()) {
            return;
        }
        a(m5Var, oVar);
    }

    private void a(com.android.tools.r8.graph.m5 m5Var, com.android.tools.r8.ir.optimize.info.o oVar) {
        C3733i c3733i = (C3733i) this.b.f();
        c3733i.getClass();
        if (c3733i.d(m5Var.e())) {
            B1 b1F = H5.F();
            Iterator it = m5Var.a().b(new q2$$ExternalSyntheticLambda9()).iterator();
            while (it.hasNext()) {
                AbstractC1049Hn abstractC1049HnB = ((com.android.tools.r8.graph.W0) it.next()).i1().l().b();
                abstractC1049HnB.getClass();
                InterfaceC0989Fn interfaceC0989FnA = abstractC1049HnB.a(m5Var.e());
                if (interfaceC0989FnA.g()) {
                    b1F = b1F.a(interfaceC0989FnA.j(), this.b.b(), m5Var.getType().X0(), false);
                    if (b1F.isUnknown()) {
                        break;
                    }
                } else {
                    if (!interfaceC0989FnA.a() && !h && !interfaceC0989FnA.h() && !interfaceC0989FnA.isUnknown()) {
                        throw new AssertionError();
                    }
                    b1F = C2892r10.F();
                    break;
                }
            }
            if (!h && (b1F instanceof H5)) {
                throw new AssertionError();
            }
            if (b1F.isUnknown()) {
                return;
            }
            oVar.a(m5Var.e(), this.b, b1F);
        }
    }

    public final void a(CN cn, final com.android.tools.r8.ir.optimize.info.o oVar) {
        if (!h) {
            oVar.b();
        }
        Iterator it = cn.iterator();
        while (it.hasNext()) {
            final com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) it.next();
            this.d.a(o5Var, new Consumer() { // from class: com.android.tools.r8.internal.Eh$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(o5Var, oVar, (com.android.tools.r8.graph.m5) obj);
                }
            });
            this.e.a(o5Var, new Consumer() { // from class: com.android.tools.r8.internal.Eh$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(oVar, (C0729q2) obj);
                }
            });
        }
        oVar.a(((C3733i) this.b.f()).o());
        oVar.c();
    }
}
