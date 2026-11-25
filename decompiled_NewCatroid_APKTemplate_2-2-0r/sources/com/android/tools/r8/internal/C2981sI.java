package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sI, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2981sI extends AbstractC1578a1 implements FI {
    final /* synthetic */ C3050tI a;

    private C2981sI(C3050tI c3050tI) {
        this.a = c3050tI;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
            return false;
        }
        Object key = entry.getKey();
        return this.a.containsKey(key) && this.a.a(key) == ((Integer) entry.getValue()).intValue();
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final InterfaceC1912eK iterator() {
        return new C2910rI(this);
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
        int iE = this.a.e(key);
        if (iE == -1 || iIntValue != this.a.c[iE]) {
            return false;
        }
        int i = (this.a.d - iE) - 1;
        int i2 = iE + 1;
        System.arraycopy(this.a.b, i2, this.a.b, iE, i);
        System.arraycopy(this.a.c, i2, this.a.c, iE, i);
        C3050tI.b(this.a);
        this.a.b[this.a.d] = null;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.d;
    }

    /* synthetic */ C2981sI(C3050tI c3050tI, int i) {
        this(c3050tI);
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final Iterator iterator() {
        return new C2910rI(this);
    }
}
