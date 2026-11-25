package com.android.tools.r8.internal;

import java.io.IOException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Zj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1558Zj extends EZ {
    C1558Zj() {
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        Number number = (Number) obj;
        if (number == null) {
            c1799cu.l();
            return;
        }
        double dFloatValue = number.floatValue();
        if (Double.isNaN(dFloatValue) || Double.isInfinite(dFloatValue)) {
            throw new IllegalArgumentException(dFloatValue + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
        c1799cu.a(number);
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        if (c1486Wt.t() == 9) {
            c1486Wt.q();
            return null;
        }
        return Float.valueOf((float) c1486Wt.m());
    }
}
