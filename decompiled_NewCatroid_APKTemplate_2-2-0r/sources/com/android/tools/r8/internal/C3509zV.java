package com.android.tools.r8.internal;

import java.util.Collections;
import java.util.Spliterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3509zV extends AbstractC1639am {
    final transient Object c;

    C3509zV(Object obj) {
        this.c = AbstractC3123uM.a(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    /* JADX INFO: renamed from: f */
    public final AbstractC2963s10 iterator() {
        return new C3241vt(this.c);
    }

    @Override // java.util.List
    public final Object get(int i) {
        AbstractC3123uM.a(i, 1);
        return this.c;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return 1;
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, com.android.tools.r8.internal.AbstractC1271Pl, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Spliterator spliterator() {
        return Collections.singleton(this.c).spliterator();
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        String string = this.c.toString();
        return new StringBuilder(String.valueOf(string).length() + 2).append('[').append(string).append(']').toString();
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, java.util.List
    /* JADX INFO: renamed from: e */
    public final AbstractC1639am subList(int i, int i2) {
        AbstractC3123uM.a(i, i2, 1);
        return i == i2 ? MS.d : this;
    }
}
