package com.android.tools.r8.naming;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.G5;
import com.android.tools.r8.graph.R2;
import com.android.tools.r8.graph.U2;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.D3;
import com.android.tools.r8.utils.G3;
import com.android.tools.r8.utils.y3;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.naming.x, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3669x {
    static final /* synthetic */ boolean h = true;
    private final C0702m a;
    private final B5 b;
    private final S e;
    private final IdentityHashMap c = new IdentityHashMap();
    private final IdentityHashMap d = new IdentityHashMap();
    private final IdentityHashMap f = new IdentityHashMap();
    private final IdentityHashMap g = new IdentityHashMap();

    C3669x(C0702m c0702m, B5 b5, S s) {
        this.a = c0702m;
        this.b = b5;
        this.e = s;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Q0 b(C0767v2 c0767v2) {
        return new Q0(this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C3672z c(C0767v2 c0767v2) {
        return new C3672z(this.a, this.e);
    }

    private void d() {
        G5.a(this.a).a(((C3733i) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.naming.x$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((AbstractC0727q0) obj);
            }
        });
    }

    final C3666v a(List list, y3 y3Var) {
        y3Var.a("reserve-names");
        d();
        y3Var.c();
        y3Var.a("rename-definitions");
        Iterator it = new C3668w(this).a(list).iterator();
        while (it.hasNext()) {
            a((Set) it.next());
        }
        a();
        b();
        y3Var.c();
        y3Var.a("rename-references");
        c();
        y3Var.c();
        return new C3666v(this.c);
    }

    private void b() {
        if (this.a.Y().D0().x()) {
            ((C3733i) this.a.f()).b(new Consumer() { // from class: com.android.tools.r8.naming.x$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C0754u0) obj);
                }
            });
        }
    }

    private void c() {
        ((C3733i) this.a.f()).v().a(new Consumer() { // from class: com.android.tools.r8.naming.x$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((R2) obj);
            }
        });
    }

    private Q0 a(C0767v2 c0767v2) {
        return (Q0) this.d.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.naming.x$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC0727q0 abstractC0727q0) {
        C0767v2 c0767v2;
        AbstractC0727q0 abstractC0727q0B;
        C0767v2 c0767v3 = abstractC0727q0.f;
        if (c0767v3 == null) {
            c0767v2 = this.a.a().Z1;
        } else {
            c0767v2 = (C0767v2) this.f.getOrDefault(c0767v3, abstractC0727q0.d);
        }
        if (c0767v2 != abstractC0727q0.d || abstractC0727q0.e0()) {
            C0767v2 c0767v4 = (C0767v2) this.f.put(abstractC0727q0.d, c0767v2);
            if (!h && c0767v4 != null) {
                throw new AssertionError();
            }
        }
        Q0 q0A = a(c0767v2);
        for (com.android.tools.r8.graph.T0 t0 : abstractC0727q0.W0()) {
            C0759u2 c0759u2A = this.e.a(abstractC0727q0, t0);
            if (c0759u2A != null) {
                C0759u2 c0759u2 = t0.getReference().f;
                C0767v2 c0767v5 = t0.getReference().h;
                q0A.a(c0759u2A, c0759u2);
                if (c0759u2A != t0.getReference().f) {
                    this.c.put(t0.getReference(), c0759u2A);
                }
            }
        }
        if (abstractC0727q0.isInterface()) {
            IdentityHashMap identityHashMap = this.g;
            C0767v2 c0767v6 = abstractC0727q0.d;
            Set setC = OU.c();
            setC.add(q0A);
            identityHashMap.put(c0767v6, setC);
        }
        for (C0767v2 c0767v7 : abstractC0727q0.H0()) {
            if (c0767v7 != this.a.a().Z1) {
                Q0 q0A2 = a((C0767v2) this.f.getOrDefault(c0767v7, c0767v7));
                if (q0A2 != q0A) {
                    q0A.a(q0A2);
                }
                if (abstractC0727q0.e0() && (abstractC0727q0B = this.a.b(abstractC0727q0.a0(), c0767v7)) != null && abstractC0727q0B.isInterface()) {
                    ((Set) this.g.get(c0767v7)).add(q0A);
                }
            }
        }
        if (c0767v2 == abstractC0727q0.d && abstractC0727q0.e0()) {
            a(abstractC0727q0.a0(), q0A);
        }
    }

    private void a(C0729q2 c0729q2, final Q0 q0) {
        ((C3733i) this.a.f()).a(c0729q2, new G3() { // from class: com.android.tools.r8.naming.x$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return this.f$0.a(q0, (C0767v2) obj, (AbstractC0727q0) obj2, (Boolean) obj3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ D3 a(Q0 q0, C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0, Boolean bool) {
        Set set;
        if (bool.booleanValue() && abstractC0727q0.I1() && (set = (Set) this.g.get(c0767v2)) != null) {
            set.add(q0);
        }
        return D3.e();
    }

    private void a() {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        ((G5) G5.a(this.a).a()).a(((C3733i) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.naming.x$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(identityHashMap, (AbstractC0727q0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Map map, AbstractC0727q0 abstractC0727q0) {
        C3672z c3672zD;
        boolean z = h;
        if (!z && abstractC0727q0.isInterface()) {
            throw new AssertionError();
        }
        C0767v2 c0767v2 = abstractC0727q0.f;
        if (c0767v2 == null) {
            c3672zD = new C3672z(this.a, this.e);
        } else {
            c3672zD = ((C3672z) map.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.naming.x$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.c((C0767v2) obj);
                }
            })).clone();
        }
        IdentityHashMap identityHashMap = this.f;
        C0767v2 c0767v3 = abstractC0727q0.d;
        final C3672z c3672zA = c3672zD.a((Q0) this.d.get((C0767v2) identityHashMap.getOrDefault(c0767v3, c0767v3)));
        if (abstractC0727q0.e0()) {
            C0729q2 c0729q2A0 = abstractC0727q0.a0();
            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.naming.x$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(c3672zA, (m5) obj);
                }
            };
            c0729q2A0.getClass();
            c0729q2A0.e(consumer, CM.b());
        }
        if (!z && map.containsKey(abstractC0727q0.d)) {
            throw new AssertionError();
        }
        map.put(abstractC0727q0.d, c3672zA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0754u0 c0754u0) {
        for (com.android.tools.r8.graph.T0 t0 : c0754u0.W0()) {
            C0759u2 c0759u2A = this.e.a(c0754u0, t0);
            if (c0759u2A != null && c0759u2A != t0.getReference().f) {
                this.c.put(t0.getReference(), c0759u2A);
            }
        }
    }

    private void a(Set set) {
        final Q0 q0 = new Q0(this.a);
        final Q0 q1 = new Q0(this.a);
        Iterator it = set.iterator();
        while (it.hasNext()) {
            AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) it.next();
            IdentityHashMap identityHashMap = this.f;
            C0767v2 c0767v2 = abstractC0727q0.d;
            Q0 q2 = (Q0) this.d.get((C0767v2) identityHashMap.getOrDefault(c0767v2, c0767v2));
            if (q2 != null) {
                q1.a(q2);
                Set set2 = (Set) this.g.get(abstractC0727q0.d);
                if (!h && set2 == null) {
                    throw new AssertionError();
                }
                set2.forEach(new Consumer() { // from class: com.android.tools.r8.naming.x$$ExternalSyntheticLambda9
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C3669x.a(q1, q0, (Q0) obj);
                    }
                });
            }
        }
        final C3672z c3672z = new C3672z(this.a, this.e, q1);
        Iterator it2 = set.iterator();
        while (it2.hasNext()) {
            AbstractC0727q0 abstractC0727q1 = (AbstractC0727q0) it2.next();
            if (abstractC0727q1.e0()) {
                if (!h && !abstractC0727q1.isInterface()) {
                    throw new AssertionError();
                }
                C0729q2 c0729q2A0 = abstractC0727q1.a0();
                Consumer consumer = new Consumer() { // from class: com.android.tools.r8.naming.x$$ExternalSyntheticLambda10
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(c3672z, q0, (m5) obj);
                    }
                };
                c0729q2A0.getClass();
                c0729q2A0.e(consumer, CM.b());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Q0 q0, Q0 q1, Q0 q2) {
        q0.a(q2);
        q2.b(q1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C3672z c3672z, m5 m5Var) {
        C0759u2 c0759u2A = c3672z.a(m5Var);
        if (c0759u2A != m5Var.getReference().f) {
            this.c.put(m5Var.getReference(), c0759u2A);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C3672z c3672z, Q0 q0, m5 m5Var) {
        C0759u2 c0759u2A = c3672z.a(m5Var);
        if (c0759u2A != m5Var.getReference().f) {
            this.c.put(m5Var.getReference(), c0759u2A);
        }
        C0759u2 c0759u2 = m5Var.getReference().f;
        C0767v2 c0767v2 = m5Var.getReference().h;
        q0.a(c0759u2A, c0759u2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(R2 r2) {
        ((U2) r2).b(new Consumer() { // from class: com.android.tools.r8.naming.x$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((Y0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Y0 y0) {
        com.android.tools.r8.graph.T0 t0S;
        if (this.c.containsKey(y0)) {
            return;
        }
        C0702m c0702m = this.a;
        c0702m.getClass();
        C0729q2 c0729q2A = C0729q2.a(c0702m.c(y0.e));
        if (c0729q2A == null || (t0S = ((C3733i) this.a.f()).a(c0729q2A, y0).s()) == null || t0S.getReference() == y0 || !this.c.containsKey(t0S.getReference())) {
            return;
        }
        IdentityHashMap identityHashMap = this.c;
        identityHashMap.put(y0, (C0759u2) identityHashMap.get(t0S.getReference()));
    }
}
