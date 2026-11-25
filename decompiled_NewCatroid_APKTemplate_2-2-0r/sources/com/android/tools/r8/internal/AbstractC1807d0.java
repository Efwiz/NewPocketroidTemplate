package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.d0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1807d0 extends AbstractC1576a0 implements YC {
    protected AbstractC1807d0() {
    }

    @Override // com.android.tools.r8.internal.AbstractC1576a0
    /* JADX INFO: renamed from: a */
    public XC iterator() {
        return listIterator(0);
    }

    public void add(int i, Object obj) {
        a(i, ((Long) obj).longValue());
    }

    public boolean addAll(int i, Collection collection) {
        j(i);
        Iterator it = collection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            a(i, ((Long) it.next()).longValue());
            i++;
        }
        return zHasNext;
    }

    @Override // com.android.tools.r8.internal.AbstractC1576a0
    public final boolean b(long j) {
        return d(j) >= 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        a(0, size());
    }

    public int d(long j) {
        AbstractC1880e0 abstractC1880e0ListIterator = listIterator(0);
        while (abstractC1880e0ListIterator.hasNext()) {
            if (j == abstractC1880e0ListIterator.c()) {
                return abstractC1880e0ListIterator.previousIndex();
            }
        }
        return -1;
    }

    public int e(long j) {
        AbstractC1880e0 abstractC1880e0ListIterator = listIterator(size());
        while (abstractC1880e0ListIterator.hasPrevious()) {
            if (j == abstractC1880e0ListIterator.e()) {
                return abstractC1880e0ListIterator.nextIndex();
            }
        }
        return -1;
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
        if (list instanceof YC) {
            AbstractC1880e0 abstractC1880e0ListIterator = listIterator(0);
            AbstractC1880e0 abstractC1880e0ListIterator2 = ((AbstractC1807d0) ((YC) list)).listIterator(0);
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return true;
                }
                if (abstractC1880e0ListIterator.c() != abstractC1880e0ListIterator2.c()) {
                    return false;
                }
                size = i;
            }
        } else {
            AbstractC1880e0 abstractC1880e0ListIterator3 = listIterator(0);
            ListIterator listIterator = list.listIterator();
            while (true) {
                int i2 = size - 1;
                if (size == 0) {
                    return true;
                }
                Long lValueOf = Long.valueOf(abstractC1880e0ListIterator3.c());
                Object next = listIterator.next();
                if (lValueOf == null) {
                    zEquals = next == null;
                } else {
                    zEquals = lValueOf.equals(next);
                }
                if (!zEquals) {
                    return false;
                }
                size = i2;
            }
        }
    }

    public Object get(int i) {
        return Long.valueOf(b(i));
    }

    @Override // java.util.Collection, java.util.List
    public final int hashCode() {
        AbstractC1880e0 abstractC1880e0ListIterator = listIterator(0);
        int size = size();
        int iA = 1;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iA;
            }
            iA = (iA * 31) + AbstractC3307wk.a(abstractC1880e0ListIterator.c());
            size = i;
        }
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        return d(((Long) obj).longValue());
    }

    @Override // com.android.tools.r8.internal.AbstractC1576a0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator iterator() {
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
    public abstract AbstractC1880e0 listIterator(int i);

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        return e(((Long) obj).longValue());
    }

    public ListIterator listIterator() {
        return listIterator(0);
    }

    public Object remove(int i) {
        return Long.valueOf(e(i));
    }

    public Object set(int i, Object obj) {
        return Long.valueOf(b(i, ((Long) obj).longValue()));
    }

    @Override // com.android.tools.r8.internal.AbstractC1576a0, java.util.AbstractCollection
    public final String toString() {
        StringBuilder sb = new StringBuilder("[");
        AbstractC1880e0 abstractC1880e0ListIterator = listIterator(0);
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
            sb.append(String.valueOf(abstractC1880e0ListIterator.c()));
            size = i;
        }
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final int compareTo(List list) {
        if (list == this) {
            return 0;
        }
        if (list instanceof YC) {
            AbstractC1880e0 abstractC1880e0ListIterator = listIterator(0);
            AbstractC1880e0 abstractC1880e0ListIterator2 = ((AbstractC1807d0) ((YC) list)).listIterator(0);
            while (abstractC1880e0ListIterator.hasNext() && abstractC1880e0ListIterator2.hasNext()) {
                int iCompare = Long.compare(abstractC1880e0ListIterator.c(), abstractC1880e0ListIterator2.c());
                if (iCompare != 0) {
                    return iCompare;
                }
            }
            if (abstractC1880e0ListIterator2.hasNext()) {
                return -1;
            }
            return abstractC1880e0ListIterator.hasNext() ? 1 : 0;
        }
        AbstractC1880e0 abstractC1880e0ListIterator3 = listIterator(0);
        ListIterator listIterator = list.listIterator();
        while (abstractC1880e0ListIterator3.hasNext() && listIterator.hasNext()) {
            int iCompareTo = Long.valueOf(abstractC1880e0ListIterator3.c()).compareTo(listIterator.next());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        if (listIterator.hasNext()) {
            return -1;
        }
        return abstractC1880e0ListIterator3.hasNext() ? 1 : 0;
    }

    @Override // java.util.List
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public C1730c0 subList(int i, int i2) {
        j(i);
        j(i2);
        if (i <= i2) {
            return new C1730c0(this, i, i2);
        }
        throw new IndexOutOfBoundsException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        return addAll(size(), collection);
    }
}
