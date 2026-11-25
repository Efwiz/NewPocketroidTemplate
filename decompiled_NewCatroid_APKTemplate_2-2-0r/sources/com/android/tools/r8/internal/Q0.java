package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.SortedSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class Q0 extends AbstractC1658b1 {
    final /* synthetic */ R0 a;

    protected Q0(R0 r0) {
        this.a = r0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.SortedSet
    public final Comparator comparator() {
        return this.a.comparator();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return this.a.containsKey(obj);
    }

    @Override // java.util.SortedSet
    public final Object first() {
        return this.a.firstKey();
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        return this.a.headMap(obj).keySet();
    }

    @Override // java.util.SortedSet
    public final Object last() {
        return this.a.lastKey();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.size();
    }

    @Override // java.util.SortedSet
    public final SortedSet subSet(Object obj, Object obj2) {
        return this.a.subMap(obj, obj2).keySet();
    }

    @Override // java.util.SortedSet
    public final SortedSet tailSet(Object obj) {
        return this.a.tailMap(obj).keySet();
    }
}
