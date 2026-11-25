package org.jetbrains.kotlin.com.intellij.util.containers;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class SmartHashSet<T> extends HashSet<T> {
    private T theElement;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 6 || i == 8 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 6 || i == 8 || i == 9) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[0] = "obj";
                break;
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/SmartHashSet";
                break;
            case 7:
                objArr[0] = "a";
                break;
            default:
                objArr[0] = "collection";
                break;
        }
        if (i == 4) {
            objArr[1] = "iterator";
        } else if (i == 5 || i == 6 || i == 8 || i == 9) {
            objArr[1] = "toArray";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/SmartHashSet";
        }
        switch (i) {
            case 1:
                objArr[2] = "contains";
                break;
            case 2:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 3:
                objArr[2] = ProductAction.ACTION_REMOVE;
                break;
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
                break;
            case 7:
                objArr[2] = "toArray";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 6 && i != 8 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // java.util.HashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(1);
        }
        T t = this.theElement;
        if (t != null) {
            return Objects.equals(obj, t);
        }
        return !super.isEmpty() && super.contains(obj);
    }

    @Override // java.util.HashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean add(T t) {
        if (t == null) {
            $$$reportNull$$$0(2);
        }
        T t2 = this.theElement;
        if (t2 != null) {
            if (Objects.equals(t, t2)) {
                return false;
            }
            super.add(this.theElement);
            this.theElement = null;
            return super.add(t);
        }
        if (super.isEmpty()) {
            this.theElement = t;
            return true;
        }
        return super.add(t);
    }

    @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        T t = this.theElement;
        if (t != null) {
            if (obj instanceof Set) {
                Set set = (Set) obj;
                if (set.size() == 1 && Objects.equals(set.iterator().next(), t)) {
                    return true;
                }
            }
            return false;
        }
        return super.equals(obj);
    }

    @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
    public int hashCode() {
        T t = this.theElement;
        return t == null ? super.hashCode() : t.hashCode();
    }

    @Override // java.util.HashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        this.theElement = null;
        super.clear();
    }

    @Override // java.util.HashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        if (this.theElement == null) {
            return super.size();
        }
        return 1;
    }

    @Override // java.util.HashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.theElement == null && super.isEmpty();
    }

    @Override // java.util.HashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(3);
        }
        T t = this.theElement;
        if (t == null) {
            return super.remove(obj);
        }
        if (!Objects.equals(obj, t)) {
            return false;
        }
        this.theElement = null;
        return true;
    }

    @Override // java.util.HashSet, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<T> iterator() {
        if (this.theElement == null) {
            Iterator<T> it = super.iterator();
            if (it == null) {
                $$$reportNull$$$0(4);
            }
            return it;
        }
        return new SingletonIteratorBase<T>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.SmartHashSet.1
            @Override // org.jetbrains.kotlin.com.intellij.util.containers.SingletonIteratorBase
            protected void checkCoModification() {
                if (SmartHashSet.this.theElement == null) {
                    throw new ConcurrentModificationException();
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.SingletonIteratorBase
            protected T getElement() {
                return (T) SmartHashSet.this.theElement;
            }

            @Override // java.util.Iterator
            public void remove() {
                checkCoModification();
                SmartHashSet.this.clear();
            }
        };
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer<? super T> consumer) {
        T t = this.theElement;
        if (t == null) {
            super.forEach(consumer);
        } else {
            consumer.accept(t);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public Object[] toArray() {
        T t = this.theElement;
        if (t == null) {
            Object[] array = super.toArray();
            if (array == null) {
                $$$reportNull$$$0(5);
            }
            return array;
        }
        return new Object[]{t};
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [O[], java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r4v1, types: [O[]] */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v8 */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public <O> O[] toArray(O[] oArr) {
        ?? r4;
        if (oArr == 0) {
            $$$reportNull$$$0(7);
        }
        T t = this.theElement;
        if (t == null) {
            O[] oArr2 = (O[]) super.toArray((Object[]) oArr);
            if (oArr2 == null) {
                $$$reportNull$$$0(8);
            }
            return oArr2;
        }
        if (oArr.length == 0) {
            r4 = oArr;
            r4 = (O[]) ArrayUtil.newArray(ArrayUtil.getComponentType(oArr), 1);
        }
        r4 = oArr;
        ((??[OBJECT, ARRAY][]) r4)[0] = t;
        if (((??[]) r4).length > 1) {
            ((??[OBJECT, ARRAY][]) r4)[1] = 0;
        }
        if (r4 == 0) {
            $$$reportNull$$$0(9);
        }
        return (O[]) r4;
    }

    @Override // java.util.Collection
    public Stream<T> stream() {
        T t = this.theElement;
        return t == null ? super.stream() : Stream.of(t);
    }

    @Override // java.util.HashSet, java.util.Collection, java.lang.Iterable, java.util.Set
    public Spliterator<T> spliterator() {
        T t = this.theElement;
        return t == null ? super.spliterator() : Stream.of(t).spliterator();
    }
}
