package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public final class ByteSpliterators {
    public static final EmptySpliterator EMPTY_SPLITERATOR = new EmptySpliterator();

    public static class EmptySpliterator implements ByteSpliterator, Serializable, Cloneable {
        private static final long serialVersionUID = 8379247926738230492L;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16448;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return 0L;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterator, java.util.Spliterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Byte> consumer) {
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(ByteConsumer byteConsumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterator, java.util.Spliterator
        @Deprecated
        public boolean tryAdvance(Consumer<? super Byte> consumer) {
            return false;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(ByteConsumer byteConsumer) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public ByteSpliterator trySplit() {
            return null;
        }

        protected EmptySpliterator() {
        }

        public Object clone() {
            return ByteSpliterators.EMPTY_SPLITERATOR;
        }

        private Object readResolve() {
            return ByteSpliterators.EMPTY_SPLITERATOR;
        }
    }

    private static class ArraySpliterator implements ByteSpliterator {
        final byte[] array;
        final int characteristics;
        private int curr;
        private int length;
        private final int offset;

        public ArraySpliterator(byte[] bArr, int i, int i2, int i3) {
            this.array = bArr;
            this.offset = i;
            this.length = i2;
            this.characteristics = i3 | 16720;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(ByteConsumer byteConsumer) {
            if (this.curr >= this.length) {
                return false;
            }
            Objects.requireNonNull(byteConsumer);
            byte[] bArr = this.array;
            int i = this.offset;
            int i2 = this.curr;
            this.curr = i2 + 1;
            byteConsumer.accept(bArr[i + i2]);
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public ByteSpliterator trySplit() {
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
        public void forEachRemaining(ByteConsumer byteConsumer) {
            Objects.requireNonNull(byteConsumer);
            while (true) {
                int i = this.curr;
                if (i >= this.length) {
                    return;
                }
                byteConsumer.accept(this.array[this.offset + i]);
                this.curr++;
            }
        }
    }

    public static ByteSpliterator wrap(byte[] bArr, int i, int i2, int i3) {
        ByteArrays.ensureOffsetLength(bArr, i, i2);
        return new ArraySpliterator(bArr, i, i2, i3);
    }

    public static abstract class AbstractIndexBasedSpliterator extends AbstractByteSpliterator {
        protected int pos;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16720;
        }

        protected abstract byte get(int i);

        protected abstract int getMaxPos();

        protected abstract ByteSpliterator makeForSplit(int i, int i2);

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
        public boolean tryAdvance(ByteConsumer byteConsumer) {
            if (this.pos >= getMaxPos()) {
                return false;
            }
            int i = this.pos;
            this.pos = i + 1;
            byteConsumer.accept(get(i));
            return true;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(ByteConsumer byteConsumer) {
            int maxPos = getMaxPos();
            while (true) {
                int i = this.pos;
                if (i >= maxPos) {
                    return;
                }
                byteConsumer.accept(get(i));
                this.pos++;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public ByteSpliterator trySplit() {
            int maxPos = getMaxPos();
            int iComputeSplitPoint = computeSplitPoint();
            if (iComputeSplitPoint == this.pos || iComputeSplitPoint == maxPos) {
                return null;
            }
            splitPointCheck(iComputeSplitPoint, maxPos);
            ByteSpliterator byteSpliteratorMakeForSplit = makeForSplit(this.pos, iComputeSplitPoint);
            if (byteSpliteratorMakeForSplit != null) {
                this.pos = iComputeSplitPoint;
            }
            return byteSpliteratorMakeForSplit;
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterators.AbstractIndexBasedSpliterator
        protected final int getMaxPos() {
            return this.maxPosFixed ? this.maxPos : getMaxPosFromBackingStore();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterators.AbstractIndexBasedSpliterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public ByteSpliterator trySplit() {
            ByteSpliterator byteSpliteratorTrySplit = super.trySplit();
            if (!this.maxPosFixed && byteSpliteratorTrySplit != null) {
                this.maxPos = getMaxPosFromBackingStore();
                this.maxPosFixed = true;
            }
            return byteSpliteratorTrySplit;
        }
    }

    private static class SpliteratorFromIterator implements ByteSpliterator {
        final int characteristics;
        private ByteSpliterator delegate;
        private final ByteIterator iter;
        private final boolean knownSize;
        private int nextBatchSize;
        private long size;

        SpliteratorFromIterator(ByteIterator byteIterator, int i) {
            this.size = Long.MAX_VALUE;
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = byteIterator;
            this.characteristics = i | 256;
            this.knownSize = false;
        }

        SpliteratorFromIterator(ByteIterator byteIterator, long j, int i) {
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = byteIterator;
            this.knownSize = true;
            this.size = j;
            if ((i & 4096) != 0) {
                this.characteristics = i | 256;
            } else {
                this.characteristics = i | 16704;
            }
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(ByteConsumer byteConsumer) {
            ByteSpliterator byteSpliterator = this.delegate;
            if (byteSpliterator != null) {
                boolean zTryAdvance = byteSpliterator.tryAdvance(byteConsumer);
                if (!zTryAdvance) {
                    this.delegate = null;
                }
                return zTryAdvance;
            }
            if (!this.iter.hasNext()) {
                return false;
            }
            this.size--;
            byteConsumer.accept(this.iter.nextByte());
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(ByteConsumer byteConsumer) {
            ByteSpliterator byteSpliterator = this.delegate;
            if (byteSpliterator != null) {
                byteSpliterator.forEachRemaining(byteConsumer);
                this.delegate = null;
            }
            this.iter.forEachRemaining(byteConsumer);
            this.size = 0L;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            ByteSpliterator byteSpliterator = this.delegate;
            if (byteSpliterator != null) {
                return byteSpliterator.estimateSize();
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

        protected ByteSpliterator makeForSplit(byte[] bArr, int i) {
            return ByteSpliterators.wrap(bArr, 0, i, this.characteristics);
        }

        /* JADX WARN: Code duplicated, block: B:11:0x001f  */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public ByteSpliterator trySplit() {
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
            byte[] bArrCopyOf = new byte[iMin];
            int i = 0;
            while (i < iMin && this.iter.hasNext()) {
                bArrCopyOf[i] = this.iter.nextByte();
                this.size--;
                i++;
            }
            if (iMin < this.nextBatchSize && this.iter.hasNext()) {
                bArrCopyOf = Arrays.copyOf(bArrCopyOf, this.nextBatchSize);
                while (this.iter.hasNext() && i < this.nextBatchSize) {
                    bArrCopyOf[i] = this.iter.nextByte();
                    this.size--;
                    i++;
                }
            }
            this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
            ByteSpliterator byteSpliteratorMakeForSplit = makeForSplit(bArrCopyOf, i);
            if (this.iter.hasNext()) {
                return byteSpliteratorMakeForSplit;
            }
            this.delegate = byteSpliteratorMakeForSplit;
            return byteSpliteratorMakeForSplit.trySplit();
        }
    }

    public static ByteSpliterator asSpliterator(ByteIterator byteIterator, long j, int i) {
        return new SpliteratorFromIterator(byteIterator, j, i);
    }

    public static ByteSpliterator asSpliteratorUnknownSize(ByteIterator byteIterator, int i) {
        return new SpliteratorFromIterator(byteIterator, i);
    }
}
