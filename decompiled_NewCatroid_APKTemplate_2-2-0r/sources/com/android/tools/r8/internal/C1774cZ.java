package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1774cZ extends AbstractC2331jn {
    private static final C1774cZ a = new C1774cZ();

    private C1774cZ() {
    }

    public static C1774cZ c() {
        return a;
    }

    @Override // com.android.tools.r8.internal.AbstractC2331jn
    public final AbstractC2331jn a(AbstractC1476Wj abstractC1476Wj) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2331jn
    public final com.android.tools.r8.graph.Y0 a(C0767v2 c0767v2) {
        throw new C3405y10("Unexpected InitClass instruction for `" + c0767v2.r0() + "`");
    }
}
