package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class ZA extends AbstractC3097u {
    public Object g;

    public ZA(Object obj) {
        super(18);
        this.g = obj;
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final void a(AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.a(this.g);
        b(abstractC1899eF);
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final int c() {
        return 9;
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final AbstractC3097u a(Map map) {
        return new ZA(this.g).a(this);
    }
}
