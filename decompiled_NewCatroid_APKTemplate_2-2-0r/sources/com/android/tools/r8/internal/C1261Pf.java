package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.shaking.C3733i;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Pf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1261Pf {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final ConcurrentHashMap b = new ConcurrentHashMap();
    private final CN c = CN.c();

    public C1261Pf(C0702m c0702m) {
        this.a = c0702m;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(S10 s10, S10 s11) {
        return s11 == s10;
    }

    public final void a(Set set) {
        final HashMap map = new HashMap();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Pf$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C1261Pf.a(map, (com.android.tools.r8.graph.o5) obj);
                }
            };
            c0729q2.getClass();
            c0729q2.h(consumer, CM.b());
        }
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Pf$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((AbstractC0717o2) obj, (CN) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, com.android.tools.r8.graph.o5 o5Var) {
        ((CN) map.computeIfAbsent(o5Var.z(), com.android.tools.r8.utils.Z1.a(new N2$$ExternalSyntheticLambda12()))).add(o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC0717o2 abstractC0717o2, CN cn) {
        if (cn.size() == 1) {
            com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) ((AbstractC0748t0) cn.iterator().next());
            if (AbstractC2913rL.a(this.a, o5Var)) {
                this.c.add(o5Var);
            }
        }
    }

    private HashSet a(com.android.tools.r8.graph.o5 o5Var, C3406y2 c3406y2, final S10 s10) {
        int iC;
        if ((o5Var.e().C1() && s10.Q()) || o5Var.e().c(this.a.a()) || !AbstractC2913rL.a(this.a, o5Var, c3406y2.J2()) || !s10.w().w() || s10.z() || s10.C()) {
            return null;
        }
        HashSet hashSet = new HashSet();
        for (AbstractC2412ko abstractC2412ko : s10.h0()) {
            if (abstractC2412ko.Y1()) {
                AbstractC1305Qs abstractC1305QsB0 = abstractC2412ko.b0();
                com.android.tools.r8.graph.o5 o5VarS = ((C3733i) this.a.f()).h(abstractC1305QsB0.S2()).s();
                if (o5VarS != null) {
                    boolean zA = true;
                    if (o5VarS.e().f.N()) {
                        zA = true ^ AbstractC2913rL.a(this.a, o5VarS);
                    } else if (this.c.b(o5VarS)) {
                        if (!d && !AbstractC2913rL.a(this.a, o5VarS)) {
                            throw new AssertionError();
                        }
                        zA = false;
                    }
                    if (!zA && (iC = com.android.tools.r8.utils.V1.c(abstractC1305QsB0.J2(), new Predicate() { // from class: com.android.tools.r8.internal.Pf$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return C1261Pf.a(s10, (S10) obj);
                        }
                    })) >= 0 && AbstractC2913rL.a(this.a, o5VarS, iC)) {
                        hashSet.add(new RE(iC, o5VarS.getReference()));
                    }
                }
            }
            return null;
        }
        return hashSet;
    }

    public final void a() {
        C1292Qf c1292QfA = C1292Qf.a(this.a, this.b);
        com.android.tools.r8.utils.O3 o3E = com.android.tools.r8.utils.O3.e();
        for (C1318Rf c1318Rf : c1292QfA.a()) {
            if (c1318Rf.g()) {
                o3E.c(c1318Rf);
            }
        }
        while (o3E.c()) {
            C1318Rf c1318Rf2 = (C1318Rf) o3E.f();
            o3E.a((Iterable) c1318Rf2.d());
            c1318Rf2.a();
            c1292QfA.b(c1318Rf2);
        }
        final com.android.tools.r8.utils.O3 o3D = com.android.tools.r8.utils.O3.d((Iterable) c1292QfA.a());
        while (o3D.c()) {
            while (o3D.c()) {
                C1318Rf c1318Rf3 = (C1318Rf) o3D.g();
                if (!d && !c1292QfA.c(c1318Rf3)) {
                    throw new AssertionError();
                }
                c1318Rf3.h();
                if (c1318Rf3.e().isEmpty()) {
                    c1318Rf3.j();
                    c1318Rf3.d().forEach(new Consumer() { // from class: com.android.tools.r8.internal.Pf$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            o3D.c((C1318Rf) obj);
                        }
                    });
                    c1318Rf3.a();
                    c1292QfA.b(c1318Rf3);
                }
            }
            c1292QfA.a(new Consumer() { // from class: com.android.tools.r8.internal.Pf$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    o3D.c((C1318Rf) obj);
                }
            });
        }
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var, C2409kl c2409kl) {
        boolean zA = true;
        if (o5Var.e().f.N()) {
            zA = true ^ AbstractC2913rL.a(this.a, o5Var);
        } else if (this.c.b(o5Var)) {
            if (!d && !AbstractC2913rL.a(this.a, o5Var)) {
                throw new AssertionError();
            }
            zA = false;
        }
        if (zA) {
            return;
        }
        Iterator itB = c2409kl.b();
        while (itB.hasNext()) {
            C3406y2 c3406y2 = (C3406y2) itB.next();
            HashSet hashSetA = a(o5Var, c3406y2, c3406y2.b());
            if (hashSetA != null && !hashSetA.isEmpty()) {
                RE re = new RE(c3406y2.J2(), o5Var.getReference());
                if (!d && this.b.containsKey(re)) {
                    throw new AssertionError();
                }
                this.b.put(re, hashSetA);
            }
        }
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var) {
        for (int i = 0; i < o5Var.e().h1(); i++) {
            this.b.remove(new RE(i, o5Var.getReference()));
        }
    }
}
