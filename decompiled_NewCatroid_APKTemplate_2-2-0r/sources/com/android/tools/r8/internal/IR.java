package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class IR extends AbstractC1578a1 implements BR {
    final /* synthetic */ MR a;

    private IR(MR mr) {
        this.a = mr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        MR mr;
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
            MR mr2 = this.a;
            return mr2.e && mr2.c[mr2.f] == iIntValue;
        }
        Object[] objArr = this.a.b;
        int iA = AbstractC3307wk.a(System.identityHashCode(key));
        MR mr3 = this.a;
        int i = iA & mr3.d;
        Object obj3 = objArr[i];
        if (obj3 == null) {
            return false;
        }
        if (key == obj3) {
            return mr3.c[i] == iIntValue;
        }
        do {
            mr = this.a;
            i = (i + 1) & mr.d;
            obj2 = objArr[i];
            if (obj2 == null) {
                return false;
            }
        } while (key != obj2);
        return mr.c[i] == iIntValue;
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final InterfaceC1912eK iterator() {
        return new ER(this.a, 0);
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
            MR mr = this.a;
            if (!mr.e || mr.c[mr.f] != iIntValue) {
                return false;
            }
            MR.a(mr);
            return true;
        }
        Object[] objArr = this.a.b;
        int iA = AbstractC3307wk.a(System.identityHashCode(key));
        MR mr2 = this.a;
        int i = iA & mr2.d;
        Object obj2 = objArr[i];
        if (obj2 == null) {
            return false;
        }
        if (obj2 == key) {
            if (mr2.c[i] != iIntValue) {
                return false;
            }
            mr2.g(i);
            return true;
        }
        while (true) {
            MR mr3 = this.a;
            i = (i + 1) & mr3.d;
            Object obj3 = objArr[i];
            if (obj3 == null) {
                return false;
            }
            if (obj3 == key && mr3.c[i] == iIntValue) {
                mr3.g(i);
                return true;
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.h;
    }

    /* synthetic */ IR(MR mr, int i) {
        this(mr);
    }
}
