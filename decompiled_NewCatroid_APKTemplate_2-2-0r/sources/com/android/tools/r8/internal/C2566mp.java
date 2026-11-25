package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mp, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2566mp extends AbstractC1578a1 implements InterfaceC2877qp {
    final /* synthetic */ C2642np a;

    private C2566mp(C2642np c2642np) {
        this.a = c2642np;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
            return false;
        }
        int iIntValue = ((Integer) entry.getKey()).intValue();
        if (!this.a.a(iIntValue)) {
            return false;
        }
        if (this.a.get(iIntValue) == null) {
            if (entry.getValue() != null) {
                return false;
            }
        } else if (!this.a.get(iIntValue).equals(entry.getValue())) {
            return false;
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final InterfaceC1912eK iterator() {
        return new C2489lp(this);
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
        int iA = C2642np.a(this.a, iIntValue);
        if (iA == -1) {
            return false;
        }
        if (value == null) {
            if (this.a.c[iA] != null) {
                return false;
            }
        } else if (!value.equals(this.a.c[iA])) {
            return false;
        }
        int i = (this.a.d - iA) - 1;
        int i2 = iA + 1;
        System.arraycopy(this.a.b, i2, this.a.b, iA, i);
        System.arraycopy(this.a.c, i2, this.a.c, iA, i);
        C2642np.b(this.a);
        this.a.c[this.a.d] = null;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.d;
    }

    /* synthetic */ C2566mp(C2642np c2642np, int i) {
        this(c2642np);
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final Iterator iterator() {
        return new C2489lp(this);
    }
}
