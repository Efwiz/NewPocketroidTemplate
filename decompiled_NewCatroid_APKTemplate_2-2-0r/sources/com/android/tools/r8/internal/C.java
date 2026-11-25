package com.android.tools.r8.internal;

import java.util.Comparator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class C extends Q {
    final /* synthetic */ D a;

    protected C(D d) {
        this.a = d;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1357Sr
    public final InterfaceC1357Sr c(int i) {
        return this.a.b(i).keySet();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.SortedSet
    public final Comparator comparator() {
        return this.a.comparator();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1357Sr
    public final InterfaceC1357Sr d(int i) {
        return this.a.c(i).keySet();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3088tr
    public final boolean f(int i) {
        return this.a.a(i);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1357Sr
    public final int k0() {
        return this.a.a();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.size();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1357Sr
    public final int u() {
        return this.a.d();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1357Sr
    public final InterfaceC1357Sr d(int i, int i2) {
        return this.a.a(i, i2).keySet();
    }
}
