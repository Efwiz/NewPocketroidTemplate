package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.so, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3015so extends AbstractC1578a1 implements InterfaceC1019Go {
    final /* synthetic */ C3085to a;

    private C3015so(C3085to c3085to) {
        this.a = c3085to;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getKey() == null || !(entry.getKey() instanceof Integer) || entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
            return false;
        }
        int iIntValue = ((Integer) entry.getKey()).intValue();
        return this.a.a(iIntValue) && this.a.get(iIntValue) == ((Integer) entry.getValue()).intValue();
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final InterfaceC1912eK iterator() {
        return new C2946ro(this);
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
        int iA = C3085to.a(this.a, iIntValue);
        if (iA == -1 || iIntValue2 != this.a.c[iA]) {
            return false;
        }
        int i = (this.a.d - iA) - 1;
        int i2 = iA + 1;
        System.arraycopy(this.a.b, i2, this.a.b, iA, i);
        System.arraycopy(this.a.c, i2, this.a.c, iA, i);
        C3085to.b(this.a);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.d;
    }

    /* synthetic */ C3015so(C3085to c3085to, int i) {
        this(c3085to);
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final Iterator iterator() {
        return new C2946ro(this);
    }
}
