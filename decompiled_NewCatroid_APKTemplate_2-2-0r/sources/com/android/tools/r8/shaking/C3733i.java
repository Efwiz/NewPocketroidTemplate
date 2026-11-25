package com.android.tools.r8.shaking;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.AbstractC0614b5;
import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.AbstractC0701l4;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0741s0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0606a5;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0762u4;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.graph.InterfaceC0669h4;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.InterfaceC0707m4;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.graph.u5;
import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2237iT;
import com.android.tools.r8.internal.C1121Ka;
import com.android.tools.r8.internal.C1229Oa;
import com.android.tools.r8.internal.C1566Zr;
import com.android.tools.r8.internal.C2769pI;
import com.android.tools.r8.internal.C3030t00;
import com.android.tools.r8.internal.C3378xe;
import com.android.tools.r8.internal.DH;
import com.android.tools.r8.internal.EnumC1953et;
import com.android.tools.r8.internal.InterfaceC0992Fq;
import com.android.tools.r8.internal.InterfaceC1912eK;
import com.android.tools.r8.internal.JA;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.Z8;
import com.android.tools.r8.ir.optimize.v$$ExternalSyntheticLambda10;
import com.android.tools.r8.ir.optimize.v$$ExternalSyntheticLambda9;
import com.android.tools.r8.synthesis.C3842a;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3892f0;
import com.android.tools.r8.utils.C3984w2;
import com.android.tools.r8.utils.InterfaceC3880d;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.i, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3733i extends C0680j implements com.android.tools.r8.graph.K3 {
    static final /* synthetic */ boolean R = true;
    private final Set A;
    private final Set B;
    private final Set C;
    public final C3984w2 D;
    private final Set E;
    private final Set F;
    private final Set G;
    private final Set H;
    private final Set I;
    public final Map J;
    public final Set K;
    private final Set L;
    public final com.android.tools.r8.internal.J0 M;
    final Set N;
    final Map O;
    private final i4 P;
    private Z8 Q;
    private final Set k;
    private final Set l;
    private final Set m;
    private final Set n;
    private final Set o;
    private final Set p;
    private final Set q;
    private final Set r;
    private com.android.tools.r8.graph.T2 s;
    private final C0762u4 t;
    private final AbstractC0614b5 u;
    public final Map v;
    private final AbstractC3735i1 w;
    public final Map x;
    private final Set y;
    private final Set z;

    C3733i(C3842a c3842a, C1121Ka c1121Ka, N1 n1, U1 u1, Set set, Set set2, Set set3, Set set4, Set set5, Set set6, Set set7, Set set8, com.android.tools.r8.graph.T2 t2, C0762u4 c0762u4, AbstractC0614b5 abstractC0614b5, Map map, AbstractC3735i1 abstractC3735i1, Map map2, Set set9, Set set10, Set set11, Set set12, Set set13, C3984w2 c3984w2, Set set14, Set set15, Set set16, Set set17, Set set18, com.android.tools.r8.internal.J0 j0, Set set19, Map map3, Set set20, Map map4, Set set21) {
        super(c3842a, c1121Ka, n1, u1);
        this.P = new i4();
        this.Q = null;
        this.k = set;
        this.l = set2;
        this.m = set3;
        this.n = set4;
        this.o = set5;
        this.p = set6;
        this.q = set7;
        this.r = set8;
        this.s = t2;
        this.t = c0762u4;
        this.u = abstractC0614b5;
        this.w = abstractC3735i1;
        this.x = map2;
        this.v = map;
        this.y = set9;
        this.z = set10;
        this.A = set11;
        this.B = set12;
        this.C = set13;
        this.D = c3984w2;
        this.E = set14;
        this.F = set15;
        this.H = set16;
        this.G = set17;
        this.L = set18;
        this.M = j0;
        this.N = set19;
        this.O = map3;
        this.I = set20;
        this.J = map4;
        this.K = set21;
        if (R) {
            return;
        }
        F();
    }

    public static C3738j E() {
        return new C3738j();
    }

    private void F() {
        boolean z = R;
        if (!z) {
            this.w.a(l(), this.l);
        }
        if (z) {
            return;
        }
        this.u.a(this.l, q(), this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC1154Lf a(AbstractC1154Lf abstractC1154Lf) {
        return abstractC1154Lf;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean o(C0767v2 c0767v2) {
        return false;
    }

    public final Set A() {
        if (!R) {
            c();
        }
        return this.N;
    }

    public final Set B() {
        return this.B;
    }

    public final Set C() {
        return this.q;
    }

    public final boolean D() {
        return this.A.isEmpty();
    }

    public final void G() {
        d().forEach(new Consumer() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda27
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.h((C0729q2) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.C0680j
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final C3733i a(N1 n1) {
        return new C3733i(g().a(b()), p(), n1, q(), this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.u, this.v, this.w, this.x, this.y, this.z, this.A, this.B, this.C, this.D, this.E, this.F, this.H, this.G, this.L, this.M, this.N, this.O, this.I, this.J, this.K);
    }

    public final InterfaceC0992Fq d(com.android.tools.r8.graph.Y0 y0) {
        if (!R) {
            c();
        }
        return (InterfaceC0992Fq) this.O.get(y0);
    }

    public final boolean e(C0729q2 c0729q2) {
        return this.l.contains(c0729q2.d);
    }

    public final boolean f(com.android.tools.r8.graph.T0 t0) {
        boolean z = R;
        if (!z) {
            c();
        }
        if (!z && !b(t0)) {
            throw new AssertionError("Expected field `" + t0.r0() + "` to be written");
        }
        com.android.tools.r8.graph.W0 w0Y0 = c(t0.J0()).a0().Y0();
        return w0Y0 != null && a(t0, w0Y0);
    }

    public final boolean g(C0729q2 c0729q2) {
        AbstractC3735i1 abstractC3735i1 = this.w;
        return abstractC3735i1.a((InterfaceC0694k5) c0729q2).c((K0) l());
    }

    public final boolean h(C0767v2 c0767v2) {
        if (!R && !c0767v2.O0()) {
            throw new AssertionError();
        }
        C0729q2 c0729q2A = C0729q2.a(c(c0767v2));
        if (c0729q2A == null) {
            return false;
        }
        for (o5 o5Var : c0729q2A.R1()) {
            if (o5Var.e().C1() && c((InterfaceC0694k5) o5Var)) {
                return true;
            }
        }
        return false;
    }

    public final boolean i(C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = c(c0767v2);
        return abstractC0727q0C != null && abstractC0727q0C.e0() && e(abstractC0727q0C.a0());
    }

    public final boolean j(C0684j2 c0684j2) {
        return this.n.contains(c0684j2);
    }

    public final boolean k(C0684j2 c0684j2) {
        return this.r.contains(c0684j2);
    }

    public final boolean l(C0684j2 c0684j2) {
        return this.m.contains(c0684j2);
    }

    public final boolean m(C0684j2 c0684j2) {
        return this.A.contains(c0684j2);
    }

    public final boolean n(C0767v2 c0767v2) {
        if (this.l.contains(c0767v2)) {
            return true;
        }
        if (this.N.contains(c0767v2)) {
            return false;
        }
        AbstractC0727q0 abstractC0727q0C = c(c0767v2);
        return abstractC0727q0C == null || !abstractC0727q0C.e0();
    }

    public final boolean p(C0767v2 c0767v2) {
        if (!R) {
            c();
        }
        return this.N.contains(c0767v2);
    }

    public final B5 r() {
        return B5.a(this);
    }

    public final Set s() {
        return this.k;
    }

    public final Set t() {
        return this.o;
    }

    public final Set u() {
        return this.n;
    }

    public final com.android.tools.r8.graph.T2 v() {
        return this.s;
    }

    public final AbstractC3735i1 w() {
        return this.w;
    }

    public final C0762u4 x() {
        return this.t;
    }

    final com.android.tools.r8.graph.T2 y() {
        return this.s;
    }

    public final AbstractC0614b5 z() {
        return this.u;
    }

    public final boolean e(com.android.tools.r8.graph.T0 t0) {
        C3704d1 c3704d1A;
        boolean z = R;
        if (!z) {
            c();
        }
        com.android.tools.r8.graph.U2 u2C = this.s.b(t0.getReference());
        if (u2C != null) {
            if (z || u2C.n() || u2C.r()) {
                return true;
            }
            throw new AssertionError();
        }
        if (z) {
            return false;
        }
        AbstractC3735i1 abstractC3735i1 = this.w;
        abstractC3735i1.getClass();
        C0729q2 c0729q2A = C0729q2.a(a(t0.J0()));
        if (c0729q2A == null) {
            c3704d1A = C3704d1.i();
        } else {
            if (!AbstractC3735i1.a && c0729q2A.a(t0.getReference()) != t0) {
                throw new AssertionError();
            }
            c3704d1A = abstractC3735i1.a(t0, c0729q2A);
        }
        if (c3704d1A.d((K0) l())) {
            return false;
        }
        throw new AssertionError();
    }

    public final boolean j(C0767v2 c0767v2) {
        return this.I.contains(c0767v2);
    }

    public final boolean k(C0767v2 c0767v2) {
        if (l().k1()) {
            AbstractC3735i1 abstractC3735i1 = this.w;
            abstractC3735i1.getClass();
            if (abstractC3735i1.a(a(c0767v2)).a((K0) l())) {
                return true;
            }
        }
        return false;
    }

    public final boolean l(C0767v2 c0767v2) {
        return this.F.contains(c0767v2) || this.G.contains(c0767v2);
    }

    public final boolean m(C0767v2 c0767v2) {
        return this.F.contains(c0767v2) || this.H.contains(c0767v2);
    }

    @Override // com.android.tools.r8.graph.C0664h
    public final C3733i o() {
        if (!R) {
            c();
        }
        return this;
    }

    public final boolean b(o5 o5Var) {
        return this.z.contains(o5Var.getReference());
    }

    public final boolean d(com.android.tools.r8.graph.T0 t0) {
        com.android.tools.r8.graph.U2 u2B;
        boolean z = R;
        if (!z) {
            c();
        }
        if (!z && !b(t0)) {
            throw new AssertionError("Expected field `" + t0.r0() + "` to be written");
        }
        if (b((com.android.tools.r8.graph.O0) t0) || (u2B = this.s.b(t0.getReference())) == null || !u2B.r()) {
            return false;
        }
        final C0767v2 c0767v2J0 = t0.J0();
        return u2B.c(new Predicate() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.b(c0767v2J0, (o5) obj);
            }
        });
    }

    public final boolean i(C0684j2 c0684j2) {
        return this.y.contains(c0684j2);
    }

    private static com.android.tools.r8.internal.J0 a(final com.android.tools.r8.internal.J0 j0, final u5 u5Var, ExecutorService executorService, ArrayList arrayList) {
        if (u5Var.i() || u5Var.j()) {
            arrayList.add(C3879c3.a(new InterfaceC3880d() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda8
                @Override // com.android.tools.r8.utils.InterfaceC3880d
                public final void a() {
                    C3733i.a(u5Var, j0);
                }
            }, executorService));
        }
        return j0;
    }

    public final boolean b(C0729q2 c0729q2) {
        boolean z = R;
        if (!z) {
            c();
        }
        if (!z) {
            c();
        }
        return (!c0729q2.isInterface() && this.u.c(c0729q2)) || (c0729q2.e.T() && this.l.contains(c0729q2.d)) || c(c0729q2);
    }

    @Override // com.android.tools.r8.graph.C0664h
    public final boolean i() {
        if (R) {
            return true;
        }
        c();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void h(C0729q2 c0729q2) {
        if (R || !p(c0729q2.d)) {
            return;
        }
        throw new AssertionError(c0729q2.d + " was not pruned");
    }

    @Override // com.android.tools.r8.graph.C0664h, com.android.tools.r8.graph.Q0
    public AbstractC0727q0 c(C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = super.c(c0767v2);
        if (R || abstractC0727q0C != null || this.k.contains(c0767v2) || q().a(c0767v2) || C1566Zr.d(c0767v2) || C3378xe.a(c0767v2)) {
            return abstractC0727q0C;
        }
        throw new AssertionError("Failed lookup of non-missing type: " + c0767v2);
    }

    public final boolean f(C0729q2 c0729q2) {
        return l().k1() && this.w.a(c0729q2).a((K0) l());
    }

    private static Set a(final Set set, final u5 u5Var, ExecutorService executorService, ArrayList arrayList) {
        if (u5Var.i() || u5Var.j()) {
            arrayList.add(C3879c3.a(new InterfaceC3880d() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda18
                @Override // com.android.tools.r8.utils.InterfaceC3880d
                public final void a() {
                    C3733i.a(u5Var, set);
                }
            }, executorService));
        }
        return set;
    }

    public final boolean b(com.android.tools.r8.graph.T0 t0) {
        if (!R) {
            c();
        }
        return c(t0) || b((com.android.tools.r8.graph.O0) t0);
    }

    public final boolean b(com.android.tools.r8.graph.T0 t0, com.android.tools.r8.graph.W0 w0) {
        boolean z = R;
        if (!z) {
            c();
        }
        if (!z && !b(t0)) {
            throw new AssertionError("Expected field `" + t0.r0() + "` to be written");
        }
        com.android.tools.r8.graph.U2 u2B = this.s.b(t0.getReference());
        return (u2B == null || !u2B.r() || u2B.a(w0)) ? false : true;
    }

    public final boolean d(C0729q2 c0729q2) {
        if (!R) {
            c();
        }
        return this.u.d(c0729q2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(u5 u5Var, final Set set) {
        final Set setF = u5Var.f();
        final Set setG = u5Var.g();
        if (set.size() <= setG.size() + setF.size()) {
            set.removeIf(new Predicate() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C3733i.a(setF, setG, (AbstractC0642e2) obj);
                }
            });
        } else {
            setF.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    set.remove((com.android.tools.r8.graph.Y0) obj);
                }
            });
            setG.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    set.remove((C0684j2) obj);
                }
            });
        }
    }

    public final boolean c(o5 o5Var) {
        return this.C.contains(o5Var.getReference()) || o5Var.B().u();
    }

    final void c(Consumer consumer) {
        this.u.a(consumer, this);
    }

    public final boolean c(C0729q2 c0729q2) {
        if (!R) {
            c();
        }
        return this.u.a(c0729q2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(C0767v2 c0767v2, o5 o5Var) {
        if (o5Var.t() == c0767v2) {
            com.android.tools.r8.graph.W0 w0E = o5Var.e();
            C0722p1 c0722p1A = a();
            if (w0E.C1() || w0E.c(c0722p1A)) {
                return true;
            }
        }
        return false;
    }

    public final boolean c(com.android.tools.r8.graph.T0 t0) {
        AbstractC0727q0 abstractC0727q0C;
        if (!R) {
            c();
        }
        com.android.tools.r8.graph.U2 u2C = this.s.b(t0.getReference());
        return (u2C != null && u2C.r()) || (abstractC0727q0C = c(t0.J0())) == null || abstractC0727q0C.f0() || (abstractC0727q0C instanceof C0754u0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, Set set2, AbstractC0642e2 abstractC0642e2) {
        if (abstractC0642e2.x0()) {
            return set.contains(abstractC0642e2.t0());
        }
        return set2.contains(abstractC0642e2.v0());
    }

    public final boolean b(C0702m c0702m, com.android.tools.r8.graph.Y0 y0) {
        if (!R) {
            c();
        }
        if (this.L.contains(y0)) {
            return false;
        }
        return !a((AbstractC0744s2) y0) || y0.getType().b(c0702m);
    }

    private static Set a(final Set set, final Set set2, ExecutorService executorService, ArrayList arrayList) {
        if (!set2.isEmpty()) {
            arrayList.add(C3879c3.a(new InterfaceC3880d() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda6
                @Override // com.android.tools.r8.utils.InterfaceC3880d
                public final void a() {
                    C3733i.a(set, set2);
                }
            }, executorService));
        }
        return set;
    }

    public final boolean c(InterfaceC0694k5 interfaceC0694k5) {
        AbstractC3735i1 abstractC3735i1 = this.w;
        return abstractC3735i1.a(interfaceC0694k5).c((K0) l());
    }

    public final boolean b(C0702m c0702m, C0684j2 c0684j2) {
        com.android.tools.r8.graph.W0 w0C;
        if (!R) {
            c();
        }
        if (this.L.contains(c0684j2)) {
            return false;
        }
        if (c0684j2.I0().b(c0702m)) {
            return true;
        }
        AbstractC3735i1 abstractC3735i1 = this.w;
        abstractC3735i1.getClass();
        C0729q2 c0729q2A = C0729q2.a(a(c0684j2.e));
        C3762m1 c3762m1I = (c0729q2A == null || (w0C = c0729q2A.c(c0684j2)) == null) ? C3762m1.i() : abstractC3735i1.a(c0729q2A, w0C);
        return c3762m1I.b((K0) l());
    }

    @Override // com.android.tools.r8.graph.C0680j
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final C3733i a(final u5 u5Var, ExecutorService executorService) {
        Set setA;
        boolean z = R;
        if (!z && getClass() != C3733i.class) {
            throw new AssertionError();
        }
        if (!z) {
            c();
        }
        if (u5Var.k()) {
            if (z || b() == u5Var.d()) {
                return this;
            }
            throw new AssertionError();
        }
        if (u5Var.h()) {
            this.u.a(new Consumer() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda19
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((C0606a5) obj).a(u5Var);
                }
            }, this);
            this.w.a(new Consumer() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda20
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((C3730h1) obj).b(u5Var);
                }
            });
        } else if (u5Var.i() || u5Var.j()) {
            this.w.a(new Consumer() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda21
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((C3730h1) obj).b(u5Var);
                }
            });
        }
        ArrayList arrayList = new ArrayList();
        C3842a c3842aA = g().a(u5Var);
        C1121Ka c1121KaA = p().a(u5Var);
        N1 n1A = f().a(u5Var);
        U1 u1Q = q();
        Set set = this.k;
        Set setA2 = a(this.l, u5Var.e(), executorService, arrayList);
        Set setA3 = a(this.m, u5Var.g(), executorService, arrayList);
        Set setA4 = a(this.n, u5Var.g(), executorService, arrayList);
        Set setA5 = a(this.o, u5Var.f(), executorService, arrayList);
        Set setA6 = a(this.p, u5Var.g(), executorService, arrayList);
        Set setA7 = a(this.q, u5Var.g(), executorService, arrayList);
        Set setA8 = a(this.r, u5Var.g(), executorService, arrayList);
        com.android.tools.r8.graph.T2 t2 = this.s;
        C0762u4 c0762u4 = this.t;
        AbstractC0614b5 abstractC0614b5B = this.u.b(u5Var);
        Map map = this.v;
        AbstractC3735i1 abstractC3735i1A = a(this, u5Var.b());
        Map map2 = this.x;
        Set setA9 = a(this.y, u5Var.g(), executorService, arrayList);
        Set setA10 = a(this.z, u5Var.g(), executorService, arrayList);
        Set setA11 = a(this.A, u5Var.g(), executorService, arrayList);
        Set setA12 = a(this.B, u5Var.g(), executorService, arrayList);
        Set setA13 = a(this.C, u5Var.g(), executorService, arrayList);
        C3984w2 c3984w2 = this.D;
        Set setA14 = a(this.E, u5Var.e(), executorService, arrayList);
        Set setA15 = a(this.F, u5Var.e(), executorService, arrayList);
        Set setA16 = a(this.H, u5Var.e(), executorService, arrayList);
        Set setA17 = a(this.G, u5Var.e(), executorService, arrayList);
        Set setA18 = a(this.L, u5Var, executorService, arrayList);
        com.android.tools.r8.internal.J0 j0A = a(this.M, u5Var, executorService, arrayList);
        if (u5Var.h()) {
            setA = C3892f0.a(this.N, u5Var.e());
        } else {
            setA = this.N;
        }
        C3733i c3733i = new C3733i(c3842aA, c1121KaA, n1A, u1Q, set, setA2, setA3, setA4, setA5, setA6, setA7, setA8, t2, c0762u4, abstractC0614b5B, map, abstractC3735i1A, map2, setA9, setA10, setA11, setA12, setA13, c3984w2, setA14, setA15, setA16, setA17, setA18, j0A, setA, this.O, a(this.I, u5Var.e(), executorService, arrayList), a(this.J, u5Var.e(), executorService, arrayList), this.K);
        C3879c3.a(arrayList);
        return c3733i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final Set set, Set set2) {
        if (set.size() <= set2.size()) {
            set.removeAll(set2);
        } else {
            set2.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda22
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    set.remove(obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(u5 u5Var, final com.android.tools.r8.internal.J0 j0) {
        Set setF = u5Var.f();
        Objects.requireNonNull(j0);
        setF.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                j0.c((com.android.tools.r8.graph.Y0) obj);
            }
        });
        u5Var.g().forEach(new Consumer() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                j0.c((C0684j2) obj);
            }
        });
    }

    private static Map a(final Map map, final Set set, ExecutorService executorService, ArrayList arrayList) {
        if (!set.isEmpty()) {
            arrayList.add(C3879c3.a(new InterfaceC3880d() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda13
                @Override // com.android.tools.r8.utils.InterfaceC3880d
                public final void a() {
                    C3733i.a(map, set);
                }
            }, executorService));
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final Map map, Set set) {
        if (map.size() <= set.size()) {
            map.keySet().removeAll(set);
        } else {
            set.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda16
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    map.remove(obj);
                }
            });
        }
    }

    public final boolean b(InterfaceC0694k5 interfaceC0694k5) {
        return l().k1() && this.w.a(interfaceC0694k5).a((K0) l());
    }

    private static AbstractC3735i1 a(final C3733i c3733i, final Set set) {
        if (set != null && !set.isEmpty()) {
            return c3733i.w.a(new Consumer() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3733i.a(set, c3733i, (C3730h1) obj);
                }
            });
        }
        return c3733i.w;
    }

    public final boolean b(com.android.tools.r8.graph.O0 o0) {
        AbstractC3735i1 abstractC3735i1 = this.w;
        return abstractC3735i1.a(o0, this).c((K0) l());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Collection collection, C3733i c3733i, C3730h1 c3730h1) {
        o5 o5VarF;
        m5 m5VarD;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            AbstractC0744s2 abstractC0744s2 = (AbstractC0744s2) it.next();
            abstractC0744s2.getClass();
            if (abstractC0744s2 instanceof C0767v2) {
                C0729q2 c0729q2A = C0729q2.a(c3733i.c(abstractC0744s2.w0()));
                if (c0729q2A != null) {
                    c3730h1.a(new Consumer() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda9
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((X0) obj).h();
                        }
                    }, c0729q2A);
                }
            } else if (abstractC0744s2.y0()) {
                C0684j2 c0684j2V0 = abstractC0744s2.v0();
                C0729q2 c0729q2A2 = C0729q2.a(c3733i.c(c0684j2V0.e));
                if (c0729q2A2 != null && (o5VarF = c0729q2A2.f(c0684j2V0)) != null) {
                    c3730h1.a(o5VarF, new Consumer() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda10
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((C3754l1) obj).h();
                        }
                    });
                }
            } else {
                com.android.tools.r8.graph.Y0 y0T0 = abstractC0744s2.t0();
                C0729q2 c0729q2A3 = C0729q2.a(c3733i.c(y0T0.e));
                if (c0729q2A3 != null && (m5VarD = c0729q2A3.d(y0T0)) != null) {
                    c3730h1.a(m5VarD, new Consumer() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda12
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((C3698c1) obj).h();
                        }
                    });
                }
            }
        }
    }

    public final C3733i b(C3842a c3842a) {
        C1121Ka c1121KaP = p();
        N1 n1F = f();
        U1 u1Q = q();
        Set set = this.k;
        Set set2 = this.l;
        set2.addAll(c3842a.b());
        return new C3733i(c3842a, c1121KaP, n1F, u1Q, set, set2, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.u, this.v, this.w, this.x, this.y, this.z, this.A, this.B, this.C, this.D, this.E, this.F, this.H, this.G, this.L, this.M, this.N, this.O, this.I, this.J, this.K);
    }

    public final void b(Consumer consumer) {
        b().b().k().forEach(consumer);
    }

    public final boolean a(com.android.tools.r8.utils.A1 a1) {
        if (!(a1.f instanceof ClassFileConsumer)) {
            return true;
        }
        if (this.Q == null) {
            synchronized (this) {
                if (this.Q == null) {
                    for (C0729q2 c0729q2 : d()) {
                        if (c0729q2.Z1()) {
                            this.Q = (Z8) com.android.tools.r8.utils.structural.s.c(this.Q, c0729q2.U1());
                        }
                    }
                    if (!R && this.Q == null) {
                        throw new AssertionError();
                    }
                }
            }
        }
        return a1.a(this.Q);
    }

    public final boolean c(com.android.tools.r8.graph.O0 o0) {
        AbstractC0727q0 abstractC0727q0C;
        if (b(o0)) {
            return true;
        }
        if (o0.D0()) {
            com.android.tools.r8.graph.W0 w0 = o0.w0();
            return !w0.getReference().e.O0() || (abstractC0727q0C = c(w0.getReference().e)) == null || !abstractC0727q0C.e0() || w0.D1().c();
        }
        if (!R && !o0.A0()) {
            throw new AssertionError();
        }
        AbstractC0727q0 abstractC0727q0T0 = o0.t0();
        return abstractC0727q0T0.I1() || d(abstractC0727q0T0.a0());
    }

    public final boolean a(o5 o5Var) {
        return this.p.contains(o5Var.getReference());
    }

    public final void a(Iterable iterable, Consumer consumer) {
        final com.android.tools.r8.utils.O3 o3E = com.android.tools.r8.utils.O3.e();
        o3E.a(iterable);
        o3E.a((Iterable) this.u.a());
        Iterator<C0729q2> it = d().iterator();
        while (it.hasNext()) {
            o3E.c(it.next().d);
        }
        while (o3E.c()) {
            AbstractC0727q0 abstractC0727q0C = c((C0767v2) o3E.f());
            if (abstractC0727q0C != null) {
                if (abstractC0727q0C.isInterface()) {
                    consumer.accept(abstractC0727q0C);
                }
                abstractC0727q0C.d(new Consumer() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda15
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        o3E.c((C0767v2) obj);
                    }
                });
            }
        }
    }

    public final Set a(C0721p0 c0721p0, C0702m c0702m) {
        AbstractC0727q0 abstractC0727q0C;
        if (!R) {
            c();
        }
        ArrayList arrayListA = JA.a(c0721p0, c0702m);
        if (arrayListA != null && !arrayListA.isEmpty()) {
            Set setC = OU.c();
            ArrayDeque arrayDeque = new ArrayDeque(arrayListA);
            Set setC2 = OU.c();
            while (!arrayDeque.isEmpty()) {
                C0767v2 c0767v2 = (C0767v2) arrayDeque.removeFirst();
                if (setC2.add(c0767v2) && (abstractC0727q0C = c(c0767v2)) != null) {
                    if (!R && !abstractC0727q0C.isInterface()) {
                        throw new AssertionError();
                    }
                    for (com.android.tools.r8.graph.W0 w0 : abstractC0727q0C.P1()) {
                        if (w0.getReference().f == c0721p0.d && w0.f.W()) {
                            setC.add(w0);
                        }
                    }
                    Collections.addAll(arrayDeque, abstractC0727q0C.g.a);
                }
            }
            return setC;
        }
        return Collections.emptySet();
    }

    final void a(AbstractC0727q0 abstractC0727q0) {
        this.P.a(abstractC0727q0.d, this);
    }

    public final boolean a(com.android.tools.r8.graph.T0 t0) {
        AbstractC0727q0 abstractC0727q0C;
        if (!R) {
            c();
        }
        com.android.tools.r8.graph.U2 u2C = this.s.b(t0.getReference());
        return (u2C != null && u2C.n()) || b((com.android.tools.r8.graph.O0) t0) || (abstractC0727q0C = c(t0.J0())) == null || abstractC0727q0C.f0() || (abstractC0727q0C instanceof C0754u0);
    }

    public final boolean a(com.android.tools.r8.graph.T0 t0, com.android.tools.r8.graph.W0 w0) {
        boolean z = R;
        if (!z) {
            c();
        }
        if (!z && !b(t0)) {
            throw new AssertionError("Expected field `" + t0.r0() + "` to be written");
        }
        if (b((com.android.tools.r8.graph.O0) t0)) {
            return false;
        }
        return b(t0, w0);
    }

    public final boolean a(final C0702m c0702m, AbstractC0741s0 abstractC0741s0) {
        if (!R) {
            c();
        }
        return ((Boolean) abstractC0741s0.getReference().a(new Function() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0702m, (com.android.tools.r8.graph.Y0) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0702m, (C0684j2) obj);
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean a(C0702m c0702m, com.android.tools.r8.graph.Y0 y0) {
        return Boolean.valueOf(b(c0702m, y0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean a(C0702m c0702m, C0684j2 c0684j2) {
        return Boolean.valueOf(b(c0702m, c0684j2));
    }

    public final boolean a(C0729q2 c0729q2) {
        return (g(c0729q2) || this.E.contains(c0729q2.getType())) ? false : true;
    }

    public final boolean a(com.android.tools.r8.graph.O0 o0) {
        return l().k1() && this.w.a(o0, this).a((K0) l());
    }

    public final boolean a(InterfaceC0694k5 interfaceC0694k5) {
        if (R || l().D0().A()) {
            return this.w.a(interfaceC0694k5).a(l());
        }
        throw new AssertionError();
    }

    public final boolean a(C0702m c0702m, C0729q2 c0729q2) {
        if (!l().n1() || !this.w.a(c0729q2).e(l()) || AbstractC2237iT.a(c0729q2, c0702m.Y())) {
            return false;
        }
        com.android.tools.r8.naming.S0 s0R = c0702m.r();
        return s0R == null || !s0R.b(c0729q2.d);
    }

    public final boolean a(AbstractC0744s2 abstractC0744s2) {
        if (!R) {
            c();
        }
        return this.w.a(abstractC0744s2, l(), this);
    }

    public final C3733i a(com.android.tools.r8.graph.N2 n2, DH dh) {
        boolean z = R;
        if (!z) {
            c();
        }
        if (!z) {
            dh.a((Iterable) this.O.keySet().stream().map(new v$$ExternalSyntheticLambda9(this)).filter(new Predicate() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda25
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((AbstractC0677i3) obj).B();
                }
            }).map(new v$$ExternalSyntheticLambda10()).collect(Collectors.toList()));
        }
        C3842a c3842aA = g().a(n2, dh);
        com.android.tools.r8.graph.Q0 q0A = c3842aA.a().a(c3842aA);
        C1121Ka c1121KaA = p().a(dh);
        N1 n1A = f().a(g(), dh);
        U1 u1Q = q();
        Set set = this.k;
        Set setA = dh.a(this.l);
        Set setA2 = dh.a(this.m);
        Set setA3 = dh.a(this.n);
        Set setA4 = dh.a(this.o);
        Set setA5 = dh.a(this.p);
        Set setA6 = dh.a(this.q);
        Set setA7 = dh.a(this.r);
        com.android.tools.r8.graph.T2 t2A = this.s.a(q0A, dh);
        C0762u4 c0762u4A = this.t.a(q0A, dh);
        C0606a5 c0606a5A = this.u.a(q0A, dh);
        IdentityHashMap identityHashMapA = dh.a(this.v, q0A);
        C3730h1 c3730h1A = this.w.a(dh, n2.d);
        IdentityHashMap identityHashMapA2 = dh.a(this.x, new BiFunction() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda26
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C3733i.a((AbstractC0744s2) obj, (List) obj2);
            }
        });
        Set setA8 = dh.a(this.y);
        Set setA9 = dh.a(this.z);
        Set setA10 = dh.a(this.A);
        Set setA11 = dh.a(this.B);
        Set setA12 = dh.a(this.C);
        C3984w2 c3984w2A = this.D.a(new g4$$ExternalSyntheticLambda0(dh));
        Set setA13 = dh.a(this.E);
        Set setA14 = dh.a(this.F);
        Set setA15 = dh.a(this.H);
        Set setA16 = dh.a(this.G);
        Set setA17 = dh.a(this.L);
        com.android.tools.r8.internal.J0 j0 = this.M;
        C2769pI c2769pI = new C2769pI();
        InterfaceC1912eK it = j0.k().iterator();
        while (it.hasNext()) {
            com.android.tools.r8.internal.I0 i0 = (com.android.tools.r8.internal.I0) it.next();
            InterfaceC1912eK interfaceC1912eK = it;
            c2769pI.a(dh.a((AbstractC0744s2) i0.getKey(), (AbstractC1476Wj) null), i0.getBooleanValue());
            it = interfaceC1912eK;
            setA6 = setA6;
        }
        return new C3733i(c3842aA, c1121KaA, n1A, u1Q, set, setA, setA2, setA3, setA4, setA5, setA6, setA7, t2A, c0762u4A, c0606a5A, identityHashMapA, c3730h1A, identityHashMapA2, setA8, setA9, setA10, setA11, setA12, c3984w2A, setA13, setA14, setA15, setA16, setA17, c2769pI, this.N, dh.a(this.O), dh.a(this.I), a(dh), dh.a(this.K));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3775o3 a(AbstractC0744s2 abstractC0744s2, List list) {
        return (C3775o3) com.android.tools.r8.utils.V1.a(list);
    }

    public final IdentityHashMap a(AbstractC1476Wj abstractC1476Wj) {
        return abstractC1476Wj.b(this.J, new BiFunction() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda14
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C3733i.a((com.android.tools.r8.utils.N3) obj, (com.android.tools.r8.utils.N3) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.utils.N3 a(com.android.tools.r8.utils.N3 n3, com.android.tools.r8.utils.N3 n4) {
        boolean z = R;
        if (!z) {
            n3.getClass();
            if (n3 == com.android.tools.r8.utils.N3.PRIVATE) {
                throw new AssertionError();
            }
        }
        if (!z) {
            n4.getClass();
            if (n4 == com.android.tools.r8.utils.N3.PRIVATE) {
                throw new AssertionError();
            }
        }
        n3.getClass();
        com.android.tools.r8.utils.N3 n5 = com.android.tools.r8.utils.N3.PUBLIC;
        if (n3 != n5) {
            n4.getClass();
            if (n4 != n5) {
                com.android.tools.r8.utils.N3 n6 = com.android.tools.r8.utils.N3.PROTECTED;
                return (n3 == n6 || n4 == n6) ? n6 : com.android.tools.r8.utils.N3.PACKAGE_PRIVATE;
            }
        }
        return n5;
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0050  */
    public final o5 a(C0702m c0702m, EnumC1953et enumC1953et, C0684j2 c0684j2, o5 o5Var, C0702m c0702m2) {
        com.android.tools.r8.graph.W0 w0A;
        boolean z = R;
        if (!z) {
            c();
        }
        if (c0684j2.e.O0()) {
            int iOrdinal = enumC1953et.ordinal();
            if (iOrdinal == 0) {
                w0A = a(c0684j2, o5Var, (C0702m<? extends C0680j>) c0702m);
            } else if (iOrdinal == 1) {
                if (!z) {
                    c();
                }
                w0A = a(c0702m, c0684j2, o5Var, true, c0702m2, AbstractC1154Lf.n());
            } else if (iOrdinal == 2) {
                w0A = b(c0684j2, o5Var, (C0702m<? extends C0680j>) c0702m);
            } else if (iOrdinal == 3) {
                AbstractC0748t0 abstractC0748t0A = a(c0684j2, o5Var.a(), c0702m, (C0680j) c0702m.f());
                com.android.tools.r8.graph.W0[] w0Arr = com.android.tools.r8.graph.W0.s;
                if (abstractC0748t0A != null) {
                    w0A = abstractC0748t0A.e();
                } else {
                    w0A = null;
                }
            } else if (iOrdinal != 4) {
                w0A = null;
            } else {
                if (!z) {
                    c();
                }
                w0A = a(c0702m, c0684j2, o5Var, false, c0702m2, AbstractC1154Lf.n());
            }
        } else {
            w0A = null;
        }
        return com.android.tools.r8.graph.W0.a(w0A, this);
    }

    public com.android.tools.r8.graph.W0 a(C0702m<C3733i> c0702m, C0684j2 c0684j2, o5 o5Var, boolean z) {
        boolean z2 = R;
        if (!z2) {
            c();
        }
        H1 h1 = new H1() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda23
            @Override // com.android.tools.r8.shaking.H1
            public final boolean d(C0767v2 c0767v2) {
                return C3733i.o(c0767v2);
            }
        };
        if (!z2) {
            c();
        }
        return a(c0702m, c0684j2, o5Var, z, h1, AbstractC1154Lf.n());
    }

    /* JADX WARN: Code duplicated, block: B:59:0x00f5  */
    public com.android.tools.r8.graph.W0 a(C0702m<C3733i> c0702m, C0684j2 c0684j2, o5 o5Var, boolean z, H1 h1, final AbstractC1154Lf abstractC1154Lf) {
        com.android.tools.r8.graph.W0 w0E;
        C0729q2 c0729q2A0;
        InterfaceC0707m4 interfaceC0707m4G;
        AbstractC0727q0 abstractC0727q0C;
        boolean z2 = R;
        if (!z2) {
            c();
        }
        if (!z2 && abstractC1154Lf == null) {
            throw new AssertionError();
        }
        com.android.tools.r8.graph.W0 w0E2 = null;
        if (c0684j2.B0().M0()) {
            return null;
        }
        if (!c0702m.A().a(c0702m, new Supplier() { // from class: com.android.tools.r8.shaking.i$$ExternalSyntheticLambda17
            @Override // java.util.function.Supplier
            public final Object get() {
                return C3733i.a(abstractC1154Lf);
            }
        }, c0684j2.B0().c((C0702m<?>) c0702m))) {
            return null;
        }
        AbstractC0727q0 abstractC0727q0C2 = c(c0684j2.e);
        if (abstractC0727q0C2 != null && abstractC0727q0C2.isInterface() == z) {
            C0767v2 c0767v2A = C3030t00.a(abstractC1154Lf, c0684j2, c0702m);
            AbstractC0727q0 abstractC0727q0C3 = c(c0767v2A);
            if (abstractC0727q0C3 == null) {
                return null;
            }
            if (!abstractC1154Lf.g() && this.P.b(c0684j2, c0767v2A)) {
                return this.P.a(c0684j2, c0767v2A);
            }
            I4.c<?> cVarO = g(abstractC0727q0C2, c0684j2).o();
            if (cVarO != null && !cVarO.b((InterfaceC0694k5) o5Var.a(), (C0702m) c0702m).a()) {
                com.android.tools.r8.graph.W0 w0R = cVarO.r();
                if (h1.d(cVarO.d().d) && (cVarO.d().e.h() || (w0R.z1() && w0R.f.m()))) {
                    this.P.a(c0767v2A, c0684j2, w0R);
                    return w0R;
                }
                C1229Oa c1229OaD = abstractC1154Lf.d();
                if (c1229OaD == null || c1229OaD.R() != c0767v2A) {
                    w0E = null;
                } else if (abstractC0727q0C3.e0()) {
                    InterfaceC0669h4 interfaceC0669h4A = cVarO.a(abstractC0727q0C3.a0(), this);
                    if (interfaceC0669h4A != null) {
                        AbstractC0748t0 abstractC0748t0P = interfaceC0669h4A.p();
                        abstractC0748t0P.getClass();
                        if (!(abstractC0748t0P instanceof o5) || this.w.a(interfaceC0669h4A.p().g0()).b((K0) l())) {
                            w0E = interfaceC0669h4A.e();
                        } else {
                            w0E = com.android.tools.r8.graph.W0.t;
                        }
                    } else {
                        w0E = com.android.tools.r8.graph.W0.t;
                    }
                } else {
                    com.android.tools.r8.graph.W0 w0R2 = cVarO.r();
                    com.android.tools.r8.graph.W0 w0D = abstractC0727q0C3.d(w0R2.getReference());
                    w0E = (w0D == null || !I4.c.a(w0R2, w0D)) ? com.android.tools.r8.graph.W0.t : w0D;
                }
                if (w0E != null) {
                    if (w0E == com.android.tools.r8.graph.W0.t) {
                        return null;
                    }
                    return w0E;
                }
                if (abstractC0727q0C3.I1()) {
                    this.P.a(c0767v2A, c0684j2, (com.android.tools.r8.graph.W0) null);
                    return null;
                }
                AbstractC0727q0 abstractC0727q0D = cVarO.d();
                if (abstractC0727q0D.isInterface() && abstractC0727q0D.e0() && this.u.b(abstractC0727q0D.a0())) {
                    this.P.a(c0767v2A, c0684j2, (com.android.tools.r8.graph.W0) null);
                    return null;
                }
                if (!abstractC1154Lf.g() || (abstractC0727q0C = c(abstractC1154Lf.d().R())) == null || ((c0729q2A0 = abstractC0727q0C.a0()) != null && !c(c0729q2A0.d, c0767v2A))) {
                    c0729q2A0 = null;
                }
                AbstractC0701l4.a aVarA = cVarO.a(o5Var.a(), c0702m, abstractC0727q0C3.a0(), c0729q2A0).a();
                if (aVarA != null && !aVarA.j() && (interfaceC0707m4G = aVarA.g()) != null && interfaceC0707m4G.h()) {
                    w0E2 = interfaceC0707m4G.f().e();
                }
                if (!abstractC1154Lf.g()) {
                    this.P.a(c0767v2A, c0684j2, w0E2);
                }
            }
        }
        return w0E2;
    }

    public final C3733i a(IdentityHashMap identityHashMap) {
        boolean z = R;
        if (!z) {
            c();
        }
        if (z || this.O.isEmpty()) {
            return new C3733i(this, identityHashMap);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.K3
    public final void a(C0767v2 c0767v2, Consumer consumer, Consumer consumer2) {
        this.u.a(c0767v2, consumer, consumer2, this);
    }

    public final void a(C0729q2 c0729q2, C0729q2 c0729q3, Consumer consumer) {
        for (C0729q2 c0729q4 : a(c0729q3, c0729q2)) {
            if (!R) {
                c();
            }
            C0767v2 c0767v2 = c0729q4.d;
            if ((!c0729q4.isInterface() && this.u.c(c0729q4)) || ((c0729q4.e.T() && this.l.contains(c0767v2)) || g(c0729q4) || d(c0729q4))) {
                consumer.accept(c0729q4);
            }
        }
    }

    public C3733i(C3733i c3733i, IdentityHashMap identityHashMap) {
        super(c3733i.g().a(c3733i.b()), c3733i.p(), c3733i.f(), c3733i.q());
        this.P = new i4();
        this.Q = null;
        this.k = c3733i.k;
        this.l = c3733i.l;
        this.m = c3733i.m;
        this.n = c3733i.n;
        this.o = c3733i.o;
        this.p = c3733i.p;
        this.q = c3733i.q;
        this.r = c3733i.r;
        this.s = c3733i.s;
        this.t = c3733i.t;
        this.u = c3733i.u;
        this.w = c3733i.w;
        this.x = c3733i.x;
        this.v = c3733i.v;
        this.y = c3733i.y;
        this.z = c3733i.z;
        this.A = c3733i.A;
        this.B = c3733i.B;
        this.C = c3733i.C;
        this.D = c3733i.D;
        this.E = c3733i.E;
        this.F = c3733i.F;
        this.H = c3733i.H;
        this.G = c3733i.G;
        this.L = c3733i.L;
        this.M = c3733i.M;
        this.N = c3733i.N;
        this.O = identityHashMap;
        this.I = c3733i.I;
        this.J = c3733i.J;
        this.K = c3733i.K;
        c3733i.k();
        if (R) {
            return;
        }
        F();
    }
}
