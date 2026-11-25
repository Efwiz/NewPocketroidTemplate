package com.android.tools.r8.utils;

import com.android.tools.r8.MarkerInfo;

/* JADX INFO: renamed from: com.android.tools.r8.utils.b2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3873b2 implements MarkerInfo {
    private final com.android.tools.r8.dex.U a;

    public C3873b2(com.android.tools.r8.dex.U u) {
        this.a = u;
    }

    final com.android.tools.r8.dex.U a() {
        return this.a;
    }

    @Override // com.android.tools.r8.MarkerInfo
    public final int getMinApi() {
        if (this.a.k()) {
            return this.a.e().intValue();
        }
        return -1;
    }

    @Override // com.android.tools.r8.MarkerInfo
    public final String getRawEncoding() {
        return this.a.toString();
    }

    @Override // com.android.tools.r8.MarkerInfo
    public final String getTool() {
        return this.a.h().toString();
    }

    @Override // com.android.tools.r8.MarkerInfo
    public final boolean isD8() {
        return this.a.m();
    }

    @Override // com.android.tools.r8.MarkerInfo
    public final boolean isL8() {
        return this.a.o();
    }

    @Override // com.android.tools.r8.MarkerInfo
    public final boolean isR8() {
        return this.a.p();
    }
}
