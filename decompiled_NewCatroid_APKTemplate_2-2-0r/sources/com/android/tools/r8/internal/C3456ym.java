package com.android.tools.r8.internal;

import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ym, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3456ym extends AbstractC0930Dm {
    private final List d;
    private final InterfaceC1600aG e;

    C3456ym(List list, InterfaceC1600aG interfaceC1600aG) {
        this.d = list;
        this.e = interfaceC1600aG;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return this.e.contains(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC0930Dm
    final Object get(int i) {
        return ((ZF) this.d.get(i)).b();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.d.size();
    }
}
