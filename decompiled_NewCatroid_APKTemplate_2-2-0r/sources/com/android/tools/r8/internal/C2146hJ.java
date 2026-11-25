package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hJ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2146hJ extends AbstractC1658b1 implements FJ {
    final /* synthetic */ C2375kJ a;

    private C2146hJ(C2375kJ c2375kJ) {
        this.a = c2375kJ;
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
        C2375kJ c2375kJ;
        Object obj2;
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        Object key = entry.getKey();
        Object value = entry.getValue();
        if (this.a.f.a(key, null)) {
            C2375kJ c2375kJ2 = this.a;
            if (!c2375kJ2.e) {
                return false;
            }
            Object obj3 = c2375kJ2.c[c2375kJ2.j];
            if (obj3 == null) {
                if (value != null) {
                    return false;
                }
            } else if (!obj3.equals(value)) {
                return false;
            }
            return true;
        }
        C2375kJ c2375kJ3 = this.a;
        Object[] objArr = c2375kJ3.b;
        int iA = AbstractC3307wk.a(c2375kJ3.f.a(key));
        C2375kJ c2375kJ4 = this.a;
        int i = iA & c2375kJ4.d;
        Object obj4 = objArr[i];
        if (obj4 == null) {
            return false;
        }
        if (c2375kJ4.f.a(key, obj4)) {
            Object obj5 = this.a.c[i];
            if (obj5 == null) {
                return value == null;
            }
            return obj5.equals(value);
        }
        do {
            c2375kJ = this.a;
            i = (i + 1) & c2375kJ.d;
            obj2 = objArr[i];
            if (obj2 == null) {
                return false;
            }
        } while (!c2375kJ.f.a(key, obj2));
        Object obj6 = this.a.c[i];
        if (obj6 == null) {
            return value == null;
        }
        return obj6.equals(value);
    }

    @Override // java.util.SortedSet
    public final Object first() {
        C2375kJ c2375kJ = this.a;
        if (c2375kJ.l != 0) {
            return new C2064gJ(c2375kJ, c2375kJ.g);
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new C1832dJ(this.a);
    }

    @Override // java.util.SortedSet
    public final Object last() {
        C2375kJ c2375kJ = this.a;
        if (c2375kJ.l != 0) {
            return new C2064gJ(c2375kJ, c2375kJ.h);
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        Object obj2;
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        Object key = entry.getKey();
        Object value = entry.getValue();
        if (this.a.f.a(key, null)) {
            C2375kJ c2375kJ = this.a;
            if (!c2375kJ.e || ((obj2 = c2375kJ.c[c2375kJ.j]) != null ? !obj2.equals(value) : value != null)) {
                return false;
            }
            C2375kJ.a(this.a);
            return true;
        }
        C2375kJ c2375kJ2 = this.a;
        Object[] objArr = c2375kJ2.b;
        int iA = AbstractC3307wk.a(c2375kJ2.f.a(key));
        C2375kJ c2375kJ3 = this.a;
        int i = iA & c2375kJ3.d;
        Object obj3 = objArr[i];
        if (obj3 == null) {
            return false;
        }
        if (c2375kJ3.f.a(obj3, key)) {
            Object obj4 = this.a.c[i];
            if (obj4 != null ? !obj4.equals(value) : value != null) {
                return false;
            }
            this.a.f(i);
            return true;
        }
        while (true) {
            C2375kJ c2375kJ4 = this.a;
            i = (i + 1) & c2375kJ4.d;
            Object obj5 = objArr[i];
            if (obj5 == null) {
                return false;
            }
            if (c2375kJ4.f.a(obj5, key)) {
                Object obj6 = this.a.c[i];
                if (obj6 == null) {
                    if (value == null) {
                        this.a.f(i);
                        return true;
                    }
                } else if (obj6.equals(value)) {
                    this.a.f(i);
                    return true;
                }
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.l;
    }

    @Override // java.util.SortedSet
    public final SortedSet subSet(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.SortedSet
    public final SortedSet tailSet(Object obj) {
        throw new UnsupportedOperationException();
    }

    /* synthetic */ C2146hJ(C2375kJ c2375kJ, int i) {
        this(c2375kJ);
    }
}
