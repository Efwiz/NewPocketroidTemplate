package com.android.tools.r8.internal;

import com.android.tools.r8.errors.d$$ExternalSyntheticLambda0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0746s4;
import com.android.tools.r8.graph.C0752t4;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0762u4;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0772w0;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3894f2;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class J5 {
    private static final com.android.tools.r8.ir.optimize.info.r c = com.android.tools.r8.ir.optimize.info.r.b();
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final L5 b;

    public J5(C0702m c0702m) {
        this.a = c0702m;
        this.b = new L5(c0702m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public void a(com.android.tools.r8.graph.B5 b5, C0729q2 c0729q2) {
        Set setC = b5.c(c0729q2.d);
        TreeSet treeSet = new TreeSet(Comparator.comparing(new d$$ExternalSyntheticLambda0()));
        Iterator it = setC.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2A = C0729q2.a(this.a.c((C0767v2) it.next()));
            if (c0729q2A != null && this.a.Y().n1.l.test(c0729q2A)) {
                treeSet.add(c0729q2A);
            }
        }
        C3894f2 c3894f2A = C3894f2.a();
        HashSet hashSet = new HashSet();
        Iterator it2 = treeSet.iterator();
        while (it2.hasNext()) {
            for (com.android.tools.r8.graph.W0 w0 : ((C0729q2) it2.next()).P1()) {
                M5 m5J = w0.i1().j();
                if (m5J != null && (m5J instanceof C2823q20)) {
                    hashSet.add(c3894f2A.c(w0.getReference()));
                }
            }
        }
        Iterator it3 = hashSet.iterator();
        while (it3.hasNext()) {
            a((C0684j2) ((C2173hh) it3.next()).a(), c0729q2, treeSet);
        }
    }

    public final void a() {
        C3733i c3733i = (C3733i) this.a.f();
        c3733i.getClass();
        final com.android.tools.r8.graph.B5 b5A = com.android.tools.r8.graph.B5.a(c3733i);
        ((com.android.tools.r8.graph.r) com.android.tools.r8.graph.r.b(this.a, b5A).a()).a(((C3733i) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.internal.J5$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(b5A, (C0729q2) obj);
            }
        });
        if (this.b.b()) {
            return;
        }
        C0746s4 c0746s4B = C0762u4.b();
        this.b.a(c0746s4B);
        this.a.b((DH) this.b.a());
        final C0752t4 c0752t4C = ((C3733i) this.a.f()).x().c();
        c0752t4C.a(c0746s4B.a());
        this.b.a(new BiConsumer() { // from class: com.android.tools.r8.internal.J5$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                J5.a(c0752t4C, (com.android.tools.r8.graph.o5) obj, (M5) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0752t4 c0752t4, com.android.tools.r8.graph.o5 o5Var, M5 m5) {
        m5.getClass();
        if (m5 instanceof C2823q20) {
            c0752t4.j(m5.a().b(), o5Var);
        } else if (!d) {
            throw new AssertionError();
        }
    }

    private void a(C0684j2 c0684j2, C0729q2 c0729q2, TreeSet treeSet) {
        com.android.tools.r8.graph.W0 w0T;
        if (c0729q2.f(c0684j2) != null) {
            return;
        }
        com.android.tools.r8.graph.I4 i4A = ((C3733i) this.a.f()).a(c0684j2, c0729q2.n1());
        if (i4A.x()) {
            if (!i4A.r().s1()) {
                return;
            }
        } else if (i4A.w()) {
            return;
        }
        HashMap map = new HashMap();
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q3 = (C0729q2) it.next();
            com.android.tools.r8.graph.W0 w0D = c0729q3.d(c0684j2);
            if (w0D == null) {
                com.android.tools.r8.graph.W0 w0T2 = ((C3733i) this.a.f()).e(c0729q3, c0684j2).t();
                if (w0T2 != null && !w0T2.s1()) {
                    return;
                }
            } else {
                M5 m5J = w0D.i1().j();
                if (m5J != null && !(m5J instanceof C3302wf)) {
                    if (!d && !(m5J instanceof C2823q20)) {
                        throw new AssertionError();
                    }
                    C0684j2 c0684j2B = m5J.a().b();
                    if (c0729q2.getType().a(c0729q3.getType()) || ((w0T = ((C3733i) this.a.f()).d(c0729q2, c0684j2B).t()) != null && !w0T.getAccessFlags().i())) {
                        ((List) map.computeIfAbsent(C3894f2.a().c(c0684j2B), new Function() { // from class: com.android.tools.r8.internal.J5$$ExternalSyntheticLambda2
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return J5.a((C2173hh) obj);
                            }
                        })).add(c0729q3);
                    }
                }
            }
        }
        if (map.isEmpty()) {
            return;
        }
        Map.Entry entry = null;
        for (Map.Entry entry2 : map.entrySet()) {
            List list = (List) entry2.getValue();
            if (entry == null || list.size() > ((List) entry.getValue()).size()) {
                entry = entry2;
            }
        }
        if (!d && entry == null) {
            throw new AssertionError();
        }
        C0684j2 c0684j3 = (C0684j2) ((C2173hh) entry.getKey()).a();
        List list2 = (List) entry.getValue();
        ArrayList<com.android.tools.r8.graph.o5> arrayList = new ArrayList();
        Iterator it2 = list2.iterator();
        while (it2.hasNext()) {
            com.android.tools.r8.graph.o5 o5VarF = ((C0729q2) it2.next()).f(c0684j2);
            if (o5VarF != null) {
                arrayList.add(o5VarF);
            }
        }
        if (!d && arrayList.isEmpty()) {
            throw new AssertionError();
        }
        com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) arrayList.iterator().next();
        int iG = o5Var.getAccessFlags().g();
        for (com.android.tools.r8.graph.o5 o5Var2 : arrayList) {
            if (o5Var2.getAccessFlags().g() != iG) {
                return;
            }
            if (!c0729q2.getType().a(o5Var2.t()) && !o5Var2.e().getAccessFlags().m()) {
                return;
            }
        }
        C0684j2 c0684j2A = this.a.a().a(c0729q2.d, c0684j3.h, c0684j3.f);
        if (((C3733i) this.a.f()).e(c0729q2, c0684j2A).x()) {
            o5Var.a(a(c0684j2A, o5Var), this.a);
            c.a(o5Var.e(), new C2823q20(c0684j2A));
            C0684j2 c0684j2A2 = this.a.a().a(c0729q2.d, c0684j2.h, c0684j2.f);
            com.android.tools.r8.graph.W0 w0A = o5Var.e().a(c0684j2A2);
            if (w0A.getAccessFlags().h()) {
                w0A.getAccessFlags().a();
            }
            c0729q2.b(w0A);
            this.b.a(AbstractC2734ot.a((Iterable) arrayList, new InterfaceC3010sj() { // from class: com.android.tools.r8.internal.J5$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((com.android.tools.r8.graph.o5) obj).getReference();
                }
            }), c0684j2A2, o5Var.getReference());
            if (!d && ((C3733i) this.a.f()).a((AbstractC0744s2) c0684j2)) {
                throw new AssertionError();
            }
            Iterator it3 = list2.iterator();
            while (it3.hasNext()) {
                com.android.tools.r8.graph.W0 w0E = ((C0729q2) it3.next()).e(c0684j2);
                if (!d && w0E == null) {
                    throw new AssertionError();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(C2173hh c2173hh) {
        return new ArrayList();
    }

    private static com.android.tools.r8.graph.W a(C0684j2 c0684j2, com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.graph.W wA1 = o5Var.e().a1();
        boolean z = false;
        if (wA1.z0()) {
            C0753u c0753uK = wA1.K();
            ArrayList arrayList = new ArrayList();
            boolean z2 = false;
            for (B7 b7 : c0753uK.I0()) {
                if (b7.H() && b7.l().T() != c0684j2) {
                    I7 i7L = b7.l();
                    boolean z3 = d;
                    if (!z3 && !i7L.L()) {
                        throw new AssertionError();
                    }
                    if (!z3 && i7L.V()) {
                        throw new AssertionError();
                    }
                    if (!z3 && !i7L.T().b(c0684j2)) {
                        throw new AssertionError();
                    }
                    arrayList.add(new I7(i7L.U(), c0684j2, false));
                    z2 = true;
                } else {
                    arrayList.add(b7);
                }
            }
            return z2 ? new C0753u(c0684j2.e, c0753uK.L0(), c0753uK.K0(), arrayList, c0753uK.Q0(), c0753uK.J0(), Position.UNKNOWN) : c0753uK;
        }
        if (wA1.B0()) {
            C0772w0 c0772w0P = wA1.P();
            com.android.tools.r8.dex.code.B1[] b1Arr = new com.android.tools.r8.dex.code.B1[c0772w0P.i.length];
            int i = 0;
            while (true) {
                com.android.tools.r8.dex.code.B1[] b1Arr2 = c0772w0P.i;
                if (i >= b1Arr2.length) {
                    break;
                }
                com.android.tools.r8.dex.code.B1 b1 = b1Arr2[i];
                b1.getClass();
                if ((b1 instanceof com.android.tools.r8.dex.code.Y1) && b1.j().s() != c0684j2) {
                    com.android.tools.r8.dex.code.Y1 y1J = b1.j();
                    com.android.tools.r8.dex.code.Y1 y1 = new com.android.tools.r8.dex.code.Y1(y1J.e, c0684j2, y1J.f, y1J.g, y1J.h, y1J.i, y1J.j);
                    y1.g(y1J.u());
                    b1Arr[i] = y1;
                } else {
                    if ((b1 instanceof com.android.tools.r8.dex.code.Z1) && b1.k().s() != c0684j2) {
                        com.android.tools.r8.dex.code.Z1 z1K = b1.k();
                        com.android.tools.r8.dex.code.Z1 z1 = new com.android.tools.r8.dex.code.Z1(z1K.f, z1K.e, c0684j2);
                        z1.g(z1K.u());
                        b1Arr[i] = z1;
                    } else {
                        b1Arr[i] = b1;
                    }
                    i++;
                }
                z = true;
                i++;
            }
            return z ? new C0772w0(c0772w0P.d, c0772w0P.e, c0772w0P.f, b1Arr, c0772w0P.g, c0772w0P.h, c0772w0P.H0()) : c0772w0P;
        }
        throw new C3405y10("Unexpected code object of type " + wA1.getClass().getTypeName());
    }
}
