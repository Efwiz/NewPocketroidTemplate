package com.android.tools.r8.utils;

import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class J1 implements Iterator {
    private Iterator a;
    final /* synthetic */ Iterable b;

    J1(LinkedList linkedList) {
        this.b = linkedList;
        this.a = linkedList.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return true;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!this.a.hasNext()) {
            this.a = this.b.iterator();
        }
        return this.a.next();
    }
}
