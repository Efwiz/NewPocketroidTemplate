package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2643nq {
    C1718bq a;
    C1718bq b;
    C1718bq c;
    int d = 0;
    final /* synthetic */ C2807pq e;

    AbstractC2643nq(C2807pq c2807pq) {
        this.e = c2807pq;
        this.b = c2807pq.d;
    }

    final C1718bq a() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        C1718bq c1718bq = this.b;
        this.a = c1718bq;
        this.c = c1718bq;
        this.d++;
        d();
        return this.c;
    }

    final C1718bq b() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        C1718bq c1718bq = this.a;
        this.b = c1718bq;
        this.c = c1718bq;
        this.d--;
        g();
        return this.c;
    }

    void d() {
        C1718bq c1718bq = this.b;
        C1718bq c1718bq2 = c1718bq.d;
        if ((c1718bq.e & Integer.MIN_VALUE) == 0) {
            while ((c1718bq2.e & 1073741824) == 0) {
                c1718bq2 = c1718bq2.c;
            }
        }
        this.b = c1718bq2;
    }

    void g() {
        this.a = this.a.d();
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
        C1718bq c1718bq = this.c;
        if (c1718bq == null) {
            throw new IllegalStateException();
        }
        if (c1718bq == this.a) {
            this.d--;
        }
        this.a = c1718bq;
        this.b = c1718bq;
        g();
        d();
        this.e.remove(this.c.a);
        this.c = null;
    }
}
