package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class MJ {
    int a;
    int b;
    int c;
    int d;
    final /* synthetic */ OJ e;

    /* synthetic */ MJ(OJ oj, int i) {
        this(oj);
    }

    private final void a() {
        if (this.d >= 0) {
            return;
        }
        if (this.a == -1) {
            this.d = 0;
            return;
        }
        if (this.b == -1) {
            this.d = this.e.j;
            return;
        }
        int i = this.e.e;
        this.d = 1;
        while (i != this.a) {
            i = (int) this.e.g[i];
            this.d++;
        }
    }

    public final int b() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int i = this.b;
        this.c = i;
        this.b = (int) this.e.g[i];
        this.a = i;
        int i2 = this.d;
        if (i2 >= 0) {
            this.d = i2 + 1;
        }
        return i;
    }

    public final int d() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        int i = this.a;
        this.c = i;
        this.a = (int) (this.e.g[i] >>> 32);
        this.b = i;
        int i2 = this.d;
        if (i2 >= 0) {
            this.d = i2 - 1;
        }
        return i;
    }

    public final boolean hasNext() {
        return this.b != -1;
    }

    public final boolean hasPrevious() {
        return this.a != -1;
    }

    public final int nextIndex() {
        a();
        return this.d;
    }

    public final int previousIndex() {
        a();
        return this.d - 1;
    }

    public void remove() {
        Object obj;
        OJ oj;
        a();
        int i = this.c;
        if (i == -1) {
            throw new IllegalStateException();
        }
        if (i == this.a) {
            this.d--;
            this.a = (int) (this.e.g[i] >>> 32);
        } else {
            this.b = (int) this.e.g[i];
        }
        OJ oj2 = this.e;
        oj2.j--;
        int i2 = this.a;
        if (i2 == -1) {
            oj2.e = this.b;
        } else {
            long[] jArr = oj2.g;
            long j = jArr[i2];
            jArr[i2] = j ^ (((((long) this.b) & 4294967295L) ^ j) & 4294967295L);
        }
        int i3 = this.b;
        if (i3 == -1) {
            oj2.f = i2;
        } else {
            long[] jArr2 = oj2.g;
            long j2 = jArr2[i3];
            jArr2[i3] = ((((((long) i2) & 4294967295L) << 32) ^ j2) & (-4294967296L)) ^ j2;
        }
        this.c = -1;
        int i4 = oj2.h;
        if (i == i4) {
            oj2.d = false;
            oj2.a[i4] = null;
            oj2.b[i4] = null;
            return;
        }
        Object[] objArr = oj2.a;
        while (true) {
            int i5 = (i + 1) & this.e.c;
            while (true) {
                obj = objArr[i5];
                if (obj != null) {
                    int iA = AbstractC3307wk.a(obj.hashCode());
                    oj = this.e;
                    int i6 = oj.c;
                    int i7 = iA & i6;
                    if (i > i5) {
                        if (i >= i7 && i7 > i5) {
                            break;
                        } else {
                            i5 = (i5 + 1) & i6;
                        }
                    } else if (i >= i7 || i7 > i5) {
                        break;
                    } else {
                        i5 = (i5 + 1) & i6;
                    }
                } else {
                    objArr[i] = null;
                    this.e.b[i] = null;
                    return;
                }
            }
            objArr[i] = obj;
            Object[] objArr2 = oj.b;
            objArr2[i] = objArr2[i5];
            if (this.b == i5) {
                this.b = i;
            }
            if (this.a == i5) {
                this.a = i;
            }
            oj.c(i5, i);
            i = i5;
        }
    }

    private MJ(OJ oj) {
        this.e = oj;
        this.a = -1;
        this.b = -1;
        this.c = -1;
        this.d = -1;
        this.b = oj.e;
        this.d = 0;
    }
}
