package org.jetbrains.kotlin.com.intellij.util.containers;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;

/* JADX INFO: loaded from: classes3.dex */
public final class DisposableWrapperList<E> extends AbstractList<E> {
    private final List<DisposableWrapperList<E>.DisposableWrapper> myWrappedList = ContainerUtil.createLockFreeCopyOnWriteList();

    /* JADX WARN: Code duplicated, block: B:45:0x0084  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 7 || i == 16 || i == 20 || i == 13 || i == 14 || i == 22 || i == 23) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 7 || i == 16 || i == 20 || i == 13 || i == 14 || i == 22 || i == 23) ? 2 : 3];
        switch (i) {
            case 3:
            case 6:
            case 19:
                objArr[0] = "parentDisposable";
                break;
            case 4:
            case 7:
            case 13:
            case 14:
            case 16:
            case 20:
            case 22:
            case 23:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/DisposableWrapperList";
                break;
            case 5:
            case 18:
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 8:
            case 9:
            case 17:
            case 21:
                objArr[0] = "collection";
                break;
            case 10:
            case 12:
                objArr[0] = Constants.OBJECTS;
                break;
            case 11:
                objArr[0] = "filter";
                break;
            case 15:
                objArr[0] = "array";
                break;
        }
        if (i == 4 || i == 7) {
            objArr[1] = ProductAction.ACTION_ADD;
        } else if (i == 16) {
            objArr[1] = "toArray";
        } else if (i == 20) {
            objArr[1] = "createDisposableWrapper";
        } else if (i == 13 || i == 14) {
            objArr[1] = "toArray";
        } else if (i == 22 || i == 23) {
            objArr[1] = "wrapAll";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/DisposableWrapperList";
        }
        if (i != 4) {
            switch (i) {
                case 7:
                case 13:
                case 14:
                case 16:
                case 20:
                case 22:
                case 23:
                    break;
                case 8:
                case 9:
                    objArr[2] = "addAll";
                    break;
                case 10:
                    objArr[2] = "removeAll";
                    break;
                case 11:
                    objArr[2] = "removeIf";
                    break;
                case 12:
                    objArr[2] = "retainAll";
                    break;
                case 15:
                    objArr[2] = "toArray";
                    break;
                case 17:
                    objArr[2] = "containsAll";
                    break;
                case 18:
                case 19:
                    objArr[2] = "createDisposableWrapper";
                    break;
                case 21:
                    objArr[2] = "wrapAll";
                    break;
                default:
                    objArr[2] = ProductAction.ACTION_ADD;
                    break;
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 7 && i != 16 && i != 20 && i != 13 && i != 14 && i != 22 && i != 23) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ boolean lambda$clear$4(Object obj) {
        return true;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(E e) {
        if (e == null) {
            $$$reportNull$$$0(0);
        }
        return this.myWrappedList.add(new DisposableWrapper(this, e));
    }

    @Override // java.util.AbstractList, java.util.List
    public void add(int i, E e) {
        if (e == null) {
            $$$reportNull$$$0(1);
        }
        this.myWrappedList.add(i, new DisposableWrapper(this, e));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection<? extends E> collection) {
        if (collection == null) {
            $$$reportNull$$$0(8);
        }
        return this.myWrappedList.addAll(wrapAll(collection));
    }

    @Override // java.util.AbstractList, java.util.List
    public boolean addAll(int i, Collection<? extends E> collection) {
        if (collection == null) {
            $$$reportNull$$$0(9);
        }
        return this.myWrappedList.addAll(i, wrapAll(collection));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(final Object obj) {
        final ArrayList arrayList = new ArrayList(1);
        boolean zRemoveIf = this.myWrappedList.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.DisposableWrapperList$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj2) {
                return DisposableWrapperList.lambda$remove$0(obj, arrayList, (DisposableWrapperList.DisposableWrapper) obj2);
            }
        });
        Iterator<E> it = arrayList.iterator();
        while (it.hasNext()) {
            ((DisposableWrapper) it.next()).disposeWithoutRemoval();
        }
        return zRemoveIf;
    }

    static /* synthetic */ boolean lambda$remove$0(Object obj, List list, DisposableWrapper disposableWrapper) {
        if (!disposableWrapper.delegate.equals(obj)) {
            return false;
        }
        if ((!list.isEmpty() || !disposableWrapper.makeUnique()) && !list.contains(disposableWrapper)) {
            return false;
        }
        list.add(disposableWrapper);
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public E remove(int i) {
        return unwrapAndDispose(this.myWrappedList.remove(i));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean removeAll(final Collection<?> collection) {
        if (collection == null) {
            $$$reportNull$$$0(10);
        }
        return removeIf(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.DisposableWrapperList$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return collection.contains(obj);
            }
        });
    }

    @Override // java.util.Collection
    public boolean removeIf(final Predicate<? super E> predicate) {
        if (predicate == null) {
            $$$reportNull$$$0(11);
        }
        final ReferenceOpenHashSet referenceOpenHashSet = new ReferenceOpenHashSet(this.myWrappedList.size());
        boolean zRemoveIf = this.myWrappedList.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.DisposableWrapperList$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DisposableWrapperList.lambda$removeIf$2(predicate, referenceOpenHashSet, (DisposableWrapperList.DisposableWrapper) obj);
            }
        });
        Iterator<K> it = referenceOpenHashSet.iterator();
        while (it.hasNext()) {
            ((DisposableWrapper) it.next()).disposeWithoutRemoval();
        }
        return zRemoveIf;
    }

    static /* synthetic */ boolean lambda$removeIf$2(Predicate predicate, Set set, DisposableWrapper disposableWrapper) {
        if (!predicate.test(disposableWrapper.delegate)) {
            return false;
        }
        if (!disposableWrapper.makeUnique() && !set.contains(disposableWrapper)) {
            return false;
        }
        set.add(disposableWrapper);
        return true;
    }

    static /* synthetic */ boolean lambda$retainAll$3(Collection collection, Object obj) {
        return !collection.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean retainAll(final Collection<?> collection) {
        if (collection == null) {
            $$$reportNull$$$0(12);
        }
        return removeIf(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.DisposableWrapperList$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DisposableWrapperList.lambda$retainAll$3(collection, obj);
            }
        });
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        removeIf(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.DisposableWrapperList$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DisposableWrapperList.lambda$clear$4(obj);
            }
        });
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.myWrappedList.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean isEmpty() {
        return this.myWrappedList.isEmpty();
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator<E> iterator() {
        return new DisposableWrapperListIterator(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public Object[] toArray() {
        Object[] array = this.myWrappedList.toArray();
        if (array.length == 0) {
            Object[] objArr = ArrayUtilRt.EMPTY_OBJECT_ARRAY;
            if (objArr == null) {
                $$$reportNull$$$0(13);
            }
            return objArr;
        }
        int length = array.length;
        for (int i = 0; i < length; i++) {
            array[i] = ((DisposableWrapper) array[i]).delegate;
        }
        if (array == null) {
            $$$reportNull$$$0(14);
        }
        return array;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public <T> T[] toArray(T[] tArr) {
        if (tArr == null) {
            $$$reportNull$$$0(15);
        }
        Object[] array = this.myWrappedList.toArray();
        int length = array.length;
        if (tArr.length < length) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), length));
        } else {
            Arrays.fill(tArr, length, tArr.length, (Object) null);
        }
        for (int i = 0; i < length; i++) {
            tArr[i] = ((DisposableWrapper) array[i]).delegate;
        }
        if (tArr == null) {
            $$$reportNull$$$0(16);
        }
        return tArr;
    }

    @Override // java.util.AbstractList, java.util.List
    public E get(int i) {
        return (E) ((DisposableWrapper) this.myWrappedList.get(i)).delegate;
    }

    @Override // java.util.AbstractList, java.util.List
    public E set(int i, E e) {
        return unwrapAndDispose(this.myWrappedList.set(i, new DisposableWrapper(this, e)));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(Object obj) {
        return obj != null && this.myWrappedList.contains(new DisposableWrapper(this, obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean containsAll(Collection<?> collection) {
        if (collection == 0) {
            $$$reportNull$$$0(17);
        }
        return this.myWrappedList.containsAll(wrapAll(collection));
    }

    @Override // java.util.AbstractList, java.util.List
    public int indexOf(Object obj) {
        if (obj == null) {
            return -1;
        }
        return this.myWrappedList.indexOf(new DisposableWrapper(this, obj));
    }

    @Override // java.util.AbstractList, java.util.List
    public int lastIndexOf(Object obj) {
        if (obj == null) {
            return -1;
        }
        return this.myWrappedList.lastIndexOf(new DisposableWrapper(this, obj));
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<E> listIterator() {
        return new DisposableWrapperListIterator(0);
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<E> listIterator(int i) {
        return new DisposableWrapperListIterator(i);
    }

    @Override // java.util.AbstractList, java.util.List
    public List<E> subList(int i, int i2) {
        throw new UnsupportedOperationException();
    }

    private Collection<DisposableWrapperList<E>.DisposableWrapper> wrapAll(Collection<? extends E> collection) {
        if (collection == null) {
            $$$reportNull$$$0(21);
        }
        if (collection.isEmpty()) {
            List listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(22);
            }
            return listEmptyList;
        }
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<? extends E> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(new DisposableWrapper(this, it.next()));
        }
        return arrayList;
    }

    private E unwrapAndDispose(DisposableWrapperList<E>.DisposableWrapper disposableWrapper) {
        if (disposableWrapper == null) {
            return null;
        }
        E e = (E) ((DisposableWrapper) disposableWrapper).delegate;
        disposableWrapper.disposeWithoutRemoval();
        return e;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DisposableWrapper extends AtomicBoolean implements Disposable {
        private final E delegate;
        private boolean removeFromContainer;
        final /* synthetic */ DisposableWrapperList this$0;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
            if (i == 1) {
                objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
            } else if (i == 2) {
                objArr[0] = "o";
            } else if (i == 3 || i == 4) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/DisposableWrapperList$DisposableWrapper";
            } else {
                objArr[0] = "obj";
            }
            if (i == 3 || i == 4) {
                objArr[1] = "classInfo";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/DisposableWrapperList$DisposableWrapper";
            }
            if (i == 2) {
                objArr[2] = "classInfo";
            } else if (i != 3 && i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 3 && i != 4) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        DisposableWrapper(DisposableWrapperList disposableWrapperList, E e) {
            this(disposableWrapperList, e, false);
            if (e == null) {
                $$$reportNull$$$0(0);
            }
        }

        DisposableWrapper(DisposableWrapperList disposableWrapperList, E e, boolean z) {
            if (e == null) {
                $$$reportNull$$$0(1);
            }
            this.this$0 = disposableWrapperList;
            this.delegate = e;
            this.removeFromContainer = z;
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
        public void dispose() {
            if (this.removeFromContainer) {
                makeUnique();
                this.this$0.myWrappedList.remove(this);
            }
        }

        void disposeWithoutRemoval() {
            if (this.removeFromContainer) {
                this.removeFromContainer = false;
                Disposer.dispose(this);
            }
        }

        boolean makeUnique() {
            return compareAndSet(false, true);
        }

        private boolean isUnique() {
            return get();
        }

        public int hashCode() {
            return this.delegate.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || !getClass().equals(obj.getClass())) {
                return false;
            }
            DisposableWrapper disposableWrapper = (DisposableWrapper) obj;
            try {
                return (!this.delegate.equals(disposableWrapper.delegate) || isUnique() || disposableWrapper.isUnique()) ? false : true;
            } catch (ClassCastException e) {
                throw new RuntimeException("failed DisposableWrapper.equals(" + classInfo(disposableWrapper.delegate) + "; this.delegate=" + classInfo(this.delegate) + ". Whole list=" + this.this$0.myWrappedList, e);
            }
        }

        private String classInfo(E e) {
            if (e == null) {
                $$$reportNull$$$0(2);
            }
            try {
                String str = e + " (" + e.getClass() + "; super interfaces: " + Arrays.toString(e.getClass().getInterfaces()) + Constant.AFTER_QUTO;
                if (str == null) {
                    $$$reportNull$$$0(3);
                }
                return str;
            } catch (Throwable th) {
                String message = th.getMessage();
                if (message == null) {
                    $$$reportNull$$$0(4);
                }
                return message;
            }
        }
    }

    private class DisposableWrapperListIterator implements ListIterator<E> {
        private final ListIterator<DisposableWrapperList<E>.DisposableWrapper> myDelegate;
        private DisposableWrapperList<E>.DisposableWrapper myLastReturned;

        DisposableWrapperListIterator(int i) {
            this.myDelegate = DisposableWrapperList.this.myWrappedList.listIterator(i);
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            return this.myDelegate.hasNext();
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public E next() {
            DisposableWrapperList<E>.DisposableWrapper next = this.myDelegate.next();
            this.myLastReturned = next;
            return (E) ((DisposableWrapper) next).delegate;
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return this.myDelegate.hasPrevious();
        }

        @Override // java.util.ListIterator
        public E previous() {
            DisposableWrapperList<E>.DisposableWrapper disposableWrapperPrevious = this.myDelegate.previous();
            this.myLastReturned = disposableWrapperPrevious;
            return (E) ((DisposableWrapper) disposableWrapperPrevious).delegate;
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.myDelegate.nextIndex();
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.myDelegate.previousIndex();
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public void remove() {
            DisposableWrapperList<E>.DisposableWrapper disposableWrapper = this.myLastReturned;
            if (disposableWrapper == null) {
                throw new NoSuchElementException();
            }
            if (disposableWrapper.makeUnique()) {
                this.myDelegate.remove();
                this.myLastReturned.disposeWithoutRemoval();
                this.myLastReturned = null;
            }
        }

        @Override // java.util.ListIterator
        public void set(E e) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.ListIterator
        public void add(E e) {
            throw new UnsupportedOperationException();
        }

        public int hashCode() {
            return this.myDelegate.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || !getClass().equals(obj.getClass())) {
                return false;
            }
            return this.myDelegate.equals(((DisposableWrapperListIterator) obj).myDelegate);
        }
    }
}
