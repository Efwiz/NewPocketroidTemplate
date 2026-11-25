package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.JA;
import com.android.tools.r8.internal.OU;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.graph.b5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0614b5 implements Y4 {
    static final /* synthetic */ boolean g = true;
    final IdentityHashMap a;
    final Set b;
    final Set c;
    final Set d;
    final IdentityHashMap e;
    IdentityHashMap f;

    private AbstractC0614b5() {
        this.a = new IdentityHashMap();
        this.b = OU.c();
        this.c = OU.c();
        this.d = OU.c();
        this.e = new IdentityHashMap();
        this.f = new IdentityHashMap();
    }

    public static C0606a5 a(boolean z, com.android.tools.r8.shaking.M0 m0) {
        return new C0606a5(z, m0);
    }

    public abstract void a(Consumer consumer, C0664h c0664h);

    public final boolean b(C0729q2 c0729q2) {
        return c0729q2.isInterface() && this.e.get(c0729q2.d) != null;
    }

    public final boolean c(C0729q2 c0729q2) {
        if (c0729q2.isInterface()) {
            return false;
        }
        if (!this.a.containsKey(c0729q2)) {
            return this.b.contains(c0729q2);
        }
        if (g || !((Set) this.a.get(c0729q2)).isEmpty()) {
            return true;
        }
        throw new AssertionError();
    }

    public final boolean d(C0729q2 c0729q2) {
        return c0729q2.isInterface() && this.d.contains(c0729q2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final C0606a5 a(Q0 q0, AbstractC1476Wj abstractC1476Wj) {
        return new C0606a5(true, null).a(this, q0, abstractC1476Wj).a(q0);
    }

    public final AbstractC0614b5 b(final u5 u5Var) {
        if (u5Var.j()) {
            Iterator it = this.a.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Set set = (Set) entry.getValue();
                set.removeIf(new Predicate() { // from class: com.android.tools.r8.graph.b5$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return AbstractC0614b5.a(u5Var, (W0) obj);
                    }
                });
                if (set.isEmpty()) {
                    this.b.add((C0729q2) entry.getKey());
                    it.remove();
                }
            }
        }
        return this;
    }

    public final boolean a(C0729q2 c0729q2) {
        if (this.f.get(c0729q2.d) != null) {
            return true;
        }
        if (c0729q2.isInterface()) {
            return this.c.contains(c0729q2) || this.d.contains(c0729q2) || b(c0729q2);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(Set set, C0729q2 c0729q2) {
        return set.contains(c0729q2.getType());
    }

    public final Set a(C0767v2 c0767v2) {
        return (Set) this.f.get(c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(Set set, C0729q2 c0729q2) {
        return set.contains(c0729q2.getType());
    }

    public final void a(BiConsumer biConsumer) {
        this.a.forEach(biConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(u5 u5Var, W0 w0) {
        return u5Var.g().contains(w0.getReference());
    }

    public final void a(C0767v2 c0767v2, final Consumer consumer, final Consumer consumer2, C0664h c0664h) {
        a(c0767v2, new Function() { // from class: com.android.tools.r8.graph.b5$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC0614b5.a(consumer, (C0729q2) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.graph.b5$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC0614b5.a(consumer2, (JA) obj);
            }
        }, c0664h);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(Consumer consumer, C0729q2 c0729q2) {
        consumer.accept(c0729q2);
        return com.android.tools.r8.utils.D3.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(Consumer consumer, JA ja) {
        consumer.accept(ja);
        return com.android.tools.r8.utils.D3.e();
    }

    public final com.android.tools.r8.utils.D3 a(C0767v2 c0767v2, final Function function, final Function function2, final C0664h c0664h) {
        final com.android.tools.r8.utils.O3 o3E = com.android.tools.r8.utils.O3.e();
        if (c0767v2 == c0664h.a().Z1) {
            this.f.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.b5$$ExternalSyntheticLambda8
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    AbstractC0614b5.a(c0664h, o3E, (C0767v2) obj, (Set) obj2);
                }
            });
        } else {
            AbstractC0727q0 abstractC0727q0C = c0664h.c(c0767v2);
            if (abstractC0727q0C == null) {
                o3E.a((Iterable) this.f.getOrDefault(c0767v2, Collections.emptySet()));
                Iterator it = ((List) this.e.getOrDefault(c0767v2, Collections.emptyList())).iterator();
                while (it.hasNext()) {
                    if (((com.android.tools.r8.utils.D3) function2.apply((JA) it.next())).f()) {
                        return com.android.tools.r8.utils.D3.d();
                    }
                }
            } else {
                o3E.c(abstractC0727q0C);
            }
        }
        return o3E.a(new Function() { // from class: com.android.tools.r8.graph.b5$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(function, o3E, function2, (AbstractC0727q0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0664h c0664h, com.android.tools.r8.utils.O3 o3, C0767v2 c0767v2, Set set) {
        AbstractC0727q0 abstractC0727q0C = c0664h.c(c0767v2);
        if (abstractC0727q0C != null) {
            o3.c(abstractC0727q0C);
        }
        o3.a((Iterable) set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.utils.D3 a(Function function, com.android.tools.r8.utils.O3 o3, Function function2, AbstractC0727q0 abstractC0727q0) {
        if (abstractC0727q0.e0()) {
            C0729q2 c0729q2A0 = abstractC0727q0.a0();
            if ((c(c0729q2A0) || d(c0729q2A0)) && ((com.android.tools.r8.utils.D3) function.apply(c0729q2A0)).f()) {
                return com.android.tools.r8.utils.D3.d();
            }
        }
        o3.a((Iterable) this.f.getOrDefault(abstractC0727q0.d, Collections.emptySet()));
        Iterator it = ((List) this.e.getOrDefault(abstractC0727q0.d, Collections.emptyList())).iterator();
        while (it.hasNext()) {
            if (((com.android.tools.r8.utils.D3) function2.apply((JA) it.next())).f()) {
                return com.android.tools.r8.utils.D3.d();
            }
        }
        return com.android.tools.r8.utils.D3.e();
    }

    public final void a(Consumer consumer) {
        this.e.keySet().forEach(consumer);
    }

    public final Set a() {
        return this.e.keySet();
    }

    public final void a(u5 u5Var) {
        final Set setE = u5Var.e();
        if (setE.isEmpty()) {
            return;
        }
        this.a.entrySet().removeIf(new Predicate() { // from class: com.android.tools.r8.graph.b5$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractC0614b5.a(setE, (Map.Entry) obj);
            }
        });
        this.b.removeIf(new Predicate() { // from class: com.android.tools.r8.graph.b5$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractC0614b5.a(setE, (C0729q2) obj);
            }
        });
        this.c.removeIf(new Predicate() { // from class: com.android.tools.r8.graph.b5$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractC0614b5.b(setE, (C0729q2) obj);
            }
        });
        boolean zRemoveIf = this.d.removeIf(new Predicate() { // from class: com.android.tools.r8.graph.b5$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractC0614b5.c(setE, (C0729q2) obj);
            }
        });
        if (!g && zRemoveIf) {
            throw new AssertionError("Unexpected removal of an interface marking an unknown hierarchy.");
        }
        final IdentityHashMap identityHashMap = this.e;
        Objects.requireNonNull(identityHashMap);
        setE.forEach(new Consumer() { // from class: com.android.tools.r8.graph.b5$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                identityHashMap.remove((C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, Map.Entry entry) {
        return set.contains(((C0729q2) entry.getKey()).getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, C0729q2 c0729q2) {
        return set.contains(c0729q2.getType());
    }

    public final void a(Set set, com.android.tools.r8.shaking.U1 u1, Q0 q0) {
        for (C0729q2 c0729q2 : this.a.keySet()) {
            if (!g && !set.contains(c0729q2.getType())) {
                throw new AssertionError();
            }
        }
        for (C0729q2 c0729q3 : this.b) {
            if (!g && !set.contains(c0729q3.getType())) {
                throw new AssertionError();
            }
        }
        for (C0729q2 c0729q4 : this.c) {
            if (!g && !set.contains(c0729q4.getType())) {
                throw new AssertionError();
            }
        }
        for (C0729q2 c0729q5 : this.d) {
            if (!g && !set.contains(c0729q5.getType())) {
                throw new AssertionError();
            }
        }
        for (C0767v2 c0767v2 : this.e.keySet()) {
            if (!g && !u1.a(c0767v2) && !q0.c(c0767v2).I1() && !set.contains(c0767v2)) {
                throw new AssertionError();
            }
        }
    }
}
