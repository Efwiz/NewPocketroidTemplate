package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class F20 {
    static final /* synthetic */ boolean a = true;

    public static AbstractC1154Lf a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C1207Nf c1207Nf) {
        return a(c0702m, c1207Nf, o5Var.t(), WH.b());
    }

    public static AbstractC1154Lf a(C0702m c0702m, AbstractC1154Lf abstractC1154Lf, C0767v2 c0767v2) {
        return a(c0702m, abstractC1154Lf, c0767v2, WH.i());
    }

    private static AbstractC1154Lf a(C0702m c0702m, AbstractC1154Lf abstractC1154Lf, C0767v2 c0767v2, WH wh) {
        boolean zH;
        if (!a && !c0767v2.O0()) {
            throw new AssertionError();
        }
        if (abstractC1154Lf.h() || abstractC1154Lf.l() || abstractC1154Lf.k() || abstractC1154Lf.m()) {
            return abstractC1154Lf;
        }
        AbstractC3472z00 abstractC3472z00O = abstractC1154Lf.a().o();
        C1229Oa c1229OaA = c0767v2.c((C0702m<?>) c0702m).b().a(wh);
        WH whF = abstractC1154Lf.f();
        boolean z = true;
        boolean zH2 = false;
        if (!whF.equals(wh) && wh == whF.a(wh)) {
            if (abstractC1154Lf.f().e() && abstractC3472z00O.a(c1229OaA)) {
                if (abstractC1154Lf.g()) {
                    AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2);
                    if (abstractC0727q0C == null) {
                        zH = false;
                    } else if (!abstractC0727q0C.e0()) {
                        zH = abstractC0727q0C.e.h();
                    } else {
                        zH = !((C3733i) c0702m.f()).z().a(abstractC0727q0C.a0());
                    }
                    if (!zH) {
                        z = false;
                    }
                }
                if (z) {
                    return OH.o();
                }
            }
            return abstractC1154Lf;
        }
        if (!abstractC3472z00O.equals(c1229OaA)) {
            return abstractC1154Lf;
        }
        if (!abstractC1154Lf.g()) {
            return AbstractC1154Lf.n();
        }
        AbstractC0727q0 abstractC0727q0C2 = c0702m.c(c0767v2);
        if (abstractC0727q0C2 != null) {
            if (!abstractC0727q0C2.e0()) {
                zH2 = abstractC0727q0C2.e.h();
            } else {
                zH2 = !((C3733i) c0702m.f()).z().a(abstractC0727q0C2.a0());
            }
        }
        return zH2 ? AbstractC1154Lf.n() : abstractC1154Lf;
    }
}
