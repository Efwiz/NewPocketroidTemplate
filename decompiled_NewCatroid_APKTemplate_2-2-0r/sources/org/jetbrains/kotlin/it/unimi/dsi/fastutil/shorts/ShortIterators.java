package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public final class ShortIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    public static class EmptyIterator implements ShortListIterator, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // java.util.Iterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Short> consumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterator, java.util.PrimitiveIterator
        public void forEachRemaining(ShortConsumer shortConsumer) {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterator
        public short nextShort() {
            throw new NoSuchElementException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator
        public short previousShort() {
            throw new NoSuchElementException();
        }

        public Object clone() {
            return ShortIterators.EMPTY_ITERATOR;
        }

        private Object readResolve() {
            return ShortIterators.EMPTY_ITERATOR;
        }
    }

    public static int unwrap(ShortIterator shortIterator, short[] sArr, int i, int i2) {
        int i3;
        if (i2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + i2 + ") is negative");
        }
        if (i < 0 || i + i2 > sArr.length) {
            throw new IllegalArgumentException();
        }
        int i4 = i2;
        while (true) {
            i3 = i4 - 1;
            if (i4 == 0 || !shortIterator.hasNext()) {
                break;
            }
            sArr[i] = shortIterator.nextShort();
            i++;
            i4 = i3;
        }
        return (i2 - i3) - 1;
    }

    public static int unwrap(ShortIterator shortIterator, short[] sArr) {
        return unwrap(shortIterator, sArr, 0, sArr.length);
    }

    public static abstract class AbstractIndexBasedIterator extends AbstractShortIterator {
        protected int lastReturned;
        protected final int minPos;
        protected int pos;

        protected abstract short get(int i);

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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterator
        public short nextShort() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i = this.pos;
            this.pos = i + 1;
            this.lastReturned = i;
            return get(i);
        }

        @Override // java.util.Iterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortListIterator, java.util.ListIterator
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterator, java.util.PrimitiveIterator
        public void forEachRemaining(ShortConsumer shortConsumer) {
            while (this.pos < getMaxPos()) {
                int i = this.pos;
                this.pos = i + 1;
                this.lastReturned = i;
                shortConsumer.accept(get(i));
            }
        }
    }

    public static abstract class AbstractIndexBasedListIterator extends AbstractIndexBasedIterator implements ShortListIterator {
        protected abstract void add(int i, short s);

        protected abstract void set(int i, short s);

        protected AbstractIndexBasedListIterator(int i, int i2) {
            super(i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
        public boolean hasPrevious() {
            return this.pos > this.minPos;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator
        public short previousShort() {
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

        public void add(short s) {
            int i = this.pos;
            this.pos = i + 1;
            add(i, s);
            this.lastReturned = -1;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortListIterator
        public void set(short s) {
            if (this.lastReturned == -1) {
                throw new IllegalStateException();
            }
            set(this.lastReturned, s);
        }
    }

    public static class UnmodifiableIterator implements ShortIterator {
        protected final ShortIterator i;

        public UnmodifiableIterator(ShortIterator shortIterator) {
            this.i = shortIterator;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterator
        public short nextShort() {
            return this.i.nextShort();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterator, java.util.PrimitiveIterator
        public void forEachRemaining(ShortConsumer shortConsumer) {
            this.i.forEachRemaining(shortConsumer);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterator, java.util.Iterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Short> consumer) {
            this.i.forEachRemaining(consumer);
        }
    }

    public static ShortIterator unmodifiable(ShortIterator shortIterator) {
        return new UnmodifiableIterator(shortIterator);
    }
}
