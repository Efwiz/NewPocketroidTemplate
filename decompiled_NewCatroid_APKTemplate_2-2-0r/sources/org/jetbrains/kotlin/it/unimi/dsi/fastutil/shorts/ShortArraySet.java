package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes5.dex */
public class ShortArraySet extends AbstractShortSet implements Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private transient short[] a;
    private int size;

    static /* synthetic */ int access$010(ShortArraySet shortArraySet) {
        int i = shortArraySet.size;
        shortArraySet.size = i - 1;
        return i;
    }

    public ShortArraySet(short[] sArr) {
        this.a = sArr;
        this.size = sArr.length;
    }

    public ShortArraySet() {
        this.a = ShortArrays.EMPTY_ARRAY;
    }

    private int findKey(short s) {
        int i = this.size;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return -1;
            }
            if (this.a[i2] == s) {
                return i2;
            }
            i = i2;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
    public ShortIterator iterator() {
        return new ShortIterator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortArraySet.1
            int next = 0;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.next < ShortArraySet.this.size;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterator
            public short nextShort() {
                if (hasNext()) {
                    short[] sArr = ShortArraySet.this.a;
                    int i = this.next;
                    this.next = i + 1;
                    return sArr[i];
                }
                throw new NoSuchElementException();
            }

            @Override // java.util.Iterator
            public void remove() {
                int iAccess$010 = ShortArraySet.access$010(ShortArraySet.this);
                int i = this.next;
                this.next = i - 1;
                System.arraycopy(ShortArraySet.this.a, this.next + 1, ShortArraySet.this.a, this.next, iAccess$010 - i);
            }
        };
    }

    private final class Spliterator implements ShortSpliterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        boolean hasSplit;
        int max;
        int pos;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16721;
        }

        public Spliterator(ShortArraySet shortArraySet) {
            this(0, shortArraySet.size, false);
        }

        private Spliterator(int i, int i2, boolean z) {
            this.pos = i;
            this.max = i2;
            this.hasSplit = z;
        }

        private int getWorkingMax() {
            return this.hasSplit ? this.max : ShortArraySet.this.size;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return getWorkingMax() - this.pos;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(ShortConsumer shortConsumer) {
            if (this.pos >= getWorkingMax()) {
                return false;
            }
            short[] sArr = ShortArraySet.this.a;
            int i = this.pos;
            this.pos = i + 1;
            shortConsumer.accept(sArr[i]);
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(ShortConsumer shortConsumer) {
            int workingMax = getWorkingMax();
            while (this.pos < workingMax) {
                shortConsumer.accept(ShortArraySet.this.a[this.pos]);
                this.pos++;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public ShortSpliterator trySplit() {
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
            return ShortArraySet.this.new Spliterator(i, i3, true);
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
    public ShortSpliterator spliterator() {
        return new Spliterator(this);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    public boolean contains(short s) {
        return findKey(s) != -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.size;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSet
    public boolean remove(short s) {
        int iFindKey = findKey(s);
        if (iFindKey == -1) {
            return false;
        }
        int i = (this.size - iFindKey) - 1;
        for (int i2 = 0; i2 < i; i2++) {
            short[] sArr = this.a;
            int i3 = iFindKey + i2;
            sArr[i3] = sArr[i3 + 1];
        }
        this.size--;
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    public boolean add(short s) {
        if (findKey(s) != -1) {
            return false;
        }
        int i = this.size;
        if (i == this.a.length) {
            short[] sArr = new short[i == 0 ? 2 : i * 2];
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    break;
                }
                sArr[i2] = this.a[i2];
                i = i2;
            }
            this.a = sArr;
        }
        short[] sArr2 = this.a;
        int i3 = this.size;
        this.size = i3 + 1;
        sArr2[i3] = s;
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    public short[] toShortArray() {
        return Arrays.copyOf(this.a, this.size);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection
    public short[] toArray(short[] sArr) {
        if (sArr == null || sArr.length < this.size) {
            sArr = new short[this.size];
        }
        System.arraycopy(this.a, 0, sArr, 0, this.size);
        return sArr;
    }

    public ShortArraySet clone() {
        try {
            ShortArraySet shortArraySet = (ShortArraySet) super.clone();
            shortArraySet.a = (short[]) this.a.clone();
            return shortArraySet;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; i++) {
            objectOutputStream.writeShort(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.a = new short[this.size];
        for (int i = 0; i < this.size; i++) {
            this.a[i] = objectInputStream.readShort();
        }
    }
}
