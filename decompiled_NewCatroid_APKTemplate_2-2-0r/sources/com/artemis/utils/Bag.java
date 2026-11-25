package com.artemis.utils;

import com.artemis.utils.reflect.ArrayReflection;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes8.dex */
public class Bag<E> implements ImmutableBag<E> {
    E[] data;
    private Bag<E>.BagIterator it;
    protected int size;

    public Bag() {
        this(64);
    }

    public Bag(Class<E> cls) {
        this(cls, 64);
    }

    public Bag(int i) {
        this.size = 0;
        this.data = (E[]) ((Object[]) ArrayReflection.newInstance(Object.class, i));
    }

    public Bag(Class<E> cls, int i) {
        this.size = 0;
        this.data = (E[]) ((Object[]) ArrayReflection.newInstance(cls, i));
    }

    public E remove(int i) {
        E[] eArr = this.data;
        E e = eArr[i];
        int i2 = this.size - 1;
        this.size = i2;
        eArr[i] = eArr[i2];
        eArr[i2] = null;
        return e;
    }

    public void sort(Comparator<E> comparator) {
        Sort.instance().sort(this, comparator);
    }

    public E removeLast() {
        int i = this.size;
        if (i <= 0) {
            return null;
        }
        E[] eArr = this.data;
        int i2 = i - 1;
        this.size = i2;
        E e = eArr[i2];
        eArr[i2] = null;
        return e;
    }

    public boolean remove(E e) {
        for (int i = 0; i < this.size; i++) {
            if (e.equals(this.data[i])) {
                E[] eArr = this.data;
                int i2 = this.size - 1;
                this.size = i2;
                eArr[i] = eArr[i2];
                eArr[i2] = null;
                return true;
            }
        }
        return false;
    }

    @Override // com.artemis.utils.ImmutableBag
    public boolean contains(E e) {
        for (int i = 0; this.size > i; i++) {
            if (e.equals(this.data[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean removeAll(ImmutableBag<E> immutableBag) {
        int size = immutableBag.size();
        boolean z = false;
        for (int i = 0; size > i; i++) {
            E e = immutableBag.get(i);
            for (int i2 = 0; i2 < this.size; i2++) {
                if (e.equals(this.data[i2])) {
                    remove(i2);
                    z = true;
                    break;
                }
            }
        }
        return z;
    }

    @Override // com.artemis.utils.ImmutableBag
    public E get(int i) {
        return this.data[i];
    }

    public E safeGet(int i) {
        E[] eArr = this.data;
        if (i >= eArr.length) {
            grow(Math.max(eArr.length * 2, (i * 3) / 2));
        }
        return this.data[i];
    }

    @Override // com.artemis.utils.ImmutableBag
    public int size() {
        return this.size;
    }

    public int getCapacity() {
        return this.data.length;
    }

    public boolean isIndexWithinBounds(int i) {
        return i < getCapacity();
    }

    @Override // com.artemis.utils.ImmutableBag
    public boolean isEmpty() {
        return this.size == 0;
    }

    public void add(E e) {
        int i = this.size;
        E[] eArr = this.data;
        if (i == eArr.length) {
            grow(eArr.length * 2);
        }
        E[] eArr2 = this.data;
        int i2 = this.size;
        this.size = i2 + 1;
        eArr2[i2] = e;
    }

    public void unsafeSet(int i, E e) {
        this.data[i] = e;
    }

    public void set(int i, E e) {
        E[] eArr = this.data;
        if (i >= eArr.length) {
            grow(Math.max(eArr.length * 2, i + 1));
        }
        this.size = Math.max(this.size, i + 1);
        unsafeSet(i, e);
    }

    private void grow(int i) {
        this.data = (E[]) Arrays.copyOf(this.data, i);
    }

    public void ensureCapacity(int i) {
        if (i >= this.data.length) {
            grow(i + 1);
        }
    }

    public void clear() {
        Arrays.fill(this.data, 0, this.size, (Object) null);
        this.size = 0;
    }

    public void addAll(ImmutableBag<E> immutableBag) {
        int size = immutableBag.size();
        for (int i = 0; size > i; i++) {
            add(immutableBag.get(i));
        }
    }

    public E[] getData() {
        return this.data;
    }

    @Override // java.lang.Iterable
    public Iterator<E> iterator() {
        if (this.it == null) {
            this.it = new BagIterator();
        }
        ((BagIterator) this.it).validCursorPos = false;
        ((BagIterator) this.it).cursor = 0;
        return this.it;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Bag(");
        for (int i = 0; this.size > i; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(this.data[i]);
        }
        sb.append(')');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Bag bag = (Bag) obj;
        if (this.size != bag.size()) {
            return false;
        }
        for (int i = 0; this.size > i; i++) {
            if (this.data[i] != bag.data[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = this.size;
        int iHashCode = 0;
        for (int i2 = 0; i > i2; i2++) {
            iHashCode = (iHashCode * 127) + this.data[i2].hashCode();
        }
        return iHashCode;
    }

    private final class BagIterator implements Iterator<E> {
        private int cursor;
        private boolean validCursorPos;

        private BagIterator() {
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.cursor < Bag.this.size;
        }

        @Override // java.util.Iterator
        public E next() throws NoSuchElementException {
            if (this.cursor == Bag.this.size) {
                throw new NoSuchElementException("Iterated past last element");
            }
            E[] eArr = Bag.this.data;
            int i = this.cursor;
            this.cursor = i + 1;
            E e = eArr[i];
            this.validCursorPos = true;
            return e;
        }

        @Override // java.util.Iterator
        public void remove() throws IllegalStateException {
            if (!this.validCursorPos) {
                throw new IllegalStateException();
            }
            this.validCursorPos = false;
            Bag bag = Bag.this;
            int i = this.cursor - 1;
            this.cursor = i;
            bag.remove(i);
        }
    }
}
