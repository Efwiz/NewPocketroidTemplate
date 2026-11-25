package com.android.tools.r8.naming;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.internal.C1566Zr;
import com.android.tools.r8.internal.C3011sk;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.RD;
import com.android.tools.r8.internal.Z8;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.y3;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0 {
    static final /* synthetic */ boolean j = true;
    private final C0702m a;
    private final C0722p1 b;
    private final S0 c;
    private final C3011sk d = C3011sk.l();
    private final Set e = OU.c();
    private final IdentityHashMap f = RD.b();
    private final IdentityHashMap g = RD.b();
    private final IdentityHashMap h = RD.b();
    private final IdentityHashMap i = RD.b();

    public C0(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a();
        S0 s0R = c0702m.r();
        this.c = s0R;
        if (!j && s0R == null) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C0767v2 c0767v2, Map map, Q q) {
        a(c0767v2, q, map, false);
    }

    public final B0 a(ExecutorService executorService, y3 y3Var) {
        AbstractC0727q0 abstractC0727q0D;
        AbstractC0727q0 abstractC0727q0C;
        final ArrayDeque arrayDeque = new ArrayDeque();
        final HashSet hashSet = new HashSet();
        final B5 b5A = AbstractC3633j0.a(this.a);
        y3Var.a("MappingInterfaces");
        List listA = b5A.a();
        ((ArrayList) listA).forEach(new Consumer() { // from class: com.android.tools.r8.naming.C0$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Throwable {
                this.f$0.a(arrayDeque, hashSet, b5A, (AbstractC0727q0) obj);
            }
        });
        y3Var.c();
        y3Var.a("MappingClasses");
        this.e.addAll(((C3733i) this.a.f()).d());
        b5A.a(this.b.Z1).forEach(new Consumer() { // from class: com.android.tools.r8.naming.C0$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Throwable {
                this.f$0.a(arrayDeque, hashSet, b5A, (C0767v2) obj);
            }
        });
        if (!j && !arrayDeque.isEmpty()) {
            throw new AssertionError();
        }
        y3Var.c();
        y3Var.a("MappingDefaultInterfaceMethods");
        for (String str : this.c.a()) {
            C3631j c3631jA = this.c.a(str);
            C0722p1 c0722p1 = this.b;
            C0767v2 c0767v2E = c0722p1.e(c0722p1.c(str));
            if (c0767v2E != null && ((abstractC0727q0D = ((C3733i) this.a.f()).d(c0767v2E)) == null || (abstractC0727q0D instanceof C0754u0))) {
                IdentityHashMap identityHashMap = this.g;
                Z8 z8 = C1566Zr.c;
                if (c0767v2E.e.toString().endsWith("$-CC;") && (abstractC0727q0C = this.a.c(C1566Zr.b(this.b, c0767v2E))) != null && (abstractC0727q0C instanceof C0754u0)) {
                    AbstractC2963s10 it = c3631jA.a().values().iterator();
                    while (it.hasNext()) {
                        List list = (List) it.next();
                        if (list.size() == 1) {
                            Q q = (Q) list.get(0);
                            Q.b bVar = (Q.b) q.b();
                            if (bVar.a.startsWith(abstractC0727q0C.d.r0())) {
                                C0684j2 c0684j2A = C1566Zr.a(this.b, bVar.j().a(this.b, abstractC0727q0C.d));
                                if (!j && c0684j2A.e != c0767v2E) {
                                    throw new AssertionError();
                                }
                                identityHashMap.put(c0684j2A, this.b.c(q.a()));
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }
        }
        y3Var.c();
        this.a.Y().e.a();
        y3Var.a("MinifyClasses");
        C3614d c3614dA = new C3618f(this.a, new z0(this.a, this.d, this.c.b()), AbstractC0681j0.a(new ArrayList(this.e))).a(y3Var);
        y3Var.c();
        A0 a0 = new A0(this.a, this.f);
        y3Var.a("MinifyMethods");
        T tA = new V(this.a, a0).a(listA, b5A, executorService, y3Var);
        tA.a.putAll(this.g);
        tA.a.putAll(this.h);
        y3Var.c();
        y3Var.a("MinifyFields");
        C3666v c3666vA = new C3669x(this.a, b5A, a0).a(listA, y3Var);
        c3666vA.a.putAll(this.i);
        y3Var.c();
        this.a.Y().e.a();
        B0 b0 = new B0(this.a, c3614dA, tA, c3666vA, hashSet);
        y3Var.a("MinifyIdentifiers");
        new B(this.a, b0).c(executorService);
        y3Var.c();
        return b0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ArrayDeque arrayDeque, Set set, B5 b5, AbstractC0727q0 abstractC0727q0) throws Throwable {
        c((Deque) arrayDeque, set, b5, abstractC0727q0.getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ArrayDeque arrayDeque, Set set, B5 b5, C0767v2 c0767v2) throws Throwable {
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
        if (abstractC0727q0C == null || abstractC0727q0C.isInterface()) {
            return;
        }
        c((Deque) arrayDeque, set, b5, c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void c(final Deque deque, final Set set, final B5 b5, final C0767v2 c0767v2) throws Throwable {
        C3631j c3631jA = this.c.a(c0767v2);
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
        if (abstractC0727q0C != null && (abstractC0727q0C instanceof C0754u0) && c3631jA != null) {
            this.e.add(abstractC0727q0C.m());
        }
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        if (c3631jA != null && (abstractC0727q0C == null || !abstractC0727q0C.f0())) {
            C0759u2 c0759u2C = this.b.c(c3631jA.b);
            Position position = c3631jA.c;
            if (this.d.g().containsKey(c0759u2C) && this.d.g().get(c0759u2C) != c0767v2) {
                this.a.Y().e.error(C3603a.a(c0767v2.toString(), c0759u2C.toString(), position));
            } else {
                this.d.put(c0767v2, c0759u2C);
            }
            c3631jA.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.naming.C0$$ExternalSyntheticLambda3
                @Override // com.android.tools.r8.utils.InterfaceC3905h3
                public final void accept(Object obj) {
                    this.f$0.b(c0767v2, identityHashMap, (Q) obj);
                }
            });
        } else if (abstractC0727q0C != null && this.a.Y().k1()) {
            if (this.a.Y().k1() && !((C3733i) this.a.f()).k(c0767v2)) {
                set.add(c0767v2);
            }
        } else {
            set.add(c0767v2);
        }
        Iterator it = deque.iterator();
        while (it.hasNext()) {
            Map map = (Map) it.next();
            for (AbstractC0744s2 abstractC0744s2 : map.keySet()) {
                if (abstractC0744s2.y0()) {
                    C0684j2 c0684j2V0 = abstractC0744s2.v0();
                    C0684j2 c0684j2A = this.b.a(c0767v2, c0684j2V0.h, c0684j2V0.f);
                    if (!this.f.containsKey(c0684j2A)) {
                        a(c0684j2A, (Q) map.get(abstractC0744s2), this.h);
                    } else if (abstractC0727q0C != null) {
                        W0 w0C = abstractC0727q0C.c(c0684j2A);
                        if (!j && w0C != null && !w0C.E0() && !((Q) this.f.get(c0684j2A)).a().equals(((Q) map.get(abstractC0744s2)).a())) {
                            throw new AssertionError();
                        }
                    } else {
                        continue;
                    }
                } else {
                    Y0 y0T0 = abstractC0744s2.t0();
                    AbstractC0642e2 abstractC0642e2A = this.b.a(c0767v2, y0T0.h, y0T0.f);
                    if (!this.f.containsKey(abstractC0642e2A)) {
                        a(abstractC0642e2A, (Q) map.get(abstractC0744s2), this.i);
                    }
                }
            }
        }
        if (abstractC0727q0C != null && (abstractC0727q0C.e0() || abstractC0727q0C.x1())) {
            a(c0767v2, identityHashMap, abstractC0727q0C.g.a);
        }
        if (identityHashMap.size() > 0) {
            deque.addLast(identityHashMap);
            b5.a(c0767v2, new Consumer() { // from class: com.android.tools.r8.naming.C0$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) throws Throwable {
                    this.f$0.b(deque, set, b5, (C0767v2) obj);
                }
            });
            deque.removeLast();
            return;
        }
        b5.a(c0767v2, new Consumer() { // from class: com.android.tools.r8.naming.C0$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Throwable {
                this.f$0.c(deque, set, b5, (C0767v2) obj);
            }
        });
    }

    private void a(final C0767v2 c0767v2, final IdentityHashMap identityHashMap, C0767v2[] c0767v2Arr) throws Throwable {
        for (C0767v2 c0767v3 : c0767v2Arr) {
            C3631j c3631jA = this.c.a(c0767v3);
            if (c3631jA != null) {
                c3631jA.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.naming.C0$$ExternalSyntheticLambda0
                    @Override // com.android.tools.r8.utils.InterfaceC3905h3
                    public final void accept(Object obj) {
                        this.f$0.a(c0767v2, identityHashMap, (Q) obj);
                    }
                });
            }
            AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v3);
            if (abstractC0727q0C != null) {
                a(c0767v2, identityHashMap, abstractC0727q0C.g.a);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0767v2 c0767v2, Map map, Q q) {
        a(c0767v2, q, map, true);
    }

    private void a(C0767v2 c0767v2, Q q, Map map, boolean z) {
        Q.c cVarB = q.b();
        if (!j && cVarB.e()) {
            throw new AssertionError();
        }
        if (cVarB instanceof Q.b) {
            C0684j2 c0684j2A = ((Q.b) cVarB).a(this.b, c0767v2);
            a(c0684j2A, q, z ? this.h : null);
            C0702m c0702m = this.a;
            c0702m.getClass();
            W0 w0C = c0684j2A.c(c0702m.c(c0684j2A.e));
            if (w0C == null || !w0C.f.j()) {
                map.put(c0684j2A, q);
                return;
            }
            return;
        }
        Y0 y0A = ((Q.a) cVarB).a(this.b, c0767v2);
        a(y0A, q, z ? this.i : null);
        C0702m c0702m2 = this.a;
        c0702m2.getClass();
        com.android.tools.r8.graph.T0 t0C = y0A.c(c0702m2.c(y0A.e));
        if (t0C == null || !t0C.O0()) {
            map.put(y0A, q);
        }
    }

    private void a(AbstractC0642e2 abstractC0642e2, Q q, IdentityHashMap identityHashMap) {
        if (!j && this.f.containsKey(abstractC0642e2) && !((Q) this.f.get(abstractC0642e2)).a().equals(q.a())) {
            throw new AssertionError();
        }
        this.f.put(abstractC0642e2, q);
        if (identityHashMap != null) {
            identityHashMap.put(abstractC0642e2, this.b.c(q.a()));
        }
    }
}
