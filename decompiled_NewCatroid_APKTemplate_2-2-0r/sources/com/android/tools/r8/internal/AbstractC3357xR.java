package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC3357xR {
    int a;
    int b;
    int c;
    int d;
    final /* synthetic */ C3505zR e;

    /* synthetic */ AbstractC3357xR(C3505zR c3505zR, int i) {
        this(c3505zR);
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
        Object obj;
        C3505zR c3505zR;
        a();
        int i = this.c;
        if (i == -1) {
            throw new IllegalStateException();
        }
        if (i == this.a) {
            this.d--;
            this.a = (int) (this.e.h[i] >>> 32);
        } else {
            this.b = (int) this.e.h[i];
        }
        C3505zR c3505zR2 = this.e;
        c3505zR2.k--;
        int i2 = this.a;
        if (i2 == -1) {
            c3505zR2.f = this.b;
        } else {
            long[] jArr = c3505zR2.h;
            long j = jArr[i2];
            jArr[i2] = j ^ (((((long) this.b) & 4294967295L) ^ j) & 4294967295L);
        }
        int i3 = this.b;
        if (i3 == -1) {
            c3505zR2.g = i2;
        } else {
            long[] jArr2 = c3505zR2.h;
            long j2 = jArr2[i3];
            jArr2[i3] = ((((((long) i2) & 4294967295L) << 32) ^ j2) & (-4294967296L)) ^ j2;
        }
        this.c = -1;
        int i4 = c3505zR2.i;
        if (i == i4) {
            c3505zR2.e = false;
            c3505zR2.b[i4] = null;
            return;
        }
        Object[] objArr = c3505zR2.b;
        while (true) {
            int i5 = (i + 1) & this.e.d;
            while (true) {
                obj = objArr[i5];
                if (obj != null) {
                    int iA = AbstractC3307wk.a(System.identityHashCode(obj));
                    c3505zR = this.e;
                    int i6 = c3505zR.d;
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
                    return;
                }
            }
            objArr[i] = obj;
            int[] iArr = c3505zR.c;
            iArr[i] = iArr[i5];
            if (this.b == i5) {
                this.b = i;
            }
            if (this.a == i5) {
                this.a = i;
            }
            c3505zR.c(i5, i);
            i = i5;
        }
    }

    private AbstractC3357xR(C3505zR c3505zR) {
        this.e = c3505zR;
        this.a = -1;
        this.b = -1;
        this.c = -1;
        this.d = -1;
        this.b = c3505zR.f;
        this.d = 0;
    }
}
