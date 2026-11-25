package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3058tQ extends AbstractC3287wQ {
    private final C0684j2 A;
    private final AbstractC3472z00 B;
    private final AbstractC3472z00 C;

    public C3058tQ(C0684j2 c0684j2, AbstractC3472z00 abstractC3472z00, AbstractC3472z00 abstractC3472z01) {
        this.A = c0684j2;
        this.B = abstractC3472z00;
        this.C = abstractC3472z01;
    }

    @Override // com.android.tools.r8.internal.AbstractC3287wQ
    public final Object a() {
        return AbstractC1639am.a(C3058tQ.class, this.A);
    }

    public final String toString() {
        String strR0 = this.A.r0();
        AbstractC3472z00 abstractC3472z00 = this.B;
        String strR1 = abstractC3472z00.w() ? abstractC3472z00.b().R().r0() : abstractC3472z00.toString();
        AbstractC3472z00 abstractC3472z01 = this.C;
        return "NonComparableElements(" + strR0 + " - " + strR1 + " vs " + (abstractC3472z01.w() ? abstractC3472z01.b().R().r0() : abstractC3472z01.toString()) + Constant.AFTER_QUTO;
    }
}
