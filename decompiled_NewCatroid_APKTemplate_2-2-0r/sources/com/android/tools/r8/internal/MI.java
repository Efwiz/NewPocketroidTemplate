package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class MI extends AbstractC1578a1 implements FI {
    final /* synthetic */ PI a;

    private MI(PI pi) {
        this.a = pi;
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
        if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
            return false;
        }
        Object key = entry.getKey();
        int iIntValue = ((Integer) entry.getValue()).intValue();
        if (key == null) {
            PI pi = this.a;
            return pi.e && pi.c[pi.f] == iIntValue;
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

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final InterfaceC1912eK iterator() {
        return new II(this.a, 0);
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
            PI pi = this.a;
            if (!pi.e || pi.c[pi.f] != iIntValue) {
                return false;
            }
            PI.a(pi);
            return true;
        }
        Object[] objArr = this.a.b;
        int iA = AbstractC3307wk.a(key.hashCode()) & this.a.d;
        Object obj2 = objArr[iA];
        if (obj2 == null) {
            return false;
        }
        if (obj2.equals(key)) {
            PI pi2 = this.a;
            if (pi2.c[iA] != iIntValue) {
                return false;
            }
            pi2.g(iA);
            return true;
        }
        while (true) {
            iA = (iA + 1) & this.a.d;
            Object obj3 = objArr[iA];
            if (obj3 == null) {
                return false;
            }
            if (obj3.equals(key)) {
                PI pi3 = this.a;
                if (pi3.c[iA] == iIntValue) {
                    pi3.g(iA);
                    return true;
                }
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.h;
    }

    /* synthetic */ MI(PI pi, int i) {
        this(pi);
    }
}
