package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3338xA extends AbstractC3097u {
    private C3263wA g;

    public C3338xA() {
        super(-1);
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final void a(AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.a(d());
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final int c() {
        return 8;
    }

    public final C3263wA d() {
        if (this.g == null) {
            this.g = new C3263wA();
        }
        return this.g;
    }

    public final void e() {
        this.g = null;
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final AbstractC3097u a(Map map) {
        return (AbstractC3097u) map.get(this);
    }
}
