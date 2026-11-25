package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Oq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1246Oq {
    int a;
    int b;
    int c;
    boolean d;
    C2732or e;
    final /* synthetic */ C1303Qq f;

    private AbstractC1246Oq(C1303Qq c1303Qq) {
        this.f = c1303Qq;
        this.a = c1303Qq.f;
        this.b = -1;
        this.c = c1303Qq.h;
        this.d = c1303Qq.e;
    }

    public final int a() {
        int i;
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.c--;
        if (this.d) {
            this.d = false;
            int i2 = this.f.f;
            this.b = i2;
            return i2;
        }
        int[] iArr = this.f.b;
        do {
            i = this.a - 1;
            this.a = i;
            if (i < 0) {
                this.b = Integer.MIN_VALUE;
                int i3 = this.e.i((-i) - 1);
                int iA = AbstractC3307wk.a(i3);
                int i4 = this.f.d;
                while (true) {
                    int i5 = iA & i4;
                    if (i3 == iArr[i5]) {
                        return i5;
                    }
                    iA = i5 + 1;
                    i4 = this.f.d;
                }
            }
        } while (iArr[i] == 0);
        this.b = i;
        return i;
    }

    public final boolean hasNext() {
        return this.c != 0;
    }

    public void remove() {
        int i;
        int i2 = this.b;
        if (i2 == -1) {
            throw new IllegalStateException();
        }
        C1303Qq c1303Qq = this.f;
        int i3 = c1303Qq.f;
        if (i2 == i3) {
            c1303Qq.e = false;
            c1303Qq.c[i3] = null;
        } else {
            int i4 = this.a;
            if (i4 < 0) {
                c1303Qq.remove(this.e.i((-i4) - 1));
                this.b = -1;
                return;
            }
            int[] iArr = c1303Qq.b;
            loop0: while (true) {
                int i5 = (i2 + 1) & this.f.d;
                while (true) {
                    i = iArr[i5];
                    if (i != 0) {
                        int iA = AbstractC3307wk.a(i);
                        int i6 = this.f.d;
                        int i7 = iA & i6;
                        if (i2 > i5) {
                            if (i2 >= i7 && i7 > i5) {
                                break;
                            } else {
                                i5 = (i5 + 1) & i6;
                            }
                        } else if (i2 >= i7 || i7 > i5) {
                            break;
                        } else {
                            i5 = (i5 + 1) & i6;
                        }
                    } else {
                        break loop0;
                    }
                }
                if (i5 < i2) {
                    if (this.e == null) {
                        this.e = new C2732or(2);
                    }
                    this.e.a(iArr[i5]);
                }
                iArr[i2] = i;
                Object[] objArr = this.f.c;
                objArr[i2] = objArr[i5];
                i2 = i5;
            }
            iArr[i2] = 0;
            this.f.c[i2] = null;
        }
        this.f.h--;
        this.b = -1;
    }

    /* synthetic */ AbstractC1246Oq(C1303Qq c1303Qq, int i) {
        this(c1303Qq);
    }
}
