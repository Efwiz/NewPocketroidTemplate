package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0649f0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C3$$ExternalSyntheticLambda1;
import com.android.tools.r8.graph.EnumC0608b0;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.n5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2964s2;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.InterfaceC1285Pz;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.utils.C3879c3;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.h, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3728h {
    static final /* synthetic */ boolean g = true;
    private final C0702m a;
    private final L.a b;
    private final com.android.tools.r8.utils.A1 c;
    private final Set d;
    private final C3718f3 e;
    private final Set f;

    private C3728h(C0702m c0702m, Set set, L.a aVar, Set set2) {
        this.a = c0702m;
        this.b = aVar;
        this.c = c0702m.Y();
        this.d = set;
        this.e = c0702m.Y().D0().h();
        this.f = set2;
    }

    public static C3720g a(L.a aVar) {
        return new C3720g(aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public C0633d0 a(InterfaceC0694k5 interfaceC0694k5, C0633d0 c0633d0, EnumC0608b0 enumC0608b0) {
        if (!this.d.contains(c0633d0)) {
            C0702m c0702m = this.a;
            if (!a(c0702m, interfaceC0694k5, c0633d0, ((C3733i) c0702m.f()).n(c0633d0.b.a.b(c0702m.a())), enumC0608b0)) {
                return null;
            }
        }
        return c0633d0.a(new Function() { // from class: com.android.tools.r8.shaking.h$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((com.android.tools.r8.graph.R0) obj);
            }
        });
    }

    public static boolean a(C0702m c0702m, InterfaceC0694k5 interfaceC0694k5, C0633d0 c0633d0, boolean z, EnumC0608b0 enumC0608b0) {
        if (!c0702m.Y().o1()) {
            return true;
        }
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        C3718f3 c3718f3H = a1Y.D0() != null ? a1Y.D0().h() : C3718f3.a(AbstractC1639am.i());
        C0722p1 c0722p1A = c0702m.a();
        int i = c0633d0.a;
        if (i == 0) {
            C0759u2 c0759u2D0 = c0633d0.t0().D0();
            C0759u2 c0759u2 = a1Y.a.l1;
            c0759u2D0.getClass();
            if (c0759u2D0.a(c0759u2.e)) {
                return true;
            }
            enumC0608b0.getClass();
            if (enumC0608b0 == EnumC0608b0.d) {
                if (!a1Y.e1()) {
                    return false;
                }
            } else {
                if (!c0633d0.v0() && !a1Y.d1()) {
                    return false;
                }
                if (c0633d0.v0() && !a1Y.f1()) {
                    return false;
                }
            }
            return z;
        }
        if (i == 1) {
            if (interfaceC0694k5.e0() && interfaceC0694k5.a0().e.T() && c0633d0.t0() == c0722p1A.j3) {
                return true;
            }
            enumC0608b0.getClass();
            if (enumC0608b0 == EnumC0608b0.d) {
                if (!a1Y.h1()) {
                    return false;
                }
            } else {
                if (!c0633d0.v0() && !a1Y.g1()) {
                    return false;
                }
                if (c0633d0.v0() && !a1Y.i1()) {
                    return false;
                }
            }
            return z;
        }
        if (i != 2) {
            throw new C3405y10("Unexpected annotation visibility.");
        }
        enumC0608b0.getClass();
        if (enumC0608b0 == EnumC0608b0.d) {
            return false;
        }
        boolean z2 = g;
        if (!z2 && C0633d0.j(c0633d0, c0722p1A)) {
            throw new AssertionError();
        }
        if (!z2 && C0633d0.k(c0633d0, c0722p1A)) {
            throw new AssertionError();
        }
        if (!z2 && C0633d0.i(c0633d0, c0722p1A)) {
            throw new AssertionError();
        }
        if (!z2 && C0633d0.h(c0633d0, c0722p1A)) {
            throw new AssertionError();
        }
        if (!z2 && !a1Y.o && C0633d0.p(c0633d0, c0722p1A)) {
            throw new AssertionError();
        }
        if (c3718f3H.f && c0633d0.b.a == c0722p1A.h5) {
            return true;
        }
        C0767v2 c0767v2 = c0633d0.b.a;
        if (c0767v2 != c0722p1A.g5) {
            return (c3718f3H.j && c0767v2 == c0722p1A.V4) || c0767v2 == c0722p1A.Q4;
        }
        if (!z2 && !interfaceC0694k5.e0()) {
            throw new AssertionError();
        }
        c0702m.a(interfaceC0694k5.a0(), c0633d0.b.b[0].b.K0());
        return c3718f3H.k;
    }

    private void b() {
        for (C0729q2 c0729q2 : ((C3733i) this.a.f()).d()) {
            if (c0729q2.V1().a()) {
                c0729q2.k(new Consumer() { // from class: com.android.tools.r8.shaking.h$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C3728h.a((n5) obj);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public C0649f0 a(C0767v2 c0767v2, final C0649f0 c0649f0) {
        AbstractC0727q0 abstractC0727q0C;
        if ((this.a.a().Q4.equals(c0767v2) && this.a.a().h1.equals(c0649f0.a)) || (abstractC0727q0C = this.a.c(c0767v2)) == null) {
            return c0649f0;
        }
        if (!g && !abstractC0727q0C.isInterface()) {
            throw new AssertionError();
        }
        if (abstractC0727q0C.Y().e(new Predicate() { // from class: com.android.tools.r8.shaking.h$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3728h.a(c0649f0, (com.android.tools.r8.graph.W0) obj);
            }
        })) {
            return c0649f0;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0633d0 b(C0633d0 c0633d0, EnumC0608b0 enumC0608b0) {
        if (this.d.contains(c0633d0)) {
            return c0633d0;
        }
        return null;
    }

    public final C3728h a() {
        C3718f3 c3718f3 = this.e;
        boolean z = this.a.Y().S0;
        if (z && c3718f3.c != c3718f3.d) {
            c3718f3.d = true;
            c3718f3.c = true;
        }
        boolean z2 = c3718f3.c;
        if (z2 && !c3718f3.d) {
            throw new C1367Tb("Attribute InnerClasses requires EnclosingMethod attribute. Check -keepattributes directive.");
        }
        if (!z2 && c3718f3.d) {
            throw new C1367Tb("Attribute EnclosingMethod requires InnerClasses attribute. Check -keepattributes directive.");
        }
        if (z && c3718f3.h && !c3718f3.g) {
            c3718f3.g = true;
        }
        if (!c3718f3.h || c3718f3.g) {
            return this;
        }
        throw new C1367Tb("Attribute LocalVariableTable requires LineNumberTable. Check -keepattributes directive.");
    }

    public final void a(ExecutorService executorService) {
        C3879c3.a(((C3733i) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.shaking.h$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        }, executorService);
        if (g) {
            return;
        }
        b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final C0729q2 c0729q2) {
        Y0 y0A = this.a.v().a(c0729q2);
        a((InterfaceC0694k5) c0729q2, (AbstractC3722g1) y0A);
        a(c0729q2, y0A);
        final Set setC = OU.c();
        c0729q2.k(new Consumer() { // from class: com.android.tools.r8.shaking.h$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0729q2, setC, (n5) obj);
            }
        });
        c0729q2.k(new Consumer() { // from class: com.android.tools.r8.shaking.h$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3728h.a(setC, (n5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Set set, n5 n5Var) {
        InterfaceC1285Pz interfaceC1285PzZ = n5Var.Z();
        if (!interfaceC1285PzZ.d() || set.contains(interfaceC1285PzZ.o())) {
            return;
        }
        n5Var.e().G0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(n5 n5Var) {
        if (!g && !n5Var.Z().a()) {
            throw new AssertionError("Should have pruned kotlin info");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2, Set set, n5 n5Var) {
        AbstractC3735i1 abstractC3735i1V = this.a.v();
        abstractC3735i1V.getClass();
        AbstractC3740j1 abstractC3740j1A = abstractC3735i1V.a(n5Var.e(), n5Var.a());
        a(n5Var, abstractC3740j1A);
        if (abstractC3740j1A.d(this.c)) {
            n5Var.e().E();
        }
        if (!n5Var.Z().d()) {
            com.android.tools.r8.utils.A1 a1 = this.c;
            if (c0729q2.V1().a() || !abstractC3740j1A.c((K0) a1)) {
                n5Var.e().G0();
            }
        }
        if (n5Var.Z().d()) {
            com.android.tools.r8.utils.A1 a2 = this.c;
            if (c0729q2.V1().a() || !abstractC3740j1A.c((K0) a2)) {
                return;
            }
            set.add(n5Var.Z().o());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.R0 a(com.android.tools.r8.graph.R0 r0) {
        AbstractC1476Wj abstractC1476WjE = this.a.E();
        C0767v2 c0767v2B = r0.a.b(this.a.a());
        if (this.f.contains(c0767v2B)) {
            return null;
        }
        final C0767v2 c0767v2A = AbstractC2964s2.a(abstractC1476WjE, c0767v2B);
        com.android.tools.r8.graph.R0 r0A = r0.a(new C3$$ExternalSyntheticLambda1(abstractC1476WjE), new Function() { // from class: com.android.tools.r8.shaking.h$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0767v2A, (C0649f0) obj);
            }
        });
        boolean z = g;
        AbstractC0727q0 abstractC0727q0C = ((C3733i) this.a.f()).c(c0767v2A);
        if (z || abstractC0727q0C == null || ((C3733i) this.a.f()).n(c0767v2A)) {
            return r0A;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0649f0 c0649f0, com.android.tools.r8.graph.W0 w0) {
        return w0.getReference().f == c0649f0.a;
    }

    private void a(final InterfaceC0694k5 interfaceC0694k5, AbstractC3722g1 abstractC3722g1) {
        if (!g && !this.b.c() && !this.d.isEmpty()) {
            throw new AssertionError();
        }
        boolean z = interfaceC0694k5.e0() && interfaceC0694k5.a0().e.T();
        if (!abstractC3722g1.b(this.c)) {
            interfaceC0694k5.a(new BiFunction() { // from class: com.android.tools.r8.shaking.h$$ExternalSyntheticLambda8
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return this.f$0.a(interfaceC0694k5, (C0633d0) obj, (EnumC0608b0) obj2);
                }
            });
            return;
        }
        if (z) {
            interfaceC0694k5.a(new BiFunction() { // from class: com.android.tools.r8.shaking.h$$ExternalSyntheticLambda6
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return this.f$0.a((C0633d0) obj, (EnumC0608b0) obj2);
                }
            });
        } else if (this.b.c()) {
            interfaceC0694k5.a(new BiFunction() { // from class: com.android.tools.r8.shaking.h$$ExternalSyntheticLambda7
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return this.f$0.b((C0633d0) obj, (EnumC0608b0) obj2);
                }
            });
        } else {
            interfaceC0694k5.M();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0633d0 a(C0633d0 c0633d0, EnumC0608b0 enumC0608b0) {
        if (a(c0633d0)) {
            return c0633d0;
        }
        return null;
    }

    private boolean a(C0633d0 c0633d0) {
        if (c0633d0.b.a == this.a.a().Q4) {
            return true;
        }
        if (c0633d0.t0() == this.a.a().j3) {
            if (c0633d0.b.s0() != 1) {
                return true;
            }
            C0649f0 c0649f0J = c0633d0.b.j(0);
            if (c0649f0J.a != this.a.a().h1) {
                return true;
            }
            com.android.tools.r8.graph.B2 b2T0 = c0649f0J.t0();
            b2T0.getClass();
            return ((b2T0 instanceof com.android.tools.r8.graph.C2) && b2T0.A0().j1() == this.a.a().t4.a) ? false : true;
        }
        return this.d.contains(c0633d0);
    }

    private void a(final C0729q2 c0729q2, final Y0 y0) {
        c0729q2.i(new Predicate() { // from class: com.android.tools.r8.shaking.h$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(y0, (com.android.tools.r8.graph.O2) obj);
            }
        });
        c0729q2.j(new Predicate() { // from class: com.android.tools.r8.shaking.h$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(c0729q2, (com.android.tools.r8.graph.J3) obj);
            }
        });
        c0729q2.a1().getClass();
        if (y0.d(this.c)) {
            c0729q2.L0();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Y0 y0, com.android.tools.r8.graph.O2 o2) {
        com.android.tools.r8.utils.A1 a1 = this.c;
        C0702m c0702m = this.a;
        y0.getClass();
        return (a1.b1() && (a1.S0 || (y0.c((K0) a1) && o2.a(c0702m)))) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(C0729q2 c0729q2, com.android.tools.r8.graph.J3 j3) {
        return a(c0729q2, j3, c0729q2.b1());
    }

    private boolean a(C0729q2 c0729q2, com.android.tools.r8.graph.J3 j3, com.android.tools.r8.graph.O2 o2) {
        if (j3.d() == null && (c0729q2.E1() || c0729q2.z1())) {
            if (!g && j3.b() == null) {
                throw new AssertionError();
            }
            AbstractC3735i1 abstractC3735i1V = this.a.v();
            C0767v2 c0767v2B = j3.b();
            C0702m c0702m = this.a;
            abstractC3735i1V.getClass();
            Y0 y0A = abstractC3735i1V.a(c0702m.a(c0767v2B));
            com.android.tools.r8.utils.A1 a1 = this.c;
            y0A.getClass();
            if (a1.c1()) {
                if (a1.S0) {
                    return false;
                }
                if (o2 != null && y0A.c((K0) a1)) {
                    return false;
                }
            }
            return true;
        }
        if (j3.d() == null) {
            if (!g && (c0729q2.E1() || c0729q2.z1())) {
                throw new AssertionError();
            }
            AbstractC3735i1 abstractC3735i1V2 = this.a.v();
            C0767v2 c0767v2B2 = j3.b();
            C0702m c0702m2 = this.a;
            abstractC3735i1V2.getClass();
            return abstractC3735i1V2.a(c0702m2.a(c0767v2B2)).c(this.c);
        }
        if (j3.b() == null) {
            if (!g && j3.d() == null) {
                throw new AssertionError();
            }
            AbstractC3735i1 abstractC3735i1V3 = this.a.v();
            C0767v2 c0767v2D = j3.d();
            C0702m c0702m3 = this.a;
            abstractC3735i1V3.getClass();
            return abstractC3735i1V3.a(c0702m3.a(c0767v2D)).c(this.c);
        }
        if (!g && (j3.d() == null || j3.b() == null)) {
            throw new AssertionError();
        }
        AbstractC3735i1 abstractC3735i1V4 = this.a.v();
        C0767v2 c0767v2B3 = j3.b();
        C0702m c0702m4 = this.a;
        abstractC3735i1V4.getClass();
        if (!abstractC3735i1V4.a(c0702m4.a(c0767v2B3)).c(this.c)) {
            AbstractC3735i1 abstractC3735i1V5 = this.a.v();
            C0767v2 c0767v2D2 = j3.d();
            C0702m c0702m5 = this.a;
            abstractC3735i1V5.getClass();
            if (!abstractC3735i1V5.a(c0702m5.a(c0767v2D2)).c(this.c)) {
                return false;
            }
        }
        return true;
    }

    public static void a(C0702m c0702m) {
        for (C0729q2 c0729q2 : c0702m.f().d()) {
            c0729q2.x0();
            c0729q2.L1().forEach(new Consumer() { // from class: com.android.tools.r8.shaking.h$$ExternalSyntheticLambda10
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((com.android.tools.r8.graph.U0) obj).x0();
                }
            });
        }
    }
}
