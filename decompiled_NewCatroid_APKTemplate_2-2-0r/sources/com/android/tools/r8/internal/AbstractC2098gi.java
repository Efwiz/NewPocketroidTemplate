package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.ir.optimize.C3585t;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.AbstractC3962s0;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gi, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2098gi {
    static final /* synthetic */ boolean h = true;
    final C0702m a;
    final C2409kl b;
    final com.android.tools.r8.graph.o5 c;
    final com.android.tools.r8.ir.optimize.info.n d;
    private C0827Af e;
    private IdentityHashMap f;
    final IdentityHashMap g = new IdentityHashMap();

    AbstractC2098gi(C0702m c0702m, C2409kl c2409kl, com.android.tools.r8.ir.optimize.info.n nVar) {
        this.a = c0702m;
        this.b = c2409kl;
        this.d = nVar;
        this.c = c2409kl.h();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List c(com.android.tools.r8.graph.T0 t0) {
        return new ArrayList();
    }

    C1162Ln a() {
        return null;
    }

    abstract void a(com.android.tools.r8.graph.T0 t0, AbstractC1182Mh abstractC1182Mh, S10 s10);

    abstract boolean a(com.android.tools.r8.graph.T0 t0);

    IW b() {
        return null;
    }

    abstract boolean b(com.android.tools.r8.graph.T0 t0);

    final C0827Af d() {
        if (this.e == null) {
            this.e = new C0827Af(this.b, 1);
        }
        return this.e;
    }

    private IdentityHashMap c() {
        boolean z;
        boolean z2;
        IdentityHashMap identityHashMap = new IdentityHashMap();
        ArrayDeque arrayDequeA = AbstractC3962s0.a(this.b.j());
        while (!arrayDequeA.isEmpty()) {
            C3332x4 c3332x4 = (C3332x4) arrayDequeA.removeFirst();
            boolean zContainsKey = identityHashMap.containsKey(c3332x4);
            AbstractC2744p abstractC2744p = (AbstractC2744p) identityHashMap.computeIfAbsent(c3332x4, new Function() { // from class: com.android.tools.r8.internal.gi$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return AbstractC2098gi.a((C3332x4) obj);
                }
            });
            abstractC2744p.getClass();
            if (!(abstractC2744p instanceof C2198i10)) {
                if (!h && !abstractC2744p.d()) {
                    throw new AssertionError();
                }
                InterfaceC1547Yy interfaceC1547YyB = abstractC2744p.b();
                int size = zContainsKey ? interfaceC1547YyB.size() : -1;
                Iterator<C3332x4> it = c3332x4.y().iterator();
                while (true) {
                    z = true;
                    if (!it.hasNext()) {
                        z2 = false;
                        break;
                    }
                    AbstractC2744p abstractC2744p2 = (AbstractC2744p) identityHashMap.getOrDefault(it.next(), C1501Xf.e());
                    abstractC2744p2.getClass();
                    if (!(abstractC2744p2 instanceof C1501Xf)) {
                        if (abstractC2744p2 instanceof C2198i10) {
                            z2 = true;
                            break;
                        }
                        if (!h && !(abstractC2744p2 instanceof C2092gc)) {
                            throw new AssertionError();
                        }
                        AbstractC2744p abstractC2744p3 = (AbstractC2744p) interfaceC1547YyB;
                        abstractC2744p3.getClass();
                        if (!(abstractC2744p3 instanceof C2092gc)) {
                            interfaceC1547YyB = new C2092gc();
                        }
                        interfaceC1547YyB.a().a(abstractC2744p2.a());
                    }
                }
                if (!z2) {
                    if (!zContainsKey) {
                        Iterator<AbstractC2412ko> it2 = c3332x4.p().iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                AbstractC2744p abstractC2744pF = it2.next().f(this.a, this.c);
                                abstractC2744pF.getClass();
                                if (!(abstractC2744pF instanceof C1501Xf)) {
                                    if (abstractC2744pF instanceof C2198i10) {
                                        break;
                                    }
                                    AbstractC2744p abstractC2744p4 = (AbstractC2744p) interfaceC1547YyB;
                                    abstractC2744p4.getClass();
                                    if (!(abstractC2744p4 instanceof C2092gc)) {
                                        interfaceC1547YyB = new C2092gc();
                                    }
                                    interfaceC1547YyB.a().a(abstractC2744pF.a());
                                }
                            }
                        }
                    } else if (!h) {
                        com.android.tools.r8.graph.o5 o5Var = this.c;
                        Iterator<AbstractC2412ko> it3 = c3332x4.p().iterator();
                        while (it3.hasNext()) {
                            AbstractC2744p abstractC2744pF2 = it3.next().f(this.a, o5Var);
                            if (!h) {
                                abstractC2744pF2.getClass();
                                if (abstractC2744pF2 instanceof C2198i10) {
                                    throw new AssertionError();
                                }
                            }
                            abstractC2744pF2.getClass();
                            if (!(abstractC2744pF2 instanceof C1501Xf)) {
                                for (com.android.tools.r8.graph.T0 t0 : abstractC2744pF2.a().e()) {
                                    if (!h && !interfaceC1547YyB.a(t0)) {
                                        throw new AssertionError();
                                    }
                                }
                            }
                        }
                    }
                    z = z2;
                    break;
                }
                z = z2;
                break;
                if (z) {
                    identityHashMap.put(c3332x4, C2198i10.e());
                } else {
                    if (interfaceC1547YyB != abstractC2744p) {
                        identityHashMap.put(c3332x4, interfaceC1547YyB.a());
                    }
                    if (interfaceC1547YyB.size() == size) {
                        continue;
                    } else if (!h && interfaceC1547YyB.size() <= size) {
                        throw new AssertionError();
                    }
                }
                arrayDequeA.addAll(c3332x4.z());
            }
        }
        return identityHashMap;
    }

    final void a(com.android.tools.r8.graph.T0 t0, AbstractC2412ko abstractC2412ko, InterfaceC0989Fn interfaceC0989Fn) {
        ((List) this.g.computeIfAbsent(t0, new Function() { // from class: com.android.tools.r8.internal.gi$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC2098gi.c((com.android.tools.r8.graph.T0) obj);
            }
        })).add(new C2019fi(abstractC2412ko, interfaceC0989Fn));
    }

    void a(C3585t c3585t) {
        AbstractC2412ko next;
        C3733i c3733i = (C3733i) this.a.f();
        boolean z = true;
        for (C3332x4 c3332x4 : this.b.d) {
            if (c3332x4.z().size() >= 2) {
                z = false;
            }
            for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
                if (abstractC2412ko.L1()) {
                    AbstractC1182Mh abstractC1182MhN = abstractC2412ko.N();
                    com.android.tools.r8.graph.m5 m5VarQ = c3733i.c(abstractC1182MhN.getField()).q();
                    if (m5VarQ != null) {
                        com.android.tools.r8.graph.T0 t0E = m5VarQ.e();
                        if (a(t0E)) {
                            a(t0E, abstractC1182MhN, C2351k10.b());
                        } else if ((this instanceof IW) && m5VarQ.a().B1() && b(t0E)) {
                            a(t0E, abstractC1182MhN, C2351k10.b());
                        }
                    }
                } else if ((this instanceof C1162Ln) && abstractC2412ko.a(this.a.a())) {
                    a().a(abstractC2412ko.Z(), this.b.o());
                }
            }
        }
        List<C3332x4> listE = this.b.e();
        for (Map.Entry entry : this.g.entrySet()) {
            com.android.tools.r8.graph.T0 t0 = (com.android.tools.r8.graph.T0) entry.getKey();
            List list = (List) entry.getValue();
            if (list.size() <= 1) {
                C2019fi c2019fi = (C2019fi) com.android.tools.r8.utils.V1.a(list);
                AbstractC2412ko abstractC2412ko2 = c2019fi.a;
                if (abstractC2412ko2.W1()) {
                    a().b(t0, c2019fi.b);
                } else {
                    AbstractC1182Mh abstractC1182MhN2 = abstractC2412ko2.N();
                    if (!z) {
                        C0827Af c0827AfD = d();
                        C3332x4 c3332x4H = abstractC1182MhN2.h();
                        c0827AfD.getClass();
                        Iterator<T> it = listE.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (!c0827AfD.a((C3332x4) it.next(), c3332x4H)) {
                                }
                            }
                        }
                    }
                    if (c3585t.b(t0) || !abstractC1182MhN2.value().V()) {
                        C3332x4 c3332x4H2 = abstractC1182MhN2.h();
                        Iterator<C3332x4> it2 = c3332x4H2.y().iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                C3332x4 next2 = it2.next();
                                if (this.f == null) {
                                    this.f = c();
                                }
                                if (((AbstractC2744p) this.f.get(next2)).a(t0)) {
                                }
                            } else {
                                InterfaceC2488lo interfaceC2488loR = c3332x4H2.R();
                                while (true) {
                                    if (interfaceC2488loR.hasNext() && (next = interfaceC2488loR.next()) != abstractC1182MhN2) {
                                        if (next.f(this.a, this.c).a(t0)) {
                                        }
                                    }
                                }
                            }
                            if (this instanceof IW) {
                                b().a(t0, abstractC1182MhN2.value(), c3585t.a(t0));
                            }
                        }
                    }
                    a(t0, abstractC1182MhN2, abstractC1182MhN2.value());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2744p a(C3332x4 c3332x4) {
        return C1501Xf.e();
    }
}
