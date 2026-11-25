package com.android.tools.r8.internal;

import java.io.IOException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Vt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1460Vt extends AbstractC1515Xt {
    C1460Vt() {
    }

    @Override // com.android.tools.r8.internal.AbstractC1515Xt
    public final void a(C1486Wt c1486Wt) throws IOException {
        int iF = c1486Wt.h;
        if (iF == 0) {
            iF = c1486Wt.f();
        }
        if (iF == 13) {
            c1486Wt.h = 9;
        } else if (iF == 12) {
            c1486Wt.h = 8;
        } else {
            if (iF != 14) {
                throw new IllegalStateException("Expected a name but was " + AbstractC1568Zt.a(c1486Wt.t()) + c1486Wt.k());
            }
            c1486Wt.h = 10;
        }
    }
}
