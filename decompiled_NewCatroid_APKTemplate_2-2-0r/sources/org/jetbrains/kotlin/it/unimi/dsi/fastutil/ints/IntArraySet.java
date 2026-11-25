package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes5.dex */
public class IntArraySet extends AbstractIntSet implements Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private transient int[] a;
    private int size;

    static /* synthetic */ int access$010(IntArraySet intArraySet) {
        int i = intArraySet.size;
        intArraySet.size = i - 1;
        return i;
    }

    public IntArraySet(int[] iArr) {
        this.a = iArr;
        this.size = iArr.length;
    }

    public IntArraySet() {
        this.a = IntArrays.EMPTY_ARRAY;
    }

    private int findKey(int i) {
        int i2 = this.size;
        while (true) {
            int i3 = i2 - 1;
            if (i2 == 0) {
                return -1;
            }
            if (this.a[i3] == i) {
                return i3;
            }
            i2 = i3;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    public IntIterator iterator() {
        return new IntIterator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntArraySet.1
            int next = 0;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.next < IntArraySet.this.size;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
            public int nextInt() {
                if (hasNext()) {
                    int[] iArr = IntArraySet.this.a;
                    int i = this.next;
                    this.next = i + 1;
                    return iArr[i];
                }
                throw new NoSuchElementException();
            }

            @Override // java.util.Iterator
            public void remove() {
                int iAccess$010 = IntArraySet.access$010(IntArraySet.this);
                int i = this.next;
                this.next = i - 1;
                System.arraycopy(IntArraySet.this.a, this.next + 1, IntArraySet.this.a, this.next, iAccess$010 - i);
            }
        };
    }

    private final class Spliterator implements IntSpliterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        boolean hasSplit;
        int max;
        int pos;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16721;
        }

        public Spliterator(IntArraySet intArraySet) {
            this(0, intArraySet.size, false);
        }

        private Spliterator(int i, int i2, boolean z) {
            this.pos = i;
            this.max = i2;
            this.hasSplit = z;
        }

        private int getWorkingMax() {
            return this.hasSplit ? this.max : IntArraySet.this.size;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return getWorkingMax() - this.pos;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            if (this.pos >= getWorkingMax()) {
                return false;
            }
            int[] iArr = IntArraySet.this.a;
            int i = this.pos;
            this.pos = i + 1;
            intConsumer.accept(iArr[i]);
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            int workingMax = getWorkingMax();
            while (this.pos < workingMax) {
                intConsumer.accept(IntArraySet.this.a[this.pos]);
                this.pos++;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            int workingMax = getWorkingMax();
            int i = this.pos;
            int i2 = (workingMax - i) >> 1;
            if (i2 <= 1) {
                return null;
            }
            this.max = workingMax;
            int i3 = i2 + i;
            this.pos = i3;
            this.hasSplit = true;
            return IntArraySet.this.new Spliterator(i, i3, true);
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    public IntSpliterator spliterator() {
        return new Spliterator(this);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public boolean contains(int i) {
        return findKey(i) != -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.size;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet
    public boolean remove(int i) {
        int iFindKey = findKey(i);
        if (iFindKey == -1) {
            return false;
        }
        int i2 = (this.size - iFindKey) - 1;
        for (int i3 = 0; i3 < i2; i3++) {
            int[] iArr = this.a;
            int i4 = iFindKey + i3;
            iArr[i4] = iArr[i4 + 1];
        }
        this.size--;
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public boolean add(int i) {
        if (findKey(i) != -1) {
            return false;
        }
        int i2 = this.size;
        if (i2 == this.a.length) {
            int[] iArr = new int[i2 == 0 ? 2 : i2 * 2];
            while (true) {
                int i3 = i2 - 1;
                if (i2 == 0) {
                    break;
                }
                iArr[i3] = this.a[i3];
                i2 = i3;
            }
            this.a = iArr;
        }
        int[] iArr2 = this.a;
        int i4 = this.size;
        this.size = i4 + 1;
        iArr2[i4] = i;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        this.size = 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public int[] toIntArray() {
        return Arrays.copyOf(this.a, this.size);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection
    public int[] toArray(int[] iArr) {
        if (iArr == null || iArr.length < this.size) {
            iArr = new int[this.size];
        }
        System.arraycopy(this.a, 0, iArr, 0, this.size);
        return iArr;
    }

    public IntArraySet clone() {
        try {
            IntArraySet intArraySet = (IntArraySet) super.clone();
            intArraySet.a = (int[]) this.a.clone();
            return intArraySet;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; i++) {
            objectOutputStream.writeInt(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.a = new int[this.size];
        for (int i = 0; i < this.size; i++) {
            this.a[i] = objectInputStream.readInt();
        }
    }
}
