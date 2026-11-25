package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.u1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3100u1 extends AbstractC2891r1 implements InterfaceC2991sS {
    protected AbstractC3100u1() {
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
    public C3031t1 subList(int i, int i2) {
        j(i);
        j(i2);
        if (i <= i2) {
            return new C3031t1(this, i, i2);
        }
        throw new IndexOutOfBoundsException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
    }

    @Override // java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
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
            if (interfaceC2376kKListIterator.next() != listIterator.next()) {
                return false;
            }
            size = i;
        }
    }

    @Override // java.util.Collection, java.util.List
    public final int hashCode() {
        InterfaceC2376kK interfaceC2376kKListIterator = listIterator(0);
        int size = size();
        int iIdentityHashCode = 1;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iIdentityHashCode;
            }
            iIdentityHashCode = (iIdentityHashCode * 31) + System.identityHashCode(interfaceC2376kKListIterator.next());
            size = i;
        }
    }

    public int indexOf(Object obj) {
        InterfaceC2376kK interfaceC2376kKListIterator = listIterator(0);
        while (interfaceC2376kKListIterator.hasNext()) {
            if (obj == interfaceC2376kKListIterator.next()) {
                return interfaceC2376kKListIterator.previousIndex();
            }
        }
        return -1;
    }

    @Override // com.android.tools.r8.internal.AbstractC2891r1, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC2703oS
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
            if (obj == interfaceC2376kKListIterator.previous()) {
                return interfaceC2376kKListIterator.nextIndex();
            }
        }
        return -1;
    }

    public ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2891r1, java.util.AbstractCollection
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

    @Override // com.android.tools.r8.internal.AbstractC2891r1, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC2703oS
    public Iterator iterator() {
        return listIterator(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        return addAll(size(), collection);
    }
}
