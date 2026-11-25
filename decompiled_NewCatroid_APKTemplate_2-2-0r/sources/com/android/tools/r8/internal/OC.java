package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class OC {
    int a;
    int b;
    int c;
    boolean d;
    SC e;
    final /* synthetic */ QC f;

    private OC(QC qc) {
        this.f = qc;
        this.a = qc.e;
        this.b = -1;
        this.c = qc.g;
        this.d = qc.d;
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
        long[] jArr = this.f.a;
        do {
            i = this.a - 1;
            this.a = i;
            if (i < 0) {
                this.b = Integer.MIN_VALUE;
                long jB = this.e.b((-i) - 1);
                int iB = (int) AbstractC3307wk.b(jB);
                int i3 = this.f.c;
                while (true) {
                    int i4 = iB & i3;
                    if (jB == jArr[i4]) {
                        return i4;
                    }
                    iB = i4 + 1;
                    i3 = this.f.c;
                }
            }
        } while (jArr[i] == 0);
        this.b = i;
        return i;
    }

    public final boolean hasNext() {
        return this.c != 0;
    }

    public void remove() {
        long j;
        int i = this.b;
        if (i == -1) {
            throw new IllegalStateException();
        }
        QC qc = this.f;
        int i2 = qc.e;
        if (i == i2) {
            qc.d = false;
            qc.b[i2] = null;
        } else {
            int i3 = this.a;
            if (i3 < 0) {
                qc.a(this.e.b((-i3) - 1));
                this.b = -1;
                return;
            }
            long[] jArr = qc.a;
            loop0: while (true) {
                int i4 = (i + 1) & this.f.c;
                while (true) {
                    j = jArr[i4];
                    if (j != 0) {
                        int iB = (int) AbstractC3307wk.b(j);
                        int i5 = this.f.c;
                        int i6 = iB & i5;
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
                        this.e = new SC(2);
                    }
                    this.e.a(jArr[i4]);
                }
                jArr[i] = j;
                Object[] objArr = this.f.b;
                objArr[i] = objArr[i4];
                i = i4;
            }
            jArr[i] = 0;
            this.f.b[i] = null;
        }
        this.f.g--;
        this.b = -1;
    }

    /* synthetic */ OC(QC qc, int i) {
        this(qc);
    }
}
