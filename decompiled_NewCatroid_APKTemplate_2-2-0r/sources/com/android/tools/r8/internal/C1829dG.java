package com.android.tools.r8.internal;

import java.io.Serializable;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dG, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C1829dG extends AbstractC1679bG implements Serializable {
    private final Object a;
    private final int b;

    C1829dG(int i, Object obj) {
        this.a = obj;
        this.b = i;
        AbstractC2625nb.a(i, "count");
    }

    @Override // com.android.tools.r8.internal.ZF
    public final int a() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.ZF
    public final Object b() {
        return this.a;
    }

    public C1829dG c() {
        return null;
    }
}
