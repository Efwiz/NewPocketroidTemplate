package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.x00, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3326x00 {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final C2433l20 b;

    public C3326x00(C0702m c0702m, C2433l20 c2433l20) {
        this.a = c0702m;
        this.b = c2433l20;
    }

    public final boolean a(C2409kl c2409kl) {
        AbstractC0727q0 abstractC0727q0C;
        for (AbstractC2412ko abstractC2412ko : c2409kl.r()) {
            if (abstractC2412ko.S1()) {
                if (!a(abstractC2412ko.V())) {
                    return false;
                }
            } else if (abstractC2412ko.u2()) {
                C1616aU c1616aUB0 = abstractC2412ko.B0();
                com.android.tools.r8.graph.W0 w0U = c2409kl.u();
                if (c1616aUB0.K2()) {
                    continue;
                } else {
                    AbstractC3472z00 abstractC3472z00W = c1616aUB0.L2().w();
                    AbstractC3472z00 abstractC3472z00A = AbstractC3472z00.a(w0U.getReference().h.e, WH.i(), (C0702m<?>) this.a);
                    if (!this.b.a(abstractC3472z00W, abstractC3472z00A) && (!abstractC3472z00A.w() || !abstractC3472z00W.I() || (abstractC0727q0C = this.a.c(w0U.getReference().h.e)) == null || !abstractC0727q0C.isInterface())) {
                        return false;
                    }
                }
            } else if (abstractC2412ko.c()) {
                if (!a(abstractC2412ko.G0())) {
                    return false;
                }
            } else if (abstractC2412ko.B2()) {
                AbstractC3472z00 abstractC3472z00W2 = abstractC2412ko.L0().K2().w();
                if (!this.b.a(abstractC3472z00W2, AbstractC3472z00.a(this.a.a().m3, abstractC3472z00W2.N(), (C0702m<?>) this.a))) {
                    return false;
                }
            } else {
                continue;
            }
        }
        return true;
    }

    public final boolean a(AbstractC1182Mh abstractC1182Mh) {
        if (!c && !abstractC1182Mh.L1()) {
            throw new AssertionError();
        }
        AbstractC3472z00 abstractC3472z00W = abstractC1182Mh.value().w();
        AbstractC3472z00 abstractC3472z00A = AbstractC3472z00.a(abstractC1182Mh.getField().h, abstractC3472z00W.N(), (C0702m<?>) this.a);
        if (this.b.a(abstractC3472z00W, abstractC3472z00A)) {
            return true;
        }
        if (!abstractC3472z00A.w() || !abstractC3472z00W.I()) {
            return false;
        }
        AbstractC0727q0 abstractC0727q0C = this.a.c(abstractC1182Mh.getField().h);
        return abstractC0727q0C != null && abstractC0727q0C.isInterface();
    }
}
