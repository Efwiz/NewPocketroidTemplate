package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class PG extends AbstractC2309jV {
    @Override // com.android.tools.r8.internal.AbstractC2309jV
    public final boolean a(C0729q2 c0729q2) {
        if (c0729q2.p1() && !c0729q2.Y0().i1().f()) {
            return c0729q2.V1().p() && c0729q2.V1().g().u();
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoClassInitializerWithObservableSideEffects";
    }
}
