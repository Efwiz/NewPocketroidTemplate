package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1715bn extends AbstractC1160Ll {
    final /* synthetic */ AbstractC1791cn c;

    C1715bn(AbstractC1791cn abstractC1791cn) {
        this.c = abstractC1791cn;
    }

    @Override // com.android.tools.r8.internal.AbstractC1160Ll, com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return this.c.e();
    }

    @Override // java.util.List
    public final Object get(int i) {
        return this.c.get(i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1160Ll
    final AbstractC1271Pl k() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.AbstractC1160Ll, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.c.size();
    }
}
