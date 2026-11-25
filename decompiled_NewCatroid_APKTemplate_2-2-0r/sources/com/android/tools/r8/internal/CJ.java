package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class CJ {
    C2841qJ a;
    C2841qJ b;
    C2841qJ c;
    int d = 0;
    final /* synthetic */ EJ e;

    CJ(EJ ej) {
        this.e = ej;
        this.b = ej.d;
    }

    final C2841qJ a() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        C2841qJ c2841qJ = this.b;
        this.a = c2841qJ;
        this.c = c2841qJ;
        this.d++;
        d();
        return this.c;
    }

    final C2841qJ b() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        C2841qJ c2841qJ = this.a;
        this.b = c2841qJ;
        this.c = c2841qJ;
        this.d--;
        g();
        return this.c;
    }

    void d() {
        C2841qJ c2841qJ = this.b;
        C2841qJ c2841qJ2 = c2841qJ.d;
        if ((c2841qJ.e & Integer.MIN_VALUE) == 0) {
            while ((c2841qJ2.e & 1073741824) == 0) {
                c2841qJ2 = c2841qJ2.c;
            }
        }
        this.b = c2841qJ2;
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
        C2841qJ c2841qJ = this.c;
        if (c2841qJ == null) {
            throw new IllegalStateException();
        }
        if (c2841qJ == this.a) {
            this.d--;
        }
        this.a = c2841qJ;
        this.b = c2841qJ;
        g();
        d();
        this.e.remove(this.c.a);
        this.c = null;
    }
}
