package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Objects;
import java.util.Spliterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Im, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1077Im extends HS implements IV {
    C1077Im(AbstractC1242Om abstractC1242Om, AbstractC1639am abstractC1639am) {
        super(abstractC1242Om, abstractC1639am);
    }

    @Override // com.android.tools.r8.internal.IV
    public final Comparator comparator() {
        return ((AbstractC1242Om) super.k()).d;
    }

    @Override // com.android.tools.r8.internal.AbstractC1160Ll, com.android.tools.r8.internal.AbstractC1639am, com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am
    final AbstractC1639am f(int i, int i2) {
        return new TS(super.f(i, i2), ((AbstractC1242Om) super.k()).d).a();
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, java.util.List
    public final int indexOf(Object obj) {
        int iIndexOf = ((AbstractC1242Om) super.k()).indexOf(obj);
        if (iIndexOf < 0 || !get(iIndexOf).equals(obj)) {
            return -1;
        }
        return iIndexOf;
    }

    @Override // com.android.tools.r8.internal.HS, com.android.tools.r8.internal.AbstractC1160Ll
    final AbstractC1271Pl k() {
        return (AbstractC1242Om) super.k();
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, java.util.List
    public final int lastIndexOf(Object obj) {
        return indexOf(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, com.android.tools.r8.internal.AbstractC1271Pl, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Spliterator spliterator() {
        int size = ((AbstractC1242Om) super.k()).size();
        AbstractC1639am abstractC1639amL = l();
        Objects.requireNonNull(abstractC1639amL);
        return AbstractC3142ub.a(size, 1301, new C1078Im$$ExternalSyntheticLambda0(abstractC1639amL), ((AbstractC1242Om) super.k()).d);
    }
}
