package com.android.tools.r8.shaking;

import com.android.tools.r8.errors.InlinableStaticFinalFieldPreconditionDiagnostic;
import com.android.tools.r8.errors.UnusedProguardKeepRuleDiagnostic;
import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0593a0;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.T0$$ExternalSyntheticLambda1;
import com.android.tools.r8.graph.W0$$ExternalSyntheticLambda12;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.u5;
import com.android.tools.r8.internal.AN;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC1699bX;
import com.android.tools.r8.internal.AbstractC2151hN;
import com.android.tools.r8.internal.C1018Gn$$ExternalSyntheticLambda0;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.utils.C3984w2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.a4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3686a4 extends AbstractC3695b4 {
    static final /* synthetic */ boolean u = true;
    public final AbstractC1639am g;
    public final Set h;
    public final Set i;
    public final Set j;
    public final Set k;
    public final Set l;
    public final C3984w2 m;
    public final Set n;
    public final Set o;
    public final Set p;
    public final Set q;
    public final Map r;
    public final Set s;
    public final Set t;

    private C3686a4(A a, AbstractC1639am abstractC1639am, Set set, Set set2, Set set3, Set set4, Set set5, Set set6, C3984w2 c3984w2, Set set7, Set set8, Set set9, Set set10, Set set11, Map map, Map map2, Set set12, Set set13, List list, AN an) {
        super(set2, set7, a, map2, list, an);
        this.g = abstractC1639am;
        this.h = set;
        this.i = set3;
        this.j = set4;
        this.k = set5;
        this.l = set6;
        this.m = c3984w2;
        this.n = set8;
        this.o = set9;
        this.p = set10;
        this.q = set11;
        this.r = map;
        this.s = Collections.unmodifiableSet(set12);
        this.t = Collections.unmodifiableSet(set13);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set b(C0767v2 c0767v2) {
        return new HashSet();
    }

    @Override // com.android.tools.r8.shaking.AbstractC3695b4
    public final A a() {
        return ((AbstractC3695b4) this).c;
    }

    public final void c(final C0702m c0702m) {
        ((AbstractC3695b4) this).c.a(X.d(), S1.a()).a(new BiPredicate() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda7
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return C3686a4.c((AbstractC0744s2) obj, (AbstractC3716f1) obj2);
            }
        }, new BiConsumer() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.b(c0702m, (AbstractC0744s2) obj, (AbstractC3716f1) obj2);
            }
        });
    }

    public final void d(final C0702m c0702m) {
        ((AbstractC3695b4) this).c.a(X.d(), S1.a()).a(new BiPredicate() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda14
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return C3686a4.d((AbstractC0744s2) obj, (AbstractC3716f1) obj2);
            }
        }, new BiConsumer() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda15
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3686a4.c(c0702m, (AbstractC0744s2) obj, (AbstractC3716f1) obj2);
            }
        });
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("RootSet");
        sb.append("\nreasonAsked: " + this.g.size());
        sb.append("\nidentifierNameStrings: " + this.s.size());
        sb.append("\nifRules: " + this.t.size());
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(AbstractC3716f1 abstractC3716f1) {
        abstractC3716f1.a().r();
    }

    public final void a(u5 u5Var) {
        S1 s1A = ((AbstractC3695b4) this).c.a(X.d(), (S1) null);
        if (s1A != null) {
            s1A.a(u5Var);
            if (s1A.b()) {
                ((AbstractC3695b4) this).c.b(X.d());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C0702m c0702m, AbstractC0744s2 abstractC0744s2, AbstractC3716f1 abstractC3716f1) {
        C0684j2 c0684j2V0 = abstractC0744s2.v0();
        boolean z = u;
        if (!z && !((C3733i) c0702m.f()).l(c0684j2V0)) {
            throw new AssertionError("Expected kept method `" + abstractC0744s2.r0() + "` to be targeted");
        }
        if (c0702m.a((AbstractC0642e2) c0684j2V0).c(c0684j2V0).s1() || !a(c0702m, c0684j2V0.B0()) || z || ((C3733i) c0702m.f()).k(c0684j2V0)) {
            return;
        }
        throw new AssertionError("Expected non-abstract kept method `" + abstractC0744s2.r0() + "` to be live");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(AbstractC0744s2 abstractC0744s2, AbstractC3716f1 abstractC3716f1) {
        return abstractC0744s2.y0() && !abstractC3716f1.m();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean d(AbstractC0744s2 abstractC0744s2, AbstractC3716f1 abstractC3716f1) {
        abstractC0744s2.getClass();
        return (abstractC0744s2 instanceof C0767v2) && !abstractC3716f1.m();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(C0702m c0702m, AbstractC0744s2 abstractC0744s2, AbstractC3716f1 abstractC3716f1) {
        C0767v2 c0767v2W0 = abstractC0744s2.w0();
        if (u || ((C3733i) c0702m.f()).i(c0767v2W0)) {
            return;
        }
        throw new AssertionError("Expected kept type `" + c0767v2W0.r0() + "` to be live");
    }

    void a(InterfaceC0694k5 interfaceC0694k5) {
        ((AbstractC3695b4) this).c.a(interfaceC0694k5.getReference()).f().a(interfaceC0694k5.e0(), new Consumer() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3686a4.b((AbstractC3716f1) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set d(C0767v2 c0767v2) {
        return OU.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set c(C0767v2 c0767v2) {
        return OU.c();
    }

    public final void b(C0702m c0702m) {
        final C0680j c0680j = (C0680j) c0702m.f();
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        ((AbstractC3695b4) this).c.a(S1.a()).a(new BiPredicate() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda1
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return C3686a4.b((AbstractC0744s2) obj, (AbstractC3716f1) obj2);
            }
        }, new BiConsumer() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3686a4.a(c0680j, identityHashMap, (AbstractC0744s2) obj, (AbstractC3716f1) obj2);
            }
        });
        for (C0729q2 c0729q2 : ((C0680j) c0702m.f()).d()) {
            Set set = null;
            Set set2 = null;
            for (AbstractC0642e2 abstractC0642e2 : (Set) identityHashMap.getOrDefault(c0729q2.d, AbstractC1048Hm.k())) {
                if (abstractC0642e2.x0()) {
                    com.android.tools.r8.graph.Y0 y0T0 = abstractC0642e2.t0();
                    if (set2 == null) {
                        set2 = (Set) AbstractC1699bX.a(c0729q2.W0()).map(new T0$$ExternalSyntheticLambda1()).collect(Collectors.toSet());
                    }
                    if (!u && !set2.contains(y0T0)) {
                        throw new AssertionError("Expected field `" + y0T0.r0() + "` from the root set to be present");
                    }
                } else {
                    C0684j2 c0684j2V0 = abstractC0642e2.v0();
                    if (set == null) {
                        set = (Set) AbstractC1699bX.a(c0729q2.M1()).map(new W0$$ExternalSyntheticLambda12()).collect(Collectors.toSet());
                    }
                    if (!u && !set.contains(c0684j2V0)) {
                        throw new AssertionError("Expected method `" + c0684j2V0.r0() + "` from the root set to be present");
                    }
                }
            }
            identityHashMap.remove(c0729q2.d);
        }
        if (identityHashMap.isEmpty()) {
            return;
        }
        C0767v2 c0767v2 = (C0767v2) identityHashMap.keySet().iterator().next();
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2);
        boolean z = u;
        if (!z && abstractC0727q0C != null && !abstractC0727q0C.e0()) {
            throw new AssertionError("Unexpected library type in root set: `" + c0767v2 + "`");
        }
        if (z || identityHashMap.isEmpty()) {
            return;
        }
        throw new AssertionError("Expected type `" + c0767v2.r0() + "` to be present");
    }

    public final void a(final C0702m c0702m) {
        ((AbstractC3695b4) this).c.a(X.d(), S1.a()).a(new BiPredicate() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda3
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return C3686a4.a((AbstractC0744s2) obj, (AbstractC3716f1) obj2);
            }
        }, new BiConsumer() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c0702m, (AbstractC0744s2) obj, (AbstractC3716f1) obj2);
            }
        });
    }

    public static void a(com.android.tools.r8.utils.A1 a1) {
        List<S2> listV = a1.D0().v();
        if (listV == null) {
            return;
        }
        for (S2 s2 : listV) {
            s2.getClass();
            if (s2 instanceof C3712e3) {
                C3712e3 c3712e3S = s2.s();
                Set setI = c3712e3S.I();
                if (!setI.isEmpty()) {
                    ArrayList arrayList = new ArrayList(setI);
                    arrayList.sort(new C1018Gn$$ExternalSyntheticLambda0());
                    a1.e.warning(new InlinableStaticFinalFieldPreconditionDiagnostic(c3712e3S, arrayList));
                }
            }
            if (!s2.C()) {
                a1.e.info(new UnusedProguardKeepRuleDiagnostic(s2));
            }
        }
    }

    final void a(W3 w3) {
        this.a.addAll(w3.a);
        this.b.addAll(w3.b);
        w3.d.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C0767v2) obj, (Set) obj2);
            }
        });
        this.e.addAll(w3.e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0767v2 c0767v2, Set set) {
        ((Set) this.d.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3686a4.b((C0767v2) obj);
            }
        })).addAll(set);
    }

    public final boolean a(InterfaceC0694k5 interfaceC0694k5, com.android.tools.r8.utils.A1 a1) {
        if (a1.o1()) {
            return ((AbstractC3695b4) this).c.a(X.d(), S1.a()).a(interfaceC0694k5.getReference(), new Predicate() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda17
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C3686a4.a((AbstractC3716f1) obj);
                }
            });
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC3716f1 abstractC3716f1) {
        return !abstractC3716f1.m();
    }

    public final void a(C0702m c0702m, L l, com.android.tools.r8.utils.y3 y3Var) {
        y3Var.a("Prune keep info");
        ((AbstractC3695b4) this).c.a(c0702m, l);
        y3Var.c();
        y3Var.a("Prune others");
        a(this.n, c0702m, l);
        a(this.o, c0702m, l);
        a(this.p, c0702m, l);
        a(this.h, c0702m, l);
        y3Var.c();
    }

    private static void a(Set set, final C0702m c0702m, final L l) {
        set.removeIf(new Predicate() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3686a4.a(c0702m, l, (AbstractC0744s2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(final com.android.tools.r8.graph.Q0 q0, L l, AbstractC0744s2 abstractC0744s2) {
        Objects.requireNonNull(q0);
        InterfaceC0593a0 interfaceC0593a0 = (InterfaceC0593a0) abstractC0744s2.a(new Function() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return q0.c((C0767v2) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3686a4.a(q0, (com.android.tools.r8.graph.Y0) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3686a4.a(q0, (C0684j2) obj);
            }
        });
        return interfaceC0593a0 == null || !l.a(interfaceC0593a0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ InterfaceC0593a0 a(com.android.tools.r8.graph.Q0 q0, com.android.tools.r8.graph.Y0 y0) {
        return y0.a(q0.c(y0.B0()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ InterfaceC0593a0 a(com.android.tools.r8.graph.Q0 q0, C0684j2 c0684j2) {
        return c0684j2.a(q0.c(c0684j2.B0()));
    }

    public C3686a4 a(AbstractC1476Wj abstractC1476Wj) {
        if (abstractC1476Wj.h()) {
            return this;
        }
        this.t.forEach(new Y3$$ExternalSyntheticLambda1());
        return new C3686a4(((AbstractC3695b4) this).c.a(abstractC1476Wj), this.g, this.h, this.a, this.i, this.j, this.k, this.l, this.m, this.b, this.n, this.o, this.p, this.q, this.r, this.d, this.s, this.t, this.e, this.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(AbstractC0744s2 abstractC0744s2, AbstractC3716f1 abstractC3716f1) {
        return !abstractC3716f1.m();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC0744s2 abstractC0744s2, AbstractC3716f1 abstractC3716f1) {
        return abstractC0744s2.x0() && !abstractC3716f1.m();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0702m c0702m, AbstractC0744s2 abstractC0744s2, AbstractC3716f1 abstractC3716f1) {
        com.android.tools.r8.graph.Y0 y0T0 = abstractC0744s2.t0();
        m5 m5VarA = y0T0.a(C0729q2.a(c0702m.a(y0T0)));
        if (m5VarA != null) {
            if (m5VarA.getAccessFlags().o() || a(c0702m, m5VarA.t())) {
                boolean z = u;
                if (!z && !((C3733i) c0702m.f()).a(m5VarA.e())) {
                    throw new AssertionError("Expected kept field `" + y0T0.r0() + "` to be read");
                }
                if (z || ((C3733i) c0702m.f()).b(m5VarA.e())) {
                    return;
                }
                throw new AssertionError("Expected kept field `" + y0T0.r0() + "` to be written");
            }
        }
    }

    private boolean a(C0702m c0702m, C0767v2 c0767v2) {
        C0729q2 c0729q2A = C0729q2.a(c0702m.c(c0767v2));
        if (c0729q2A == null) {
            return false;
        }
        if (a(c0729q2A, c0702m.Y())) {
            return true;
        }
        C0767v2 c0767v3 = c0729q2A.f;
        if (c0767v3 != null) {
            return a(c0702m, c0767v3);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0680j c0680j, Map map, AbstractC0744s2 abstractC0744s2, AbstractC3716f1 abstractC3716f1) {
        if (abstractC0744s2.z0()) {
            C0767v2 c0767v2W0 = abstractC0744s2.w0();
            if (!u && c0680j.i() && !c0680j.o().a((AbstractC0744s2) c0767v2W0)) {
                throw new AssertionError("Expected reference `" + c0767v2W0.r0() + "` to be pinned");
            }
            map.computeIfAbsent(c0767v2W0, new Function() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C3686a4.c((C0767v2) obj);
                }
            });
            return;
        }
        AbstractC0642e2 abstractC0642e2U0 = abstractC0744s2.u0();
        if (!u && c0680j.i() && !c0680j.o().a((AbstractC0744s2) abstractC0642e2U0)) {
            throw new AssertionError("Expected reference `" + abstractC0642e2U0.r0() + "` to be pinned");
        }
        ((Set) map.computeIfAbsent(abstractC0642e2U0.e, new Function() { // from class: com.android.tools.r8.shaking.a4$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3686a4.d((C0767v2) obj);
            }
        })).add(abstractC0642e2U0);
    }

    public static C3707d4 a(C0702m c0702m, L l, B5 b5) {
        return new C3707d4(c0702m, U3.a(l.k()), b5, null);
    }

    public static C3707d4 a(C0702m<? extends C0680j> c0702m, AbstractC2151hN abstractC2151hN, B5 b5, Iterable<? extends S2> iterable) {
        return new C3707d4(c0702m, U3.a(abstractC2151hN), b5, iterable);
    }
}
