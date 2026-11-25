package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.AbstractC0674i0;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.ir.optimize.C3541a;
import com.android.tools.r8.position.MethodPosition;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.InterfaceC3877c1;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class PM extends C3012sl {
    static final /* synthetic */ boolean P = true;
    private final com.android.tools.r8.utils.y3 O;

    public PM(C0702m c0702m, com.android.tools.r8.utils.y3 y3Var) {
        super(c0702m);
        this.O = y3Var;
    }

    private void b(C0825Ad c0825Ad, C2569ms c2569ms, ExecutorService executorService) {
        AbstractC2591n8 abstractC2591n8A = AbstractC2591n8.a(this.a, c0825Ad.e(), c0825Ad, this.d);
        c0825Ad.f();
        AbstractC2360k8.a(this.a, this.d.a(c2569ms), new Predicate() { // from class: com.android.tools.r8.internal.PM$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PM.e((com.android.tools.r8.graph.o5) obj);
            }
        }).a(this.a.f().d(), abstractC2591n8A, executorService);
        c0825Ad.d();
        abstractC2591n8A.a();
    }

    private void c() {
        this.d.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.PM$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                ((C0919Dd) obj).a();
            }
        });
    }

    private void d() {
        this.d.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.PM$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                ((C0919Dd) obj).c();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean e(com.android.tools.r8.graph.o5 o5Var) {
        return true;
    }

    public final void a(C0702m c0702m, ExecutorService executorService) {
        if (c0702m.Y().A0.b()) {
            KA.a(c0702m, c0702m.f().d());
        }
        a(executorService);
        AbstractC0681j0 abstractC0681j0B = c0702m.f().b();
        AbstractC2151hN abstractC2151hNB = AbstractC2151hN.b(c0702m);
        C0825Ad c0825Ad = new C0825Ad(abstractC2151hNB, this, executorService);
        C2569ms c2569msA = C2569ms.a(c0702m);
        this.O.a("IR conversion");
        a(c0825Ad, c2569msA, executorService);
        d();
        c();
        C1536Yn c1536Yn = this.y;
        if (c1536Yn != null) {
            a(c1536Yn.a(), executorService);
        }
        C3541a c3541a = this.l;
        if (c3541a != null) {
            a(c3541a.a(), executorService);
        }
        if (c0702m.D().d()) {
            c0702m.d(new C0664h(c0702m.f().g().a(abstractC0681j0B), c0702m.f().f()));
            abstractC0681j0B = c0702m.f().b();
        }
        b(c0825Ad, c2569msA, executorService);
        if (c0702m.D().d()) {
            c0702m.d(new C0664h(c0702m.f().g().a(abstractC0681j0B), c0702m.f().f()));
            abstractC0681j0B = c0702m.f().b();
        }
        AbstractC0674i0 abstractC0674i0A = abstractC0681j0B.c().a(this.H);
        if (c0702m.Y().C1.d()) {
            new C2478lg(c0702m).a(abstractC0674i0A);
            new C3188vA(c0702m).a();
        }
        C2627nd c2627nd = this.s;
        if (c2627nd != null) {
            c2627nd.a(abstractC0674i0A, InterfaceC2794pd.a(abstractC2151hNB));
        }
        this.O.c();
        c0702m.d(new C0664h(c0702m.f().g().a(abstractC0674i0A.b()), c0702m.f().f()));
        abstractC2151hNB.a(c0702m);
    }

    final com.android.tools.r8.utils.y3 b(final com.android.tools.r8.graph.o5 o5Var, final G7 g7, final com.android.tools.r8.ir.optimize.info.n nVar, final TE te, final C1230Ob c1230Ob) {
        return (com.android.tools.r8.utils.y3) com.android.tools.r8.utils.R0.a(o5Var.getOrigin(), new MethodPosition(o5Var.getReference().E0()), new Supplier() { // from class: com.android.tools.r8.internal.PM$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.a(o5Var, g7, nVar, te, c1230Ob);
            }
        });
    }

    final void a(final C0729q2 c0729q2, G7 g7, C0825Ad c0825Ad, C2569ms c2569ms) {
        com.android.tools.r8.graph.o5 o5VarX1 = c0729q2.X1();
        ArrayList<com.android.tools.r8.graph.o5> arrayListA = com.android.tools.r8.utils.V1.a(new InterfaceC3877c1() { // from class: com.android.tools.r8.internal.PM$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.utils.InterfaceC3877c1
            public final void forEach(Consumer consumer) {
                c0729q2.l((Consumer<? super com.android.tools.r8.graph.o5>) consumer);
            }
        });
        if (o5VarX1 != null) {
            c0825Ad.b(o5VarX1, g7);
        }
        for (com.android.tools.r8.graph.o5 o5Var : arrayListA) {
            if (!o5Var.e().u1()) {
                c0825Ad.b(o5Var, g7);
                if (c2569ms != null) {
                    c2569ms.a(o5Var, g7);
                }
            }
        }
        if ((this.j.f instanceof ClassFileConsumer) && c0729q2.Z1()) {
            c0729q2.a(this.a.Y().b(c0729q2.U1()));
        }
    }

    private void a(C0825Ad c0825Ad, C2569ms c2569ms, ExecutorService executorService) {
        M9 k9;
        C0684j2 c0684j2;
        C0702m c0702m = this.a;
        if (c0702m.Y().i0().b) {
            k9 = new L9(c0702m, this, c0825Ad, c2569ms);
        } else {
            k9 = new K9(c0702m, this, c0825Ad, c2569ms);
        }
        O9 o9A = k9.a(executorService);
        a(c0825Ad, executorService);
        c0825Ad.g();
        C0702m c0702m2 = this.a;
        Map mapA = o9A.a();
        if (!mapA.isEmpty()) {
            for (C0729q2 c0729q2 : c0702m2.f().d()) {
                if (c0729q2.r1() && (c0684j2 = (C0684j2) mapA.get(c0729q2.b1().b())) != null) {
                    c0729q2.a(new com.android.tools.r8.graph.O2(c0684j2));
                }
            }
        }
        this.d.a(new PM$$ExternalSyntheticLambda6());
    }

    final void a(final G7 g7, ExecutorService executorService) {
        final C3124uN c3124uN = new C3124uN();
        C3879c3.a(this.a.f().d(), new Consumer() { // from class: com.android.tools.r8.internal.PM$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(g7, c3124uN, (C0729q2) obj);
            }
        }, executorService);
        c3124uN.a(executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final G7 g7, final C3124uN c3124uN, C0729q2 c0729q2) {
        c0729q2.g(new Consumer() { // from class: com.android.tools.r8.internal.PM$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(g7, c3124uN, (com.android.tools.r8.graph.o5) obj);
            }
        }, new Predicate() { // from class: com.android.tools.r8.internal.PM$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PM.a((com.android.tools.r8.graph.W0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.W0 w0) {
        return w0.q1() && w0.a1().z0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(G7 g7, C3124uN c3124uN, com.android.tools.r8.graph.o5 o5Var) {
        this.d.a(g7, c3124uN, o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.utils.y3 a(com.android.tools.r8.graph.o5 o5Var, G7 g7, com.android.tools.r8.ir.optimize.info.n nVar, TE te, C1230Ob c1230Ob) {
        if (o5Var.e().a1().z0()) {
            this.d.a(o5Var, g7);
            if (this.d.a(o5Var)) {
                this.d.a(c1230Ob, o5Var, g7);
            }
        }
        return a(o5Var, nVar, te, c1230Ob);
    }

    private void a(final C0825Ad c0825Ad, final ExecutorService executorService) {
        this.d.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.PM$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                ((C0919Dd) obj).a(c0825Ad, executorService);
            }
        });
        c0825Ad.d();
    }
}
