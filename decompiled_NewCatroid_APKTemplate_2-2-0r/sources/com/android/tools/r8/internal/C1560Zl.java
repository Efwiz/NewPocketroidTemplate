package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Zl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1560Zl extends AbstractC1639am {
    final transient int c;
    final transient int d;
    final /* synthetic */ AbstractC1639am e;

    C1560Zl(AbstractC1639am abstractC1639am, int i, int i2) {
        this.e = abstractC1639am;
        this.c = i;
        this.d = i2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return true;
    }

    @Override // java.util.List
    public final Object get(int i) {
        AbstractC3123uM.a(i, this.d);
        return this.e.get(i + this.c);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return listIterator(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, java.util.List
    public final ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, java.util.List
    /* JADX INFO: renamed from: e */
    public final AbstractC1639am subList(int i, int i2) {
        AbstractC3123uM.a(i, i2, this.d);
        AbstractC1639am abstractC1639am = this.e;
        int i3 = this.c;
        return abstractC1639am.subList(i + i3, i2 + i3);
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, java.util.List
    public final /* bridge */ /* synthetic */ ListIterator listIterator(int i) {
        return listIterator(i);
    }
}
