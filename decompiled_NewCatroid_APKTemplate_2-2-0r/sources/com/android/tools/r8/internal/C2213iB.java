package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2213iB {
    static final /* synthetic */ boolean f = true;
    private final C2409kl a;
    private final AbstractC2412ko b;
    private final InterfaceC2641no c;
    private final Set d;
    final /* synthetic */ C2289jB e;

    C2213iB(C2289jB c2289jB, C2409kl c2409kl, AbstractC2412ko abstractC2412ko, InterfaceC2641no interfaceC2641no, Set set) {
        this.e = c2289jB;
        this.a = c2409kl;
        this.b = abstractC2412ko;
        this.c = interfaceC2641no;
        this.d = set;
    }

    final void a(C0767v2 c0767v2, BiFunction biFunction, DH dh, AbstractC1476Wj abstractC1476Wj) {
        S10 s10A;
        C0767v2 c0767v2A = dh.a(abstractC1476Wj, c0767v2);
        if (c0767v2A != c0767v2) {
            C2289jB c2289jB = this.e;
            AbstractC2412ko abstractC2412ko = this.b;
            C2409kl c2409kl = this.a;
            c2289jB.getClass();
            if (abstractC2412ko.d1()) {
                AbstractC3472z00 abstractC3472z00A = abstractC2412ko.a();
                C0702m c0702m = c2289jB.a;
                abstractC3472z00A.getClass();
                s10A = c2409kl.a(abstractC2412ko.j(), abstractC3472z00A.a(c0702m, dh, abstractC1476Wj, Collections.emptySet()));
            } else {
                s10A = null;
            }
            this.c.d((AbstractC2412ko) biFunction.apply(c0767v2A, s10A));
            if (s10A != null) {
                if (s10A.w() != this.b.a()) {
                    this.d.addAll(s10A.g0());
                    return;
                }
                boolean z = f;
                if (!z && !this.b.c1()) {
                    throw new AssertionError();
                }
                if (z) {
                    return;
                }
                AbstractC2412ko abstractC2412ko2 = this.b;
                abstractC2412ko2.getClass();
                if (abstractC2412ko2 instanceof C3446yc) {
                    return;
                }
                AbstractC2412ko abstractC2412ko3 = this.b;
                abstractC2412ko3.getClass();
                if (abstractC2412ko3 instanceof C2179hn) {
                    return;
                }
                AbstractC2412ko abstractC2412ko4 = this.b;
                abstractC2412ko4.getClass();
                if (abstractC2412ko4 instanceof C1641ao) {
                    return;
                }
                if (!this.b.f2() || !this.b.i0().S2().e.M0()) {
                    throw new AssertionError();
                }
            }
        }
    }
}
