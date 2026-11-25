package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2964s2;
import com.android.tools.r8.internal.JA;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.shaking.AbstractC3837z1;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.graph.a5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0606a5 extends AbstractC0614b5 {
    static final /* synthetic */ boolean i = true;
    private Z4 h;

    /* JADX INFO: renamed from: $r8$lambda$wBZ5N9SAmxJLRX-vx8hIUeo_fgc, reason: not valid java name */
    public static /* synthetic */ ArrayList m595$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc() {
        return new ArrayList();
    }

    private C0606a5(boolean z, com.android.tools.r8.shaking.M0 m0) {
        super();
        this.h = new Z4(z, m0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC1476Wj abstractC1476Wj, C0767v2 c0767v2, List list) {
        C0767v2 c0767v2A = AbstractC2964s2.a(abstractC1476Wj, c0767v2);
        if (!c0767v2A.W0()) {
            ((List) this.e.computeIfAbsent(c0767v2A, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.graph.a5$$ExternalSyntheticLambda7
                @Override // java.util.function.Supplier
                public final Object get() {
                    return C0606a5.m595$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc();
                }
            }))).addAll(list);
        } else if (!i) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(Q0 q0, C0729q2 c0729q2) {
        boolean z = i;
        if (!z && c0729q2.isInterface()) {
            throw new AssertionError();
        }
        if (!z && this.a.containsKey(c0729q2)) {
            throw new AssertionError();
        }
        if (z) {
            return;
        }
        a(c0729q2.H0(), q0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set e(C0729q2 c0729q2) {
        return OU.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set f(C0729q2 c0729q2) {
        return OU.c();
    }

    public final boolean b(C0729q2 c0729q2, C0680j c0680j) {
        boolean z = i;
        if (!z && !c0729q2.isInterface()) {
            throw new AssertionError();
        }
        if (!z && c0729q2.e.T()) {
            throw new AssertionError();
        }
        if (!this.d.add(c0729q2)) {
            return false;
        }
        c((AbstractC0727q0) c0729q2, (Q0) c0680j);
        return true;
    }

    public final void g(C0729q2 c0729q2) {
        this.a.remove(c0729q2);
        this.b.remove(c0729q2);
        this.f = null;
    }

    final void c(final Q0 q0) {
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.a5$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(q0, (C0729q2) obj, (Set) obj2);
            }
        });
        this.b.forEach(new Consumer() { // from class: com.android.tools.r8.graph.a5$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.d(q0, (C0729q2) obj);
            }
        });
        this.e.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.a5$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(q0, (C0767v2) obj, (List) obj2);
            }
        });
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            a(((C0729q2) it.next()).H0(), q0);
        }
        this.f.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.a5$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C0767v2) obj, (Set) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List b(C0767v2 c0767v2) {
        return new ArrayList();
    }

    private void b(final Q0 q0) {
        this.f = new IdentityHashMap();
        this.a.keySet().forEach(new Consumer() { // from class: com.android.tools.r8.graph.a5$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(q0, (C0729q2) obj);
            }
        });
        this.b.forEach(new Consumer() { // from class: com.android.tools.r8.graph.a5$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(q0, (C0729q2) obj);
            }
        });
        this.d.forEach(new Consumer() { // from class: com.android.tools.r8.graph.a5$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c(q0, (C0729q2) obj);
            }
        });
        this.e.keySet().forEach(new Consumer() { // from class: com.android.tools.r8.graph.a5$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(q0, (C0767v2) obj);
            }
        });
    }

    public final C0606a5 a(Q0 q0) {
        boolean z = i;
        if (!z && this.h == null) {
            throw new AssertionError();
        }
        if (this.f == null) {
            b(q0);
        }
        if (!z) {
            c(q0);
        }
        this.h = null;
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0614b5
    public final void a(Consumer consumer, C0664h c0664h) {
        consumer.accept(this);
        b(c0664h);
    }

    public final boolean a(C0729q2 c0729q2, o5 o5Var, com.android.tools.r8.shaking.V0 v0, AbstractC3837z1 abstractC3837z1, C0680j c0680j) {
        boolean z = i;
        if (!z && c0729q2.isInterface()) {
            throw new AssertionError();
        }
        com.android.tools.r8.shaking.M0 m0 = this.h.b;
        if (m0 != null) {
            m0.a(c0729q2, abstractC3837z1);
        }
        c((AbstractC0727q0) c0729q2, (Q0) c0680j);
        if (!this.h.a || v0 != com.android.tools.r8.shaking.V0.c || this.b.contains(c0729q2)) {
            return this.b.add(c0729q2) && ((Set) this.a.remove(c0729q2)) == null;
        }
        if (!z && o5Var == null) {
            throw new AssertionError();
        }
        Set set = (Set) this.a.computeIfAbsent(c0729q2, new Function() { // from class: com.android.tools.r8.graph.a5$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0606a5.e((C0729q2) obj);
            }
        });
        set.add(o5Var.e());
        return set.size() == 1;
    }

    public final boolean a(C0729q2 c0729q2, C0680j c0680j) {
        boolean z = i;
        if (!z && !c0729q2.isInterface()) {
            throw new AssertionError();
        }
        if (!z && !c0729q2.e.T()) {
            throw new AssertionError();
        }
        if (!this.c.add(c0729q2)) {
            return false;
        }
        c((AbstractC0727q0) c0729q2, (Q0) c0680j);
        return true;
    }

    public final void a(C0767v2 c0767v2, JA ja, C0680j c0680j) {
        ((List) this.e.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.graph.a5$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0606a5.b((C0767v2) obj);
            }
        })).add(ja);
        AbstractC0727q0 abstractC0727q0C = c0680j.c(c0767v2);
        if (abstractC0727q0C != null) {
            c(abstractC0727q0C, c0680j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Q0 q0, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = q0.c(c0767v2);
        if (abstractC0727q0C != null) {
            c(abstractC0727q0C, q0);
        }
    }

    public final void a(C0680j c0680j, C0729q2 c0729q2, Set set) {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            a(c0680j, ((AbstractC0727q0) it.next()).d, c0729q2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void c(AbstractC0727q0 abstractC0727q0, Q0 q0) {
        C0767v2 c0767v2 = abstractC0727q0.f;
        if (c0767v2 != null) {
            a(q0, c0767v2, abstractC0727q0);
        }
        for (C0767v2 c0767v3 : abstractC0727q0.g.a) {
            a(q0, c0767v3, abstractC0727q0);
        }
    }

    private void a(Q0 q0, C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0) {
        if (c0767v2 == q0.a().Z1) {
            return;
        }
        Set set = (Set) this.f.get(c0767v2);
        if (set != null) {
            set.add(abstractC0727q0);
            return;
        }
        Set setC = OU.c();
        setC.add(abstractC0727q0);
        this.f.put(c0767v2, setC);
        AbstractC0727q0 abstractC0727q0C = q0.c(c0767v2);
        if (abstractC0727q0C != null) {
            c(abstractC0727q0C, q0);
        }
    }

    final C0606a5 a(final AbstractC0614b5 abstractC0614b5, final Q0 q0, final AbstractC1476Wj abstractC1476Wj) {
        this.f = null;
        abstractC0614b5.b.forEach(new Consumer() { // from class: com.android.tools.r8.graph.a5$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(abstractC1476Wj, abstractC0614b5, q0, (C0729q2) obj);
            }
        });
        abstractC0614b5.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.a5$$ExternalSyntheticLambda13
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(abstractC1476Wj, abstractC0614b5, q0, (C0729q2) obj, (Set) obj2);
            }
        });
        Iterator it = abstractC0614b5.d.iterator();
        while (it.hasNext()) {
            C0767v2 c0767v2A = AbstractC2964s2.a(abstractC1476Wj, ((C0729q2) it.next()).d);
            if (c0767v2A.W0()) {
                if (!i) {
                    throw new AssertionError();
                }
            } else {
                C0729q2 c0729q2A = C0729q2.a(((C0665h0) q0).c(c0767v2A));
                boolean z = i;
                if (!z && c0729q2A == null) {
                    throw new AssertionError();
                }
                if (!z && this.d.contains(c0729q2A)) {
                    throw new AssertionError();
                }
                this.d.add(c0729q2A);
            }
        }
        abstractC0614b5.e.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.a5$$ExternalSyntheticLambda14
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(abstractC1476Wj, (C0767v2) obj, (List) obj2);
            }
        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC1476Wj abstractC1476Wj, AbstractC0614b5 abstractC0614b5, Q0 q0, C0729q2 c0729q2) {
        C0767v2 c0767v2A = AbstractC2964s2.a(abstractC1476Wj, c0729q2.d);
        if (c0767v2A.W0()) {
            if (!i && abstractC0614b5.a(c0729q2)) {
                throw new AssertionError();
            }
        } else {
            C0729q2 c0729q2A = C0729q2.a(q0.c(c0767v2A));
            if (!i && c0729q2A == null) {
                throw new AssertionError();
            }
            this.b.add(c0729q2A);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC1476Wj abstractC1476Wj, AbstractC0614b5 abstractC0614b5, Q0 q0, C0729q2 c0729q2, Set set) {
        C0767v2 c0767v2A = AbstractC2964s2.a(abstractC1476Wj, c0729q2.d);
        if (c0767v2A.W0()) {
            if (!i && abstractC0614b5.a(c0729q2)) {
                throw new AssertionError();
            }
            return;
        }
        C0729q2 c0729q2A = C0729q2.a(q0.c(c0767v2A));
        if (!i && c0729q2A == null) {
            throw new AssertionError();
        }
        if (this.b.contains(c0729q2A)) {
            return;
        }
        Set set2 = (Set) this.a.computeIfAbsent(c0729q2A, new Function() { // from class: com.android.tools.r8.graph.a5$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0606a5.f((C0729q2) obj);
            }
        });
        Set setC = OU.c();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            setC.add(abstractC1476Wj.a((W0) it.next(), q0));
        }
        set2.addAll(setC);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Q0 q0, C0729q2 c0729q2, Set set) {
        boolean z = i;
        if (!z && c0729q2.isInterface()) {
            throw new AssertionError();
        }
        if (!z && this.b.contains(c0729q2)) {
            throw new AssertionError();
        }
        if (z) {
            return;
        }
        a(c0729q2.H0(), q0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Q0 q0, C0767v2 c0767v2, List list) {
        boolean z = i;
        if (!z && list.isEmpty()) {
            throw new AssertionError();
        }
        AbstractC0727q0 abstractC0727q0C = q0.c(c0767v2);
        if (abstractC0727q0C != null) {
            if (!z && !abstractC0727q0C.isInterface()) {
                throw new AssertionError();
            }
            if (z) {
                return;
            }
            a(abstractC0727q0C.H0(), q0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0767v2 c0767v2, Set set) {
        if (!i && set.isEmpty()) {
            throw new AssertionError();
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) it.next();
            if (!i) {
                Iterator it2 = abstractC0727q0.H0().iterator();
                do {
                    if (!it2.hasNext()) {
                        throw new AssertionError();
                    }
                } while (c0767v2 != ((C0767v2) it2.next()));
            }
        }
    }

    private void a(Iterable iterable, Q0 q0) {
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            C0767v2 c0767v2 = (C0767v2) it.next();
            if (!i && c0767v2 != q0.a().Z1 && !this.f.containsKey(c0767v2)) {
                throw new AssertionError("Type not found in hierarchy: " + c0767v2);
            }
        }
    }
}
