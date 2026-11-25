package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class N extends J implements InterfaceC0967Er {
    protected N() {
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final int compareTo(List list) {
        if (list == this) {
            return 0;
        }
        if (list instanceof InterfaceC0967Er) {
            InterfaceC0993Fr interfaceC0993FrListIterator = listIterator(0);
            InterfaceC0993Fr interfaceC0993FrListIterator2 = ((InterfaceC0967Er) list).listIterator();
            while (interfaceC0993FrListIterator.hasNext() && interfaceC0993FrListIterator2.hasNext()) {
                int iCompare = Integer.compare(interfaceC0993FrListIterator.t(), interfaceC0993FrListIterator2.t());
                if (iCompare != 0) {
                    return iCompare;
                }
            }
            if (interfaceC0993FrListIterator2.hasNext()) {
                return -1;
            }
            return interfaceC0993FrListIterator.hasNext() ? 1 : 0;
        }
        InterfaceC0993Fr interfaceC0993FrListIterator3 = listIterator(0);
        ListIterator listIterator = list.listIterator();
        while (interfaceC0993FrListIterator3.hasNext() && listIterator.hasNext()) {
            int iCompareTo = ((Comparable) interfaceC0993FrListIterator3.next()).compareTo(listIterator.next());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        if (listIterator.hasNext()) {
            return -1;
        }
        return interfaceC0993FrListIterator3.hasNext() ? 1 : 0;
    }

    @Override // com.android.tools.r8.internal.J, com.android.tools.r8.internal.InterfaceC3088tr
    public abstract boolean a(int i);

    public abstract boolean a(int i, InterfaceC0967Er interfaceC0967Er);

    public void add(int i, Object obj) {
        b(i, ((Integer) obj).intValue());
    }

    public boolean addAll(int i, Collection collection) {
        k(i);
        Iterator it = collection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            b(i, ((Integer) it.next()).intValue());
            i++;
        }
        return zHasNext;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        a(0, size());
    }

    @Override // java.util.List
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public InterfaceC0967Er subList(int i, int i2) {
        k(i);
        k(i2);
        if (i <= i2) {
            return new M(this, i, i2);
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
        if (list instanceof InterfaceC0967Er) {
            InterfaceC0993Fr interfaceC0993FrListIterator = listIterator(0);
            InterfaceC0993Fr interfaceC0993FrListIterator2 = ((InterfaceC0967Er) list).listIterator();
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return true;
                }
                if (interfaceC0993FrListIterator.t() != interfaceC0993FrListIterator2.t()) {
                    return false;
                }
                size = i;
            }
        } else {
            InterfaceC0993Fr interfaceC0993FrListIterator3 = listIterator(0);
            ListIterator listIterator = list.listIterator();
            while (true) {
                int i2 = size - 1;
                if (size == 0) {
                    return true;
                }
                Object next = interfaceC0993FrListIterator3.next();
                Object next2 = listIterator.next();
                if (next == null) {
                    zEquals = next2 == null;
                } else {
                    zEquals = next.equals(next2);
                }
                if (!zEquals) {
                    return false;
                }
                size = i2;
            }
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC3088tr
    public final boolean f(int i) {
        return m(i) >= 0;
    }

    public Object get(int i) {
        return Integer.valueOf(i(i));
    }

    @Override // java.util.Collection, java.util.List
    public final int hashCode() {
        InterfaceC0993Fr interfaceC0993FrListIterator = listIterator(0);
        int size = size();
        int iT = 1;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iT;
            }
            iT = (iT * 31) + interfaceC0993FrListIterator.t();
            size = i;
        }
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        return m(((Integer) obj).intValue());
    }

    @Override // com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public InterfaceC0841Ar iterator() {
        return listIterator(0);
    }

    protected final void k(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is negative");
        }
        if (i > size()) {
            throw new IndexOutOfBoundsException(AbstractC1588a5.a("Index (", i, ") is greater than list size (").append(size()).append(Constant.AFTER_QUTO).toString());
        }
    }

    protected final void l(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is negative");
        }
        if (i >= size()) {
            throw new IndexOutOfBoundsException(AbstractC1588a5.a("Index (", i, ") is greater than or equal to list size (").append(size()).append(Constant.AFTER_QUTO).toString());
        }
    }

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        return n(((Integer) obj).intValue());
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er, java.util.List
    public final InterfaceC0993Fr listIterator() {
        return listIterator(0);
    }

    public int m(int i) {
        InterfaceC0993Fr interfaceC0993FrListIterator = listIterator(0);
        while (interfaceC0993FrListIterator.hasNext()) {
            if (i == interfaceC0993FrListIterator.t()) {
                return interfaceC0993FrListIterator.previousIndex();
            }
        }
        return -1;
    }

    public int n(int i) {
        InterfaceC0993Fr interfaceC0993FrListIterator = listIterator(size());
        while (interfaceC0993FrListIterator.hasPrevious()) {
            if (i == interfaceC0993FrListIterator.i()) {
                return interfaceC0993FrListIterator.nextIndex();
            }
        }
        return -1;
    }

    @Override // java.util.List
    /* JADX INFO: renamed from: o, reason: merged with bridge method [inline-methods] */
    public abstract InterfaceC0993Fr listIterator(int i);

    public Object remove(int i) {
        return Integer.valueOf(h(i));
    }

    public Object set(int i, Object obj) {
        return Integer.valueOf(c(i, ((Integer) obj).intValue()));
    }

    @Override // com.android.tools.r8.internal.J, java.util.AbstractCollection
    public final String toString() {
        StringBuilder sb = new StringBuilder("[");
        InterfaceC0993Fr interfaceC0993FrListIterator = listIterator(0);
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
            sb.append(String.valueOf(interfaceC0993FrListIterator.t()));
            size = i;
        }
    }

    @Override // com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final InterfaceC0993Fr iterator() {
        return listIterator(0);
    }

    public ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public Iterator iterator() {
        return listIterator(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        return addAll(size(), collection);
    }

    public boolean a(int i, InterfaceC3088tr interfaceC3088tr) {
        k(i);
        InterfaceC0841Ar it = interfaceC3088tr.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            b(i, it.t());
            i++;
        }
        return zHasNext;
    }

    public final boolean a(InterfaceC0967Er interfaceC0967Er) {
        return a(size(), interfaceC0967Er);
    }
}
