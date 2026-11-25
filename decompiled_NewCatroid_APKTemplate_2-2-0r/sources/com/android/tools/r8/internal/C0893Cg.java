package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import java.util.ArrayList;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Cg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0893Cg extends HW {
    private final C0702m a;

    C0893Cg(C0702m c0702m) {
        this.a = c0702m;
    }

    @Override // com.android.tools.r8.internal.HW
    public final void a(AbstractC0748t0 abstractC0748t0, B4 b4, C2409kl c2409kl, InterfaceC2641no interfaceC2641no, AbstractC1305Qs abstractC1305Qs, Set set, Set set2) {
        C0767v2 c0767v2K2;
        C0729q2 c0729q2B;
        S10 s10B;
        if (this.a.f().i() && abstractC0748t0.getReference() == this.a.a().r4.c && ((S10) ((ArrayList) abstractC1305Qs.h1()).get(0)).I()) {
            C0702m<C3733i> c0702mI0 = this.a.i0();
            if (abstractC1305Qs.h().D() || (c0729q2B = c0702mI0.b((c0767v2K2 = ((S10) ((ArrayList) abstractC1305Qs.h1()).get(0)).n().A().K2()))) == null || !c0729q2B.B1() || c0729q2B.f != c0702mI0.a().e2 || (s10B = abstractC1305Qs.b()) == null) {
                return;
            }
            S10 s10A = c2409kl.a(s10B.s(), s10B.w());
            s10B.g(s10A);
            WH whB = WH.b();
            c0767v2K2.getClass();
            N3 n3 = new N3(new L3(AbstractC1154Lf.a(c0702mI0, AbstractC3472z00.a(c0767v2K2, whB, c0702mI0))), null, s10A, s10B, abstractC1305Qs, c0702mI0);
            n3.b(c0702mI0.Y().W0 ? abstractC1305Qs.Y0() : AbstractC1915eM.u());
            interfaceC2641no.add(n3);
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2833qB
    public final C0767v2 getType() {
        return this.a.a().e2;
    }
}
