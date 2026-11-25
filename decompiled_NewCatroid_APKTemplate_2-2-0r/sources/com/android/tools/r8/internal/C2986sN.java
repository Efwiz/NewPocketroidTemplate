package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2986sN implements com.android.tools.r8.shaking.U3 {
    private final C2626nc a;
    private final com.android.tools.r8.shaking.U3 b;

    private C2986sN(C2626nc c2626nc, com.android.tools.r8.shaking.T3 t3) {
        this.a = c2626nc;
        this.b = t3;
    }

    public static com.android.tools.r8.shaking.U3 a(AbstractC2151hN abstractC2151hN, com.android.tools.r8.shaking.T3 t3) {
        abstractC2151hN.getClass();
        return abstractC2151hN instanceof IH ? t3 : new C2986sN(abstractC2151hN.a(), t3);
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
    }

    @Override // com.android.tools.r8.internal.InterfaceC1720bs
    public final void h(final com.android.tools.r8.graph.o5 o5Var, final com.android.tools.r8.graph.o5 o5Var2) {
        C2626nc c2626nc = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.sN$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2986sN.a(o5Var2, o5Var, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var.getReference(), consumer);
        this.b.h(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1720bs
    public final void k(final com.android.tools.r8.graph.o5 o5Var, final com.android.tools.r8.graph.o5 o5Var2) {
        C2626nc c2626nc = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.sN$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2986sN.b(o5Var2, o5Var, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var.getReference(), consumer);
        this.b.k(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1720bs
    public final void l(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.a.b(o5Var2, o5Var);
        this.b.l(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1720bs
    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.a.b(o5Var2, o5Var);
        this.b.a(o5Var, o5Var2);
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
    }
}
