package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zp, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3529zp extends AbstractC1578a1 implements InterfaceC2877qp {
    final /* synthetic */ C0905Cp a;

    private C3529zp(C0905Cp c0905Cp) {
        this.a = c0905Cp;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        C0905Cp c0905Cp;
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
            C0905Cp c0905Cp2 = this.a;
            if (!c0905Cp2.e) {
                return false;
            }
            Object obj2 = c0905Cp2.c[c0905Cp2.f];
            if (obj2 == null) {
                if (value != null) {
                    return false;
                }
            } else if (!obj2.equals(value)) {
                return false;
            }
            return true;
        }
        int[] iArr = this.a.b;
        int iA = AbstractC3307wk.a(iIntValue);
        C0905Cp c0905Cp3 = this.a;
        int i2 = iA & c0905Cp3.d;
        int i3 = iArr[i2];
        if (i3 == 0) {
            return false;
        }
        if (iIntValue == i3) {
            Object obj3 = c0905Cp3.c[i2];
            if (obj3 == null) {
                return value == null;
            }
            return obj3.equals(value);
        }
        do {
            c0905Cp = this.a;
            i2 = (i2 + 1) & c0905Cp.d;
            i = iArr[i2];
            if (i == 0) {
                return false;
            }
        } while (iIntValue != i);
        Object obj4 = c0905Cp.c[i2];
        if (obj4 == null) {
            return value == null;
        }
        return obj4.equals(value);
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final InterfaceC1912eK iterator() {
        return new C3236vp(this.a, 0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        Object obj2;
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
            C0905Cp c0905Cp = this.a;
            if (!c0905Cp.e || ((obj2 = c0905Cp.c[c0905Cp.f]) != null ? !obj2.equals(value) : value != null)) {
                return false;
            }
            C0905Cp.a(this.a);
            return true;
        }
        int[] iArr = this.a.b;
        int iA = AbstractC3307wk.a(iIntValue);
        C0905Cp c0905Cp2 = this.a;
        int i = iA & c0905Cp2.d;
        int i2 = iArr[i];
        if (i2 == 0) {
            return false;
        }
        if (i2 == iIntValue) {
            Object obj3 = c0905Cp2.c[i];
            if (obj3 != null ? !obj3.equals(value) : value != null) {
                return false;
            }
            this.a.e(i);
            return true;
        }
        while (true) {
            C0905Cp c0905Cp3 = this.a;
            i = (i + 1) & c0905Cp3.d;
            int i3 = iArr[i];
            if (i3 == 0) {
                return false;
            }
            if (i3 == iIntValue) {
                Object obj4 = c0905Cp3.c[i];
                if (obj4 == null) {
                    if (value == null) {
                        this.a.e(i);
                        return true;
                    }
                } else if (obj4.equals(value)) {
                    this.a.e(i);
                    return true;
                }
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.h;
    }

    /* synthetic */ C3529zp(C0905Cp c0905Cp, int i) {
        this(c0905Cp);
    }
}
