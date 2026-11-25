package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.r9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2901r9 extends Cdo {
    protected C0767v2 c;
    protected S10 d;

    @Override // com.android.tools.r8.internal.Cdo
    public final Cdo a() {
        return this;
    }

    public final C2901r9 b(S10 s10) {
        this.d = s10;
        return this;
    }

    public final C2901r9 a(C0767v2 c0767v2) {
        this.c = c0767v2;
        return this;
    }

    public C2972s9 b() {
        return (C2972s9) a(new C2972s9(this.a, this.d, this.c, false));
    }
}
