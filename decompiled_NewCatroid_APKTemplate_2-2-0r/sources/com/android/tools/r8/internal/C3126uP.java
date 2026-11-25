package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0692k3;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.shaking.AbstractC3837z1;
import com.android.tools.r8.shaking.C3762m1;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3126uP extends AbstractC2797pg {
    static final /* synthetic */ boolean k = true;
    private final C0702m a;
    private final C2076gQ b;
    private final AP c;
    private final VP d;
    private final IdentityHashMap e = new IdentityHashMap();
    private final IdentityHashMap f = new IdentityHashMap();
    private final IdentityHashMap g = new IdentityHashMap();
    private final Set h = OU.c();
    private final CN i = CN.b();
    private final IdentityHashMap j = new IdentityHashMap();

    public C3126uP(C0702m c0702m) {
        WP wpZ = c0702m.Z();
        this.a = c0702m;
        this.b = wpZ.a;
        this.c = wpZ.b;
        this.d = wpZ.h;
    }

    private void b(C0729q2 c0729q2, com.android.tools.r8.shaking.F0 f0) {
        if (c0729q2.x1()) {
            if (!k && c0729q2.d != this.d.e) {
                throw new AssertionError();
            }
            return;
        }
        com.android.tools.r8.graph.o5 o5VarF = c0729q2.f(this.d.E);
        if (o5VarF != null) {
            f0.a(c0729q2, o5VarF, com.android.tools.r8.shaking.V0.d, AbstractC3837z1.g(o5VarF));
        } else {
            if (k) {
                return;
            }
            throw new AssertionError("Expected class `" + c0729q2.d.r0() + "` to declare a dynamicMethod()");
        }
    }

    private IdentityHashMap c() {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        Iterator it = this.i.iterator();
        while (it.hasNext()) {
            C2409kl c2409klA = ((com.android.tools.r8.graph.o5) it.next()).a(this.a);
            Set setC = OU.c();
            Iterator it2 = c2409klA.a(new Predicate() { // from class: com.android.tools.r8.internal.uP$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((C3332x4) obj).O();
                }
            }).iterator();
            while (it2.hasNext()) {
                a(((C3332x4) it2.next()).m().B0().L2(), setC, new Consumer() { // from class: com.android.tools.r8.internal.uP$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C3126uP.a(identityHashMap, (com.android.tools.r8.graph.m5) obj);
                    }
                });
            }
        }
        return identityHashMap;
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(C0729q2 c0729q2, com.android.tools.r8.shaking.F0 f0) {
        if (!k) {
            ((C0680j) this.a.f()).h();
        }
        if (((C0680j) this.a.f()).n().b(c0729q2.d, this.d.i)) {
            b(c0729q2, f0);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(com.android.tools.r8.graph.o5 o5Var, InterfaceC0694k5 interfaceC0694k5, com.android.tools.r8.shaking.L l) {
        if (this.d.b(o5Var.getReference())) {
            l.a(o5Var, C3762m1.x().x());
            this.i.add(o5Var);
            return;
        }
        VP vp = this.d;
        vp.getClass();
        if (vp.a(o5Var.getReference())) {
            C0767v2 c0767v2T = o5Var.t();
            if (this.f.containsKey(c0767v2T)) {
                this.e.put(c0767v2T, (EP) this.f.remove(c0767v2T));
                return;
            }
            boolean z = k;
            if (!z && this.e.containsKey(c0767v2T)) {
                throw new AssertionError();
            }
            IdentityHashMap identityHashMap = this.e;
            C0767v2 c0767v2T2 = o5Var.t();
            if (!z && identityHashMap.containsKey(c0767v2T2)) {
                throw new AssertionError();
            }
            AbstractC1305Qs abstractC1305QsA = C1158Lj.a(o5Var.a(this.a), this.d);
            identityHashMap.put(c0767v2T2, abstractC1305QsA != null ? this.b.a(o5Var, abstractC1305QsA) : null);
        }
    }

    private void b(final com.android.tools.r8.shaking.L l) {
        c().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.uP$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(l, (C0729q2) obj, (Set) obj2);
            }
        });
        this.i.clear();
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(com.android.tools.r8.shaking.L l, com.android.tools.r8.shaking.F0 f0, com.android.tools.r8.utils.y3 y3Var) {
        com.android.tools.r8.graph.m5 m5VarC;
        y3Var.a("[Proto] Extend fixpoint");
        b(l);
        a(l, f0);
        if (l.l() == C3208vP.g()) {
            if (!k && !l.i().b()) {
                throw new AssertionError();
            }
            if (f0.a()) {
                for (EP ep : this.e.values()) {
                    if (ep != null && ep.g()) {
                        com.android.tools.r8.graph.o5 o5VarA = ep.a();
                        if (this.h.add(o5VarA.e())) {
                            for (C3355xP c3355xP : ep.b()) {
                                List<LP> listC = c3355xP.c();
                                if (listC != null && !listC.isEmpty() && (m5VarC = c3355xP.c(this.a, ep)) != null && l.b(m5VarC.e())) {
                                    for (LP lp : listC) {
                                        lp.getClass();
                                        if (lp instanceof NP) {
                                            f0.a(lp.c().g(), o5VarA);
                                        } else if (lp instanceof XP) {
                                            f0.a(lp.d().g(), o5VarA);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        y3Var.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set b(C0767v2 c0767v2) {
        return OU.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.shaking.L l, C0729q2 c0729q2, Set set) {
        com.android.tools.r8.graph.o5 o5VarX1 = c0729q2.X1();
        if (o5VarX1 == null) {
            if (!k) {
                throw new AssertionError();
            }
            return;
        }
        IdentityHashMap identityHashMapA = AbstractC2871ql.a(this.a, o5VarX1.a(this.a), set);
        Iterator it = set.iterator();
        while (it.hasNext()) {
            RW rw = (RW) identityHashMapA.get((com.android.tools.r8.graph.T0) it.next());
            if (rw == null) {
                if (!k && !l.i().b()) {
                    throw new AssertionError();
                }
            } else {
                a(rw);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(C0729q2 c0729q2) {
        return OU.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, com.android.tools.r8.graph.m5 m5Var) {
        ((Set) map.computeIfAbsent(m5Var.a(), new Function() { // from class: com.android.tools.r8.internal.uP$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3126uP.a((C0729q2) obj);
            }
        })).add(m5Var.e());
    }

    private void a(S10 s10, Set set, Consumer consumer) {
        S10 s10M = s10.m();
        if (s10M.i()) {
            ML mlL = s10M.l();
            if (set.add(mlL)) {
                Iterator<S10> it = mlL.k0().iterator();
                while (it.hasNext()) {
                    a(it.next(), set, consumer);
                }
                return;
            }
            return;
        }
        if (s10M.V()) {
            return;
        }
        AbstractC2412ko abstractC2412ko = s10M.b;
        abstractC2412ko.getClass();
        if (abstractC2412ko instanceof QW) {
            AbstractC0735r0 abstractC0735r0C = ((C0680j) this.a.f()).c(abstractC2412ko.F0().getField()).c();
            if (abstractC0735r0C != null && (abstractC0735r0C instanceof com.android.tools.r8.graph.m5)) {
                consumer.accept(abstractC0735r0C.Q());
                return;
            } else {
                if (!k) {
                    throw new AssertionError();
                }
                return;
            }
        }
        if (k) {
            return;
        }
        if (!abstractC2412ko.Y1() || !this.d.b(abstractC2412ko.b0().S2())) {
            throw new AssertionError();
        }
    }

    private void a(RW rw) {
        AbstractC3472z00 abstractC3472z00W;
        AbstractC3472z00 abstractC3472z00W2;
        S10 s10M = rw.value().m();
        if (s10M.i()) {
            return;
        }
        AbstractC2412ko abstractC2412koA = s10M.b;
        if (abstractC2412koA.p2()) {
            abstractC2412koA = AbstractC2871ql.a(this.a.a(), abstractC2412koA.t0().b());
            if (abstractC2412koA == null) {
                if (!k) {
                    throw new AssertionError();
                }
                return;
            }
        }
        if (abstractC2412koA.W1() || (abstractC2412koA instanceof C1721bt)) {
            AbstractC1305Qs abstractC1305QsB0 = abstractC2412koA.b0();
            C0684j2 c0684j2S2 = abstractC1305QsB0.S2();
            VP vp = this.d;
            TP tp = vp.q;
            if (c0684j2S2 == tp.d) {
                abstractC3472z00W = ((S10) ((ArrayList) abstractC1305QsB0.J2()).get(0)).w();
                abstractC3472z00W2 = ((S10) ((ArrayList) abstractC1305QsB0.J2()).get(1)).w();
            } else if (c0684j2S2 == tp.e) {
                abstractC3472z00W = ((S10) ((ArrayList) abstractC1305QsB0.J2()).get(0)).w();
                abstractC3472z00W2 = ((S10) ((ArrayList) abstractC1305QsB0.J2()).get(2)).w();
            } else {
                QP qp = vp.p;
                if (c0684j2S2 != qp.a && c0684j2S2 != qp.b) {
                    return;
                }
                abstractC3472z00W = ((S10) ((ArrayList) abstractC1305QsB0.J2()).get(1)).w();
                abstractC3472z00W2 = ((S10) ((ArrayList) abstractC1305QsB0.J2()).get(3)).w();
            }
            if (abstractC3472z00W2.E()) {
                return;
            }
            if (abstractC3472z00W.w() && abstractC3472z00W2.w()) {
                ((Set) this.j.computeIfAbsent(abstractC3472z00W.b().R(), new Function() { // from class: com.android.tools.r8.internal.uP$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return C3126uP.b((C0767v2) obj);
                    }
                })).add(abstractC3472z00W2.b().R());
            } else if (!k) {
                throw new AssertionError();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:151:0x0102 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:165:0x002b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:57:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:61:0x010e A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:67:0x0122  */
    private void a(com.android.tools.r8.shaking.L l, com.android.tools.r8.shaking.F0 f0) {
        final com.android.tools.r8.graph.o5 o5VarY1;
        for (EP ep : this.e.values()) {
            if (ep != null && ep.g()) {
                final com.android.tools.r8.graph.o5 o5VarA = ep.a();
                for (C3355xP c3355xP : ep.b()) {
                    com.android.tools.r8.graph.m5 m5VarC = c3355xP.c(this.a, ep);
                    if (m5VarC != null) {
                        l.getClass();
                        boolean z = true;
                        if (l.b(m5VarC.e())) {
                            if (l.d(m5VarC) || l.e(m5VarC) || a(c3355xP)) {
                                f0.a(m5VarC, o5VarA);
                            }
                        } else if (a(c3355xP)) {
                            f0.a(m5VarC, o5VarA);
                        } else {
                            z = false;
                        }
                        if (z) {
                            C3503zP c3503zPD = c3355xP.d();
                            c3503zPD.getClass();
                            if (c3503zPD instanceof OP) {
                                m5VarC = c3355xP.b(this.a, ep);
                            } else if (c3355xP.b(ep)) {
                                com.android.tools.r8.graph.m5 m5VarA = c3355xP.a(this.a, ep);
                                f0.a(m5VarC, o5VarA);
                                m5VarC = m5VarA;
                            } else {
                                m5VarC = null;
                            }
                            if (m5VarC != null) {
                                o5VarY1 = o5VarA.a().Y1();
                                if (k && o5VarY1 == null) {
                                    throw new AssertionError();
                                }
                                if (l.b(m5VarC, new Predicate() { // from class: com.android.tools.r8.internal.uP$$ExternalSyntheticLambda5
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return C3126uP.a(o5VarY1, o5VarA, (com.android.tools.r8.graph.o5) obj);
                                    }
                                })) {
                                    f0.b(m5VarC, o5VarA);
                                }
                                f0.c(m5VarC, o5VarA);
                            } else {
                                continue;
                            }
                        } else {
                            C3503zP c3503zPD2 = c3355xP.d();
                            c3503zPD2.getClass();
                            if (c3503zPD2 instanceof OP) {
                                com.android.tools.r8.graph.m5 m5VarB = c3355xP.b(this.a, ep);
                                if (m5VarB == null || !l.b(m5VarB.e())) {
                                    m5VarC = null;
                                }
                                if (m5VarC != null) {
                                    o5VarY1 = o5VarA.a().Y1();
                                    if (k) {
                                    }
                                    if (l.b(m5VarC, new Predicate() { // from class: com.android.tools.r8.internal.uP$$ExternalSyntheticLambda5
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            return C3126uP.a(o5VarY1, o5VarA, (com.android.tools.r8.graph.o5) obj);
                                        }
                                    })) {
                                        f0.b(m5VarC, o5VarA);
                                    }
                                    f0.c(m5VarC, o5VarA);
                                } else {
                                    continue;
                                }
                            } else if (c3355xP.b(ep)) {
                                com.android.tools.r8.graph.m5 m5VarA2 = c3355xP.a(this.a, ep);
                                if (m5VarA2 != null && l.b(m5VarA2.e())) {
                                    if (this.a.Y().s && this.a.P()) {
                                        com.android.tools.r8.ir.optimize.info.e eVarX0 = m5VarA2.e().X0();
                                        if (!com.android.tools.r8.utils.J.c(eVarX0.h(), c3355xP.a(ep))) {
                                            continue;
                                        }
                                    }
                                    if (m5VarC != null) {
                                        o5VarY1 = o5VarA.a().Y1();
                                        if (k) {
                                        }
                                        if (l.b(m5VarC, new Predicate() { // from class: com.android.tools.r8.internal.uP$$ExternalSyntheticLambda5
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return C3126uP.a(o5VarY1, o5VarA, (com.android.tools.r8.graph.o5) obj);
                                            }
                                        })) {
                                            f0.b(m5VarC, o5VarA);
                                        }
                                        f0.c(m5VarC, o5VarA);
                                    } else {
                                        continue;
                                    }
                                }
                            } else {
                                m5VarC = null;
                                if (m5VarC != null) {
                                    o5VarY1 = o5VarA.a().Y1();
                                    if (k) {
                                    }
                                    if (l.b(m5VarC, new Predicate() { // from class: com.android.tools.r8.internal.uP$$ExternalSyntheticLambda5
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            return C3126uP.a(o5VarY1, o5VarA, (com.android.tools.r8.graph.o5) obj);
                                        }
                                    })) {
                                        f0.b(m5VarC, o5VarA);
                                    }
                                    f0.c(m5VarC, o5VarA);
                                } else {
                                    continue;
                                }
                            }
                        }
                    }
                }
                if (ep.k() == 0) {
                    continue;
                } else {
                    for (PP pp : ep.e()) {
                        AbstractC3433yP abstractC3433yPA = pp.a();
                        abstractC3433yPA.getClass();
                        if (!(abstractC3433yPA instanceof C2975sC)) {
                            if (!k) {
                                throw new AssertionError();
                            }
                        } else {
                            AbstractC0677i3 abstractC0677i3C = ((C0680j) this.a.f()).c(abstractC3433yPA.a().g());
                            abstractC0677i3C.getClass();
                            if (!(abstractC0677i3C instanceof C0692k3)) {
                                if (!k) {
                                    throw new AssertionError();
                                }
                            } else {
                                com.android.tools.r8.graph.m5 m5VarQ = abstractC0677i3C.q();
                                if (m5VarQ == null) {
                                    if (!k) {
                                        throw new AssertionError();
                                    }
                                } else {
                                    com.android.tools.r8.graph.o5 o5VarF = m5VarQ.a().f(this.d.E);
                                    if (o5VarF == null) {
                                        if (!k) {
                                            throw new AssertionError();
                                        }
                                    } else {
                                        l.getClass();
                                        if (l.b(m5VarQ.e())) {
                                            AbstractC3433yP abstractC3433yPB = pp.b();
                                            abstractC3433yPB.getClass();
                                            if (!(abstractC3433yPB instanceof C2975sC)) {
                                                if (!k) {
                                                    throw new AssertionError();
                                                }
                                            } else {
                                                AbstractC0677i3 abstractC0677i3C2 = ((C0680j) this.a.f()).c(abstractC3433yPB.a().g());
                                                abstractC0677i3C2.getClass();
                                                if (!(abstractC0677i3C2 instanceof C0692k3)) {
                                                    if (!k) {
                                                        throw new AssertionError();
                                                    }
                                                } else {
                                                    com.android.tools.r8.graph.m5 m5VarQ2 = abstractC0677i3C2.q();
                                                    if (m5VarQ2 != null && m5VarQ2.a() == m5VarQ.a()) {
                                                        f0.c(m5VarQ2, o5VarF);
                                                    } else if (!k) {
                                                        throw new AssertionError();
                                                    }
                                                }
                                            }
                                        } else {
                                            continue;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, com.android.tools.r8.graph.o5 o5Var3) {
        return (o5Var3.b(o5Var) || o5Var3.b(o5Var2)) ? false : true;
    }

    private boolean a(C3355xP c3355xP) {
        C0767v2 c0767v2A;
        C3503zP c3503zPD = c3355xP.d();
        if (c3503zPD.e() || c3503zPD.j()) {
            return true;
        }
        if ((this.a.Y().x1().d || (!c3503zPD.h() && !c3503zPD.i())) && (c0767v2A = c3355xP.a(this.c)) != null) {
            EP epA = a(c0767v2A);
            if (epA != null) {
                return a(epA);
            }
            if (!k) {
                throw new AssertionError("Unable to find proto message info for `" + c0767v2A + "`");
            }
        }
        return false;
    }

    private boolean a(EP ep) {
        if (!ep.g() && !this.j.containsKey(ep.f())) {
            return false;
        }
        AbstractC3938n2 abstractC3938n2 = (AbstractC3938n2) this.g.getOrDefault(ep, AbstractC3938n2.c);
        if (!abstractC3938n2.e()) {
            return abstractC3938n2.d();
        }
        this.g.put(ep, AbstractC3938n2.a(false));
        if (ep.g()) {
            Iterator it = ep.b().iterator();
            while (it.hasNext()) {
                if (a((C3355xP) it.next())) {
                    this.g.put(ep, AbstractC3938n2.a(true));
                    return true;
                }
            }
        }
        Iterator it2 = ((Iterable) this.j.getOrDefault(ep.f(), AbstractC1048Hm.k())).iterator();
        while (it2.hasNext()) {
            EP epA = a((C0767v2) it2.next());
            if (!k && epA == null) {
                throw new AssertionError();
            }
            if (a(epA)) {
                this.g.put(ep, AbstractC3938n2.a(true));
                return true;
            }
        }
        return false;
    }

    private EP a(C0767v2 c0767v2) {
        if (this.e.containsKey(c0767v2)) {
            return (EP) this.e.get(c0767v2);
        }
        if (this.f.containsKey(c0767v2)) {
            return (EP) this.f.get(c0767v2);
        }
        C0729q2 c0729q2A = C0729q2.a(this.a.c(c0767v2));
        if (c0729q2A == null) {
            this.f.put(c0767v2, null);
            return null;
        }
        com.android.tools.r8.graph.o5 o5VarF = c0729q2A.f(this.d.E);
        if (o5VarF == null) {
            this.f.put(c0767v2, null);
            return null;
        }
        IdentityHashMap identityHashMap = this.f;
        C0767v2 c0767v2T = o5VarF.t();
        if (!k && identityHashMap.containsKey(c0767v2T)) {
            throw new AssertionError();
        }
        AbstractC1305Qs abstractC1305QsA = C1158Lj.a(o5VarF.a(this.a), this.d);
        identityHashMap.put(c0767v2T, abstractC1305QsA != null ? this.b.a(o5VarF, abstractC1305QsA) : null);
        return (EP) this.f.get(c0767v2);
    }
}
