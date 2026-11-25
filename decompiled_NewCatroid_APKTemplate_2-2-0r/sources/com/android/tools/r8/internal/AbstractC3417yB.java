package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.e5$$ExternalSyntheticLambda4;
import com.android.tools.r8.naming.C3605b;
import com.android.tools.r8.shaking.AbstractC3735i1;
import com.android.tools.r8.utils.AbstractC3972u2;
import com.android.tools.r8.utils.C3945p;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3417yB {
    static final /* synthetic */ boolean a = true;

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0051, code lost:
    
        if (r0.isUnknown() == false) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.android.tools.r8.naming.J0 a(com.android.tools.r8.utils.C3945p r3, com.android.tools.r8.graph.C0702m r4, com.android.tools.r8.utils.y3 r5, com.android.tools.r8.utils.AbstractC3972u2 r6, com.android.tools.r8.internal.InterfaceC1444Vd r7) {
        /*
            Method dump skipped, instruction units count: 251
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.AbstractC3417yB.a(com.android.tools.r8.utils.p, com.android.tools.r8.graph.m, com.android.tools.r8.utils.y3, com.android.tools.r8.utils.u2, com.android.tools.r8.internal.Vd):com.android.tools.r8.naming.J0");
    }

    public static C3605b a(C0702m c0702m, C3945p c3945p, AbstractC3972u2 abstractC3972u2, InterfaceC1444Vd interfaceC1444Vd) {
        int i;
        boolean z;
        int i2;
        ArrayList arrayList;
        C0702m c0702m2 = c0702m;
        com.android.tools.r8.utils.T t = new com.android.tools.r8.utils.T(c3945p);
        InterfaceC2697oM interfaceC2697oMA = InterfaceC2697oM.a(c0702m);
        C3341xD c3341xDA = C3341xD.a(c0702m2, abstractC3972u2);
        for (C0729q2 c0729q2 : c0702m.f().d()) {
            IdentityHashMap identityHashMapA = a(c0702m2, c0729q2);
            C3191vD c3191vDA = c3341xDA.a(c0729q2);
            ArrayList<C0759u2> arrayList2 = new ArrayList(identityHashMapA.keySet());
            arrayList2.sort(new e5$$ExternalSyntheticLambda4());
            for (C0759u2 c0759u2 : arrayList2) {
                List<com.android.tools.r8.graph.o5> list = (List) identityHashMapA.get(c0759u2);
                int i3 = 1;
                if (list.size() > 1) {
                    a(list);
                    if (!a && !a(c0702m2, list)) {
                        throw new AssertionError();
                    }
                }
                C2067gM c2067gMA = InterfaceC2229iM.a(c0702m2, t);
                for (com.android.tools.r8.graph.o5 o5Var : list) {
                    com.android.tools.r8.graph.W0 w0E = o5Var.e();
                    C0684j2 reference = o5Var.getReference();
                    if (c0759u2 != o5Var.u()) {
                        i = i3;
                    } else if (c0702m.E().a((AbstractC1476Wj) null, reference) != reference || AbstractC2773pM.a(w0E, c0702m.Y()) || w0E.N0()) {
                        i = 1;
                    } else {
                        i = 1;
                        if (list.size() <= 1) {
                            i3 = 1;
                        }
                    }
                    c2067gMA.a(w0E);
                    int iA = list.size() == i ? interfaceC1444Vd.a(o5Var) : -1;
                    boolean z2 = iA > 0;
                    if (w0E.a1() != null && ((w0E.a1().z0() || w0E.a1().B0()) && !c0702m2.a(w0E))) {
                        z = z2;
                        i2 = 1;
                        arrayList = interfaceC2697oMA.a(o5Var, c2067gMA, list.size() > 1, z, iA);
                    } else {
                        z = z2;
                        i2 = 1;
                        arrayList = new ArrayList();
                    }
                    c3191vDA.a(o5Var, arrayList, c2067gMA, z);
                    c0702m2 = c0702m;
                    c2067gMA = c2067gMA;
                    t = t;
                    i3 = i2;
                }
                c0702m2 = c0702m;
            }
            c0702m2 = c0702m;
        }
        interfaceC2697oMA.a();
        return c3341xDA.b();
    }

    private static boolean a(C0702m c0702m, List list) {
        AbstractC0748t0 abstractC0748t0B;
        if (!c0702m.Y().X0() && c0702m.f().h()) {
            C0680j c0680jN = c0702m.f().n();
            AbstractC3735i1 abstractC3735i1V = c0702m.v();
            Iterator it = list.iterator();
            boolean z = true;
            while (it.hasNext()) {
                com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) it.next();
                if (o5Var.e().C1()) {
                    if (!a && !z) {
                        throw new AssertionError();
                    }
                } else {
                    if (abstractC3735i1V.a(o5Var, c0702m.Y()) && !o5Var.e().D1().d() && (abstractC0748t0B = c0680jN.b((AbstractC0727q0) c0702m.b(o5Var.t()), o5Var.getReference())) != null) {
                        String str = o5Var.getReference().K0() + " is not kept but is overloaded";
                        if (!a && !abstractC0748t0B.a().isInterface()) {
                            throw new AssertionError(str);
                        }
                        C0759u2 c0759u2 = o5Var.getReference().f;
                    }
                    z = false;
                }
            }
        }
        return true;
    }

    private static int a(com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.graph.W wA1 = o5Var.e().a1();
        if (wA1 == null) {
            return 0;
        }
        if (wA1.B0()) {
            com.android.tools.r8.graph.J0 j0H0 = wA1.P().H0();
            if (j0H0 == null) {
                return 0;
            }
            return j0H0.x0();
        }
        if (wA1.z0()) {
            for (B7 b7 : wA1.K().I0()) {
                if (b7 instanceof C2051g8) {
                    return ((C2051g8) b7).U().e();
                }
            }
        }
        return 0;
    }

    private static void a(List list) {
        list.sort(new Comparator() { // from class: com.android.tools.r8.internal.yB$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return AbstractC3417yB.a((com.android.tools.r8.graph.o5) obj, (com.android.tools.r8.graph.o5) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        int iA = a(o5Var) - a(o5Var2);
        if (iA != 0) {
            return iA;
        }
        return o5Var.e().getReference().compareTo(o5Var2.e().getReference());
    }

    public static IdentityHashMap a(C0702m c0702m, C0729q2 c0729q2) {
        IdentityHashMap identityHashMap = new IdentityHashMap(c0729q2.Y().l());
        for (com.android.tools.r8.graph.o5 o5Var : AbstractC0984Fi.a(c0729q2.R1(), c0729q2.k2())) {
            o5Var.e();
            ((List) identityHashMap.computeIfAbsent(c0702m.z().a(o5Var.getReference()), new Function() { // from class: com.android.tools.r8.internal.yB$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return AbstractC3417yB.a((C0759u2) obj);
                }
            })).add(o5Var);
        }
        return identityHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(C0759u2 c0759u2) {
        return new ArrayList();
    }
}
