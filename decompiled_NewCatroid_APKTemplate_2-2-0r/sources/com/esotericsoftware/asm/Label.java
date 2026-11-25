package com.esotericsoftware.asm;

/* JADX INFO: loaded from: classes8.dex */
public class Label {
    int a;
    int b;
    int c;
    private int d;
    private int[] e;
    int f;
    int g;
    Frame h;
    Label i;
    public Object info;
    Edge j;
    Label k;

    private void a(int i, int i2) {
        if (this.e == null) {
            this.e = new int[6];
        }
        int i3 = this.d;
        int[] iArr = this.e;
        if (i3 >= iArr.length) {
            int[] iArr2 = new int[iArr.length + 6];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            this.e = iArr2;
        }
        int[] iArr3 = this.e;
        int i4 = this.d;
        int i5 = i4 + 1;
        this.d = i5;
        iArr3[i4] = i;
        this.d = i4 + 2;
        iArr3[i5] = i2;
    }

    Label a() {
        Frame frame = this.h;
        return frame == null ? this : frame.b;
    }

    void a(long j, int i) {
        int i2 = this.a;
        if ((i2 & 1024) == 0) {
            this.a = i2 | 1024;
            this.e = new int[(i / 32) + 1];
        }
        int[] iArr = this.e;
        int i3 = (int) (j >>> 32);
        iArr[i3] = ((int) j) | iArr[i3];
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x001a, code lost:
    
        if (r4 != false) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void a(com.esotericsoftware.asm.MethodWriter r1, com.esotericsoftware.asm.ByteVector r2, int r3, boolean r4) {
        /*
            r0 = this;
            int r1 = r0.a
            r1 = r1 & 2
            if (r1 != 0) goto L17
            r1 = -1
            if (r4 == 0) goto L11
            int r3 = (-1) - r3
            int r4 = r2.b
            r0.a(r3, r4)
            goto L1c
        L11:
            int r4 = r2.b
            r0.a(r3, r4)
            goto L20
        L17:
            int r1 = r0.c
            int r1 = r1 - r3
            if (r4 == 0) goto L20
        L1c:
            r2.putInt(r1)
            goto L23
        L20:
            r2.putShort(r1)
        L23:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.asm.Label.a(com.esotericsoftware.asm.MethodWriter, com.esotericsoftware.asm.ByteVector, int, boolean):void");
    }

    boolean a(long j) {
        if ((this.a & 1024) != 0) {
            return (((int) j) & this.e[(int) (j >>> 32)]) != 0;
        }
        return false;
    }

    boolean a(Label label) {
        if ((this.a & 1024) != 0 && (label.a & 1024) != 0) {
            int i = 0;
            while (true) {
                int[] iArr = this.e;
                if (i >= iArr.length) {
                    break;
                }
                if ((iArr[i] & label.e[i]) != 0) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    boolean a(MethodWriter methodWriter, int i, byte[] bArr) {
        this.a |= 2;
        this.c = i;
        int i2 = 0;
        boolean z = false;
        while (i2 < this.d) {
            int[] iArr = this.e;
            int i3 = i2 + 1;
            int i4 = iArr[i2];
            i2 += 2;
            int i5 = iArr[i3];
            if (i4 >= 0) {
                int i6 = i - i4;
                if (i6 < -32768 || i6 > 32767) {
                    int i7 = i5 - 1;
                    int i8 = bArr[i7] & 255;
                    if (i8 <= 168) {
                        bArr[i7] = (byte) (i8 + 49);
                    } else {
                        bArr[i7] = (byte) (i8 + 20);
                    }
                    z = true;
                }
                bArr[i5] = (byte) (i6 >>> 8);
                bArr[i5 + 1] = (byte) i6;
            } else {
                int i9 = i4 + i + 1;
                bArr[i5] = (byte) (i9 >>> 24);
                bArr[i5 + 1] = (byte) (i9 >>> 16);
                bArr[i5 + 2] = (byte) (i9 >>> 8);
                bArr[i5 + 3] = (byte) i9;
            }
        }
        return z;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0044  */
    /*  JADX ERROR: JadxOverflowException in pass: LoopRegionVisitor
        jadx.core.utils.exceptions.JadxOverflowException: LoopRegionVisitor.assignOnlyInLoop endless recursion
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    void b(com.esotericsoftware.asm.Label r5, long r6, int r8) {
        /*
            r4 = this;
            r0 = r4
        L1:
            if (r0 == 0) goto L5f
            com.esotericsoftware.asm.Label r1 = r0.k
            r2 = 0
            r0.k = r2
            if (r5 == 0) goto L35
            int r2 = r0.a
            r3 = r2 & 2048(0x800, float:2.87E-42)
            if (r3 == 0) goto L11
            goto L3b
        L11:
            r2 = r2 | 2048(0x800, float:2.87E-42)
            r0.a = r2
            r2 = r2 & 256(0x100, float:3.59E-43)
            if (r2 == 0) goto L40
            boolean r2 = r0.a(r5)
            if (r2 != 0) goto L40
            com.esotericsoftware.asm.Edge r2 = new com.esotericsoftware.asm.Edge
            r2.<init>()
            int r3 = r0.f
            r2.a = r3
            com.esotericsoftware.asm.Edge r3 = r5.j
            com.esotericsoftware.asm.Label r3 = r3.b
            r2.b = r3
            com.esotericsoftware.asm.Edge r3 = r0.j
            r2.c = r3
            r0.j = r2
            goto L40
        L35:
            boolean r2 = r0.a(r6)
            if (r2 == 0) goto L3d
        L3b:
            r0 = r1
            goto L1
        L3d:
            r0.a(r6, r8)
        L40:
            com.esotericsoftware.asm.Edge r2 = r0.j
        L42:
            if (r2 == 0) goto L3b
            int r3 = r0.a
            r3 = r3 & 128(0x80, float:1.8E-43)
            if (r3 == 0) goto L50
            com.esotericsoftware.asm.Edge r3 = r0.j
            com.esotericsoftware.asm.Edge r3 = r3.c
            if (r2 == r3) goto L5c
        L50:
            com.esotericsoftware.asm.Label r3 = r2.b
            com.esotericsoftware.asm.Label r3 = r3.k
            if (r3 != 0) goto L5c
            com.esotericsoftware.asm.Label r3 = r2.b
            r3.k = r1
            com.esotericsoftware.asm.Label r1 = r2.b
        L5c:
            com.esotericsoftware.asm.Edge r2 = r2.c
            goto L42
        L5f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.asm.Label.b(com.esotericsoftware.asm.Label, long, int):void");
    }

    public int getOffset() {
        if ((this.a & 2) != 0) {
            return this.c;
        }
        throw new IllegalStateException("Label offset position has not been resolved yet");
    }

    public String toString() {
        return new StringBuffer("L").append(System.identityHashCode(this)).toString();
    }
}
