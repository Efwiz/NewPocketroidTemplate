package com.android.tools.r8.internal;

import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.j, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2272j extends AbstractC2194i implements YJ, ListIterator {
    protected AbstractC2272j() {
    }

    public abstract boolean a();

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        ((Boolean) obj).booleanValue();
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.YJ
    public final Object previous() {
        return Boolean.valueOf(a());
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        ((Boolean) obj).booleanValue();
        throw new UnsupportedOperationException();
    }
}
