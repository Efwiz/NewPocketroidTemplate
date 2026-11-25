package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.b20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1662b20 {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final com.android.tools.r8.graph.o5 b;
    private final C0722p1 c;
    private final com.android.tools.r8.utils.A1 d;

    public C1662b20(C0702m c0702m, C2409kl c2409kl) {
        this.a = c0702m;
        this.b = c2409kl.h();
        this.c = c0702m.a();
        this.d = c0702m.Y();
    }

    private boolean b(S10 s10) {
        S10 s10M = s10.m();
        return s10M.b(new Predicate() { // from class: com.android.tools.r8.internal.b20$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC2412ko) obj).x1();
            }
        }) && !this.a.v().a(s10M.q().A().J2(), this.d, this.a);
    }

    public final boolean a(Set set) {
        V10 v10 = new V10();
        Set setC = OU.c();
        Set setC2 = OU.c();
        com.android.tools.r8.utils.O3 o3D = com.android.tools.r8.utils.O3.d((Iterable) set);
        while (o3D.c()) {
            S10 s10 = (S10) o3D.f();
            S10 s10M = s10.m();
            U10 u10A = v10.a(s10M);
            if (s10M != s10) {
                V10.a(v10.a(s10), u10A);
            }
            if (!s10M.L()) {
                if (!e && s10M.m().L()) {
                    throw new AssertionError();
                }
                B1 b1A = s10M.a(this.a, this.b);
                if (b1A.A()) {
                    continue;
                } else {
                    if (b1A.B()) {
                        com.android.tools.r8.graph.Y0 y0F = b1A.p().F();
                        C0702m c0702m = this.a;
                        c0702m.getClass();
                        com.android.tools.r8.graph.T0 t0C = y0F.c(c0702m.c(y0F.e));
                        if (t0C == null || !t0C.f.R()) {
                        }
                    }
                    if (!s10M.i()) {
                        AbstractC2412ko abstractC2412ko = s10M.b;
                        if (abstractC2412ko.b2()) {
                            for (S10 s11 : (ArrayList) abstractC2412ko.e0().J2()) {
                                V10.a(u10A, v10.a(s11));
                                o3D.c(s11);
                            }
                        } else if (abstractC2412ko instanceof AG) {
                            S10 s10J2 = abstractC2412ko.r0().J2();
                            V10.a(u10A, v10.a(s10J2));
                            o3D.c(s10J2);
                        }
                        for (AbstractC2412ko abstractC2412ko2 : abstractC2412ko.h().a(abstractC2412ko)) {
                            if (abstractC2412ko2.r1()) {
                                C1736c3 c1736c3V = abstractC2412ko2.v();
                                if (c1736c3V.J2().m() != s10M) {
                                    break;
                                }
                                V10.a(u10A, v10.a(c1736c3V.K2()));
                                o3D.c(c1736c3V.K2());
                                V10.a(u10A, v10.a(c1736c3V.value()));
                                o3D.c(c1736c3V.value());
                                setC.add(abstractC2412ko2);
                            } else if (abstractC2412ko2.o2()) {
                                if (abstractC2412ko2.s0().J2() != s10M) {
                                    break;
                                }
                                setC.add(abstractC2412ko2);
                                setC.add(abstractC2412ko2);
                            } else {
                                if (abstractC2412ko2.c(this.a, this.b)) {
                                    break;
                                }
                                setC.add(abstractC2412ko2);
                            }
                        }
                        setC2.add(s10M);
                    }
                    if (!a(s10M) && !a(s10M, u10A, v10, o3D) && !a(s10M, u10A, v10, setC, setC2, o3D)) {
                        return true;
                    }
                }
            }
        }
        v10.b();
        final Set<U10> setA = com.android.tools.r8.utils.T2.a((Iterable) v10.a());
        while (!setA.isEmpty()) {
            final Set setC3 = OU.c();
            for (U10 u10 : setA) {
                if (!u10.a(new Predicate() { // from class: com.android.tools.r8.internal.b20$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return C1662b20.a(setA, setC3, (U10) obj);
                    }
                })) {
                    setC3.add(u10);
                }
            }
            if (setC3.isEmpty()) {
                return true;
            }
            setA.removeAll(setC3);
        }
        return a(setC2, setC);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, Set set2, U10 u10) {
        return set.contains(u10) && !set2.contains(u10);
    }

    private boolean a(S10 s10) {
        if (!s10.b(new b20$$ExternalSyntheticLambda1())) {
            return false;
        }
        C2115gt c2115gtI0 = s10.q().i0();
        if (c2115gtI0.S2() == this.c.p4.a) {
            return b(c2115gtI0.U2());
        }
        return false;
    }

    private static boolean a(S10 s10, U10 u10, V10 v10, com.android.tools.r8.utils.O3 o3) {
        if (!s10.b(new Predicate() { // from class: com.android.tools.r8.internal.b20$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC2412ko) obj).i2();
            }
        })) {
            return false;
        }
        for (S10 s11 : (ArrayList) s10.q().l0().h1()) {
            V10.a(u10, v10.a(s11));
            o3.c(s11);
        }
        return true;
    }

    private boolean a(S10 s10, U10 u10, V10 v10, Set set, Set set2, com.android.tools.r8.utils.O3 o3) {
        C1166Ls c1166LsA;
        com.android.tools.r8.graph.W0 w0C;
        if (!s10.b(new IW$$ExternalSyntheticLambda0())) {
            return false;
        }
        DG dgT0 = s10.b.t0();
        AbstractC0727q0 abstractC0727q0A = C0729q2.a(this.a.c(dgT0.h));
        if (abstractC0727q0A == null || (c1166LsA = AbstractC2871ql.a(this.c, dgT0.b())) == null || c1166LsA.S2().e != abstractC0727q0A.d || (w0C = abstractC0727q0A.c(c1166LsA.S2())) == null) {
            return false;
        }
        AbstractC1380Tn abstractC1380TnA = w0C.i1().a(c1166LsA);
        C0702m c0702m = this.a;
        ArrayList arrayList = new ArrayList();
        while (abstractC0727q0A != null && abstractC0727q0A.d != c0702m.a().Z1) {
            arrayList.addAll(abstractC0727q0A.w1());
            abstractC0727q0A = c0702m.c(abstractC0727q0A.f);
        }
        if (!arrayList.isEmpty()) {
            if (abstractC1380TnA.d()) {
                return false;
            }
            for (int i = 1; i < ((ArrayList) c1166LsA.J2()).size(); i++) {
                S10 s10C = c1166LsA.c(i);
                V10.a(u10, v10.a(s10C));
                o3.c(s10C);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (!((com.android.tools.r8.graph.T0) it.next()).f.h()) {
                    set2.add(s10);
                    break;
                }
            }
        }
        if (!abstractC1380TnA.e()) {
            set.add(c1166LsA);
        }
        return true;
    }

    private boolean a(Set set, Set set2) {
        boolean z;
        boolean z2;
        Set setC = OU.c();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            S10 s10 = (S10) it.next();
            if (!e && s10.i()) {
                throw new AssertionError();
            }
            setC.add(s10.b.h());
        }
        IdentityHashMap identityHashMap = new IdentityHashMap();
        ArrayDeque arrayDeque = new ArrayDeque(setC);
        while (!arrayDeque.isEmpty()) {
            C3332x4 c3332x4 = (C3332x4) arrayDeque.removeFirst();
            final C1581a20 c1581a20 = new C1581a20();
            Iterator<C3332x4> it2 = c3332x4.y().iterator();
            while (it2.hasNext()) {
                C1581a20 c1581a21 = (C1581a20) identityHashMap.getOrDefault(it2.next(), C1581a20.c);
                c1581a20.a |= c1581a21.a;
                c1581a20.b.addAll(c1581a21.b);
            }
            boolean zAdd = false;
            for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
                if (!set2.contains(abstractC2412ko)) {
                    if (abstractC2412ko.c()) {
                        RW rwG0 = abstractC2412ko.G0();
                        if (c1581a20.b.contains(rwG0.value())) {
                            if (c1581a20.a) {
                                z = false;
                            } else {
                                c1581a20.a = true;
                                z = true;
                            }
                            zAdd |= z;
                        }
                        if (c1581a20.a) {
                            if (rwG0.getField().e.a(this.b, this.a)) {
                                return true;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        if (abstractC2412ko.d(this.a, this.b)) {
                            if (abstractC2412ko.a(new Predicate() { // from class: com.android.tools.r8.internal.b20$$ExternalSyntheticLambda4
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return c1581a20.a((S10) obj);
                                }
                            })) {
                                if (c1581a20.a) {
                                    z2 = false;
                                } else {
                                    c1581a20.a = true;
                                    z2 = true;
                                }
                                zAdd |= z2;
                            }
                            if (c1581a20.a && abstractC2412ko.c(this.a, this.b)) {
                                return true;
                            }
                        }
                        if (abstractC2412ko.d1() && set.contains(abstractC2412ko.b())) {
                            zAdd |= c1581a20.b.add(abstractC2412ko.b());
                        }
                    }
                }
            }
            identityHashMap.put(c3332x4, c1581a20);
            if (zAdd) {
                arrayDeque.addAll(c3332x4.z());
            }
        }
        return false;
    }
}
