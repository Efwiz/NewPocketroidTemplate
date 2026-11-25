package com.android.tools.r8.internal;

import com.android.tools.r8.dex.C0563k$$ExternalSyntheticLambda21;
import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0612b4;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.AbstractC3735i1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Wj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1476Wj {
    static final /* synthetic */ boolean a = true;

    AbstractC1476Wj() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C1237Oh a(C1237Oh c1237Oh) {
        return c1237Oh;
    }

    public abstract com.android.tools.r8.graph.Y0 a(com.android.tools.r8.graph.Y0 y0);

    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public abstract com.android.tools.r8.graph.Y0 b(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.Y0 y0);

    public abstract C0767v2 a(C0767v2 c0767v2);

    public abstract KE a(C0684j2 c0684j2, C0684j2 c0684j3, EnumC1953et enumC1953et, AbstractC1476Wj abstractC1476Wj);

    protected abstract KE a(C0684j2 c0684j2, C0684j2 c0684j3, EnumC1953et enumC1953et, AbstractC1476Wj abstractC1476Wj, InterfaceC1450Vj interfaceC1450Vj);

    protected abstract C1237Oh a(com.android.tools.r8.graph.Y0 y0, AbstractC1476Wj abstractC1476Wj, InterfaceC1424Uj interfaceC1424Uj);

    public C1319Rg a() {
        return null;
    }

    public abstract String a(String str);

    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public abstract C0684j2 c(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2);

    public abstract C0767v2 b(AbstractC1476Wj abstractC1476Wj, C0767v2 c0767v2);

    public C2141hE b() {
        return null;
    }

    public abstract Iterable b(C0767v2 c0767v2);

    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public abstract C0767v2 a(AbstractC1476Wj abstractC1476Wj, C0767v2 c0767v2);

    public C2449lE c() {
        return null;
    }

    public abstract com.android.tools.r8.graph.proto.k d(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2);

    public DH d() {
        return null;
    }

    public boolean e(C0684j2 c0684j2) {
        return g();
    }

    public boolean f() {
        return !(this instanceof C3254w2);
    }

    public abstract boolean g();

    public abstract boolean h();

    public abstract boolean i();

    public static AbstractC1476Wj e() {
        return C0929Dl.j();
    }

    public final C0684j2 b(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjK = this;
        while (abstractC1476WjK.i()) {
            DH dhD = abstractC1476WjK.d();
            c0684j2 = dhD.g(c0684j2);
            abstractC1476WjK = dhD.k();
        }
        if (a || abstractC1476WjK.h()) {
            return c0684j2;
        }
        throw new AssertionError();
    }

    public final C0684j2 c(C0684j2 c0684j2) {
        return c((AbstractC1476Wj) null, c0684j2);
    }

    public final C0767v2 d(C0767v2 c0767v2) {
        return a(e(), c0767v2);
    }

    public final C0684j2 a(C0684j2 c0684j2) {
        return a((AbstractC1476Wj) null, c0684j2);
    }

    public final C0767v2 c(C0767v2 c0767v2) {
        return b(e(), c0767v2);
    }

    public final C0684j2 d(C0684j2 c0684j2) {
        if (a || e(c0684j2)) {
            return (C0684j2) a(c0684j2, (C0684j2) null, (EnumC1953et) null, (AbstractC1476Wj) null).b();
        }
        throw new AssertionError();
    }

    public final C0684j2 a(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjK = this;
        while (abstractC1476WjK.i() && abstractC1476WjK != abstractC1476Wj) {
            DH dhD = abstractC1476WjK.d();
            c0684j2 = dhD.f(c0684j2);
            abstractC1476WjK = dhD.k();
        }
        if (a || (abstractC1476Wj != null ? abstractC1476WjK == abstractC1476Wj : abstractC1476WjK.h())) {
            return c0684j2;
        }
        throw new AssertionError();
    }

    public final com.android.tools.r8.graph.Y0 c(com.android.tools.r8.graph.Y0 y0) {
        return c((AbstractC1476Wj) null, y0);
    }

    public com.android.tools.r8.graph.Y0 c(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.Y0 y0) {
        return (com.android.tools.r8.graph.Y0) d(abstractC1476Wj, y0).b();
    }

    public final C1237Oh d(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.Y0 y0) {
        return a(y0, abstractC1476Wj, new InterfaceC1424Uj() { // from class: com.android.tools.r8.internal.Wj$$ExternalSyntheticLambda10
            @Override // com.android.tools.r8.internal.InterfaceC1424Uj
            public final C1237Oh a(C1237Oh c1237Oh) {
                return AbstractC1476Wj.a(c1237Oh);
            }
        });
    }

    public final com.android.tools.r8.graph.Y0 b(com.android.tools.r8.graph.Y0 y0) {
        return b((AbstractC1476Wj) null, y0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C1946em c1946em, com.android.tools.r8.graph.Y0 y0, Object obj) {
        c1946em.a(b((AbstractC1476Wj) null, y0), obj);
    }

    public final IdentityHashMap b(Map map, final BiFunction biFunction) {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Wj$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(identityHashMap, biFunction, (C0767v2) obj, obj2);
            }
        });
        return identityHashMap;
    }

    public final com.android.tools.r8.graph.W0 a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.graph.Q0 q0) {
        boolean z = a;
        if (!z && w0 == com.android.tools.r8.graph.W0.t) {
            throw new AssertionError();
        }
        C0684j2 c0684j2C = c((AbstractC1476Wj) null, w0.getReference());
        AbstractC0727q0 abstractC0727q0C = q0.c(c0684j2C.e);
        if (!z && abstractC0727q0C == null) {
            throw new AssertionError();
        }
        com.android.tools.r8.graph.W0 w0C = abstractC0727q0C.c(c0684j2C);
        if (z || w0C != null) {
            return w0C;
        }
        throw new AssertionError();
    }

    public final com.android.tools.r8.graph.o5 a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.Q0 q0) {
        C0684j2 c0684j2C = c((AbstractC1476Wj) null, o5Var.getReference());
        return c0684j2C.a(C0729q2.a(q0.a((AbstractC0642e2) c0684j2C)));
    }

    public boolean a(AbstractC0744s2 abstractC0744s2, AbstractC0744s2 abstractC0744s3) {
        if (a || abstractC0744s2 != abstractC0744s3) {
            return false;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Map map, BiFunction biFunction, C0767v2 c0767v2, Object obj) {
        C0767v2 c0767v2A = a(e(), c0767v2);
        Object obj2 = map.get(c0767v2A);
        if (obj2 != null) {
            obj = biFunction.apply(obj, obj2);
        }
        map.put(c0767v2A, obj);
    }

    public final KE a(C0684j2 c0684j2, C0684j2 c0684j3, EnumC1953et enumC1953et) {
        return a(c0684j2, c0684j3, enumC1953et, (AbstractC1476Wj) null);
    }

    public final AbstractC0744s2 a(AbstractC0744s2 abstractC0744s2) {
        return (AbstractC0744s2) abstractC0744s2.a(new Function() { // from class: com.android.tools.r8.internal.Wj$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.d((C0767v2) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.internal.Wj$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.c((com.android.tools.r8.graph.Y0) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.internal.Wj$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.d((C0684j2) obj);
            }
        });
    }

    public final void a(Iterable iterable) {
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            com.android.tools.r8.graph.O0 o0 = (com.android.tools.r8.graph.O0) it.next();
            AbstractC0744s2 reference = o0.getReference();
            boolean z = o0.D0() && o0.w0().f.X();
            if (!a && !z && a(reference) != reference) {
                throw new AssertionError();
            }
        }
    }

    public final void a(AbstractC3735i1 abstractC3735i1, com.android.tools.r8.utils.A1 a1) {
        ArrayList<AbstractC0744s2> arrayList = new ArrayList();
        abstractC3735i1.c(new C0563k$$ExternalSyntheticLambda21(arrayList), a1);
        abstractC3735i1.b(new Wj$$ExternalSyntheticLambda2(arrayList), a1);
        abstractC3735i1.a(new Wj$$ExternalSyntheticLambda3(arrayList), a1);
        for (AbstractC0744s2 abstractC0744s2 : arrayList) {
            if (abstractC0744s2.x0()) {
                com.android.tools.r8.graph.Y0 y0T0 = abstractC0744s2.t0();
                if (!a && b((AbstractC1476Wj) null, y0T0) != y0T0) {
                    throw new AssertionError();
                }
            } else if (abstractC0744s2.y0()) {
                C0684j2 c0684j2V0 = abstractC0744s2.v0();
                if (!a && c((AbstractC1476Wj) null, c0684j2V0) != c0684j2V0) {
                    throw new AssertionError();
                }
            } else {
                boolean z = a;
                if (!z && !(abstractC0744s2 instanceof C0767v2)) {
                    throw new AssertionError();
                }
                C0767v2 c0767v2W0 = abstractC0744s2.w0();
                if (!z && a(e(), c0767v2W0) != c0767v2W0) {
                    throw new AssertionError();
                }
            }
        }
    }

    public final IdentityHashMap a(Map map, final com.android.tools.r8.graph.Q0 q0) {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        final C2363kB c2363kB = new C2363kB(q0, this, null);
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Wj$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(q0, c2363kB, identityHashMap, (C0721p0) obj, (CN) obj2);
            }
        });
        return identityHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.Q0 q0, C2363kB c2363kB, Map map, C0721p0 c0721p0, CN cn) {
        for (com.android.tools.r8.graph.o5 o5Var : cn.a(q0, this)) {
            ((CN) map.computeIfAbsent(c2363kB.a(c0721p0, o5Var), new Function() { // from class: com.android.tools.r8.internal.Wj$$ExternalSyntheticLambda11
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return AbstractC1476Wj.a((C0721p0) obj);
                }
            })).add(o5Var);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CN a(C0721p0 c0721p0) {
        return CN.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Map map, Map map2, AbstractC0744s2 abstractC0744s2, Object obj) {
        AbstractC0744s2 abstractC0744s2A = a(abstractC0744s2, (AbstractC1476Wj) null);
        List list = (List) map.get(abstractC0744s2A);
        if (list != null) {
            list.add(obj);
            return;
        }
        Object objPut = map2.put(abstractC0744s2A, obj);
        if (objPut != null) {
            ArrayList arrayList = new ArrayList(2);
            arrayList.add(objPut);
            arrayList.add(obj);
            map.put(abstractC0744s2A, arrayList);
            map2.remove(abstractC0744s2A);
        }
    }

    public final AbstractC0744s2 a(AbstractC0744s2 abstractC0744s2, final AbstractC1476Wj abstractC1476Wj) {
        return (AbstractC0744s2) abstractC0744s2.a(new Function() { // from class: com.android.tools.r8.internal.Wj$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(abstractC1476Wj, (C0767v2) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.internal.Wj$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b(abstractC1476Wj, (com.android.tools.r8.graph.Y0) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.internal.Wj$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.c(abstractC1476Wj, (C0684j2) obj);
            }
        });
    }

    public final Set a(Set set) {
        Set setB = com.android.tools.r8.utils.T2.b(set.size());
        Iterator it = set.iterator();
        while (it.hasNext()) {
            setB.add(a((AbstractC0744s2) it.next(), (AbstractC1476Wj) null));
        }
        return setB;
    }

    public final IdentityHashMap a(Map map, final BiFunction biFunction) {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        final IdentityHashMap identityHashMap2 = new IdentityHashMap();
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Wj$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(identityHashMap2, identityHashMap, (AbstractC0744s2) obj, obj2);
            }
        });
        identityHashMap2.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Wj$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AbstractC1476Wj.a(biFunction, identityHashMap, (AbstractC0744s2) obj, (List) obj2);
            }
        });
        return identityHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BiFunction biFunction, Map map, AbstractC0744s2 abstractC0744s2, List list) {
        Object objApply = biFunction.apply(abstractC0744s2, list);
        if (objApply != null) {
            map.put(abstractC0744s2, objApply);
        }
    }

    public final AbstractC2178hm a(Map map) {
        final C1946em c1946emE = AbstractC2178hm.e();
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Wj$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c1946emE, (com.android.tools.r8.graph.Y0) obj, obj2);
            }
        });
        return c1946emE.a();
    }

    public final void a(C0702m c0702m, C0612b4 c0612b4) {
        Collection<C0729q2> collectionE = c0702m.f().e();
        Set setC = OU.c();
        Set setC2 = OU.c();
        for (C0729q2 c0729q2 : c0612b4.d()) {
            Iterator<com.android.tools.r8.graph.T0> it = c0729q2.W0().iterator();
            while (it.hasNext()) {
                setC.add(it.next().getReference());
            }
            Iterator<com.android.tools.r8.graph.W0> it2 = c0729q2.M1().iterator();
            while (it2.hasNext()) {
                setC2.add(it2.next().getReference());
            }
        }
        for (C0729q2 c0729q3 : collectionE) {
            if (!c0702m.f().g().b(c0729q3)) {
                for (com.android.tools.r8.graph.T0 t0 : c0729q3.W0()) {
                    if (!t0.N0()) {
                        com.android.tools.r8.graph.Y0 y0A = a(t0.getReference());
                        if (!a && !setC.contains(y0A)) {
                            throw new AssertionError("Unable to map field `" + t0.getReference().r0() + "` back to original program");
                        }
                    }
                }
                for (com.android.tools.r8.graph.W0 w0 : c0729q3.M1()) {
                    if (!w0.N0()) {
                        C0684j2 c0684j2A = a((AbstractC1476Wj) null, w0.getReference());
                        if (!a && !setC2.contains(c0684j2A)) {
                            throw new AssertionError("Method could not be mapped back: " + w0.r0() + ", originalMethod: " + c0684j2A.r0());
                        }
                    }
                }
            }
        }
    }
}
