package com.android.tools.r8.graph;

import com.android.tools.r8.AbstractC0403b;
import com.android.tools.r8.dex.code.InterfaceC0419c;
import com.android.tools.r8.internal.A8;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1899eF;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC2331jn;
import com.android.tools.r8.internal.AbstractC2619nY;
import com.android.tools.r8.internal.AbstractC2964s2;
import com.android.tools.r8.internal.B7;
import com.android.tools.r8.internal.C0994Fs;
import com.android.tools.r8.internal.C1890e6;
import com.android.tools.r8.internal.C2049g6;
import com.android.tools.r8.internal.C2051g8;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2145hI;
import com.android.tools.r8.internal.C2284j7;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2674o7;
import com.android.tools.r8.internal.C2757p8;
import com.android.tools.r8.internal.C3017sq;
import com.android.tools.r8.internal.C3263wA;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.CE;
import com.android.tools.r8.internal.DE;
import com.android.tools.r8.internal.InterfaceC0934Dq;
import com.android.tools.r8.internal.InterfaceC0992Fq;
import com.android.tools.r8.internal.InterfaceC1912eK;
import com.android.tools.r8.internal.M7;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.R7;
import com.android.tools.r8.internal.W8;
import com.android.tools.r8.internal.Z00;
import com.android.tools.r8.internal.Z8;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.graph.u, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0753u extends W implements D, com.android.tools.r8.utils.structural.x<C0753u> {
    static final /* synthetic */ boolean m = true;
    private final C0767v2 d;
    private int e;
    private int f;
    private List g;
    private final List h;
    private final List i;
    private int j;
    private final Position k;
    private final C2049g6 l;

    public C0753u(C0767v2 c0767v2, int i, int i2, List list) {
        this(c0767v2, i, i2, list, Collections.emptyList(), Collections.emptyList());
    }

    @Override // com.android.tools.r8.graph.W
    public final boolean A0() {
        return true;
    }

    public final Position H0() {
        return this.k;
    }

    public List<B7> I0() {
        return Collections.unmodifiableList(this.g);
    }

    public List<a> J0() {
        return Collections.unmodifiableList(this.i);
    }

    @Override // com.android.tools.r8.graph.W
    public final C0753u K() {
        return this;
    }

    public final int K0() {
        return this.e;
    }

    public final int L0() {
        return this.f;
    }

    public final C0767v2 M0() {
        return this.d;
    }

    public final AbstractC1915eM N0() {
        for (B7 b7 : this.g) {
            b7.getClass();
            if (!(b7 instanceof M7)) {
                if (!(b7 instanceof C2051g8)) {
                    break;
                }
                AbstractC1915eM abstractC1915eMU = b7.s().U();
                if (abstractC1915eMU.e() == 0) {
                    return abstractC1915eMU;
                }
            }
        }
        return null;
    }

    public final int O0() {
        if (m || this.j != 1) {
            return this.j;
        }
        throw new AssertionError();
    }

    public final Set P0() {
        Set setC = OU.c();
        for (W8 w8 : Q0()) {
            setC.add(w8.a);
            setC.add(w8.b);
        }
        return setC;
    }

    public List<W8> Q0() {
        return this.h;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.graph.W
    public final void d(Consumer consumer) {
        for (B7 b7 : I0()) {
            b7.getClass();
            if (b7 instanceof C2051g8) {
                consumer.accept(b7.s().U());
            }
        }
    }

    @Override // com.android.tools.r8.graph.W
    public final boolean k(int i) {
        Iterator it = this.g.iterator();
        int i2 = 0;
        while (it.hasNext() && (!((B7) it.next()).y() || (i2 = i2 + 1) <= i)) {
        }
        return i2 <= i;
    }

    public final B7 l(int i) {
        return (B7) this.g.get(i);
    }

    public final void m(int i) {
        this.e = i;
    }

    public final void n(int i) {
        this.f = i;
    }

    @Override // com.android.tools.r8.graph.D
    public final int n0() {
        return 1;
    }

    public final void o(int i) {
        this.j = i;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final int s0() {
        throw new Z00();
    }

    @Override // com.android.tools.r8.graph.W
    public final D t0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.W
    public String toString() {
        return new C2757p8(this, null, com.android.tools.r8.utils.P2.a()).toString();
    }

    @Override // com.android.tools.r8.graph.W
    public final int w0() {
        Iterator it = this.g.iterator();
        int i = 0;
        while (it.hasNext() && (!((B7) it.next()).y() || (i = i + 1) <= Integer.MAX_VALUE)) {
        }
        return i * 5;
    }

    @Override // com.android.tools.r8.graph.W
    public final int x0() {
        Iterator it = this.g.iterator();
        int i = 0;
        while (it.hasNext() && (!((B7) it.next()).y() || (i = i + 1) <= Integer.MAX_VALUE)) {
        }
        return i;
    }

    @Override // com.android.tools.r8.graph.W
    public final boolean y0() {
        Iterator<B7> it = I0().iterator();
        while (it.hasNext()) {
            if (it.next() instanceof R7) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.graph.W
    public final boolean z0() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.o oVar, B7 b7) {
        oVar.a(b7.z());
        b7.a(oVar);
    }

    private void b(C0702m c0702m, o5 o5Var, AbstractC1476Wj abstractC1476Wj) {
        int iA = a(c0702m, o5Var, abstractC1476Wj);
        this.j = iA;
        if (iA == 4 || iA == 2) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.g);
        arrayList.removeIf(new Predicate() { // from class: com.android.tools.r8.graph.u$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((B7) obj).G();
            }
        });
        a(arrayList);
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final boolean c(Object obj) {
        throw new Z00();
    }

    /* JADX INFO: renamed from: com.android.tools.r8.graph.u$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        static final /* synthetic */ boolean e = true;
        private final int a;
        private final X b;
        private final M7 c;
        private M7 d;

        public a(int i, X x, M7 m7) {
            this.a = i;
            this.b = x;
            this.c = m7;
        }

        public final void a(M7 m7) {
            boolean z = e;
            if (!z && this.d != null) {
                throw new AssertionError();
            }
            if (!z && m7 == null) {
                throw new AssertionError();
            }
            this.d = m7;
        }

        public int b() {
            return this.a;
        }

        public X c() {
            return this.b;
        }

        public M7 d() {
            return this.c;
        }

        public final String toString() {
            return this.a + " => " + this.b;
        }

        public M7 a() {
            return this.d;
        }

        public a(int i, X x, M7 m7, M7 m8) {
            this(i, x, m7);
            a(m8);
        }

        public final int a(a aVar, com.android.tools.r8.utils.structural.a aVar2, final C c) {
            return aVar2.a(this, aVar, (com.android.tools.r8.utils.structural.y<a>) new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.u$a$$ExternalSyntheticLambda4
                @Override // com.android.tools.r8.utils.structural.y
                public final void a(com.android.tools.r8.utils.structural.z zVar) {
                    C0753u.a.a(c, zVar);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void a(C c, com.android.tools.r8.utils.structural.z zVar) {
            com.android.tools.r8.utils.structural.z zVarA = zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.graph.u$a$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((C0753u.a) obj).b();
                }
            });
            Function function = new Function() { // from class: com.android.tools.r8.graph.u$a$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((C0753u.a) obj).d();
                }
            };
            com.android.tools.r8.utils.structural.u uVarA = c.a();
            com.android.tools.r8.utils.structural.z zVarA2 = zVarA.a(function, uVarA, uVarA);
            Function function2 = new Function() { // from class: com.android.tools.r8.graph.u$a$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((C0753u.a) obj).a();
                }
            };
            com.android.tools.r8.utils.structural.u uVarA2 = c.a();
            zVarA2.a(function2, uVarA2, uVarA2).c(new Function() { // from class: com.android.tools.r8.graph.u$a$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((C0753u.a) obj).c();
                }
            });
        }
    }

    public C0753u(C0767v2 c0767v2, int i, int i2, List list, List list2, List list3) {
        this(c0767v2, i, i2, list, list2, list3, Position.UNKNOWN);
    }

    public C0753u(C0767v2 c0767v2, int i, int i2, List list, List list2, List list3, Position position) {
        this(c0767v2, i, i2, list, list2, list3, position, C2049g6.a());
    }

    @Override // com.android.tools.r8.graph.W
    public final C1890e6 a(InterfaceC0419c interfaceC0419c) {
        return this.l.a(interfaceC0419c.m());
    }

    public C0753u(C0767v2 c0767v2, int i, int i2, List list, List list2, List list3, Position position, C2049g6 c2049g6) {
        this.j = 1;
        this.d = c0767v2;
        this.f = i;
        this.e = i2;
        this.g = list;
        this.h = list2;
        this.i = list3;
        this.k = position;
        this.l = c2049g6;
    }

    public void a(List<B7> list) {
        this.g = list;
    }

    @Override // com.android.tools.r8.graph.D, com.android.tools.r8.graph.M2
    public final void a(final com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this.g.size());
        oVar.a(this.h.size());
        oVar.a(this.i.size());
        this.g.forEach(new Consumer() { // from class: com.android.tools.r8.graph.u$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0753u.a(oVar, (B7) obj);
            }
        });
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final int a(C0753u c0753u, com.android.tools.r8.utils.structural.a aVar) {
        final C c = new C(this, c0753u);
        return aVar.a(this, c0753u, (com.android.tools.r8.utils.structural.y<C0753u>) new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.u$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C0753u.a(c, zVar);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C c, com.android.tools.r8.utils.structural.z zVar) {
        Function function = new Function() { // from class: com.android.tools.r8.graph.u$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0753u) obj).g;
            }
        };
        c.getClass();
        zVar.a(function, (com.android.tools.r8.utils.structural.u) new C0813z(c)).a(new Function() { // from class: com.android.tools.r8.graph.u$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0753u) obj).h;
            }
        }, (com.android.tools.r8.utils.structural.u) new A(c)).a(new Function() { // from class: com.android.tools.r8.graph.u$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0753u) obj).i;
            }
        }, (com.android.tools.r8.utils.structural.u) new B(c));
    }

    /* JADX WARN: Code duplicated, block: B:35:0x009b  */
    @Override // com.android.tools.r8.graph.D
    public final void a(o5 o5Var, Z8 z8, C0702m c0702m, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        M7 m7;
        int iA;
        AbstractC1476Wj abstractC1476WjE = c0702m.E();
        boolean z = m;
        if (!z && (iA = a(c0702m, o5Var, a(c0702m))) != 4 && iA != 2) {
            throw new AssertionError("Could not validate stack map frames");
        }
        C0722p1 c0722p1A = c0702m.a();
        AbstractC2331jn abstractC2331jnN = c0702m.N();
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        W0 w0E = o5Var.e();
        if (c0702m.Y().T0() || c0702m.Y().W0 || c0702m.a(w0E)) {
            m7 = null;
        } else {
            if (!z && !this.i.isEmpty()) {
                throw new AssertionError();
            }
            if (w0E.r1() && (!c0702m.f().i() || c0702m.f().o().b(w0E))) {
                M7 m8 = new M7();
                abstractC1899eF.a(m8.T());
                m7 = m8;
            } else {
                m7 = null;
            }
        }
        Z8 z9 = Z8.f;
        boolean z2 = z8.e(z9) || (c0702m.q() && z8.isEqualTo(z9) && !a1Y.E1());
        C0763v c0763v = new C0763v(abstractC1899eF, c0702m);
        for (B7 b7 : this.g) {
            if (!z2 || !(b7 instanceof C2284j7)) {
                C0763v c0763v2 = c0763v;
                b7.a(c0702m, o5Var, c0722p1A, abstractC1476WjE, abstractC2331jnN, abstractC3649o0, c2363kB, c0763v2);
                a1Y = a1Y;
                c0763v = c0763v2;
            }
        }
        com.android.tools.r8.utils.A1 a1 = a1Y;
        abstractC1899eF.c(this.f, this.e);
        for (W8 w8 : this.h) {
            C3263wA c3263wAT = w8.a.T();
            C3263wA c3263wAT2 = w8.b.T();
            for (int i = 0; i < w8.c.size(); i++) {
                C0767v2 c0767v2A = AbstractC2964s2.a(abstractC1476WjE, w8.c.get(i));
                abstractC1899eF.a(c3263wAT, c3263wAT2, w8.d.get(i).T(), c0767v2A == a1.a.m3 ? null : abstractC3649o0.d(c0767v2A));
            }
        }
        if (m7 != null) {
            if (!m && !this.i.isEmpty()) {
                throw new AssertionError();
            }
            InterfaceC1912eK it = o5Var.e().k1().c().iterator();
            while (it.hasNext()) {
                InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it.next();
                X x = (X) interfaceC0934Dq.getValue();
                int iA2 = interfaceC0934Dq.a();
                C0767v2 c0767v2A2 = AbstractC2964s2.a(abstractC1476WjE, x.b);
                String string = x.a.toString();
                String string2 = abstractC3649o0.c(c0767v2A2).toString();
                C0759u2 c0759u2 = x.c;
                abstractC1899eF.a(string, string2, c0759u2 == null ? null : c0759u2.toString(), m7.T(), m7.T(), iA2);
            }
            return;
        }
        for (a aVar : this.i) {
            X x2 = aVar.b;
            M7 m9 = aVar.c;
            M7 m10 = aVar.d;
            int i2 = aVar.a;
            C0767v2 c0767v2A3 = AbstractC2964s2.a(abstractC1476WjE, x2.b);
            String string3 = x2.a.toString();
            String string4 = abstractC3649o0.c(c0767v2A3).toString();
            C0759u2 c0759u3 = x2.c;
            abstractC1899eF.a(string3, string4, c0759u3 == null ? null : c0759u3.toString(), m9.T(), m10.T(), i2);
        }
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(o5 o5Var, C0702m c0702m, Origin origin, CE.a aVar) {
        b(c0702m, o5Var, a(c0702m));
        AbstractC1476Wj abstractC1476WjN = c0702m.n();
        o5Var.getClass();
        if (c0702m.Y().n1.V || (!c0702m.Y().W0 && !o5Var.a().f(c0702m))) {
            return a(Collections.emptyList(), o5Var, o5Var, c0702m, abstractC1476WjN, null, null, origin, null, aVar);
        }
        try {
            return a(Collections.unmodifiableList(this.i), o5Var, o5Var, c0702m, abstractC1476WjN, null, null, origin, null, aVar);
        } catch (C0994Fs e) {
            c0702m.Y().a(o5Var, origin, e);
            return a(Collections.emptyList(), o5Var, o5Var, c0702m, abstractC1476WjN, null, null, origin, null, aVar);
        }
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(o5 o5Var, o5 o5Var2, C0702m c0702m, AbstractC1476Wj abstractC1476Wj, C2145hI c2145hI, AbstractC1915eM abstractC1915eM, Origin origin, com.android.tools.r8.graph.proto.k kVar) {
        boolean z = m;
        if (!z && c2145hI == null) {
            throw new AssertionError();
        }
        if (!z && abstractC1915eM == null) {
            throw new AssertionError();
        }
        if (!z && kVar == null) {
            throw new AssertionError();
        }
        b(c0702m, o5Var2, abstractC1476Wj);
        DE de = new DE(c0702m.Y());
        o5Var2.getClass();
        if (c0702m.Y().n1.V || (!c0702m.Y().W0 && !o5Var2.a().f(c0702m))) {
            return a(Collections.emptyList(), o5Var, o5Var2, c0702m, abstractC1476Wj, c2145hI, abstractC1915eM, origin, kVar, de);
        }
        try {
            try {
                return a(Collections.unmodifiableList(this.i), o5Var, o5Var2, c0702m, abstractC1476Wj, c2145hI, abstractC1915eM, origin, kVar, de);
            } catch (C0994Fs e) {
                e = e;
                c0702m.Y().a(o5Var2, origin, e);
                return a(Collections.emptyList(), o5Var, o5Var2, c0702m, abstractC1476Wj, c2145hI, abstractC1915eM, origin, kVar, de);
            }
        } catch (C0994Fs e2) {
            e = e2;
        }
    }

    private C2409kl a(List list, o5 o5Var, o5 o5Var2, C0702m c0702m, AbstractC1476Wj abstractC1476Wj, C2145hI c2145hI, AbstractC1915eM abstractC1915eM, Origin origin, com.android.tools.r8.graph.proto.k kVar, CE.a aVar) {
        C2102gl c2102glA;
        A8 a8 = new A8(this, list, o5Var2, c0702m.E().a((AbstractC1476Wj) null, o5Var2.getReference()), abstractC1915eM, origin, c0702m);
        if (c2145hI == null) {
            if (!m && kVar != null) {
                throw new AssertionError();
            }
            c2102glA = C2102gl.a(o5Var2, c0702m, a8, origin);
        } else {
            c2102glA = C2102gl.a(c0702m, o5Var2, abstractC1476Wj, kVar, c2145hI, a8, origin);
        }
        return c2102glA.a(o5Var, aVar);
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(o5 o5Var, J5 j5) {
        if (!m && !j5.e().g()) {
            throw new AssertionError();
        }
        ListIterator listIterator = this.g.listIterator();
        while (listIterator.hasNext()) {
            ((B7) listIterator.next()).a(j5, o5Var, listIterator);
            if (j5.e().f()) {
                return;
            }
        }
        Iterator it = this.h.iterator();
        while (it.hasNext()) {
            ((W8) it.next()).a(j5);
            if (j5.e().f()) {
                return;
            }
        }
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(S s, final J5 j5) {
        ListIterator listIterator = this.g.listIterator();
        while (listIterator.hasNext()) {
            ((B7) listIterator.next()).a(j5, s, listIterator);
        }
        this.h.forEach(new Consumer() { // from class: com.android.tools.r8.graph.u$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0753u.a(j5, (W8) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final J5 j5, W8 w8) {
        List<C0767v2> list = w8.c;
        Objects.requireNonNull(j5);
        list.forEach(new Consumer() { // from class: com.android.tools.r8.graph.u$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                j5.f((C0767v2) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.W
    public final InterfaceC0992Fq a(C0702m c0702m, W0 w0) {
        M7 m7;
        int i;
        Iterator it = this.g.iterator();
        while (true) {
            if (!it.hasNext()) {
                m7 = null;
                break;
            }
            B7 b7 = (B7) it.next();
            if (b7 instanceof M7) {
                m7 = (M7) b7;
                break;
            }
        }
        if (m7 == null) {
            return W0.u;
        }
        if (c0702m.Y().M0() && c0702m.Y().D0().D()) {
            if (c0702m.f().i() && !c0702m.f().o().b(w0)) {
                return W0.u;
            }
            BitSet bitSet = new BitSet(0);
            if (w0.E0()) {
                i = 0;
            } else {
                bitSet.set(0);
                i = 1;
            }
            for (C0767v2 c0767v2 : w0.getReference().h.f.a) {
                bitSet.set(i);
                i += (c0767v2.T0() || c0767v2.P0()) ? 2 : 1;
            }
            C3017sq c3017sq = new C3017sq(bitSet.cardinality());
            for (a aVar : this.i) {
                if (aVar.c == m7 && bitSet.get(aVar.a) && !c3017sq.a(aVar.a)) {
                    int i2 = aVar.a;
                    X x = aVar.b;
                    c3017sq.a(i2, new X(x.a, x.c, x.b));
                }
            }
            return c3017sq;
        }
        return W0.u;
    }

    @Override // com.android.tools.r8.graph.W
    public final String a(W0 w0, com.android.tools.r8.utils.P2 p2) {
        return new C2757p8(this, w0, p2).toString();
    }

    public final com.android.tools.r8.ir.optimize.V a(o5 o5Var, C0702m c0702m, AbstractC1476Wj abstractC1476Wj, o5 o5Var2) {
        com.android.tools.r8.ir.optimize.V vA;
        C3550d0 c3550d0 = new C3550d0(c0702m, abstractC1476Wj);
        if (c0702m.Y().Z0()) {
            c3550d0.a();
        }
        if (o5Var.e().f.c0()) {
            vA = com.android.tools.r8.ir.optimize.V.d;
        } else {
            vA = com.android.tools.r8.ir.optimize.V.d;
        }
        if (vA == com.android.tools.r8.ir.optimize.V.c) {
            return vA;
        }
        Iterator it = this.g.iterator();
        while (it.hasNext()) {
            vA = com.android.tools.r8.ir.optimize.V.a(vA, ((B7) it.next()).a(c3550d0, this, o5Var2), (C0702m<?>) c0702m);
            if (vA == com.android.tools.r8.ir.optimize.V.c) {
                return vA;
            }
        }
        return !this.h.isEmpty() ? com.android.tools.r8.ir.optimize.V.a(vA, com.android.tools.r8.ir.optimize.V.d, (C0702m<?>) c0702m) : vA;
    }

    final void a(C0722p1 c0722p1) {
        List list = this.i;
        if (list == null || list.isEmpty()) {
            return;
        }
        int i = -1;
        int iMax = 0;
        for (int i2 = 0; i2 < this.i.size(); i2++) {
            a aVar = (a) this.i.get(i2);
            iMax = Math.max(iMax, C0772w0.a(c0722p1, aVar.b.a));
            if (aVar.b.a.toString().equals("this")) {
                i = i2;
            }
        }
        if (i < 0) {
            return;
        }
        X x = new X(c0722p1.c(AbstractC2619nY.a(iMax + 1, "_") + "this"), null, this.d);
        a aVar2 = (a) this.i.get(i);
        this.i.set(i, new a(aVar2.a, x, aVar2.c, aVar2.d));
    }

    @Override // com.android.tools.r8.graph.W
    public final W a(C0722p1 c0722p1, C0684j2 c0684j2, C0684j2 c0684j3, boolean z) {
        M7 m7;
        AbstractC1915eM.c cVarD = AbstractC1915eM.c.x().a(0).a(c0684j2).a();
        ArrayList arrayList = new ArrayList(this.g.size() + 2);
        B7 b7 = (B7) this.g.get(0);
        b7.getClass();
        if (b7 instanceof M7) {
            m7 = ((B7) this.g.get(0)).q();
        } else {
            m7 = new M7();
            arrayList.add(m7);
        }
        boolean z2 = false;
        for (B7 b8 : this.g) {
            b8.getClass();
            if (b8 instanceof C2051g8) {
                C2051g8 c2051g8S = b8.s();
                arrayList.add(new C2051g8(c2051g8S.T(), W.a(cVarD, c2051g8S.U(), z)));
                z2 = true;
            } else {
                if (!(b8 instanceof M7) && !z2) {
                    arrayList.add(new C2051g8(m7, z ? cVarD : ((AbstractC1915eM.c.a) AbstractC1915eM.c.x().a(c0684j3).a(cVarD)).a(0).a()));
                    z2 = true;
                }
                arrayList.add(b8);
            }
        }
        return new C0753u(this.d, this.f, this.e, arrayList, this.h, this.i, Position.UNKNOWN);
    }

    public final int a(C0702m c0702m, o5 o5Var, AbstractC1476Wj abstractC1476Wj) {
        int i = this.j;
        AbstractC0403b.a(i);
        if (i == 1) {
            this.j = C2674o7.a(c0702m, this, o5Var).a(abstractC1476Wj).a(new C0747t(c0702m)).a().a();
        }
        return this.j;
    }
}
