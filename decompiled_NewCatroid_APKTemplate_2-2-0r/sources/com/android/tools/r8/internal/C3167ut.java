package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ut, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3167ut implements Iterator {
    private int a;
    final /* synthetic */ int b;
    final /* synthetic */ Iterator c;

    C3167ut(int i, Iterator it) {
        this.b = i;
        this.c = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a < this.b && this.c.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.a++;
        return this.c.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.c.remove();
    }
}
