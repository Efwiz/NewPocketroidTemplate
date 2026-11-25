package com.android.tools.r8.internal;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class FB extends HB {
    FB(GB gb) {
        super(gb.a);
    }

    @Override // java.util.Iterator
    public final Object next() {
        IB ib = this.a;
        JB jb = this.d;
        if (ib == jb.e) {
            throw new NoSuchElementException();
        }
        if (jb.d != this.c) {
            throw new ConcurrentModificationException();
        }
        this.a = ib.d;
        this.b = ib;
        return ib.f;
    }
}
