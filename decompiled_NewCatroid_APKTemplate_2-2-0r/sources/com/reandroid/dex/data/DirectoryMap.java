package com.reandroid.dex.data;

import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.base.CountedList;
import com.reandroid.dex.data.DataItem;
import com.reandroid.dex.data.DefIndex;
import com.reandroid.dex.key.Key;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.FilterIterator;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class DirectoryMap<DEFINITION extends DefIndex, VALUE extends DataItem> extends CountedList<DirectoryEntry<DEFINITION, VALUE>> implements Iterable<DirectoryEntry<DEFINITION, VALUE>> {
    public DirectoryMap(IntegerReference integerReference, Creator<DirectoryEntry<DEFINITION, VALUE>> creator) {
        super(integerReference, creator);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean sort() {
        return super.sort(CompareUtil.getComparableComparator());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void add(DEFINITION definition, VALUE value) {
        if (contains(definition, value)) {
            return;
        }
        ((DirectoryEntry) createNext()).set(definition, value);
    }

    public boolean contains(DEFINITION definition) {
        return getEntries(definition).hasNext();
    }

    public boolean contains(DEFINITION definition, VALUE value) {
        Iterator<DirectoryEntry<DEFINITION, VALUE>> entries = getEntries(definition);
        while (entries.hasNext()) {
            if (entries.next().equalsValue(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(DEFINITION definition, Key key) {
        Iterator<DirectoryEntry<DEFINITION, VALUE>> entries = getEntries(definition);
        while (entries.hasNext()) {
            if (entries.next().matchesValue(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Key key, Key key2) {
        Iterator<DirectoryEntry<DEFINITION, VALUE>> entries = getEntries(key);
        while (entries.hasNext()) {
            if (entries.next().matchesValue(key2)) {
                return true;
            }
        }
        return false;
    }

    public void remove(final DEFINITION definition) {
        super.removeIf(new Predicate() { // from class: com.reandroid.dex.data.DirectoryMap$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((DirectoryEntry) obj).equalsDefIndex(definition);
            }
        });
    }

    static /* synthetic */ boolean lambda$remove$1(DefIndex defIndex, Predicate predicate, DirectoryEntry directoryEntry) {
        return directoryEntry.equalsDefIndex(defIndex) && predicate.test(directoryEntry.getValue());
    }

    public void remove(final DEFINITION definition, final Predicate<VALUE> predicate) {
        super.removeIf(new Predicate() { // from class: com.reandroid.dex.data.DirectoryMap$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DirectoryMap.lambda$remove$1(definition, predicate, (DirectoryEntry) obj);
            }
        });
    }

    public void link(DEFINITION definition) {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            ((DirectoryEntry) it.next()).link(definition);
        }
    }

    public Iterator<VALUE> getValues(int i) {
        return ComputeIterator.of(getEntries(i), new DirectoryMap$$ExternalSyntheticLambda0());
    }

    public Iterator<DirectoryEntry<DEFINITION, VALUE>> getEntries(final int i) {
        return FilterIterator.of(iterator(), new Predicate() { // from class: com.reandroid.dex.data.DirectoryMap$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((DirectoryEntry) obj).equalsDefIndex(i);
            }
        });
    }

    public Iterator<VALUE> getValues(DEFINITION definition) {
        return ComputeIterator.of(getEntries(definition), new DirectoryMap$$ExternalSyntheticLambda0());
    }

    public Iterator<VALUE> getValues() {
        return ComputeIterator.of(iterator(), new DirectoryMap$$ExternalSyntheticLambda0());
    }

    public Iterator<DirectoryEntry<DEFINITION, VALUE>> getEntries(final DEFINITION definition) {
        return FilterIterator.of(iterator(), new Predicate() { // from class: com.reandroid.dex.data.DirectoryMap$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((DirectoryEntry) obj).equalsDefIndex(definition);
            }
        });
    }

    public Iterator<DirectoryEntry<DEFINITION, VALUE>> getEntries(final Key key) {
        return FilterIterator.of(iterator(), new Predicate() { // from class: com.reandroid.dex.data.DirectoryMap$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((DirectoryEntry) obj).matchesDefinition(key);
            }
        });
    }

    @Override // com.reandroid.arsc.container.BlockList, com.reandroid.arsc.base.Block
    public int countBytes() {
        return getCount() * 8;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void merge(DirectoryMap<DEFINITION, VALUE> directoryMap) {
        if (directoryMap == this) {
            return;
        }
        int size = directoryMap.size();
        ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            ((DirectoryEntry) createNext()).merge((DirectoryEntry) directoryMap.get(i));
        }
    }
}
