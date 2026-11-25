package com.reandroid.utils.collection;

import com.reandroid.common.ArraySupplier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class ArrayCollection<T> implements ArraySupplier<T>, List<T>, Set<T>, Swappable {
    private static final int GROW_LIMIT = 8192;
    private Object[] mElements;
    private int mHashCode;
    private Initializer<T> mInitializer;
    private int mLastGrow;
    private boolean mLocked;
    private Monitor<T> mMonitor;
    private int size;
    static final Object[] EMPTY_OBJECTS = new Object[0];
    private static final ArrayCollection<?> EMPTY = new ArrayCollection<Object>() { // from class: com.reandroid.utils.collection.ArrayCollection.2
        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List, java.util.Collection, java.util.Set
        public void clear() {
        }

        @Override // com.reandroid.utils.collection.ArrayCollection
        public void clearTemporarily() {
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return false;
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List, java.util.Collection, java.util.Set
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override // com.reandroid.utils.collection.ArrayCollection
        public void ensureCapacity(int i) {
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List, java.util.Collection, java.util.Set
        public int hashCode() {
            return 0;
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return true;
        }

        @Override // com.reandroid.utils.collection.ArrayCollection
        public boolean isImmutableEmpty() {
            return true;
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List
        public Object remove(int i) {
            return null;
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return false;
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override // com.reandroid.utils.collection.ArrayCollection
        public void setSize(int i) {
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List, java.util.Collection, java.util.Set
        public int size() {
            return 0;
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List
        public void sort(Comparator<? super Object> comparator) {
        }

        @Override // com.reandroid.utils.collection.ArrayCollection
        public void trimToSize() {
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List
        public /* bridge */ /* synthetic */ List subList(int i, int i2) {
            return super.subList(i, i2);
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List, java.util.Collection, java.util.Set
        public Object[] toArray() {
            return EMPTY_OBJECTS;
        }

        @Override // com.reandroid.utils.collection.ArrayCollection
        public void addAll(Iterator<? extends Object> it) {
            throw new IllegalArgumentException("Empty ArrayCollection!");
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List, java.util.Collection, java.util.Set
        public boolean addAll(Collection<?> collection) {
            throw new IllegalArgumentException("Empty ArrayCollection!");
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List, java.util.Collection, java.util.Set
        public boolean add(Object obj) {
            throw new IllegalArgumentException("Empty ArrayCollection!");
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List
        public Object set(int i, Object obj) {
            throw new IllegalArgumentException("Empty ArrayCollection!");
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List
        public void add(int i, Object obj) {
            throw new IllegalArgumentException("Empty ArrayCollection!");
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Object> iterator() {
            return EmptyIterator.of();
        }

        @Override // com.reandroid.utils.collection.ArrayCollection, java.util.List, java.util.Collection, java.util.Set
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return (obj instanceof Collection) && ((Collection) obj).size() == 0;
        }
    };

    public interface Initializer<T1> {
        T1 createNewItem(int i);

        T1[] newArray(int i);
    }

    public interface Monitor<T> {
        void onAdd(int i, T t);

        void onRemoved(int i, T t);
    }

    public boolean isImmutableEmpty() {
        return false;
    }

    public ArrayCollection(int i) {
        Object[] objArr;
        if (i == 0) {
            objArr = EMPTY_OBJECTS;
        } else {
            objArr = new Object[i];
        }
        this.mElements = objArr;
        this.size = 0;
    }

    public ArrayCollection(Object[] objArr) {
        objArr = (objArr == null || objArr.length == 0) ? EMPTY_OBJECTS : objArr;
        this.mElements = objArr;
        this.size = objArr.length;
    }

    public ArrayCollection(Collection<? extends T> collection) {
        Object[] objArr;
        int size = collection.size();
        if (size == 0) {
            objArr = EMPTY_OBJECTS;
        } else {
            Object[] array = collection.toArray();
            objArr = collection.getClass() != ArrayCollection.class ? (Object[]) array.clone() : array;
        }
        this.mElements = objArr;
        this.size = size;
    }

    public ArrayCollection() {
        this(0);
    }

    public Monitor<T> getMonitor() {
        return this.mMonitor;
    }

    public void setMonitor(Monitor<T> monitor) {
        this.mMonitor = monitor;
    }

    public Initializer<T> getInitializer() {
        return this.mInitializer;
    }

    public void setInitializer(Initializer<T> initializer) {
        this.mInitializer = initializer;
    }

    public ArrayCollection<T> copy() {
        return new ArrayCollection<>((Object[]) toArray().clone());
    }

    public ArrayCollection<T> filter(Predicate<? super T> predicate) {
        int iCount = count(predicate);
        if (iCount == size()) {
            return this;
        }
        ArrayCollection<T> arrayCollection = new ArrayCollection<>(iCount);
        arrayCollection.addAll(iterator(predicate));
        return arrayCollection;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T1 extends T> ArrayCollection<T1> filter(Class<T1> cls) {
        int iCount = count((Class<?>) cls);
        if (iCount == size()) {
            return this;
        }
        ArrayCollection<T1> arrayCollection = new ArrayCollection<>(iCount);
        arrayCollection.addAll(iterator(cls));
        return arrayCollection;
    }

    public int count(Predicate<? super T> predicate) {
        return count(predicate, size());
    }

    public int count(Predicate<? super T> predicate, int i) {
        int size = size();
        int i2 = 0;
        for (int i3 = 0; i3 < size && i2 < i; i3++) {
            if (predicate.test(get(i3))) {
                i2++;
            }
        }
        return i2;
    }

    public int count(Class<?> cls) {
        return count(cls, size());
    }

    public int count(Class<?> cls, int i) {
        int size = size();
        Object[] objArr = this.mElements;
        int i2 = 0;
        for (int i3 = 0; i3 < size && i2 < i; i3++) {
            if (cls.isInstance(objArr[i3])) {
                i2++;
            }
        }
        return i2;
    }

    public int countFromLast(Predicate<? super T> predicate) {
        int i = 0;
        for (int size = size() - 1; size >= 0 && predicate.test(get(size)); size--) {
            i++;
        }
        return i;
    }

    @Override // java.util.List
    public void sort(Comparator<? super T> comparator) {
        int size;
        if (!this.mLocked && (size = size()) >= 2 && new ArraySort.ObjectSort(this.mElements, 0, size, comparator).sort()) {
            onChanged();
        }
    }

    public boolean sortItems(Comparator<? super T> comparator) {
        int size;
        if (this.mLocked || (size = size()) < 2 || !new ArraySort.ObjectSort(this.mElements, 0, size, comparator).sort()) {
            return false;
        }
        onChanged();
        return true;
    }

    public boolean sort(Comparator<? super T> comparator, Swappable swappable) {
        if (swappable == null) {
            throw new NullPointerException("swappable == null");
        }
        if (swappable == this) {
            throw new IllegalArgumentException("swappable == this");
        }
        return sort(comparator, SwapListener.redirectTo(swappable));
    }

    public boolean sort(Comparator<? super T> comparator, final SwapListener swapListener) {
        int size;
        if (this.mLocked || (size = size()) < 2) {
            return false;
        }
        if (swapListener == null) {
            throw new NullPointerException("swapListener == null");
        }
        if (!new ArraySort.ObjectSort(this.mElements, 0, size, comparator) { // from class: com.reandroid.utils.collection.ArrayCollection.1
            @Override // com.reandroid.utils.collection.ArraySort.ObjectSort, com.reandroid.utils.collection.Sorter
            public void onSwap(int i, int i2) {
                super.onSwap(i, i2);
                swapListener.onSwap(i, i2);
            }
        }.sort()) {
            return false;
        }
        onChanged();
        return true;
    }

    @Override // java.util.List, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        return containsFast(obj) || containsEquals(obj);
    }

    public boolean containsIf(Predicate<? super T> predicate) {
        return containsIf(0, predicate);
    }

    public boolean containsIf(int i, Predicate<? super T> predicate) {
        if (i < 0) {
            i = 0;
        }
        int i2 = this.size;
        if (i2 == 0) {
            return false;
        }
        Object[] objArr = this.mElements;
        while (i < i2) {
            if (predicate.test(objArr[i])) {
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean containsEquals(Object obj) {
        return indexOf(obj) >= 0;
    }

    public boolean containsFast(Object obj) {
        return indexOfFast(obj) >= 0;
    }

    @Override // java.util.List, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return size() == 0;
    }

    public T getFirst() {
        if (size() == 0) {
            return null;
        }
        return get(0);
    }

    public T getLast() {
        int size = size();
        if (size == 0) {
            return null;
        }
        return get(size - 1);
    }

    public T getElement(Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf >= 0) {
            return get(iIndexOf);
        }
        return null;
    }

    @Override // com.reandroid.common.ArraySupplier
    public T get(int i) {
        return (T) this.mElements[i];
    }

    @Override // java.util.List, java.util.Collection, java.util.Set
    public int size() {
        return this.size;
    }

    public void ensureSize(int i) {
        if (i > size()) {
            setSize(i);
        }
    }

    public void setSize(int i) {
        int i2 = this.size;
        if (i == i2) {
            return;
        }
        if (i < i2) {
            this.size = i;
            onChanged();
            return;
        }
        boolean z = this.mLocked;
        this.mLocked = true;
        int i3 = i - i2;
        ensureCapacity(i3);
        this.size = i;
        fillElements(this.mElements, i2, i3);
        this.mLocked = z;
        onChanged();
    }

    private void fillElements(Object[] objArr, int i, int i2) {
        Initializer<T> initializer = getInitializer();
        if (initializer == null) {
            return;
        }
        int i3 = i2 + i;
        while (i < i3) {
            T tCreateNewItem = initializer.createNewItem(i);
            objArr[i] = tCreateNewItem;
            notifyAdd(i, tCreateNewItem);
            i++;
        }
    }

    @Override // com.reandroid.common.CountSupplier
    public int getCount() {
        return size();
    }

    public <T1> Iterator<T1> iterator(Class<T1> cls) {
        return InstanceIterator.of(iterator(), cls);
    }

    public Iterator<T> iterator(Predicate<? super T> predicate) {
        return FilterIterator.of(iterator(), predicate);
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<T> iterator() {
        return ArraySupplierIterator.of(this);
    }

    public Iterator<T> iterator(int i) {
        return iterator(i, size() - i);
    }

    public Iterator<T> iterator(int i, int i2) {
        return ArraySupplierIterator.of(this, i, i2);
    }

    public Iterator<T> arrayIterator() {
        return ArrayIterator.of(this.mElements, 0, size());
    }

    public Iterator<T> clonedIterator() {
        return clonedIterator(0, size());
    }

    public Iterator<T> clonedIterator(int i, int i2) {
        if (isEmpty()) {
            return EmptyIterator.of();
        }
        return ArrayIterator.of((Object[]) this.mElements.clone(), i, i2);
    }

    public Iterator<T> reversedIterator() {
        return ReversedIterator.of(this);
    }

    public Iterator<T> reversedIterator(int i) {
        return ReversedIterator.of(this, i);
    }

    public Iterator<T> reversedIterator(int i, int i2) {
        return ReversedIterator.of(this, i, i2);
    }

    public Iterator<T> reversedClonedIterator() {
        return ReversedIterator.of((Object[]) this.mElements.clone());
    }

    public Iterator<T> reversedClonedIterator(int i) {
        return ReversedIterator.of((Object[]) this.mElements.clone(), i);
    }

    public Iterator<T> reversedClonedIterator(int i, int i2) {
        return ReversedIterator.of((Object[]) this.mElements.clone(), i, i2);
    }

    @Override // java.util.List, java.util.Collection, java.util.Set
    public Object[] toArray() {
        Object[] objArrTrimToSize = trimToSize(getElements(), size());
        return objArrTrimToSize == this.mElements ? (Object[]) objArrTrimToSize.clone() : objArrTrimToSize;
    }

    public Object[] getElements() {
        return this.mElements;
    }

    public void setElements(Object[] objArr) {
        setElements(objArr, objArr.length);
    }

    public void setElements(Object[] objArr, int i) {
        this.mElements = objArr;
        this.size = i;
        onChanged();
    }

    @Override // java.util.List, java.util.Collection, java.util.Set
    public <T1> T1[] toArray(T1[] t1Arr) {
        int size = size();
        if (size == 0) {
            return t1Arr;
        }
        Object[] objArr = this.mElements;
        int length = t1Arr.length;
        if (length <= 0 || length > size) {
            return (T1[]) Arrays.copyOf(objArr, size, t1Arr.getClass());
        }
        for (int i = 0; i < length; i++) {
            t1Arr[i] = objArr[i];
        }
        return t1Arr;
    }

    public T removeItem(Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf < 0) {
            return null;
        }
        T t = get(iIndexOf);
        remove(iIndexOf);
        onChanged();
        return t;
    }

    public ArrayCollection<T> subListIf(Predicate<? super T> predicate) {
        ArrayCollection<T> arrayCollection = new ArrayCollection<>();
        arrayCollection.addAll(iterator(predicate));
        return arrayCollection;
    }

    @Override // java.util.List
    public ArrayCollection<T> subList(int i, int i2) {
        int i3 = i + i2;
        int size = size();
        if (i3 > size) {
            i3 = size;
        }
        if (i == 0 && i3 == size) {
            return this;
        }
        Object[] newArray = getNewArray(i2);
        Object[] objArr = this.mElements;
        while (i < i3) {
            newArray[i] = objArr[i];
            i++;
        }
        return new ArrayCollection<>(newArray);
    }

    public ArrayCollection<T> reversedCopy() {
        Object[] array = toArray();
        if (array == this.mElements && array.length != 0) {
            array = (Object[]) array.clone();
        }
        ArrayUtil.reverse(array);
        return new ArrayCollection<>(array);
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return indexOf(obj, 0, false);
    }

    public int indexOfFast(Object obj) {
        return indexOf(obj, 0, true);
    }

    public int indexOf(Object obj, int i) {
        return indexOf(obj, i, false);
    }

    public int indexOfFast(Object obj, int i) {
        return indexOf(obj, i, true);
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return lastIndexOf(obj, false);
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable, java.util.Set
    public Spliterator<T> spliterator() {
        throw new IllegalArgumentException("Not implemented");
    }

    @Override // java.util.List
    public ListIterator<T> listIterator() {
        return ListItr.of(this);
    }

    @Override // java.util.List
    public ListIterator<T> listIterator(int i) {
        return ListItr.of(this, i);
    }

    public int lastIndexOfFast(Object obj) {
        return lastIndexOf(obj, true);
    }

    private int indexOf(Object obj, int i, boolean z) {
        if (obj == null) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        int i2 = this.size;
        if (i2 == 0) {
            return -1;
        }
        Object[] objArr = this.mElements;
        for (int i3 = i; i3 < i2; i3++) {
            if (matches(obj, objArr[i3], true)) {
                return i3;
            }
        }
        if (z) {
            return -1;
        }
        while (i < i2) {
            if (matches(obj, objArr[i], false)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private boolean matches(Object obj, Object obj2, boolean z) {
        if (obj2 == null) {
            return false;
        }
        if (obj == obj2) {
            return true;
        }
        if (z) {
            return false;
        }
        return obj.equals(obj2);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x001b  */
    private int lastIndexOf(Object obj, boolean z) {
        int i = -1;
        if (obj == null) {
            return -1;
        }
        Object[] objArr = this.mElements;
        int i2 = this.size;
        for (int i3 = 0; i3 < i2; i3++) {
            Object obj2 = objArr[i3];
            if (obj2 != null) {
                if (z) {
                    if (obj == obj2) {
                        i = i3;
                    }
                } else if (obj.equals(obj2)) {
                    i = i3;
                }
            }
        }
        return i;
    }

    public int indexOfIf(Predicate<? super T> predicate) {
        return indexOfIf(0, predicate);
    }

    public int indexOfIf(int i, Predicate<? super T> predicate) {
        if (i < 0) {
            i = 0;
        }
        int i2 = this.size;
        if (i2 == 0) {
            return -1;
        }
        Object[] objArr = this.mElements;
        while (i < i2) {
            if (predicate.test(objArr[i])) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override // java.util.List, java.util.Collection, java.util.Set
    public boolean containsAll(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return !collection.isEmpty();
    }

    public void addAll(int i, T[] tArr) {
        if (tArr == null) {
            return;
        }
        if (size() == 0) {
            setElements(tArr);
            return;
        }
        int length = tArr.length;
        ensureCapacity(length);
        for (int i2 = 0; i2 < length; i2++) {
            add(i + i2, tArr[i2]);
        }
    }

    public void addAll(T[] tArr) {
        if (tArr == null) {
            return;
        }
        addAll(size(), tArr);
    }

    public void addAll(Iterator<? extends T> it) {
        if (it == null) {
            return;
        }
        if (it instanceof SizedIterator) {
            ensureCapacity(((SizedIterator) it).getRemainingSize());
        }
        while (it.hasNext()) {
            add(it.next());
        }
    }

    public void addIterable(Iterable<? extends T> iterable) {
        if (iterable == null) {
            return;
        }
        if (iterable instanceof Collection) {
            addAll((Collection) iterable);
        } else {
            addAll(iterable.iterator());
        }
    }

    @Override // java.util.List, java.util.Collection, java.util.Set
    public boolean addAll(Collection<? extends T> collection) {
        if (collection == null) {
            return false;
        }
        if (size() == 0) {
            setElements(getNewArray(collection.toArray(), collection.size()));
            return true;
        }
        int size = collection.size();
        this.mLocked = true;
        boolean z = false;
        for (T t : collection) {
            if (availableCapacity() == 0) {
                ensureCapacity(size);
            }
            if (add(t)) {
                z = true;
            }
        }
        this.mLocked = false;
        return z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.List, java.util.Collection, java.util.Set
    public boolean removeAll(Collection<?> collection) {
        int i;
        Object[] objArr = this.mElements;
        if (objArr == null || (i = this.size) == 0) {
            return false;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (obj == it.next()) {
                    objArr[i3] = null;
                    notifyRemoved(i3, obj);
                    i2++;
                    break;
                }
            }
        }
        if (i2 == 0) {
            return false;
        }
        if (this.mElements != objArr) {
            throw new ConcurrentModificationException();
        }
        int i4 = this.size - i2;
        this.size = i4;
        if (i4 == 0) {
            this.mElements = EMPTY_OBJECTS;
            return true;
        }
        Object[] newArray = getNewArray(i4);
        int i5 = 0;
        for (int i6 = 0; i6 < i; i6++) {
            Object obj2 = objArr[i6];
            if (obj2 != null) {
                newArray[i5] = obj2;
                i5++;
            }
        }
        this.size = i5;
        this.mElements = newArray;
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Collection
    public boolean removeIf(Predicate<? super T> predicate) {
        int i;
        Object[] objArr = this.mElements;
        if (objArr == null || (i = this.size) == 0) {
            return false;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (predicate.test(obj)) {
                objArr[i3] = null;
                notifyRemoved(i3, obj);
                i2++;
            }
        }
        if (i2 == 0) {
            return false;
        }
        if (this.mElements != objArr) {
            throw new ConcurrentModificationException();
        }
        int i4 = this.size - i2;
        this.size = i4;
        if (i4 == 0) {
            this.mElements = EMPTY_OBJECTS;
            return true;
        }
        Object[] newArray = getNewArray(i4);
        int i5 = 0;
        for (int i6 = 0; i6 < i; i6++) {
            Object obj2 = objArr[i6];
            if (obj2 != null) {
                newArray[i5] = obj2;
                i5++;
            }
        }
        this.size = i5;
        this.mElements = newArray;
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean removeAllIndexes(int[] iArr) {
        int i;
        Object[] objArr = this.mElements;
        if (objArr == null || iArr.length == 0 || (i = this.size) == 0) {
            return false;
        }
        int length = iArr.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            int i4 = iArr[i3];
            if (i4 >= 0 && i4 < i) {
                Object obj = objArr[i4];
                objArr[i4] = null;
                notifyRemoved(i3, obj);
                i2++;
            }
        }
        if (i2 == 0) {
            return false;
        }
        int i5 = this.size - i2;
        this.size = i5;
        if (i5 == 0) {
            this.mElements = EMPTY_OBJECTS;
            return true;
        }
        Object[] newArray = getNewArray(i5);
        int i6 = 0;
        for (int i7 = 0; i7 < i; i7++) {
            Object obj2 = objArr[i7];
            if (obj2 != null) {
                newArray[i6] = obj2;
                i6++;
            }
        }
        this.size = i6;
        this.mElements = newArray;
        return true;
    }

    @Override // java.util.List, java.util.Collection, java.util.Set
    public boolean retainAll(Collection<?> collection) {
        throw new RuntimeException("Method not implemented");
    }

    public void clearTemporarily() {
        int i = this.size;
        this.size = 0;
        Object[] objArr = this.mElements;
        this.mLocked = false;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        onChanged();
    }

    @Override // java.util.List, java.util.Collection, java.util.Set
    public void clear() {
        int i = this.size;
        this.size = 0;
        Object[] objArr = this.mElements;
        this.mElements = EMPTY_OBJECTS;
        this.mLastGrow = 0;
        this.mLocked = false;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        onChanged();
    }

    @Override // java.util.List, java.util.Collection, java.util.Set
    public boolean remove(Object obj) {
        return removeItem(obj) != null;
    }

    @Override // java.util.List, java.util.Collection, java.util.Set
    public boolean add(T t) {
        if (t == null) {
            return false;
        }
        boolean z = this.mLocked;
        this.mLocked = true;
        ensureCapacity();
        int i = this.size;
        this.mElements[i] = t;
        this.size = i + 1;
        this.mLocked = z;
        onChanged();
        notifyAdd(i, t);
        return true;
    }

    @Override // java.util.List
    public void add(int i, T t) {
        if (t == null) {
            return;
        }
        boolean z = this.mLocked;
        this.mLocked = true;
        slideRight(i, 1);
        this.mElements[i] = t;
        notifyAdd(i, t);
        this.mLocked = z;
        onChanged();
    }

    @Override // com.reandroid.utils.collection.Swappable
    public boolean swap(int i, int i2) {
        if (i == i2) {
            return false;
        }
        Object[] objArr = this.mElements;
        Object obj = objArr[i];
        objArr[i] = objArr[i2];
        objArr[i2] = obj;
        onChanged();
        return true;
    }

    public void move(Object obj, int i) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf >= 0) {
            move(iIndexOf, i);
        }
    }

    public void move(int i, int i2) {
        if (i == i2 || i2 < 0) {
            return;
        }
        ensureSize(i2 + 1);
        boolean z = this.mLocked;
        this.mLocked = true;
        Object[] objArr = this.mElements;
        Object obj = objArr[i];
        if (i > i2) {
            while (i > i2) {
                objArr[i] = objArr[i - 1];
                i--;
            }
        } else {
            while (i < i2) {
                int i3 = i + 1;
                objArr[i] = objArr[i3];
                i = i3;
            }
        }
        objArr[i2] = obj;
        this.mElements = objArr;
        this.mLocked = z;
        onChanged();
    }

    @Override // java.util.List
    public T set(int i, T t) {
        if (t == null || i < 0) {
            return null;
        }
        ensureSize(i + 1);
        Object[] objArr = this.mElements;
        T t2 = (T) objArr[i];
        objArr[i] = t;
        if (t != t2) {
            onChanged();
        }
        return t2;
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends T> collection) {
        int size;
        if (collection == null || (size = collection.size()) == 0) {
            return false;
        }
        boolean z = this.mLocked;
        this.mLocked = true;
        slideRight(i, size);
        Object[] objArr = this.mElements;
        int i2 = i;
        for (T t : collection) {
            if (t != null && !containsFast(t)) {
                objArr[i2] = t;
                notifyAdd(i2, t);
                i2++;
            }
        }
        int i3 = size - (i2 - i);
        slideLeft(i2, i3);
        this.mLocked = z;
        return i3 < size;
    }

    @Override // java.util.List
    public T remove(int i) {
        T t = get(i);
        slideLeft(i, 1);
        notifyRemoved(i, t);
        return t;
    }

    private void slideLeft(int i, int i2) {
        if (i2 == 0 || i < 0) {
            return;
        }
        boolean z = this.mLocked;
        this.mLocked = true;
        Object[] objArr = this.mElements;
        int i3 = this.size;
        int i4 = i3 - i2;
        while (i < i4) {
            objArr[i] = objArr[i + i2];
            i++;
        }
        for (int i5 = i4; i5 < i3; i5++) {
            objArr[i5] = null;
        }
        this.size = i4;
        this.mLocked = z;
        onChanged();
    }

    private void slideRight(int i, int i2) {
        boolean z = this.mLocked;
        this.mLocked = true;
        ensureCapacity(i2);
        Object[] objArr = this.mElements;
        int i3 = this.size;
        for (int i4 = i3 - 1; i4 >= i; i4--) {
            objArr[i4 + i2] = objArr[i4];
        }
        this.size = i3 + i2;
        int i5 = i2 + i;
        while (i < i5) {
            objArr[i] = null;
            i++;
        }
        this.mLocked = z;
        onChanged();
    }

    public void trimToSize() {
        if (this.mLocked || availableCapacity() == 0) {
            if (this.mLastGrow == 0) {
                this.mLastGrow = size() / 3;
            }
        } else {
            this.mElements = trimToSize(this.mElements, this.size);
            this.mLastGrow = this.size / 4;
        }
    }

    private Object[] trimToSize(Object[] objArr, int i) {
        if (i >= objArr.length) {
            return objArr;
        }
        if (i == 0) {
            return getNewArray(0);
        }
        Object[] newArray = getNewArray(i);
        arrayCopy(objArr, newArray, i);
        return newArray;
    }

    private void arrayCopy(Object[] objArr, Object[] objArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            objArr2[i2] = objArr[i2];
        }
    }

    private Object[] getNewArray(Object[] objArr, int i) {
        Object[] newArray = getNewArray(i);
        arrayCopy(objArr, newArray, i);
        return newArray;
    }

    private Object[] getNewArray(int i) {
        Initializer<T> initializer = getInitializer();
        if (initializer != null) {
            return initializer.newArray(i);
        }
        if (i == 0) {
            return EMPTY_OBJECTS;
        }
        return new Object[i];
    }

    private void ensureCapacity() {
        if (availableCapacity() > 0) {
            return;
        }
        ensureCapacity(calculateGrow());
    }

    public void ensureCapacity(int i) {
        int iAvailableCapacity;
        if (i > 0 && (iAvailableCapacity = i - availableCapacity()) > 0) {
            int i2 = this.size;
            Object[] newArray = getNewArray(iAvailableCapacity + i2);
            Object[] objArr = this.mElements;
            if (objArr.length == 0 || i2 == 0) {
                this.mElements = newArray;
            } else {
                arrayCopy(objArr, newArray, i2);
                this.mElements = newArray;
            }
        }
    }

    public int availableCapacity() {
        return this.mElements.length - this.size;
    }

    private int calculateGrow() {
        int i = this.size;
        if (i == 0) {
            return 1;
        }
        int i2 = this.mLastGrow;
        if (i2 >= 8192) {
            return i2;
        }
        if (i2 == 0) {
            i2 = 1;
        }
        int i3 = i2 << 1;
        if (i3 > 32) {
            i3 = i2 << 2;
        }
        if (i3 > 32 && i3 < 256) {
            i3 <<= 1;
        }
        int i4 = i3 <= 8192 ? i3 : 8192;
        this.mLastGrow = i4;
        if (i < 4) {
            return 1;
        }
        return i4;
    }

    public void onChanged() {
        this.mHashCode = 0;
    }

    private void notifyAdd(int i, T t) {
        Monitor<T> monitor = getMonitor();
        if (monitor != null) {
            monitor.onAdd(i, t);
        }
    }

    private void notifyRemoved(int i, T t) {
        Monitor<T> monitor = getMonitor();
        if (monitor != null) {
            monitor.onRemoved(i, t);
        }
    }

    @Override // java.util.List, java.util.Collection, java.util.Set
    public int hashCode() {
        int i = this.mHashCode;
        if (i != 0) {
            return i;
        }
        int iComputeHashCode = computeHashCode();
        this.mHashCode = iComputeHashCode;
        return iComputeHashCode;
    }

    @Override // java.util.List, java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ArrayCollection arrayCollection = (ArrayCollection) obj;
            int size = size();
            if (size == arrayCollection.size() && hashCode() == arrayCollection.hashCode()) {
                for (int i = 0; i < size; i++) {
                    if (!Objects.equals(get(i), arrayCollection.get(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public int computeHashCode() {
        int size = size();
        if (size == 0) {
            return 0;
        }
        Object[] objArr = this.mElements;
        int iHashCode = 1;
        for (int i = 0; i < size; i++) {
            Object obj = objArr[i];
            iHashCode = (iHashCode * 31) + (obj == null ? 0 : obj.hashCode());
        }
        this.mHashCode = iHashCode;
        return iHashCode;
    }

    public String toString() {
        if (size() == 0) {
            return "EMPTY";
        }
        return size() + "{" + get(0) + "}";
    }

    public static <T> ArrayCollection<T> of(Iterable<? extends T> iterable) {
        if (iterable == null) {
            return empty();
        }
        ArrayCollection<T> arrayCollection = new ArrayCollection<>();
        arrayCollection.addIterable(iterable);
        arrayCollection.trimToSize();
        return arrayCollection;
    }

    public static <T> ArrayCollection<T> of(Iterator<? extends T> it) {
        ArrayCollection<T> arrayCollection = new ArrayCollection<>();
        arrayCollection.addAll(it);
        arrayCollection.trimToSize();
        return arrayCollection;
    }

    public static <T> ArrayCollection<T> empty() {
        return (ArrayCollection<T>) EMPTY;
    }
}
