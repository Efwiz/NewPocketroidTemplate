package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public final class BooleanSpliterators {
    public static final EmptySpliterator EMPTY_SPLITERATOR = new EmptySpliterator();

    public static class EmptySpliterator implements BooleanSpliterator, Serializable, Cloneable {
        private static final long serialVersionUID = 8379247926738230492L;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16448;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return 0L;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanSpliterator, java.util.Spliterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Boolean> consumer) {
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(BooleanConsumer booleanConsumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanSpliterator, java.util.Spliterator
        @Deprecated
        public boolean tryAdvance(Consumer<? super Boolean> consumer) {
            return false;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(BooleanConsumer booleanConsumer) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public BooleanSpliterator trySplit() {
            return null;
        }

        protected EmptySpliterator() {
        }

        public Object clone() {
            return BooleanSpliterators.EMPTY_SPLITERATOR;
        }

        private Object readResolve() {
            return BooleanSpliterators.EMPTY_SPLITERATOR;
        }
    }

    private static class ArraySpliterator implements BooleanSpliterator {
        final boolean[] array;
        final int characteristics;
        private int curr;
        private int length;
        private final int offset;

        public ArraySpliterator(boolean[] zArr, int i, int i2, int i3) {
            this.array = zArr;
            this.offset = i;
            this.length = i2;
            this.characteristics = i3 | 16720;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(BooleanConsumer booleanConsumer) {
            if (this.curr >= this.length) {
                return false;
            }
            Objects.requireNonNull(booleanConsumer);
            boolean[] zArr = this.array;
            int i = this.offset;
            int i2 = this.curr;
            this.curr = i2 + 1;
            booleanConsumer.accept(zArr[i + i2]);
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public BooleanSpliterator trySplit() {
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
        public void forEachRemaining(BooleanConsumer booleanConsumer) {
            Objects.requireNonNull(booleanConsumer);
            while (true) {
                int i = this.curr;
                if (i >= this.length) {
                    return;
                }
                booleanConsumer.accept(this.array[this.offset + i]);
                this.curr++;
            }
        }
    }

    public static BooleanSpliterator wrap(boolean[] zArr, int i, int i2, int i3) {
        BooleanArrays.ensureOffsetLength(zArr, i, i2);
        return new ArraySpliterator(zArr, i, i2, i3);
    }

    public static abstract class AbstractIndexBasedSpliterator extends AbstractBooleanSpliterator {
        protected int pos;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16720;
        }

        protected abstract boolean get(int i);

        protected abstract int getMaxPos();

        protected abstract BooleanSpliterator makeForSplit(int i, int i2);

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

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(BooleanConsumer booleanConsumer) {
            if (this.pos >= getMaxPos()) {
                return false;
            }
            int i = this.pos;
            this.pos = i + 1;
            booleanConsumer.accept(get(i));
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(BooleanConsumer booleanConsumer) {
            int maxPos = getMaxPos();
            while (true) {
                int i = this.pos;
                if (i >= maxPos) {
                    return;
                }
                booleanConsumer.accept(get(i));
                this.pos++;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public BooleanSpliterator trySplit() {
            int maxPos = getMaxPos();
            int iComputeSplitPoint = computeSplitPoint();
            if (iComputeSplitPoint == this.pos || iComputeSplitPoint == maxPos) {
                return null;
            }
            splitPointCheck(iComputeSplitPoint, maxPos);
            BooleanSpliterator booleanSpliteratorMakeForSplit = makeForSplit(this.pos, iComputeSplitPoint);
            if (booleanSpliteratorMakeForSplit != null) {
                this.pos = iComputeSplitPoint;
            }
            return booleanSpliteratorMakeForSplit;
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanSpliterators.AbstractIndexBasedSpliterator
        protected final int getMaxPos() {
            return this.maxPosFixed ? this.maxPos : getMaxPosFromBackingStore();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanSpliterators.AbstractIndexBasedSpliterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public BooleanSpliterator trySplit() {
            BooleanSpliterator booleanSpliteratorTrySplit = super.trySplit();
            if (!this.maxPosFixed && booleanSpliteratorTrySplit != null) {
                this.maxPos = getMaxPosFromBackingStore();
                this.maxPosFixed = true;
            }
            return booleanSpliteratorTrySplit;
        }
    }

    private static class SpliteratorFromIterator implements BooleanSpliterator {
        final int characteristics;
        private BooleanSpliterator delegate;
        private final BooleanIterator iter;
        private final boolean knownSize;
        private int nextBatchSize;
        private long size;

        SpliteratorFromIterator(BooleanIterator booleanIterator, int i) {
            this.size = Long.MAX_VALUE;
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = booleanIterator;
            this.characteristics = i | 256;
            this.knownSize = false;
        }

        SpliteratorFromIterator(BooleanIterator booleanIterator, long j, int i) {
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = booleanIterator;
            this.knownSize = true;
            this.size = j;
            if ((i & 4096) != 0) {
                this.characteristics = i | 256;
            } else {
                this.characteristics = i | 16704;
            }
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(BooleanConsumer booleanConsumer) {
            BooleanSpliterator booleanSpliterator = this.delegate;
            if (booleanSpliterator != null) {
                boolean zTryAdvance = booleanSpliterator.tryAdvance(booleanConsumer);
                if (!zTryAdvance) {
                    this.delegate = null;
                }
                return zTryAdvance;
            }
            if (!this.iter.hasNext()) {
                return false;
            }
            this.size--;
            booleanConsumer.accept(this.iter.nextBoolean());
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(BooleanConsumer booleanConsumer) {
            BooleanSpliterator booleanSpliterator = this.delegate;
            if (booleanSpliterator != null) {
                booleanSpliterator.forEachRemaining(booleanConsumer);
                this.delegate = null;
            }
            this.iter.forEachRemaining(booleanConsumer);
            this.size = 0L;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            BooleanSpliterator booleanSpliterator = this.delegate;
            if (booleanSpliterator != null) {
                return booleanSpliterator.estimateSize();
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

        protected BooleanSpliterator makeForSplit(boolean[] zArr, int i) {
            return BooleanSpliterators.wrap(zArr, 0, i, this.characteristics);
        }

        /* JADX WARN: Code duplicated, block: B:11:0x001f  */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public BooleanSpliterator trySplit() {
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
            boolean[] zArrCopyOf = new boolean[iMin];
            int i = 0;
            while (i < iMin && this.iter.hasNext()) {
                zArrCopyOf[i] = this.iter.nextBoolean();
                this.size--;
                i++;
            }
            if (iMin < this.nextBatchSize && this.iter.hasNext()) {
                zArrCopyOf = Arrays.copyOf(zArrCopyOf, this.nextBatchSize);
                while (this.iter.hasNext() && i < this.nextBatchSize) {
                    zArrCopyOf[i] = this.iter.nextBoolean();
                    this.size--;
                    i++;
                }
            }
            this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
            BooleanSpliterator booleanSpliteratorMakeForSplit = makeForSplit(zArrCopyOf, i);
            if (this.iter.hasNext()) {
                return booleanSpliteratorMakeForSplit;
            }
            this.delegate = booleanSpliteratorMakeForSplit;
            return booleanSpliteratorMakeForSplit.trySplit();
        }
    }

    public static BooleanSpliterator asSpliterator(BooleanIterator booleanIterator, long j, int i) {
        return new SpliteratorFromIterator(booleanIterator, j, i);
    }

    public static BooleanSpliterator asSpliteratorUnknownSize(BooleanIterator booleanIterator, int i) {
        return new SpliteratorFromIterator(booleanIterator, i);
    }
}
