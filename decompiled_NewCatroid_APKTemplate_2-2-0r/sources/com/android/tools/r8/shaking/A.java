package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0593a0;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class A {
    static final /* synthetic */ boolean b = true;
    private final ConcurrentHashMap a = new ConcurrentHashMap();

    public final void a(final C0702m c0702m, final com.android.tools.r8.utils.F3 f3, final com.android.tools.r8.utils.F3 f4, final com.android.tools.r8.utils.F3 f5) {
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.A$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                A.a(c0702m, f3, f4, f5, (Y) obj, (S1) obj2);
            }
        });
    }

    public final S1 b(Y y) {
        return (S1) this.a.remove(y);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.Q0 q0, final com.android.tools.r8.utils.F3 f3, final com.android.tools.r8.utils.F3 f4, final com.android.tools.r8.utils.F3 f5, final Y y, S1 s1) {
        s1.a(q0, new BiConsumer() { // from class: com.android.tools.r8.shaking.A$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                f3.a(y, (C0729q2) obj, (X0) obj2);
            }
        }, new BiConsumer() { // from class: com.android.tools.r8.shaking.A$$ExternalSyntheticLambda10
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                f4.a(y, (m5) obj, (C3698c1) obj2);
            }
        }, new BiConsumer() { // from class: com.android.tools.r8.shaking.A$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                f5.a(y, (o5) obj, (C3754l1) obj2);
            }
        });
    }

    public final S1 a(Y y) {
        return (S1) this.a.computeIfAbsent(y, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.shaking.A$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return new S1();
            }
        }));
    }

    public final S1 a() {
        return a(X.d());
    }

    public final AbstractC3716f1 a(AbstractC0744s2 abstractC0744s2) {
        return a(X.d()).a(abstractC0744s2);
    }

    public final S1 a(X x, S1 s1) {
        return (S1) this.a.getOrDefault(x, s1);
    }

    public final S1 a(S1 s1) {
        return a(X.d(), s1);
    }

    public final void a(A a) {
        a.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.A$$ExternalSyntheticLambda8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((Y) obj, (S1) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Y y, S1 s1) {
        a(y).a(s1);
    }

    public final void a(final C0702m c0702m, final L l) {
        com.android.tools.r8.utils.Z1.a(this.a, new BiPredicate() { // from class: com.android.tools.r8.shaking.A$$ExternalSyntheticLambda0
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return A.a(c0702m, l, (Y) obj, (S1) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(com.android.tools.r8.graph.Q0 q0, L l, Y y, S1 s1) {
        if (y.b()) {
            AbstractC0727q0 abstractC0727q0C = q0.c(y.a().d());
            if (abstractC0727q0C == null || !l.a((InterfaceC0593a0) abstractC0727q0C)) {
                return true;
            }
        } else if (!b && !(y instanceof X)) {
            throw new AssertionError();
        }
        if (!b && s1.b()) {
            throw new AssertionError();
        }
        s1.a(q0, l);
        return s1.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ X0 a(C0767v2 c0767v2, S1 s1) {
        return s1.a(c0767v2);
    }

    public final X0 a(X x, final C0767v2 c0767v2) {
        AbstractC3716f1 abstractC3716f1;
        Function function = new Function() { // from class: com.android.tools.r8.shaking.A$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return A.a(c0767v2, (S1) obj);
            }
        };
        S1 s1 = (S1) this.a.get(x);
        if (s1 == null) {
            abstractC3716f1 = null;
        } else {
            AbstractC3716f1 abstractC3716f2 = (AbstractC3716f1) function.apply(s1);
            if (s1.b()) {
                b(x);
            }
            abstractC3716f1 = abstractC3716f2;
        }
        return (X0) abstractC3716f1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3698c1 a(com.android.tools.r8.graph.Y0 y0, S1 s1) {
        return s1.a(y0);
    }

    public final C3698c1 a(X x, final com.android.tools.r8.graph.Y0 y0) {
        AbstractC3716f1 abstractC3716f1;
        Function function = new Function() { // from class: com.android.tools.r8.shaking.A$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return A.a(y0, (S1) obj);
            }
        };
        S1 s1 = (S1) this.a.get(x);
        if (s1 == null) {
            abstractC3716f1 = null;
        } else {
            AbstractC3716f1 abstractC3716f2 = (AbstractC3716f1) function.apply(s1);
            if (s1.b()) {
                b(x);
            }
            abstractC3716f1 = abstractC3716f2;
        }
        return (C3698c1) abstractC3716f1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3754l1 a(C0684j2 c0684j2, S1 s1) {
        return s1.a(c0684j2);
    }

    public final C3754l1 a(X x, final C0684j2 c0684j2) {
        AbstractC3716f1 abstractC3716f1;
        Function function = new Function() { // from class: com.android.tools.r8.shaking.A$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return A.a(c0684j2, (S1) obj);
            }
        };
        S1 s1 = (S1) this.a.get(x);
        if (s1 == null) {
            abstractC3716f1 = null;
        } else {
            AbstractC3716f1 abstractC3716f2 = (AbstractC3716f1) function.apply(s1);
            if (s1.b()) {
                b(x);
            }
            abstractC3716f1 = abstractC3716f2;
        }
        return (C3754l1) abstractC3716f1;
    }

    public final A a(final AbstractC1476Wj abstractC1476Wj) {
        final A a = new A();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.A$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                A.a(abstractC1476Wj, a, (Y) obj, (S1) obj2);
            }
        });
        return a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC1476Wj abstractC1476Wj, A a, Y y, S1 s1) {
        Y yA = y.a(abstractC1476Wj);
        yA.getClass();
        if (yA instanceof W) {
            return;
        }
        a.a(yA).a(s1.a(abstractC1476Wj));
    }
}
