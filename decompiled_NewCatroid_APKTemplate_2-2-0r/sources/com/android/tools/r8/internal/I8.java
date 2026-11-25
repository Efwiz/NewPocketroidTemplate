package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class I8 extends K8 {
    private final EnumC1812d20 a;

    I8(EnumC1812d20 enumC1812d20) {
        super();
        this.a = enumC1812d20;
    }

    @Override // com.android.tools.r8.internal.K8
    public final EnumC1812d20 a() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.K8
    public final C0767v2 b() {
        return null;
    }

    public final String toString() {
        return "Imprecise(" + this.a + Constant.AFTER_QUTO;
    }
}
