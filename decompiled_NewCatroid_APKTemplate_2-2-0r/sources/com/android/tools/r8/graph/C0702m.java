package com.android.tools.r8.graph;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.internal.AbstractC0892Cf;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.AbstractC2331jn;
import com.android.tools.r8.internal.AbstractC3104u3;
import com.android.tools.r8.internal.AbstractC3135uW;
import com.android.tools.r8.internal.AbstractC3424yG;
import com.android.tools.r8.internal.C0831Aj;
import com.android.tools.r8.internal.C1079In;
import com.android.tools.r8.internal.C1105Jk;
import com.android.tools.r8.internal.C1121Ka;
import com.android.tools.r8.internal.C1132Kj;
import com.android.tools.r8.internal.C1158Lj;
import com.android.tools.r8.internal.C1257Pb;
import com.android.tools.r8.internal.C1341Sb;
import com.android.tools.r8.internal.C1703bb;
import com.android.tools.r8.internal.C2005fV;
import com.android.tools.r8.internal.C2141hE;
import com.android.tools.r8.internal.C2411kn;
import com.android.tools.r8.internal.C2449lE;
import com.android.tools.r8.internal.C2482li;
import com.android.tools.r8.internal.C2685oB;
import com.android.tools.r8.internal.C2750p20;
import com.android.tools.r8.internal.C2836qE;
import com.android.tools.r8.internal.C2974sB;
import com.android.tools.r8.internal.C3226vg;
import com.android.tools.r8.internal.C3254w2;
import com.android.tools.r8.internal.C3476z2;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.DH;
import com.android.tools.r8.internal.EnumC0928Dk;
import com.android.tools.r8.internal.G00;
import com.android.tools.r8.internal.KK;
import com.android.tools.r8.internal.VP;
import com.android.tools.r8.internal.WP;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.shaking.AbstractC3735i1;
import com.android.tools.r8.shaking.C3686a4;
import com.android.tools.r8.shaking.C3704d1;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3749l;
import com.android.tools.r8.shaking.C3762m1;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.InterfaceC3880d;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.InterfaceC3949p3;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.graph.m, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0702m<T extends C0664h> implements Q0, com.android.tools.r8.shaking.H1 {
    static final /* synthetic */ boolean P = true;
    private C2411kn A;
    private C2750p20 C;
    private com.android.tools.r8.naming.S0 I;
    private final C1341Sb K;
    private final com.android.tools.r8.androidapi.b M;
    private final com.android.tools.r8.androidapi.h N;
    private final int O;
    private C0664h a;
    private C0680j b;
    private C0695l c;
    private AbstractC3104u3 d;
    private final AbstractC0892Cf f;
    private AbstractC2331jn i;
    private com.android.tools.r8.shaking.K2 l;
    private C3686a4 m;
    private AbstractC3135uW o;
    public final G00 t;
    private final C2974sB u;
    private final C3476z2 v;
    private final C2685oB w;
    private final WP x;
    private C3749l e = C3749l.a().a();
    private AbstractC1476Wj g = AbstractC1476Wj.e();
    private AbstractC1476Wj h = AbstractC1476Wj.e();
    private AbstractC1476Wj j = AbstractC1476Wj.e();
    private AbstractC3649o0 k = AbstractC3649o0.a();
    private com.android.tools.r8.shaking.Y3 n = null;
    private AbstractC3735i1 p = null;
    private final com.android.tools.r8.internal.C1 q = new com.android.tools.r8.internal.C1();
    private final C1079In r = new C1079In();
    private final C2005fV s = new C2005fV();
    private boolean y = false;
    private Predicate z = CM.b();
    private C1105Jk B = C1105Jk.a();
    private C3226vg D = null;
    private KK E = KK.a();
    private Set F = AbstractC1048Hm.k();
    private final IdentityHashMap G = new IdentityHashMap();
    private final IdentityHashMap H = new IdentityHashMap();
    private Set J = null;
    private final Thread L = Thread.currentThread();

    private C0702m(C0664h c0664h, AbstractC3104u3 abstractC3104u3, AbstractC3135uW abstractC3135uW, int i, G00 g00, final com.android.tools.r8.utils.y3 y3Var) {
        if (!P && c0664h == null) {
            throw new AssertionError();
        }
        this.a = c0664h;
        this.K = (C1341Sb) y3Var.a("Compilation context", new InterfaceC3949p3() { // from class: com.android.tools.r8.graph.m$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return this.f$0.Q();
            }
        });
        this.d = abstractC3104u3;
        this.o = abstractC3135uW;
        this.f = (AbstractC0892Cf) y3Var.a("Dont warn config", new InterfaceC3949p3() { // from class: com.android.tools.r8.graph.m$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return this.f$0.R();
            }
        });
        this.O = i;
        this.i = (AbstractC2331jn) y3Var.a("Init class lens", new InterfaceC3949p3() { // from class: com.android.tools.r8.graph.m$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return AbstractC2331jn.b();
            }
        });
        this.t = g00;
        y3Var.a("Create argument propagator");
        if (q() && Y().c().b()) {
            this.v = new C3476z2(i0());
        } else {
            this.v = null;
        }
        y3Var.c();
        this.u = (C2974sB) y3Var.a("Library side-effects", new InterfaceC3949p3() { // from class: com.android.tools.r8.graph.m$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return this.f$0.S();
            }
        });
        this.w = (C2685oB) y3Var.a("Library optimizer", new InterfaceC3949p3() { // from class: com.android.tools.r8.graph.m$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return this.f$0.a(y3Var);
            }
        });
        this.x = (WP) y3Var.a("Proto shrinker", new InterfaceC3949p3() { // from class: com.android.tools.r8.graph.m$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return this.f$0.T();
            }
        });
        this.M = (com.android.tools.r8.androidapi.b) y3Var.a("ApiLevel compute", new InterfaceC3949p3() { // from class: com.android.tools.r8.graph.m$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return this.f$0.U();
            }
        });
        this.N = (com.android.tools.r8.androidapi.h) y3Var.a("ApiLevel computed", new InterfaceC3949p3() { // from class: com.android.tools.r8.graph.m$$ExternalSyntheticLambda8
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return this.f$0.V();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C1341Sb Q() {
        return C1341Sb.a(Y());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AbstractC0892Cf R() {
        return AbstractC0892Cf.a(Y().D0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C2974sB S() {
        return new C2974sB(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public WP T() {
        C0702m<C3733i> c0702mI0 = i0();
        if (!c0702mI0.q() || !c0702mI0.Y().x1().c()) {
            return null;
        }
        VP vp = new VP(c0702mI0.a());
        if (c0702mI0.c(vp.i) != null) {
            return new WP(c0702mI0, vp);
        }
        c0702mI0.Y().e.warning(new StringDiagnostic("Ignoring -shrinkunusedprotofields since the protobuf-lite runtime is missing"));
        com.android.tools.r8.utils.A1.o oVarX1 = c0702mI0.Y().x1();
        oVarX1.a = false;
        oVarX1.b = false;
        oVarX1.c = false;
        oVarX1.d = false;
        oVarX1.e = false;
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.androidapi.b U() {
        return com.android.tools.r8.androidapi.b.a((C0702m<?>) this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.androidapi.h V() {
        return this.M.a(Y());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C2685oB a(com.android.tools.r8.utils.y3 y3Var) {
        return new C2685oB(this, y3Var);
    }

    public static C0702m b(C0664h c0664h) {
        return new C0702m(c0664h, AbstractC3104u3.b(), AbstractC3135uW.b(), 2, c0664h.l().J0(), com.android.tools.r8.utils.y3.b());
    }

    public static C0702m c(C0664h c0664h) {
        return new C0702m(c0664h, AbstractC3104u3.b(), AbstractC3135uW.b(), 2, c0664h.l().J0(), com.android.tools.r8.utils.y3.b());
    }

    public final KK A() {
        return this.E;
    }

    public final com.android.tools.r8.shaking.K2 B() {
        return this.l;
    }

    public final AbstractC3135uW C() {
        return this.o;
    }

    public final com.android.tools.r8.synthesis.D D() {
        return this.a.g();
    }

    public AbstractC1476Wj E() {
        return this.h;
    }

    public final boolean F() {
        return !this.F.isEmpty();
    }

    public final boolean G() {
        return f().h();
    }

    public final boolean H() {
        return f().i();
    }

    public final boolean I() {
        return this.n != null;
    }

    public final boolean J() {
        return this.l != null;
    }

    public final boolean K() {
        return this.D != null;
    }

    public final boolean L() {
        return this.C != null;
    }

    public final C1105Jk M() {
        return this.B;
    }

    public final AbstractC2331jn N() {
        return this.i;
    }

    public final C1079In O() {
        return this.r;
    }

    public final boolean P() {
        return this.y;
    }

    public final C2685oB W() {
        return this.w;
    }

    public final void X() {
        if (Y().D0().x()) {
            this.I = com.android.tools.r8.naming.S0.a(Y().e, Y().D0().d());
        }
    }

    public com.android.tools.r8.utils.A1 Y() {
        return this.a.l();
    }

    public final WP Z() {
        return this.x;
    }

    public final com.android.tools.r8.utils.O2 a0() {
        return Y().e;
    }

    public C3686a4 b0() {
        return this.m;
    }

    public final void c0() {
        this.y = true;
    }

    @Override // com.android.tools.r8.shaking.H1
    public final boolean d(C0767v2 c0767v2) {
        return this.w.a(c0767v2);
    }

    public final C2005fV d0() {
        return this.s;
    }

    public AbstractC0681j0 e() {
        return f().b();
    }

    public final C3226vg e0() {
        return K() ? this.D : new C3226vg(AbstractC2178hm.s());
    }

    public T f() {
        if (P || !this.a.h() || q()) {
            return (T) this.a;
        }
        throw new AssertionError();
    }

    public final void f0() {
        for (C0767v2 c0767v2 : this.D.b()) {
            boolean z = P;
            if (!z && this.a.d(c0767v2) != null) {
                throw new AssertionError("Enum " + c0767v2 + " has been unboxed but is still in the program.");
            }
            if (!z && !f().o().p(c0767v2)) {
                throw new AssertionError("Enum " + c0767v2 + " has been unboxed but was not pruned.");
            }
        }
    }

    public C0680j g() {
        if (q()) {
            if (P || this.a.h()) {
                return this.a.n();
            }
            throw new AssertionError();
        }
        if (!P && this.a.h()) {
            throw new AssertionError();
        }
        if (this.b == null) {
            this.b = C0680j.a(f());
        }
        return this.b;
    }

    public final C2750p20 g0() {
        return this.C;
    }

    public final boolean h(C0767v2 c0767v2) {
        if (P || c0767v2.O0()) {
            return this.z.test(c0767v2);
        }
        throw new AssertionError();
    }

    public final C0702m h0() {
        if (this.a.h()) {
            return this;
        }
        return null;
    }

    public final C3733i i() {
        if (f().i()) {
            return this.a.o();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public C0702m<C3733i> i0() {
        return this;
    }

    public final C0695l j() {
        return this.c;
    }

    public final C0702m j0() {
        if (P || !f().h()) {
            return this;
        }
        throw new AssertionError();
    }

    public final boolean k() {
        if (Y().o1()) {
            AbstractC2331jn abstractC2331jn = this.i;
            abstractC2331jn.getClass();
            if (!(abstractC2331jn instanceof C2482li)) {
                return true;
            }
        }
        return false;
    }

    public final void l() {
        this.I = null;
    }

    public final void m() {
        AbstractC1476Wj c1703bb = this.h;
        C0722p1 c0722p1A = a();
        if (c1703bb.f()) {
            c1703bb = new C1703bb(c0722p1A, c1703bb);
        }
        b(c1703bb);
    }

    public final AbstractC1476Wj n() {
        return this.g;
    }

    public final com.android.tools.r8.androidapi.h o() {
        return this.N;
    }

    public final C1257Pb p() {
        boolean z = P;
        if (z || z || this.L == Thread.currentThread()) {
            return this.K.a();
        }
        throw new AssertionError();
    }

    public final boolean q() {
        return this.O == 1;
    }

    public final com.android.tools.r8.naming.S0 r() {
        return this.I;
    }

    public final AbstractC3104u3 s() {
        return this.d;
    }

    public final C3749l t() {
        return this.e;
    }

    public final AbstractC0892Cf u() {
        return this.f;
    }

    public final AbstractC3735i1 v() {
        return this.p;
    }

    public final AbstractC1476Wj w() {
        return this.j;
    }

    public final C2974sB x() {
        return this.u;
    }

    public final com.android.tools.r8.shaking.Y3 y() {
        return this.n;
    }

    public final AbstractC3649o0 z() {
        return this.k;
    }

    public static <T extends C0664h> C0702m<T> a(T t) {
        return new C0702m<>(t, AbstractC3104u3.a(t, t.l()), AbstractC3135uW.b(), 2, t.l().J0(), com.android.tools.r8.utils.y3.b());
    }

    /* JADX WARN: Incorrect types in method signature: <U:TT;>(TU;)Lcom/android/tools/r8/graph/m<TU;>; */
    public C0702m d(C0664h c0664h) {
        if (!P && c0664h.j()) {
            throw new AssertionError();
        }
        C0664h c0664h2 = this.a;
        this.a = c0664h;
        this.b = null;
        if (c0664h != c0664h2) {
            c0664h2.k();
        }
        if (c0664h.i()) {
            this.p = c0664h.o().w();
        }
        return this;
    }

    public final void e(InterfaceC3905h3 interfaceC3905h3) throws Throwable {
        WP wp = this.x;
        if (wp != null) {
            interfaceC3905h3.accept(wp);
        }
    }

    @Override // com.android.tools.r8.graph.Q0
    public final O f(C0767v2 c0767v2) {
        return f().f(c0767v2);
    }

    public final C0680j h() {
        if (f().h()) {
            return this.a.n();
        }
        return null;
    }

    public final com.android.tools.r8.internal.C1 b() {
        return this.q;
    }

    @Override // com.android.tools.r8.graph.Q0
    public final AbstractC0727q0 c(C0767v2 c0767v2) {
        return f().c(c0767v2);
    }

    public static C0702m a(N2 n2, com.android.tools.r8.shaking.N1 n1) {
        com.android.tools.r8.utils.A1 a1 = n2.d;
        int i = C1121Ka.d;
        C0680j c0680jA = C0680j.a(n2, C1121Ka.a(a1.j0(), a1.k, a1.e), n1, com.android.tools.r8.synthesis.y.e());
        return new C0702m(c0680jA, AbstractC3104u3.b(), AbstractC3135uW.b(), 1, c0680jA.l().J0(), com.android.tools.r8.utils.y3.b());
    }

    public final void b(InterfaceC3905h3 interfaceC3905h3) throws Throwable {
        C0831Aj c0831Aj;
        WP wp = this.x;
        if (wp == null || (c0831Aj = wp.c) == null) {
            return;
        }
        interfaceC3905h3.accept(c0831Aj);
    }

    public final Object c(Boolean bool, Function function) {
        WP wp = this.x;
        return wp != null ? function.apply(wp) : bool;
    }

    public final void d(InterfaceC3905h3 interfaceC3905h3) throws Throwable {
        C1158Lj c1158Lj;
        WP wp = this.x;
        if (wp == null || (c1158Lj = wp.d) == null) {
            return;
        }
        interfaceC3905h3.accept(c1158Lj);
    }

    public final String g(C0767v2 c0767v2) {
        return (String) this.H.get(c0767v2);
    }

    public final Object b(Object obj, Function function) {
        C1158Lj c1158Lj;
        WP wp = this.x;
        return (wp == null || (c1158Lj = wp.d) == null) ? obj : function.apply(c1158Lj);
    }

    public final void c(InterfaceC3905h3 interfaceC3905h3) throws Throwable {
        C1132Kj c1132Kj;
        WP wp = this.x;
        if (wp == null || (c1132Kj = wp.e) == null) {
            return;
        }
        interfaceC3905h3.accept(c1132Kj);
    }

    public final com.android.tools.r8.androidapi.b d() {
        return this.M;
    }

    public final boolean b(AbstractC1476Wj abstractC1476Wj) {
        if (abstractC1476Wj == this.h) {
            return false;
        }
        this.h = abstractC1476Wj;
        abstractC1476Wj.getClass();
        if (!(abstractC1476Wj instanceof C3254w2) && !(abstractC1476Wj instanceof C1703bb)) {
            return true;
        }
        this.g = abstractC1476Wj;
        return true;
    }

    public final void c(AbstractC1476Wj abstractC1476Wj) {
        this.j = abstractC1476Wj;
    }

    public final C2836qE c() {
        C2836qE c2836qE = new C2836qE();
        if (!this.B.e()) {
            c2836qE.a(this.B);
        }
        C2750p20 c2750p20 = this.C;
        if (c2750p20 != null) {
            c2836qE.a(c2750p20);
        }
        return c2836qE;
    }

    public final void b(Set set) {
        this.F = set;
    }

    public final Object b(Boolean bool, Function function) {
        C2411kn c2411kn = this.A;
        return c2411kn != null ? function.apply(c2411kn) : bool;
    }

    public static C0702m a(C0664h c0664h, G00 g00, com.android.tools.r8.utils.y3 y3Var) {
        return new C0702m(c0664h, AbstractC3104u3.a(c0664h, c0664h.l()), AbstractC3135uW.b(), 2, g00, y3Var);
    }

    public final void b(DH dh) {
        if (dh != null) {
            a(dh, f().b().b(), h0(), dh.k());
        }
    }

    public final boolean b(C0729q2 c0729q2) {
        if (!Y().i0().b) {
            return false;
        }
        if (P || this.J != null) {
            return this.J.contains(c0729q2.getType());
        }
        throw new AssertionError();
    }

    public static C0702m<C0680j> a(AbstractC0681j0 abstractC0681j0) {
        return a(abstractC0681j0, com.android.tools.r8.shaking.N1.f());
    }

    public static C0702m a(AbstractC0681j0 abstractC0681j0, com.android.tools.r8.shaking.N1 n1) {
        com.android.tools.r8.utils.A1 a1 = abstractC0681j0.d;
        int i = C1121Ka.d;
        C0680j c0680jA = C0680j.a(abstractC0681j0, C1121Ka.a(a1.j0(), a1.k, a1.e), n1, com.android.tools.r8.synthesis.y.e());
        return new C0702m(c0680jA, AbstractC3104u3.a(c0680jA, c0680jA.l()), AbstractC3135uW.a(abstractC0681j0), 1, c0680jA.l().J0(), com.android.tools.r8.utils.y3.b());
    }

    public static C0702m a(C0664h c0664h, G00 g00) {
        return new C0702m(c0664h, AbstractC3104u3.a(c0664h, c0664h.l()), AbstractC3135uW.b(), 2, g00, com.android.tools.r8.utils.y3.b());
    }

    public static C0702m a(C0680j c0680j) {
        return new C0702m(c0680j, AbstractC3104u3.b(), AbstractC3135uW.b(), 1, c0680j.l().J0(), com.android.tools.r8.utils.y3.b());
    }

    public void a(C0695l c0695l) {
        this.c = c0695l;
    }

    public final void a(AbstractC3104u3 abstractC3104u3) {
        this.d = abstractC3104u3;
    }

    public final void a(AbstractC3135uW abstractC3135uW) {
        this.o = abstractC3135uW;
    }

    public final void a(C3749l c3749l) {
        this.e = c3749l;
    }

    public final void a(Predicate predicate) {
        this.z = predicate;
    }

    public final void a(C0729q2 c0729q2, B2.j jVar) {
        this.G.put(c0729q2.d, jVar);
    }

    public final B2.j a(AbstractC0727q0 abstractC0727q0) {
        return (B2.j) this.G.get(abstractC0727q0.d);
    }

    @Override // com.android.tools.r8.graph.Q0
    public C0722p1 a() {
        return this.a.a();
    }

    public final void a(InterfaceC3905h3 interfaceC3905h3) throws Throwable {
        C3476z2 c3476z2 = this.v;
        if (c3476z2 != null) {
            interfaceC3905h3.accept(c3476z2);
        }
    }

    public final Object a(Set set, Function function) {
        return (this.x == null || !Y().x1().e) ? set : function.apply(this.x.f);
    }

    public final Object a(Object obj, Function function) {
        C0831Aj c0831Aj;
        WP wp = this.x;
        return (wp == null || (c0831Aj = wp.c) == null) ? obj : function.apply(c0831Aj);
    }

    public final Object a(Boolean bool, Function function) {
        C1132Kj c1132Kj;
        WP wp = this.x;
        return (wp == null || (c1132Kj = wp.e) == null) ? bool : function.apply(c1132Kj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(C0702m c0702m, DH dh) {
        return dh == c0702m.g;
    }

    public final void a(AbstractC1476Wj abstractC1476Wj) {
        this.g = abstractC1476Wj;
    }

    public final void a(C2482li c2482li) {
        this.i = c2482li;
    }

    public final void a(C2411kn c2411kn) {
        this.A = c2411kn;
    }

    public final boolean a(Supplier supplier) {
        if (Y().n1.p0) {
            return ((Boolean) supplier.get()).booleanValue();
        }
        return true;
    }

    public void a(C3686a4 c3686a4) {
        this.m = c3686a4;
    }

    public final void a(com.android.tools.r8.shaking.Y3 y3) {
        this.n = y3;
    }

    public final com.android.tools.r8.shaking.Y0 a(C0729q2 c0729q2) {
        return this.p.a(c0729q2);
    }

    public final C3704d1 a(m5 m5Var) {
        AbstractC3735i1 abstractC3735i1 = this.p;
        abstractC3735i1.getClass();
        return abstractC3735i1.a(m5Var.e(), m5Var.a());
    }

    public final C3762m1 a(o5 o5Var) {
        AbstractC3735i1 abstractC3735i1 = this.p;
        abstractC3735i1.getClass();
        return abstractC3735i1.a(o5Var.a(), o5Var.e());
    }

    public final void a(AbstractC3649o0 abstractC3649o0) {
        this.k = abstractC3649o0;
    }

    public final void a(com.android.tools.r8.shaking.K2 k2) {
        if (!P && !Y().S0) {
            throw new AssertionError();
        }
        this.l = k2;
    }

    public final void a(C1105Jk c1105Jk, EnumC0928Dk enumC0928Dk) {
        if (!P && !this.B.e() && !enumC0928Dk.a()) {
            throw new AssertionError();
        }
        this.B = this.B.a(c1105Jk);
        if (enumC0928Dk.a()) {
            Y().n1.s.accept(a(), this.B);
        }
    }

    public final void a(C2750p20 c2750p20) {
        if (!P && this.C != null) {
            throw new AssertionError();
        }
        this.C = c2750p20;
        Y().n1.w.accept(a(), c2750p20);
    }

    public void a(KK kk) {
        this.E = kk;
    }

    public final void a(C3226vg c3226vg) {
        if (!P && K()) {
            throw new AssertionError();
        }
        this.D = c3226vg;
        Y().n1.v.accept(a(), c3226vg);
    }

    public final AbstractC3938n2 a(C0767v2 c0767v2, C0767v2 c0767v3) {
        if (f().h()) {
            return AbstractC3938n2.a(f().n().c(c0767v2, c0767v3));
        }
        if (c0767v2 != c0767v3 && c0767v3 != a().Z1) {
            return AbstractC3938n2.c;
        }
        return AbstractC3938n2.a;
    }

    public final boolean a(W0 w0) {
        if (!(Y().f instanceof ClassFileConsumer)) {
            return false;
        }
        if (this.F.contains(w0.getReference())) {
            return true;
        }
        return Y().n1.I0 != null && Y().n1.I0.test(w0.getReference());
    }

    public final void a(u5 u5Var, ExecutorService executorService) {
        if (u5Var.k()) {
            if (!P && f().b() != u5Var.d()) {
                throw new AssertionError();
            }
            return;
        }
        if (this.a.i()) {
            C0702m<C3733i> c0702mI0 = i0();
            c0702mI0.d(((C3733i) c0702mI0.f()).a(u5Var, executorService));
        } else if (this.a.h()) {
            C0702m c0702mH0 = h0();
            c0702mH0.d(((C0680j) c0702mH0.f()).a(u5Var, executorService));
        } else {
            d(f().a(u5Var, executorService));
        }
        C0695l c0695l = this.c;
        if (c0695l != null) {
            a(c0695l.a(u5Var));
        }
        this.d = this.d.a(u5Var);
        this.e = this.e.a(u5Var);
        if (J()) {
            a(this.l.a(u5Var));
        }
        C3686a4 c3686a4 = this.m;
        if (c3686a4 != null) {
            c3686a4.a(u5Var);
        }
        AbstractC3135uW abstractC3135uW = this.o;
        this.a.g();
        this.o = abstractC3135uW.a(u5Var);
        if (I()) {
            this.n = this.n.b(u5Var);
        }
        a(this.E.a(u5Var));
    }

    public final void a(N2 n2, AbstractC3424yG abstractC3424yG) {
        AbstractC1476Wj abstractC1476WjK = abstractC3424yG.k();
        if (!P && n2 == null) {
            throw new AssertionError();
        }
        a(abstractC3424yG, n2, h0(), abstractC1476WjK);
    }

    private static void a(final DH dh, final N2 n2, final C0702m c0702m, AbstractC1476Wj abstractC1476Wj) {
        DH dhA;
        if (dh == null) {
            return;
        }
        boolean zB = c0702m.b((AbstractC1476Wj) dh);
        boolean z = P;
        if (!z && !zB) {
            throw new AssertionError();
        }
        if (!z) {
            n2.a(((C0680j) c0702m.f()).b().b(), dh);
        }
        DH dhD = dh;
        while (dhD.k() != abstractC1476Wj) {
            AbstractC1476Wj abstractC1476WjK = dhD.k();
            boolean z2 = P;
            if (!z2 && !abstractC1476WjK.i()) {
                throw new AssertionError();
            }
            if (!z2 && abstractC1476WjK == c0702m.g) {
                throw new AssertionError();
            }
            dhD = abstractC1476WjK.d();
        }
        AbstractC1476Wj abstractC1476WjE = AbstractC1476Wj.e();
        if (!(dhD instanceof C2449lE) && !(dhD instanceof C2141hE) && (dhA = dhD.a(new Predicate() { // from class: com.android.tools.r8.graph.m$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C0702m.a((DH) obj);
            }
        }, new Predicate() { // from class: com.android.tools.r8.graph.m$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C0702m.a(this.f$0, (DH) obj);
            }
        })) != null) {
            abstractC1476WjE = dhA instanceof C2449lE ? dhA.c().a(c0702m, abstractC1476Wj, dhA) : dhA.b().a(c0702m, abstractC1476Wj, dhA);
        }
        dhD.a(abstractC1476WjE, new InterfaceC3880d() { // from class: com.android.tools.r8.graph.m$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.InterfaceC3880d
            public final void a() {
                C0702m.a(this.f$0, n2, dh);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(DH dh) {
        dh.getClass();
        return (dh instanceof C2449lE) || (dh instanceof C2141hE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0702m c0702m, N2 n2, DH dh) {
        AbstractC1476Wj abstractC1476WjE = AbstractC1476Wj.e();
        if (c0702m.f().i()) {
            c0702m.i0().d(c0702m.i().a(n2, dh));
        } else {
            if (!P && !c0702m.f().h()) {
                throw new AssertionError();
            }
            C0702m c0702mH0 = c0702m.h0();
            C0680j c0680j = (C0680j) c0702mH0.f();
            c0702mH0.d(c0680j.a(c0680j.f().a(c0702m.a.g(), dh)));
        }
        c0702m.a(c0702m.c.a(dh));
        c0702m.d = c0702m.d.a(c0702m, dh);
        c0702m.e = c0702m.e.a(c0702m, dh, abstractC1476WjE);
        AbstractC2331jn abstractC2331jn = c0702m.i;
        if (abstractC2331jn != null) {
            c0702m.i = abstractC2331jn.a(dh);
        }
        if (c0702m.J()) {
            c0702m.a(c0702m.l.a(dh));
        }
        if (c0702m.I()) {
            c0702m.n = c0702m.n.a(dh);
        }
        c0702m.a(c0702m.E.a(dh));
        if (c0702m.m != null) {
            c0702m.a(c0702m.b0().a(dh));
        }
        c0702m.o = c0702m.o.a(dh);
    }

    public final void a(AbstractC3424yG abstractC3424yG) {
        C0702m c0702mJ0 = j0();
        boolean zB = c0702mJ0.b((AbstractC1476Wj) abstractC3424yG);
        if (!P && !zB) {
            throw new AssertionError();
        }
        c0702mJ0.d = c0702mJ0.d.a(c0702mJ0, abstractC3424yG);
    }

    public final void a(Set set) {
        if (!P && this.J != null) {
            throw new AssertionError();
        }
        this.J = set;
    }

    public final void a(String str, C0767v2 c0767v2) {
        this.H.put(c0767v2, str);
    }
}
