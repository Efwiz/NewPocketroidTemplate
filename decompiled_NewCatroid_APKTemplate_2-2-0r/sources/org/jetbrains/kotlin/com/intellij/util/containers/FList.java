package org.jetbrains.kotlin.com.intellij.util.containers;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;

/* JADX INFO: loaded from: classes3.dex */
public final class FList<E> extends AbstractList<E> {
    private static final FList<?> EMPTY_LIST = new FList<>(null, null, 0);
    private final E myHead;
    private final int mySize;
    private final FList<E> myTail;

    private FList(E e, FList<E> fList, int i) {
        this.myHead = e;
        this.myTail = fList;
        this.mySize = i;
    }

    @Override // java.util.AbstractList, java.util.List
    public E get(int i) {
        if (i < 0 || i >= this.mySize) {
            throw new IndexOutOfBoundsException("index = " + i + ", size = " + this.mySize);
        }
        FList<E> fList = this;
        while (i > 0) {
            fList = fList.myTail;
            i--;
        }
        return fList.myHead;
    }

    public E getHead() {
        return this.myHead;
    }

    public FList<E> prepend(E e) {
        return new FList<>(e, this, this.mySize + 1);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator<E> iterator() {
        return new Iterator<E>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.FList.1
            static final /* synthetic */ boolean $assertionsDisabled = false;
            private FList<E> list;

            {
                this.list = FList.this;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.list.size() > 0;
            }

            @Override // java.util.Iterator
            public E next() {
                if (this.list.size() != 0) {
                    E e = (E) ((FList) this.list).myHead;
                    this.list = this.list.getTail();
                    return e;
                }
                throw new NoSuchElementException();
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public FList<E> getTail() {
        return this.myTail;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.mySize;
    }

    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FList) {
            FList<E> tail = (FList) obj;
            if (this.mySize != tail.mySize) {
                return false;
            }
            FList<E> tail2 = this;
            while (tail2 != null) {
                if (!Comparing.equal(tail2.myHead, tail.myHead)) {
                    return false;
                }
                tail2 = tail2.getTail();
                tail = tail.getTail();
                if (tail2 == tail) {
                    break;
                }
            }
            return true;
        }
        return super.equals(obj);
    }

    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    public int hashCode() {
        int iHashCode = 1;
        for (FList<E> tail = this; tail != null; tail = tail.getTail()) {
            int i = iHashCode * 31;
            E e = tail.myHead;
            iHashCode = i + (e != null ? e.hashCode() : 0);
        }
        return iHashCode;
    }

    public static <E> FList<E> emptyList() {
        return (FList<E>) EMPTY_LIST;
    }
}
