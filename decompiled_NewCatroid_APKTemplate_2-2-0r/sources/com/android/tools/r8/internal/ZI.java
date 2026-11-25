package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class ZI {
    int a;
    int b;
    int c;
    boolean d;
    TJ e;
    final /* synthetic */ C1684bJ f;

    private ZI(C1684bJ c1684bJ) {
        this.f = c1684bJ;
        this.a = c1684bJ.e;
        this.b = -1;
        this.c = c1684bJ.g;
        this.d = c1684bJ.d;
    }

    public final int a() {
        int i;
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.c--;
        if (this.d) {
            this.d = false;
            int i2 = this.f.e;
            this.b = i2;
            return i2;
        }
        Object[] objArr = this.f.a;
        do {
            i = this.a - 1;
            this.a = i;
            if (i < 0) {
                this.b = Integer.MIN_VALUE;
                Object obj = this.e.get((-i) - 1);
                int iA = AbstractC3307wk.a(obj.hashCode());
                int i3 = this.f.c;
                while (true) {
                    int i4 = iA & i3;
                    if (obj.equals(objArr[i4])) {
                        return i4;
                    }
                    iA = i4 + 1;
                    i3 = this.f.c;
                }
            }
        } while (objArr[i] == null);
        this.b = i;
        return i;
    }

    public final boolean hasNext() {
        return this.c != 0;
    }

    public void remove() {
        Object obj;
        int i = this.b;
        if (i == -1) {
            throw new IllegalStateException();
        }
        C1684bJ c1684bJ = this.f;
        int i2 = c1684bJ.e;
        if (i == i2) {
            c1684bJ.d = false;
            c1684bJ.a[i2] = null;
        } else {
            int i3 = this.a;
            if (i3 < 0) {
                c1684bJ.d(this.e.set((-i3) - 1, null));
                this.b = -1;
                return;
            }
            Object[] objArr = c1684bJ.a;
            loop0: while (true) {
                int i4 = (i + 1) & this.f.c;
                while (true) {
                    obj = objArr[i4];
                    if (obj != null) {
                        int iA = AbstractC3307wk.a(obj.hashCode());
                        int i5 = this.f.c;
                        int i6 = iA & i5;
                        if (i > i4) {
                            if (i >= i6 && i6 > i4) {
                                break;
                            } else {
                                i4 = (i4 + 1) & i5;
                            }
                        } else if (i >= i6 || i6 > i4) {
                            break;
                        } else {
                            i4 = (i4 + 1) & i5;
                        }
                    } else {
                        break loop0;
                    }
                }
                if (i4 < i) {
                    if (this.e == null) {
                        this.e = new TJ(2);
                    }
                    this.e.add(objArr[i4]);
                }
                objArr[i] = obj;
                long[] jArr = this.f.b;
                jArr[i] = jArr[i4];
                i = i4;
            }
            objArr[i] = null;
        }
        this.f.g--;
        this.b = -1;
    }

    /* synthetic */ ZI(C1684bJ c1684bJ, int i) {
        this(c1684bJ);
    }
}
