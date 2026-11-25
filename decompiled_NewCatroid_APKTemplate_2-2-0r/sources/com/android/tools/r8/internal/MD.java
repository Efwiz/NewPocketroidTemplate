package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class MD extends MU {
    MD() {
    }

    abstract Map a();

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        a().clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        Object key = entry.getKey();
        Object objA = RD.a(a(), key);
        if (AbstractC3428yK.a(objA, entry.getValue())) {
            return objA != null || a().containsKey(key);
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return a().isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object obj) {
        if (contains(obj)) {
            return a().keySet().remove(((Map.Entry) obj).getKey());
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.MU, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean removeAll(Collection collection) {
        try {
            int i = AbstractC3123uM.a;
            collection.getClass();
            return OU.a(this, collection);
        } catch (UnsupportedOperationException unused) {
            return OU.a(this, collection.iterator());
        }
    }

    @Override // com.android.tools.r8.internal.MU, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean retainAll(Collection collection) {
        try {
            int i = AbstractC3123uM.a;
            collection.getClass();
            return super.retainAll(collection);
        } catch (UnsupportedOperationException unused) {
            HashSet hashSetA = OU.a(collection.size());
            for (Object obj : collection) {
                if (contains(obj)) {
                    hashSetA.add(((Map.Entry) obj).getKey());
                }
            }
            return a().keySet().retainAll(hashSetA);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return a().size();
    }
}
