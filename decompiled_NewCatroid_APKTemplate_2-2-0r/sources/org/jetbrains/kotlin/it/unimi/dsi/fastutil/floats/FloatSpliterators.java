package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public final class FloatSpliterators {
    public static final EmptySpliterator EMPTY_SPLITERATOR = new EmptySpliterator();

    public static class EmptySpliterator implements FloatSpliterator, Serializable, Cloneable {
        private static final long serialVersionUID = 8379247926738230492L;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16448;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return 0L;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatSpliterator, java.util.Spliterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Float> consumer) {
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(FloatConsumer floatConsumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatSpliterator, java.util.Spliterator
        @Deprecated
        public boolean tryAdvance(Consumer<? super Float> consumer) {
            return false;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(FloatConsumer floatConsumer) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public FloatSpliterator trySplit() {
            return null;
        }

        protected EmptySpliterator() {
        }

        public Object clone() {
            return FloatSpliterators.EMPTY_SPLITERATOR;
        }

        private Object readResolve() {
            return FloatSpliterators.EMPTY_SPLITERATOR;
        }
    }

    private static class ArraySpliterator implements FloatSpliterator {
        final float[] array;
        final int characteristics;
        private int curr;
        private int length;
        private final int offset;

        public ArraySpliterator(float[] fArr, int i, int i2, int i3) {
            this.array = fArr;
            this.offset = i;
            this.length = i2;
            this.characteristics = i3 | 16720;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(FloatConsumer floatConsumer) {
            if (this.curr >= this.length) {
                return false;
            }
            Objects.requireNonNull(floatConsumer);
            float[] fArr = this.array;
            int i = this.offset;
            int i2 = this.curr;
            this.curr = i2 + 1;
            floatConsumer.accept(fArr[i + i2]);
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public FloatSpliterator trySplit() {
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
        public void forEachRemaining(FloatConsumer floatConsumer) {
            Objects.requireNonNull(floatConsumer);
            while (true) {
                int i = this.curr;
                if (i >= this.length) {
                    return;
                }
                floatConsumer.accept(this.array[this.offset + i]);
                this.curr++;
            }
        }
    }

    public static FloatSpliterator wrap(float[] fArr, int i, int i2, int i3) {
        FloatArrays.ensureOffsetLength(fArr, i, i2);
        return new ArraySpliterator(fArr, i, i2, i3);
    }

    public static abstract class AbstractIndexBasedSpliterator extends AbstractFloatSpliterator {
        protected int pos;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16720;
        }

        protected abstract float get(int i);

        protected abstract int getMaxPos();

        protected abstract FloatSpliterator makeForSplit(int i, int i2);

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
        public boolean tryAdvance(FloatConsumer floatConsumer) {
            if (this.pos >= getMaxPos()) {
                return false;
            }
            int i = this.pos;
            this.pos = i + 1;
            floatConsumer.accept(get(i));
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(FloatConsumer floatConsumer) {
            int maxPos = getMaxPos();
            while (true) {
                int i = this.pos;
                if (i >= maxPos) {
                    return;
                }
                floatConsumer.accept(get(i));
                this.pos++;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public FloatSpliterator trySplit() {
            int maxPos = getMaxPos();
            int iComputeSplitPoint = computeSplitPoint();
            if (iComputeSplitPoint == this.pos || iComputeSplitPoint == maxPos) {
                return null;
            }
            splitPointCheck(iComputeSplitPoint, maxPos);
            FloatSpliterator floatSpliteratorMakeForSplit = makeForSplit(this.pos, iComputeSplitPoint);
            if (floatSpliteratorMakeForSplit != null) {
                this.pos = iComputeSplitPoint;
            }
            return floatSpliteratorMakeForSplit;
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatSpliterators.AbstractIndexBasedSpliterator
        protected final int getMaxPos() {
            return this.maxPosFixed ? this.maxPos : getMaxPosFromBackingStore();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatSpliterators.AbstractIndexBasedSpliterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public FloatSpliterator trySplit() {
            FloatSpliterator floatSpliteratorTrySplit = super.trySplit();
            if (!this.maxPosFixed && floatSpliteratorTrySplit != null) {
                this.maxPos = getMaxPosFromBackingStore();
                this.maxPosFixed = true;
            }
            return floatSpliteratorTrySplit;
        }
    }

    private static class SpliteratorFromIterator implements FloatSpliterator {
        final int characteristics;
        private FloatSpliterator delegate;
        private final FloatIterator iter;
        private final boolean knownSize;
        private int nextBatchSize;
        private long size;

        SpliteratorFromIterator(FloatIterator floatIterator, int i) {
            this.size = Long.MAX_VALUE;
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = floatIterator;
            this.characteristics = i | 256;
            this.knownSize = false;
        }

        SpliteratorFromIterator(FloatIterator floatIterator, long j, int i) {
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = floatIterator;
            this.knownSize = true;
            this.size = j;
            if ((i & 4096) != 0) {
                this.characteristics = i | 256;
            } else {
                this.characteristics = i | 16704;
            }
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(FloatConsumer floatConsumer) {
            FloatSpliterator floatSpliterator = this.delegate;
            if (floatSpliterator != null) {
                boolean zTryAdvance = floatSpliterator.tryAdvance(floatConsumer);
                if (!zTryAdvance) {
                    this.delegate = null;
                }
                return zTryAdvance;
            }
            if (!this.iter.hasNext()) {
                return false;
            }
            this.size--;
            floatConsumer.accept(this.iter.nextFloat());
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(FloatConsumer floatConsumer) {
            FloatSpliterator floatSpliterator = this.delegate;
            if (floatSpliterator != null) {
                floatSpliterator.forEachRemaining(floatConsumer);
                this.delegate = null;
            }
            this.iter.forEachRemaining(floatConsumer);
            this.size = 0L;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            FloatSpliterator floatSpliterator = this.delegate;
            if (floatSpliterator != null) {
                return floatSpliterator.estimateSize();
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

        protected FloatSpliterator makeForSplit(float[] fArr, int i) {
            return FloatSpliterators.wrap(fArr, 0, i, this.characteristics);
        }

        /* JADX WARN: Code duplicated, block: B:11:0x001f  */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public FloatSpliterator trySplit() {
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
            float[] fArrCopyOf = new float[iMin];
            int i = 0;
            while (i < iMin && this.iter.hasNext()) {
                fArrCopyOf[i] = this.iter.nextFloat();
                this.size--;
                i++;
            }
            if (iMin < this.nextBatchSize && this.iter.hasNext()) {
                fArrCopyOf = Arrays.copyOf(fArrCopyOf, this.nextBatchSize);
                while (this.iter.hasNext() && i < this.nextBatchSize) {
                    fArrCopyOf[i] = this.iter.nextFloat();
                    this.size--;
                    i++;
                }
            }
            this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
            FloatSpliterator floatSpliteratorMakeForSplit = makeForSplit(fArrCopyOf, i);
            if (this.iter.hasNext()) {
                return floatSpliteratorMakeForSplit;
            }
            this.delegate = floatSpliteratorMakeForSplit;
            return floatSpliteratorMakeForSplit.trySplit();
        }
    }

    public static FloatSpliterator asSpliterator(FloatIterator floatIterator, long j, int i) {
        return new SpliteratorFromIterator(floatIterator, j, i);
    }

    public static FloatSpliterator asSpliteratorUnknownSize(FloatIterator floatIterator, int i) {
        return new SpliteratorFromIterator(floatIterator, i);
    }
}
