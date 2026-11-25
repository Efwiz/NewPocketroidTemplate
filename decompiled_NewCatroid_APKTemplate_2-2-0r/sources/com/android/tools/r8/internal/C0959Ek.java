package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.AbstractC3735i1;
import com.android.tools.r8.shaking.C3730h1;
import com.android.tools.r8.shaking.C3754l1;
import com.android.tools.r8.utils.C3879c3;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ek, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0959Ek {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final EnumC0928Dk b;
    private final com.android.tools.r8.utils.A1.h c;

    private C0959Ek(C0702m c0702m, EnumC0928Dk enumC0928Dk) {
        this.a = c0702m;
        this.b = enumC0928Dk;
        this.c = c0702m.Y().O0();
    }

    public static C0959Ek a(C0702m c0702m) {
        if (d || c0702m.Y().O0().g()) {
            return new C0959Ek(c0702m, EnumC0928Dk.b);
        }
        throw new AssertionError();
    }

    public static C0959Ek b(C0702m c0702m) {
        return new C0959Ek(c0702m, EnumC0928Dk.b);
    }

    public static C0959Ek c(C0702m c0702m) {
        return new C0959Ek(c0702m, EnumC0928Dk.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean d(com.android.tools.r8.graph.W0 w0) {
        return w0.q1() && w0.a1().E0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(com.android.tools.r8.graph.W0 w0) {
        return w0.q1() && w0.a1().D0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(com.android.tools.r8.graph.W0 w0) {
        return w0.q1() && w0.a1().E0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(com.android.tools.r8.shaking.G0 g0, C2688oE c2688oE, com.android.tools.r8.graph.o5 o5Var) {
        g0.a(c2688oE.a(), o5Var);
    }

    public final void a(ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var, com.android.tools.r8.shaking.g4 g4Var) {
        InterfaceC2802pl interfaceC2802plA;
        if (this.c.a(this.b)) {
            y3Var.a("HorizontalClassMerger (" + this.b.toString() + Constant.AFTER_QUTO);
            if (this.a.G()) {
                interfaceC2802plA = InterfaceC2802pl.a(this.a.h0());
            } else {
                interfaceC2802plA = InterfaceC2802pl.a();
            }
            a(g4Var, interfaceC2802plA, executorService, y3Var);
            if (!d) {
                AbstractC3256w3.a(this.a, new EnumC3181v3[0]);
            }
            this.a.a().c();
            y3Var.c();
            return;
        }
        this.a.a(C1105Jk.a(), this.b);
    }

    private void a(com.android.tools.r8.shaking.g4 g4Var, InterfaceC2802pl interfaceC2802pl, ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        LinkedList<C2688oE> linkedListA = XL.a(a(), YL.a(this.a, interfaceC2802pl, this.b, g4Var), executorService, y3Var);
        if (linkedListA.isEmpty()) {
            this.a.a(C1105Jk.a(), this.b);
            return;
        }
        C0986Fk c0986Fk = new C0986Fk();
        ArrayList arrayList = new ArrayList(linkedListA.size());
        for (C2688oE c2688oE : linkedListA) {
            boolean z = d;
            if (!z && c2688oE.m()) {
                throw new AssertionError();
            }
            if (!z && !c2688oE.i()) {
                throw new AssertionError();
            }
            if (!z && !c2688oE.j()) {
                throw new AssertionError();
            }
            arrayList.add(new C2787pa(this.a, interfaceC2802pl, c2688oE, this.b).a(c0986Fk));
        }
        this.a.a().c();
        AbstractC2151hN abstractC2151hNB = AbstractC2151hN.b(this.a);
        PY pyA = this.b.b() ? new OY(this.a.i0()).a(linkedListA) : null;
        RY ryA = SY.a(this.a, interfaceC2802pl, this.b);
        final ArrayList arrayList2 = new ArrayList();
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList2.add((C3407y20) obj);
            }
        };
        com.android.tools.r8.graph.t5 t5VarA = com.android.tools.r8.graph.u5.a().a(this.a.e());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((C2858qa) it.next()).a(abstractC2151hNB, t5VarA, pyA, ryA, consumer);
        }
        final com.android.tools.r8.graph.u5 u5VarA = t5VarA.a();
        SY syA = ryA.a();
        boolean z2 = d;
        if (!z2 && !syA.b() && !this.a.q()) {
            throw new AssertionError();
        }
        syA.a(executorService);
        boolean z3 = C1105Jk.b;
        C1105Jk c1105JkA = new C1073Ik().a(linkedListA).a();
        this.a.a(c1105JkA, this.b);
        final C1013Gk c1013GkB = new C3140uZ(this.a, c1105JkA, c0986Fk, this.b, abstractC2151hNB, pyA).b();
        AbstractC2151hN abstractC2151hNA = abstractC2151hNB.a(new Function() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return c1013GkB.i((C0684j2) obj);
            }
        });
        if (!z2) {
            a(this.a, linkedListA);
        }
        AbstractC0681j0 abstractC0681j0A = a(c1105JkA);
        if (this.a.q()) {
            AbstractC3735i1 abstractC3735i1V = this.a.v();
            Consumer consumer2 = new Consumer() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((C3730h1) obj).a(u5VarA);
                }
            };
            C3730h1 c3730h1 = (C3730h1) abstractC3735i1V;
            c3730h1.getClass();
            consumer2.accept(c3730h1);
            if (!z2 && !this.a.f().h()) {
                throw new AssertionError();
            }
            this.a.a(abstractC0681j0A.i(), c1013GkB);
        } else {
            if (!z2 && !this.b.a()) {
                throw new AssertionError();
            }
            com.android.tools.r8.synthesis.D dG = this.a.f().g();
            if (!z2 && dG.d()) {
                throw new AssertionError();
            }
            this.a.j0().d(new C0664h(dG.a(abstractC0681j0A, c1013GkB), this.a.f().f().a(dG, c1013GkB)));
            this.a.a((AbstractC3424yG) c1013GkB);
        }
        interfaceC2802pl.a(c1013GkB);
        abstractC2151hNA.a(this.a.s()).a(this.a.C()).a(this.a);
        if (this.b.b()) {
            a(linkedListA).a(this.a.i0());
        } else if (!z2 && !linkedListA.stream().noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((C2688oE) obj).g();
            }
        })) {
            throw new AssertionError();
        }
        a(linkedListA, c1013GkB, executorService);
        syA.b(executorService);
        this.a.a(u5VarA.l().a(this.a.e()).a(), executorService);
        a(c1013GkB, arrayList2);
    }

    private void a(final C1013Gk c1013Gk, final ArrayList arrayList) {
        if (!this.a.q()) {
            if (!d && !arrayList.isEmpty()) {
                throw new AssertionError();
            }
            return;
        }
        this.a.v().a(new Consumer() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(arrayList, c1013Gk, (C3730h1) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, C1013Gk c1013Gk, C3730h1 c3730h1) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            final C3407y20 c3407y20 = (C3407y20) it.next();
            com.android.tools.r8.graph.o5 o5VarA = AbstractC0748t0.a(this.a.b((C0684j2) c1013Gk.a(c3407y20.b(), (C0684j2) null, EnumC1953et.g, c1013Gk.k()).b()));
            if (o5VarA != null) {
                c3730h1.a(o5VarA, new Consumer() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda13
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C0959Ek.a(c3407y20, (C3754l1) obj);
                    }
                });
            } else if (!d) {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3407y20 c3407y20, C3754l1 c3754l1) {
        c3754l1.a(c3407y20.a());
    }

    private com.android.tools.r8.shaking.I0 a(LinkedList linkedList) {
        if (!d && !this.b.b()) {
            throw new AssertionError();
        }
        final com.android.tools.r8.shaking.G0 g0 = new com.android.tools.r8.shaking.G0();
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            final C2688oE c2688oE = (C2688oE) it.next();
            if (c2688oE.g()) {
                C0729q2 c0729q2F = c2688oE.f();
                c0729q2F.f(new Consumer() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda10
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C0959Ek.a(g0, c2688oE, (com.android.tools.r8.graph.o5) obj);
                    }
                }, new Predicate() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return C0959Ek.a((com.android.tools.r8.graph.W0) obj);
                    }
                });
                c0729q2F.h(new Consumer() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda12
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C0959Ek.b(g0, c2688oE, (com.android.tools.r8.graph.o5) obj);
                    }
                }, new Predicate() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return C0959Ek.b((com.android.tools.r8.graph.W0) obj);
                    }
                });
            }
        }
        return g0.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.W0 w0) {
        return w0.a1().D0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.shaking.G0 g0, C2688oE c2688oE, com.android.tools.r8.graph.o5 o5Var) {
        g0.b(c2688oE.a(), o5Var);
    }

    private void a(LinkedList linkedList, final C1013Gk c1013Gk, ExecutorService executorService) {
        if (!this.a.f().h()) {
            if (d) {
                return;
            }
            a(linkedList, executorService);
            return;
        }
        C3879c3.a(linkedList, new Consumer() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c1013Gk, (C2688oE) obj);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final C1013Gk c1013Gk, C2688oE c2688oE) {
        c2688oE.f().g(new Consumer() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c1013Gk, (com.android.tools.r8.graph.o5) obj);
            }
        }, new Predicate() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C0959Ek.c((com.android.tools.r8.graph.W0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C1013Gk c1013Gk, com.android.tools.r8.graph.o5 o5Var) {
        o5Var.a(((AbstractC1453Vm) o5Var.e().a1()).a(this.a.h0(), o5Var, c1013Gk), this.a);
    }

    private static void a(LinkedList linkedList, ExecutorService executorService) {
        C3879c3.a(linkedList, new Consumer() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0959Ek.a((C2688oE) obj);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C2688oE c2688oE) {
        if (!d && c2688oE.f().h(new Predicate() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C0959Ek.d((com.android.tools.r8.graph.W0) obj);
            }
        }).iterator().hasNext()) {
            throw new AssertionError("Expected no incomplete code");
        }
    }

    private AbstractC0681j0 a(final C1105Jk c1105Jk) {
        if (this.b.b()) {
            return this.a.e();
        }
        return this.a.e().c().a(new Predicate() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C0959Ek.a(c1105Jk, (C0729q2) obj);
            }
        }).b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C1105Jk c1105Jk, C0729q2 c0729q2) {
        return c1105Jk.a(c0729q2.getType());
    }

    private LinkedList a() {
        C2688oE c2688oE = new C2688oE();
        C2688oE c2688oE2 = new C2688oE();
        for (C0729q2 c0729q2 : this.a.f().e()) {
            if (c0729q2.isInterface()) {
                c2688oE2.a(c0729q2);
            } else {
                c2688oE.a(c0729q2);
            }
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(c2688oE);
        linkedList.add(c2688oE2);
        linkedList.removeIf(new Ek$$ExternalSyntheticLambda2());
        return linkedList;
    }

    private static void a(C0702m c0702m, LinkedList linkedList) {
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            C2688oE c2688oE = (C2688oE) it.next();
            if (!c2688oE.k()) {
                if (!d && !c0702m.f().h()) {
                    throw new AssertionError();
                }
                final C0729q2 c0729q2F = c2688oE.f();
                ((C0680j) c0702m.h0().f()).a(c0729q2F, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.Ek$$ExternalSyntheticLambda1
                    @Override // com.android.tools.r8.utils.G3
                    public final Object a(Object obj, Object obj2, Object obj3) {
                        return C0959Ek.a(c0729q2F, (C0767v2) obj, (AbstractC0727q0) obj2, (Boolean) obj3);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(C0729q2 c0729q2, C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0, Boolean bool) {
        if (!d && c0767v2 == c0729q2.getType()) {
            throw new AssertionError("Interface " + c0729q2.o1() + " inherits from itself");
        }
        return com.android.tools.r8.utils.D3.e();
    }
}
