package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.um, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3156um extends AbstractC0836Am {
    final /* synthetic */ AbstractC3232vm e;

    C3156um(AbstractC3232vm abstractC3232vm) {
        this.e = abstractC3232vm;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public final int b(Object obj) {
        Collection collection = (Collection) this.e.e.get(obj);
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    @Override // com.android.tools.r8.internal.AbstractC0836Am, com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return this.e.e.containsKey(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC0836Am
    /* JADX INFO: renamed from: i */
    public final AbstractC1048Hm I() {
        return this.e.e.keySet();
    }

    @Override // com.android.tools.r8.internal.AbstractC0836Am
    final ZF j(int i) {
        Map.Entry entry = (Map.Entry) this.e.e.entrySet().a().get(i);
        return AbstractC1987fG.a(((Collection) entry.getValue()).size(), entry.getKey());
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        return this.e.f;
    }
}
