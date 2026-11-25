package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2301jN implements R2 {
    private final C2626nc a;
    private final R2 b;

    private C2301jN(C2626nc c2626nc, Q2 q2) {
        this.a = c2626nc;
        this.b = q2;
    }

    public static R2 a(C0702m c0702m, Q2 q2) {
        AbstractC2151hN abstractC2151hNB = AbstractC2151hN.b(c0702m);
        abstractC2151hNB.getClass();
        return abstractC2151hNB instanceof IH ? q2 : new C2301jN(abstractC2151hNB.a(), q2);
    }

    @Override // com.android.tools.r8.internal.R2
    public final void b(C0702m c0702m) {
        this.a.a(c0702m);
        this.b.b(c0702m);
    }

    @Override // com.android.tools.r8.internal.R2
    public final void a(final C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var) {
        C2626nc c2626nc = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.jN$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2301jN.a(c0729q2, (InterfaceC1997fN) obj);
            }
        };
        c2626nc.getClass();
        c2626nc.a(o5Var.getReference(), consumer);
        this.b.a(c0729q2, o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0729q2 c0729q2, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        interfaceC1997fN.a(c0729q2.getReference());
    }
}
