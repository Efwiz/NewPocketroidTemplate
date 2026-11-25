package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lK, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2455lK extends HW {
    private final C0722p1 a;

    C2455lK(C0702m c0702m) {
        this.a = c0702m.a();
    }

    @Override // com.android.tools.r8.internal.HW
    public final void a(AbstractC0748t0 abstractC0748t0, B4 b4, C2409kl c2409kl, InterfaceC2641no interfaceC2641no, AbstractC1305Qs abstractC1305Qs, Set set, Set set2) {
        if (abstractC0748t0.getReference() == this.a.g4.e) {
            if (!(abstractC1305Qs.d1() && abstractC1305Qs.b().y()) && ((S10) ((ArrayList) abstractC1305Qs.h1()).get(0)).P()) {
                interfaceC2641no.s();
            }
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2833qB
    public final C0767v2 getType() {
        return this.a.Z1;
    }
}
