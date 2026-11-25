package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2383kR {
    int a;
    int b;
    int c;
    boolean d;
    C2464lS e;
    final /* synthetic */ C2537mR f;

    private AbstractC2383kR(C2537mR c2537mR) {
        this.f = c2537mR;
        this.a = c2537mR.e;
        this.b = -1;
        this.c = c2537mR.g;
        this.d = c2537mR.d;
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
                int iA = AbstractC3307wk.a(System.identityHashCode(obj));
                int i3 = this.f.c;
                while (true) {
                    int i4 = iA & i3;
                    if (obj == objArr[i4]) {
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
        C2537mR c2537mR = this.f;
        int i2 = c2537mR.e;
        if (i == i2) {
            c2537mR.d = false;
            c2537mR.a[i2] = null;
        } else {
            int i3 = this.a;
            if (i3 < 0) {
                c2537mR.c(this.e.set((-i3) - 1, null));
                this.b = -1;
                return;
            }
            Object[] objArr = c2537mR.a;
            loop0: while (true) {
                int i4 = (i + 1) & this.f.c;
                while (true) {
                    obj = objArr[i4];
                    if (obj != null) {
                        int iA = AbstractC3307wk.a(System.identityHashCode(obj));
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
                        this.e = new C2464lS(2);
                    }
                    this.e.add(objArr[i4]);
                }
                objArr[i] = obj;
                boolean[] zArr = this.f.b;
                zArr[i] = zArr[i4];
                i = i4;
            }
            objArr[i] = null;
        }
        this.f.g--;
        this.b = -1;
    }

    /* synthetic */ AbstractC2383kR(C2537mR c2537mR, int i) {
        this(c2537mR);
    }
}
