package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public final class ByteIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    public static class EmptyIterator implements ByteListIterator, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterator, java.util.Iterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Byte> consumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterator, java.util.PrimitiveIterator
        public void forEachRemaining(ByteConsumer byteConsumer) {
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public boolean hasNext() {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
        public boolean hasPrevious() {
            return false;
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return 0;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return -1;
        }

        protected EmptyIterator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterator
        public byte nextByte() {
            throw new NoSuchElementException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator
        public byte previousByte() {
            throw new NoSuchElementException();
        }

        public Object clone() {
            return ByteIterators.EMPTY_ITERATOR;
        }

        private Object readResolve() {
            return ByteIterators.EMPTY_ITERATOR;
        }
    }

    public static int unwrap(ByteIterator byteIterator, byte[] bArr, int i, int i2) {
        int i3;
        if (i2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + i2 + ") is negative");
        }
        if (i < 0 || i + i2 > bArr.length) {
            throw new IllegalArgumentException();
        }
        int i4 = i2;
        while (true) {
            i3 = i4 - 1;
            if (i4 == 0 || !byteIterator.hasNext()) {
                break;
            }
            bArr[i] = byteIterator.nextByte();
            i++;
            i4 = i3;
        }
        return (i2 - i3) - 1;
    }

    public static int unwrap(ByteIterator byteIterator, byte[] bArr) {
        return unwrap(byteIterator, bArr, 0, bArr.length);
    }

    public static abstract class AbstractIndexBasedIterator extends AbstractByteIterator {
        protected int lastReturned;
        protected final int minPos;
        protected int pos;

        protected abstract byte get(int i);

        protected abstract int getMaxPos();

        protected abstract void remove(int i);

        protected AbstractIndexBasedIterator(int i, int i2) {
            this.minPos = i;
            this.pos = i2;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.pos < getMaxPos();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterator
        public byte nextByte() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i = this.pos;
            this.pos = i + 1;
            this.lastReturned = i;
            return get(i);
        }

        @Override // java.util.Iterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteListIterator, java.util.ListIterator
        public void remove() {
            int i = this.lastReturned;
            if (i == -1) {
                throw new IllegalStateException();
            }
            remove(i);
            int i2 = this.lastReturned;
            int i3 = this.pos;
            if (i2 < i3) {
                this.pos = i3 - 1;
            }
            this.lastReturned = -1;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterator, java.util.PrimitiveIterator
        public void forEachRemaining(ByteConsumer byteConsumer) {
            while (this.pos < getMaxPos()) {
                int i = this.pos;
                this.pos = i + 1;
                this.lastReturned = i;
                byteConsumer.accept(get(i));
            }
        }
    }

    public static abstract class AbstractIndexBasedListIterator extends AbstractIndexBasedIterator implements ByteListIterator {
        protected abstract void add(int i, byte b);

        protected abstract void set(int i, byte b);

        protected AbstractIndexBasedListIterator(int i, int i2) {
            super(i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
        public boolean hasPrevious() {
            return this.pos > this.minPos;
        }

        public byte previousByte() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            int i = this.pos - 1;
            this.pos = i;
            this.lastReturned = i;
            return get(i);
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.pos;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.pos - 1;
        }

        public void add(byte b) {
            int i = this.pos;
            this.pos = i + 1;
            add(i, b);
            this.lastReturned = -1;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteListIterator
        public void set(byte b) {
            if (this.lastReturned == -1) {
                throw new IllegalStateException();
            }
            set(this.lastReturned, b);
        }
    }
}
