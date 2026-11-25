package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2942rk extends PD {
    final /* synthetic */ C3011sk b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C2942rk(C3011sk c3011sk) {
        super(c3011sk);
        this.b = c3011sk;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new C2870qk(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        C2328jk c2328jkA = this.b.a(AbstractC0900Ck.a(obj), obj);
        if (c2328jkA == null) {
            return false;
        }
        this.b.a(c2328jkA);
        c2328jkA.h = null;
        c2328jkA.g = null;
        return true;
    }
}
