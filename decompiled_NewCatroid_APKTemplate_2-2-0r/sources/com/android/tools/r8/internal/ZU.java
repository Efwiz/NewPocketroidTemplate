package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class ZU extends AbstractC1617aV {
    private final int b;

    ZU(int i) {
        this.b = i;
    }

    abstract ZU a(int i, C2005fV c2005fV);

    final S10 b(AbstractC1305Qs abstractC1305Qs) {
        return abstractC1305Qs.c(this.b);
    }

    @Override // com.android.tools.r8.internal.AbstractC1617aV
    public final boolean d() {
        return true;
    }

    final int h() {
        return this.b;
    }
}
