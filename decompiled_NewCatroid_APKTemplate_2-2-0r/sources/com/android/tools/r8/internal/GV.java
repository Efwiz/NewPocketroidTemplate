package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class GV implements Iterator {
    private int a;
    private boolean b;
    private Iterator c;
    final /* synthetic */ BV d;

    private GV(BV bv) {
        this.d = bv;
        this.a = -1;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.a + 1 < this.d.b.size()) {
            return true;
        }
        if (this.c == null) {
            this.c = this.d.c.entrySet().iterator();
        }
        return this.c.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        this.b = true;
        int i = this.a + 1;
        this.a = i;
        if (i < this.d.b.size()) {
            return (Map.Entry) this.d.b.get(this.a);
        }
        if (this.c == null) {
            this.c = this.d.c.entrySet().iterator();
        }
        return (Map.Entry) this.c.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.b) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.b = false;
        this.d.e();
        if (this.a >= this.d.b.size()) {
            if (this.c == null) {
                this.c = this.d.c.entrySet().iterator();
            }
            this.c.remove();
        } else {
            BV bv = this.d;
            int i = this.a;
            this.a = i - 1;
            BV.a(i, bv);
        }
    }

    /* synthetic */ GV(BV bv, int i) {
        this(bv);
    }
}
