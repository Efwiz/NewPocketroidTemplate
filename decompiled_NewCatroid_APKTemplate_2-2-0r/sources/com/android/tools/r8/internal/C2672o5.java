package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.o5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2672o5 extends HW {
    private final C0702m a;
    private final C0722p1 b;

    C2672o5(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a();
    }

    @Override // com.android.tools.r8.internal.HW
    public final void a(AbstractC0748t0 abstractC0748t0, B4 b4, C2409kl c2409kl, InterfaceC2641no interfaceC2641no, AbstractC1305Qs abstractC1305Qs, Set set, Set set2) {
        if (abstractC0748t0.getReference() == this.b.Y3.d) {
            S10 s10M = ((S10) ((ArrayList) abstractC1305Qs.J2()).get(0)).m();
            if (s10M.i()) {
                return;
            }
            AbstractC2412ko abstractC2412ko = s10M.b;
            abstractC2412ko.getClass();
            if (abstractC2412ko instanceof QW) {
                com.android.tools.r8.graph.Y0 field = abstractC2412ko.F0().getField();
                C0722p1.a aVar = this.b.Y3;
                if (field == aVar.b) {
                    interfaceC2641no.a(c2409kl, 1);
                    return;
                } else {
                    if (field == aVar.a) {
                        interfaceC2641no.a(c2409kl, 0);
                        return;
                    }
                    return;
                }
            }
            return;
        }
        if (abstractC0748t0.getReference() != this.b.Y3.e) {
            if (abstractC0748t0.getReference() == this.b.Y3.f) {
                B1 b1A = ((S10) ((ArrayList) abstractC1305Qs.J2()).get(0)).a(this.a, c2409kl.h());
                b1A.getClass();
                if (b1A instanceof C2853qV) {
                    interfaceC2641no.a(this.a, c2409kl, b1A.e().F() ? this.b.Y3.b : this.b.Y3.a, (Set<S10>) set);
                    return;
                }
                return;
            }
            return;
        }
        S10 s10M2 = ((S10) ((ArrayList) abstractC1305Qs.J2()).get(0)).m();
        if (s10M2.i()) {
            return;
        }
        AbstractC2412ko abstractC2412ko2 = s10M2.b;
        abstractC2412ko2.getClass();
        if (abstractC2412ko2 instanceof C1033Hc) {
            C1033Hc c1033HcF = abstractC2412ko2.F();
            if (c1033HcF.i1()) {
                return;
            }
            String lowerCase = c1033HcF.J2().toString().toLowerCase();
            if (lowerCase.equals("true")) {
                interfaceC2641no.a(c2409kl, 1);
            } else if (lowerCase.equals("false")) {
                interfaceC2641no.a(c2409kl, 0);
            }
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2833qB
    public final C0767v2 getType() {
        return this.b.M1;
    }
}
