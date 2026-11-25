package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.r5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2897r5 extends AbstractC2347k implements Serializable {
    protected final boolean a;

    protected C2897r5(boolean z) {
        this.a = z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC2122h
    public final boolean b(boolean z) {
        return z == this.a;
    }

    public final Object clone() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2347k
    public final boolean d(boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC2122h, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC1889e5, java.util.Set
    public final InterfaceC2282j5 iterator() {
        boolean z = this.a;
        C2357k5 c2357k5 = AbstractC2588n5.a;
        return new C2437l5(z);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return 1;
    }

    @Override // com.android.tools.r8.internal.AbstractC2122h, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC1889e5, java.util.Set
    public final Iterator iterator() {
        boolean z = this.a;
        C2357k5 c2357k5 = AbstractC2588n5.a;
        return new C2437l5(z);
    }
}
