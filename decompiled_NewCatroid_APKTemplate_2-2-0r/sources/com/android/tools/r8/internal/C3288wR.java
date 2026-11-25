package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3288wR extends AbstractC1658b1 implements NR {
    final /* synthetic */ C3505zR a;

    private C3288wR(C3505zR c3505zR) {
        this.a = c3505zR;
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
        C3505zR c3505zR;
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
            C3505zR c3505zR2 = this.a;
            return c3505zR2.e && c3505zR2.c[c3505zR2.i] == iIntValue;
        }
        Object[] objArr = this.a.b;
        int iA = AbstractC3307wk.a(System.identityHashCode(key));
        C3505zR c3505zR3 = this.a;
        int i = iA & c3505zR3.d;
        Object obj3 = objArr[i];
        if (obj3 == null) {
            return false;
        }
        if (key == obj3) {
            return c3505zR3.c[i] == iIntValue;
        }
        do {
            c3505zR = this.a;
            i = (i + 1) & c3505zR.d;
            obj2 = objArr[i];
            if (obj2 == null) {
                return false;
            }
        } while (key != obj2);
        return c3505zR.c[i] == iIntValue;
    }

    @Override // java.util.SortedSet
    public final Object first() {
        C3505zR c3505zR = this.a;
        if (c3505zR.k != 0) {
            return new C3210vR(c3505zR, c3505zR.f);
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new C2990sR(this.a);
    }

    @Override // java.util.SortedSet
    public final Object last() {
        C3505zR c3505zR = this.a;
        if (c3505zR.k != 0) {
            return new C3210vR(c3505zR, c3505zR.g);
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
            C3505zR c3505zR = this.a;
            if (!c3505zR.e || c3505zR.c[c3505zR.i] != iIntValue) {
                return false;
            }
            C3505zR.a(c3505zR);
            return true;
        }
        Object[] objArr = this.a.b;
        int iA = AbstractC3307wk.a(System.identityHashCode(key));
        C3505zR c3505zR2 = this.a;
        int i = iA & c3505zR2.d;
        Object obj2 = objArr[i];
        if (obj2 == null) {
            return false;
        }
        if (obj2 == key) {
            if (c3505zR2.c[i] != iIntValue) {
                return false;
            }
            c3505zR2.h(i);
            return true;
        }
        while (true) {
            C3505zR c3505zR3 = this.a;
            i = (i + 1) & c3505zR3.d;
            Object obj3 = objArr[i];
            if (obj3 == null) {
                return false;
            }
            if (obj3 == key && c3505zR3.c[i] == iIntValue) {
                c3505zR3.h(i);
                return true;
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

    /* synthetic */ C3288wR(C3505zR c3505zR, int i) {
        this(c3505zR);
    }
}
