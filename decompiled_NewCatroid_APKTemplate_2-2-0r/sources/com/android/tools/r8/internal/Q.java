package com.android.tools.r8.internal;

import java.util.SortedSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class Q extends P implements InterfaceC1357Sr {
    protected Q() {
    }

    @Override // java.util.SortedSet
    public final Object first() {
        return Integer.valueOf(k0());
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        return d(((Integer) obj).intValue());
    }

    @Override // java.util.SortedSet
    public final Object last() {
        return Integer.valueOf(u());
    }

    @Override // java.util.SortedSet
    public final SortedSet subSet(Object obj, Object obj2) {
        return d(((Integer) obj).intValue(), ((Integer) obj2).intValue());
    }

    @Override // java.util.SortedSet
    public final SortedSet tailSet(Object obj) {
        return c(((Integer) obj).intValue());
    }
}
