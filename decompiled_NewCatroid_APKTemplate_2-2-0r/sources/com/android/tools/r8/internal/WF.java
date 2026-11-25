package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class WF extends AbstractC1679bG {
    final /* synthetic */ Map.Entry a;

    WF(Map.Entry entry) {
        this.a = entry;
    }

    @Override // com.android.tools.r8.internal.ZF
    public final int a() {
        return ((Collection) this.a.getValue()).size();
    }

    @Override // com.android.tools.r8.internal.ZF
    public final Object b() {
        return this.a.getKey();
    }
}
