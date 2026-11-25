package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes5.dex */
public class LongArraySet extends AbstractLongSet implements Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private transient long[] a;
    private int size;

    static /* synthetic */ int access$010(LongArraySet longArraySet) {
        int i = longArraySet.size;
        longArraySet.size = i - 1;
        return i;
    }

    public LongArraySet(long[] jArr) {
        this.a = jArr;
        this.size = jArr.length;
    }

    public LongArraySet() {
        this.a = LongArrays.EMPTY_ARRAY;
    }

    private int findKey(long j) {
        int i = this.size;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return -1;
            }
            if (this.a[i2] == j) {
                return i2;
            }
            i = i2;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
    public LongIterator iterator() {
        return new LongIterator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongArraySet.1
            int next = 0;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.next < LongArraySet.this.size;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterator, java.util.PrimitiveIterator.OfLong
            public long nextLong() {
                if (hasNext()) {
                    long[] jArr = LongArraySet.this.a;
                    int i = this.next;
                    this.next = i + 1;
                    return jArr[i];
                }
                throw new NoSuchElementException();
            }

            @Override // java.util.Iterator
            public void remove() {
                int iAccess$010 = LongArraySet.access$010(LongArraySet.this);
                int i = this.next;
                this.next = i - 1;
                System.arraycopy(LongArraySet.this.a, this.next + 1, LongArraySet.this.a, this.next, iAccess$010 - i);
            }
        };
    }

    private final class Spliterator implements LongSpliterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        boolean hasSplit;
        int max;
        int pos;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16721;
        }

        public Spliterator(LongArraySet longArraySet) {
            this(0, longArraySet.size, false);
        }

        private Spliterator(int i, int i2, boolean z) {
            this.pos = i;
            this.max = i2;
            this.hasSplit = z;
        }

        private int getWorkingMax() {
            return this.hasSplit ? this.max : LongArraySet.this.size;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return getWorkingMax() - this.pos;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(java.util.function.LongConsumer longConsumer) {
            if (this.pos >= getWorkingMax()) {
                return false;
            }
            long[] jArr = LongArraySet.this.a;
            int i = this.pos;
            this.pos = i + 1;
            longConsumer.accept(jArr[i]);
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(java.util.function.LongConsumer longConsumer) {
            int workingMax = getWorkingMax();
            while (this.pos < workingMax) {
                longConsumer.accept(LongArraySet.this.a[this.pos]);
                this.pos++;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterator, java.util.Spliterator.OfLong, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public LongSpliterator trySplit() {
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
            return LongArraySet.this.new Spliterator(i, i3, true);
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
    public LongSpliterator spliterator() {
        return new Spliterator(this);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    public boolean contains(long j) {
        return findKey(j) != -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.size;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSet
    public boolean remove(long j) {
        int iFindKey = findKey(j);
        if (iFindKey == -1) {
            return false;
        }
        int i = (this.size - iFindKey) - 1;
        for (int i2 = 0; i2 < i; i2++) {
            long[] jArr = this.a;
            int i3 = iFindKey + i2;
            jArr[i3] = jArr[i3 + 1];
        }
        this.size--;
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    public boolean add(long j) {
        if (findKey(j) != -1) {
            return false;
        }
        int i = this.size;
        if (i == this.a.length) {
            long[] jArr = new long[i == 0 ? 2 : i * 2];
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    break;
                }
                jArr[i2] = this.a[i2];
                i = i2;
            }
            this.a = jArr;
        }
        long[] jArr2 = this.a;
        int i3 = this.size;
        this.size = i3 + 1;
        jArr2[i3] = j;
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    public long[] toLongArray() {
        return Arrays.copyOf(this.a, this.size);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection
    public long[] toArray(long[] jArr) {
        if (jArr == null || jArr.length < this.size) {
            jArr = new long[this.size];
        }
        System.arraycopy(this.a, 0, jArr, 0, this.size);
        return jArr;
    }

    public LongArraySet clone() {
        try {
            LongArraySet longArraySet = (LongArraySet) super.clone();
            longArraySet.a = (long[]) this.a.clone();
            return longArraySet;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; i++) {
            objectOutputStream.writeLong(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.a = new long[this.size];
        for (int i = 0; i < this.size; i++) {
            this.a[i] = objectInputStream.readLong();
        }
    }
}
