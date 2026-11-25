package org.jetbrains.kotlin.com.intellij.util.containers;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ImmutableList<E> extends AbstractCollection<E> implements List<E> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "c";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ImmutableList";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ImmutableList";
        } else {
            objArr[1] = "listIterator";
        }
        if (i != 1) {
            objArr[2] = "addAll";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends E> collection) {
        if (collection == null) {
            $$$reportNull$$$0(0);
        }
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public E set(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public void add(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public E remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        ListIterator<E> listIterator = listIterator();
        if (obj == null) {
            while (listIterator.hasNext()) {
                if (listIterator.next() == null) {
                    return listIterator.previousIndex();
                }
            }
            return -1;
        }
        while (listIterator.hasNext()) {
            if (obj.equals(listIterator.next())) {
                return listIterator.previousIndex();
            }
        }
        return -1;
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        ListIterator<E> listIterator = listIterator(size());
        if (obj == null) {
            while (listIterator.hasPrevious()) {
                if (listIterator.previous() == null) {
                    return listIterator.nextIndex();
                }
            }
            return -1;
        }
        while (listIterator.hasPrevious()) {
            if (obj.equals(listIterator.previous())) {
                return listIterator.nextIndex();
            }
        }
        return -1;
    }

    @Override // java.util.List
    public ListIterator<E> listIterator() {
        ListIterator<E> listIterator = listIterator(0);
        if (listIterator == null) {
            $$$reportNull$$$0(1);
        }
        return listIterator;
    }

    @Override // java.util.List
    public ListIterator<E> listIterator(int i) {
        return new ListItr(i);
    }

    @Override // java.util.List
    public ImmutableList<E> subList(int i, int i2) {
        if (this instanceof SubList) {
            SubList subList = (SubList) this;
            List list = subList.l;
            int i3 = subList.offset;
            return new SubList(list, i + i3, i2 + i3);
        }
        return new SubList(this, i, i2);
    }

    @Override // java.util.Collection, java.util.List
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        ListIterator<E> listIterator = listIterator();
        ListIterator<E> listIterator2 = ((List) obj).listIterator();
        while (listIterator.hasNext() && listIterator2.hasNext()) {
            if (!Objects.equals(listIterator.next(), listIterator2.next())) {
                return false;
            }
        }
        return (listIterator.hasNext() || listIterator2.hasNext()) ? false : true;
    }

    @Override // java.util.Collection, java.util.List
    public int hashCode() {
        Iterator<E> it = iterator();
        int iHashCode = 1;
        while (it.hasNext()) {
            E next = it.next();
            iHashCode = (iHashCode * 31) + (next == null ? 0 : next.hashCode());
        }
        return iHashCode;
    }

    private class Itr implements Iterator<E> {
        int cursor;
        int lastRet;

        private Itr() {
            this.lastRet = -1;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.cursor != ImmutableList.this.size();
        }

        @Override // java.util.Iterator
        public E next() {
            try {
                int i = this.cursor;
                E e = ImmutableList.this.get(i);
                this.lastRet = i;
                this.cursor = i + 1;
                return e;
            } catch (IndexOutOfBoundsException unused) {
                throw new NoSuchElementException();
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class ListItr extends ImmutableList<E>.Itr implements ListIterator<E> {
        ListItr(int i) {
            super();
            this.cursor = i;
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return this.cursor != 0;
        }

        @Override // java.util.ListIterator
        public E previous() {
            try {
                int i = this.cursor - 1;
                E e = ImmutableList.this.get(i);
                this.cursor = i;
                this.lastRet = i;
                return e;
            } catch (IndexOutOfBoundsException unused) {
                throw new NoSuchElementException();
            }
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.cursor;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.cursor - 1;
        }

        @Override // java.util.ListIterator
        public void set(E e) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.ListIterator
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
    }

    private static class SubList<E> extends ImmutableList<E> {
        private final List<? extends E> l;
        private final int offset;
        private final int size;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", SchemaSymbols.ATTVAL_LIST, "org/jetbrains/kotlin/com/intellij/util/containers/ImmutableList$SubList", "<init>"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ImmutableList, java.util.List
        public /* bridge */ /* synthetic */ List subList(int i, int i2) {
            return super.subList(i, i2);
        }

        SubList(List<? extends E> list, int i, int i2) {
            if (list == null) {
                $$$reportNull$$$0(0);
            }
            if (i < 0) {
                throw new IndexOutOfBoundsException("fromIndex = " + i);
            }
            if (i2 > list.size()) {
                throw new IndexOutOfBoundsException("toIndex = " + i2);
            }
            if (i > i2) {
                throw new IllegalArgumentException("fromIndex(" + i + ") > toIndex(" + i2 + Constant.AFTER_QUTO);
            }
            this.l = list;
            this.offset = i;
            this.size = i2 - i;
        }

        @Override // java.util.List
        public E get(int i) {
            if (i < 0 || i >= this.size) {
                throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + this.size);
            }
            return this.l.get(i + this.offset);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.size;
        }
    }

    public static <T> ImmutableList<T> singleton(T t) {
        return new Singleton(t);
    }

    private static class Singleton<E> extends ImmutableList<E> {
        private final E element;

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return 1;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ImmutableList, java.util.List
        public /* bridge */ /* synthetic */ List subList(int i, int i2) {
            return super.subList(i, i2);
        }

        Singleton(E e) {
            this.element = e;
        }

        @Override // java.util.List
        public E get(int i) {
            if (i != 0) {
                throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size());
            }
            return this.element;
        }
    }
}
