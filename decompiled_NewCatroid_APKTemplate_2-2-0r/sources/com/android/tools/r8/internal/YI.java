package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class YI extends AbstractC1578a1 implements SI {
    final /* synthetic */ C1684bJ a;

    private YI(C1684bJ c1684bJ) {
        this.a = c1684bJ;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        Object obj2;
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
            return false;
        }
        Object key = entry.getKey();
        long jLongValue = ((Long) entry.getValue()).longValue();
        if (key == null) {
            C1684bJ c1684bJ = this.a;
            return c1684bJ.d && c1684bJ.b[c1684bJ.e] == jLongValue;
        }
        Object[] objArr = this.a.a;
        int iA = AbstractC3307wk.a(key.hashCode()) & this.a.c;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return false;
        }
        if (key.equals(obj3)) {
            return this.a.b[iA] == jLongValue;
        }
        do {
            iA = (iA + 1) & this.a.c;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return false;
            }
        } while (!key.equals(obj2));
        return this.a.b[iA] == jLongValue;
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final InterfaceC1912eK iterator() {
        return new UI(this.a, 0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
            return false;
        }
        Object key = entry.getKey();
        long jLongValue = ((Long) entry.getValue()).longValue();
        if (key == null) {
            C1684bJ c1684bJ = this.a;
            if (!c1684bJ.d || c1684bJ.b[c1684bJ.e] != jLongValue) {
                return false;
            }
            C1684bJ.a(c1684bJ);
            return true;
        }
        Object[] objArr = this.a.a;
        int iA = AbstractC3307wk.a(key.hashCode()) & this.a.c;
        Object obj2 = objArr[iA];
        if (obj2 == null) {
            return false;
        }
        if (obj2.equals(key)) {
            C1684bJ c1684bJ2 = this.a;
            if (c1684bJ2.b[iA] != jLongValue) {
                return false;
            }
            c1684bJ2.e(iA);
            return true;
        }
        while (true) {
            iA = (iA + 1) & this.a.c;
            Object obj3 = objArr[iA];
            if (obj3 == null) {
                return false;
            }
            if (obj3.equals(key)) {
                C1684bJ c1684bJ3 = this.a;
                if (c1684bJ3.b[iA] == jLongValue) {
                    c1684bJ3.e(iA);
                    return true;
                }
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.g;
    }

    /* synthetic */ YI(C1684bJ c1684bJ, int i) {
        this(c1684bJ);
    }
}
