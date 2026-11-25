package com.android.tools.r8.internal;

import java.sql.Time;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1850dZ implements FZ {
    C1850dZ() {
    }

    @Override // com.android.tools.r8.internal.FZ
    public final EZ a(C1862dk c1862dk, I00 i00) {
        if (i00.a() == Time.class) {
            return new C1932eZ();
        }
        return null;
    }
}
