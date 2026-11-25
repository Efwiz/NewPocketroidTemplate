package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3530zq extends AbstractC1658b1 implements InterfaceC2335jr {
    final /* synthetic */ C0906Cq a;

    private C3530zq(C0906Cq c0906Cq) {
        this.a = c0906Cq;
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
        C0906Cq c0906Cq;
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
            C0906Cq c0906Cq2 = this.a;
            return c0906Cq2.e && c0906Cq2.c[c0906Cq2.i] == value;
        }
        int[] iArr = this.a.b;
        int iA = AbstractC3307wk.a(iIntValue);
        C0906Cq c0906Cq3 = this.a;
        int i2 = iA & c0906Cq3.d;
        int i3 = iArr[i2];
        if (i3 == 0) {
            return false;
        }
        if (iIntValue == i3) {
            return c0906Cq3.c[i2] == value;
        }
        do {
            c0906Cq = this.a;
            i2 = (i2 + 1) & c0906Cq.d;
            i = iArr[i2];
            if (i == 0) {
                return false;
            }
        } while (iIntValue != i);
        return c0906Cq.c[i2] == value;
    }

    @Override // java.util.SortedSet
    public final Object first() {
        C0906Cq c0906Cq = this.a;
        if (c0906Cq.k != 0) {
            return new C3460yq(c0906Cq, c0906Cq.f);
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new C3237vq(this.a);
    }

    @Override // java.util.SortedSet
    public final Object last() {
        C0906Cq c0906Cq = this.a;
        if (c0906Cq.k != 0) {
            return new C3460yq(c0906Cq, c0906Cq.g);
        }
        throw new NoSuchElementException();
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
            C0906Cq c0906Cq = this.a;
            if (!c0906Cq.e || c0906Cq.c[c0906Cq.i] != value) {
                return false;
            }
            C0906Cq.a(c0906Cq);
            return true;
        }
        int[] iArr = this.a.b;
        int iA = AbstractC3307wk.a(iIntValue);
        C0906Cq c0906Cq2 = this.a;
        int i = iA & c0906Cq2.d;
        int i2 = iArr[i];
        if (i2 == 0) {
            return false;
        }
        if (i2 == iIntValue) {
            if (c0906Cq2.c[i] != value) {
                return false;
            }
            c0906Cq2.f(i);
            return true;
        }
        while (true) {
            C0906Cq c0906Cq3 = this.a;
            i = (i + 1) & c0906Cq3.d;
            int i3 = iArr[i];
            if (i3 == 0) {
                return false;
            }
            if (i3 == iIntValue && c0906Cq3.c[i] == value) {
                c0906Cq3.f(i);
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

    /* synthetic */ C3530zq(C0906Cq c0906Cq, int i) {
        this(c0906Cq);
    }
}
