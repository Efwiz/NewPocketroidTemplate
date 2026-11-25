package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1844dT {
    private final com.android.tools.r8.graph.O0 a;
    private final Set b = OU.a();

    C1844dT(com.android.tools.r8.graph.O0 o0) {
        this.a = o0;
    }

    public final void a(C1844dT c1844dT) {
        this.b.add(c1844dT);
        c1844dT.b.add(this);
    }

    public final String toString() {
        return "Node(" + this.a.getReference().r0() + Constant.AFTER_QUTO;
    }
}
