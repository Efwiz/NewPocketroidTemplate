package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.Set;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractIntSet extends AbstractIntCollection implements IntSet, Cloneable {
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    public abstract IntIterator iterator();

    protected AbstractIntSet() {
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
        if (set instanceof IntSet) {
            return containsAll((IntCollection) set);
        }
        return containsAll(set);
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        int size = size();
        IntIterator it = iterator();
        int iNextInt = 0;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iNextInt;
            }
            iNextInt += it.nextInt();
            size = i;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet
    public boolean remove(int i) {
        return super.rem(i);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    @Deprecated
    public boolean rem(int i) {
        return remove(i);
    }
}
