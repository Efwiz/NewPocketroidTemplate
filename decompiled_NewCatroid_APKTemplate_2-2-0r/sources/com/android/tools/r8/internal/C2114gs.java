package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.q2$$ExternalSyntheticLambda28;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.MethodPosition;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gs, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2114gs implements C7 {
    static final /* synthetic */ boolean k = true;
    private final C0702m a;
    private final com.android.tools.r8.utils.A1 b;
    final C0722p1 c;
    private final int d;
    private final C1566Zr e;
    private final Set f = OU.c();
    private final CN g = CN.c();
    private final ConcurrentHashMap h = new ConcurrentHashMap();
    private final Set i;
    private final Set j;

    private C2114gs(C0702m c0702m, AbstractC1048Hm abstractC1048Hm, AbstractC1048Hm abstractC1048Hm2, int i) {
        this.a = c0702m;
        this.i = abstractC1048Hm;
        this.j = abstractC1048Hm2;
        this.b = c0702m.Y();
        this.c = c0702m.a();
        boolean z = k;
        if (!z && i != 2 && i != 1) {
            throw new AssertionError();
        }
        this.d = i;
        if (!z && i != 2 && !c0702m.Y().Z0()) {
            throw new AssertionError();
        }
        this.e = new C1566Zr(c0702m);
        c();
    }

    public static C2114gs a(C0702m c0702m, AbstractC1048Hm abstractC1048Hm, AbstractC1048Hm abstractC1048Hm2) {
        int i;
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        Z8 z8 = C1566Zr.c;
        if (a1Y.Z0()) {
            i = 1;
        } else {
            i = a1Y.C1.m().isEmpty() ? 3 : 2;
        }
        if (i == 3) {
            return null;
        }
        return new C2114gs(c0702m, abstractC1048Hm, abstractC1048Hm2, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(com.android.tools.r8.graph.o5 o5Var) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Collection d(AbstractC0748t0 abstractC0748t0, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        return a(this.e.b(abstractC0748t0, g7).getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(com.android.tools.r8.graph.o5 o5Var) {
        f(o5Var);
    }

    private static void f(com.android.tools.r8.graph.o5 o5Var) {
        if (!o5Var.a().Z1()) {
            o5Var.e().b(Z8.h);
            return;
        }
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        Z8 z8 = Z8.h;
        Z8 z8U1 = o5Var.a().U1();
        if (!z8.e(z8U1)) {
            z8 = z8U1;
        }
        w0E.b(z8);
    }

    @Override // com.android.tools.r8.internal.C7
    public final boolean b() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(I7 i7, com.android.tools.r8.graph.o5 o5Var) {
        if (i7.K()) {
            f(o5Var);
        }
        a(i7.T().B0(), o5Var);
    }

    private void c() {
        Iterator it = this.b.C1.m().keySet().iterator();
        while (it.hasNext()) {
            AbstractC0727q0 abstractC0727q0C = this.a.c((C0767v2) it.next());
            if (abstractC0727q0C != null) {
                Iterator it2 = abstractC0727q0C.h(new q2$$ExternalSyntheticLambda28()).iterator();
                while (it2.hasNext()) {
                    this.f.add(((com.android.tools.r8.graph.W0) it2.next()).getReference().f);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(com.android.tools.r8.graph.o5 o5Var) {
        this.a.a0().warning(new StringDiagnostic("Interface method desugaring has inserted NoSuchMethodError replacing a super call in " + o5Var.w(), o5Var.getOrigin()));
    }

    private C3224ve b(AbstractC0727q0 abstractC0727q0, final I7 i7, final com.android.tools.r8.graph.o5 o5Var) {
        if (!abstractC0727q0.isInterface()) {
            return C3224ve.a().a(new InterfaceC3147ue() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda8
                @Override // com.android.tools.r8.internal.InterfaceC3147ue
                public final void a() {
                    this.f$0.a(o5Var);
                }
            }).a();
        }
        if (this.a.D().h(i7.T().B0())) {
            return C3224ve.c();
        }
        if (abstractC0727q0.f0() && !this.e.a(abstractC0727q0)) {
            if (this.b.G()) {
                return C3224ve.a().a(new InterfaceC3147ue() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda9
                    @Override // com.android.tools.r8.internal.InterfaceC3147ue
                    public final void a() {
                        this.f$0.b(o5Var);
                    }
                }).a();
            }
            if (this.g.b(o5Var)) {
                return C3224ve.c();
            }
            return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda10
                @Override // com.android.tools.r8.internal.InterfaceC2864qe
                public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                    return this.f$0.a(i7, interfaceC1374Ti, ac, g7, o5Var2, c1230Ob, d7, c0722p1);
                }
            }).a();
        }
        com.android.tools.r8.graph.I4.c<?> cVarO = this.a.g().f(abstractC0727q0, i7.T()).o();
        if (abstractC0727q0.isInterface() && (cVarO == null || !cVarO.r().E0())) {
            return a(i7, cVarO, o5Var);
        }
        boolean z = k;
        if (!z && cVarO == null) {
            throw new AssertionError();
        }
        if (!z && !cVarO.r().E0()) {
            throw new AssertionError();
        }
        final AbstractC0748t0 abstractC0748t0C = cVarO.c();
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda12
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.d(abstractC0748t0C, interfaceC1374Ti, ac, g7, o5Var2, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    public static void a(final C0664h c0664h, com.android.tools.r8.utils.A1 a1) {
        C2215iD c2215iD = a1.C1;
        c2215iD.m().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda17
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2114gs.a(c0664h, (C0767v2) obj, (C2556mg) obj2);
            }
        });
        c2215iD.l().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda18
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2114gs.a(c0664h, (C0767v2) obj, (C3004sd) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Collection c(AbstractC0748t0 abstractC0748t0, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        return a(this.e.a(abstractC0748t0, g7).getReference());
    }

    private C3224ve c(I7 i7, final com.android.tools.r8.graph.o5 o5Var) {
        final C0684j2 c0684j2T = i7.T();
        C0702m c0702m = this.a;
        C0767v2 c0767v2 = c0684j2T.e;
        c0702m.getClass();
        AbstractC0727q0 abstractC0727q0B = c0702m.b(o5Var.a(), c0767v2);
        if (abstractC0727q0B == null) {
            return C3224ve.a().a(new InterfaceC3147ue() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda23
                @Override // com.android.tools.r8.internal.InterfaceC3147ue
                public final void a() {
                    this.f$0.a(o5Var, c0684j2T);
                }
            }).a();
        }
        final com.android.tools.r8.graph.I4.c<?> cVarO = this.a.g().g(abstractC0727q0B, c0684j2T).o();
        if (abstractC0727q0B.isInterface() && (cVarO == null || cVarO.r().E0())) {
            return a(i7, cVarO, o5Var);
        }
        if (abstractC0727q0B.isInterface() && !cVarO.c().a().f0()) {
            if (cVarO.r().L1()) {
                C0702m c0702m2 = this.a;
                if (cVarO.a(o5Var, c0702m2, c0702m2.g()).a()) {
                    return a(i7, (com.android.tools.r8.graph.I4.c) null, o5Var);
                }
                return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda1
                    @Override // com.android.tools.r8.internal.InterfaceC2864qe
                    public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                        return this.f$0.a(cVarO, interfaceC1374Ti, ac, g7, o5Var2, c1230Ob, d7, c0722p1);
                    }
                }).a();
            }
            return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda2
                @Override // com.android.tools.r8.internal.InterfaceC2864qe
                public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                    return this.f$0.a(cVarO, c0684j2T, interfaceC1374Ti, ac, g7, o5Var2, c1230Ob, d7, c0722p1);
                }
            }).a();
        }
        C3224ve c3224veA = a(abstractC0727q0B, c0684j2T, o5Var);
        if (c3224veA.b()) {
            return c3224veA;
        }
        if (o5Var.H()) {
            return Q1.a(this.a, i7, new InterfaceC3147ue() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda3
                @Override // com.android.tools.r8.internal.InterfaceC3147ue
                public final void a() {
                    this.f$0.d(o5Var);
                }
            });
        }
        return C3224ve.a().a(new InterfaceC3147ue() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.internal.InterfaceC3147ue
            public final void a() {
                this.f$0.e(o5Var);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0664h c0664h, C0767v2 c0767v2, C2556mg c2556mg) {
        a(c0664h, c0767v2);
        a(c0664h, c2556mg.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0664h c0664h, C0767v2 c0767v2, C3004sd c3004sd) {
        a(c0664h, c0767v2);
        a(c0664h, c3004sd.b().B0());
        a(c0664h, c3004sd.a().B0());
    }

    private static void a(final C0664h c0664h, C0767v2 c0767v2) {
        c0664h.a().i(c0767v2);
        AbstractC0727q0 abstractC0727q0C = c0664h.c(c0767v2);
        if (abstractC0727q0C != null && abstractC0727q0C.f0() && abstractC0727q0C.isInterface()) {
            abstractC0727q0C.f(new Consumer() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2114gs.a(c0664h, (com.android.tools.r8.graph.W0) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0664h c0664h, com.android.tools.r8.graph.W0 w0) {
        if (w0.w1()) {
            c0664h.a().i(w0.getReference().h.e);
            for (C0767v2 c0767v2 : w0.getReference().h.f.a) {
                c0664h.a().i(c0767v2);
            }
        }
    }

    public final Set a() {
        return this.f;
    }

    private boolean a(final I7 i7, final com.android.tools.r8.graph.o5 o5Var) {
        return AbstractC2734ot.b(this.i, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda14
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C2114gs.a(i7, o5Var, (C7) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(I7 i7, com.android.tools.r8.graph.o5 o5Var, C7 c7) {
        return c7.a(o5Var, i7).b();
    }

    private boolean a(final J7 j7, final com.android.tools.r8.graph.o5 o5Var) {
        return AbstractC2734ot.b(this.j, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C2114gs.a(j7, o5Var, (C7) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(J7 j7, com.android.tools.r8.graph.o5 o5Var, C7 c7) {
        return c7.a(o5Var, j7).b();
    }

    @Override // com.android.tools.r8.internal.C7
    public final void a(com.android.tools.r8.graph.o5 o5Var, G7 g7) {
        if (this.d == 2) {
            return;
        }
        if (this.a.D().a(o5Var)) {
            f(o5Var);
            return;
        }
        for (B7 b7 : o5Var.e().a1().K().I0()) {
            b7.getClass();
            if ((b7 instanceof J7) && !a(b7.n(), o5Var)) {
                C0721p0 c0721p0T = b7.n().T();
                a(o5Var, c0721p0T.f);
                for (com.android.tools.r8.graph.B2 b2 : c0721p0T.g) {
                    b2.getClass();
                    if (b2 instanceof com.android.tools.r8.graph.G2) {
                        a(o5Var, (C0699l2) b2.G0().b);
                    }
                }
            }
            a(o5Var, b7).d();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection a(final I7 i7, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        com.android.tools.r8.graph.o5 o5VarB = this.a.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.D;
            }
        }, c1230Ob.a(), this.a, new Consumer() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(i7, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        this.g.add(o5VarB);
        g7.i(o5VarB, o5Var);
        f(o5VarB);
        return a(o5VarB.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final I7 i7, com.android.tools.r8.synthesis.H h) {
        h.a(i7.T().H0()).a(C0725p4.Q()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda21
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.a(i7, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W a(I7 i7, C0684j2 c0684j2) {
        return C1071Ii.a(this.c).b(i7.T(), true).d(c0684j2).a();
    }

    private C3224ve a(I7 i7) {
        com.android.tools.r8.graph.I4 i4B = this.a.g().b(i7.T(), i7.V());
        C1566Zr c1566Zr = this.e;
        c1566Zr.getClass();
        C2404kg c2404kgB = c1566Zr.b(i4B.a(), i4B.c());
        final C2630ne c2630neB = c2404kgB == null ? null : c2404kgB.b();
        if (c2630neB == null) {
            return null;
        }
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda22
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.b(c2630neB, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Collection b(C2630ne c2630ne, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        return a(this.e.a(c2630ne, (InterfaceC2632ng) g7).getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection b(AbstractC0748t0 abstractC0748t0, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        C0684j2 reference;
        if (abstractC0748t0.e().L1()) {
            if (abstractC0748t0 instanceof com.android.tools.r8.graph.o5) {
                reference = this.e.c(abstractC0748t0.g0(), g7).getReference();
            } else {
                reference = this.e.b(abstractC0748t0);
            }
        } else {
            reference = this.e.a(abstractC0748t0, g7).getReference();
        }
        return a(reference);
    }

    private C3224ve a(AbstractC0727q0 abstractC0727q0, I7 i7, com.android.tools.r8.graph.o5 o5Var) {
        Origin originUnknown;
        C0684j2 c0684j2T = i7.T();
        if (!abstractC0727q0.isInterface()) {
            return C3224ve.c();
        }
        if (!abstractC0727q0.f0()) {
            com.android.tools.r8.graph.I4 i4B = this.a.g().b(c0684j2T, i7.V());
            if (i4B.h()) {
                return a(i7, (com.android.tools.r8.graph.I4.c) null, o5Var);
            }
            com.android.tools.r8.graph.I4.c<?> cVarO = i4B.o();
            if (cVarO == null) {
                return C3224ve.c();
            }
            final AbstractC0748t0 abstractC0748t0A = abstractC0727q0.a(c0684j2T);
            if (abstractC0748t0A != null) {
                if (k || abstractC0748t0A.e() == cVarO.c().e()) {
                    return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda19
                        @Override // com.android.tools.r8.internal.InterfaceC2864qe
                        public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                            return this.f$0.b(abstractC0748t0A, interfaceC1374Ti, ac, g7, o5Var2, c1230Ob, d7, c0722p1);
                        }
                    }).a();
                }
                throw new AssertionError();
            }
            final AbstractC0748t0 abstractC0748t0B = this.a.g().b(abstractC0727q0, c0684j2T);
            if (abstractC0748t0B != null) {
                if (!k && abstractC0748t0B.e() != cVarO.c().e()) {
                    throw new AssertionError();
                }
                return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda20
                    @Override // com.android.tools.r8.internal.InterfaceC2864qe
                    public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                        return this.f$0.c(abstractC0748t0B, interfaceC1374Ti, ac, g7, o5Var2, c1230Ob, d7, c0722p1);
                    }
                }).a();
            }
            if (k) {
                return C3224ve.c();
            }
            throw new AssertionError();
        }
        String str = "Unexpected call to a private method defined in library class " + abstractC0727q0.r0();
        C0767v2 c0767v2C = o5Var.getReference().e;
        if (C1566Zr.d(c0767v2C)) {
            c0767v2C = this.e.c(c0767v2C);
        }
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2C);
        if (abstractC0727q0C == null) {
            originUnknown = Origin.unknown();
        } else {
            originUnknown = abstractC0727q0C.c;
        }
        throw new C1367Tb(str, originUnknown);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(com.android.tools.r8.graph.o5 o5Var) {
        f(o5Var);
    }

    final C1937ee b(C0729q2 c0729q2, AbstractC0727q0 abstractC0727q0, C0767v2 c0767v2) {
        C1937ee c1937ee = (C1937ee) this.h.get(c0767v2);
        if (c1937ee != null) {
            return c1937ee;
        }
        C1937ee c1937eeA = a(c0729q2, abstractC0727q0, c0767v2);
        C1937ee c1937ee2 = (C1937ee) this.h.putIfAbsent(c0767v2, c1937eeA);
        return c1937ee2 != null ? c1937ee2 : c1937eeA;
    }

    private C3224ve a(I7 i7, com.android.tools.r8.graph.I4.c cVar, com.android.tools.r8.graph.o5 o5Var) {
        if (k || !a(i7, o5Var)) {
            return Q1.a(this.a, i7, cVar);
        }
        throw new AssertionError();
    }

    private static List a(C0684j2 c0684j2) {
        return Collections.singletonList(new I7(184, c0684j2, false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.o5 o5Var) {
        f(o5Var);
    }

    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(final com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        if (this.a.D().a(o5Var)) {
            return C3224ve.c();
        }
        final I7 i7L = b7.l();
        if (i7L == null) {
            return C3224ve.c();
        }
        if (a(i7L, o5Var)) {
            return C3224ve.c();
        }
        if (i7L.J()) {
            if (i7L.T().b(this.c)) {
                return C3224ve.c();
            }
        }
        if (this.d == 2) {
            if (!i7L.T().B0().O0()) {
                return C3224ve.c();
            }
            C0702m c0702m = this.a;
            C0684j2 c0684j2T = i7L.T();
            c0702m.getClass();
            AbstractC0727q0 abstractC0727q0B = c0702m.b(o5Var.a(), c0684j2T.e);
            if (abstractC0727q0B == null) {
                return C3224ve.c();
            }
            if (!i7L.V()) {
                if (i7L.J()) {
                    return a(abstractC0727q0B, i7L.T(), o5Var);
                }
                if (!i7L.L() && !i7L.I()) {
                    return C3224ve.c();
                }
                C3224ve c3224veA = a(i7L);
                return c3224veA == null ? C3224ve.c() : c3224veA;
            }
            if (!i7L.L() && !i7L.I()) {
                return C3224ve.c();
            }
            C0680j c0680jG = this.a.g();
            com.android.tools.r8.graph.I4.c<?> cVarO = c0680jG.b(i7L.T(), i7L.V()).o();
            if (cVarO != null && cVarO.r().O0() && cVarO.a(o5Var, this.a, c0680jG).d()) {
                return C3224ve.c();
            }
            if (cVarO != null && cVarO.r().E0()) {
                return C3224ve.c();
            }
            C3224ve c3224veA2 = a(i7L);
            return c3224veA2 != null ? c3224veA2 : C3224ve.c();
        }
        if (!i7L.V()) {
            if (!k && i7L.V()) {
                throw new AssertionError();
            }
            if (i7L.J()) {
                C0702m c0702m2 = this.a;
                C0684j2 c0684j2T2 = i7L.T();
                c0702m2.getClass();
                AbstractC0727q0 abstractC0727q0B2 = c0702m2.b(o5Var.a(), c0684j2T2.e);
                if (abstractC0727q0B2 == null) {
                    return C3224ve.c();
                }
                return a(abstractC0727q0B2, i7L.T(), o5Var);
            }
            if (!i7L.L() && !i7L.I()) {
                return C3224ve.c();
            }
            C3224ve c3224veA3 = a(i7L);
            if (c3224veA3 != null) {
                return c3224veA3;
            }
            C0680j c0680jG2 = this.a.g();
            com.android.tools.r8.graph.I4 i4B = c0680jG2.b(i7L.T(), i7L.V());
            if (i4B.x() && i4B.o().r().E0()) {
                AbstractC0748t0 abstractC0748t0B = c0680jG2.b(c0680jG2.b(o5Var.a(), i7L.T().B0()), i7L.T());
                if (abstractC0748t0B != null && abstractC0748t0B.H()) {
                    return a(i7L, i4B.o(), o5Var);
                }
                return C3224ve.c();
            }
            return C3224ve.c();
        }
        C0702m c0702m3 = this.a;
        C0684j2 c0684j2T3 = i7L.T();
        c0702m3.getClass();
        AbstractC0727q0 abstractC0727q0B3 = c0702m3.b(o5Var.a(), c0684j2T3.e);
        if (abstractC0727q0B3 == null) {
            if (!i7L.L() && !i7L.I()) {
                return C3224ve.a().a(new InterfaceC3147ue() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda5
                    @Override // com.android.tools.r8.internal.InterfaceC3147ue
                    public final void a() {
                        this.f$0.b(i7L, o5Var);
                    }
                }).a();
            }
            return C3224ve.c();
        }
        if (!i7L.L() && !i7L.I()) {
            if (i7L.K()) {
                return b(abstractC0727q0B3, i7L, o5Var);
            }
            if (i7L.J()) {
                if (i7L.b(o5Var.t())) {
                    return c(i7L, o5Var);
                }
                return a(abstractC0727q0B3, i7L, o5Var);
            }
            return C3224ve.c();
        }
        C0680j c0680jG3 = this.a.g();
        com.android.tools.r8.graph.I4.c<?> cVarO2 = c0680jG3.b(i7L.T(), i7L.V()).o();
        if (cVarO2 != null && cVarO2.r().O0() && cVarO2.a(o5Var, this.a, c0680jG3).d()) {
            return a(abstractC0727q0B3, i7L, o5Var);
        }
        if (cVarO2 != null && cVarO2.r().E0()) {
            return a(i7L, cVarO2, o5Var);
        }
        C3224ve c3224veA4 = a(i7L);
        return c3224veA4 != null ? c3224veA4 : C3224ve.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.o5 o5Var, C0684j2 c0684j2) {
        a(c0684j2.e, o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection a(com.android.tools.r8.graph.I4.c cVar, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        C0684j2 c0684j2B;
        AbstractC0748t0 abstractC0748t0C = cVar.c();
        if (abstractC0748t0C instanceof com.android.tools.r8.graph.o5) {
            c0684j2B = this.e.c(abstractC0748t0C.g0(), g7).getReference();
        } else {
            c0684j2B = this.e.b(abstractC0748t0C);
        }
        return a(c0684j2B);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection a(com.android.tools.r8.graph.I4.c cVar, C0684j2 c0684j2, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        AbstractC0748t0 abstractC0748t0C = cVar.c();
        C0729q2 c0729q2A = o5Var.a();
        C0684j2 c0684j2A = b(c0729q2A, c0729q2A, c0684j2.e).a(c0684j2);
        if (c0684j2A != null) {
            c0684j2 = c0684j2A;
        }
        if (k || abstractC0748t0C.getReference() == c0684j2) {
            return a(this.e.a(abstractC0748t0C, g7).getReference());
        }
        throw new AssertionError();
    }

    private C3224ve a(AbstractC0727q0 abstractC0727q0, C0684j2 c0684j2, com.android.tools.r8.graph.o5 o5Var) {
        C0680j c0680jG = this.a.g();
        C0680j c0680jG2 = this.a.g();
        C0702m c0702m = this.a;
        c0680jG2.getClass();
        final AbstractC0748t0 abstractC0748t0A = c0680jG2.a(c0684j2, o5Var.a(), c0702m, c0680jG);
        if (abstractC0727q0.isInterface() && abstractC0727q0.f0() && this.e.a(abstractC0727q0) && !this.e.e(abstractC0727q0.d) && abstractC0748t0A != null && abstractC0748t0A.e().w1()) {
            AbstractC0727q0 abstractC0727q0A = abstractC0748t0A.a();
            if (abstractC0727q0A.f0() && abstractC0727q0A.isInterface()) {
                return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda15
                    @Override // com.android.tools.r8.internal.InterfaceC2864qe
                    public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                        return this.f$0.a(abstractC0748t0A, interfaceC1374Ti, ac, g7, o5Var2, c1230Ob, d7, c0722p1);
                    }
                }).a();
            }
        }
        final C2630ne c2630neA = this.e.a(abstractC0727q0, abstractC0748t0A);
        if (c2630neA == null) {
            return C3224ve.c();
        }
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda16
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(c2630neA, interfaceC1374Ti, ac, g7, o5Var2, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Collection a(AbstractC0748t0 abstractC0748t0, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        return a(this.e.a(abstractC0748t0, g7).getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Collection a(C2630ne c2630ne, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        return a(this.e.a(c2630ne, (InterfaceC1720bs) g7));
    }

    private void a(com.android.tools.r8.graph.o5 o5Var, C0699l2 c0699l2) {
        if (c0699l2.d.g()) {
            AbstractC0727q0 abstractC0727q0C = this.a.c(c0699l2.u0().e);
            if (abstractC0727q0C == null) {
                a(c0699l2.u0().e, o5Var);
            } else if (abstractC0727q0C.isInterface()) {
                throw new Z00("Desugaring of static interface method handle in `" + o5Var.w() + "` is not yet supported.");
            }
        }
    }

    public final C1952es a(C2569ms c2569ms) {
        return new C1952es(this.a, new Predicate() { // from class: com.android.tools.r8.internal.gs$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2114gs.c((com.android.tools.r8.graph.o5) obj);
            }
        }, c2569ms, this.d);
    }

    public final C1952es a(Predicate predicate, C2569ms c2569ms) {
        return new C1952es(this.a, predicate, c2569ms, this.d);
    }

    private C1937ee a(C0729q2 c0729q2, AbstractC0727q0 abstractC0727q0, C0767v2 c0767v2) {
        C2015fe c2015fe = new C2015fe();
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
        if (abstractC0727q0C == null) {
            this.e.a(c0729q2, abstractC0727q0, c0767v2);
            return c2015fe.a();
        }
        if (abstractC0727q0C.isInterface()) {
            if (abstractC0727q0C.f0() && !this.e.a(abstractC0727q0C)) {
                return c2015fe.a();
            }
            if (abstractC0727q0.e0() && !abstractC0727q0C.f0()) {
                this.a.f().a(abstractC0727q0.a0(), abstractC0727q0C);
            }
            for (C0767v2 c0767v3 : abstractC0727q0C.g.a) {
                c2015fe.a(b(c0729q2, abstractC0727q0C, c0767v3));
            }
            Iterator<com.android.tools.r8.graph.W0> it = abstractC0727q0C.P1().iterator();
            while (it.hasNext()) {
                c2015fe.a(it.next().getReference());
            }
            for (com.android.tools.r8.graph.W0 w0 : abstractC0727q0C.P1()) {
                this.e.getClass();
                if (C1566Zr.a(w0)) {
                    c2015fe.a(w0);
                }
            }
            return c2015fe.a();
        }
        throw new C1367Tb("Type " + c0767v2.r0() + " is referenced as an interface from `" + abstractC0727q0.toString() + "`.");
    }

    private void a(C0767v2 c0767v2, com.android.tools.r8.graph.o5 o5Var) {
        Origin originUnknown;
        if (this.e.f(c0767v2)) {
            return;
        }
        C0684j2 c0684j2A = this.a.E().a((AbstractC1476Wj) null, o5Var.getReference());
        C0767v2 c0767v2C = c0684j2A.e;
        if (C1566Zr.d(c0767v2C)) {
            c0767v2C = this.e.c(c0767v2C);
        }
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2C);
        if (abstractC0727q0C == null) {
            originUnknown = Origin.unknown();
        } else {
            originUnknown = abstractC0727q0C.c;
        }
        this.b.a(originUnknown, new MethodPosition(c0684j2A.E0()), c0767v2, c0684j2A);
    }
}
