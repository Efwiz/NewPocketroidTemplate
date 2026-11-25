package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2777pQ extends AbstractC3287wQ {
    private final com.android.tools.r8.graph.Y0 A;
    private final int B;

    public C2777pQ(com.android.tools.r8.graph.Y0 y0) {
        this.A = y0;
        this.B = -1;
    }

    @Override // com.android.tools.r8.internal.AbstractC3287wQ
    public final Object a() {
        return C2777pQ.class;
    }

    public final String toString() {
        String strR0;
        com.android.tools.r8.graph.Y0 y0 = this.A;
        if (y0 != null) {
            strR0 = y0.r0();
        } else {
            strR0 = "ordinal=" + this.B;
        }
        return "MissingDynamicType(" + strR0 + Constant.AFTER_QUTO;
    }

    public C2777pQ(int i) {
        this.B = i;
        this.A = null;
    }
}
