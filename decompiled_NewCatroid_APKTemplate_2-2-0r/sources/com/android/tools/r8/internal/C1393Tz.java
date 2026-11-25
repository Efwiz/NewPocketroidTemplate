package com.android.tools.r8.internal;

import com.android.tools.r8.dex.C0562k$$ExternalSyntheticLambda20;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Tz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1393Tz extends AbstractC2797pg {
    private static final com.android.tools.r8.ir.optimize.info.r e = com.android.tools.r8.ir.optimize.info.r.b();
    static final /* synthetic */ boolean f = true;
    private final C0702m a;
    private final com.android.tools.r8.shaking.G b;
    private final Set c;
    private final AtomicBoolean d = new AtomicBoolean(false);

    public C1393Tz(C0702m c0702m, com.android.tools.r8.shaking.G g, Set set) {
        this.a = c0702m;
        this.b = g;
        this.c = set;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean b(C0633d0 c0633d0) {
        return c0633d0.t0() == this.a.a().t3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean c() {
        return Boolean.valueOf(this.d.getAndSet(true));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean d() {
        return Boolean.valueOf(this.d.getAndSet(true));
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(final com.android.tools.r8.shaking.L l) {
        com.android.tools.r8.graph.W0 w0C;
        C0722p1 c0722p1A = this.a.a();
        com.android.tools.r8.utils.A1 a1Y = this.a.Y();
        C0664h c0664hF = this.a.f();
        Objects.requireNonNull(c0664hF);
        C0562k$$ExternalSyntheticLambda20 c0562k$$ExternalSyntheticLambda20 = new C0562k$$ExternalSyntheticLambda20(c0664hF);
        Objects.requireNonNull(l);
        final boolean zA = com.android.tools.r8.shaking.Y0.a(c0722p1A, a1Y, c0562k$$ExternalSyntheticLambda20, new Function() { // from class: com.android.tools.r8.internal.Tz$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return l.b((C0729q2) obj);
            }
        });
        if (l.i().c()) {
            final Set setC = OU.c();
            final Set<C0729q2> setC2 = OU.c();
            l.a(new Consumer() { // from class: com.android.tools.r8.internal.Tz$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(l, zA, setC, setC2, (C0729q2) obj);
                }
            });
            for (C0729q2 c0729q2 : setC2) {
                com.android.tools.r8.graph.O2 o2B1 = c0729q2.b1();
                AbstractC0727q0 abstractC0727q0C = new C1365Sz(this, c0729q2, this.b, this.c).c(o2B1.b().e);
                if (abstractC0727q0C != null && ((w0C = abstractC0727q0C.c(o2B1.b())) == null || (w0C.f1().l() && w0C.f1().b().u()))) {
                    c0729q2.l(new Consumer() { // from class: com.android.tools.r8.internal.Tz$$ExternalSyntheticLambda7
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            C1393Tz.a(setC, (com.android.tools.r8.graph.o5) obj);
                        }
                    });
                }
            }
            this.a.b(setC);
        } else {
            if (!f && !l.i().b()) {
                throw new AssertionError();
            }
            l.a(new Consumer() { // from class: com.android.tools.r8.internal.Tz$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(l, zA, (C0729q2) obj);
                }
            });
        }
        l.a(new Consumer() { // from class: com.android.tools.r8.internal.Tz$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.shaking.L l, boolean z, final Set set, Set set2, C0729q2 c0729q2) {
        InterfaceC2743oz interfaceC2743ozA;
        if (!f && !c0729q2.V1().a()) {
            throw new AssertionError();
        }
        com.android.tools.r8.shaking.Y0 y0B = l.b(c0729q2);
        com.android.tools.r8.utils.A1 a1Y = this.a.Y();
        if (z) {
            if (y0B.c((com.android.tools.r8.shaking.K0) a1Y) && a1Y.g1() && !y0B.b(a1Y)) {
                C0702m c0702m = this.a;
                Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Tz$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C1393Tz.a(set, (com.android.tools.r8.graph.W0) obj);
                    }
                };
                Supplier supplier = new Supplier() { // from class: com.android.tools.r8.internal.Tz$$ExternalSyntheticLambda3
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return this.f$0.d();
                    }
                };
                boolean z2 = C2888qz.a;
                C0633d0 c0633d0A = c0729q2.s0().a(c0702m.a().t3);
                if (c0633d0A == null) {
                    interfaceC2743ozA = AbstractC1745cA.c();
                } else {
                    interfaceC2743ozA = C2888qz.a(c0702m, c0729q2, c0633d0A, consumer, supplier);
                }
                c0729q2.a(interfaceC2743ozA);
                if (c0729q2.b1() == null || c0729q2.b1().b() == null) {
                    return;
                }
                set2.add(c0729q2);
                return;
            }
        } else {
            y0B.getClass();
        }
        if (C2888qz.a(this.a, c0729q2, new Supplier() { // from class: com.android.tools.r8.internal.Tz$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.c();
            }
        }) && c0729q2.p1()) {
            e.e(c0729q2.Y0());
        }
        c0729q2.Q1();
        c0729q2.a(new Predicate() { // from class: com.android.tools.r8.internal.Tz$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((C0633d0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(C0633d0 c0633d0) {
        return c0633d0.t0() == this.a.a().t3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, com.android.tools.r8.graph.W0 w0) {
        set.add(w0.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, com.android.tools.r8.graph.o5 o5Var) {
        set.add(o5Var.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.shaking.L l, boolean z, C0729q2 c0729q2) {
        com.android.tools.r8.shaking.Y0 y0B = l.b(c0729q2);
        com.android.tools.r8.utils.A1 a1Y = this.a.Y();
        if (z) {
            if (y0B.c((com.android.tools.r8.shaking.K0) a1Y) && a1Y.g1() && !y0B.b(a1Y)) {
                if (f) {
                    return;
                }
                C1365Sz c1365Sz = new C1365Sz(this, c0729q2, this.b, this.c);
                boolean z2 = C2888qz.a;
                if ((c0729q2.s0().a(c1365Sz.a().t3) != null) != (c0729q2.V1() != AbstractC1745cA.c())) {
                    throw new AssertionError();
                }
                return;
            }
        } else {
            y0B.getClass();
        }
        c0729q2.Q1();
        c0729q2.L1().forEach(new Consumer() { // from class: com.android.tools.r8.internal.Tz$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((com.android.tools.r8.graph.U0) obj).G0();
            }
        });
        c0729q2.a(new Predicate() { // from class: com.android.tools.r8.internal.Tz$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.b((C0633d0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        c0729q2.V1().a(new C1365Sz(this, c0729q2, this.b, this.c));
        c0729q2.k(new Consumer() { // from class: com.android.tools.r8.internal.Tz$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.n5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.n5 n5Var) {
        n5Var.e().Z().a(new C1365Sz(this, n5Var, this.b, this.c));
    }
}
