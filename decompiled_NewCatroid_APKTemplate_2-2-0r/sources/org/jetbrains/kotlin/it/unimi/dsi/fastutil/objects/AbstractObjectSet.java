package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.Set;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractObjectSet<K> extends AbstractObjectCollection<K> implements ObjectSet<K>, Cloneable {
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
    public abstract ObjectIterator<K> iterator();

    protected AbstractObjectSet() {
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        if (set.size() != size()) {
            return false;
        }
        return containsAll(set);
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        int size = size();
        ObjectIterator<K> it = iterator();
        int iHashCode = 0;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iHashCode;
            }
            K next = it.next();
            iHashCode += next == null ? 0 : next.hashCode();
            size = i;
        }
    }
}
