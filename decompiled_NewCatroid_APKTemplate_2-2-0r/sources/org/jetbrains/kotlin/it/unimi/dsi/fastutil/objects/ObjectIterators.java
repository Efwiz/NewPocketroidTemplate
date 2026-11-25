package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public final class ObjectIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    public static class EmptyIterator<K> implements ObjectListIterator<K>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super K> consumer) {
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

        @Override // java.util.Iterator, java.util.ListIterator
        public K next() {
            throw new NoSuchElementException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
        public K previous() {
            throw new NoSuchElementException();
        }

        public Object clone() {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        private Object readResolve() {
            return ObjectIterators.EMPTY_ITERATOR;
        }
    }

    public static abstract class AbstractIndexBasedIterator<K> extends AbstractObjectIterator<K> {
        protected int lastReturned;
        protected final int minPos;
        protected int pos;

        protected abstract K get(int i);

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

        @Override // java.util.Iterator, java.util.ListIterator
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i = this.pos;
            this.pos = i + 1;
            this.lastReturned = i;
            return get(i);
        }

        @Override // java.util.Iterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectListIterator, java.util.ListIterator
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

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super K> consumer) {
            while (this.pos < getMaxPos()) {
                int i = this.pos;
                this.pos = i + 1;
                this.lastReturned = i;
                consumer.accept(get(i));
            }
        }
    }

    public static abstract class AbstractIndexBasedListIterator<K> extends AbstractIndexBasedIterator<K> implements ObjectListIterator<K> {
        protected abstract void add(int i, K k);

        protected abstract void set(int i, K k);

        protected AbstractIndexBasedListIterator(int i, int i2) {
            super(i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
        public boolean hasPrevious() {
            return this.pos > this.minPos;
        }

        public K previous() {
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

        public void add(K k) {
            int i = this.pos;
            this.pos = i + 1;
            add(i, k);
            this.lastReturned = -1;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public void set(K k) {
            if (this.lastReturned == -1) {
                throw new IllegalStateException();
            }
            set(this.lastReturned, k);
        }
    }

    public static class UnmodifiableIterator<K> implements ObjectIterator<K> {
        protected final ObjectIterator<? extends K> i;

        public UnmodifiableIterator(ObjectIterator<? extends K> objectIterator) {
            this.i = objectIterator;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override // java.util.Iterator
        public K next() {
            return this.i.next();
        }

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super K> consumer) {
            this.i.forEachRemaining(consumer);
        }
    }

    public static <K> ObjectIterator<K> unmodifiable(ObjectIterator<? extends K> objectIterator) {
        return new UnmodifiableIterator(objectIterator);
    }
}
