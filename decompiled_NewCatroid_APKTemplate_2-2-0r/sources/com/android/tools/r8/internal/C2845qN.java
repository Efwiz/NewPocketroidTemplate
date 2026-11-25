package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0729q2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2845qN implements InterfaceC3345xG {
    private final C2626nc a;
    private final InterfaceC3345xG b;

    private C2845qN(C2626nc c2626nc, InterfaceC3345xG interfaceC3345xG) {
        this.a = c2626nc;
        this.b = interfaceC3345xG;
    }

    public static InterfaceC3345xG a(AbstractC2151hN abstractC2151hN, InterfaceC3345xG interfaceC3345xG) {
        abstractC2151hN.getClass();
        return abstractC2151hN instanceof IH ? interfaceC3345xG : new C2845qN(abstractC2151hN.a(), interfaceC3345xG);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3345xG
    public final void b(com.android.tools.r8.graph.m5 m5Var, com.android.tools.r8.graph.o5 o5Var, AbstractC0748t0 abstractC0748t0) {
        this.a.a(abstractC0748t0, o5Var);
        this.b.b(m5Var, o5Var, abstractC0748t0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3345xG
    public final void a(com.android.tools.r8.graph.o5 o5Var, final com.android.tools.r8.graph.o5 o5Var2, final C0729q2 c0729q2, AbstractC0748t0 abstractC0748t0) {
        abstractC0748t0.getClass();
        if (abstractC0748t0 instanceof com.android.tools.r8.graph.o5) {
            C2626nc c2626nc = this.a;
            com.android.tools.r8.graph.o5 o5VarG0 = abstractC0748t0.g0();
            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.qN$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2845qN.a(c0729q2, o5Var2, (InterfaceC1997fN) obj);
                }
            };
            c2626nc.getClass();
            c2626nc.a(o5VarG0.getReference(), consumer);
        } else {
            this.a.a(new Consumer() { // from class: com.android.tools.r8.internal.qN$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2845qN.a(c0729q2, o5Var2, (AbstractC2069gN) obj);
                }
            });
        }
        this.b.a(o5Var, o5Var2, c0729q2, abstractC0748t0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        InterfaceC1997fN interfaceC1997fNA = interfaceC1997fN.a(c0729q2.getReference());
        interfaceC1997fNA.getClass();
        interfaceC1997fNA.a(o5Var.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var, AbstractC2069gN abstractC2069gN) {
        abstractC2069gN.getClass();
        abstractC2069gN.a(c0729q2.getType());
        abstractC2069gN.f().a(o5Var, new C1609aN$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.internal.InterfaceC3345xG
    public final void a(com.android.tools.r8.graph.m5 m5Var, com.android.tools.r8.graph.o5 o5Var, AbstractC0748t0 abstractC0748t0) {
        this.a.a(abstractC0748t0, o5Var);
        this.b.a(m5Var, o5Var, abstractC0748t0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3345xG
    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, AbstractC0748t0 abstractC0748t0) {
        this.a.a(abstractC0748t0, o5Var2);
        this.b.a(o5Var, o5Var2, abstractC0748t0);
    }
}
