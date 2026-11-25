package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2774pN extends VE {
    private final C2626nc a;
    private final VE b;

    private C2774pN(C2626nc c2626nc, VE ve) {
        this.a = c2626nc;
        this.b = ve;
    }

    public static VE a(C0702m c0702m, VE ve) {
        AbstractC2151hN abstractC2151hNB = AbstractC2151hN.b(c0702m);
        abstractC2151hNB.getClass();
        return abstractC2151hNB instanceof IH ? ve : new C2774pN(abstractC2151hNB.a(), ve);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        InterfaceC1997fN interfaceC1997fNA = interfaceC1997fN.a(o5Var.getReference());
        C0729q2 c0729q2A = o5Var.a();
        interfaceC1997fNA.getClass();
        interfaceC1997fNA.a(c0729q2A.getReference());
        o5Var.a().i(new lN$$ExternalSyntheticLambda1(interfaceC1997fN));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        InterfaceC1997fN interfaceC1997fNA = interfaceC1997fN.a(o5Var.getReference());
        C0729q2 c0729q2A = o5Var.a();
        interfaceC1997fNA.getClass();
        interfaceC1997fNA.a(c0729q2A.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void h(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        InterfaceC1997fN interfaceC1997fNA = interfaceC1997fN.a(o5Var.getReference());
        C0729q2 c0729q2A = o5Var.a();
        interfaceC1997fNA.getClass();
        interfaceC1997fNA.a(c0729q2A.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void i(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        InterfaceC1997fN interfaceC1997fNA = interfaceC1997fN.a(o5Var.getReference());
        C0729q2 c0729q2A = o5Var.a();
        interfaceC1997fNA.getClass();
        interfaceC1997fNA.a(c0729q2A.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void j(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        InterfaceC1997fN interfaceC1997fNA = interfaceC1997fN.a(o5Var.getReference());
        C0729q2 c0729q2A = o5Var.a();
        interfaceC1997fNA.getClass();
        interfaceC1997fNA.a(c0729q2A.getReference());
    }

    @Override // com.android.tools.r8.internal.InterfaceC1069Ig
    public final void b(final com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        C2626nc c2626nc = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.pN$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2774pN.a(o5Var, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var2.getReference(), consumer);
        this.b.b(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1069Ig
    public final void e(final com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        C2626nc c2626nc = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.pN$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2774pN.c(o5Var, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var2.getReference(), consumer);
        this.b.e(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3543b
    public final void f(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.a.b(o5Var, o5Var2);
        this.b.f(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1069Ig
    public final void g(final com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        C2626nc c2626nc = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.pN$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2774pN.b(o5Var, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var2.getReference(), consumer);
        this.b.g(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void n(final com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        C2626nc c2626nc = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.pN$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2774pN.i(o5Var, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var2.getReference(), consumer);
        this.b.n(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void q(final com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        C2626nc c2626nc = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.pN$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2774pN.j(o5Var, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var2.getReference(), consumer);
        this.b.q(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void r(final com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        C2626nc c2626nc = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.pN$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2774pN.h(o5Var, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var2.getReference(), consumer);
        this.b.r(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void s(final com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        C2626nc c2626nc = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.pN$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2774pN.e(o5Var, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var2.getReference(), consumer);
        this.b.s(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void t(final com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        C2626nc c2626nc = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.pN$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2774pN.g(o5Var, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var2.getReference(), consumer);
        this.b.t(o5Var, o5Var2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void f(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        InterfaceC1997fN interfaceC1997fNA = interfaceC1997fN.a(o5Var.getReference());
        C0729q2 c0729q2A = o5Var.a();
        interfaceC1997fNA.getClass();
        interfaceC1997fNA.a(c0729q2A.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        InterfaceC1997fN interfaceC1997fNA = interfaceC1997fN.a(o5Var.getReference());
        C0729q2 c0729q2A = o5Var.a();
        interfaceC1997fNA.getClass();
        interfaceC1997fNA.a(c0729q2A.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void e(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        InterfaceC1997fN interfaceC1997fNA = interfaceC1997fN.a(o5Var.getReference());
        C0729q2 c0729q2A = o5Var.a();
        interfaceC1997fNA.getClass();
        interfaceC1997fNA.a(c0729q2A.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void g(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        InterfaceC1997fN interfaceC1997fNA = interfaceC1997fN.a(o5Var.getReference());
        C0729q2 c0729q2A = o5Var.a();
        interfaceC1997fNA.getClass();
        interfaceC1997fNA.a(c0729q2A.getReference());
    }

    @Override // com.android.tools.r8.ir.optimize.V0
    public final void d(final com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        C2626nc c2626nc = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.pN$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2774pN.f(o5Var, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var2.getReference(), consumer);
        this.b.d(o5Var, o5Var2);
    }

    public static VE a(AbstractC2151hN abstractC2151hN, VE ve) {
        abstractC2151hN.getClass();
        return abstractC2151hN instanceof IH ? ve : new C2774pN(abstractC2151hN.a(), ve);
    }

    @Override // com.android.tools.r8.ir.optimize.H0
    public final void c(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.a.b(o5Var, o5Var2);
        this.b.c(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.VE
    public final void b(C0702m c0702m) {
        this.a.a(c0702m);
        this.b.b(c0702m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        InterfaceC1997fN interfaceC1997fNA = interfaceC1997fN.a(o5Var.getReference());
        C0729q2 c0729q2A = o5Var.a();
        interfaceC1997fNA.getClass();
        interfaceC1997fNA.a(c0729q2A.getReference());
    }

    @Override // com.android.tools.r8.internal.InterfaceC1562Zn
    public final void a(final com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        C2626nc c2626nc = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.pN$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2774pN.d(o5Var, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var2.getReference(), consumer);
        this.b.a(o5Var, o5Var2);
    }
}
