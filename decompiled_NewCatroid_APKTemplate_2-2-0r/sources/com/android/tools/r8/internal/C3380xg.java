package com.android.tools.r8.internal;

import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3380xg extends AbstractC3303wg {
    private final C1452Vl a;

    public C3380xg(C1452Vl c1452Vl) {
        this.a = c1452Vl;
    }

    public final void a(BiConsumer biConsumer) {
        this.a.forEach(biConsumer);
    }

    @Override // com.android.tools.r8.internal.AbstractC3303wg
    public final C3380xg c() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3303wg
    public final boolean d() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC3303wg
    public final boolean e() {
        return false;
    }
}
