package com.hivemq.client.internal.util.collections;

import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes10.dex */
public class ChunkedArrayQueue<E> implements Iterable<E> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final int chunkSize;
    private Object[] consumerChunk;
    private int consumerIndex;
    private Object[] producerChunk;
    private int producerIndex;
    private E single;
    private int size;

    public ChunkedArrayQueue(int i) {
        this.chunkSize = i;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void offer(E e) {
        int i = this.size;
        if (i == 0) {
            this.size = 1;
            this.single = e;
            return;
        }
        if (i == 1) {
            if (this.producerChunk == null) {
                Object[] objArr = new Object[this.chunkSize];
                this.consumerChunk = objArr;
                this.producerChunk = objArr;
            }
            E e2 = this.single;
            if (e2 != null) {
                this.size = 0;
                this.single = null;
                offerQueue(e2);
            }
        }
        offerQueue(e);
    }

    private void offerQueue(E e) {
        Object[] objArr = this.producerChunk;
        int i = this.producerIndex;
        int i2 = this.chunkSize;
        if (i == i2 || (objArr == this.consumerChunk && objArr[i] != null)) {
            if (this.size >= i2) {
                Object[] objArr2 = new Object[i2];
                int i3 = i - 1;
                Object obj = objArr[i3];
                objArr[i3] = objArr2;
                objArr2[0] = obj;
                this.producerChunk = objArr2;
                objArr = objArr2;
                i = 1;
            } else {
                i = 0;
            }
        }
        objArr[i] = e;
        this.producerIndex = i + 1;
        this.size++;
    }

    public E poll() {
        E e = this.single;
        int i = 0;
        if (e != null) {
            this.size = 0;
            this.single = null;
            return e;
        }
        Object[] objArr = this.consumerChunk;
        if (objArr == null) {
            return null;
        }
        int i2 = this.consumerIndex;
        Object obj = (E) objArr[i2];
        if (obj == null) {
            return null;
        }
        objArr[i2] = null;
        if (obj.getClass() == Object[].class) {
            Object[] objArr2 = (Object[]) obj;
            this.consumerChunk = objArr2;
            Object obj2 = objArr2[0];
            objArr2[0] = null;
            obj = (E) obj2;
            i = 1;
        } else {
            int i3 = i2 + 1;
            if (i3 != this.chunkSize) {
                i = i3;
            }
        }
        this.size--;
        this.consumerIndex = i;
        return (E) obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public E peek() {
        E e;
        E e2 = this.single;
        if (e2 != null) {
            return e2;
        }
        Object[] objArr = this.consumerChunk;
        if (objArr == null || (e = (E) objArr[this.consumerIndex]) == 0) {
            return null;
        }
        return e.getClass() == Object[].class ? (E) ((Object[]) e)[0] : e;
    }

    public void clear() {
        while (poll() != null) {
        }
    }

    @Override // java.lang.Iterable
    public ChunkedArrayQueue<E>.Iterator iterator() {
        return new Iterator();
    }

    public class Iterator implements java.util.Iterator<E> {
        private int iterated;
        private Object[] iteratorChunk;
        private int iteratorIndex;

        Iterator() {
            reset();
        }

        public void reset() {
            this.iteratorChunk = ChunkedArrayQueue.this.consumerChunk;
            this.iteratorIndex = ChunkedArrayQueue.this.consumerIndex;
            this.iterated = 0;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.iterated < ChunkedArrayQueue.this.size;
        }

        @Override // java.util.Iterator
        public E next() {
            E e = (E) ChunkedArrayQueue.this.single;
            if (e != null) {
                if (this.iterated > 0) {
                    throw new NoSuchElementException();
                }
                this.iterated = 1;
                return e;
            }
            Object[] objArr = this.iteratorChunk;
            if (objArr == null) {
                throw new NoSuchElementException();
            }
            Object obj = (E) objArr[this.iteratorIndex];
            if (obj == null) {
                throw new NoSuchElementException();
            }
            if (obj.getClass() == Object[].class) {
                Object[] objArr2 = (Object[]) obj;
                this.iteratorChunk = objArr2;
                this.iteratorIndex = 1;
                obj = (E) objArr2[0];
            } else {
                int i = this.iteratorIndex + 1;
                this.iteratorIndex = i;
                if (i == ChunkedArrayQueue.this.chunkSize) {
                    this.iteratorIndex = 0;
                }
            }
            this.iterated++;
            return (E) obj;
        }

        @Override // java.util.Iterator
        public void remove() {
            for (int i = 0; i < this.iterated; i++) {
                ChunkedArrayQueue.this.poll();
            }
            this.iterated = 0;
        }

        public int getIterated() {
            return this.iterated;
        }
    }
}
