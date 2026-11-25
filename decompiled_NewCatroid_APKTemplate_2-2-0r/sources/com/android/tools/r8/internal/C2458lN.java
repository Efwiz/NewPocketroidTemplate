package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.InterfaceC0694k5;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2458lN extends G7 {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C2626nc b;
    private final G7 c;
    private final InterfaceC3345xG d;

    private C2458lN(C0702m c0702m, C2626nc c2626nc, G7 g7) {
        this.a = c0702m;
        this.b = c2626nc;
        this.c = g7;
        this.d = C2845qN.a(c2626nc, InterfaceC3345xG.empty());
    }

    public static G7 a(C0702m c0702m, AbstractC2151hN abstractC2151hN, G7 g7) {
        abstractC2151hN.getClass();
        return abstractC2151hN instanceof IH ? g7 : new C2458lN(c0702m, abstractC2151hN.a(), g7);
    }

    private void c(final HA ha, com.android.tools.r8.graph.o5 o5Var) {
        AbstractC0748t0 abstractC0748t0C;
        final GA ga = ha.i;
        EnumC1953et enumC1953etC = ga.c();
        enumC1953etC.getClass();
        if (enumC1953etC != EnumC1953et.d && !ga.c().f() && (ga.b().B0() == o5Var.t() || (this.a.f().h() && (abstractC0748t0C = this.a.h().a(ga.b(), ga.d()).c()) != null && (abstractC0748t0C instanceof com.android.tools.r8.graph.o5)))) {
            this.b.a(ga.b(), new Consumer() { // from class: com.android.tools.r8.internal.lN$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2458lN.b(ha, ga, (InterfaceC1997fN) obj);
                }
            });
            return;
        }
        C2626nc c2626nc = this.b;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.lN$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2458lN.a(ha, ga, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var.getReference(), consumer);
    }

    @Override // com.android.tools.r8.internal.W3
    public final void b(final C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var) {
        if (this.a.Y().q0().f()) {
            C2626nc c2626nc = this.b;
            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.lN$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2458lN.a(c0729q2, (InterfaceC1997fN) obj);
                }
            };
            c2626nc.getClass();
            c2626nc.a(o5Var.getReference(), consumer);
        }
        this.c.b(c0729q2, o5Var);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2632ng
    public final void d(C0754u0 c0754u0) {
        this.c.d(c0754u0);
    }

    @Override // com.android.tools.r8.internal.W3
    public final void e(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.b(o5Var, o5Var2);
        this.c.e(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.AZ
    public final void f(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.b(o5Var, o5Var2);
        this.c.f(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.GQ
    public final void g(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.b(o5Var, o5Var2);
        this.c.g(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1720bs
    public final void h(final com.android.tools.r8.graph.o5 o5Var, final com.android.tools.r8.graph.o5 o5Var2) {
        C2626nc c2626nc = this.b;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.lN$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2458lN.a(o5Var2, o5Var, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var.getReference(), consumer);
        this.c.h(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1869ds
    public final void i(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.b(o5Var, o5Var2);
        this.c.i(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.GQ
    public final void j(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.b(o5Var, o5Var2);
        this.c.j(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1720bs
    public final void k(final com.android.tools.r8.graph.o5 o5Var, final com.android.tools.r8.graph.o5 o5Var2) {
        C2626nc c2626nc = this.b;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.lN$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2458lN.b(o5Var2, o5Var, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var.getReference(), consumer);
        this.c.k(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1720bs
    public final void l(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.b(o5Var2, o5Var);
        this.c.l(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1098Je
    public final void m(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.b(o5Var, o5Var2);
        this.c.m(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void n(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.b(o5Var, o5Var2);
        this.c.n(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.GQ
    public final void o(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.a(o5Var, o5Var2);
        this.c.o(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1370Te
    public final void p(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.b(o5Var, o5Var2);
        this.c.p(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void q(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.b(o5Var2, o5Var);
        this.c.q(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void r(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.b(o5Var, o5Var2);
        this.c.r(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void s(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.b(o5Var, o5Var2);
        this.c.s(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void t(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.b(o5Var, o5Var2);
        this.c.t(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.GQ
    public final void u(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.a(o5Var, o5Var2);
        this.c.u(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1471We
    public final void v(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.b(o5Var, o5Var2);
        this.c.v(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void d(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.b(o5Var, o5Var2);
        this.c.d(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1398Ue
    public final void e(C0754u0 c0754u0) {
        this.c.e(c0754u0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0729q2 c0729q2, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        interfaceC1997fN.a(c0729q2.getReference());
        c0729q2.l(new lN$$ExternalSyntheticLambda1(interfaceC1997fN));
    }

    @Override // com.android.tools.r8.internal.InterfaceC1398Ue
    public final void b(C0754u0 c0754u0) {
        this.c.b(c0754u0);
    }

    private void b(final HA ha, com.android.tools.r8.graph.o5 o5Var) {
        C2626nc c2626nc = this.b;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.lN$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(ha, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var.getReference(), consumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(HA ha, GA ga, InterfaceC1997fN interfaceC1997fN) {
        C0729q2 c0729q2D = ha.d();
        Objects.requireNonNull(interfaceC1997fN);
        lN$$ExternalSyntheticLambda1 ln__externalsyntheticlambda1 = new lN$$ExternalSyntheticLambda1(interfaceC1997fN);
        c0729q2D.getClass();
        c0729q2D.h(ln__externalsyntheticlambda1, CM.b());
        if (ga.a() != ga.b()) {
            interfaceC1997fN.a((AbstractC0744s2) ga.a());
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC1124Kc
    public final void a(final C1096Jc c1096Jc, com.android.tools.r8.graph.o5 o5Var) {
        if (this.a.Y().q0().g()) {
            C2626nc c2626nc = this.b;
            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.lN$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2458lN.a(c1096Jc, (InterfaceC1997fN) obj);
                }
            };
            c2626nc.getClass();
            c2626nc.a(o5Var.getReference(), consumer);
        }
        this.c.a(c1096Jc, o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C1096Jc c1096Jc, InterfaceC1997fN interfaceC1997fN) {
        C0729q2 c0729q2A = c1096Jc.a();
        interfaceC1997fN.getClass();
        interfaceC1997fN.a(c0729q2A.getReference());
        c0729q2A.l(new lN$$ExternalSyntheticLambda1(interfaceC1997fN));
    }

    @Override // com.android.tools.r8.internal.InterfaceC3345xG
    public final void b(com.android.tools.r8.graph.m5 m5Var, com.android.tools.r8.graph.o5 o5Var, AbstractC0748t0 abstractC0748t0) {
        this.d.b(m5Var, o5Var, abstractC0748t0);
        this.c.b(m5Var, o5Var, abstractC0748t0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        InterfaceC1997fN interfaceC1997fNA = interfaceC1997fN.a(o5Var.getReference());
        C0729q2 c0729q2A = o5Var.a();
        interfaceC1997fNA.getClass();
        InterfaceC1997fN interfaceC1997fNA2 = interfaceC1997fNA.a(c0729q2A.getReference());
        interfaceC1997fNA2.getClass();
        interfaceC1997fNA2.a(o5Var2.getReference(), o5Var);
        o5Var.a().i(new lN$$ExternalSyntheticLambda1(interfaceC1997fN));
    }

    @Override // com.android.tools.r8.internal.InterfaceC1124Kc
    public final void a(final C0684j2 c0684j2, final com.android.tools.r8.graph.o5 o5Var) {
        this.b.a(c0684j2, new Consumer() { // from class: com.android.tools.r8.internal.lN$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2458lN.a(o5Var, c0684j2, (InterfaceC1997fN) obj);
            }
        });
        this.c.a(c0684j2, o5Var);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2128h20
    public final void b(C0729q2 c0729q2) {
        this.c.b(c0729q2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.graph.o5 o5Var, C0684j2 c0684j2, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        interfaceC1997fN.a(o5Var.getReference()).a(c0684j2, o5Var);
    }

    @Override // com.android.tools.r8.internal.G7
    public final void b() {
        if (e) {
            return;
        }
        this.c.b();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2749p2
    public final void c(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.b.b(o5Var, o5Var2);
        this.c.c(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1720bs
    public final void a(com.android.tools.r8.graph.o5 o5Var, final com.android.tools.r8.graph.o5 o5Var2) {
        C2626nc c2626nc = this.b;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.lN$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2458lN.a(o5Var2, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var.getReference(), consumer);
        this.c.a(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1398Ue
    public final void c(C0754u0 c0754u0) {
        this.c.c(c0754u0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        InterfaceC1997fN interfaceC1997fNA = interfaceC1997fN.a(o5Var.getReference());
        C0729q2 c0729q2A = o5Var.a();
        interfaceC1997fNA.getClass();
        interfaceC1997fNA.a(c0729q2A.getReference());
        o5Var.a().i(new lN$$ExternalSyntheticLambda1(interfaceC1997fN));
    }

    @Override // com.android.tools.r8.internal.InterfaceC1098Je
    public final void a(C0754u0 c0754u0) {
        this.c.a(c0754u0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1567Zs
    public final void a(C1514Xs c1514Xs) {
        this.b.a(c1514Xs.a(), c1514Xs.b());
        this.c.a(c1514Xs);
    }

    @Override // com.android.tools.r8.internal.LA
    public final void a(HA ha, com.android.tools.r8.graph.o5 o5Var) {
        b(ha, o5Var);
        c(ha, o5Var);
        this.c.a(ha, o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(HA ha, InterfaceC1997fN interfaceC1997fN) {
        C0729q2 c0729q2D = ha.d();
        interfaceC1997fN.getClass();
        interfaceC1997fN.a(c0729q2D.getReference());
        if (c0729q2D.p1()) {
            interfaceC1997fN.a(c0729q2D.X1().getReference());
        }
        c0729q2D.f(new lN$$ExternalSyntheticLambda1(interfaceC1997fN), CM.b());
        if (this.a.Y().n1.K0) {
            c0729q2D.m(new lN$$ExternalSyntheticLambda1(interfaceC1997fN));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(HA ha, GA ga, InterfaceC1997fN interfaceC1997fN) {
        C0729q2 c0729q2D = ha.d();
        Objects.requireNonNull(interfaceC1997fN);
        lN$$ExternalSyntheticLambda1 ln__externalsyntheticlambda1 = new lN$$ExternalSyntheticLambda1(interfaceC1997fN);
        c0729q2D.getClass();
        c0729q2D.h(ln__externalsyntheticlambda1, CM.b());
        if (ga.a() != ga.b()) {
            interfaceC1997fN.a((AbstractC0744s2) ga.a());
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC3345xG
    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, C0729q2 c0729q2, AbstractC0748t0 abstractC0748t0) {
        this.d.a(o5Var, o5Var2, c0729q2, abstractC0748t0);
        this.c.a(o5Var, o5Var2, c0729q2, abstractC0748t0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3345xG
    public final void a(com.android.tools.r8.graph.m5 m5Var, com.android.tools.r8.graph.o5 o5Var, AbstractC0748t0 abstractC0748t0) {
        this.d.a(m5Var, o5Var, abstractC0748t0);
        this.c.a(m5Var, o5Var, abstractC0748t0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3345xG
    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, AbstractC0748t0 abstractC0748t0) {
        this.d.a(o5Var, o5Var2, abstractC0748t0);
        this.c.a(o5Var, o5Var2, abstractC0748t0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        InterfaceC1997fN interfaceC1997fNA = interfaceC1997fN.a(o5Var.getReference());
        C0729q2 c0729q2A = o5Var.a();
        interfaceC1997fNA.getClass();
        InterfaceC1997fN interfaceC1997fNA2 = interfaceC1997fNA.a(c0729q2A.getReference());
        interfaceC1997fNA2.getClass();
        interfaceC1997fNA2.a(o5Var2.getReference(), o5Var);
        o5Var.a().i(new lN$$ExternalSyntheticLambda1(interfaceC1997fN));
    }

    @Override // com.android.tools.r8.internal.HQ
    public final void a(C0729q2 c0729q2) {
        this.c.a(c0729q2);
    }

    @Override // com.android.tools.r8.internal.GQ
    public final void a(C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var) {
        this.c.a(c0729q2, o5Var);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2128h20
    public final void a(C0729q2 c0729q2, InterfaceC0694k5 interfaceC0694k5) {
        AbstractC3055tN.a(c0729q2, interfaceC0694k5, this.b, this.a.Y().q0());
        this.c.a(c0729q2, interfaceC0694k5);
    }

    @Override // com.android.tools.r8.internal.G7
    public final List a() {
        return this.c.a();
    }
}
