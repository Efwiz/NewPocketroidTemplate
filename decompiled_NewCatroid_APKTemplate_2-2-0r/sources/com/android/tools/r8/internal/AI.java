package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class AI extends AbstractC1658b1 implements QI {
    final /* synthetic */ DI a;

    private AI(DI di) {
        this.a = di;
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
        if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
            return false;
        }
        Object key = entry.getKey();
        int iIntValue = ((Integer) entry.getValue()).intValue();
        if (key == null) {
            DI di = this.a;
            return di.e && di.c[di.i] == iIntValue;
        }
        Object[] objArr = this.a.b;
        int iA = AbstractC3307wk.a(key.hashCode()) & this.a.d;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return false;
        }
        if (key.equals(obj3)) {
            return this.a.c[iA] == iIntValue;
        }
        do {
            iA = (iA + 1) & this.a.d;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return false;
            }
        } while (!key.equals(obj2));
        return this.a.c[iA] == iIntValue;
    }

    @Override // java.util.SortedSet
    public final Object first() {
        DI di = this.a;
        if (di.k != 0) {
            return new C3495zI(di, di.f);
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new C3271wI(this.a);
    }

    @Override // java.util.SortedSet
    public final Object last() {
        DI di = this.a;
        if (di.k != 0) {
            return new C3495zI(di, di.g);
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
            return false;
        }
        Object key = entry.getKey();
        int iIntValue = ((Integer) entry.getValue()).intValue();
        if (key == null) {
            DI di = this.a;
            if (!di.e || di.c[di.i] != iIntValue) {
                return false;
            }
            DI.a(di);
            return true;
        }
        Object[] objArr = this.a.b;
        int iA = AbstractC3307wk.a(key.hashCode()) & this.a.d;
        Object obj2 = objArr[iA];
        if (obj2 == null) {
            return false;
        }
        if (obj2.equals(key)) {
            DI di2 = this.a;
            if (di2.c[iA] != iIntValue) {
                return false;
            }
            di2.h(iA);
            return true;
        }
        while (true) {
            iA = (iA + 1) & this.a.d;
            Object obj3 = objArr[iA];
            if (obj3 == null) {
                return false;
            }
            if (obj3.equals(key)) {
                DI di3 = this.a;
                if (di3.c[iA] == iIntValue) {
                    di3.h(iA);
                    return true;
                }
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.k;
    }

    @Override // java.util.SortedSet
    public final SortedSet subSet(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.SortedSet
    public final SortedSet tailSet(Object obj) {
        throw new UnsupportedOperationException();
    }

    /* synthetic */ AI(DI di, int i) {
        this(di);
    }
}
