package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class NS extends AbstractC1791cn {
    private final PS d;

    NS(PS ps) {
        this.d = ps;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return this.d.containsKey(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1791cn
    final Object get(int i) {
        return this.d.e[i].getKey();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.d.e.length;
    }
}
