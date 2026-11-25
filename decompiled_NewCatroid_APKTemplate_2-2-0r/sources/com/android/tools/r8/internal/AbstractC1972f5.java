package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.f5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1972f5 extends AbstractC2122h {
    protected AbstractC1972f5() {
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC2122h
    public final boolean b(boolean z) {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final void clear() {
    }

    @Override // java.util.Collection
    public final int hashCode() {
        return 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC2122h, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC1889e5, java.util.Set
    public final InterfaceC2282j5 iterator() {
        return AbstractC2588n5.a;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        return 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final Object[] toArray() {
        return WJ.a;
    }

    @Override // com.android.tools.r8.internal.AbstractC2122h, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC1889e5, java.util.Set
    public final Iterator iterator() {
        return AbstractC2588n5.a;
    }
}
