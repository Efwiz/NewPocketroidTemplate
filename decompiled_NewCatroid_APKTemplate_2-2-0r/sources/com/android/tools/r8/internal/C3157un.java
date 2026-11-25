package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0639e;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0644e4;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0692k3;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3546c0;
import com.android.tools.r8.ir.optimize.C3579q;
import com.android.tools.r8.ir.optimize.EnumC3542a0;
import com.android.tools.r8.ir.optimize.InterfaceC3556e0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.AbstractC3896g;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.un, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3157un {
    private static final O3 p = O3.a();
    static final /* synthetic */ boolean q = true;
    private final C0702m a;
    private final C0722p1 b;
    private final C3546c0 c;
    private final Function d;
    private final TE e;
    private final com.android.tools.r8.graph.o5 f;
    private final AbstractC2412ko g;
    private S10 h;
    private C0729q2 i;
    private AbstractC3121uK j;
    private final IdentityHashMap k = new IdentityHashMap();
    private final CN l = CN.b();
    private final IdentityHashMap m = new IdentityHashMap();
    private final ArrayList n = new ArrayList();
    private final C2244ia o;

    C3157un(C0702m c0702m, C3546c0 c3546c0, Function function, TE te, com.android.tools.r8.graph.o5 o5Var, AbstractC2412ko abstractC2412ko) {
        this.a = c0702m;
        this.b = c0702m.a();
        this.c = c3546c0;
        this.d = function;
        this.f = o5Var;
        this.g = abstractC2412ko;
        this.e = te;
        this.o = new C2244ia(abstractC2412ko.b());
    }

    private void g() {
        S10 s10 = this.h;
        Set<S10> setC = OU.c();
        setC.add(s10);
        Set<ML> setC2 = OU.c();
        final com.android.tools.r8.utils.O3 o3E = com.android.tools.r8.utils.O3.e();
        this.h.h0().forEach(new Consumer() { // from class: com.android.tools.r8.internal.un$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                o3E.c((AbstractC2412ko) obj);
            }
        });
        this.h.g0().forEach(new Consumer() { // from class: com.android.tools.r8.internal.un$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                o3E.c((ML) obj);
            }
        });
        while (o3E.c()) {
            InterfaceC2729oo interfaceC2729oo = (InterfaceC2729oo) o3E.f();
            if (interfaceC2729oo.i()) {
                ML mlL = interfaceC2729oo.l();
                setC2.add(mlL);
                mlL.h0().forEach(new Consumer() { // from class: com.android.tools.r8.internal.un$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        o3E.c((AbstractC2412ko) obj);
                    }
                });
                mlL.g0().forEach(new Consumer() { // from class: com.android.tools.r8.internal.un$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        o3E.c((ML) obj);
                    }
                });
            } else {
                AbstractC2412ko abstractC2412koK = interfaceC2729oo.k();
                if (p.b(abstractC2412koK)) {
                    setC.add(abstractC2412koK.b());
                    abstractC2412koK.b().h0().forEach(new Consumer() { // from class: com.android.tools.r8.internal.un$$ExternalSyntheticLambda4
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            o3E.c((AbstractC2412ko) obj);
                        }
                    });
                    abstractC2412koK.b().g0().forEach(new Consumer() { // from class: com.android.tools.r8.internal.un$$ExternalSyntheticLambda5
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            o3E.c((ML) obj);
                        }
                    });
                }
            }
        }
        for (ML ml : setC2) {
            for (S10 s11 : ml.k0()) {
                O3 o3 = p;
                s11.getClass();
                S10 s10A = s11.a(o3, CM.a());
                if (s10A.i() && !setC2.contains(s10A.l())) {
                    throw new C3089ts("Unexpected non-trivial phi in method eligible for class inlining");
                }
                if (!s10A.i() && !setC.contains(s10A)) {
                    throw new C3089ts("Unexpected non-trivial phi in method eligible for class inlining");
                }
            }
            ml.g(this.h);
            ml.n0();
        }
        for (S10 s12 : setC) {
            if (s12 != this.h) {
                if (!q) {
                    AbstractC2412ko abstractC2412ko = s12.b;
                    abstractC2412ko.getClass();
                    if (!(abstractC2412ko instanceof N3) && !s12.b.w1()) {
                        throw new AssertionError();
                    }
                }
                s12.g(this.h);
                a(s12.b);
            }
        }
        boolean z = q;
        if (!z && !this.h.a(C1552Zd.a()).stream().noneMatch(new VH$$ExternalSyntheticLambda0())) {
            throw new AssertionError();
        }
        if (!z && !this.h.a(C1552Zd.a()).stream().noneMatch(new Kj$$ExternalSyntheticLambda4())) {
            throw new AssertionError();
        }
    }

    private void h() {
        for (AbstractC2412ko abstractC2412ko : this.h.h0()) {
            if (!abstractC2412ko.S1()) {
                throw new C3405y10("Unexpected usage left in method `" + this.f.w() + "` after field reads removed: " + abstractC2412ko);
            }
            if (!q && !this.g.p2()) {
                throw new AssertionError();
            }
            if (((C3733i) this.a.f()).a(this.i, abstractC2412ko.V().getField()).s() == null) {
                throw new C3405y10("Unexpected field write left in method `" + this.f.w() + "` after field reads removed: " + abstractC2412ko);
            }
            a(abstractC2412ko);
        }
    }

    final IdentityHashMap a() {
        return this.m;
    }

    final C0729q2 b() {
        return this.i;
    }

    final ArrayList c() {
        return this.n;
    }

    final C2244ia d() {
        return this.o;
    }

    final EnumC1851da e() {
        return (EnumC1851da) this.d.apply(this.i);
    }

    final EnumC1851da f() {
        S10 s10B = this.g.b();
        this.h = s10B;
        if (s10B == null) {
            return EnumC1851da.b;
        }
        if (this.g.p2()) {
            C0729q2 c0729q2A = C0729q2.a(this.a.c(this.g.t0().h));
            this.i = c0729q2A;
            if (c0729q2A == null) {
                return EnumC1851da.b;
            }
            C0729q2 c0729q2A2 = this.f.a();
            C0729q2 c0729q2 = this.i;
            if (c0729q2A2 == c0729q2) {
                return EnumC1851da.b;
            }
            return c0729q2.a(this.a, this.f) ? EnumC1851da.b : EnumC1851da.a;
        }
        if (!q) {
            AbstractC2412ko abstractC2412ko = this.g;
            abstractC2412ko.getClass();
            if (!(abstractC2412ko instanceof QW)) {
                throw new AssertionError();
            }
        }
        QW qwF0 = this.g.F0();
        C0692k3 c0692k3M = ((C3733i) this.a.f()).c(qwF0.getField()).m();
        if (c0692k3M == null) {
            return EnumC1851da.b;
        }
        if (this.f.a() == c0692k3M.d()) {
            return EnumC1851da.b;
        }
        if (qwF0.b(this.a, this.f, C2332jo.a)) {
            return EnumC1851da.b;
        }
        com.android.tools.r8.ir.optimize.info.e eVarX0 = c0692k3M.s().X0();
        AbstractC1154Lf abstractC1154LfG = eVarX0.g();
        if (!abstractC1154LfG.j() || !abstractC1154LfG.f().e()) {
            return EnumC1851da.b;
        }
        C0729q2 c0729q2A3 = C0729q2.a(this.a.c(abstractC1154LfG.e().R()));
        this.i = c0729q2A3;
        if (c0729q2A3 == null) {
            return EnumC1851da.b;
        }
        B1 b1F = eVarX0.f();
        this.j = b1F.B() ? b1F.p().t() : C2096gg.f();
        return EnumC1851da.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(com.android.tools.r8.graph.o5 o5Var) {
        return o5Var.e().i1().A();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(com.android.tools.r8.graph.o5 o5Var) {
        return o5Var.e().C1() && !o5Var.e().i1().l().e();
    }

    final InterfaceC2729oo a(com.android.tools.r8.utils.P1 p1) {
        com.android.tools.r8.graph.T0 t0S;
        AbstractC0748t0 abstractC0748t0H;
        com.android.tools.r8.ir.optimize.Z z;
        com.android.tools.r8.graph.o5 o5VarF;
        if (this.h.C()) {
            return this.h.j();
        }
        Set<AbstractC2412ko> setH0 = this.h.h0();
        while (!setH0.isEmpty()) {
            Set<AbstractC2412ko> setC = OU.c();
            Iterator<AbstractC2412ko> it = setH0.iterator();
            while (it.hasNext()) {
                AbstractC2412ko next = it.next();
                next.getClass();
                if ((next instanceof N3) || next.w1()) {
                    if (next.w1()) {
                        C2972s9 c2972s9Y = next.y();
                        if (!c2972s9Y.J2().O0() || !((C3733i) this.a.f()).c(this.i.d, c2972s9Y.J2())) {
                            return next;
                        }
                    }
                    S10 s10B = next.b();
                    if (this.o.d(s10B)) {
                        continue;
                    } else {
                        if (s10B.C()) {
                            return s10B.j();
                        }
                        if (!this.o.b(s10B)) {
                            return next;
                        }
                        setC.addAll(s10B.h0());
                    }
                } else {
                    if (next instanceof C1189Mn) {
                        com.android.tools.r8.graph.T0 t0S2 = ((C3733i) this.a.f()).c(next.N().getField()).s();
                        if (t0S2 != null && !t0S2.E0()) {
                            AbstractC2412ko abstractC2412ko = this.g;
                            abstractC2412ko.getClass();
                            if (!(abstractC2412ko instanceof QW) || this.j.a(this.a, t0S2, this.f, new Predicate() { // from class: com.android.tools.r8.internal.un$$ExternalSyntheticLambda1
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return ((AbstractC3292wV) obj).A();
                                }
                            })) {
                            }
                        }
                        return next;
                    }
                    if (next.S1()) {
                        AbstractC2412ko abstractC2412ko2 = this.g;
                        abstractC2412ko2.getClass();
                        if ((abstractC2412ko2 instanceof QW) || !this.o.a(next.V().value()) || (t0S = ((C3733i) this.a.f()).c(next.N().getField()).s()) == null || t0S.E0()) {
                            return next;
                        }
                    } else {
                        if (!next.Y1()) {
                            if (next.N1()) {
                                C1015Gl c1015GlQ = next.Q();
                                EnumC1076Il enumC1076IlL2 = c1015GlQ.L2();
                                if (!c1015GlQ.N2() || (enumC1076IlL2 != EnumC1076Il.EQ && enumC1076IlL2 != EnumC1076Il.NE)) {
                                }
                            }
                            return next;
                        }
                        AbstractC1305Qs abstractC1305QsB0 = next.b0();
                        com.android.tools.r8.graph.I4.c<?> cVarO = ((C3733i) this.a.f()).b(abstractC1305QsB0.S2(), abstractC1305QsB0.R2()).o();
                        if (cVarO == null || cVarO.a(this.f, this.a).b() || (abstractC0748t0H = abstractC1305QsB0.h(this.a, this.f)) == null) {
                            return next;
                        }
                        if (abstractC0748t0H instanceof C0644e4) {
                            C0644e4 c0644e4W = abstractC0748t0H.W();
                            if (this.a.x().a(abstractC1305QsB0, c0644e4W)) {
                                if (!abstractC1305QsB0.d1() || !abstractC1305QsB0.b().y()) {
                                }
                            } else if (c0644e4W.getReference() != this.b.f4.e || (abstractC1305QsB0.d1() && abstractC1305QsB0.b().y())) {
                            }
                        }
                        com.android.tools.r8.graph.o5 o5VarG0 = abstractC0748t0H.g0();
                        if (!a(o5VarG0)) {
                            return next;
                        }
                        boolean z2 = q;
                        if (!z2 && !AbstractC0639e.a(o5VarG0.a(), this.f, this.a).d()) {
                            throw new AssertionError();
                        }
                        if (next.a(this.b)) {
                            C1166Ls c1166LsZ = next.Z();
                            if (this.g.p2() && this.g.b() == c1166LsZ.U2()) {
                                if (!z2 && !this.b.b(c1166LsZ.S2())) {
                                    throw new AssertionError();
                                }
                                if (!z2 && !a(o5VarG0)) {
                                    throw new AssertionError();
                                }
                                if (!this.o.c(c1166LsZ.U2())) {
                                    z = null;
                                    break;
                                }
                                List listH1 = c1166LsZ.h1();
                                int i = 1;
                                while (true) {
                                    ArrayList arrayList = (ArrayList) listH1;
                                    if (i >= arrayList.size()) {
                                        if (c1166LsZ.S2().e == this.i.d) {
                                            AbstractC1380Tn abstractC1380TnA = o5VarG0.e().i1().a(c1166LsZ);
                                            if (abstractC1380TnA.g() && AbstractC3896g.a(this.f, o5VarG0, this.a.Y(), JH.w())) {
                                                C0684j2 c0684j2C = abstractC1380TnA.c();
                                                while (true) {
                                                    if (c0684j2C == this.b.g4.g) {
                                                        z = new com.android.tools.r8.ir.optimize.Z(this.i, o5VarG0);
                                                        break;
                                                    }
                                                    if (c0684j2C != null) {
                                                        C0702m c0702m = this.a;
                                                        com.android.tools.r8.graph.o5 o5Var = this.f;
                                                        c0702m.getClass();
                                                        C0729q2 c0729q2A = C0729q2.a(c0702m.b(o5Var.a(), c0684j2C.e));
                                                        if (c0729q2A != null && (o5VarF = c0729q2A.f(c0684j2C)) != null && !this.e.b(o5VarF)) {
                                                            com.android.tools.r8.graph.W0 w0E = o5VarF.e();
                                                            if (w0E.a(this.f, EnumC3542a0.b, (C0680j) this.a.f(), JH.w()) && AbstractC3896g.a(this.f, o5VarF, this.a.Y(), JH.w())) {
                                                                c0684j2C = w0E.i1().l().c();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            break;
                                        }
                                    } else if (this.o.a((S10) arrayList.get(i))) {
                                        i++;
                                    }
                                    z = null;
                                    break;
                                }
                                if (z != null) {
                                    this.k.put(abstractC1305QsB0, z);
                                }
                            }
                            return next;
                        }
                        if (((!abstractC1305QsB0.W1() || abstractC1305QsB0.a(this.b)) && !(abstractC1305QsB0 instanceof C1248Os) && !(abstractC1305QsB0 instanceof C1721bt) && !abstractC1305QsB0.f2()) || ((abstractC1305QsB0.Z1() && abstractC1305QsB0.c0().U2().w().N().f()) || !((InterfaceC3556e0) p1.d()).a(abstractC1305QsB0, cVarO, o5VarG0, EnumC3542a0.b, JH.w()) || !a(abstractC1305QsB0, o5VarG0, setC))) {
                            return next;
                        }
                        this.k.put(abstractC1305QsB0, new com.android.tools.r8.ir.optimize.Z(null, o5VarG0));
                        if (!z2 && this.e.b(o5VarG0)) {
                            throw new AssertionError();
                        }
                        InterfaceC2743oz interfaceC2743ozV1 = o5VarG0.a().V1();
                        if (!interfaceC2743ozV1.p() || !interfaceC2743ozV1.g().u()) {
                            this.m.put(abstractC1305QsB0, o5VarG0);
                        }
                    }
                }
            }
            setH0 = setC;
        }
        return null;
    }

    private void b(C2409kl c2409kl, Set set, C3579q c3579q) {
        Set setC = OU.c();
        Set<AbstractC2412ko> setH0 = this.h.h0();
        for (AbstractC2412ko abstractC2412ko : setH0) {
            if (abstractC2412ko.a1()) {
                C3332x4 c3332x4H = abstractC2412ko.h();
                if (setC.add(c3332x4H)) {
                    InterfaceC2641no interfaceC2641noA = c3332x4H.a(c2409kl);
                    while (interfaceC2641noA.hasNext()) {
                        AbstractC2412ko next = interfaceC2641noA.next();
                        if (setH0.contains(next)) {
                            if (next.Q1()) {
                                c3579q.a(next.b());
                                if (next.g1()) {
                                    C1189Mn c1189MnT = abstractC2412ko.T();
                                    com.android.tools.r8.graph.Y0 field = c1189MnT.getField();
                                    C0702m c0702m = this.a;
                                    C0767v2 c0767v2B0 = field.B0();
                                    com.android.tools.r8.graph.o5 o5Var = this.f;
                                    c0702m.getClass();
                                    com.android.tools.r8.graph.T0 t0C = field.c(c0702m.b(o5Var.a(), c0767v2B0));
                                    if (t0C != null) {
                                        B1 b1A = this.j.a(t0C);
                                        if (b1A.A()) {
                                            AbstractC2467lV abstractC2467lVN = b1A.n();
                                            if (abstractC2467lVN.b(this.a, this.f)) {
                                                interfaceC2641noA.a(abstractC2467lVN.a(this.a, c2409kl.h(), c2409kl.e, c1189MnT), (Set<S10>) set);
                                            } else {
                                                b(c1189MnT);
                                                throw null;
                                            }
                                        } else {
                                            b(c1189MnT);
                                            throw null;
                                        }
                                    } else {
                                        b(c1189MnT);
                                        throw null;
                                    }
                                } else {
                                    interfaceC2641noA.s();
                                }
                            } else if (next.S1()) {
                                interfaceC2641noA.s();
                            } else {
                                throw new C3405y10("Unexpected usage left in method `" + this.f.w() + "` after inlining: " + abstractC2412ko);
                            }
                        }
                    }
                } else {
                    continue;
                }
            }
        }
    }

    private void b(C1189Mn c1189Mn) {
        throw this.a.a0().b("Unexpected usage left in method `" + this.f.w() + "` after inlining: " + c1189Mn.toString());
    }

    final boolean a(C2409kl c2409kl, Set set, C3579q c3579q, C3387xn c3387xn) throws C3084tn {
        boolean z;
        if (!q) {
            S10 s10 = this.h;
            if (s10 != s10.m()) {
                throw new AssertionError();
            }
        }
        if (this.k.isEmpty()) {
            z = false;
        } else {
            this.c.a(this.f, c2409kl, this.k, c3387xn, this.e, com.android.tools.r8.utils.y3.b());
            if (this.g.p2()) {
                do {
                    this.k.clear();
                    for (AbstractC2412ko abstractC2412ko : this.h.h0()) {
                        if (abstractC2412ko.W1()) {
                            C1166Ls c1166LsZ = abstractC2412ko.Z();
                            if (c1166LsZ.U2().m() != this.h) {
                                continue;
                            } else {
                                C0684j2 c0684j2S2 = c1166LsZ.S2();
                                C0722p1 c0722p1 = this.b;
                                if (c0684j2S2 != c0722p1.g4.g) {
                                    if (c0722p1.b(c0684j2S2)) {
                                        C0702m c0702m = this.a;
                                        com.android.tools.r8.graph.o5 o5Var = this.f;
                                        c0702m.getClass();
                                        C0729q2 c0729q2A = C0729q2.a(c0702m.b(o5Var.a(), c0684j2S2.e));
                                        if (c0729q2A != null) {
                                            com.android.tools.r8.graph.o5 o5VarF = c0729q2A.f(c0684j2S2);
                                            if (o5VarF != null && o5VarF.e().a(this.f, EnumC3542a0.b, (C0680j) this.a.f(), JH.w())) {
                                                this.k.put(c1166LsZ, new com.android.tools.r8.ir.optimize.Z(this.i, o5VarF));
                                                break;
                                            }
                                            throw new C3084tn();
                                        }
                                        throw new C3084tn();
                                    }
                                    throw new C3084tn();
                                }
                            }
                        }
                    }
                    if (!this.k.isEmpty()) {
                        this.c.a(this.f, c2409kl, this.k, c3387xn, this.e, com.android.tools.r8.utils.y3.b());
                    }
                } while (!this.k.isEmpty());
            }
            z = true;
        }
        boolean zA = a(c2409kl, c3387xn) | z;
        g();
        a(c2409kl, set);
        Set setC = OU.c();
        if (this.g.p2()) {
            a(c2409kl, setC, c3579q);
        } else {
            if (!q) {
                AbstractC2412ko abstractC2412ko2 = this.g;
                abstractC2412ko2.getClass();
                if (!(abstractC2412ko2 instanceof QW)) {
                    throw new AssertionError();
                }
            }
            b(c2409kl, setC, c3579q);
        }
        if (!setC.isEmpty()) {
            new C3030t00(this.a, false).a((Iterable) setC);
        }
        h();
        a(this.g);
        return zA;
    }

    private boolean a(C2409kl c2409kl, C3387xn c3387xn) throws C3084tn {
        if (this.l.isEmpty()) {
            return false;
        }
        IdentityHashMap identityHashMap = new IdentityHashMap();
        Set<AbstractC2412ko> setH0 = this.h.h0();
        while (!setH0.isEmpty()) {
            Set<AbstractC2412ko> setC = OU.c();
            for (AbstractC2412ko abstractC2412ko : setH0) {
                abstractC2412ko.getClass();
                if (!(abstractC2412ko instanceof N3) && !abstractC2412ko.w1()) {
                    if (abstractC2412ko.Z1()) {
                        AbstractC1332Rs abstractC1332RsC0 = abstractC2412ko.c0();
                        if (abstractC1332RsC0.S2() == this.b.g4.g) {
                            continue;
                        } else {
                            S10 s10U2 = abstractC1332RsC0.U2();
                            O3 o3 = p;
                            s10U2.getClass();
                            if (s10U2.a(o3, CM.a()) != this.h) {
                                continue;
                            } else {
                                com.android.tools.r8.graph.o5 o5VarA = AbstractC0748t0.a(abstractC1332RsC0.a(this.a, this.f, AbstractC1154Lf.a(C1229Oa.b(this.i.getType(), WH.b(), (C0702m<? extends C0680j>) this.a))));
                                if (o5VarA != null && this.l.b(o5VarA)) {
                                    identityHashMap.put(abstractC1332RsC0, new com.android.tools.r8.ir.optimize.Z(null, o5VarA));
                                } else {
                                    throw new C3084tn();
                                }
                            }
                        }
                    } else {
                        continue;
                    }
                } else {
                    setC.addAll(abstractC2412ko.b().h0());
                }
            }
            setH0 = setC;
        }
        if (!identityHashMap.isEmpty()) {
            this.c.a(this.f, c2409kl, identityHashMap, c3387xn, this.e, com.android.tools.r8.utils.y3.b());
            return true;
        }
        if (q || this.l.stream().filter(new Predicate() { // from class: com.android.tools.r8.internal.un$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3157un.b((com.android.tools.r8.graph.o5) obj);
            }
        }).allMatch(new Predicate() { // from class: com.android.tools.r8.internal.un$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3157un.c((com.android.tools.r8.graph.o5) obj);
            }
        })) {
            return true;
        }
        throw new AssertionError();
    }

    private void a(C2409kl c2409kl, Set set) {
        boolean z = false;
        for (AbstractC2412ko abstractC2412ko : this.h.h0()) {
            if (abstractC2412ko.R1()) {
                C1641ao c1641aoU = abstractC2412ko.U();
                abstractC2412ko.h().a(c2409kl, c1641aoU).a(c2409kl, ((C3733i) this.a.f()).c(this.i.getType(), c1641aoU.J2()));
            } else {
                if (abstractC2412ko.Y1()) {
                    AbstractC1305Qs abstractC1305QsB0 = abstractC2412ko.b0();
                    if (abstractC2412ko.W1() && this.g.p2() && abstractC1305QsB0.S2() == this.b.g4.g) {
                        a(abstractC1305QsB0);
                    } else if (abstractC2412ko.d2()) {
                        if (!q && abstractC1305QsB0.S2() != this.b.f4.e) {
                            throw new AssertionError();
                        }
                        a(abstractC1305QsB0);
                    } else {
                        AbstractC0748t0 abstractC0748t0H = abstractC1305QsB0.h(this.a, this.f);
                        if (abstractC0748t0H != null && abstractC0748t0H.i0() && this.a.x().a(abstractC1305QsB0, abstractC0748t0H.W()) && (!abstractC1305QsB0.d1() || !abstractC1305QsB0.b().y())) {
                            a(abstractC1305QsB0);
                        }
                    }
                }
                if (abstractC2412ko.N1()) {
                    C1015Gl c1015GlQ = abstractC2412ko.Q();
                    boolean z2 = q;
                    if (!z2 && !c1015GlQ.N2()) {
                        throw new AssertionError("Unexpected usage in non-zero-test IF instruction: " + abstractC2412ko);
                    }
                    C3332x4 c3332x4H = abstractC2412ko.h();
                    EnumC1076Il enumC1076IlL2 = c1015GlQ.L2();
                    if (!z2 && enumC1076IlL2 != EnumC1076Il.EQ && enumC1076IlL2 != EnumC1076Il.NE) {
                        throw new AssertionError("Unexpected type in zero-test IF instruction: " + abstractC2412ko);
                    }
                    EnumC1076Il enumC1076Il = EnumC1076Il.EQ;
                    C3332x4 c3332x4J2 = enumC1076IlL2 == enumC1076Il ? c1015GlQ.J2() : c1015GlQ.K2();
                    C3332x4 c3332x4K2 = enumC1076IlL2 == enumC1076Il ? c1015GlQ.K2() : c1015GlQ.J2();
                    if (!z2 && c3332x4J2 == c3332x4K2) {
                        throw new AssertionError();
                    }
                    c3332x4H.b(c3332x4K2, c3332x4J2);
                    c3332x4K2.a(c3332x4H, (Set) null);
                    if (!z2 && !c3332x4H.m().M1()) {
                        throw new AssertionError();
                    }
                    if (!z2 && AbstractC2967s4.a(c3332x4H) != c3332x4J2) {
                        throw new AssertionError();
                    }
                    z = true;
                } else if (!abstractC2412ko.Q1() && !abstractC2412ko.S1()) {
                    if (abstractC2412ko.j2()) {
                        a(abstractC2412ko);
                    } else {
                        throw new C3405y10("Unexpected usage left in method `" + this.f.w() + "` after inlining: " + abstractC2412ko);
                    }
                }
            }
        }
        if (z) {
            set.addAll(c2409kl.z());
        }
    }

    private void a(C2409kl c2409kl, Set set, C3579q c3579q) {
        TreeSet treeSet = new TreeSet(Comparator.comparingInt(new ToIntFunction() { // from class: com.android.tools.r8.internal.un$$ExternalSyntheticLambda6
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return C3157un.a((C1189Mn) obj);
            }
        }));
        for (AbstractC2412ko abstractC2412ko : this.h.h0()) {
            if (abstractC2412ko.Q1()) {
                c3579q.a(abstractC2412ko.b());
                if (abstractC2412ko.g1()) {
                    treeSet.add(abstractC2412ko.T());
                } else {
                    a(abstractC2412ko);
                }
            } else if (abstractC2412ko.S1()) {
                if (!q && !this.g.p2()) {
                    throw new AssertionError();
                }
            } else {
                throw new C3405y10("Unexpected usage left in method `" + this.f.w() + "` after inlining: " + abstractC2412ko);
            }
        }
        IdentityHashMap identityHashMap = new IdentityHashMap();
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            a(c2409kl, (C1189Mn) it.next(), set, identityHashMap);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(C1189Mn c1189Mn) {
        return c1189Mn.b().u();
    }

    private void a(final C2409kl c2409kl, C1189Mn c1189Mn, Set set, IdentityHashMap identityHashMap) {
        S10 s10B = c1189Mn.b();
        if (s10B != null) {
            S10 s10B2 = ((C2174hi) identityHashMap.computeIfAbsent(c1189Mn.getField(), new Function() { // from class: com.android.tools.r8.internal.un$$ExternalSyntheticLambda8
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a(c2409kl, (com.android.tools.r8.graph.Y0) obj);
                }
            })).b(c1189Mn.h(), c1189Mn);
            s10B.g(s10B2);
            Iterator it = identityHashMap.values().iterator();
            while (it.hasNext()) {
                ((C2174hi) it.next()).a(s10B, s10B2);
            }
            if (!q && s10B.y()) {
                throw new AssertionError();
            }
            set.add(s10B2);
            set.addAll(s10B2.a());
        }
        a((AbstractC2412ko) c1189Mn);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C2174hi a(C2409kl c2409kl, com.android.tools.r8.graph.Y0 y0) {
        return new C2174hi(y0, c2409kl, this.g, this.a);
    }

    private boolean a(final AbstractC1305Qs abstractC1305Qs, final com.android.tools.r8.graph.o5 o5Var, final Set set) {
        for (int i = 0; i < ((ArrayList) abstractC1305Qs.J2()).size(); i++) {
            S10 s10C = abstractC1305Qs.c(i);
            if (this.o.d(s10C)) {
                if (!b(abstractC1305Qs, o5Var, i, set)) {
                    return false;
                }
            } else {
                final int i2 = i;
                this.o.a(s10C, new BooleanSupplier() { // from class: com.android.tools.r8.internal.un$$ExternalSyntheticLambda7
                    @Override // java.util.function.BooleanSupplier
                    public final boolean getAsBoolean() {
                        return this.f$0.b(abstractC1305Qs, o5Var, i2, set);
                    }
                });
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public boolean b(AbstractC1305Qs abstractC1305Qs, com.android.tools.r8.graph.o5 o5Var, int i, Set set) {
        AbstractC3938n2 abstractC3938n2;
        boolean zA;
        S10 s10B;
        InterfaceC2090ga interfaceC2090gaK = o5Var.e().i1().k();
        if (this.g.p2()) {
            if (!interfaceC2090gaK.a(this.a, this.i, i)) {
                return false;
            }
        } else {
            if (!q) {
                AbstractC2412ko abstractC2412ko = this.g;
                abstractC2412ko.getClass();
                if (!(abstractC2412ko instanceof QW)) {
                    throw new AssertionError();
                }
            }
            if (!interfaceC2090gaK.a(this.a, this.i, i, this.j, this.f)) {
                return false;
            }
        }
        AbstractC3204vL abstractC3204vLA = interfaceC2090gaK.a(i);
        if (o5Var.e().i1().k().a(i).f()) {
            if (o5Var.e().i1().E()) {
                if (!q && o5Var.e().i1().r() != i) {
                    throw new AssertionError();
                }
                abstractC3938n2 = AbstractC3938n2.a;
            } else {
                abstractC3938n2 = AbstractC3938n2.c;
            }
        } else {
            abstractC3938n2 = AbstractC3938n2.b;
        }
        if (!abstractC3938n2.a() && (s10B = abstractC1305Qs.b()) != null && s10B.y()) {
            if (!s10B.C() && !s10B.z() && !abstractC3938n2.e()) {
                if (!q && !abstractC3938n2.d()) {
                    throw new AssertionError();
                }
                if (this.o.b(s10B)) {
                    set.addAll(s10B.h0());
                }
            }
            return false;
        }
        if (!abstractC3204vLA.e()) {
            for (C0684j2 c0684j2 : abstractC3204vLA.b().n()) {
                com.android.tools.r8.graph.I4.c<?> cVarO = ((C3733i) this.a.f()).g(this.i, c0684j2).o();
                if (cVarO != null && cVarO.d().e0()) {
                    com.android.tools.r8.graph.o5 o5VarG0 = cVarO.c().g0();
                    if (a(o5VarG0) && !o5VarG0.e().D1().d()) {
                        boolean z = q;
                        if (!z && !a(o5VarG0)) {
                            throw new AssertionError();
                        }
                        com.android.tools.r8.graph.I4 i4E = ((C3733i) this.a.f()).e(this.i, c0684j2);
                        if ((!i4E.x() || i4E.t().H1()) && o5VarG0.e().H1() && this.f.e() != o5VarG0.e()) {
                            InterfaceC2090ga interfaceC2090gaK2 = o5VarG0.e().i1().k();
                            if (this.g.p2()) {
                                zA = interfaceC2090gaK2.a(this.a, this.i, 0);
                            } else {
                                if (!z) {
                                    AbstractC2412ko abstractC2412ko2 = this.g;
                                    abstractC2412ko2.getClass();
                                    if (!(abstractC2412ko2 instanceof QW)) {
                                        throw new AssertionError();
                                    }
                                }
                                zA = interfaceC2090gaK2.a(this.a, this.i, 0, this.j, this.f);
                            }
                        } else {
                            zA = false;
                        }
                        if (zA) {
                            AbstractC3204vL abstractC3204vLA2 = o5VarG0.e().i1().k().a(0);
                            if (!z && abstractC3204vLA2.g()) {
                                throw new AssertionError();
                            }
                            if (!abstractC3204vLA2.e()) {
                                C3494zH c3494zHB = abstractC3204vLA2.b();
                                if (!c3494zHB.n().isEmpty() || c3494zHB.f()) {
                                }
                            }
                            if (!z && this.e.b(o5VarG0)) {
                                throw new AssertionError();
                            }
                            InterfaceC2743oz interfaceC2743ozV1 = o5VarG0.a().V1();
                            if (!(interfaceC2743ozV1.p() && interfaceC2743ozV1.g().u())) {
                                this.n.add(o5VarG0);
                            }
                            this.l.add(o5VarG0);
                        }
                    }
                }
                return false;
            }
        }
        return true;
    }

    private boolean a(com.android.tools.r8.graph.o5 o5Var) {
        return (o5Var == null || this.e.b(o5Var) || AbstractC0639e.a(o5Var, o5Var.a(), this.f, this.a).b() || !this.a.a(o5Var).f(this.a.Y()) || !o5Var.e().a(this.f, EnumC3542a0.b, (C0680j) this.a.f(), JH.w())) ? false : true;
    }

    private static void a(final AbstractC2412ko abstractC2412ko) {
        ((ArrayList) abstractC2412ko.h1()).forEach(new Consumer() { // from class: com.android.tools.r8.internal.un$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((S10) obj).e(abstractC2412ko);
            }
        });
        abstractC2412ko.h().g(abstractC2412ko);
    }
}
