package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Aq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC0840Aq {
    int a;
    int b;
    int c;
    int d;
    final /* synthetic */ C0906Cq e;

    /* synthetic */ AbstractC0840Aq(C0906Cq c0906Cq, int i) {
        this(c0906Cq);
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
            this.d = this.e.k;
            return;
        }
        int i = this.e.f;
        this.d = 1;
        while (i != this.a) {
            i = (int) this.e.h[i];
            this.d++;
        }
    }

    public final int b() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int i = this.b;
        this.c = i;
        this.b = (int) this.e.h[i];
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
        this.a = (int) (this.e.h[i] >>> 32);
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
        int i;
        C0906Cq c0906Cq;
        a();
        int i2 = this.c;
        if (i2 == -1) {
            throw new IllegalStateException();
        }
        if (i2 == this.a) {
            this.d--;
            this.a = (int) (this.e.h[i2] >>> 32);
        } else {
            this.b = (int) this.e.h[i2];
        }
        C0906Cq c0906Cq2 = this.e;
        c0906Cq2.k--;
        int i3 = this.a;
        if (i3 == -1) {
            c0906Cq2.f = this.b;
        } else {
            long[] jArr = c0906Cq2.h;
            long j = jArr[i3];
            jArr[i3] = j ^ (((((long) this.b) & 4294967295L) ^ j) & 4294967295L);
        }
        int i4 = this.b;
        if (i4 == -1) {
            c0906Cq2.g = i3;
        } else {
            long[] jArr2 = c0906Cq2.h;
            long j2 = jArr2[i4];
            jArr2[i4] = ((((((long) i3) & 4294967295L) << 32) ^ j2) & (-4294967296L)) ^ j2;
        }
        this.c = -1;
        int i5 = c0906Cq2.i;
        if (i2 == i5) {
            c0906Cq2.e = false;
            c0906Cq2.c[i5] = null;
            return;
        }
        int[] iArr = c0906Cq2.b;
        while (true) {
            int i6 = (i2 + 1) & this.e.d;
            while (true) {
                i = iArr[i6];
                if (i != 0) {
                    int iA = AbstractC3307wk.a(i);
                    c0906Cq = this.e;
                    int i7 = c0906Cq.d;
                    int i8 = iA & i7;
                    if (i2 > i6) {
                        if (i2 >= i8 && i8 > i6) {
                            break;
                        } else {
                            i6 = (i6 + 1) & i7;
                        }
                    } else if (i2 >= i8 || i8 > i6) {
                        break;
                    } else {
                        i6 = (i6 + 1) & i7;
                    }
                } else {
                    iArr[i2] = 0;
                    this.e.c[i2] = null;
                    return;
                }
            }
            iArr[i2] = i;
            Object[] objArr = c0906Cq.c;
            objArr[i2] = objArr[i6];
            if (this.b == i6) {
                this.b = i2;
            }
            if (this.a == i6) {
                this.a = i2;
            }
            c0906Cq.c(i6, i2);
            i2 = i6;
        }
    }

    private AbstractC0840Aq(C0906Cq c0906Cq) {
        this.e = c0906Cq;
        this.a = -1;
        this.b = -1;
        this.c = -1;
        this.d = -1;
        this.b = c0906Cq.f;
        this.d = 0;
    }
}
