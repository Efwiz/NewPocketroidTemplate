package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0692k3;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.Q2;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.A4$$ExternalSyntheticLambda5;
import com.android.tools.r8.internal.AbstractC1182Mh;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC2871ql;
import com.android.tools.r8.internal.B4;
import com.android.tools.r8.internal.C0978Fc;
import com.android.tools.r8.internal.C1033Hc;
import com.android.tools.r8.internal.C1166Ls;
import com.android.tools.r8.internal.C1189Mn;
import com.android.tools.r8.internal.C2375kJ;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2477lf;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.C3406y2;
import com.android.tools.r8.internal.C3446yc;
import com.android.tools.r8.internal.InterfaceC1102Jh;
import com.android.tools.r8.internal.InterfaceC2454lJ;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.InterfaceC2729oo;
import com.android.tools.r8.internal.ML;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.QW;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.I1;
import com.android.tools.r8.utils.O3;
import com.android.tools.r8.utils.Z1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class H {
    static final /* synthetic */ boolean g = true;
    private final C0702m a;
    private final F b;
    private final o5 c;
    private final C2409kl d;
    private AbstractC3938n2 e = AbstractC3938n2.g();
    private Set f;

    /* JADX INFO: renamed from: $r8$lambda$wBZ5N9SAmxJLRX-vx8hIUeo_fgc, reason: not valid java name */
    public static /* synthetic */ ArrayList m1851$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc() {
        return new ArrayList();
    }

    public H(C0702m c0702m, F f, o5 o5Var, C2409kl c2409kl) {
        this.a = c0702m;
        this.b = f;
        this.c = o5Var;
        this.d = c2409kl;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean a(InterfaceC1102Jh interfaceC1102Jh) {
        if (this.e.e()) {
            this.e = AbstractC3938n2.a(d());
        }
        if (this.e.d()) {
            return false;
        }
        C0702m c0702mH0 = this.a.h0();
        AbstractC0677i3.a<?> aVarL = ((C0680j) c0702mH0.f()).c(interfaceC1102Jh.getField()).l();
        if (aVarL == null || !(aVarL instanceof C0692k3)) {
            return false;
        }
        m5 m5VarU = aVarL.u();
        Q2 q2X = m5VarU.getAccessFlags();
        if (!g && q2X.V()) {
            throw new AssertionError();
        }
        if (!q2X.h() || this.a.a(m5VarU).c((com.android.tools.r8.shaking.K0) this.a.Y())) {
            return false;
        }
        if (this.c.e().A1()) {
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) interfaceC1102Jh;
            if (this.c.getAccessFlags().o() == (abstractC2412ko instanceof QW)) {
                if (this.c.a() == m5VarU.a()) {
                    return false;
                }
                if ((abstractC2412ko instanceof C1189Mn) && ((C0680j) c0702mH0.f()).c(this.c.a(), m5VarU.a())) {
                    return false;
                }
            }
        }
        return aVarL.a().d(this.a);
    }

    private Set b() {
        final O3 o3E = O3.e();
        this.d.d.forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.H$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                H.a(o3E, (C3332x4) obj);
            }
        });
        while (o3E.c()) {
            o3E.a((Iterable) ((C3332x4) o3E.f()).z());
        }
        return o3E.b();
    }

    private Set c() {
        Set setC = OU.c();
        for (C3332x4 c3332x4 : b()) {
            for (C1189Mn c1189Mn : I1.a(c3332x4.p(), new F$$ExternalSyntheticLambda3())) {
                S10 s10E = c1189Mn.e();
                if (!s10E.b(new A4$$ExternalSyntheticLambda5()) && s10E.h().D()) {
                    setC.add(c1189Mn);
                }
            }
        }
        return setC;
    }

    private boolean d() {
        if (!this.a.f().h()) {
            return true;
        }
        C0680j c0680jH = this.a.h();
        Iterator it = this.d.b(new Predicate() { // from class: com.android.tools.r8.ir.optimize.H$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC2412ko) obj).K1();
            }
        }).iterator();
        while (it.hasNext()) {
            AbstractC0677i3.a<?> aVarL = c0680jH.c(((AbstractC1182Mh) it.next()).getField()).l();
            if (aVarL == null || aVarL.s().getAccessFlags().V()) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(O3 o3, C3332x4 c3332x4) {
        o3.a((Iterable) c3332x4.n().a());
    }

    public final void a() {
        boolean z;
        AbstractC2412ko abstractC2412koA;
        InterfaceC2729oo interfaceC2729oo;
        AbstractC2412ko abstractC2412koQ;
        C1166Ls c1166LsA;
        C3406y2 c3406y2M;
        ML mlL;
        C2375kJ c2375kJ = new C2375kJ(new G(), 0);
        for (AbstractC2412ko abstractC2412ko : this.d.r()) {
            if (a(abstractC2412ko)) {
                ((List) c2375kJ.computeIfAbsent(abstractC2412ko, Z1.a(new H$$ExternalSyntheticLambda2()))).add(abstractC2412ko);
            }
        }
        if (c2375kJ.isEmpty()) {
            this.e = AbstractC3938n2.c;
            this.f = null;
            return;
        }
        if (!g && this.d.j().D()) {
            throw new AssertionError();
        }
        Iterator it = c2375kJ.k().stream().filter(new Predicate() { // from class: com.android.tools.r8.ir.optimize.H$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return H.a((InterfaceC2454lJ) obj);
            }
        }).sorted(new Comparator() { // from class: com.android.tools.r8.ir.optimize.H$$ExternalSyntheticLambda4
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return H.a((InterfaceC2454lJ) obj, (InterfaceC2454lJ) obj2);
            }
        }).limit(22L).iterator();
        if (!it.hasNext()) {
            this.e = AbstractC3938n2.c;
            this.f = null;
            return;
        }
        IdentityHashMap identityHashMap = new IdentityHashMap();
        boolean z2 = false;
        do {
            InterfaceC2454lJ interfaceC2454lJ = (InterfaceC2454lJ) it.next();
            AbstractC2412ko abstractC2412ko2 = (AbstractC2412ko) interfaceC2454lJ.getKey();
            boolean z3 = g;
            if (!z3 && !abstractC2412ko2.j1()) {
                throw new AssertionError();
            }
            if (!abstractC2412ko2.h().y().isEmpty()) {
                int iG2 = abstractC2412ko2.G2();
                if (iG2 == 12) {
                    C2409kl c2409kl = this.d;
                    C3446yc c3446ycA = abstractC2412ko2.A();
                    int i = C3446yc.l;
                    abstractC2412koA = C3446yc.a(new S10(c2409kl.e.a(), c3446ycA.a(), c3446ycA.j()), c3446ycA);
                } else if (iG2 == 20) {
                    C2409kl c2409kl2 = this.d;
                    C2477lf c2477lfJ = abstractC2412ko2.J();
                    int i2 = C2477lf.l;
                    abstractC2412koA = C2477lf.a(new S10(c2409kl2.e.a(), c2477lfJ.a(), c2477lfJ.j()), c2477lfJ);
                } else if (iG2 == 28) {
                    abstractC2412koA = C1189Mn.a(this.d, abstractC2412ko2.T());
                } else if (iG2 == 59) {
                    abstractC2412koA = QW.a(this.d, abstractC2412ko2.F0());
                } else if (iG2 == 15) {
                    abstractC2412koA = C0978Fc.a(this.d, abstractC2412ko2.E());
                } else if (iG2 == 16) {
                    C2409kl c2409kl3 = this.d;
                    C1033Hc c1033HcF = abstractC2412ko2.F();
                    int i3 = C1033Hc.k;
                    abstractC2412koA = C1033Hc.a(new S10(c2409kl3.e.a(), c1033HcF.a(), c1033HcF.j()), c1033HcF);
                } else {
                    throw new C3405y10();
                }
                abstractC2412ko2 = abstractC2412koA;
                int iG3 = abstractC2412ko2.G2();
                if (iG3 == 12 || iG3 == 20) {
                    interfaceC2729oo = null;
                } else {
                    if (iG3 == 28) {
                        S10 s10E = abstractC2412ko2.T().e();
                        if (!s10E.Q()) {
                            if (s10E.i()) {
                                mlL = s10E.l();
                            } else {
                                abstractC2412koQ = s10E.q();
                                if (abstractC2412koQ.m1()) {
                                    c3406y2M = this.d.m();
                                } else if (abstractC2412koQ.p2()) {
                                    c1166LsA = AbstractC2871ql.a(this.a.a(), abstractC2412koQ.t0().b());
                                    if (!z3 && c1166LsA == null) {
                                        throw new AssertionError();
                                    }
                                }
                            }
                        }
                    } else if (iG3 != 59 && iG3 != 15 && iG3 != 16) {
                        throw new C3405y10();
                    }
                    interfaceC2729oo = null;
                }
                if (interfaceC2729oo == null) {
                    interfaceC2729oo = c3406y2M;
                    interfaceC2729oo = mlL;
                    InterfaceC2641no interfaceC2641noA = this.d.j().a(this.d);
                    while (interfaceC2641noA.hasNext()) {
                        if (!interfaceC2641noA.next().m1()) {
                            abstractC2412ko2.b(this.d.c);
                            interfaceC2641noA.previous();
                            break;
                        }
                    }
                    interfaceC2641noA.add(abstractC2412ko2);
                } else {
                    interfaceC2729oo = c3406y2M;
                    interfaceC2729oo = mlL;
                    a(interfaceC2729oo, abstractC2412ko2, identityHashMap);
                }
            }
            for (AbstractC2412ko abstractC2412ko3 : (List) interfaceC2454lJ.getValue()) {
                if (abstractC2412ko3 != abstractC2412ko2) {
                    abstractC2412ko3.b().g(abstractC2412ko2.b());
                    abstractC2412ko3.h().a(this.d, abstractC2412ko3).s();
                    List listEmptyList = (List) identityHashMap.remove(abstractC2412ko3);
                    if (listEmptyList == null) {
                        listEmptyList = Collections.emptyList();
                    }
                    Iterator it2 = listEmptyList.iterator();
                    while (it2.hasNext()) {
                        a(abstractC2412ko2, (AbstractC2412ko) it2.next(), identityHashMap);
                    }
                }
            }
            S10 s10B = abstractC2412ko2.b();
            Predicate predicate = new Predicate() { // from class: com.android.tools.r8.ir.optimize.H$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((AbstractC2412ko) obj).N1();
                }
            };
            Iterator<AbstractC2412ko> it3 = s10B.h0().iterator();
            while (true) {
                if (it3.hasNext()) {
                    if (predicate.test(it3.next())) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            z2 |= z;
        } while (it.hasNext());
        if (!identityHashMap.isEmpty()) {
            B4 b4S = this.d.s();
            while (b4S.hasNext()) {
                C3332x4 c3332x4A = b4S.next();
                InterfaceC2641no interfaceC2641noA2 = c3332x4A.a(this.d);
                for (ML ml : c3332x4A.x()) {
                    List listEmptyList2 = (List) identityHashMap.remove(ml);
                    if (listEmptyList2 == null) {
                        listEmptyList2 = Collections.emptyList();
                    }
                    if (!listEmptyList2.isEmpty()) {
                        O3 o3D = O3.d((Iterable) listEmptyList2);
                        while (o3D.c()) {
                            AbstractC2412ko abstractC2412ko4 = (AbstractC2412ko) o3D.f();
                            List listEmptyList3 = (List) identityHashMap.remove(abstractC2412ko4);
                            if (listEmptyList3 == null) {
                                listEmptyList3 = Collections.emptyList();
                            }
                            if (listEmptyList3.isEmpty()) {
                                interfaceC2641noA2 = a(b4S, interfaceC2641noA2, ml, abstractC2412ko4);
                            } else {
                                o3D.a((Iterable) listEmptyList3);
                                o3D.d(abstractC2412ko4);
                            }
                        }
                    }
                }
                while (interfaceC2641noA2.hasNext()) {
                    AbstractC2412ko next = interfaceC2641noA2.next();
                    List listEmptyList4 = (List) identityHashMap.remove(next);
                    if (listEmptyList4 == null) {
                        listEmptyList4 = Collections.emptyList();
                    }
                    if (!listEmptyList4.isEmpty()) {
                        O3 o3D2 = O3.d((Iterable) listEmptyList4);
                        while (o3D2.c()) {
                            AbstractC2412ko abstractC2412ko5 = (AbstractC2412ko) o3D2.f();
                            List listEmptyList5 = (List) identityHashMap.remove(abstractC2412ko5);
                            if (listEmptyList5 == null) {
                                listEmptyList5 = Collections.emptyList();
                            }
                            if (listEmptyList5.isEmpty()) {
                                interfaceC2641noA2 = a(b4S, interfaceC2641noA2, next, abstractC2412ko5);
                            } else {
                                o3D2.a((Iterable) listEmptyList5);
                                o3D2.d(abstractC2412ko5);
                            }
                        }
                    }
                }
            }
        }
        boolean z4 = g;
        if (!z4 && !identityHashMap.isEmpty()) {
            throw new AssertionError();
        }
        if (this.d.y() | z2) {
            this.b.p(this.d);
        }
        if (!z4 && !this.d.b(this.a)) {
            throw new AssertionError();
        }
        this.e = AbstractC3938n2.c;
        this.f = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(InterfaceC2454lJ interfaceC2454lJ) {
        return ((List) interfaceC2454lJ.getValue()).size() > 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(InterfaceC2454lJ interfaceC2454lJ, InterfaceC2454lJ interfaceC2454lJ2) {
        return Integer.compare(((List) interfaceC2454lJ2.getValue()).size(), ((List) interfaceC2454lJ.getValue()).size());
    }

    private static void a(InterfaceC2729oo interfaceC2729oo, AbstractC2412ko abstractC2412ko, IdentityHashMap identityHashMap) {
        ((List) identityHashMap.computeIfAbsent(interfaceC2729oo, Z1.a(new H$$ExternalSyntheticLambda2()))).add(abstractC2412ko);
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x008e, code lost:
    
        if (r0.F().c(r5.a.c(r1)) != null) goto L59;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean a(com.android.tools.r8.internal.AbstractC2412ko r6) {
        /*
            Method dump skipped, instruction units count: 318
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.ir.optimize.H.a(com.android.tools.r8.internal.ko):boolean");
    }

    private InterfaceC2641no a(B4 b4, InterfaceC2641no interfaceC2641no, final InterfaceC2729oo interfaceC2729oo, AbstractC2412ko abstractC2412ko) {
        AbstractC1915eM abstractC1915eMY0;
        boolean z = g;
        if (!z && interfaceC2729oo.i() && interfaceC2641no.hasPrevious()) {
            throw new AssertionError();
        }
        if (!z && !interfaceC2729oo.i() && interfaceC2641no.j() != interfaceC2729oo) {
            throw new AssertionError();
        }
        if (interfaceC2729oo.i()) {
            abstractC1915eMY0 = interfaceC2729oo.h().k().Y0();
        } else {
            abstractC1915eMY0 = interfaceC2729oo.k().Y0();
        }
        if (abstractC2412ko.g() && abstractC1915eMY0.o()) {
            abstractC1915eMY0 = AbstractC1915eM.v();
        }
        abstractC2412ko.b(abstractC1915eMY0);
        if (abstractC2412ko.g() && interfaceC2729oo.h().D() && interfaceC2729oo.h().a()) {
            C3332x4 c3332x4A = interfaceC2641no.a(this.d, b4, this.a.Y(), new UnaryOperator() { // from class: com.android.tools.r8.ir.optimize.H$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return H.a(interfaceC2729oo, (C3332x4) obj);
                }
            });
            if (interfaceC2729oo.i()) {
                if (!z && interfaceC2729oo.h().p().size() != 1) {
                    throw new AssertionError();
                }
                interfaceC2641no.b(abstractC2412ko);
                if (!z && interfaceC2641no.hasPrevious()) {
                    throw new AssertionError();
                }
            } else {
                if (interfaceC2729oo.h().a()) {
                    if (!z && c3332x4A.a()) {
                        throw new AssertionError();
                    }
                    c3332x4A.a(this.d).add(abstractC2412ko);
                } else {
                    if (!z && !c3332x4A.a()) {
                        throw new AssertionError();
                    }
                    interfaceC2641no.b(abstractC2412ko);
                }
                interfaceC2641no.a(interfaceC2729oo.k());
            }
        } else {
            interfaceC2641no.c(abstractC2412ko);
        }
        return interfaceC2641no;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3332x4 a(InterfaceC2729oo interfaceC2729oo, C3332x4 c3332x4) {
        return interfaceC2729oo.h();
    }
}
