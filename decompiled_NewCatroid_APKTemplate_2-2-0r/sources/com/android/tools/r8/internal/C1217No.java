package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.No, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1217No extends AbstractC1578a1 implements InterfaceC1019Go {
    final /* synthetic */ C1301Qo a;

    private C1217No(C1301Qo c1301Qo) {
        this.a = c1301Qo;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        C1301Qo c1301Qo;
        int i;
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getKey() == null || !(entry.getKey() instanceof Integer) || entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
            return false;
        }
        int iIntValue = ((Integer) entry.getKey()).intValue();
        int iIntValue2 = ((Integer) entry.getValue()).intValue();
        if (iIntValue == 0) {
            C1301Qo c1301Qo2 = this.a;
            return c1301Qo2.e && c1301Qo2.c[c1301Qo2.f] == iIntValue2;
        }
        int[] iArr = this.a.b;
        int iA = AbstractC3307wk.a(iIntValue);
        C1301Qo c1301Qo3 = this.a;
        int i2 = iA & c1301Qo3.d;
        int i3 = iArr[i2];
        if (i3 == 0) {
            return false;
        }
        if (iIntValue == i3) {
            return c1301Qo3.c[i2] == iIntValue2;
        }
        do {
            c1301Qo = this.a;
            i2 = (i2 + 1) & c1301Qo.d;
            i = iArr[i2];
            if (i == 0) {
                return false;
            }
        } while (iIntValue != i);
        return c1301Qo.c[i2] == iIntValue2;
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final InterfaceC1912eK iterator() {
        return new C1109Jo(this.a, 0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getKey() == null || !(entry.getKey() instanceof Integer) || entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
            return false;
        }
        int iIntValue = ((Integer) entry.getKey()).intValue();
        int iIntValue2 = ((Integer) entry.getValue()).intValue();
        if (iIntValue == 0) {
            C1301Qo c1301Qo = this.a;
            if (!c1301Qo.e || c1301Qo.c[c1301Qo.f] != iIntValue2) {
                return false;
            }
            C1301Qo.a(c1301Qo);
            return true;
        }
        int[] iArr = this.a.b;
        int iA = AbstractC3307wk.a(iIntValue);
        C1301Qo c1301Qo2 = this.a;
        int i = iA & c1301Qo2.d;
        int i2 = iArr[i];
        if (i2 == 0) {
            return false;
        }
        if (i2 == iIntValue) {
            if (c1301Qo2.c[i] != iIntValue2) {
                return false;
            }
            c1301Qo2.g(i);
            return true;
        }
        while (true) {
            C1301Qo c1301Qo3 = this.a;
            i = (i + 1) & c1301Qo3.d;
            int i3 = iArr[i];
            if (i3 == 0) {
                return false;
            }
            if (i3 == iIntValue && c1301Qo3.c[i] == iIntValue2) {
                c1301Qo3.g(i);
                return true;
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.h;
    }

    /* synthetic */ C1217No(C1301Qo c1301Qo, int i) {
        this(c1301Qo);
    }
}
