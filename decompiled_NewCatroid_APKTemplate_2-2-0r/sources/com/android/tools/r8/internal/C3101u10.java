package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.u10, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3101u10 implements Iterator {
    Iterator a;

    C3101u10(C3178v10 c3178v10) {
        this.a = c3178v10.a.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        return (String) this.a.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
