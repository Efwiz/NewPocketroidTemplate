package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0740s;
import com.android.tools.r8.graph.AbstractC0741s0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.u5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.l, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3749l {
    static final /* synthetic */ boolean b = true;
    private final Map a;

    C3749l(IdentityHashMap identityHashMap) {
        if (!b && !identityHashMap.values().stream().noneMatch(new Predicate() { // from class: com.android.tools.r8.shaking.l$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((com.android.tools.r8.internal.Q3) obj).e();
            }
        })) {
            throw new AssertionError();
        }
        this.a = identityHashMap;
    }

    public static C3743k a() {
        return new C3743k();
    }

    public final boolean a(AbstractC0741s0 abstractC0741s0) {
        return this.a.containsKey(abstractC0741s0.getReference());
    }

    public final com.android.tools.r8.internal.Q3 a(AbstractC0642e2 abstractC0642e2) {
        return (com.android.tools.r8.internal.Q3) this.a.getOrDefault(abstractC0642e2, com.android.tools.r8.internal.Q3.b());
    }

    public final C3749l a(final C0702m c0702m, final AbstractC1476Wj abstractC1476Wj, final AbstractC1476Wj abstractC1476Wj2) {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.l$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3749l.a(abstractC1476Wj, abstractC1476Wj2, c0702m, identityHashMap, (AbstractC0642e2) obj, (com.android.tools.r8.internal.Q3) obj2);
            }
        });
        return new C3749l(identityHashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C0702m c0702m, Map map, AbstractC0642e2 abstractC0642e2, com.android.tools.r8.internal.Q3 q3) {
        AbstractC0740s abstractC0740sC;
        abstractC1476Wj.getClass();
        if (abstractC0642e2.x0()) {
            abstractC0740sC = abstractC1476Wj.b(abstractC1476Wj2, abstractC0642e2.t0());
        } else {
            abstractC0740sC = abstractC1476Wj.c(abstractC1476Wj2, abstractC0642e2.v0());
        }
        com.android.tools.r8.internal.Q3 q3A = q3.a(c0702m, abstractC1476Wj);
        if (!b && q3A.e()) {
            throw new AssertionError();
        }
        map.put(abstractC0740sC, q3A);
    }

    public final C3749l a(final u5 u5Var) {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.l$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3749l.a(u5Var, identityHashMap, (AbstractC0642e2) obj, (com.android.tools.r8.internal.Q3) obj2);
            }
        });
        return new C3749l(identityHashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(u5 u5Var, Map map, AbstractC0642e2 abstractC0642e2, com.android.tools.r8.internal.Q3 q3) {
        if (u5Var.a(abstractC0642e2)) {
            return;
        }
        com.android.tools.r8.internal.Q3 q3A = q3.a(u5Var);
        if (q3A.e()) {
            return;
        }
        map.put(abstractC0642e2, q3A);
    }
}
