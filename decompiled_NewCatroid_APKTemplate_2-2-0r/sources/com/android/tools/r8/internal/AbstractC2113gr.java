package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2113gr {
    C1431Uq a;
    C1431Uq b;
    C1431Uq c;
    int d = 0;
    final /* synthetic */ C2261ir e;

    AbstractC2113gr(C2261ir c2261ir) {
        this.e = c2261ir;
        this.b = c2261ir.d;
    }

    final C1431Uq a() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        C1431Uq c1431Uq = this.b;
        this.a = c1431Uq;
        this.c = c1431Uq;
        this.d++;
        d();
        return this.c;
    }

    final C1431Uq b() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        C1431Uq c1431Uq = this.a;
        this.b = c1431Uq;
        this.c = c1431Uq;
        this.d--;
        g();
        return this.c;
    }

    void d() {
        C1431Uq c1431Uq = this.b;
        C1431Uq c1431Uq2 = c1431Uq.d;
        if ((c1431Uq.e & Integer.MIN_VALUE) == 0) {
            while ((c1431Uq2.e & 1073741824) == 0) {
                c1431Uq2 = c1431Uq2.c;
            }
        }
        this.b = c1431Uq2;
    }

    void g() {
        this.a = this.a.e();
    }

    public final boolean hasNext() {
        return this.b != null;
    }

    public final boolean hasPrevious() {
        return this.a != null;
    }

    public final int nextIndex() {
        return this.d;
    }

    public final int previousIndex() {
        return this.d - 1;
    }

    public final void remove() {
        C1431Uq c1431Uq = this.c;
        if (c1431Uq == null) {
            throw new IllegalStateException();
        }
        if (c1431Uq == this.a) {
            this.d--;
        }
        this.a = c1431Uq;
        this.b = c1431Uq;
        g();
        d();
        this.e.remove(this.c.a);
        this.c = null;
    }
}
