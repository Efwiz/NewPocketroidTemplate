package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3083tm extends AbstractC1271Pl {
    final AbstractC3232vm b;

    C3083tm(AbstractC3232vm abstractC3232vm) {
        this.b = abstractC3232vm;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return this.b.a(entry.getKey(), entry.getValue());
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return this.b.e.o();
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    /* JADX INFO: renamed from: f */
    public final AbstractC2963s10 iterator() {
        AbstractC3232vm abstractC3232vm = this.b;
        abstractC3232vm.getClass();
        return new C2944rm(abstractC3232vm);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        return this.b.f;
    }
}
