package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public final class IntSpliterators {
    public static final EmptySpliterator EMPTY_SPLITERATOR = new EmptySpliterator();

    public static class EmptySpliterator implements IntSpliterator, Serializable, Cloneable {
        private static final long serialVersionUID = 8379247926738230492L;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16448;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return 0L;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Integer> consumer) {
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator
        @Deprecated
        public boolean tryAdvance(Consumer<? super Integer> consumer) {
            return false;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            return null;
        }

        protected EmptySpliterator() {
        }

        public Object clone() {
            return IntSpliterators.EMPTY_SPLITERATOR;
        }

        private Object readResolve() {
            return IntSpliterators.EMPTY_SPLITERATOR;
        }
    }

    private static class ArraySpliterator implements IntSpliterator {
        final int[] array;
        final int characteristics;
        private int curr;
        private int length;
        private final int offset;

        public ArraySpliterator(int[] iArr, int i, int i2, int i3) {
            this.array = iArr;
            this.offset = i;
            this.length = i2;
            this.characteristics = i3 | 16720;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            if (this.curr >= this.length) {
                return false;
            }
            Objects.requireNonNull(intConsumer);
            int[] iArr = this.array;
            int i = this.offset;
            int i2 = this.curr;
            this.curr = i2 + 1;
            intConsumer.accept(iArr[i + i2]);
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
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
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            while (true) {
                int i = this.curr;
                if (i >= this.length) {
                    return;
                }
                intConsumer.accept(this.array[this.offset + i]);
                this.curr++;
            }
        }
    }

    private static class ArraySpliteratorWithComparator extends ArraySpliterator {
        private final IntComparator comparator;

        public ArraySpliteratorWithComparator(int[] iArr, int i, int i2, int i3, IntComparator intComparator) {
            super(iArr, i, i2, i3 | 20);
            this.comparator = intComparator;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterators.ArraySpliterator
        public ArraySpliteratorWithComparator makeForSplit(int i, int i2) {
            return new ArraySpliteratorWithComparator(this.array, i, i2, this.characteristics, this.comparator);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator, java.util.Spliterator
        public IntComparator getComparator() {
            return this.comparator;
        }
    }

    public static IntSpliterator wrap(int[] iArr, int i, int i2, int i3) {
        IntArrays.ensureOffsetLength(iArr, i, i2);
        return new ArraySpliterator(iArr, i, i2, i3);
    }

    public static IntSpliterator wrapPreSorted(int[] iArr, int i, int i2, int i3, IntComparator intComparator) {
        IntArrays.ensureOffsetLength(iArr, i, i2);
        return new ArraySpliteratorWithComparator(iArr, i, i2, i3, intComparator);
    }

    public static abstract class AbstractIndexBasedSpliterator extends AbstractIntSpliterator {
        protected int pos;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16720;
        }

        protected abstract int get(int i);

        protected abstract int getMaxPos();

        protected abstract IntSpliterator makeForSplit(int i, int i2);

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
        public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            if (this.pos >= getMaxPos()) {
                return false;
            }
            int i = this.pos;
            this.pos = i + 1;
            intConsumer.accept(get(i));
            return true;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            int maxPos = getMaxPos();
            while (true) {
                int i = this.pos;
                if (i >= maxPos) {
                    return;
                }
                intConsumer.accept(get(i));
                this.pos++;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            int maxPos = getMaxPos();
            int iComputeSplitPoint = computeSplitPoint();
            if (iComputeSplitPoint == this.pos || iComputeSplitPoint == maxPos) {
                return null;
            }
            splitPointCheck(iComputeSplitPoint, maxPos);
            IntSpliterator intSpliteratorMakeForSplit = makeForSplit(this.pos, iComputeSplitPoint);
            if (intSpliteratorMakeForSplit != null) {
                this.pos = iComputeSplitPoint;
            }
            return intSpliteratorMakeForSplit;
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
        protected final int getMaxPos() {
            return this.maxPosFixed ? this.maxPos : getMaxPosFromBackingStore();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            IntSpliterator intSpliteratorTrySplit = super.trySplit();
            if (!this.maxPosFixed && intSpliteratorTrySplit != null) {
                this.maxPos = getMaxPosFromBackingStore();
                this.maxPosFixed = true;
            }
            return intSpliteratorTrySplit;
        }
    }

    private static class SpliteratorFromIterator implements IntSpliterator {
        final int characteristics;
        private IntSpliterator delegate;
        private final IntIterator iter;
        private final boolean knownSize;
        private int nextBatchSize;
        private long size;

        SpliteratorFromIterator(IntIterator intIterator, int i) {
            this.size = Long.MAX_VALUE;
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = intIterator;
            this.characteristics = i | 256;
            this.knownSize = false;
        }

        SpliteratorFromIterator(IntIterator intIterator, long j, int i) {
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = intIterator;
            this.knownSize = true;
            this.size = j;
            if ((i & 4096) != 0) {
                this.characteristics = i | 256;
            } else {
                this.characteristics = i | 16704;
            }
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            IntSpliterator intSpliterator = this.delegate;
            if (intSpliterator != null) {
                boolean zTryAdvance = intSpliterator.tryAdvance(intConsumer);
                if (!zTryAdvance) {
                    this.delegate = null;
                }
                return zTryAdvance;
            }
            if (!this.iter.hasNext()) {
                return false;
            }
            this.size--;
            intConsumer.accept(this.iter.nextInt());
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            IntSpliterator intSpliterator = this.delegate;
            if (intSpliterator != null) {
                intSpliterator.forEachRemaining(intConsumer);
                this.delegate = null;
            }
            this.iter.forEachRemaining(intConsumer);
            this.size = 0L;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            IntSpliterator intSpliterator = this.delegate;
            if (intSpliterator != null) {
                return intSpliterator.estimateSize();
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

        protected IntSpliterator makeForSplit(int[] iArr, int i) {
            return IntSpliterators.wrap(iArr, 0, i, this.characteristics);
        }

        /* JADX WARN: Code duplicated, block: B:11:0x001f  */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
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
            int[] iArrCopyOf = new int[iMin];
            int i = 0;
            while (i < iMin && this.iter.hasNext()) {
                iArrCopyOf[i] = this.iter.nextInt();
                this.size--;
                i++;
            }
            if (iMin < this.nextBatchSize && this.iter.hasNext()) {
                iArrCopyOf = Arrays.copyOf(iArrCopyOf, this.nextBatchSize);
                while (this.iter.hasNext() && i < this.nextBatchSize) {
                    iArrCopyOf[i] = this.iter.nextInt();
                    this.size--;
                    i++;
                }
            }
            this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
            IntSpliterator intSpliteratorMakeForSplit = makeForSplit(iArrCopyOf, i);
            if (this.iter.hasNext()) {
                return intSpliteratorMakeForSplit;
            }
            this.delegate = intSpliteratorMakeForSplit;
            return intSpliteratorMakeForSplit.trySplit();
        }
    }

    private static class SpliteratorFromIteratorWithComparator extends SpliteratorFromIterator {
        private final IntComparator comparator;

        SpliteratorFromIteratorWithComparator(IntIterator intIterator, long j, int i, IntComparator intComparator) {
            super(intIterator, j, i | 20);
            this.comparator = intComparator;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator, java.util.Spliterator
        public IntComparator getComparator() {
            return this.comparator;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterators.SpliteratorFromIterator
        protected IntSpliterator makeForSplit(int[] iArr, int i) {
            return IntSpliterators.wrapPreSorted(iArr, 0, i, this.characteristics, this.comparator);
        }
    }

    public static IntSpliterator asSpliterator(IntIterator intIterator, long j, int i) {
        return new SpliteratorFromIterator(intIterator, j, i);
    }

    public static IntSpliterator asSpliteratorFromSorted(IntIterator intIterator, long j, int i, IntComparator intComparator) {
        return new SpliteratorFromIteratorWithComparator(intIterator, j, i, intComparator);
    }

    public static IntSpliterator asSpliteratorUnknownSize(IntIterator intIterator, int i) {
        return new SpliteratorFromIterator(intIterator, i);
    }
}
