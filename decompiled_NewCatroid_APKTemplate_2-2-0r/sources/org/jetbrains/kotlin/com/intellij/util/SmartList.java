package org.jetbrains.kotlin.com.intellij.util;

import com.reandroid.archive.PathTree;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;

/* JADX INFO: loaded from: classes3.dex */
public class SmartList<E> extends AbstractList<E> implements RandomAccess {
    private Object myElem;
    private int mySize;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 5 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/SmartList";
                break;
            case 4:
                objArr[0] = "a";
                break;
            case 8:
            case 9:
            case 10:
                objArr[0] = "that";
                break;
            case 11:
                objArr[0] = "action";
                break;
            default:
                objArr[0] = PathTree.NAME_elements;
                break;
        }
        if (i == 2) {
            objArr[1] = "resizeIfNecessary";
        } else if (i == 3) {
            objArr[1] = "iterator";
        } else if (i == 5 || i == 6 || i == 7) {
            objArr[1] = "toArray";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/SmartList";
        }
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
                break;
            case 4:
                objArr[2] = "toArray";
                break;
            case 8:
                objArr[2] = "equalsWithSmartList";
                break;
            case 9:
                objArr[2] = "equalsWithArrayList";
                break;
            case 10:
                objArr[2] = "compareOneByOne";
                break;
            case 11:
                objArr[2] = "forEach";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 5 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public SmartList() {
    }

    public SmartList(E e) {
        this.myElem = e;
        this.mySize = 1;
    }

    public SmartList(Collection<? extends E> collection) {
        if (collection == null) {
            $$$reportNull$$$0(0);
        }
        int size = collection.size();
        if (size == 1) {
            add(collection instanceof RandomAccess ? (E) ((List) collection).get(0) : collection.iterator().next());
        } else if (size > 0) {
            this.mySize = size;
            this.myElem = collection.toArray(new Object[size]);
        }
    }

    @SafeVarargs
    public SmartList(E... eArr) {
        if (eArr == null) {
            $$$reportNull$$$0(1);
        }
        int length = eArr.length;
        if (length != 0) {
            if (length == 1) {
                this.myElem = eArr[0];
                this.mySize = 1;
            } else {
                this.myElem = Arrays.copyOf(eArr, length);
                this.mySize = length;
            }
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public E get(int i) {
        int i2 = this.mySize;
        checkOutOfBounds(i, i2, false);
        if (i2 == 1) {
            return asElement();
        }
        return asArray()[i];
    }

    private static void checkOutOfBounds(int i, int i2, boolean z) {
        if (i >= 0) {
            if (z) {
                if (i <= i2) {
                    return;
                }
            } else if (i < i2) {
                return;
            }
        }
        throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + i2);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(E e) {
        int i = this.mySize;
        if (i == 0) {
            this.myElem = e;
        } else if (i == 1) {
            this.myElem = new Object[]{this.myElem, e};
        } else {
            resizeIfNecessary(i)[i] = e;
        }
        this.mySize++;
        this.modCount++;
        return true;
    }

    private E[] resizeIfNecessary(int i) {
        E[] eArrAsArray = asArray();
        int length = eArrAsArray.length;
        if (i >= length) {
            eArrAsArray = (E[]) ArrayUtil.realloc(eArrAsArray, Math.max(((length * 3) / 2) + 1, i + 1), new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.util.SmartList$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
                public final Object[] create(int i2) {
                    return SmartList.lambda$resizeIfNecessary$0(i2);
                }
            });
            this.myElem = eArrAsArray;
        }
        if (eArrAsArray == null) {
            $$$reportNull$$$0(2);
        }
        return eArrAsArray;
    }

    static /* synthetic */ Object[] lambda$resizeIfNecessary$0(int i) {
        return new Object[i];
    }

    @Override // java.util.AbstractList, java.util.List
    public void add(int i, E e) {
        int i2 = this.mySize;
        checkOutOfBounds(i, i2, true);
        if (i2 == 0) {
            this.myElem = e;
        } else if (i2 == 1) {
            this.myElem = i == 0 ? new Object[]{e, this.myElem} : new Object[]{this.myElem, e};
        } else {
            E[] eArrResizeIfNecessary = resizeIfNecessary(i2);
            System.arraycopy(eArrResizeIfNecessary, i, eArrResizeIfNecessary, i + 1, i2 - i);
            eArrResizeIfNecessary[i] = e;
        }
        this.mySize++;
        this.modCount++;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.mySize;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        this.myElem = null;
        this.mySize = 0;
        this.modCount++;
    }

    @Override // java.util.AbstractList, java.util.List
    public E set(int i, E e) {
        int i2 = this.mySize;
        checkOutOfBounds(i, i2, false);
        if (i2 == 1) {
            E eAsElement = asElement();
            this.myElem = e;
            return eAsElement;
        }
        E[] eArrAsArray = asArray();
        E e2 = eArrAsArray[i];
        eArrAsArray[i] = e;
        return e2;
    }

    private E asElement() {
        return (E) this.myElem;
    }

    private E[] asArray() {
        return (E[]) ((Object[]) this.myElem);
    }

    @Override // java.util.AbstractList, java.util.List
    public E remove(int i) {
        E eAsElement;
        int i2 = this.mySize;
        checkOutOfBounds(i, i2, false);
        if (i2 == 0 || i2 == 1) {
            eAsElement = asElement();
            this.myElem = null;
        } else if (i2 == 2) {
            E[] eArrAsArray = asArray();
            eAsElement = eArrAsArray[i];
            this.myElem = eArrAsArray[1 - i];
        } else {
            E[] eArrAsArray2 = asArray();
            eAsElement = eArrAsArray2[i];
            int i3 = (i2 - i) - 1;
            if (i3 > 0) {
                System.arraycopy(eArrAsArray2, i + 1, eArrAsArray2, i, i3);
            }
            eArrAsArray2[i2 - 1] = null;
        }
        this.mySize--;
        this.modCount++;
        return eAsElement;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator<E> iterator() {
        Iterator<E> itEmptyIterator = this.mySize == 0 ? Collections.emptyIterator() : super.iterator();
        if (itEmptyIterator == null) {
            $$$reportNull$$$0(3);
        }
        return itEmptyIterator;
    }

    @Override // java.util.List
    public void sort(Comparator<? super E> comparator) {
        if (this.mySize >= 2) {
            Arrays.sort(asArray(), 0, this.mySize, comparator);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public <T> T[] toArray(T[] tArr) {
        if (tArr == 0) {
            $$$reportNull$$$0(4);
        }
        int length = tArr.length;
        int i = this.mySize;
        if (i != 0) {
            if (i == 1) {
                E eAsElement = asElement();
                if (length == 0) {
                    T[] tArr2 = (T[]) ArrayUtil.newArray(ArrayUtil.getComponentType(tArr), 1);
                    tArr2[0] = eAsElement;
                    if (tArr2 == 0) {
                        $$$reportNull$$$0(5);
                    }
                    return tArr2;
                }
                tArr[0] = eAsElement;
            } else {
                if (length < i) {
                    T[] tArr3 = (T[]) Arrays.copyOf(asArray(), i, tArr.getClass());
                    if (tArr3 == null) {
                        $$$reportNull$$$0(6);
                    }
                    return tArr3;
                }
                System.arraycopy(asArray(), 0, tArr, 0, i);
            }
        }
        if (length > i) {
            tArr[i] = 0;
        }
        if (tArr == 0) {
            $$$reportNull$$$0(7);
        }
        return tArr;
    }

    @Override // java.util.AbstractList, java.util.List
    public int indexOf(Object obj) {
        int i = this.mySize;
        if (i == 0) {
            return -1;
        }
        if (i != 1) {
            return ArrayUtilRt.indexOf(asArray(), obj, 0, this.mySize);
        }
        return Objects.equals(obj, this.myElem) ? 0 : -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SmartList) {
            return equalsWithSmartList((SmartList) obj);
        }
        if (obj instanceof ArrayList) {
            return equalsWithArrayList((ArrayList) obj);
        }
        return super.equals(obj);
    }

    private boolean equalsWithSmartList(SmartList<?> smartList) {
        if (smartList == null) {
            $$$reportNull$$$0(8);
        }
        int i = this.mySize;
        if (i != smartList.mySize) {
            return false;
        }
        if (i == 0) {
            return true;
        }
        if (i == 1) {
            return Objects.equals(this.myElem, smartList.myElem);
        }
        return compareOneByOne(i, smartList);
    }

    private boolean equalsWithArrayList(ArrayList<?> arrayList) {
        if (arrayList == null) {
            $$$reportNull$$$0(9);
        }
        int i = this.mySize;
        if (i != arrayList.size()) {
            return false;
        }
        if (i == 0) {
            return true;
        }
        if (i == 1) {
            return Objects.equals(this.myElem, arrayList.get(0));
        }
        return compareOneByOne(i, arrayList);
    }

    private boolean compareOneByOne(int i, List<?> list) {
        if (list == null) {
            $$$reportNull$$$0(10);
        }
        E[] eArrAsArray = asArray();
        for (int i2 = 0; i2 < i; i2++) {
            if (!Objects.equals(eArrAsArray[i2], list.get(i2))) {
                return false;
            }
        }
        return true;
    }

    @Override // java.lang.Iterable
    public void forEach(java.util.function.Consumer<? super E> consumer) {
        if (consumer == null) {
            $$$reportNull$$$0(11);
        }
        int i = this.mySize;
        if (i != 0) {
            if (i == 1) {
                consumer.accept(asElement());
                return;
            }
            E[] eArrAsArray = asArray();
            for (int i2 = 0; i2 < i; i2++) {
                consumer.accept(eArrAsArray[i2]);
            }
        }
    }
}
