package com.android.tools.r8.internal;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class UZ extends EZ {
    UZ() {
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        URI uri = (URI) obj;
        c1799cu.d(uri == null ? null : uri.toASCIIString());
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        if (c1486Wt.t() == 9) {
            c1486Wt.q();
            return null;
        }
        try {
            String strR = c1486Wt.r();
            if ("null".equals(strR)) {
                return null;
            }
            return new URI(strR);
        } catch (URISyntaxException e) {
            throw new C1279Pt(e);
        }
    }
}
