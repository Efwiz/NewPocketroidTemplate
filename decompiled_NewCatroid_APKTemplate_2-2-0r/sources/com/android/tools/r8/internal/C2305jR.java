package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2305jR extends AbstractC1578a1 implements ZQ {
    final /* synthetic */ C2537mR a;

    private C2305jR(C2537mR c2537mR) {
        this.a = c2537mR;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        C2537mR c2537mR;
        Object obj2;
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
            return false;
        }
        Object key = entry.getKey();
        boolean zBooleanValue = ((Boolean) entry.getValue()).booleanValue();
        if (key == null) {
            C2537mR c2537mR2 = this.a;
            return c2537mR2.d && c2537mR2.b[c2537mR2.e] == zBooleanValue;
        }
        Object[] objArr = this.a.a;
        int iA = AbstractC3307wk.a(System.identityHashCode(key));
        C2537mR c2537mR3 = this.a;
        int i = iA & c2537mR3.c;
        Object obj3 = objArr[i];
        if (obj3 == null) {
            return false;
        }
        if (key == obj3) {
            return c2537mR3.b[i] == zBooleanValue;
        }
        do {
            c2537mR = this.a;
            i = (i + 1) & c2537mR.c;
            obj2 = objArr[i];
            if (obj2 == null) {
                return false;
            }
        } while (key != obj2);
        return c2537mR.b[i] == zBooleanValue;
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final InterfaceC1912eK iterator() {
        return new C2001fR(this.a, 0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
            return false;
        }
        Object key = entry.getKey();
        boolean zBooleanValue = ((Boolean) entry.getValue()).booleanValue();
        if (key == null) {
            C2537mR c2537mR = this.a;
            if (!c2537mR.d || c2537mR.b[c2537mR.e] != zBooleanValue) {
                return false;
            }
            c2537mR.e();
            return true;
        }
        Object[] objArr = this.a.a;
        int iA = AbstractC3307wk.a(System.identityHashCode(key));
        C2537mR c2537mR2 = this.a;
        int i = iA & c2537mR2.c;
        Object obj2 = objArr[i];
        if (obj2 == null) {
            return false;
        }
        if (obj2 == key) {
            if (c2537mR2.b[i] != zBooleanValue) {
                return false;
            }
            c2537mR2.e(i);
            return true;
        }
        while (true) {
            C2537mR c2537mR3 = this.a;
            i = (i + 1) & c2537mR3.c;
            Object obj3 = objArr[i];
            if (obj3 == null) {
                return false;
            }
            if (obj3 == key && c2537mR3.b[i] == zBooleanValue) {
                c2537mR3.e(i);
                return true;
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.g;
    }

    /* synthetic */ C2305jR(C2537mR c2537mR, int i) {
        this(c2537mR);
    }
}
