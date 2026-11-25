package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class IS extends AbstractC1160Ll {
    final /* synthetic */ JS c;

    IS(JS js) {
        this.c = js;
    }

    @Override // java.util.List
    public final Object get(int i) {
        Map.Entry entry = this.c.d.e.g[i];
        return new C1325Rl(entry.getValue(), entry.getKey());
    }

    @Override // com.android.tools.r8.internal.AbstractC1160Ll
    final AbstractC1271Pl k() {
        return this.c;
    }
}
