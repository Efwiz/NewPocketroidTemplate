package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.n5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AN;
import com.android.tools.r8.internal.C1230Ob;
import com.android.tools.r8.internal.C1257Pb;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.utils.C3979v2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class O {
    static final /* synthetic */ boolean i = true;
    private final C1257Pb a;
    private ConcurrentHashMap b = new ConcurrentHashMap();
    private final LinkedList c = new LinkedList();
    private final ConcurrentHashMap d = new ConcurrentHashMap();
    private final AN e = AN.d();
    private final ConcurrentHashMap f = new ConcurrentHashMap();
    private final ConcurrentHashMap g = new ConcurrentHashMap();
    private final ArrayList h = new ArrayList();

    O(C1257Pb c1257Pb) {
        this.a = c1257Pb;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(L l, o5 o5Var, C3754l1 c3754l1) {
        l.c(Y.c(), o5Var, c3754l1);
    }

    final boolean b() {
        boolean z = this.c.isEmpty() && this.d.isEmpty() && this.f.isEmpty() && this.g.isEmpty();
        if (i || !z || this.h.isEmpty()) {
            return z;
        }
        throw new AssertionError();
    }

    final C1230Ob c(final o5 o5Var) {
        return (C1230Ob) this.b.computeIfAbsent(o5Var.getReference(), new Function() { // from class: com.android.tools.r8.shaking.O$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(o5Var, (C0684j2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C1230Ob a(o5 o5Var, C0684j2 c0684j2) {
        return this.a.a(o5Var);
    }

    public final void a(C0754u0 c0754u0) {
        C0754u0 c0754u1 = (C0754u0) this.f.put(c0754u0.d, c0754u0);
        if (!i && c0754u1 != null && c0754u1 != c0754u0) {
            throw new AssertionError();
        }
    }

    public final Set a() {
        return this.d.keySet();
    }

    public final void a(o5 o5Var) {
        o5 o5Var2 = (o5) this.d.put(o5Var.e().getReference(), o5Var);
        if (!i && o5Var2 != null) {
            throw new AssertionError();
        }
    }

    public final void b(o5 o5Var) {
        this.c.add(o5Var);
    }

    public final void a(C0729q2 c0729q2, AbstractC0727q0 abstractC0727q0) {
        ((Set) this.g.computeIfAbsent(c0729q2, new Function() { // from class: com.android.tools.r8.shaking.O$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return O.a((C0729q2) obj);
            }
        })).add(abstractC0727q0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(C0729q2 c0729q2) {
        return OU.a();
    }

    public final void a(o5 o5Var, Consumer consumer) {
        consumer.accept((C3754l1) this.e.a((n5) o5Var, com.android.tools.r8.utils.Z1.a(new O$$ExternalSyntheticLambda3())));
    }

    final void a(final L l) {
        if (!i && !l.b.c()) {
            throw new AssertionError();
        }
        l.h0.getClass();
        L0 l0A = M0.a();
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            l.U.a((o5) it.next());
        }
        this.h.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.O$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                O.a(l, (C3979v2) obj);
            }
        });
        for (o5 o5Var : this.d.values()) {
            if (!i) {
                K k = l.N;
                if (k.a.contains(o5Var.e())) {
                    throw new AssertionError();
                }
            }
            l.b(l0A, o5Var);
            l.U.a(o5Var, o5Var, l0A);
        }
        l.F.addAll(this.f.values());
        this.g.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.O$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                O.a(l, (C0729q2) obj, (Set) obj2);
            }
        });
        this.e.a(new BiConsumer() { // from class: com.android.tools.r8.shaking.O$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                O.a(l, (o5) obj, (C3754l1) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(L l, C3979v2 c3979v2) {
        l.Y.a((o5) c3979v2.a(), (Consumer) c3979v2.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(L l, C0729q2 c0729q2, Set set) {
        l.u.a(l.b(), c0729q2, set);
    }
}
