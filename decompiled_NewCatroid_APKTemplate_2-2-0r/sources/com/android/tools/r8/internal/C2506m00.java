package com.android.tools.r8.internal;

import java.io.IOException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.m00, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2506m00 extends EZ {
    C2506m00() {
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        c1799cu.a((Number) obj);
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        if (c1486Wt.t() == 9) {
            c1486Wt.q();
            return null;
        }
        try {
            return Byte.valueOf((byte) c1486Wt.n());
        } catch (NumberFormatException e) {
            throw new C1542Yt(e);
        }
    }
}
