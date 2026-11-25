package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.util.Set;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.HashCommon;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractDoubleSet extends AbstractDoubleCollection implements DoubleSet, Cloneable {
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
    public abstract DoubleIterator iterator();

    protected AbstractDoubleSet() {
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
        if (set instanceof DoubleSet) {
            return containsAll((DoubleCollection) set);
        }
        return containsAll(set);
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        int size = size();
        DoubleIterator it = iterator();
        int iDouble2int = 0;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iDouble2int;
            }
            iDouble2int += HashCommon.double2int(it.nextDouble());
            size = i;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet
    public boolean remove(double d) {
        return super.rem(d);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    @Deprecated
    public boolean rem(double d) {
        return remove(d);
    }
}
