package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface ReferenceList<K> extends ReferenceCollection<K>, List<K> {
    void getElements(int i, Object[] objArr, int i2, int i3);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection
    ObjectListIterator<K> iterator();

    @Override // java.util.List
    ObjectListIterator<K> listIterator();

    @Override // java.util.List
    ObjectListIterator<K> listIterator(int i);

    void removeElements(int i, int i2);

    @Override // java.util.List
    ReferenceList<K> subList(int i, int i2);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection
    default ObjectSpliterator<K> spliterator() {
        if (this instanceof RandomAccess) {
            return new AbstractReferenceList.IndexBasedSpliterator(this, 0);
        }
        return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 16464);
    }

    default void setElements(K[] kArr) {
        setElements(0, kArr);
    }

    default void setElements(int i, K[] kArr) {
        setElements(i, kArr, 0, kArr.length);
    }

    default void setElements(int i, K[] kArr, int i2, int i3) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is negative");
        }
        if (i > size()) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        ObjectArrays.ensureOffsetLength(kArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        ObjectListIterator<K> objectListIteratorListIterator = listIterator(i);
        for (int i5 = 0; i5 < i3; i5++) {
            objectListIteratorListIterator.next();
            objectListIteratorListIterator.set(kArr[i5 + i2]);
        }
    }

    default boolean addAll(int i, ReferenceList<? extends K> referenceList) {
        return addAll(i, (Collection) referenceList);
    }

    default boolean addAll(ReferenceList<? extends K> referenceList) {
        return addAll(size(), (ReferenceList) referenceList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.List
    default void sort(Comparator<? super K> comparator) {
        Object[] array = toArray();
        if (comparator == null) {
            ObjectArrays.stableSort(array);
        } else {
            ObjectArrays.stableSort(array, comparator);
        }
        setElements(array);
    }
}
