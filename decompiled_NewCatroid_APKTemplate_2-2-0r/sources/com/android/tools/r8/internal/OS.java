package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class OS extends AbstractC1639am {
    final PS c;

    OS(PS ps) {
        this.c = ps;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return true;
    }

    @Override // java.util.List
    public final Object get(int i) {
        return this.c.e[i].getValue();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.c.e.length;
    }
}
