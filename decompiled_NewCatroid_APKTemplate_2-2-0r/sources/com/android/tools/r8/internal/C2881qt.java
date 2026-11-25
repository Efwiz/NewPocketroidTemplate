package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2881qt implements Iterator {
    Iterator a = EnumC3463yt.a;
    final /* synthetic */ Iterable b;

    C2881qt(List list) {
        this.b = list;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a.hasNext() || this.b.iterator().hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!this.a.hasNext()) {
            Iterator it = this.b.iterator();
            this.a = it;
            if (!it.hasNext()) {
                throw new NoSuchElementException();
            }
        }
        return this.a.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.a.remove();
    }
}
