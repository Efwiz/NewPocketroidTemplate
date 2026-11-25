package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ll, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1160Ll extends AbstractC1639am {
    AbstractC1160Ll() {
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return k().contains(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    boolean e() {
        return k().e();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        return k().isEmpty();
    }

    abstract AbstractC1271Pl k();

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return k().size();
    }
}
