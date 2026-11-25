package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Nq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1219Nq extends AbstractC1578a1 implements InterfaceC0966Eq {
    final /* synthetic */ C1303Qq a;

    private C1219Nq(C1303Qq c1303Qq) {
        this.a = c1303Qq;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        C1303Qq c1303Qq;
        int i;
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
            return false;
        }
        int iIntValue = ((Integer) entry.getKey()).intValue();
        Object value = entry.getValue();
        if (iIntValue == 0) {
            C1303Qq c1303Qq2 = this.a;
            return c1303Qq2.e && c1303Qq2.c[c1303Qq2.f] == value;
        }
        int[] iArr = this.a.b;
        int iA = AbstractC3307wk.a(iIntValue);
        C1303Qq c1303Qq3 = this.a;
        int i2 = iA & c1303Qq3.d;
        int i3 = iArr[i2];
        if (i3 == 0) {
            return false;
        }
        if (iIntValue == i3) {
            return c1303Qq3.c[i2] == value;
        }
        do {
            c1303Qq = this.a;
            i2 = (i2 + 1) & c1303Qq.d;
            i = iArr[i2];
            if (i == 0) {
                return false;
            }
        } while (iIntValue != i);
        return c1303Qq.c[i2] == value;
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final InterfaceC1912eK iterator() {
        return new C1111Jq(this.a, 0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
            return false;
        }
        int iIntValue = ((Integer) entry.getKey()).intValue();
        Object value = entry.getValue();
        if (iIntValue == 0) {
            C1303Qq c1303Qq = this.a;
            if (!c1303Qq.e || c1303Qq.c[c1303Qq.f] != value) {
                return false;
            }
            C1303Qq.a(c1303Qq);
            return true;
        }
        int[] iArr = this.a.b;
        int iA = AbstractC3307wk.a(iIntValue);
        C1303Qq c1303Qq2 = this.a;
        int i = iA & c1303Qq2.d;
        int i2 = iArr[i];
        if (i2 == 0) {
            return false;
        }
        if (i2 == iIntValue) {
            if (c1303Qq2.c[i] != value) {
                return false;
            }
            c1303Qq2.e(i);
            return true;
        }
        while (true) {
            C1303Qq c1303Qq3 = this.a;
            i = (i + 1) & c1303Qq3.d;
            int i3 = iArr[i];
            if (i3 == 0) {
                return false;
            }
            if (i3 == iIntValue && c1303Qq3.c[i] == value) {
                c1303Qq3.e(i);
                return true;
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.h;
    }

    /* synthetic */ C1219Nq(C1303Qq c1303Qq, int i) {
        this(c1303Qq);
    }
}
