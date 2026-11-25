package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ip, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2259ip {
    C1481Wo a;
    C1481Wo b;
    C1481Wo c;
    int d = 0;
    final /* synthetic */ C2413kp e;

    AbstractC2259ip(C2413kp c2413kp) {
        this.e = c2413kp;
        this.b = c2413kp.d;
    }

    final C1481Wo a() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        C1481Wo c1481Wo = this.b;
        this.a = c1481Wo;
        this.c = c1481Wo;
        this.d++;
        d();
        return this.c;
    }

    final C1481Wo b() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        C1481Wo c1481Wo = this.a;
        this.b = c1481Wo;
        this.c = c1481Wo;
        this.d--;
        g();
        return this.c;
    }

    void d() {
        C1481Wo c1481Wo = this.b;
        C1481Wo c1481Wo2 = c1481Wo.d;
        if ((c1481Wo.e & Integer.MIN_VALUE) == 0) {
            while ((c1481Wo2.e & 1073741824) == 0) {
                c1481Wo2 = c1481Wo2.c;
            }
        }
        this.b = c1481Wo2;
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
        C1481Wo c1481Wo = this.c;
        if (c1481Wo == null) {
            throw new IllegalStateException();
        }
        if (c1481Wo == this.a) {
            this.d--;
        }
        this.a = c1481Wo;
        this.b = c1481Wo;
        g();
        d();
        this.e.remove(this.c.a);
        this.c = null;
    }
}
