package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.utils.InterfaceC3877c1;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qa, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2858qa {
    private static final com.android.tools.r8.ir.optimize.info.r m = com.android.tools.r8.ir.optimize.info.r.b();
    static final /* synthetic */ boolean n = true;
    private final C0702m a;
    private final EnumC0928Dk b;
    private final C2688oE c;
    private final C0722p1 d;
    private final C0986Fk e;
    private final C2931ra f;
    private final MR g;
    private final InterfaceC2621na h;
    private final C1092Ja i;
    private final C1622aa j;
    private final C1509Xn k;
    private final Collection l;

    private C2858qa(C0702m c0702m, InterfaceC2802pl interfaceC2802pl, EnumC0928Dk enumC0928Dk, C0986Fk c0986Fk, C2688oE c2688oE, List list) {
        this.f = new C2931ra();
        MR mr = new MR();
        this.g = mr;
        this.a = c0702m;
        this.d = c0702m.a();
        this.c = c2688oE;
        this.e = c0986Fk;
        this.b = enumC0928Dk;
        this.i = new C1092Ja(c0702m, c0986Fk, c2688oE);
        this.h = InterfaceC2621na.a(c0702m, c0986Fk, c2688oE);
        this.j = C1622aa.a(c2688oE);
        this.k = C1509Xn.a(c0702m, mr, interfaceC2802pl, c2688oE, c0986Fk, enumC0928Dk);
        this.l = list;
        a();
    }

    private void d() {
        if (!n && this.c.b().stream().filter(new Predicate() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((C0729q2) obj).y0();
            }
        }).count() > 1) {
            throw new AssertionError();
        }
        for (C0729q2 c0729q2 : this.c.d()) {
            if (c0729q2.y0()) {
                this.c.f().a(c0729q2.s0());
                return;
            }
        }
    }

    private void e() {
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (this.c.l()) {
            final AbstractC1048Hm abstractC1048HmB = com.android.tools.r8.utils.T2.b(new InterfaceC3877c1() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda13
                @Override // com.android.tools.r8.utils.InterfaceC3877c1
                public final void forEach(Consumer consumer) {
                    this.f$0.a(consumer);
                }
            });
            this.c.forEach(new Consumer() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda14
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2858qa.a(abstractC1048HmB, linkedHashSet, (C0729q2) obj);
                }
            });
        } else {
            this.c.forEach(new Consumer() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda15
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2858qa.a(linkedHashSet, (C0729q2) obj);
                }
            });
        }
        C0729q2 c0729q2F = this.c.f();
        int i = C0800x2.d;
        c0729q2F.g = linkedHashSet.isEmpty() ? C0800x2.s0() : new C0800x2((C0767v2[]) linkedHashSet.toArray(C0767v2.g));
    }

    final void a() {
        this.g.a(0, this.c.f().getType());
        this.c.a(new Consumer() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        });
    }

    final C0684j2 b(com.android.tools.r8.graph.o5 o5Var) {
        if (!n && !o5Var.e().f.N()) {
            throw new AssertionError();
        }
        C0722p1 c0722p1 = this.d;
        String strR0 = o5Var.u().r0();
        C0737r2 c0737r2F = o5Var.F();
        C0767v2 type = this.c.f().getType();
        C2931ra c2931ra = this.f;
        Objects.requireNonNull(c2931ra);
        return c0722p1.a(c0737r2F, type, strR0, new Wn$$ExternalSyntheticLambda1(c2931ra));
    }

    final void c(C0729q2 c0729q2) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        };
        c0729q2.getClass();
        c0729q2.d(consumer, CM.b());
        c0729q2.Y().b();
    }

    final void f() {
        C2688oE c2688oE = this.c;
        final C1092Ja c1092Ja = this.i;
        Objects.requireNonNull(c1092Ja);
        c2688oE.a(new Consumer() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c1092Ja.a((C0729q2) obj);
            }
        });
        this.i.a();
        this.c.a(new Consumer() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C0729q2) obj).T0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0729q2 c0729q2) {
        this.g.a(this.g.size(), c0729q2.getType());
    }

    final void a(AbstractC2151hN abstractC2151hN, PY py, RY ry) {
        b(abstractC2151hN, py, ry);
        if (!this.j.b()) {
            C0684j2 c0684j2A = this.d.a(this.c.f().getType());
            C0684j2 c0684j2A2 = c0684j2A.a("$r8$clinit$synthetic", this.d);
            this.e.a(c0684j2A2, c0684j2A, true);
            com.android.tools.r8.androidapi.h hVarA = this.j.a(this.a);
            com.android.tools.r8.graph.W0 w0A = com.android.tools.r8.graph.W0.Y1().a(c0684j2A).a(C0725p4.b(4104, true)).a(this.j.a(c0684j2A2)).a(this.j.a()).b(hVarA).a(hVarA).a();
            this.f.a(w0A);
            if (!w0A.a1().z0()) {
                boolean z = n;
                if (!z && !this.a.Y().Y0()) {
                    throw new AssertionError();
                }
                if (!z && !this.b.a()) {
                    throw new AssertionError();
                }
                ry.a(new com.android.tools.r8.graph.o5(this.c.f(), w0A));
            }
        }
        this.c.forEach(new Consumer() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c((C0729q2) obj);
            }
        });
    }

    final void c() {
        if (!this.c.f().D1() || this.c.f().t1()) {
            return;
        }
        for (C0729q2 c0729q2 : this.c.d()) {
            if (c0729q2.t1()) {
                this.c.f().P0();
                this.c.f().b((List) c0729q2.i1());
                this.c.f().k(new Predicate() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda17
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return this.f$0.a((com.android.tools.r8.graph.X4) obj);
                    }
                });
                return;
            }
        }
    }

    final void b(final AbstractC2151hN abstractC2151hN, final PY py, final RY ry) {
        this.k.a(new Consumer() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(abstractC2151hN, py, ry, (C1480Wn) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(C0729q2 c0729q2) {
        c0729q2.Y().c();
    }

    final void b() {
        if (AbstractC2734ot.b(this.c.d(), CM.a(new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda9
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ((C0729q2) obj).x1();
            }
        }))) {
            this.c.f().getAccessFlags().O();
        }
        if (AbstractC2734ot.b(this.c.d(), CM.a(new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda11
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ((C0729q2) obj).C1();
            }
        }))) {
            this.c.f().getAccessFlags().a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(final com.android.tools.r8.graph.t5 t5Var, C0729q2 c0729q2) {
        c0729q2.e(new Consumer() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2858qa.a(t5Var, (com.android.tools.r8.graph.T0) obj);
            }
        });
        c0729q2.O0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        if (w0E.u1()) {
            C0986Fk c0986Fk = this.e;
            C0684j2 reference = o5Var.getReference();
            C0684j2 c0684j2A = this.d.a(this.c.f().getType());
            c0986Fk.b.a(reference, c0684j2A);
            c0986Fk.a(reference, c0684j2A, false);
            return;
        }
        if (w0E.C1()) {
            return;
        }
        C0684j2 c0684j2B = o5Var.getReference().b(this.c.f().getType(), this.d);
        if (!this.f.a(c0684j2B)) {
            c0684j2B = b(o5Var);
        }
        this.f.a(w0E.a(c0684j2B));
        if (w0E.getReference() != c0684j2B) {
            C0986Fk c0986Fk2 = this.e;
            C0684j2 reference2 = w0E.getReference();
            c0986Fk2.b.a(reference2, c0684j2B);
            c0986Fk2.a(reference2, c0684j2B, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC2151hN abstractC2151hN, PY py, RY ry, C1480Wn c1480Wn) {
        c1480Wn.a(abstractC2151hN, this.f, py, ry);
    }

    final void a(final AbstractC2151hN abstractC2151hN, final Consumer consumer) {
        this.l.forEach(new Consumer() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(abstractC2151hN, consumer, (C3180v20) obj);
            }
        });
        this.c.a(new Consumer() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2858qa.b((C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC2151hN abstractC2151hN, Consumer consumer, C3180v20 c3180v20) {
        c3180v20.a(abstractC2151hN, this.f, this.e, this.g, consumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(com.android.tools.r8.graph.X4 x4) {
        return x4.a() == this.c.f().getType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, C0729q2 c0729q2) {
        consumer.accept(c0729q2.getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final Consumer consumer) {
        this.c.forEach(new Consumer() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2858qa.a(consumer, (C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, Set set2, C0729q2 c0729q2) {
        for (C0767v2 c0767v2 : c0729q2.f1()) {
            if (!set.contains(c0767v2)) {
                set2.add(c0767v2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, C0729q2 c0729q2) {
        AbstractC2734ot.a((Iterable) c0729q2.f1(), (Collection) set);
    }

    final void a(final com.android.tools.r8.graph.t5 t5Var) {
        this.c.a(new Consumer() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2858qa.b(t5Var, (C0729q2) obj);
            }
        });
        this.c.f().a(this.h.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.t5 t5Var, com.android.tools.r8.graph.T0 t0) {
        t5Var.a(t0.getReference());
    }

    public final void a(AbstractC2151hN abstractC2151hN, final com.android.tools.r8.graph.t5 t5Var, PY py, RY ry, Consumer consumer) {
        b();
        c();
        d();
        e();
        if (this.c.g()) {
            boolean z = n;
            if (!z && !this.a.f().i()) {
                throw new AssertionError();
            }
            if (!z && !this.b.b()) {
                throw new AssertionError();
            }
            com.android.tools.r8.graph.T0 t0A = com.android.tools.r8.graph.T0.d1().a(this.c.a()).a(com.android.tools.r8.graph.Q2.k(4113)).a(this.a.o()).a(!this.a.Y().a().d()).a();
            m.a(t0A, this.a.i0(), new C1910eI(0L, this.c.size() - 1));
            this.h.a(t0A);
        }
        a(t5Var);
        f();
        a(abstractC2151hN, consumer);
        a(abstractC2151hN, py, ry);
        this.f.a(this.c.f());
        this.c.f().L0();
        this.c.f().k(new Consumer() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((com.android.tools.r8.graph.n5) obj).E();
            }
        });
        this.c.a(new Consumer() { // from class: com.android.tools.r8.internal.qa$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2858qa.a(t5Var, (C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.t5 t5Var, C0729q2 c0729q2) {
        t5Var.a(c0729q2.getType());
    }
}
