package org.jetbrains.kotlin.com.intellij.util.containers;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.reandroid.archive.PathTree;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;

/* JADX INFO: loaded from: classes3.dex */
final class LockFreeCopyOnWriteArrayList<E> extends AtomicReference<Object[]> implements ConcurrentList<E>, List<E>, RandomAccess {
    /* JADX WARN: Code duplicated, block: B:61:0x00ba  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 13 || i == 15 || i == 17 || i == 19 || i == 32 || i == 39 || i == 6 || i == 7 || i == 9 || i == 10 || i == 36 || i == 37) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 13 || i == 15 || i == 17 || i == 19 || i == 32 || i == 39 || i == 6 || i == 7 || i == 9 || i == 10 || i == 36 || i == 37) ? 2 : 3];
        switch (i) {
            case 1:
            case 6:
            case 7:
            case 9:
            case 10:
            case 13:
            case 15:
            case 17:
            case 19:
            case 32:
            case 36:
            case 37:
            case 39:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/LockFreeCopyOnWriteArrayList";
                break;
            case 2:
                objArr[0] = "oldArray";
                break;
            case 3:
                objArr[0] = "newArray";
                break;
            case 4:
            case 5:
            case 12:
            case 14:
            case 16:
            case 18:
            case 20:
            case 24:
            case 27:
            case 30:
            case 34:
                objArr[0] = PathTree.NAME_elements;
                break;
            case 8:
            case 11:
                objArr[0] = "a";
                break;
            case 21:
            case 22:
            case 23:
            case 25:
            case 26:
            case 28:
            case 29:
            case 33:
            default:
                objArr[0] = "c";
                break;
            case 31:
            case 35:
                objArr[0] = LanguageCodeUtil.CS;
                break;
            case 38:
                objArr[0] = "action";
                break;
        }
        if (i == 1) {
            objArr[1] = "getArray";
        } else if (i == 13) {
            objArr[1] = "createArraySet";
        } else if (i == 15 || i == 17) {
            objArr[1] = "createArrayAdd";
        } else if (i == 19) {
            objArr[1] = "createArrayRemove";
        } else if (i == 32) {
            objArr[1] = "createArrayAddAll";
        } else if (i == 39) {
            objArr[1] = "listIterator";
        } else if (i == 6 || i == 7 || i == 9 || i == 10) {
            objArr[1] = "toArray";
        } else if (i == 36) {
            objArr[1] = "createArrayAddAll";
        } else if (i != 37) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/LockFreeCopyOnWriteArrayList";
        } else {
            objArr[1] = "toString";
        }
        switch (i) {
            case 1:
            case 6:
            case 7:
            case 9:
            case 10:
            case 13:
            case 15:
            case 17:
            case 19:
            case 32:
            case 36:
            case 37:
            case 39:
                break;
            case 2:
            case 3:
                objArr[2] = "replaceArray";
                break;
            case 4:
                objArr[2] = "indexOf";
                break;
            case 5:
                objArr[2] = "lastIndexOf";
                break;
            case 8:
                objArr[2] = "toArray";
                break;
            case 11:
                objArr[2] = "get";
                break;
            case 12:
                objArr[2] = "createArraySet";
                break;
            case 14:
            case 16:
                objArr[2] = "createArrayAdd";
                break;
            case 18:
            case 20:
                objArr[2] = "createArrayRemove";
                break;
            case 21:
                objArr[2] = "containsAll";
                break;
            case 22:
                objArr[2] = "removeAll";
                break;
            case 23:
            case 24:
                objArr[2] = "createArrayRemoveAll";
                break;
            case 25:
                objArr[2] = "retainAll";
                break;
            case 26:
            case 27:
                objArr[2] = "createArrayRetainAll";
                break;
            case 28:
                objArr[2] = "addAllAbsent";
                break;
            case 29:
            case 33:
                objArr[2] = "addAll";
                break;
            case 30:
            case 31:
            case 34:
            case 35:
                objArr[2] = "createArrayAddAll";
                break;
            case 38:
                objArr[2] = "forEach";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 13 && i != 15 && i != 17 && i != 19 && i != 32 && i != 39 && i != 6 && i != 7 && i != 9 && i != 10 && i != 36 && i != 37) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    LockFreeCopyOnWriteArrayList() {
        clear();
    }

    private boolean replaceArray(Object[] objArr, Object[] objArr2) {
        if (objArr == null) {
            $$$reportNull$$$0(2);
        }
        if (objArr2 == null) {
            $$$reportNull$$$0(3);
        }
        return compareAndSet(objArr, objArr2);
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return get().length;
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return size() == 0;
    }

    private static int indexOf(Object obj, Object[] objArr, int i, int i2) {
        if (objArr == null) {
            $$$reportNull$$$0(4);
        }
        return ArrayUtilRt.indexOf(objArr, obj, i, i2);
    }

    private static int lastIndexOf(Object obj, Object[] objArr, int i) {
        if (objArr == null) {
            $$$reportNull$$$0(5);
        }
        if (obj == null) {
            while (i >= 0) {
                if (objArr[i] == null) {
                    return i;
                }
                i--;
            }
            return -1;
        }
        while (i >= 0) {
            if (obj.equals(objArr[i])) {
                return i;
            }
            i--;
        }
        return -1;
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        Object[] objArr = get();
        return indexOf(obj, objArr, 0, objArr.length) >= 0;
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        Object[] objArr = get();
        return indexOf(obj, objArr, 0, objArr.length);
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        Object[] objArr = get();
        return lastIndexOf(obj, objArr, objArr.length - 1);
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        Object[] objArr = get();
        if (objArr.length == 0) {
            Object[] objArr2 = ArrayUtilRt.EMPTY_OBJECT_ARRAY;
            if (objArr2 == null) {
                $$$reportNull$$$0(6);
            }
            return objArr2;
        }
        Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length, Object[].class);
        if (objArrCopyOf == null) {
            $$$reportNull$$$0(7);
        }
        return objArrCopyOf;
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        if (tArr == null) {
            $$$reportNull$$$0(8);
        }
        Object[] objArr = get();
        int length = objArr.length;
        if (tArr.length < length) {
            T[] tArr2 = (T[]) Arrays.copyOf(objArr, length, tArr.getClass());
            if (tArr2 == null) {
                $$$reportNull$$$0(9);
            }
            return tArr2;
        }
        System.arraycopy(objArr, 0, tArr, 0, length);
        if (tArr.length > length) {
            tArr[length] = null;
        }
        if (tArr == null) {
            $$$reportNull$$$0(10);
        }
        return tArr;
    }

    private E get(Object[] objArr, int i) {
        if (objArr == null) {
            $$$reportNull$$$0(11);
        }
        return (E) objArr[i];
    }

    @Override // java.util.List
    public E get(int i) {
        return get(get(), i);
    }

    @Override // java.util.List
    public E set(int i, E e) throws IndexOutOfBoundsException {
        Object[] objArr;
        E e2;
        do {
            objArr = get();
            e2 = get(objArr, i);
        } while (!replaceArray(objArr, e2 == e ? objArr : createArraySet(objArr, i, e)));
        return e2;
    }

    private static Object[] createArraySet(Object[] objArr, int i, Object obj) {
        if (objArr == null) {
            $$$reportNull$$$0(12);
        }
        Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length, Object[].class);
        objArrCopyOf[i] = obj;
        if (objArrCopyOf == null) {
            $$$reportNull$$$0(13);
        }
        return objArrCopyOf;
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(E e) {
        Object[] objArr;
        do {
            objArr = get();
        } while (!replaceArray(objArr, createArrayAdd(objArr, e)));
        return true;
    }

    private static <E> Object[] createArrayAdd(Object[] objArr, E e) {
        if (objArr == null) {
            $$$reportNull$$$0(14);
        }
        int length = objArr.length;
        Object[] objArrCopyOf = Arrays.copyOf(objArr, length + 1);
        objArrCopyOf[length] = e;
        if (objArrCopyOf == null) {
            $$$reportNull$$$0(15);
        }
        return objArrCopyOf;
    }

    @Override // java.util.List
    public void add(int i, E e) throws IndexOutOfBoundsException {
        Object[] objArr;
        do {
            objArr = get();
        } while (!replaceArray(objArr, createArrayAdd(objArr, i, e)));
    }

    private static <E> Object[] createArrayAdd(Object[] objArr, int i, E e) {
        if (objArr == null) {
            $$$reportNull$$$0(16);
        }
        int length = objArr.length;
        if (i > length || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + length);
        }
        Object[] objArr2 = new Object[length + 1];
        if (i != 0) {
            System.arraycopy(objArr, 0, objArr2, 0, i);
        }
        int i2 = length - i;
        if (i2 != 0) {
            System.arraycopy(objArr, i, objArr2, i + 1, i2);
        }
        objArr2[i] = e;
        return objArr2;
    }

    @Override // java.util.List
    public E remove(int i) throws IndexOutOfBoundsException {
        Object[] objArr;
        do {
            objArr = get();
        } while (!replaceArray(objArr, createArrayRemove(objArr, i)));
        return get(objArr, i);
    }

    private static Object[] createArrayRemove(Object[] objArr, int i) {
        if (objArr == null) {
            $$$reportNull$$$0(18);
        }
        int length = objArr.length;
        Object[] objArr2 = length == 1 ? ArrayUtilRt.EMPTY_OBJECT_ARRAY : new Object[length - 1];
        if (i != 0) {
            System.arraycopy(objArr, 0, objArr2, 0, i);
        }
        int i2 = (length - i) - 1;
        if (i2 != 0) {
            System.arraycopy(objArr, i + 1, objArr2, i, i2);
        }
        if (objArr2 == null) {
            $$$reportNull$$$0(19);
        }
        return objArr2;
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        Object[] objArr;
        Object[] objArrCreateArrayRemove;
        do {
            objArr = get();
            objArrCreateArrayRemove = createArrayRemove(objArr, obj);
            if (objArrCreateArrayRemove == null) {
                return false;
            }
        } while (!replaceArray(objArr, objArrCreateArrayRemove));
        return true;
    }

    private static Object[] createArrayRemove(Object[] objArr, Object obj) {
        if (objArr == null) {
            $$$reportNull$$$0(20);
        }
        int length = objArr.length;
        if (length == 0) {
            return null;
        }
        int i = length - 1;
        Object[] objArr2 = i == 0 ? ArrayUtilRt.EMPTY_OBJECT_ARRAY : new Object[i];
        while (i != 0) {
            Object obj2 = objArr[i];
            if (Objects.equals(obj, obj2)) {
                System.arraycopy(objArr, 0, objArr2, 0, i);
                break;
            }
            objArr2[i - 1] = obj2;
            i--;
        }
        if (i != 0 || Objects.equals(obj, objArr[0])) {
            return objArr2;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentList
    public boolean addIfAbsent(E e) {
        Object[] objArr;
        Object[] objArr2;
        do {
            objArr = get();
            int length = objArr.length;
            objArr2 = new Object[length + 1];
            for (int i = 0; i < length; i++) {
                if (Objects.equals(e, objArr[i])) {
                    return false;
                }
                objArr2[i] = objArr[i];
            }
            objArr2[length] = e;
        } while (!replaceArray(objArr, objArr2));
        return true;
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        if (collection == null) {
            $$$reportNull$$$0(21);
        }
        Object[] objArr = get();
        int length = objArr.length;
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (indexOf(it.next(), objArr, 0, length) < 0) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        Object[] objArr;
        Object[] objArrCreateArrayRemoveAll;
        if (collection == null) {
            $$$reportNull$$$0(22);
        }
        if (collection.isEmpty()) {
            return false;
        }
        do {
            objArr = get();
            objArrCreateArrayRemoveAll = createArrayRemoveAll(objArr, collection);
            if (objArrCreateArrayRemoveAll == null) {
                return false;
            }
        } while (!replaceArray(objArr, objArrCreateArrayRemoveAll));
        return true;
    }

    private static Object[] createArrayRemoveAll(Object[] objArr, Collection<?> collection) {
        if (collection == null) {
            $$$reportNull$$$0(23);
        }
        if (objArr == null) {
            $$$reportNull$$$0(24);
        }
        int length = objArr.length;
        if (length == 0) {
            return null;
        }
        Object[] objArr2 = new Object[length];
        int i = 0;
        for (Object obj : objArr) {
            if (!collection.contains(obj)) {
                objArr2[i] = obj;
                i++;
            }
        }
        if (i == length) {
            return null;
        }
        return Arrays.copyOf(objArr2, i, Object[].class);
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        Object[] objArr;
        Object[] objArrCreateArrayRetainAll;
        if (collection == null) {
            $$$reportNull$$$0(25);
        }
        do {
            objArr = get();
            objArrCreateArrayRetainAll = createArrayRetainAll(objArr, collection);
            if (objArrCreateArrayRetainAll == null) {
                return false;
            }
        } while (!replaceArray(objArr, objArrCreateArrayRetainAll));
        return true;
    }

    private static Object[] createArrayRetainAll(Object[] objArr, Collection<?> collection) {
        if (collection == null) {
            $$$reportNull$$$0(26);
        }
        if (objArr == null) {
            $$$reportNull$$$0(27);
        }
        int length = objArr.length;
        if (length == 0) {
            return null;
        }
        Object[] objArr2 = new Object[length];
        int i = 0;
        for (Object obj : objArr) {
            if (collection.contains(obj)) {
                objArr2[i] = obj;
                i++;
            }
        }
        if (i == length) {
            return null;
        }
        return Arrays.copyOf(objArr2, i, Object[].class);
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        set(ArrayUtilRt.EMPTY_OBJECT_ARRAY);
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends E> collection) {
        Object[] objArr;
        if (collection == null) {
            $$$reportNull$$$0(29);
        }
        if (collection.isEmpty()) {
            return false;
        }
        Object[] array = collection.toArray();
        if (array.length == 0) {
            return false;
        }
        do {
            objArr = get();
        } while (!replaceArray(objArr, createArrayAddAll(objArr, array)));
        return true;
    }

    private static Object[] createArrayAddAll(Object[] objArr, Object[] objArr2) {
        if (objArr == null) {
            $$$reportNull$$$0(30);
        }
        if (objArr2 == null) {
            $$$reportNull$$$0(31);
        }
        int length = objArr.length;
        Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr2.length + length, Object[].class);
        System.arraycopy(objArr2, 0, objArrCopyOf, length, objArr2.length);
        if (objArrCopyOf == null) {
            $$$reportNull$$$0(32);
        }
        return objArrCopyOf;
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends E> collection) throws IndexOutOfBoundsException {
        Object[] objArr;
        if (collection == null) {
            $$$reportNull$$$0(33);
        }
        Object[] array = collection.toArray();
        if (array.length == 0) {
            return false;
        }
        do {
            objArr = get();
        } while (!replaceArray(objArr, createArrayAddAll(objArr, i, array)));
        return true;
    }

    private static Object[] createArrayAddAll(Object[] objArr, int i, Object[] objArr2) {
        Object[] objArrCopyOf;
        if (objArr == null) {
            $$$reportNull$$$0(34);
        }
        if (objArr2 == null) {
            $$$reportNull$$$0(35);
        }
        int length = objArr.length;
        if (i > length || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + length);
        }
        int i2 = length - i;
        if (i2 == 0) {
            objArrCopyOf = Arrays.copyOf(objArr, length + objArr2.length, Object[].class);
        } else {
            Object[] objArr3 = new Object[length + objArr2.length];
            System.arraycopy(objArr, 0, objArr3, 0, i);
            System.arraycopy(objArr, i, objArr3, objArr2.length + i, i2);
            objArrCopyOf = objArr3;
        }
        System.arraycopy(objArr2, 0, objArrCopyOf, i, objArr2.length);
        if (objArrCopyOf == null) {
            $$$reportNull$$$0(36);
        }
        return objArrCopyOf;
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        String string = Arrays.toString(get());
        if (string == null) {
            $$$reportNull$$$0(37);
        }
        return string;
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        Iterator<E> it = ((List) obj).iterator();
        for (Object obj2 : get()) {
            if (!it.hasNext() || !Objects.equals(obj2, it.next())) {
                return false;
            }
        }
        return !it.hasNext();
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        Object[] objArr = get();
        int length = objArr.length;
        int iHashCode = 1;
        for (int i = 0; i < length; i++) {
            Object obj = objArr[i];
            iHashCode = (iHashCode * 31) + (obj == null ? 0 : obj.hashCode());
        }
        return iHashCode;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        Object[] objArr = get();
        return objArr.length == 0 ? Collections.emptyIterator() : new COWIterator(objArr, 0);
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer<? super E> consumer) {
        if (consumer == null) {
            $$$reportNull$$$0(38);
        }
        for (Object obj : get()) {
            consumer.accept(obj);
        }
    }

    @Override // java.util.List
    public ListIterator<E> listIterator() {
        ListIterator<E> listIterator = listIterator(0);
        if (listIterator == null) {
            $$$reportNull$$$0(39);
        }
        return listIterator;
    }

    @Override // java.util.List
    public ListIterator<E> listIterator(int i) {
        Object[] objArr = get();
        int length = objArr.length;
        if (i < 0 || i > length) {
            throw new IndexOutOfBoundsException("Index: " + i);
        }
        return objArr.length == 0 ? Collections.emptyListIterator() : new COWIterator(objArr, i);
    }

    private final class COWIterator implements ListIterator<E> {
        private int cursor;
        private int lastRet;
        private final Object[] snapshot;
        final /* synthetic */ LockFreeCopyOnWriteArrayList this$0;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", PathTree.NAME_elements, "org/jetbrains/kotlin/com/intellij/util/containers/LockFreeCopyOnWriteArrayList$COWIterator", "<init>"));
        }

        private COWIterator(LockFreeCopyOnWriteArrayList lockFreeCopyOnWriteArrayList, Object[] objArr, int i) {
            if (objArr == null) {
                $$$reportNull$$$0(0);
            }
            this.this$0 = lockFreeCopyOnWriteArrayList;
            this.lastRet = -1;
            this.cursor = i;
            this.snapshot = objArr;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            return this.cursor < this.snapshot.length;
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return this.cursor > 0;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i = this.cursor;
            this.lastRet = i;
            Object[] objArr = this.snapshot;
            this.cursor = i + 1;
            return (E) objArr[i];
        }

        @Override // java.util.ListIterator
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            Object[] objArr = this.snapshot;
            int i = this.cursor - 1;
            this.cursor = i;
            this.lastRet = i;
            return (E) objArr[i];
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.cursor;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.cursor - 1;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public void remove() {
            int i = this.lastRet;
            if (i < 0) {
                throw new NoSuchElementException();
            }
            Object obj = this.snapshot[i];
            this.lastRet = -1;
            this.this$0.remove(obj);
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

    @Override // java.util.List
    public List<E> subList(int i, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Spliterator<E> spliterator() {
        return Arrays.spliterator(get());
    }
}
