package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2379kN extends S6 {
    private final C2626nc a;
    private final D3 b;
    private final S6 c;

    private C2379kN(C2626nc c2626nc, D3 d3, S6 s6) {
        this.a = c2626nc;
        this.b = d3;
        this.c = s6;
    }

    public static S6 a(C0702m c0702m, S6 s6, AbstractC2151hN abstractC2151hN) {
        abstractC2151hN.getClass();
        return abstractC2151hN instanceof IH ? s6 : new C2379kN(abstractC2151hN.a(), c0702m.Y().q0(), s6);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2128h20
    public final void b(C0729q2 c0729q2) {
        this.c.b(c0729q2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1126Ke
    public final void c(C0729q2 c0729q2) {
        this.c.c(c0729q2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2721og
    public final void d(C0729q2 c0729q2) {
        this.c.d(c0729q2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1445Ve
    public final void e(C0729q2 c0729q2) {
        this.c.e(c0729q2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1445Ve
    public final void f(C0729q2 c0729q2) {
        this.c.f(c0729q2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1471We
    public final void v(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.a.b(o5Var, o5Var2);
        this.c.v(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.S6
    public final void b(C0702m c0702m) {
        this.a.a(c0702m);
        this.c.b(c0702m);
    }

    @Override // com.android.tools.r8.internal.HQ
    public final void a(C0729q2 c0729q2) {
        this.c.a(c0729q2);
    }

    @Override // com.android.tools.r8.internal.FQ
    public final void a(final C0729q2 c0729q2, C0729q2 c0729q3) {
        this.a.a(new Consumer() { // from class: com.android.tools.r8.internal.kN$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2379kN.a(c0729q2, (InterfaceC1997fN) obj);
            }
        }, c0729q3);
        final com.android.tools.r8.graph.o5 o5VarY1 = c0729q2.Y1();
        if (o5VarY1 != null) {
            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.kN$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(o5VarY1, (com.android.tools.r8.graph.o5) obj);
                }
            };
            c0729q3.getClass();
            c0729q3.f(consumer, CM.b());
        }
        this.c.a(c0729q2, c0729q3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0729q2 c0729q2, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.a(c0729q2.getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        C2626nc c2626nc = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.kN$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2379kN.a(o5Var, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var2.getReference(), consumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        interfaceC1997fN.a(o5Var.getReference());
    }

    @Override // com.android.tools.r8.internal.InterfaceC2128h20
    public final void a(C0729q2 c0729q2, InterfaceC0694k5 interfaceC0694k5) {
        AbstractC3055tN.a(c0729q2, interfaceC0694k5, this.a, this.b);
        this.c.a(c0729q2, interfaceC0694k5);
    }

    @Override // com.android.tools.r8.internal.S6
    public final Set a() {
        return this.c.a();
    }
}
