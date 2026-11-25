package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class T5 extends HW {
    private final C0722p1 a;

    T5(C0702m c0702m) {
        this.a = c0702m.a();
    }

    @Override // com.android.tools.r8.internal.HW
    public final void a(AbstractC0748t0 abstractC0748t0, B4 b4, C2409kl c2409kl, InterfaceC2641no interfaceC2641no, AbstractC1305Qs abstractC1305Qs, Set set, Set set2) {
        if (abstractC0748t0.getReference() == this.a.Z3.b) {
            S10 s10M = abstractC1305Qs.c(0).m();
            if (s10M.i()) {
                return;
            }
            AbstractC2412ko abstractC2412koQ = s10M.q();
            abstractC2412koQ.getClass();
            if ((abstractC2412koQ instanceof C1721bt) && abstractC2412koQ.g0().S2() == this.a.Z3.d) {
                abstractC1305Qs.b().g(abstractC2412koQ.g0().c(0));
                interfaceC2641no.s();
            }
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2833qB
    public final C0767v2 getType() {
        return this.a.N1;
    }
}
