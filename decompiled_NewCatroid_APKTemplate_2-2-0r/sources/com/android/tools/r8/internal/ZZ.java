package com.android.tools.r8.internal;

import java.sql.Timestamp;
import java.util.Date;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class ZZ implements FZ {
    ZZ() {
    }

    @Override // com.android.tools.r8.internal.FZ
    public final EZ a(C1862dk c1862dk, I00 i00) {
        if (i00.a() != Timestamp.class) {
            return null;
        }
        c1862dk.getClass();
        return new YZ(c1862dk.a(I00.a(Date.class)));
    }
}
