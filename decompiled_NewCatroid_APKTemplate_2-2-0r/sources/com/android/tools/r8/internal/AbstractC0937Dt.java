package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Dt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0937Dt {
    static final /* synthetic */ boolean a = true;

    public static boolean a(C0702m c0702m, List list) {
        C3733i c3733iO;
        AbstractC0727q0 abstractC0727q0C;
        com.android.tools.r8.graph.I4.c<?> cVarO;
        C0722p1 c0722p1A = c0702m.a();
        AbstractC3472z00 abstractC3472z00W = ((S10) list.get(0)).w();
        if (abstractC3472z00W.r() || abstractC3472z00W.E()) {
            return false;
        }
        if (!a && !abstractC3472z00W.w()) {
            throw new AssertionError();
        }
        C0767v2 c0767v2R = abstractC3472z00W.b().R();
        C0684j2 c0684j2B = c0722p1A.g4.i.b(c0767v2R, c0722p1A);
        if (c0702m.x().a(c0684j2B, list) || c0702m.t().a(c0684j2B).f()) {
            return false;
        }
        return !c0702m.f().i() || (abstractC0727q0C = (c3733iO = c0702m.f().o()).c(c0767v2R)) == null || !abstractC0727q0C.c(c0702m) || (cVarO = c3733iO.g(abstractC0727q0C, c0684j2B).o()) == null || cVarO.r().i1().A();
    }
}
