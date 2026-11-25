package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public final class FloatIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    public static class EmptyIterator implements FloatListIterator, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterator, java.util.Iterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Float> consumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterator, java.util.PrimitiveIterator
        public void forEachRemaining(FloatConsumer floatConsumer) {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterator
        public float nextFloat() {
            throw new NoSuchElementException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator
        public float previousFloat() {
            throw new NoSuchElementException();
        }

        public Object clone() {
            return FloatIterators.EMPTY_ITERATOR;
        }

        private Object readResolve() {
            return FloatIterators.EMPTY_ITERATOR;
        }
    }

    public static int unwrap(FloatIterator floatIterator, float[] fArr, int i, int i2) {
        int i3;
        if (i2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + i2 + ") is negative");
        }
        if (i < 0 || i + i2 > fArr.length) {
            throw new IllegalArgumentException();
        }
        int i4 = i2;
        while (true) {
            i3 = i4 - 1;
            if (i4 == 0 || !floatIterator.hasNext()) {
                break;
            }
            fArr[i] = floatIterator.nextFloat();
            i++;
            i4 = i3;
        }
        return (i2 - i3) - 1;
    }

    public static int unwrap(FloatIterator floatIterator, float[] fArr) {
        return unwrap(floatIterator, fArr, 0, fArr.length);
    }

    public static abstract class AbstractIndexBasedIterator extends AbstractFloatIterator {
        protected int lastReturned;
        protected final int minPos;
        protected int pos;

        protected abstract float get(int i);

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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterator
        public float nextFloat() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i = this.pos;
            this.pos = i + 1;
            this.lastReturned = i;
            return get(i);
        }

        @Override // java.util.Iterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatListIterator, java.util.ListIterator
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterator, java.util.PrimitiveIterator
        public void forEachRemaining(FloatConsumer floatConsumer) {
            while (this.pos < getMaxPos()) {
                int i = this.pos;
                this.pos = i + 1;
                this.lastReturned = i;
                floatConsumer.accept(get(i));
            }
        }
    }

    public static abstract class AbstractIndexBasedListIterator extends AbstractIndexBasedIterator implements FloatListIterator {
        protected abstract void add(int i, float f);

        protected abstract void set(int i, float f);

        protected AbstractIndexBasedListIterator(int i, int i2) {
            super(i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
        public boolean hasPrevious() {
            return this.pos > this.minPos;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator
        public float previousFloat() {
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

        public void add(float f) {
            int i = this.pos;
            this.pos = i + 1;
            add(i, f);
            this.lastReturned = -1;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatListIterator
        public void set(float f) {
            if (this.lastReturned == -1) {
                throw new IllegalStateException();
            }
            set(this.lastReturned, f);
        }
    }
}
