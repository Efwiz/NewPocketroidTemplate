package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2636nk extends PD {
    final /* synthetic */ C2725ok b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C2636nk(C2725ok c2725ok) {
        super(c2725ok);
        this.b = c2725ok;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new C2560mk(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        C2328jk c2328jkB = this.b.a.b(AbstractC0900Ck.a(obj), obj);
        if (c2328jkB == null) {
            return false;
        }
        this.b.a.a(c2328jkB);
        return true;
    }
}
