package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC0824Ac;
import com.android.tools.r8.internal.AbstractC1380Tn;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC2706oV;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.internal.AbstractC3121uK;
import com.android.tools.r8.internal.AbstractC3292wV;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.B1;
import com.android.tools.r8.internal.C1166Ls;
import com.android.tools.r8.internal.C1189Mn;
import com.android.tools.r8.internal.C1721bt;
import com.android.tools.r8.internal.C1736c3;
import com.android.tools.r8.internal.C1793co;
import com.android.tools.r8.internal.C2179hn;
import com.android.tools.r8.internal.C2332jo;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2750p20;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.DG;
import com.android.tools.r8.internal.EnumC2523mE;
import com.android.tools.r8.internal.InterfaceC0989Fn;
import com.android.tools.r8.internal.InterfaceC1102Jh;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.MR;
import com.android.tools.r8.internal.NG$$ExternalSyntheticLambda0;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.QW;
import com.android.tools.r8.internal.RW;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.Y2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.Z1;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class B0 {
    static final /* synthetic */ boolean j = true;
    private final C0702m a;
    private final o5 b;
    private final C2409kl c;
    private final int d;
    private final boolean e;
    private C3591v0 h;
    private final Set f = OU.c();
    private final w0 g = new w0();
    private final IdentityHashMap i = new IdentityHashMap();

    public B0(C0702m c0702m, C2409kl c2409kl) {
        this.a = c0702m;
        this.b = c2409kl.h();
        this.c = c2409kl;
        this.d = Math.max(50, 10000 / c2409kl.d.size());
        this.e = !c0702m.Y().W0;
    }

    private void b() {
        this.i.forEach(new BiConsumer() { // from class: com.android.tools.r8.ir.optimize.B0$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C3332x4) obj, (Set) obj2);
            }
        });
    }

    public final boolean a(AbstractC0735r0 abstractC0735r0) {
        if (abstractC0735r0 instanceof m5) {
            return abstractC0735r0.e().f.h() || abstractC0735r0.e().X0().f().g();
        }
        return this.a.W().a(abstractC0735r0.e());
    }

    /* JADX WARN: Code duplicated, block: B:65:0x01a5  */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final void c() {
        int iN2;
        AbstractC0735r0 abstractC0735r0D;
        AbstractC2706oV abstractC2706oVP;
        o5 o5VarS;
        MR mr = new MR();
        for (C3332x4 c3332x4 : this.c.d) {
            if (!c3332x4.H()) {
                mr.a(c3332x4.W(), c3332x4);
            }
        }
        C3579q c3579q = new C3579q(this.a, this.c, this.f);
        AbstractC2963s10 it = this.c.D().iterator();
        while (it.hasNext()) {
            C3332x4 c3332x4A = (C3332x4) it.next();
            if (!c3332x4A.J() || !c3332x4A.B().H()) {
                this.h = this.g.a(this.d, c3332x4A);
                this.g.a(c3332x4A, mr);
                x0IA x0ia = null;
                C3332x4 c3332x5 = null;
                do {
                    InterfaceC2641no interfaceC2641noA = c3332x4A.a(this.c);
                    while (interfaceC2641noA.hasNext()) {
                        AbstractC2412ko next = interfaceC2641noA.next();
                        if (next.o1()) {
                            if (next.p1()) {
                                Y2 y2T = next.t();
                                if (!y2T.J2().A() && !y2T.b().A()) {
                                    AbstractC3584s0 abstractC3584s0A = AbstractC3584s0.a(y2T.J2().m(), y2T.K2().m(), y2T.d());
                                    LinkedHashMap linkedHashMap = this.h.a;
                                    z0 z0Var = linkedHashMap != null ? (z0) linkedHashMap.get(abstractC3584s0A) : null;
                                    if (z0Var != null) {
                                        AbstractC3472z00 abstractC3472z00W = y2T.b().w();
                                        if (z0Var.a(this.a, abstractC3472z00W).a(abstractC3472z00W, this.a)) {
                                            z0Var.a(y2T, interfaceC2641noA);
                                        }
                                    } else {
                                        this.h.a(abstractC3584s0A, new x0(this, y2T.b()));
                                    }
                                }
                            } else {
                                if (!j && !next.r1()) {
                                    throw new AssertionError();
                                }
                                C1736c3 c1736c3V = next.v();
                                if (c1736c3V.K2().L()) {
                                    AbstractC0824Ac abstractC0824AcN = c1736c3V.K2().n();
                                    abstractC0824AcN.getClass();
                                    iN2 = abstractC0824AcN.E().N2();
                                } else {
                                    iN2 = -1;
                                }
                                EnumC2523mE enumC2523mED = c1736c3V.d();
                                if (iN2 < 0) {
                                    this.h.a(enumC2523mED);
                                } else {
                                    this.h.a(enumC2523mED, iN2);
                                }
                                this.h.a(AbstractC3584s0.a(c1736c3V.J2().m(), c1736c3V.K2().m(), enumC2523mED), new x0(this, c1736c3V.value()));
                            }
                        } else if (next.K1()) {
                            Y0 field = next.N().getField();
                            if (this.a.q()) {
                                AbstractC0677i3.a<?> aVarL = this.a.f().o().c(field).l();
                                if (aVarL != null) {
                                    abstractC0735r0D = aVarL.c();
                                } else {
                                    abstractC0735r0D = null;
                                }
                            } else if (field.B0() == this.b.t()) {
                                abstractC0735r0D = this.b.a().d(field);
                            } else {
                                abstractC0735r0D = null;
                            }
                            if (abstractC0735r0D == null || abstractC0735r0D.e().c1()) {
                                a();
                            } else if (next.Q1()) {
                                C1189Mn c1189MnT = next.T();
                                if (!c1189MnT.b().A()) {
                                    y0 y0Var = new y0(abstractC0735r0D.getReference(), c1189MnT.e().m());
                                    C3591v0 c3591v0 = this.h;
                                    LinkedHashMap linkedHashMap2 = c3591v0.e;
                                    z0 z0Var2 = linkedHashMap2 != null ? (z0) linkedHashMap2.get(y0Var) : null;
                                    if (z0Var2 == null) {
                                        LinkedHashMap linkedHashMap3 = c3591v0.b;
                                        z0Var2 = linkedHashMap3 != null ? (z0) linkedHashMap3.get(y0Var) : null;
                                    }
                                    if (z0Var2 != null) {
                                        a(c1189MnT, z0Var2, c3579q);
                                        z0Var2.a(c1189MnT, interfaceC2641noA);
                                    } else {
                                        this.h.a(y0Var, new x0(this, c1189MnT.value()));
                                        this.h.b();
                                        if (c1189MnT.b(this.a, this.b, C2332jo.a)) {
                                            this.h.a();
                                        } else {
                                            this.h.a(abstractC0735r0D.getReference());
                                        }
                                    }
                                } else if (c1189MnT.b(this.a, this.b, C2332jo.a)) {
                                    this.h.a();
                                } else {
                                    this.h.a(abstractC0735r0D.getReference());
                                }
                            } else if (next.S1()) {
                                a(next.V(), abstractC0735r0D);
                            } else if (next.w2()) {
                                QW qwF0 = next.F0();
                                C0767v2 c0767v2T = abstractC0735r0D.t();
                                C3591v0 c3591v1 = this.h;
                                c3591v1.c();
                                if (c3591v1.d == null) {
                                    c3591v1.d = new LinkedHashSet();
                                }
                                c3591v1.d.add(c0767v2T);
                                if (qwF0.b().A()) {
                                    a(qwF0);
                                    if (qwF0.b(this.a, this.b, C2332jo.a)) {
                                        this.h.a();
                                    } else {
                                        C3591v0 c3591v2 = this.h;
                                        Y0 reference = abstractC0735r0D.getReference();
                                        LinkedHashMap linkedHashMap4 = c3591v2.i;
                                        if (linkedHashMap4 != null) {
                                            linkedHashMap4.remove(reference);
                                        }
                                    }
                                } else {
                                    C3591v0 c3591v3 = this.h;
                                    Y0 reference2 = abstractC0735r0D.getReference();
                                    LinkedHashMap linkedHashMap5 = c3591v3.f;
                                    z0 z0Var3 = linkedHashMap5 != null ? (z0) linkedHashMap5.get(reference2) : null;
                                    if (z0Var3 == null) {
                                        LinkedHashMap linkedHashMap6 = c3591v3.c;
                                        z0Var3 = linkedHashMap6 != null ? (z0) linkedHashMap6.get(reference2) : null;
                                    }
                                    if (z0Var3 != null) {
                                        a(qwF0, z0Var3, c3579q);
                                        z0Var3.a(qwF0, interfaceC2641noA);
                                    } else {
                                        a(qwF0);
                                        if (qwF0.b(this.a, this.b, C2332jo.a)) {
                                            this.h.a();
                                        } else {
                                            C3591v0 c3591v4 = this.h;
                                            Y0 reference3 = abstractC0735r0D.getReference();
                                            LinkedHashMap linkedHashMap7 = c3591v4.i;
                                            if (linkedHashMap7 != null) {
                                                linkedHashMap7.remove(reference3);
                                            }
                                        }
                                        x0 x0Var = new x0(this, qwF0.value());
                                        if (a(abstractC0735r0D)) {
                                            this.h.a(abstractC0735r0D.getReference(), x0Var);
                                        } else {
                                            this.h.b(abstractC0735r0D.getReference(), x0Var);
                                        }
                                        if (this.a.f().i() && (abstractC2706oVP = abstractC0735r0D.e().X0().f().p()) != null) {
                                            a(qwF0.b(), abstractC2706oVP.t());
                                        }
                                        a(abstractC0735r0D.t());
                                        this.h.b();
                                    }
                                }
                            } else if (next.c()) {
                                a(next.G0(), abstractC0735r0D);
                            }
                        } else if (next.s1()) {
                            c3579q.a(next.w(), interfaceC2641noA);
                        } else if (next.O1()) {
                            C2179hn c2179hnR = next.R();
                            if (!j && c2179hnR.b().y()) {
                                throw new AssertionError();
                            }
                            a(c2179hnR);
                            if (c2179hnR.b(this.a, this.b)) {
                                this.h.a();
                            }
                            C0767v2 c0767v2K2 = c2179hnR.K2();
                            C3591v0 c3591v5 = this.h;
                            c3591v5.c();
                            if (c3591v5.d == null) {
                                c3591v5.d = new LinkedHashSet();
                            }
                            if (!c3591v5.d.add(c0767v2K2)) {
                                interfaceC2641noA.s();
                            } else if (this.e) {
                                this.h.g = c2179hnR;
                            }
                        } else if (next.j2()) {
                            if (next.m0().J2()) {
                                a();
                            }
                        } else if (next.W1()) {
                            a(next.Z());
                        } else if (next.d2()) {
                            C1721bt c1721btG0 = next.g0();
                            if (this.a.f().h() && (o5VarS = this.a.f().n().h(c1721btG0.S2()).s()) != null) {
                                C0767v2 c0767v2T2 = o5VarS.t();
                                C3591v0 c3591v6 = this.h;
                                c3591v6.c();
                                if (c3591v6.d == null) {
                                    c3591v6.d = new LinkedHashSet();
                                }
                                c3591v6.d.add(c0767v2T2);
                                a(o5VarS.t());
                            }
                            a();
                        } else if (next.Y1() || next.V1()) {
                            a();
                        } else if (next.p2()) {
                            DG dgT0 = next.t0();
                            C0767v2 c0767v2J2 = dgT0.J2();
                            C3591v0 c3591v7 = this.h;
                            c3591v7.c();
                            if (c3591v7.d == null) {
                                c3591v7.d = new LinkedHashSet();
                            }
                            c3591v7.d.add(c0767v2J2);
                            a(dgT0.J2());
                            if (dgT0.J2().a(this.b, this.a)) {
                                a();
                            }
                        } else {
                            boolean z = j;
                            if (!z && next.d(this.a, this.b)) {
                                throw new AssertionError();
                            }
                            if (next.b(this.a, this.b)) {
                                this.h.a();
                                this.h.b();
                            }
                            if (!z && !next.m1() && !next.p1() && !next.q1() && !next.r1() && !next.s1() && !next.v1() && !next.w1() && !next.x1() && !next.z1() && !next.A1() && !next.B1() && !next.C1() && !next.F1() && !next.I1() && !next.M1() && !next.N1() && !next.R1() && !next.a2() && !next.b2() && !next.m2() && !next.n2() && !next.o2() && !next.u2() && !next.A2() && !next.B2() && !next.C2() && !next.t2()) {
                                throw new AssertionError("Unexpected instruction of type " + next.getClass().getTypeName());
                            }
                        }
                    }
                    if (c3332x4A.I()) {
                        c3332x4A = c3332x4A.A();
                    } else {
                        c3332x5 = c3332x4A;
                        c3332x4A = null;
                    }
                } while (c3332x4A != null);
                if (!j && c3332x5 == null) {
                    throw new AssertionError();
                }
                this.g.a(c3332x5, this.h);
            }
        }
        b();
        c3579q.d().a();
        if (!j && !this.c.b(this.a)) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Boolean b(AbstractC0735r0 abstractC0735r0) {
        return Boolean.valueOf(!abstractC0735r0.e().f.h() || this.b.e().u1());
    }

    public static boolean a(C0702m c0702m, C2409kl c2409kl) {
        return c0702m.Y().V && (c2409kl.t().d() || c2409kl.t().l() || c2409kl.t().m());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final C3332x4 c3332x4, Set set) {
        if (!j && !set.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.ir.optimize.B0$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return B0.a(c3332x4, (AbstractC2412ko) obj);
            }
        })) {
            throw new AssertionError();
        }
        InterfaceC2641no interfaceC2641noA = c3332x4.a(this.c);
        while (interfaceC2641noA.hasNext()) {
            AbstractC2412ko next = interfaceC2641noA.next();
            if (!j && next.g2()) {
                throw new AssertionError();
            }
            if (set.contains(next)) {
                interfaceC2641noA.s();
                set.remove(next);
                if (set.isEmpty()) {
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C3332x4 c3332x4, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.h() == c3332x4;
    }

    private void a(final C1166Ls c1166Ls) {
        if (!this.a.f().i()) {
            a();
            return;
        }
        final C0702m<C3733i> c0702mI0 = this.a.i0();
        AbstractC0748t0 abstractC0748t0H = c1166Ls.h(this.a, this.b);
        if (abstractC0748t0H != null && abstractC0748t0H.e().C1()) {
            AbstractC1380Tn abstractC1380TnA = abstractC0748t0H.e().i1().a(c1166Ls);
            if (abstractC1380TnA.e()) {
                a();
            }
            abstractC1380TnA.b().a(this.a, new BiConsumer() { // from class: com.android.tools.r8.ir.optimize.B0$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a(c0702mI0, c1166Ls, (com.android.tools.r8.graph.T0) obj, (InterfaceC0989Fn) obj2);
                }
            });
            return;
        }
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void a(C0702m c0702m, C1166Ls c1166Ls, com.android.tools.r8.graph.T0 t0, InterfaceC0989Fn interfaceC0989Fn) {
        if (((C3733i) c0702m.f()).b(c0702m, t0.getReference())) {
            x0IA x0ia = null;
            Object[] objArr = 0;
            Object[] objArr2 = 0;
            Object[] objArr3 = 0;
            if (interfaceC0989Fn.h()) {
                S10 s10C = c1166Ls.c(interfaceC0989Fn.f().b());
                y0 y0Var = new y0(t0.getReference(), c1166Ls.U2().m());
                if (t0.f.h()) {
                    C3591v0 c3591v0 = this.h;
                    x0 x0Var = new x0(this, s10C);
                    c3591v0.c();
                    if (c3591v0.b == null) {
                        c3591v0.b = new LinkedHashMap();
                    }
                    c3591v0.b.put(y0Var, x0Var);
                    return;
                }
                this.h.a(y0Var, new x0(this, s10C));
                return;
            }
            if (interfaceC0989Fn.g()) {
                AbstractC3292wV abstractC3292wVJ = interfaceC0989Fn.j();
                if (abstractC3292wVJ.b(c0702m, this.b)) {
                    y0 y0Var2 = new y0(t0.getReference(), c1166Ls.U2().m());
                    if (t0.f.h()) {
                        C3591v0 c3591v1 = this.h;
                        A0 a0 = new A0(this, abstractC3292wVJ);
                        c3591v1.c();
                        if (c3591v1.b == null) {
                            c3591v1.b = new LinkedHashMap();
                        }
                        c3591v1.b.put(y0Var2, a0);
                        return;
                    }
                    this.h.a(y0Var2, new A0(this, abstractC3292wVJ));
                    return;
                }
                return;
            }
            if (!j && !interfaceC0989Fn.a()) {
                throw new AssertionError();
            }
        }
    }

    private void a(C0767v2 c0767v2) {
        C2179hn c2179hn = this.h.g;
        if (c2179hn == null || c2179hn.K2() != c0767v2) {
            return;
        }
        ((Set) this.i.computeIfAbsent(c2179hn.h(), Z1.a(new NG$$ExternalSyntheticLambda0()))).add(c2179hn);
    }

    private static void a(final InterfaceC1102Jh interfaceC1102Jh, z0 z0Var, C3579q c3579q) {
        x0 x0VarA = z0Var.a();
        if (x0VarA == null || !x0VarA.a.b(new Predicate() { // from class: com.android.tools.r8.ir.optimize.B0$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return B0.a(interfaceC1102Jh, (AbstractC2412ko) obj);
            }
        })) {
            c3579q.a(interfaceC1102Jh.b());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(InterfaceC1102Jh interfaceC1102Jh, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.J1() && abstractC2412ko.M().getField().getType() == interfaceC1102Jh.getField().getType();
    }

    private void a(C1793co c1793co, AbstractC0735r0 abstractC0735r0) {
        this.h.b(abstractC0735r0.getReference());
        if (c1793co.b(this.a, this.b, C2332jo.a)) {
            this.h.a();
        }
        y0 y0Var = new y0(abstractC0735r0.getReference(), c1793co.e().m());
        x0 x0Var = new x0(this, c1793co.value());
        if (a(abstractC0735r0)) {
            boolean z = j;
            if (!z && abstractC0735r0.e().f.h() && !this.b.e().C1()) {
                C2750p20 c2750p20G0 = this.a.g0();
                if (!z && c2750p20G0 == null) {
                    throw new AssertionError();
                }
                if (!z && !c2750p20G0.f(this.b.t()) && !this.a.M().e(this.b.t())) {
                    throw new AssertionError();
                }
                if (!z && !this.a.a().b(this.a.E().a((AbstractC1476Wj) null, this.b.getReference()))) {
                    throw new AssertionError();
                }
                if (!z && !this.b.e().i1().g()) {
                    throw new AssertionError();
                }
            }
            C3591v0 c3591v0 = this.h;
            c3591v0.c();
            if (c3591v0.b == null) {
                c3591v0.b = new LinkedHashMap();
            }
            c3591v0.b.put(y0Var, x0Var);
        } else {
            this.h.a(y0Var, x0Var);
            if (this.e) {
                C3591v0 c3591v1 = this.h;
                c3591v1.c();
                if (c3591v1.h == null) {
                    c3591v1.h = new LinkedHashMap();
                }
                C1793co c1793co2 = (C1793co) c3591v1.h.put(y0Var, c1793co);
                if (c1793co2 != null) {
                    ((Set) this.i.computeIfAbsent(c1793co2.h(), Z1.a(new NG$$ExternalSyntheticLambda0()))).add(c1793co2);
                }
            }
        }
        this.h.g = null;
    }

    private void a(RW rw, final AbstractC0735r0 abstractC0735r0) {
        C0767v2 c0767v2T = abstractC0735r0.t();
        C3591v0 c3591v0 = this.h;
        c3591v0.c();
        if (c3591v0.d == null) {
            c3591v0.d = new LinkedHashSet();
        }
        c3591v0.d.add(c0767v2T);
        a(rw);
        if (rw.b(this.a, this.b, C2332jo.a)) {
            this.h.a();
        }
        x0 x0Var = new x0(this, rw.value());
        if (a(abstractC0735r0)) {
            if (!j && !this.a.a(new Supplier() { // from class: com.android.tools.r8.ir.optimize.B0$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.b(abstractC0735r0);
                }
            })) {
                throw new AssertionError();
            }
            this.h.a(abstractC0735r0.getReference(), x0Var);
        } else {
            this.h.b(abstractC0735r0.getReference(), x0Var);
            if (this.e) {
                C3591v0 c3591v1 = this.h;
                Y0 reference = abstractC0735r0.getReference();
                c3591v1.c();
                if (c3591v1.i == null) {
                    c3591v1.i = new LinkedHashMap();
                }
                RW rw2 = (RW) c3591v1.i.put(reference, rw);
                if (rw2 != null) {
                    ((Set) this.i.computeIfAbsent(rw2.h(), Z1.a(new NG$$ExternalSyntheticLambda0()))).add(rw2);
                }
            }
        }
        a(abstractC0735r0.t());
        this.h.g = null;
    }

    private void a(final S10 s10, AbstractC3121uK abstractC3121uK) {
        final C0702m<C3733i> c0702mI0 = this.a.i0();
        abstractC3121uK.a(new BiConsumer() { // from class: com.android.tools.r8.ir.optimize.B0$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c0702mI0, s10, (Y0) obj, (B1) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0702m c0702m, S10 s10, Y0 y0, B1 b1) {
        if (((C3733i) c0702m.f()).b(c0702m, y0) && b1.g()) {
            AbstractC3292wV abstractC3292wVJ = b1.j();
            if (abstractC3292wVJ.b(c0702m, this.b)) {
                C3591v0 c3591v0 = this.h;
                y0 y0Var = new y0(y0, s10);
                A0 a0 = new A0(this, abstractC3292wVJ);
                c3591v0.c();
                if (c3591v0.b == null) {
                    c3591v0.b = new LinkedHashMap();
                }
                c3591v0.b.put(y0Var, a0);
            }
        }
    }

    private void a() {
        C3591v0 c3591v0 = this.h;
        c3591v0.a = null;
        c3591v0.e = null;
        c3591v0.f = null;
        c3591v0.a();
        this.h.g = null;
    }

    private void a(AbstractC2412ko abstractC2412ko) {
        if (!j) {
            abstractC2412ko.getClass();
            if (!(abstractC2412ko instanceof C2179hn) && !abstractC2412ko.v2()) {
                throw new AssertionError();
            }
        }
        if (abstractC2412ko.c()) {
            if (abstractC2412ko.d(this.a, this.b)) {
                C3591v0 c3591v0 = this.h;
                c3591v0.f = null;
                c3591v0.a();
                return;
            } else {
                C3591v0 c3591v1 = this.h;
                Y0 field = abstractC2412ko.G0().getField();
                LinkedHashMap linkedHashMap = c3591v1.f;
                if (linkedHashMap != null) {
                    linkedHashMap.remove(field);
                    return;
                }
                return;
            }
        }
        if (!(abstractC2412ko instanceof C2179hn) && !(abstractC2412ko instanceof QW)) {
            if (abstractC2412ko instanceof C1189Mn) {
                throw new C3405y10();
            }
        } else if (abstractC2412ko.d(this.a, this.b)) {
            C3591v0 c3591v2 = this.h;
            c3591v2.f = null;
            c3591v2.a();
        }
    }
}
