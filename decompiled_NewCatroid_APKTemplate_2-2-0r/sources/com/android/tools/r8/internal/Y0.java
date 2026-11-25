package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class Y0 extends U0 implements InterfaceC2298jK {
    protected Y0() {
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final int compareTo(List list) {
        if (list == this) {
            return 0;
        }
        if (list instanceof InterfaceC2298jK) {
            InterfaceC2376kK interfaceC2376kKListIterator = listIterator(0);
            InterfaceC2376kK interfaceC2376kKListIterator2 = ((Y0) ((InterfaceC2298jK) list)).listIterator(0);
            while (interfaceC2376kKListIterator.hasNext() && interfaceC2376kKListIterator2.hasNext()) {
                int iCompareTo = ((Comparable) interfaceC2376kKListIterator.next()).compareTo(interfaceC2376kKListIterator2.next());
                if (iCompareTo != 0) {
                    return iCompareTo;
                }
            }
            if (interfaceC2376kKListIterator2.hasNext()) {
                return -1;
            }
            return interfaceC2376kKListIterator.hasNext() ? 1 : 0;
        }
        InterfaceC2376kK interfaceC2376kKListIterator3 = listIterator(0);
        ListIterator listIterator = list.listIterator();
        while (interfaceC2376kKListIterator3.hasNext() && listIterator.hasNext()) {
            int iCompareTo2 = ((Comparable) interfaceC2376kKListIterator3.next()).compareTo(listIterator.next());
            if (iCompareTo2 != 0) {
                return iCompareTo2;
            }
        }
        if (listIterator.hasNext()) {
            return -1;
        }
        return interfaceC2376kKListIterator3.hasNext() ? 1 : 0;
    }

    public boolean addAll(int i, Collection collection) {
        j(i);
        Iterator it = collection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            add(i, it.next());
            i++;
        }
        return zHasNext;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        a(0, size());
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // java.util.List
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public X0 subList(int i, int i2) {
        j(i);
        j(i2);
        if (i <= i2) {
            return new X0(this, i, i2);
        }
        throw new IndexOutOfBoundsException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
    }

    @Override // java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        boolean zEquals;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        List list = (List) obj;
        int size = size();
        if (size != list.size()) {
            return false;
        }
        InterfaceC2376kK interfaceC2376kKListIterator = listIterator(0);
        ListIterator listIterator = list.listIterator();
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return true;
            }
            Object next = interfaceC2376kKListIterator.next();
            Object next2 = listIterator.next();
            if (next == null) {
                zEquals = next2 == null;
            } else {
                zEquals = next.equals(next2);
            }
            if (!zEquals) {
                return false;
            }
            size = i;
        }
    }

    @Override // java.util.Collection, java.util.List
    public final int hashCode() {
        InterfaceC2376kK interfaceC2376kKListIterator = listIterator(0);
        int size = size();
        int iHashCode = 1;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iHashCode;
            }
            E next = interfaceC2376kKListIterator.next();
            iHashCode = (iHashCode * 31) + (next == 0 ? 0 : next.hashCode());
            size = i;
        }
    }

    public int indexOf(Object obj) {
        InterfaceC2376kK interfaceC2376kKListIterator = listIterator(0);
        while (interfaceC2376kKListIterator.hasNext()) {
            Object next = interfaceC2376kKListIterator.next();
            if (obj == null) {
                if (next == null) {
                    return interfaceC2376kKListIterator.previousIndex();
                }
            } else if (obj.equals(next)) {
                return interfaceC2376kKListIterator.previousIndex();
            }
        }
        return -1;
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public InterfaceC1912eK iterator() {
        return listIterator(0);
    }

    protected final void j(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is negative");
        }
        if (i > size()) {
            throw new IndexOutOfBoundsException(AbstractC1588a5.a("Index (", i, ") is greater than list size (").append(size()).append(Constant.AFTER_QUTO).toString());
        }
    }

    protected final void k(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is negative");
        }
        if (i >= size()) {
            throw new IndexOutOfBoundsException(AbstractC1588a5.a("Index (", i, ") is greater than or equal to list size (").append(size()).append(Constant.AFTER_QUTO).toString());
        }
    }

    @Override // java.util.List
    /* JADX INFO: renamed from: l, reason: merged with bridge method [inline-methods] */
    public abstract InterfaceC2376kK listIterator(int i);

    public int lastIndexOf(Object obj) {
        InterfaceC2376kK interfaceC2376kKListIterator = listIterator(size());
        while (interfaceC2376kKListIterator.hasPrevious()) {
            Object objPrevious = interfaceC2376kKListIterator.previous();
            if (obj == null) {
                if (objPrevious == null) {
                    return interfaceC2376kKListIterator.nextIndex();
                }
            } else if (obj.equals(objPrevious)) {
                return interfaceC2376kKListIterator.nextIndex();
            }
        }
        return -1;
    }

    public ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection
    public final String toString() {
        StringBuilder sb = new StringBuilder("[");
        InterfaceC2376kK interfaceC2376kKListIterator = listIterator(0);
        int size = size();
        boolean z = true;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                sb.append("]");
                return sb.toString();
            }
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            E next = interfaceC2376kKListIterator.next();
            if (this == next) {
                sb.append("(this list)");
            } else {
                sb.append(String.valueOf(next));
            }
            size = i;
        }
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public Iterator iterator() {
        return listIterator(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        return addAll(size(), collection);
    }
}
