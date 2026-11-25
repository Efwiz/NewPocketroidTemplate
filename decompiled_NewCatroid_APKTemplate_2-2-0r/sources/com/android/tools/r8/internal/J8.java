package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class J8 extends K8 {
    private final C0767v2 a;

    J8(C0767v2 c0767v2) {
        super();
        this.a = c0767v2;
    }

    @Override // com.android.tools.r8.internal.K8
    public final EnumC1812d20 a() {
        return EnumC1812d20.a(this.a);
    }

    @Override // com.android.tools.r8.internal.K8
    public final C0767v2 b() {
        return this.a;
    }

    public final String toString() {
        return "Precise(" + this.a + Constant.AFTER_QUTO;
    }
}
