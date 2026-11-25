package com.android.tools.r8.shaking;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.dex.code.InterfaceC0419c;
import com.android.tools.r8.errors.InterfaceDesugarMissingTypeDiagnostic;
import com.android.tools.r8.errors.d$$ExternalSyntheticLambda0;
import com.android.tools.r8.experimental.graphinfo.GraphConsumer;
import com.android.tools.r8.graph.AbstractC0614b5;
import com.android.tools.r8.graph.AbstractC0629c3;
import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.AbstractC0701l4;
import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0606a5;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0635d2;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0662g4;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0746s4;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0757u1;
import com.android.tools.r8.graph.C0762u4;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0792w4;
import com.android.tools.r8.graph.C0801x3;
import com.android.tools.r8.graph.EnumC0608b0;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.graph.I5;
import com.android.tools.r8.graph.InterfaceC0593a0;
import com.android.tools.r8.graph.InterfaceC0669h4;
import com.android.tools.r8.graph.InterfaceC0679i5;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.InterfaceC0707m4;
import com.android.tools.r8.graph.X4;
import com.android.tools.r8.graph.l5;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.graph.q2$$ExternalSyntheticLambda28;
import com.android.tools.r8.internal.AG;
import com.android.tools.r8.internal.AN;
import com.android.tools.r8.internal.AbstractC0833Al;
import com.android.tools.r8.internal.AbstractC0901Cl;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1231Oc;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2151hN;
import com.android.tools.r8.internal.AbstractC2360k8;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC2591n8;
import com.android.tools.r8.internal.AbstractC2797pg;
import com.android.tools.r8.internal.B7;
import com.android.tools.r8.internal.BB;
import com.android.tools.r8.internal.C0831Aj;
import com.android.tools.r8.internal.C1096Jc;
import com.android.tools.r8.internal.C1121Ka;
import com.android.tools.r8.internal.C1132Kj;
import com.android.tools.r8.internal.C1158Lj;
import com.android.tools.r8.internal.C1268Pj;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C1385Ts;
import com.android.tools.r8.internal.C1393Tz;
import com.android.tools.r8.internal.C1721bt;
import com.android.tools.r8.internal.C1736c3;
import com.android.tools.r8.internal.C1952es;
import com.android.tools.r8.internal.C2051g8;
import com.android.tools.r8.internal.C2115gt;
import com.android.tools.r8.internal.C2173hh;
import com.android.tools.r8.internal.C2185ht;
import com.android.tools.r8.internal.C2458lN;
import com.android.tools.r8.internal.C2569ms;
import com.android.tools.r8.internal.C2720of;
import com.android.tools.r8.internal.C2769pI;
import com.android.tools.r8.internal.C2893r2;
import com.android.tools.r8.internal.C3124uN;
import com.android.tools.r8.internal.C3126uP;
import com.android.tools.r8.internal.C3208vP;
import com.android.tools.r8.internal.C3378xe;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.C3431yN;
import com.android.tools.r8.internal.C3446yc;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.CN;
import com.android.tools.r8.internal.D7;
import com.android.tools.r8.internal.F7;
import com.android.tools.r8.internal.G7;
import com.android.tools.r8.internal.HA;
import com.android.tools.r8.internal.InterfaceC1720bs;
import com.android.tools.r8.internal.InterfaceC2488lo;
import com.android.tools.r8.internal.InterfaceC2866qg;
import com.android.tools.r8.internal.InterfaceC2938rg;
import com.android.tools.r8.internal.InterfaceC3007sg;
import com.android.tools.r8.internal.InterfaceC3077tg;
import com.android.tools.r8.internal.JA;
import com.android.tools.r8.internal.M7;
import com.android.tools.r8.internal.N2$$ExternalSyntheticLambda12;
import com.android.tools.r8.internal.O2$$ExternalSyntheticLambda4;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.PM$$ExternalSyntheticLambda6;
import com.android.tools.r8.internal.RD;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.nH$$ExternalSyntheticLambda1;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.synthesis.C3842a;
import com.android.tools.r8.utils.AbstractC3920k0;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3888e1;
import com.android.tools.r8.utils.C3984w2;
import com.android.tools.r8.utils.InterfaceC3880d;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.StringDiagnostic;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class L {
    static final /* synthetic */ boolean q0 = true;
    private final N A;
    private final N C;
    private final N D;
    private final N E;
    private final T1 I;
    private Set K;
    private final K N;
    private final Set O;
    private final Set P;
    private final K S;
    private final J T;
    private F0 U;
    private final J2 V;
    private final boolean a;
    private final a b;
    private HashMap f0;
    private final M0 h0;
    private C0680j i;
    private final D7 i0;
    private final C0702m j;
    private final P k;
    private final ExecutorService l;
    private B5 m;
    private final C2569ms m0;
    private final com.android.tools.r8.utils.A1 n;
    private C3686a4 o;
    private final AbstractC2151hN o0;
    private final InterfaceC3697c0 p;
    private C3720g q;
    private final C0606a5 u;
    private final LinkedHashSet c = new LinkedHashSet();
    private final LinkedHashSet d = new LinkedHashSet();
    private final LinkedHashSet e = new LinkedHashSet();
    private final LinkedHashSet f = new LinkedHashSet();
    private final LinkedHashSet g = new LinkedHashSet();
    private final LinkedHashSet h = new LinkedHashSet();
    private final G r = new G(this);
    private final com.android.tools.r8.graph.T2 s = new com.android.tools.r8.graph.T2();
    private final C0746s4 t = C0762u4.b();
    private final IdentityHashMap v = new IdentityHashMap();
    private final Set w = OU.c();
    private final IdentityHashMap x = RD.b();
    private final IdentityHashMap y = RD.b();
    private final IdentityHashMap z = new IdentityHashMap();
    private final Set B = OU.c();
    private final Set F = OU.c();
    private final Set G = OU.c();
    private final Set H = OU.c();
    private Set J = OU.c();
    private final Set L = OU.c();
    private final IdentityHashMap M = new IdentityHashMap();
    private final Set Q = OU.c();
    private final Set R = OU.c();
    private final BB W = CN.d();
    private final IdentityHashMap X = new IdentityHashMap();
    private final C3730h1 Y = new C3730h1();
    private final A Z = new A();
    private final Set a0 = OU.c();
    private final IdentityHashMap b0 = new IdentityHashMap();
    private final Set c0 = OU.c();
    private final IdentityHashMap d0 = new IdentityHashMap();
    private final IdentityHashMap e0 = new IdentityHashMap();
    private final IdentityHashMap g0 = new IdentityHashMap();
    private final CN j0 = CN.b();
    private final CN k0 = CN.b();
    private final AN l0 = AN.d();
    private final Thread n0 = Thread.currentThread();
    private final LinkedHashMap p0 = new LinkedHashMap();

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class a {
        public static final a a = new a("INITIAL_TREE_SHAKING", 0);
        public static final a b = new a("FINAL_TREE_SHAKING", 1);
        public static final a c = new a("INITIAL_MAIN_DEX_TRACING", 2);
        public static final a d = new a("FINAL_MAIN_DEX_TRACING", 3);
        public static final a e = new a("GENERATE_MAIN_DEX_LIST", 4);
        public static final a f = new a("WHY_ARE_YOU_KEEPING", 5);

        private a(String str, int i) {
            super(str, i);
        }

        public final boolean a() {
            return this == d;
        }

        public final boolean b() {
            return this == b;
        }

        public final boolean c() {
            return this == a;
        }

        public final boolean d() {
            return this == c || a() || this == e;
        }

        public final boolean e() {
            return c() || b();
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$sUZQVfQ4XNh1oZg9vIcD2TcdV-o, reason: not valid java name */
    public static /* synthetic */ HashMap m2124$r8$lambda$sUZQVfQ4XNh1oZg9vIcD2TcdVo() {
        return new HashMap();
    }

    /* JADX INFO: renamed from: $r8$lambda$wBZ5N9SAmxJLRX-vx8hIUeo_fgc, reason: not valid java name */
    public static /* synthetic */ ArrayList m2128$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc() {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: -$$Nest$mb, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m2148$$Nest$mb(L l, AbstractC0727q0 abstractC0727q0, l5 l5Var) {
        l.b(abstractC0727q0, l5Var);
    }

    L(C0702m c0702m, AbstractC2151hN abstractC2151hN, ExecutorService executorService, B5 b5, GraphConsumer graphConsumer, a aVar) throws Throwable {
        this.A = new N();
        this.C = new N();
        this.D = new N();
        this.E = new N();
        if (!q0 && c0702m.j() == null) {
            throw new AssertionError();
        }
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        this.i = (C0680j) c0702m.f();
        this.j = c0702m.h0();
        this.o0 = abstractC2151hN;
        this.k = P.a(c0702m, this, aVar);
        this.l = executorService;
        this.m = b5;
        this.a = a1Y.S0;
        final M0 m0 = new M0(c0702m, graphConsumer);
        this.h0 = m0;
        this.I = ((C0680j) c0702m.f()).q().a();
        this.b = aVar;
        this.n = a1Y;
        this.p = d();
        this.U = F0.a(this);
        this.V = (aVar.c() && a1Y.S0) ? K2.a() : null;
        if (aVar.e()) {
            C1268Pj.a(c0702m, this);
            C2185ht.a(c0702m, this);
            if (a1Y.x1().b) {
                b(new C3126uP(c0702m));
            }
            c0702m.c(new InterfaceC3905h3() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda100
                @Override // com.android.tools.r8.utils.InterfaceC3905h3
                public final void accept(Object obj) {
                    this.f$0.a((C1132Kj) obj);
                }
            });
        }
        this.N = new K(this, new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda101
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                m0.a((com.android.tools.r8.graph.W0) obj, (AbstractC3837z1) obj2);
            }
        });
        this.O = com.android.tools.r8.utils.T2.b(2);
        this.P = com.android.tools.r8.utils.T2.b(0);
        this.S = new K(this, new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda101
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                m0.a((com.android.tools.r8.graph.W0) obj, (AbstractC3837z1) obj2);
            }
        });
        this.T = new J(this, new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda102
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                m0.a((com.android.tools.r8.graph.T0) obj, (AbstractC3837z1) obj2);
            }
        });
        if (aVar.c()) {
            this.i0 = D7.a(c0702m, c0702m.d());
            this.m0 = C2569ms.a(c0702m);
        } else {
            this.i0 = D7.a();
            this.m0 = null;
        }
        this.u = AbstractC0614b5.a(aVar.c(), m0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC0681j0 a(AbstractC0681j0 abstractC0681j0) {
        return abstractC0681j0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC3837z1 a(L0 l0) {
        return l0;
    }

    private void a(final o5 o5Var, AbstractC1305Qs abstractC1305Qs) {
        List listH1;
        C0729q2 c0729q2A;
        if (!(abstractC1305Qs instanceof C1721bt)) {
            if (!q0) {
                throw new AssertionError();
            }
            return;
        }
        S10 s10 = (S10) ((ArrayList) abstractC1305Qs.J2()).get(1);
        if (s10.i()) {
            return;
        }
        C1385Ts c1385TsE0 = s10.b.e0();
        AG agR0 = s10.b.r0();
        if (c1385TsE0 != null) {
            listH1 = c1385TsE0.h1();
        } else {
            if (agR0 == null) {
                return;
            }
            ArrayList arrayList = new ArrayList(s10.h0().size());
            Iterator<AbstractC2412ko> it = s10.h0().iterator();
            while (it.hasNext()) {
                C1736c3 c1736c3V = it.next().v();
                if (c1736c3V != null) {
                    arrayList.add(c1736c3V.value());
                }
            }
            listH1 = arrayList;
        }
        com.android.tools.r8.utils.O3 o3E = com.android.tools.r8.utils.O3.e();
        Iterator it2 = listH1.iterator();
        while (it2.hasNext()) {
            C0767v2 c0767v2A = AbstractC1231Oc.a(this.j, (S10) it2.next());
            if (c0767v2A != null && c0767v2A.O0() && (c0729q2A = C0729q2.a(b(c0767v2A, (InterfaceC0694k5) o5Var))) != null && c0729q2A.isInterface()) {
                b(c0729q2A, this.h0.a(c0729q2A, AbstractC3837z1.g(o5Var)));
                o3E.c(c0729q2A);
            }
        }
        while (o3E.c()) {
            C0729q2 c0729q2 = (C0729q2) o3E.f();
            if (!q0 && !c0729q2.isInterface()) {
                throw new AssertionError();
            }
            this.Y.a(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda53
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    L.a((X0) obj);
                }
            }, c0729q2);
            if (this.b.c()) {
                final C3824x1 c3824x1G = AbstractC3837z1.g(o5Var);
                c0729q2.h(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda64
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(o5Var, c3824x1G, (o5) obj);
                    }
                }, CM.b());
            }
            Iterator<C0767v2> it3 = c0729q2.f1().iterator();
            while (it3.hasNext()) {
                C0729q2 c0729q2A2 = C0729q2.a(a(it3.next(), (InterfaceC0694k5) c0729q2));
                if (c0729q2A2 != null && c0729q2A2.isInterface()) {
                    o3E.c(c0729q2A2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC0681j0 b(AbstractC0681j0 abstractC0681j0) {
        return abstractC0681j0;
    }

    private InterfaceC3697c0 d() {
        if (!this.b.b()) {
            return new InterfaceC3697c0() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda13
                @Override // com.android.tools.r8.shaking.InterfaceC3697c0
                public final C3822x a(C0702m c0702m, o5 o5Var, L l, com.android.tools.r8.androidapi.b bVar) {
                    return new C3822x(c0702m, o5Var, l, bVar);
                }
            };
        }
        return (InterfaceC3697c0) this.j.b(new InterfaceC3697c0() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda13
            @Override // com.android.tools.r8.shaking.InterfaceC3697c0
            public final C3822x a(C0702m c0702m, o5 o5Var, L l, com.android.tools.r8.androidapi.b bVar) {
                return new C3822x(c0702m, o5Var, l, bVar);
            }
        }, new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return L.a((C1158Lj) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public void b(AbstractC3837z1 abstractC3837z1, final o5 o5Var) {
        if (this.N.a(abstractC3837z1, o5Var)) {
            K k = this.S;
            k.getClass();
            if (!k.a.contains(o5Var.e())) {
                g(o5Var);
            }
            if (this.a && !o5Var.getAccessFlags().W() && o5Var.a().isInterface()) {
                F0 f0 = this.U;
                this.h0.getClass();
                f0.a(o5Var, o5Var, M0.b());
            }
            this.c.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda46
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(o5Var, (AbstractC2797pg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f(C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = b().c(c0767v2);
        if (abstractC0727q0C == null) {
            this.I.b(c0767v2);
            return;
        }
        if (abstractC0727q0C.I1()) {
            com.android.tools.r8.graph.T tN = abstractC0727q0C.N();
            L$$ExternalSyntheticLambda123 l$$ExternalSyntheticLambda123 = new L$$ExternalSyntheticLambda123(this);
            com.android.tools.r8.utils.O3 o3A = com.android.tools.r8.utils.O3.a(this.F, tN);
            while (o3A.c()) {
                a((com.android.tools.r8.graph.T) o3A.f(), o3A, (BiConsumer) l$$ExternalSyntheticLambda123, true);
            }
        }
    }

    private void n() {
        this.i0.a(new PM$$ExternalSyntheticLambda6());
        O o = new O(this.j.p());
        if (!q0 && !this.U.b.isEmpty()) {
            throw new AssertionError();
        }
        AbstractC2591n8 abstractC2591n8A = AbstractC2591n8.a(this.j, o, this.o0, this.i0, new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda112
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C0729q2) obj, (C0767v2) obj2);
            }
        });
        D7 d7 = this.i0;
        final K k = this.S;
        Objects.requireNonNull(k);
        C1952es c1952esA = d7.a(new Predicate() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda113
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return k.a((o5) obj);
            }
        }, this.m0);
        C0702m c0702m = this.j;
        final K k2 = this.S;
        Objects.requireNonNull(k2);
        AbstractC2360k8.a(c0702m, c1952esA, new Predicate() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda113
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return k2.a((o5) obj);
            }
        }).a(this.A.a, abstractC2591n8A, this.l);
        if (o.b()) {
            return;
        }
        C0680j c0680jA = this.i.a(new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda114
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return L.a((AbstractC0681j0) obj);
            }
        });
        this.i = c0680jA;
        this.j.d(c0680jA);
        this.m = B5.a((C0702m<? extends C0680j>) this.j);
        o.a(this);
        this.U = this.U.b();
        while (!this.U.b.isEmpty()) {
            ((AbstractC3709e0) this.U.b.poll()).a(this);
        }
    }

    private void o() {
        if (this.b.c()) {
            O o = new O(this.j.p());
            a(o);
            for (C3835z c3835z : this.p0.values()) {
                o5 o5VarB = c3835z.b();
                o5VarB.a().b(o5VarB.e());
                this.o0.a(o5VarB, c3835z.c());
            }
            this.p0.clear();
            if (o.b()) {
                return;
            }
            C0680j c0680jA = this.i.a(new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda80
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return L.b((AbstractC0681j0) obj);
                }
            });
            this.i = c0680jA;
            this.j.d(c0680jA);
            this.m = B5.a((C0702m<? extends C0680j>) this.j);
            o.a(this);
        }
    }

    public final void c(Set set) {
        if (!q0 && !this.b.b()) {
            throw new AssertionError();
        }
        this.K = set;
    }

    public final M0 g() {
        return this.h0;
    }

    public final C3730h1 h() {
        return this.Y;
    }

    public final a i() {
        return this.b;
    }

    public final C0606a5 j() {
        return this.u;
    }

    public final AbstractC2151hN k() {
        return this.o0;
    }

    public final InterfaceC3697c0 l() {
        return this.p;
    }

    public final F0 m() {
        return this.U;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0680j b() {
        return (C0680j) this.j.f();
    }

    private boolean j(C0729q2 c0729q2) {
        o5 o5VarX1 = c0729q2.X1();
        L0 l0C = this.h0.c(c0729q2, o5VarX1);
        if (!this.E.a(c0729q2, l0C)) {
            return false;
        }
        if (o5VarX1 != null && o5VarX1.e().i1().A() && c0729q2.Y().e(new q2$$ExternalSyntheticLambda28())) {
            if (this.D.a.contains(c0729q2)) {
                if (!q0) {
                    K k = this.S;
                    k.getClass();
                    if (!k.a.contains(o5VarX1.e())) {
                        throw new AssertionError();
                    }
                }
                return true;
            }
            c(l0C, o5VarX1);
        }
        return true;
    }

    private void k(C0729q2 c0729q2) {
        com.android.tools.r8.utils.O3 o3B = com.android.tools.r8.utils.O3.b(this.B);
        if (!c0729q2.getAccessFlags().W()) {
            do {
                a(new U(c0729q2));
                Iterator<C0767v2> it = c0729q2.f1().iterator();
                while (it.hasNext()) {
                    C0729q2 c0729q2A = C0729q2.a(a(it.next(), (InterfaceC0694k5) c0729q2));
                    if (c0729q2A != null) {
                        o3B.c(c0729q2A);
                    }
                }
                C0767v2 c0767v2 = c0729q2.f;
                c0729q2 = c0767v2 != null ? C0729q2.a(this.j.c(c0767v2)) : null;
                if (c0729q2 == null) {
                    break;
                }
            } while (!this.u.c(c0729q2));
        } else {
            o3B.c(c0729q2);
            break;
        }
        while (o3B.c()) {
            C0729q2 c0729q3 = (C0729q2) o3B.f();
            a(new U(c0729q3));
            Iterator<C0767v2> it2 = c0729q3.f1().iterator();
            while (it2.hasNext()) {
                C0729q2 c0729q2A2 = C0729q2.a(a(it2.next(), (InterfaceC0694k5) c0729q3));
                if (c0729q2A2 != null) {
                    o3B.c(c0729q2A2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void l(C0729q2 c0729q2) {
        if (!c0729q2.isInterface()) {
            if (!q0 && this.M.containsKey(c0729q2)) {
                throw new AssertionError();
            }
        } else {
            Set set = (Set) this.M.remove(c0729q2);
            if (set != null) {
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    a(c0729q2, (InterfaceC0694k5) it.next());
                }
            }
        }
    }

    private void m(C0729q2 c0729q2) {
        AbstractC0727q0 abstractC0727q0A;
        C0767v2 c0767v2 = c0729q2.f;
        if (c0767v2 != null && (abstractC0727q0A = a(c0767v2, (InterfaceC0694k5) c0729q2)) != null && abstractC0727q0A.isInterface()) {
            this.n.e.warning(new StringDiagnostic("Class " + c0729q2.r0() + " extends " + abstractC0727q0A.r0() + " which is an interface"));
        }
        for (C0767v2 c0767v3 : c0729q2.g.a) {
            AbstractC0727q0 abstractC0727q0A2 = a(c0767v3, (InterfaceC0694k5) c0729q2);
            if (abstractC0727q0A2 != null && !abstractC0727q0A2.isInterface()) {
                this.n.e.warning(new StringDiagnostic("Class " + c0729q2.r0() + " implements " + abstractC0727q0A2.r0() + " which is not an interface"));
            }
        }
    }

    final void g(final C0767v2 c0767v2, final o5 o5Var) {
        if (a(c0767v2, o5Var, new InterfaceC3880d() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda159
            @Override // com.android.tools.r8.utils.InterfaceC3880d
            public final void a() {
                this.f$0.c(c0767v2, o5Var);
            }
        })) {
            a(c0767v2);
            return;
        }
        V0 v0 = V0.c;
        C3778p1 c3778p1B = AbstractC3837z1.b(o5Var);
        C0729q2 c0729q2C = c(c0767v2, (InterfaceC0694k5) o5Var);
        if (c0729q2C != null) {
            if (c0729q2C.e.T() || c0729q2C.isInterface()) {
                c(c0729q2C, this.h0.a(c0729q2C, c3778p1B));
            } else {
                this.U.a(c0729q2C, o5Var, v0, c3778p1B);
            }
        }
    }

    final void h(final C0767v2 c0767v2, final o5 o5Var) {
        this.h.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda116
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0767v2 c0767v3 = c0767v2;
                o5 o5Var2 = o5Var;
                ((InterfaceC2866qg) obj).getClass();
            }
        });
        a(c0767v2, o5Var, true);
    }

    final void i(C0767v2 c0767v2, o5 o5Var) {
        d(c0767v2, (InterfaceC0694k5) o5Var);
    }

    private void c(C0767v2 c0767v2, l5 l5Var, BiConsumer biConsumer, BiConsumer biConsumer2) {
        if (c0767v2 == null) {
            return;
        }
        if (c0767v2.M0()) {
            c0767v2 = c0767v2.b(this.j.a());
        }
        if (c0767v2.O0()) {
            a(c0767v2, l5Var, biConsumer, biConsumer2);
        }
    }

    private void i(C0729q2 c0729q2) {
        o5 o5VarX1 = c0729q2.X1();
        L0 l0C = this.h0.c(c0729q2, o5VarX1);
        if (this.D.a(c0729q2, l0C) && o5VarX1 != null && o5VarX1.e().i1().A()) {
            if (this.E.a.contains(c0729q2) && c0729q2.Y().e(new q2$$ExternalSyntheticLambda28())) {
                if (q0) {
                    return;
                }
                K k = this.S;
                k.getClass();
                if (!k.a.contains(o5VarX1.e())) {
                    throw new AssertionError();
                }
                return;
            }
            c(l0C, o5VarX1);
        }
    }

    public final void b(AbstractC2797pg abstractC2797pg) {
        this.c.add(abstractC2797pg);
    }

    public final void b(Set set) {
        if (!q0 && !this.b.b()) {
            throw new AssertionError();
        }
        this.J = set;
    }

    final void h(o5 o5Var) {
        if (this.b.b()) {
            this.c0.add(o5Var.getReference());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C0767v2 c0767v2, l5 l5Var) {
        c(c0767v2, l5Var, new L$$ExternalSyntheticLambda32(this), new L$$ExternalSyntheticLambda42(this));
    }

    final void d(final C0767v2 c0767v2, final o5 o5Var) {
        this.g.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda63
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                L.a(c0767v2, o5Var, (InterfaceC2938rg) obj);
            }
        });
        d(c0767v2, (InterfaceC0694k5) o5Var);
    }

    final void h(C0684j2 c0684j2, o5 o5Var) {
        d(c0684j2, o5Var, AbstractC3837z1.c(o5Var));
    }

    private com.android.tools.r8.graph.O b(C0767v2 c0767v2, final l5 l5Var, final BiConsumer biConsumer, BiConsumer biConsumer2) {
        com.android.tools.r8.graph.O oF = b().f(c0767v2);
        if (oF.T()) {
            oF.b(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda107
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    biConsumer.accept((AbstractC0727q0) obj, l5Var);
                }
            });
        } else {
            biConsumer2.accept(c0767v2, l5Var);
        }
        return oF;
    }

    /* JADX WARN: Code duplicated, block: B:24:0x005d  */
    /* JADX WARN: Code duplicated, block: B:37:0x0069 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:39:0x0057 A[SYNTHETIC] */
    final void h(C0729q2 c0729q2) {
        Iterator it;
        C0729q2 c0729q2C;
        if (c0729q2.isInterface()) {
            i(c0729q2);
            return;
        }
        com.android.tools.r8.utils.O3 o3H = com.android.tools.r8.utils.O3.h(c0729q2);
        while (o3H.c()) {
            C0729q2 c0729q3 = (C0729q2) o3H.f();
            if (c0729q3.isInterface()) {
                if (j(c0729q3)) {
                    it = c0729q3.H0().iterator();
                    while (it.hasNext()) {
                        c0729q2C = c((C0767v2) it.next(), (InterfaceC0694k5) c0729q3);
                        if (c0729q2C != null) {
                            o3H.c(c0729q2C);
                        }
                    }
                }
            } else {
                o5 o5VarX1 = c0729q3.X1();
                L0 l0C = this.h0.c(c0729q3, o5VarX1);
                if (this.C.a(c0729q3, l0C)) {
                    if (o5VarX1 != null && o5VarX1.e().i1().A()) {
                        c(l0C, o5VarX1);
                    }
                    it = c0729q3.H0().iterator();
                    while (it.hasNext()) {
                        c0729q2C = c((C0767v2) it.next(), (InterfaceC0694k5) c0729q3);
                        if (c0729q2C != null) {
                            o3H.c(c0729q2C);
                        }
                    }
                }
            }
        }
    }

    final void d(final C0684j2 c0684j2, final o5 o5Var) {
        if (a(c0684j2.e, o5Var, new InterfaceC3880d() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda97
            @Override // com.android.tools.r8.utils.InterfaceC3880d
            public final void a() {
                this.f$0.b(c0684j2, o5Var);
            }
        })) {
            a(c0684j2.e);
        } else {
            a(c0684j2, o5Var, AbstractC3837z1.c(o5Var));
        }
    }

    public final boolean c(C0729q2 c0729q2) {
        com.android.tools.r8.graph.O oB = b(c0729q2.d, c0729q2, new L$$ExternalSyntheticLambda32(this), new L$$ExternalSyntheticLambda42(this));
        boolean z = q0;
        if (!z && !oB.T()) {
            throw new AssertionError();
        }
        AbstractC0727q0 abstractC0727q0G = oB.G();
        if (z || abstractC0727q0G == null || abstractC0727q0G.f0()) {
            return abstractC0727q0G != null;
        }
        throw new AssertionError();
    }

    public final com.android.tools.r8.graph.T2 f() {
        return this.s;
    }

    final void f(final C0767v2 c0767v2, final o5 o5Var) {
        this.f.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda103
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                L.a(c0767v2, o5Var, (InterfaceC3077tg) obj);
            }
        });
        d(c0767v2, (InterfaceC0694k5) o5Var);
    }

    public final Y0 b(C0729q2 c0729q2) {
        return this.Y.a(c0729q2);
    }

    public final C3704d1 b(m5 m5Var) {
        C3730h1 c3730h1 = this.Y;
        c3730h1.getClass();
        return c3730h1.a(m5Var.e(), m5Var.a());
    }

    final void f(C0684j2 c0684j2, o5 o5Var) {
        c(c0684j2, o5Var, AbstractC3837z1.c(o5Var));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3431yN f(C0729q2 c0729q2) {
        return C3431yN.a();
    }

    private AbstractC0727q0 b(C0767v2 c0767v2, InterfaceC0694k5 interfaceC0694k5) {
        return a(c0767v2, interfaceC0694k5, new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda55
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.c((AbstractC0727q0) obj, (l5) obj2);
            }
        }, new L$$ExternalSyntheticLambda56(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public void a(C0767v2 c0767v2, InterfaceC0694k5 interfaceC0694k5) {
        c(c0767v2, interfaceC0694k5, new L$$ExternalSyntheticLambda32(this), new L$$ExternalSyntheticLambda42(this));
    }

    private void f(AbstractC3837z1 abstractC3837z1, o5 o5Var) {
        if (!q0 && o5Var.e().s1()) {
            abstractC3837z1.getClass();
            if (!(abstractC3837z1 instanceof C3824x1)) {
                throw new AssertionError();
            }
        }
        this.U.a(o5Var, o5Var, abstractC3837z1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0729q2 c(C0767v2 c0767v2, InterfaceC0694k5 interfaceC0694k5) {
        AbstractC0727q0 abstractC0727q0A = a(c0767v2, interfaceC0694k5);
        if (abstractC0727q0A == null || !abstractC0727q0A.e0()) {
            return null;
        }
        return abstractC0727q0A.a0();
    }

    private void e() {
        if (this.j.Y().o1()) {
            return;
        }
        X0 x0 = (X0) Y0.p().h();
        final C3698c1 c3698c1 = (C3698c1) C3704d1.p().h();
        final C3754l1 c3754l1 = (C3754l1) C3762m1.x().h();
        final X xD = X.d();
        for (C0729q2 c0729q2 : ((C0680j) this.j.f()).d()) {
            com.android.tools.r8.synthesis.D D = this.j.D();
            D.getClass();
            if (!D.i(c0729q2.d) || this.j.D().a(c0729q2)) {
                if (!q0) {
                    x0.a(this.n);
                }
                b();
                a(c0729q2, x0.c, (com.android.tools.r8.graph.O0) null);
                c0729q2.e(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda154
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(c3698c1, xD, (m5) obj);
                    }
                }, CM.b());
                c0729q2.l(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda155
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(c3754l1, xD, (o5) obj);
                    }
                });
            }
        }
    }

    public final void b(m5 m5Var, o5 o5Var) {
        this.k.a(m5Var);
        if (a(m5Var.getReference(), o5Var, true, true)) {
            b(m5Var, o5Var, AbstractC3837z1.g(o5Var));
        }
    }

    public final void c(m5 m5Var, o5 o5Var) {
        this.k.a(m5Var);
        if (a(m5Var.getReference(), o5Var, false, true)) {
            b(m5Var, o5Var, AbstractC3837z1.g(o5Var));
        }
    }

    private void d(final C0684j2 c0684j2, final o5 o5Var, AbstractC3837z1 abstractC3837z1) {
        if (c0684j2 != this.j.a().p4.l && c0684j2 != this.j.a().q4.a) {
            if (this.j.a().p4.a(c0684j2)) {
                this.w.add(c0684j2);
                this.W.add(o5Var);
            }
        } else {
            this.W.add(o5Var);
        }
        final C0746s4 c0746s4 = this.t;
        Objects.requireNonNull(c0746s4);
        if (a(new BiPredicate() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda121
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return c0746s4.j((C0684j2) obj, (o5) obj2);
            }
        }, c0684j2, o5Var)) {
            a(c0684j2, false, o5Var, abstractC3837z1);
            this.e.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda122
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((C2185ht) obj).a(c0684j2, o5Var);
                }
            });
        }
    }

    final void b(final C0767v2 c0767v2, final o5 o5Var, boolean z) {
        this.h.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda25
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                L.a(c0767v2, o5Var, (InterfaceC2866qg) obj);
            }
        });
        a(c0767v2, o5Var, z);
    }

    private void c(final C0684j2 c0684j2, final o5 o5Var, AbstractC3837z1 abstractC3837z1) {
        C0722p1 c0722p1A = this.j.a();
        C0757u1 c0757u1 = c0722p1A.p4;
        if (c0684j2 == c0757u1.b || c0684j2 == c0757u1.c || c0722p1A.y4.a(c0684j2)) {
            this.w.add(c0684j2);
            this.W.add(o5Var);
        }
        if (c0684j2 == c0722p1A.r4.c) {
            this.W.add(o5Var);
        }
        com.android.tools.r8.graph.X1 x1 = c0722p1A.D5;
        if (c0684j2 == x1.a || c0684j2 == x1.b || c0684j2 == x1.c) {
            this.W.add(o5Var);
        }
        if (c0684j2 == c0722p1A.B4.a) {
            this.W.add(o5Var);
        }
        final C0746s4 c0746s4 = this.t;
        Objects.requireNonNull(c0746s4);
        if (a(new BiPredicate() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda110
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return c0746s4.h((C0684j2) obj, (o5) obj2);
            }
        }, c0684j2, o5Var)) {
            b(c0684j2, (InterfaceC0694k5) o5Var, abstractC3837z1);
            this.e.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda111
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C0684j2 c0684j3 = c0684j2;
                    o5 o5Var2 = o5Var;
                    ((C2185ht) obj).getClass();
                }
            });
        }
    }

    final void g(final C0684j2 c0684j2, final o5 o5Var) {
        AbstractC0727q0 abstractC0727q0C = this.j.c(c0684j2.e);
        if (abstractC0727q0C == null || !abstractC0727q0C.isInterface()) {
            C0729q2 c0729q2A = o5Var.a();
            if (c0729q2A.f != null && !c0729q2A.isInterface()) {
                this.j.a().a(c0729q2A.f, c0684j2.h, c0684j2.f);
            }
        }
        final C0746s4 c0746s4 = this.t;
        Objects.requireNonNull(c0746s4);
        if (a(new BiPredicate() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda108
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return c0746s4.i((C0684j2) obj, (o5) obj2);
            }
        }, c0684j2, o5Var)) {
            this.U.a(c0684j2, o5Var);
            this.e.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda109
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C0684j2 c0684j3 = c0684j2;
                    o5 o5Var2 = o5Var;
                    ((C2185ht) obj).getClass();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C0684j2 c0684j2, o5 o5Var) {
        this.U.b(c0684j2, o5Var);
    }

    final void f(final o5 o5Var) {
        InterfaceC3697c0 interfaceC3697c0 = this.p;
        C0702m c0702m = this.j;
        final C3822x c3822xA = interfaceC3697c0.a(c0702m, o5Var, this, c0702m.d());
        o5Var.a(c3822xA);
        this.c.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda170
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(o5Var, c3822xA, (AbstractC2797pg) obj);
            }
        });
    }

    private void b(final C0684j2 c0684j2, final o5 o5Var, AbstractC3837z1 abstractC3837z1) {
        final C0746s4 c0746s4 = this.t;
        Objects.requireNonNull(c0746s4);
        if (a(new BiPredicate() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda98
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return c0746s4.g((C0684j2) obj, (o5) obj2);
            }
        }, c0684j2, o5Var)) {
            a(c0684j2, true, o5Var, abstractC3837z1);
            this.e.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda99
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C0684j2 c0684j3 = c0684j2;
                    o5 o5Var2 = o5Var;
                    ((C2185ht) obj).getClass();
                }
            });
        }
    }

    final void b(final com.android.tools.r8.graph.Y0 y0, final o5 o5Var, final I i) {
        if (i.a() || a(y0, o5Var, false, false)) {
            AbstractC0677i3 abstractC0677i3A = a(y0, o5Var);
            if (this.k.a(y0, abstractC0677i3A, o5Var, H.b, i)) {
                if (!q0 && i.a()) {
                    throw new AssertionError();
                }
            } else {
                Consumer consumer = new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda27
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.b(y0, o5Var, i, (AbstractC0677i3.a) obj);
                    }
                };
                abstractC0677i3A.a(consumer, consumer, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda28
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.b(y0, o5Var, (AbstractC0629c3) obj);
                    }
                });
            }
        }
    }

    final void e(C0767v2 c0767v2, o5 o5Var) {
        com.android.tools.r8.utils.N3 n3;
        com.android.tools.r8.utils.N3 n4;
        boolean z = q0;
        if (!z && !c0767v2.O0()) {
            throw new AssertionError();
        }
        com.android.tools.r8.utils.N3 n5 = (com.android.tools.r8.utils.N3) this.b0.get(c0767v2);
        if (n5 == null) {
            C0729q2 c0729q2C = c(c0767v2, (InterfaceC0694k5) o5Var);
            if (c0729q2C == null) {
                if (!z) {
                    throw new AssertionError();
                }
                return;
            }
            IdentityHashMap identityHashMap = this.b0;
            C0729q2 c0729q2A = o5Var.a();
            if (c0767v2.a(c0729q2A.d)) {
                n4 = com.android.tools.r8.utils.N3.PACKAGE_PRIVATE;
            } else if (this.i.b(c0729q2A.d, c0767v2)) {
                n4 = com.android.tools.r8.utils.N3.PROTECTED;
            } else {
                n4 = com.android.tools.r8.utils.N3.PUBLIC;
            }
            identityHashMap.put(c0767v2, n4);
            c(c0729q2C, this.h0.a(c0729q2C, (InterfaceC0694k5) o5Var));
            h(c0729q2C);
            return;
        }
        com.android.tools.r8.utils.N3 n6 = com.android.tools.r8.utils.N3.PUBLIC;
        if (n5 == n6) {
            return;
        }
        C0729q2 c0729q2A2 = o5Var.a();
        if (c0767v2.a(c0729q2A2.d)) {
            n3 = com.android.tools.r8.utils.N3.PACKAGE_PRIVATE;
        } else {
            n3 = this.i.b(c0729q2A2.d, c0767v2) ? com.android.tools.r8.utils.N3.PROTECTED : n6;
        }
        if (!z) {
            n3.getClass();
            if (n3 == com.android.tools.r8.utils.N3.PRIVATE) {
                throw new AssertionError();
            }
        }
        n3.getClass();
        if (n3 == n6) {
            this.b0.put(c0767v2, n3);
            return;
        }
        com.android.tools.r8.utils.N3 n7 = com.android.tools.r8.utils.N3.PROTECTED;
        if (n5 == n7) {
            return;
        }
        if (n3 == n7) {
            this.b0.put(c0767v2, n3);
        } else {
            if (!z && n5 != com.android.tools.r8.utils.N3.PACKAGE_PRIVATE) {
                throw new AssertionError();
            }
            if (!z && n3 != com.android.tools.r8.utils.N3.PACKAGE_PRIVATE) {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set g(C0729q2 c0729q2) {
        return OU.c();
    }

    final void g(final o5 o5Var) {
        a(o5Var.a(), (InterfaceC0694k5) o5Var);
        Iterator<C0767v2> it = o5Var.e().l1().iterator();
        while (it.hasNext()) {
            d(it.next(), (InterfaceC0694k5) o5Var);
        }
        d(o5Var.e().V1(), (InterfaceC0694k5) o5Var);
        b((InterfaceC0694k5) o5Var);
        o5Var.e().j1().d(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda33
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(o5Var, (C0633d0) obj);
            }
        });
        a(o5Var);
        if (!c(o5Var.a()) || o5Var.e().L1()) {
            return;
        }
        this.Y.b(o5Var);
    }

    final void d(final com.android.tools.r8.graph.Y0 y0, final o5 o5Var, final I i) {
        if (i.a() || a(y0, o5Var, false, false)) {
            final AbstractC0677i3 abstractC0677i3A = a(y0, o5Var);
            if (this.j.Y().x1().a) {
                if (((Boolean) this.j.a((Object) Boolean.FALSE, new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda20
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.b(abstractC0677i3A, (C0831Aj) obj);
                    }
                })).booleanValue()) {
                    this.H.add(abstractC0677i3A.u().a());
                    return;
                }
            }
            if (this.k.a(y0, abstractC0677i3A, o5Var, H.d, i)) {
                if (!q0 && i.a()) {
                    throw new AssertionError();
                }
            } else {
                Consumer consumer = new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda22
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.d(y0, o5Var, i, (AbstractC0677i3.a) obj);
                    }
                };
                abstractC0677i3A.a(consumer, consumer, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda23
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.d(y0, o5Var, (AbstractC0629c3) obj);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(final com.android.tools.r8.graph.Y0 y0, final o5 o5Var, I i, final AbstractC0677i3.a aVar) {
        this.d.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda57
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(y0, aVar, o5Var, (InterfaceC3007sg) obj);
            }
        });
        m5 m5VarQ = aVar.q();
        if (m5VarQ == null) {
            return;
        }
        if (!q0 && this.b.b() && m5VarQ.e().X0().i()) {
            throw new AssertionError("Unexpected reference in `" + o5Var.w() + "` to field marked dead: " + m5VarQ.getReference().r0());
        }
        if (i.b()) {
            this.s.b(m5VarQ.getReference()).y();
        }
        if (m5VarQ.getReference() != y0) {
            AbstractC0727q0 abstractC0727q0A = aVar.a();
            if (abstractC0727q0A.e0()) {
                C0729q2 c0729q2A0 = abstractC0727q0A.a0();
                c(c0729q2A0, this.h0.a(c0729q2A0, (InterfaceC0694k5) o5Var));
            }
        }
        this.U.a(m5VarQ, o5Var, AbstractC3837z1.a(o5Var));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(C0767v2 c0767v2, o5 o5Var) {
        this.U.b(c0767v2, o5Var);
    }

    final void c(final com.android.tools.r8.graph.Y0 y0, final o5 o5Var, final I i) {
        if (i.a() || a(y0, o5Var, true, false)) {
            final AbstractC0677i3 abstractC0677i3A = a(y0, o5Var);
            if (this.j.Y().x1().a) {
                if (((Boolean) this.j.a((Object) Boolean.FALSE, new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda147
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.a(abstractC0677i3A, (C0831Aj) obj);
                    }
                })).booleanValue()) {
                    this.H.add(abstractC0677i3A.u().a());
                    return;
                }
            }
            if (this.k.a(y0, abstractC0677i3A, o5Var, H.c, i)) {
                if (!q0 && i.a()) {
                    throw new AssertionError();
                }
            } else {
                Consumer consumer = new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda149
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.c(y0, o5Var, i, (AbstractC0677i3.a) obj);
                    }
                };
                abstractC0677i3A.a(consumer, consumer, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda150
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) throws Throwable {
                        this.f$0.c(y0, o5Var, (AbstractC0629c3) obj);
                    }
                });
            }
        }
    }

    public final boolean a(Y y) {
        if (y == null || (y instanceof X)) {
            return true;
        }
        if (y.b()) {
            C0729q2 c0729q2A = C0729q2.a(this.j.c(y.a().d()));
            if (c0729q2A == null) {
                return false;
            }
            if (y instanceof V) {
                return this.A.a.contains(c0729q2A);
            }
            if (y instanceof U) {
                C0606a5 c0606a5 = this.u;
                c0606a5.getClass();
                return (!c0729q2A.isInterface() && c0606a5.c(c0729q2A)) || c0606a5.a(c0729q2A);
            }
        }
        if (q0) {
            return false;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(final com.android.tools.r8.graph.Y0 y0, final o5 o5Var, I i, final AbstractC0677i3.a aVar) {
        this.d.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda79
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.d(y0, aVar, o5Var, (InterfaceC3007sg) obj);
            }
        });
        m5 m5VarQ = aVar.q();
        if (m5VarQ == null) {
            return;
        }
        if (!q0 && this.b.b() && m5VarQ.e().X0().i()) {
            throw new AssertionError("Unexpected reference in `" + o5Var.w() + "` to field marked dead: " + m5VarQ.getReference().r0());
        }
        if (i.b()) {
            this.s.b(m5VarQ.getReference()).y();
        }
        if (m5VarQ.getReference() != y0) {
            AbstractC0727q0 abstractC0727q0A = aVar.a();
            if (abstractC0727q0A.e0()) {
                C0729q2 c0729q2A0 = abstractC0727q0A.a0();
                c(c0729q2A0, this.h0.a(c0729q2A0, (InterfaceC0694k5) o5Var));
            }
        }
        a(m5VarQ, o5Var, AbstractC3837z1.a(o5Var));
    }

    final void a(C0699l2 c0699l2, I5 i5, o5 o5Var) {
        if (!c0699l2.d.h() || i5 == I5.a) {
            return;
        }
        I4 i4A = a(c0699l2.u0(), o5Var, AbstractC3837z1.f(o5Var), c0699l2.f);
        if (i4A.x()) {
            AbstractC0748t0 abstractC0748t0C = i4A.o().c();
            if (abstractC0748t0C instanceof o5) {
                c(Y.c(), abstractC0748t0C.g0(), (C3754l1) C3762m1.x().g());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(com.android.tools.r8.graph.Y0 y0, AbstractC0677i3.a aVar, o5 o5Var, InterfaceC3007sg interfaceC3007sg) {
        interfaceC3007sg.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Boolean b(AbstractC0677i3 abstractC0677i3, C0831Aj c0831Aj) {
        com.android.tools.r8.graph.T2 t2 = this.s;
        C3730h1 c3730h1 = this.Y;
        c0831Aj.getClass();
        m5 m5VarU = abstractC0677i3.u();
        return Boolean.valueOf(m5VarU != null && c0831Aj.a(m5VarU, t2, c3730h1));
    }

    final void e(C0684j2 c0684j2, o5 o5Var) {
        b(c0684j2, o5Var, AbstractC3837z1.c(o5Var));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map e(C0767v2 c0767v2) {
        return new IdentityHashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ h4 b(C0767v2 c0767v2) {
        return new h4();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(o5 o5Var) {
        C0729q2 c0729q2A = o5Var.a();
        if (!q0 && !this.A.a.contains(c0729q2A)) {
            throw new AssertionError();
        }
    }

    private void b(C0729q2 c0729q2, C0767v2 c0767v2) {
        C0729q2 c0729q2C = c(c0767v2, (InterfaceC0694k5) c0729q2);
        if (c0729q2C == null) {
            return;
        }
        if (this.j.Y().u && !this.o.n.contains(c0767v2) && !this.b.d()) {
            if (this.A.a(c0729q2C)) {
                this.h0.a(c0729q2C, c0729q2);
                return;
            }
            if (this.b.c() && !c0729q2C.isInterface()) {
                this.j.a0().c("The class " + c0729q2 + " implements the interface " + c0767v2 + " but " + c0767v2 + " is not an interface.");
            }
            com.android.tools.r8.utils.O3 o3E = com.android.tools.r8.utils.O3.e();
            o3E.c(c0729q2C);
            while (o3E.c()) {
                C0729q2 c0729q3 = (C0729q2) o3E.f();
                if (!this.A.a(c0729q3) && ((Set) this.M.computeIfAbsent(c0729q3, new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda156
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return L.g((C0729q2) obj);
                    }
                })).add(c0729q2)) {
                    Iterator<C0767v2> it = c0729q3.f1().iterator();
                    while (it.hasNext()) {
                        C0729q2 c0729q2C2 = c(it.next(), (InterfaceC0694k5) c0729q3);
                        if (c0729q2C2 != null) {
                            if (!c0729q2C2.J1() && !c0729q2.getType().a(c0729q2C2.getType())) {
                                c(c0729q3, this.h0.a(c0729q3, (InterfaceC0694k5) c0729q2));
                            } else {
                                o3E.c(c0729q2C2);
                            }
                        }
                    }
                }
            }
            return;
        }
        c(c0729q2C, this.h0.a(c0729q2C, (InterfaceC0694k5) c0729q2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(final com.android.tools.r8.graph.Y0 y0, final o5 o5Var, I i, final AbstractC0677i3.a aVar) {
        this.d.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda81
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c(y0, aVar, o5Var, (InterfaceC3007sg) obj);
            }
        });
        m5 m5VarQ = aVar.q();
        if (m5VarQ == null) {
            return;
        }
        if (!q0 && this.b.b() && m5VarQ.e().X0().i()) {
            throw new AssertionError("Unexpected reference in `" + o5Var.w() + "` to field marked dead: " + m5VarQ.getReference().r0());
        }
        if (i.b()) {
            this.s.b(m5VarQ.getReference()).w();
        }
        if (m5VarQ.getReference() != y0) {
            AbstractC0727q0 abstractC0727q0A = aVar.a();
            if (abstractC0727q0A.e0()) {
                C0729q2 c0729q2A0 = abstractC0727q0A.a0();
                c(c0729q2A0, this.h0.a(c0729q2A0, (InterfaceC0694k5) o5Var));
            }
        }
        a(m5VarQ, o5Var, AbstractC3837z1.a(o5Var));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C1132Kj c1132Kj) {
        this.c.add(c1132Kj.b());
    }

    public final boolean e(m5 m5Var) {
        com.android.tools.r8.graph.U2 u2C = this.s.b(m5Var.getReference());
        if (u2C == null) {
            return false;
        }
        C0729q2 c0729q2A = m5Var.a();
        c0729q2A.getClass();
        com.android.tools.r8.graph.W0 w0A = c0729q2A.a(C0767v2.g);
        if (w0A != null) {
            return u2C.a(w0A);
        }
        return u2C.r();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ InterfaceC3697c0 a(C1158Lj c1158Lj) {
        return C3208vP.g();
    }

    public final void a(InterfaceC3007sg interfaceC3007sg) {
        this.d.add(interfaceC3007sg);
    }

    public final void a(C2185ht c2185ht) {
        this.e.add(c2185ht);
    }

    public final L a(InterfaceC3077tg interfaceC3077tg) {
        this.f.add(interfaceC3077tg);
        return this;
    }

    public final L a(InterfaceC2866qg interfaceC2866qg) {
        this.h.add(interfaceC2866qg);
        return this;
    }

    public final void a(InterfaceC2938rg interfaceC2938rg) {
        this.g.add(interfaceC2938rg);
    }

    public final void a(C3720g c3720g) {
        this.q = c3720g;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Set e(C0729q2 c0729q2) {
        o5 o5Var = (o5) this.z.get(c0729q2);
        if (o5Var != null) {
            return AbstractC1048Hm.c(o5Var.getReference());
        }
        return AbstractC1048Hm.c(c0729q2.getType());
    }

    public final void a(C0767v2 c0767v2) {
        C0729q2 c0729q2A = C0729q2.a(this.j.c(c0767v2));
        if (c0729q2A != null) {
            this.H.add(c0729q2A);
        }
    }

    public final boolean a(AbstractC3837z1 abstractC3837z1, o5 o5Var) {
        return this.S.a(abstractC3837z1, o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0684j2 c0684j2, l5 l5Var) {
        a(c0684j2, l5Var, new L$$ExternalSyntheticLambda32(this), new L$$ExternalSyntheticLambda42(this));
    }

    private void a(C0684j2 c0684j2, l5 l5Var, BiConsumer biConsumer, BiConsumer biConsumer2) {
        c(c0684j2.e, l5Var, biConsumer, biConsumer2);
        c(c0684j2.h.e, l5Var, biConsumer, biConsumer2);
        for (C0767v2 c0767v2 : c0684j2.h.f.a) {
            c(c0767v2, l5Var, biConsumer, biConsumer2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(com.android.tools.r8.graph.Y0 y0, AbstractC0677i3.a aVar, o5 o5Var, InterfaceC3007sg interfaceC3007sg) {
        interfaceC3007sg.a();
    }

    private void d(C0767v2 c0767v2, InterfaceC0694k5 interfaceC0694k5) {
        C0729q2 c0729q2C;
        if (c0767v2.M0()) {
            d(c0767v2.b(this.j.a()), interfaceC0694k5);
        } else if (c0767v2.O0() && (c0729q2C = c(c0767v2, interfaceC0694k5)) != null) {
            a(c0729q2C, interfaceC0694k5);
        }
    }

    public final AbstractC0727q0 a(C0767v2 c0767v2, InterfaceC0694k5 interfaceC0694k5) {
        return a(c0767v2, interfaceC0694k5, new L$$ExternalSyntheticLambda32(this), new L$$ExternalSyntheticLambda42(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC0727q0 a(C0767v2 c0767v2, l5 l5Var, BiConsumer biConsumer, BiConsumer biConsumer2) {
        boolean z = q0;
        if (z || z || Thread.currentThread() == this.n0) {
            return b(c0767v2, l5Var, biConsumer, biConsumer2).R();
        }
        throw new AssertionError();
    }

    public final boolean a(m5 m5Var, Predicate predicate) {
        AbstractC3716f1 abstractC3716f1A = this.Z.a(X.d(), S1.a()).a((AbstractC0744s2) m5Var.getReference(), (C3754l1) null);
        C3698c1 c3698c1B = abstractC3716f1A != null ? abstractC3716f1A.b() : null;
        return c3698c1B != null && predicate.test(c3698c1B);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(com.android.tools.r8.graph.Y0 y0, AbstractC0677i3.a aVar, o5 o5Var, InterfaceC3007sg interfaceC3007sg) {
        interfaceC3007sg.a(y0, o5Var, this.U);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ h4 d(C0767v2 c0767v2) {
        return new h4();
    }

    final void c(C0729q2 c0729q2, AbstractC3837z1 abstractC3837z1) {
        if (!q0 && c0729q2 == null) {
            throw new AssertionError();
        }
        a(c0729q2, (h4) this.g0.computeIfAbsent(c0729q2.getType(), new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return L.c((C0767v2) obj);
            }
        }), this.h0.a(c0729q2, abstractC3837z1));
    }

    private I4 d(final C0684j2 c0684j2, final InterfaceC0694k5 interfaceC0694k5, final AbstractC3837z1 abstractC3837z1) {
        final I4 i4H = this.i.h(c0684j2);
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0684j2, interfaceC0694k5, (I4) obj);
            }
        };
        i4H.a(consumer, consumer, consumer, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0684j2, i4H, interfaceC0694k5, abstractC3837z1, (I4.a) obj);
            }
        });
        return i4H;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void a(final com.android.tools.r8.graph.T t, final com.android.tools.r8.utils.O3 o3, final BiConsumer biConsumer, boolean z) {
        a((com.android.tools.r8.graph.F) t);
        if (z) {
            if (t.f0()) {
                C0635d2 c0635d2D0 = t.d0();
                C0767v2 c0767v2 = c0635d2D0.f;
                if (c0767v2 != null) {
                    a(c0767v2, c0635d2D0);
                }
                for (C0767v2 c0767v3 : c0635d2D0.g.a) {
                    a(c0767v3, c0635d2D0);
                }
            }
            this.c.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda132
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((AbstractC2797pg) obj).a(t);
                }
            });
            t.a(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda133
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(biConsumer, (AbstractC0735r0) obj);
                }
            });
            t.c(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda134
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(biConsumer, (AbstractC0748t0) obj);
                }
            });
        }
        Iterator it = ((AbstractC0727q0) t).H0().iterator();
        while (it.hasNext()) {
            a((C0767v2) it.next(), t, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda135
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    o3.c((com.android.tools.r8.graph.T) obj);
                }
            }, biConsumer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(com.android.tools.r8.graph.Y0 y0, o5 o5Var, AbstractC0629c3 abstractC0629c3) {
        d(y0.B0(), (InterfaceC0694k5) o5Var);
        d(y0.getType(), (InterfaceC0694k5) o5Var);
        this.L.add(y0.B0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(o5 o5Var) {
        boolean z = q0;
        if (!z && !o5Var.e().u1()) {
            com.android.tools.r8.graph.W0 w0E = o5Var.e();
            if (!z && w0E.u1()) {
                throw new AssertionError("Class initializers are never targeted");
            }
            if (!z && !this.N.a.contains(w0E)) {
                throw new AssertionError();
            }
        }
        if (z) {
            return;
        }
        C0729q2 c0729q2A = o5Var.a();
        if (!z && !this.A.a.contains(c0729q2A)) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ h4 c(C0767v2 c0767v2) {
        return new h4();
    }

    final void c(C0684j2 c0684j2, InterfaceC0694k5 interfaceC0694k5, AbstractC3837z1 abstractC3837z1) {
        a(c0684j2, interfaceC0694k5, abstractC3837z1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(AbstractC0727q0 abstractC0727q0, l5 l5Var) {
        if (abstractC0727q0.e0()) {
            return;
        }
        com.android.tools.r8.graph.T tN = abstractC0727q0.N();
        L$$ExternalSyntheticLambda123 l$$ExternalSyntheticLambda123 = new L$$ExternalSyntheticLambda123(this);
        com.android.tools.r8.utils.O3 o3A = com.android.tools.r8.utils.O3.a(this.F, tN);
        while (o3A.c()) {
            a((com.android.tools.r8.graph.T) o3A.f(), o3A, (BiConsumer) l$$ExternalSyntheticLambda123, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(C0767v2 c0767v2, l5 l5Var) {
        Set set;
        if (!q0 && this.b.b() && !this.I.d(c0767v2) && !this.j.a().g(c0767v2) && !this.J.contains(c0767v2) && ((set = this.K) == null || !set.contains(c0767v2))) {
            throw new AssertionError("Unexpected missing class `" + c0767v2.r0() + "`");
        }
        if (l5Var.O() && l5Var.getContext().h0() && l5Var.getContext().c().e().N0() && !this.j.D().b(l5Var.getContext().i().b())) {
            this.I.b(c0767v2);
        } else {
            this.I.a(c0767v2, l5Var);
        }
    }

    public final boolean d(m5 m5Var) {
        com.android.tools.r8.graph.U2 u2C = this.s.b(m5Var.getReference());
        return u2C != null && u2C.n();
    }

    public final boolean d(C0729q2 c0729q2) {
        return this.A.a.contains(c0729q2);
    }

    private void b(InterfaceC0694k5 interfaceC0694k5) {
        C0658g0 c0658g0S0 = interfaceC0694k5.e().s0();
        EnumC0608b0 enumC0608b0A = EnumC0608b0.a(interfaceC0694k5.getReference());
        for (C0633d0 c0633d0 : c0658g0S0.c) {
            a(interfaceC0694k5, c0633d0, enumC0608b0A);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(Y y, C0729q2 c0729q2, X0 x0) {
        if (a(y)) {
            c(y, c0729q2, x0);
        } else {
            this.Z.a(y).a((AbstractC0744s2) c0729q2.getType()).a(x0);
        }
        y.getClass();
        if (y instanceof X) {
            if ((this.n.o1() || this.b.d()) && !x0.m()) {
                boolean z = q0;
                if (!z) {
                    x0.a(this.n);
                }
                if (!z) {
                    x0.a(this.n);
                }
                a(c0729q2, x0.c, y.a(b()));
            }
        }
    }

    private void b(C0684j2 c0684j2, InterfaceC0694k5 interfaceC0694k5, final AbstractC3837z1 abstractC3837z1) {
        d(c0684j2, interfaceC0694k5, abstractC3837z1).a(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda153
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(abstractC3837z1, (I4) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(AbstractC0727q0 abstractC0727q0, final l5 l5Var) {
        if (abstractC0727q0.e0()) {
            return;
        }
        com.android.tools.r8.graph.T tN = abstractC0727q0.N();
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda93
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(l5Var, (C0767v2) obj, (com.android.tools.r8.graph.V) obj2);
            }
        };
        com.android.tools.r8.utils.O3 o3A = com.android.tools.r8.utils.O3.a(this.F, tN);
        while (o3A.c()) {
            a((com.android.tools.r8.graph.T) o3A.f(), o3A, biConsumer, true);
        }
    }

    final void b(C0729q2 c0729q2, L0 l0) {
        boolean z = q0;
        if (!z && c0729q2.e.T()) {
            throw new AssertionError();
        }
        if (!z && !c0729q2.isInterface()) {
            throw new AssertionError();
        }
        if (this.u.b(c0729q2, this.i)) {
            c(c0729q2, l0);
            if (!z && !c0729q2.isInterface()) {
                throw new AssertionError();
            }
            k(c0729q2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(BiConsumer biConsumer, AbstractC0735r0 abstractC0735r0) {
        C0767v2 type = abstractC0735r0.getType();
        com.android.tools.r8.graph.V vG = abstractC0735r0.g();
        Set set = this.G;
        Objects.requireNonNull(set);
        a(type, vG, new L$$ExternalSyntheticLambda104(set), biConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(Y y, m5 m5Var, C3698c1 c3698c1) {
        if (a(y)) {
            c(y, m5Var, c3698c1);
        } else {
            this.Z.a(y).a((AbstractC0744s2) m5Var.getReference()).a(c3698c1);
        }
        y.getClass();
        if (y instanceof X) {
            if ((this.n.o1() || this.b.d()) && !c3698c1.m()) {
                boolean z = q0;
                if (!z) {
                    c3698c1.a(this.n);
                }
                if (!z) {
                    c3698c1.a(this.n);
                }
                this.U.a(this.h0.a(y.a(b()), c3698c1.b, c3698c1.c, m5Var.e()), m5Var);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(BiConsumer biConsumer, AbstractC0748t0 abstractC0748t0) {
        com.android.tools.r8.graph.V vG = abstractC0748t0.g();
        C0767v2 c0767v2G = abstractC0748t0.G();
        Set set = this.G;
        Objects.requireNonNull(set);
        a(c0767v2G, vG, new L$$ExternalSyntheticLambda104(set), biConsumer);
        for (C0767v2 c0767v2 : abstractC0748t0.D()) {
            Set set2 = this.G;
            Objects.requireNonNull(set2);
            a(c0767v2, vG, new L$$ExternalSyntheticLambda104(set2), biConsumer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(final com.android.tools.r8.graph.Y0 y0, final o5 o5Var, AbstractC0629c3 abstractC0629c3) throws Throwable {
        d(y0.B0(), (InterfaceC0694k5) o5Var);
        d(y0.getType(), (InterfaceC0694k5) o5Var);
        this.L.add(y0.B0());
        this.j.b(new InterfaceC3905h3() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda14
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                this.f$0.a(y0, o5Var, (C0831Aj) obj);
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0027  */
    final void b(final m5 m5Var, InterfaceC0694k5 interfaceC0694k5, AbstractC3837z1 abstractC3837z1) {
        if (!m5Var.e().E0()) {
            C0606a5 c0606a5 = this.u;
            C0729q2 c0729q2A = m5Var.a();
            c0606a5.getClass();
            if ((!c0729q2A.isInterface() && c0606a5.c(c0729q2A)) || c0606a5.a(c0729q2A)) {
                a(m5Var, interfaceC0694k5, abstractC3837z1);
            }
        } else {
            a(m5Var, interfaceC0694k5, abstractC3837z1);
        }
        J j = this.T;
        j.getClass();
        if (!j.a.contains(m5Var.e()) && ((C3431yN) this.y.computeIfAbsent(m5Var.a(), new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda163
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return L.f((C0729q2) obj);
            }
        })).a(m5Var)) {
            C0729q2 c0729q2A2 = m5Var.a();
            c(c0729q2A2, this.h0.a(c0729q2A2, m5Var));
            d(m5Var.getType(), m5Var);
            b((InterfaceC0694k5) m5Var);
            this.c.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda164
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(m5Var, (AbstractC2797pg) obj);
                }
            });
            return;
        }
        this.h0.a(m5Var.e(), abstractC3837z1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(AbstractC3837z1 abstractC3837z1, final o5 o5Var) {
        if (this.U.a(o5Var, o5Var, abstractC3837z1)) {
            if (q0) {
                return;
            }
            this.U.a(new InterfaceC3880d() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda130
                @Override // com.android.tools.r8.utils.InterfaceC3880d
                public final void a() {
                    this.f$0.d(o5Var);
                }
            });
            return;
        }
        boolean z = q0;
        if (!z && !o5Var.e().u1()) {
            com.android.tools.r8.graph.W0 w0E = o5Var.e();
            if (!z && w0E.u1()) {
                throw new AssertionError("Class initializers are never targeted");
            }
            if (!z && !this.N.a.contains(w0E)) {
                throw new AssertionError();
            }
        }
        if (z) {
            return;
        }
        this.U.a(new InterfaceC3880d() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda131
            @Override // com.android.tools.r8.utils.InterfaceC3880d
            public final void a() {
                this.f$0.e(o5Var);
            }
        });
    }

    private void a(C0767v2 c0767v2, com.android.tools.r8.graph.V v, final Consumer consumer, BiConsumer biConsumer) {
        if (c0767v2.M0()) {
            c0767v2 = c0767v2.b(this.j.a());
        }
        if (c0767v2.O0()) {
            com.android.tools.r8.graph.O oF = this.j.f().f(c0767v2);
            if (!oF.T()) {
                biConsumer.accept(c0767v2, v);
            } else {
                oF.b(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda83
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        L.a(consumer, (AbstractC0727q0) obj);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(Y y, o5 o5Var, C3754l1 c3754l1) {
        if (a(y)) {
            c(y, o5Var, c3754l1);
        } else {
            this.Z.a(y).a((AbstractC0744s2) o5Var.getReference()).a(c3754l1);
        }
        y.getClass();
        if (y instanceof X) {
            b(y, o5Var, c3754l1);
        }
    }

    public final boolean c(m5 m5Var) {
        J j = this.T;
        j.getClass();
        return j.a.contains(m5Var.e());
    }

    final void c(C0684j2 c0684j2, final o5 o5Var) {
        final AbstractC3837z1 abstractC3837z1H = AbstractC3837z1.h(o5Var);
        d(c0684j2, (InterfaceC0694k5) o5Var, abstractC3837z1H).a(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda166
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(abstractC3837z1H, o5Var, (I4) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(com.android.tools.r8.graph.Y0 y0, o5 o5Var, AbstractC0629c3 abstractC0629c3) {
        d(y0.B0(), (InterfaceC0694k5) o5Var);
        d(y0.getType(), (InterfaceC0694k5) o5Var);
        this.L.add(y0.B0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, AbstractC0727q0 abstractC0727q0) {
        if (abstractC0727q0.e0()) {
            return;
        }
        consumer.accept(abstractC0727q0.N());
    }

    final void d(AbstractC3837z1 abstractC3837z1, o5 o5Var) {
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        C0729q2 c0729q2A = o5Var.a();
        C0684j2 reference = o5Var.getReference();
        b(abstractC3837z1, o5Var);
        if (w0E.S1()) {
            a(reference, c0729q2A.isInterface(), o5Var, abstractC3837z1);
            if (w0E.G1()) {
                C0606a5 c0606a5 = this.u;
                c0606a5.getClass();
                if ((c0729q2A.isInterface() || !c0606a5.c(c0729q2A)) && !c0606a5.a(c0729q2A)) {
                    return;
                }
                f(abstractC3837z1, o5Var);
                return;
            }
            return;
        }
        c(abstractC3837z1, o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CN c(com.android.tools.r8.graph.W0 w0) {
        return CN.b();
    }

    private void a(C0729q2 c0729q2, Set set, com.android.tools.r8.graph.O0 o0) {
        L0 l0A = this.h0.a(o0, set, c0729q2);
        if (c0729q2.e.T()) {
            this.U.a(c0729q2, l0A);
            return;
        }
        if (c0729q2.isInterface()) {
            this.U.b(c0729q2, l0A);
            return;
        }
        this.U.a(c0729q2, null, V0.a, l0A);
        if (c0729q2.q1()) {
            o5 o5VarY1 = c0729q2.Y1();
            if (this.a) {
                C3754l1 c3754l1X = C3762m1.x();
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    C3737i3 c3737i3 = (C3737i3) it.next();
                    if (!c3737i3.H().equals(EnumC3758l3.KEEP_CLASS_MEMBERS)) {
                        c3754l1X.c.add(c3737i3);
                    }
                }
                if (!c3754l1X.c.isEmpty()) {
                    this.U.a(this.h0.a(o5VarY1), o5VarY1);
                    c(Y.c(), o5VarY1, (C3754l1) c3754l1X.g());
                }
            }
            C0680j c0680j = (C0680j) this.j.f();
            if (c0680j.c(c0729q2.d, c0680j.a().v5)) {
                this.U.a(o5VarY1, o5VarY1, l0A);
                c(Y.c(), o5VarY1, (C3754l1) C3762m1.x().g());
            }
        }
    }

    public final boolean b(com.android.tools.r8.graph.T0 t0) {
        return this.s.b(t0.getReference()) != null;
    }

    public final boolean b(m5 m5Var, Predicate predicate) {
        com.android.tools.r8.graph.U2 u2C = this.s.b(m5Var.getReference());
        return u2C != null && u2C.b(predicate);
    }

    private W3 c() {
        C3707d4 c3707d4A = C3686a4.a(this.j, this, this.m);
        for (C3835z c3835z : this.o.e) {
            c3835z.getClass();
            o5 o5VarB = c3835z.b();
            o5 o5VarC = c3835z.c();
            com.android.tools.r8.graph.W0 w0E = o5VarC.e();
            if (!this.o.a(o5VarC, this.n)) {
                if (w0E.D1().d()) {
                    o5VarB.e().a(AbstractC3938n2.a);
                }
                c3835z.a().accept(c3707d4A);
            }
        }
        return c3707d4A.a();
    }

    public final boolean b(com.android.tools.r8.graph.W0 w0) {
        return this.N.a.contains(w0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(com.android.tools.r8.graph.O0 o0) {
        C3730h1 c3730h1 = this.Y;
        return c3730h1.a(o0, this.i).c((K0) this.n);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C0729q2 c0729q2, AbstractC3837z1 abstractC3837z1) {
        o5 o5VarF = c0729q2.f(a((AbstractC0727q0) c0729q2));
        if (o5VarF != null) {
            this.U.a(abstractC3837z1, o5VarF);
            this.Y.a(o5VarF, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda91
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    L.b((C3754l1) obj);
                }
            });
            if (this.n.k1()) {
                this.o.a(o5VarF);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(C3754l1 c3754l1) {
        ((C3754l1) ((C3754l1) c3754l1.f()).g()).h();
    }

    public final N1 b(ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        boolean z = q0;
        if (!z && !this.c.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.b.d()) {
            throw new AssertionError();
        }
        Y3 y3Y = this.j.y();
        this.o = y3Y;
        a(y3Y);
        a(executorService, y3Var);
        this.n.e.a();
        final L1 l1A = ((C0680j) this.j.f()).f().a();
        Collections.unmodifiableSet(this.A.a).forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda29
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                l1A.b((C0729q2) obj);
            }
        });
        a aVar = this.b;
        aVar.getClass();
        if (aVar == a.c) {
            Collections.unmodifiableSet(this.S.a).forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda30
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    L.a(l1A, (com.android.tools.r8.graph.W0) obj);
                }
            });
        } else if (!z && !((C0680j) this.j.f()).f().e()) {
            a aVar2 = this.b;
            aVar2.getClass();
            if (aVar2 != a.e) {
                throw new AssertionError();
            }
        }
        new O1(this.j, l1A.c(), l1A).a();
        return l1A.a(this.i.f());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C3698c1 c3698c1, Y y, m5 m5Var) {
        if (!q0) {
            c3698c1.a(this.n);
        }
        this.U.a(this.h0.a(y.a(b()), c3698c1.b, c3698c1.c, m5Var.e()), m5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C3754l1 c3754l1, Y y, o5 o5Var) {
        if (!q0) {
            c3754l1.a(this.n);
        }
        this.U.a(this.h0.a(y.a(b()), c3754l1.c, o5Var.e()), o5Var);
    }

    private boolean a(BiPredicate biPredicate, C0684j2 c0684j2, o5 o5Var) {
        C0767v2 c0767v2B = c0684j2.e.b(this.j.a());
        if (!c0767v2B.O0()) {
            return false;
        }
        d(c0767v2B, (InterfaceC0694k5) o5Var);
        return biPredicate.test(c0684j2, o5Var);
    }

    private void b(Y y, o5 o5Var, C3754l1 c3754l1) {
        if ((this.n.o1() || this.b.d()) && !c3754l1.m()) {
            boolean z = q0;
            if (!z) {
                c3754l1.a(this.n);
            }
            if (!z) {
                c3754l1.a(this.n);
            }
            this.U.a(this.h0.a(y.a(b()), c3754l1.c, o5Var.e()), o5Var);
            if (o5Var.e().C1()) {
                a(o5Var.a(), c3754l1.c, o5Var.e());
            }
        }
    }

    public final void a(m5 m5Var, o5 o5Var) {
        this.k.a(m5Var);
        if (a(m5Var.getReference(), o5Var, true, true) || a(m5Var.getReference(), o5Var, false, true)) {
            b(m5Var, o5Var, AbstractC3837z1.g(o5Var));
        }
    }

    private boolean a(com.android.tools.r8.graph.Y0 y0, o5 o5Var, boolean z, boolean z2) {
        com.android.tools.r8.graph.U2 u2C = this.s.b(y0);
        if (u2C == null) {
            final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
            a(y0, o5Var).b(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda160
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    L.a(n, (AbstractC0677i3.a) obj);
                }
            });
            if (!n.c()) {
                this.s.a(y0, com.android.tools.r8.graph.U2.e);
                return true;
            }
            com.android.tools.r8.graph.T0 t0E = ((AbstractC0735r0) n.a()).e();
            com.android.tools.r8.graph.U2 u2C2 = this.s.b(t0E.getReference());
            if (u2C2 == null) {
                u2C2 = new com.android.tools.r8.graph.U2(t0E.getReference());
                this.s.a(t0E.getReference(), u2C2);
            }
            if (y0 != t0E.getReference()) {
                this.s.a(y0, u2C2);
            }
            u2C = u2C2;
        } else if (u2C == com.android.tools.r8.graph.U2.e) {
            return false;
        }
        if (!z2) {
            return z ? u2C.a(y0, o5Var) : u2C.b(y0, o5Var);
        }
        if (z) {
            if (!u2C.l()) {
                u2C.t();
                return true;
            }
        } else if (!u2C.m()) {
            u2C.u();
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(o5 o5Var, o5 o5Var2) {
        synchronized (this.z) {
            this.z.put(o5Var.a(), o5Var2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(o5 o5Var) {
        InterfaceC2488lo interfaceC2488loQ = o5Var.a(this.j).q();
        while (interfaceC2488loQ.hasNext()) {
            a(o5Var, interfaceC2488loQ.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(X0 x0) {
        ((X0) x0.g()).h();
    }

    private C2720of b(C0767v2 c0767v2, o5 o5Var) {
        C2720of c2720ofA = C2720of.a();
        com.android.tools.r8.utils.O3 o3H = com.android.tools.r8.utils.O3.h(c0767v2);
        while (o3H.c()) {
            AbstractC0727q0 abstractC0727q0B = b((C0767v2) o3H.f(), (InterfaceC0694k5) o5Var);
            if (abstractC0727q0B != null) {
                abstractC0727q0B.b(new nH$$ExternalSyntheticLambda1(c2720ofA), new O2$$ExternalSyntheticLambda4());
                o3H.a((Iterable) abstractC0727q0B.f1());
            }
        }
        return c2720ofA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.N n, AbstractC0677i3.a aVar) {
        AbstractC0735r0 abstractC0735r0C = aVar.c();
        if (!n.c() || (abstractC0735r0C instanceof m5)) {
            n.a(abstractC0735r0C);
        }
    }

    /* JADX WARN: Code duplicated, block: B:11:0x002b  */
    /* JADX WARN: Code duplicated, block: B:13:0x003b  */
    final void a(C0721p0 c0721p0, final o5 o5Var) {
        if (!(this.n.f instanceof ClassFileConsumer)) {
            C0680j c0680jB = b();
            int i = JA.n;
            if (c0721p0.f.d.g()) {
                C0722p1 c0722p1A = c0680jB.a();
                C0684j2 c0684j2U0 = c0721p0.f.u0();
                if (c0684j2U0 != c0722p1A.I5 && c0684j2U0 != c0722p1A.J5) {
                    if (c(c0721p0.f.u0().B0(), (InterfaceC0694k5) o5Var) != null) {
                        this.Q.add(c0721p0.f.u0());
                    }
                }
            } else if (c(c0721p0.f.u0().B0(), (InterfaceC0694k5) o5Var) != null) {
                this.Q.add(c0721p0.f.u0());
            }
        } else if (c(c0721p0.f.u0().B0(), (InterfaceC0694k5) o5Var) != null) {
            this.Q.add(c0721p0.f.u0());
        }
        JA jaB = JA.b(c0721p0, this.j, b(), o5Var);
        if (jaB == null) {
            for (com.android.tools.r8.graph.B2 b2 : c0721p0.g) {
                b2.getClass();
                if (b2 instanceof com.android.tools.r8.graph.G2) {
                    C0699l2 c0699l2 = (C0699l2) b2.G0().j1();
                    if (c0699l2.d.h()) {
                        a(c0699l2.u0(), o5Var);
                    }
                }
            }
            return;
        }
        if (!q0 && !this.n.A0.a()) {
            throw new AssertionError();
        }
        for (C0767v2 c0767v2 : jaB.g) {
            a(c0767v2, o5Var);
            this.u.a(c0767v2, jaB, this.i);
        }
        a(new T0(jaB), this.i.a().Z1, jaB.g);
        ((CN) this.v.computeIfAbsent(c0721p0, new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda94
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return L.a((C0721p0) obj);
            }
        })).add(o5Var);
        jaB.i.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda95
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(o5Var, (C0767v2) obj);
            }
        });
        C0699l2 c0699l3 = jaB.f;
        if (!q0 && c0699l3 == null) {
            throw new AssertionError();
        }
        C0684j2 c0684j2U1 = c0699l3.u0();
        switch (c0699l3.d.ordinal()) {
            case 4:
                c(c0684j2U1, o5Var, AbstractC3837z1.d(o5Var));
                break;
            case 5:
                d(c0684j2U1, o5Var, AbstractC3837z1.d(o5Var));
                break;
            case 6:
                C0767v2 c0767v3 = c0684j2U1.e;
                V0 v0 = V0.b;
                AbstractC3837z1 abstractC3837z1D = AbstractC3837z1.d(o5Var);
                C0729q2 c0729q2C = c(c0767v3, (InterfaceC0694k5) o5Var);
                if (c0729q2C != null) {
                    if (!c0729q2C.e.T() && !c0729q2C.isInterface()) {
                        this.U.a(c0729q2C, o5Var, v0, abstractC3837z1D);
                    } else {
                        c(c0729q2C, this.h0.a(c0729q2C, abstractC3837z1D));
                    }
                }
                break;
            case 7:
                a(c0684j2U1, o5Var, AbstractC3837z1.d(o5Var));
                break;
            case 8:
                b(c0684j2U1, o5Var, AbstractC3837z1.d(o5Var));
                break;
            default:
                throw new C3405y10();
        }
        a(c0684j2U1, o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CN a(C0721p0 c0721p0) {
        return CN.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(o5 o5Var, C0767v2 c0767v2) {
        d(c0767v2, (InterfaceC0694k5) o5Var);
    }

    private void a(C0684j2 c0684j2, o5 o5Var) {
        d(c0684j2, (InterfaceC0694k5) o5Var, AbstractC3837z1.f(o5Var)).a(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda92
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((I4) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(I4 i4) {
        if (i4.x() && i4.o().d().e0()) {
            c(Y.c(), i4.s(), C3762m1.x().s());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0767v2 c0767v2, o5 o5Var, InterfaceC2866qg interfaceC2866qg) {
        ((C3719f4) interfaceC2866qg).a(c0767v2);
    }

    final void a(C0767v2 c0767v2, o5 o5Var, ListIterator listIterator, boolean z) {
        C0767v2 c0767v2B = c0767v2.b(this.j.a());
        if (c0767v2B.O0() && c(c0767v2B, (InterfaceC0694k5) o5Var) != null && a(o5Var, listIterator)) {
            this.a0.add(c0767v2B);
        }
        a(c0767v2, o5Var, z);
    }

    private boolean a(o5 o5Var, ListIterator listIterator) {
        boolean z = true;
        if (listIterator == null) {
            return true;
        }
        if (o5Var.e().a1().z0()) {
            B7 b7M = ((InterfaceC0419c) com.android.tools.r8.utils.L1.b((Iterator) listIterator, new Predicate() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda15
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return L.a((InterfaceC0419c) obj);
                }
            })).m();
            if (!q0 && b7M == null) {
                throw new AssertionError();
            }
            if (b7M.H()) {
                C0684j2 c0684j2T = b7M.l().T();
                C0757u1 c0757u1 = this.j.a().p4;
                if (c0757u1.n.contains(c0684j2T) || c0684j2T == c0757u1.a || c0684j2T == c0757u1.d || c0684j2T == c0757u1.k) {
                    z = false;
                }
            }
            listIterator.previous();
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(InterfaceC0419c interfaceC0419c) {
        B7 b7M = interfaceC0419c.m();
        b7M.getClass();
        if (!(b7M instanceof M7)) {
            B7 b7M2 = interfaceC0419c.m();
            b7M2.getClass();
            if (!(b7M2 instanceof C2051g8)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AbstractC3837z1 a(C0729q2 c0729q2, o5 o5Var) {
        return this.h0.b(c0729q2, o5Var);
    }

    private void a(C0767v2 c0767v2, final o5 o5Var, boolean z) {
        final C0729q2 c0729q2C;
        d(c0767v2, (InterfaceC0694k5) o5Var);
        if (!this.a || z) {
            return;
        }
        C0767v2 c0767v2B = c0767v2.b(this.j.a());
        if (!c0767v2B.O0() || (c0729q2C = c(c0767v2B, (InterfaceC0694k5) o5Var)) == null) {
            return;
        }
        a(c0729q2C, new Supplier() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda106
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.a(c0729q2C, o5Var);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0767v2 c0767v2, o5 o5Var, InterfaceC3077tg interfaceC3077tg) {
        ((C3719f4) interfaceC3077tg).c(c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0767v2 c0767v2, o5 o5Var, InterfaceC2938rg interfaceC2938rg) {
        ((C3719f4) interfaceC2938rg).b(c0767v2);
    }

    private boolean a(C0767v2 c0767v2, final o5 o5Var, final InterfaceC3880d interfaceC3880d) {
        final C0729q2 c0729q2C = c(c0767v2, (InterfaceC0694k5) o5Var);
        if (c0729q2C == null) {
            return false;
        }
        return ((Boolean) this.j.a(Boolean.FALSE, new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0729q2C, o5Var, interfaceC3880d, (C1132Kj) obj);
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean a(final C0729q2 c0729q2, o5 o5Var, final InterfaceC3880d interfaceC3880d, C1132Kj c1132Kj) {
        return Boolean.valueOf(c1132Kj.a(c0729q2, o5Var, new BooleanSupplier() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda120
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.a(c0729q2, interfaceC3880d);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(C0729q2 c0729q2, InterfaceC3880d interfaceC3880d) {
        return this.A.a(c0729q2, interfaceC3880d);
    }

    private void a(final C0684j2 c0684j2, final o5 o5Var, AbstractC3837z1 abstractC3837z1) {
        final C0746s4 c0746s4 = this.t;
        Objects.requireNonNull(c0746s4);
        if (a(new BiPredicate() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda49
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return c0746s4.f((C0684j2) obj, (o5) obj2);
            }
        }, c0684j2, o5Var)) {
            a(c0684j2, (InterfaceC0694k5) o5Var, abstractC3837z1);
            this.e.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda50
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C0684j2 c0684j3 = c0684j2;
                    o5 o5Var2 = o5Var;
                    ((C2185ht) obj).getClass();
                }
            });
        }
    }

    final void a(final com.android.tools.r8.graph.Y0 y0, final o5 o5Var, final I i) {
        if (i.a() || a(y0, o5Var, true, false)) {
            AbstractC0677i3 abstractC0677i3A = a(y0, o5Var);
            if (this.k.a(y0, abstractC0677i3A, o5Var, H.a, i)) {
                if (!q0 && i.a()) {
                    throw new AssertionError();
                }
            } else {
                Consumer consumer = new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda115
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(y0, o5Var, i, (AbstractC0677i3.a) obj);
                    }
                };
                abstractC0677i3A.a(consumer, consumer, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda126
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(y0, o5Var, (AbstractC0629c3) obj);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final com.android.tools.r8.graph.Y0 y0, final o5 o5Var, I i, final AbstractC0677i3.a aVar) {
        this.d.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda62
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(y0, aVar, o5Var, (InterfaceC3007sg) obj);
            }
        });
        m5 m5VarQ = aVar.q();
        if (m5VarQ == null) {
            return;
        }
        if (!q0 && this.b.b() && m5VarQ.e().X0().i()) {
            throw new AssertionError("Unexpected reference in `" + o5Var.w() + "` to field marked dead: " + m5VarQ.getReference().r0());
        }
        if (i.b()) {
            this.s.b(m5VarQ.getReference()).w();
        } else if (i.c()) {
            this.s.b(m5VarQ.getReference()).x();
        }
        if (m5VarQ.getReference() != y0) {
            AbstractC0727q0 abstractC0727q0A = aVar.a();
            if (abstractC0727q0A.e0()) {
                C0729q2 c0729q2A0 = abstractC0727q0A.a0();
                c(c0729q2A0, this.h0.a(c0729q2A0, (InterfaceC0694k5) o5Var));
            }
        }
        this.U.a(m5VarQ, o5Var, AbstractC3837z1.a(o5Var));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.Y0 y0, AbstractC0677i3.a aVar, o5 o5Var, InterfaceC3007sg interfaceC3007sg) {
        interfaceC3007sg.a(y0, o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Boolean a(AbstractC0677i3 abstractC0677i3, C0831Aj c0831Aj) {
        com.android.tools.r8.graph.T2 t2 = this.s;
        C3730h1 c3730h1 = this.Y;
        c0831Aj.getClass();
        m5 m5VarU = abstractC0677i3.u();
        return Boolean.valueOf(m5VarU != null && c0831Aj.a(m5VarU, t2, c3730h1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.Y0 y0, o5 o5Var, C0831Aj c0831Aj) {
        c0831Aj.a(y0, o5Var, this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0767v2 c0767v2, InterfaceC0694k5 interfaceC0694k5, AbstractC3837z1 abstractC3837z1) {
        C0729q2 c0729q2C;
        if (c0767v2.M0()) {
            a(c0767v2.b(this.j.a()), interfaceC0694k5, abstractC3837z1);
        } else if (c0767v2.O0() && (c0729q2C = c(c0767v2, interfaceC0694k5)) != null) {
            c(c0729q2C, abstractC3837z1);
        }
    }

    private void a(C0729q2 c0729q2, InterfaceC0694k5 interfaceC0694k5) {
        c(c0729q2, this.h0.a(c0729q2, interfaceC0694k5));
    }

    private void a(final C0729q2 c0729q2, h4 h4Var, L0 l0) {
        BiConsumer l$$ExternalSyntheticLambda56;
        BiConsumer l$$ExternalSyntheticLambda57;
        if (this.A.a(c0729q2, l0)) {
            boolean z = q0;
            if (!z && this.b.a() && this.n.n1.n0 && !((C0680j) this.j.f()).f().c(c0729q2, this.j.D())) {
                throw new AssertionError("Class " + c0729q2.r0() + " was not a main dex root in the first round");
            }
            if (!z && this.j.e0().b(c0729q2)) {
                throw new AssertionError("Enum " + c0729q2.r0() + " has been unboxed but is still in the program.");
            }
            if (this.n.X0() && c0729q2.u1()) {
                throw new C1367Tb("Sealed classes are not supported as program classes when generating class files", c0729q2.getOrigin());
            }
            if (this.n.Q0) {
                l$$ExternalSyntheticLambda56 = new L$$ExternalSyntheticLambda42(this);
            } else {
                l$$ExternalSyntheticLambda56 = new L$$ExternalSyntheticLambda56(this);
            }
            for (com.android.tools.r8.graph.J3 j3 : c0729q2.e1()) {
                c(j3.b(), c0729q2, new L$$ExternalSyntheticLambda32(this), l$$ExternalSyntheticLambda56);
                c(j3.d(), c0729q2, new L$$ExternalSyntheticLambda32(this), l$$ExternalSyntheticLambda56);
            }
            if (c0729q2.G1()) {
                Iterator<X4> it = c0729q2.i1().iterator();
                while (it.hasNext()) {
                    a(it.next().a(), c0729q2);
                }
            } else {
                a(c0729q2.g1(), c0729q2);
            }
            com.android.tools.r8.graph.O2 o2B1 = c0729q2.b1();
            if (o2B1 != null) {
                C0684j2 c0684j2B = o2B1.b();
                if (this.n.P0) {
                    l$$ExternalSyntheticLambda57 = new L$$ExternalSyntheticLambda42(this);
                } else {
                    l$$ExternalSyntheticLambda57 = new L$$ExternalSyntheticLambda56(this);
                }
                if (c0684j2B != null) {
                    a(c0684j2B, c0729q2, new L$$ExternalSyntheticLambda32(this), l$$ExternalSyntheticLambda57);
                } else {
                    c(o2B1.a(), c0729q2, new L$$ExternalSyntheticLambda32(this), l$$ExternalSyntheticLambda57);
                }
            }
            AbstractC3837z1 abstractC3837z1A = AbstractC3837z1.a(c0729q2.d);
            Iterator<C0767v2> it2 = c0729q2.f1().iterator();
            while (it2.hasNext()) {
                b(c0729q2, it2.next());
            }
            C0767v2 c0767v2 = c0729q2.f;
            if (c0767v2 != null) {
                h4Var.a((h4) this.g0.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda60
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return L.d((C0767v2) obj);
                    }
                }));
                d(c0729q2.f, c0729q2);
            }
            m(c0729q2);
            l(c0729q2);
            a(c0729q2, h4Var, abstractC3837z1A);
            if (c0729q2.e(this.j)) {
                if (!q0 && !c0729q2.e(this.j)) {
                    throw new AssertionError();
                }
                C0729q2 c0729q2C = c0729q2;
                do {
                    if (c0729q2C.e(this.j)) {
                        c0729q2C = c(c0729q2C.f, (InterfaceC0694k5) c0729q2C);
                    } else {
                        if (!c0729q2C.q1()) {
                            break;
                        }
                        this.U.a(c0729q2C.Y1(), c0729q2C, abstractC3837z1A);
                        c(Y.c(), c0729q2C.Y1(), (C3754l1) C3762m1.x().g());
                        break;
                    }
                } while (c0729q2C != null);
            }
            a(c0729q2);
            a((T) new V(c0729q2));
            if (c(c0729q2)) {
                this.Y.b(c0729q2);
            }
            b((InterfaceC0694k5) c0729q2);
            if (c0729q2.y1()) {
                a(c0729q2, this.d0, new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda61
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return EnumC0608b0.a((InterfaceC0694k5) obj);
                    }
                });
                a(c0729q2, this.e0, new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda58
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return L.a((InterfaceC0694k5) obj);
                    }
                });
            }
            Set setA = this.o.a(c0729q2.getType());
            if (this.a && setA != null) {
                a(c0729q2, setA, (com.android.tools.r8.graph.O0) null);
            }
            this.c.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda59
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(c0729q2, (AbstractC2797pg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ EnumC0608b0 a(InterfaceC0694k5 interfaceC0694k5) {
        return EnumC0608b0.d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0729q2 c0729q2, AbstractC2797pg abstractC2797pg) {
        abstractC2797pg.a(c0729q2, this.U);
    }

    private void a(final C0729q2 c0729q2, IdentityHashMap identityHashMap, final Function function) {
        Map map = (Map) identityHashMap.remove(c0729q2.getType());
        if (map != null) {
            if (!q0 && !map.keySet().stream().allMatch(new Predicate() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return L.a(c0729q2, (C0633d0) obj);
                }
            })) {
                throw new AssertionError();
            }
            map.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda85
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a(function, (C0633d0) obj, (List) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0729q2 c0729q2, C0633d0 c0633d0) {
        return c0633d0.t0() == c0729q2.getType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final Function function, final C0633d0 c0633d0, List list) {
        list.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda71
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0633d0, function, (InterfaceC0694k5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0633d0 c0633d0, Function function, InterfaceC0694k5 interfaceC0694k5) {
        a(interfaceC0694k5, c0633d0, (EnumC0608b0) function.apply(interfaceC0694k5));
    }

    private void a(com.android.tools.r8.graph.F f) {
        if (!q0 && f.e0()) {
            throw new AssertionError();
        }
        final h4 h4Var = (h4) this.g0.computeIfAbsent(f.getType(), new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda145
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return L.b((C0767v2) obj);
            }
        });
        C0792w4 c0792w4Y = f.Y();
        Objects.requireNonNull(h4Var);
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda146
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                h4Var.a((com.android.tools.r8.graph.W0) obj);
            }
        };
        c0792w4Y.getClass();
        c0792w4Y.c(consumer, CM.b());
    }

    private void a(final C0729q2 c0729q2, final h4 h4Var, final AbstractC3837z1 abstractC3837z1) {
        c0729q2.h(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda76
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(abstractC3837z1, (o5) obj);
            }
        }, new Predicate() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda75
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(h4Var, c0729q2, (com.android.tools.r8.graph.W0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(h4 h4Var, C0729q2 c0729q2, com.android.tools.r8.graph.W0 w0) {
        C0680j c0680j;
        AbstractC0727q0 abstractC0727q0C;
        if (h4Var.a(w0) != 3 || (abstractC0727q0C = (c0680j = (C0680j) this.j.f()).c(c0729q2.d)) == null) {
            return false;
        }
        for (C0767v2 c0767v2 : abstractC0727q0C.g.a) {
            if (c0680j.a(w0, c0767v2)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x002b  */
    final void a(InterfaceC0694k5 interfaceC0694k5, C0633d0 c0633d0, EnumC0608b0 enumC0608b0) {
        boolean z;
        boolean zA;
        C0767v2 c0767v2T0 = c0633d0.t0();
        AbstractC0727q0 abstractC0727q0A = a(c0767v2T0, interfaceC0694k5);
        boolean z2 = abstractC0727q0A == null || abstractC0727q0A.I1();
        if (z2) {
            z = true;
        } else {
            if (this.A.a.contains(abstractC0727q0A.a0())) {
                z = true;
            } else {
                z = false;
            }
        }
        C3720g c3720g = this.q;
        if (c3720g != null && c3720g.a(c0633d0)) {
            if (!q0 && !this.b.c()) {
                throw new AssertionError();
            }
            zA = true;
        } else {
            zA = C3728h.a(this.j, interfaceC0694k5, c0633d0, z, enumC0608b0);
        }
        if (zA) {
            this.h0.b(c0633d0, interfaceC0694k5);
            c0633d0.b.a(this.j, new F(this, c0633d0, interfaceC0694k5));
        } else {
            if (z2) {
                return;
            }
            enumC0608b0.getClass();
            ((List) ((Map) (enumC0608b0 == EnumC0608b0.d ? this.e0 : this.d0).computeIfAbsent(c0767v2T0, new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda77
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return L.e((C0767v2) obj);
                }
            })).computeIfAbsent(c0633d0, new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda78
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return L.a((C0633d0) obj);
                }
            })).add(interfaceC0694k5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(C0633d0 c0633d0) {
        return new ArrayList();
    }

    private AbstractC0677i3 a(final com.android.tools.r8.graph.Y0 y0, final InterfaceC0694k5 interfaceC0694k5) {
        AbstractC0677i3 abstractC0677i3C = this.i.c(y0);
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda72
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(y0, interfaceC0694k5, (AbstractC0677i3.a) obj);
            }
        };
        abstractC0677i3C.a(consumer, consumer, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda73
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(y0, interfaceC0694k5, (AbstractC0629c3) obj);
            }
        });
        return abstractC0677i3C;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.Y0 y0, InterfaceC0694k5 interfaceC0694k5, AbstractC0677i3.a aVar) {
        l5 l5VarA = aVar.c().a(interfaceC0694k5);
        b(y0.B0(), l5VarA);
        b(y0.getType(), l5VarA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.Y0 y0, InterfaceC0694k5 interfaceC0694k5, AbstractC0629c3 abstractC0629c3) {
        this.P.add(y0);
        b(y0.B0(), (l5) interfaceC0694k5);
        b(y0.getType(), (l5) interfaceC0694k5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0684j2 c0684j2, InterfaceC0694k5 interfaceC0694k5, I4 i4) {
        a(c0684j2, interfaceC0694k5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0684j2 c0684j2, I4 i4, InterfaceC0694k5 interfaceC0694k5, AbstractC3837z1 abstractC3837z1, I4.a aVar) {
        a(c0684j2, i4.k(), interfaceC0694k5, abstractC3837z1);
        a(c0684j2, interfaceC0694k5, new L$$ExternalSyntheticLambda161(this), new L$$ExternalSyntheticLambda42(this));
    }

    private I4 a(final C0684j2 c0684j2, final InterfaceC0694k5 interfaceC0694k5, final AbstractC3837z1 abstractC3837z1, boolean z) {
        I4 i4B = this.i.b(c0684j2, z);
        i4B.a(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda45
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0684j2, interfaceC0694k5, abstractC3837z1, (I4) obj);
            }
        });
        return i4B;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0684j2 c0684j2, InterfaceC0694k5 interfaceC0694k5, AbstractC3837z1 abstractC3837z1, I4 i4) {
        if (i4.x()) {
            a(c0684j2, i4.c().a(interfaceC0694k5));
        } else {
            if (!q0 && !i4.h()) {
                throw new AssertionError();
            }
            a(c0684j2, i4.k(), interfaceC0694k5, abstractC3837z1);
            a(c0684j2, interfaceC0694k5, new L$$ExternalSyntheticLambda161(this), new L$$ExternalSyntheticLambda42(this));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC3837z1 abstractC3837z1, I4 i4) {
        if (i4.x()) {
            I4.c<?> cVarO = i4.o();
            if (cVarO.d().I1()) {
                return;
            }
            C0729q2 c0729q2A0 = cVarO.d().a0();
            com.android.tools.r8.graph.W0 w0R = cVarO.r();
            o5 o5Var = new o5(c0729q2A0, w0R);
            b(abstractC3837z1, o5Var);
            if (w0R.E0()) {
                h(c0729q2A0);
                c(abstractC3837z1, o5Var);
            }
        }
    }

    private void a(final C0684j2 c0684j2, final InterfaceC0694k5 interfaceC0694k5, final AbstractC3837z1 abstractC3837z1) {
        d(c0684j2, interfaceC0694k5, abstractC3837z1).a(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda165
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0684j2, abstractC3837z1, interfaceC0694k5, (I4) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0684j2 c0684j2, AbstractC3837z1 abstractC3837z1, InterfaceC0694k5 interfaceC0694k5, I4 i4) {
        if (i4.h()) {
            this.O.add(c0684j2);
            return;
        }
        if (i4.x() && i4.d().e0()) {
            o5 o5VarS = i4.o().s();
            b(abstractC3837z1, o5VarS);
            if (o5VarS.getAccessFlags().o()) {
                return;
            }
            c(abstractC3837z1, o5VarS);
            if (o5VarS.e().H1() && this.R.add(o5VarS.getReference())) {
                this.U.a(o5VarS, interfaceC0694k5, abstractC3837z1);
            }
        }
    }

    private void a(C0767v2 c0767v2, C0635d2 c0635d2) {
        C0729q2 c0729q2A;
        if (this.b.d() || (c0729q2A = C0729q2.a(b().c(c0767v2))) == null) {
            return;
        }
        AbstractC0727q0 abstractC0727q0G = b().f(c0767v2).G();
        if (abstractC0727q0G != null && abstractC0727q0G.f0()) {
            b(abstractC0727q0G, c0729q2A);
            return;
        }
        if (this.a) {
            final AbstractC3837z1 abstractC3837z1A = AbstractC3837z1.a(c0635d2.d);
            a(c0729q2A, abstractC3837z1A);
            this.i.a(c0729q2A, new com.android.tools.r8.utils.F3() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda140
                @Override // com.android.tools.r8.utils.F3
                public final void a(Object obj, Object obj2, Object obj3) {
                    this.f$0.a(abstractC3837z1A, (C0767v2) obj, (AbstractC0727q0) obj2, (Boolean) obj3);
                }
            });
        }
        if (!this.j.u().a(c0635d2) && this.b.c()) {
            StringDiagnostic stringDiagnostic = new StringDiagnostic("Library class " + c0635d2.d.r0() + (c0729q2A.isInterface() ? " implements " : " extends ") + "program class " + c0767v2.r0());
            if (this.a) {
                this.n.e.warning(stringDiagnostic);
            } else {
                this.n.e.error(stringDiagnostic);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC3837z1 abstractC3837z1, C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0, Boolean bool) {
        C0729q2 c0729q2A = C0729q2.a(b().c(c0767v2));
        if (c0729q2A != null) {
            a(c0729q2A, abstractC3837z1);
        }
    }

    private void a(C0729q2 c0729q2, AbstractC3837z1 abstractC3837z1) {
        final L0 l0A = this.h0.a(c0729q2, abstractC3837z1);
        a(c0729q2.a0(), new Supplier() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda17
            @Override // java.util.function.Supplier
            public final Object get() {
                return L.a(l0A);
            }
        });
        this.Y.b(c0729q2);
        if (this.n.k1()) {
            this.o.a(c0729q2);
        }
        c0729q2.e(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(l0A, (m5) obj);
            }
        }, CM.b());
        c0729q2.l(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(l0A, (o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(L0 l0, m5 m5Var) {
        this.Y.a(m5Var);
        if (this.n.k1()) {
            this.o.a(m5Var);
        }
        a(m5Var, l0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(L0 l0, o5 o5Var) {
        this.Y.b(o5Var);
        if (this.n.k1()) {
            this.o.a(o5Var);
        }
        d(l0, o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC0727q0 abstractC0727q0, l5 l5Var) {
        if (abstractC0727q0.e0()) {
            if (l5Var.O()) {
                InterfaceC0694k5 interfaceC0694k5I = l5Var.getContext().i();
                if (abstractC0727q0.e0()) {
                    C0729q2 c0729q2A0 = abstractC0727q0.a0();
                    c(c0729q2A0, this.h0.a(c0729q2A0, interfaceC0694k5I));
                    return;
                }
                return;
            }
            return;
        }
        b(abstractC0727q0, l5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(l5 l5Var, C0767v2 c0767v2, com.android.tools.r8.graph.V v) {
        c(c0767v2, v.a(l5Var));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0767v2 c0767v2, l5 l5Var) {
        this.I.b(c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0767v2 c0767v2, com.android.tools.r8.graph.V v) {
        this.I.b(c0767v2);
    }

    final void a(final C0729q2 c0729q2, final o5 o5Var, V0 v0, AbstractC3837z1 abstractC3837z1) {
        boolean z = q0;
        if (!z && c0729q2.e.T()) {
            throw new AssertionError();
        }
        if (!z && c0729q2.isInterface()) {
            throw new AssertionError();
        }
        this.c.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda82
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0729q2, o5Var, (AbstractC2797pg) obj);
            }
        });
        if (!z && c0729q2.isInterface()) {
            throw new AssertionError();
        }
        if (this.u.a(c0729q2, o5Var, v0, abstractC3837z1, this.i)) {
            c(c0729q2, this.h0.a(c0729q2, abstractC3837z1));
            h(c0729q2);
            if (!z && c0729q2.e.T()) {
                throw new AssertionError();
            }
            if (!z && c0729q2.isInterface()) {
                throw new AssertionError();
            }
            a(new S0(c0729q2), c0729q2.d, Collections.emptyList());
            C0729q2 c0729q2C = c0729q2;
            do {
                C3431yN c3431yN = (C3431yN) this.y.get(c0729q2C);
                if (c3431yN != null) {
                    AbstractC3837z1 abstractC3837z1A = AbstractC3837z1.a(c0729q2C.d);
                    Iterator it = c3431yN.iterator();
                    while (it.hasNext()) {
                        a((m5) it.next(), c0729q2C, abstractC3837z1A);
                    }
                }
                C0767v2 c0767v2 = c0729q2C.f;
                if (c0767v2 == null || (c0729q2C = c(c0767v2, (InterfaceC0694k5) c0729q2C)) == null) {
                    break;
                }
            } while (!this.u.c(c0729q2C));
            boolean z2 = q0;
            if (!z2 && c0729q2.e.T()) {
                throw new AssertionError();
            }
            if (!z2 && c0729q2.isInterface()) {
                throw new AssertionError();
            }
            k(c0729q2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0729q2 c0729q2, o5 o5Var, AbstractC2797pg abstractC2797pg) {
        abstractC2797pg.a(c0729q2.a0(), o5Var);
    }

    final void a(C0729q2 c0729q2, L0 l0) {
        boolean z = q0;
        if (!z && !c0729q2.e.T()) {
            throw new AssertionError();
        }
        if (this.u.a(c0729q2, this.i)) {
            c(c0729q2, l0);
            if (!z && !c0729q2.isInterface()) {
                throw new AssertionError();
            }
            k(c0729q2);
        }
    }

    private void a(C0767v2 c0767v2, o5 o5Var) {
        AbstractC0727q0 abstractC0727q0A = a(c0767v2, (InterfaceC0694k5) o5Var);
        if (abstractC0727q0A == null) {
            if (this.j.u().a(c0767v2)) {
                return;
            }
            this.n.e.warning(new StringDiagnostic("Lambda expression implements missing interface `" + c0767v2.r0() + "`", o5Var.getOrigin()));
            return;
        }
        if (abstractC0727q0A.isInterface() || this.j.u().a(c0767v2)) {
            return;
        }
        this.n.e.warning(new StringDiagnostic("Lambda expression expected to implement an interface, but found `" + c0767v2.r0() + "`", o5Var.getOrigin()));
    }

    private void a(final U0 u0, C0767v2 c0767v2, List list) {
        final com.android.tools.r8.utils.O3 o3H = com.android.tools.r8.utils.O3.h(c0767v2);
        o3H.a((Iterable) list);
        while (o3H.c()) {
            b().f((C0767v2) o3H.f()).b(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda162
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(u0, o3H, (AbstractC0727q0) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(U0 u0, com.android.tools.r8.utils.O3 o3, AbstractC0727q0 abstractC0727q0) {
        if (abstractC0727q0.e0()) {
            a(u0, abstractC0727q0.a0());
        } else {
            if (!q0 && !abstractC0727q0.I1()) {
                throw new AssertionError();
            }
            if (!this.b.d()) {
                for (com.android.tools.r8.graph.W0 w0 : abstractC0727q0.P1()) {
                    boolean z = q0;
                    if (!z && w0.L1()) {
                        throw new AssertionError();
                    }
                    a(u0, abstractC0727q0, this.i.g(abstractC0727q0, w0.getReference()));
                    u0.getClass();
                    if ((u0 instanceof S0) && this.j.t.a(w0.getReference().h, this.j)) {
                        C0684j2 c0684j2A = C3378xe.a(this.j, w0.getReference(), w0.J0());
                        if (!z && c0684j2A == w0.getReference()) {
                            throw new AssertionError();
                        }
                        a(u0, abstractC0727q0, this.i.g(u0.a(), c0684j2A));
                    }
                }
            }
        }
        C0767v2 c0767v2 = abstractC0727q0.f;
        if (c0767v2 != null) {
            o3.c(c0767v2);
        }
        o3.a((Iterable) abstractC0727q0.g);
    }

    private void a(final U0 u0, final C0729q2 c0729q2) {
        if (!q0) {
            u0.getClass();
            if (!(u0 instanceof T0) && !this.i.c(u0.a().getType(), c0729q2.d)) {
                throw new AssertionError();
            }
        }
        ((Map) this.X.getOrDefault(c0729q2, Collections.emptyMap())).forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda157
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c0729q2, u0, (M) obj, (CN) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final C0729q2 c0729q2, final U0 u0, M m, CN cn) {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        Iterator it = cn.iterator();
        while (it.hasNext()) {
            o5 o5Var = (o5) it.next();
            ((List) identityHashMap.computeIfAbsent(o5Var.a(), com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda89
                @Override // java.util.function.Supplier
                public final Object get() {
                    return L.m2128$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc();
                }
            }))).add(o5Var);
        }
        this.i.b(m.a, m.b).a(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda90
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(identityHashMap, c0729q2, u0, (I4) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Map map, final C0729q2 c0729q2, final U0 u0, I4 i4) {
        final I4.c<?> cVarO = i4.o();
        if (cVarO == null) {
            if (!q0) {
                throw new AssertionError("Should not be null");
            }
        } else {
            map.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda105
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a(cVarO, c0729q2, u0, (C0729q2) obj, (List) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final I4.c cVar, final C0729q2 c0729q2, final U0 u0, final C0729q2 c0729q3, final List list) {
        AbstractC0701l4 abstractC0701l4A = cVar.a(c0729q3, this.j, new com.android.tools.r8.graph.K3() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda84
            @Override // com.android.tools.r8.graph.K3
            public final void a(C0767v2 c0767v2, Consumer consumer, Consumer consumer2) {
                this.f$0.a(c0729q2, u0, c0767v2, consumer, consumer2);
            }
        }, new InterfaceC0679i5() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda86
            @Override // com.android.tools.r8.graph.InterfaceC0679i5
            public final boolean a(com.android.tools.r8.graph.O0 o0) {
                return this.f$0.a(o0);
            }
        });
        Consumer<? super InterfaceC0669h4> consumer = new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda87
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(cVar, (InterfaceC0707m4) obj);
            }
        };
        abstractC0701l4A.a(consumer, consumer);
        abstractC0701l4A.a(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda88
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0729q3, list, (com.android.tools.r8.graph.W0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0729q2 c0729q2, U0 u0, C0767v2 c0767v2, Consumer consumer, Consumer consumer2) {
        if (!q0 && !this.i.c(c0729q2.d, c0767v2)) {
            throw new AssertionError();
        }
        u0.a(consumer, consumer2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(com.android.tools.r8.graph.O0 o0) {
        C3730h1 c3730h1 = this.Y;
        return c3730h1.a(o0, this.i).c((K0) this.n);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final I4.c cVar, InterfaceC0707m4 interfaceC0707m4) {
        a(interfaceC0707m4, new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda48
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(cVar, (o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ L0 a(I4.c cVar, o5 o5Var) {
        return this.h0.a(cVar.r().getReference(), o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0729q2 c0729q2, List list, com.android.tools.r8.graph.W0 w0) {
        C0729q2 c0729q2C = c(w0.J0(), (InterfaceC0694k5) c0729q2);
        if (c0729q2C != null) {
            this.O.add(w0.getReference());
            Iterator it = list.iterator();
            while (it.hasNext()) {
                o5 o5Var = (o5) it.next();
                b(AbstractC3837z1.c(o5Var), new o5(c0729q2C, w0));
            }
        }
    }

    private void a(final U0 u0, final AbstractC0727q0 abstractC0727q0, I4 i4) {
        Set setEmptySet;
        Set<C0729q2> setEmptySet2;
        InterfaceC0707m4 interfaceC0707m4A = i4.a(u0, this.i);
        if (interfaceC0707m4A == null) {
            return;
        }
        if (!interfaceC0707m4A.n()) {
            o5 o5VarG0 = interfaceC0707m4A.f().p().g0();
            if (o5VarG0 == null) {
                return;
            }
            C0729q2 c0729q2A = o5VarG0.a();
            com.android.tools.r8.graph.W0 w0E = o5VarG0.e();
            if (!q0 && !w0E.S1()) {
                throw new AssertionError();
            }
            if (w0E.s1() || w0E.L1()) {
                return;
            }
            if (!this.j.h(c0729q2A.d)) {
                Set<AbstractC0727q0> setA = this.u.a(c0729q2A.d);
                if (setA == null) {
                    setEmptySet = Collections.emptySet();
                } else {
                    Set setB = com.android.tools.r8.utils.T2.b(setA.size());
                    for (AbstractC0727q0 abstractC0727q1 : setA) {
                        if (abstractC0727q1.e0()) {
                            setB.add(abstractC0727q1.a0());
                        }
                    }
                    setEmptySet = setB;
                }
                if (setEmptySet.isEmpty()) {
                    return;
                }
                ArrayDeque arrayDeque = new ArrayDeque(setEmptySet);
                Set setA2 = com.android.tools.r8.utils.T2.a((Iterable) setEmptySet);
                while (!arrayDeque.isEmpty()) {
                    C0729q2 c0729q2 = (C0729q2) arrayDeque.removeFirst();
                    if (!q0 && !setA2.contains(c0729q2)) {
                        throw new AssertionError();
                    }
                    if (c0729q2.d(w0E.getReference()) == null) {
                        if (!this.j.h(c0729q2.d)) {
                            Set<AbstractC0727q0> setA3 = this.u.a(c0729q2.d);
                            if (setA3 == null) {
                                setEmptySet2 = Collections.emptySet();
                            } else {
                                Set setB2 = com.android.tools.r8.utils.T2.b(setA3.size());
                                for (AbstractC0727q0 abstractC0727q2 : setA3) {
                                    if (abstractC0727q2.e0()) {
                                        setB2.add(abstractC0727q2.a0());
                                    }
                                }
                                setEmptySet2 = setB2;
                            }
                            for (C0729q2 c0729q3 : setEmptySet2) {
                                if (setA2.add(c0729q3)) {
                                    arrayDeque.add(c0729q3);
                                }
                            }
                        }
                    }
                }
                return;
            }
        }
        a(interfaceC0707m4A, new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda141
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(u0, abstractC0727q0, (o5) obj);
            }
        });
        u0.getClass();
        if (u0 instanceof S0) {
            a(u0.a(), interfaceC0707m4A.f().e().getReference());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ L0 a(U0 u0, AbstractC0727q0 abstractC0727q0, o5 o5Var) {
        return this.h0.a(u0, o5Var);
    }

    private void a(C0729q2 c0729q2, C0684j2 c0684j2) {
        final com.android.tools.r8.utils.O3 o3E = com.android.tools.r8.utils.O3.e();
        o3E.c(c0729q2);
        while (o3E.c()) {
            final C0729q2 c0729q3 = (C0729q2) o3E.f();
            o5 o5VarF = c0729q3.f(c0684j2);
            if (o5VarF != null) {
                if (!o5VarF.e().D1().d()) {
                    o5VarF.e().a(AbstractC3938n2.a);
                    com.android.tools.r8.utils.A1 a1 = this.n;
                    if (a1.K0 && a1.k1()) {
                        this.o.a(o5VarF);
                    }
                }
            }
            c0729q3.d(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda31
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(c0729q3, o3E, (C0767v2) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0729q2 c0729q2, com.android.tools.r8.utils.O3 o3, C0767v2 c0767v2) {
        C0729q2 c0729q2C = c(c0767v2, (InterfaceC0694k5) c0729q2);
        if (c0729q2C != null) {
            o3.c(c0729q2C);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final m5 m5Var, final InterfaceC0694k5 interfaceC0694k5, AbstractC3837z1 abstractC3837z1) {
        J j = this.T;
        j.getClass();
        com.android.tools.r8.graph.T0 t0E = m5Var.e();
        j.b.accept(t0E, abstractC3837z1);
        j.c.l(m5Var.a());
        if (j.a.add(t0E)) {
            if (m5Var.getAccessFlags().o()) {
                C0729q2 c0729q2A = m5Var.a();
                c(c0729q2A, this.h0.a(c0729q2A, m5Var));
                d(m5Var.getType(), m5Var);
                b((InterfaceC0694k5) m5Var);
                h(m5Var.a());
            } else if (!((C3431yN) this.y.getOrDefault(m5Var.a(), C3431yN.b())).b(m5Var)) {
                C0729q2 c0729q2A2 = m5Var.a();
                c(c0729q2A2, this.h0.a(c0729q2A2, m5Var));
                d(m5Var.getType(), m5Var);
                b((InterfaceC0694k5) m5Var);
            }
            a(m5Var);
            if (c(m5Var.a()) && !m5Var.e().O0()) {
                this.Y.a(m5Var);
            }
            this.c.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda47
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(m5Var, interfaceC0694k5, (AbstractC2797pg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(m5 m5Var, InterfaceC0694k5 interfaceC0694k5, AbstractC2797pg abstractC2797pg) {
        abstractC2797pg.a(m5Var, interfaceC0694k5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(m5 m5Var, AbstractC2797pg abstractC2797pg) {
        abstractC2797pg.a(m5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.Y0 y0, o5 o5Var, AbstractC0629c3 abstractC0629c3) {
        d(y0.B0(), (InterfaceC0694k5) o5Var);
        d(y0.getType(), (InterfaceC0694k5) o5Var);
        this.L.add(y0.B0());
    }

    public final boolean a(com.android.tools.r8.graph.T0 t0) {
        return this.T.a.contains(t0);
    }

    public final boolean a(com.android.tools.r8.graph.W0 w0) {
        return this.S.a.contains(w0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(o5 o5Var, o5 o5Var2) {
        if (a(o5Var.e())) {
            return;
        }
        this.l0.a(o5Var2, o5Var);
    }

    public final boolean a(InterfaceC0593a0 interfaceC0593a0) {
        boolean z = q0;
        if (interfaceC0593a0.isClass()) {
            AbstractC0727q0 abstractC0727q0AsClass = interfaceC0593a0.asClass();
            if (abstractC0727q0AsClass.e0()) {
                return d(abstractC0727q0AsClass.a0());
            }
            if (z || !abstractC0727q0AsClass.e0()) {
                return this.F.contains(abstractC0727q0AsClass);
            }
            throw new AssertionError();
        }
        if (!z && !interfaceC0593a0.A()) {
            throw new AssertionError();
        }
        if (interfaceC0593a0.b().e0()) {
            if (interfaceC0593a0.j0()) {
                m5 m5VarQ = interfaceC0593a0.Q();
                return c(m5VarQ) || b(m5VarQ.e());
            }
            if (!z && !interfaceC0593a0.h0()) {
                throw new AssertionError();
            }
            o5 o5VarG0 = interfaceC0593a0.g0();
            return a(o5VarG0.e()) || b(o5VarG0.e());
        }
        AbstractC0727q0 abstractC0727q0B = interfaceC0593a0.b();
        if (z || !abstractC0727q0B.e0()) {
            return this.F.contains(abstractC0727q0B);
        }
        throw new AssertionError();
    }

    public final void a(Consumer consumer) {
        Collections.unmodifiableSet(this.A.a).forEach(consumer);
    }

    private void a(final C0684j2 c0684j2, final boolean z, final o5 o5Var, final AbstractC3837z1 abstractC3837z1) {
        if (c0684j2.e.M0()) {
            a(c0684j2.e, o5Var, abstractC3837z1);
        } else {
            a(c0684j2, o5Var, abstractC3837z1, z).a(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda96
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(c0684j2, o5Var, z, abstractC3837z1, (I4) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0684j2 c0684j2, o5 o5Var, boolean z, AbstractC3837z1 abstractC3837z1, I4 i4) {
        if (i4.x()) {
            I4.c<?> cVarO = i4.o();
            C0729q2 c0729q2A0 = cVarO.a().a0();
            if (c0729q2A0 == null) {
                a(c0684j2, (l5) o5Var);
                return;
            }
            if (cVarO.d().I1()) {
                return;
            }
            C0729q2 c0729q2A = o5Var.a();
            M m = new M(c0684j2, z);
            CN cn = (CN) ((Map) this.X.getOrDefault(c0729q2A0, Collections.emptyMap())).get(m);
            if (cn != null) {
                cn.add(o5Var);
                this.h0.a(cVarO.r(), abstractC3837z1);
                return;
            }
            C0729q2 c0729q2A1 = cVarO.d().a0();
            final com.android.tools.r8.graph.W0 w0R = cVarO.r();
            b(abstractC3837z1, new o5(c0729q2A1, w0R));
            if (cVarO.b((InterfaceC0694k5) c0729q2A, this.j).a()) {
                return;
            }
            ((CN) ((Map) this.X.computeIfAbsent(c0729q2A0, C3888e1.a(new Supplier() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda35
                @Override // java.util.function.Supplier
                public final Object get() {
                    return L.m2124$r8$lambda$sUZQVfQ4XNh1oZg9vIcD2TcdVo();
                }
            }))).computeIfAbsent(m, C3888e1.a(new N2$$ExternalSyntheticLambda12()))).add(o5Var);
            AbstractC0701l4 abstractC0701l4A = cVarO.a(c0729q2A, this.j, new com.android.tools.r8.graph.K3() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda36
                @Override // com.android.tools.r8.graph.K3
                public final void a(C0767v2 c0767v2, Consumer consumer, Consumer consumer2) {
                    this.f$0.a(c0767v2, consumer, consumer2);
                }
            }, new InterfaceC0679i5() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda37
                @Override // com.android.tools.r8.graph.InterfaceC0679i5
                public final boolean a(com.android.tools.r8.graph.O0 o0) {
                    return this.f$0.b(o0);
                }
            });
            Consumer<? super InterfaceC0669h4> consumer = new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda38
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(w0R, (InterfaceC0707m4) obj);
                }
            };
            abstractC0701l4A.a(consumer, consumer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0767v2 c0767v2, Consumer consumer, Consumer consumer2) {
        this.u.a(c0767v2, consumer, consumer2, this.i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final com.android.tools.r8.graph.W0 w0, InterfaceC0707m4 interfaceC0707m4) {
        a(interfaceC0707m4, new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda148
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(w0, (o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ L0 a(com.android.tools.r8.graph.W0 w0, o5 o5Var) {
        return this.h0.a(w0.getReference(), o5Var);
    }

    private void a(final InterfaceC0707m4 interfaceC0707m4, final Function function) {
        interfaceC0707m4.a(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda117
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(function, (InterfaceC0669h4) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda118
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(function, (C0662g4) obj);
            }
        });
        this.c.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda119
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(interfaceC0707m4, (AbstractC2797pg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(InterfaceC0707m4 interfaceC0707m4, AbstractC2797pg abstractC2797pg) {
        abstractC2797pg.a(interfaceC0707m4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Function function, InterfaceC0669h4 interfaceC0669h4) {
        o5 o5VarG0 = interfaceC0669h4.p().g0();
        if (o5VarG0 == null || o5VarG0.e().s1()) {
            return;
        }
        L0 l0 = (L0) function.apply(o5VarG0);
        f(l0, o5VarG0);
        AbstractC0748t0 abstractC0748t0R = interfaceC0669h4.r();
        if (abstractC0748t0R == null || !(abstractC0748t0R instanceof o5)) {
            return;
        }
        b(l0, abstractC0748t0R.g0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Function function, C0662g4 c0662g4) {
        o5 o5VarG0 = c0662g4.b().g0();
        if (o5VarG0 != null) {
            this.U.a(o5VarG0, o5VarG0, (AbstractC3837z1) function.apply(o5VarG0));
        }
    }

    private void a(C0684j2 c0684j2, I4.a aVar, final InterfaceC0694k5 interfaceC0694k5, final AbstractC3837z1 abstractC3837z1) {
        C0729q2 c0729q2A;
        this.O.add(c0684j2);
        aVar.a(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda124
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(interfaceC0694k5, (C0767v2) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda125
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(interfaceC0694k5, abstractC3837z1, (com.android.tools.r8.graph.W0) obj);
            }
        });
        for (C0767v2 c0767v2 : c0684j2.a(this.j.a())) {
            if (c0767v2.O0() && (c0729q2A = C0729q2.a(a(c0767v2, interfaceC0694k5))) != null) {
                c(Y.c(), c0729q2A, (X0) ((X0) Y0.p().f()).g());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(InterfaceC0694k5 interfaceC0694k5, AbstractC3837z1 abstractC3837z1, com.android.tools.r8.graph.W0 w0) {
        C0729q2 c0729q2C = c(w0.J0(), interfaceC0694k5);
        if (c0729q2C != null) {
            this.O.add(w0.getReference());
            b(abstractC3837z1, new o5(c0729q2C, w0));
        }
    }

    private C0684j2 a(AbstractC0727q0 abstractC0727q0) {
        return this.j.a().a(abstractC0727q0.d, this.j.a().b(this.j.a().c(this.j.a().c("[" + abstractC0727q0.d.b1())), new C0767v2[0]), this.j.a().c(CompoundEntry.NAME_values));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC3837z1 abstractC3837z1, o5 o5Var, I4 i4) {
        if (i4.x()) {
            final I4.c<?> cVarO = i4.o();
            if (cVarO.d().e0()) {
                b(abstractC3837z1, new o5(cVarO.d().a0(), cVarO.r()));
            }
            AbstractC0748t0 abstractC0748t0D = cVarO.d(o5Var.a(), this.j);
            if (abstractC0748t0D == null) {
                this.O.add(cVarO.r().getReference());
                this.c.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(cVarO, (AbstractC2797pg) obj);
                    }
                });
                return;
            }
            if (abstractC0748t0D.a().a0() == null) {
                return;
            }
            o5 o5VarG0 = abstractC0748t0D.g0();
            if (((CN) this.x.computeIfAbsent(o5Var.e(), new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda9
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return L.c((com.android.tools.r8.graph.W0) obj);
                }
            })).add(o5VarG0)) {
                K k = this.S;
                k.getClass();
                if (k.a.contains(o5Var.e())) {
                    b(AbstractC3837z1.e(o5Var), o5VarG0);
                    if (abstractC0748t0D.getAccessFlags().W()) {
                        return;
                    }
                    f(AbstractC3837z1.e(o5Var), o5VarG0);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(I4.c cVar, AbstractC2797pg abstractC2797pg) {
        abstractC2797pg.a(cVar.r());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(L1 l1, com.android.tools.r8.graph.W0 w0) {
        l1.a(w0.getReference());
    }

    public C3690b0 a(C3686a4 c3686a4, ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) throws ExecutionException {
        this.o = c3686a4;
        AN an = c3686a4.f;
        AN an2 = this.l0;
        Objects.requireNonNull(an2);
        an.a(new L$$ExternalSyntheticLambda127(an2));
        y3Var.a("Register analysis");
        if (this.b.e() && this.j.Y().M0() && !this.n.s1().a) {
            this.c.add(new C1393Tz(this.j, this.r, this.K));
        }
        if (this.j.Y().D0() != null && this.j.Y().D0().h().e) {
            this.c.add(new C0801x3(this.r));
        }
        if (this.n.a().a) {
            this.c.add(new C2893r2(this.j));
        }
        y3Var.c();
        y3Var.a("Transfer minimum keep info");
        a(c3686a4);
        y3Var.c();
        if (this.b.c()) {
            y3Var.a("Model library");
            AbstractC3920k0.a(this.j);
            y3Var.c();
        } else if (this.j.v() != null) {
            y3Var.a("Retain keep info");
            final X xD = X.d();
            this.j.v().a(this.j, new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda39
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.c(xD, (C0729q2) obj, (X0) obj2);
                }
            }, new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda40
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.c(xD, (m5) obj, (C3698c1) obj2);
                }
            }, new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda41
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a((o5) obj, (C3754l1) obj2);
                }
            });
            y3Var.c();
        }
        y3Var.a("Enqueue all");
        e();
        y3Var.c();
        y3Var.a("Trace");
        a(executorService, y3Var);
        y3Var.c();
        this.n.e.a();
        y3Var.a("Finalize library override");
        Iterator it = Collections.unmodifiableSet(this.A.a).iterator();
        while (it.hasNext()) {
            for (com.android.tools.r8.graph.W0 w0 : ((C0729q2) it.next()).P1()) {
                if (w0.D1().e()) {
                    w0.a(AbstractC3938n2.b);
                }
            }
        }
        y3Var.c();
        y3Var.a("Finish analysis");
        this.c.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda43
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((AbstractC2797pg) obj);
            }
        });
        y3Var.c();
        if (!q0 && this.j.Y().n1.x0) {
            for (C0729q2 c0729q2 : Collections.unmodifiableSet(this.A.a)) {
                if (!q0 && !this.h0.a(c0729q2)) {
                    throw new AssertionError();
                }
            }
        }
        y3Var.a("Finish compat building");
        if (this.b.c() && this.a) {
            this.j.a(this.V.a());
        } else if (!q0 && this.V != null) {
            throw new AssertionError();
        }
        y3Var.c();
        a aVar = this.b;
        aVar.getClass();
        if (aVar == a.f) {
            return null;
        }
        y3Var.a("Create result");
        C3690b0 c3690b0A = a(this.i, y3Var);
        this.o0.a(this.j);
        y3Var.c();
        return c3690b0A;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC2797pg abstractC2797pg) {
        abstractC2797pg.a(this);
    }

    private void a(AbstractC3695b4 abstractC3695b4) {
        abstractC3695b4.a().a(this.j, new com.android.tools.r8.utils.F3() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda136
            @Override // com.android.tools.r8.utils.F3
            public final void a(Object obj, Object obj2, Object obj3) {
                this.f$0.d((Y) obj, (C0729q2) obj2, (X0) obj3);
            }
        }, new com.android.tools.r8.utils.F3() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda138
            @Override // com.android.tools.r8.utils.F3
            public final void a(Object obj, Object obj2, Object obj3) {
                this.f$0.d((Y) obj, (m5) obj2, (C3698c1) obj3);
            }
        }, new com.android.tools.r8.utils.F3() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda139
            @Override // com.android.tools.r8.utils.F3
            public final void a(Object obj, Object obj2, Object obj3) {
                this.f$0.d((Y) obj, (o5) obj2, (C3754l1) obj3);
            }
        });
    }

    private void a(C0729q2 c0729q2) {
        final X0 x0A = this.Z.a(X.d(), c0729q2.getType());
        if (x0A != null) {
            this.Y.a(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda137
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((X0) obj).a(x0A);
                }
            }, c0729q2);
            if ((this.n.o1() || this.b.d()) && !x0A.m()) {
                boolean z = q0;
                if (!z) {
                    x0A.a(this.n);
                }
                if (!z) {
                    x0A.a(this.n);
                }
                b();
                a(c0729q2, x0A.c, (com.android.tools.r8.graph.O0) null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void c(Y y, C0729q2 c0729q2, final X0 x0) {
        if (this.A.a.contains(c0729q2)) {
            this.Y.a(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda34
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((X0) obj).a(x0);
                }
            }, c0729q2);
        } else {
            this.Z.a(X.d()).a((AbstractC0744s2) c0729q2.getType()).a(x0);
        }
        if ((this.n.o1() || this.b.d()) && !x0.m()) {
            boolean z = q0;
            if (!z) {
                x0.a(this.n);
            }
            if (!z) {
                x0.a(this.n);
            }
            a(c0729q2, x0.c, y.a(b()));
        }
    }

    private void a(m5 m5Var) {
        final C3698c1 c3698c1A = this.Z.a(X.d(), m5Var.getReference());
        if (c3698c1A != null) {
            this.Y.a(m5Var, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda129
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((C3698c1) obj).a(c3698c1A);
                }
            });
            if ((this.n.o1() || this.b.d()) && !c3698c1A.m()) {
                boolean z = q0;
                if (!z) {
                    c3698c1A.a(this.n);
                }
                if (!z) {
                    c3698c1A.a(this.n);
                }
                b();
                this.U.a(this.h0.a(null, c3698c1A.b, c3698c1A.c, m5Var.e()), m5Var);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void c(Y y, m5 m5Var, final C3698c1 c3698c1) {
        J j = this.T;
        j.getClass();
        if (j.a.contains(m5Var.e())) {
            this.Y.a(m5Var, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda44
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((C3698c1) obj).a(c3698c1);
                }
            });
        } else {
            this.Z.a(X.d()).a((AbstractC0744s2) m5Var.getReference()).a(c3698c1);
        }
        if ((this.n.o1() || this.b.d()) && !c3698c1.m()) {
            boolean z = q0;
            if (!z) {
                c3698c1.a(this.n);
            }
            if (!z) {
                c3698c1.a(this.n);
            }
            this.U.a(this.h0.a(y.a(b()), c3698c1.b, c3698c1.c, m5Var.e()), m5Var);
        }
    }

    private void a(o5 o5Var) {
        X xD = X.d();
        final C3754l1 c3754l1A = this.Z.a(xD, o5Var.getReference());
        if (c3754l1A != null) {
            this.Y.a(o5Var, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda26
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((C3754l1) obj).a(c3754l1A);
                }
            });
            b(xD, o5Var, c3754l1A);
        }
    }

    public final void a(o5 o5Var, C3754l1 c3754l1) {
        c(Y.c(), o5Var, c3754l1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:8:0x0041  */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void c(Y y, o5 o5Var, final C3754l1 c3754l1) {
        K k = this.S;
        k.getClass();
        if (!k.a.contains(o5Var.e())) {
            K k2 = this.N;
            k2.getClass();
            if (!k2.a.contains(o5Var.e())) {
                this.Z.a(X.d()).a((AbstractC0744s2) o5Var.getReference()).a(c3754l1);
            } else {
                this.Y.a(o5Var, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((C3754l1) obj).a(c3754l1);
                    }
                });
            }
        } else {
            this.Y.a(o5Var, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((C3754l1) obj).a(c3754l1);
                }
            });
        }
        b(y, o5Var, c3754l1);
    }

    private void a(final T t) {
        S1 s1B = this.Z.b(t);
        if (s1B != null) {
            s1B.a(this.j, new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda142
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.b(t, (C0729q2) obj, (X0) obj2);
                }
            }, new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda143
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.b(t, (m5) obj, (C3698c1) obj2);
                }
            }, new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda144
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.c(t, (o5) obj, (C3754l1) obj2);
                }
            });
        }
    }

    private void a(final O o) {
        if (this.j0.isEmpty() && this.k0.isEmpty()) {
            return;
        }
        this.j0.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda167
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                o.b((o5) obj);
            }
        });
        for (o5 o5Var : this.k0) {
            if (this.i0.a(o5Var)) {
                this.j0.add(o5Var);
            }
        }
        BiConsumer biConsumerAndThen = new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda168
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((HA) obj, (o5) obj2);
            }
        };
        if (!this.n.o1()) {
            biConsumerAndThen = biConsumerAndThen.andThen(new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda169
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a(o, (HA) obj, (o5) obj2);
                }
            });
        }
        BiConsumer biConsumer = biConsumerAndThen;
        C0702m c0702m = this.j;
        final G7 g7A = C2458lN.a(c0702m, this.o0, new F7(c0702m, biConsumer, new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C1096Jc) obj, (o5) obj2);
            }
        }, new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.b((o5) obj, (o5) obj2);
            }
        }, o, new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((o5) obj, (o5) obj2);
            }
        }));
        final C3124uN c3124uN = new C3124uN();
        C3879c3.a(this.j0, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(g7A, c3124uN, (o5) obj);
            }
        }, this.l);
        c3124uN.a(this.l);
        C3879c3.a(this.j0, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(o, g7A, (o5) obj);
            }
        }, this.l);
        for (o5 o5Var2 : this.k0) {
            if (!q0 && this.m0 == null) {
                throw new AssertionError();
            }
            o5 o5VarB = this.m0.b().b(o5Var2, (InterfaceC1720bs) g7A);
            this.m0.a(o5Var2, o5VarB);
            this.l0.c(o5VarB);
            if (!a(o5VarB.e())) {
                o.a(o5VarB);
            }
            o.c.add(o5VarB);
        }
        List listA = g7A.a();
        if (!q0 && !listA.isEmpty()) {
            throw new AssertionError();
        }
        this.k0.clear();
        this.j0.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(O o, HA ha, o5 o5Var) {
        for (C0767v2 c0767v2 : ha.d().f1()) {
            C0680j c0680jB = b();
            c0680jB.getClass();
            if (c0680jB.b(o5Var.a(), c0767v2) == null) {
                for (o5 o5Var2 : ha.d().k2()) {
                    synchronized (o) {
                        o.a(o5Var2);
                    }
                }
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(G7 g7, C3124uN c3124uN, o5 o5Var) {
        this.i0.a(g7, c3124uN, o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(O o, G7 g7, o5 o5Var) {
        this.i0.a(o.c(o5Var), o5Var, g7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(HA ha, o5 o5Var) {
        synchronized (this.z) {
            this.z.put(ha.d(), o5Var);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C1096Jc c1096Jc, o5 o5Var) {
        synchronized (this.z) {
            this.z.put(c1096Jc.a(), o5Var);
        }
    }

    private C3690b0 a(C0680j c0680j, com.android.tools.r8.utils.y3 y3Var) {
        U1 u1A;
        y3Var.a("Remove dead protos");
        this.H.removeIf(new Predicate() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda65
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.d((C0729q2) obj);
            }
        });
        final Set setB = com.android.tools.r8.utils.T2.b(this.J.size() + this.H.size());
        this.H.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda66
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                L.a(setB, (C0729q2) obj);
            }
        });
        setB.addAll(this.J);
        y3Var.c();
        y3Var.a("Prune field access mappings");
        this.s.a(new BiPredicate() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda67
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return L.a((com.android.tools.r8.graph.Y0) obj, (com.android.tools.r8.graph.U2) obj2);
            }
        });
        boolean z = q0;
        if (!z) {
            this.s.c();
        }
        y3Var.c();
        if (!z) {
            AbstractC0681j0 abstractC0681j0B = c0680j.b();
            com.android.tools.r8.utils.O3 o3E = com.android.tools.r8.utils.O3.e();
            Iterator it = Collections.unmodifiableSet(this.A.a).iterator();
            while (it.hasNext()) {
                o3E.c((C0729q2) it.next());
            }
            while (o3E.c()) {
                AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) o3E.f();
                if (!q0) {
                    a(abstractC0727q0, o3E, abstractC0681j0B);
                }
            }
        }
        y3Var.a("Prune dead items");
        this.o.a(this.j, this, y3Var);
        if (this.b.e() && this.j.I()) {
            if (!z && this.o == this.j.y()) {
                throw new AssertionError();
            }
            this.j.y().a(this.j, this, y3Var);
        }
        y3Var.c();
        y3Var.a("Ensure static factory references");
        this.j.a().b(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda68
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.f((C0767v2) obj);
            }
        });
        y3Var.c();
        y3Var.a("Rebuild application");
        Set setC = OU.c();
        Set setC2 = OU.c();
        this.G.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda69
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.T) obj);
            }
        });
        for (com.android.tools.r8.graph.F f : this.F) {
            if (f.f0()) {
                setC.add(f.d0());
            } else if (((AbstractC0727q0) f) instanceof C0754u0) {
                setC2.add(f.m());
            } else if (!q0) {
                throw new AssertionError();
            }
        }
        com.android.tools.r8.graph.N2 n2E = c0680j.b().b().c().a((Collection<C0635d2>) setC).a(setC2).b();
        y3Var.c();
        if (!q0) {
            com.android.tools.r8.utils.O3 o3E2 = com.android.tools.r8.utils.O3.e();
            Iterator it2 = Collections.unmodifiableSet(this.A.a).iterator();
            while (it2.hasNext()) {
                o3E2.c((C0729q2) it2.next());
            }
            while (o3E2.c()) {
                AbstractC0727q0 abstractC0727q1 = (AbstractC0727q0) o3E2.f();
                if (!q0) {
                    a(abstractC0727q1, o3E2, n2E);
                }
            }
        }
        com.android.tools.r8.synthesis.A a2 = new com.android.tools.r8.synthesis.A() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda70
            @Override // com.android.tools.r8.synthesis.A
            public final Set a(C0729q2 c0729q2) {
                return this.f$0.e(c0729q2);
            }
        };
        a();
        y3Var.a("Rewrite with deferred results");
        this.k.a(this.l);
        y3Var.c();
        y3Var.a("Create app info with liveness");
        C3842a c3842aA = c0680j.g().a(n2E);
        C1121Ka c1121KaP = c0680j.p();
        N1 n1F = c0680j.f();
        if (this.b.c()) {
            u1A = this.I.b(this.j, a2);
        } else {
            u1A = this.I.a(this.j);
        }
        U1 u1 = u1A;
        Set setA = com.android.tools.r8.utils.T2.a(Collections.unmodifiableSet(this.A.a), new d$$ExternalSyntheticLambda0());
        Set setUnmodifiableSet = Collections.unmodifiableSet(this.N.a);
        Set setC3 = OU.c();
        Iterator it3 = setUnmodifiableSet.iterator();
        while (it3.hasNext()) {
            setC3.add(((com.android.tools.r8.graph.U0) it3.next()).getReference());
        }
        Set set = this.O;
        Set set2 = this.P;
        Set set3 = this.Q;
        Set set4 = this.R;
        Set setUnmodifiableSet2 = Collections.unmodifiableSet(this.S.a);
        Set setC4 = OU.c();
        Iterator it4 = setUnmodifiableSet2.iterator();
        while (it4.hasNext()) {
            setC4.add(((com.android.tools.r8.graph.U0) it4.next()).getReference());
        }
        com.android.tools.r8.graph.T2 t2 = this.s;
        C0762u4 c0762u4A = this.t.a();
        C0606a5 c0606a5A = this.u.a(c0680j);
        IdentityHashMap identityHashMap = this.v;
        C3730h1 c3730h1 = this.Y;
        C3686a4 c3686a4 = this.o;
        Map map = c3686a4.r;
        Set setA2 = a(c3686a4.h);
        Set setA3 = a(this.o.a);
        Set setA4 = a(this.o.j);
        Set setA5 = a(this.o.k);
        Set setA6 = a(this.o.l);
        C3686a4 c3686a5 = this.o;
        C3984w2 c3984w2 = c3686a5.m;
        Set set5 = c3686a5.b;
        Set set6 = this.L;
        Set set7 = c3686a5.o;
        Set set8 = c3686a5.p;
        Set set9 = c3686a5.q;
        Set set10 = c3686a5.s;
        Set set11 = this.w;
        C2769pI c2769pI = new C2769pI();
        Iterator it5 = set10.iterator();
        while (it5.hasNext()) {
            Iterator it6 = it5;
            c2769pI.putIfAbsent((AbstractC0642e2) it5.next(), Boolean.TRUE);
            set4 = set4;
            it5 = it6;
        }
        Set set12 = set4;
        Iterator it7 = set11.iterator();
        while (it7.hasNext()) {
            c2769pI.putIfAbsent((AbstractC0642e2) it7.next(), Boolean.FALSE);
        }
        C3733i c3733i = new C3733i(c3842aA, c1121KaP, n1F, u1, setB, setA, setC3, set, set2, set3, set12, setC4, t2, c0762u4A, c0606a5A, identityHashMap, c3730h1, map, setA2, setA3, setA4, setA5, setA6, c3984w2, set5, set6, set7, set8, set9, c2769pI, Collections.emptySet(), Collections.emptyMap(), this.a0, this.b0, this.c0);
        y3Var.c();
        c0680j.k();
        BiConsumer<C3733i, a> biConsumer = this.n.n1.p;
        if (biConsumer != null) {
            biConsumer.accept(c3733i, this.b);
        }
        return new C3690b0(c3733i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, C0729q2 c0729q2) {
        set.add(c0729q2.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.U2 u2) {
        return y0 != u2.e() || u2 == com.android.tools.r8.graph.U2.e;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.T t) {
        L$$ExternalSyntheticLambda123 l$$ExternalSyntheticLambda123 = new L$$ExternalSyntheticLambda123(this);
        com.android.tools.r8.utils.O3 o3A = com.android.tools.r8.utils.O3.a(this.F, t);
        while (o3A.c()) {
            a((com.android.tools.r8.graph.T) o3A.f(), o3A, (BiConsumer) l$$ExternalSyntheticLambda123, false);
        }
    }

    private void a() {
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda74
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C0684j2) obj, (C0684j2) obj2);
            }
        };
        C2569ms c2569ms = this.m0;
        if (c2569ms != null) {
            c2569ms.a(biConsumer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0684j2 c0684j2, C0684j2 c0684j3) {
        com.android.tools.r8.graph.W0 w0C;
        o5 o5VarG0 = this.j.b(c0684j3).g0();
        C3730h1 c3730h1 = this.Y;
        C0680j c0680j = this.i;
        c3730h1.getClass();
        C0729q2 c0729q2A = C0729q2.a(c0680j.a(c0684j2.e));
        C3762m1 c3762m1I = (c0729q2A == null || (w0C = c0729q2A.c(c0684j2)) == null) ? C3762m1.i() : c3730h1.a(c0729q2A, w0C);
        this.Y.a(o5VarG0, c3762m1I.w().a(this.Z.a(X.d(), S1.a()).a((AbstractC0744s2) c0684j2, C3762m1.x()).c()));
    }

    private Set a(final Set set) {
        if (!set.isEmpty() && this.m0 != null) {
            final Set setC = OU.c();
            this.m0.a(new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda24
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    L.a(set, setC, (C0684j2) obj, (C0684j2) obj2);
                }
            });
            set.addAll(setC);
        }
        return set;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, Set set2, C0684j2 c0684j2, C0684j2 c0684j3) {
        if (set.contains(c0684j2)) {
            set2.add(c0684j3);
        }
    }

    private boolean a(C0767v2 c0767v2, com.android.tools.r8.utils.O3 o3, AbstractC0681j0 abstractC0681j0) {
        if (c0767v2.M0()) {
            c0767v2 = c0767v2.b(this.j.a());
        }
        if (!c0767v2.O0()) {
            return true;
        }
        AbstractC0727q0 abstractC0727q0C = abstractC0681j0.c(c0767v2);
        if (abstractC0727q0C == null) {
            if (!q0 && !this.I.a(c0767v2)) {
                throw new AssertionError("Expected type to be in missing types': " + c0767v2);
            }
        } else {
            boolean z = q0;
            if (!z && this.I.a(c0767v2)) {
                throw new AssertionError("Type with definition also in missing types: " + c0767v2);
            }
            if (!z && !abstractC0727q0C.e0() && !this.F.contains(abstractC0727q0C)) {
                throw new AssertionError("Expected type to be in live non-program types: " + abstractC0727q0C);
            }
            o3.c(abstractC0727q0C);
        }
        return true;
    }

    private void a(AbstractC0727q0 abstractC0727q0, com.android.tools.r8.utils.O3 o3, AbstractC0681j0 abstractC0681j0) {
        boolean z;
        for (C0767v2 c0767v2 : abstractC0727q0.H0()) {
            if (!q0 && !a(c0767v2, o3, abstractC0681j0)) {
                throw new AssertionError();
            }
        }
        if (!q0 && !abstractC0727q0.e0() && !this.F.contains(abstractC0727q0)) {
            throw new AssertionError("Expected type to be in live non-program types: " + abstractC0727q0);
        }
        if (abstractC0727q0.e0()) {
            for (com.android.tools.r8.graph.T0 t0 : abstractC0727q0.W0()) {
                if (b(t0) && !q0 && !a(t0.getReference().h, o3, abstractC0681j0)) {
                    throw new AssertionError();
                }
            }
            for (com.android.tools.r8.graph.W0 w0 : abstractC0727q0.M1()) {
                if (b(w0) && !(z = q0)) {
                    if (!z && !a(w0.getReference().h.e, o3, abstractC0681j0)) {
                        throw new AssertionError();
                    }
                    for (C0767v2 c0767v3 : w0.getReference().h.f.a) {
                        if (!q0 && !a(c0767v3, o3, abstractC0681j0)) {
                            throw new AssertionError();
                        }
                    }
                }
            }
        }
    }

    private void a(ExecutorService executorService, final com.android.tools.r8.utils.y3 y3Var) {
        y3Var.a("Grow the tree.");
        while (true) {
            try {
                long size = ((long) Collections.unmodifiableSet(this.A.a).size()) + ((long) this.S.a.size()) + ((long) this.T.a.size());
                while (!this.U.b.isEmpty()) {
                    ((AbstractC3709e0) this.U.b.poll()).a(this);
                }
                long size2 = ((long) Collections.unmodifiableSet(this.A.a).size()) + ((long) this.S.a.size()) + ((long) this.T.a.size());
                if (size2 > size) {
                    if (this.f0 == null) {
                        this.f0 = new HashMap();
                        N0 n0 = new N0();
                        for (C3712e3 c3712e3 : this.o.t) {
                            ((Set) this.f0.computeIfAbsent(n0.c(c3712e3), new Function() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda51
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    return L.a((C2173hh) obj);
                                }
                            })).add(c3712e3);
                        }
                    }
                    a(new O0(this.j, this.m, this, executorService, this.f0, W3.a(this.j, this, this.m)).a());
                    if (!q0 && ((long) Collections.unmodifiableSet(this.A.a).size()) + ((long) this.S.a.size()) + ((long) this.T.a.size()) != size2) {
                        throw new AssertionError();
                    }
                    if (!this.U.b.isEmpty()) {
                    }
                }
                if (!this.W.isEmpty()) {
                    this.W.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda52
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            this.f$0.b((o5) obj);
                        }
                    });
                    this.W.clear();
                }
                if (this.U.b.isEmpty()) {
                    if (this.k.a(this.U)) {
                        if (!q0 && this.U.b.isEmpty()) {
                            throw new AssertionError();
                        }
                    } else {
                        this.c.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda54
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                this.f$0.a(y3Var, (AbstractC2797pg) obj);
                            }
                        });
                        if (this.U.b.isEmpty()) {
                            for (C3835z c3835z : this.o.e) {
                                c3835z.getClass();
                                a(c3835z);
                            }
                            o();
                            W3 w3C = c();
                            a(w3C);
                            this.o.a().a(w3C.a());
                            this.o.e.clear();
                            if (this.U.b.isEmpty()) {
                                if (this.b.c()) {
                                    n();
                                }
                                y3Var.c();
                                return;
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                y3Var.c();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(C2173hh c2173hh) {
        return new LinkedHashSet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.utils.y3 y3Var, AbstractC2797pg abstractC2797pg) {
        abstractC2797pg.a(this, this.U, y3Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2, C0767v2 c0767v2) {
        T1 t1 = this.I;
        new InterfaceDesugarMissingTypeDiagnostic(c0729q2.c, Position.UNKNOWN, c0767v2.B0(), c0729q2.getType().B0(), null);
        t1.a(c0767v2, c0729q2);
    }

    private void a(W3 w3) {
        this.o.a(w3);
        a((AbstractC3695b4) w3);
        AN an = w3.f;
        AN an2 = this.l0;
        Objects.requireNonNull(an2);
        an.a(new L$$ExternalSyntheticLambda127(an2));
        if (this.a) {
            w3.d.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda128
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a((C0767v2) obj, (Set) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0767v2 c0767v2, Set set) {
        if (!q0) {
            c0767v2.getClass();
        }
        C0680j c0680jB = b();
        c0767v2.getClass();
        C0729q2 c0729q2A = C0729q2.a(c0680jB.c(c0767v2));
        if (!this.a || set == null) {
            return;
        }
        a(c0729q2A, set, (com.android.tools.r8.graph.O0) null);
    }

    private void a(C3835z c3835z) {
        o5 o5VarB = c3835z.b();
        o5 o5VarC = c3835z.c();
        if (this.o.a(o5VarC, this.n) || o5VarB == o5VarC || this.p0.containsKey(o5VarB.e().getReference())) {
            return;
        }
        this.p0.put(o5VarB.e().getReference(), c3835z);
    }

    final void a(List list) {
        if (!q0 && !this.b.c()) {
            throw new AssertionError();
        }
        if (this.q != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                C3708e c3708e = (C3708e) it.next();
                this.q.b(c3708e.c());
                this.U.a(c3708e.a(), c3708e.c(), c3708e.b());
            }
        }
    }

    final void a(m5 m5Var, AbstractC3837z1 abstractC3837z1) {
        com.android.tools.r8.graph.T0 t0E = m5Var.e();
        com.android.tools.r8.graph.U2 u2C = this.s.b(t0E.getReference());
        if (u2C == null) {
            u2C = new com.android.tools.r8.graph.U2(t0E.getReference());
            this.s.a(t0E.getReference(), u2C);
        }
        u2C.t();
        u2C.u();
        if (m5Var.e().E0()) {
            a(m5Var, m5Var, abstractC3837z1);
        } else {
            this.U.a(m5Var, m5Var, abstractC3837z1);
        }
    }

    /* JADX WARN: Code duplicated, block: B:47:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:49:0x00d8  */
    /* JADX WARN: Code duplicated, block: B:50:0x00de  */
    final void a(final o5 o5Var, final InterfaceC0694k5 interfaceC0694k5) {
        boolean z = q0;
        if (!z) {
            K k = this.S;
            k.getClass();
            if (!k.a.contains(o5Var.e())) {
                throw new AssertionError();
            }
        }
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        if (!z && w0E.i1().g()) {
            throw new AssertionError();
        }
        if (w0E.E0()) {
            h(o5Var.a());
        }
        if (this.b.c()) {
            if (this.n.Z0()) {
                if (o5Var.a().isInterface() && o5Var.e().F1() && !o5Var.e().A1()) {
                    if (!com.android.tools.r8.graph.L3.a(o5Var.e().a1())) {
                        this.k0.add(o5Var);
                    }
                } else {
                    o5 o5Var2 = (o5) this.l0.b(o5Var);
                    if (o5Var2 != null) {
                        if (!z && !com.android.tools.r8.graph.L3.a(o5Var.e().a1())) {
                            throw new AssertionError();
                        }
                        if (!z && com.android.tools.r8.graph.L3.a(o5Var2.e().a1())) {
                            throw new AssertionError();
                        }
                        this.k0.add(o5Var2);
                    } else if (this.i0.a(o5Var)) {
                        this.j0.add(o5Var);
                    } else {
                        f(o5Var);
                    }
                }
            } else if (this.i0.a(o5Var)) {
                this.j0.add(o5Var);
            } else {
                f(o5Var);
            }
        } else {
            f(o5Var);
        }
        CN<o5> cn = (CN) this.x.get(o5Var.e());
        if (cn != null) {
            for (o5 o5Var3 : cn) {
                b(AbstractC3837z1.e(o5Var), o5Var3);
                f(AbstractC3837z1.e(o5Var), o5Var3);
            }
        }
        this.c.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda158
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(o5Var, interfaceC0694k5, (AbstractC2797pg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(o5 o5Var, InterfaceC0694k5 interfaceC0694k5, AbstractC2797pg abstractC2797pg) {
        abstractC2797pg.a(o5Var, interfaceC0694k5, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(o5 o5Var, AbstractC2797pg abstractC2797pg) {
        abstractC2797pg.a(o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(o5 o5Var, C0633d0 c0633d0) {
        a(o5Var, c0633d0, EnumC0608b0.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(o5 o5Var, C3822x c3822x, AbstractC2797pg abstractC2797pg) {
        abstractC2797pg.a(o5Var, c3822x);
    }

    private void a(C0729q2 c0729q2, Supplier supplier) {
        boolean z = q0;
        if (!z && !this.a) {
            throw new AssertionError();
        }
        if (!z && !this.a) {
            throw new AssertionError();
        }
        if (this.b.c()) {
            this.V.a(c0729q2);
        } else {
            if (!z && this.V != null) {
                throw new AssertionError();
            }
            if (!this.j.J() || !this.j.B().a(c0729q2)) {
                return;
            }
        }
        L0 l0A = this.h0.a(c0729q2, (AbstractC3837z1) supplier.get());
        if (c0729q2.e.T()) {
            c(c0729q2, l0A);
            return;
        }
        if (c0729q2.isInterface()) {
            b(c0729q2, l0A);
            return;
        }
        this.U.a(c0729q2, null, V0.a, l0A);
        if (c0729q2.q1()) {
            o5 o5VarY1 = c0729q2.Y1();
            this.U.a(o5VarY1.getReference(), o5VarY1, this.h0.a(o5VarY1));
            c(Y.c(), o5VarY1, (C3754l1) C3762m1.x().g());
        }
    }

    /* JADX WARN: Code duplicated, block: B:66:0x0147  */
    private void a(final o5 o5Var, AbstractC2412ko abstractC2412ko) {
        AbstractC0833Al abstractC0833AlA;
        o5 o5VarF;
        com.android.tools.r8.graph.T0 t0A;
        C0729q2 c0729q2C;
        C0729q2 c0729q2A;
        int iR2;
        o5 o5VarB;
        int iN2;
        C0729q2 c0729q2A2;
        o5 o5VarY1;
        if (abstractC2412ko.Y1()) {
            AbstractC1305Qs abstractC1305QsB0 = abstractC2412ko.b0();
            C0684j2 c0684j2S2 = abstractC1305QsB0.S2();
            C0722p1 c0722p1A = this.j.a();
            if (c0684j2S2 == c0722p1A.p4.l) {
                if (!abstractC1305QsB0.f2()) {
                    if (!q0) {
                        throw new AssertionError();
                    }
                    return;
                }
                C0767v2 c0767v2A = AbstractC1231Oc.a(this.j, abstractC1305QsB0.i0().U2());
                if (c0767v2A == null || !c0767v2A.O0() || (c0729q2A2 = C0729q2.a(b(c0767v2A, (InterfaceC0694k5) o5Var))) == null || (o5VarY1 = c0729q2A2.Y1()) == null) {
                    return;
                }
                C3824x1 c3824x1G = AbstractC3837z1.g(o5Var);
                this.U.a(c0729q2A2, null, V0.d, c3824x1G);
                if (c0729q2A2.q1()) {
                    o5 o5VarY2 = c0729q2A2.Y1();
                    this.U.a(o5VarY2.getReference(), o5VarY2, c3824x1G);
                }
                b(c3824x1G, o5VarY1);
                c(c3824x1G, o5VarY1);
                c(Y.c(), o5VarY1, (C3754l1) C3762m1.x().g());
                return;
            }
            int i = 0;
            if (c0684j2S2 == c0722p1A.q4.a) {
                if (!abstractC1305QsB0.f2()) {
                    if (!q0) {
                        throw new AssertionError();
                    }
                    return;
                }
                S10 s10M = abstractC1305QsB0.i0().U2().m();
                if (s10M.i() || !s10M.b.f2()) {
                    return;
                }
                C2115gt c2115gtI0 = s10M.b.i0();
                C0684j2 c0684j2S3 = c2115gtI0.S2();
                if (c0684j2S3 == this.j.a().p4.i || c0684j2S3 == this.j.a().p4.j) {
                    C0767v2 c0767v2A2 = AbstractC1231Oc.a(this.j, c2115gtI0.U2());
                    if (c0767v2A2 == null || !c0767v2A2.O0() || (c0729q2A = C0729q2.a(b(c0767v2A2, (InterfaceC0694k5) o5Var))) == null) {
                        return;
                    }
                    S10 s10 = (S10) ((ArrayList) c2115gtI0.h1()).get(1);
                    if (s10.i()) {
                        return;
                    }
                    AG agR0 = s10.b.r0();
                    C1385Ts c1385TsE0 = s10.b.e0();
                    if (agR0 != null) {
                        S10 s10J2 = agR0.J2();
                        if (s10J2.J()) {
                            iR2 = s10J2.n().E().N2();
                        } else {
                            iR2 = -1;
                        }
                    } else if (c1385TsE0 != null) {
                        iR2 = c1385TsE0.R2();
                    } else {
                        iR2 = -1;
                    }
                    if (iR2 < 0) {
                        return;
                    }
                    if (iR2 == 0) {
                        o5VarB = c0729q2A.Y1();
                    } else {
                        C0767v2[] c0767v2Arr = new C0767v2[iR2];
                        if (agR0 != null) {
                            i = iR2;
                        } else {
                            List listH1 = c1385TsE0.h1();
                            for (int i2 = 0; i2 < iR2; i2++) {
                                C0767v2 c0767v2A3 = AbstractC1231Oc.a(this.j, (S10) ((ArrayList) listH1).get(i2));
                                if (c0767v2A3 == null) {
                                    return;
                                }
                                c0767v2Arr[i2] = c0767v2A3;
                            }
                        }
                        for (AbstractC2412ko abstractC2412ko2 : s10.h0()) {
                            if (abstractC2412ko2.r1()) {
                                C1736c3 c1736c3V = abstractC2412ko2.v();
                                if (c1736c3V.J2() != s10) {
                                    return;
                                }
                                S10 s10K2 = c1736c3V.K2();
                                if (s10K2.i() || !s10K2.b.B1() || (iN2 = s10K2.b.E().N2()) >= iR2) {
                                    return;
                                }
                                C0767v2 c0767v2A4 = AbstractC1231Oc.a(this.j, c1736c3V.value());
                                if (c0767v2A4 == null) {
                                    return;
                                }
                                C0767v2 c0767v2 = c0767v2Arr[iN2];
                                if (c0767v2 == c0767v2A4) {
                                    continue;
                                } else {
                                    if (c0767v2 != null) {
                                        return;
                                    }
                                    c0767v2Arr[iN2] = c0767v2A4;
                                    i--;
                                }
                            }
                        }
                        o5VarB = i == 0 ? c0729q2A.b(c0767v2Arr) : null;
                    }
                    if (o5VarB != null) {
                        C3824x1 c3824x1G2 = AbstractC3837z1.g(o5Var);
                        this.U.a(c0729q2A, null, V0.d, c3824x1G2);
                        if (c0729q2A.q1()) {
                            o5 o5VarY3 = c0729q2A.Y1();
                            this.U.a(o5VarY3.getReference(), o5VarY3, c3824x1G2);
                        }
                        b(c3824x1G2, o5VarB);
                        c(c3824x1G2, o5VarB);
                        c(Y.c(), o5VarB, (C3754l1) C3762m1.x().g());
                        return;
                    }
                    return;
                }
                return;
            }
            if (c0684j2S2 == c0722p1A.r4.c) {
                if (((S10) ((ArrayList) abstractC1305QsB0.h1()).get(0)).I() && (c0729q2C = c(((S10) ((ArrayList) abstractC1305QsB0.h1()).get(0)).b.A().K2(), (InterfaceC0694k5) o5Var)) != null && c0729q2C.B1()) {
                    b(c0729q2C, AbstractC3837z1.c(o5Var));
                    return;
                }
                return;
            }
            if (c0684j2S2 == c0722p1A.B4.a) {
                a(o5Var, abstractC1305QsB0);
                return;
            }
            com.android.tools.r8.graph.X1 x1 = c0722p1A.D5;
            if (c0684j2S2 == x1.a || c0684j2S2 == x1.b || c0684j2S2 == x1.c) {
                if (((ArrayList) abstractC1305QsB0.h1()).size() == 0) {
                    return;
                }
                S10 s10M2 = ((S10) ((ArrayList) abstractC1305QsB0.h1()).get(0)).m();
                if (!s10M2.i()) {
                    AbstractC2412ko abstractC2412ko3 = s10M2.b;
                    abstractC2412ko3.getClass();
                    if (abstractC2412ko3 instanceof C3446yc) {
                        C0767v2 c0767v2K2 = s10M2.b.A().K2();
                        if (this.j.j().a().contains(c0767v2K2)) {
                            a(c0767v2K2, o5Var, AbstractC3837z1.g(o5Var));
                            return;
                        }
                        return;
                    }
                }
                C3824x1 c3824x1G3 = AbstractC3837z1.g(o5Var);
                Iterator it = this.j.j().a().iterator();
                while (it.hasNext()) {
                    a((C0767v2) it.next(), o5Var, c3824x1G3);
                }
                return;
            }
            if (com.android.tools.r8.naming.D.a(c0722p1A, c0684j2S2) && (abstractC0833AlA = com.android.tools.r8.naming.D.a(abstractC1305QsB0, this.j, o5Var)) != null) {
                AbstractC0744s2 abstractC0744s2B = abstractC0833AlA.b();
                abstractC0744s2B.getClass();
                if (abstractC0744s2B instanceof C0767v2) {
                    if (!q0 && !abstractC0833AlA.c()) {
                        throw new AssertionError();
                    }
                    AbstractC0901Cl abstractC0901ClA = abstractC0833AlA.a();
                    C0729q2 c0729q2A3 = C0729q2.a(b(abstractC0744s2B.w0(), (InterfaceC0694k5) o5Var));
                    if (c0729q2A3 == null) {
                        return;
                    }
                    c(c0729q2A3, AbstractC3837z1.g(o5Var));
                    if (!c0729q2A3.x1() && !c0729q2A3.e.T() && !c0729q2A3.isInterface() && abstractC0901ClA.a(this.n)) {
                        a(c0729q2A3, new Supplier() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda151
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                return AbstractC3837z1.g(o5Var);
                            }
                        });
                    } else if (abstractC0901ClA.d()) {
                        h(c0729q2A3);
                    }
                    if (this.Y.a(c0729q2A3).d((K0) this.n)) {
                        this.Y.a(new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda152
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                L.b((X0) obj);
                            }
                        }, c0729q2A3);
                        return;
                    }
                    return;
                }
                if (abstractC0744s2B.x0()) {
                    com.android.tools.r8.graph.Y0 y0T0 = abstractC0744s2B.t0();
                    C0729q2 c0729q2A4 = C0729q2.a(b(y0T0.e, (InterfaceC0694k5) o5Var));
                    if (c0729q2A4 == null || (t0A = c0729q2A4.a(y0T0)) == null) {
                        return;
                    }
                    if (!t0A.E0() && c0722p1A.y4.a(c0684j2S2)) {
                        this.U.a(c0729q2A4, null, V0.d, AbstractC3837z1.g(o5Var));
                    }
                    if (this.Y.a(t0A, c0729q2A4).d((K0) this.n)) {
                        m5 m5Var = new m5(t0A, c0729q2A4);
                        C3698c1 c3698c1 = (C3698c1) ((C3698c1) C3704d1.p().g()).h();
                        c3698c1.b.add(AbstractC3837z1.g(o5Var));
                        c(Y.c(), m5Var, c3698c1);
                        return;
                    }
                    return;
                }
                if (!q0 && !abstractC0744s2B.y0()) {
                    throw new AssertionError();
                }
                C0684j2 c0684j2V0 = abstractC0744s2B.v0();
                C0729q2 c0729q2A5 = C0729q2.a(b(c0684j2V0.e, (InterfaceC0694k5) o5Var));
                if (c0729q2A5 == null || (o5VarF = c0729q2A5.f(c0684j2V0)) == null) {
                    return;
                }
                C3824x1 c3824x1G4 = AbstractC3837z1.g(o5Var);
                if (o5VarF.e().f.N()) {
                    b(c3824x1G4, o5VarF);
                    c(c3824x1G4, o5VarF);
                } else {
                    f(c3824x1G4, o5VarF);
                }
                c(Y.c(), o5VarF, (C3754l1) C3762m1.x().g());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(X0 x0) {
        ((X0) x0.g()).h();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(o5 o5Var, AbstractC3837z1 abstractC3837z1, o5 o5Var2) {
        this.Y.a(o5Var2, new Consumer() { // from class: com.android.tools.r8.shaking.L$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                L.a((C3754l1) obj);
            }
        });
        a(o5Var2.getReference(), true, o5Var, abstractC3837z1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3754l1 c3754l1) {
        ((C3754l1) c3754l1.g()).h();
    }

    private void a(C0767v2 c0767v2, o5 o5Var, C3824x1 c3824x1) {
        C0729q2 c0729q2C;
        AbstractC1639am<C0767v2> abstractC1639amA = this.j.j().a(c0767v2);
        C2720of c2720ofB = b(c0767v2, o5Var);
        for (C0767v2 c0767v3 : abstractC1639amA) {
            if (c0767v3.O0() && (c0729q2C = c(c0767v3, (InterfaceC0694k5) o5Var)) != null) {
                this.U.a(c0729q2C, null, V0.d, c3824x1);
                if (c0729q2C.q1()) {
                    o5 o5VarY1 = c0729q2C.Y1();
                    this.U.a(o5VarY1.getReference(), o5VarY1, c3824x1);
                }
                o5 o5VarY2 = c0729q2C.Y1();
                if (o5VarY2 != null) {
                    c(Y.c(), o5VarY2, (C3754l1) C3762m1.x().g());
                }
                Iterator it = c2720ofB.iterator();
                while (it.hasNext()) {
                    com.android.tools.r8.graph.W0 w0A = c0729q2C.Y().a((AbstractC0717o2) it.next());
                    com.android.tools.r8.graph.W0[] w0Arr = com.android.tools.r8.graph.W0.s;
                    o5 o5VarA = w0A != null ? w0A.a(c0729q2C) : null;
                    if (o5VarA != null) {
                        c(Y.c(), o5VarA, (C3754l1) C3762m1.x().g());
                    }
                }
            }
        }
    }
}
