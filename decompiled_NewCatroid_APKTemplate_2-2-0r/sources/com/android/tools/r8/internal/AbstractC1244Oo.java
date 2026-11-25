package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Oo, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1244Oo {
    int a;
    int b;
    int c;
    boolean d;
    C2732or e;
    final /* synthetic */ C1301Qo f;

    private AbstractC1244Oo(C1301Qo c1301Qo) {
        this.f = c1301Qo;
        this.a = c1301Qo.f;
        this.b = -1;
        this.c = c1301Qo.h;
        this.d = c1301Qo.e;
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
        C1301Qo c1301Qo = this.f;
        if (i2 == c1301Qo.f) {
            c1301Qo.e = false;
        } else {
            int i3 = this.a;
            if (i3 < 0) {
                c1301Qo.remove(this.e.i((-i3) - 1));
                this.b = -1;
                return;
            }
            int[] iArr = c1301Qo.b;
            loop0: while (true) {
                int i4 = (i2 + 1) & this.f.d;
                while (true) {
                    i = iArr[i4];
                    if (i != 0) {
                        int iA = AbstractC3307wk.a(i);
                        int i5 = this.f.d;
                        int i6 = iA & i5;
                        if (i2 > i4) {
                            if (i2 >= i6 && i6 > i4) {
                                break;
                            } else {
                                i4 = (i4 + 1) & i5;
                            }
                        } else if (i2 >= i6 || i6 > i4) {
                            break;
                        } else {
                            i4 = (i4 + 1) & i5;
                        }
                    } else {
                        break loop0;
                    }
                }
                if (i4 < i2) {
                    if (this.e == null) {
                        this.e = new C2732or(2);
                    }
                    this.e.a(iArr[i4]);
                }
                iArr[i2] = i;
                int[] iArr2 = this.f.c;
                iArr2[i2] = iArr2[i4];
                i2 = i4;
            }
            iArr[i2] = 0;
        }
        this.f.h--;
        this.b = -1;
    }

    /* synthetic */ AbstractC1244Oo(C1301Qo c1301Qo, int i) {
        this(c1301Qo);
    }
}
