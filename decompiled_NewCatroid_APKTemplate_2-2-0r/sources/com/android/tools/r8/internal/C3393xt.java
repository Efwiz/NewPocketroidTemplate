package com.android.tools.r8.internal;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3393xt implements Iterator {
    private Iterator a;
    private Iterator b = AbstractC3533zt.a();
    private Iterator c;
    private ArrayDeque d;

    C3393xt(Iterator it) {
        this.c = (Iterator) AbstractC3123uM.a(it);
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        Iterator it;
        while (true) {
            Iterator it2 = this.b;
            int i = AbstractC3123uM.a;
            it2.getClass();
            if (it2.hasNext()) {
                return true;
            }
            while (true) {
                Iterator it3 = this.c;
                if (it3 != null && it3.hasNext()) {
                    it = this.c;
                    break;
                }
                ArrayDeque arrayDeque = this.d;
                if (arrayDeque == null || arrayDeque.isEmpty()) {
                    it = null;
                    break;
                }
                this.c = (Iterator) this.d.removeFirst();
            }
            this.c = it;
            if (it == null) {
                return false;
            }
            Iterator it4 = (Iterator) it.next();
            this.b = it4;
            if (it4 instanceof C3393xt) {
                C3393xt c3393xt = (C3393xt) it4;
                this.b = c3393xt.b;
                if (this.d == null) {
                    this.d = new ArrayDeque();
                }
                this.d.addFirst(this.c);
                if (c3393xt.d != null) {
                    while (!c3393xt.d.isEmpty()) {
                        this.d.addFirst((Iterator) c3393xt.d.removeLast());
                    }
                }
                this.c = c3393xt.c;
            }
        }
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Iterator it = this.b;
        this.a = it;
        return it.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        AbstractC3123uM.b("no calls to next() since the last call to remove()", this.a != null);
        this.a.remove();
        this.a = null;
    }
}
