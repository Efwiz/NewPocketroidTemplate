package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Kl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1134Kl extends AbstractC3097u {
    public int g;
    public int h;

    public C1134Kl(int i, int i2) {
        super(132);
        this.g = i;
        this.h = i2;
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final void a(AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.a(this.g, this.h);
        b(abstractC1899eF);
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final int c() {
        return 10;
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final AbstractC3097u a(Map map) {
        return new C1134Kl(this.g, this.h).a(this);
    }
}
