package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class HB implements Iterator {
    IB a;
    IB b = null;
    int c;
    final /* synthetic */ JB d;

    HB(JB jb) {
        this.d = jb;
        this.a = jb.e.d;
        this.c = jb.d;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a != this.d.e;
    }

    @Override // java.util.Iterator
    public final void remove() {
        IB ib = this.b;
        if (ib == null) {
            throw new IllegalStateException();
        }
        this.d.b(ib, true);
        this.b = null;
        this.c = this.d.d;
    }
}
