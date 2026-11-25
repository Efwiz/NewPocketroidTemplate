package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zo, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3528zo extends Q {
    final /* synthetic */ C0964Eo a;

    private C3528zo(C0964Eo c0964Eo) {
        this.a = c0964Eo;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1357Sr
    public final InterfaceC1357Sr c(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.SortedSet
    public final /* bridge */ /* synthetic */ Comparator comparator() {
        return null;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1357Sr
    public final InterfaceC1357Sr d(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3088tr
    public final boolean f(int i) {
        return this.a.a(i);
    }

    @Override // com.android.tools.r8.internal.Q, com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final InterfaceC3018sr iterator() {
        return new C3458yo(this.a);
    }

    @Override // com.android.tools.r8.internal.P
    public final boolean k(int i) {
        C0964Eo c0964Eo = this.a;
        int i2 = c0964Eo.k;
        c0964Eo.remove(i);
        return this.a.k != i2;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1357Sr
    public final int k0() {
        C0964Eo c0964Eo = this.a;
        if (c0964Eo.k != 0) {
            return c0964Eo.b[c0964Eo.f];
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.k;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1357Sr
    public final int u() {
        C0964Eo c0964Eo = this.a;
        if (c0964Eo.k != 0) {
            return c0964Eo.b[c0964Eo.g];
        }
        throw new NoSuchElementException();
    }

    /* synthetic */ C3528zo(C0964Eo c0964Eo, int i) {
        this(c0964Eo);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1357Sr
    public final InterfaceC1357Sr d(int i, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.Q, com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final InterfaceC0841Ar iterator() {
        return new C3458yo(this.a);
    }

    @Override // com.android.tools.r8.internal.Q, com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final Iterator iterator() {
        return new C3458yo(this.a);
    }
}
