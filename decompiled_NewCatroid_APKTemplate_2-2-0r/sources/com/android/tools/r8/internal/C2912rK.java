package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rK, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2912rK extends C2604nK implements InterfaceC2842qK {
    protected C2912rK() {
    }

    @Override // com.android.tools.r8.internal.C2604nK
    public final Object clone() {
        return AbstractC2983sK.a;
    }

    @Override // java.util.SortedSet
    public final Comparator comparator() {
        return null;
    }

    @Override // java.util.SortedSet
    public final Object first() {
        throw new NoSuchElementException();
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        return AbstractC2983sK.a;
    }

    @Override // java.util.SortedSet
    public final Object last() {
        throw new NoSuchElementException();
    }

    @Override // java.util.SortedSet
    public final SortedSet subSet(Object obj, Object obj2) {
        return AbstractC2983sK.a;
    }

    @Override // java.util.SortedSet
    public final SortedSet tailSet(Object obj) {
        return AbstractC2983sK.a;
    }
}
