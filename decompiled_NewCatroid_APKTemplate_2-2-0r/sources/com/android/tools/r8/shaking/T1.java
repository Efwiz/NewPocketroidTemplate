package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.l5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC0892Cf;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC2863qd;
import com.android.tools.r8.internal.C0862Bm;
import com.android.tools.r8.internal.C0961El;
import com.android.tools.r8.internal.C3004sd;
import com.android.tools.r8.internal.OU;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class T1 {
    static final /* synthetic */ boolean d = true;
    private final Set a;
    private final IdentityHashMap b;
    private final Set c;

    private T1(Set set) {
        this.b = new IdentityHashMap();
        this.c = OU.c();
        this.a = set;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set c(C0767v2 c0767v2) {
        return C0961El.a();
    }

    private static Predicate d(C0702m c0702m) {
        final C0759u2 c0759u2C = c0702m.a().c("L$-vivified-$/");
        return new Predicate() { // from class: com.android.tools.r8.shaking.T1$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return T1.a(c0759u2C, (C0767v2) obj);
            }
        };
    }

    public final void a(C0767v2 c0767v2, l5 l5Var) {
        boolean z = d;
        if (!z && l5Var == null) {
            throw new AssertionError();
        }
        if (!z && l5Var.getContext().C() == c0767v2) {
            throw new AssertionError();
        }
        if (this.a.contains(c0767v2)) {
            return;
        }
        ((Set) this.b.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.shaking.T1$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return T1.c((C0767v2) obj);
            }
        })).add(l5Var);
    }

    public final void b(C0767v2 c0767v2) {
        this.c.add(c0767v2);
    }

    private static Predicate c(C0702m c0702m) {
        final AbstractC1048Hm abstractC1048HmB = b(c0702m);
        final Predicate predicateD = d(c0702m);
        final AbstractC0892Cf abstractC0892CfU = c0702m.u();
        return new Predicate() { // from class: com.android.tools.r8.shaking.T1$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return T1.a(abstractC1048HmB, predicateD, abstractC0892CfU, (C0767v2) obj);
            }
        };
    }

    public final U1 b(C0702m c0702m, com.android.tools.r8.synthesis.A a) {
        IdentityHashMap identityHashMapA = a(c0702m, a);
        if (!identityHashMapA.isEmpty()) {
            com.android.tools.r8.diagnostic.internal.m mVarA = a(identityHashMapA);
            if (c0702m.Y().O0) {
                c0702m.Y().e.warning(mVarA);
            } else {
                c0702m.Y().e.error(mVarA);
            }
        }
        c0702m.Y().e.a();
        return new U1(com.android.tools.r8.utils.T2.a(this.a, this.b.keySet(), this.c));
    }

    public final boolean d(C0767v2 c0767v2) {
        return this.a.contains(c0767v2);
    }

    public final boolean a(C0767v2 c0767v2) {
        return this.a.contains(c0767v2) || this.b.containsKey(c0767v2) || this.c.contains(c0767v2);
    }

    public final U1 a(C0702m c0702m) {
        if (!d && !a(c0702m, new com.android.tools.r8.synthesis.A() { // from class: com.android.tools.r8.shaking.T1$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.A
            public final Set a(C0729q2 c0729q2) {
                return T1.a(c0729q2);
            }
        }).isEmpty()) {
            throw new AssertionError();
        }
        return new U1(com.android.tools.r8.utils.T2.a(this.a, this.b.keySet(), this.c));
    }

    private static AbstractC1048Hm b(final C0702m c0702m) {
        C0722p1 c0722p1A = c0702m.a();
        final C0862Bm c0862BmA = AbstractC1048Hm.g().a((Object[]) new C0767v2[]{c0722p1A.Q4, c0722p1A.V4, c0722p1A.g5, c0722p1A.i5, c0722p1A.h5, c0722p1A.O4, c0722p1A.z5});
        c0702m.Y().C1.l().forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.T1$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                T1.a(c0862BmA, c0702m, (C0767v2) obj, (C3004sd) obj2);
            }
        });
        AbstractC2863qd.a(c0722p1A, new Consumer() { // from class: com.android.tools.r8.shaking.T1$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                T1.a(c0862BmA, (C0684j2) obj);
            }
        });
        return c0862BmA.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(C0729q2 c0729q2) {
        return AbstractC1048Hm.c(c0729q2.getType());
    }

    private static com.android.tools.r8.diagnostic.internal.m a(IdentityHashMap identityHashMap) {
        final com.android.tools.r8.diagnostic.internal.l lVarA = com.android.tools.r8.diagnostic.internal.m.a();
        identityHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.T1$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                T1.a(lVarA, (C0767v2) obj, (Set) obj2);
            }
        });
        return lVarA.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.diagnostic.internal.l lVar, C0767v2 c0767v2, Set set) {
        com.android.tools.r8.diagnostic.internal.g gVarA = com.android.tools.r8.diagnostic.internal.h.a().a(c0767v2.B0());
        Iterator it = set.iterator();
        while (it.hasNext()) {
            gVarA.a(com.android.tools.r8.diagnostic.internal.d.a((l5) it.next()));
        }
        lVar.a((com.android.tools.r8.diagnostic.internal.j) gVarA.b());
    }

    private IdentityHashMap a(final C0702m c0702m, com.android.tools.r8.synthesis.A a) {
        for (Map.Entry entry : this.b.entrySet()) {
            entry.setValue(a(c0702m, (Set) entry.getValue(), a));
        }
        final Predicate predicateC = c(c0702m);
        final IdentityHashMap identityHashMap = new IdentityHashMap(this.b.size());
        this.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.T1$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                T1.a(predicateC, c0702m, identityHashMap, (C0767v2) obj, (Set) obj2);
            }
        });
        return identityHashMap;
    }

    private static Set a(C0702m c0702m, Set set, com.android.tools.r8.synthesis.A a) {
        if (set.isEmpty()) {
            return set;
        }
        C0961El c0961ElA = C0961El.a();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            l5 l5Var = (l5) it.next();
            if (!l5Var.O()) {
                c0961ElA.add(l5Var);
            } else {
                C0729q2 c0729q2B = l5Var.getContext().i().b();
                if (!c0702m.D().b(c0729q2B)) {
                    c0961ElA.add(l5Var);
                } else {
                    Set<AbstractC0744s2> setA = c0702m.D().a(c0729q2B, a);
                    boolean z = d;
                    if (!z && setA == null) {
                        throw new AssertionError();
                    }
                    if (!z && setA.isEmpty()) {
                        throw new AssertionError();
                    }
                    for (AbstractC0744s2 abstractC0744s2 : setA) {
                        if (abstractC0744s2.y0()) {
                            C0729q2 c0729q2A0 = c0702m.c(abstractC0744s2.C()).a0();
                            o5 o5VarF = c0729q2A0.f(abstractC0744s2.v0());
                            if (o5VarF != null) {
                                c0961ElA.add(o5VarF);
                            } else {
                                if (!d && !c0729q2A0.isInterface()) {
                                    throw new AssertionError();
                                }
                                c0961ElA.add(c0729q2A0);
                            }
                        } else if (abstractC0744s2.z0()) {
                            c0961ElA.add(c0702m.c(abstractC0744s2.w0()).a0());
                        } else if (!d) {
                            throw new AssertionError("Unexpected synthesizing context: " + abstractC0744s2.r0());
                        }
                    }
                }
            }
        }
        return c0961ElA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Predicate predicate, final C0702m c0702m, Map map, C0767v2 c0767v2, Set set) {
        if (predicate.test(c0767v2)) {
            return;
        }
        set.removeIf(new Predicate() { // from class: com.android.tools.r8.shaking.T1$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return T1.a(c0702m, (l5) obj);
            }
        });
        if (set.isEmpty()) {
            return;
        }
        map.put(c0767v2, set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0702m c0702m, l5 l5Var) {
        return c0702m.u().a(l5Var.getContext().C());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, Predicate predicate, AbstractC0892Cf abstractC0892Cf, C0767v2 c0767v2) {
        return set.contains(c0767v2) || predicate.test(c0767v2) || abstractC0892Cf.a(c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0862Bm c0862Bm, C0702m c0702m, C0767v2 c0767v2, C3004sd c3004sd) {
        C0767v2 c0767v2B0 = c3004sd.a().B0();
        c0862Bm.a(c0767v2B0);
        C0767v2 c0767v2B = c0702m.t.b(c0702m, c0767v2B0);
        if (c0767v2B != null) {
            c0862Bm.a(c0767v2B);
        }
        C0767v2 c0767v2B1 = c3004sd.b().B0();
        c0862Bm.a(c0767v2B1);
        C0767v2 c0767v2B2 = c0702m.t.b(c0702m, c0767v2B1);
        if (c0767v2B2 != null) {
            c0862Bm.a(c0767v2B2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final C0862Bm c0862Bm, C0684j2 c0684j2) {
        Iterable<C0767v2> iterableD0 = c0684j2.D0();
        Objects.requireNonNull(c0862Bm);
        iterableD0.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.T1$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c0862Bm.a((C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(C0759u2 c0759u2, C0767v2 c0767v2) {
        C0759u2 c0759u2D0 = c0767v2.D0();
        c0759u2D0.getClass();
        return c0759u2D0.a(c0759u2.e);
    }
}
