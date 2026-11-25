package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public final class IntIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    public static class EmptyIterator implements IntListIterator, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt, java.util.Iterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Integer> consumer) {
        }

        @Override // java.util.PrimitiveIterator
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            throw new NoSuchElementException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntBidirectionalIterator
        public int previousInt() {
            throw new NoSuchElementException();
        }

        public Object clone() {
            return IntIterators.EMPTY_ITERATOR;
        }

        private Object readResolve() {
            return IntIterators.EMPTY_ITERATOR;
        }
    }

    public static int unwrap(IntIterator intIterator, int[] iArr, int i, int i2) {
        int i3;
        if (i2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + i2 + ") is negative");
        }
        if (i < 0 || i + i2 > iArr.length) {
            throw new IllegalArgumentException();
        }
        int i4 = i2;
        while (true) {
            i3 = i4 - 1;
            if (i4 == 0 || !intIterator.hasNext()) {
                break;
            }
            iArr[i] = intIterator.nextInt();
            i++;
            i4 = i3;
        }
        return (i2 - i3) - 1;
    }

    public static int unwrap(IntIterator intIterator, int[] iArr) {
        return unwrap(intIterator, iArr, 0, iArr.length);
    }

    public static abstract class AbstractIndexBasedIterator extends AbstractIntIterator {
        protected int lastReturned;
        protected final int minPos;
        protected int pos;

        protected abstract int get(int i);

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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i = this.pos;
            this.pos = i + 1;
            this.lastReturned = i;
            return get(i);
        }

        @Override // java.util.Iterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntListIterator, java.util.ListIterator
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
        @Override // java.util.PrimitiveIterator
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            while (this.pos < getMaxPos()) {
                int i = this.pos;
                this.pos = i + 1;
                this.lastReturned = i;
                intConsumer.accept(get(i));
            }
        }
    }

    public static abstract class AbstractIndexBasedListIterator extends AbstractIndexBasedIterator implements IntListIterator {
        protected abstract void add(int i, int i2);

        protected abstract void set(int i, int i2);

        protected AbstractIndexBasedListIterator(int i, int i2) {
            super(i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
        public boolean hasPrevious() {
            return this.pos > this.minPos;
        }

        public int previousInt() {
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

        public void add(int i) {
            int i2 = this.pos;
            this.pos = i2 + 1;
            add(i2, i);
            this.lastReturned = -1;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntListIterator
        public void set(int i) {
            if (this.lastReturned == -1) {
                throw new IllegalStateException();
            }
            set(this.lastReturned, i);
        }
    }

    public static class UnmodifiableIterator implements IntIterator {
        protected final IntIterator i;

        public UnmodifiableIterator(IntIterator intIterator) {
            this.i = intIterator;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return this.i.nextInt();
        }

        @Override // java.util.PrimitiveIterator
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            this.i.forEachRemaining(intConsumer);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt, java.util.Iterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Integer> consumer) {
            this.i.forEachRemaining(consumer);
        }
    }

    public static IntIterator unmodifiable(IntIterator intIterator) {
        return new UnmodifiableIterator(intIterator);
    }
}
