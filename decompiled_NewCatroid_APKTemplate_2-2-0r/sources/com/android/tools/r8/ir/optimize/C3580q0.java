package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.B4;
import com.android.tools.r8.internal.C0978Fc;
import com.android.tools.r8.internal.C1296Qj;
import com.android.tools.r8.internal.C1303Qq;
import com.android.tools.r8.internal.C1316Rd;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2173hh;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2799pi;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.C3479z4;
import com.android.tools.r8.internal.InterfaceC2488lo;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.S10;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.q0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3580q0 {
    static final /* synthetic */ boolean a = true;

    public static void a(C0702m<?> c0702m, C2409kl c2409kl, com.android.tools.r8.ir.regalloc.b bVar) {
        a(c2409kl, bVar);
        for (C3332x4 c3332x4 : c2409kl.d) {
            HashMap map = new HashMap();
            C3568k0 c3568k0 = new C3568k0(bVar);
            InterfaceC2641no interfaceC2641noA = c3332x4.a(c2409kl);
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko next = interfaceC2641noA.next();
                if (c3568k0.a(next)) {
                    interfaceC2641noA.v();
                } else if (next.b() != null && next.b().Y()) {
                    S10 s10B = next.b();
                    int iW0 = next.W0();
                    if (s10B.L() && next.B1()) {
                        C0978Fc c0978FcE = next.E();
                        S10 s10B2 = c0978FcE.b();
                        s10B2.getClass();
                        if (s10B2 instanceof C2799pi ? false : c0978FcE.b().r().d(c0978FcE.W0()).z()) {
                            interfaceC2641noA.v();
                        } else {
                            int iA = bVar.a(s10B, iW0);
                            C0978Fc c0978Fc = (C0978Fc) map.get(Integer.valueOf(iA));
                            if (c0978Fc == null || !c0978Fc.b(next)) {
                                map.put(Integer.valueOf(iA), next.E());
                                if (next.H2().b()) {
                                    map.remove(Integer.valueOf(iA + 1));
                                }
                                int i = iA - 1;
                                C0978Fc c0978Fc2 = (C0978Fc) map.get(Integer.valueOf(i));
                                if (c0978Fc2 != null && c0978Fc2.H2().b()) {
                                    map.remove(Integer.valueOf(i));
                                }
                            } else {
                                interfaceC2641noA.v();
                            }
                        }
                    } else {
                        int iA2 = bVar.a(s10B, iW0);
                        for (int i2 = 0; i2 < s10B.e0(); i2++) {
                            map.remove(Integer.valueOf(iA2 + i2));
                        }
                        int i3 = iA2 - 1;
                        C0978Fc c0978Fc3 = (C0978Fc) map.get(Integer.valueOf(i3));
                        if (c0978Fc3 != null && c0978Fc3.H2().b()) {
                            map.remove(Integer.valueOf(i3));
                        }
                    }
                }
            }
        }
        C3560g0 c3560g0 = new C3560g0(c2409kl, bVar);
        Set setC = OU.c();
        Iterator<C3332x4> it = c2409kl.d.iterator();
        while (it.hasNext()) {
            a(it.next(), bVar, setC, c3560g0);
        }
        c2409kl.d.removeAll(setC);
        a(c2409kl, bVar, 0);
        if (a) {
            return;
        }
        c2409kl.b((C0702m) c0702m, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List b(C2173hh c2173hh) {
        return new ArrayList();
    }

    private static void a(final C3332x4 c3332x4, final com.android.tools.r8.ir.regalloc.f fVar, Set set, C3560g0 c3560g0) {
        if (set.contains(c3332x4) || !a(c3332x4)) {
            return;
        }
        List listU = c3332x4.u();
        while (true) {
            C3332x4 c3332x5 = (C3332x4) listU.get(0);
            Iterator it = listU.iterator();
            while (it.hasNext()) {
                if (((C3332x4) it.next()).M()) {
                    if (!a && !set.containsAll(listU)) {
                        throw new AssertionError();
                    }
                    return;
                }
            }
            final AbstractC2412ko abstractC2412koK = c3332x5.k();
            for (int i = 1; i < listU.size(); i++) {
                if (!c3560g0.b(abstractC2412koK, ((C3332x4) listU.get(i)).k())) {
                    return;
                }
            }
            if (abstractC2412koK.g()) {
                if (c3332x4.D()) {
                    return;
                }
                Iterator it2 = listU.iterator();
                while (it2.hasNext()) {
                    if (((C3332x4) it2.next()).D()) {
                        return;
                    }
                }
            }
            if (abstractC2412koK.b() != null && abstractC2412koK.b().Y()) {
                final int iA = fVar.a(abstractC2412koK.b(), abstractC2412koK.W0());
                if (!c3332x4.m().h1().stream().allMatch(new Predicate() { // from class: com.android.tools.r8.ir.optimize.q0$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return C3580q0.a(fVar, c3332x4, abstractC2412koK, iA, (S10) obj);
                    }
                })) {
                    return;
                }
            }
            if (!abstractC2412koK.Y0().equals(c3332x4.m().Y0()) && (!c3332x4.m().Y0().o() || c3332x4.m().S0().isEmpty())) {
                return;
            }
            Iterator it3 = listU.iterator();
            while (it3.hasNext()) {
                ((C3332x4) it3.next()).p().removeFirst();
            }
            if (abstractC2412koK.g2()) {
                LinkedList<AbstractC2412ko> linkedListP = c3332x4.p();
                linkedListP.removeLast();
                linkedListP.add(abstractC2412koK);
                abstractC2412koK.a(c3332x4);
                ArrayList arrayList = new ArrayList(listU);
                c3332x4.i();
                Iterator it4 = c3332x5.u().iterator();
                while (it4.hasNext()) {
                    c3332x4.h((C3332x4) it4.next());
                }
                Iterator it5 = arrayList.iterator();
                while (it5.hasNext()) {
                    ((C3332x4) it5.next()).i();
                }
                set.addAll(arrayList);
                if (!a(c3332x4)) {
                    return;
                }
            } else {
                c3332x4.p().listIterator(c3332x4.p().size() - 1).add(abstractC2412koK);
                abstractC2412koK.a(c3332x4);
                if (abstractC2412koK instanceof C1316Rd) {
                    C1316Rd c1316RdH = abstractC2412koK.H();
                    Iterator it6 = listU.iterator();
                    while (it6.hasNext()) {
                        c1316RdH.a(((C3332x4) it6.next()).q());
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.ir.regalloc.f fVar, C3332x4 c3332x4, AbstractC2412ko abstractC2412ko, int i, S10 s10) {
        int iA = fVar.a(s10, c3332x4.m().W0());
        for (int i2 = 0; i2 < abstractC2412ko.b().e0(); i2++) {
            for (int i3 = 0; i3 < s10.e0(); i3++) {
                if (i + i2 == iA + i3) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean a(C3332x4 c3332x4) {
        List listU = c3332x4.u();
        if (listU.size() <= 1) {
            return false;
        }
        Iterator it = listU.iterator();
        while (it.hasNext()) {
            if (((C3332x4) it.next()).y().size() != 1) {
                return false;
            }
        }
        C3332x4 c3332x5 = (C3332x4) listU.get(0);
        for (int i = 1; i < listU.size(); i++) {
            if (!Objects.equals(c3332x5.q(), ((C3332x4) listU.get(i)).q())) {
                return false;
            }
        }
        return true;
    }

    public static void a(C2409kl c2409kl, com.android.tools.r8.ir.regalloc.f fVar, int i) {
        C3332x4 c3332x4;
        int i2;
        C3332x4 c3332x5;
        C1303Qq c1303Qq;
        C1303Qq c1303Qq2;
        C1303Qq c1303Qq3;
        Iterator it;
        int i3;
        C2409kl c2409kl2 = c2409kl;
        Collection collection = c2409kl2.d;
        List<C3332x4> listE = c2409kl.e();
        int i4 = 1;
        if (listE.size() > 1) {
            C3332x4 c3332x6 = new C3332x4();
            c3332x6.r().addAll(listE);
            ArrayList arrayList = new ArrayList(c2409kl2.d);
            arrayList.add(c3332x6);
            c3332x4 = c3332x6;
            collection = arrayList;
        } else {
            c3332x4 = null;
        }
        while (true) {
            IdentityHashMap identityHashMap = new IdentityHashMap();
            Iterator it2 = collection.iterator();
            while (it2.hasNext()) {
                C3332x4 c3332x7 = (C3332x4) it2.next();
                C3560g0 c3560g0 = new C3560g0(c2409kl2, fVar);
                HashMap map = new HashMap();
                Iterator<C3332x4> it3 = c3332x7.y().iterator();
                while (true) {
                    i2 = 2;
                    if (!it3.hasNext()) {
                        break;
                    }
                    C3332x4 next = it3.next();
                    if (next.m().M1() && next.z().size() == i4 && next.p().size() > i4) {
                        LinkedList<AbstractC2412ko> linkedListP = next.p();
                        ((List) map.computeIfAbsent(c3560g0.c(linkedListP.get(linkedListP.size() - 2)), new Function() { // from class: com.android.tools.r8.ir.optimize.q0$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return C3580q0.a((C2173hh) obj);
                            }
                        })).add(next);
                    } else if (next.m().u2() && next.z().isEmpty() && next.p().size() > 2) {
                        ((List) map.computeIfAbsent(c3560g0.c(next.m()), new Function() { // from class: com.android.tools.r8.ir.optimize.q0$$ExternalSyntheticLambda1
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return C3580q0.b((C2173hh) obj);
                            }
                        })).add(next);
                    }
                }
                for (List<C3332x4> list : map.values()) {
                    if (list.size() >= i2) {
                        C3332x4 c3332x8 = list.get(0);
                        int size = c3332x8.p().size();
                        int i5 = i4;
                        while (i5 < list.size()) {
                            C3332x4 c3332x9 = list.get(i5);
                            boolean z = a;
                            if (!z && !c3332x9.m().M1() && !c3332x9.m().u2()) {
                                throw new AssertionError();
                            }
                            if (!z && !c3332x8.m().M1() && !c3332x8.m().u2()) {
                                throw new AssertionError();
                            }
                            if (c3332x8.q() == null) {
                                c1303Qq2 = null;
                            } else {
                                c1303Qq2 = new C1303Qq(c3332x8.q());
                                for (AbstractC2412ko abstractC2412ko : c3332x8.p()) {
                                    abstractC2412ko.getClass();
                                    if (abstractC2412ko instanceof C1316Rd) {
                                        abstractC2412ko.H().a(c1303Qq2);
                                    }
                                }
                            }
                            if (c3332x9.q() == null) {
                                it = it2;
                                c1303Qq3 = null;
                            } else {
                                c1303Qq3 = new C1303Qq(c3332x9.q());
                                for (AbstractC2412ko abstractC2412ko2 : c3332x9.p()) {
                                    abstractC2412ko2.getClass();
                                    Iterator it4 = it2;
                                    if (abstractC2412ko2 instanceof C1316Rd) {
                                        abstractC2412ko2.H().a(c1303Qq3);
                                    }
                                    it2 = it4;
                                }
                                it = it2;
                            }
                            if (Objects.equals(c1303Qq2, c1303Qq3)) {
                                C3479z4 c3479z4D = c3332x8.d(c3332x8.p().size());
                                C3479z4 c3479z4D2 = c3332x9.d(c3332x9.p().size());
                                i3 = 0;
                                while (c3479z4D.hasPrevious() && c3479z4D2.hasPrevious() && c3479z4D.previous().a(c3479z4D2.previous(), fVar, c2409kl.k())) {
                                    i3++;
                                }
                            } else {
                                i3 = 0;
                            }
                            size = Math.min(size, i3);
                            i5++;
                            it2 = it;
                        }
                        it2 = it2;
                        int size2 = i - ((list.size() - 1) * size);
                        if (size > 1) {
                            if (size2 < 0) {
                                int iA = c2409kl2.f.a();
                                C3332x4 c3332x10 = c3332x7 == c3332x4 ? null : c3332x7;
                                C3332x4 c3332x11 = list.get(0);
                                if (!a && ((c3332x10 == null || !c3332x11.m().M1()) && (c3332x10 != null || !c3332x11.m().u2()))) {
                                    throw new AssertionError();
                                }
                                C3332x4 c3332x12 = new C3332x4();
                                c3332x12.g(iA);
                                C3479z4 c3479z4D3 = c3332x11.d(c3332x11.p().size());
                                if (c3332x11.q() != null) {
                                    c1303Qq = new C1303Qq(c3332x11.q());
                                    int size3 = c3332x11.p().size() - size;
                                    InterfaceC2488lo interfaceC2488loR = c3332x11.R();
                                    int i6 = 0;
                                    while (i6 < size3) {
                                        AbstractC2412ko next2 = interfaceC2488loR.next();
                                        next2.getClass();
                                        C3332x4 c3332x13 = c3332x4;
                                        if (next2 instanceof C1316Rd) {
                                            next2.H().a(c1303Qq);
                                        }
                                        i6++;
                                        c3332x4 = c3332x13;
                                    }
                                    c3332x5 = c3332x4;
                                } else {
                                    c3332x5 = c3332x4;
                                    c1303Qq = null;
                                }
                                fVar.a(c3332x12, size, list);
                                boolean z2 = false;
                                for (int i7 = 0; i7 < size; i7++) {
                                    AbstractC2412ko abstractC2412koPrevious = c3479z4D3.previous();
                                    z2 = z2 || abstractC2412koPrevious.g();
                                    c3332x12.p().addFirst(abstractC2412koPrevious);
                                    abstractC2412koPrevious.a(c3332x12);
                                }
                                if (z2 && c3332x11.D()) {
                                    c3332x12.l(c3332x11);
                                }
                                for (C3332x4 c3332x14 : list) {
                                    AbstractC1915eM abstractC1915eMY0 = c3332x14.k().Y0();
                                    LinkedList<AbstractC2412ko> linkedListP2 = c3332x14.p();
                                    for (int i8 = 0; i8 < size; i8++) {
                                        linkedListP2.removeLast();
                                    }
                                    for (AbstractC2412ko abstractC2412ko3 : c3332x14.p()) {
                                        if (abstractC2412ko3.Y0().s()) {
                                            abstractC1915eMY0 = abstractC2412ko3.Y0();
                                        }
                                    }
                                    C1296Qj c1296Qj = new C1296Qj();
                                    c1296Qj.a(c3332x14);
                                    c1296Qj.b(abstractC1915eMY0);
                                    linkedListP2.add(c1296Qj);
                                    c3332x12.r().add(c3332x14);
                                    if (c3332x10 != null) {
                                        c3332x14.b(c3332x10, c3332x12);
                                        c3332x10.r().remove(c3332x14);
                                    } else {
                                        c3332x14.s().add(c3332x12);
                                    }
                                    if (z2) {
                                        c3332x14.c();
                                    }
                                }
                                c3332x12.a((C2102gl) null);
                                if (c1303Qq != null) {
                                    c3332x12.a(c1303Qq);
                                }
                                if (c3332x10 != null) {
                                    c3332x12.h(c3332x10);
                                }
                                identityHashMap.put(list.get(0), c3332x12);
                                c2409kl2 = c2409kl;
                                c3332x4 = c3332x5;
                                i4 = 1;
                            }
                            i2 = 2;
                        } else {
                            c2409kl2 = c2409kl;
                        }
                        i4 = 1;
                        i2 = 2;
                    }
                }
                c2409kl2 = c2409kl;
            }
            C3332x4 c3332x15 = c3332x4;
            B4 b4S = c2409kl.s();
            while (b4S.hasNext()) {
                C3332x4 next3 = b4S.next();
                if (identityHashMap.containsKey(next3)) {
                    b4S.add((C3332x4) identityHashMap.get(next3));
                }
            }
            Collection collectionValues = identityHashMap.values();
            if (collectionValues.isEmpty()) {
                return;
            }
            i4 = 1;
            collection = collectionValues;
            c3332x4 = c3332x15;
            c2409kl2 = c2409kl;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(C2173hh c2173hh) {
        return new ArrayList();
    }

    public static void a(C2409kl c2409kl, com.android.tools.r8.ir.regalloc.f fVar) {
        boolean z;
        r rVar = new r(c2409kl, fVar);
        do {
            z = false;
            for (C3332x4 c3332x4 : c2409kl.d) {
                HashMap map = new HashMap();
                for (int i = 0; i < c3332x4.y().size(); i++) {
                    C3332x4 c3332x5 = c3332x4.y().get(i);
                    if (c3332x5.p().size() != 1) {
                        C2173hh c2173hhC = rVar.c(c3332x5);
                        if (map.containsKey(c2173hhC)) {
                            C3332x4 c3332x6 = c3332x4.y().get(((Integer) map.get(c2173hhC)).intValue());
                            if (!a && fVar.b().W0 && !Objects.equals(c3332x5.k().Y0(), c3332x6.k().Y0())) {
                                throw new AssertionError();
                            }
                            fVar.b(c3332x6, c3332x5);
                            c3332x5.c();
                            c3332x5.p().clear();
                            rVar.a(c3332x5);
                            Iterator<C3332x4> it = c3332x5.z().iterator();
                            while (it.hasNext()) {
                                it.next().a(c3332x5, (Set) null);
                            }
                            c3332x5.s().clear();
                            c3332x5.s().add(c3332x6);
                            if (!a && c3332x6.y().contains(c3332x5)) {
                                throw new AssertionError();
                            }
                            c3332x6.r().add(c3332x5);
                            C1296Qj c1296Qj = new C1296Qj();
                            c1296Qj.a(c3332x5);
                            c1296Qj.b(c3332x6.k().Y0());
                            c3332x5.p().add(c1296Qj);
                            z = true;
                        } else {
                            map.put(c2173hhC, Integer.valueOf(i));
                        }
                    }
                }
            }
        } while (z);
    }
}
