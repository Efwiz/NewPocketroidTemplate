package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class ZT extends Cdo {
    private S10 c = null;

    @Override // com.android.tools.r8.internal.Cdo
    public final Cdo a() {
        return this;
    }

    public final ZT b(S10 s10) {
        this.c = s10;
        return this;
    }

    public final C1616aU b() {
        return (C1616aU) a(this.c == null ? new C1616aU() : new C1616aU(this.c));
    }
}
