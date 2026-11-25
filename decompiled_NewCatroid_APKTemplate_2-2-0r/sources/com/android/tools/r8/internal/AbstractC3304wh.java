package com.android.tools.r8.internal;

import com.android.tools.r8.FeatureSplit;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.utils.AbstractC3938n2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3304wh {
    static final /* synthetic */ boolean a = true;

    public static boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        C1121Ka c1121KaP = ((C0680j) c0702m.f()).p();
        c1121KaP.getClass();
        FeatureSplit featureSplitA = c1121KaP.a(o5Var.t(), c0702m.Y(), c0702m.C(), c0702m.D());
        FeatureSplit featureSplitA2 = c1121KaP.a(o5Var2.t(), c0702m.Y(), c0702m.C(), c0702m.D());
        if (!featureSplitA2.isBase() && featureSplitA2 != featureSplitA) {
            return false;
        }
        if (o5Var2.B().g()) {
            return true;
        }
        AbstractC3135uW abstractC3135uWC = c0702m.C();
        AbstractC3938n2 abstractC3938n2A = o5Var.e().N0() ? AbstractC3938n2.c : AbstractC3938n2.a(abstractC3135uWC.a(o5Var.getReference()));
        if (abstractC3938n2A.d()) {
            return !(o5Var2.e().N0() ? AbstractC3938n2.c : AbstractC3938n2.a(abstractC3135uWC.a(o5Var2.getReference()))).a();
        }
        return (abstractC3938n2A.a() && abstractC3135uWC.b(o5Var.t()) && !abstractC3135uWC.b(o5Var2.t())) ? false : true;
    }

    public static boolean a(C0729q2 c0729q2, C0729q2 c0729q3, C0702m c0702m) {
        C1121Ka c1121KaP = ((C0680j) c0702m.f()).p();
        c1121KaP.getClass();
        FeatureSplit featureSplitA = c1121KaP.a(c0729q2.C(), c0702m.Y(), c0702m.C(), c0702m.D());
        FeatureSplit featureSplitA2 = c1121KaP.a(c0729q3.C(), c0702m.Y(), c0702m.C(), c0702m.D());
        if (featureSplitA2.isBase()) {
            if (!a && !featureSplitA.isBase()) {
                throw new AssertionError("Unexpected class in base that inherits from feature");
            }
        } else if (featureSplitA != featureSplitA2) {
            return false;
        }
        AbstractC3135uW abstractC3135uWC = c0702m.C();
        return !abstractC3135uWC.b(c0729q2.getType()) || abstractC3135uWC.b(c0729q3.getType());
    }
}
