package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.utils.C3879c3;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.z2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3476z2 {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private C2 b;
    private C1261Pf c;
    private P2 d;

    public C3476z2(C0702m c0702m) {
        boolean z = e;
        if (!z && !c0702m.q()) {
            throw new AssertionError();
        }
        if (!z && !c0702m.Y().l1()) {
            throw new AssertionError();
        }
        if (!z && !c0702m.Y().c().b()) {
            throw new AssertionError();
        }
        this.a = c0702m;
    }

    public final void a(ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        if (!e && this.a.D().d()) {
            throw new AssertionError();
        }
        y3Var.a("Argument propagator");
        y3Var.a("Initialize code scanner");
        this.d = new P2(this.a);
        this.b = new C2(this.a, this.d);
        this.c = new C1261Pf(this.a);
        final com.android.tools.r8.graph.H3 h3A = com.android.tools.r8.graph.H3.a(this.a);
        C3879c3.a(new C3206vN(this.a, h3A).a(), new Consumer() { // from class: com.android.tools.r8.internal.z2$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(h3A, (Set) obj);
            }
        }, executorService);
        y3Var.c();
        y3Var.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.H3 h3, Set set) {
        new S2(this.a, h3, this.b.a()).a(set);
        new C3330x20(this.a, h3).a(set, this.b);
        this.c.a(set);
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var, C2409kl c2409kl, TE te, com.android.tools.r8.utils.y3 y3Var) {
        if (this.b != null) {
            boolean z = e;
            if (!z && !te.c()) {
                throw new AssertionError();
            }
            this.b.a(o5Var, c2409kl, y3Var);
            if (!z && this.c == null) {
                throw new AssertionError();
            }
            this.c.a(o5Var, c2409kl);
            if (!z && this.d == null) {
                throw new AssertionError();
            }
            this.d.a(o5Var, c2409kl);
            return;
        }
        boolean z2 = e;
        if (!z2 && te.c()) {
            throw new AssertionError();
        }
        if (!z2 && this.c != null) {
            throw new AssertionError();
        }
        if (z2) {
            return;
        }
        te.getClass();
        if ((te instanceof C2914rM) && this.d != null) {
            throw new AssertionError();
        }
    }

    public final void a() {
        if (!e && this.d == null) {
            throw new AssertionError();
        }
        this.d.a();
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        if (!e && this.b == null) {
            throw new AssertionError();
        }
        C1678bF c1678bFA = this.b.a();
        YE yeA = c1678bFA.a(o5Var, C3480z5.k());
        if (yeA.d()) {
            return;
        }
        c1678bFA.a(this.a, o5Var2, yeA);
    }

    public final void a(C3012sl c3012sl, C2844qM c2844qM, ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        boolean z = e;
        if (!z && this.a.D().d()) {
            throw new AssertionError();
        }
        if (!z) {
            this.d.b();
        }
        y3Var.a("Argument propagator");
        y3Var.a("Compute components");
        com.android.tools.r8.graph.H3 h3A = com.android.tools.r8.graph.H3.a(this.a);
        ArrayList arrayListA = new C3206vN(this.a, h3A).a();
        y3Var.c();
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.tools.r8.internal.z2$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3476z2.a(identityHashMap, (Set) obj, (AbstractC0717o2) obj2);
            }
        };
        if (!z && !this.a.P()) {
            throw new AssertionError();
        }
        C1678bF c1678bFA = this.b.a();
        this.a.Y().n1.k.a(c1678bFA);
        this.b = null;
        c2844qM.a(this.a);
        y3Var.a("Compute optimization info");
        new L2(this.a, c3012sl, h3A, c1678bFA, c2844qM, arrayListA, biConsumer).a(executorService, y3Var);
        y3Var.c();
        y3Var.a("Compute unused arguments");
        this.c.a();
        this.c = null;
        y3Var.c();
        final Set setA = OU.a();
        O2 o2 = new O2(this.a, h3A, identityHashMap);
        Objects.requireNonNull(setA);
        H2 h2A = o2.a(arrayListA, new Consumer() { // from class: com.android.tools.r8.internal.z2$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                setA.add((C0729q2) obj);
            }
        }, executorService, y3Var);
        new K2(this.a, this.d).a(h2A, c2844qM, executorService, y3Var);
        this.d = null;
        new B2(this.a, h2A).a(y3Var, setA, executorService);
        y3Var.c();
        this.a.Y().n1.a(new Fg$$ExternalSyntheticLambda6(c2844qM));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, Set set, AbstractC0717o2 abstractC0717o2) {
        ((C2720of) map.computeIfAbsent(set, new Function() { // from class: com.android.tools.r8.internal.z2$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3476z2.a((Set) obj);
            }
        })).a(abstractC0717o2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C2720of a(Set set) {
        return C2720of.a();
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var) {
        boolean z = e;
        if (!z && this.b == null) {
            throw new AssertionError();
        }
        YE yeA = this.b.a().a(o5Var, (YE) null);
        if (!z && yeA != null && !o5Var.e().f.N()) {
            throw new AssertionError();
        }
        if (!z && this.c == null) {
            throw new AssertionError();
        }
        this.c.a(o5Var);
    }
}
