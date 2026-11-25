package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.shaking.C3804u;
import com.android.tools.r8.utils.AbstractC3962s0;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2409kl implements InterfaceC2943rl, T10 {
    static final /* synthetic */ boolean l = true;
    private final com.android.tools.r8.graph.o5 a;
    private final CE.a b;
    public final AbstractC1915eM c;
    public LinkedList<C3332x4> d;
    public final C2145hI e;
    public final C2145hI f;
    private int g = 0;
    private int h = 0;
    private final C3155ul i;
    private final com.android.tools.r8.utils.A1 j;
    public final Origin k;

    public C2409kl(com.android.tools.r8.utils.A1 a1, com.android.tools.r8.graph.o5 o5Var, AbstractC1915eM abstractC1915eM, LinkedList<C3332x4> linkedList, C2145hI c2145hI, C2145hI c2145hI2, C3155ul c3155ul, Origin origin, CE.a aVar) {
        boolean z = l;
        if (!z && c3155ul == null) {
            throw new AssertionError();
        }
        if (!z && a1 == null) {
            throw new AssertionError();
        }
        if (!z && linkedList.size() != c2145hI2.b()) {
            throw new AssertionError();
        }
        if (!z && abstractC1915eM == null) {
            throw new AssertionError();
        }
        this.j = a1;
        this.b = aVar;
        this.a = o5Var;
        this.c = abstractC1915eM;
        this.d = linkedList;
        this.e = c2145hI;
        this.f = c2145hI2;
        this.i = c3155ul;
        this.k = origin;
    }

    private void H() {
        e(f(new Predicate() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2409kl.c((S10) obj);
            }
        }));
    }

    private void e(Predicate predicate) {
        for (C3332x4 c3332x4 : this.d) {
            for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
                if (abstractC2412ko.d1() && !l && !predicate.test(abstractC2412ko.b())) {
                    throw new AssertionError();
                }
            }
            for (ML ml : c3332x4.x()) {
                if (!l && !predicate.test(ml)) {
                    throw new AssertionError();
                }
            }
        }
    }

    private void g() {
        Set setA = com.android.tools.r8.utils.T2.a((Iterable) this.d);
        IdentityHashMap identityHashMap = new IdentityHashMap(this.d.size());
        IdentityHashMap identityHashMap2 = new IdentityHashMap(this.d.size());
        final Function function = new Function() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2409kl.a((Collection) obj);
            }
        };
        for (C3332x4 c3332x4 : this.d) {
            Collection<?> collection = (Collection) identityHashMap.computeIfAbsent(c3332x4, new Function() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C2409kl.a(function, (C3332x4) obj);
                }
            });
            Collection<?> collection2 = (Collection) identityHashMap2.computeIfAbsent(c3332x4, new Function() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda7
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C2409kl.b(function, (C3332x4) obj);
                }
            });
            boolean z = l;
            if (!z && collection.size() != c3332x4.y().size()) {
                throw new AssertionError();
            }
            if (!z && collection2.size() != c3332x4.z().size()) {
                throw new AssertionError();
            }
            if (!z && !setA.containsAll(collection)) {
                throw new AssertionError();
            }
            if (!z && !setA.containsAll(collection2)) {
                throw new AssertionError();
            }
            Iterator<?> it = collection2.iterator();
            while (it.hasNext()) {
                Collection collection3 = (Collection) identityHashMap.computeIfAbsent((C3332x4) it.next(), new Function() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda8
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return C2409kl.c(function, (C3332x4) obj);
                    }
                });
                if (!l && !collection3.contains(c3332x4)) {
                    throw new AssertionError();
                }
            }
            Iterator<?> it2 = collection.iterator();
            while (it2.hasNext()) {
                Collection collection4 = (Collection) identityHashMap2.computeIfAbsent((C3332x4) it2.next(), new Function() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda9
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return C2409kl.d(function, (C3332x4) obj);
                    }
                });
                if (!l && !collection4.contains(c3332x4)) {
                    throw new AssertionError();
                }
            }
        }
    }

    public final int A() {
        int i = 1;
        if (!l) {
            for (int i2 = 1; (this.g & i2) == i2; i2 <<= 1) {
                if (i2 > 1073741824) {
                    throw new AssertionError();
                }
            }
        }
        while (true) {
            int i3 = this.g;
            if ((i3 & i) != i) {
                int i4 = i3 | i;
                this.g = i4;
                boolean z = l;
                if (!z && (i4 & i) == 0) {
                    throw new AssertionError();
                }
                if (!z) {
                    for (C3332x4 c3332x4 : this.d) {
                        if (!l && c3332x4.c(i)) {
                            throw new AssertionError();
                        }
                    }
                }
                return i;
            }
            if (!l && i > 1073741824) {
                throw new AssertionError();
            }
            i <<= 1;
        }
    }

    public final void B() {
        ArrayList arrayList = new ArrayList();
        for (C3332x4 c3332x4 : this.d) {
            List<C3332x4> listR = c3332x4.r();
            if (listR.size() > 1) {
                if (!l) {
                    AbstractC2412ko abstractC2412koK = c3332x4.k();
                    abstractC2412koK.getClass();
                    if (abstractC2412koK instanceof AF) {
                        throw new AssertionError();
                    }
                }
                for (int i = 0; i < listR.size(); i++) {
                    C3332x4 c3332x5 = listR.get(i);
                    if (!c3332x5.F()) {
                        C3332x4 c3332x4A = C3332x4.a(this.f.a(), c3332x5.m().Y0(), this.i, c3332x4);
                        arrayList.add(c3332x4A);
                        c3332x5.b(c3332x4, c3332x4A);
                        c3332x4A.r().add(c3332x5);
                        listR.set(i, c3332x4A);
                    }
                }
            }
        }
        this.d.addAll(arrayList);
    }

    public Stream<AbstractC2412ko> C() {
        return AbstractC1699bX.a(r());
    }

    public final AbstractC1639am D() {
        ArrayList arrayList = new ArrayList(this.d.size());
        HashSet hashSet = new HashSet(this.d.size());
        ArrayDeque arrayDeque = new ArrayDeque(this.d.size());
        arrayDeque.addLast(j());
        while (!arrayDeque.isEmpty()) {
            Object objRemoveLast = arrayDeque.removeLast();
            if (objRemoveLast instanceof C2255il) {
                arrayList.add(((C2255il) objRemoveLast).a);
            } else {
                C3332x4 c3332x4 = (C3332x4) objRemoveLast;
                if (!hashSet.contains(c3332x4)) {
                    hashSet.add(c3332x4);
                    arrayDeque.addLast(new C2255il(c3332x4));
                    for (int size = c3332x4.z().size() - 1; size >= 0; size--) {
                        arrayDeque.addLast(c3332x4.z().get(size));
                    }
                }
            }
        }
        C1507Xl c1507XlG = AbstractC1639am.g();
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            c1507XlG.a((C3332x4) arrayList.get(size2));
        }
        AbstractC1639am abstractC1639amA = c1507XlG.a();
        if (!this.j.n1.Q) {
            return abstractC1639amA;
        }
        C1507Xl c1507XlG2 = AbstractC1639am.g();
        AbstractC2963s10 it = abstractC1639amA.iterator();
        while (it.hasNext()) {
            C3332x4 c3332x5 = (C3332x4) it.next();
            AbstractC2412ko abstractC2412koK = c3332x5.k();
            abstractC2412koK.getClass();
            if (!(abstractC2412koK instanceof AF)) {
                c1507XlG2.a(c3332x5);
            }
        }
        AbstractC2963s10 it2 = abstractC1639amA.iterator();
        while (it2.hasNext()) {
            C3332x4 c3332x6 = (C3332x4) it2.next();
            AbstractC2412ko abstractC2412koK2 = c3332x6.k();
            abstractC2412koK2.getClass();
            if (abstractC2412koK2 instanceof AF) {
                c1507XlG2.a(c3332x6);
            }
        }
        return c1507XlG2.a();
    }

    public final void E() {
        C3332x4 c3332x4;
        AbstractC1639am abstractC1639amD = D();
        int iA = A();
        LinkedList<C3332x4> linkedList = new LinkedList<>();
        AbstractC2963s10 it = abstractC1639amD.iterator();
        while (it.hasNext()) {
            C3332x4 c3332x5 = (C3332x4) it.next();
            if (!c3332x5.c(iA)) {
                c3332x5.e(iA);
                linkedList.add(c3332x5);
                C3332x4 c3332x4J2 = c3332x5.m().J2();
                while (true) {
                    C3332x4 c3332x6 = c3332x4J2;
                    c3332x4 = c3332x5;
                    c3332x5 = c3332x6;
                    if (c3332x5 == null || c3332x5.c(iA)) {
                        break;
                    }
                    c3332x5.e(iA);
                    linkedList.add(c3332x5);
                    c3332x4J2 = c3332x5.m().J2();
                }
                if (c3332x5 != null) {
                    C3332x4 c3332x4A = C3332x4.a(this.f.a(), c3332x4.m().Y0(), this.i, c3332x5);
                    c3332x4.m().b(c3332x4A);
                    c3332x4A.r().add(c3332x4);
                    c3332x5.a(c3332x4, c3332x4A);
                    c3332x4A.e(iA);
                    linkedList.add(c3332x4A);
                }
            }
        }
        this.d = linkedList;
        b(iA);
        if (!l && this.g != 0) {
            throw new AssertionError();
        }
    }

    public final void F() {
        e(f(new Predicate() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2409kl.a((S10) obj);
            }
        }));
    }

    public final void G() {
        e(f(new Predicate() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2409kl.b((S10) obj);
            }
        }));
    }

    public final void I() {
        for (C3332x4 c3332x4 : this.d) {
            List<C3332x4> listY = c3332x4.y();
            if (listY.size() > 1) {
                for (C3332x4 c3332x5 : listY) {
                    boolean z = l;
                    if (!z && !c3332x5.F()) {
                        throw new AssertionError();
                    }
                    if (!z && c3332x5.z().get(0) != c3332x4) {
                        throw new AssertionError();
                    }
                }
            }
            if (c3332x4.D()) {
                for (C3332x4 c3332x6 : c3332x4.n().c()) {
                    boolean z2 = l;
                    if (!z2 && c3332x6.y().size() != 1) {
                        throw new AssertionError();
                    }
                    if (!z2 && c3332x6.y().get(0) != c3332x4) {
                        throw new AssertionError();
                    }
                }
            }
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2475ld
    public final com.android.tools.r8.utils.D3 a(Object obj, Object obj2, BiFunction biFunction) {
        return ((C3332x4) obj).a(obj2, biFunction);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2475ld
    public final com.android.tools.r8.utils.D3 b(Object obj, AbstractC3327x1 abstractC3327x1, BiFunction biFunction) {
        com.android.tools.r8.utils.D3 d3B = com.android.tools.r8.utils.D3.b(abstractC3327x1);
        Iterator<AbstractC2412ko> it = ((C3332x4) obj).p().iterator();
        while (it.hasNext()) {
            d3B = (com.android.tools.r8.utils.D3) biFunction.apply(it.next(), d3B.c().i());
            if (d3B.f()) {
                break;
            }
        }
        return d3B;
    }

    public final void c(C0702m c0702m) {
        boolean z = l;
        if (!z) {
            b(c0702m, true);
        }
        if (!z) {
            a(c0702m, true);
        }
        if (!z) {
            Set<S10> setC = OU.c();
            for (C3332x4 c3332x4 : this.d) {
                int size = c3332x4.y().size();
                for (ML ml : c3332x4.x()) {
                    boolean z2 = l;
                    if (!z2 && ml.l0()) {
                        throw new AssertionError();
                    }
                    if (!z2 && ml.k0().size() != size) {
                        throw new AssertionError();
                    }
                    setC.add(ml);
                    for (S10 s10 : ml.k0()) {
                        setC.add(s10);
                        boolean z3 = l;
                        if (!z3 && !s10.g0().contains(ml)) {
                            throw new AssertionError();
                        }
                        if (!z3 && ml.A() && ml.s() != s10.s()) {
                            throw new AssertionError();
                        }
                        if (!z3 && !s10.i() && !s10.b.a1()) {
                            throw new AssertionError();
                        }
                    }
                }
                for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
                    boolean z4 = l;
                    if (!z4 && abstractC2412ko.h() != c3332x4) {
                        throw new AssertionError();
                    }
                    S10 s10B = abstractC2412ko.b();
                    if (s10B != null) {
                        setC.add(s10B);
                        if (!z4 && s10B.b != abstractC2412ko) {
                            throw new AssertionError();
                        }
                    }
                    for (S10 s11 : abstractC2412ko.b) {
                        setC.add(s11);
                        if (!l && !s11.h0().contains(abstractC2412ko)) {
                            throw new AssertionError();
                        }
                    }
                    for (S10 s12 : abstractC2412ko.S0()) {
                        setC.add(s12);
                        if (!l && !s12.g().contains(abstractC2412ko)) {
                            throw new AssertionError();
                        }
                    }
                }
            }
            for (S10 s13 : setC) {
                boolean z5 = l;
                if (!z5 && !z5) {
                    if (s13.i()) {
                        ML mlL = s13.l();
                        if (!z5 && !mlL.h().x().contains(mlL)) {
                            throw new AssertionError();
                        }
                    } else {
                        S10 s10B2 = s13.b.b();
                        if (!z5 && s10B2 != s13 && (!(s13 instanceof C2617nW) || !Arrays.asList(((C2708oW) s10B2).j0()).contains(s13))) {
                            throw new AssertionError();
                        }
                    }
                }
                if (!z5) {
                    for (AbstractC2412ko abstractC2412ko2 : s13.h0()) {
                        if (!l && !abstractC2412ko2.b.contains(s13)) {
                            throw new AssertionError();
                        }
                    }
                    for (ML ml2 : s13.g0()) {
                        boolean z6 = l;
                        if (!z6 && !ml2.k0().contains(s13)) {
                            throw new AssertionError();
                        }
                        if (!z6 && !ml2.h().x().contains(ml2)) {
                            throw new AssertionError();
                        }
                    }
                    if (s13.A()) {
                        for (AbstractC2412ko abstractC2412ko3 : s13.g()) {
                            if (!l && !abstractC2412ko3.S0().contains(s13)) {
                                throw new AssertionError();
                            }
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        if (!l) {
            for (C3332x4 c3332x5 : this.d) {
                if (c3332x5.D()) {
                    if (!l && c3332x5 == j()) {
                        throw new AssertionError();
                    }
                    for (C3332x4 c3332x6 : c3332x5.n().c()) {
                        if (!l && c3332x6.y().size() != 1) {
                            throw new AssertionError();
                        }
                    }
                    boolean z7 = false;
                    for (AbstractC2412ko abstractC2412ko4 : c3332x5.p()) {
                        if (abstractC2412ko4.g()) {
                            if (!l && z7) {
                                throw new AssertionError();
                            }
                            z7 = true;
                        } else if (!l && z7 && !abstractC2412ko4.l1()) {
                            throw new AssertionError();
                        }
                    }
                }
            }
        }
        if (!l) {
            for (C3332x4 c3332x7 : this.d) {
                List<C3332x4> listY = c3332x7.y();
                if (listY.size() > 1) {
                    if (!(c3332x7.k() instanceof AF)) {
                        for (int i = 0; i < listY.size(); i++) {
                            if (!listY.get(i).F()) {
                                if (!l) {
                                    throw new AssertionError();
                                }
                            }
                        }
                    } else if (!l) {
                        throw new AssertionError();
                    }
                    throw new AssertionError();
                }
            }
        }
        if (l) {
            return;
        }
        H();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2475ld
    public final com.android.tools.r8.utils.D3 d(Object obj, BiFunction biFunction) {
        return ((C3332x4) obj).a(biFunction);
    }

    public final void f() {
        ((Map) this.d.stream().collect(Collectors.groupingBy(new Function() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((C3332x4) obj).v());
            }
        }, Collectors.counting()))).forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((Integer) obj, (Long) obj2);
            }
        });
    }

    public com.android.tools.r8.graph.o5 h() {
        return this.a;
    }

    public final C0978Fc i() {
        return new C0978Fc(a((com.android.tools.r8.graph.X) null, AbstractC3472z00.m()), 0L);
    }

    public C3332x4 j() {
        return this.d.getFirst();
    }

    public final CE.a k() {
        return this.b;
    }

    public final int l() {
        return this.f.b();
    }

    public final C3406y2 m() {
        C3332x4 c3332x4J = j();
        int iA = (com.android.tools.r8.utils.M.a(!h().e().E0()) + h().getReference().F0()) - 1;
        c3332x4J.getClass();
        C3479z4 c3479z4 = new C3479z4(iA, c3332x4J);
        C3406y2 c3406y2Q = ((AbstractC2412ko) c3479z4.next()).q();
        boolean z = l;
        if (!z && c3406y2Q == null) {
            throw new AssertionError();
        }
        if (z || !c3479z4.o().m1()) {
            return c3406y2Q;
        }
        throw new AssertionError();
    }

    public final int n() {
        return this.h;
    }

    public final S10 o() {
        if (u().f.o()) {
            return null;
        }
        AbstractC2412ko abstractC2412ko = (AbstractC2412ko) j().R().a(new A4$$ExternalSyntheticLambda3());
        boolean z = l;
        if (!z && abstractC2412ko == null) {
            throw new AssertionError();
        }
        S10 s10B = abstractC2412ko.q().b();
        if (z || s10B.Q()) {
            return s10B;
        }
        throw new AssertionError();
    }

    public final Set p() {
        Set setC = OU.c();
        int iA = A();
        b(iA, j());
        for (C3332x4 c3332x4 : this.d) {
            if (!c3332x4.c(iA)) {
                setC.add(c3332x4);
            }
        }
        b(iA);
        return setC;
    }

    public InterfaceC2488lo q() {
        return new C2485ll(this);
    }

    public Iterable<AbstractC2412ko> r() {
        return new Iterable() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda21
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return this.f$0.q();
            }
        };
    }

    public B4 s() {
        return new B4(this);
    }

    public final C3155ul t() {
        return this.i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("blocks:\n");
        Iterator<C3332x4> it = this.d.iterator();
        while (it.hasNext()) {
            sb.append(it.next().b0());
            sb.append("\n");
        }
        return sb.toString();
    }

    public com.android.tools.r8.graph.W0 u() {
        return this.a.e();
    }

    public final AbstractC1639am v() {
        AbstractC1639am abstractC1639amD = D();
        AbstractC2963s10 it = abstractC1639amD.iterator();
        while (it.hasNext()) {
            this.h = ((C3332x4) it.next()).f(this.h);
        }
        return abstractC1639amD;
    }

    public final int w() {
        for (AbstractC2412ko abstractC2412ko : r()) {
            if (abstractC2412ko.W0() == -1) {
                abstractC2412ko.b(this.h);
                this.h += 2;
            }
        }
        return this.h;
    }

    public final void x() {
        C3332x4 c3332x4J = j();
        B4 b4S = s();
        while (b4S.hasNext()) {
            C3332x4 next = b4S.next();
            InterfaceC2641no interfaceC2641noA = next.a(this);
            boolean z = false;
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko next2 = interfaceC2641noA.next();
                boolean zG = next2.g();
                if ((z && !next2.l1()) || (zG && next == c3332x4J)) {
                    interfaceC2641noA.previous();
                    interfaceC2641noA.a(this, b4S);
                    b4S.previous();
                    break;
                } else if (zG) {
                    z = true;
                }
            }
            if (z) {
                List<C3332x4> listZ = next.z();
                if (listZ.size() == 1 && ((C3332x4) com.android.tools.r8.utils.V1.a(listZ)).y().size() > 1) {
                    C3332x4 c3332x4A = next.a(this.f.a(), true);
                    C1296Qj c1296Qj = new C1296Qj();
                    c1296Qj.a(next);
                    c1296Qj.b(AbstractC1915eM.u());
                    c3332x4A.a(this).add(c1296Qj);
                    b4S.add(c3332x4A);
                }
            }
        }
        if (!l && !this.d.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2409kl.a((C3332x4) obj);
            }
        })) {
            throw new AssertionError();
        }
    }

    public final boolean y() {
        return a((C2102gl) null, (Set) null);
    }

    public final AbstractC1048Hm z() {
        C0862Bm c0862BmG = AbstractC1048Hm.g();
        int iA = A();
        b(iA, j());
        B4 b4S = s();
        while (b4S.hasNext()) {
            C3332x4 next = b4S.next();
            if (!next.c(iA)) {
                c0862BmG.a(next.b());
                b4S.remove();
            }
        }
        b(iA);
        return c0862BmG.a();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2475ld
    public final com.android.tools.r8.utils.D3 a(Object obj, com.android.tools.r8.utils.G3 g3, Object obj2) {
        return ((C3332x4) obj).a(g3, obj2);
    }

    public final IdentityHashMap d() {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        ArrayDeque arrayDeque = new ArrayDeque();
        AbstractC1639am abstractC1639amD = D();
        arrayDeque.addAll(abstractC1639amD.j());
        while (!arrayDeque.isEmpty()) {
            C3332x4 c3332x4 = (C3332x4) arrayDeque.poll();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Set setC = OU.c();
            ArrayDeque arrayDeque2 = new ArrayDeque();
            Set setC2 = c3332x4.n().c();
            for (C3332x4 c3332x5 : c3332x4.z()) {
                C2329jl c2329jl = (C2329jl) identityHashMap.get(c3332x5);
                if (c2329jl != null) {
                    com.android.tools.r8.utils.T1.a(linkedHashSet, c2329jl.a);
                    setC.addAll(c2329jl.b);
                    if (setC2.contains(c3332x5)) {
                        if (!l && c2329jl.c.size() != 0) {
                            throw new AssertionError();
                        }
                    } else {
                        if (!l && !arrayDeque2.isEmpty()) {
                            throw new AssertionError();
                        }
                        arrayDeque2 = new ArrayDeque(c2329jl.c);
                    }
                }
                int iIndexOf = c3332x5.y().indexOf(c3332x4);
                for (ML ml : c3332x5.x()) {
                    S10 s10C = ml.c(iIndexOf);
                    if (s10C.T()) {
                        arrayDeque2.addLast(s10C);
                    } else {
                        linkedHashSet.add(s10C);
                        if (!ml.A()) {
                            continue;
                        } else {
                            if (!l && ml.s() != s10C.s()) {
                                throw new AssertionError();
                            }
                            setC.add(s10C);
                        }
                    }
                }
            }
            if (!l && !arrayDeque2.isEmpty() && c3332x4.z().size() - setC2.size() != 1) {
                throw new AssertionError();
            }
            InterfaceC2641no interfaceC2641noA = c3332x4.a(this, c3332x4.p().size());
            while (interfaceC2641noA.hasPrevious()) {
                AbstractC2412ko abstractC2412koPrevious = interfaceC2641noA.previous();
                S10 s10B = abstractC2412koPrevious.b();
                if (s10B != null) {
                    if (s10B instanceof C2617nW) {
                        S10 s10 = (S10) arrayDeque2.removeLast();
                        if (!l && s10 != s10B) {
                            throw new AssertionError();
                        }
                    } else if (s10B instanceof C2708oW) {
                        C2617nW[] c2617nWArrJ0 = ((C2708oW) s10B).j0();
                        for (int length = c2617nWArrJ0.length - 1; length >= 0; length--) {
                            S10 s11 = (S10) arrayDeque2.removeLast();
                            if (!l && s11 != c2617nWArrJ0[length]) {
                                throw new AssertionError();
                            }
                        }
                    } else {
                        linkedHashSet.remove(s10B);
                        if (!l && !s10B.A() && setC.contains(s10B)) {
                            throw new AssertionError();
                        }
                        if (s10B.A()) {
                            setC.remove(s10B);
                        }
                    }
                }
                for (S10 s12 : (ArrayList) abstractC2412koPrevious.h1()) {
                    if (s12.Y()) {
                        linkedHashSet.add(s12);
                    } else if (s12.T()) {
                        arrayDeque2.addLast(s12);
                    }
                }
                if (!abstractC2412koPrevious.S0().isEmpty()) {
                    ArrayList arrayList = new ArrayList(abstractC2412koPrevious.S0());
                    arrayList.sort(new kl$$ExternalSyntheticLambda10());
                    boolean z = l;
                    if (!z && !arrayList.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda12
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return ((S10) obj).Y();
                        }
                    })) {
                        throw new AssertionError();
                    }
                    if (!z && !arrayList.stream().allMatch(new kl$$ExternalSyntheticLambda13())) {
                        throw new AssertionError();
                    }
                    linkedHashSet.addAll(arrayList);
                    setC.addAll(arrayList);
                }
            }
            for (ML ml2 : c3332x4.x()) {
                if (ml2.T()) {
                    arrayDeque2.remove(ml2);
                } else {
                    linkedHashSet.remove(ml2);
                }
                if (!l && !ml2.A() && setC.contains(ml2)) {
                    throw new AssertionError();
                }
                if (ml2.A()) {
                    setC.remove(ml2);
                }
            }
            C2329jl c2329jl2 = new C2329jl(linkedHashSet, setC, arrayDeque2);
            C2329jl c2329jl3 = (C2329jl) identityHashMap.put(c3332x4, c2329jl2);
            if (c2329jl3 == null || !c2329jl3.equals(c2329jl2)) {
                for (C3332x4 c3332x6 : c3332x4.y()) {
                    if (!arrayDeque.contains(c3332x6)) {
                        arrayDeque.add(c3332x6);
                    }
                }
            }
        }
        if (l || ((C2329jl) identityHashMap.get(abstractC1639amD.get(0))).a()) {
            return identityHashMap;
        }
        throw new AssertionError("Unexpected values live at entry to first block: " + ((C2329jl) identityHashMap.get(abstractC1639amD.get(0))).a);
    }

    private static Predicate f(final Predicate predicate) {
        return new Predicate() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2409kl.a(predicate, (S10) obj);
            }
        };
    }

    @Override // com.android.tools.r8.internal.InterfaceC2475ld
    public final com.android.tools.r8.utils.D3 a(Object obj, AbstractC3327x1 abstractC3327x1, BiFunction biFunction) {
        return ((C3332x4) obj).a(abstractC3327x1, biFunction);
    }

    public final void b(Consumer consumer) {
        consumer.accept(this.b);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2475ld
    public final Object a() {
        return this.d.getFirst();
    }

    public void b(Collection<C3332x4> collection) {
        if (collection.isEmpty()) {
            return;
        }
        this.d.removeAll(collection);
    }

    public final boolean a(Consumer consumer) {
        for (C3332x4 c3332x4 : this.d) {
            if (c3332x4.D()) {
                return true;
            }
            if (c3332x4.m().N1()) {
                C1015Gl c1015GlQ = c3332x4.m().Q();
                consumer.accept(c1015GlQ.O2());
                if (!c1015GlQ.N2()) {
                    consumer.accept(c1015GlQ.P2());
                }
            } else if (c3332x4.m().A2()) {
                consumer.accept(c3332x4.m().K0().value());
            }
        }
        return false;
    }

    public boolean b(C0702m<?> c0702m) {
        c((C0702m) c0702m);
        if (l) {
            return true;
        }
        F();
        return true;
    }

    public List<C3332x4> e() {
        C1507Xl c1507XlG = AbstractC1639am.g();
        for (C3332x4 c3332x4 : this.d) {
            if (c3332x4.m().u2()) {
                c1507XlG.a(c3332x4);
            }
        }
        return c1507XlG.a();
    }

    public final void b(C0702m c0702m, boolean z) {
        boolean z2 = l;
        if (!z2 && this.g != 0) {
            throw new AssertionError();
        }
        if (!z2) {
            f();
        }
        if (!z2) {
            g();
        }
        if (!z2) {
            for (C3332x4 c3332x4 : this.d) {
                if (!l) {
                    c3332x4.f();
                }
            }
        }
        boolean z3 = l;
        if (!z3) {
            a(c0702m, z);
        }
        if (!z3) {
            for (AbstractC2412ko abstractC2412ko : r()) {
                if (abstractC2412ko.k1()) {
                    if (!l && (!this.i.a() || !this.i.c())) {
                        throw new AssertionError("IR metadata should indicate that code has an add");
                    }
                } else if (abstractC2412ko instanceof X1) {
                    if (!l && (!this.i.b() || !this.i.c())) {
                        throw new AssertionError("IR metadata should indicate that code has an and");
                    }
                } else if (abstractC2412ko.w1()) {
                    if (!l && !this.i.f()) {
                        throw new AssertionError("IR metadata should indicate that code has a check-cast");
                    }
                } else if (abstractC2412ko.B1()) {
                    if (!l && !this.i.g()) {
                        throw new AssertionError("IR metadata should indicate that code has a const-number");
                    }
                } else if (abstractC2412ko instanceof C1033Hc) {
                    if (!l && !this.i.h()) {
                        throw new AssertionError("IR metadata should indicate that code has a const-string");
                    }
                } else if (abstractC2412ko instanceof C1343Sd) {
                    if (!l && !this.i.i()) {
                        throw new AssertionError("IR metadata should indicate that code has a debug position");
                    }
                } else if (abstractC2412ko instanceof C2477lf) {
                    if (!l && !this.i.j()) {
                        throw new AssertionError("IR metadata should indicate that code has a dex-item-based-const-string");
                    }
                } else if (abstractC2412ko instanceof C3379xf) {
                    if (!l && (!this.i.k() || !this.i.c())) {
                        throw new AssertionError("IR metadata should indicate that code has a div");
                    }
                } else if (abstractC2412ko instanceof C1189Mn) {
                    if (!l && !this.i.n()) {
                        throw new AssertionError("IR metadata should indicate that code has an instance-get");
                    }
                } else if (abstractC2412ko.S1()) {
                    if (!l && !this.i.p()) {
                        throw new AssertionError("IR metadata should indicate that code has an instance-put");
                    }
                } else if (abstractC2412ko instanceof C1641ao) {
                    if (!l && !this.i.o()) {
                        throw new AssertionError("IR metadata should indicate that code has an instance-of");
                    }
                } else if (abstractC2412ko.T1()) {
                    if (!l && !this.i.q()) {
                        throw new AssertionError("IR metadata should indicate that code has an int-switch");
                    }
                } else if (abstractC2412ko.W1()) {
                    if (!l && !this.i.r()) {
                        throw new AssertionError("IR metadata should indicate that code has an invoke-direct");
                    }
                } else if (abstractC2412ko instanceof C1248Os) {
                    if (!l && !this.i.s()) {
                        throw new AssertionError("IR metadata should indicate that code has an invoke-interface");
                    }
                } else if (abstractC2412ko instanceof C1433Us) {
                    if (!l && !this.i.v()) {
                        throw new AssertionError("IR metadata should indicate that code has an invoke-polymorphic");
                    }
                } else if (abstractC2412ko instanceof C1721bt) {
                    if (!l && !this.i.w()) {
                        throw new AssertionError("IR metadata should indicate that code has an invoke-static");
                    }
                } else if (abstractC2412ko instanceof C1798ct) {
                    if (!l && !this.i.x()) {
                        throw new AssertionError("IR metadata should indicate that code has an invoke-super");
                    }
                } else if (abstractC2412ko.f2()) {
                    if (!l && !this.i.y()) {
                        throw new AssertionError("IR metadata should indicate that code has an invoke-virtual");
                    }
                } else if (abstractC2412ko instanceof NK) {
                    if (!l && (!this.i.C() || !this.i.c())) {
                        throw new AssertionError("IR metadata should indicate that code has an or");
                    }
                } else if (abstractC2412ko instanceof C3344xF) {
                    if (!l && !this.i.z()) {
                        throw new AssertionError("IR metadata should indicate that code has a monitor instruction");
                    }
                } else if (abstractC2412ko instanceof CF) {
                    if (!l && (!this.i.A() || !this.i.c())) {
                        throw new AssertionError("IR metadata should indicate that code has a mul");
                    }
                } else if (abstractC2412ko.p2()) {
                    if (!l && !this.i.B()) {
                        throw new AssertionError("IR metadata should indicate that code has a new-instance");
                    }
                } else if (abstractC2412ko instanceof US) {
                    if (!l && (!this.i.D() || !this.i.c())) {
                        throw new AssertionError("IR metadata should indicate that code has a rem");
                    }
                } else if (abstractC2412ko instanceof TU) {
                    if (!l && (!this.i.E() || !this.i.c())) {
                        throw new AssertionError("IR metadata should indicate that code has a shl");
                    }
                } else if (abstractC2412ko instanceof WU) {
                    if (!l && (!this.i.F() || !this.i.c())) {
                        throw new AssertionError("IR metadata should indicate that code has a shr");
                    }
                } else if (abstractC2412ko instanceof QW) {
                    if (!l && !this.i.G()) {
                        throw new AssertionError("IR metadata should indicate that code has a static-get");
                    }
                } else if (abstractC2412ko.c()) {
                    if (!l && !this.i.H()) {
                        throw new AssertionError("IR metadata should indicate that code has a static-put");
                    }
                } else if (abstractC2412ko.y2()) {
                    if (!l && !this.i.I()) {
                        throw new AssertionError("IR metadata should indicate that code has a string-switch");
                    }
                } else if (abstractC2412ko instanceof C3218vY) {
                    if (!l && (!this.i.J() || !this.i.c())) {
                        throw new AssertionError("IR metadata should indicate that code has a sub");
                    }
                } else if (abstractC2412ko instanceof N10) {
                    if (!l && (!this.i.L() || !this.i.c())) {
                        throw new AssertionError("IR metadata should indicate that code has an ushr");
                    }
                } else if ((abstractC2412ko instanceof P30) && !l && (!this.i.M() || !this.i.c())) {
                    throw new AssertionError("IR metadata should indicate that code has an xor");
                }
            }
        }
        if (l) {
            return;
        }
        for (AbstractC2412ko abstractC2412ko2 : r()) {
            if (abstractC2412ko2.g() && !(abstractC2412ko2 instanceof C1033Hc) && !(abstractC2412ko2 instanceof C2477lf) && abstractC2412ko2.Y0().o() && !abstractC2412ko2.Y0().t()) {
                throw new AssertionError();
            }
        }
    }

    public final void a(C0702m c0702m) {
        final C2836qE c2836qEC = c0702m.c();
        for (AbstractC2412ko abstractC2412ko : r()) {
            S10 s10 = abstractC2412ko.a;
            if (s10 != null && s10.w().w()) {
                C1229Oa c1229OaB = abstractC2412ko.a.w().b();
                boolean z = l;
                if (!z && c2836qEC.a(c1229OaB.R())) {
                    throw new AssertionError("Expected reference to " + c1229OaB.R().L0() + " to be rewritten at instruction " + abstractC2412ko.toString());
                }
                if (!z && c1229OaB.S().a(new BiPredicate() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda18
                    @Override // java.util.function.BiPredicate
                    public final boolean test(Object obj, Object obj2) {
                        return C2409kl.a(c2836qEC, (C0767v2) obj, (Boolean) obj2);
                    }
                })) {
                    throw new AssertionError();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C2836qE c2836qE, C0767v2 c0767v2, Boolean bool) {
        if (l || !c2836qE.a(c0767v2)) {
            return false;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0702m c0702m, C2433l20 c2433l20, C3332x4 c3332x4) {
        c3332x4.a(c0702m, c2433l20);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection a(Collection collection) {
        return collection.size() > 5 ? com.android.tools.r8.utils.T2.a((Iterable) collection) : collection;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection a(Function function, C3332x4 c3332x4) {
        return (Collection) function.apply(c3332x4.y());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Integer num, Long l2) {
        boolean z = l;
        if (!z && l2.longValue() != 1) {
            throw new AssertionError();
        }
        if (!z && l2.longValue() > this.f.b()) {
            throw new AssertionError();
        }
    }

    private void a(C0702m c0702m, boolean z) {
        boolean z2 = true;
        for (C3332x4 c3332x4 : this.d) {
            if (!l) {
                boolean z3 = this.j.W0 || h().a().f(c0702m);
                for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
                    boolean z4 = C3332x4.p;
                    if (!z4) {
                        abstractC2412ko.a(z3);
                    }
                    if (!z4 && abstractC2412ko.h() != c3332x4) {
                        throw new AssertionError();
                    }
                    if (!z4 && abstractC2412ko.m1() && !z2) {
                        throw new AssertionError();
                    }
                    if (!z4) {
                        abstractC2412ko.getClass();
                        if ((abstractC2412ko instanceof C1232Od) && abstractC2412ko.S0().isEmpty()) {
                            throw new AssertionError();
                        }
                    }
                    if (!z4) {
                        abstractC2412ko.getClass();
                        if ((abstractC2412ko instanceof C2179hn) && !C3332x4.a(abstractC2412ko.R(), z)) {
                            throw new AssertionError();
                        }
                    }
                    abstractC2412ko.getClass();
                    if (abstractC2412ko instanceof AF) {
                        if (!z4 && abstractC2412ko != c3332x4.k()) {
                            throw new AssertionError();
                        }
                        for (C3332x4 c3332x5 : c3332x4.y()) {
                            if (!C3332x4.p && !c3332x5.c(c3332x4) && (!c3332x5.Q() || c3332x5.j() != c3332x4)) {
                                throw new AssertionError();
                            }
                        }
                    }
                    if (!abstractC2412ko.m1()) {
                        z2 = false;
                    }
                }
            }
            z2 = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(S10 s10) {
        boolean z = l;
        if (!z && !s10.w().G()) {
            throw new AssertionError();
        }
        if (!z && s10.w().A()) {
            throw new AssertionError();
        }
        if (!z) {
            AbstractC3472z00 abstractC3472z00W = s10.w();
            abstractC3472z00W.getClass();
            if (abstractC3472z00W instanceof G5) {
                throw new AssertionError();
            }
        }
        if (z) {
            return true;
        }
        InterfaceC2729oo interfaceC2729oo = s10.b;
        if (!(interfaceC2729oo instanceof InterfaceC1299Qm) || ((InterfaceC1299Qm) interfaceC2729oo).d().a()) {
            return true;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return !(abstractC2412ko instanceof N3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Predicate predicate, S10 s10) {
        if (s10 instanceof C2708oW) {
            return Stream.of((Object[]) ((C2708oW) s10).j0()).allMatch(predicate);
        }
        return predicate.test(s10);
    }

    public final Iterable a(final Predicate predicate) {
        return new Iterable() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda2
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return this.f$0.c(predicate);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection c(Function function, C3332x4 c3332x4) {
        return (Collection) function.apply(c3332x4.y());
    }

    public final B4 a(int i) {
        return new B4(this, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(S10 s10) {
        if (l || !s10.E() || s10.h0().stream().anyMatch(new Predicate() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2409kl.a((AbstractC2412ko) obj);
            }
        })) {
            return true;
        }
        if ((s10.i() || !s10.b.m1()) && s10.z() && !s10.g().stream().anyMatch(new Predicate() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2409kl.b((AbstractC2412ko) obj);
            }
        }) && s10.a0() <= 0) {
            throw new AssertionError(com.android.tools.r8.utils.Y2.a(System.lineSeparator(), s10.h0()));
        }
        return true;
    }

    public final S10 a(com.android.tools.r8.graph.X x, AbstractC3472z00 abstractC3472z00) {
        return new S10(this.e.a(), abstractC3472z00, x);
    }

    public final C0978Fc a(int i, com.android.tools.r8.graph.X x) {
        return new C0978Fc(a(x, AbstractC3472z00.k()), i);
    }

    public final boolean a(C2102gl c2102gl, Set set) {
        Iterator<C3332x4> it = this.d.iterator();
        boolean zA = false;
        while (it.hasNext()) {
            for (ML ml : new ArrayList(it.next().x())) {
                com.android.tools.r8.utils.O3 o3H = com.android.tools.r8.utils.O3.h(ml);
                while (true) {
                    if (o3H.c()) {
                        ML ml2 = (ML) o3H.f();
                        if (!ml2.E() && !ml2.z()) {
                            o3H.a((Iterable) ml2.g0());
                        } else {
                            zA |= ml.a(c2102gl, set);
                            break;
                        }
                    } else {
                        o3H.b().forEach(new Consumer() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((ML) obj).n0();
                            }
                        });
                        zA = true;
                        break;
                    }
                }
            }
        }
        return zA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection b(Function function, C3332x4 c3332x4) {
        return (Collection) function.apply(c3332x4.z());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(S10 s10) {
        if (l || s10.w().H() || s10.w().d().N() != WH.a()) {
            return true;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return !(abstractC2412ko instanceof N3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Iterator c(Predicate predicate) {
        return com.android.tools.r8.utils.L1.a((Iterator) s(), predicate);
    }

    public final Iterable b(final Predicate predicate) {
        return new Iterable() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda14
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return this.f$0.d(predicate);
            }
        };
    }

    public final Iterator b() {
        return new C2177hl(this);
    }

    public List<S10> c() {
        ArrayList arrayList = new ArrayList();
        InterfaceC2488lo interfaceC2488loR = j().R();
        C3406y2 c3406y2Q = interfaceC2488loR.next().q();
        while (c3406y2Q != null) {
            if (c3406y2Q != null) {
                C3406y2 c3406y2Q2 = interfaceC2488loR.next().q();
                arrayList.add(c3406y2Q.b());
                c3406y2Q = c3406y2Q2;
            } else {
                throw new NoSuchElementException();
            }
        }
        if (l || arrayList.size() == u().getReference().F0() + (!u().f.o() ? 1 : 0)) {
            return arrayList;
        }
        throw new AssertionError();
    }

    public final void b(int i) {
        if (!l && (this.g & i) == 0) {
            throw new AssertionError();
        }
        Iterator<C3332x4> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().a(i);
        }
        this.g = (~i) & this.g;
    }

    public final void a(int i, C3332x4 c3332x4) {
        if (!l && (this.g & i) == 0) {
            throw new AssertionError();
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(c3332x4);
        while (!arrayDeque.isEmpty()) {
            C3332x4 c3332x5 = (C3332x4) arrayDeque.poll();
            if (!c3332x5.c(i)) {
                c3332x5.e(i);
                for (C3332x4 c3332x6 : c3332x5.y()) {
                    if (!c3332x6.c(i)) {
                        arrayDeque.add(c3332x6);
                    }
                }
            }
        }
    }

    private void b(int i, C3332x4 c3332x4) {
        ArrayDeque arrayDequeA = AbstractC3962s0.a(c3332x4);
        if (!l) {
            if ((this.g & i) != 0) {
                Iterator<C3332x4> it = this.d.iterator();
                while (it.hasNext()) {
                    if (!it.next().c(i)) {
                    }
                }
            }
            throw new AssertionError();
        }
        while (!arrayDequeA.isEmpty()) {
            C3332x4 c3332x5 = (C3332x4) arrayDequeA.poll();
            if (!c3332x5.c(i)) {
                c3332x5.e(i);
                for (C3332x4 c3332x6 : c3332x5.z()) {
                    if (!c3332x6.c(i)) {
                        arrayDequeA.add(c3332x6);
                    }
                }
            }
        }
    }

    public final void d(final C0702m c0702m) {
        final C2433l20 c2433l20A = C2433l20.a(c0702m);
        if (c0702m.q()) {
            if (!l) {
                Iterator<C3332x4> it = this.d.iterator();
                while (it.hasNext()) {
                    for (AbstractC2412ko abstractC2412ko : it.next().p()) {
                        abstractC2412ko.getClass();
                        if ((abstractC2412ko instanceof N3) && !l) {
                            abstractC2412ko.w().b(c0702m);
                        }
                    }
                }
            }
            if (!l && !new C3326x00(c0702m.i0(), c2433l20A).a(this)) {
                throw new AssertionError();
            }
        }
        if (!l && !this.d.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.kl$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2409kl.a(c0702m, c2433l20A, (C3332x4) obj);
            }
        })) {
            throw new AssertionError();
        }
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var, C3804u c3804u) {
        if (!l && !c3804u.e().g()) {
            throw new AssertionError();
        }
        Iterator<C3332x4> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().a(o5Var, c3804u);
            if (c3804u.e().f()) {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(C3332x4 c3332x4) {
        Iterator<AbstractC2412ko> it = c3332x4.p().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().g()) {
                i++;
            }
        }
        return i <= 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection d(Function function, C3332x4 c3332x4) {
        return (Collection) function.apply(c3332x4.z());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Iterator d(Predicate predicate) {
        return com.android.tools.r8.utils.L1.a(q(), predicate);
    }
}
