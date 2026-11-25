package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public final class ShortSpliterators {
    public static final EmptySpliterator EMPTY_SPLITERATOR = new EmptySpliterator();

    public static class EmptySpliterator implements ShortSpliterator, Serializable, Cloneable {
        private static final long serialVersionUID = 8379247926738230492L;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16448;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return 0L;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSpliterator, java.util.Spliterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Short> consumer) {
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(ShortConsumer shortConsumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSpliterator, java.util.Spliterator
        @Deprecated
        public boolean tryAdvance(Consumer<? super Short> consumer) {
            return false;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(ShortConsumer shortConsumer) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public ShortSpliterator trySplit() {
            return null;
        }

        protected EmptySpliterator() {
        }

        public Object clone() {
            return ShortSpliterators.EMPTY_SPLITERATOR;
        }

        private Object readResolve() {
            return ShortSpliterators.EMPTY_SPLITERATOR;
        }
    }

    private static class ArraySpliterator implements ShortSpliterator {
        final short[] array;
        final int characteristics;
        private int curr;
        private int length;
        private final int offset;

        public ArraySpliterator(short[] sArr, int i, int i2, int i3) {
            this.array = sArr;
            this.offset = i;
            this.length = i2;
            this.characteristics = i3 | 16720;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(ShortConsumer shortConsumer) {
            if (this.curr >= this.length) {
                return false;
            }
            Objects.requireNonNull(shortConsumer);
            short[] sArr = this.array;
            int i = this.offset;
            int i2 = this.curr;
            this.curr = i2 + 1;
            shortConsumer.accept(sArr[i + i2]);
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public ShortSpliterator trySplit() {
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
        public void forEachRemaining(ShortConsumer shortConsumer) {
            Objects.requireNonNull(shortConsumer);
            while (true) {
                int i = this.curr;
                if (i >= this.length) {
                    return;
                }
                shortConsumer.accept(this.array[this.offset + i]);
                this.curr++;
            }
        }
    }

    public static ShortSpliterator wrap(short[] sArr, int i, int i2, int i3) {
        ShortArrays.ensureOffsetLength(sArr, i, i2);
        return new ArraySpliterator(sArr, i, i2, i3);
    }

    public static abstract class AbstractIndexBasedSpliterator extends AbstractShortSpliterator {
        protected int pos;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16720;
        }

        protected abstract short get(int i);

        protected abstract int getMaxPos();

        protected abstract ShortSpliterator makeForSplit(int i, int i2);

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
        public boolean tryAdvance(ShortConsumer shortConsumer) {
            if (this.pos >= getMaxPos()) {
                return false;
            }
            int i = this.pos;
            this.pos = i + 1;
            shortConsumer.accept(get(i));
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(ShortConsumer shortConsumer) {
            int maxPos = getMaxPos();
            while (true) {
                int i = this.pos;
                if (i >= maxPos) {
                    return;
                }
                shortConsumer.accept(get(i));
                this.pos++;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public ShortSpliterator trySplit() {
            int maxPos = getMaxPos();
            int iComputeSplitPoint = computeSplitPoint();
            if (iComputeSplitPoint == this.pos || iComputeSplitPoint == maxPos) {
                return null;
            }
            splitPointCheck(iComputeSplitPoint, maxPos);
            ShortSpliterator shortSpliteratorMakeForSplit = makeForSplit(this.pos, iComputeSplitPoint);
            if (shortSpliteratorMakeForSplit != null) {
                this.pos = iComputeSplitPoint;
            }
            return shortSpliteratorMakeForSplit;
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSpliterators.AbstractIndexBasedSpliterator
        protected final int getMaxPos() {
            return this.maxPosFixed ? this.maxPos : getMaxPosFromBackingStore();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSpliterators.AbstractIndexBasedSpliterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public ShortSpliterator trySplit() {
            ShortSpliterator shortSpliteratorTrySplit = super.trySplit();
            if (!this.maxPosFixed && shortSpliteratorTrySplit != null) {
                this.maxPos = getMaxPosFromBackingStore();
                this.maxPosFixed = true;
            }
            return shortSpliteratorTrySplit;
        }
    }

    private static class SpliteratorFromIterator implements ShortSpliterator {
        final int characteristics;
        private ShortSpliterator delegate;
        private final ShortIterator iter;
        private final boolean knownSize;
        private int nextBatchSize;
        private long size;

        SpliteratorFromIterator(ShortIterator shortIterator, int i) {
            this.size = Long.MAX_VALUE;
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = shortIterator;
            this.characteristics = i | 256;
            this.knownSize = false;
        }

        SpliteratorFromIterator(ShortIterator shortIterator, long j, int i) {
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = shortIterator;
            this.knownSize = true;
            this.size = j;
            if ((i & 4096) != 0) {
                this.characteristics = i | 256;
            } else {
                this.characteristics = i | 16704;
            }
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(ShortConsumer shortConsumer) {
            ShortSpliterator shortSpliterator = this.delegate;
            if (shortSpliterator != null) {
                boolean zTryAdvance = shortSpliterator.tryAdvance(shortConsumer);
                if (!zTryAdvance) {
                    this.delegate = null;
                }
                return zTryAdvance;
            }
            if (!this.iter.hasNext()) {
                return false;
            }
            this.size--;
            shortConsumer.accept(this.iter.nextShort());
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(ShortConsumer shortConsumer) {
            ShortSpliterator shortSpliterator = this.delegate;
            if (shortSpliterator != null) {
                shortSpliterator.forEachRemaining(shortConsumer);
                this.delegate = null;
            }
            this.iter.forEachRemaining(shortConsumer);
            this.size = 0L;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            ShortSpliterator shortSpliterator = this.delegate;
            if (shortSpliterator != null) {
                return shortSpliterator.estimateSize();
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

        protected ShortSpliterator makeForSplit(short[] sArr, int i) {
            return ShortSpliterators.wrap(sArr, 0, i, this.characteristics);
        }

        /* JADX WARN: Code duplicated, block: B:11:0x001f  */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public ShortSpliterator trySplit() {
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
            short[] sArrCopyOf = new short[iMin];
            int i = 0;
            while (i < iMin && this.iter.hasNext()) {
                sArrCopyOf[i] = this.iter.nextShort();
                this.size--;
                i++;
            }
            if (iMin < this.nextBatchSize && this.iter.hasNext()) {
                sArrCopyOf = Arrays.copyOf(sArrCopyOf, this.nextBatchSize);
                while (this.iter.hasNext() && i < this.nextBatchSize) {
                    sArrCopyOf[i] = this.iter.nextShort();
                    this.size--;
                    i++;
                }
            }
            this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
            ShortSpliterator shortSpliteratorMakeForSplit = makeForSplit(sArrCopyOf, i);
            if (this.iter.hasNext()) {
                return shortSpliteratorMakeForSplit;
            }
            this.delegate = shortSpliteratorMakeForSplit;
            return shortSpliteratorMakeForSplit.trySplit();
        }
    }

    public static ShortSpliterator asSpliterator(ShortIterator shortIterator, long j, int i) {
        return new SpliteratorFromIterator(shortIterator, j, i);
    }

    public static ShortSpliterator asSpliteratorUnknownSize(ShortIterator shortIterator, int i) {
        return new SpliteratorFromIterator(shortIterator, i);
    }
}
