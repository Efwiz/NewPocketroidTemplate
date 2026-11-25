package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0772w0;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2457lM {
    final C0772w0 a;
    final int b;
    final int c;

    public C2457lM(C0772w0 c0772w0, int i, int i2) {
        this.a = c0772w0;
        this.b = i;
        this.c = i2;
    }

    public final boolean equals(Object obj) {
        C2457lM c2457lM = (C2457lM) obj;
        return this.b == c2457lM.b && this.c == c2457lM.c;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.b), Integer.valueOf(this.c));
    }
}
