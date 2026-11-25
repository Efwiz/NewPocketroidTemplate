package com.android.tools.r8.internal;

import com.android.tools.r8.naming.C3605b;
import com.android.tools.r8.naming.C3634k;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class ED extends DD {
    private final C3605b a;

    ED(C3605b c3605b) {
        this.a = c3605b;
    }

    @Override // com.android.tools.r8.internal.DD
    final C3634k a(String str) {
        return this.a.b(str);
    }

    @Override // com.android.tools.r8.internal.DD
    final String b(String str) {
        return this.a.c(str);
    }

    public static DD a(C3605b c3605b) {
        return new ED(c3605b);
    }
}
