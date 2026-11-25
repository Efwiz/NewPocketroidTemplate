package com.android.tools.r8.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class K1 implements Iterator {
    private Object a = a();
    final /* synthetic */ Iterator b;
    final /* synthetic */ Predicate c;

    K1(Iterator it, Predicate predicate) {
        this.b = it;
        this.c = predicate;
    }

    private Object a() {
        while (this.b.hasNext()) {
            Object next = this.b.next();
            if (this.c.test(next)) {
                return next;
            }
        }
        return null;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a != null;
    }

    @Override // java.util.Iterator
    public final Object next() {
        Object obj = this.a;
        if (obj == null) {
            throw new NoSuchElementException();
        }
        this.a = a();
        return obj;
    }
}
