package com.android.tools.r8.internal;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C2686oC extends AbstractList {
    private final List a;

    C2686oC(List list) {
        this.a = (List) AbstractC3123uM.a(list);
    }

    final List a() {
        return this.a;
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        List list = this.a;
        int size = list.size();
        AbstractC3123uM.b(i, size);
        list.add(size - i, obj);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        List list = this.a;
        int size = list.size();
        AbstractC3123uM.a(i, size);
        return list.get((size - 1) - i);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return listIterator();
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator(int i) {
        int size = this.a.size();
        AbstractC3123uM.b(i, size);
        return new C2596nC(this, this.a.listIterator(size - i));
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object remove(int i) {
        List list = this.a;
        int size = list.size();
        AbstractC3123uM.a(i, size);
        return list.remove((size - 1) - i);
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int i, int i2) {
        subList(i, i2).clear();
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        List list = this.a;
        int size = list.size();
        AbstractC3123uM.a(i, size);
        return list.set((size - 1) - i, obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.a.size();
    }

    @Override // java.util.AbstractList, java.util.List
    public final List subList(int i, int i2) {
        AbstractC3123uM.a(i, i2, this.a.size());
        List list = this.a;
        int size = list.size();
        AbstractC3123uM.b(i2, size);
        int i3 = size - i2;
        int size2 = this.a.size();
        AbstractC3123uM.b(i, size2);
        return AbstractC2763pC.a(list.subList(i3, size2 - i));
    }

    static int a(C2686oC c2686oC, int i) {
        int size = c2686oC.a.size();
        AbstractC3123uM.b(i, size);
        return size - i;
    }
}
