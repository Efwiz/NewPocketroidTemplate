package com.android.tools.r8.shaking;

import com.android.tools.r8.AbstractC0403b;
import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.AbstractC0741s0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0635d2;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.EnumC0608b0;
import com.android.tools.r8.graph.InterfaceC0593a0;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.Y0$$ExternalSyntheticLambda4;
import com.android.tools.r8.graph.n5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AN;
import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.AbstractC2237iT;
import com.android.tools.r8.internal.AbstractC2763pC;
import com.android.tools.r8.internal.C1132Kj;
import com.android.tools.r8.internal.C1158Lj;
import com.android.tools.r8.internal.C1566Zr;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.InterfaceC1720bs;
import com.android.tools.r8.internal.JV$$ExternalSyntheticLambda3;
import com.android.tools.r8.internal.O2$$ExternalSyntheticLambda4;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3894f2;
import com.android.tools.r8.utils.C3954q2;
import com.android.tools.r8.utils.C3984w2;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.StringDiagnostic;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.bytebuddy.description.method.MethodDescription;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.d4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3707d4 {
    static final /* synthetic */ boolean H = true;
    private final B A;
    private final Set B;
    private final LinkedHashMap C;
    private final Set D;
    private final com.android.tools.r8.ir.optimize.info.r E;
    private final C1566Zr F;
    private final AN G;
    private final C0702m a;
    private C3743k b;
    private final U3 c;
    private final B5 d;
    private final com.android.tools.r8.graph.N2 e;
    private final Iterable f;
    private final A g;
    private final LinkedHashMap h;
    private final Set i;
    private final Set j;
    private final Set k;
    private final Set l;
    private final Set m;
    private final Set n;
    private final C3984w2 o;
    private final Set p;
    private final Set q;
    private final Set r;
    private final Set s;
    private final Set t;
    private final IdentityHashMap u;
    private final IdentityHashMap v;
    private final Set w;
    private final ConcurrentHashMap x;
    private final ConcurrentLinkedQueue y;
    private final com.android.tools.r8.utils.A1 z;

    private C3707d4(C0702m c0702m, U3 u3, B5 b5, Iterable iterable) {
        this.g = new A();
        this.h = new LinkedHashMap();
        this.i = OU.c();
        this.j = OU.c();
        this.k = OU.c();
        this.l = OU.c();
        this.m = OU.c();
        this.n = OU.c();
        this.o = new C3984w2();
        this.p = OU.c();
        this.q = OU.c();
        this.r = OU.c();
        this.s = OU.c();
        this.t = OU.c();
        this.u = new IdentityHashMap();
        this.v = new IdentityHashMap();
        this.w = OU.c();
        this.x = new ConcurrentHashMap();
        this.y = new ConcurrentLinkedQueue();
        this.A = new B();
        this.B = OU.c();
        this.C = new LinkedHashMap();
        this.D = OU.c();
        this.E = com.android.tools.r8.ir.optimize.info.r.b();
        this.G = AN.c();
        this.a = c0702m;
        this.c = u3;
        this.d = b5;
        this.e = ((C0680j) c0702m.f()).b().b();
        this.f = iterable;
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        this.z = a1Y;
        this.F = a1Y.Z0() ? new C1566Zr(c0702m) : null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC0744s2 a(AbstractC0744s2 abstractC0744s2) {
        return abstractC0744s2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.O0 o0) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(AbstractC3714f abstractC3714f) {
    }

    private void c(AbstractC0727q0 abstractC0727q0, final List list, final S2 s2, final C3712e3 c3712e3) {
        AbstractC0727q0 abstractC0727q0C;
        HashSet hashSet = new HashSet();
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.addAll(this.d.c(abstractC0727q0.d));
        while (!arrayDeque.isEmpty()) {
            C0767v2 c0767v2 = (C0767v2) arrayDeque.poll();
            if (hashSet.add(c0767v2) && (abstractC0727q0C = this.a.c(c0767v2)) != null) {
                final Map map = null;
                abstractC0727q0C.b(new Consumer() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda14
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.b(map, list, s2, c3712e3, (AbstractC0748t0) obj);
                    }
                }, new O2$$ExternalSyntheticLambda4());
                arrayDeque.addAll(this.d.c(abstractC0727q0C.d));
            }
        }
    }

    void a(AbstractC3714f abstractC3714f) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(com.android.tools.r8.graph.O0 o0) {
        return !o0.F0();
    }

    private void b(final AbstractC0727q0 abstractC0727q0, final List list, final S2 s2, final Map map, boolean z, final C3712e3 c3712e3) {
        AbstractC0727q0 abstractC0727q0C;
        final HashSet hashSet = this.z.S0 ? null : new HashSet();
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(abstractC0727q0);
        while (!arrayDeque.isEmpty()) {
            final AbstractC0727q0 abstractC0727q1 = (AbstractC0727q0) arrayDeque.pop();
            if (!z && abstractC0727q1.I1()) {
                break;
            }
            abstractC0727q1.b(new Consumer() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda19
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(map, list, hashSet, s2, c3712e3, (AbstractC0748t0) obj);
                }
            }, new Predicate() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda9
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.a(abstractC0727q1, abstractC0727q0, (com.android.tools.r8.graph.W0) obj);
                }
            });
            C0767v2 c0767v2 = abstractC0727q1.f;
            if (c0767v2 != null && (abstractC0727q0C = this.e.c(c0767v2)) != null) {
                arrayDeque.add(abstractC0727q0C);
            }
        }
        if (abstractC0727q0.e0()) {
            s2.getClass();
            if (!(s2 instanceof C3732h3) || s2.t().G().d || (this instanceof Z3)) {
                return;
            }
            C3701c4 c3701c4 = new C3701c4(this, abstractC0727q0.a0(), list, s2, map, c3712e3);
            c3701c4.a(c3701c4.a.d);
        }
    }

    public final C3707d4 a(C3743k c3743k) {
        this.b = c3743k;
        return this;
    }

    final void a(ExecutorService executorService, ArrayList arrayList, final S2 s2, final C3712e3 c3712e3) {
        List<C0767v2> listA = s2.c().a();
        if (listA != null) {
            Iterator<C0767v2> it = listA.iterator();
            while (it.hasNext()) {
                AbstractC0727q0 abstractC0727q0C = this.e.c(it.next());
                if (abstractC0727q0C != null) {
                    a(abstractC0727q0C, s2, c3712e3);
                }
            }
            return;
        }
        arrayList.add(executorService.submit(new Runnable() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.a(s2, c3712e3);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(S2 s2, C3712e3 c3712e3) {
        Iterator it = s2.a(this.a, this.d, this.e.d()).iterator();
        while (it.hasNext()) {
            a((AbstractC0727q0) it.next(), s2, c3712e3);
        }
        if (s2.p()) {
            Iterator<C0635d2> it2 = this.e.l().iterator();
            while (it2.hasNext()) {
                a((AbstractC0727q0) it2.next(), s2, c3712e3);
            }
        }
    }

    final boolean c(AbstractC0727q0 abstractC0727q0, S2 s2) {
        boolean zA;
        AbstractC0727q0 abstractC0727q0C;
        AbstractC3714f abstractC3714fA;
        C0767v2 c0767v2 = abstractC0727q0.f;
        while (true) {
            if (c0767v2 != null && (abstractC0727q0C = this.e.c(c0767v2)) != null) {
                if (s2.g().a(this.a, abstractC0727q0C.d) && (abstractC3714fA = a(s2.f(), abstractC0727q0C, abstractC0727q0C.s0(), EnumC0608b0.c)) != null) {
                    a(abstractC3714fA);
                    zA = true;
                    break;
                }
                c0767v2 = abstractC0727q0C.f;
            } else {
                zA = a(abstractC0727q0, s2, false);
                break;
            }
        }
        if (zA || a(abstractC0727q0, s2)) {
            return true;
        }
        return a(abstractC0727q0, s2, true);
    }

    public C3686a4 a(ExecutorService executorService) throws Throwable {
        this.e.c.a("Build root set...");
        try {
            ArrayList arrayList = new ArrayList();
            Iterable<S2> iterable = this.f;
            if (iterable != null) {
                for (S2 s2 : iterable) {
                    if (s2 instanceof C3712e3) {
                        this.B.add((C3712e3) s2);
                    } else {
                        a(executorService, arrayList, s2, (C3712e3) null);
                    }
                }
                C3879c3.a(arrayList);
            }
            this.e.c.c();
            b();
            c();
            C3743k c3743k = this.b;
            if (c3743k != null && !c3743k.b()) {
                com.android.tools.r8.graph.r.a(this.a, this.d).a(((C0680j) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda17
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a((AbstractC0727q0) obj);
                    }
                });
            }
            this.a.d(new InterfaceC3905h3() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda18
                @Override // com.android.tools.r8.utils.InterfaceC3905h3
                public final void accept(Object obj) {
                    this.f$0.a((C1158Lj) obj);
                }
            });
            if (this.a.Y().x1().c) {
                C1132Kj.a(this.a, this.d, this.o, this.r, this.s, this.i, this.k);
            }
            return new C3686a4(this.g, AbstractC1639am.a(this.h.values()), this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.v, this.u, this.w, this.B, AbstractC2763pC.a(this.y), this.G);
        } catch (Throwable th) {
            this.e.c.c();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Map map, Collection collection, S2 s2, C3712e3 c3712e3, AbstractC0748t0 abstractC0748t0) {
        a(abstractC0748t0, collection, (Set) null, s2, a(abstractC0748t0.e(), map), c3712e3);
    }

    private void b(AbstractC0727q0 abstractC0727q0, final List list, final S2 s2, final C3712e3 c3712e3) {
        final Map map = null;
        abstractC0727q0.c(new Consumer() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(map, list, s2, c3712e3, (AbstractC0748t0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Map map, Collection collection, S2 s2, C3712e3 c3712e3, AbstractC0735r0 abstractC0735r0) {
        a(abstractC0735r0, collection, s2, a(abstractC0735r0.e(), map), c3712e3);
    }

    static AbstractC3714f b(AbstractC0727q0 abstractC0727q0, S2 s2) {
        return a(s2.b(), abstractC0727q0, abstractC0727q0.s0(), EnumC0608b0.c);
    }

    final boolean b(C3775o3 c3775o3, Iterable iterable) {
        if (!c3775o3.i().b()) {
            return false;
        }
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            if (c3775o3.a((AbstractC0748t0) it.next(), this.a, new d4$$ExternalSyntheticLambda23(this), this.A)) {
                return true;
            }
        }
        return false;
    }

    private void b() {
        com.android.tools.r8.utils.D3 d3D;
        final S1 s1A = this.g.a(X.d(), S1.a());
        for (C0729q2 c0729q2 : this.D) {
            Function function = new Function() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C3707d4.a(s1A, (n5) obj);
                }
            };
            if (c0729q2.b(function).g()) {
                d3D = c0729q2.d(function);
            } else {
                d3D = com.android.tools.r8.utils.D3.d();
            }
            if (d3D.g()) {
                a((InterfaceC0694k5) c0729q2);
            }
        }
    }

    private void c() {
        if (this.a.u().a(this.z.a.Z1)) {
            return;
        }
        this.C.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C3954q2) obj, (Set) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C1158Lj c1158Lj) {
        c1158Lj.a(this.g);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC0727q0 abstractC0727q0) {
        Set setC = this.d.c(abstractC0727q0.d);
        if (setC.isEmpty()) {
            return;
        }
        for (com.android.tools.r8.graph.W0 w0 : abstractC0727q0.P1()) {
            if (w0.q1()) {
                if (!H && w0.X1()) {
                    throw new AssertionError();
                }
            } else {
                C0684j2 reference = w0.getReference();
                Iterator it = setC.iterator();
                com.android.tools.r8.internal.Q3 q3 = null;
                com.android.tools.r8.internal.Q3 q4 = null;
                while (true) {
                    if (!it.hasNext()) {
                        q3 = q4;
                        break;
                    }
                    AbstractC0748t0 abstractC0748t0C = ((C0680j) this.a.f()).h(this.a.a().a((C0767v2) it.next(), reference.h, reference.f)).c();
                    if (abstractC0748t0C != null && abstractC0748t0C.a() != abstractC0727q0) {
                        com.android.tools.r8.internal.Q3 q3A = this.b.a(abstractC0748t0C);
                        if (q3A == null) {
                            break;
                        }
                        if (q4 != null) {
                            if (!q4.equals(q3A)) {
                                break;
                            }
                        } else {
                            q4 = q3A;
                        }
                    }
                }
                if (q3 != null) {
                    this.b.a(reference, q3);
                }
            }
        }
    }

    final W3 a() {
        return new W3(this.j, this.p, this.g, this.u, AbstractC2763pC.a(this.y), this.G);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C0729q2 a(com.android.tools.r8.graph.U0 u0, Map map) {
        boolean z;
        C0729q2 c0729q2 = null;
        if (map == null) {
            return null;
        }
        Iterator it = map.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            Map.Entry entry = (Map.Entry) it.next();
            if (((Predicate) entry.getKey()).test(u0)) {
                c0729q2 = (C0729q2) entry.getValue();
                z = true;
                break;
            }
        }
        if (H || z) {
            return c0729q2;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1, com.android.tools.r8.graph.W0 w0) {
        return w0.R0() || abstractC0727q0 == abstractC0727q1 || !(!w0.E0() || w0.O0() || w0.A1()) || this.z.S0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Map map, Collection collection, Set set, S2 s2, C3712e3 c3712e3, AbstractC0748t0 abstractC0748t0) {
        a(abstractC0748t0, collection, set, s2, a(abstractC0748t0.e(), map), c3712e3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Map map, Collection collection, S2 s2, C3712e3 c3712e3, AbstractC0748t0 abstractC0748t0) {
        a(abstractC0748t0, collection, (Set) null, s2, a(abstractC0748t0.e(), map), c3712e3);
    }

    private void a(AbstractC0727q0 abstractC0727q0, final List list, final S2 s2, final Map map, boolean z, final C3712e3 c3712e3) {
        while (abstractC0727q0 != null) {
            if (!z && abstractC0727q0.I1()) {
                return;
            }
            abstractC0727q0.a(new Consumer() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b(map, list, s2, c3712e3, (AbstractC0735r0) obj);
                }
            });
            C0767v2 c0767v2 = abstractC0727q0.f;
            abstractC0727q0 = c0767v2 == null ? null : this.e.c(c0767v2);
        }
    }

    private void a(AbstractC0727q0 abstractC0727q0, final List list, final S2 s2, final C3712e3 c3712e3) {
        final Map map = null;
        abstractC0727q0.a(new Consumer() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(map, list, s2, c3712e3, (AbstractC0735r0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Map map, Collection collection, S2 s2, C3712e3 c3712e3, AbstractC0735r0 abstractC0735r0) {
        a(abstractC0735r0, collection, s2, a(abstractC0735r0.e(), map), c3712e3);
    }

    public static void a(final C3733i c3733i, final PrintStream printStream, final Predicate predicate) {
        com.android.tools.r8.utils.A1 a1 = c3733i.b().d;
        c3733i.w().c(new Consumer() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3707d4.a(predicate, printStream, (C0767v2) obj);
            }
        }, a1);
        c3733i.w().a(new Consumer() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3707d4.a(predicate, printStream, (com.android.tools.r8.graph.Y0) obj);
            }
        }, a1);
        c3733i.w().b(new Consumer() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3707d4.a(predicate, c3733i, printStream, (C0684j2) obj);
            }
        }, a1);
        printStream.close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Predicate predicate, PrintStream printStream, C0767v2 c0767v2) {
        if (predicate.test(c0767v2)) {
            printStream.println(c0767v2.r0());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Predicate predicate, PrintStream printStream, com.android.tools.r8.graph.Y0 y0) {
        if (predicate.test(y0.e)) {
            printStream.println(y0.e.r0() + ": " + y0.h.r0() + " " + y0.f.r0());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Predicate predicate, C3733i c3733i, PrintStream printStream, C0684j2 c0684j2) {
        if (predicate.test(c0684j2.e)) {
            com.android.tools.r8.graph.W0 w0C = c0684j2.c((AbstractC0727q0) C0729q2.a(c3733i.a((AbstractC0642e2) c0684j2)));
            if (w0C == null) {
                if (!H && !c0684j2.b(c3733i.a().K5)) {
                    throw new AssertionError();
                }
                return;
            }
            printStream.print(c0684j2.e.r0() + ": ");
            boolean z = true;
            if (w0C.u1()) {
                printStream.print(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME);
            } else if (w0C.C1()) {
                String strR0 = c0684j2.e.r0();
                printStream.print(strR0.substring(strR0.lastIndexOf(46) + 1));
            } else {
                printStream.print(c0684j2.h.e.r0() + " " + c0684j2.f.r0());
            }
            printStream.print("(");
            C0767v2[] c0767v2Arr = c0684j2.h.f.a;
            int length = c0767v2Arr.length;
            int i = 0;
            while (i < length) {
                C0767v2 c0767v2 = c0767v2Arr[i];
                if (!z) {
                    printStream.print(",");
                }
                printStream.print(c0767v2.r0());
                i++;
                z = false;
            }
            printStream.println(Constant.AFTER_QUTO);
        }
    }

    private void a(AbstractC0727q0 abstractC0727q0, S2 s2, C3712e3 c3712e3) {
        AbstractC3714f abstractC3714fA;
        if (s2.d().a(abstractC0727q0) == s2.e() || !s2.a().a(abstractC0727q0.e) || !s2.k().b(abstractC0727q0.e) || (abstractC3714fA = a(s2.b(), abstractC0727q0, abstractC0727q0.s0(), EnumC0608b0.c)) == null) {
            return;
        }
        a(abstractC3714fA);
        if ((!s2.o() || c(abstractC0727q0, s2)) && s2.c().a(abstractC0727q0.d)) {
            List listJ = s2.j();
            if (s2 instanceof C3732h3) {
                if (abstractC0727q0.I1()) {
                    return;
                }
                int i = V3.a[((C3732h3) s2).H().ordinal()];
                if (i != 1) {
                    if (i == 2) {
                        for (C3775o3 c3775o3 : listJ) {
                            if (!b(c3775o3, abstractC0727q0.K0()) && !a(c3775o3, abstractC0727q0.J0())) {
                                return;
                            }
                        }
                    } else if (i != 3) {
                        if (i == 4) {
                            throw new C3405y10("-if rule will be evaluated separately, not here.");
                        }
                        return;
                    }
                    a(abstractC0727q0, s2, (C3775o3) null, (C0729q2) null, c3712e3);
                    HashMap map = new HashMap();
                    if (c3712e3 != null) {
                        map.put(new Predicate() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda25
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ((com.android.tools.r8.graph.O0) obj).F0();
                            }
                        }, null);
                        map.put(new Predicate() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda26
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return C3707d4.b((com.android.tools.r8.graph.O0) obj);
                            }
                        }, abstractC0727q0.a0());
                    } else {
                        map.put(CM.b(), null);
                    }
                    b(abstractC0727q0, listJ, s2, map, false, c3712e3);
                    a(abstractC0727q0, listJ, s2, (Map) map, false, c3712e3);
                    return;
                }
                Map mapB = AbstractC2178hm.b(new Predicate() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda27
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return C3707d4.a((com.android.tools.r8.graph.O0) obj);
                    }
                }, abstractC0727q0.a0());
                b(abstractC0727q0, listJ, s2, mapB, false, c3712e3);
                a(abstractC0727q0, listJ, s2, mapB, false, c3712e3);
                return;
            }
            boolean z = H;
            if (!z && c3712e3 != null) {
                throw new AssertionError();
            }
            if (!(s2 instanceof C3712e3)) {
                if (s2 instanceof C3820w2) {
                    a(abstractC0727q0, s2.r());
                    return;
                }
                if (s2 instanceof C3765n) {
                    a(abstractC0727q0, (C3765n) s2);
                    return;
                }
                if (s2 instanceof N3) {
                    a(abstractC0727q0, s2, (C3775o3) null, (C0729q2) null, c3712e3);
                    b(abstractC0727q0, listJ, s2, null, true, c3712e3);
                    a(abstractC0727q0, listJ, s2, (Map) null, true, c3712e3);
                    return;
                }
                if (s2 instanceof C3785q2) {
                    b(abstractC0727q0, listJ, s2, null, true, c3712e3);
                    c(abstractC0727q0, listJ, s2, c3712e3);
                    a(abstractC0727q0, listJ, s2, (Map) null, true, c3712e3);
                    return;
                }
                if (!(s2 instanceof C3795s2) && !(s2 instanceof C3808u2)) {
                    if (s2 instanceof W1) {
                        a(abstractC0727q0, listJ, s2, c3712e3);
                        return;
                    }
                    if (!(s2 instanceof R0) && !(s2 instanceof C3681a1) && !(s2 instanceof D1) && !(s2 instanceof C3683a2) && !(s2 instanceof C3711e2) && !(s2 instanceof C3726g2) && !(s2 instanceof C3736i2) && !(s2 instanceof B1) && !(s2 instanceof S3) && !(s2 instanceof F4)) {
                        if (!(s2 instanceof C3792s) && !(s2 instanceof C3746k2) && !(s2 instanceof C3763m2) && !(s2 instanceof Y1) && !(s2 instanceof Q3)) {
                            if (s2 instanceof R1) {
                                b(abstractC0727q0, listJ, s2, null, true, c3712e3);
                                a(abstractC0727q0, listJ, s2, (Map) null, true, c3712e3);
                                return;
                            } else if (s2 instanceof C3684a3) {
                                a(abstractC0727q0, listJ, s2, c3712e3);
                                b(abstractC0727q0, listJ, s2, c3712e3);
                                return;
                            } else {
                                if (!z && !(s2 instanceof C3816w)) {
                                    throw new AssertionError();
                                }
                                b(abstractC0727q0, listJ, s2, c3712e3);
                                return;
                            }
                        }
                        for (C3775o3 c3775o4 : listJ) {
                            if (!b(c3775o4, abstractC0727q0.K0()) && !a(c3775o4, abstractC0727q0.J0())) {
                                return;
                            }
                        }
                        a(abstractC0727q0, s2, (C3775o3) null, (C0729q2) null, c3712e3);
                        return;
                    }
                    b(abstractC0727q0, listJ, s2, c3712e3);
                    return;
                }
                if (this.b != null) {
                    b(abstractC0727q0, listJ, s2, null, true, c3712e3);
                    c(abstractC0727q0, listJ, s2, c3712e3);
                    a(abstractC0727q0, listJ, s2, (Map) null, true, c3712e3);
                    return;
                }
                return;
            }
            throw new C3405y10("-if rule will be evaluated separately, not here.");
        }
    }

    private boolean a(AbstractC0727q0 abstractC0727q0, S2 s2) {
        AbstractC0727q0 abstractC0727q0C;
        AbstractC3714f abstractC3714fA;
        if (abstractC0727q0 == null) {
            return false;
        }
        for (C0767v2 c0767v2 : abstractC0727q0.g.a) {
            AbstractC0727q0 abstractC0727q0C2 = this.e.c(c0767v2);
            if (abstractC0727q0C2 == null) {
                return false;
            }
            if (s2.g().a(this.a, c0767v2) && (abstractC3714fA = a(s2.f(), abstractC0727q0C2, abstractC0727q0C2.s0(), EnumC0608b0.c)) != null) {
                a(abstractC3714fA);
                return true;
            }
            if (a(abstractC0727q0C2, s2)) {
                return true;
            }
        }
        C0767v2 c0767v3 = abstractC0727q0.f;
        if (c0767v3 == null || (abstractC0727q0C = this.e.c(c0767v3)) == null) {
            return false;
        }
        return a(abstractC0727q0C, s2);
    }

    private boolean a(AbstractC0727q0 abstractC0727q0, S2 s2, final boolean z) {
        if (this.a.g0() != null) {
            Stream streamFilter = this.a.g0().b(abstractC0727q0.d).stream().filter(new Predicate() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda20
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.a(z, (C0767v2) obj);
                }
            });
            G3 g3G = s2.g();
            Objects.requireNonNull(g3G);
            if (streamFilter.anyMatch(new G3$$ExternalSyntheticLambda2(g3G))) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(boolean z, C0767v2 c0767v2) {
        return this.a.c(c0767v2).e.W() == z;
    }

    final boolean a(C3775o3 c3775o3, Iterable iterable) {
        if (!c3775o3.i().a()) {
            return false;
        }
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            if (c3775o3.a((AbstractC0735r0) it.next(), this.a, new d4$$ExternalSyntheticLambda23(this), this.A)) {
                return true;
            }
        }
        return false;
    }

    final boolean a(C3775o3 c3775o3, AbstractC0735r0 abstractC0735r0) {
        return c3775o3.a(abstractC0735r0, this.a, new Consumer() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3707d4.b((AbstractC3714f) obj);
            }
        }, this.A);
    }

    static boolean a(List list, AbstractC0741s0 abstractC0741s0, Consumer consumer) {
        AbstractC3714f abstractC3714fA = a(list, abstractC0741s0, abstractC0741s0.s(), abstractC0741s0.j0() ? EnumC0608b0.a : EnumC0608b0.b);
        if (abstractC3714fA != null) {
            consumer.accept(abstractC3714fA);
            return true;
        }
        if (abstractC0741s0.h0()) {
            AbstractC0748t0 abstractC0748t0C = abstractC0741s0.c();
            for (int i = 0; i < abstractC0748t0C.e().j1().size(); i++) {
                AbstractC3714f abstractC3714fA2 = a(list, abstractC0748t0C, abstractC0748t0C.e().j1().j(i), EnumC0608b0.d);
                if (abstractC3714fA2 != null) {
                    consumer.accept(abstractC3714fA2);
                    return true;
                }
            }
        }
        return false;
    }

    private static AbstractC3714f a(List list, InterfaceC0593a0 interfaceC0593a0, C0658g0 c0658g0, EnumC0608b0 enumC0608b0) {
        C0633d0 c0633d0;
        if (list.isEmpty()) {
            return C3696c.b();
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            G3 g3 = (G3) it.next();
            C0633d0[] c0633d0Arr = c0658g0.c;
            int length = c0633d0Arr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    c0633d0 = null;
                    break;
                }
                c0633d0 = c0633d0Arr[i];
                if (g3.b(c0633d0.t0())) {
                    break;
                }
                i++;
            }
            if (c0633d0 == null) {
                return null;
            }
            if (interfaceC0593a0.j()) {
                arrayList.add(new C3708e(interfaceC0593a0.i(), c0633d0, enumC0608b0));
            }
        }
        return new C3702d(arrayList);
    }

    private void a(AbstractC0748t0 abstractC0748t0, Collection collection, Set set, S2 s2, C0729q2 c0729q2, C3712e3 c3712e3) {
        if (set == null || !set.contains(C3894f2.a().c(abstractC0748t0.getReference()))) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                C3775o3 c3775o3 = (C3775o3) it.next();
                if (c3775o3.a(abstractC0748t0, this.a, new d4$$ExternalSyntheticLambda23(this), this.A)) {
                    if (set != null) {
                        set.add(C3894f2.a().c(abstractC0748t0.getReference()));
                    }
                    a(abstractC0748t0, s2, c3775o3, c0729q2, c3712e3);
                }
            }
        }
    }

    private void a(AbstractC0735r0 abstractC0735r0, Collection collection, S2 s2, C0729q2 c0729q2, C3712e3 c3712e3) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            C3775o3 c3775o3 = (C3775o3) it.next();
            if (c3775o3.a(abstractC0735r0, this.a, new d4$$ExternalSyntheticLambda23(this), this.A)) {
                a(abstractC0735r0, s2, c3775o3, c0729q2, c3712e3);
            }
        }
    }

    private void a(C0767v2 c0767v2, C3737i3 c3737i3, Y y) {
        C0729q2 c0729q2A;
        if (c0767v2.Y0()) {
            return;
        }
        if (c0767v2.M0()) {
            c0767v2 = c0767v2.b(this.a.a());
        }
        if (c0767v2.W0() || (c0729q2A = C0729q2.a(this.a.c(c0767v2))) == null) {
            return;
        }
        C3742j3 c3742j3G = c3737i3.G();
        if (this.a.Y().o1() && !c3742j3G.d) {
            AbstractC3716f1 abstractC3716f1A = this.g.a(y).a(c0729q2A.getReference());
            abstractC3716f1A.c.add(c3737i3);
            abstractC3716f1A.o().h();
        }
        if (!this.a.Y().k1() || c3742j3G.f) {
            return;
        }
        this.g.a(y).a(c0729q2A.getReference()).f().a().r();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(InterfaceC0593a0 interfaceC0593a0, S2 s2, C3775o3 c3775o3, C0729q2 c0729q2, C3712e3 c3712e3) {
        if (s2.A()) {
            if (!interfaceC0593a0.j()) {
            } else {
                a(interfaceC0593a0.i(), s2.t(), c0729q2, c3712e3);
            }
        } else if (s2 instanceof C3785q2) {
            this.v.put(interfaceC0593a0.getReference(), c3775o3);
            s2.D();
        } else if (s2 instanceof C3795s2) {
            a(interfaceC0593a0, (C3795s2) s2, c3775o3);
        } else if (s2 instanceof C3808u2) {
            a(interfaceC0593a0, (C3808u2) s2, c3775o3);
        } else if (s2 instanceof N3) {
            this.h.computeIfAbsent(interfaceC0593a0.getReference(), new Function() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda10
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C3707d4.a((AbstractC0744s2) obj);
                }
            });
            s2.D();
        } else if (s2.z()) {
            if (!H && !interfaceC0593a0.X()) {
                throw new AssertionError();
            }
            n5 n5VarQ = interfaceC0593a0.q();
            C3820w2 c3820w2R = s2.r();
            a(n5VarQ);
            c3820w2R.D();
        } else if (s2 instanceof R0) {
            if (interfaceC0593a0.h0()) {
                C0684j2 reference = interfaceC0593a0.c().getReference();
                int iOrdinal = ((R0) s2).H().ordinal();
                if (iOrdinal == 0) {
                    this.i.add(reference);
                } else if (iOrdinal == 1) {
                    this.g.a(interfaceC0593a0.getReference()).c().u();
                } else if (iOrdinal == 2) {
                    this.g.a(interfaceC0593a0.getReference()).c().r();
                } else {
                    if (iOrdinal != 3) {
                        throw new C3405y10();
                    }
                    this.j.add(reference);
                }
                s2.D();
            }
        } else if (s2 instanceof F4) {
            if (interfaceC0593a0.h0()) {
                this.l.add(interfaceC0593a0.c().getReference());
                s2.D();
            } else {
                throw new C3405y10();
            }
        } else if (s2.y()) {
            C3792s c3792sQ = s2.q();
            if (interfaceC0593a0.asClass() != null) {
                int iOrdinal2 = c3792sQ.H().ordinal();
                if (iOrdinal2 == 0) {
                    this.o.a(interfaceC0593a0.asClass().getType());
                } else if (iOrdinal2 == 1) {
                    this.p.add(interfaceC0593a0.asClass().getType());
                } else {
                    throw new C3405y10();
                }
                s2.D();
            } else {
                throw new IllegalStateException("Unexpected -" + c3792sQ.F() + " rule for a non-class type: `" + interfaceC0593a0.getReference().r0() + "`");
            }
        } else if (s2 instanceof W1) {
            if (!H && !interfaceC0593a0.l0()) {
                throw new AssertionError();
            }
            this.g.a(interfaceC0593a0.getReference()).b().r();
            s2.D();
        } else if (s2 instanceof C3746k2) {
            this.q.add(interfaceC0593a0.asClass().d);
            s2.D();
        } else if (s2 instanceof C3763m2) {
            this.r.add(interfaceC0593a0.asClass().d);
            s2.D();
        } else if (s2 instanceof Y1) {
            this.s.add(interfaceC0593a0.asClass().d);
            s2.D();
        } else if (s2 instanceof C3683a2) {
            if (!H && !interfaceC0593a0.k()) {
                throw new AssertionError();
            }
            this.g.a(interfaceC0593a0.getReference()).c().v();
            s2.D();
        } else if (s2 instanceof C3711e2) {
            if (!H && !interfaceC0593a0.k()) {
                throw new AssertionError();
            }
            this.g.a(interfaceC0593a0.getReference()).c().x();
            s2.D();
        } else if (s2 instanceof C3726g2) {
            if (!H && !interfaceC0593a0.k()) {
                throw new AssertionError();
            }
            this.g.a(interfaceC0593a0.getReference()).c().y();
            s2.D();
        } else if (s2 instanceof C3736i2) {
            if (!H && !interfaceC0593a0.k()) {
                throw new AssertionError();
            }
            this.g.a(interfaceC0593a0.getReference()).c().z();
            s2.D();
        } else if (s2 instanceof R1) {
            if (((R1) s2).H().ordinal() == 0) {
                if (interfaceC0593a0.j0()) {
                    AbstractC0735r0 abstractC0735r0D = interfaceC0593a0.d();
                    if (abstractC0735r0D.l0()) {
                        this.t.add(abstractC0735r0D.getReference());
                        s2.D();
                    }
                } else if (interfaceC0593a0.h0()) {
                    AbstractC0748t0 abstractC0748t0C = interfaceC0593a0.c();
                    if (abstractC0748t0C.k()) {
                        this.t.add(abstractC0748t0C.getReference());
                        s2.D();
                    }
                }
            } else {
                throw new C3405y10();
            }
        } else if (s2 instanceof C3684a3) {
            a(interfaceC0593a0, s2, c3712e3);
        } else if (s2 instanceof Q3) {
            C0729q2 c0729q2A0 = interfaceC0593a0.a0();
            if (c0729q2A0 != null && c0729q2A0.p1()) {
                int iB = AbstractC0403b.b(s2.u().H());
                if (iB == 0) {
                    this.m.add(c0729q2A0.Y0().getReference());
                } else if (iB == 1) {
                    this.n.add(c0729q2A0.Y0().getReference());
                } else {
                    throw new C3405y10();
                }
                s2.D();
            }
        } else if (s2.B()) {
            if (interfaceC0593a0.h0()) {
                AbstractC0748t0 abstractC0748t0C2 = interfaceC0593a0.c();
                int iB2 = AbstractC0403b.b(s2.v().H());
                if (iB2 == 0) {
                    this.m.add(abstractC0748t0C2.getReference());
                } else if (iB2 == 1) {
                    this.n.add(abstractC0748t0C2.getReference());
                } else {
                    throw new C3405y10();
                }
                s2.D();
            }
        } else if (s2 instanceof C3681a1) {
            if (!H && !interfaceC0593a0.k()) {
                throw new AssertionError();
            }
            this.g.a(interfaceC0593a0.getReference()).c().t();
            s2.D();
        } else if (s2 instanceof B1) {
            if (!H && !interfaceC0593a0.k()) {
                throw new AssertionError();
            }
            this.g.a(interfaceC0593a0.getReference()).c().A();
            s2.D();
        } else if (s2 instanceof D1) {
            if (!H && !interfaceC0593a0.k()) {
                throw new AssertionError();
            }
            this.g.a(interfaceC0593a0.getReference()).c().B();
            s2.D();
        } else if (s2 instanceof C3816w) {
            if (!H && !interfaceC0593a0.h0()) {
                throw new AssertionError();
            }
            com.android.tools.r8.ir.optimize.info.r rVar = this.E;
            AbstractC0748t0 abstractC0748t0C3 = interfaceC0593a0.c();
            rVar.getClass();
            com.android.tools.r8.ir.optimize.info.r.a(abstractC0748t0C3);
            if (interfaceC0593a0.k()) {
                this.g.a(interfaceC0593a0.getReference()).c().g();
            }
            s2.D();
        } else {
            throw new C3405y10();
        }
    }

    private void a(AbstractC0727q0 abstractC0727q0, C3820w2 c3820w2) {
        if (abstractC0727q0.e0()) {
            C0729q2 c0729q2A0 = abstractC0727q0.a0();
            C3820w2 c3820w2R = c3820w2.r();
            synchronized (this) {
                if (c3820w2R.j().isEmpty()) {
                    a(c0729q2A0, c3820w2R);
                } else if (c0729q2A0.b2() || c0729q2A0.c2()) {
                    a(c0729q2A0, c3820w2R.j(), c3820w2R, (C3712e3) null);
                    b(c0729q2A0, c3820w2R.j(), c3820w2R, null);
                    this.D.add(c0729q2A0);
                }
            }
            return;
        }
        this.a.a0().warning(new StringDiagnostic("The rule `" + c3820w2 + "` matches a class not in the program."));
    }

    private void a(C0729q2 c0729q2, C3820w2 c3820w2) {
        a((InterfaceC0694k5) c0729q2);
        c0729q2.k(new Consumer() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((n5) obj);
            }
        });
        c3820w2.D();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(InterfaceC0694k5 interfaceC0694k5) {
        this.g.a(X.d()).a(interfaceC0694k5.getReference()).p();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(S1 s1, n5 n5Var) {
        return com.android.tools.r8.utils.D3.a(s1.a(((AbstractC0741s0) n5Var).getReference(), new C$$ExternalSyntheticLambda0()));
    }

    private void a(InterfaceC0593a0 interfaceC0593a0, final C3795s2 c3795s2, final C3775o3 c3775o3) {
        boolean z = H;
        if (!z && this.b == null) {
            throw new AssertionError();
        }
        if (interfaceC0593a0.A()) {
            final AbstractC0741s0 abstractC0741s0O0 = interfaceC0593a0.o0();
            if (abstractC0741s0O0.t() == this.a.a().Z1) {
                if (!z && !abstractC0741s0O0.h0()) {
                    throw new AssertionError();
                }
                a(abstractC0741s0O0.c(), c3795s2);
            } else {
                final C0767v2 c0767v2 = (C0767v2) abstractC0741s0O0.getReference().a(new Y0$$ExternalSyntheticLambda4(), new d4$$ExternalSyntheticLambda3());
                this.b.a(c3775o3.l(), new Consumer() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(c3775o3, c0767v2, abstractC0741s0O0, c3795s2, (C3743k) obj);
                    }
                }).a(abstractC0741s0O0);
                if (abstractC0741s0O0.h0()) {
                    AbstractC0748t0 abstractC0748t0C = abstractC0741s0O0.c();
                    if (abstractC0748t0C.e().u1()) {
                        this.E.e(abstractC0748t0C.e());
                    }
                }
            }
            c3795s2.D();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C3775o3 c3775o3, C0767v2 c0767v2, AbstractC0741s0 abstractC0741s0, C3795s2 c3795s2, C3743k c3743k) {
        AbstractC1154Lf abstractC1154LfA = c3775o3.h().a(c0767v2);
        com.android.tools.r8.internal.B1 b1A = c3775o3.h().a(this.a, c0767v2);
        c3743k.a(abstractC0741s0, abstractC1154LfA).a(abstractC0741s0, b1A);
        a(c3795s2, c3775o3, b1A);
    }

    private void a(InterfaceC0593a0 interfaceC0593a0, C3808u2 c3808u2, C3775o3 c3775o3) {
        if (!H && this.b == null) {
            throw new AssertionError();
        }
        if (interfaceC0593a0.A() && c3775o3.l()) {
            AbstractC0741s0 abstractC0741s0O0 = interfaceC0593a0.o0();
            C0767v2 c0767v2 = (C0767v2) abstractC0741s0O0.getReference().a(new Y0$$ExternalSyntheticLambda4(), new d4$$ExternalSyntheticLambda3());
            AbstractC1154Lf abstractC1154LfA = c3775o3.h().a(c0767v2);
            com.android.tools.r8.internal.B1 b1A = c3775o3.h().a(this.a, c0767v2);
            this.b.a(abstractC0741s0O0, abstractC1154LfA).a(abstractC0741s0O0, b1A);
            a(c3808u2, c3775o3, b1A);
            c3808u2.D();
        }
    }

    private void a(AbstractC0727q0 abstractC0727q0, C3765n c3765n) {
        if (abstractC0727q0.e0()) {
            if (abstractC0727q0.B1()) {
                this.g.a().a((AbstractC0744s2) abstractC0727q0.getType()).a().s();
                return;
            }
            this.a.a0().warning(new StringDiagnostic("The rule `" + c3765n + "` matches the non-enum class " + abstractC0727q0.o1() + "."));
            return;
        }
        this.a.a0().warning(new StringDiagnostic("The rule `" + c3765n + "` matches the non-program class " + abstractC0727q0.o1() + "."));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.tools.r8.shaking.d4] */
    /* JADX WARN: Type inference failed for: r9v0, types: [com.android.tools.r8.shaking.e3] */
    /* JADX WARN: Type inference failed for: r9v1, types: [com.android.tools.r8.shaking.i3, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v2 */
    private void a(InterfaceC0694k5 interfaceC0694k5, C3732h3 c3732h3, C0729q2 c0729q2, C3712e3 c3712e3) {
        Y yD;
        if (interfaceC0694k5.j0()) {
            if (interfaceC0694k5.Q().e().X0().e()) {
                if (!H && c3712e3 == 0) {
                    throw new AssertionError();
                }
                return;
            }
        } else if (interfaceC0694k5.h0()) {
            o5 o5VarG0 = interfaceC0694k5.g0();
            if (o5VarG0.e().u1() && !this.z.W0) {
                return;
            }
            if (o5VarG0.B().e()) {
                if (!H && c3712e3 == 0) {
                    throw new AssertionError();
                }
                return;
            } else if (this.z.Y0()) {
                C0684j2 reference = o5VarG0.getReference();
                C0722p1 c0722p1A = this.a.a();
                if (reference.f == c0722p1A.N4 && reference.h == c0722p1A.P4) {
                    return;
                }
            }
        }
        if (c3712e3 == 0) {
            c3712e3 = c3732h3;
        }
        C3742j3 c3742j3G = c3732h3.G();
        if (c3742j3G.a && c3742j3G.b && c3742j3G.c && c3742j3G.f && c3742j3G.e && c3742j3G.d && !c3742j3G.g) {
            return;
        }
        if (this.z.S0 && !c3742j3G.d && c0729q2 != null && !interfaceC0694k5.isClass() && !interfaceC0694k5.getAccessFlags().o()) {
            ((Set) this.u.computeIfAbsent(c0729q2.getType(), new Function() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda15
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C3707d4.a((C0767v2) obj);
                }
            })).add(c3712e3);
            c3732h3.D();
        }
        if (c0729q2 != null) {
            if (!interfaceC0694k5.getAccessFlags().o() && (!interfaceC0694k5.h0() || !interfaceC0694k5.c().e().C1())) {
                yD = new U(c0729q2);
            } else {
                yD = new V(c0729q2);
            }
        } else {
            yD = X.d();
        }
        boolean z = this instanceof Z3;
        if (!z && this.z.Z0() && interfaceC0694k5.h0() && interfaceC0694k5.c().a().isInterface() && !interfaceC0694k5.c().e().u1() && interfaceC0694k5.c().e().q1()) {
            o5 o5VarC = interfaceC0694k5.c();
            o5 o5VarB = this.F.b(o5VarC, (InterfaceC1720bs) this.c);
            this.G.a(o5VarB, o5VarC);
            if (this.a.Y().l1() && !c3742j3G.e) {
                this.g.a(yD).a((AbstractC0744s2) o5VarB.getReference()).g();
                c3732h3.D();
            }
            if (this.a.Y().o1() && !c3742j3G.d) {
                AbstractC3716f1 abstractC3716f1A = this.g.a(yD).a((AbstractC0744s2) o5VarB.getReference());
                abstractC3716f1A.c.add(c3712e3);
                abstractC3716f1A.o().h();
                c3732h3.D();
            }
            if (!interfaceC0694k5.c().H()) {
                return;
            }
        }
        com.android.tools.r8.utils.A1 a1Y = this.a.Y();
        if (a1Y.D0() != null && a1Y.D0().A() && !c3742j3G.a) {
            this.g.a(yD).a(interfaceC0694k5.getReference()).d();
            c3732h3.D();
        }
        if (!this.a.Y().S0 && !c3742j3G.b) {
            this.g.a(yD).a(interfaceC0694k5.getReference()).e();
            c3732h3.D();
        }
        if (this.a.Y().a1()) {
            this.g.a(yD).a(interfaceC0694k5.getReference()).i();
            c3732h3.D();
        }
        if (this.a.Y().k1() && !c3742j3G.f) {
            this.g.a(yD).a(interfaceC0694k5.getReference()).f();
            c3732h3.D();
        }
        if (this.a.Y().n1()) {
            if (!c3742j3G.c ? true : AbstractC2237iT.a(interfaceC0694k5.b(), this.z)) {
                this.g.a(yD).a(interfaceC0694k5.getReference()).a(interfaceC0694k5.e0(), new Consumer() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda16
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C3707d4.a((AbstractC3716f1) obj);
                    }
                });
                c3732h3.D();
            }
        }
        if (this.a.Y().l1() && !c3742j3G.e) {
            this.g.a(yD).a(interfaceC0694k5.getReference()).g();
            c3732h3.D();
        }
        if ((this.a.Y().o1() || z) && !c3742j3G.d) {
            AbstractC3716f1 abstractC3716f1A2 = this.g.a(yD).a(interfaceC0694k5.getReference());
            abstractC3716f1A2.c.add(c3712e3);
            abstractC3716f1A2.o().h();
            c3732h3.D();
        }
        if (c3742j3G.g) {
            if (interfaceC0694k5.h0()) {
                o5 o5VarG1 = interfaceC0694k5.g0();
                a(o5VarG1.G(), c3712e3, yD);
                Iterator<C0767v2> it = o5VarG1.D().iterator();
                while (it.hasNext()) {
                    a(it.next(), c3712e3, yD);
                }
            } else if (interfaceC0694k5.j0()) {
                a(interfaceC0694k5.Q().getType(), c3712e3, yD);
            } else if (!H && !interfaceC0694k5.isClass()) {
                throw new AssertionError();
            }
            c3732h3.D();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(C0767v2 c0767v2) {
        return new HashSet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC3716f1 abstractC3716f1) {
        abstractC3716f1.a().r();
    }

    private void a(InterfaceC0593a0 interfaceC0593a0, S2 s2, C3712e3 c3712e3) {
        boolean z = H;
        if (!z && (this instanceof Z3)) {
            throw new AssertionError();
        }
        if (!z && c3712e3 != null) {
            throw new AssertionError();
        }
        if (interfaceC0593a0.isClass()) {
            return;
        }
        if (interfaceC0593a0.j0()) {
            AbstractC0735r0 abstractC0735r0D = interfaceC0593a0.d();
            if (abstractC0735r0D.e().a(this.a.a())) {
                this.a.a0().warning(new StringDiagnostic("Rule matches the static final field `" + abstractC0735r0D.w() + "`, which may have been inlined: " + s2.toString(), s2.l()));
            }
        }
        this.w.add(interfaceC0593a0.o0().getReference());
        s2.D();
    }

    private void a(AbstractC0748t0 abstractC0748t0, C3795s2 c3795s2) {
        if (!H && abstractC0748t0.t() != this.z.j0().Z1) {
            throw new AssertionError();
        }
        ((Set) this.C.computeIfAbsent(new C3954q2(c3795s2.l(), c3795s2.m()), new Function() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda24
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3707d4.a((C3954q2) obj);
            }
        })).add(abstractC0748t0.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(C3954q2 c3954q2) {
        return new TreeSet(new JV$$ExternalSyntheticLambda3());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(C0684j2 c0684j2) {
        C0759u2 c0759u2 = c0684j2.f;
        C0722p1 c0722p1 = this.z.a;
        return c0759u2 == c0722p1.L || c0759u2 == c0722p1.M || c0759u2 == c0722p1.N;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C3954q2 c3954q2, Set set) {
        if (set.stream().anyMatch(new Predicate() { // from class: com.android.tools.r8.shaking.d4$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((C0684j2) obj);
            }
        })) {
            this.z.e.warning(new com.android.tools.r8.errors.a().a(set).a(c3954q2.a()).a(c3954q2.b()).a());
        }
    }

    private void a(S2 s2, C3775o3 c3775o3, com.android.tools.r8.internal.B1 b1) {
        if (c3775o3.l() && c3775o3.h().g()) {
            if (!H && !b1.B() && !b1.isUnknown()) {
                throw new AssertionError();
            }
            if (b1.isUnknown()) {
                C3786q3 c3786q3H = c3775o3.h();
                this.z.e.warning(new com.android.tools.r8.errors.b().a(c3786q3H.c(), c3786q3H.b()).a(s2.l()).a(s2.m()).a());
            }
        }
    }
}
