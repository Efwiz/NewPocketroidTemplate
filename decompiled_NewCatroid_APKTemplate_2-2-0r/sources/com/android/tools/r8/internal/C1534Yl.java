package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Yl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1534Yl extends AbstractC1639am {
    private final transient AbstractC1639am c;

    C1534Yl(AbstractC1639am abstractC1639am) {
        this.c = abstractC1639am;
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return this.c.contains(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, java.util.List
    /* JADX INFO: renamed from: e */
    public final AbstractC1639am subList(int i, int i2) {
        AbstractC3123uM.a(i, i2, this.c.size());
        AbstractC1639am abstractC1639am = this.c;
        return abstractC1639am.subList(abstractC1639am.size() - i2, this.c.size() - i).j();
    }

    @Override // java.util.List
    public final Object get(int i) {
        AbstractC3123uM.a(i, this.c.size());
        AbstractC1639am abstractC1639am = this.c;
        return abstractC1639am.get((abstractC1639am.size() - 1) - i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, java.util.List
    public final int indexOf(Object obj) {
        int iLastIndexOf = this.c.lastIndexOf(obj);
        if (iLastIndexOf >= 0) {
            return (this.c.size() - 1) - iLastIndexOf;
        }
        return -1;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return listIterator(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am
    public final AbstractC1639am j() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, java.util.List
    public final int lastIndexOf(Object obj) {
        int iIndexOf = this.c.indexOf(obj);
        if (iIndexOf >= 0) {
            return (this.c.size() - 1) - iIndexOf;
        }
        return -1;
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, java.util.List
    public final ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.c.size();
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, java.util.List
    public final /* bridge */ /* synthetic */ ListIterator listIterator(int i) {
        return listIterator(i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return this.c.e();
    }
}
