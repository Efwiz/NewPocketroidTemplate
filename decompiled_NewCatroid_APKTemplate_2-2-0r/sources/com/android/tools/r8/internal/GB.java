package com.android.tools.r8.internal;

import java.util.AbstractSet;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class GB extends AbstractSet {
    final /* synthetic */ JB a;

    GB(JB jb) {
        this.a = jb;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return this.a.containsKey(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new FB(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        IB ibA;
        JB jb = this.a;
        jb.getClass();
        if (obj != null) {
            try {
                ibA = jb.a(obj, false);
            } catch (ClassCastException unused) {
                ibA = null;
            }
        } else {
            ibA = null;
        }
        if (ibA != null) {
            jb.b(ibA, true);
        }
        return ibA != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.c;
    }
}
