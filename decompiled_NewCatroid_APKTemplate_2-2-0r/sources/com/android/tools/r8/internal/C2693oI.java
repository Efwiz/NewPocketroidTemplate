package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oI, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2693oI extends AbstractC1578a1 {
    final /* synthetic */ C2769pI a;

    private C2693oI(C2769pI c2769pI) {
        this.a = c2769pI;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
            return false;
        }
        Object key = entry.getKey();
        return this.a.containsKey(key) && this.a.b(key) == ((Boolean) entry.getValue()).booleanValue();
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final InterfaceC1912eK iterator() {
        return new C2602nI(this);
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
        int iD = this.a.d(key);
        if (iD == -1 || zBooleanValue != this.a.b[iD]) {
            return false;
        }
        int i = (this.a.c - iD) - 1;
        int i2 = iD + 1;
        System.arraycopy(this.a.a, i2, this.a.a, iD, i);
        System.arraycopy(this.a.b, i2, this.a.b, iD, i);
        C2769pI.b(this.a);
        this.a.a[this.a.c] = null;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.c;
    }

    /* synthetic */ C2693oI(C2769pI c2769pI, int i) {
        this(c2769pI);
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final Iterator iterator() {
        return new C2602nI(this);
    }
}
