package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Sp, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1355Sp {
    C1020Gp a;
    C1020Gp b;
    C1020Gp c;
    int d = 0;
    final /* synthetic */ C1430Up e;

    AbstractC1355Sp(C1430Up c1430Up) {
        this.e = c1430Up;
        this.b = c1430Up.d;
    }

    final C1020Gp a() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        C1020Gp c1020Gp = this.b;
        this.a = c1020Gp;
        this.c = c1020Gp;
        this.d++;
        d();
        return this.c;
    }

    final C1020Gp b() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        C1020Gp c1020Gp = this.a;
        this.b = c1020Gp;
        this.c = c1020Gp;
        this.d--;
        g();
        return this.c;
    }

    void d() {
        C1020Gp c1020Gp = this.b;
        C1020Gp c1020Gp2 = c1020Gp.d;
        if ((c1020Gp.e & Integer.MIN_VALUE) == 0) {
            while ((c1020Gp2.e & 1073741824) == 0) {
                c1020Gp2 = c1020Gp2.c;
            }
        }
        this.b = c1020Gp2;
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
        C1020Gp c1020Gp = this.c;
        if (c1020Gp == null) {
            throw new IllegalStateException();
        }
        if (c1020Gp == this.a) {
            this.d--;
        }
        this.a = c1020Gp;
        this.b = c1020Gp;
        g();
        d();
        this.e.remove(this.c.a);
        this.c = null;
    }
}
