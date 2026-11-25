package com.android.tools.r8.internal;

import java.io.IOException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Hs, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1054Hs extends IOException {
    private B0 a;

    public C1054Hs(String str) {
        super(str);
        this.a = null;
    }

    static C1054Hs b() {
        return new C1054Hs("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either than the input has been truncated or that an embedded message misreported its own length.");
    }

    public final C1054Hs a(B0 b0) {
        this.a = b0;
        return this;
    }

    public final B0 a() {
        return this.a;
    }
}
