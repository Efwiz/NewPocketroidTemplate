package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ni, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1211Ni extends AbstractC1183Mi implements Set, Collection {
    protected AbstractC1211Ni() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public boolean a(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!((C2934rc) this).contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean add(Object obj) {
        return ((C2934rc) this).a.add(obj);
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean addAll(Collection collection) {
        return ((C2934rc) this).a.addAll(collection);
    }

    @Override // java.util.Set, java.util.Collection
    public final void clear() {
        ((C2934rc) this).a.clear();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection collection) {
        return a(collection);
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean equals(Object obj) {
        return obj == this || ((C2934rc) this).a.equals(obj);
    }

    @Override // java.util.Set, java.util.Collection
    public final int hashCode() {
        return ((C2934rc) this).a.hashCode();
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean isEmpty() {
        return ((C2934rc) this).a.isEmpty();
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return ((C2934rc) this).a.iterator();
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean retainAll(Collection collection) {
        return ((C2934rc) this).a.retainAll(collection);
    }

    @Override // java.util.Set, java.util.Collection
    public final int size() {
        return ((C2934rc) this).a.size();
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray() {
        return ((C2934rc) this).a.toArray();
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return ((C2934rc) this).a.toArray(objArr);
    }
}
