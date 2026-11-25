package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public final class DoubleIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    public static class EmptyIterator implements DoubleListIterator, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterator, java.util.PrimitiveIterator.OfDouble, java.util.Iterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Double> consumer) {
        }

        @Override // java.util.PrimitiveIterator
        public void forEachRemaining(java.util.function.DoubleConsumer doubleConsumer) {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterator, java.util.PrimitiveIterator.OfDouble
        public double nextDouble() {
            throw new NoSuchElementException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator
        public double previousDouble() {
            throw new NoSuchElementException();
        }

        public Object clone() {
            return DoubleIterators.EMPTY_ITERATOR;
        }

        private Object readResolve() {
            return DoubleIterators.EMPTY_ITERATOR;
        }
    }

    public static int unwrap(DoubleIterator doubleIterator, double[] dArr, int i, int i2) {
        int i3;
        if (i2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + i2 + ") is negative");
        }
        if (i < 0 || i + i2 > dArr.length) {
            throw new IllegalArgumentException();
        }
        int i4 = i2;
        while (true) {
            i3 = i4 - 1;
            if (i4 == 0 || !doubleIterator.hasNext()) {
                break;
            }
            dArr[i] = doubleIterator.nextDouble();
            i++;
            i4 = i3;
        }
        return (i2 - i3) - 1;
    }

    public static int unwrap(DoubleIterator doubleIterator, double[] dArr) {
        return unwrap(doubleIterator, dArr, 0, dArr.length);
    }

    public static abstract class AbstractIndexBasedIterator extends AbstractDoubleIterator {
        protected int lastReturned;
        protected final int minPos;
        protected int pos;

        protected abstract double get(int i);

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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterator, java.util.PrimitiveIterator.OfDouble
        public double nextDouble() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i = this.pos;
            this.pos = i + 1;
            this.lastReturned = i;
            return get(i);
        }

        @Override // java.util.Iterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleListIterator, java.util.ListIterator
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
        public void forEachRemaining(java.util.function.DoubleConsumer doubleConsumer) {
            while (this.pos < getMaxPos()) {
                int i = this.pos;
                this.pos = i + 1;
                this.lastReturned = i;
                doubleConsumer.accept(get(i));
            }
        }
    }

    public static abstract class AbstractIndexBasedListIterator extends AbstractIndexBasedIterator implements DoubleListIterator {
        protected abstract void add(int i, double d);

        protected abstract void set(int i, double d);

        protected AbstractIndexBasedListIterator(int i, int i2) {
            super(i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
        public boolean hasPrevious() {
            return this.pos > this.minPos;
        }

        public double previousDouble() {
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

        public void add(double d) {
            int i = this.pos;
            this.pos = i + 1;
            add(i, d);
            this.lastReturned = -1;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleListIterator
        public void set(double d) {
            if (this.lastReturned == -1) {
                throw new IllegalStateException();
            }
            set(this.lastReturned, d);
        }
    }

    public static class UnmodifiableIterator implements DoubleIterator {
        protected final DoubleIterator i;

        public UnmodifiableIterator(DoubleIterator doubleIterator) {
            this.i = doubleIterator;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterator, java.util.PrimitiveIterator.OfDouble
        public double nextDouble() {
            return this.i.nextDouble();
        }

        @Override // java.util.PrimitiveIterator
        public void forEachRemaining(java.util.function.DoubleConsumer doubleConsumer) {
            this.i.forEachRemaining(doubleConsumer);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterator, java.util.PrimitiveIterator.OfDouble, java.util.Iterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Double> consumer) {
            this.i.forEachRemaining(consumer);
        }
    }

    public static DoubleIterator unmodifiable(DoubleIterator doubleIterator) {
        return new UnmodifiableIterator(doubleIterator);
    }
}
