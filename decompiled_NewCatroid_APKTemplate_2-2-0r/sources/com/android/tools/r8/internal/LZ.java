package com.android.tools.r8.internal;

import com.android.tools.r8.AbstractC0403b;
import java.io.IOException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class LZ extends EZ {
    LZ() {
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        c1799cu.a((Number) obj);
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        int iT = c1486Wt.t();
        int iB = AbstractC0403b.b(iT);
        if (iB == 6) {
            return new TA(c1486Wt.r());
        }
        if (iB == 8) {
            c1486Wt.q();
            return null;
        }
        throw new C1542Yt("Expecting number, got: ".concat(AbstractC1568Zt.a(iT)));
    }
}
