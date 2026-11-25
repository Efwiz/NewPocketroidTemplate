package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3689b;
import com.android.tools.r8.shaking.C3733i;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1695bT {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final com.android.tools.r8.shaking.L2 b;
    private final ZS c;

    public C1695bT(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.Y().D0();
        this.c = (ZS) c0702m.Y().n1.r.apply(c0702m);
    }

    public final C2079gT a(com.android.tools.r8.graph.N2.a aVar, ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        y3Var.a("Repackage classes");
        C2079gT c2079gTA = a(aVar, executorService);
        y3Var.c();
        return c2079gTA;
    }

    public static void a(C0702m c0702m) {
        ArrayList arrayListA = new WS(c0702m).a(((C3733i) c0702m.f()).e());
        c0702m.a(((com.android.tools.r8.graph.N2.a) ((C3733i) c0702m.f()).b().b().c().a((AbstractCollection) new ArrayList(arrayListA))).b(), new XS(c0702m));
    }

    private C2079gT a(com.android.tools.r8.graph.N2.a aVar, ExecutorService executorService) {
        Collection collectionA;
        com.android.tools.r8.utils.A1.n nVarO = this.b.o();
        nVarO.getClass();
        if (nVarO == com.android.tools.r8.utils.A1.n.a) {
            return null;
        }
        C3011sk c3011skL = C3011sk.l();
        HashMap map = new HashMap();
        HashSet hashSet = new HashSet();
        com.android.tools.r8.graph.z5<com.android.tools.r8.graph.r5> z5VarA = com.android.tools.r8.graph.z5.a(this.a);
        Iterator it = z5VarA.iterator();
        while (it.hasNext()) {
            com.android.tools.r8.graph.r5<C0729q2> r5Var = (com.android.tools.r8.graph.r5) it.next();
            if (((YS) this.c).a(r5Var)) {
                for (C0729q2 c0729q2 : r5Var) {
                    if (!((C3733i) this.a.f()).a(this.a, c0729q2)) {
                        c3011skL.put(c0729q2.getType(), c0729q2.getType());
                    }
                }
                Iterator it2 = r5Var.iterator();
                while (it2.hasNext()) {
                    a((C0729q2) it2.next(), r5Var, r5Var.b(), c3011skL);
                }
                map.put(r5Var.b(), r5Var.b());
                hashSet.add(r5Var.b());
                it.remove();
            }
        }
        IdentityHashMap identityHashMap = new IdentityHashMap();
        for (com.android.tools.r8.graph.r5 r5Var2 : z5VarA) {
            C1926eT c1926eT = new C1926eT(this.a, r5Var2);
            if (c1926eT.b()) {
                collectionA = r5Var2.a();
            } else {
                c1926eT.a(executorService);
                collectionA = c1926eT.a();
            }
            identityHashMap.put(r5Var2, collectionA);
            if (collectionA.size() != r5Var2.a().size()) {
                hashSet.add(r5Var2.b());
            }
        }
        for (com.android.tools.r8.graph.r5 r5Var3 : z5VarA) {
            Collection collection = (Collection) identityHashMap.get(r5Var3);
            if (!collection.isEmpty()) {
                if (!d && ((YS) this.c).a(r5Var3)) {
                    throw new AssertionError();
                }
                String strA = ((YS) this.c).a(r5Var3, hashSet);
                Iterator it3 = collection.iterator();
                while (it3.hasNext()) {
                    a((C0729q2) it3.next(), r5Var3, strA, c3011skL);
                }
                hashSet.add(strA);
                String strB = r5Var3.b();
                if (collection.size() != r5Var3.a().size()) {
                    strA = r5Var3.b();
                }
                map.put(strB, strA);
            }
        }
        c3011skL.entrySet().removeIf(new Predicate() { // from class: com.android.tools.r8.internal.bT$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1695bT.a((Map.Entry) obj);
            }
        });
        if (c3011skL.isEmpty()) {
            return null;
        }
        C2003fT c2003fT = new C2003fT();
        aVar.a((AbstractCollection) new ArrayList(new C1615aT(this.a, c3011skL, c2003fT).a(((C3733i) this.a.f()).e())));
        C2079gT c2079gTA = c2003fT.a(this.a, map);
        new C3689b(c2079gTA, this.a.E()).a(aVar.c());
        return c2079gTA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Map.Entry entry) {
        return entry.getKey() == entry.getValue();
    }

    private void a(C0729q2 c0729q2, com.android.tools.r8.graph.r5 r5Var, String str, C3011sk c3011sk) {
        com.android.tools.r8.graph.J3 j3D1;
        C0767v2 c0767v2A;
        if (c3011sk.containsKey(c0729q2.getType())) {
            return;
        }
        C0729q2 c0729q3 = null;
        C0729q2 c0729q2A = (!c0729q2.r1() || (c0767v2A = c0729q2.b1().a()) == null) ? null : C0729q2.a(this.a.c(c0767v2A));
        if (c0729q2A == null && (j3D1 = c0729q2.d1()) != null && j3D1.d() != null) {
            c0729q2A = C0729q2.a(this.a.c(j3D1.d()));
        }
        if (c0729q2A == null) {
            c0729q3 = c0729q2A;
        } else if (r5Var.b(c0729q2A)) {
            a(c0729q2A, r5Var, str, c3011sk);
            c0729q3 = c0729q2A;
        }
        c3011sk.put(c0729q2.getType(), ((YS) this.c).a(c0729q2, c0729q3, str, c3011sk));
    }
}
