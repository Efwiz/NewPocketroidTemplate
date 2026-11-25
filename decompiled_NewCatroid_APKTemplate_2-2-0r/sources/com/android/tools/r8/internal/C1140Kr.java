package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Kr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1140Kr extends K {
    int a;
    int b;
    int c;
    boolean d;
    C2732or e;
    final /* synthetic */ Lr f;

    private C1140Kr(Lr lr) {
        this.f = lr;
        this.a = lr.d;
        this.b = -1;
        this.c = lr.f;
        this.d = lr.c;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.c != 0;
    }

    @Override // com.android.tools.r8.internal.K, java.util.Iterator
    public final void remove() {
        int i;
        int i2 = this.b;
        if (i2 == -1) {
            throw new IllegalStateException();
        }
        Lr lr = this.f;
        int i3 = lr.d;
        if (i2 == i3) {
            lr.c = false;
            lr.a[i3] = 0;
        } else {
            int i4 = this.a;
            if (i4 < 0) {
                lr.k(this.e.i((-i4) - 1));
                this.b = -1;
                return;
            }
            int[] iArr = lr.a;
            loop0: while (true) {
                int i5 = (i2 + 1) & this.f.b;
                while (true) {
                    i = iArr[i5];
                    if (i != 0) {
                        int iA = AbstractC3307wk.a(i);
                        int i6 = this.f.b;
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
                i2 = i5;
            }
            iArr[i2] = 0;
        }
        this.f.f--;
        this.b = -1;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0841Ar
    public final int t() {
        int i;
        int i2;
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.c--;
        if (this.d) {
            this.d = false;
            Lr lr = this.f;
            int i3 = lr.d;
            this.b = i3;
            return lr.a[i3];
        }
        int[] iArr = this.f.a;
        do {
            i = this.a - 1;
            this.a = i;
            if (i < 0) {
                this.b = Integer.MIN_VALUE;
                return this.e.i((-i) - 1);
            }
            i2 = iArr[i];
        } while (i2 == 0);
        this.b = i;
        return i2;
    }

    /* synthetic */ C1140Kr(Lr lr, int i) {
        this(lr);
    }
}
