package com.android.tools.r8.internal;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.MethodPosition;
import com.android.tools.r8.utils.StringDiagnostic;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class S10 implements Comparable<S10> {
    public static final S10 o = new S10(-1, AbstractC3472z00.f(), null);
    static final /* synthetic */ boolean p = true;
    protected final int a;
    private com.android.tools.r8.ir.regalloc.c i;
    private com.android.tools.r8.utils.W1 l;
    private Q10 m;
    protected AbstractC3472z00 n;
    public AbstractC2412ko b = null;
    private LinkedList c = new LinkedList();
    private AbstractC1048Hm d = null;
    private LinkedList e = new LinkedList();
    private AbstractC1048Hm f = null;
    private S10 g = null;
    private S10 h = null;
    private int j = -1;
    private boolean k = false;

    public S10(int i, AbstractC3472z00 abstractC3472z00, com.android.tools.r8.graph.X x) {
        if (!p && abstractC3472z00 == null) {
            throw new AssertionError();
        }
        this.a = i;
        this.m = x != null ? new Q10(x) : null;
        this.n = abstractC3472z00;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(ML ml, ML ml2) {
        return ml2 == ml;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC2412ko abstractC2412ko, AbstractC2412ko abstractC2412ko2) {
        return abstractC2412ko2 == abstractC2412ko;
    }

    private void c(final AbstractC2412ko abstractC2412ko) {
        this.c.removeIf(new Predicate() { // from class: com.android.tools.r8.internal.S10$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return S10.a(abstractC2412ko, (AbstractC2412ko) obj);
            }
        });
        this.d = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean e(S10 s10) {
        return s10 == this;
    }

    public final boolean A() {
        return s() != null;
    }

    public final boolean B() {
        return E() || C();
    }

    public final boolean C() {
        return !this.e.isEmpty();
    }

    public final boolean D() {
        return h0().size() == 1;
    }

    public final boolean E() {
        return !this.c.isEmpty();
    }

    public final boolean F() {
        return this.c != null;
    }

    public final boolean G() {
        return this.l != null || J();
    }

    public boolean H() {
        return b(new A4$$ExternalSyntheticLambda3());
    }

    public final boolean I() {
        if (L()) {
            AbstractC0824Ac abstractC0824AcN = n();
            abstractC0824AcN.getClass();
            if (abstractC0824AcN instanceof C3446yc) {
                return true;
            }
        }
        return false;
    }

    public final boolean J() {
        return L() && n().B1();
    }

    public final boolean K() {
        if (L()) {
            AbstractC0824Ac abstractC0824AcN = n();
            abstractC0824AcN.getClass();
            if (abstractC0824AcN instanceof C1033Hc) {
                return true;
            }
        }
        return false;
    }

    public boolean L() {
        return this.b.r2() && !A();
    }

    public final boolean M() {
        if (L()) {
            AbstractC0824Ac abstractC0824AcN = n();
            abstractC0824AcN.getClass();
            if (abstractC0824AcN instanceof C2477lf) {
                return true;
            }
        }
        return false;
    }

    public boolean N() {
        return this instanceof C2799pi;
    }

    public final boolean O() {
        return (this.g == null && this.h == null) ? false : true;
    }

    public boolean P() {
        if (p || this.n.I()) {
            return b(new Predicate() { // from class: com.android.tools.r8.internal.S10$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((AbstractC2412ko) obj).u1();
                }
            }) || this.n.x();
        }
        throw new AssertionError();
    }

    public final boolean Q() {
        return this.k;
    }

    public final boolean R() {
        AbstractC2412ko abstractC2412ko = this.b;
        if (abstractC2412ko != null) {
            abstractC2412ko.getClass();
            if (abstractC2412ko instanceof C1259Pd) {
                return true;
            }
        }
        return false;
    }

    public final boolean S() {
        if (this.c.isEmpty() && this.e.isEmpty()) {
            Q10 q10 = this.m;
            if ((q10 == null ? 0 : q10.b.size()) <= 0) {
                return false;
            }
        }
        return true;
    }

    public boolean T() {
        return this instanceof C2617nW;
    }

    public final boolean V() {
        return L() && n().B1() && n().E().T2();
    }

    public final void W() {
        boolean z = p;
        if (!z && !H()) {
            throw new AssertionError();
        }
        if (!z && this.k) {
            throw new AssertionError();
        }
        this.k = true;
    }

    public void X() {
        if (!p && i()) {
            throw new AssertionError();
        }
    }

    public boolean Y() {
        boolean z;
        boolean z2 = p;
        if (!z2 && this.j < 0) {
            throw new AssertionError();
        }
        if (!z2 && F()) {
            boolean z3 = this.j > 0;
            if (!J() || a0() > 0) {
                z = true;
                break;
            }
            Iterator<AbstractC2412ko> it = h0().iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (it.next().d(this)) {
                    z = true;
                    break;
                }
            }
            if (z3 != z) {
                throw new AssertionError();
            }
        }
        return this.j > 0;
    }

    public int Z() {
        int iA0 = a0() + b0();
        Q10 q10 = this.m;
        return (q10 == null ? 0 : q10.b.size()) + iA0;
    }

    public final int a0() {
        int size = this.e.size();
        return size <= 1 ? size : g0().size();
    }

    public C2799pi b() {
        return null;
    }

    public final int b0() {
        int size = this.c.size();
        return size <= 1 ? size : h0().size();
    }

    public final boolean c0() {
        if (i()) {
            return false;
        }
        int iG2 = this.b.G2();
        if (iG2 == 5) {
            return true;
        }
        if (iG2 == 10) {
            return this.b.y().e().c0();
        }
        if (iG2 == 12 || iG2 == 20) {
            return true;
        }
        if (iG2 != 29) {
            return iG2 == 15 || iG2 == 16;
        }
        return this.b.U().value().c0();
    }

    public final void d() {
        this.c.clear();
        this.d = null;
        c();
        Q10 q10 = this.m;
        if (q10 != null) {
            q10.b.clear();
        }
    }

    public EnumC1812d20 d0() {
        return EnumC1812d20.a(this.n);
    }

    public final int e0() {
        return this.n.O();
    }

    public final void f(S10 s10) {
        S10 s11;
        S10 s12;
        boolean z = p;
        if (!z && (s12 = this.g) != null && s12 != s10) {
            throw new AssertionError();
        }
        if (!z && (s11 = s10.h) != null && s11 != this) {
            throw new AssertionError();
        }
        s10.h = this;
        this.g = s10;
    }

    public final AbstractC2412ko f0() {
        if (p || AbstractC1048Hm.a(this.c).size() == 1) {
            return (AbstractC2412ko) this.c.getFirst();
        }
        throw new AssertionError();
    }

    public final Set g() {
        Q10 q10 = this.m;
        if (q10 == null) {
            return null;
        }
        return Collections.unmodifiableSet(q10.b);
    }

    public final Set g0() {
        AbstractC1048Hm abstractC1048Hm = this.f;
        if (abstractC1048Hm != null) {
            return abstractC1048Hm;
        }
        AbstractC1048Hm abstractC1048HmA = AbstractC1048Hm.a(this.e);
        this.f = abstractC1048HmA;
        return abstractC1048HmA;
    }

    public C3332x4 h() {
        return this.b.h();
    }

    public Set<AbstractC2412ko> h0() {
        AbstractC1048Hm abstractC1048Hm = this.d;
        if (abstractC1048Hm != null) {
            return abstractC1048Hm;
        }
        AbstractC1048Hm abstractC1048HmA = AbstractC1048Hm.a(this.c);
        this.d = abstractC1048HmA;
        return abstractC1048HmA;
    }

    public final int hashCode() {
        return this.a;
    }

    public boolean i() {
        return false;
    }

    public final boolean i0() {
        for (AbstractC2412ko abstractC2412ko : h0()) {
            abstractC2412ko.getClass();
            if (abstractC2412ko instanceof C3344xF) {
                return true;
            }
        }
        return false;
    }

    public final ML j() {
        if (p || !this.e.isEmpty()) {
            return (ML) this.e.getFirst();
        }
        throw new AssertionError();
    }

    public ML l() {
        return null;
    }

    public S10 m() {
        return a(C1552Zd.a(), CM.a());
    }

    public final AbstractC0824Ac n() {
        if (p || L()) {
            return this.b.X0();
        }
        throw new AssertionError();
    }

    public final Set o() {
        Q10 q10 = this.m;
        return q10 == null ? Collections.emptySet() : Collections.unmodifiableSet(q10.b);
    }

    public final AbstractC2412ko q() {
        if (p || !i()) {
            return this.b;
        }
        throw new AssertionError();
    }

    public final com.android.tools.r8.ir.regalloc.c r() {
        return this.i;
    }

    public final com.android.tools.r8.graph.X s() {
        Q10 q10 = this.m;
        if (q10 == null) {
            return null;
        }
        return q10.a;
    }

    public final S10 t() {
        return this.g;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(RegisterSpec.PREFIX);
        sb.append(this.a);
        AbstractC2412ko abstractC2412ko = this.b;
        boolean z = abstractC2412ko != null && abstractC2412ko.B1();
        if (z || A()) {
            sb.append("(");
            if (z && this.b.E().a != null) {
                C0978Fc c0978FcE = this.b.E();
                if (c0978FcE.a().K()) {
                    sb.append((int) c0978FcE.P2());
                } else {
                    sb.append(c0978FcE.P2());
                }
            }
            if (z && A()) {
                sb.append(", ");
            }
            if (A()) {
                sb.append(s());
            }
            sb.append(Constant.AFTER_QUTO);
        }
        com.android.tools.r8.utils.W1 w1 = this.l;
        if (w1 != null) {
            sb.append(w1);
        }
        return sb.toString();
    }

    public int u() {
        return this.a;
    }

    public final S10 v() {
        S10 s10 = this;
        while (true) {
            S10 s11 = s10.h;
            if (s11 == null) {
                return s10;
            }
            s10 = s11;
        }
    }

    public AbstractC3472z00 w() {
        return this.n;
    }

    public final com.android.tools.r8.utils.W1 x() {
        if (!J()) {
            return this.l;
        }
        if (this.n.K()) {
            int iN2 = n().E().N2();
            return new com.android.tools.r8.utils.W1(iN2, iN2);
        }
        if (!p && !this.n.M()) {
            throw new AssertionError();
        }
        long jO2 = n().E().O2();
        return new com.android.tools.r8.utils.W1(jO2, jO2);
    }

    public final boolean y() {
        return E() || C() || z();
    }

    public final boolean z() {
        Q10 q10 = this.m;
        return (q10 == null || q10.b.isEmpty()) ? false : true;
    }

    public final boolean b(C0702m c0702m) {
        if (A()) {
            return false;
        }
        if (this.n.N().f()) {
            return true;
        }
        if (this.n.w() && c0702m.f().i()) {
            return this.n.b().R().b(c0702m.i0());
        }
        return false;
    }

    public final void e(AbstractC2412ko abstractC2412ko) {
        this.c.remove(abstractC2412ko);
        this.d = null;
    }

    public void g(S10 s10) {
        a(s10, (Set) null);
    }

    public void a(EnumC1968f20 enumC1968f20, C0684j2 c0684j2, Origin origin, com.android.tools.r8.utils.O2 o2) {
        AbstractC3472z00 abstractC3472z00A = a(enumC1968f20);
        if (abstractC3472z00A != null) {
            if (abstractC3472z00A != this.n) {
                a(abstractC3472z00A);
            }
        } else {
            o2.a(new StringDiagnostic("Cannot constrain type: " + this.n + " for value: " + this + " by constraint: " + enumC1968f20, origin, new MethodPosition(c0684j2.E0())));
            throw null;
        }
    }

    public final void c() {
        this.e.clear();
        this.f = null;
    }

    public final void e() {
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        Q10 q10 = this.m;
        if (q10 != null) {
            q10.b = null;
        }
    }

    public final void c(ML ml) {
        this.e.remove(ml);
        this.f = null;
    }

    public void f() {
        boolean z;
        if (!p && this.j >= 0) {
            throw new AssertionError();
        }
        if (J() && a0() <= 0) {
            Iterator<AbstractC2412ko> it = h0().iterator();
            do {
                if (!it.hasNext()) {
                    z = false;
                }
            } while (!it.next().d(this));
            z = true;
        } else {
            z = true;
        }
        a(z);
    }

    public final void d(AbstractC2412ko abstractC2412ko) {
        Set set;
        Q10 q10 = this.m;
        if (q10 != null && (set = q10.b) != null) {
            set.remove(abstractC2412ko);
        } else if (!p) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(S10 s10) {
        return s10.b(new Rs$$ExternalSyntheticLambda0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean d(S10 s10) {
        return s10.b(new Rs$$ExternalSyntheticLambda0());
    }

    public final void b(AbstractC2412ko abstractC2412ko) {
        this.c.add(abstractC2412ko);
        this.d = null;
    }

    private void b(final ML ml) {
        this.e.removeIf(new Predicate() { // from class: com.android.tools.r8.internal.S10$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return S10.a(ml, (ML) obj);
            }
        });
        this.f = null;
    }

    public final void b(AbstractC2412ko abstractC2412ko, AbstractC2412ko abstractC2412ko2) {
        boolean zRemove = this.m.b.remove(abstractC2412ko);
        if (!p && !zRemove) {
            throw new AssertionError();
        }
        if (zRemove) {
            a(abstractC2412ko2);
        }
    }

    public boolean b(Predicate<AbstractC2412ko> predicate) {
        return predicate.test(this.b);
    }

    public final boolean b(Set set) {
        AbstractC3472z00 abstractC3472z00W = w();
        abstractC3472z00W.getClass();
        if (!(abstractC3472z00W instanceof C1484Wr)) {
            return false;
        }
        if (i()) {
            ML mlL = l();
            if (set == null) {
                set = OU.c();
            }
            if (set.contains(mlL)) {
                return true;
            }
            set.add(mlL);
            for (S10 s10 : mlL.k0()) {
                if (!s10.b(set)) {
                    s10.b(set);
                    return false;
                }
            }
            return true;
        }
        if (p || this.b != null) {
            return this.b.a(set);
        }
        throw new AssertionError();
    }

    public final AbstractC3472z00 a(EnumC1968f20 enumC1968f20) {
        if (enumC1968f20 == EnumC1968f20.INT_OR_FLOAT_OR_OBJECT && !this.n.M()) {
            return this.n;
        }
        int iOrdinal = enumC1968f20.ordinal();
        if (iOrdinal == 0) {
            if (this.n.L()) {
                AbstractC2412ko abstractC2412ko = this.b;
                if (abstractC2412ko != null && abstractC2412ko.B1()) {
                    if (p || this.b.E().T2()) {
                        return AbstractC3472z00.m();
                    }
                    throw new AssertionError();
                }
                return AbstractC3472z00.f();
            }
            if (this.n.I()) {
                return this.n;
            }
            if (!this.n.t()) {
                return null;
            }
            if (!p && !i() && !this.b.G1() && (!this.b.p1() || this.b.t().d() != EnumC2523mE.OBJECT)) {
                throw new AssertionError();
            }
            return this.n;
        }
        if (iOrdinal == 1) {
            if (this.n.L() || (this.n.K() && !this.n.B())) {
                return AbstractC3472z00.k();
            }
            return null;
        }
        if (iOrdinal == 2) {
            if (this.n.L() || (this.n.K() && !this.n.C())) {
                return AbstractC3472z00.j();
            }
            return null;
        }
        if (iOrdinal == 3) {
            if (this.n.L()) {
                return AbstractC3472z00.o();
            }
            if (this.n.K()) {
                return this.n;
            }
            return null;
        }
        if (iOrdinal == 5) {
            if (!this.n.M() || this.n.z()) {
                return null;
            }
            return AbstractC3472z00.l();
        }
        if (iOrdinal == 6) {
            if (!this.n.M() || this.n.D()) {
                return null;
            }
            return AbstractC3472z00.i();
        }
        if (iOrdinal != 7) {
            throw new C3405y10("Unexpected constraint: " + enumC1968f20);
        }
        if (this.n.M()) {
            return this.n;
        }
        return null;
    }

    public final void b(C0702m c0702m, AbstractC3472z00 abstractC3472z00) {
        if (p || (!(c0702m.Y().n1.X || c0702m.q()) || this.n.a(abstractC3472z00, (C0702m<?>) c0702m))) {
            a(abstractC3472z00);
        } else {
            throw new AssertionError("During WIDENING, " + abstractC3472z00 + " < " + this.n + " at " + (i() ? l().m0() : this.b.toString()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(S10 s10) {
        return s10.q().t1();
    }

    public final void a(EnumC1812d20 enumC1812d20) {
        EnumC1968f20 enumC1968f20A = EnumC1968f20.a(enumC1812d20);
        if (!p && a(enumC1968f20A) == null) {
            throw new AssertionError();
        }
    }

    public final S10 a(I1 i1, Predicate predicate) {
        S10 s10A;
        if (!p && predicate == null) {
            throw new AssertionError();
        }
        Set setC = OU.c();
        S10 s10 = this;
        while (!s10.i() && !predicate.test(s10)) {
            AbstractC2412ko abstractC2412ko = s10.b;
            if (i1.b(abstractC2412ko)) {
                s10A = i1.a(abstractC2412ko);
                if (!p && !setC.add(s10A)) {
                    throw new AssertionError();
                }
            } else {
                s10A = s10;
            }
            if (s10A == s10) {
                if (!p && !s10A.i()) {
                    AbstractC2412ko abstractC2412ko2 = s10A.b;
                    abstractC2412ko2.getClass();
                    if (abstractC2412ko2 instanceof N3) {
                        throw new AssertionError();
                    }
                }
                return s10A;
            }
            s10 = s10A;
        }
        return s10;
    }

    public final S10 a(Predicate predicate) {
        S10 s10A = a(C1552Zd.a(), predicate);
        if (predicate.test(s10A)) {
            return s10A;
        }
        return null;
    }

    public final void a(com.android.tools.r8.graph.X x) {
        boolean z = p;
        if (!z && x == null) {
            throw new AssertionError();
        }
        if (!z && this.m != null) {
            throw new AssertionError();
        }
        this.m = new Q10(x);
    }

    public final void a(AbstractC2412ko abstractC2412ko) {
        if (!p && abstractC2412ko == null) {
            throw new AssertionError();
        }
        this.m.b.add(abstractC2412ko);
        abstractC2412ko.b(this);
    }

    public final boolean a(C3332x4 c3332x4) {
        if (C() || z()) {
            return false;
        }
        Iterator<AbstractC2412ko> it = h0().iterator();
        while (it.hasNext()) {
            if (it.next().h() != c3332x4) {
                return false;
            }
        }
        return true;
    }

    public final Set a(I1 i1) {
        Set setA = com.android.tools.r8.utils.T2.a((Iterable) h0());
        a(i1, OU.c(), h0(), setA);
        return setA;
    }

    private static void a(I1 i1, Set set, Set set2, Set set3) {
        Iterator it = set2.iterator();
        while (it.hasNext()) {
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) it.next();
            if (set.add(abstractC2412ko) && i1.b(abstractC2412ko)) {
                set3.addAll(abstractC2412ko.b().h0());
                a(i1, set, abstractC2412ko.b().h0(), set3);
            }
        }
    }

    public final void a(ML ml) {
        this.e.add(ml);
        this.f = null;
    }

    public final AbstractC1048Hm a() {
        final C0862Bm c0862BmG = AbstractC1048Hm.g();
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.S10$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c0862BmG.a((S10) obj);
            }
        };
        for (AbstractC2412ko abstractC2412ko : h0()) {
            if (abstractC2412ko.d1()) {
                consumer.accept(abstractC2412ko.b());
            }
        }
        g0().forEach(consumer);
        return c0862BmG.a();
    }

    public final void a(Set set) {
        Objects.requireNonNull(set);
        S10$$ExternalSyntheticLambda8 s10$$ExternalSyntheticLambda8 = new S10$$ExternalSyntheticLambda8(set);
        for (AbstractC2412ko abstractC2412ko : h0()) {
            if (abstractC2412ko.d1()) {
                s10$$ExternalSyntheticLambda8.accept(abstractC2412ko.b());
            }
        }
        g0().forEach(s10$$ExternalSyntheticLambda8);
    }

    public final void a(S10 s10, Set set) {
        if (this == s10) {
            return;
        }
        for (AbstractC2412ko abstractC2412ko : h0()) {
            abstractC2412ko.b(this, s10);
            if (set != null && abstractC2412ko.d1()) {
                set.add(abstractC2412ko.a);
            }
        }
        for (ML ml : g0()) {
            ml.a(this, s10);
            if (set != null) {
                set.add(ml);
            }
        }
        Q10 q10 = this.m;
        if (q10 != null) {
            Iterator it = q10.b.iterator();
            while (it.hasNext()) {
                ((AbstractC2412ko) it.next()).a(this, s10);
            }
            this.m.b.clear();
        }
        d();
    }

    public final void a(Predicate predicate, S10 s10) {
        if (this == s10) {
            return;
        }
        for (AbstractC2412ko abstractC2412ko : h0()) {
            if (predicate.test(abstractC2412ko)) {
                c(abstractC2412ko);
                abstractC2412ko.b(this, s10);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void a(S10 s10, Set set, Map map) {
        if (this == s10) {
            return;
        }
        for (AbstractC2412ko abstractC2412ko : h0()) {
            if (set.contains(abstractC2412ko)) {
                c(abstractC2412ko);
                abstractC2412ko.b(this, s10);
            }
        }
        Set setKeySet = map.keySet();
        for (ML ml : g0()) {
            if (setKeySet.contains(ml)) {
                long jCount = ml.k0().stream().filter(new Predicate() { // from class: com.android.tools.r8.internal.S10$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return this.f$0.e((S10) obj);
                    }
                }).count();
                InterfaceC0967Er interfaceC0967Er = (InterfaceC0967Er) map.get(ml);
                if (jCount == interfaceC0967Er.size()) {
                    b(ml);
                }
                InterfaceC0993Fr it = interfaceC0967Er.iterator();
                while (it.hasNext()) {
                    int iIntValue = ((Integer) it.next()).intValue();
                    if (!p && ml.c(iIntValue) != this) {
                        throw new AssertionError();
                    }
                    ml.a(iIntValue, s10);
                }
            }
        }
        Q10 q10 = this.m;
        if (q10 != null) {
            Iterator it2 = q10.b.iterator();
            while (it2.hasNext()) {
                AbstractC2412ko abstractC2412ko2 = (AbstractC2412ko) it2.next();
                if (set.contains(abstractC2412ko2)) {
                    abstractC2412ko2.a(this, s10);
                    it2.remove();
                }
            }
        }
    }

    public final void a(com.android.tools.r8.ir.regalloc.c cVar) {
        if (!p && this.i != null) {
            throw new AssertionError();
        }
        this.i = cVar;
    }

    public void a(boolean z) {
        int i;
        if (!p && (i = this.j) != -1) {
            if ((i > 0) != z) {
                throw new AssertionError();
            }
        }
        this.j = z ? 1 : 0;
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final int compareTo(S10 s10) {
        return Integer.compare(this.a, s10.a);
    }

    public final B1 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        if (!c0702m.q()) {
            return C2892r10.F();
        }
        if (w().N().f()) {
            return c0702m.b().a();
        }
        S10 s10M = m();
        if (s10M.i()) {
            return C2892r10.F();
        }
        return s10M.b.a(c0702m.h0(), o5Var);
    }

    public final void a(C1910eI c1910eI) {
        this.l = new com.android.tools.r8.utils.W1(c1910eI.H(), c1910eI.G());
    }

    public final void a(AbstractC3472z00 abstractC3472z00) {
        if (!p && abstractC3472z00 == null) {
            throw new AssertionError();
        }
        this.n = abstractC3472z00;
    }

    public final void a(C0702m c0702m, AbstractC3472z00 abstractC3472z00) {
        if (p || !((c0702m.Y().n1.X || c0702m.q()) && this.n.c(abstractC3472z00, c0702m))) {
            a(abstractC3472z00);
        } else {
            throw new AssertionError("During NARROWING, " + this.n + " < " + abstractC3472z00 + " at " + (i() ? l().m0() : this.b.toString()));
        }
    }

    public AbstractC3472z00 a(C0702m c0702m) {
        AbstractC3472z00 abstractC3472z00O;
        S10 s10M = m();
        if (s10M.i()) {
            if (!p && a(new Predicate() { // from class: com.android.tools.r8.internal.S10$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return S10.c((S10) obj);
                }
            }) != null) {
                throw new AssertionError();
            }
            AbstractC3472z00 abstractC3472z00A = s10M.a(c0702m);
            return (w().I() && w().x()) ? abstractC3472z00A.d().P() : abstractC3472z00A;
        }
        S10 s10A = a(new Predicate() { // from class: com.android.tools.r8.internal.S10$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return S10.d((S10) obj);
            }
        });
        if (s10A != null) {
            abstractC3472z00O = s10A.b.w().J2().a().o();
            if (!p && !abstractC3472z00O.b(this.n, (C0702m<?>) c0702m)) {
                throw new AssertionError(this.n + " < " + abstractC3472z00O);
            }
        } else {
            abstractC3472z00O = this.n;
        }
        if (!this.n.x() || !abstractC3472z00O.F()) {
            return abstractC3472z00O;
        }
        if (p || abstractC3472z00O.I()) {
            return abstractC3472z00O.d().P();
        }
        throw new AssertionError();
    }

    public final C1229Oa a(C0702m c0702m, AbstractC3472z00 abstractC3472z00, WH wh) {
        C1229Oa c1229OaD;
        if (abstractC3472z00 == null) {
            abstractC3472z00 = w();
        }
        if (abstractC3472z00.w()) {
            C1229Oa c1229OaB = abstractC3472z00.b();
            AbstractC0727q0 abstractC0727q0C = c0702m.c(c1229OaB.R());
            if (abstractC0727q0C != null && abstractC0727q0C.c(c0702m)) {
                return c1229OaB.a(c1229OaB.N().b(wh));
            }
        }
        S10 s10M = m();
        if (s10M.i()) {
            return null;
        }
        AbstractC2412ko abstractC2412koQ = s10M.q();
        if (abstractC2412koQ.p2()) {
            C0767v2 c0767v2J2 = abstractC2412koQ.t0().J2();
            AbstractC0727q0 abstractC0727q0C2 = c0702m.c(c0767v2J2);
            if (abstractC0727q0C2 == null || abstractC0727q0C2.isInterface()) {
                return null;
            }
            if (p || !wh.d()) {
                return AbstractC3472z00.a(c0767v2J2, WH.b(), (C0702m<?>) c0702m).b();
            }
            throw new AssertionError();
        }
        S10 s10A = a(new Predicate() { // from class: com.android.tools.r8.internal.S10$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return S10.b((S10) obj);
            }
        });
        if (s10A == null || (c1229OaD = s10A.q().w().J2().a().d()) == null) {
            return null;
        }
        C1229Oa c1229OaC = c1229OaD.a(c1229OaD.N().b(w().N()));
        if (p || c1229OaC.N().a(wh) == wh) {
            return c1229OaC;
        }
        throw new AssertionError();
    }
}
