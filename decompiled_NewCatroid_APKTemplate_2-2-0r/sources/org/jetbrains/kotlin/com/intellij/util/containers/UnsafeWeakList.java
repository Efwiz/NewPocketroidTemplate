package org.jetbrains.kotlin.com.intellij.util.containers;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX INFO: loaded from: classes3.dex */
public class UnsafeWeakList<T> extends AbstractCollection<T> {
    private int modCount;
    private int myAlive;
    private final ReferenceQueue<T> myQueue = new ReferenceQueue<>();
    protected final List<MyReference<T>> myList = new ArrayList();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 6 || i == 7) ? 2 : 3];
        if (i == 3) {
            objArr[0] = "o";
        } else if (i == 4 || i == 5) {
            objArr[0] = "c";
        } else if (i == 6 || i == 7) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/UnsafeWeakList";
        } else {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        }
        if (i == 6 || i == 7) {
            objArr[1] = "toStrongList";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/UnsafeWeakList";
        }
        switch (i) {
            case 1:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 2:
                objArr[2] = "addIfAbsent";
                break;
            case 3:
                objArr[2] = ProductAction.ACTION_REMOVE;
                break;
            case 4:
                objArr[2] = "addAll";
                break;
            case 5:
                objArr[2] = "removeAll";
                break;
            case 6:
            case 7:
                break;
            default:
                objArr[2] = "append";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ int access$308(UnsafeWeakList unsafeWeakList) {
        int i = unsafeWeakList.modCount;
        unsafeWeakList.modCount = i + 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class MyReference<T> extends WeakReference<T> {
        private final int index;

        private MyReference(int i, T t, ReferenceQueue<? super T> referenceQueue) {
            super(t, referenceQueue);
            this.index = i;
        }
    }

    boolean processQueue() {
        boolean z = false;
        while (true) {
            MyReference<T> myReference = (MyReference) this.myQueue.poll();
            if (myReference == null) {
                break;
            }
            int i = ((MyReference) myReference).index;
            if (i < this.myList.size() && myReference == this.myList.get(i)) {
                nullizeAt(i);
            }
            z = true;
        }
        if (this.myAlive < this.myList.size() / 2) {
            reduceCapacity();
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nullizeAt(int i) {
        this.myList.set(i, null);
        this.myAlive--;
    }

    private void reduceCapacity() {
        int i = 0;
        for (int i2 = 0; i2 < this.myList.size(); i2++) {
            MyReference<T> myReference = this.myList.get(i2);
            if (myReference != null) {
                Object obj = myReference.get();
                if (obj == null) {
                    this.myAlive--;
                } else {
                    if (i != i2) {
                        this.myList.set(i, new MyReference<>(i, obj, this.myQueue));
                    }
                    i++;
                }
            }
        }
        if (i != this.myList.size()) {
            List<MyReference<T>> list = this.myList;
            list.subList(i, list.size()).clear();
            this.modCount++;
        }
        this.myAlive = i;
    }

    private void append(T t) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        this.myList.add(new MyReference<>(this.myList.size(), t, this.myQueue));
        this.myAlive++;
        this.modCount++;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean add(T t) {
        if (t == null) {
            $$$reportNull$$$0(1);
        }
        processQueue();
        append(t);
        return true;
    }

    public boolean addIfAbsent(T t) {
        if (t == null) {
            $$$reportNull$$$0(2);
        }
        processQueue();
        if (contains(t)) {
            return false;
        }
        append(t);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public void clear() {
        processQueue();
        this.myList.clear();
        this.myAlive = 0;
        this.modCount++;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private final class MyIterator implements Iterator<T> {
        private T curElement;
        private int curIndex;
        private boolean modified;
        private T nextElement;
        private int nextIndex;
        private final int startModCount;

        private MyIterator() {
            this.nextIndex = -1;
            this.startModCount = UnsafeWeakList.this.modCount;
            findNext();
        }

        private void findNext() {
            if (UnsafeWeakList.this.modCount != this.startModCount) {
                throw new ConcurrentModificationException();
            }
            int i = this.nextIndex;
            this.curIndex = i;
            this.curElement = this.nextElement;
            this.nextElement = null;
            this.nextIndex = -1;
            while (true) {
                i++;
                if (i >= UnsafeWeakList.this.myList.size()) {
                    break;
                }
                MyReference<T> myReference = UnsafeWeakList.this.myList.get(i);
                T t = myReference == null ? null : myReference.get();
                if (t != null) {
                    this.nextElement = t;
                    this.nextIndex = i;
                    break;
                }
            }
            if (this.nextIndex == -1 && this.modified) {
                UnsafeWeakList.access$308(UnsafeWeakList.this);
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.nextElement != null;
        }

        @Override // java.util.Iterator
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            findNext();
            return this.curElement;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.curElement == null) {
                throw new NoSuchElementException();
            }
            UnsafeWeakList.this.nullizeAt(this.curIndex);
            this.modified = true;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean remove(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(3);
        }
        processQueue();
        for (int i = 0; i < this.myList.size(); i++) {
            MyReference<T> myReference = this.myList.get(i);
            T t = myReference == null ? null : myReference.get();
            if (t != null && t.equals(obj)) {
                nullizeAt(i);
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends T> collection) {
        if (collection == 0) {
            $$$reportNull$$$0(4);
        }
        processQueue();
        return super.addAll(collection);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        if (collection == null) {
            $$$reportNull$$$0(5);
        }
        processQueue();
        return super.removeAll(collection);
    }

    public List<T> toStrongList() {
        if (this.myList.isEmpty()) {
            List<T> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(6);
            }
            return listEmptyList;
        }
        ArrayList arrayList = new ArrayList(this.myList.size());
        Iterator<MyReference<T>> it = this.myList.iterator();
        while (it.hasNext()) {
            MyReference<T> next = it.next();
            Object obj = next == null ? null : next.get();
            if (obj != null) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public int size() {
        throwNotAllowedException();
        return -1;
    }

    private static void throwNotAllowedException() {
        throw new UnsupportedOperationException("index/size-based operations in UnsafeWeakList are not supported because they don't make sense in the presence of weak references. Use .iterator() (which retains its elements to avoid sudden GC) instead.");
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        if (this.myList.isEmpty()) {
            return true;
        }
        for (MyReference<T> myReference : this.myList) {
            if (myReference != null && myReference.get() != null) {
                return false;
            }
        }
        return true;
    }
}
