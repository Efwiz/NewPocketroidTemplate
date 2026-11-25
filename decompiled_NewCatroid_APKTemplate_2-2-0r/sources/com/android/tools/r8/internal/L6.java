package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.AbstractC3582r0;
import com.android.tools.r8.ir.optimize.C3580q0;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.InterfaceC3885d3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class L6 {
    static final /* synthetic */ boolean s = true;
    public final C0702m a;
    private final com.android.tools.r8.graph.o5 b;
    private final C2409kl c;
    private HashMap d;
    private HashSet e;
    private ArrayList f;
    private C2971s8 g;
    private AbstractC1915eM h = AbstractC1915eM.u();
    private final C1303Qq i = new C1303Qq();
    private C1303Qq j = null;
    private boolean k = false;
    private C3332x4 l = null;
    private final ArrayList m = new ArrayList();
    private final C1303Qq n = new C1303Qq();
    private HashMap o;
    private ArrayList p;
    private HashMap q;
    private final C1973f6 r;

    public L6(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2409kl c2409kl, C2133h6 c2133h6) {
        this.a = c0702m;
        this.b = o5Var;
        this.c = c2409kl;
        this.r = C2049g6.a(c2133h6);
    }

    private boolean b() {
        if (!this.k) {
            return false;
        }
        boolean z = !com.android.tools.r8.graph.X.a(this.i, this.j);
        this.k = z;
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        C0978Fc c0978FcE;
        C3044tC c3044tCK0;
        for (C3332x4 c3332x4 : this.c.d) {
            InterfaceC2641no interfaceC2641noA = c3332x4.a(this.c);
            while (4 <= c3332x4.p().size() - interfaceC2641noA.nextIndex()) {
                AbstractC2412ko next = interfaceC2641noA.next();
                next.getClass();
                boolean z = next instanceof C3044tC;
                if (z || next.B1()) {
                    if (z) {
                        c3044tCK0 = next.k0();
                        c0978FcE = interfaceC2641noA.next().E();
                    } else {
                        C3044tC c3044tCK1 = interfaceC2641noA.next().k0();
                        c0978FcE = next.E();
                        c3044tCK0 = c3044tCK1;
                    }
                    H1 h1N = interfaceC2641noA.next().n();
                    SW swH0 = interfaceC2641noA.next().H0();
                    interfaceC2641noA.previous();
                    interfaceC2641noA.previous();
                    interfaceC2641noA.previous();
                    interfaceC2641noA.previous();
                    if (c3044tCK0 == null || c0978FcE == null || h1N == null || swH0 == null || c0978FcE.a() != AbstractC3472z00.k()) {
                        interfaceC2641noA.next();
                    } else {
                        int iN2 = c0978FcE.N2();
                        if (iN2 < -128 || 127 < iN2) {
                            interfaceC2641noA.next();
                        } else {
                            if (this.g.a(c3044tCK0.J2()) != this.g.a(swH0.b())) {
                                interfaceC2641noA.next();
                            } else {
                                AbstractC1915eM abstractC1915eMY0 = h1N.Y0();
                                if (abstractC1915eMY0 == c3044tCK0.Y0() && abstractC1915eMY0 == c0978FcE.Y0() && abstractC1915eMY0 == swH0.Y0()) {
                                    interfaceC2641noA.v();
                                    interfaceC2641noA.next();
                                    interfaceC2641noA.v();
                                    interfaceC2641noA.next();
                                    interfaceC2641noA.v();
                                    interfaceC2641noA.next();
                                    C1427Um c1427Um = new C1427Um(swH0.b(), (S10) c3044tCK0.h1().get(0), iN2);
                                    c1427Um.b(abstractC1915eMY0);
                                    c1427Um.a(c3332x4);
                                    interfaceC2641noA.set(c1427Um);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        for (C3332x4 c3332x4 : this.c.d) {
            InterfaceC2641no interfaceC2641noA = c3332x4.a(this.c);
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko next = interfaceC2641noA.next();
                next.getClass();
                if (next instanceof LH) {
                    S10 s10 = (S10) next.h1().get(0);
                    interfaceC2641noA.previous();
                    S10 s10A = this.c.a(s10.w());
                    C0978Fc c0978Fc = new C0978Fc(s10A, -1L);
                    c0978Fc.a(c3332x4);
                    c0978Fc.b(next.Y0());
                    interfaceC2641noA.add(c0978Fc);
                    interfaceC2641noA.next();
                    interfaceC2641noA.d(new P30(next.v0().h, next.b(), s10, s10A));
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:184:0x047a  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v31 */
    /* JADX WARN: Type inference failed for: r14v32, types: [int] */
    /* JADX WARN: Type inference failed for: r14v34 */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6 */
    public final C0753u a(com.android.tools.r8.ir.optimize.M m, com.android.tools.r8.utils.y3 y3Var) {
        Set<AbstractC2412ko> setC;
        B4 b4;
        AbstractC1915eM abstractC1915eM;
        boolean z;
        InterfaceC3054tM interfaceC3054tMA;
        InterfaceC1348Si interfaceC1348SiA;
        AbstractC1579a10 abstractC1579a10A;
        boolean z2;
        final C2409kl c2409kl = this.c;
        Objects.requireNonNull(c2409kl);
        y3Var.a("Trace blocks", new InterfaceC3885d3() { // from class: com.android.tools.r8.internal.L6$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3885d3
            public final void a() {
                c2409kl.E();
            }
        });
        y3Var.a("Compute Initializers", new InterfaceC3885d3() { // from class: com.android.tools.r8.internal.L6$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.InterfaceC3885d3
            public final void a() {
                this.f$0.a();
            }
        });
        y3Var.a("Compute verification types");
        Q00 q00 = new Q00(this.a, this.c);
        q00.a();
        y3Var.c();
        if (!s) {
            m.b(this.c);
        }
        y3Var.a("Rewrite nots", new InterfaceC3885d3() { // from class: com.android.tools.r8.internal.L6$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.InterfaceC3885d3
            public final void a() {
                this.f$0.d();
            }
        });
        y3Var.a("Insert loads and stores");
        C3340xC c3340xC = new C3340xC(this.a, this.c, q00);
        c3340xC.a();
        y3Var.c();
        boolean z3 = true;
        if (this.a.Y().z0) {
            y3Var.a("Load store optimizations (BasicBlockMunching)");
            AbstractC3582r0.a(this.c);
            boolean z4 = false;
            while (!z4) {
                C4.a(this.c, this.a.Y());
                z4 = !AbstractC3582r0.a(this.c);
            }
            y3Var.c();
        }
        if (!s && !this.c.b(this.a)) {
            throw new AssertionError();
        }
        y3Var.a("Insert uninitialized local reads");
        if (this.b.getReference().b(this.a)) {
            setC = OU.c();
            for (C3332x4 c3332x4 : this.c.d) {
                if (c3332x4.m().B2() && !c3332x4.D()) {
                    LinkedList<AbstractC2412ko> linkedListP = c3332x4.p();
                    AbstractC2412ko abstractC2412koRemoveLast = linkedListP.removeLast();
                    if (!s && !abstractC2412koRemoveLast.B2()) {
                        throw new AssertionError();
                    }
                    C1966f10 c1966f10 = new C1966f10(this.c.o());
                    c1966f10.b(abstractC2412koRemoveLast.Y0());
                    setC.add(c1966f10);
                    c1966f10.a(c3332x4);
                    linkedListP.addLast(c1966f10);
                    linkedListP.addLast(abstractC2412koRemoveLast);
                }
            }
        } else {
            setC = Collections.emptySet();
        }
        y3Var.c();
        y3Var.a("Register allocation");
        C2971s8 c2971s8 = new C2971s8(this.a, this.c, q00);
        this.g = c2971s8;
        c2971s8.a();
        y3Var.c();
        y3Var.a("Remove uninitialized local reads");
        if (!setC.isEmpty()) {
            for (AbstractC2412ko abstractC2412ko : setC) {
                abstractC2412ko.h().g(abstractC2412ko);
            }
        }
        y3Var.c();
        y3Var.a("Insert phi moves");
        c3340xC.a(this.g);
        y3Var.c();
        y3Var.a("BasicBlock peephole optimizations");
        if (this.c.k().c()) {
            for (int i = 0; i < 2; i++) {
                com.android.tools.r8.ir.optimize.F.a((C0702m<?>) this.a, this.c);
                C3580q0.a(this.c, this.g);
                C3580q0.a(this.c, this.g, 30);
            }
        }
        y3Var.c();
        y3Var.a("Rewrite Iinc patterns", new InterfaceC3885d3() { // from class: com.android.tools.r8.internal.L6$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.InterfaceC3885d3
            public final void a() {
                this.f$0.c();
            }
        });
        com.android.tools.r8.ir.optimize.F.a((C0702m<?>) this.a, this.c);
        y3Var.a("Remove redundant debug positions");
        C2322jf.a(this.c);
        y3Var.c();
        y3Var.a("Build CF Code");
        ArrayList arrayList = new ArrayList();
        this.d = new HashMap(this.c.d.size());
        this.e = new HashSet(this.c.d.size());
        this.q = new HashMap(this.o.size());
        this.f = new ArrayList();
        B4 b4S = this.c.s();
        C3332x4 next = b4S.next();
        C3038t6 c3038t6 = C3038t6.e;
        boolean z5 = true;
        ?? r9 = 0;
        M7 m7A = null;
        int iMax = 0;
        while (true) {
            C3038t6 c3038t6N = next.n();
            if (!c3038t6.equals(c3038t6N)) {
                if (!c3038t6.isEmpty()) {
                    M7 m7A2 = a(next);
                    int i2 = W8.f;
                    List listB = c3038t6.b();
                    ArrayList arrayList2 = new ArrayList(c3038t6.a().size());
                    Iterator it = c3038t6.a().iterator();
                    while (it.hasNext()) {
                        arrayList2.add(a((C3332x4) it.next()));
                    }
                    arrayList.add(new W8(m7A, m7A2, listB, arrayList2));
                    if (!this.e.contains(m7A2)) {
                        this.e.add(m7A2);
                        this.f.add(m7A2);
                    }
                }
                if (!c3038t6N.isEmpty()) {
                    m7A = a(next);
                    if (!this.e.contains(m7A)) {
                        this.e.add(m7A);
                        this.f.add(m7A);
                    }
                }
                c3038t6 = c3038t6N;
            }
            C3332x4 next2 = b4S.hasNext() ? b4S.next() : null;
            if (next.y().size() > r9) {
                this.l = next;
                M7 m7A3 = a(next);
                if (!this.e.contains(m7A3)) {
                    this.e.add(m7A3);
                    this.f.add(m7A3);
                }
            }
            AbstractC1954eu abstractC1954euM = next.m();
            boolean z6 = ((abstractC1954euM.M1() && abstractC1954euM.P().K2() == next2) || (abstractC1954euM.N1() && abstractC1954euM.J2() == next2)) ? z3 : false;
            InterfaceC0992Fq interfaceC0992FqQ = next.q();
            if (interfaceC0992FqQ != null) {
                C1303Qq c1303Qq = new C1303Qq(interfaceC0992FqQ.size());
                c1303Qq.putAll(interfaceC0992FqQ);
                this.j = c1303Qq;
                this.k = z3;
            } else if (!s && this.j != null) {
                throw new AssertionError();
            }
            Iterator it2 = this.g.a(next).b.iterator();
            int iE0 = 0;
            while (it2.hasNext()) {
                C0767v2 c0767v2A = ((P00) it2.next()).a();
                iE0 += (c0767v2A.P0() || c0767v2A.T0()) ? 2 : z3;
            }
            if (!K6.a && iE0 > iMax) {
                throw new AssertionError();
            }
            if (this.l != null) {
                Iterator<AbstractC2412ko> it3 = next.p().iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        z = false;
                        break;
                    }
                    AbstractC2412ko next3 = it3.next();
                    if (!(next3.m1() || ((z2 = next3 instanceof AF)) || (next3 instanceof C1316Rd) || z2 || (next3.M1() && next3.P().K2() == next2))) {
                        z = true;
                        break;
                    }
                }
                boolean z7 = s;
                if (!z7 && !z && next2 == null) {
                    throw new AssertionError();
                }
                if (z) {
                    C3332x4 c3332x5 = this.l;
                    List<P00> list = this.g.a(c3332x5).b;
                    C2284j7.a aVarT = C2284j7.T();
                    AbstractC2412ko abstractC2412koK = c3332x5.k();
                    abstractC2412koK.getClass();
                    if (!(abstractC2412koK instanceof AF)) {
                        aVarT.a(list.size());
                        for (P00 p00 : list) {
                            if (p00 instanceof M00) {
                                interfaceC3054tMA = InterfaceC1348Si.a(p00.a());
                            } else {
                                AbstractC1579a10 abstractC1579a10A2 = a(c3332x5, p00);
                                interfaceC3054tMA = abstractC1579a10A2 != null ? abstractC1579a10A2 : InterfaceC1348Si.a(p00.a());
                            }
                            aVarT.a(interfaceC3054tMA);
                        }
                    } else {
                        if (!z7 && !list.isEmpty()) {
                            throw new AssertionError();
                        }
                        P00 p00K0 = ((C2617nW) c3332x5.k().b()).k0();
                        InterfaceC3054tM interfaceC3054tMA2 = ((p00K0 instanceof M00) || (abstractC1579a10A = a(c3332x5, p00K0)) == null) ? InterfaceC1348Si.a(p00K0.a()) : abstractC1579a10A;
                        aVarT.a(interfaceC3054tMA2);
                    }
                    InterfaceC1912eK it4 = this.g.a(c3332x5).a.c().iterator();
                    while (it4.hasNext()) {
                        InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it4.next();
                        int iA = interfaceC0934Dq.a();
                        P00 p01 = (P00) interfaceC0934Dq.getValue();
                        InterfaceC1912eK interfaceC1912eK = it4;
                        if (p01 instanceof M00) {
                            interfaceC1348SiA = InterfaceC1348Si.a(p01.a());
                        } else {
                            interfaceC1348SiA = a(c3332x5, p01);
                            if (interfaceC1348SiA == null) {
                                interfaceC1348SiA = InterfaceC1348Si.a(p01.a());
                            }
                        }
                        aVarT.a(iA, interfaceC1348SiA);
                        it4 = interfaceC1912eK;
                    }
                    C2284j7 c2284j7A = aVarT.a();
                    if (b()) {
                        a(B8.a(this.f));
                    }
                    this.f.add(c2284j7A);
                    this.l = null;
                }
            }
            for (AbstractC2412ko abstractC2412ko2 : next.p()) {
                if (z6 && abstractC2412ko2.M1()) {
                    if (!s && next.m() != abstractC2412ko2) {
                        throw new AssertionError();
                    }
                    break;
                    break;
                }
                for (int size = ((ArrayList) abstractC2412ko2.h1()).size() - 1; size >= 0; size--) {
                    if (((S10) ((ArrayList) abstractC2412ko2.h1()).get(size)).T()) {
                        S10 s10 = (S10) ((ArrayList) abstractC2412ko2.h1()).get(size);
                        if (!K6.a && !s10.T()) {
                            throw new AssertionError();
                        }
                        iE0 -= s10.e0();
                    }
                }
                if (abstractC2412ko2.b() != null) {
                    S10 s10B = abstractC2412ko2.b();
                    if (s10B instanceof C2617nW) {
                        if (!K6.a && !s10B.T()) {
                            throw new AssertionError();
                        }
                        iE0 += s10B.e0();
                        iMax = Math.max(iMax, iE0);
                    }
                    if (s10B instanceof C2708oW) {
                        for (C2617nW c2617nW : ((C2708oW) s10B).j0()) {
                            if (!K6.a) {
                                c2617nW.getClass();
                            }
                            iE0 = c2617nW.e0() + iE0;
                            iMax = Math.max(iMax, iE0);
                        }
                    }
                }
                if (abstractC2412ko2 instanceof C1316Rd) {
                    if (abstractC2412ko2.H().a(this.j)) {
                        this.k = true;
                    }
                    b4 = b4S;
                } else {
                    if (abstractC2412ko2.p2()) {
                        this.q.put(abstractC2412ko2.t0(), B8.a(this.f));
                    }
                    AbstractC1915eM abstractC1915eMY0 = abstractC2412ko2.Y0();
                    boolean zB = b();
                    boolean z8 = abstractC1915eMY0.s() && abstractC1915eMY0 != (abstractC1915eM = this.h) && !(abstractC1915eM.o() && (abstractC1915eMY0 instanceof AbstractC1915eM.c) && !abstractC1915eMY0.k()) && (this.a.Y().W0 || abstractC2412ko2.g());
                    if (zB || z8) {
                        b4 = b4S;
                        M7 m7A4 = B8.a(this.f);
                        if (zB) {
                            a(m7A4);
                        }
                        if (z8) {
                            this.f.add(new C2051g8(m7A4, abstractC1915eMY0));
                            this.h = abstractC1915eMY0;
                        }
                    } else {
                        b4 = b4S;
                    }
                    abstractC2412ko2.a(this);
                }
                b4S = b4;
            }
            B4 b5 = b4S;
            if (!s && next.m().u2()) {
                if (!(iE0 == 0)) {
                    throw new AssertionError();
                }
            }
            if (z5) {
                z5 = false;
            }
            if (next2 == null) {
                if (!this.n.isEmpty()) {
                    M7 m7A5 = B8.a(this.f);
                    InterfaceC1912eK it5 = this.n.values().iterator();
                    while (it5.hasNext()) {
                        C0753u.a aVar = (C0753u.a) it5.next();
                        aVar.a(m7A5);
                        this.m.add(aVar);
                    }
                }
                Position positionH0 = Position.UNKNOWN;
                if (this.b.e().a1().z0()) {
                    positionH0 = this.b.e().a1().K().H0();
                }
                C0753u c0753u = new C0753u(this.b.t(), iMax, this.g.e(), this.f, arrayList, this.m, positionH0, this.r.a());
                y3Var.c();
                if (!s) {
                    C0702m c0702m = this.a;
                    if (!c0702m.Y().n1.D) {
                        for (B7 b7 : c0753u.I0()) {
                            if (b7 instanceof I7) {
                                I7 i7 = (I7) b7;
                                if (i7.T().e.O0()) {
                                    AbstractC0727q0 abstractC0727q0C = c0702m.c(i7.T().e);
                                    if (!s && abstractC0727q0C != null && abstractC0727q0C.isInterface() != i7.V()) {
                                        throw new AssertionError();
                                    }
                                } else {
                                    continue;
                                }
                            }
                        }
                    }
                }
                if (!s) {
                    com.android.tools.r8.graph.o5 o5Var = this.b;
                    C0702m c0702m2 = this.a;
                    int iA2 = c0753u.a(c0702m2, o5Var, c0702m2.E());
                    if (!(iA2 == 4 || iA2 == 2)) {
                        throw new AssertionError();
                    }
                }
                return c0753u;
            }
            next = next2;
            b4S = b5;
            z3 = true;
            r9 = z6;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ M7 b(C3332x4 c3332x4) {
        return new M7();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        boolean z = s;
        if (!z && this.o != null) {
            throw new AssertionError();
        }
        if (!z && this.p != null) {
            throw new AssertionError();
        }
        this.o = new HashMap();
        boolean zB = this.b.getReference().b(this.a);
        Iterator<C3332x4> it = this.c.d.iterator();
        while (it.hasNext()) {
            for (AbstractC2412ko abstractC2412ko : it.next().p()) {
                if (abstractC2412ko.p2()) {
                    HashMap map = this.o;
                    DG dgT0 = abstractC2412ko.t0();
                    S10 s10B = abstractC2412ko.b();
                    ArrayList arrayList = new ArrayList();
                    for (AbstractC2412ko abstractC2412ko2 : s10B.h0()) {
                        if ((abstractC2412ko2 instanceof C1166Ls) && ((ArrayList) abstractC2412ko2.h1()).get(0) == s10B && abstractC2412ko2.Z().S2().f == this.a.a().Y0) {
                            arrayList.add(abstractC2412ko2.Z());
                        }
                    }
                    map.put(dgT0, arrayList);
                } else if (abstractC2412ko.m1() && zB && abstractC2412ko.b().Q()) {
                    S10 s10B2 = abstractC2412ko.b();
                    ArrayList arrayList2 = new ArrayList();
                    for (AbstractC2412ko abstractC2412ko3 : s10B2.h0()) {
                        if ((abstractC2412ko3 instanceof C1166Ls) && ((ArrayList) abstractC2412ko3.h1()).get(0) == s10B2 && abstractC2412ko3.Z().S2().f == this.a.a().Y0) {
                            arrayList2.add(abstractC2412ko3.Z());
                        }
                    }
                    this.p = arrayList2;
                }
            }
        }
        if (!s && zB && this.p == null) {
            throw new AssertionError();
        }
    }

    private void a(M7 m7) {
        C1303Qq c1303Qq = this.i;
        C1303Qq c1303Qq2 = this.j;
        int i = com.android.tools.r8.graph.X.d;
        C2807pq c2807pq = new C2807pq();
        InterfaceC1912eK it = c1303Qq.c().iterator();
        while (it.hasNext()) {
            InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it.next();
            int iA = interfaceC0934Dq.a();
            com.android.tools.r8.graph.X x = (com.android.tools.r8.graph.X) interfaceC0934Dq.getValue();
            if (c1303Qq2.get(iA) != x) {
                c2807pq.a(iA, x);
            }
        }
        C1303Qq c1303Qq3 = this.i;
        C1303Qq c1303Qq4 = this.j;
        C2807pq c2807pq2 = new C2807pq();
        InterfaceC1912eK it2 = c1303Qq4.c().iterator();
        while (it2.hasNext()) {
            InterfaceC0934Dq interfaceC0934Dq2 = (InterfaceC0934Dq) it2.next();
            int iA2 = interfaceC0934Dq2.a();
            com.android.tools.r8.graph.X x2 = (com.android.tools.r8.graph.X) interfaceC0934Dq2.getValue();
            if (c1303Qq3.get(iA2) != x2) {
                c2807pq2.a(iA2, x2);
            }
        }
        if (!s && c2807pq.isEmpty() && c2807pq2.isEmpty()) {
            throw new AssertionError();
        }
        YJ it3 = c2807pq.c().iterator();
        while (it3.hasNext()) {
            InterfaceC0934Dq interfaceC0934Dq3 = (InterfaceC0934Dq) it3.next();
            int iA3 = interfaceC0934Dq3.a();
            C0753u.a aVar = (C0753u.a) this.n.remove(iA3);
            aVar.a(m7);
            this.m.add(aVar);
            com.android.tools.r8.graph.X x3 = (com.android.tools.r8.graph.X) this.i.remove(iA3);
            if (!s && x3 != interfaceC0934Dq3.getValue()) {
                throw new AssertionError();
            }
        }
        if (!c2807pq2.isEmpty()) {
            YJ it4 = c2807pq2.c().iterator();
            while (it4.hasNext()) {
                InterfaceC0934Dq interfaceC0934Dq4 = (InterfaceC0934Dq) it4.next();
                int iA4 = interfaceC0934Dq4.a();
                boolean z = s;
                if (!z && this.i.a(iA4)) {
                    throw new AssertionError();
                }
                if (!z && this.n.a(iA4)) {
                    throw new AssertionError();
                }
                this.n.a(iA4, new C0753u.a(iA4, (com.android.tools.r8.graph.X) interfaceC0934Dq4.getValue(), m7));
                this.i.a(iA4, (com.android.tools.r8.graph.X) interfaceC0934Dq4.getValue());
            }
        }
        this.k = false;
    }

    private AbstractC1579a10 a(C3332x4 c3332x4, P00 p00) {
        AbstractC2412ko abstractC2412ko;
        AbstractC1579a10 abstractC1579a10G;
        if (p00 instanceof N00) {
            N00 n00 = (N00) p00;
            abstractC2412ko = n00.a;
            abstractC1579a10G = InterfaceC1348Si.a((M7) this.q.get(abstractC2412ko), n00.a());
        } else if (p00 instanceof O00) {
            abstractC2412ko = ((O00) p00).b;
            abstractC1579a10G = InterfaceC1348Si.g();
        } else {
            throw new C3405y10("Unexpected type info: " + p00);
        }
        C3332x4 c3332x4H = abstractC2412ko.h();
        if (!s && c3332x4H == c3332x4) {
            throw new AssertionError();
        }
        HashSet hashSet = new HashSet();
        Iterator it = (abstractC2412ko.m1() ? this.p : (List) this.o.get(abstractC2412ko.t0())).iterator();
        while (it.hasNext()) {
            C3332x4 c3332x4H2 = ((C1166Ls) it.next()).h();
            if (c3332x4H2 == c3332x4) {
                return abstractC1579a10G;
            }
            hashSet.add(c3332x4H2);
        }
        com.android.tools.r8.utils.O3 o3C = com.android.tools.r8.utils.O3.c((Iterable) c3332x4.y());
        while (o3C.c()) {
            C3332x4 c3332x5 = (C3332x4) o3C.f();
            if (hashSet.contains(c3332x5)) {
                return null;
            }
            if (c3332x5 != c3332x4H) {
                o3C.a((Iterable) c3332x5.y());
            }
        }
        return abstractC1579a10G;
    }

    public final M7 a(C3332x4 c3332x4) {
        return (M7) this.d.computeIfAbsent(c3332x4, new Function() { // from class: com.android.tools.r8.internal.L6$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return L6.b((C3332x4) obj);
            }
        });
    }

    public final int a(S10 s10) {
        return this.g.a(s10);
    }

    public final void a(B7 b7, AbstractC2412ko abstractC2412ko) {
        this.r.a(abstractC2412ko, b7);
        this.f.add(b7);
    }

    public final void a(B7... b7Arr) {
        Collections.addAll(this.f, b7Arr);
    }

    public final void a(AbstractC2412ko abstractC2412ko) {
        if (s) {
            return;
        }
        this.r.a(abstractC2412ko);
    }
}
