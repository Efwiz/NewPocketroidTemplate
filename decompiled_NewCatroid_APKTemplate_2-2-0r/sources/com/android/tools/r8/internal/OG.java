package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class OG extends TF {
    static final /* synthetic */ boolean e = true;
    final C0702m b;
    final IdentityHashMap c = new IdentityHashMap();
    private C2239iV d;

    public OG(C0702m c0702m) {
        this.b = c0702m;
    }

    private AbstractC1639am b(C2688oE c2688oE) {
        C0729q2 c0729q2A;
        final Set<C0729q2> setC = OU.c();
        C3733i c3733i = (C3733i) this.b.f();
        AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) c2688oE.iterator().next();
        com.android.tools.r8.utils.G3 g3 = new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.OG$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return OG.a(setC, (C0767v2) obj, (AbstractC0727q0) obj2, (AbstractC0727q0) obj3);
            }
        };
        c3733i.getClass();
        while (abstractC0727q0 != null && abstractC0727q0.n1() != null) {
            AbstractC0727q0 abstractC0727q0C = c3733i.c(abstractC0727q0.n1());
            if (((com.android.tools.r8.utils.D3) g3.a(abstractC0727q0.n1(), abstractC0727q0C, abstractC0727q0)).f()) {
                break;
            }
            abstractC0727q0 = abstractC0727q0C;
        }
        NG ng = new NG(this, c2688oE);
        ng.a(c2688oE);
        for (C0729q2 c0729q2 : setC) {
            if (c0729q2.p1()) {
                ng.b(c0729q2.X1());
            }
        }
        ng.c();
        C2688oE c2688oE2 = new C2688oE();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = c2688oE.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q3 = (C0729q2) it.next();
            if (ng.a(c0729q3)) {
                CN cnB = CN.b();
                com.android.tools.r8.graph.o5 o5VarA = this.d.a(c0729q3);
                while (true) {
                    if (o5VarA == null || !cnB.add(o5VarA)) {
                        c0729q2A = null;
                        break;
                    }
                    if (o5VarA.e().u1() && setC.contains(o5VarA.a())) {
                        c0729q2A = o5VarA.a();
                        break;
                    }
                    o5VarA = this.d.a(o5VarA);
                }
                if (c0729q2A != null) {
                    ((C2688oE) linkedHashMap.computeIfAbsent(c0729q2A, com.android.tools.r8.utils.Z1.a(new BU$$ExternalSyntheticLambda2()))).a(c0729q3);
                }
            } else {
                c2688oE2.a(c0729q3);
            }
        }
        return AbstractC1639am.g().a(c2688oE2).a((Iterable) linkedHashMap.values()).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0702m k() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.TF
    public final /* bridge */ /* synthetic */ Collection a(C2688oE c2688oE, Object obj) {
        return a(c2688oE);
    }

    @Override // com.android.tools.r8.internal.WL
    public final void d() {
        this.c.clear();
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoClassInitializerCycles";
    }

    @Override // com.android.tools.r8.internal.WL
    public final boolean j() {
        return !this.b.Y().O0().f();
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0063  */
    /* JADX WARN: Code duplicated, block: B:23:0x006d  */
    /* JADX WARN: Code duplicated, block: B:58:0x0093 A[SYNTHETIC] */
    public final LinkedList a(C2688oE c2688oE) {
        Iterator it;
        C2688oE c2688oE2;
        AbstractC1639am<C2688oE> abstractC1639amB = b(c2688oE);
        LinkedList<C2688oE> linkedList = new LinkedList();
        for (C2688oE<C0729q2> c2688oE3 : abstractC1639amB) {
            LinkedList linkedList2 = new LinkedList();
            NG ng = new NG(this, c2688oE3);
            for (C0729q2 c0729q2 : c2688oE3) {
                if (!e && ng.a(c0729q2)) {
                    throw new AssertionError();
                }
                if (!c0729q2.p1()) {
                    it = linkedList2.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            c2688oE2 = new C2688oE();
                            linkedList2.add(c2688oE2);
                            break;
                        }
                        C2688oE<C0729q2> c2688oE4 = (C2688oE) it.next();
                        do {
                            if (ng.a(c0729q2, c0729q2)) {
                            }
                        } while (!ng.a(c0729q2, c0729q2));
                    }
                } else {
                    ng.a(AbstractC1639am.c(c0729q2));
                    ng.b(c0729q2.X1());
                    ng.c();
                    if (!ng.a(c0729q2)) {
                        it = linkedList2.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                c2688oE2 = new C2688oE();
                                linkedList2.add(c2688oE2);
                                break;
                            }
                            C2688oE<C0729q2> c2688oE5 = (C2688oE) it.next();
                            for (C0729q2 c0729q3 : c2688oE5) {
                                if (ng.a(c0729q3, c0729q2) && !ng.a(c0729q2, c0729q3)) {
                                }
                            }
                            c2688oE2 = c2688oE5;
                            break;
                        }
                    }
                    c2688oE2 = null;
                }
                if (c2688oE2 != null) {
                    c2688oE2.a(c0729q2);
                }
            }
            linkedList.addAll(linkedList2);
        }
        WL.a(linkedList);
        for (C2688oE c2688oE6 : linkedList) {
            Iterator it2 = c2688oE6.iterator();
            while (it2.hasNext()) {
                this.c.put((C0729q2) it2.next(), c2688oE6);
            }
        }
        Iterator it3 = c2688oE.iterator();
        while (it3.hasNext()) {
            C0729q2 c0729q4 = (C0729q2) it3.next();
            if (((C2688oE) this.c.get(c0729q4)) == c2688oE) {
                this.c.remove(c0729q4);
            }
        }
        return linkedList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(Set set, C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1) {
        if (abstractC0727q0 != null && abstractC0727q0.e0()) {
            set.add(abstractC0727q0.a0());
            return com.android.tools.r8.utils.D3.e();
        }
        return com.android.tools.r8.utils.D3.d();
    }

    @Override // com.android.tools.r8.internal.TF
    public final Object a(Collection collection, ExecutorService executorService) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            C2688oE c2688oE = (C2688oE) it.next();
            Iterator it2 = c2688oE.iterator();
            while (it2.hasNext()) {
                this.c.put((C0729q2) it2.next(), c2688oE);
            }
        }
        this.d = C2239iV.a(this.b).a(executorService).a();
        return null;
    }
}
