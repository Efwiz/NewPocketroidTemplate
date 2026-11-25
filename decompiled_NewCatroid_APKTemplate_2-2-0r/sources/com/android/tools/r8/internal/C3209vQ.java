package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3209vQ extends AbstractC3287wQ {
    private final C0684j2 A;

    public C3209vQ(C0684j2 c0684j2) {
        this.A = c0684j2;
    }

    @Override // com.android.tools.r8.internal.AbstractC3287wQ
    public final Object a() {
        return AbstractC1639am.a(C3209vQ.class, this.A);
    }

    public final String toString() {
        return "UnsupportedLibraryInvoke(" + this.A.r0() + Constant.AFTER_QUTO;
    }
}
