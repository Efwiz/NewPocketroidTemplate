package com.android.tools.r8.internal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class UX {
    static final /* synthetic */ boolean f = true;
    private final ArrayList a = new ArrayList();
    private SX b = null;
    private VX c = null;
    private int d = 0;
    final /* synthetic */ WX e;

    UX(WX wx) {
        this.e = wx;
    }

    final void a(SX sx) {
        this.a.add(sx);
        this.d = sx.c().c() + this.d;
        if (this.b == null || sx.c().c() > this.b.c().c()) {
            this.b = sx;
        }
    }

    final void a(VX vx) {
        this.a.add(vx);
        if (this.c == null) {
            this.c = vx;
        }
    }

    final Collection a(AC ac) {
        ArrayDeque arrayDeque = new ArrayDeque();
        for (TX tx : this.a) {
            tx.getClass();
            if (tx instanceof SX) {
                SX sxA = tx.a();
                arrayDeque.addFirst(new R8(sxA.c(), sxA.c));
            }
        }
        arrayDeque.add(new V7(null, this.e.a.v2));
        arrayDeque.add(new D8(D8.a.Dup));
        boolean z = false;
        arrayDeque.add(new I7(183, this.e.b.o, false));
        for (TX tx2 : this.a) {
            tx2.getClass();
            if (tx2 instanceof SX) {
                SX sxA2 = tx2.a();
                arrayDeque.add(new O7(sxA2.c(), sxA2.c));
            } else {
                if (!f && !(tx2 instanceof VX)) {
                    throw new AssertionError();
                }
                arrayDeque.add(new C1891e7(tx2.b().c));
            }
            arrayDeque.add(new I7(182, tx2.a, false));
        }
        arrayDeque.add(new I7(182, this.e.b.r, false));
        SX sx = this.b;
        if (sx != null && sx.c().c() == 2) {
            z = true;
        }
        int iA = com.android.tools.r8.utils.M.a(z) + 2;
        int i = this.d;
        if (iA > i) {
            ac.a(iA - i);
        }
        return arrayDeque;
    }
}
