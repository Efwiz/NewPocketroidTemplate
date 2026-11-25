package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3912j0;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2533mN extends AbstractC2591n8 {
    private final C2626nc a;
    private final D3 b;
    private final AbstractC2591n8 c;

    private C2533mN(C2626nc c2626nc, D3 d3, AbstractC2591n8 abstractC2591n8) {
        this.a = c2626nc;
        this.b = d3;
        this.c = abstractC2591n8;
    }

    public static AbstractC2591n8 a(C0702m c0702m, AbstractC2151hN abstractC2151hN, AbstractC2591n8 abstractC2591n8) {
        abstractC2151hN.getClass();
        return abstractC2151hN instanceof IH ? abstractC2591n8 : new C2533mN(abstractC2151hN.a(), c0702m.Y().q0(), abstractC2591n8);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1344Se
    public final void b(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.a.a(o5Var, o5Var2);
        this.c.b(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1398Ue
    public final void c(C0754u0 c0754u0) {
        this.c.c(c0754u0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1398Ue
    public final void e(C0754u0 c0754u0) {
        this.c.e(c0754u0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1098Je
    public final void m(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.a.b(o5Var2, o5Var);
        this.c.m(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1471We
    public final void v(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.a.b(o5Var, o5Var2);
        this.c.v(o5Var, o5Var2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1398Ue
    public final void b(C0754u0 c0754u0) {
        this.c.b(c0754u0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.a(o5Var.getReference());
    }

    @Override // com.android.tools.r8.internal.AbstractC2591n8
    public final Set b() {
        return this.c.b();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1098Je
    public final void a(C0754u0 c0754u0) {
        this.c.a(c0754u0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1153Le
    public final void a(final com.android.tools.r8.graph.o5 o5Var, C2404kg c2404kg) {
        if (this.b.h()) {
            this.a.a(new Consumer() { // from class: com.android.tools.r8.internal.mN$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2533mN.a(o5Var, (AbstractC2069gN) obj);
                }
            });
        }
        this.c.a(o5Var, c2404kg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.o5 o5Var, AbstractC2069gN abstractC2069gN) {
        abstractC2069gN.a(o5Var, new C1609aN$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.internal.InterfaceC2262is
    public final void a(C0729q2 c0729q2, C0754u0 c0754u0) {
        this.c.a(c0729q2, c0754u0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1153Le
    public final void a(C0729q2 c0729q2, AbstractC0727q0 abstractC0727q0) {
        this.c.a(c0729q2, abstractC0727q0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2262is
    public final void a(AbstractC0748t0 abstractC0748t0, com.android.tools.r8.graph.o5 o5Var) {
        this.a.a(abstractC0748t0, o5Var);
        this.c.a(abstractC0748t0, o5Var);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2262is
    public final void a(final com.android.tools.r8.graph.o5 o5Var, C0767v2 c0767v2, com.android.tools.r8.graph.I4.a aVar) {
        if (this.b.i()) {
            final com.android.tools.r8.utils.K k = new com.android.tools.r8.utils.K();
            aVar.a(C3912j0.b(), new Consumer() { // from class: com.android.tools.r8.internal.mN$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(o5Var, k, (com.android.tools.r8.graph.W0) obj);
                }
            });
            if (k.d()) {
                this.a.a(new Consumer() { // from class: com.android.tools.r8.internal.mN$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C2533mN.b(o5Var, (InterfaceC1997fN) obj);
                    }
                }, o5Var.a());
            }
        }
        this.c.a(o5Var, c0767v2, aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.utils.K k, com.android.tools.r8.graph.W0 w0) {
        this.a.a(w0.getReference(), new Consumer() { // from class: com.android.tools.r8.internal.mN$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2533mN.a(o5Var, (InterfaceC1997fN) obj);
            }
        });
        k.f();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        interfaceC1997fN.a(o5Var.getReference());
    }

    @Override // com.android.tools.r8.internal.AbstractC2591n8
    public final void a() {
        this.c.a();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2262is
    public final void a(C0729q2 c0729q2, C0767v2 c0767v2, C1566Zr c1566Zr) {
        this.c.a(c0729q2, c0767v2, c1566Zr);
    }
}
