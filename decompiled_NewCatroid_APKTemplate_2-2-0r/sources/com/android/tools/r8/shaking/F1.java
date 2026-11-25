package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C1166Ls;
import com.android.tools.r8.internal.C2405kh;
import com.android.tools.r8.internal.InterfaceC2481lh;
import com.android.tools.r8.internal.S10;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class F1 implements InterfaceC2481lh {
    private static final F1 a = new F1();

    private F1() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2481lh
    public final boolean a(C0702m c0702m, C2405kh c2405kh, AbstractC2412ko abstractC2412ko, o5 o5Var) {
        if (!c0702m.f().i()) {
            return false;
        }
        C0702m<C3733i> c0702mI0 = c0702m.i0();
        if (!abstractC2412ko.W1()) {
            return false;
        }
        C1166Ls c1166LsZ = abstractC2412ko.Z();
        if (!c0702mI0.a().b(c1166LsZ.S2())) {
            return false;
        }
        for (int i = 1; i < c1166LsZ.J2().size(); i++) {
            if (c2405kh.b((S10) c1166LsZ.J2().get(i))) {
                return false;
            }
        }
        AbstractC0748t0 abstractC0748t0H = c1166LsZ.h(c0702mI0, o5Var);
        if (abstractC0748t0H == null) {
            return false;
        }
        return abstractC0748t0H.e().i1().a(c1166LsZ).g();
    }
}
