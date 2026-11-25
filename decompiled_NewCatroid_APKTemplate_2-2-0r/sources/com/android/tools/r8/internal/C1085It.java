package com.android.tools.r8.internal;

import java.util.HashSet;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.It, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1085It extends AbstractC1791cn {
    private final Set d;
    private final AbstractC1639am e;

    C1085It(HashSet hashSet, AbstractC1639am abstractC1639am) {
        this.d = hashSet;
        this.e = abstractC1639am;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return this.d.contains(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC1791cn
    final Object get(int i) {
        return this.e.get(i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.e.size();
    }
}
