package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1271Pl;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.internal.AbstractC2964s2;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.C1946em;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.naming.C3605b;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class N2 extends AbstractC0681j0 {
    static final /* synthetic */ boolean n = true;
    private final IdentityHashMap i;
    private final AbstractC2178hm j;
    private final AbstractC2178hm k;
    private final AbstractC1271Pl l;
    private final AbstractC1271Pl m;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends AbstractC0674i0 {
        static final /* synthetic */ boolean m = true;
        private AbstractC1271Pl i;
        private Map j;
        private final ArrayList k;
        private final Set l;

        a(C0612b4 c0612b4) {
            super(c0612b4);
            this.k = new ArrayList();
            this.l = OU.c();
            C0601a4 c0601a4J = c0612b4.j();
            this.i = c0601a4J.a().values();
            this.j = c0601a4J.b();
            a((AbstractCollection) c0601a4J.c().values());
        }

        @Override // com.android.tools.r8.graph.AbstractC0674i0
        public final a a() {
            return this;
        }

        @Override // com.android.tools.r8.graph.AbstractC0674i0
        final AbstractC0674i0 d() {
            return this;
        }

        @Override // com.android.tools.r8.graph.AbstractC0674i0
        /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
        public N2 b() {
            if (!this.k.isEmpty()) {
                this.i = AbstractC1639am.g().a((Iterable) this.i).a((Iterable) this.k).a();
                this.k.clear();
            }
            IdentityHashMap identityHashMap = new IdentityHashMap(this.i.size() + ((ArrayList) c()).size());
            AbstractC1271Pl abstractC1271PlA = this.i;
            if (a(abstractC1271PlA, identityHashMap)) {
                C1507Xl c1507XlG = AbstractC1639am.g();
                AbstractC2963s10 it = this.i.iterator();
                while (it.hasNext()) {
                    C0754u0 c0754u0 = (C0754u0) it.next();
                    if (!this.l.contains(c0754u0.getType())) {
                        c1507XlG.a(c0754u0);
                    }
                }
                abstractC1271PlA = c1507XlG.a();
            }
            AbstractC1271Pl abstractC1271Pl = abstractC1271PlA;
            a(c(), identityHashMap);
            C3605b c3605b = this.d;
            C0696l0 c0696l0 = this.f;
            AbstractC2178hm abstractC2178hmA = AbstractC2178hm.a(identityHashMap);
            Map map = this.j;
            return new N2(c3605b, c0696l0, abstractC2178hmA, map instanceof AbstractC2178hm ? (AbstractC2178hm) map : AbstractC2178hm.a(map), AbstractC1639am.a(c()), abstractC1271Pl, AbstractC1639am.a(this.b), this.c, this.g, this.e);
        }

        public final void a(C0754u0 c0754u0) {
            this.k.add(c0754u0);
        }

        @Override // com.android.tools.r8.graph.AbstractC0674i0
        public final void b(C0729q2 c0729q2) {
            a(c0729q2);
            this.l.add(c0729q2.d);
            if (this.j.containsKey(c0729q2.d)) {
                if (!(this.j instanceof IdentityHashMap)) {
                    this.j = new IdentityHashMap(this.j);
                }
                this.j.remove(c0729q2.d);
            }
        }

        public final a a(Set set) {
            if (!m && set == null) {
                throw new AssertionError();
            }
            this.i = AbstractC1639am.a(set);
            this.k.clear();
            return this;
        }

        public a a(Collection<C0635d2> collection) {
            final C1946em c1946emE = AbstractC2178hm.e();
            collection.forEach(new Consumer() { // from class: com.android.tools.r8.graph.N2$a$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    N2.a.a(c1946emE, (C0635d2) obj);
                }
            });
            this.j = c1946emE.a();
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(C1946em c1946em, C0635d2 c0635d2) {
            c1946em.a(c0635d2.d, c0635d2);
        }

        private boolean a(Collection collection, IdentityHashMap identityHashMap) {
            Iterator it = collection.iterator();
            boolean z = false;
            while (it.hasNext()) {
                p5 p5Var = (p5) it.next();
                if (p5Var.e0() || !this.l.contains(p5Var.getType())) {
                    p5 p5Var2 = (p5) identityHashMap.put(p5Var.getType(), p5Var);
                    if (!m && p5Var2 != null) {
                        throw new AssertionError("Class " + p5Var2.getType().toString() + " was already present.");
                    }
                } else {
                    z = true;
                }
            }
            return z;
        }

        private a(N2 n2) {
            super(n2);
            this.k = new ArrayList();
            this.l = OU.c();
            this.i = n2.m;
            this.j = n2.k;
        }
    }

    private N2(C3605b c3605b, C0696l0 c0696l0, AbstractC2178hm abstractC2178hm, AbstractC2178hm abstractC2178hm2, AbstractC1639am abstractC1639am, AbstractC1271Pl abstractC1271Pl, AbstractC1639am abstractC1639am2, com.android.tools.r8.utils.A1 a1, C0759u2 c0759u2, com.android.tools.r8.utils.y3 y3Var) {
        super(c3605b, c0696l0, abstractC1639am2, a1, c0759u2, y3Var);
        this.i = new IdentityHashMap();
        this.j = abstractC2178hm;
        this.k = abstractC2178hm2;
        this.l = abstractC1639am;
        this.m = abstractC1271Pl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, C0729q2 c0729q2) {
        consumer.accept(c0729q2.d);
    }

    @Override // com.android.tools.r8.graph.AbstractC0681j0
    public final N2 b() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0681j0
    public final C0729q2 d(C0767v2 c0767v2) {
        p5 p5Var = (p5) this.j.get(c0767v2);
        if (p5Var == null) {
            return null;
        }
        return p5Var.a0();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.graph.Q0
    public final O f(C0767v2 c0767v2) {
        if (!n && !c0767v2.O0()) {
            throw new AssertionError("Cannot lookup definition for type: " + c0767v2);
        }
        C0635d2 c0635d2 = (C0635d2) this.k.get(c0767v2);
        p5 p5Var = (p5) this.j.get(c0767v2);
        if (c0635d2 == null && p5Var == 0) {
            return M.a();
        }
        if (c0635d2 == null || p5Var != 0) {
            return c0635d2 == null ? ((AbstractC0727q0) p5Var).t0() : O.L().a(c0635d2).a(p5Var).a();
        }
        return c0635d2;
    }

    @Override // com.android.tools.r8.graph.AbstractC0681j0
    final Collection h() {
        return this.l;
    }

    @Override // com.android.tools.r8.graph.AbstractC0681j0
    public final N2 i() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0681j0
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public a c() {
        return new a();
    }

    public final AbstractC1271Pl k() {
        return this.m;
    }

    public Collection<C0635d2> l() {
        return this.k.values();
    }

    public final void m() {
        this.i.clear();
        AbstractC2963s10 it = this.l.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            for (W0 w0 : c0729q2.k.f(new Predicate() { // from class: com.android.tools.r8.graph.N2$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((W0) obj).F1();
                }
            })) {
                W wA1 = w0.a1();
                boolean z = n;
                if (!z && wA1 == null) {
                    throw new AssertionError();
                }
                if (wA1.z0()) {
                    wA1 = wA1.K();
                } else if (wA1.F0()) {
                    continue;
                }
                W0 w1 = (W0) this.i.put(wA1, w0);
                if (!z && w1 != null) {
                    throw new AssertionError();
                }
            }
        }
    }

    public final String toString() {
        return "DexApplication (direct)";
    }

    @Override // com.android.tools.r8.graph.AbstractC0681j0
    public final void a(final Consumer consumer) {
        this.k.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.N2$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                consumer.accept((C0767v2) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.AbstractC0681j0
    public final void b(final Consumer consumer) {
        this.l.forEach(new Consumer() { // from class: com.android.tools.r8.graph.N2$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                N2.a(consumer, (C0729q2) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.AbstractC0681j0, com.android.tools.r8.graph.Q0
    public final AbstractC0727q0 c(C0767v2 c0767v2) {
        if (!n && !c0767v2.O0()) {
            throw new AssertionError("Cannot lookup definition for type: " + c0767v2);
        }
        if (this.d.I0) {
            C0635d2 c0635d2 = (C0635d2) this.k.get(c0767v2);
            if (c0635d2 != null) {
                return c0635d2;
            }
            F f = (p5) this.j.get(c0767v2);
            if (f != null) {
                return ((AbstractC0727q0) f).t0();
            }
            return null;
        }
        F f2 = (p5) this.j.get(c0767v2);
        if (f2 != null && f2.e0()) {
            return ((AbstractC0727q0) f2).t0();
        }
        C0635d2 c0635d3 = (C0635d2) this.k.get(c0767v2);
        if (c0635d3 != null) {
            return c0635d3;
        }
        if (f2 == null) {
            return null;
        }
        return ((AbstractC0727q0) f2).t0();
    }

    public final void a(N2 n2, AbstractC1476Wj abstractC1476Wj) {
        if (!n) {
            Iterator<E> it = n2.l.iterator();
            while (it.hasNext()) {
                C0767v2 type = ((C0729q2) it.next()).getType();
                C0767v2 c0767v2A = AbstractC2964s2.a(abstractC1476Wj, type);
                if (!c0767v2A.R0() && c0767v2A != type && (c(type) != null || c(c0767v2A) == null)) {
                    if (!n && c(type).d != c0767v2A && c(c0767v2A) == null) {
                        throw new AssertionError("The lens and app is inconsistent");
                    }
                }
            }
        }
        if (n) {
            return;
        }
        m();
    }
}
