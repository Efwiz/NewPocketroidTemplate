package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.IdentityHashMap;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2057gE {
    private final C0702m a;
    private final AbstractC1476Wj b;
    private final IdentityHashMap c;
    private final IdentityHashMap d;

    private C2057gE(C0702m c0702m, AbstractC1476Wj abstractC1476Wj) {
        this.c = new IdentityHashMap();
        this.d = new IdentityHashMap();
        this.a = c0702m;
        this.b = abstractC1476Wj;
    }

    final void a(final com.android.tools.r8.graph.R2 r2) {
        ((com.android.tools.r8.graph.U2) r2).b(new Consumer() { // from class: com.android.tools.r8.internal.gE$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(r2, (com.android.tools.r8.graph.Y0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.R2 r2, com.android.tools.r8.graph.Y0 y0) {
        this.c.put(y0, ((com.android.tools.r8.graph.U2) r2).e());
    }

    final void a(C0684j2 c0684j2) {
        AbstractC0727q0 abstractC0727q0A;
        com.android.tools.r8.graph.I4.c<?> cVarO;
        if (c0684j2.B0().M0() || (abstractC0727q0A = this.a.a(c0684j2.B0())) == null || (cVarO = ((C0680j) this.a.f()).g(abstractC0727q0A, c0684j2).o()) == null || cVarO.d() == abstractC0727q0A) {
            return;
        }
        this.d.put(c0684j2, cVarO.r().getReference());
    }

    final C2141hE a() {
        return new C2141hE(this.c, this.d, this.a.a(), this.b);
    }
}
