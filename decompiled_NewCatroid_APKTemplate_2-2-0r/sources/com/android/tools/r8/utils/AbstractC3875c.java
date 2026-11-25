package com.android.tools.r8.utils;

import com.android.tools.r8.FeatureSplit;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C1121Ka;
import com.android.tools.r8.shaking.C3733i;

/* JADX INFO: renamed from: com.android.tools.r8.utils.c, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3875c {
    static final /* synthetic */ boolean a = true;

    public static boolean a(C0702m c0702m, C0767v2 c0767v2, C0767v2 c0767v3) {
        C1121Ka c1121KaP;
        C0722p1 c0722p1A = c0702m.a();
        C0767v2 c0767v2B = c0767v2.b(c0722p1A);
        if (!c0767v2B.O0()) {
            return true;
        }
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2B);
        if (abstractC0727q0C == null) {
            return false;
        }
        C0767v2 c0767v2B2 = c0767v3.b(c0722p1A);
        if (!abstractC0727q0C.J1()) {
            if (!a && !c0767v2B2.O0()) {
                throw new AssertionError();
            }
            AbstractC0727q0 abstractC0727q0C2 = c0702m.c(c0767v2B2);
            if (abstractC0727q0C2 == null || abstractC0727q0C2.J1() || !c0767v2B.a(c0767v2B2)) {
                return false;
            }
        }
        if (abstractC0727q0C.e0() && (c1121KaP = ((C3733i) c0702m.f()).p()) != null) {
            FeatureSplit featureSplitA = c1121KaP.a(abstractC0727q0C.a0().getType(), c0702m.Y(), c0702m.C(), c0702m.D());
            if (!featureSplitA.isBase() && featureSplitA != c1121KaP.a(c0767v2B2, c0702m.Y(), c0702m.C(), c0702m.D())) {
                return false;
            }
        }
        return true;
    }
}
