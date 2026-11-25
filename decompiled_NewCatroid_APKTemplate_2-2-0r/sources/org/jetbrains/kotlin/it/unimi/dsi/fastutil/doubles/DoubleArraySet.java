package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes5.dex */
public class DoubleArraySet extends AbstractDoubleSet implements Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private transient double[] a;
    private int size;

    static /* synthetic */ int access$010(DoubleArraySet doubleArraySet) {
        int i = doubleArraySet.size;
        doubleArraySet.size = i - 1;
        return i;
    }

    public DoubleArraySet(double[] dArr) {
        this.a = dArr;
        this.size = dArr.length;
    }

    public DoubleArraySet() {
        this.a = DoubleArrays.EMPTY_ARRAY;
    }

    private int findKey(double d) {
        int i = this.size;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return -1;
            }
            if (Double.doubleToLongBits(this.a[i2]) == Double.doubleToLongBits(d)) {
                return i2;
            }
            i = i2;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
    public DoubleIterator iterator() {
        return new DoubleIterator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleArraySet.1
            int next = 0;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.next < DoubleArraySet.this.size;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterator, java.util.PrimitiveIterator.OfDouble
            public double nextDouble() {
                if (hasNext()) {
                    double[] dArr = DoubleArraySet.this.a;
                    int i = this.next;
                    this.next = i + 1;
                    return dArr[i];
                }
                throw new NoSuchElementException();
            }

            @Override // java.util.Iterator
            public void remove() {
                int iAccess$010 = DoubleArraySet.access$010(DoubleArraySet.this);
                int i = this.next;
                this.next = i - 1;
                System.arraycopy(DoubleArraySet.this.a, this.next + 1, DoubleArraySet.this.a, this.next, iAccess$010 - i);
            }
        };
    }

    private final class Spliterator implements DoubleSpliterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        boolean hasSplit;
        int max;
        int pos;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16721;
        }

        public Spliterator(DoubleArraySet doubleArraySet) {
            this(0, doubleArraySet.size, false);
        }

        private Spliterator(int i, int i2, boolean z) {
            this.pos = i;
            this.max = i2;
            this.hasSplit = z;
        }

        private int getWorkingMax() {
            return this.hasSplit ? this.max : DoubleArraySet.this.size;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return getWorkingMax() - this.pos;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(java.util.function.DoubleConsumer doubleConsumer) {
            if (this.pos >= getWorkingMax()) {
                return false;
            }
            double[] dArr = DoubleArraySet.this.a;
            int i = this.pos;
            this.pos = i + 1;
            doubleConsumer.accept(dArr[i]);
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(java.util.function.DoubleConsumer doubleConsumer) {
            int workingMax = getWorkingMax();
            while (this.pos < workingMax) {
                doubleConsumer.accept(DoubleArraySet.this.a[this.pos]);
                this.pos++;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterator, java.util.Spliterator.OfDouble, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public DoubleSpliterator trySplit() {
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
            return DoubleArraySet.this.new Spliterator(i, i3, true);
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
    public DoubleSpliterator spliterator() {
        return new Spliterator(this);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    public boolean contains(double d) {
        return findKey(d) != -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.size;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet
    public boolean remove(double d) {
        int iFindKey = findKey(d);
        if (iFindKey == -1) {
            return false;
        }
        int i = (this.size - iFindKey) - 1;
        for (int i2 = 0; i2 < i; i2++) {
            double[] dArr = this.a;
            int i3 = iFindKey + i2;
            dArr[i3] = dArr[i3 + 1];
        }
        this.size--;
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    public boolean add(double d) {
        if (findKey(d) != -1) {
            return false;
        }
        int i = this.size;
        if (i == this.a.length) {
            double[] dArr = new double[i == 0 ? 2 : i * 2];
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    break;
                }
                dArr[i2] = this.a[i2];
                i = i2;
            }
            this.a = dArr;
        }
        double[] dArr2 = this.a;
        int i3 = this.size;
        this.size = i3 + 1;
        dArr2[i3] = d;
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    public double[] toDoubleArray() {
        return Arrays.copyOf(this.a, this.size);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection
    public double[] toArray(double[] dArr) {
        if (dArr == null || dArr.length < this.size) {
            dArr = new double[this.size];
        }
        System.arraycopy(this.a, 0, dArr, 0, this.size);
        return dArr;
    }

    public DoubleArraySet clone() {
        try {
            DoubleArraySet doubleArraySet = (DoubleArraySet) super.clone();
            doubleArraySet.a = (double[]) this.a.clone();
            return doubleArraySet;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; i++) {
            objectOutputStream.writeDouble(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.a = new double[this.size];
        for (int i = 0; i < this.size; i++) {
            this.a[i] = objectInputStream.readDouble();
        }
    }
}
