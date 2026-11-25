package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Bo, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0864Bo extends AbstractC1658b1 implements InterfaceC1328Ro {
    final /* synthetic */ C0964Eo a;

    private C0864Bo(C0964Eo c0964Eo) {
        this.a = c0964Eo;
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
        C0964Eo c0964Eo;
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
            C0964Eo c0964Eo2 = this.a;
            return c0964Eo2.e && c0964Eo2.c[c0964Eo2.i] == iIntValue2;
        }
        int[] iArr = this.a.b;
        int iA = AbstractC3307wk.a(iIntValue);
        C0964Eo c0964Eo3 = this.a;
        int i2 = iA & c0964Eo3.d;
        int i3 = iArr[i2];
        if (i3 == 0) {
            return false;
        }
        if (iIntValue == i3) {
            return c0964Eo3.c[i2] == iIntValue2;
        }
        do {
            c0964Eo = this.a;
            i2 = (i2 + 1) & c0964Eo.d;
            i = iArr[i2];
            if (i == 0) {
                return false;
            }
        } while (iIntValue != i);
        return c0964Eo.c[i2] == iIntValue2;
    }

    @Override // java.util.SortedSet
    public final Object first() {
        C0964Eo c0964Eo = this.a;
        if (c0964Eo.k != 0) {
            return new C0838Ao(c0964Eo, c0964Eo.f);
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new C3388xo(this.a);
    }

    @Override // java.util.SortedSet
    public final Object last() {
        C0964Eo c0964Eo = this.a;
        if (c0964Eo.k != 0) {
            return new C0838Ao(c0964Eo, c0964Eo.g);
        }
        throw new NoSuchElementException();
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
            C0964Eo c0964Eo = this.a;
            if (!c0964Eo.e || c0964Eo.c[c0964Eo.i] != iIntValue2) {
                return false;
            }
            C0964Eo.a(c0964Eo);
            return true;
        }
        int[] iArr = this.a.b;
        int iA = AbstractC3307wk.a(iIntValue);
        C0964Eo c0964Eo2 = this.a;
        int i = iA & c0964Eo2.d;
        int i2 = iArr[i];
        if (i2 == 0) {
            return false;
        }
        if (i2 == iIntValue) {
            if (c0964Eo2.c[i] != iIntValue2) {
                return false;
            }
            c0964Eo2.h(i);
            return true;
        }
        while (true) {
            C0964Eo c0964Eo3 = this.a;
            i = (i + 1) & c0964Eo3.d;
            int i3 = iArr[i];
            if (i3 == 0) {
                return false;
            }
            if (i3 == iIntValue && c0964Eo3.c[i] == iIntValue2) {
                c0964Eo3.h(i);
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

    /* synthetic */ C0864Bo(C0964Eo c0964Eo, int i) {
        this(c0964Eo);
    }
}
