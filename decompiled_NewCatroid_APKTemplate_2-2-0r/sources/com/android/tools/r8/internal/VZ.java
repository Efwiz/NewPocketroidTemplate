package com.android.tools.r8.internal;

import java.io.IOException;
import java.net.InetAddress;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class VZ extends EZ {
    VZ() {
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        InetAddress inetAddress = (InetAddress) obj;
        c1799cu.d(inetAddress == null ? null : inetAddress.getHostAddress());
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        if (c1486Wt.t() == 9) {
            c1486Wt.q();
            return null;
        }
        return InetAddress.getByName(c1486Wt.r());
    }
}
