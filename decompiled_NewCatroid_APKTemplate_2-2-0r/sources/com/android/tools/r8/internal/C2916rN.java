package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2916rN implements TK {
    private final C2626nc a;
    private final TK b;

    private C2916rN(C2626nc c2626nc, SK sk) {
        this.a = c2626nc;
        this.b = sk;
    }

    public static TK a(C0702m c0702m, SK sk) {
        AbstractC2151hN abstractC2151hNB = AbstractC2151hN.b(c0702m);
        abstractC2151hNB.getClass();
        return abstractC2151hNB instanceof IH ? sk : new C2916rN(abstractC2151hNB.a(), sk);
    }

    @Override // com.android.tools.r8.internal.TK
    public final void b(C0702m c0702m) {
        this.a.a(c0702m);
        this.b.b(c0702m);
    }

    @Override // com.android.tools.r8.internal.TK
    public final void a(final com.android.tools.r8.graph.o5 o5Var, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            com.android.tools.r8.graph.o5 o5Var2 = (com.android.tools.r8.graph.o5) it.next();
            C2626nc c2626nc = this.a;
            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.rN$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2916rN.a(o5Var, (InterfaceC1997fN) obj);
                }
            };
            c2626nc.getClass();
            c2626nc.a(o5Var2.getReference(), consumer);
        }
        this.b.a(o5Var, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        InterfaceC1997fN interfaceC1997fNA = interfaceC1997fN.a(o5Var.getReference());
        C0729q2 c0729q2A = o5Var.a();
        interfaceC1997fNA.getClass();
        interfaceC1997fNA.a(c0729q2A.getReference());
    }
}
