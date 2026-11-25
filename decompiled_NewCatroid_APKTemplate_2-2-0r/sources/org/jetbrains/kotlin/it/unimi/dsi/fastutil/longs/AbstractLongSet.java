package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.Set;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.HashCommon;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractLongSet extends AbstractLongCollection implements LongSet, Cloneable {
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
    public abstract LongIterator iterator();

    protected AbstractLongSet() {
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
        if (set instanceof LongSet) {
            return containsAll((LongCollection) set);
        }
        return containsAll(set);
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        int size = size();
        LongIterator it = iterator();
        int iLong2int = 0;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iLong2int;
            }
            iLong2int += HashCommon.long2int(it.nextLong());
            size = i;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSet
    public boolean remove(long j) {
        return super.rem(j);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    @Deprecated
    public boolean rem(long j) {
        return remove(j);
    }
}
