package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class JR {
    int a;
    int b;
    int c;
    boolean d;
    C2464lS e;
    final /* synthetic */ MR f;

    private JR(MR mr) {
        this.f = mr;
        this.a = mr.f;
        this.b = -1;
        this.c = mr.h;
        this.d = mr.e;
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
        Object[] objArr = this.f.b;
        do {
            i = this.a - 1;
            this.a = i;
            if (i < 0) {
                this.b = Integer.MIN_VALUE;
                Object obj = this.e.get((-i) - 1);
                int iA = AbstractC3307wk.a(System.identityHashCode(obj));
                int i3 = this.f.d;
                while (true) {
                    int i4 = iA & i3;
                    if (obj == objArr[i4]) {
                        return i4;
                    }
                    iA = i4 + 1;
                    i3 = this.f.d;
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
        MR mr = this.f;
        int i2 = mr.f;
        if (i == i2) {
            mr.e = false;
            mr.b[i2] = null;
        } else {
            int i3 = this.a;
            if (i3 < 0) {
                mr.d(this.e.set((-i3) - 1, null));
                this.b = -1;
                return;
            }
            Object[] objArr = mr.b;
            loop0: while (true) {
                int i4 = (i + 1) & this.f.d;
                while (true) {
                    obj = objArr[i4];
                    if (obj != null) {
                        int iA = AbstractC3307wk.a(System.identityHashCode(obj));
                        int i5 = this.f.d;
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
                int[] iArr = this.f.c;
                iArr[i] = iArr[i4];
                i = i4;
            }
            objArr[i] = null;
        }
        this.f.h--;
        this.b = -1;
    }

    /* synthetic */ JR(MR mr, int i) {
        this(mr);
    }
}
