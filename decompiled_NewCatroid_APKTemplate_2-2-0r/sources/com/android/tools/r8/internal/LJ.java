package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class LJ extends AbstractC1658b1 implements RJ {
    final /* synthetic */ OJ a;

    private LJ(OJ oj) {
        this.a = oj;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.SortedSet
    public final Comparator comparator() {
        return null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        Object obj2;
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        Object key = entry.getKey();
        Object value = entry.getValue();
        if (key == null) {
            OJ oj = this.a;
            return oj.d && oj.b[oj.h] == value;
        }
        Object[] objArr = this.a.a;
        int iA = AbstractC3307wk.a(key.hashCode()) & this.a.c;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return false;
        }
        if (key.equals(obj3)) {
            return this.a.b[iA] == value;
        }
        do {
            iA = (iA + 1) & this.a.c;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return false;
            }
        } while (!key.equals(obj2));
        return this.a.b[iA] == value;
    }

    @Override // java.util.SortedSet
    public final Object first() {
        OJ oj = this.a;
        if (oj.j != 0) {
            return new KJ(oj, oj.e);
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new HJ(this.a);
    }

    @Override // java.util.SortedSet
    public final Object last() {
        OJ oj = this.a;
        if (oj.j != 0) {
            return new KJ(oj, oj.f);
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        Object key = entry.getKey();
        Object value = entry.getValue();
        if (key == null) {
            OJ oj = this.a;
            if (!oj.d || oj.b[oj.h] != value) {
                return false;
            }
            OJ.a(oj);
            return true;
        }
        Object[] objArr = this.a.a;
        int iA = AbstractC3307wk.a(key.hashCode()) & this.a.c;
        Object obj2 = objArr[iA];
        if (obj2 == null) {
            return false;
        }
        if (obj2.equals(key)) {
            OJ oj2 = this.a;
            if (oj2.b[iA] != value) {
                return false;
            }
            oj2.f(iA);
            return true;
        }
        while (true) {
            iA = (iA + 1) & this.a.c;
            Object obj3 = objArr[iA];
            if (obj3 == null) {
                return false;
            }
            if (obj3.equals(key)) {
                OJ oj3 = this.a;
                if (oj3.b[iA] == value) {
                    oj3.f(iA);
                    return true;
                }
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.j;
    }

    @Override // java.util.SortedSet
    public final SortedSet subSet(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.SortedSet
    public final SortedSet tailSet(Object obj) {
        throw new UnsupportedOperationException();
    }

    /* synthetic */ LJ(OJ oj, int i) {
        this(oj);
    }
}
