package org.jf.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import org.jf.util.collection.ArraySet;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public abstract class ImmutableConverter<ImmutableItem, Item> {
    protected abstract boolean isImmutable(Item item);

    protected abstract ImmutableItem makeImmutable(Item item);

    public List<ImmutableItem> toList(Iterable<? extends Item> iterable) {
        if (iterable == null) {
            return ListUtil.of();
        }
        if (iterable instanceof List) {
            Iterator<? extends Item> it = iterable.iterator();
            while (it.hasNext()) {
                if (!isImmutable(it.next())) {
                }
            }
            return (List) iterable;
        }
        final Iterator<? extends Item> it2 = iterable.iterator();
        return ListUtil.copyOf(new Iterator<ImmutableItem>() { // from class: org.jf.util.ImmutableConverter.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return it2.hasNext();
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Iterator
            public ImmutableItem next() {
                return (ImmutableItem) ImmutableConverter.this.makeImmutable(it2.next());
            }

            @Override // java.util.Iterator
            public void remove() {
                it2.remove();
            }
        });
    }

    public ArraySet<ImmutableItem> toSet(Iterable<? extends Item> iterable) {
        if (iterable == null) {
            return ArraySet.of();
        }
        if (iterable instanceof ArraySet) {
            Iterator<? extends Item> it = iterable.iterator();
            while (it.hasNext()) {
                if (!isImmutable(it.next())) {
                }
            }
            return (ArraySet) iterable;
        }
        final Iterator<? extends Item> it2 = iterable.iterator();
        return ArraySet.copyOf(new Iterator<ImmutableItem>() { // from class: org.jf.util.ImmutableConverter.2
            @Override // java.util.Iterator
            public boolean hasNext() {
                return it2.hasNext();
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Iterator
            public ImmutableItem next() {
                return (ImmutableItem) ImmutableConverter.this.makeImmutable(it2.next());
            }

            @Override // java.util.Iterator
            public void remove() {
                it2.remove();
            }
        }).sort();
    }

    public ArraySet<ImmutableItem> toSortedSet(Iterable<? extends Item> iterable) {
        if (iterable == null) {
            return ArraySet.of();
        }
        if (iterable instanceof ArraySet) {
            Iterator<? extends Item> it = iterable.iterator();
            while (it.hasNext()) {
                if (!isImmutable(it.next())) {
                }
            }
            return (ArraySet) iterable;
        }
        final Iterator<? extends Item> it2 = iterable.iterator();
        return ArraySet.copyOf(new Iterator<ImmutableItem>() { // from class: org.jf.util.ImmutableConverter.3
            @Override // java.util.Iterator
            public boolean hasNext() {
                return it2.hasNext();
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Iterator
            public ImmutableItem next() {
                return (ImmutableItem) ImmutableConverter.this.makeImmutable(it2.next());
            }

            @Override // java.util.Iterator
            public void remove() {
                it2.remove();
            }
        }).sort();
    }

    public SortedSet<ImmutableItem> toSortedSet(Comparator<? super ImmutableItem> comparator, SortedSet<? extends Item> sortedSet) {
        int i = 0;
        if (sortedSet == null || sortedSet.size() == 0) {
            return ArraySortedSet.of((Comparator) comparator, new Object[0]);
        }
        Object[] objArr = new Object[sortedSet.size()];
        Iterator<? extends Item> it = sortedSet.iterator();
        while (it.hasNext()) {
            objArr[i] = makeImmutable(it.next());
            i++;
        }
        return ArraySortedSet.of((Comparator) comparator, objArr);
    }
}
