package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C1367Tb;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class O1 {
    static final /* synthetic */ boolean e = true;
    private final Set a;
    private final C0702m b;
    private final IdentityHashMap c;
    private final L1 d;

    public O1(final C0702m c0702m, Set set, L1 l1) {
        this.b = c0702m;
        if (!e && !set.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.shaking.O1$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return O1.a(c0702m, (C0767v2) obj);
            }
        })) {
            throw new AssertionError();
        }
        this.a = set;
        this.d = l1;
        this.c = new IdentityHashMap();
    }

    public static void a(C0664h c0664h) {
        if (c0664h.c(c0664h.a().e2) == null) {
            throw new C1367Tb("Tracing for legacy multi dex is not possible without all classpath libraries (java.lang.Enum is missing)");
        }
        if (c0664h.c(c0664h.a().f2) == null) {
            throw new C1367Tb("Tracing for legacy multi dex is not possible without all classpath libraries (java.lang.annotation.Annotation is missing)");
        }
    }

    private boolean b(C0767v2 c0767v2) {
        Boolean bool = (Boolean) this.c.get(c0767v2);
        if (bool == null) {
            AbstractC0727q0 abstractC0727q0C = this.b.c(c0767v2);
            if (abstractC0727q0C == null) {
                bool = Boolean.TRUE;
            } else {
                Boolean boolValueOf = Boolean.FALSE;
                Iterator<com.android.tools.r8.graph.W0> it = abstractC0727q0C.P1().iterator();
                while (it.hasNext()) {
                    C0737r2 c0737r2 = it.next().getReference().h;
                    if (c0737r2.f.isEmpty()) {
                        C0767v2 c0767v2B = c0737r2.e.b(this.b.a());
                        if (c0767v2B.O0()) {
                            if (!e && boolValueOf.booleanValue()) {
                                throw new AssertionError();
                            }
                            AbstractC0727q0 abstractC0727q0C2 = ((C0680j) this.b.f()).c(c0767v2B);
                            boolean z = false;
                            boolean z2 = ((abstractC0727q0C2 == null || abstractC0727q0C2.f0()) && this.b.Y().w0) ? false : true;
                            if ((c0767v2B.O0() && ((C0680j) this.b.f()).c(c0767v2B, this.b.a().e2) && z2) || (((C0680j) this.b.f()).c(c0767v2B, this.b.a().f2) && b(c0767v2B))) {
                                z = true;
                            }
                            boolValueOf = Boolean.valueOf(z);
                        }
                    }
                    if (boolValueOf.booleanValue()) {
                        break;
                    }
                }
                bool = boolValueOf;
            }
            this.c.put(c0767v2, bool);
        }
        return bool.booleanValue();
    }

    private void c() {
        boolean zContains;
        for (final C0729q2 c0729q2 : ((C0680j) this.b.f()).d()) {
            L1 l1 = this.d;
            l1.getClass();
            if (!l1.e(c0729q2.d)) {
                C0767v2 c0767v2 = c0729q2.d;
                if (((C0680j) this.b.f()).c(c0767v2, this.b.a().f2) && b(c0767v2)) {
                    if (C0633d0.a(this.b.Y(), c0729q2.d)) {
                        zContains = true;
                    } else {
                        C0633d0 c0633d0A = c0729q2.s0().a(this.b.a().j3);
                        zContains = c0633d0A == null ? false : c0633d0A.b.toString().contains("RUNTIME");
                    }
                    if (zContains) {
                        a(c0729q2);
                        Iterator<com.android.tools.r8.graph.W0> it = c0729q2.P1().iterator();
                        while (it.hasNext()) {
                            C0737r2 c0737r2 = it.next().getReference().h;
                            if (c0737r2.f.isEmpty()) {
                                C0767v2 c0767v2B = c0737r2.e.b(this.b.a());
                                if (c0767v2B.O0() && ((C0680j) this.b.f()).c(c0767v2B, this.b.a().e2)) {
                                    a(c0767v2B);
                                }
                            }
                        }
                    }
                } else {
                    Consumer consumer = new Consumer() { // from class: com.android.tools.r8.shaking.O1$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            this.f$0.a(c0729q2, (C0633d0) obj);
                        }
                    };
                    c0729q2.s0().forEach(consumer);
                    for (com.android.tools.r8.graph.W0 w0 : c0729q2.M1()) {
                        w0.s0().forEach(consumer);
                        w0.h.d(consumer);
                    }
                    Iterator<com.android.tools.r8.graph.T0> it2 = c0729q2.W0().iterator();
                    while (it2.hasNext()) {
                        it2.next().s0().forEach(consumer);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0702m c0702m, C0767v2 c0767v2) {
        return c0702m.c(c0767v2).e0();
    }

    public final void a() {
        b();
        c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2, C0633d0 c0633d0) {
        L1 l1 = this.d;
        l1.getClass();
        if (!l1.e(c0729q2.d) && c0633d0.a == 1 && b(c0633d0.b.a)) {
            L1 l2 = this.d;
            l2.getClass();
            l2.a(c0729q2.getType());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C;
        C0767v2 c0767v2B = c0767v2.b(this.b.a());
        if (!c0767v2B.O0() || this.d.e(c0767v2B) || (abstractC0727q0C = this.b.c(c0767v2B)) == null || abstractC0727q0C.I1()) {
            return;
        }
        a(abstractC0727q0C.a0());
    }

    private void a(C0729q2 c0729q2) {
        if (!e) {
            L1 l1 = this.d;
            l1.getClass();
            if (l1.e(c0729q2.d)) {
                throw new AssertionError();
            }
        }
        L1 l2 = this.d;
        l2.getClass();
        l2.a(c0729q2.getType());
        C0767v2 c0767v2 = c0729q2.f;
        if (c0767v2 != null) {
            a(c0767v2);
        }
        for (C0767v2 c0767v3 : c0729q2.g.a) {
            a(c0767v3);
        }
    }

    private void b() {
        new K1(new Consumer() { // from class: com.android.tools.r8.shaking.O1$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0767v2) obj);
            }
        }, this.b).a(this.a);
    }
}
