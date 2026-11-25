package gnu.trove;

import gnu.trove.TLinkable;
import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes11.dex */
public class TLinkedList<T extends TLinkable> extends AbstractSequentialList<T> implements Serializable {
    protected T _head;
    protected int _size;
    protected T _tail;

    @Override // java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
    public ListIterator<T> listIterator(int i) {
        return new IteratorImpl(i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this._size;
    }

    @Override // java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
    public void add(int i, T t) {
        if (i < 0 || i > size()) {
            throw new IndexOutOfBoundsException("index:" + i);
        }
        insert(i, t);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(T t) {
        insert(this._size, t);
        return true;
    }

    public void addFirst(T t) {
        insert(0, t);
    }

    public void addLast(T t) {
        insert(size(), t);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        T t = this._head;
        if (t != null) {
            for (TLinkable next = t.getNext(); next != null; next = next.getNext()) {
                next.getPrevious().setNext(null);
                next.setPrevious(null);
            }
            this._tail = null;
            this._head = null;
        }
        this._size = 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public Object[] toArray() {
        Object[] objArr = new Object[this._size];
        TLinkable next = this._head;
        int i = 0;
        while (next != null) {
            objArr[i] = next;
            next = next.getNext();
            i++;
        }
        return objArr;
    }

    public Object[] toUnlinkedArray() {
        Object[] objArr = new Object[this._size];
        TLinkable tLinkable = this._head;
        int i = 0;
        while (tLinkable != null) {
            objArr[i] = tLinkable;
            TLinkable next = tLinkable.getNext();
            tLinkable.setNext(null);
            tLinkable.setPrevious(null);
            i++;
            tLinkable = next;
        }
        this._size = 0;
        this._tail = null;
        this._head = null;
        return objArr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(Object obj) {
        for (TLinkable next = this._head; next != null; next = next.getNext()) {
            if (obj.equals(next)) {
                return true;
            }
        }
        return false;
    }

    public T getFirst() {
        return this._head;
    }

    public T getLast() {
        return this._tail;
    }

    public T removeFirst() {
        T t = this._head;
        T t2 = (T) t.getNext();
        t.setNext(null);
        if (t2 != null) {
            t2.setPrevious(null);
        }
        this._head = t2;
        int i = this._size - 1;
        this._size = i;
        if (i == 0) {
            this._tail = null;
        }
        return t;
    }

    public T removeLast() {
        T t = this._tail;
        T t2 = (T) t.getPrevious();
        t.setPrevious(null);
        if (t2 != null) {
            t2.setNext(null);
        }
        this._tail = t2;
        int i = this._size - 1;
        this._size = i;
        if (i == 0) {
            this._head = null;
        }
        return t;
    }

    protected void insert(int i, T t) {
        TLinkable previous;
        int i2 = this._size;
        if (i2 == 0) {
            this._tail = t;
            this._head = t;
        } else if (i == 0) {
            t.setNext(this._head);
            this._head.setPrevious(t);
            this._head = t;
        } else if (i == i2) {
            this._tail.setNext(t);
            t.setPrevious(this._tail);
            this._tail = t;
        } else {
            if (i > (i2 >> 1)) {
                previous = this._tail;
                for (int i3 = i2 - 1; i3 > i; i3--) {
                    previous = previous.getPrevious();
                }
            } else {
                TLinkable next = this._head;
                for (int i4 = 0; i4 < i; i4++) {
                    next = next.getNext();
                }
                previous = next;
            }
            TLinkable next2 = previous.getNext();
            t.setNext(next2);
            t.setPrevious(previous);
            next2.setPrevious(t);
            previous.setNext(t);
        }
        this._size++;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(Object obj) {
        if (!(obj instanceof TLinkable)) {
            return false;
        }
        TLinkable tLinkable = (TLinkable) obj;
        TLinkableAdaptor tLinkableAdaptor = (T) tLinkable.getPrevious();
        TLinkableAdaptor tLinkableAdaptor2 = (T) tLinkable.getNext();
        if (tLinkableAdaptor2 == null && tLinkableAdaptor == null) {
            this._tail = null;
            this._head = null;
        } else if (tLinkableAdaptor2 == null) {
            tLinkable.setPrevious(null);
            tLinkableAdaptor.setNext(null);
            this._tail = tLinkableAdaptor;
        } else if (tLinkableAdaptor == null) {
            tLinkable.setNext(null);
            tLinkableAdaptor2.setPrevious(null);
            this._head = tLinkableAdaptor2;
        } else {
            tLinkableAdaptor.setNext(tLinkableAdaptor2);
            tLinkableAdaptor2.setPrevious(tLinkableAdaptor);
            tLinkable.setNext(null);
            tLinkable.setPrevious(null);
        }
        this._size--;
        return true;
    }

    public void addBefore(T t, T t2) {
        if (t == this._head) {
            addFirst(t2);
            return;
        }
        if (t == null) {
            addLast(t2);
            return;
        }
        TLinkable previous = t.getPrevious();
        t2.setNext(t);
        previous.setNext(t2);
        t2.setPrevious(previous);
        t.setPrevious(t2);
        this._size++;
    }

    protected final class IteratorImpl implements ListIterator<T> {
        private T _lastReturned;
        private T _next;
        private int _nextIndex;

        IteratorImpl(int i) {
            if (i < 0 || i > TLinkedList.this._size) {
                throw new IndexOutOfBoundsException();
            }
            this._nextIndex = i;
            if (i == 0) {
                this._next = TLinkedList.this._head;
                return;
            }
            if (i == TLinkedList.this._size) {
                this._next = null;
                return;
            }
            if (i < (TLinkedList.this._size >> 1)) {
                this._next = TLinkedList.this._head;
                for (int i2 = 0; i2 < i; i2++) {
                    this._next = (T) this._next.getNext();
                }
                return;
            }
            this._next = TLinkedList.this._tail;
            for (int i3 = TLinkedList.this._size - 1; i3 > i; i3--) {
                this._next = (T) this._next.getPrevious();
            }
        }

        @Override // java.util.ListIterator
        public final void add(T t) {
            this._lastReturned = null;
            this._nextIndex++;
            if (TLinkedList.this._size == 0) {
                TLinkedList.this.add((TLinkable) t);
            } else {
                TLinkedList.this.addBefore(this._next, t);
            }
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public final boolean hasNext() {
            return this._nextIndex != TLinkedList.this._size;
        }

        @Override // java.util.ListIterator
        public final boolean hasPrevious() {
            return this._nextIndex != 0;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public final T next() {
            if (this._nextIndex == TLinkedList.this._size) {
                throw new NoSuchElementException();
            }
            T t = this._next;
            this._lastReturned = t;
            this._next = (T) t.getNext();
            this._nextIndex++;
            return this._lastReturned;
        }

        @Override // java.util.ListIterator
        public final int nextIndex() {
            return this._nextIndex;
        }

        @Override // java.util.ListIterator
        public final T previous() {
            int i = this._nextIndex;
            if (i == 0) {
                throw new NoSuchElementException();
            }
            if (i == TLinkedList.this._size) {
                T t = TLinkedList.this._tail;
                this._next = t;
                this._lastReturned = t;
            } else {
                T t2 = (T) this._next.getPrevious();
                this._next = t2;
                this._lastReturned = t2;
            }
            this._nextIndex--;
            return this._lastReturned;
        }

        @Override // java.util.ListIterator
        public final int previousIndex() {
            return this._nextIndex - 1;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public final void remove() {
            T t = this._lastReturned;
            if (t == null) {
                throw new IllegalStateException("must invoke next or previous before invoking remove");
            }
            if (t != this._next) {
                this._nextIndex--;
            }
            this._next = (T) t.getNext();
            TLinkedList.this.remove(this._lastReturned);
            this._lastReturned = null;
        }

        @Override // java.util.ListIterator
        public final void set(T t) {
            T t2 = this._lastReturned;
            if (t2 == null) {
                throw new IllegalStateException();
            }
            if (t2 == TLinkedList.this._head) {
                TLinkedList.this._head = t;
            }
            if (this._lastReturned == TLinkedList.this._tail) {
                TLinkedList.this._tail = t;
            }
            swap(this._lastReturned, t);
            this._lastReturned = t;
        }

        private void swap(T t, T t2) {
            TLinkable previous = t.getPrevious();
            TLinkable next = t.getNext();
            if (previous != null) {
                t2.setPrevious(previous);
                previous.setNext(t2);
            }
            if (next != null) {
                t2.setNext(next);
                next.setPrevious(t2);
            }
            t.setNext(null);
            t.setPrevious(null);
        }
    }
}
