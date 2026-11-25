package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Et, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0969Et extends AbstractC1639am {
    final /* synthetic */ C0995Ft c;

    private C0969Et(C0995Ft c0995Ft) {
        this.c = c0995Ft;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return false;
    }

    @Override // java.util.List
    public final Object get(int i) {
        Map.Entry entry = (Map.Entry) this.c.e.get(i);
        return RD.a(entry.getValue(), entry.getKey());
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.c.e.size();
    }

    /* synthetic */ C0969Et(C0995Ft c0995Ft, int i) {
        this(c0995Ft);
    }
}
