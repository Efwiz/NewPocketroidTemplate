package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.C2173hh;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.utils.C3879c3;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class O0 {
    static final /* synthetic */ boolean h = true;
    private final C0702m a;
    private final B5 b;
    private final L c;
    private final ExecutorService d;
    private final ArrayList e = new ArrayList();
    private final Map f;
    private final X3 g;

    O0(C0702m c0702m, B5 b5, L l, ExecutorService executorService, Map map, X3 x3) {
        this.a = c0702m;
        this.b = b5;
        this.c = l;
        this.d = executorService;
        this.f = map;
        this.g = x3;
    }

    /* JADX WARN: Code duplicated, block: B:120:0x01b4 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:123:0x010c A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v59 */
    /* JADX WARN: Type inference failed for: r1v65 */
    /* JADX WARN: Type inference failed for: r1v67 */
    /* JADX WARN: Type inference failed for: r1v88 */
    public final W3 a() {
        boolean zC;
        AbstractC3714f abstractC3714fB;
        ?? r1;
        boolean zC2;
        AbstractC3714f abstractC3714fB2;
        ((C0680j) this.a.f()).b().c.a("Find consequent items for -if rules...");
        try {
            Map map = this.f;
            if (map != null && !map.isEmpty()) {
                Iterator it = this.f.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    C3712e3 c3712e3 = (C3712e3) ((C2173hh) entry.getKey()).a();
                    Set set = (Set) entry.getValue();
                    final com.android.tools.r8.utils.A1.p.b bVar = this.a.Y().n1.G0;
                    final ArrayList arrayList = new ArrayList();
                    C0702m c0702m = this.a;
                    for (final C0729q2 c0729q2 : c3712e3.a(c0702m, this.b, ((C0680j) c0702m.f()).d())) {
                        if (a(c0729q2)) {
                            int i = 1;
                            if (this.a.Y().n1.F0) {
                                bVar.a++;
                            }
                            if (c3712e3.d().a(c0729q2) == c3712e3.e()) {
                                zC = false;
                            } else {
                                if ((c3712e3.a().a(c0729q2.e) && c3712e3.k().b(c0729q2.e)) && (abstractC3714fB2 = C3707d4.b(c0729q2, c3712e3)) != null) {
                                    this.g.a(abstractC3714fB2);
                                    if (c3712e3.c().a(c0729q2.d)) {
                                        zC = c3712e3.o() ? this.g.c(c0729q2, c3712e3) : true;
                                    }
                                }
                                zC = false;
                            }
                            if (zC) {
                                set.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.O0$$ExternalSyntheticLambda5
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        this.f$0.a(c0729q2, bVar, arrayList, (C3712e3) obj);
                                    }
                                });
                            }
                            if (this.a.g0() != null) {
                                Iterator it2 = this.a.g0().b(c0729q2.d).iterator();
                                while (it2.hasNext()) {
                                    final C0729q2 c0729q2A = C0729q2.a(((C0680j) this.a.f()).d((C0767v2) it2.next()));
                                    if (c0729q2A != null) {
                                        if (this.a.Y().n1.F0) {
                                            bVar.a += i;
                                        }
                                        if ((c3712e3.d().a(c0729q2A) != c3712e3.e() ? i : 0) != 0) {
                                            if (((c3712e3.a().a(c0729q2A.e) && c3712e3.k().b(c0729q2A.e)) ? i : 0) != 0 && (abstractC3714fB = C3707d4.b(c0729q2A, c3712e3)) != null) {
                                                this.g.a(abstractC3714fB);
                                                if (c3712e3.c().a(c0729q2A.d)) {
                                                    if (c3712e3.o()) {
                                                        zC2 = this.g.c(c0729q2A, c3712e3);
                                                    } else {
                                                        r1 = i;
                                                    }
                                                }
                                                if (r1 != 0) {
                                                    r1 = zC2;
                                                    set.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.O0$$ExternalSyntheticLambda6
                                                        @Override // java.util.function.Consumer
                                                        public final void accept(Object obj) {
                                                            this.f$0.a(c0729q2A, c0729q2, bVar, arrayList, (C3712e3) obj);
                                                        }
                                                    });
                                                    i = 1;
                                                } else {
                                                    r1 = zC2;
                                                }
                                            }
                                        }
                                        r1 = 0;
                                        if (r1 != 0) {
                                            r1 = zC2;
                                            set.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.O0$$ExternalSyntheticLambda6
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj) {
                                                    this.f$0.a(c0729q2A, c0729q2, bVar, arrayList, (C3712e3) obj);
                                                }
                                            });
                                            i = 1;
                                        } else {
                                            r1 = zC2;
                                        }
                                    } else if (h) {
                                        continue;
                                    } else {
                                        L.a aVarI = this.c.i();
                                        aVarI.getClass();
                                        if ((aVarI == L.a.f ? i : 0) == 0) {
                                            throw new AssertionError();
                                        }
                                    }
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    if (set.size() == arrayList.size()) {
                        it.remove();
                    } else if (!arrayList.isEmpty()) {
                        set.removeAll(arrayList);
                    }
                }
                C3879c3.a(this.e);
            }
            ((C0680j) this.a.f()).b().c.c();
            return this.g.a();
        } catch (Throwable th) {
            ((C0680j) this.a.f()).b().c.c();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2, com.android.tools.r8.utils.A1.p.b bVar, List list, C3712e3 c3712e3) {
        boolean zA = c3712e3.c().a(c0729q2.d);
        boolean z = h;
        if (!z && !zA) {
            throw new AssertionError();
        }
        if (c3712e3.o()) {
            boolean zC = this.g.c(c0729q2, c3712e3);
            if (!z && !zC) {
                throw new AssertionError();
            }
        }
        if (this.a.Y().n1.F0) {
            bVar.b++;
        }
        if (a(c3712e3, c0729q2, c0729q2) && AbstractC2734ot.c(c3712e3.u.x())) {
            list.add(c3712e3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2, C0729q2 c0729q3, com.android.tools.r8.utils.A1.p.b bVar, List list, C3712e3 c3712e3) {
        boolean zA = c3712e3.c().a(c0729q2.d);
        boolean z = h;
        if (!z && !zA) {
            throw new AssertionError();
        }
        if (c3712e3.o()) {
            boolean zC = this.g.c(c0729q3, c3712e3);
            if (!z && !zC) {
                throw new AssertionError();
            }
        }
        if (this.a.Y().n1.F0) {
            bVar.b++;
        }
        if (a(c3712e3, c0729q2, c0729q3) && AbstractC2734ot.c(c3712e3.u.x())) {
            list.add(c3712e3);
        }
    }

    private boolean a(C0729q2 c0729q2) {
        if (this.c.d(c0729q2)) {
            return true;
        }
        Iterator<com.android.tools.r8.graph.T0> it = c0729q2.W0().iterator();
        while (it.hasNext()) {
            if (it.next().X0().j()) {
                return true;
            }
        }
        Iterator<com.android.tools.r8.graph.W0> it2 = c0729q2.M1().iterator();
        while (it2.hasNext()) {
            if (it2.next().i1().C()) {
                return true;
            }
        }
        return false;
    }

    private boolean a(C3712e3 c3712e3, final AbstractC0727q0 abstractC0727q0, final AbstractC0727q0 abstractC0727q1) {
        List<C3775o3> listJ = c3712e3.j();
        if (listJ.isEmpty()) {
            a(c3712e3, AbstractC1048Hm.c(abstractC0727q0.getReference()));
            return true;
        }
        final ArrayList<AbstractC0735r0> arrayList = new ArrayList();
        Set setC = OU.c();
        AbstractC2734ot.a(abstractC0727q1.c(new Predicate() { // from class: com.android.tools.r8.shaking.O0$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(arrayList, abstractC0727q1, abstractC0727q0, (com.android.tools.r8.graph.T0) obj);
            }
        }), (Collection) setC);
        AbstractC2734ot.a(abstractC0727q1.h(new Predicate() { // from class: com.android.tools.r8.shaking.O0$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(abstractC0727q0, (com.android.tools.r8.graph.W0) obj);
            }
        }), (Collection) setC);
        if (!arrayList.isEmpty()) {
            for (C3775o3 c3775o3 : listJ) {
                if (c3775o3.i().a()) {
                    for (AbstractC0735r0 abstractC0735r0 : arrayList) {
                        if (this.g.a(c3775o3, abstractC0735r0)) {
                            c3712e3.a(abstractC0735r0.getReference());
                        }
                    }
                }
            }
        }
        if (setC.size() < listJ.size()) {
            return false;
        }
        for (Set set : OU.a(listJ.size(), setC)) {
            final Collection collection = (Collection) com.android.tools.r8.graph.O0.a(set.stream(), new Function() { // from class: com.android.tools.r8.shaking.O0$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return AbstractC0735r0.a(abstractC0727q1, (com.android.tools.r8.graph.T0) obj);
                }
            }).collect(Collectors.toList());
            final Collection collection2 = (Collection) com.android.tools.r8.graph.O0.b(set.stream(), new Function() { // from class: com.android.tools.r8.shaking.O0$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return AbstractC0748t0.a(abstractC0727q1, (com.android.tools.r8.graph.W0) obj);
                }
            }).collect(Collectors.toList());
            if (listJ.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.shaking.O0$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.a(collection, collection2, (C3775o3) obj);
                }
            })) {
                a(c3712e3, AbstractC1048Hm.c(abstractC0727q0.getReference()));
                if (AbstractC2734ot.c(c3712e3.u.x())) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(AbstractC0727q0 abstractC0727q0, com.android.tools.r8.graph.W0 w0) {
        return (this.c.a(w0) || this.c.b(w0) || w0.i1().C()) && this.a.E().a((AbstractC1476Wj) null, w0.getReference()).e == abstractC0727q0.d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Collection collection, Collection collection2, C3775o3 c3775o3) {
        return this.g.a(c3775o3, collection) || this.g.b(c3775o3, collection2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(List list, AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1, com.android.tools.r8.graph.T0 t0) {
        boolean zA;
        if (this.c.i().b()) {
            zA = t0.U0();
        } else {
            zA = t0.a(this.a.a());
        }
        if (!zA) {
            return (this.c.a(t0) || this.c.b(t0) || t0.X0().j()) && this.a.E().a(t0.getReference()).e == abstractC0727q1.d;
        }
        list.add(AbstractC0735r0.a(abstractC0727q0, t0));
        return false;
    }

    private void a(C3712e3 c3712e3, Set set) {
        C0722p1 c0722p1A = this.a.a();
        C3712e3 c3712e3A = c3712e3.a(c0722p1A, set);
        if (this.c.i().c() && !c3712e3.C()) {
            this.g.a(this.d, this.e, c3712e3A.a(c0722p1A), (C3712e3) null);
            R0 r0A = c3712e3A.a(c0722p1A, Q0.NEVER_CLASS_INLINE);
            if (r0A != null) {
                this.g.a(this.d, this.e, r0A, (C3712e3) null);
            }
            R0 r0A2 = c3712e3A.a(c0722p1A, Q0.NEVER);
            if (r0A2 != null) {
                this.g.a(this.d, this.e, r0A2, (C3712e3) null);
            }
            this.g.a(this.d, this.e, c3712e3A.b(c0722p1A), (C3712e3) null);
        }
        this.g.a(this.d, this.e, c3712e3A.u, c3712e3A);
        c3712e3.D();
    }
}
