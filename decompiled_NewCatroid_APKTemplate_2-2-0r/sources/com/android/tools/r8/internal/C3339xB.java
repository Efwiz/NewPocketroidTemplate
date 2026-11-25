package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3339xB extends AbstractC3097u {
    public int g;
    public C3338xA h;

    public C3339xB(int i, C3338xA c3338xA) {
        super(-1);
        this.g = i;
        this.h = c3338xA;
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final void a(AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.b(this.g, this.h.d());
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final int c() {
        return 15;
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final AbstractC3097u a(Map map) {
        return new C3339xB(this.g, (C3338xA) map.get(this.h));
    }
}
