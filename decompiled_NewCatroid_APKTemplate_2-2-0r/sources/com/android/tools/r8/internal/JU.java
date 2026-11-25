package com.android.tools.r8.internal;

import java.util.AbstractSet;
import java.util.BitSet;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class JU extends AbstractSet {
    final /* synthetic */ BitSet a;
    final /* synthetic */ KU b;

    JU(KU ku, BitSet bitSet) {
        this.b = ku;
        this.a = bitSet;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        Integer num = (Integer) this.b.d.b.get(obj);
        return num != null && this.a.get(num.intValue());
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new IU(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.b.d.a;
    }
}
