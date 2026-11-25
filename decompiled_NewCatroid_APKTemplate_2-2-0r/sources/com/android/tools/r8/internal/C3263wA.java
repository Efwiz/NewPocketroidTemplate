package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3263wA {
    static final C3263wA o = new C3263wA();
    public C3338xA a;
    short b;
    private short c;
    private int[] d;
    int e;
    private int[] f;
    short g;
    short h;
    short i;
    short j;
    C1267Pi k;
    C3263wA l;
    C1235Of m;
    C3263wA n;

    final C3263wA a() {
        C1267Pi c1267Pi = this.k;
        return c1267Pi == null ? this : c1267Pi.a;
    }

    public final String toString() {
        return "L" + System.identityHashCode(this);
    }

    final void a(int i) {
        if (this.c == 0) {
            this.c = (short) i;
            return;
        }
        if (this.d == null) {
            this.d = new int[4];
        }
        int[] iArr = this.d;
        int i2 = iArr[0] + 1;
        iArr[0] = i2;
        if (i2 >= iArr.length) {
            int[] iArr2 = new int[iArr.length + 4];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            this.d = iArr2;
        }
        this.d[i2] = i;
    }

    final void a(AbstractC1899eF abstractC1899eF, boolean z) {
        short s;
        abstractC1899eF.a(this);
        if (!z || (s = this.c) == 0) {
            return;
        }
        abstractC1899eF.b(s & 65535, this);
        if (this.d == null) {
            return;
        }
        int i = 1;
        while (true) {
            int[] iArr = this.d;
            if (i > iArr[0]) {
                return;
            }
            abstractC1899eF.b(iArr[i], this);
            i++;
        }
    }

    final void a(C1667b6 c1667b6, int i, boolean z) {
        if ((this.b & 4) != 0) {
            if (z) {
                c1667b6.c(this.e - i);
                return;
            } else {
                c1667b6.d(this.e - i);
                return;
            }
        }
        if (z) {
            a(i, 536870912, c1667b6.b);
            c1667b6.c(-1);
        } else {
            a(i, 268435456, c1667b6.b);
            c1667b6.d(-1);
        }
    }

    private void a(int i, int i2, int i3) {
        if (this.f == null) {
            this.f = new int[6];
        }
        int[] iArr = this.f;
        int i4 = iArr[0];
        if (i4 + 2 >= iArr.length) {
            int[] iArr2 = new int[iArr.length + 6];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            this.f = iArr2;
        }
        int[] iArr3 = this.f;
        iArr3[i4 + 1] = i;
        int i5 = i4 + 2;
        iArr3[i5] = i2 | i3;
        iArr3[0] = i5;
    }

    final boolean a(int i, byte[] bArr) {
        this.b = (short) (this.b | 4);
        this.e = i;
        int[] iArr = this.f;
        boolean z = false;
        if (iArr == null) {
            return false;
        }
        for (int i2 = iArr[0]; i2 > 0; i2 -= 2) {
            int[] iArr2 = this.f;
            int i3 = iArr2[i2 - 1];
            int i4 = iArr2[i2];
            int i5 = i - i3;
            int i6 = 268435455 & i4;
            if ((i4 & (-268435456)) == 268435456) {
                if (i5 < -32768 || i5 > 32767) {
                    int i7 = bArr[i3] & 255;
                    if (i7 < 198) {
                        bArr[i3] = (byte) (i7 + 49);
                    } else {
                        bArr[i3] = (byte) (i7 + 20);
                    }
                    z = true;
                }
                bArr[i6] = (byte) (i5 >>> 8);
                bArr[i6 + 1] = (byte) i5;
            } else {
                bArr[i6] = (byte) (i5 >>> 24);
                bArr[i6 + 1] = (byte) (i5 >>> 16);
                bArr[i6 + 2] = (byte) (i5 >>> 8);
                bArr[i6 + 3] = (byte) i5;
            }
        }
        return z;
    }

    /*  JADX ERROR: JadxOverflowException in pass: LoopRegionVisitor
        jadx.core.utils.exceptions.JadxOverflowException: LoopRegionVisitor.assignOnlyInLoop endless recursion
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    final void a(short r6) {
        /*
            r5 = this;
            com.android.tools.r8.internal.wA r0 = com.android.tools.r8.internal.C3263wA.o
            r5.n = r0
            r0 = r5
        L5:
            com.android.tools.r8.internal.wA r1 = com.android.tools.r8.internal.C3263wA.o
            if (r0 == r1) goto L33
            com.android.tools.r8.internal.wA r1 = r0.n
            r2 = 0
            r0.n = r2
            short r2 = r0.j
            if (r2 != 0) goto L31
            r0.j = r6
            com.android.tools.r8.internal.Of r2 = r0.m
        L16:
            if (r2 == 0) goto L31
            short r3 = r0.b
            r3 = r3 & 16
            if (r3 == 0) goto L25
            com.android.tools.r8.internal.Of r3 = r0.m
            com.android.tools.r8.internal.Of r3 = r3.c
            if (r2 != r3) goto L25
            goto L2e
        L25:
            com.android.tools.r8.internal.wA r3 = r2.b
            com.android.tools.r8.internal.wA r4 = r3.n
            if (r4 != 0) goto L2e
            r3.n = r1
            r1 = r3
        L2e:
            com.android.tools.r8.internal.Of r2 = r2.c
            goto L16
        L31:
            r0 = r1
            goto L5
        L33:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.C3263wA.a(short):void");
    }

    /*  JADX ERROR: JadxOverflowException in pass: LoopRegionVisitor
        jadx.core.utils.exceptions.JadxOverflowException: LoopRegionVisitor.assignOnlyInLoop endless recursion
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    final void a(com.android.tools.r8.internal.C3263wA r7) {
        /*
            r6 = this;
            com.android.tools.r8.internal.wA r0 = com.android.tools.r8.internal.C3263wA.o
            r6.n = r0
            r1 = r0
            r0 = r6
        L6:
            com.android.tools.r8.internal.wA r2 = com.android.tools.r8.internal.C3263wA.o
            if (r0 == r2) goto L49
            com.android.tools.r8.internal.wA r2 = r0.n
            r0.n = r1
            short r1 = r0.b
            r1 = r1 & 64
            if (r1 == 0) goto L29
            short r1 = r0.j
            short r3 = r7.j
            if (r1 == r3) goto L29
            com.android.tools.r8.internal.Of r1 = new com.android.tools.r8.internal.Of
            short r3 = r0.h
            com.android.tools.r8.internal.Of r4 = r7.m
            com.android.tools.r8.internal.wA r4 = r4.b
            com.android.tools.r8.internal.Of r5 = r0.m
            r1.<init>(r3, r4, r5)
            r0.m = r1
        L29:
            com.android.tools.r8.internal.Of r1 = r0.m
        L2b:
            if (r1 == 0) goto L46
            short r3 = r0.b
            r3 = r3 & 16
            if (r3 == 0) goto L3a
            com.android.tools.r8.internal.Of r3 = r0.m
            com.android.tools.r8.internal.Of r3 = r3.c
            if (r1 != r3) goto L3a
            goto L43
        L3a:
            com.android.tools.r8.internal.wA r3 = r1.b
            com.android.tools.r8.internal.wA r4 = r3.n
            if (r4 != 0) goto L43
            r3.n = r2
            r2 = r3
        L43:
            com.android.tools.r8.internal.Of r1 = r1.c
            goto L2b
        L46:
            r1 = r0
            r0 = r2
            goto L6
        L49:
            com.android.tools.r8.internal.wA r7 = com.android.tools.r8.internal.C3263wA.o
            if (r1 == r7) goto L54
            com.android.tools.r8.internal.wA r7 = r1.n
            r0 = 0
            r1.n = r0
            r1 = r7
            goto L49
        L54:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.C3263wA.a(com.android.tools.r8.internal.wA):void");
    }
}
