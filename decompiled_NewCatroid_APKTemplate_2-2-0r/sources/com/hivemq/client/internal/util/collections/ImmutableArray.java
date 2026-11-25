package com.hivemq.client.internal.util.collections;

import com.hivemq.client.internal.util.Checks;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes10.dex */
class ImmutableArray<E> implements ImmutableList<E> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Object[] array;

    int getFromIndex() {
        return 0;
    }

    static <E> ImmutableList<E> of(Object... objArr) {
        return of(objArr, "Immutable list");
    }

    static <E> ImmutableList<E> of(Object[] objArr, String str) {
        return new ImmutableArray(Checks.elementsNotNull(objArr, str));
    }

    ImmutableArray(Object[] objArr) {
        this.array = objArr;
    }

    int getToIndex() {
        return this.array.length;
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return getToIndex() - getFromIndex();
    }

    @Override // com.hivemq.client.internal.util.collections.ImmutableList, java.util.List
    public E get(int i) {
        return (E) this.array[getFromIndex() + Checks.index(i, size())];
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return Arrays.copyOfRange(this.array, getFromIndex(), getToIndex());
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        Checks.notNull(tArr, "Array");
        int size = size();
        if (tArr.length < size) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
        } else if (tArr.length > size) {
            tArr[size] = null;
        }
        System.arraycopy(this.array, getFromIndex(), tArr, 0, size);
        return tArr;
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        if (obj == null) {
            return -1;
        }
        int fromIndex = getFromIndex();
        int toIndex = getToIndex();
        for (int i = fromIndex; i < toIndex; i++) {
            if (obj.equals(this.array[i])) {
                return i - fromIndex;
            }
        }
        return -1;
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        if (obj == null) {
            return -1;
        }
        int fromIndex = getFromIndex();
        for (int toIndex = getToIndex() - 1; toIndex >= fromIndex; toIndex--) {
            if (obj.equals(this.array[toIndex])) {
                return toIndex - fromIndex;
            }
        }
        return -1;
    }

    @Override // com.hivemq.client.internal.util.collections.ImmutableList, java.util.List
    public ImmutableList.ImmutableListIterator<E> listIterator(int i) {
        return new ArrayIterator(getFromIndex() + Checks.cursorIndex(i, size()));
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this.array, getFromIndex(), getToIndex(), 1296);
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer<? super E> consumer) {
        Checks.notNull(consumer, "Consumer");
        int toIndex = getToIndex();
        for (int fromIndex = getFromIndex(); fromIndex < toIndex; fromIndex++) {
            consumer.accept(this.array[fromIndex]);
        }
    }

    @Override // com.hivemq.client.internal.util.collections.ImmutableList, java.util.List
    public ImmutableList<E> subList(int i, int i2) {
        int size = size();
        Checks.indexRange(i, i2, size);
        int fromIndex = getFromIndex();
        int i3 = i2 - i;
        if (i3 == 0) {
            return ImmutableEmptyList.of();
        }
        if (i3 != 1) {
            return i3 == size ? this : new SubArray(this.array, i + fromIndex, fromIndex + i2);
        }
        return new ImmutableElement(this.array[fromIndex + i]);
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        List list = (List) obj;
        int fromIndex = getFromIndex();
        int toIndex = getToIndex();
        if (toIndex - fromIndex != list.size()) {
            return false;
        }
        if (list instanceof RandomAccess) {
            while (fromIndex < toIndex) {
                if (!this.array[fromIndex].equals(list.get(fromIndex))) {
                    return false;
                }
                fromIndex++;
            }
        } else {
            Iterator<E> it = list.iterator();
            while (it.hasNext()) {
                int i = fromIndex + 1;
                if (!this.array[fromIndex].equals(it.next())) {
                    return false;
                }
                fromIndex = i;
            }
        }
        return true;
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        int toIndex = getToIndex();
        int iHashCode = 1;
        for (int fromIndex = getFromIndex(); fromIndex < toIndex; fromIndex++) {
            iHashCode = (iHashCode * 31) + this.array[fromIndex].hashCode();
        }
        return iHashCode;
    }

    public String toString() {
        int fromIndex = getFromIndex();
        int toIndex = getToIndex();
        StringBuilder sb = new StringBuilder("[");
        while (true) {
            int i = fromIndex + 1;
            sb.append(this.array[fromIndex]);
            if (i == toIndex) {
                return sb.append(']').toString();
            }
            sb.append(", ");
            fromIndex = i;
        }
    }

    private static class SubArray<E> extends ImmutableArray<E> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final int fromIndex;
        private final int toIndex;

        @Override // com.hivemq.client.internal.util.collections.ImmutableArray, com.hivemq.client.internal.util.collections.ImmutableList, java.util.List
        public /* bridge */ /* synthetic */ ListIterator listIterator(int i) {
            return super.listIterator(i);
        }

        @Override // com.hivemq.client.internal.util.collections.ImmutableArray, com.hivemq.client.internal.util.collections.ImmutableList, java.util.List
        public /* bridge */ /* synthetic */ List subList(int i, int i2) {
            return super.subList(i, i2);
        }

        SubArray(Object[] objArr, int i, int i2) {
            super(objArr);
            this.fromIndex = i;
            this.toIndex = i2;
        }

        @Override // com.hivemq.client.internal.util.collections.ImmutableArray
        public int getFromIndex() {
            return this.fromIndex;
        }

        @Override // com.hivemq.client.internal.util.collections.ImmutableArray
        public int getToIndex() {
            return this.toIndex;
        }

        @Override // com.hivemq.client.internal.util.collections.ImmutableList
        public ImmutableList<E> trim() {
            return new ImmutableArray(toArray());
        }
    }

    private class ArrayIterator implements ImmutableList.ImmutableListIterator<E> {
        private int index;

        ArrayIterator(int i) {
            this.index = i;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            return this.index < ImmutableArray.this.getToIndex();
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public E next() {
            if (hasNext()) {
                Object[] objArr = ImmutableArray.this.array;
                int i = this.index;
                this.index = i + 1;
                return (E) objArr[i];
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.index - ImmutableArray.this.getFromIndex();
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return this.index > ImmutableArray.this.getFromIndex();
        }

        @Override // java.util.ListIterator
        public E previous() {
            if (hasPrevious()) {
                Object[] objArr = ImmutableArray.this.array;
                int i = this.index - 1;
                this.index = i;
                return (E) objArr[i];
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return (this.index - 1) - ImmutableArray.this.getFromIndex();
        }

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super E> consumer) {
            Checks.notNull(consumer, "Consumer");
            while (hasNext()) {
                consumer.accept((Object) next());
            }
        }
    }
}
