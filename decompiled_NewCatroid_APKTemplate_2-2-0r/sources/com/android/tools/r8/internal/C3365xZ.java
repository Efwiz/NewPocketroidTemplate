package com.android.tools.r8.internal;

import com.android.tools.r8.AbstractC0403b;
import com.android.tools.r8.dex.C0572k$$ExternalSyntheticLambda8;
import com.android.tools.r8.graph.AbstractC0632d;
import com.android.tools.r8.graph.C0592a;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.shaking.AbstractC3769n2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3365xZ {
    static final /* synthetic */ boolean i = true;
    private final C0702m a;
    private final C2844qM b;
    private final ConcurrentHashMap c = new ConcurrentHashMap();
    private final ConcurrentHashMap d = new ConcurrentHashMap();
    private final ConcurrentHashMap e = new ConcurrentHashMap();
    private final Set f = OU.a();
    private final Set g = OU.a();
    private final CN h = CN.c();

    public C3365xZ(C0702m c0702m, C2844qM c2844qM) {
        this.a = c0702m;
        this.b = c2844qM;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0702m a() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(com.android.tools.r8.graph.T0 t0) {
        a(t0);
        com.android.tools.r8.ir.optimize.info.r rVarA = com.android.tools.r8.ir.optimize.info.n.a();
        C0702m c0702m = this.a;
        rVarA.a(t0, c0702m, c0702m.b().a());
    }

    public final void a(ExecutorService executorService, com.android.tools.r8.ir.optimize.info.o oVar, com.android.tools.r8.utils.y3 y3Var) {
        C3733i c3733i = (C3733i) this.a.f();
        y3Var.a("Trivial field accesses analysis");
        if (!i) {
            oVar.b();
        }
        y3Var.a("Compute fields of interest");
        for (C0729q2 c0729q2 : ((C3733i) this.a.f()).d()) {
            for (com.android.tools.r8.graph.T0 t0 : c0729q2.w1()) {
                int iA = a(this.a, t0);
                int iB = AbstractC0403b.b(iA);
                if (iB == 0) {
                    this.f.add(t0);
                } else if (iB == 1) {
                    this.g.add(t0);
                } else if (!i && iA != 3) {
                    throw new AssertionError();
                }
            }
            if (this.a.k() || !c0729q2.b(this.a)) {
                for (com.android.tools.r8.graph.T0 t1 : c0729q2.N1()) {
                    int iA2 = a(this.a, t1);
                    if (iA2 == 1) {
                        this.f.add(t1);
                    } else if (!i && iA2 != 2 && iA2 != 3) {
                        throw new AssertionError();
                    }
                }
            }
        }
        if (!i) {
            Iterator it = ((C3733i) this.a.f()).g().c().iterator();
            while (it.hasNext()) {
                for (com.android.tools.r8.graph.T0 t2 : ((C0729q2) it.next()).W0()) {
                    if (!i && !t2.X0().f().isUnknown()) {
                        throw new AssertionError();
                    }
                }
            }
        }
        y3Var.c();
        y3Var.a("Enqueue methods for reprocessing");
        a(c3733i, executorService);
        y3Var.c();
        y3Var.a("Clear reads and writes from fields of interest");
        com.android.tools.r8.graph.T2 t2V = c3733i.v();
        Iterator it2 = this.f.iterator();
        while (it2.hasNext()) {
            com.android.tools.r8.graph.U2 u2B = t2V.b(((com.android.tools.r8.graph.T0) it2.next()).getReference());
            u2B.getClass();
            u2B.a();
        }
        Iterator it3 = this.d.keySet().iterator();
        while (it3.hasNext()) {
            com.android.tools.r8.graph.U2 u2B2 = t2V.b(((com.android.tools.r8.graph.T0) it3.next()).getReference());
            u2B2.getClass();
            u2B2.b();
        }
        Iterator it4 = this.e.keySet().iterator();
        while (it4.hasNext()) {
            com.android.tools.r8.graph.U2 u2B3 = t2V.b(((com.android.tools.r8.graph.T0) it4.next()).getReference());
            u2B3.getClass();
            u2B3.a();
        }
        y3Var.c();
        y3Var.c();
        this.f.forEach(new Consumer() { // from class: com.android.tools.r8.internal.xZ$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.T0) obj);
            }
        });
        this.d.keySet().forEach(new Consumer() { // from class: com.android.tools.r8.internal.xZ$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.T0) obj);
            }
        });
        this.e.keySet().forEach(new Consumer() { // from class: com.android.tools.r8.internal.xZ$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((com.android.tools.r8.graph.T0) obj);
            }
        });
        com.android.tools.r8.utils.A1.p pVar = this.a.Y().n1;
        C2844qM c2844qM = this.b;
        Objects.requireNonNull(c2844qM);
        pVar.a(new Fg$$ExternalSyntheticLambda6(c2844qM));
    }

    private void b(C3733i c3733i) {
        final com.android.tools.r8.graph.T2 t2V = c3733i.v();
        this.d.entrySet().removeIf(new Predicate() { // from class: com.android.tools.r8.internal.xZ$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.b(t2V, (Map.Entry) obj);
            }
        });
        this.d.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.xZ$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((com.android.tools.r8.graph.T0) obj, (AbstractC0632d) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(com.android.tools.r8.graph.S2 s2, Map.Entry entry) {
        AbstractC0632d abstractC0632d = (AbstractC0632d) entry.getValue();
        abstractC0632d.getClass();
        return ((abstractC0632d instanceof C0592a) && a((com.android.tools.r8.graph.T0) entry.getKey(), false, s2)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.T0 t0) {
        if (((C3733i) this.a.f()).b((com.android.tools.r8.graph.O0) t0)) {
            if (!i && !t0.getType().b(this.a)) {
                throw new AssertionError();
            }
        } else {
            com.android.tools.r8.ir.optimize.info.n.a().getClass();
            com.android.tools.r8.ir.optimize.info.r.a(t0);
        }
    }

    private void a(C3733i c3733i, ExecutorService executorService) {
        C3879c3.a(c3733i.d(), new Consumer() { // from class: com.android.tools.r8.internal.xZ$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        }, executorService);
        C3879c3.a(c3733i.g().c(), new Consumer() { // from class: com.android.tools.r8.internal.xZ$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        }, executorService);
        a(c3733i);
        b(c3733i);
        this.b.a(this.a.E()).a(this.h);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        c0729q2.g(new Consumer() { // from class: com.android.tools.r8.internal.xZ$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        }, new C0572k$$ExternalSyntheticLambda8());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.o5 o5Var) {
        o5Var.a(new C3296wZ(this, o5Var));
    }

    private static int a(C0702m c0702m, com.android.tools.r8.graph.T0 t0) {
        com.android.tools.r8.graph.U2 u2B = ((C3733i) c0702m.f()).v().b(t0.getReference());
        if (u2B != null && !u2B.k() && !u2B.p() && !u2B.s() && !u2B.o()) {
            B1 b1F = t0.X0().f();
            if (b1F.g()) {
                AbstractC3292wV abstractC3292wVJ = b1F.j();
                if (!abstractC3292wVJ.a(c0702m)) {
                    return 3;
                }
                if (abstractC3292wVJ.A()) {
                    return 1;
                }
                if (abstractC3292wVJ.B()) {
                    AbstractC2706oV abstractC2706oVP = abstractC3292wVJ.p();
                    if (abstractC2706oVP.F() != t0.getReference() && !abstractC2706oVP.b(c0702m)) {
                        return 1;
                    }
                }
                return 3;
            }
            if (b1F.c()) {
                return 2;
            }
        }
        return 3;
    }

    private void a(C3733i c3733i) {
        final com.android.tools.r8.graph.T2 t2V = c3733i.v();
        this.e.entrySet().removeIf(new Predicate() { // from class: com.android.tools.r8.internal.xZ$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(t2V, (Map.Entry) obj);
            }
        });
        this.e.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.xZ$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(t2V, (com.android.tools.r8.graph.T0) obj, (AbstractC0632d) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(com.android.tools.r8.graph.S2 s2, Map.Entry entry) {
        AbstractC0632d abstractC0632d = (AbstractC0632d) entry.getValue();
        abstractC0632d.getClass();
        return ((abstractC0632d instanceof C0592a) && a((com.android.tools.r8.graph.T0) entry.getKey(), true, s2)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.S2 s2, com.android.tools.r8.graph.T0 t0, AbstractC0632d abstractC0632d) {
        if (!i && this.d.containsKey(t0)) {
            throw new AssertionError();
        }
        com.android.tools.r8.graph.U2 u2B = ((com.android.tools.r8.graph.T2) s2).b(t0.getReference());
        u2B.getClass();
        u2B.a();
        this.h.a((CN) abstractC0632d.a().d().values().iterator().next());
        this.h.a((CN) this.c.getOrDefault(t0, CN.e()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.T0 t0, AbstractC0632d abstractC0632d) {
        if (!i && this.e.containsKey(t0)) {
            throw new AssertionError();
        }
        this.h.a((CN) abstractC0632d.a().d().values().iterator().next());
        this.h.a((CN) this.c.getOrDefault(t0, CN.e()));
    }

    private boolean a(com.android.tools.r8.graph.T0 t0, boolean z, com.android.tools.r8.graph.S2 s2) {
        boolean z2 = i;
        if (!z2 && ((C3733i) this.a.f()).b((com.android.tools.r8.graph.O0) t0) && !t0.getType().b(this.a)) {
            throw new AssertionError();
        }
        com.android.tools.r8.graph.U2 u2B = ((com.android.tools.r8.graph.T2) s2).b(t0.getReference());
        if (u2B == null) {
            if (z2) {
                return false;
            }
            throw new AssertionError("Expected program field with concrete accesses to be present in field access collection");
        }
        if (u2B.k() || u2B.p() || u2B.s() || u2B.q() || u2B.o()) {
            return false;
        }
        if (!z || !t0.getType().X0()) {
            return true;
        }
        AbstractC3472z00 abstractC3472z00D = AbstractC3472z00.a(t0.getReference().getType(), WH.i(), (C0702m<?>) this.a).d();
        if (abstractC3472z00D.r()) {
            abstractC3472z00D = abstractC3472z00D.a().R();
        }
        C1229Oa c1229OaB = abstractC3472z00D.b();
        if (c1229OaB == null) {
            return true;
        }
        C0702m c0702m = this.a;
        return !AbstractC3769n2.a(c0702m, c1229OaB, ((C3733i) c0702m.f()).z());
    }
}
