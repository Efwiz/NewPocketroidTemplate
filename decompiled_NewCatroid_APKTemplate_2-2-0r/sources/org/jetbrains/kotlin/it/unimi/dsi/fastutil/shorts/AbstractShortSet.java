package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.Set;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractShortSet extends AbstractShortCollection implements ShortSet, Cloneable {
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
    public abstract ShortIterator iterator();

    protected AbstractShortSet() {
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
        if (set instanceof ShortSet) {
            return containsAll((ShortCollection) set);
        }
        return containsAll(set);
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        int size = size();
        ShortIterator it = iterator();
        int iNextShort = 0;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iNextShort;
            }
            iNextShort += it.nextShort();
            size = i;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSet
    public boolean remove(short s) {
        return super.rem(s);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    @Deprecated
    public boolean rem(short s) {
        return remove(s);
    }
}
