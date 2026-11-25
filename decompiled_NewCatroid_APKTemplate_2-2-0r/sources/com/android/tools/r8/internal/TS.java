package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class TS extends AbstractC1242Om {
    static final TS h = new TS(MS.d, C2838qG.a);
    private final transient AbstractC1639am g;

    TS(AbstractC1639am abstractC1639am, Comparator comparator) {
        super(comparator);
        this.g = abstractC1639am;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final int a(int i, Object[] objArr) {
        return this.g.a(i, objArr);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final Object[] b() {
        return this.g.b();
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final int c() {
        return this.g.c();
    }

    @Override // java.util.NavigableSet
    public final Object ceiling(Object obj) {
        int iC = c(obj, true);
        if (iC == this.g.size()) {
            return null;
        }
        return this.g.get(iC);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return Collections.binarySearch(this.g, obj, this.d) >= 0;
        } catch (ClassCastException unused) {
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean containsAll(Collection collection) {
        boolean zEquals;
        Comparator comparator;
        if (collection instanceof InterfaceC1600aG) {
            collection = ((InterfaceC1600aG) collection).I();
        }
        Comparator comparator2 = this.d;
        int i = AbstractC3123uM.a;
        comparator2.getClass();
        collection.getClass();
        if (!(collection instanceof SortedSet)) {
            if (collection instanceof IV) {
                comparator = ((IV) collection).comparator();
            } else {
                zEquals = false;
            }
            if (zEquals || collection.size() <= 1) {
                return super.containsAll(collection);
            }
            AbstractC2963s10 it = this.g.iterator();
            Iterator it2 = collection.iterator();
            if (!it.hasNext()) {
                return false;
            }
            Object next = it2.next();
            Object next2 = it.next();
            while (true) {
                try {
                    int iCompare = this.d.compare(next2, next);
                    if (iCompare < 0) {
                        if (!it.hasNext()) {
                            return false;
                        }
                        next2 = it.next();
                    } else if (iCompare == 0) {
                        if (!it2.hasNext()) {
                            return true;
                        }
                        next = it2.next();
                    } else if (iCompare > 0) {
                        break;
                    }
                } catch (ClassCastException | NullPointerException unused) {
                }
            }
            return false;
        }
        comparator = ((SortedSet) collection).comparator();
        if (comparator == null) {
            comparator = C2838qG.a;
        }
        zEquals = comparator2.equals(comparator);
        if (zEquals) {
        }
        return super.containsAll(collection);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final int d() {
        return this.g.d();
    }

    @Override // java.util.NavigableSet
    public final Iterator descendingIterator() {
        return this.g.j().iterator();
    }

    final TS e(int i, int i2) {
        if (i == 0 && i2 == this.g.size()) {
            return this;
        }
        if (i < i2) {
            return new TS(this.g.subList(i, i2), this.d);
        }
        Comparator comparator = this.d;
        return C2838qG.a.equals(comparator) ? h : new TS(MS.d, comparator);
    }

    /* JADX WARN: Code duplicated, block: B:25:0x004a  */
    /* JADX WARN: Code duplicated, block: B:29:0x005a A[Catch: ClassCastException | NoSuchElementException -> 0x006e, TryCatch #0 {ClassCastException | NoSuchElementException -> 0x006e, blocks: (B:26:0x004e, B:27:0x0054, B:29:0x005a, B:31:0x0064), top: B:38:0x004e }] */
    /* JADX WARN: Code duplicated, block: B:36:0x006f  */
    @Override // com.android.tools.r8.internal.AbstractC1048Hm, java.util.Collection, java.util.Set
    public final boolean equals(Object obj) {
        boolean zEquals;
        Comparator comparator;
        Iterator it;
        AbstractC2963s10 it2;
        Object next;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        if (this.g.size() != set.size()) {
            return false;
        }
        if (isEmpty()) {
            return true;
        }
        Comparator comparator2 = this.d;
        int i = AbstractC3123uM.a;
        comparator2.getClass();
        if (!(set instanceof SortedSet)) {
            if (set instanceof IV) {
                comparator = ((IV) set).comparator();
            } else {
                zEquals = false;
            }
            if (zEquals) {
                return containsAll(set);
            }
            it = set.iterator();
            try {
                it2 = this.g.iterator();
                while (it2.hasNext()) {
                    Object next2 = it2.next();
                    next = it.next();
                    if (next != null || this.d.compare(next2, next) != 0) {
                        return false;
                    }
                }
                return true;
            } catch (ClassCastException | NoSuchElementException unused) {
                return false;
            }
        }
        comparator = ((SortedSet) set).comparator();
        if (comparator == null) {
            comparator = C2838qG.a;
        }
        zEquals = comparator2.equals(comparator);
        if (zEquals) {
            return containsAll(set);
        }
        it = set.iterator();
        it2 = this.g.iterator();
        while (it2.hasNext()) {
            Object next3 = it2.next();
            next = it.next();
            if (next != null) {
            }
            return false;
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    /* JADX INFO: renamed from: f */
    public final AbstractC2963s10 iterator() {
        return this.g.iterator();
    }

    @Override // java.util.SortedSet
    public final Object first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.g.get(0);
    }

    @Override // java.util.NavigableSet
    public final Object floor(Object obj) {
        int iB = b(obj, true) - 1;
        if (iB == -1) {
            return null;
        }
        return this.g.get(iB);
    }

    @Override // java.lang.Iterable
    public final void forEach(Consumer consumer) {
        this.g.forEach(consumer);
    }

    @Override // java.util.NavigableSet
    public final Object higher(Object obj) {
        int iC = c(obj, false);
        if (iC == this.g.size()) {
            return null;
        }
        return this.g.get(iC);
    }

    @Override // com.android.tools.r8.internal.AbstractC1048Hm
    final AbstractC1639am i() {
        return this.g.size() <= 1 ? this.g : new C1077Im(this, this.g);
    }

    @Override // com.android.tools.r8.internal.AbstractC1242Om
    final int indexOf(Object obj) {
        if (obj == null) {
            return -1;
        }
        try {
            int iBinarySearch = Collections.binarySearch(this.g, obj, this.d);
            if (iBinarySearch >= 0) {
                return iBinarySearch;
            }
            return -1;
        } catch (ClassCastException unused) {
            return -1;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return this.g.iterator();
    }

    @Override // com.android.tools.r8.internal.AbstractC1242Om
    final AbstractC1242Om l() {
        TS ts;
        Comparator comparatorReverseOrder = Collections.reverseOrder(this.d);
        if (!isEmpty()) {
            ts = new TS(this.g.j(), comparatorReverseOrder);
        } else {
            if (C2838qG.a.equals(comparatorReverseOrder)) {
                return h;
            }
            ts = new TS(MS.d, comparatorReverseOrder);
        }
        return ts;
    }

    @Override // java.util.SortedSet
    public final Object last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        AbstractC1639am abstractC1639am = this.g;
        return abstractC1639am.get(abstractC1639am.size() - 1);
    }

    @Override // java.util.NavigableSet
    public final Object lower(Object obj) {
        int iB = b(obj, false) - 1;
        if (iB == -1) {
            return null;
        }
        return this.g.get(iB);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.g.size();
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Spliterator spliterator() {
        return a().spliterator();
    }

    @Override // com.android.tools.r8.internal.AbstractC1242Om
    final AbstractC1242Om a(Object obj, boolean z) {
        return e(c(obj, z), this.g.size());
    }

    final int b(Object obj, boolean z) {
        AbstractC1639am abstractC1639am = this.g;
        int i = AbstractC3123uM.a;
        obj.getClass();
        int iBinarySearch = Collections.binarySearch(abstractC1639am, obj, this.d);
        if (iBinarySearch >= 0) {
            return z ? iBinarySearch + 1 : iBinarySearch;
        }
        return ~iBinarySearch;
    }

    final int c(Object obj, boolean z) {
        AbstractC1639am abstractC1639am = this.g;
        int i = AbstractC3123uM.a;
        obj.getClass();
        int iBinarySearch = Collections.binarySearch(abstractC1639am, obj, this.d);
        if (iBinarySearch >= 0) {
            return z ? iBinarySearch : iBinarySearch + 1;
        }
        return ~iBinarySearch;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return this.g.e();
    }
}
