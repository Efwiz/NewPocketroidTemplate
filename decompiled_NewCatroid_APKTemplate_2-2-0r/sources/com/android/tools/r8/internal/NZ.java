package com.android.tools.r8.internal;

import java.io.IOException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class NZ extends EZ {
    NZ() {
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        c1799cu.d((String) obj);
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        int iT = c1486Wt.t();
        if (iT == 9) {
            c1486Wt.q();
            return null;
        }
        if (iT == 8) {
            return Boolean.toString(c1486Wt.l());
        }
        return c1486Wt.r();
    }
}
