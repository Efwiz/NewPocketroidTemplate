package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public final class DoubleSpliterators {
    public static final EmptySpliterator EMPTY_SPLITERATOR = new EmptySpliterator();

    public static class EmptySpliterator implements DoubleSpliterator, Serializable, Cloneable {
        private static final long serialVersionUID = 8379247926738230492L;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16448;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return 0L;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterator, java.util.Spliterator.OfDouble, java.util.Spliterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Double> consumer) {
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(java.util.function.DoubleConsumer doubleConsumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterator, java.util.Spliterator.OfDouble, java.util.Spliterator
        @Deprecated
        public boolean tryAdvance(Consumer<? super Double> consumer) {
            return false;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(java.util.function.DoubleConsumer doubleConsumer) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterator, java.util.Spliterator.OfDouble, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public DoubleSpliterator trySplit() {
            return null;
        }

        protected EmptySpliterator() {
        }

        public Object clone() {
            return DoubleSpliterators.EMPTY_SPLITERATOR;
        }

        private Object readResolve() {
            return DoubleSpliterators.EMPTY_SPLITERATOR;
        }
    }

    private static class ArraySpliterator implements DoubleSpliterator {
        final double[] array;
        final int characteristics;
        private int curr;
        private int length;
        private final int offset;

        public ArraySpliterator(double[] dArr, int i, int i2, int i3) {
            this.array = dArr;
            this.offset = i;
            this.length = i2;
            this.characteristics = i3 | 16720;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(java.util.function.DoubleConsumer doubleConsumer) {
            if (this.curr >= this.length) {
                return false;
            }
            Objects.requireNonNull(doubleConsumer);
            double[] dArr = this.array;
            int i = this.offset;
            int i2 = this.curr;
            this.curr = i2 + 1;
            doubleConsumer.accept(dArr[i + i2]);
            return true;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return this.length - this.curr;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.characteristics;
        }

        protected ArraySpliterator makeForSplit(int i, int i2) {
            return new ArraySpliterator(this.array, i, i2, this.characteristics);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterator, java.util.Spliterator.OfDouble, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public DoubleSpliterator trySplit() {
            int i = this.length;
            int i2 = this.curr;
            int i3 = (i - i2) >> 1;
            if (i3 <= 1) {
                return null;
            }
            int i4 = this.offset + i2;
            this.curr = i2 + i3;
            return makeForSplit(i4, i3);
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(java.util.function.DoubleConsumer doubleConsumer) {
            Objects.requireNonNull(doubleConsumer);
            while (true) {
                int i = this.curr;
                if (i >= this.length) {
                    return;
                }
                doubleConsumer.accept(this.array[this.offset + i]);
                this.curr++;
            }
        }
    }

    public static DoubleSpliterator wrap(double[] dArr, int i, int i2, int i3) {
        DoubleArrays.ensureOffsetLength(dArr, i, i2);
        return new ArraySpliterator(dArr, i, i2, i3);
    }

    public static abstract class AbstractIndexBasedSpliterator extends AbstractDoubleSpliterator {
        protected int pos;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16720;
        }

        protected abstract double get(int i);

        protected abstract int getMaxPos();

        protected abstract DoubleSpliterator makeForSplit(int i, int i2);

        protected AbstractIndexBasedSpliterator(int i) {
            this.pos = i;
        }

        protected int computeSplitPoint() {
            return this.pos + ((getMaxPos() - this.pos) / 2);
        }

        private void splitPointCheck(int i, int i2) {
            if (i < this.pos || i > i2) {
                throw new IndexOutOfBoundsException("splitPoint " + i + " outside of range of current position " + this.pos + " and range end " + i2);
            }
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return ((long) getMaxPos()) - ((long) this.pos);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(java.util.function.DoubleConsumer doubleConsumer) {
            if (this.pos >= getMaxPos()) {
                return false;
            }
            int i = this.pos;
            this.pos = i + 1;
            doubleConsumer.accept(get(i));
            return true;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(java.util.function.DoubleConsumer doubleConsumer) {
            int maxPos = getMaxPos();
            while (true) {
                int i = this.pos;
                if (i >= maxPos) {
                    return;
                }
                doubleConsumer.accept(get(i));
                this.pos++;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterator, java.util.Spliterator.OfDouble, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public DoubleSpliterator trySplit() {
            int maxPos = getMaxPos();
            int iComputeSplitPoint = computeSplitPoint();
            if (iComputeSplitPoint == this.pos || iComputeSplitPoint == maxPos) {
                return null;
            }
            splitPointCheck(iComputeSplitPoint, maxPos);
            DoubleSpliterator doubleSpliteratorMakeForSplit = makeForSplit(this.pos, iComputeSplitPoint);
            if (doubleSpliteratorMakeForSplit != null) {
                this.pos = iComputeSplitPoint;
            }
            return doubleSpliteratorMakeForSplit;
        }
    }

    public static abstract class LateBindingSizeIndexBasedSpliterator extends AbstractIndexBasedSpliterator {
        protected int maxPos;
        private boolean maxPosFixed;

        protected abstract int getMaxPosFromBackingStore();

        protected LateBindingSizeIndexBasedSpliterator(int i) {
            super(i);
            this.maxPos = -1;
            this.maxPosFixed = false;
        }

        protected LateBindingSizeIndexBasedSpliterator(int i, int i2) {
            super(i);
            this.maxPos = i2;
            this.maxPosFixed = true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterators.AbstractIndexBasedSpliterator
        protected final int getMaxPos() {
            return this.maxPosFixed ? this.maxPos : getMaxPosFromBackingStore();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterators.AbstractIndexBasedSpliterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterator, java.util.Spliterator.OfDouble, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public DoubleSpliterator trySplit() {
            DoubleSpliterator doubleSpliteratorTrySplit = super.trySplit();
            if (!this.maxPosFixed && doubleSpliteratorTrySplit != null) {
                this.maxPos = getMaxPosFromBackingStore();
                this.maxPosFixed = true;
            }
            return doubleSpliteratorTrySplit;
        }
    }

    private static class SpliteratorFromIterator implements DoubleSpliterator {
        final int characteristics;
        private DoubleSpliterator delegate;
        private final DoubleIterator iter;
        private final boolean knownSize;
        private int nextBatchSize;
        private long size;

        SpliteratorFromIterator(DoubleIterator doubleIterator, int i) {
            this.size = Long.MAX_VALUE;
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = doubleIterator;
            this.characteristics = i | 256;
            this.knownSize = false;
        }

        SpliteratorFromIterator(DoubleIterator doubleIterator, long j, int i) {
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = doubleIterator;
            this.knownSize = true;
            this.size = j;
            if ((i & 4096) != 0) {
                this.characteristics = i | 256;
            } else {
                this.characteristics = i | 16704;
            }
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(java.util.function.DoubleConsumer doubleConsumer) {
            DoubleSpliterator doubleSpliterator = this.delegate;
            if (doubleSpliterator != null) {
                boolean zTryAdvance = doubleSpliterator.tryAdvance(doubleConsumer);
                if (!zTryAdvance) {
                    this.delegate = null;
                }
                return zTryAdvance;
            }
            if (!this.iter.hasNext()) {
                return false;
            }
            this.size--;
            doubleConsumer.accept(this.iter.nextDouble());
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(java.util.function.DoubleConsumer doubleConsumer) {
            DoubleSpliterator doubleSpliterator = this.delegate;
            if (doubleSpliterator != null) {
                doubleSpliterator.forEachRemaining(doubleConsumer);
                this.delegate = null;
            }
            this.iter.forEachRemaining(doubleConsumer);
            this.size = 0L;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            DoubleSpliterator doubleSpliterator = this.delegate;
            if (doubleSpliterator != null) {
                return doubleSpliterator.estimateSize();
            }
            if (!this.iter.hasNext()) {
                return 0L;
            }
            if (this.knownSize) {
                long j = this.size;
                if (j >= 0) {
                    return j;
                }
            }
            return Long.MAX_VALUE;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.characteristics;
        }

        protected DoubleSpliterator makeForSplit(double[] dArr, int i) {
            return DoubleSpliterators.wrap(dArr, 0, i, this.characteristics);
        }

        /* JADX WARN: Code duplicated, block: B:11:0x001f  */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterator, java.util.Spliterator.OfDouble, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public DoubleSpliterator trySplit() {
            int iMin;
            if (!this.iter.hasNext()) {
                return null;
            }
            if (this.knownSize) {
                long j = this.size;
                if (j > 0) {
                    iMin = (int) Math.min(this.nextBatchSize, j);
                } else {
                    iMin = this.nextBatchSize;
                }
            } else {
                iMin = this.nextBatchSize;
            }
            double[] dArrCopyOf = new double[iMin];
            int i = 0;
            while (i < iMin && this.iter.hasNext()) {
                dArrCopyOf[i] = this.iter.nextDouble();
                this.size--;
                i++;
            }
            if (iMin < this.nextBatchSize && this.iter.hasNext()) {
                dArrCopyOf = Arrays.copyOf(dArrCopyOf, this.nextBatchSize);
                while (this.iter.hasNext() && i < this.nextBatchSize) {
                    dArrCopyOf[i] = this.iter.nextDouble();
                    this.size--;
                    i++;
                }
            }
            this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
            DoubleSpliterator doubleSpliteratorMakeForSplit = makeForSplit(dArrCopyOf, i);
            if (this.iter.hasNext()) {
                return doubleSpliteratorMakeForSplit;
            }
            this.delegate = doubleSpliteratorMakeForSplit;
            return doubleSpliteratorMakeForSplit.trySplit();
        }
    }

    public static DoubleSpliterator asSpliterator(DoubleIterator doubleIterator, long j, int i) {
        return new SpliteratorFromIterator(doubleIterator, j, i);
    }

    public static DoubleSpliterator asSpliteratorUnknownSize(DoubleIterator doubleIterator, int i) {
        return new SpliteratorFromIterator(doubleIterator, i);
    }
}
