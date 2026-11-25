package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3134uV extends AbstractC2706oV {
    C3134uV(com.android.tools.r8.graph.Y0 y0) {
        super(y0);
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean equals(Object obj) {
        return obj != null && C3134uV.class == obj.getClass() && this.d == ((C3134uV) obj).d;
    }

    @Override // com.android.tools.r8.internal.B1
    public final int hashCode() {
        return this.d.hashCode();
    }

    @Override // com.android.tools.r8.internal.AbstractC2706oV, com.android.tools.r8.internal.B1
    public final AbstractC3121uK t() {
        return C2096gg.f();
    }

    @Override // com.android.tools.r8.internal.B1
    public final String toString() {
        return "SingleStatelessFieldValue(" + this.d.r0() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean v() {
        return false;
    }
}
