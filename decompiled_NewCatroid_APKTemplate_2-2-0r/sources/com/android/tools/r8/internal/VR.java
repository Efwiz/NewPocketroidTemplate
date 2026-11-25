package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class VR extends AbstractC1578a1 implements PR {
    final /* synthetic */ YR a;

    private VR(YR yr) {
        this.a = yr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        YR yr;
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
            YR yr2 = this.a;
            return yr2.d && yr2.b[yr2.e] == jLongValue;
        }
        Object[] objArr = this.a.a;
        int iA = AbstractC3307wk.a(System.identityHashCode(key));
        YR yr3 = this.a;
        int i = iA & yr3.c;
        Object obj3 = objArr[i];
        if (obj3 == null) {
            return false;
        }
        if (key == obj3) {
            return yr3.b[i] == jLongValue;
        }
        do {
            yr = this.a;
            i = (i + 1) & yr.c;
            obj2 = objArr[i];
            if (obj2 == null) {
                return false;
            }
        } while (key != obj2);
        return yr.b[i] == jLongValue;
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final InterfaceC1912eK iterator() {
        return new RR(this.a, 0);
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
            YR yr = this.a;
            if (!yr.d || yr.b[yr.e] != jLongValue) {
                return false;
            }
            YR.a(yr);
            return true;
        }
        Object[] objArr = this.a.a;
        int iA = AbstractC3307wk.a(System.identityHashCode(key));
        YR yr2 = this.a;
        int i = iA & yr2.c;
        Object obj2 = objArr[i];
        if (obj2 == null) {
            return false;
        }
        if (obj2 == key) {
            if (yr2.b[i] != jLongValue) {
                return false;
            }
            yr2.e(i);
            return true;
        }
        while (true) {
            YR yr3 = this.a;
            i = (i + 1) & yr3.c;
            Object obj3 = objArr[i];
            if (obj3 == null) {
                return false;
            }
            if (obj3 == key && yr3.b[i] == jLongValue) {
                yr3.e(i);
                return true;
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.g;
    }

    /* synthetic */ VR(YR yr, int i) {
        this(yr);
    }
}
