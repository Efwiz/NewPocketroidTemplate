package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.UUID;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class WZ extends EZ {
    WZ() {
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        UUID uuid = (UUID) obj;
        c1799cu.d(uuid == null ? null : uuid.toString());
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        if (c1486Wt.t() == 9) {
            c1486Wt.q();
            return null;
        }
        return UUID.fromString(c1486Wt.r());
    }
}
