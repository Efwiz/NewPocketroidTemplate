package com.android.tools.r8.internal;

import java.io.IOException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class MZ extends EZ {
    MZ() {
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        Character ch = (Character) obj;
        c1799cu.d(ch == null ? null : String.valueOf(ch));
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        if (c1486Wt.t() == 9) {
            c1486Wt.q();
            return null;
        }
        String strR = c1486Wt.r();
        if (strR.length() == 1) {
            return Character.valueOf(strR.charAt(0));
        }
        throw new C1542Yt("Expecting character, got: ".concat(strR));
    }
}
